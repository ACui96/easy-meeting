package com.bocft.meeting;

import com.bocft.meeting.model.User;
import com.bocft.meeting.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testGetByID(){
        System.out.println(userService.getById(1));
    }
}
