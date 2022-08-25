package com.bocft.meeting.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.*;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.service.RepairMessageService;
import com.bocft.meeting.vo.RepairMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@Api(tags = "会议室报修相关")
public class RepairMessageController {

    @Autowired
    private RepairMessageService repairMessageService;


    @Autowired
    private ConferenceService conferenceService;

    @ApiOperation("用户添加报修单")
    @PostMapping("api/repair/addRepair")
    @ResponseBody
    public Result addRepair(@RequestParam(name = "房间名") String bName,
                            @RequestParam(name = "报修人邮箱") String uEmail,
                            @RequestParam(name = "报修信息") String repair){
       boolean add= repairMessageService.addRepair(bName,uEmail,repair);
        if (!add){
            return Result.fail("数据插入失败");
        }

        return Result.ok("数据插入成功");
    }

    @ApiOperation("管理员根据报修状态查看报修信息")
    @GetMapping("admin/repair/getRepair")
    @ResponseBody
    public Result getRepair(@RequestParam(name = "页", required = false) Long current){


        List<RepairMessageVo> list=repairMessageService.getRepair(Constant.REPAIR_NO);
        Page<RepairMessageVo> page = PageUtils.CurrentPage(current, Constant.LIMIT,list);

        return Result.ok(page);

    }
    @ApiOperation("导出excel")
    @GetMapping("admin/repair/export")
    @ResponseBody
    public void export(HttpServletResponse response){
        List<RepairMessageVo> list=repairMessageService.getRepair(Constant.REPAIR_NO);
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("报修信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), RepairMessageVo.class).sheet("报修信息").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @ApiOperation("维修完成管理员更改报修状态")
    @PostMapping("admin/repair/setRepair")
    @ResponseBody
    public Result setRepair(@RequestParam(name = "房间名") String bName){
       boolean set= repairMessageService.setRepair(bName);
       if (!set){
           return Result.fail("修改失败");
       }
       return Result.ok("修改成功");
    }


}
