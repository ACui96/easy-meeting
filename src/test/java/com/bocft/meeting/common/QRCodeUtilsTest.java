package com.bocft.meeting.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;

/**
 * @author Acui
 * @date 2022年07月19日 16:12
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class QRCodeUtilsTest {

    @Test
    public void testQRCodeGenerate() throws Exception {
        String content = "I Love U";
        QRCodeUtils.saveQRCodeToLocal("PNG", "QRCode", "", 0.0, "D:/", content, 512);

    }

}
