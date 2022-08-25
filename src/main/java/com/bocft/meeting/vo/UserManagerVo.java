package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author xz
 * @date 2022/7/14 22:10
 */
//传过来的参数
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagerVo {

    @TableField("u_id")
    @ApiModelProperty(value = "用户id")
    @JsonProperty("uId")
    private String uId;

    @TableField("u_name")
    @ApiModelProperty(value = "姓名")
    @JsonProperty("uName")
    private String uName;

    @TableField("u_mail")
    @ApiModelProperty(value = "uMail邮箱")
    @JsonProperty("uMail")
    private String uMail;

    @TableField("u_phone")
    @ApiModelProperty(value = "uPhone电话")
    @JsonProperty("uPhone")
    private String uPhone;

    @TableField("u_dept_id")
    @ApiModelProperty(value="用户部门")
    @JsonProperty("uDpt")
    private String uDept;

    @TableField("u_pwd")
    @ApiModelProperty(value="密码")
    @JsonProperty("uPwd")
    private String uPwd;

    @NotBlank(message = "权限")
    @JsonProperty("uVerifyAll")
    @ApiModelProperty(value="权限全称")
    private String uVerifyAll;
}
