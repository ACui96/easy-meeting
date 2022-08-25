package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xz
 * @date 2022/7/26 14:02
 */
@Data
public class AttendeesVo {
    @TableField("a_id")
    @ApiModelProperty(value = "与会人id")
    @JsonProperty("aId")
    private String aId;

    @TableField("u_name")
    @ApiModelProperty(value = "用户姓名")
    @JsonProperty("uName")
    private String uName;

    @TableField("u_phone")
    @ApiModelProperty(value = "用户电话")
    @JsonProperty("uPhone")
    private String uPhone;

    @TableField("d_name")
    @ApiModelProperty(value = "部门名称")
    @JsonProperty("dName")
    private String dName;

    @TableField("msg")
    @ApiModelProperty(value = "是否已通知")
    @JsonProperty("msg")
    private String msg;


}
