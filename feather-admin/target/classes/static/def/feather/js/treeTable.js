var layer,insTb,treeTable
var treeTableObj = function (option){
    this.expandFlag = false,//是否展开
    this.option = option,
    this.init = function(){
        layui.config({
            base: ctx + 'def/libs/layui-2.5.5/lay/modules/'
        }).extend({
            treeTable: '/treeTable/treeTable'
        }).use(['layer', 'util', 'treeTable'], function () {
            var $ = layui.jquery;
            layer = layui.layer;
            treeTable = layui.treeTable;

            insTb = treeTable.render({
                elem: option.elem == null ? '#bootstrap-tree-table' : option.elem,
                expandable: option.expandable == null ? false : true,
                tree: option.tree,
                treeDefaultClose: option.treeDefaultClose,     //是否默认折叠
                text: {},
                cols: option.cols,
                done: function (res, curr, count) {
                    layer.closeAll('loading');
                },
                reqData: function (data, callback) {
                    option.reqData(data, callback);
                },
                style: 'margin-top:0;'
            });
       
            if (option.toolbar) {
                treeTable.on('tool(bootstrap-tree-table)', function (obj) {
                    var data = obj.data;
                    var layEvent = obj.event;
                    var id = data.id;
                    var pid_ = data.pid;
        
                    //获取当前点击的层级
                    if (layEvent === 'add') {
                        conTreeTable.edit("新增" + treeTableObj.option.module.name, treeTableObj.option.module.insertUrl, function () {
                            option.addLayuiOpen(data);
                        });
        
                    } else if (layEvent === 'edit') {
                       conTreeTable.edit("修改" + treeTableObj.option.module.name, treeTableObj.option.module.updateUrl, function (index) {
                            option.editLayuiOpen(data);
                        });
                    } else if (layEvent === 'del') {
                            option.removeLayuiOpen(data);
                    }
                });
            } 
        });
        
    }
    return this;
}

/**
 * 表格操控方法
 */
var conTreeTable = {
    edit: function (title, url, callBack) {
        $("#editState").val(0)
        layer.open({
            type: 2,
            area: ['800px', ($(window).height() - 50) + 'px'],
            fix: false,
            maxmin: true,//不固定
            shade: 0.3,
            title: title,
            content: url,
            btn: ['确定', '关闭'],
            shadeClose: true,// 弹层外区域关闭
            yes: function (index, layero) {
                var iframeWin = layero.find('iframe')[0];
                iframeWin.contentWindow.submitHandler(index, layero);
            },
            end: callBack,
            cancel: function () {
                callBack();
                return;
            }
        });
    },

    remove: function (id, name, fn) {
        $.modal.confirm("确定删除【" + name + "】吗？", function () {
            var removeUrl = prefix + "/remove/" + id;
            $.get(removeUrl, function (result) {
                fn(result);
            });

        });
    },
    refresh_: function (id) {
        if (id == 0) {
            insTb.refresh();
        } else {
            insTb.refresh(id);
        }
    },
    expandAll: function () {
        treeTableObj.expandFlag = false;
        var components = insTb.getComponents();
        var table = components.$table;
        var $tr = table.children('tbody').children('tr');
        $tr.each(function () {
            if ($(this).hasClass('ew-tree-table-open')) {
                treeTableObj.expandFlag = true;
                return false;
            }
        });

        if (expandFlag) {
            insTb.foldAll();
        } else {
            insTb.expandAll();
        }
    },
    search:function(){
        var keyword = $('#'+tt.option.keyWords).val();
            	if(keyword){
            		insTb.filterData(keyword)
            	}else{
            		insTb.clearFilter();
            		insTb.foldAll();
            	}
    },
    /**
     * 后台搜索
     */
    ajaxSearch:function(){
        //获取form表单数据
        var currentId = $('form').attr('id');
        var params = $.common.formToJSON(currentId);
        var j = params;
        $.get(prefix+"/tree/list",params,function(res){
            insTb.refresh(0,res.data);
        })
    }

}

/**
 * 数据结构方法
 */
var construtsData = {
    isLastChildren: function (id, fn) {
        $.get(prefix + "/hasLastChildren/" + id, fn);
    }
}