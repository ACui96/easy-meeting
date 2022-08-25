package com.bocft.meeting.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
/**
 * 用户查询个人预约信息返回类
 */
@AllArgsConstructor
@NoArgsConstructor
public class UserConferenceVo  {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("会议记录id")
    @JsonProperty("cId")
    private long conId;

    @JsonProperty("bName")
    @ApiModelProperty(value = "会议房间名")
    private String bName;

    @JsonProperty("comments")
    @ApiModelProperty(value = "会议主题")
    private String comments;

    @JsonProperty("cDate")
    @ApiModelProperty(value = "会议日期")
    private String cDate;

    @JsonProperty("cBeginTime")
    @ApiModelProperty(value = "会议开始时间")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date cBeginTime;

    @JsonProperty("cEndTime")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(value = "会议结束时间")
    private Date cEndTime;

    @JsonProperty("status")
    @ApiModelProperty("会议审批状态 0-未审批 1-通过审批 2-拒绝")
    private String status;

    @JsonProperty("capacities")
    @ApiModelProperty("可容纳人数")
    private int capacities;

    @JsonProperty("bLocation")
    @ApiModelProperty(value = "地点")
    private String bLocation;
}
