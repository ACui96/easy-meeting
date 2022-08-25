package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.mapper.AttendeesMapper;
import com.bocft.meeting.model.Attendees;
import com.bocft.meeting.service.AttendeesService;
import com.bocft.meeting.vo.AttendeesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeesServiceImpl extends ServiceImpl<AttendeesMapper, Attendees> implements AttendeesService {

    @Autowired
    private AttendeesMapper attendeesMapper;

    @Override
    public int insert(Attendees attendees) {
        return attendeesMapper.insert(attendees);
    }

    @Override
    public void deleteByUId(Long uId) {
        QueryWrapper<Attendees> attendeesQueryWrapper=new QueryWrapper<>();
        attendeesQueryWrapper.eq("a_uid",uId);
        attendeesQueryWrapper.eq("a_ustatus",1);
        List<Attendees> attendeesList = attendeesMapper.selectList(attendeesQueryWrapper);
        if(attendeesList.size()==0) return;
        for (Attendees attendees:attendeesList) {
            attendees.setAUStatus(0);
            attendeesMapper.updateById(attendees);
        }
        return;
    }

    @Override
    public List<AttendeesVo> findAttendeesByConId(String conferenceId) {
        List<AttendeesVo> list = attendeesMapper.selectAttendeesByConferenceId(Long.parseLong(conferenceId));
        return list;
    }
}
