package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.*;
import com.bocft.meeting.common.redis.RedisUtils;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.to.ConferenceWithPhone;
import com.bocft.meeting.vo.ConferenceQueryVo;
import com.bocft.meeting.vo.UserConferenceVo;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Acui
 * @date 2022年07月06日 15:11
 */
@Controller
@Api(tags = "管理员审批会议申请相关")
@Slf4j
@RequestMapping("/admin/conference")
public class AdminReviewController {

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    PhoneUtil phoneUtil;

    @Autowired
    UserService userService;

    @Autowired
    RedisUtils redisUtils;

    @ApiOperation("管理员更改预定申请状态  1-未审批 2-通过审批 3-拒绝")
    @PostMapping("setConStatus")
    @ResponseBody
    public Result setConStatus(@RequestParam(name = "applyId") Long applyId,
                               @RequestParam(name = "status") int status) throws ParseException {

        Conference conference = conferenceService.selectConById(applyId);
        conference.setStatus(status);
        boolean set = conferenceService.updateById(conference);
        if (!set) {
            return Result.fail("操作失败");
        }
        String msg_type = "";
        Conference conference1 = conferenceService.selectConById(applyId);
        String[] phoneSet;
        String[] templateParamSet;

        if (status == 2) {
            msg_type = "审批通过";
            // 通知参会人
            List<String> phones = conferenceService.selectAttendeesPhonesByConId(applyId);
            // 短信参数 会议通知：尊敬的{1}您好，请于{2}{3}-{4}准时到{5}参加{6}会议，非本人请忽略此短信。conferenceService = {$Proxy91@10244}

            templateParamSet = new String[6];
            templateParamSet[1] = DateUtils.getShorterDate(conference1.getCDate()); // 2022-07-03 -> 07月03日
            Date cBeginTime = conference1.getCBeginTime();
            templateParamSet[2] = DateUtils.timeToShorterString(cBeginTime);
            Date cEndTime = conference1.getCEndTime();
            templateParamSet[3] = DateUtils.timeToShorterString(cEndTime);
            templateParamSet[4] = conference1.getBName();
            templateParamSet[5] = conference1.getComments();

            for (String phone : phones) {
                phoneSet = new String[]{phone};
                User user = userService.getByPhone(phone);
                String uName = user.getUName();
                templateParamSet[0] = uName;
                SendSmsResponse sendSmsResponse = phoneUtil.sendMessage(phoneSet, templateParamSet, "1471530");
                if (!Objects.equals(sendSmsResponse.getSendStatusSet()[0].getMessage(), "send success")) {
                    log.error("手机号{}短信发送失败:+”\t" + sendSmsResponse.getSendStatusSet()[0].getMessage(), phone);
                    return Result.fail(sendSmsResponse.getSendStatusSet());
                } else {
                    log.info("手机号{}通知短信发送成功", phone);
                }
            }

            //审批通过某个申请后，检查与该条记录时间区间有重合且未审核（c_status = 1）的申请记录并自动拒绝
//            int ret = conferenceService.rejectConflictCons(applyId);
//            if (ret > 0) {
//                log.info("===========拒绝了与{}冲突的会议============", applyId);
//            }

        } else if (status == 3) {
            msg_type = "未通过";
            // 通知申请人, 发送拒绝短信
            //尊敬的{1}您好，会议室预约申请{2}，会议室名称：{3},预约时间：{4}，{5}-{6}，请重新申请。
            long userId = conference1.getUserId();
            User applyUser = userService.selByIdUser((int) userId);
            templateParamSet = new String[6];
            templateParamSet[0] = applyUser.getUName();
            templateParamSet[1] = msg_type;
            templateParamSet[2] = conference1.getBName();
            templateParamSet[3] = conference1.getCDate();
            Date cBeginTime = conference1.getCBeginTime();
            templateParamSet[4] = DateUtils.timeToShorterString(cBeginTime);
            Date cEndTime = conference1.getCEndTime();
            templateParamSet[5] = DateUtils.timeToShorterString(cEndTime);

            phoneSet = new String[]{applyUser.getUPhone()};
            SendSmsResponse sendSmsResponse = phoneUtil.sendMessage(phoneSet, templateParamSet, "1471528");
            if (Objects.equals(sendSmsResponse.getSendStatusSet()[0].getCode(), "OK")) {
                log.info("手机号{}审批未通过短信发送成功", phoneSet[0]);
            } else {
                log.error("手机号{}短信发送失败:+”\t" + sendSmsResponse.getSendStatusSet()[0].getMessage(), phoneSet[0]);
                return Result.fail(sendSmsResponse.getSendStatusSet());
            }
        }

        //清除Redis会议记录缓存
        redisUtils.deleteByDate(conference1.getCDate());
        return Result.ok("操作成功，" + msg_type + "短信已发送！");
    }


    @ApiOperation("根据审批状态查询申请列表  1-未审批 2-通过审批 3-拒绝")
    @GetMapping("getConByStatus")
    @ResponseBody
    public Result getConByStatus(
            @RequestParam(name = "页", required = false) Long current,
            @RequestParam(name = "会议申请编号", required = true) int status
    ) {

        List<UserConferenceVo> list = conferenceService.selectByStatus(status);
        Page<UserConferenceVo> page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);
    }

    @ApiOperation("多条件筛选查询会议")
    @PostMapping("selectByMulCondition")
    @ResponseBody
    public Result selectByMulCondition(
            @RequestParam(name = "页", required = false) Long current,
            @RequestBody(required = true) ConferenceQueryVo queryVo) throws ParseException {

        List<ConferenceWithPhone> conferences = conferenceService.selectByExample(queryVo);

        if (conferences.size() == 0) {
            return Result.fail("无符合条件的会议预约记录！");
        }
        Page<Conference> page = PageUtils.CurrentPage(current, Constant.LIMIT, conferences);
        return Result.ok(page);
    }


    @ApiOperation("获取冲突且未审批申请集合")
    @GetMapping("getConflictCons")
    @ResponseBody
    public Result getConflictCons(
            @RequestParam(name = "页", required = false) Long current,
            @RequestParam(name = "会议申请编号") Long applyId) {
        Conference con = conferenceService.getById(applyId);
        List<Conference> conflictCons = conferenceService.getConflictCons(
                con.getCDate(),
                con.getRoomId(),
                con.getCBeginTime(),
                con.getCEndTime(),
//                DateUtils.dateToString(con.getCBeginTime()),
//                DateUtils.dateToString(con.getCEndTime()),
                1 // 未审批
        );
        if (conflictCons.size() != 0) {
            Page<Conference> page = PageUtils.CurrentPage(current, Constant.LIMIT, conflictCons);
            return Result.ok(page);
        }
        return Result.fail("没有与之冲突的集合");

    }
}
