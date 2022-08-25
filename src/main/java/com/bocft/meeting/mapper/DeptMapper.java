package com.bocft.meeting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocft.meeting.model.Dept;
import com.bocft.meeting.model.DeptIdAndName;
import com.bocft.meeting.model.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/13 17:06
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    //查询所有部门
    List<Dept> queryDeptAll();

    //添加部门
    int insert(Dept dept);


    //查询pid下的所有部门
    List<Dept> queryListByPid(@Param("pid")Integer pid);

    List<Node> queryDeptTree();


    List<DeptIdAndName> queryDeptId();


    //查询部门树信息
//    List<Node> queryDeptTree();

}
