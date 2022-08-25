package com.bocft.meeting.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "自定义全局异常类")
public class ConferenceException extends RuntimeException{

    //异常状态码
    private Integer code;
    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public ConferenceException(String message, Integer code) {
        this.message=message;
        this.code = code;
    }
    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public ConferenceException(ResultCodeEnum resultCodeEnum) {
        this.message=resultCodeEnum.getMessage();
        this.code = resultCodeEnum.getCode();
    }

    public ConferenceException(Integer code, String[] params) {

    }
}
