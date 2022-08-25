package com.bocft.meeting.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author xz
 * @date 2022/7/15 9:02
 */
public class TimeComparation {
    //会议状态 0-未开始 1-正在进行中 2-已结束
    public static int comparation(String cDate,Date cBeginTime,Date cEndTime)  {
        int state=0;
        //比较年月日
        try {
        SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat f  =new SimpleDateFormat("HH:mm:ss");
        Date beginTime= sdf.parse(cDate+" "+f.format(cBeginTime));
        Date endTime= sdf.parse(cDate+" "+f.format(cEndTime));
        Date today=new Date();
        //现在比结束时间晚：已结束
        if(today.getTime()>endTime.getTime()) state=2;
        //正在进行中
        if(today.getTime()>=beginTime.getTime()&&today.getTime()<=endTime.getTime()) state=1;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return state;
    }
}
