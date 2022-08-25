package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.RepairMessage;
import com.bocft.meeting.vo.RepairMessageVo;

import java.util.List;

public interface RepairMessageService extends IService<RepairMessage> {
    boolean addRepair(String bName, String uEmail, String repair);

    List<RepairMessageVo> getRepair(int repairIsNo);

    boolean setRepair(String bName);

}
