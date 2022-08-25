package com.bocft.meeting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocft.meeting.model.User;
import com.bocft.meeting.vo.UserInforVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper//代表持久层
public interface UserMapper extends BaseMapper<User> {


    boolean updateUser( User user);

    int delUser(@Param("userId") Integer user_id);

    List<UserInforVo> selectUserByName(@Param("name")String name);


    int countByDeptId(long deptId);
}
