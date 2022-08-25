package com.bocft.meeting.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
//时间没分段的返回类
public class NoTimeConferenceVo {
    @ApiModelProperty("会议记录id")
    private Long conId;

    @ApiModelProperty("房间类型")
    private Integer bType;

    @ApiModelProperty("房间名称")
    private String bName;

    @ApiModelProperty(value = "会议开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date cbeginTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(value = "会议结束时间")
    private Date cendTime;



}
