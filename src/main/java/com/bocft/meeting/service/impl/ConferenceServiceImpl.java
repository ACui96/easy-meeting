package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.*;
import com.bocft.meeting.controller.AdminReviewController;
import com.bocft.meeting.mapper.*;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.model.RepairMessage;
import com.bocft.meeting.model.Room;
import com.bocft.meeting.model.User;
import com.bocft.meeting.mapper.*;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.to.ConferenceWithPhone;
import com.bocft.meeting.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class ConferenceServiceImpl extends ServiceImpl<ConferenceMapper, Conference> implements ConferenceService {
    @Autowired
    private UserService userService;

    @Autowired
    private ConferenceMapper conferenceMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AttendeesMapper attendeesMapper;

    @Autowired
    private RepairMessageMapper repairMessageMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PhoneUtil phoneUtil;

    @Autowired
    private AdminReviewController adminReviewController;

    @Override

    public Conference selectConById(Long conId) {
        return conferenceMapper.selectById(conId);
    }

    @Override
    public List<ConferenceVo> selectConByDate(String cDate) {
        //list<返回字段>
        List<ConferenceVo> list = new ArrayList<>();
        //分页查出房间预订记录，一条记录对应一个房间
        List<NoTimeConferenceVo> noTimeConferenceVoList = conferenceMapper.selectIndexByDate(cDate);
        //将查到的数据，变成可以返回的类型，时间分段等等
        list = splitCon(noTimeConferenceVoList);

        return list;
    }

    @Override
    public List<QueConferenceVo> selectRoomByDate(String cDate) {
        //list<返回字段>
        List<QueConferenceVo> list = new ArrayList<>();
        //分页查出房间预订记录，一条记录对应一个房间
        List<TimeConferenceVo> TimeConferenceVoList = conferenceMapper.selectRoomByDate(cDate);
        //将查到的数据，变成可以返回的类型，时间分段等等
        list = splitTime(TimeConferenceVoList);

        return list;
    }


    @Override
    public List<String> isConflict(List<String> dateList, String bName, String begin, String end) {
        //创建预约日期冲突集合
        List<String> list = new ArrayList<>();
        for (String date : dateList) {
            //创建匹配器
            QueryWrapper<Conference> wrapper = new QueryWrapper<>();
            //数据库里面的开始时间小于用户填的结束时间  且数据库里面的结束时间大于用户填的开始时间
            wrapper.lt("c_begin_time", end);
            wrapper.gt("c_end_time", begin);
            wrapper.eq("b_name", bName);
            wrapper.eq("c_date", date);
            List<Conference> conferences = baseMapper.selectList(wrapper);
            if (conferences.size() > 0) {
                list.add(date);
            }
        }
        return list;
    }

    @Override
    public boolean isConflict(String date, String roomId, String begin, String end) {
        //创建匹配器
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        //数据库里面的开始时间小于等于用户填的结束时间  且数据库里面的结束时间大于大于用户填的开始时间
        wrapper.lt("c_begin_time", end);
        wrapper.gt("c_end_time", begin);
        wrapper.eq("c_date", date);
        wrapper.eq("room_id", roomId);
        wrapper.eq("status", 2);
        List<Conference> conference = baseMapper.selectList(wrapper);

        if (conference.size() != 0) return false;
        return true;
    }

    @Override
    public boolean isConflict(String date, Long roomId, String begin, String end) {
        //创建匹配器
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        //数据库里面的开始时间小于等于用户填的结束时间  且数据库里面的结束时间大于大于用户填的开始时间
        wrapper.lt("c_begin_time", end);
        wrapper.gt("c_end_time", begin);
        wrapper.eq("c_date", date);
        wrapper.eq("room_id", roomId);
        //状态为已审批
        //wrapper.eq("status",2).or().eq("status",1);
        wrapper.and(Wrapper->Wrapper.eq("status",1).or().eq("status",2));
        List<Conference> list = baseMapper.selectList(wrapper);
        if (list.size() != 0) return false;
        return true;
    }


    //返回不冲突的会议室合集
    @Override
    public List<RoomVo> selectByTime(String date, String begin, String end) {
        return conferenceMapper.selectByTime(date, begin, end);
    }

    @Override
    public List<Conference> getConflictCons(String date, Long room_id, Date begin, Date end, int status) {
        //创建匹配器
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        //数据库里面的开始时间小于等于用户填的结束时间  且数据库里面的结束时间大于大于用户填的开始时间
        wrapper.lt("c_begin_time", end);
        wrapper.gt("c_end_time", begin);
        wrapper.eq("c_date", date);
        wrapper.eq("room_id", room_id);
        //状态为未审批
        wrapper.eq("status", status);
        List<Conference> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public int insertCons(List<String> dateList, String bName, String begin, String end, String uEmail, String comment) {
        Room room = getRoomByName(bName);
        Long roomId = room.getRoomId();
        User user = getUserByEmail(uEmail);
        Long userId = user.getUserId();
        //创建插入对象
        Conference conference = new Conference();
        conference.setRoomId(roomId);
        conference.setBName(bName);
        conference.setUserId(userId);
        conference.setUEmail(uEmail);
        conference.setCBeginTime(DateUtils.stringToTime(begin).toDate());
        conference.setCEndTime(DateUtils.stringToTime(end).toDate());
        conference.setComments(comment);
        int insert = 0;
        for (String date : dateList) {
            conference.setCDate(date);
            //插入数据库
            int insert1 = conferenceMapper.insert(conference);
            insert += insert1;
        }
        if (insert <= 0) {
            throw new ConferenceException(ResultCodeEnum.FAIL);
        }
        //预约成功发送邮件
        StringBuilder builder = new StringBuilder();
        //拼接开会日期
        for (String s : dateList) {
            builder.append(s).append(" ").append(begin).append("-").append(end).append("和");
        }
        builder.deleteCharAt(builder.length() - 1);
        emailUtil.sendEmail(uEmail, "预订会议成功", "您好:" + uEmail + ",您预订的会议室是：" + bName + "您预订的时间有：" +
                builder + "请您准时参加，祝您工作顺利", mailSender);
        return insert;
    }

    @Override
    public int insertCon(String date, Long roomId, String begin, String end, String uEmail, String comment) {
        Room room = getRoomById(roomId);
        String bName = room.getBName();
        User user = getUserByEmail(uEmail);
        Long userId = user.getUserId();

        //创建插入对象
        Conference conference = new Conference();
        conference.setCDate(date);
        conference.setRoomId(roomId);
        conference.setBName(bName);
        conference.setUserId(userId);
        conference.setUEmail(uEmail);
        conference.setCBeginTime(DateUtils.stringToTime(begin).toDate());
        conference.setCEndTime(DateUtils.stringToTime(end).toDate());
        conference.setComments(comment);

        int insert = conferenceMapper.insert(conference);
        if (insert <= 0) {
            throw new ConferenceException(ResultCodeEnum.FAIL);
        }

        return insert;
    }

    @Override
    public Long insertRes(String date, Long roomId, String begin, String end, Long userId, String comment) {
        Room room = getRoomById(roomId);
        String bName = room.getBName();
        User user = getUserById(userId);
        String uEmail = user.getUMail();
        String uName = user.getUName();

        //创建插入对象
        Conference conference = new Conference();
        conference.setCDate(date);
        conference.setRoomId(roomId);
        conference.setBName(bName);
        conference.setUserId(userId);
        conference.setUEmail(uEmail);
        conference.setCBeginTime(DateUtils.stringToTime(begin).toDate());
        conference.setCEndTime(DateUtils.stringToTime(end).toDate());
        conference.setUName(uName);
        conference.setStatus(1);
        conference.setComments(comment);

        //得到的值为插入数据的自增ID主键
        int insert = conferenceMapper.insert(conference);
        if (insert <= 0) {
            throw new ConferenceException(ResultCodeEnum.FAIL);
        }

        return conference.getConId();
    }

    @Override
    public List<UserConferenceVo> selectByEmail(String email) {
        //返回对象
        List<UserConferenceVo> voList = new ArrayList<>();
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("u_email", email);
        wrapper.orderByDesc("c_date", "c_begin_time");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            BeanUtils.copyBean(conference, vo, UserConferenceVo.class);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public OneConferenceVo selectOneCon(String bName, String cDate) {
        //创建返回类
        OneConferenceVo vo = new OneConferenceVo();
        //根据房间名查看房间信息
        Room room = getRoomByName(bName);
        //塞信息
        vo.setCapacities(room.getCapacities());
        vo.setBRemark(room.getBRemark());
        vo.setBType(room.getBType());
        //根据房间名查询报修信息
        RepairMessage repairMessageByRName = getRepairMessageByRName(bName);
        if (repairMessageByRName != null) {
            vo.setRepairMessage(repairMessageByRName.getRepairMessage());
        }
        //根据日期和房间名查找预约记录
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("b_name", bName);
        wrapper.eq("c_date", cDate);
        List<Conference> list = conferenceMapper.selectList(wrapper);
        if (list.size() == 0) {
            return vo;
        }
        //创建字符串拼接，将预约用户和时间拼接
        StringBuilder builder = new StringBuilder("<strong>");
        for (Conference conference : list) {
            builder.append("用户").append(conference.getUEmail()).append("预约了").append(DateUtils.timeToString(conference.getCBeginTime())).
                    append("到").append(DateUtils.timeToString(conference.getCEndTime())).append("的会议").append("<br/>");
        }
        builder.append("<strong>");
        vo.setUEmail(builder.toString());
        return vo;
    }





    @Override
    public Conference setConference(Long id, String bName, String cDate, String comment, String begin, String end) {
        Conference conference = new Conference();
        //插入信息
        Conference selectById = conferenceMapper.selectById(id);
        conference.setUserId(selectById.getUserId());
        conference.setRoomId(getRoomByName(bName).getRoomId());
        conference.setConId(id);
        conference.setBName(bName);
        conference.setCDate(cDate);
        conference.setCBeginTime(DateUtils.stringToTime(begin).toDate());
        conference.setCEndTime(DateUtils.stringToTime(end).toDate());
        conference.setComments(comment);
        //邮件发送
        emailUtil.sendEmail(selectById.getUEmail(), "修改预订会议成功", "您好:" + selectById.getUEmail() + ",您修改预订后的会议室是：" + bName + ",会议开始时间为:" + cDate + "-" + begin + ",请您准时参加，祝您工作顺利", mailSender);
        return conference;
    }

    @Override
    public boolean roomIsTrue(String bName) {
        Room room = getRoomByName(bName);
        if (room == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean roomIsTrue(Long roomId) {
        Room room = getRoomById(roomId);
        if (room == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean userIsTrue(String email) {
        return getUserByEmail(email) != null;
    }

    @Override
    public boolean UserIsTrue(Long userId) {
        return getUserById(userId) != null;
    }

    @Override
    public boolean userPhoneAlreadyExist(String phone) {
        return getUserByPhone(phone) != null;
    }


    @Override
    public List<UserConferenceVo> selectByUserId(long userId) {
        //返回对象
        List<UserConferenceVo> voList = new ArrayList<>();
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("c_date");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            BeanUtils.copyProperties(conference, vo);
            voList.add(vo);
        }
        return voList;

    }

    @Override
    public List<UserConferenceVo> selectAll(String date) {
        //返回对象
        List<UserConferenceVo> voList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.ge("c_date", date);
        wrapper.orderByAsc("c_date");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            BeanUtils.copyProperties(conference, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<UserConferenceVo> selectByStatus(int status) {
        //返回对象
        List<UserConferenceVo> voList = new ArrayList<>();
        //创建匹配器
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("status", status);
        wrapper.orderByAsc("c_date");
        List<Conference> records = baseMapper.selectList(wrapper);
        for (Conference conference : records) {
            UserConferenceVo vo = new UserConferenceVo();
            BeanUtils.copyProperties(conference, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<String> selectAttendeesPhonesByConId(Long conId) {
        return attendeesMapper.selectAttendeesPhonesByConId(conId);
    }

    @Override
    public List<ConferenceWithPhone> selectByExample(ConferenceQueryVo conferenceQueryVo) throws ParseException {
        List<ConferenceWithPhone> voList = new ArrayList<>();

        QueryWrapper<Conference> queryWrapper = new QueryWrapper<>();
        ConferenceQueryVo conference;
        conference = conferenceQueryVo == null ? new ConferenceQueryVo() : conferenceQueryVo;
        if (conference.getCStatus() != 0) {
            queryWrapper.eq("status", conference.getCStatus());
        }
        if (conference.getCBeginDate() != null) {
            queryWrapper.ge("c_date", conference.getCBeginDate());
        }
        if (conference.getCEndDate() != null) {
            queryWrapper.le("c_date", conference.getCEndDate());
        }
        if (conference.getRName() != null) {
            queryWrapper.like("b_name", conference.getRName());
        }
        if (conference.getUName() != null && conference.getUName() != "") {
            queryWrapper.like("u_name", conference.getUName());
        }
        if (conference.getCBeginTime() != null) {
            queryWrapper.eq("c_begin_time", conference.getCBeginTime());
        }
        if (conference.getCEndTime() != null) {
            queryWrapper.eq("c_end_time", conference.getCEndTime());
        }

        queryWrapper.orderByAsc("c_date");
        List<Conference> conferences = baseMapper.selectList(queryWrapper);
        if (conferences.size() == 0) {
            return voList;
        }

        for (Conference con : conferences) {
            ConferenceWithPhone vo = new ConferenceWithPhone();

            BeanUtils.copyProperties(con, vo);
            User user = userService.selByIdUser((int) con.getUserId());
            if (user != null) {
                vo.setUPhone(user.getUPhone());
            } else {
                vo.setUPhone("ERR_USER");
            }
            voList.add(vo);
        }

        return voList;
    }

    //根据房间ID查房间
    private Room getRoomById(Long roomId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_id", roomId);
        return roomMapper.selectOne(wrapper);
    }


    //根据房间名字查房间
    private Room getRoomByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("b_name", name);
        return roomMapper.selectOne(wrapper);
    }

    //根据邮箱查用户
    private User getUserByEmail(String email) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("u_mail", email);
        return userMapper.selectOne(wrapper);
    }

    private User getUserById(Long userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        return userMapper.selectOne(wrapper);
    }

    //根据手机号查用户
    private User getUserByPhone(String phoneNumber) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("u_phone", phoneNumber);
        wrapper.eq("u_status", 1);
        return userMapper.selectOne(wrapper);
    }

    //根据房间名查询报修信息
    private RepairMessage getRepairMessageByRName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("b_name", name);
        return repairMessageMapper.selectOne(wrapper);
    }

    private List<ConferenceVo> splitCon(List<NoTimeConferenceVo> noTimeList) {
        //一个ConferenceVo代表一个会议室在列表一条的记录，使用map判断每个房间的ConferenceVo有没有创建
        Map<String, ConferenceVo> map = new HashMap<>();
        for (NoTimeConferenceVo noTime : noTimeList) {
            String bName = noTime.getBName();
            Integer bType = noTime.getBType();
            //房间没有预约直接跳过时间分段
            if (noTime.getConId() == null) {
                ConferenceVo conferenceVo = new ConferenceVo(bName, bType);
                map.put(bName, conferenceVo);
                continue;
            }

            //房间有预约，将时间分段，并将每一段时间是否被预约情况返回前端
            Date cbeginTime = noTime.getCbeginTime();
            Date cendTime = noTime.getCendTime();
            Long conId = noTime.getConId();
            if (map.containsKey(bName)) {
                //时间分段,并存到返回记录
                ConferenceVo conferenceVo = map.get(bName);
                //修改conferenceVo里面的list
                DateUtils.splitTime(conferenceVo.getVoList(), cbeginTime, cendTime, conId);
            } else {
                ConferenceVo conferenceVo = new ConferenceVo(bName, bType);
                DateUtils.splitTime(conferenceVo.getVoList(), cbeginTime, cendTime, conId);
                map.put(bName, conferenceVo);
            }
        }
        Collection<ConferenceVo> values = map.values();
        List<ConferenceVo> conferenceVos = new ArrayList<>(values);
        conferenceVos.sort((o1, o2) -> o1.getBName().compareTo(o2.getBName()));
        return conferenceVos;
    }

    private List<QueConferenceVo> splitTime(List<TimeConferenceVo> TimeList) {
        //一个QueConferenceVo代表一个会议室在列表一条的记录，使用map判断每个房间的QueConferenceVo有没有创建
        //使用redis缓存优化
        Map<Long, QueConferenceVo> map = new HashMap<>();
        for (TimeConferenceVo Time : TimeList) {
            Long roomId = Time.getRoomId();
            String bName = Time.getBName();
            Integer cap = Time.getCapacities();
            String blocation = Time.getBLocation();
            //房间没有预约直接跳过时间分段
            if (Time.getConId() == null) {
                //ConferenceVo conferenceVo = new ConferenceVo(bName, bType);
                QueConferenceVo queConferenceVo = new QueConferenceVo(roomId, bName, cap, blocation);
                map.put(roomId, queConferenceVo);
                continue;
            }

            //房间有预约，将时间分段，并将每一段时间是否被预约情况返回前端
            Date cbeginTime = Time.getCbeginTime();
            Date cendTime = Time.getCendTime();
            Long conId = Time.getConId();
            int status = Time.getStatus();
            if (map.containsKey(roomId)) {
                //时间分段,并存到返回记录
                //ConferenceVo conferenceVo = map.get(bName);
                QueConferenceVo queConferenceVo = map.get(roomId);
                //修改conferenceVo里面的list
                //DateUtils.splitTime(conferenceVo.getVoList(), cbeginTime, cendTime, conId);
                DateUtils.split(queConferenceVo.getVoList(), cbeginTime, cendTime, conId, status);
            } else {
                //ConferenceVo conferenceVo = new ConferenceVo(bName, bType);
                QueConferenceVo queConferenceVo = new QueConferenceVo(roomId, bName, cap, blocation);
                //DateUtils.splitTime(conferenceVo.getVoList(), cbeginTime, cendTime, conId);
                DateUtils.split(queConferenceVo.getVoList(), cbeginTime, cendTime, conId, status);
                map.put(roomId, queConferenceVo);
            }
        }
        Collection<QueConferenceVo> values = map.values();
        List<QueConferenceVo> queConferenceVos = new ArrayList<>(values);
        queConferenceVos.sort(((o1, o2) -> o1.getRoomId().compareTo(o2.getRoomId())));
        return queConferenceVos;
    }


    @Override
    public List<NameNumber> countByRoom(int id) {
        return conferenceMapper.selectNumByRoomID(id);
    }

    @Override
    public List<NameNumber> countByUser(int id) {
        return conferenceMapper.selectNumByUserID(id);
    }

    @Override
    public int rejectConflictCons(Long applyId) throws ParseException {
        Conference con = conferenceMapper.selectById(applyId);
        List<Conference> conflictCons = getConflictCons(
                con.getCDate(),
                con.getRoomId(),
                con.getCBeginTime(),
                con.getCEndTime(),
                1 // 未审批
        );
        if (conflictCons.size() == 0) {
            return 0;
        }
        // 拒绝这些冲突申请 并 发送拒绝短信
        for (Conference c : conflictCons) {
            if (c.getConId() != applyId) {
                log.info("=======拒绝了编号为{}, 申请人为{}的会议=======", c.getConId(), c.getUName());
                Result result = adminReviewController.setConStatus(c.getConId(), 3);


            }
        }
        return conflictCons.size();

    }

    @Override
    public Long updateOldCon() {
        Long ret = conferenceMapper.updateOldCon();
        return ret == null ? 0 : ret;
    }


}
