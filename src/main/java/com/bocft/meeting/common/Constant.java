package com.bocft.meeting.common;

/**
 * 系统常量类
 */
public class Constant {

    //房间状态
    //房间正常
    public static final int ROOM_STATE_YES = 0;
    //房间不正常
    public static final int ROOM_STATE_NO = 1;

    //预订类型
    public static final int CON_TYPE_WEEK = 7;
    public static final int CON_TYPE_DAY = 1;
    public static final int CON_TYPE_NO = 0;

    //房间类型
    public static final int ROOM_TYPE_COMMON = 0;//普通
    public static final int ROOM_TYPE_PHONE = 1;//电话
    public static final int ROOM_TYPE_TV = 2;//电视

    //维修相关
    public static final int REPAIR_YES = 1;
    public static final int REPAIR_NO = 0;
    //分页相关
    public static final long PAGE = 1;
    public static final long LIMIT = 5;
    public static final int SIZE = 5;
    public static final long SIZE_ROOM = 10;

    //管理员邮箱
    public static final String ADMIN_EMAIL="xinfeng.cui@foxmail.com";


    // 短信类型相关
    public static final int MSG_VALIDATION_CODE = 1;
    public static final int MSG_AGREE = 2;
    public static final int MSG_REJECT = 3;
    // 腾讯云短信模板编号 https://console.cloud.tencent.com/smsv2/csms-template
    public static final String TEMPLATE_ID_CODE = "1471039"; //验证码
    public static final String TEMPLATE_ID_AGREE = "1470256";//同意
    public static final String TEMPLATE_ID_REJECT = "1471528";// 拒绝
    public static final String TEMPLATE_ID_NOTIFY = "1471530";// 群发通知



}
