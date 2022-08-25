package com.bocft.meeting.common;


import com.bocft.meeting.vo.TimeIsConVo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 主要是一些日期处理函数
 */
public class DateUtils {

    /**
     *
     * @author Acui
     * @date 2022/7/15 15:37
     * @param s 2022-07-03
     * @return java.lang.String 7月3日
     */
    public static String getShorterDate(String s) {
        String ret = s.substring(5);
        return ret.substring(0, 2) + "月" + ret.substring(3) + "日";
    }



    //字符串转日期天
    public static DateTime stringToDate(String s) {
        DateTime parse = null;
        try {
            parse = DateTime.parse(s, DateTimeFormat.forPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new ConferenceException("日期格式错误", 403);
        }

        return parse;

    }

    //日期转字符串
    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    //字符串转时间
    public static DateTime stringToTime(String s) {

        DateTime parse = null;
        try {
            parse = DateTime.parse(s, DateTimeFormat.forPattern("HH:mm:ss"));
        } catch (Exception e) {
            throw new ConferenceException("日期格式错误", 403);
        }
        /*String[] split = s.split(":");
        int h = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        int ss = Integer.parseInt(split[2]);
        if (h < 9 || h > 21 || (m != 0 && m != 30) || ss != 0) {
            throw new ConferenceException("日期格式必须为整数00或者30", 403);
        }*/
        return parse;

    }

    //时间转字符串
    public static String timeToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static String timeToShorterString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }


    public static void main(String[] args) {
        System.out.println(timeToString(new Date()));
    }


    /**
     * 取出每一个预约开始时间和结束时间
     *
     * @param date      第一次预订日期
     * @param olderType 预约类型（0：预约一天，1：每天预约，7：每周预约）
     * @param num       （预约次数）
     * @return
     */
    public static List<String> getDate(String date, int olderType, int num) {

        DateTime time = DateTime.parse(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        List<String> list = new ArrayList<>();
        DateTime date1 = stringToDate(date);
        for (int i = 0; i < num; i++) {
            list.add(dateToString(date1.toDate()));
            date1 = date1.plusDays(olderType);

        }
        DateTime dateTime = stringToDate(list.get(list.size() - 1));
        if (dateTime.isAfter(time.plusDays(30))) {
            throw new ConferenceException(ResultCodeEnum.DATE_FaIL);
        }
        return list;
    }

    public static boolean timeIsTrue(String date, String begin, String end) {
        //开始时间必须小于结束时间
        if (stringToTime(begin).isAfter(stringToTime(end)) || begin.equals(end)) {
            return false;
        }
        //判断用户预约结束时间是否大于当前时间
        DateTime time = DateTime.parse(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime time1 = addDateTime(date, end);
        if (time1.isBefore(time)) {
            return false;
        }
        return true;
    }


    public static void splitTime(List<TimeIsConVo> vo, Date cbeginTime, Date cendTime, long conId) {
        int i = timeToInt(cbeginTime);
        int j = timeToInt(cendTime);
        while (i < j) {
            TimeIsConVo timeIsConVo = vo.get(i++);
            timeIsConVo.setConId(conId);
            timeIsConVo.setIsCon(1);
        }

    }

    //splitTime2.0
    public static void split(List<TimeIsConVo> vo, Date cbeginTime, Date cendTime, long conId, int status) {
        int i = timeToInt(cbeginTime);
        int j = timeToInt(cendTime);
        while (i < j) {
            TimeIsConVo timeIsConVo = vo.get(i++);
            timeIsConVo.setConId(conId);
            if(status==1){
                timeIsConVo.setIsCon(1);
            }else if(status==2){
                timeIsConVo.setIsCon(2);
            }
        }

    }

    public static int timeToInt(Date time) {
        DateTime dateTime = new DateTime(time);
        //将时间的小时减九 九点开始预约
        DateTime dateTime1 = dateTime.minusHours(9);
        //然后转成字符串可以得到这个时间在返回list的位置
        String s = timeToString(dateTime1.toDate());
        String[] split = s.split(":");
        Integer hour = Integer.valueOf(split[0]);
        Integer minute = Integer.valueOf(split[1]);
        return (hour * 60 + minute) / 30;
    }

    public static DateTime addDateTime(String date, String time) {
        StringBuilder builder = new StringBuilder();
        String s = builder.append(date).append(" ").append(time).toString();
        return DateTime.parse(s, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
