package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.*;
import com.bocft.meeting.model.Attendees;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.AttendeesService;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.OneConferenceVo;
import com.bocft.meeting.vo.QueConferenceVo;
import com.bocft.meeting.vo.UserInforVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Api(tags = "预约相关/用户接口(1.0)")
@Controller
@RequestMapping("/api/con")
public class ConferenceApiController {
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendeesService attendeesService;

/*    @ApiOperation("根据日期查询每个房间会议预订情况")
    @GetMapping("indexList")
    @ResponseBody
    public Result selectConByDate(@RequestParam(name = "request") String cDate,
                                  @RequestParam(name = "页", required = false) Long current) {
        cDate = cDate.substring(0, 10);
        DateTime date = DateUtils.stringToDate(cDate);
        DateTime now = DateTime.parse(DateTime.now().toString("yyyy-MM-dd"),
                DateTimeFormat.forPattern("yyyy-MM-dd"));
        if (date.isBefore(now)) {
            return Result.fail("日期必须大于等于今日");
        }
        //返回list
        List<ConferenceVo> list = conferenceService.selectConByDate(cDate);
        Page page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);
    }*/

    @ApiOperation("根据日期查询每个房间会议预订情况2.0")
    @GetMapping("conList")
    @ResponseBody
    public Result selectRoomByDate(@RequestParam(name = "request") String cDate,
                                   @RequestParam(name = "current", required = false) Long current){
        cDate = cDate.substring(0, 10);
        DateTime date = DateUtils.stringToDate(cDate);
        DateTime now = DateTime.parse(DateTime.now().toString("yyyy-MM-dd"),
                DateTimeFormat.forPattern("yyyy-MM-dd"));
        if (date.isBefore(now)) {
            return Result.fail("日期必须大于等于今日");
        }
        //返回list
        List<QueConferenceVo> list = conferenceService.selectRoomByDate(cDate);
        Page page = PageUtils.CurrentPage(current, Constant.SIZE_ROOM, list);
        return Result.ok(page);
    }


/*    @ApiOperation("根据具体时间查询每个房间会议预订情况")
    @GetMapping("roomList")
    @ResponseBody
    public Result selectConByTime(@RequestParam(name = "cDate") String cDate,
                                               @RequestParam(name = "begin") String begin,
                                               @RequestParam(name = "end") String end,
                                               @RequestParam(name = "current", required = false) Long current) {
        //cDate = cDate.substring(0, 10);
        DateTime date = DateUtils.stringToDate(cDate);
        DateTime now = DateTime.parse(DateTime.now().toString("yyyy-MM-dd"),
                DateTimeFormat.forPattern("yyyy-MM-dd"));
        if (date.isBefore(now)) {
            return Result.fail("日期必须大于等于今日");
        }

        //日期格式以及开始与结束时间是否合理 开始时间必须小于结束时间
        if (!DateUtils.timeIsTrue(cDate, begin, end)) {
            return Result.fail(ResultCodeEnum.TIME_DATE_FAIL.getMessage());
        }

        //返回list
        //List<ConferenceVo> list = conferenceService.selectConByDate(cDate);
        List<RoomVo> list = conferenceService.selectByTime(cDate, begin, end);
        Page page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);
    }*/

    @ApiOperation("预定会议室")
    @PostMapping("reserve")
    @ResponseBody
    public synchronized Result reserveCon(@RequestParam(name = "userId") Long userId,
                              @RequestParam(name = "cDate") String cDate,
                              @RequestParam(name = "begin") String begin,
                              @RequestParam(name = "end") String end,
                              @RequestParam(name = "roomId") Long roomId,
                              @RequestParam(name = "comment",required = false) String comment,
                              @RequestParam(name = "attends") List<Long> attends){
        //日期格式以及开始与结束时间是否合理
        if (!DateUtils.timeIsTrue(cDate, begin, end)) {
            return Result.fail(ResultCodeEnum.TIME_DATE_FAIL.getMessage());
        }

        //判断预约房间是否合理
        if (!conferenceService.roomIsTrue(roomId)) {
            throw new ConferenceException(ResultCodeEnum.ROOM_FAIL);
        }

        //判断预约人是否合理
        if(!conferenceService.UserIsTrue(userId)){
            throw new ConferenceException(ResultCodeEnum.ROOM_FAIL);
        }

        //没有查到数据说明可以预约 ** 可以提交申请
        if(conferenceService.isConflict(cDate,roomId,begin,end)){
            //获取到该次申请记录的ID
            Long conId = conferenceService.insertRes(cDate,roomId,begin,end,userId,comment);

            if(attends!=null){
                for(Long attend:attends){
                    System.out.println(attend);
                    Attendees attendees = new Attendees();
                    attendees.setAUId(attend);
                    attendees.setACId(conId);
                    attendees.setAMsgStatus(1);
                    attendeesService.insert(attendees);
                }
            }

            return Result.ok("申请成功，申请结果将以短信通知");
        }
        return Result.fail("申请失败，该会议室已经被人预约");
    }

    /*@ApiOperation("预订房间/最多预约30天")
    @PostMapping("order")
    @ResponseBody
    public synchronized Result orderCon(@RequestParam(name = "开始日期") String cDate,
                                        @RequestParam(name = "房间名") String bName,
                                        @RequestParam(name = "开始时间") String begin,
                                        @RequestParam(name = "结束时间") String end,
                                        @RequestParam(name = "预订周期") Integer type,
                                        @RequestParam(name = "预订次数") Integer num,
                                        @RequestParam(name = "预订人邮箱") String uEmail,
                                        @RequestParam(name = "会议主要内容") String comment) {
        //邮箱格式
        if (!EmailUtil.emailIsTrue(uEmail)) {
            return Result.fail("邮箱格式不对");
        }
        //如果预订周期为0，则预订次数默认为1
        if (type == Constant.CON_TYPE_NO) {
            num = 1;
        }
        //日期格式以及开始与结束时间是否合理
        if (!DateUtils.timeIsTrue(cDate, begin, end)) {
            return Result.fail(ResultCodeEnum.TIME_DATE_FAIL.getMessage());
        }
        //判断邮箱是否存在
        if (!conferenceService.userIsTrue(uEmail)) {
            throw new ConferenceException(ResultCodeEnum.EMAIL_FAIL);
        }
        //判断预约房间是否合理
        if (!conferenceService.roomIsTrue(bName)) {
            throw new ConferenceException(ResultCodeEnum.ROOM_FAIL);
        }
        //获取预约的日期（年月日）集合
        //因为没有多次预约功能 注释
        List<String> dateList = DateUtils.getDate(cDate, type, num);
        //查询预约时间是否冲突，并记录是哪些天冲突。
        List<String> conflictList = conferenceService.isConflict(dateList, bName, begin, end);
        //如果查到数据说明冲突
        if (conflictList.size() > 0) {
            return Result.fail("这些天：" + conflictList + "的预约时间已经被预约，请重新选择", ResultCodeEnum.CON_FAIl);
        }
        //没有查到数据说明可以预约 ** 可以提交申请
        conferenceService.insertCons(dateList, bName, begin, end, uEmail, comment);
//        return Result.ok("预约成功，请查看邮件");
        return Result.ok("申请成功，申请结果将以短信通知");
    }*/

    @ApiOperation("展示参会人员列表")
    @GetMapping("userList")
    @ResponseBody
    public synchronized Result getUserList(@RequestParam(name = "uName", required = false) String name,
                                           @RequestParam(name = "current", required = false) Long current) {
        //List<RoomVo> list = conferenceService.selectByTime(cDate,begin,end);
        List<UserInforVo> list = userService.selectUserByName(name);
        Page page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);
    }

/*    @ApiOperation("根据邮箱查找个人预订记录")
    @GetMapping("email")
    @ResponseBody
    public Result selectByEmail(@RequestParam(name = "页", required = false) Long current,
                                @Email @RequestParam(name = "邮箱") String email) {
        //邮箱格式
        if (!EmailUtil.emailIsTrue(email)) {
            return Result.fail("邮箱格式不对");
        }
        List<UserConferenceVo> list = conferenceService.selectByEmail(email);
        Page<UserConferenceVo> page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);

    }*/

    @ApiOperation("根据房间名查看已经预约信息")
    @GetMapping("OneCon")
    @ResponseBody
    public Result selectOneCon(@RequestParam(name = "房间名") String bName,
                               @RequestParam(name = "日期") String cDate) {

        OneConferenceVo oneConferenceVo = conferenceService.selectOneCon(bName, cDate.substring(0, 10));
        return Result.ok(oneConferenceVo);
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("delete")
    @ResponseBody
    public Result removeCon(@RequestParam(name = "预约id") int id) {
        conferenceService.removeById(id);
        return Result.ok("删除成功");
    }

/*    @ApiOperation("根据修改")
    @PutMapping("put")
    @ResponseBody
    public Result updateCon(@RequestParam(name = "预约id") long id,
                            @RequestParam(name = "会议主要内容") String comment,
                            @RequestParam(name = "开始日期") String cDate,
                            @RequestParam(name = "房间名") String bName,
                            @RequestParam(name = "开始时间") String begin,
                            @RequestParam(name = "结束时间") String end) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //已经过去的会议不能修改
        Conference serviceById = conferenceService.getById(id);
        String cDate1 = serviceById.getCDate();
        String cBeginTime = DateUtils.timeToString(serviceById.getCBeginTime());
        DateTime dateTime = DateUtils.addDateTime(cDate1, cBeginTime);
        DateTime now = DateTime.now();
        if (dateTime.isBefore(now)) {
            return Result.fail("该会议已经开始或结束，不能修改");
        }

        //开始与结束时间是否合理

        if (!DateUtils.timeIsTrue(cDate, begin, end)) {
            return Result.fail(ResultCodeEnum.TIME_DATE_FAIL.getMessage());
        }
        //判断修改房间是否存在
        if (!conferenceService.roomIsTrue(bName)) {
            return Result.fail(ResultCodeEnum.ROOM_FAIL.getMessage());
        }
        //查询/预约时间是否冲突。
        if (!conferenceService.isConflict(cDate, bName, begin, end)) {
            return Result.fail(ResultCodeEnum.CON_FAIl.getMessage());
        }
        //插入更新数据
        Conference conference = conferenceService.setConference(id, bName, cDate, comment, begin, end);
        //更新
        conferenceService.updateById(conference);
        return Result.ok("修改成功");

    }*/

/*    @ApiModelProperty("会议记录查询")
    @GetMapping("maintain")
    @ResponseBody
    public Result maintainCon(@RequestParam(name = "用户ID") long userId,
                              @RequestParam(name = "页", required = false) Long current) {
        User userMsg = userService.selByIdUser((int) userId);
        //判断用户还是管理员
        if (userMsg.getUVerify() == 0) {
            //用户维护自己发布的会议
            List<UserConferenceVo> list = conferenceService.selectByUserId(userId);

            if (list.size() == 0) {
                return Result.fail("您还没预约任何会议");
            }
            List<UserConferenceVo> beforList = new LinkedList<>();
            List<UserConferenceVo> afterList = new LinkedList<>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                UserConferenceVo tmp = (UserConferenceVo) it.next();
                if(tmp.getCDate().compareTo(new Date().toString())<0){
                    afterList.add(tmp);
                }else{
                    beforList.add(tmp);
                }
            }
            Page<UserConferenceVo> page = PageUtils.CurrentPage(current, Constant.LIMIT, afterList);
            Page<UserConferenceVo> page1 = PageUtils.CurrentPage(current,Constant.LIMIT,beforList);
            return Result.ok(page);
        } else if (userMsg.getUVerify() == 1) {
            //管理员维护全部会议
            String date = DateTime.now().toString("yyyy-MM-dd");
            List<UserConferenceVo> list = conferenceService.selectAll(date);
            if (list.size() == 0) {
                return Result.fail("您还没预约任何会议");
            }
            Page<UserConferenceVo> page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
            return Result.ok(page);
        }
        return Result.fail();
    }*/

    @ApiModelProperty("管理员审批会议")
    @GetMapping("admin/conference/review")
    @ResponseBody
    public Result reviewCon(@RequestParam(name = "用户ID") long userId,
                            @RequestParam(name = "页", required = false) Long current) {
        User user = userService.selByIdUser((int) userId);
        if (user.getUVerify() != 1) {
            return Result.fail("无审批权限！");
        }
        return Result.fail();
    }


//    @ApiModelProperty("多条件筛选查询会议")
//    @PostMapping("selectByMulCondition")
//    @ResponseBody
//    public Result selectByMulCondition(
//            @RequestParam(name = "页", required = false) Long current,
//            @RequestBody(required = true) ConferenceQueryVo queryVo) {
//
//        List<ConferenceWithPhone> conferences = conferenceService.selectByExample(queryVo);
//
//        if (conferences.size() == 0) {
//            return Result.fail("无符合条件的会议预约记录！");
//        }
//        Page<Conference> page = PageUtils.CurrentPage(current, Constant.LIMIT, conferences);
//        return Result.ok(page);
//    }

    @ApiOperation("会议延时 下一时间段的会没有其他用户预订信息，可以进行10，20，30分钟，以及1个小时的延时")
    @PostMapping("delayCon")
    @ResponseBody
    public Result delayCon(
            @RequestParam(name = "会议Id") Long conId,
            @RequestParam(name = "delTime") int minutes
    ) throws ParseException {
        if (minutes > 60) {
            return Result.fail("最多支持延迟一个小时");
        }
        Conference curCon = conferenceService.selectConById(conId);
//        String bName = curCon.getBName();
        String cDate = curCon.getCDate();
        String roomId = String.valueOf(curCon.getRoomId());

        Date cEndTime = curCon.getCEndTime();

        String endTimeOrigin = DateUtils.timeToString(cEndTime);


        SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");

        String endTimeNew = df.format(new Date(cEndTime.getTime() + (long) minutes * 60 * 1000));


        if (!conferenceService.isConflict(cDate, roomId, endTimeOrigin, endTimeNew)) {
            return Result.fail("时间冲突，不允许延时！");
        }
        curCon.setCEndTime(df.parse(endTimeNew));

        boolean updateRet = conferenceService.updateById(curCon);

        if (updateRet) {
            return Result.ok("延时成功");
        }
        return Result.fail("延时失败");
    }
}
