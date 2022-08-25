package com.bocft.meeting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocft.meeting.model.Attendees;
import com.bocft.meeting.vo.AttendeesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Acui
 * @date 2022年07月15日 10:30
 */
@Mapper
public interface AttendeesMapper extends BaseMapper<Attendees> {
    List<String> selectAttendeesPhonesByConId(Long conId);
    List<AttendeesVo> selectAttendeesByConferenceId(Long conId);
}
