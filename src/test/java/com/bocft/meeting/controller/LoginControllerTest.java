package com.bocft.meeting.controller;

import com.bocft.meeting.common.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Acui
 * @date 2022年07月12日 10:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

    @Autowired
    LoginController loginController;

    @Test
    public void loginTest() {
        Result result = loginController.login("17603209617", "123456");
        System.out.println(result.toString());
    }
}
