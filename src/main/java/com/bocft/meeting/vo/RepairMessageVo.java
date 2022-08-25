package com.bocft.meeting.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.bocft.meeting.model.RepairMessage;
import com.bocft.meeting.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
public class RepairMessageVo {

    @ExcelProperty(index = 0,value = "房间名字")
    private String name;


    @ExcelProperty(index = 1,value = "报修信息")
    private String repairMessage;


    @ExcelProperty(index = 2,value = "报修人联系方式")
    private String phone;

    @ExcelProperty(index = 3,value = "报修日期")
    private String date;


    @ExcelProperty(index = 4,value = "报修完成日期")
    private String endDate;

    @ExcelProperty(index = 5,value = "维修人员电话")
    private String repairerPhone;
    @ExcelProperty(index = 6,value = "维修人员签字")
    private String repairer;
public RepairMessageVo(RepairMessage message, User user){
    this.name =message.getBName();
    this.repairMessage=message.getRepairMessage();
    this.phone=user.getUPhone();
    this.date=new DateTime(message.getCreateTime()).toString("yyyy-MM-dd");

}

}
