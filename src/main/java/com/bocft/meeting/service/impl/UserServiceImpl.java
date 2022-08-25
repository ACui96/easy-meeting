package com.bocft.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.BeanUtils;
import com.bocft.meeting.common.MD5;
import com.bocft.meeting.mapper.UserMapper;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.UserInforVo;
import com.bocft.meeting.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean addUser(UserVo userVo,String code) {

        //通过MD5加密
        userVo.setUPwd(MD5.encrypt(userVo.getUPwd()));
        User user = new User();
        user.setUStatus(1);
        BeanUtils.copyProperties(userVo, user);
        int a = userMapper.insert(user);
        if (a == 1) {
            return true;
        }
        return false;
    }


    @Override
    public User selByIdUser(int id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", id);
        User user = userMapper.selectOne(wrapper);
        //System.out.println(user);
        return user;
    }


    @Override
    public boolean updateUser(User user) {
        String encrypt = MD5.encrypt(user.getUPwd());
        user.setUPwd(encrypt);
        boolean bool = userMapper.updateUser(user);
        return bool;
    }


    @Override
    public int delUser(Integer user_id) {
        return userMapper.delUser(user_id);

    }

    @Override
    public User getByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("u_phone", phone);
        wrapper.eq("u_status",1);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public List<UserInforVo> selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    public int countByDeptId(long deptId) {
        return userMapper.countByDeptId(deptId);
    }


}
