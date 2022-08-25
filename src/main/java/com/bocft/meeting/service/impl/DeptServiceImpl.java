package com.bocft.meeting.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bocft.meeting.common.TreeUtil;
import com.bocft.meeting.mapper.DeptMapper;
import com.bocft.meeting.model.Dept;
import com.bocft.meeting.model.DeptIdAndName;
import com.bocft.meeting.model.Node;
import com.bocft.meeting.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/14 10:32
 */
@Service
@Slf4j
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;


    //查询所有部门
    @Override
    public List<Dept> queryDeptAll() {
        List<Dept> list = deptMapper.queryDeptAll();
        return list;
    }

    //插入部门信息
    @Override
    public int insert(Dept dept) {
        return deptMapper.insert(dept);
    }

    //更新部门信息
    @Override
    public int updateDept(Dept dept) {
        return deptMapper.updateById(dept);
    }

    //根据id查询部门
    @Override
    public Dept selectById(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<Dept> queryListByPid(Integer pid) {
        return deptMapper.queryListByPid(pid);
    }

    @Override
    public int deleteById(long id) {
        return deptMapper.deleteById(id);
    }

    @Override
    public List<Node> queryDeptTree() {
        List<Node> list = deptMapper.queryDeptTree();
        TreeUtil util = new TreeUtil();
        List<Node> treeList = util.build(list);
        return treeList;
    }

    @Override
    public List<DeptIdAndName> queryDeptId() {
        List<DeptIdAndName> list = deptMapper.queryDeptId();
        return list;

    }
}
