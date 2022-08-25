package com.bocft.meeting.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Acui
 * @Date 2022年07月12日 14:17
 * 多条件会议查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConferenceQueryVo {
    /**
     * 会议状态 1 未审批 2 通过 3 拒绝
     */
    @ApiModelProperty(value = "会议状态",example = "1")
    private int cStatus;


    /**
     * 起始日期
     */
    @ApiModelProperty(value = "起始日期  ",example = "2022-7-13")
    private String cBeginDate;

    /**
     * 截止日期
     */
    @ApiModelProperty(value = "截止日期  ",example = "2022-7-13")
    private String cEndDate;

    /**
     * 会议室名称
     */
    @ApiModelProperty(value = "会议室名称",example = "101")
    private String rName;

    /**
     * 申请人的名字
     */
    @ApiModelProperty(value = "申请人的名字",example = "崔欣锋")
    private String uName;
    /**
     * 会议开始时间
     */
    @ApiModelProperty(value = "会议开始时间",example = "9:00:00")
    private String cBeginTime;

    /**
     * 会议结束时间
     */
    @ApiModelProperty(value = "会议结束时间",example = "10:00:00")
    private String cEndTime;

}
