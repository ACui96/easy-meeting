package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.Attendees;
import com.bocft.meeting.vo.AttendeesVo;

import java.util.List;

public interface AttendeesService extends IService<Attendees> {
    int insert(Attendees attendees);
    void deleteByUId(Long uId);
    List<AttendeesVo> findAttendeesByConId(String conferenceId);

}
