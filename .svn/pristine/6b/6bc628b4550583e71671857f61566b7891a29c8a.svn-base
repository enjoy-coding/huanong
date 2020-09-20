/**
 * 构造异步树
 */
function ztreeAsync(option){
    this.option = option;
    var treeId = option.treeId?option.treeId:"tree";
    var $tree =  option.treeId?$("#"+option.treeId):$("#treeId");
    var isSearch = option.isSearch?option.isSearch:false;
    var inputId = option.inputId?option.inputId:"";
    var url = option.url;
    var isStringId = option.isStringId?option.isStringId:false;
    var isFirst = true;
    function initZTree(){
       var setting = {
            view: {
                dblClickExpand: false,
                showIcon: false,
                showLine: true,
                nameIsHTML:true,//支持后台html拼接
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    code: "code",
                    rootPId: "0"
                }
            },
            callback: {
                onClick: zTreeOnClick,
                onAsyncSuccess: zTreeOnAsyncSuccess,
                asyncError: zTreeOnAsyncError
            },
            async: {
                type: "get",
                dataType: 'text',
                enable: true,
                url: url,
                autoParam: ["id=pid"],
                //返回的数据过滤器
                dataFilter: function (treeId, parentNode, childNodes) {
                    if (!childNodes) return null;
                    for (var i = 0, l = childNodes.length; i < l; i++) {
                        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                    }
                    return childNodes;
                }
            }
        };
        var zTreeObj = $.fn.zTree.init($tree, setting);
        //是否模糊搜索
        if(isSearch){
            new MtrSearchZTree(treeId, inputId);
        }
        return zTreeObj;
    }
    function initZTreeString(){
        var setting = {
            view: {
                dblClickExpand: false,
                showIcon: false,
                showLine: true,
                nameIsHTML:true,//支持后台html拼接
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id_",
                    pIdKey: "pid_",
                    code: "code",
                    rootPId: ""
                }
            },
            callback: {
                onClick: zTreeOnClick,
                onAsyncSuccess: zTreeOnAsyncSuccess,
                asyncError: zTreeOnAsyncError
            },
            async: {
                type: "get",
                dataType: 'text',
                enable: true,
                url: url,
                autoParam: ["id_=pid_"],
                //返回的数据过滤器
                dataFilter: function (treeId, parentNode, childNodes) {
                    if (!childNodes) return null;
                    for (var i = 0, l = childNodes.length; i < l; i++) {
                        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                    }
                    return childNodes;
                }
            }
        };
        var zTreeObj = $.fn.zTree.init($tree, setting);
        //是否模糊搜索
        if(isSearch){
            new MtrSearchZTree(treeId, inputId);
        }
        return zTreeObj;
    }
    function zTreeOnClick(event, treeId, treeNode){
        option.zTreeOnClick(event, treeId, treeNode);
    }
    function zTreeOnAsyncError(event, treeId, treeNode) {
    }
    // 节点加载完的回调函数，加载方式依旧是分批加载，但是不是等用户展开节点才去加
    // 载，而是让它自动完成加载，这里不好的地方是依旧要加载全部数据，所以必须等待
    // 它加载完才能使用搜索功能
    function zTreeOnAsyncSuccess(event, treeId, treeNode) {
        var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
        //展开所有
        //zTreeObj.expandAll(true);
        //默认展开第一级节点
        if (isFirst) {
            //获取根节点个数,getNodes获取的是根节点的集合
            var nodeList = zTreeObj.getNodes();
            //展开第一个根节点
            zTreeObj.expandNode(nodeList[0], true);
            //默认选中第一个
            zTreeObj.selectNode(nodeList[0], true);
            $("#showSelected").text(nodeList[0].name);
            //当再次点击节点时条件不符合,直接跳出方法
            isFirst= false;
        }
        //这个方法是将标准 JSON 嵌套格式的数据转换为简单 Array 格式
        var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
        for (var i = 0; i < nodes.length; i++) {
            // 判断节点是否已经加载过，如果已经加载过则不需要再加载
            if (!nodes[i].zAsync) {
                zTreeObj.reAsyncChildNodes(nodes[i], '', true);
            }
        }
    }
    if(!isStringId){
        initZTree();
    }else{
        initZTreeString();
    }
}

/**
 * 树操作
 * @param control
 * @returns {Object}
 */
var ztreeControl = function (control){
    var obj = new Object();
    var $con = control;
    /**
     * 新增
     * @param selectTreeId
     * @param selectTreeName
     */
    obj.add = function (selectTreeId,selectTreeName){
        if(this.isNull(selectTreeId)){
            $.modal.msgError("请选择一个父节点！");
        }else{
            var pid = selectTreeId;
            $("#showSelected").text(selectTreeName + "-【新增子节点】");
            //刷新右侧
            $("#" + $con.fragment).load($con.prefix + '/add/fragment?pid=' + pid);
        }

    };
    /**
     * 点击查询右侧详情
     * @param id
     */
    obj.queryRightDetail = function (id) {
        $("#"+$con.fragment).load($con.prefix + '/edit/fragment?id=' + id);
    };
    /**
     * 编辑保存后显示右侧详情
     * @param id
     * @param name
     */
    obj.editSaveDetail = function (id,name){
        $("#"+$con.fragment).load(prefix + '/edit/fragment?id=' +id);
        if(name==null){
            name = $("#parentName").val();
        }
        $("#showSelected").text(name+"【编辑】");
    };
    /**
     * 保存右侧内容
     * @param id
     * @param sfn
     */
    obj.saveRight = function (id,sfn) {
        //校验
        if ($.validate.form()) {
            var params = $.common.formToJSON($con.formId);
            var url = prefix + (isAddOrEdit(id) ? "/add" : "/edit")

            $.ajax({
                url: url,
                data: params,
                type: 'POST',
                success: function (result) {
                    sfn(result);
                }
            });
        }
    };
    obj.refresh = function(selectTreeId,selectTreeNode){
        var zTree = $.fn.zTree.getZTreeObj("tree");
        if(!this.isNull(selectTreeId)){
            zTree.reAsyncChildNodes(selectTreeId, "refresh");
        }else{
            zTree.reAsyncChildNodes(selectTreeNode, "refresh", false);
        }
    };
    /**
     * 删除
     * @param selectTreeId
     * @param selectTreeNode
     */
    obj.remove = function (selectTreeId,selectTreeNode){
        if(this.isNull(selectTreeId)){
            $.modal.msgError("请选择要删除的节点！")
        }else{
            $.modal.confirm("确定删除该节点吗？", function () {
                var pid = selectTreeNode.pId;
                var parentName = selectTreeNode.maps.parentName;
                var data ={
                    id:selectTreeId,
                    pid:pid
                };
                $.post($con.prefix + '/remove',data,function(res){
                    if(res.code === 0){
                        $.modal.msgSuccess("删除成功!");
                        obj.editSaveDetail(pid,parentName);
                        refreshParentNode("tree");
                    }else{
                        $.modal.msgError(res.msg);
                    }
                });
            });
        }
    };
    /**
     * 判断值是空
     * @param value
     * @returns {boolean}
     */
    obj.isNull =function (value) {
        return value === undefined || value === null;
    };
    return obj;
};

    /**
     * 刷新当前节点
     */
    function refreshNode(tree,idValue) {
        /*根据 treeId 获取 zTree 对象*/
        var zTree = $.fn.zTree.getZTreeObj(tree),
        /*获取 zTree 当前被选中的节点数据集合*/
        nodes = zTree.getSelectedNodes();
        nodes.isParent = true;
        /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
        zTree.reAsyncChildNodes(nodes[0], "refresh", false);
        setTimeout(function () {
            selectNode(tree,idValue);
        }, 5000);
    }

    /**
     * 指定选中值
     * @param {*} tree 
     * @param {*} idValue 
     */
    function selectNode(tree,idValue){
        var zTree = $.fn.zTree.getZTreeObj(tree);
        var selectedNode=zTree.getNodeByParam("id", idValue);
        if(selectedNode==null){
            refreshNode(tree,idValue);
        }else {
            zTree.cancelSelectedNode();//先取消所有的选中状态
            zTree.selectNode(selectedNode, true);//将指定ID的节点选中
        }
    }
    /** 
     * 刷新当前选择节点的父节点 
     */  
    function refreshParentNode(tree) {  
        var zTree = $.fn.zTree.getZTreeObj(tree),
        nodes = zTree.getSelectedNodes();  
        /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/  
        var parentNode = zTree.getNodeByTId(nodes[0].pid);
        /*选中指定节点*/  
        zTree.selectNode(parentNode);  
        zTree.reAsyncChildNodes(parentNode, "refresh", false);
    }


    /**
     * 判断是新增还是编辑
     * @returns {boolean}
     */
    function isAddOrEdit(id){
        return id === "";
    }





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
        return node.name.indexOf(keywords) !== -1;
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
};