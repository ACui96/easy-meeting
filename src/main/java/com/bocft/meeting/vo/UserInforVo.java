package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInforVo {
    @TableField("user_id")
    @ApiModelProperty(value = "参会员工ID")
    @JsonProperty("userId")
    private Long userId;

    @TableField("u_name")
    @ApiModelProperty(value = "参会员工姓名")
    @JsonProperty("uName")
    private String uName;

    @TableField("u_mail")
    @ApiModelProperty(value = "uMail电话")
    @JsonProperty("uMail")
    private String uMail;

    @TableField("d_name")
    @ApiModelProperty(value = "参会人员部门")
    @JsonProperty("dName")
    private String dName;
}
