package com.bocft.meeting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@TableName( "t_user")
@Data
public class User extends BaseEntity{
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="userId用户ID")
    private long userId;

    /**
     * 邮箱
     */
    @TableField("u_mail")
    @ApiModelProperty(value="uMail邮箱")
    private String uMail;

    /**
     * 密码
     */
    @TableField("u_pwd")
    @ApiModelProperty(value="uPwd密码")
    private String uPwd;

    /**
     * 电话
     */
    @TableField("u_phone")
    @ApiModelProperty(value="uPhone电话")
    private String uPhone;

    /**
     * 0普通用户，1管理员
     */
    @TableField("u_verify")
    @ApiModelProperty(value="uVerify0普通用户，1管理员")
    private int uVerify;

    /**
     * 用户部门编号
     */
    @TableField("u_dept_id")
    @ApiModelProperty(value="uDeptId用户部门编号")
    private int uDeptId;

    /**
     * 用户姓名
     */
    @TableField("u_name")
    @ApiModelProperty(value="uName 用户姓名")
    private String uName;

    /**
     * 用户状态
     */
    @TableField("u_status")
    @ApiModelProperty(value="0表示禁用 1表示正在使用")
    private int uStatus;



}
