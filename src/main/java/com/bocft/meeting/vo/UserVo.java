package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {


    @Email
    @TableField("u_mail")
    @ApiModelProperty(value = "uMail邮箱")
    private String uMail;


    @TableField("u_pwd")
    @ApiModelProperty(value = "uPwd密码")
    private String uPwd;


    @TableField("u_phone")
    @ApiModelProperty(value = "uPhone电话")
    private String uPhone;


    @TableField("u_verify")
    @ApiModelProperty(value = "uVerify0普通用户，1管理员")
    private Integer uVerify;

    @TableField("u_dept_id")
    @ApiModelProperty(value = "部门编号")
    private Long uDeptId;

    @TableField("u_name")
    @ApiModelProperty(value = "用户姓名")
    private String uName;



    @TableField("u_status")
    @ApiModelProperty(value = "用户状态")
    private int uStatus;

}
