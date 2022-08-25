package com.bocft.meeting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.vo.NameNumber;
import com.bocft.meeting.vo.NoTimeConferenceVo;
import com.bocft.meeting.vo.RoomVo;
import com.bocft.meeting.vo.TimeConferenceVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConferenceMapper extends BaseMapper<Conference> {

    List<NoTimeConferenceVo> selectIndexByDate(String cDate);

    List<TimeConferenceVo> selectRoomByDate(String cDate);

    //根据会议室使用情况导出数据 参数为天数
    List<NameNumber> selectNumByRoomID(int id);

    //根据申请人申请情况导出数据 参数为天数
    List<NameNumber> selectNumByUserID(int id);

    //根据时间选择有空闲的会议室
    List<RoomVo> selectByTime(String date, String begin, String end);

    Long updateOldCon();
}
