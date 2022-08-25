package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.Conference;
import com.bocft.meeting.vo.UserConferenceVo;

import java.util.List;
import java.util.Map;

/**
 * @author xz
 * @date 2022/7/15 17:24
 */
public interface MyOrderService extends IService<Conference> {
    Map<String,List<UserConferenceVo>> findAll(String uId);
    int cancel(String conId);
    List<UserConferenceVo> findIng(String uId);
    List<UserConferenceVo> findOver(String uId);
}
