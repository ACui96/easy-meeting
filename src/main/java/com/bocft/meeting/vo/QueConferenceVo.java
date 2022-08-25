package com.bocft.meeting.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
/**
 * 预订页面返回相关 2.0
 */
public class QueConferenceVo  {

    @ApiModelProperty("房间ID")
    @JsonProperty(value = "roomId")
    private Long roomId;

    @ApiModelProperty("房间名称/地点")
    @JsonProperty(value = "bName")
    private String bName;

    @ApiModelProperty("容纳人数")
    @JsonProperty(value = "capacities")
    private Integer capacities;

    @ApiModelProperty("地点")
    @JsonProperty(value = "bLocation")
    private String bLocation;

    @ApiModelProperty("这个房间一天的预约开始结束时间集合")
    @JsonProperty(value = "voList")
    private List<TimeIsConVo> voList;

    public QueConferenceVo(){}

    public QueConferenceVo(Long roomId,String bName, Integer capacities,String bLocation){
        this.bName=bName;
        this.roomId=roomId;
        this.capacities=capacities;
        this.bLocation=bLocation;
        List<TimeIsConVo> voList=new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            TimeIsConVo timeIsConVo = new TimeIsConVo();
            voList.add(timeIsConVo);
        }
        this.voList=voList;
    }


}
