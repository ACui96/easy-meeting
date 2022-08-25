package com.bocft.meeting.common;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Acui
 * @date 2022年07月23日 11:08
 */
public class TimeAddTest {
    public static void main(String[] args) {
//        String date = "2020-11-03 9:00:00";
//        DateTime dateTime = DateUtils.stringToDate(date);
        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");

        System.out.println("现在："+df.format(date));
        System.out.println("五分钟后的：" + df.format(new Date(date.getTime() + 5 * 60 * 1000)));
//        System.out.println("三天后的日期：" + df.format(new Date(date.getTime() + 3 * 24 * 60 * 60 * 1000)));
    }
}
