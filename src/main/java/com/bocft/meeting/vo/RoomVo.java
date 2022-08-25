package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yang
 * @date 2022/4/15 17:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVo  {




    /**
     * 会议室ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="roomId会议室ID")
    @JsonProperty(value = "roomId")
    private long roomId;



    @TableField("b_type")
    @JsonProperty(value = "btype")
    @ApiModelProperty(value="bType会议室类型0，1，2")
    private Integer bType;

    @NotBlank(message = "输入会议室名称")
    @JsonProperty(value = "bName")
    @ApiModelProperty(value="bName会议室名称")
    private String bName;


    @NotBlank
    @TableField("b_remark")
    @JsonProperty(value = "bremark")
    @ApiModelProperty(value="bRemark房间信息")
    private String bRemark;

    @NotNull
    @TableField("capacities")
    @ApiModelProperty(value="capacities会议室容纳人数")
    private Integer capacities;

    @NotBlank
    @TableField("b_location")
    @JsonProperty(value = "blocation")
    @ApiModelProperty(value="bRemark房间信息")
    private String bLocation;

}
