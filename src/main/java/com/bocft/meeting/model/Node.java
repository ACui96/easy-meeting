package com.bocft.meeting.model;

/**
 * @author xmy
 * @version 1.0
 * @date 2022/7/14 15:20
 */
import java.util.ArrayList;
import java.util.List;

/***
 * 树结构节点
 */

public class Node {
//    private Integer id;
//    private Integer parentId;
//    private String name;
    private Integer deptId;
    private String dName;
    private Integer dManagerId;
    private Integer dParentId;
    private Integer dLevel;
    private Boolean open=false;//默认折叠
    List<Node> children= new ArrayList<>();;
    private Boolean checked=false;//是否选中

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getdManagerId() {
        return dManagerId;
    }

    public void setdManagerId(Integer dManagerId) {
        this.dManagerId = dManagerId;
    }

    public Integer getdParentId() {
        return dParentId;
    }

    public void setdParentId(Integer dParentId) {
        this.dParentId = dParentId;
    }

    public Integer getdLevel() {
        return dLevel;
    }

    public void setdLevel(Integer dLevel) {
        this.dLevel = dLevel;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}


