package com.bocft.meeting.controller;

import com.bocft.meeting.common.BeanUtils;
import com.bocft.meeting.common.Result;
import com.bocft.meeting.model.Dept;
import com.bocft.meeting.model.DeptIdAndName;
import com.bocft.meeting.model.Node;
import com.bocft.meeting.service.DeptService;
import com.bocft.meeting.service.UserService;
import com.bocft.meeting.vo.DeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/14 10:29
 */
@RestController
@Slf4j
@Api(tags = "部门管理")
@RequestMapping("dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

    //查询所有部门列表信息
    @RequestMapping("/deptIndex")
    @ResponseBody
    public Result queryDeptIndex(){
        List<Dept> list = deptService.queryDeptAll();
        return Result.ok(list);
    }

    //添加部门
    @ApiOperation("增加部门")
    @RequestMapping("/deptAdd")
    @ResponseBody
    public Result deptAdd(@RequestBody DeptVo deptVo){
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptVo,dept);
        int num = deptService.insert(dept);
        if (num>0){
            return Result.ok();
        }
        return Result.fail("添加失败");
    }


    //修改部门
    @ApiOperation("修改部门")
    @RequestMapping("/deptUpdate")
    @ResponseBody
    public Result deptUpdate(@RequestBody DeptVo deptVo){
        System.out.println(deptVo);
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptVo,dept);
        int num = deptService.updateDept(dept);
        if (num>0){
            return Result.ok();
        }
        return Result.fail("修改失败");
    }


    //删除部门
    //修改部门
    @ApiOperation("删除部门")
    @RequestMapping("/deptDelete")
    @ResponseBody
    public Result deptDelete( Integer id){
        //根据id获取当前对象信息
        Dept dept = deptService.selectById(id);
        if (dept.getDLevel()==1){//如果是部门
            //查询以id为父节点的所有记录的信息
            List<Dept> list = deptService.queryListByPid(id);
            for (Dept zid:list){
                //判断子部门下是否有员工
                int num = userService.countByDeptId(zid.getDeptId());
                if (num>0){
                    return Result.fail("部门下还有员工");
                }
            }
            //没有隶属于部门的员工则删除：先删除子节点，再删除父节点
            for (Dept zid:list){
                //删除所有子节点
                deptService.deleteById(zid.getDeptId());
            }
            //删除父节点
            deptService.deleteById(id);
        }else {//如果是子部门
            int num = userService.countByDeptId(id);
            if (num>0){
                return Result.fail("部门下还有员工");
            }
            deptService.deleteById(id);
        }
        return Result.ok();
    }


    @ApiOperation("部门树结构信息")
    @ResponseBody
    @RequestMapping("queryDeptTree")
    //返回树结构信息
    public List queryDeptTree(){
        List<Node> list = deptService.queryDeptTree();
        return list;
    }

    @ApiOperation("查询所有子部门")
    @ResponseBody
    @RequestMapping("queryDeptId")
    public List<DeptIdAndName> queryDeptId(){
        List<DeptIdAndName> list = deptService.queryDeptId();
        return list;
    }


}
