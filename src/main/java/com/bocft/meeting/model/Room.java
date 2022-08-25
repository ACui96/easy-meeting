package com.bocft.meeting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("t_board_room")
public class Room extends BaseEntity{
    /**
     * 会议室ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="roomId会议室ID")
    private long roomId;

    /**
     * 会议室类型0，1，2
     */
    @TableField("b_type")
    @ApiModelProperty(value="012")
    private Integer bType;

    /**
     * 会议室名称
     */
    @JsonProperty("bName")
    @TableField("b_name")
    @ApiModelProperty(value="bName会议室名称")
    private String bName;


    /**
     * 房间信息
     */
    @TableField("b_remark")
    @ApiModelProperty(value="bRemark房间信息")
    private String bRemark;

    /**
     * 会议室容纳人数
     */
    @TableField("capacities")
    @ApiModelProperty(value="capacities会议室容纳人数")
    private Integer capacities;

    @TableField("b_state")
    @ApiModelProperty(value="会议室状态")
    private Integer bState;

    @TableField("b_location")
    @ApiModelProperty(value="会议室地点")
    private String bLocation;


   }