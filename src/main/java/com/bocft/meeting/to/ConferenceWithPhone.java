package com.bocft.meeting.to;

import com.bocft.meeting.model.Conference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author Acui
 * @date 2022年08月05日 14:47
 */
@Data
public class ConferenceWithPhone extends Conference {
    @ApiModelProperty(value = "用户手机号")
    @JsonProperty("uPhone")
    private String uPhone;
}
