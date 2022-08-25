package com.bocft.meeting.controller;

import com.bocft.meeting.common.Result;
import com.bocft.meeting.common.ResultCodeEnum;
import com.bocft.meeting.model.User;
import com.bocft.meeting.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Acui
 * @date 2022年07月12日 8:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserController userController;

    @Test
    public void testSendPhoneCode() {
        Result result = userController.sendSms("17603209617");
        System.out.println(result.toString());
    }


    @Test
    public void testRegisterByPhone() {
        User user = new User();
        user.setUPhone("17603209617");
        user.setUPwd("123456");
        user.setUMail("17603209617@163.com");
        Result result = userController.addUserByPhone(user, "922070");
        System.out.println(result.toString());
    }
}
