package com.bocft.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bocft.meeting.model.Dept;
import com.bocft.meeting.model.DeptIdAndName;
import com.bocft.meeting.model.Node;

import java.util.HashMap;
import java.util.List;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/13 17:25
 */
public interface DeptService extends IService<Dept> {

    List<Dept> queryDeptAll();

    int insert(Dept dept);

    int updateDept(Dept dept);

    Dept selectById(Integer id);

    List<Dept> queryListByPid(Integer pid);

    int deleteById(long id);

    List<Node> queryDeptTree();

    List<DeptIdAndName> queryDeptId();
}
