package com.feather.common.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

/**
 * 使用步骤 1、import com.feather.common.utils.tree.*; 1、Bean类需要实现ITreeNode接口
 * 2、Bean集合转化为List<ITreeNode> iTreeNodes 3、调用new
 * Tree(iTreeNodes).getTreeData()返回数据
 */
@Data
@JsonPropertyOrder({ "nodeId", "nodeName", "parentNodeId", "children" })
public class TreeNode implements ITreeNode {

    // 树节点ID
    private String nodeId;

    // 树节点名称
    private String nodeName;

    // 父节点ID
    private String parentNodeId;

    private TreeNode parent;

    // 当前节点的子节点
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public void addChild(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    public void removeChild(TreeNode treeNode) {
        this.children.remove(treeNode);
    }
}