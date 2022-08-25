package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.*;
import com.bocft.meeting.mapper.AttendeesMapper;
import com.bocft.meeting.mapper.ConferenceMapper;
import com.bocft.meeting.mapper.RoomMapper;
import com.bocft.meeting.mapper.UserMapper;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.MyOrderService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.UserConferenceVo;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xz
 * @date 2022/7/18 9:40
 */
@Service
@Slf4j
public class MyOrderServiceImpl extends ServiceImpl<ConferenceMapper,Conference> implements MyOrderService {
    @Autowired
    ConferenceMapper conferenceMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    AttendeesMapper attendeesMapper;
    @Autowired
    PhoneUtil phoneUtil;
    @Autowired
    UserService userService;
    @Override
    /**
     * 查找所有记录
     */
    public Map<String,List<UserConferenceVo>> findAll(String uId) {
        List<UserConferenceVo> voList1 = new ArrayList<>();
        List<UserConferenceVo> voList2 = new ArrayList<>();
        Long userId = Long.parseLong(uId);
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        //queryWrapper.last("order by nvl2(update_time, update_time, create_time) desc");
        wrapper.last("order by c_date desc,c_begin_time desc");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            BeanUtils.copyProperties(conference, vo);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("room_id",conference.getRoomId());
            vo.setCapacities(roomMapper.selectOne(queryWrapper).getCapacities());
            //设置会议审批状态
            int status=conference.getStatus();
            switch (status){
                case 1:vo.setStatus("未审批");break;
                case 2:vo.setStatus("通过审批");break;
                case 3:vo.setStatus("已被拒绝");break;
                case 4:vo.setStatus("已取消");break;
            }
            //会议进行状态 0-未开始 1-正在进行中 2-历史记录
            if(TimeComparation.comparation(vo.getCDate(),vo.getCBeginTime(),vo.getCEndTime())==2){
                voList2.add(vo);
            }else voList1.add(vo);
        }
        Map<String,List<UserConferenceVo>> map=new HashMap<>();
        map.put("NotStartOrInProcess",voList1);
        map.put("IsOver",voList2);
        return map;
    }

    /**
     * 用户取消会议
     * @param conId
     * @return
     */
    //0代表取消失败、1代表成功、2代表发短信失败
    @Override
    public int cancel(String conId) {
        Long conferenceId=Long.parseLong(conId);
        Conference conference = conferenceMapper.selectById(conferenceId);
        int status=conference.getStatus();
        if(status==4||status==3) return 0;
        conference.setStatus(4);
        int i = conferenceMapper.updateById(conference);
        if(i!=1) return 0;
        if(status==1) return 1;
        //如果会议已审批且与会人已被通知，则发送短信通知取消
        String []templateParamSet = new String[6];
        templateParamSet[1] = DateUtils.getShorterDate(conference.getCDate()); // 2022-07-03 -> 07月03日
        Date cBeginTime = conference.getCBeginTime();
        templateParamSet[2] = DateUtils.timeToShorterString(cBeginTime);
        Date cEndTime = conference.getCEndTime();
        templateParamSet[3] = DateUtils.timeToShorterString(cEndTime);
        templateParamSet[4] = conference.getBName();
        templateParamSet[5] = conference.getComments();
        List<String> numbers=attendeesMapper.selectAttendeesPhonesByConId(conferenceId);
        if(numbers.size()==0){
            return 1;
        }
        for(String number:numbers){
            String[] phoneSet = new String[]{number};
            User user=userService.getByPhone(number);
            templateParamSet[0]=user.getUName();
            SendSmsResponse sendSmsResponse = phoneUtil.sendMessage(phoneSet, templateParamSet, "1502110");
            if (!Objects.equals(sendSmsResponse.getSendStatusSet()[0].getCode(), "Ok")) {
                log.error("手机号{}短信发送失败:+”\t"+sendSmsResponse.getSendStatusSet()[0].getMessage(),number);
            } else {
                log.info("手机号{}通知短信发送成功", numbers);
            }
        }
        return 1;

    }

    @Override
    public List<UserConferenceVo> findIng(String uId) {
        List<UserConferenceVo> voList1 = new ArrayList<>();
        Long userId = Long.parseLong(uId);
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        //queryWrapper.last("order by nvl2(update_time, update_time, create_time) asc");
        wrapper.last("order by c_date asc,c_begin_time asc");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            //会议进行状态 0-未开始 1-正在进行中 2-历史记录
            int i = TimeComparation.comparation(conference.getCDate(),conference.getCBeginTime(),conference.getCEndTime());
            if(i==0||i==1){
                //未审批，通过审批
                if(conference.getStatus()==1||conference.getStatus()==2)
                    BeanUtils.copyProperties(conference, vo);
                else
                    continue;
            }else continue;
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("room_id",conference.getRoomId());
            vo.setCapacities(roomMapper.selectOne(queryWrapper).getCapacities());
            vo.setBLocation(roomMapper.selectOne(queryWrapper).getBLocation());
            //设置会议审批状态
            int status=conference.getStatus();
            switch (status){
                case 1:vo.setStatus("未审批");break;
                case 2:vo.setStatus("通过审批");break;
            }
            voList1.add(vo);
        }
        return voList1;
    }

    @Override
    public List<UserConferenceVo> findOver(String uId) {
        List<UserConferenceVo> voList1 = new ArrayList<>();
        Long userId = Long.parseLong(uId);
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        //queryWrapper.last("order by nvl2(update_time, update_time, create_time) desc");
        wrapper.last("order by c_date desc,c_begin_time desc");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            //会议进行状态 0-未开始 1-正在进行中 2-历史记录
            int i = TimeComparation.comparation(conference.getCDate(),conference.getCBeginTime(),conference.getCEndTime());
            if(i==2){
                BeanUtils.copyProperties(conference, vo);
            }else {
                if(conference.getStatus()==3||conference.getStatus()==4){
                    BeanUtils.copyProperties(conference, vo);
                }
                else
                    continue;
            }
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("room_id",conference.getRoomId());
            vo.setCapacities(roomMapper.selectOne(queryWrapper).getCapacities());
            vo.setBLocation(roomMapper.selectOne(queryWrapper).getBLocation());
            //设置会议审批状态
            int status=conference.getStatus();
            switch (status){
                case 1:vo.setStatus("未审批");break;
                case 2:vo.setStatus("通过审批");break;
                case 3:vo.setStatus("已被拒绝");break;
                case 4:vo.setStatus("已取消");break;
            }
            voList1.add(vo);
        }
        return voList1;
    }
}
