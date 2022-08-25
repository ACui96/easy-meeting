package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.Room;
import com.bocft.meeting.vo.RoomVo;

/**
 * @author yang
 * @date 2022/4/12 9:47
 */
public interface RoomService extends IService<Room> {
    /**
     * 删除会议室
     *
     * @param roomId
     * @return int
     * @author yang
     * @date 2022/4/12 15:25
     */

    int deleteRoom(long roomId);

    /**
     * 添加会议室
     *
     * @param roomVo
     * @return boolean
     * @author yang
     * @date 2022/4/15 17:48
     */
    boolean addRoom(RoomVo roomVo);



}
