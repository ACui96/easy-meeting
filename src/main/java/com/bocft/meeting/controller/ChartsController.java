package com.bocft.meeting.controller;

import com.bocft.meeting.vo.NameNumber;
import com.bocft.meeting.service.ConferenceService;
import com.bocft.meeting.vo.BarVo;
import com.bocft.meeting.vo.PieVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "数据图表接口")
@Controller
@RequestMapping("/api/charts")
public class ChartsController {
    @Autowired
    private ConferenceService conferenceService;


    @ApiOperation("根据会议室查询过去会议室使用频率")
    @PostMapping("pie")
    @ResponseBody
    public PieVo selectPie(@RequestParam( required = false,defaultValue = "7") Integer id){
        PieVo pieVo = new PieVo();
        List<NameNumber> pieVolist = conferenceService.countByRoom(id);
        pieVo.setNameNumberList(pieVolist);
        return pieVo;
    }

    @ApiOperation("根据预约人查询过去会议室使用频率")
    @PostMapping("bar")
    @ResponseBody
    public BarVo selectBar(@RequestParam( required = false,defaultValue = "7") Integer id){
        BarVo barVo = new BarVo();
        //System.out.println(conferenceService.countByRoom());
        List<NameNumber> barVolist = conferenceService.countByUser(id);
        //System.out.println(barVolist);
        barVo.setNameNumberList(barVolist);
        return barVo;
    }
}
