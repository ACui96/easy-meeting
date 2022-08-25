package com.bocft.meeting.controller;

import com.bocft.meeting.common.Result;
import com.bocft.meeting.vo.ConferenceQueryVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

/**
 * @author Acui
 * @date 2022年07月08日 15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConferenceControllerTest {

    @Autowired
    ConferenceApiController conferenceApiController;



/*    @Test
    public void selectConByMulConditionsTest() {
        ConferenceQueryVo c = new ConferenceQueryVo();
        c.setCStatus(0);
        c.setCBeginDate("2022-7-3");
        c.setCEndDate("2022-7-6");
//        c.setR_name("101");
        c.setUName("崔崔1");


        Result result = conferenceApiController.selectByMulCondition(1L, c);
        System.out.println(result.toString());
    }*/


}
