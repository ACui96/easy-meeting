package com.bocft.meeting.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/**
 * 查看某一个房间在某一个时间段的信息返回类
 */
public class GivenConferenceVo {



    @ApiModelProperty(value="会议室容纳人数")
    private Integer capacities;

    @ApiModelProperty(value="房间基本信息")
    private String bRemark;


}
