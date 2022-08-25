package com.bocft.meeting.common;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {
    //异常相关
    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    PARAM_ERROR( 202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),

    //登录相关
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    CAPTCHA_FALSE(210, "验证码不正确"),
    PASSWORD_ERROR(211, "密码不正确"),

    //预约相关
    CON_FAIl(403, "这些日期冲突"),
    DATE_FaIL(403,"预订日期超过当前日期30天"),
    END_DATE_FAIL(403,"预约结束时间必须大于当前时间"),
    TIME_DATE_FAIL(403,"预约时间不合理"),
    ROOM_FAIL(403,"房间不存在"),
    EMAIL_FAIL(403,"邮箱必须为登录邮箱"),
    PHONE_FAIL(403, "手机号必须为登录手机号"),
    //维修相关
    ADD_REPAIR_FAIL(403,"这个房间正在维修")
    ;
    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
