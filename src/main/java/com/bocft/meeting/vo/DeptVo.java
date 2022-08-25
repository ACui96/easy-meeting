package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/14 11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptVo implements Serializable {


    /**
     * 部门ID
     */
    @TableId(type = IdType.AUTO)
    @JsonProperty(value = "DeptId")
    @ApiModelProperty(value="部门ID")
    private long deptId;

    @JsonProperty(value = "DeptName")
    @ApiModelProperty(value = "部门名称")
    private String dName;

    @JsonProperty(value = "DeptManagerId")
    @ApiModelProperty(value = "部门主管编号")
    private Integer dManagerId;

    @JsonProperty(value = "DeptParentId")
    @ApiModelProperty(value = "上级部门编号")
    private Integer dParentId;

    @JsonProperty(value = "DeptLevel")
    @ApiModelProperty(value = "部门层级 0 为最高部门")
    private Integer dLevel;
}
