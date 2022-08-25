package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.BeanUtils;
import com.bocft.meeting.common.PageUtils;
import com.bocft.meeting.common.Result;
import com.bocft.meeting.common.ResultCodeEnum;
import com.bocft.meeting.common.redis.RedisUtils;
import com.bocft.meeting.model.Room;
import com.bocft.meeting.service.RoomService;
import com.bocft.meeting.vo.RoomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/4/12 10:58
 */
@Api(tags = "会议室管理")
@Controller
@RequestMapping("/admin/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    RedisUtils redisUtils;


    @ApiOperation("分页查找所有会议室")
    @GetMapping("/roomList")
    @ResponseBody
    public Result selectPage(@RequestParam(name = "current",required = false) Long current, Long size) {
        Page page = PageUtils.CurrentPage(current, size);
        QueryWrapper<Room> wrapper=new QueryWrapper();
        wrapper.orderByAsc("b_name");
        Page<Room> roomPage = roomService.page(page,wrapper);
        return Result.ok(roomPage);
    }

    @ApiOperation("根据id查找会议室")
    @GetMapping("selRoom")
    @ResponseBody
    public Result selRoomById(long roomId) {
        Room room = roomService.getById(roomId);
        return Result.ok(room);
    }

    @ApiOperation("增加会议室")
    @PostMapping("/roomSave")
    @ResponseBody
    public Result saveRoom(@RequestBody @Valid RoomVo roomVo) {
        System.out.println(roomVo.getRoomId());
        System.out.println(roomVo.getBRemark());
        if (roomVo.getCapacities() <= 0) {
            return Result.fail("房间容量必须大于0");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("b_name", roomVo.getBName());
        if (roomService.getOne(wrapper) != null) {
            return Result.fail("房间已经存在");
        }
        Room room = new Room();
        BeanUtils.copyProperties(roomVo, room);
        boolean b = roomService.addRoom(roomVo);
        if (b) {
            //清除redis会议预约记录缓存
            redisUtils.deleteAll();
            return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
        }
        return Result.ok(ResultCodeEnum.FAIL.getMessage());
    }

    @ApiOperation("更新房间")
    @PostMapping ("/roomUpdate")
    @ResponseBody
    public Result updateRoom(@RequestBody @Valid RoomVo roomVo) {
        if (roomVo.getCapacities() <= 0) {
            return Result.fail("房间容量必须大于0");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("b_name", roomVo.getBName());
        if (roomService.getOne(wrapper) == null) {
            return Result.fail("房间不存在");
        }
        Room room = new Room();
        BeanUtils.copyProperties(roomVo, room);
        boolean b = roomService.updateById(room);
        if (b) {
            //清除redis会议预约记录缓存
            redisUtils.deleteAll();
            return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
        }
        return Result.fail(ResultCodeEnum.FAIL.getMessage());
    }

    @ApiOperation("删除房间")
    @DeleteMapping("/roomDelete")
    @ResponseBody
    public Result deleteRom(long roomId) {
        int i = roomService.deleteRoom(roomId);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        //清除redis会议预约记录缓存
        redisUtils.deleteAll();
        return Result.ok(ResultCodeEnum.SUCCESS.getMessage());
    }

}
