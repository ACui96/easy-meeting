package com.bocft.meeting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;




@Data
@TableName("t_repair")
public class RepairMessage extends BaseEntity{
    /**
     * 维修id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="repairId维修id")
    private long repairId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @ApiModelProperty(value="userId用户ID")
    private long userId;

    @TableField("u_email")
    private String uEmail;
    /**
     * 会议室ID
     */
    @TableField("room_id")
    @ApiModelProperty(value="roomId会议室ID")
    private Long roomId;

    @TableField("b_name")
    @JsonProperty("bName")
    private String bName;
    /**
     * 报修信息
     */
    @TableField( "repair_message")
    @ApiModelProperty(value="repairMessage报修信息")
    private String repairMessage;


    @TableField("repair_state")
    @ApiModelProperty(value="repairState0：完成   1：未完成")
    private Integer repairState;

    }