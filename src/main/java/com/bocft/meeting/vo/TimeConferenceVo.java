package com.bocft.meeting.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
//时间没分段的返回类
public class TimeConferenceVo {
    @ApiModelProperty("会议室记录id")
    private Long ConId;

    @ApiModelProperty("房间id")
    private Long RoomId;

    @ApiModelProperty("房间名称")
    private String bName;

    @ApiModelProperty("容纳人数")
    private Integer capacities;

    @ApiModelProperty("地点")
    private String bLocation;

    @ApiModelProperty(value = "会议开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date cbeginTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(value = "会议结束时间")
    private Date cendTime;

    @ApiModelProperty(value = "会议审批状态 0：未审批 1：通过审批")
    private int status;





}
