package com.bocft.meeting.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NameNumber {
    @TableField("number")
    @JsonProperty("value")
    private int number;

    @TableField("name")
    @JsonProperty("name") //echarts 要求返回的名称，要不封装不上数据
    private String name;


}
