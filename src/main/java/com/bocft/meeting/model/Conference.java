package com.bocft.meeting.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_conference_rel")
public class Conference extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private long conId;

    @TableField("user_id")
    private long userId;

    @TableField("u_email")
    private String uEmail;

    @TableField("room_id")
    private long roomId;

    @TableField("b_name")
    @ApiModelProperty(value = "会议房间号")
    private String bName;

    @TableField("comments")
    @ApiModelProperty(value = "会议主题")
    private String comments;

    @TableField("c_date")
    @ApiModelProperty(value = "会议日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String cDate;

    @TableField("c_begin_time")
    @ApiModelProperty(value = "会议开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date cBeginTime;

    @TableField("c_end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(value = "会议结束时间")
    private Date cEndTime;

    @TableField("status")
    @ApiModelProperty(value = "会议审批状态： 0-未审批 1-通过审批 2-拒绝")
    private int status;

    @TableField("u_name")
    @ApiModelProperty(value = "用户姓名")
    private String uName;
}
