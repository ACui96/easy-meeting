package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.User;


public interface LoginService extends IService<User> {


    User selectUserByEmail(String uPwd);

    User selectUserByPhone(String uPhone);
}
