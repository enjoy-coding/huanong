package com.feather.common.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 使用步骤 1、import com.feather.common.utils.tree.*; 1、Bean类需要实现ITreeNode接口
 * 2、Bean集合转化为List<ITreeNode> iTreeNodes 3、调用new
 * Tree(iTreeNodes).getTreeData()返回数据
 */
public class Tree {

    private HashMap<String, TreeNode> treeNodesMap = new HashMap<>();

    private List<TreeNode> treeNodesList = new ArrayList<>();

    public Tree(List<ITreeNode> list) {
        initTreeNodeMap(list);
        initTreeNodeList();
    }

    private void initTreeNodeMap(List<ITreeNode> list) {
        TreeNode treeNode = null;
        for (ITreeNode item : list) {
            treeNode = new TreeNode();
            treeNode.setNodeId(item.getNodeId());
            treeNode.setNodeName(item.getNodeName());
            treeNode.setParentNodeId(item.getParentNodeId());
            treeNodesMap.put(treeNode.getNodeId(), treeNode);
        } // 将所有的nodeId和TreeNode作为键值对放入map中

        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode parentTreeNode = null;// 新建一个基本的Node
        while (iter.hasNext()) {// 从Map中循环取值
            treeNode = iter.next();
            if (treeNode.getParentNodeId() == null || treeNode.getParentNodeId() == "") {
                continue;
            } // 当遍历到的元素的父节点为null或者空字符时，跳过当前循环

            parentTreeNode = treeNodesMap.get(treeNode.getParentNodeId());// 直接获取当前元素的父元素
            if (parentTreeNode != null) {// 如果父元素已经添加到map中了，就直接设置到treeNode上，并且也给父元素设置子元素
                treeNode.setParent(parentTreeNode);
                parentTreeNode.addChild(treeNode);
            }
        }
    }

    /**
     * 为getRoot准备list
     */
    private void initTreeNodeList() {
        if (treeNodesList.size() > 0) {
            return;
        }
        if (treeNodesMap.size() == 0) {
            return;
        }
        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode treeNode = null;
        while (iter.hasNext()) {// 继续遍历一遍treeNodesMap
            treeNode = iter.next();
            if (treeNode.getParent() == null) {// 当存在Parent为空的情况时
                this.treeNodesList.add(treeNode);// 将treeNode加入到list中
                // this.treeNodesList.addAll(treeNode.getAllChildren());//将treeNode的所有子节点加入list中
            }
        }
    }

    public List<TreeNode> getRoot() {
        List<TreeNode> rootList = new ArrayList<TreeNode>();
        if (this.treeNodesList.size() > 0) {
            for (TreeNode node : treeNodesList) {
                if (node.getParent() == null)
                    rootList.add(node);
            }
        }
        return rootList;
    }

    // public String getTreeData() {
    // List<TreeNode> root = getRoot();
    // SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); //
    // 构造方法里，也可以直接传需要序列化的属性名字
    // filter.getExcludes().add("parent");

    // return JSONObject.toJSONString(root, filter);
    // }

    /**
     * 获取指定nodeId的树
     * 
     * @param nodeId
     * @return
     */
    public TreeNode getTreeNode(String nodeId) {
        return this.treeNodesMap.get(nodeId);
    }
}
