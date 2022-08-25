package com.bocft.meeting.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/25 16:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptIdAndName {
    private long deptId;
    private String dName;
}
