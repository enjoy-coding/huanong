var NHJG = ModelManager["NHJG"] = {
    pickedObject: null,
    picType: 'meter',
    data: {
        setting: {data: {simpleData: {enable: true}}},
        data: [{
            id: 1,
            pId: 0,
            name: "父节点1",
            open: true,//该节点默认打开
        },
            {
                id: 11,
                pId: 1,
                name: "子节点1",
                checkbox: true

            },
            {
                id: 12,
                pId: 1,
                name: "子节点2"
            }
        ],
        ChartsName: [],
        year: "",
        month: "",
        cachename: "",
        datayear1: "2020",
        datamonth1: "1",
        datayear2: "",
        datamonth2: ""

    },
    nhtype:'water',
    element: null,
    layui:null,
    laypage: null,
    table: null,
    laydate:null,
    form:null,
    com: {
        $nhprefix: ctx + "screen/nhjg",
    },
    ztreeObj: "",
    ysphZtreeObj:"",
    ydphZtreeObj:"",
    selectTabIndex:0,
    mo: {
        $yxjk_monitor_tree: "zTree",
        $yd_monitor_tree: "ydzTree",
        $ld_monitor_tree: "ldzTree",
        $ld_monitor_inputId_zdjz: "keyzdjz",
        $ld_monitor_inputId_hd: "keyhd",
        $ld_monitor_inputId_hs: "keyhs"
    },
    echarts: {
        $useNumber: null,//用量图表id
        $category: null,//类别
        $area: null,//面积
        $person: null,//人均
    },
    /**
     * 初始化年份时间框
     */
    initSelectYear:function(){
        NHJG.laydate.render({
            elem: '#test2-rqxz'
            , type: 'year'
            , value: new Date()
            , max : NHJG.getNowFormatDate()
            , done: function (value, date) {
                NHJG.data.datayear1 = date.year;
            }
            ,change: function(value, date, endDate){
                $('#test2-rqxz').val(value);
                $('.layui-laydate').remove();//删除
                if(NHJG.selectTabIndex == 1){
                    NHJG.nhtj.init("roote",value,"0");
                }else{
                    NHJG.nhtj.init("rootw",value,"0");
                }


            }

        });
    },
    /**
     * 获取年份时间框的值
     * @returns {string}
     */
    getSelectYear:function(){
        var year = $("#test2-rqxz").val() == ""? NHJG.data.datayear1: $("#test2-rqxz").val() ;
        return year;
    },
    /**
     * 初始化定位
     */
    initDingwei:function(){
        $("#dingwei").empty();
        $("#dingwei").remove();
        $("#dingwei_water").empty;
        $("#dingwei_water").remove();


    },
    /**
     * 初始化图标
     */
    initEcharts: function () {
        this.echarts.$useNumber = null;
        this.echarts.$category = null;
        this.echarts.$area = null;
        this.echarts.$person = null;

    },
    /**
     * 初始化layer
     */
    initLayer: function () {
        layui.use(['element', 'util', 'table', 'laypage', 'laydate', 'form'], function () {
            var $ = layui.jquery;
            var $ = layui.jquery;
            NHJG.layui = layui;
            layui.$.support.cors = true;
            NHJG.element = layui.element;
            NHJG.util = layui.util;
            NHJG.table = layui.table;
            NHJG.laypage = layui.laypage;
            NHJG.form = layui.form;
            NHJG.laydate = layui.laydate;


            NHJG.form.render("select");    //下拉框的初始化


        });
    },
    initTableHeightAuto:function(){
        $(".layui-table-cell").css("display","table-cell");
        $(".layui-table-cell").css("vertical-align:","middle");
    },
    /**
     * 获取当前时间
     * @returns {*}
     */
    getNowFormatDate:function () {
        var date = new Date();
        var year = date.getYear()+1;
        return year;
    },
    /**
     * 用水平衡日期选择
     * @returns {number}
     */
    getSdphNowFormatDate:function () {
        var date = new Date();
        var month = date.getMonth()+1;
        return month;
    },
    getCurrentDate:function(){
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        return year+"-"+month;
    },
    /**
     * 能耗监管页面初始化方法
     * @param name
     */
    init: function (name) {
        //参数name：二级菜单名（如：预警报警、历史预警、预警统计）,它等于 currMenuName
        //         为空时表示是通过一级菜单打开

        NHJG.initLayer();
        menuChangeInit();
        //初始化右边展开
        rightBoxShow();
        NHJG.initTableHeightAuto();
        //改变全局搜索的默认值
        objectType = 'subject';
        $(".searchLabel").find("input").eq(0)[0].checked = true;
        setTimeout(function(){
            if(viewer){
                viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                    var scene = viewer.scene;
                    layerTree.removeLocatedBuilding();
                    if (NHJG.pickedObject != null) {
                        if(NHJG.pickedObject.id){
                            if(NHJG.pickedObject.id._billboard){
                                NHJG.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                            }
                        }
                    }
                    if (scene.mode !== Cesium.SceneMode.MORPHING) {
                        NHJG.pickedObject = scene.pick(movement.position);
                        if(NHJG.pickedObject && NHJG.picType == 'meter'){
                            if(NHJG.pickedObject.id){
                                if(NHJG.pickedObject.id._properties["_FWBM"]){
                                    var typeno = NHJG.pickedObject.id._properties["_FWBM"]._value;
                                    NHJG.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                    $.get(ctx + 'aupipes/nhjg/getAupSideList?typeno='+ typeno+'&type='+NHJG.nhtype,function(data){
                                        var res = data;
                                        var cacheId='';
                                        if(res.length>0){
                                            for(var i in res){
                                                if(res[i].type!=undefined&&res[i].type.indexOf("build")==0&&NHJG.nhtype=='water'){
                                                    cacheId = res[i].cacheId;
                                                    //选中左侧树节点
                                                    var treeObj = $.fn.zTree.getZTreeObj("zTree");
                                                    var nodes = treeObj.getNodesByParamFuzzy("name", res[i].name, null);
                                                    if (nodes.length>0) {
                                                        treeObj.selectNode(nodes[0]);  //选中节点
                                                        layerTree.locateBuildingByID(nodes[0].code, "HOUSE_ANNOTATION");
                                                    }

                                                }else if(res[i].type!=undefined&&res[i].type.indexOf("Electric")==0&&NHJG.nhtype=='ele'){
                                                    cacheId = res[i].cacheId;
                                                    //选中左侧树节点
                                                    var treeObj = $.fn.zTree.getZTreeObj("ydzTree");
                                                    var nodes = treeObj.getNodesByParamFuzzy("name", res[i].name, null);
                                                    if (nodes.length>0) {
                                                        treeObj.selectNode(nodes[0]);  //选中节点
                                                        layerTree.locateBuildingByID(treeNode.code, "HOUSE_ANNOTATION");
                                                    }
                                                }
                                            }
                                            var myDate = new Date();
                                            var tYear = myDate.getFullYear();
                                            NHJG.nhtj.init(cacheId,tYear,0);
                                        }
                                    });
                                }
                            }
                        }
                    }
                },Cesium.ScreenSpaceEventType.LEFT_CLICK);
            }
        },1000)


        //初始化左侧面板
        $("#left-panel").load(ctx + 'screen/nhjg/left', function (data) {
            //勾选初始化图层
            selectedLinePipes(layerCfg.nhjg.water);
            selectedLinePipe(layerCfg.nhjg.water);
            //加载左侧面板
            $("#left-panel").html(data);
            //初始化年份
            NHJG.initSelectYear();
            NHJG.element.on('tab(sdphNhtjTitle)', function (elem) {
                window.onresize();
            });

            NHJG.ysph.init();

            //左侧tab切换
            NHJG.element.on('tab(nhjg_left_tab)', function (elem) {
                NHJG.initDingwei();
                NHJG.selectTabIndex = elem.index;
                if (elem.index == 0) {
                    //勾选初始化图层
                    selectedLinePipe(layerCfg.nhjg.water);
                    NHJG.ysph.init();
                    NHJG.nhtype='water';
                }
                if (elem.index == 1) {
                    //勾选初始化图层
                    selectedLinePipe(layerCfg.nhjg.electricity);
                    NHJG.ydph.init();
                    NHJG.nhtype='ele';
                }
                if (elem.index == 2) {//重點建築
                    //勾选初始化图层
                    selectedLinePipe(layerCfg.nhjg.house);
                    NHJG.importantBuilding.init();
                }

            });
        });
    }
};

/**
 * 重点建筑
 * @type {{init: NHJG.importantBuilding.init, importantBuildingTree: NHJG.importantBuilding.importantBuildingTree}}
 */
NHJG.importantBuilding = {
    init:function(){
        // NHJG.importantBuilding.importantBuildingTree();
        //默认用电
        NHJG.ydph.electricityTree("ldzTree");
        //下拉选项（用水，用电），默认为用电
        var selectValue = $("#zdjz").val();
        NHJG.picType = 'zdjz';
        NHJG.nhtj.init("roote",NHJG.getSelectYear(),"1");
        //重点建筑下拉选项
        NHJG.form.on('select(test)', function (data) {
            if(data.value=="rootw"){
                NHJG.ysph.waterTree("ldzTree");
            }else{
                NHJG.ydph.electricityTree("ldzTree");
            }
            NHJG.nhtj.init(data.value, NHJG.getSelectYear(),"1");//用水能耗
        });
    },
    /**
     * 重点建筑树结构
     */
    importantBuildingTree:function(){
        ztree.initZTree("ldzTree", ctx + "aupipes/nhjg/jdxx/waterAndElectricity/tree?important=" + "1" + "&elementById=" + $("#zdjz").val(), function (event, treeId, treeNode) {

            NHJG.nhtj.init(treeNode.maps.cacheId,NHJG.getSelectYear(),1);

            NHJG.position.init(NHJG.getSelectYear(),$("#zdjz").val(), treeNode.name,treeNode, treeNode.maps.cacheId,1);

            //定位
            if(treeNode.name == '东北' || treeNode.name == '东南' || treeNode.name == '西北' || treeNode.name == '西南'){
                layerTree.locateBuildingByID(treeNode.name, "分区范围");
            }else{
                layerTree.locateBuildingByID(treeNode.code, "HOUSE_ANNOTATION");
            }
        },true,'keyzdjz');
    }
}
/**
 * 用电平衡
 * @type {{init: NHJG.ydph.init, openYdphtk: NHJG.ydph.openYdphtk, loadYdphPage: NHJG.ydph.loadYdphPage, electricityTree: NHJG.ydph.electricityTree}}
 */
NHJG.ydph = {
    init:function(){
         //加载用电平衡弹框页面
        NHJG.ydph.loadYdphPage();
    
        NHJG.picType = 'meter';
        //用电平衡树
        NHJG.ydph.electricityTree();
        //用电平衡按钮
        $("#ydphtk").click(function(){
            //加载用水平衡弹框
            NHJG.ydph.openYdphtk();

        })
        //初始化用电右侧内容
        NHJG.nhtj.init("roote", NHJG.getSelectYear(),"0");
    },
    /**
     * 用电平衡树
     */
    electricityTree:function(id){
        var treeId = "ydzTree";
        if(id!=undefined){
            treeId = id;
        }
        NHJG.ydphZtreeObj=ztree.initZTree(treeId, ctx + "aupipes/nhjg/jdxx/electricity/tree", function (event, treeId, treeNode) {
            //加载右侧数据
            NHJG.nhtj.init(treeNode.maps.cacheid, NHJG.getSelectYear(),"0");
            //用电平衡不定位
            // NHJG.position.init(NHJG.getSelectYear(),"roote", treeNode.name,treeNode,treeNode.maps.cacheId,"0");
            //
            // //定位
            // if(treeNode.name == '东北' || treeNode.name == '东南' || treeNode.name == '西北' || treeNode.name == '西南'){
            //     layerTree.locateBuildingByID(treeNode.name, "分区范围");
            // }else{
            //     layerTree.locateBuildingByID(treeNode.code, "HOUSE_ANNOTATION");
            // }
        },true,'keyhd');
    },
    /**
     *  打开用电平衡弹框
     */
    openYdphtk: function () {
        layer.open({
            type: 1,
            title: '用电平衡',
            area: ['100%', '100%'],
            offset: 'b',
            content: $('.ydphMoreContent'),
            skin: 'layer-style',
            id: 'Box5_'
        });
        NHJG.ydph.initYdphTk();

    },
    initYdphTk:function(){
        //重新调用重置
        NHJG.ydph.resetYdphForm();
        //重新调用按钮查询根节点查询事件
        NHJG.ydph.loadYdphTable(NHJG.ydph.getTableParams("roote",2));

    },
    resetYdphForm:function(){
        //重置搜索时间为当前时间
        $("#yd_beginTime").val(NHJG.getCurrentDate());
        $("#yd_endTime").val(NHJG.getCurrentDate());
        $("#yd_cacheName").val("");
        //树节点重置
        var treeObj = $.fn.zTree.getZTreeObj("zTree_ydph");

        //清除所有树节点选中
        treeObj.cancelSelectedNode();
        NHJG.form.render();
        //默认选中第一个
        var nodeList = treeObj.getNodes();
        treeObj.selectNode(nodeList[0], true);
        //收起所有节点
        treeObj.expandAll(false);
        //展开第一个根节点
        treeObj.expandNode(nodeList[0], true);
    },
    /**
     * 加载用电平衡页面
     */
    loadYdphPage:function(){
        $(".ElectricityBox").remove();
        $.get(ctx + 'screen/nhjg/ydphtk',function(data){
            if($(".ydphMoreContent").length==0) {
                $(".contentBox.insidePageBox.pr").append(data);
                NHJG.form.render("select");
                NHJG.laydate.render({
                    elem: '#yd_beginTime'
                    , type: 'month'
                    , trigger: 'click'
                    , max: NHJG.getSdphNowFormatDate()
                    , change: function (value, date, endDate) {
                        $('#yd_beginTime').val(value);
                        $('.layui-laydate').remove();//删除
                    }

                });
                NHJG.laydate.render({
                    elem: '#yd_endTime'
                    , type: 'month'
                    , trigger: 'click'
                    , max: NHJG.getSdphNowFormatDate()
                    , change: function (value, date, endDate) {
                        $('#yd_endTime').val(value);
                        $('.layui-laydate').remove();//删除
                    }
                });
                //默认选中当前时间
                $("#yd_beginTime").val(NHJG.getCurrentDate());
                $("#yd_endTime").val(NHJG.getCurrentDate());
                $("#cacheName").val();

                //加载用水平衡树
                var url = ctx + "aupipes/nhjg/jdxx/electricity/tree";
                var treeId = "zTree_ydph";
                var inputId = "";
                var cacheId = "roote";
                var cacheLevel = 2;//等级
                NHJG.data.ydphZtreeObj = ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
                    // //获取树节点选中的cacheId;
                    cacheId = treeNode.maps.cacheid;
                    cacheLevel = parseInt(treeNode.maps.cachelevel) + 1; //只查询当前节点的下一级节点
                    //树节点点击直接查询
                    NHJG.ydph.loadYdphTable(NHJG.ydph.getTableParams(cacheId, cacheLevel));
                }, false, inputId);
                //默认查询
                   NHJG.ydph.loadYdphTable(NHJG.ydph.getTableParams(cacheId,cacheLevel));
                   //查询按钮
                   $("#yd_query").click(function(){
                       NHJG.ydph.loadYdphTable(NHJG.ydph.getTableParams(cacheId,cacheLevel));
                   });
                   //重置按钮
                   $("#yd_reset").click(function(){
                       NHJG.ydph.initYdphTk();
                       cacheId = "roote";
                   });
                  //导出按钮
                   $("#yd_exp").click(function(){
                       var msg = layer.msg('正在导出数据，请稍后...', {time: false});
                       var where = NHJG.ydph.getTableParams(cacheId,cacheLevel);
                       $.post(ctx + 'screen/nhjg/ydphTable/export', where, function (result) {
                           layer.close(msg);//手动关闭
                           layer.msg("导出成功！", {time: 1000});
                           if (result.code == 0) {
                               window.location.href = ctx + "system/common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
                           } else if (result.code == 500) {
                               layer.msg(result.msg)
                           } else {
                               layer.msg(result.msg);
                           }
                       });
                   });

            }
        });
    },
    getTableParams:function(cacheId,cacheLevel){
        var beginYear = NHJG.getSelectYear();
        var beginMonth = "";
        var endYear = "";
        var endMonth = "";
        var cacheName =  $("#yd_cacheName").val();

        if($("#yd_beginTime").val()!=""){
            beginMonth = $("#yd_beginTime").val().substr(5,2);
            beginYear = $("#yd_beginTime").val().substr(0,4);

        }
        if($("#yd_endTime").val()!=""){
            endYear = $('#yd_endTime').val().substr(0,4);
            endMonth = $('#yd_endTime').val().substr(5,2);

        }

        var category ="";

        return {
            "cacheId":cacheId,
            "cacheLevel":cacheLevel,
            "cacheName":cacheName,
            "categoryId":category,
            "params":{
                "beginYear":beginYear,
                "beginMonth":beginMonth,
                "endYear":endYear,
                "endMonth":endMonth
            }

        };
    },

    /**
     * 用电平衡查询
     */
    loadYdphTable:function(where){
        NHJG.table.render({
            elem: '#ydphTable',
            // height: "full-200",
            totalRow: true,//开启合计行
            skin: 'nob',
            method:'post',
            limit: 10 ,
            where:where,
            url: ctx + 'screen/nhjg/ydphTable' ,//数据接口
            cols: [[ //标题栏
                {type: 'numbers', title: '序号', align: 'center'},
                {field: 'year', title: '年份', align: 'center'},
                {field: 'month', title: '月份', align: 'center'},
                {field: 'cacheName', title: '名称', align: 'center',totalRowText:'合计'},
                {field: 'tableUseNumber', title: '总用量(度)', align: 'center',totalRow: true},
                {field: 'sumUseNumber', title: '汇总用量(度)', align: 'center',totalRow: true},
                {field: 'shl', title: '损耗率', align: 'center',templet:function(d){
                        var iconClass = d.shl > 0?"iconfont icon-jiantou-copy":"iconfont icon-jiantou-copy1";
                        return '<i class="'+iconClass+'">'+d.shl+'%</i>';
                    }},
                {field: 'sumUseNumberHb', title: '汇总用量环比', align: 'center'
                    ,templet: function (d) {
                        var iconClass = d.hbIcon == 1?"iconfont icon-jiantou-copy":"iconfont icon-jiantou-copy1";
                        return '<i class="'+iconClass+'">'+d.sumUseNumberHb+'%</i>';

                    }}
            ]],
            response: {dataName: "rows"
                        , countName: "total"
                        ,statusCode:"0"
                        ,statusName:"code"
                }//状态要设置为200
            ,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            ,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
                , limits: [10, 20, 30, 50]
            }
            ,done:function(res){
                var totalRow = res.totalRow;//统计结算后余额
                var tableUseNumber = totalRow.tableUseNumber;
                var sumUseNumber = totalRow.sumUseNumber;
                //修改 结算后余额 统计单元格文本
                this.elem.next().find('.layui-table-total td[data-field="tableUseNumber"] .layui-table-cell').text(tableUseNumber);
                this.elem.next().find('.layui-table-total td[data-field="sumUseNumber"] .layui-table-cell').text(sumUseNumber);

            }
        })
    }

}
/**
 * 用水平衡
 * @type {{loadYsphTable: NHJG.ysph.loadYsphTable, openYsphtk: NHJG.ysph.openYsphtk, loadCascadeOption: NHJG.ysph.loadCascadeOption, loadYsphPage: NHJG.ysph.loadYsphPage}}
 */
NHJG.ysph = {
    /**
     * 用水平衡初始化方法
     */
    init:function(){
        //加载用水平衡页面
        NHJG.ysph.loadYsphPage();
        //用水平衡树结构
        NHJG.ysph.waterTree();
        NHJG.picType = 'meter';
        //用水平衡按钮
        $("#ysphtk").click(function(){
            //加载用水平衡弹框
            NHJG.ysph.openYsphtk();

        });
        //用水平衡右侧内容初始化
        NHJG.nhtj.init("rootw", NHJG.getSelectYear(),0);//用水能耗

    },
    /**
     * 用水平衡树
     * @param id
     */
    waterTree:function(id){
        var treeId = "zTree";
        if(id!=undefined){
            treeId = id;
        }
        NHJG.ysphZtreeObj = ztree.initZTree(treeId, ctx + "aupipes/nhjg/jdxx/water/tree", function (event, treeId, treeNode) {
            //加载右侧数据
            NHJG.nhtj.init(treeNode.maps.cacheId, NHJG.getSelectYear(),"0");

            NHJG.position.init(NHJG.getSelectYear(),"rootw", treeNode.name,treeNode,treeNode.maps.cacheId,"0");

            //定位
            if(treeNode.name == '东北' || treeNode.name == '东南' || treeNode.name == '西北' || treeNode.name == '西南'){
                layerTree.locateBuildingByID(treeNode.name, "分区范围");
            }else{
                layerTree.locateBuildingByID(treeNode.code, "HOUSE_ANNOTATION");
            }

        },true,'keyhs');
    },
    /**
     * 打开用水平衡窗口
     */
    openYsphtk: function () {

        layer.open({
            type: 1,
            title: '用水平衡',
            area: ['100%', '100%'],
            offset: 'b',
            content: $('.ysphMoreContent'),
            skin: 'layer-style',
            id: 'Box4_'
        });
        NHJG.ysph.initYsphTk();
    },
    initYsphTk:function(){
        //重新调用重置
        NHJG.ysph.resetYsphForm();
        //重新调用按钮查询根节点查询事件
        NHJG.ysph.loadYsphTable(NHJG.ysph.getTableParams("rootw",2));

    },
    resetYsphForm:function(){
        //重置搜索时间为当前时间
        $("#beginTime").val(NHJG.getCurrentDate());
        $("#endTime").val(NHJG.getCurrentDate());
        $("#cacheName").val("");
        //树节点重置
        var treeObj = $.fn.zTree.getZTreeObj("zTree_ysph");
        //清除所有树节点选中
        treeObj.cancelSelectedNode();
        NHJG.form.render();
        //默认选中第一个
        var nodeList = treeObj.getNodes();
        treeObj.selectNode(nodeList[0], true);
        //收起所有节点
        treeObj.expandAll(false);
        //展开第一个根节点
        treeObj.expandNode(nodeList[0], true);
    },
    /**
     * 加载用水平和页面
     */
    loadYsphPage:function(){
        $(".waterBox").remove();
        $.get(ctx + 'screen/nhjg/ysphtk',function (data) {
            if($(".ysphMoreContent").length==0){
                $(".contentBox.insidePageBox.pr").append(data);
                NHJG.form.render("select");

                NHJG.laydate.render({
                    elem: '#beginTime'
                    ,type: 'month'
                    ,trigger: 'click'
                    ,max:NHJG.getSdphNowFormatDate()
                    ,change: function(value, date, endDate){
                        $('#beginTime').val(value);
                        $('.layui-laydate').remove();//删除
                    }

                });
                NHJG.laydate.render({
                    elem: '#endTime'
                    ,type: 'month'
                    ,trigger: 'click'
                    ,max:NHJG.getSdphNowFormatDate()
                    ,change: function(value, date, endDate){
                        $('#endTime').val(value);
                        $('.layui-laydate').remove();//删除
                    }

                });
                //默认选中当前时间
                 $("#beginTime").val(NHJG.getCurrentDate());
                 $("#endTime").val(NHJG.getCurrentDate());
                //选项级联
                // NHJG.ysph.loadCascadeOption();

                //加载用水平衡树
                var url = ctx + "aupipes/nhjg/jdxx/water/tree";
                var treeId = "zTree_ysph";
                var inputId = "";
                var cacheId = "rootw";
                var cacheLevel = 2;//等级
                NHJG.data.ysphZtreeObj = ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
                    //获取树节点选中的cacheId;
                    cacheId = treeNode.maps.cacheId;
                    cacheLevel = treeNode.maps.cacheLevel + 1; //只查询当前节点的下一级节点
                    //树节点点击直接查询
                    NHJG.ysph.loadYsphTable(NHJG.ysph.getTableParams(cacheId,cacheLevel));
                },false,inputId);
                //默认查询
                NHJG.ysph.loadYsphTable(NHJG.ysph.getTableParams(cacheId,cacheLevel));
                //查询按钮
                $("#query").click(function(){
                    NHJG.ysph.loadYsphTable(NHJG.ysph.getTableParams(cacheId,cacheLevel));
                });
                //重置按钮
                $("#reset").click(function(){
                    NHJG.ysph.initYsphTk();
                    cacheId = "rootw";
                });
                //导出按钮
                $("#exp").click(function(){
                    var msg = layer.msg('正在导出数据，请稍后...', {time: false});
                    var where = NHJG.ysph.getTableParams(cacheId,cacheLevel)
                    $.post(ctx + 'screen/nhjg/ysphTable/export', where, function (result) {
                        layer.close(msg);//手动关闭
                        layer.msg("导出成功！", {time: 1000});
                        if (result.code == 0) {
                            window.location.href = ctx + "system/common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
                        } else if (result.code == 500) {
                            layer.msg(result.msg)
                        } else {
                            layer.msg(result.msg);
                        }
                    });
                });
            }
        })

    },
    getTableParams:function(cacheId,cacheLevel){
        var beginYear = NHJG.getSelectYear();
        var beginMonth = "";
        var endYear = "";
        var endMonth = "";
        var cacheName =  $("#cacheName").val();
        if($("#beginTime").val()!=""){
            beginMonth = $("#beginTime").val().substr(5,2);
            beginYear = $("#beginTime").val().substr(0,4);

        }
        if($("#endTime").val()!=""){
            endYear = $('#endTime').val().substr(0,4);
            endMonth = $('#endTime').val().substr(5,2);

        }

        var category ="";

        return {
            "cacheid":cacheId,
            "cachename":cacheName,
            "cachelevel":cacheLevel,
            "categoryId":category,
            "params":{
                "beginYear":beginYear,
                "beginMonth":beginMonth,
                "endYear":endYear,
                "endMonth":endMonth
            }

        };
    },
    /**
     * 加载用水平衡表格
     * @param where 条件
     */
    loadYsphTable:function(where){

        NHJG.table.render({
            elem: '#ysphTable',
            // height: "full-200",
            skin: 'nob',
            method:'post'
            ,totalRow:true
            ,limit: 10
            ,where:where
            ,url: ctx + 'screen/nhjg/ysphTable' //数据接口
            ,cols: [[ //标题栏
                {type: 'numbers', title: '序号', align: 'center'},
                {field: 'year', title: '年份', align: 'center'},
                {field: 'month', title: '月份', align: 'center'},
                {field: 'cachename', title: '名称', align: 'center',totalRowText:'合计'},
                {field: 'tableusenumber', title: '总用量', align: 'center',totalRow:true},
                {field: 'sumusenumber', title: '汇总用量', align: 'center',totalRow:true},
                {field: 'shl', title: '损耗率', align: 'center',templet: function (d) {
                        var iconClass = d.shl >= 0?"iconfont icon-jiantou-copy":"iconfont icon-jiantou-copy1";
                        return '<i class="'+iconClass+'">'+d.shl+'%</i>';
                    }}

            ]],
            response: {dataName: "rows", countName: "total"}//状态要设置为200
            ,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            ,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
                , limits: [10, 20, 30, 50]
            }
            ,done:function(res){
                if(where.cachelevel ==5 ){
                    $("[data-field='shl']").css('display','none');
                }else{
                    $("[data-field='shl']").css('display','block');
                }
                var totalRow = res.totalRow;//统计结算后余额
                var tableUseNumber = totalRow.tableusenumber;
                var sumUseNumber = totalRow.sumusenumber;
                //修改 结算后余额 统计单元格文本
                this.elem.next().find('.layui-table-total td[data-field="tableusenumber"] .layui-table-cell').text(tableUseNumber);
                this.elem.next().find('.layui-table-total td[data-field="sumusenumber"] .layui-table-cell').text(sumUseNumber);

            }
        })

    },
    /**
     * 加载级联选择框
     */
    loadCascadeOption:function(){
        NHJG.form.on('select(cascade_area)', function(data){
            //data.value 得到被选中的值
            var index = data.value.indexOf("-");
            var value = data.value.substr(0,index);
            var url = ctx+'screen/nhjg/cascade/' + value;
            $.get(url,function(res){
                if(res.code == 0 ) {
                    var data = res.data;
                    $("#cascade_zone").empty();
                    $("#cascade_zone").append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $("#cascade_zone").append(new Option(item.name, item.selectValue));
                    });
                    NHJG.form.render("select");
                }
            });

        });
        NHJG.form.on('select(cascade_zone)', function(data){
            //data.value 得到被选中的值
            var index = data.value.indexOf("-");
            var value = data.value.substr(0,index);
            var url = ctx+'screen/nhjg/cascade/' + value;
            $.get(url,function(res){
                if(res.code == 0 ) {
                    var data = res.data;
                    $("#cascade_build").empty();
                    $("#cascade_build").append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $("#cascade_build").append(new Option(item.name, item.selectValue));
                        console.log(index, item);
                    });
                    NHJG.form.render("select");
                }
            });

        });
    }
}



/**
 * 能源统计
 * @type {{rjshEcharts: NHJG.nhtj.rjshEcharts, init: NHJG.nhtj.init, sdphqsEcharts: NHJG.nhtj.sdphqsEcharts, lbxxEcharts: NHJG.nhtj.lbxxEcharts, mjshEcharts: NHJG.nhtj.mjshEcharts, sdphylEcharts: NHJG.nhtj.sdphylEcharts}}
 */
NHJG.nhtj = {
    /**
     * 统计图表
     * @param cacheId
     * @param year
     * @param important
     */
    init: function (cacheId, year,important) {
        if($("#nhtj_total").length == 0){
            //初始化加载右侧
            $("#right-panel").empty();
            $("#right-panel").load(ctx + 'screen/nhjg/nhtj', function (result) {
                NHJG.nhtj.initCountNumber(cacheId, year,important);
            });

        }else{
            NHJG.nhtj.initCountNumber(cacheId, year,important);
        }


    },
    /**
     * 统计数据
     * @param cacheId 类型
     * @param year 时间
     * @param important 是否重点建筑
     */
    initCountNumber:function(cacheId,year,important){
        if(cacheId.indexOf('rootw')==0){
            $("#areaWater").text("面积水耗");
            $("#perCapitaWater").text("人均水耗");
        }else{
            $("#areaWater").text("面积电耗");
            $("#perCapitaWater").text("人均电耗");
        }
        //统计
        NHJG.nhtj.queryTotal(cacheId, year,important);
        //图表初始化
        NHJG.initEcharts();
        //图表加载
        NHJG.nhtj.sdphylEcharts(cacheId, year,important);   // 用量

        NHJG.nhtj.lbxxEcharts(cacheId, year,important);//类别

        NHJG.nhtj.mjshEcharts(cacheId, year,important);     // 面积水耗
        // NHJG.nhtj.rjshEcharts(cacheId, year,important);     // 人均水耗
    },
    /**
     * 根据请求类型设置单位
     * @param cacheId
     */
    setUnit:function(cacheId){
        var unit = "万吨";
        if(cacheId.indexOf("roote") == 0){
            unit = "万度";
        }
        return unit;
    },
    /**
     * 总量统计
     * @param cacheId
     * @param year
     * @param important
     */
    queryTotal:function(cacheId,year,important){
        var url = ctx+"screen/nhjg/queryTotalUseNumber?cacheId="+cacheId+"&year="+year+"&important="+important;
        var gl = "环比";


        $.get(url,function(res){
            if(res.code ==0 && res.data !=null){
                var isUpDown = res.data.isUpDown;
                var iconClass = isUpDown == 1?"iconfont icon-jiantou-copy":"iconfont icon-jiantou-copy1";
                $("#nhtj_total").empty();
                $("#nhtj_total").append(' <li>\n' +
                    '       <h4>'+res.data.yearUseNumber+'</h4>\n' +
                    '        <p class="clGreen" style="display: inline">'+res.data.yearName+'用量('+NHJG.nhtj.setUnit(cacheId)+')</p><p class="clGreen" style="display: inline"></p>\n' +
                    '    </li>\n' +
                    '    <li>\n' +
                    '        <h4>'+res.data.currMonthUseNumber+'</h4>\n' +
                    '        <p class="clGreen" style="display: inline">本月用量('+NHJG.nhtj.setUnit(cacheId)+')</p><p class="clGreen" style="display: inline"></p>\n' +
                    '    </li>\n' +
                    '    <li>\n' +
                    '        <h5 class="clRed"><i class="'+iconClass+'">'+res.data.gl+'%</i></h5>\n' +
                    '        <p class="clGreen">'+gl+'</p>\n' +
                    '    </li>')
            }
        })
    },
    /**
     *  用量
     * @param cacheId
     * @param year
     * @param important
     */
    sdphylEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$useNumber == null) {
            NHJG.echarts.$useNumber = echarts.init(document.getElementById('sdphylEcharts'));
        } else {
            NHJG.echarts.$useNumber.dispose();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$useNumber);

        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: '0',
                data: ['上年度','本年度'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10 // 设置高度
            },
            grid: {
                top: '25%',
                left: '40',
                right: '10%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false,
                    }
                }
            ],
            yAxis: [
                {
                    name: '单位（万吨）',
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    }

                }
            ],
            series: [
                {
                    name: '上年度',
                    type: 'bar',
                    barMaxWidth: '10',
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [4,4,0,0],
                            color: "#00fffb"
                        }
                    },
                    data: [6600, 4100, 6200, 12000, 2000]
                },
                {
                    name: '本年度',
                    type: 'bar',
                    barMaxWidth: '10',
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [4,4,0,0],
                            color: '#f4a12b'
                        }
                    },
                    data: [6400, 4200, 6300, 10000, 3000]
                }
            ]
        };

        var cacheId_ = cacheId==null?"rootw": cacheId;
        option.yAxis[0].name = cacheId_.indexOf("rootw")==0?"单位（万吨）":"单位（万度）";

        NHJG.echarts.$useNumber.setOption(option);
        var url = ctx+"screen/nhjg/queryMonthUseNumber?cacheId="+cacheId_+"&year="+year+"&important="+important;
        $.get(url,function(res){
            if(res.code==0){
                var names = [];//月份
                var data1 = [];//上年度
                var data2 = [];//本年度
                for (var i = 0; i < res.data.length; i++) {
                    var d = res.data[i];
                    data1.push(d.useNumber2);//本年
                    data2.push(d.useNumber1);//上年
                    names.push(d.name);

                }
                var options = NHJG.echarts.$useNumber.getOption();
                options.series[0].data = data1;
                options.series[1].data = data2;
                options.xAxis[0].data = names;
                NHJG.echarts.$useNumber.setOption(options);
            }
        })

    },
    /**
     * 类别信息
     */
    lbxxEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$category == null) {
            NHJG.echarts.$category = echarts.init(document.getElementById('lbxxEcharts'));
        } else {
            NHJG.echarts.$category.dispose();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$category);

        if(cacheId.indexOf("roote") == 0){
            NHJG.echarts.$category.setOption(NHJG.nhtj.pdfEchartsOption());
        }
        else{
            NHJG.echarts.$category.setOption(NHJG.nhtj.lxEchartsOption());
        }

        /**
         * 用水获取分区年度统计
         * 用电获取配电房年度统计
         * @type {string}
         */
        var url = ctx+"screen/nhjg/querySideUseNumber?cacheId="+cacheId+"&year="+year+"&important="+important;
        $.get(url, function (d) {
            if(d.code == 0){
                var data = d.data;
                var value = [],hb=[],name = [];
                for (var i = 0; i < data.length; i++) {
                    value.push(data[i].value);
                    hb.push(data[i].hb);
                    name.push(data[i].name);
                }
                NHJG.echarts.$category.setOption({},true);
                if(cacheId.indexOf("roote") == 0) {
                    var ydoption = NHJG.nhtj.pdfEchartsOption();
                    var maxValue = Math.max.apply(null, value);
                    var maxValueArr = [];
                    for (var i = 0; i < value.length; i++) {
                        maxValueArr.push(maxValue);
                    }
                    ydoption.yAxis[1].data = value;
                    ydoption.series[0].data = value;
                    ydoption.series[1].data = maxValueArr;
                    NHJG.echarts.$category.setOption(ydoption);
                }else{
                    var ysoption = NHJG.nhtj.lxEchartsOption();
                    ysoption.series[0].data = value;
                    ysoption.series[1].data = hb;
                    ysoption.xAxis[0].data = name;
                    NHJG.echarts.$category.setOption(ysoption);
                }

            }
        })

    },
    /**
     * 用水分区
     */
    lxEchartsOption:function(){
        return  {
            tooltip: {
                trigger: 'axis',
                formatter: function(params) {
                    if (params.length > 1){
                        var str = params[0].name+"<br/>"+params[0].seriesName+":"+params[0].data+"万吨<br/>"+params[1].seriesName+":"+params[1].data+"%";
                    }else{
                        if(params[0].seriesName == "环比"){
                            var str = params[0].name+"<br/>"+params[0].seriesName+":"+params[0].data+"%";
                        }else{
                            var str = params[0].name+"<br/>"+params[0].seriesName+":"+params[0].data+"万吨";
                        }

                    }

                    return str;
                }
                // formatter: "{a} <br/>{b} : {c}万吨"
            },
            legend: {
                top: '0',
                data: ['本月用量','环比'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10 // 设置高度
            },
            grid: {
                top: '25%',
                left: '40',
                right: '10%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ["东北", "东南", "西北", "西南"],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    name: "单位（万吨）",
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#fff',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    }

                },
                {
                    name: '(%)',
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#fff',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    }
                }
            ],
            series: [
                {
                    name: '本月用量',
                    type: 'bar',
                    barMaxWidth: '10',
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },
                    itemStyle: {
                        normal: {
                            barBorderRadius: 4
                        }
                    },
                    data: [400, 300, 150, 120]
                },

                {
                    name: '环比',
                    type: 'line',
                    yAxisIndex: 1,
                    barMaxWidth: '10',
                    data: [10, 49, 12, -15]
                }
            ],
            color: ['#1ee4b1', '#fff102', '#56efff']
        };
    },
    /**
     * 用电配电房
     * @returns {{yAxis: *[], xAxis: {axisLabel: {show: boolean}, axisLine: {show: boolean}, splitLine: {show: boolean}, axisTick: {show: boolean}, type: string}, grid: {top: string, left: string, bottom: string, right: string, containLabel: boolean}, series: *[], tooltip: {formatter: (function(*): string), axisPointer: {type: string}, trigger: string}}}
     */
    pdfEchartsOption:function(){
        var salvProName =["开发区","西苑西","西苑南","总配","中心配","科技园","图书馆","荟园"];
        var salvProValue =[154,144,135,117,74,72,67,55];
        var salvProMax =[];//背景按最大值
        for (var i = 0; i < salvProValue.length; i++) {
            salvProMax.push(salvProValue[0])
        }

        return {
            grid: {
                left: '2%',
                right: '2%',
                bottom: '2%',
                top: '2%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'none'
                },
                formatter: function(params) {
                    return params[0].name  + ' : ' + params[0].value
                }
            },
            xAxis: {
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            },
            yAxis: [{
                type: 'category',
                inverse: true,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                data: salvProName
            }, {
                type: 'category',
                inverse: true,
                axisTick: 'none',
                axisLine: 'none',
                show: true,
                axisLabel: {
                    formatter: function (value) {
                        return value + "万度"
                    },
                    textStyle: {
                        color: '#ffffff',
                        fontSize: '12'
                    }
                },
                data:salvProValue

            }],
            series: [{
                name: '万度',
                type: 'bar',
                zlevel: 1,
                itemStyle: {
                    normal: {
                        barBorderRadius: 30,
                        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                            offset: 0,
                            color: 'rgb(0,238,255,1)'
                        }, {
                            offset: 1,
                            color: 'rgb(46,200,207,1)'
                        }])
                    }
                },
                barWidth: 10,
                data: salvProValue
            },
                {
                    name: '背景',
                    type: 'bar',
                    barWidth: 10,
                    barGap: '-100%',
                    data: salvProMax,
                    itemStyle: {
                        normal: {
                            color: 'rgba(24,31,68,1)',
                            barBorderRadius: 30
                        }
                    }
                }
            ]
        };
    },
    /**
     * 面积水耗
     * @param cacheId
     * @param year
     * @param important
     */
    mjshEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$area == null) {
            NHJG.echarts.$area = echarts.init(document.getElementById('mjshEcharts'));
        } else {
            NHJG.echarts.$area.dispose();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$area);
        var unit = "吨";
        if(cacheId.indexOf("roote") == 0){
            unit = "万度";
        }
        var option = {
            tooltip: {
                trigger: 'axis',
                formatter: "{a}年{b} : {c}"+NHJG.nhtj.setUnit(cacheId)+"",
            },

            grid: {
                top: '30%',
                left: '40',
                right: '10%',
                bottom: '20%'
            },

            xAxis: [
                {
                    type: 'category',
                    data: ['1', '2', '3', '4', '5'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
                        }
                    },
                    boundaryGap: false,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    name: "单位（"+unit+"）",
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)'//x线的颜色
                        }
                    }

                }
            ],
            series: [
                {
                    name: '2018',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [0, 0, 0, 0, 0],
                    itemStyle: {
                        normal: {
                            label: {
                                show: false,
                                formatter: "{c}",
                                textStyle: {
                                    color: '#fff'
                                },
                                position: 'bottom'
                            }
                        }
                    }
                }
            ],
            color: ['#00eaff']
        };
        NHJG.echarts.$area.setOption(option);

        //面积
        $.get(ctx + "screen/nhjg/nhtj/echarts/area?cacheId=" + cacheId + "&year=" + year+"&important="+important, function (result) {
                if (result.code == 0&&result.data.length > 0) {
                    var data = result.data;
                    var op = NHJG.echarts.$area.getOption();
                    var xAxisData = [];
                    var seriesData = [];
                    for (var i = 0; i < data.length; i++) {
                        xAxisData.push(data[i].name);
                        seriesData.push(data[i].value);
                    }
                    op.xAxis[0].data = xAxisData;
                    op.series[0].name = year;
                    op.series[0].data = seriesData;
                    NHJG.echarts.$area.setOption(op);
                }

            });

    },

}



/**
 * 定位
 * @type {{init: YXJK.position.init}}
 */
NHJG.position = {
    init: function (year,code, name,treeNode,cacheId,important) {

        if (cacheId.indexOf("rootw") == 0) {
            $("#dingwei_water").remove();
            var url = ctx + "screen/nhjg/waterPosition";
            $.get(url, function (result) {
                $(".contentBox.insidePageBox.pr").append(result);
                //到房屋定位框
                if(treeNode.level == 4)
                {
                    var infoUrl = ctx + "screen/nhjg/waterHousePositionInfo?cacheId=" + cacheId + "&year=" + year ;
                    $.get(infoUrl, function (res) {
                        if (res.code == 0 || res.data.length > 0) {
                            var d = res.data[0];
                            $("#waterPosition").empty();
                            var html = '<h2>' + d.name + '</h2>\n' +
                                '            <div>\n' +
                                '                <div class="lineBlue mh15"></div>\n' +
                                '                <p>开户人：<span>' + d.accountName + '</span></p>\n' +
                                '                <p>开户日期：<span>' + d.openDate + '</span></p>\n' +
                                '                <p>联系电话：<span>' + d.phoneNumber + '</span></p>\n' +
                                '                <p>账户类型：<span>' + d.categoryName + '</span></p>\n' +
                                '                <p>房屋类型：<span>' + d.houseTypeName + '</span></p>\n' +
                                '                <p>房屋面积：<span>' + d.Usearea + 'm²</span></p>\n' +
                                '                <div class="lineBlue mh15"></div>\n' +
                                '                <p>' + year + '年总用水量：<span>' + d.yearUseNumber + '吨</span></p>\n' +
                                '                <p>本月用水量：<span>' + d.monthTableUseNumber + '吨</span></p>\n' +
                                '                <p>上月用水量：<span>' + d.perMonthTableUseNumber + '吨</span></p>\n' +
                                '                <p>所辖水表：<span>' + d.waterCount + '个</span></p>\n' +
                                '                <div class="lineBlue mh15"></div>\n';
                            for (var i = 0; i <d.tableId.split(',').length ; i++) {
                                var r = d.tableUseNumber.split(',')[i];
                                var j = d.tableId.split(',')[i];

                                if(!isNaN(r)){
                                    r=r+'吨';
                                }
                                if(r==undefined){
                                    r = "当前时间未获取到数据"
                                }
                                var str = '<p>'+j+':<span>'+r+'</span></p>';
                                html+= str;
                            }
                            html+='</div>'
                            $("#waterPosition").append(html);
                        }
                    });

                }else {
                    //楼栋，片区，区域用水定位框
                    var infoUrl = ctx + "screen/nhjg/waterPositionInfo?cacheId=" + cacheId + "&year=" + year + "&important=" + important;
                    $.get(infoUrl, function (res) {
                        if (res.code == 0 || res.data.length > 0) {
                            var d = res.data[0];
                            $("#waterPosition").empty();
                            $("#waterPosition").append('<h2>' + d.name + '</h2>\n' +
                                '            <div>\n' +
                                '                <div class="lineBlue mh15"></div>\n' +
                                '                <p>' + year + '年总用水量：<span>' + d.yearUseNumber + '吨</span></p>\n' +
                                '                <p>本月用水量：<span>' + d.monthTableUseNumber + '吨</span></p>\n' +
                                '                <p>上月用水量：<span>' + d.perMonthTableUseNumber + '吨</span></p>\n' +
                                '                <p>所辖水表：<span>' + d.waterCount + '个</span></p>\n' +
                                '            </div>\n')
                        }
                    });
                }

            })
        } else {
            $("#dingwei").remove();
            var dwUrl = NHJG.com.$nhprefix + "/position?year=" + year + "&code=" + code + "&name=" + name + "&cacheId=" + cacheId + "&important=" + important;
            $.get(dwUrl, function (result) {
                $("#dingwei").empty();
                $(".contentBox.insidePageBox.pr").append(result);
                // $("#yxjk_detail_btn").hide();

            });

        }

    }
};

