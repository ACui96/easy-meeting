package com.bocft.meeting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Acui
 * @date 2022年07月13日 8:54
 * 部门类
 */
@TableName("t_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    /**
     * 部门编号
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="deptId 部门编号")
    private long deptId;

    /**
     * 名字
     */
    @ApiModelProperty(value="deptName 部门名称")
    private String dName;

    /**
     * 部门主管编号
     */
    private Integer dManagerId;

    /**
     * 上级部门编号
     */
    private Integer dParentId;


    /**
     * 部门层级
     */
    private Integer dLevel;

}
