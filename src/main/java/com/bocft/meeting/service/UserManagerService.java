package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.User;
import com.bocft.meeting.vo.UserManagerVo;
import com.bocft.meeting.vo.UserViewVo;

import java.util.List;

/**
 * @author xz
 * @date 2022/7/14 16:54
 */
public interface UserManagerService extends IService<User> {
    //添加用户
    boolean MAddUser(UserManagerVo userManagerVo);
    //查询用户信息
    List<UserViewVo> findAllUser();
    //删除用户信息
    int deleteUser(String userId);
    //根据id获取用户信息
    UserViewVo SelectUser(String userId);
    //修改用户信息
    boolean updateUser(UserManagerVo userManagerVo);
    //按姓名查询
    List<UserViewVo> findByName(String name);
    //按部门查询
    List<UserViewVo> findByDeptId(String tDeptId);
    //用户自己更新信息
    boolean updateByUser(UserManagerVo userManagerVo);
}
