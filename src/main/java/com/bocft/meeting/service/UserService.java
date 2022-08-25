package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.User;
import com.bocft.meeting.vo.UserInforVo;
import com.bocft.meeting.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService extends IService<User> {


    boolean addUser(UserVo userVo,String code);

    User selByIdUser(int user_id);

    boolean updateUser(@Param("vo") User user);

    int delUser(@Param("id") Integer user_id);

    // 根据手机号查询用户
    User getByPhone(String phone);

    List<UserInforVo> selectUserByName(@Param("name") String name);

    int countByDeptId(long deptId);
}
