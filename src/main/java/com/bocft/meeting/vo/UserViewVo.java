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
 * @date 2022/7/15 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewVo {
    @TableField("user_id")
    @ApiModelProperty(value = "用户id")
    @JsonProperty("uId")
    private String userId;

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

    @ApiModelProperty(value="用户部门全称")
    @JsonProperty("uDptAll")
    private String uDeptAll;

    @ApiModelProperty(value = "部门id")
    @JsonProperty("uDpt")
    private int uDeptId;

    @NotBlank(message = "权限")
    @JsonProperty("uVerifyAll")
    @ApiModelProperty(value="权限全称")
    private String uVerifyAll;
}
