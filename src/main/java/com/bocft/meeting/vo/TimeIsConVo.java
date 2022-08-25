package com.bocft.meeting.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * 此类代表一条已经分段记录的开始与结束时间以及id
 * 例如9：00-9：30 会议id是3 isCon是0 这样
 */
public class TimeIsConVo {



    @ApiModelProperty("0:代表时间端没预约 1：代表时间段被占用 2:代表时间段被预约但未被审批 ")
    private Integer isCon;

    @ApiModelProperty("会议记录id")
    private Long conId;
    public TimeIsConVo(){
        this.conId=null;
        this.isCon=0;
    }

}
