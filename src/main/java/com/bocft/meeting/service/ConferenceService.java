package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.to.ConferenceWithPhone;
import com.bocft.meeting.vo.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;



public interface ConferenceService extends IService<Conference> {

    // 根据 id 获取预约信息
    Conference selectConById(Long conId);

    //根据日期获取预约信息
    List<ConferenceVo> selectConByDate(String cDate);

    //根据日期获取预约信息
    List<QueConferenceVo> selectRoomByDate(String cDate);

    //判断预约日期是否冲突
    List<String> isConflict(List<String> dateList, String bName, String begin, String end);


    //查询预约时间段不冲突的会议室
    List<RoomVo> selectByTime(String date, String begin, String end);

    //预订
    int insertCons(List<String> dateList, String bName, String begin, String end, String uEmail, String comment);

    int insertCon(String date,Long roomId,String begin,String end,String uEmail,String comment);

    Long insertRes(String date, Long roomId, String begin, String end, Long userId, String comment);

    //用户查看自己预订记录
    List<UserConferenceVo> selectByEmail(String email);

    OneConferenceVo selectOneCon(String bName, String cDate);

    boolean isConflict(String date, String roomId, String begin, String end);

    boolean isConflict(String date, Long room_id, String begin, String end);

    /**
     * 得到与当前申请冲突且未审批的申请集合
     *
     * @param date   日期
     * @param roomId 会议室编号
     * @param begin  开始时间
     * @param end    结束时间
     * @param status 会议审批状态
     * @return java.util.List<com.bocft.meeting.model.Conference> 冲突集合
     * @author Acui
     * @date 2022/8/10 16:12
     */
    List<Conference> getConflictCons(String date, Long roomId, Date begin, Date end, int status);

    boolean roomIsTrue(String bName);

    boolean roomIsTrue(Long roomId);

    // 用户已存在（通过邮箱判断）
    boolean userIsTrue(String email);

    boolean UserIsTrue(Long userId);

    // 用户已存在（通过手机号判断）
    boolean userPhoneAlreadyExist(String phone);

    Conference setConference(Long id, String bName, String cDate, String comment, String begin, String end);

    //通过userId查看自己的预定记录
    List<UserConferenceVo> selectByUserId(long userId);
    //查找全部预定记录
    List<UserConferenceVo> selectAll(String date);
    // 根据审批状态查询申请记录
    List<UserConferenceVo> selectByStatus(int status);

    // 多条件筛选查询
    List<ConferenceWithPhone> selectByExample(ConferenceQueryVo conferenceQueryVo) throws ParseException;

    // 根据 会议id 查询该会议所有参会人员手机号
    List<String> selectAttendeesPhonesByConId(Long conId);

    //根据会议室id统计使用情况
    List<NameNumber> countByRoom(int id);

    //根据预约人id统计使用情况
    List<NameNumber> countByUser(int id);

    /**
     * 审批-自动拒绝有冲突的会议申请
     *
     * @return
     */
    int rejectConflictCons(Long applyId) throws ParseException;

    /**
     * 更新过期的会议
     */
    Long updateOldCon();
}
