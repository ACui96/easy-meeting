package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.*;
import com.bocft.meeting.model.Attendees;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.service.AttendeesService;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.QueConferenceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Api(tags = "预约/延时会议接口(2.0)")
@Controller
@RequestMapping("/api/Conference")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendeesService attendeesService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据日期查询每个房间会议预订情况")
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

        //返回结果
        List<QueConferenceVo> list = null;
        Page page = null;

        //使用redis缓存会议室预约数据
        String key = "cDate_" + cDate;
        list = (List<QueConferenceVo>) redisTemplate.opsForValue().get(key);

        //如果存在，直接返回，无需查询数据库
        if(list!=null){
            page = PageUtils.CurrentPage(current, Constant.SIZE_ROOM, list);
            System.out.println("使用了redis缓存！");
            return Result.ok(page);
        }

        //如果不存在，需要查询数据库，将查询结果保存到Redis
        list = conferenceService.selectRoomByDate(cDate);
        redisTemplate.opsForValue().set(key,list,60, TimeUnit.MINUTES);

        page = PageUtils.CurrentPage(current, Constant.SIZE_ROOM, list);
        return Result.ok(page);
    }


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

            //清除当天预约记录的Redis缓存
            redisTemplate.delete("cDate_" + cDate);
            //System.out.println("缓存已清除");
            return Result.ok("申请成功，申请结果将以短信通知");
        }
        return Result.fail("申请失败，该会议室已经被人预约");
    }


    /*@ApiOperation("展示参会人员列表")
    @GetMapping("userList")
    @ResponseBody
    public synchronized Result getUserList(@RequestParam(name = "uName", required = false) String name,
                                           @RequestParam(name = "current", required = false) Long current) {
        //List<RoomVo> list = conferenceService.selectByTime(cDate,begin,end);
        List<UserInforVo> list = userService.selectUserByName(name);
        Page page = PageUtils.CurrentPage(current, Constant.LIMIT, list);
        return Result.ok(page);
    }*/


    /*@ApiOperation("根据房间名查看已经预约信息")
    @GetMapping("OneCon")
    @ResponseBody
    public Result selectOneCon(@RequestParam(name = "房间名") String bName,
                               @RequestParam(name = "日期") String cDate) {

        OneConferenceVo oneConferenceVo = conferenceService.selectOneCon(bName, cDate.substring(0, 10));
        return Result.ok(oneConferenceVo);
    }*/

    /*@ApiOperation("根据id删除")
    @DeleteMapping("delete")
    @ResponseBody
    public Result removeCon(@RequestParam(name = "预约id") int id) {
        conferenceService.removeById(id);
        return Result.ok("删除成功");
    }*/

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
            //清除当天预约记录的Redis缓存
            redisTemplate.delete("cDate_" + cDate);
            return Result.ok("延时成功");
        }
        return Result.fail("延时失败");
    }
}
