package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.*;
import com.bocft.meeting.mapper.RepairMessageMapper;
import com.bocft.meeting.mapper.RoomMapper;
import com.bocft.meeting.mapper.UserMapper;
import com.bocft.meeting.model.RepairMessage;
import com.bocft.meeting.model.Room;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.RepairMessageService;
import com.bocft.meeting.vo.RepairMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RepairMessageServiceImpl extends ServiceImpl<RepairMessageMapper, RepairMessage> implements RepairMessageService {

    @Autowired
    private RepairMessageMapper repairMessageMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailUtil emailUtil;
    @Override
    public boolean addRepair(String bName, String uEmail, String repair) {
        Room roomByName = getRoomByName(bName);
        if (roomByName==null){
            throw new ConferenceException(ResultCodeEnum.ROOM_FAIL);
        }
        User userByEmail = getUserByEmail(uEmail);
        if (userByEmail==null){
            throw new ConferenceException(ResultCodeEnum.EMAIL_FAIL);
        }
        //查询这个房间是否被报修
        QueryWrapper<RepairMessage> wrapper=new QueryWrapper();
        wrapper.eq("b_name",bName);
        wrapper.eq("repair_state", Constant.REPAIR_NO);
        Integer count = repairMessageMapper.selectCount(wrapper);
        //房间还有未完成的维修，无法添加新的维修
        if (count>0){
            throw new ConferenceException(ResultCodeEnum.ADD_REPAIR_FAIL);
        }
        RepairMessage repairMessage=new RepairMessage();
        repairMessage.setRepairMessage(repair);
        repairMessage.setRepairState(Constant.REPAIR_NO);
        repairMessage.setBName(bName);
        repairMessage.setRoomId(roomByName.getRoomId());
        repairMessage.setUEmail(uEmail);
        repairMessage.setUserId(userByEmail.getUserId());
        int insert = repairMessageMapper.insert(repairMessage);
        if (insert>0){
            //新增维修成功，提醒管理员处理
            emailUtil.sendEmail(Constant.ADMIN_EMAIL,"房间维修提醒","房间号是"+bName+"的房间出现问题，请登录查看详细信息",mailSender);
            return true;
        }
        return false;

    }

    @Override
    public List<RepairMessageVo> getRepair(int repairIsNo) {
        List<RepairMessageVo> voList=new ArrayList<>();
        QueryWrapper<RepairMessage> wrapper=new QueryWrapper();
        wrapper.eq("repair_state", Constant.REPAIR_NO);
        List<RepairMessage> list = repairMessageMapper.selectList(wrapper);
        for (RepairMessage repair:list) {
            User userByEmail = getUserByEmail(repair.getUEmail());
            RepairMessageVo vo=new RepairMessageVo(repair,userByEmail);
            voList.add(vo);
        }
        return voList;
    }


    @Override
    public boolean setRepair(String bName) {
        QueryWrapper<RepairMessage> wrapper = new QueryWrapper();
        wrapper.eq("b_name", bName);
        wrapper.eq("repair_state", Constant.REPAIR_NO);
        RepairMessage repairMessage = repairMessageMapper.selectOne(wrapper);
        repairMessage.setRepairState(Constant.REPAIR_YES);
        repairMessage.setRepairMessage("一切正常，欢迎您来预约");
        repairMessage.setBName(bName+"：维修完成日期："+new Date());
        int i = repairMessageMapper.updateById(repairMessage);
        return i>0;
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
}
