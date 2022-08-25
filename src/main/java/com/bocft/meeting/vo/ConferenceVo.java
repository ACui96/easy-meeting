package com.bocft.meeting.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
/**
 * 预订页面返回相关
 */
public class ConferenceVo {

    @ApiModelProperty("房间类型")
    private Integer bType;

    @ApiModelProperty("房间名称")
    private String bName;


    @ApiModelProperty("这个房间一天的预约开始结束时间集合")
    private List<TimeIsConVo> voList;

    public ConferenceVo(String bName,Integer bType){
        this.bName=bName;
        this.bType=bType;
        List<TimeIsConVo> voList=new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            TimeIsConVo timeIsConVo = new TimeIsConVo();
            voList.add(timeIsConVo);
        }
        this.voList=voList;
    }

}
