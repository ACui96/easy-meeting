package com.bocft.meeting.common;

import com.bocft.meeting.model.Node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * 构架树结构树形json--好用
 * */
public class TreeUtil {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends Node> List<T> build(List<T> treeNodes, Integer pid) {
        List<T> treeList = new ArrayList<>();
        for(T treeNode : treeNodes) {
            if (pid.equals(treeNode.getdParentId())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends Node> T findChildren(List<T> treeNodes, T rootNode) {
        for(T treeNode : treeNodes) {
            if(rootNode.getDeptId().equals(treeNode.getdParentId())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends Node> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();
        //list转map
        Map<Integer, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for(T treeNode : treeNodes){
            nodeMap.put(treeNode.getDeptId(), treeNode);
        }
        for(T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getdParentId());
            if(node.getdParentId()==-1){//如果是父节点
                node.setOpen(true);
            }
            if(parent != null && !(node.getDeptId().equals(parent.getDeptId()))){
                //如果已经添加 不在添加
                if(!result.contains(node.getdName())){
                    parent.getChildren().add(node);
                }
                continue;
            }

            result.add(node);
        }
        return result;
    }
}
