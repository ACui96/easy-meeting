package com.bocft.meeting.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/**
 * 查看某一个房间在某一个时间段的信息返回类
 */
public class OneConferenceVo  {


    @ApiModelProperty(value="bType会议室类型0，1，2")
    private Integer bType;

    @ApiModelProperty(value="repairMessage报修信息")
    private String repairMessage;

    @ApiModelProperty(value="会议室容纳人数")
    private Integer capacities;

    @ApiModelProperty(value="房间基本信息")
    private String bRemark;

    @ApiModelProperty(value="预约用户邮箱及时间")
    private String uEmail;

}
