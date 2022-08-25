package com.bocft.meeting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.controller.ChartsController;
import com.bocft.meeting.controller.ConferenceApiController;
import com.bocft.meeting.mapper.ConferenceMapper;
import com.bocft.meeting.mapper.UserMapper;
import com.bocft.meeting.model.User;
import com.bocft.meeting.service.ConferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MeetingApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ConferenceMapper conferenceMapper;

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    ConferenceApiController conferenceApiController;

    @Autowired
    ChartsController chartsController;



    @Test
    void contextLoads() {
    }

    @Test
    void testGetById(){
        System.out.println(userMapper.selectList(null));
    }

    @Test
    void testGetPage(){
        IPage page = new Page(2,4);
        userMapper.selectPage(page,null);
    }

    @Test
    void testGetBy(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("u_phone","176");
        userMapper.selectList(qw);
    }

    //语法级检查
    @Test
    void testGetBy2(){
        String name = null;
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper();
        lqw.like(name!=null,User::getUName,name);
        userMapper.selectList(lqw);
    }

    @Test
    void testChart(){
        //conferenceMapper.selectNumByRoomID();
        //conferenceService.countByRoom();
        //chartsController.selectBar();
        //conferenceMapper.selectNumByUserID();
        //chartsController.selectBar();
        System.out.println(conferenceService.countByRoom(30));

    }

    @Test
    void testTime(){

        //System.out.println(conferenceMapper.selectByTime("2021-01-01","1:30:3","2:30:0"));
        //conferenceService.selectByTime("2021-01-01","1:30:3","2:30:0");
        //conferenceApiController.selectConByTime("2021-01-01","1:30:3","2:30:0");

    }

    @Test
    void testInsert(){
        conferenceService.insertCon("2022-8-19",(long)1,"6:30:00","10:30:00","1473444277@qq.com","早八开会");
    }

    @Test
    void testSQL(){
        //conferenceMapper.selectByTime("2022-08-19","6:30:00","10:30:00");
        conferenceMapper.selectRoomByDate("2022-12-12");
    }






}
