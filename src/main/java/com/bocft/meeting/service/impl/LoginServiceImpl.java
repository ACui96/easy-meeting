package com.bocft.meeting.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.mapper.LoginMapper;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, User> implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User selectUserByEmail(String uEmail) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("u_mail", uEmail);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public User selectUserByPhone(String uPhone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("u_phone", uPhone);
        wrapper.eq("u_status", 1);
        return baseMapper.selectOne(wrapper);
    }
}
