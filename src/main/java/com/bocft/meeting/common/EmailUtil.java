package com.bocft.meeting.common;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

//发送邮箱工具类
@Component
public class EmailUtil {


    private static final String from = "meeting_t@163.com";


    /**
     * 发送 邮箱方法
     *
     * @param email 接收的邮箱
     * @param title 邮箱标题
     * @param mes   邮箱内容
     * @return true 发送成功
     */
    @Async//异步发送
    public void sendEmail(String email, String title, String mes, JavaMailSender mailSender) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(email);
        //邮件标题
        message.setSubject(title);
        //邮件内容
        message.setText(mes);
        try {
            mailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    public static boolean emailIsTrue(String email){
        return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
    }


}
