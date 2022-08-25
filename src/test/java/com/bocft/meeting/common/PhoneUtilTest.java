package com.bocft.meeting.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Acui
 * @date 2022年07月12日 9:12
 */
@Slf4j
@SpringBootTest
public class PhoneUtilTest {

    PhoneUtil phoneUtil = new PhoneUtil();

    @Test
    public void testPhoneUtils() {
        boolean b = phoneUtil.phoneNumberIsValid("17603209617");
        if (b) {
            log.info("手机号符合规则");
        } else {
            log.info("手机号格式不对");
        }
    }

}
