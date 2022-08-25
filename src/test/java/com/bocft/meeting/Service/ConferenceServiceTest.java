package com.bocft.meeting.Service;

import com.bocft.meeting.service.ConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Acui
 * @date 2022年08月12日 9:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ConferenceServiceTest {

    @Autowired
    private ConferenceService conferenceService;

    @Test
    public void testUpdateOldCons() {
        System.out.println(conferenceService.updateOldCon());
    }
}
