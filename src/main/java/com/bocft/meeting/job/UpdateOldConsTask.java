package com.bocft.meeting.job;

import com.bocft.meeting.service.ConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 定时任务，每半个小时更新一次数据库，将过期未审核的会议申请设置为 3（已拒绝）
 *
 * @author Acui
 * @date 2022/8/12 10:25
 */
@Component
@Slf4j
public class UpdateOldConsTask {

    @Autowired
    private ConferenceService conferenceService;

    @PostConstruct
    @Scheduled(cron = "0 0/30 * * * ?") //    表示每 30 分钟 执行任务
    public void updateOldConStatus() {
        log.info("=============开始更新过期未审核的会议============");
        conferenceService.updateOldCon();
        log.info("=============过期未审核的会议更新完成============");

    }
}
