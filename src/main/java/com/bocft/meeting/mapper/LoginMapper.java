package com.bocft.meeting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocft.meeting.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<User> {

}
