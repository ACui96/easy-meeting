package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.Constant;
import com.bocft.meeting.common.PageUtils;
import com.bocft.meeting.common.Result;
import com.bocft.meeting.common.redis.RedisUtils;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.service.AttendeesService;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.MyOrderService;
import com.bocft.meeting.vo.AttendeesVo;
import com.bocft.meeting.vo.UserConferenceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xz
 * @date 2022/7/18 10:27
 */
@Api(tags = "我的预定")
@RestController
@RequestMapping("/user/conference")
public class UserOrderController {
    @Autowired
    MyOrderService myOrderService;
    @Autowired
    AttendeesService attendeesService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ConferenceService conferenceService;
    @ApiOperation(value = "查询个人预约")
    @GetMapping("/findAll")
    public  Result findAll(@RequestParam(name = "userId") String userId,
                          @RequestParam(name = "page1", required = false) Long current1,
                          @RequestParam(name = "page2", required = false) Long current2){

        if(current1!=null&&current1<=0) current1=1L;
        if(current2!=null&&current2<=0) current2=1L;
        Map<String, List<UserConferenceVo>> map = myOrderService.findAll(userId);
        List<UserConferenceVo> list1=map.get("NotStartOrInProcess");
        Page<UserConferenceVo> page1 = PageUtils.CurrentPage(current1, Constant.LIMIT, list1);
        List<UserConferenceVo> list2=map.get("IsOver");
        Page<UserConferenceVo> page2 = PageUtils.CurrentPage(current2, Constant.LIMIT, list2);
        Map<String,Page<UserConferenceVo>> pageMap=new HashMap<>();
        pageMap.put("NotStartOrInProcess",page1);
        pageMap.put("IsOver",page2);
        return Result.ok(pageMap);
        //NotStartOrInProcess    IsOver
    }
    @ApiOperation(value = "查询未开始的记录")
    @GetMapping("/findNotStarted")
    public  Result findNotStart(@RequestParam(name = "userId") String userId,
                           @RequestParam(name = "page", required = false) Long current1){
        if(current1!=null&&current1<=0) current1=1L;
        List<UserConferenceVo> list = myOrderService.findIng(userId);
        Page<UserConferenceVo> page = PageUtils.CurrentPage(current1, Constant.LIMIT, list);
        if(list.size()==0) return Result.fail("暂无数据");
        else return Result.ok(page);
    }
    @ApiOperation(value = "查询已结束的记录")
    @GetMapping("/findOver")
    public  Result findOver(@RequestParam(name = "userId") String userId,
                                @RequestParam(name = "page", required = false) Long current1){
        if(current1!=null&&current1<=0) current1=1L;
        List<UserConferenceVo> list = myOrderService.findOver(userId);
        Page<UserConferenceVo> page = PageUtils.CurrentPage(current1, Constant.LIMIT, list);
        if(list.size()==0) return Result.fail("暂无数据");
        else return Result.ok(page);
    }
    @ApiOperation(value = "取消预约")
    @GetMapping("/cancel")
    public  Result cancelCon(@RequestParam(name = "conId") String conId){
        int isSuccess=myOrderService.cancel(conId);

        if(isSuccess==1||isSuccess==2) {
            //清除redis缓存
            Conference conference = conferenceService.selectConById(Long.valueOf(conId));
            String cDate = conference.getCDate();
            redisUtils.deleteByDate(cDate);
        }
        if(isSuccess==1) {
            return Result.ok();
        }
        if(isSuccess==2) return Result.fail("会议取消成功，通知与会人失败");
        return Result.fail("取消失败");
    }
    @ApiOperation(value = "查看与会人")
    @GetMapping("/showAttendees")
    public Result showAttendees(@RequestParam(name = "conId") String conId){
        List<AttendeesVo> list = attendeesService.findAttendeesByConId(conId);
        if(list.size()==0) return Result.fail("暂无数据");
        return Result.ok(list);
    }
}
