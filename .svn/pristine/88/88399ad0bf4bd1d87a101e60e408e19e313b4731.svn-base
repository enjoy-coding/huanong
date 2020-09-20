/**
 * 用于模糊查询ztree节点
 * @param treeId 树id
 * @param inputId 搜索input id
 * @returns {{getshowNodes: (function()), getshowNodesById: getshowNodesById}}
 * @constructor
 */
var MtrSearchZTree = function (treeId, inputId) {

    // var location = l || ".ztree";

    //所有元素
    var nodesAll = {};

    //需要显示的元素
    var showNodesAll = {};

    //当前输入的关键字
    var keywords = "";

    //储存所有显示的父节点用于去重
    var parentNodes = new Map();

    //查找匹配的节点
    function filterFunc(node) {
        if (node.name.indexOf(keywords) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // 查找父级
    function findParent(ztreeObj, node, showNodes) {
        ztreeObj.expandNode(node, true, false, false);
        var pNode = node.getParentNode();
        if (pNode != null) {
            //如果有则不加,节约时间
            if (parentNodes.get(pNode.tId) == null) {
                parentNodes.set(pNode.tId, pNode);
                showNodes.push(pNode);
                findParent(ztreeObj, pNode, showNodes);
            }
        }
    }

    //给绑定事件
    function bindingZTree(id, inputId) {
        //如果数据量大的话,可以修改为回车后在查询
        $("#" + inputId).on("input change", function (e) {
            parentNodes = new Map();
            keywords = $(this).val();
            var ztreeObj = $.fn.zTree.getZTreeObj(id);
            if (keywords) {
                var showNodes = showNodesAll[id];
                //隐藏所有显示的节点，注意：使用hideNodes（）方法时需要引包jquery.ztree.exhide-3.5.min.js
                ztreeObj.hideNodes(ztreeObj.getNodesByParam("isHidden", false));
                //获取不符合条件的子父结点
                showNodes = ztreeObj.getNodesByFilter(filterFunc);
                //将 zTree 使用的标准 JSON 嵌套格式的数据转换为简单 Array 格式。 也就是为了返回子节点
                showNodes = ztreeObj.transformToArray(showNodes);
                //查找父节点
                for (var n in showNodes) {
                    if (showNodes.hasOwnProperty(n)) {
                        findParent(ztreeObj, showNodes[n], showNodes);
                    }
                }
                //显示匹配的节点，注意：使用showNodes（）方法时需要引包jquery.ztree.exhide-3.5.min.js
                ztreeObj.showNodes(showNodes);
                //把当前显示的的元素保存
                showNodesAll[id] = showNodes;
            } else {
                //显示所有影藏的节点
                ztreeObj.showNodes(ztreeObj.getNodesByParam("isHidden", true));
                //折叠所有节点
                //ztreeObj.expandAll(false);
            }
        });
    }

    function setAllNodes(treeId) {
        //保存全部节点
        var ztreeObj = $.fn.zTree.getZTreeObj(treeId);
        if (ztreeObj) {
            var nodes = ztreeObj.getNodes();
            nodesAll[treeId] = ztreeObj.transformToArray(nodes);
            //初始为空
            showNodesAll[treeId] = [];
        }
    }

    function initSearchZTree(treeId, inputId) {

        //初始化节点保存
        setAllNodes(treeId);
        //绑定事件
        bindingZTree(treeId, inputId);
    }

    //初始化
    initSearchZTree(treeId, inputId);

    return {
        getshowNodesById: function (id) {
            if (id) {
                return showNodesAll[id];
            }
        },
        getshowNodes:function() {
            return showNodesAll;
        }
    }
}