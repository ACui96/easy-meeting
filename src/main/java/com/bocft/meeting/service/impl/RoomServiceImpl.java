package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.*;
import com.bocft.meeting.mapper.ConferenceMapper;
import com.bocft.meeting.mapper.RoomMapper;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.Room;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.RoomService;
import com.bocft.meeting.vo.RoomVo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yang
 * @date 2022/4/12 9:47
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    ConferenceMapper conferenceMapper;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    PhoneUtil phoneUtil;


    /**
     * 判断预约开始时间是否在当前时间之后
     *
     * @param date
     * @param end
     * @return boolean
     * @author yang
     * @date 2022/4/12 16:00
     */
    private boolean timeIsTrue(String date, String end) {

        DateTime time = DateTime.parse(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        StringBuilder builder = new StringBuilder();
        String s = builder.append(date).append(" ").append(end).toString();
        DateTime time1 = DateTime.parse(s, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        if (time1.isBefore(time)) {
            return false;
        }

        return true;
    }

    @Override
    public int deleteRoom(long roomId) {
        //根据房间id查询预约信息
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id", roomId);
        List<Conference> conferencesList = new ArrayList<>();

        conferencesList = conferenceMapper.selectList(wrapper);

        //提取预约信息中的用户email，根据email发送邮件  新增删除会议室后，发短信通知该会议室绑定的所有参会人
        List<String> emailList = new ArrayList<>();
        List<String> phoneListAlreadySend = new ArrayList<>();



        if (conferencesList != null) {
            for (Conference conference : conferencesList) {
                List<String> tmpPhoneList = conferenceService.selectAttendeesPhonesByConId(conference.getConId());

                String uEmail = conference.getUEmail();
                String bName = conference.getBName();
                Date beginTime = conference.getCBeginTime();
                String cDate = conference.getCDate();

                //判断email是否重复发送以及预定时间是否在当前时间之后
                if (!emailList.contains(uEmail) && timeIsTrue(cDate, DateUtils.timeToString(beginTime))) {
                    emailUtil.sendEmail(uEmail, "会议室关闭", "抱歉，您所预定的会议室" + bName + "场所已关闭，请谅解。如还需会议室，请到系统重新预约会议室。感谢配合！", mailSender);
                    emailList.add(uEmail);
                }
                // 发短信
                for (String phone : tmpPhoneList) {
                    if (!phoneListAlreadySend.contains(phone) && timeIsTrue(cDate, DateUtils.timeToString(beginTime))) {
                        sendSms(phone, bName);
                        phoneListAlreadySend.add(phone);
                    }
                }
                //删除预定信息
                conferenceMapper.delete(new QueryWrapper<>(conference));
            }
        }
        return roomMapper.deleteById(roomId);
    }

    private void sendSms(String phone, String bName) {
        String[] phoneNumberSet1 = new String[]{phone};
        String[] templateParamSet = new String[]{bName};
        String templateId = MsgType.AFTER_ROOM_DEL.getTmpId();
        phoneUtil.sendMessage(phoneNumberSet1, templateParamSet, templateId);
    }

    @Override
    public boolean addRoom(RoomVo roomVo) {

        Room room = new Room();
        BeanUtils.copyProperties(roomVo, room);
        int i = roomMapper.insert(room);
        if (i == 1) {
            return true;
        }
        return false;
    }


}
