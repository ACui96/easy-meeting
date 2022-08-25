package com.bocft.meeting.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bocft.meeting.common.PageUtils;
import com.bocft.meeting.common.Result;
import com.bocft.meeting.service.DeptService;
import com.bocft.meeting.service.UserManagerService;
import com.bocft.meeting.vo.UserManagerVo;
import com.bocft.meeting.vo.UserViewVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * @date 2022/7/15 9:02
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/userManager")
public class UserManagerController {
    @Autowired
    UserManagerService userManagerService;

    @Autowired
    DeptService deptService;

    //新增用户
    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public Result addUser(@RequestBody UserManagerVo user){
        boolean add = userManagerService.MAddUser(user);
        if(add) return Result.ok();
        else
            return Result.fail("添加失败，用户已存在");
    }
    //查询所有用户
    @ApiOperation(value = "查询所有用户")
    @GetMapping("/findAll")
    public Result findAllUsers(@RequestParam(name = "page", required = false) Long current){
        System.err.println("current="+current);
        List<UserViewVo> list =new ArrayList<>();
        list=userManagerService.findAllUser();

        if(list.size()==0)
            return Result.fail("没有任何用户");
        if(current!=null&&current<=0) current=1L;
        Page<UserViewVo> page = PageUtils.CurrentPage(current, 8L, list);
            return Result.ok(page);
    }
    //删除用户
    @ApiOperation(value = "删除用户")
    @GetMapping("/delete")
    public Result delUser(@RequestParam("id") String id){
        int del=userManagerService.deleteUser(id);
        if (del==0){
            return Result.fail("删除失败");
        }else if(del==2){
            return Result.fail("删除失败，该用户有正在预约中的会议");
        }
        return Result.ok("成功删除"+"ID为："+id+"的用户");
    }

    /**
     * 修改用户信息
     */
    //获取用户信息
    @ApiOperation(value = "回显用户信息")
    @GetMapping("/showUserInfo/{userId}")
    @ResponseBody
    public Result showUserInfo(@PathVariable() String userId) {
        UserViewVo userViewVo = userManagerService.SelectUser(userId);
        if (userViewVo == null) return Result.fail("用户不存在");
        return Result.ok(userViewVo);
    }

    //更改用户信息
    @ApiOperation(value = "更改用户信息")
    @PostMapping("/updateUser")
    public Result upDate(@RequestBody UserManagerVo userManagerVo) {
        boolean success = userManagerService.updateUser(userManagerVo);
        if (success) return Result.ok();
        else return Result.fail("更新用户信息失败");
    }
    //根据姓名查询用户信息
    @ApiOperation(value = "根据用户名查询")
    @GetMapping("/findByName")
    public Result findByUserName(@RequestParam("name") String name,@RequestParam(name = "page", required = false) Long current){
        List<UserViewVo> list = userManagerService.findByName(name);
        if(current!=null&&current<=0) current=1L;
        if(list.size()==0)
            return Result.fail("没有任何用户");
        Page<UserViewVo> page = PageUtils.CurrentPage(current, 8L, list);
        return Result.ok(page);
    }
    @ApiOperation(value = "根据部门id查询用户")
    @GetMapping("/findByDeptId")
    public Result findUsersByDeptId(@RequestParam("deptId") String deptId,
                                    @RequestParam(name = "page", required = false) Long current){
        List<UserViewVo> list = userManagerService.findByDeptId(deptId);
        if(list.size()==0)
            return Result.fail("没有任何用户");
        if(current==null) return Result.ok(list);
        if(current<=0) current=1L;
        Page<UserViewVo> page = PageUtils.CurrentPage(current, 8L, list);
        return Result.ok(page);
    }
    @ApiOperation(value = "用户自己更新信息")
    @PostMapping("/updateByUser")
    public Result upDateByUser(@RequestBody UserManagerVo userManagerVo){
        boolean is = userManagerService.updateByUser(userManagerVo);
        if(is) return Result.ok();
        else return Result.fail("修改失败");
    }





}
