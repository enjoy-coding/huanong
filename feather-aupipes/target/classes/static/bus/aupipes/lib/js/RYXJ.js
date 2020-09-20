/**
 * 人员巡检
 * @type {{init: ModelManager.RYXJ.init}}
 */
ModelManager["RYXJ"] = {
    $: layui.jquery,
    element: layui.element,
    laypage: layui.laypage,
    table: layui.table,
    tree: layui.tree,
    laydate: layui.laydate
};
var RYXJ = ModelManager["RYXJ"];

RYXJ.com = {
    $prefix: ctx + "screen/ryxj",
    $target: "ryxj",
    $content: $(".contentBox"),
    $left: $("#left-panel"),
    $right: $("#right-panel"),
    $bottom: $("#bottom-panel"),
    $bottomLayer: $(".bottomLayer"),
};

RYXJ.GK = {
    $xjgkEcharts: "xjgkEcharts", //巡检概况图
    $ryxjTable: "ryxjTable",  //日常巡检表格 在巡
    $ryxjEndTable: "ryxjEndTable",  //日常巡检表格 已巡
    $ryxjStartTable: "ryxjStartTable",  //日常巡检表格 未巡
    $wxjlTable: "wxjlTable",  //维修记录表格
};
RYXJ.XQ = {
    $xjlbTable: "xjlbTable", //巡检详情表格
    $ycxqNum: "ycxqNum", //异常详情分页
    $curNum: "curNum",
    $totalNum: "totalNum"
};
RYXJ.POP = {
    $xjjlLayerBox: "xjjlLayerBox",//巡检记录
    $xjtjLayerBox: "xjtjLayerBox",
    $wxjlLayerBox: "wxjlLayerBox",
    $addInpectBox: "addInpectBox",//巡检新增弹框
    $inspectFormBox: "inspectFormBox",//巡检表弹框
};

RYXJ.api = {
    taskList: ctx + "aupipes/inspect/list",
    taskAdd: ctx + "aupipes/inspect/add",
    taskDel: ctx + "aupipes/inspect/remove",
    // taskList: ctx + "aupipes/task/patroList",
    detailList:ctx + "aupipes/detail/list",
    serviceList:ctx + "aupipes/inspectservice/list",
    coordinateList:ctx + "aupipes/coordinate/list",
    coordinateGeoJson:ctx + "aupipes/coordinate/geojson",
    // situation:ctx + "aupipes/task/patroListCount",
    situation:ctx + "aupipes/inspect/selectAupInspectCountByStatus",
    // situation:ctx + "api/screen/index/inspectNum",
    inspectRecord:ctx + "aupipes/dailyInspect/inspectRecord",
    deviceProportion:ctx + "aupipes/dailyInspect/deviceProportion",
    areaProportion:ctx + "aupipes/dailyInspect/areaProportion",
    exceptionNum:ctx + "aupipes/dailyInspect/exceptionNum",
    personnelStatistics:ctx + "aupipes/dailyInspect/personnelStatistics",
    exportUrl: ctx + "aupipes/inspect/customExport",
    detailExportUrl: ctx + "aupipes/detail/customExport",

    wxjlList: ctx + "aupipes/repair/list",
    wxjlAdd: ctx + "aupipes/repair/add",
    wxjlDel: ctx + "aupipes/repair/remove",
    wxjlExportUrl: ctx + "aupipes/repair/export",
    faultType: ctx + "system/dict/data/api/list",
    uploadFile: ctx + "aupipes/info/add",
    fileList: ctx + "aupipes/info/list",
    delFile: ctx + "aupipes/info/remove",

    pipeList: ctx + "aupipes/pipeline/list",
    pipeAddList: ctx + "aupipes/pipeline/addList",
    pipeDel: ctx + "aupipes/pipeline/remove",


}


/*****************************************初始化代码start**************************************************/

var ryxjInsIng;
var ryxjInsNotyet;
var ryxjInsAlready;
RYXJ.init = function (currMenuCode, currMenuValue) {
    //改变全局搜索的默认值
    objectType = 'subject';
    $(".searchLabel").find("input").eq(2)[0].checked = true;
    var layerType = currModelName.toLowerCase();
    // initTree(jsonObj, layerType);
    // 清除面板
    menuChangeInit();

    //加载巡检概况
    RYXJ.com.$left.load(RYXJ.com.$prefix + "/xjgk", function (result) {

        //清除其他图层
        selectedLinePipes(layerCfg.ryxj.all);
        RYXJ.addCheckListener();

        layui.form.render();//要渲染form，不然下拉框出不来
        RYXJ.initDate();
        RYXJ.xjgkEcharts();
        ryxjInsIng = RYXJ.ryxjTable(1);//在巡
        ryxjInsNotyet = RYXJ.ryxjTable(0);//未巡
        ryxjInsAlready = RYXJ.ryxjTable(2);//已巡
        wxjlTableIns = RYXJ.wxjlTable();//初始化渲染维修列表
        //layerTree.initTree(jsonObj, "ryxj");

        if(currMenuValue == 2){
            $("#wx").click();
        }
    });

    RYXJ.leftBoxShow();

    //加载巡检记录弹窗
    $.ajax({
        url: RYXJ.com.$prefix + "/xjjl",
        type: 'post',
        async: false,
        success: function (data) {
            $(".contentBox.insidePageBox.pr").append(data);
            // RYXJ.zbfxEcharts();  // 设备占比分析
            // RYXJ.qyzbEcharts();  // 设备占比分析
            // RYXJ.slfxEcharts();  // 异常数量分析
            // RYXJ.rwtjEcharts();  // 人员统计
        }
    })
    $.ajax({
        url: RYXJ.com.$prefix + "/addInspect",
        type: 'post',
        async: false,
        success: function (data) {
            $(".contentBox.insidePageBox.pr").append(data);
        }
    })
    $.ajax({
        // url: RYXJ.com.$prefix + "/addInspect",
        url: RYXJ.com.$prefix + "/inspectForm",
        type: 'post',
        async: false,
        success: function (data) {
            $(".contentBox.insidePageBox.pr").append(data);
        }
    })
    $.ajax({
        url: RYXJ.com.$prefix + "/wxjl",
        type: 'post',
        async: false,
        success: function (data) {
            $(".contentBox.insidePageBox.pr").append(data);
            // RYXJ.zbfxEcharts();  // 设备占比分析
            // RYXJ.qyzbEcharts();  // 设备占比分析
            // RYXJ.slfxEcharts();  // 异常数量分析
            // RYXJ.rwtjEcharts();  // 人员统计
        }
    })

}

//给ul设置监听
var currentWindow = "xj";
RYXJ.addCheckListener = function(){
    var list = document.getElementById("left_ul");
    list.addEventListener('click', function (event) {
        event = event || window.event;
        var target = event.target || event.srcElement;
        switch (target.id) {
            case "xj" :
                if (currentWindow == "wx"){
                    RYXJ.rightBoxHide();
                }
                currentWindow = "xj";
                break;
            case "wx" :
                if (currentWindow == "xj"){
                    RYXJ.rightBoxHide();
                }
                if(trackPlayback.isWorking){//清除轨迹
                    trackPlayback.isWorking = false;
                    trackPlayback.stop();
                    var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
                    layerTree.setAlpha(node,1);
                }
                currentWindow = "wx";
                break;
        }
    }, false);
}

/*****************************************初始化代码end**************************************************/

var ChartsName = [];

// 窗口重新初始化
window.onresize = function () {
    for (var i = 0; i < ChartsName.length; i++) {
        ChartsName[i].resize();
    }
};

RYXJ.initDate = function () {
    RYXJ.laydate.render({
        elem: '#selectTime'
        , type: 'date'
        , range: '到'
        , format: 'yyyy-MM-dd'
    });
}


/*****************************************巡检概况和巡检记录start**************************************************/

// 巡检概况图表
var xjgkEchartsIns;
RYXJ.xjgkEcharts = function () {
    xjgkEchartsIns = echarts.init(document.getElementById(RYXJ.GK.$xjgkEcharts));
    ChartsName.push(xjgkEchartsIns);
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            y: 'center',
            x: '0',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: '13',
                fontWeight: 'normal',
            },
            itemWidth: 20,  // 设置宽度
            itemHeight: 10, // 设置高度
            data: ['未巡', '在巡', '已巡']
            // data: ['正常', '异常']
        },
        series: [{
            name: '巡检概况',
            type: 'pie',
            radius: ['55%', '80%'],
            center: ['60%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center',
                },
                emphasis: {
                    show: true,
                    formatter: '{b|{b}} \n{d|{c}}',  //label 的内容
                    rich: {
                        d: {
                            fontSize: '18',
                            fontFamily: '微软雅黑',
                        }
                    },
                    textStyle: {
                        fontSize: '16',
                        fontWeight: 'normal',
                        color: '#fff'
                    },
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [
                {
                    value: 0,
                    name: '未巡'
                },
                {
                    value: 0,
                    name: '在巡'
                },
                {
                    value: 0,
                    name: '已巡'
                }
            ]
        }],
        color: ['green', '#3ed6d7', '#f3842b']
    };
    xjgkEchartsIns.setOption(option);
    RYXJ.xjjlSituation();

    var index = 0;
    //默认显示第一个
    xjgkEchartsIns.dispatchAction({
        type: 'highlight',
        seriesIndex: 0,
        dataIndex: 0
    });
    //设置默认选中高亮部分
    xjgkEchartsIns.on('mouseover', function (e) {
        if (e.dataIndex != index) {
            xjgkEchartsIns.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: index
            });
        }
    });
    xjgkEchartsIns.on('mouseout', function (e) {
        index = e.dataIndex;
        xjgkEchartsIns.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: e.dataIndex
        });
    });
}

RYXJ.xjjlSituation = function(){
    // var startTime, endTime, time = $("#selectTime").val().split("到");
    var searchValue = $("#xjSearchValue").val()

    // if(time.length > 1){
    //     startTime = time[0].trim();
    //     endTime = time[1].trim();
    // }else{
    //     startTime = "";
    //     endTime = "";
    // }
    var keyword =
    //访问网络数据
    $.ajax({
        // url: RYXJ.api.situation+"?startdate="+startTime+"&enddate="+endTime,
        url: RYXJ.api.situation,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                var option = xjgkEchartsIns.getOption();
                option.series[0].data = data.data;
                xjgkEchartsIns.setOption(option);
            }
        }
    })
    if(ryxjInsIng == undefined || ryxjInsNotyet == undefined || ryxjInsAlready == undefined){
        return;
    }
    ryxjInsIng.reload({
        where:{
            status: "0",
            "params[searchValue]":searchValue
            // startdate: startTime,
            // enddate: endTime
        }
    });

    ryxjInsNotyet.reload({
        where:{
            status: "1",
            "params[searchValue]":searchValue
            // startdate: startTime,
            // enddate: endTime
        }
    });

    ryxjInsAlready.reload({
        where:{
            status: "2",
            "params[searchValue]":searchValue
            // startdate: startTime,
            // enddate: endTime
        }
    });
}

//人员巡检列表
var ryxjTableTaskId;
RYXJ.ryxjTable = function (state) {
    var id;
    if(state == 1) {
        id = RYXJ.GK.$ryxjTable;
    }else if(state == 2){
        id = RYXJ.GK.$ryxjEndTable;
    }else{
        id = RYXJ.GK.$ryxjStartTable;
    }
    var width = ($('.ryxjTabe').width()) / 4;
    var tableHeight = $(".ryxjTabe").height();

    // 人员巡检
    var ryxjTableIns = RYXJ.table.render({
        elem: "#" + id
        , height: tableHeight
        , skin: 'nob'
        , method: 'post'
        , url: RYXJ.api.taskList
        , request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , where :{
            inspectStatus: state
        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.rows
            }
        }
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        , cols: [[ //标题栏
            {title: '序号', align: 'center', width: width, type: 'numbers'},
            // {title: '序号', align: 'center', width: width, field: 'id'},
            {field: 'inspectName', title: '名称', align: 'center'},
            // {field: 'taskWorker', title: '人员', align: 'center', width: width}
            {field: 'inspectWorker', title: '人员', align: 'center', width: width}
        ]]
        , data: [{
            "taskId": "1"
            , "taskName": "华农南苑"
            , "taskWorker": "李云迪"
        },]
        , page: {
            layout: ['prev', 'page', 'next', 'count'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            // ,groups: 1 //只显示 1 个连续页码
            // ,first: false //不显示首页
            // ,last: false //不显示尾页
        }
    });

    //监听单元格事件
    RYXJ.table.on('row(clickEvent)', function (obj) {

        $("#ycxqSwiper").empty();
        var data = obj.data;
        if(!$.common.isEmpty(ryxjTableTaskId) && ryxjTableTaskId != data.id && trackPlayback.isWorking){
            RYXJ.trackStop();
        }
        ryxjTableTaskId = data.id;
        RYXJ.rightBoxShow(data.id);
    });

    return ryxjTableIns;
}

var inspectTableIns;
//  巡检记录
RYXJ.xjjlLayerBox = function () {
    layer.open({
        type: 1,
        title: '巡检管理',
        area: ['100%', '93.2%'],
        offset: 'b',
        // content: $('.xjjlLayerBox'),
        content: $('#' + RYXJ.POP.$xjjlLayerBox),
        skin: 'layer-style',
        id: 'Box2'
    });
    $("#inspectTime").val("");
    $("#inspectName").val("");
    $(".layui-unselect").val("");
    // $("#inspectState option[value='']").attr("selected", true);
    // layui.form.render();
    var tatleHeight = $(".xjjlTable").height()

    RYXJ.laydate.render({
        elem: '#inspectTime'
        , type: 'date'
        , range: '到'
        , format: 'yyyy-MM-dd'
    });

    inspectTableIns = RYXJ.table.render({
        elem: '#xjjlTable2'
        , height: tatleHeight
        // ,width:width
        , skin: 'nob'
        , method: 'post'
        // , url: RYXJ.api.inspectRecord
        , url: RYXJ.api.taskList
        , request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.rows
            }
        }
        , limit: 15
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        , cols: [[ //标题栏
            {type: 'checkbox'},
            {field: 'serial', title: '序号', align: 'center',templet : function(d){
                    return d.LAY_TABLE_INDEX+1;
                }},
            {field: 'inspectName', title: '名称', align: 'center',},
            {field: 'inspectArea', title: '区域', align: 'center',},
            {field: 'inspectWorker', title: '巡检人员', align: 'center'},
            // {field: 'distributeTime', title: '下发时间', align: 'center'},
            {field: 'distributeWorker', title: '下发人员', align: 'center'},
            // {field: 'startTime', title: '开始时间', align: 'center'},
            {field: 'endTime', title: '结束时间', align: 'center'},
            {field: 'inspectStatus', title: '处理状态', align: 'center', templet : function(d){
                    if(d.inspectStatus==0){
                        return '未巡';
                    }
                    else if(d.inspectStatus==2){
                        return '已巡';
                    }else{
                        return '在巡';
                    }
                }}
        ]]
        ,done:function(res){
            tdTitle();
        }
        , data: [{
        }]
        , page: {
            layout: ['prev', 'page', 'next'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            // , groups: 1 //只显示 1 个连续页码
            // , first: false //不显示首页
            // , last: false //不显示尾页

        }
    });
}

//巡检概况查询
RYXJ.xjSearch = function(){

}

//新增巡检记录
var addInspectIndex;
RYXJ.addInspect = function(){

    addInspectIndex = layer.open({
        type: 1,
        title: '新增巡检任务',
        area: ['40%', '80%'],
        // area: ['693px', '730px'],
        offset: 'auto',
        content: $('#' + RYXJ.POP.$addInpectBox),
        skin: 'layer-style',
        id: 'Box4'
    });

    inspectAddInit();
}

RYXJ.delInspect = function(){
    var checkStatus = RYXJ.table.checkStatus('xjjlTable2'); //idTest 即为基础参数 id 对应的值
    if(checkStatus.data.length == 0){
        layer.msg("请至少选择一条记录", {icon: 6});
        return;
    }

    var ids = "";
    for(i = 0; i < checkStatus.data.length; i++){
        var item = checkStatus.data[i];
        if(i == 0){
            ids += item.id;
        }else{
            ids += ',' + item.id;
        }
    }
    console.log(ids);
    console.log(checkStatus.data) //获取选中行的数据
    console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件

    layer.confirm('确定删除选中的巡检任务么？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: RYXJ.api.taskDel,
            data: {"ids":ids},
            contentType:'application/x-www-form-urlencoded',
            dataType: "json",
            success: function(result){
                layer.alert(result.msg)
                if(result.code == 0){
                    inspectTableIns.reload();
                    // RYXJ.changeState();
                    // RYXJ.updateFileDiv();
                }
            }
        });
    });
}

//巡检记录导出excel
RYXJ.exportExcel = function() {
    var msg = layer.msg('正在导出数据，请稍后...', {time: false});

    var startTime, endTime, time = $("#inspectTime").val().split("到");
    // alert($("#inspectTime").val());
    if(time.length > 1){
        startTime = time[0].trim();
        endTime = time[1].trim();
    }else{
        startTime = "";
        endTime = "";
    }
    var data = {
        username: $("#inspectName").val(),
        status: $("#inspectState option:selected").val(),
        startdate: startTime,
        enddate: endTime,
    }
    $.post(RYXJ.api.exportUrl, data, function(result) {
        layer.close(msg);//手动关闭
        layer.msg("导出成功！", {time: 1000});
        if (result.code == 0) {
            window.location.href = ctx + "system/common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
        } else if (result.code == 500) {
            layer.msg(result.msg)
        } else {
            layer.msg(result.msg)
        }
        // $.modal.closeLoading();
    });
}

RYXJ.exportExcel2 = function(){

    var checkStatus = RYXJ.table.checkStatus('xjjlTable2'); //idTest 即为基础参数 id 对应的值
    if(checkStatus.data.length == 0){
        layer.msg("请至少选择一条记录", {icon: 6});
        return;
    }

    var ids = "";
    for(i = 0; i < checkStatus.data.length; i++){
        var item = checkStatus.data[i];
        if(i == 0){
            ids += item.id;
        }else{
            ids += ',' + item.id;
        }
    }

    var msg = layer.msg('正在导出数据，请稍后...', {time: false});

    $.post(RYXJ.api.exportUrl, {"ids":ids}, function(result) {
        layer.close(msg);//手动关闭
        layer.msg("导出成功！", {time: 1000});
        if (result.code == 0) {
            window.location.href = ctx + "system/common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
        } else if (result.code == 500) {
            layer.msg(result.msg)
        } else {
            layer.msg(result.msg)
        }
    });
}

/*****************************************巡检概况和巡检记录end**************************************************/


/*****************************************巡检详情start**************************************************/
//字符过长时，鼠标移上去可以看到内容的效果
function tdTitle(){
    $('th').each(function(index,element){
        $(element).attr('title',$(element).text());
    });
    $('td').each(function(index,element){
        $(element).attr('title',$(element).text());
    });
};

//轨迹坐标列表，0605改为巡检详情列表
RYXJ.xjxq = function (taskId) {

    var id = RYXJ.XQ.$xjlbTable;
    var tableHeight = $(".xjlbTabe").height()
    var width = ($('.xjlbTable').width()) / 5;

    // parseFloat(taskId)
    // 巡检记录
    RYXJ.table.render({
        elem: '#' + id
        , height: tableHeight
        , skin: 'nob'
        , method: 'post'
        // , url: RYXJ.api.coordinateList
        , url: RYXJ.api.detailList
        , request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , where :{
            taskId:taskId
        }
        , parseData: function (res) {

            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.rows
            }
        }
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        , cols: [[ //标题栏
            {title: '序号', align: 'center', type: 'numbers'},
            /*{field: 'longitude', title: '坐标', align: 'center', templet:function(d){
                    return d.longitude +","+d.latitude;
                }},
            {field: 'createTime', title: '时间', align: 'center'}*/
            {field: 'serviceAddress', title: '巡检地点', align: 'center'},
            {field: 'serviceStatusText', title: '设备状态', align: 'center'}
            /*{field: 'operating', title: '操作', align: 'center', toolbar: '#gjhf'}*/
        ]]
        ,done:function(res){
            if(res.code == 0) {
                if (res.count > 0) {
                    var serviceId = res.data[0].id;
                    RYXJ.initPicList(serviceId);
                }
            }
            tdTitle();
        }
        , data: [{
        },]
        // , page: {
        //     layout: ['prev', 'page', 'next'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            // , groups: 1 //只显示 1 个连续页码
            // , first: false //不显示首页
            // , last: false //不显示尾页
        // }
    });

    //监听单元格事件
    RYXJ.table.on('row(clickDetailEvent)', function (obj) {
        var data = obj.data;

        RYXJ.initPicList(data.id);
        RYXJ.inspectForm(data);
    });


    $.post(RYXJ.api.coordinateGeoJson, {taskId:taskId%5}, function(result) {
        if(result.code == 0){
            trackPlayback.historyGeoData = result.data;
        }
    });
}

//弹出巡检表
RYXJ.inspectForm = function(data){
    console.log(data);

    $("#inspectDetailId").val(data.id);
    $("#inspectWorker").val(data.inspectWorker);
    $("#inspectFormTime").val(data.createTime);
    $("#serviceTypeId").val(data.serviceType);
    $("#serviceAddress").val(data.serviceAddress);
    $("#serviceStatusText").val(data.serviceStatusText);
    $("#description").val(data.description);

    $("#serviceFormList").empty();

    $.post(RYXJ.api.serviceList, {detailId:data.id}, function(result) {
        if(result.code == 0){

            for(i = 0; i < result.total; i++){
                var item = result.rows[i];
                var num = i + 1;
                $("#serviceFormList").append("            <div class=\"layui-col-md6 layui-col-sm6 layui-col-xs6\">\n" +
                    "                <div class=\"layui-form-item\">\n" +
                    "                    <label class=\"layui-form-label\">"+num+".名称：</label>\n" +
                    "                    <div class=\"layui-input-block\">\n" +
                    "                        <span class=\"name\">"+item.serviceName+"</span>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"layui-col-md6 layui-col-sm6 layui-col-xs6\">\n" +
                    "                <div class=\"layui-form-item\">\n" +
                    "                    <label class=\"layui-form-label\">设备状态：</label>\n" +
                    "                    <div class=\"layui-input-block\">\n" +
                    "                        <span class=\"name\">"+item.serviceStatus+"</span>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"layui-col-md12 layui-col-sm12\">\n" +
                    "                <div class=\"layui-form-item\">\n" +
                    "                    <label class=\"layui-form-label\">设备情况：</label>\n" +
                    "                    <div class=\"layui-input-block\">\n" +
                    "                        <textarea name=\"desc\" placeholder=\"请输入内容\" class=\"layui-textarea\" style=\"min-height:40px;\">"+item.serviceSituation+"</textarea>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>")
            }
        }
    });

    layer.open({
        type: 1,
        title: '巡检记录表',
        area: ['40%', '80%'],
        // area: ['800px', '800px'],
        offset: 'auto',
        content: $('#' + RYXJ.POP.$inspectFormBox),
        skin: 'layer-style',
        id: 'Box3'
    });
}

RYXJ.initPicList = function(serviceId){
    $("#ycxqSwiper").empty();
    //获取多媒体内容
    $.ajax({
        type: "POST",
        url: RYXJ.api.fileList,
        data: {"fileCode":serviceId},
        contentType:'application/x-www-form-urlencoded',
        dataType: "json",
        success: function(result){
            if(result.code == 0){
                RYXJ.renderContent(result.rows);
            }
        }
    });
}

//添加图片
RYXJ.addInternalImg = function(filePath){
    var url = "";
    // var url = filePath + "?text=图片加载中";
    if(ctx == "/"){
        url = filePath + "?text=图片加载中";
    }else{
        url = ctx + filePath + "?text=图片加载中";
    }

    var ulDiv = $("#ycxqSwiper");
    // ulDiv.append("<div class=\"swiper-slide\"><img src=\"../../../img/1.png\"></div>");
    /*ulDiv.append("<div class=\"swiper-slide\"><img onclick=\"showImg("+"#picList"+")\" src="+url+"></div>");*/
    ulDiv.append("                <li class=\"swiper-slide\">\n" +
        "                    <img class=\"img\" onclick=\"RYXJ.middleBoxShow('#ycxqSwiper')\" layer-src="+url+" src="+url+" alt=\"\">\n" +
        "                </li>");

}

RYXJ.renderContent = function(picList) {
    // document.getElementById('ycxqSwiper').innerHTML = function () {
    //
    //     var arr = [];
    //     for(i = 0; i < picList.length; i++){
    //         var url = picList[i].filePath;
    //
    //         arr.push("                <li class=\"swiper-slide\">\n" +
    //             "                    <img class=\"img\" onclick=\"RYXJ.middleBoxShow('#ycxqSwiper')\" layer-src="+ctx+url+" src="+ctx+url+" alt=\"\">\n" +
    //             "                </li>");
    //     }
    //     return arr.join('');
    // }();

    for(i = 0; i < picList.length; i++){
        RYXJ.addInternalImg(picList[i].filePath);
    }

    var mySwiper = new Swiper('.swiper-container', {
//			spaceBetween: 30,
//         loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    })
}

// 左侧内容显示
RYXJ.leftBoxShow = function() {
    if ($('.leftBtn').hasClass('active')) {
        $('.leftBtn').removeClass('active');
        $(".leftBox").animate({left:"0"});
    }
}

RYXJ.middleBoxShow = function (id) {
//调用示例
    layer.photos({
        photos: id
        ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });

    // $("body").on("click",id+" img",function(e){
    //     layer.photos({
    //         photos: { "data": [{"src": e.target.src}] }
    //     });
    // });
    // $(id).viewer({
    //     url: 'data-original',
    // });
}

// 右侧内容显示
RYXJ.rightBoxShow = function (taskId) {
    var content = RYXJ.com.$right.html();
    if(content == null || content.length == 0){
        RYXJ.com.$right.load(RYXJ.com.$prefix + "/xjxq", function (result) {
            RYXJ.xjxq(taskId);
        });
    }else{
        RYXJ.xjxq(taskId);
    }

    $('.rightBtn').removeClass('active');
    $(".rightBox").removeClass('rightHide');
    $(".rightBox").animate({right: "0"});
}

// 右侧内容隐藏
RYXJ.rightBoxHide = function () {
    $('.rightBtn').addClass('active');
    $(".rightBox").removeClass('rightHide');
    $(".rightBox").animate({right: "-18vw"});
    RYXJ.com.$right.html("");
    if(pickFeature.isWorking){//如果开启了管线拾取，需要关闭
        setTimeout(function(){
            RYXJ.stopFeaturePick();
            }, 500);
    }
}
/*****************************************巡检详情end**************************************************/

/*****************************************统计模块start，现在没用**************************************************/
//巡检统计
RYXJ.xjtjLayerBox = function () {
    layer.open({
        type: 1,
        title: '巡检统计',
        area: ['100%', '93.2%'],
        offset: 'b',
        content: $('#' + RYXJ.POP.$xjtjLayerBox),
        skin: 'layer-style',
        id: 'Box2'
    });
    // RYXJ.zbfxEchartsGetData(); // 设备占比分析
    // RYXJ.qyzbEchartsGetData(); // 设备占比分析
    RYXJ.slfxEchartsGetData();  // 异常数量分析
    RYXJ.rwtjEchartsGetData();  // 人员统计
}

var zbfxEcharts;
//  设备占比分析
RYXJ.zbfxEcharts = function () {
    zbfxEcharts = echarts.init(document.getElementById('zbfxEcharts'));
    ChartsName.push(zbfxEcharts);
    var option = {
        title: {
            top: '10%',
            text: '设备占比分析',
            textStyle: {
                color: '#fff',
                fontSize: '18',
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            y: 'center',
            x: '0',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: '13',
                fontWeight: 'normal',
            },
            itemWidth: 20,  // 设置宽度
            itemHeight: 10, // 设置高度
            data: ['监控', '监测', '表计', '电闸', '路灯']
        },
        series: [{
            name: '预警处置',
            type: 'pie',
            radius: ['50%', '65%'],
            center: ['60%', '50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center',
                },
                emphasis: {
                    show: true,
                    formatter: '{b|{b}} \n\n{d|{c}}',  //label 的内容
                    rich: {
                        d: {
                            fontSize: '22',
                            fontFamily: '微软雅黑',
                        }
                    },
                    textStyle: {
                        fontSize: '16',
                        fontWeight: 'normal',
                        color: '#fff'
                    },
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [
                {
                    value: 203,
                    name: '监控'
                },
                {
                    value: 178,
                    name: '监测'
                },
                {
                    value: 260,
                    name: '表计'
                },
                {
                    value: 378,
                    name: '电闸'
                },
                {
                    value: 478,
                    name: '路灯'
                }
            ]
        }],
        color: ['#eff718', '#37bf6b', '#4e8ef0', '#e4694a', '#fba43d']
    };

    //访问网络数据
    $.ajax({
        url: RYXJ.api.deviceProportion,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                option.series[0].data = data.data.list;
                zbfxEcharts.setOption(option);
            }
        }
    })
    // zbfxEcharts.setOption(option);
    // console.log(option.series[0].data);

    var index = 0;
    //默认显示第一个
    zbfxEcharts.dispatchAction({
        type: 'highlight',
        seriesIndex: 0,
        dataIndex: 0
    });
    //设置默认选中高亮部分
    zbfxEcharts.on('mouseover', function (e) {
        if (e.dataIndex != index) {
            zbfxEcharts.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: index
            });
        }
    });
    zbfxEcharts.on('mouseout', function (e) {
        index = e.dataIndex;
        zbfxEcharts.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: e.dataIndex
        });
    });
}

RYXJ.zbfxEchartsGetData = function () {
    // var zbfxEcharts = echarts.init(document.getElementById('zbfxEcharts'));

    var option = zbfxEcharts.getOption();
    $.ajax({
        url: RYXJ.api.deviceProportion,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                option.series[0].data = data.data.list;
                /*setTimeout(() => {
                    zbfxEcharts.setOption(option);
                }, 500);*/

            }
        }
    })
}

var qyzbEcharts;
// 区域占比分析
RYXJ.qyzbEcharts = function () {
    qyzbEcharts = echarts.init(document.getElementById('qyzbEcharts'));
    ChartsName.push(qyzbEcharts);
    var option = {
        title: {
            top: '10%',
            text: '区域占比分析',
            textStyle: {
                color: '#fff',
                fontSize: '18',
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            y: 'center',
            orient: 'vertical',
            left: '20px',
            itemWidth: 20,  // 设置宽度
            itemHeight: 10, // 设置高度
            itemGap: 10, // 设置间距
            data: ['梦泽园', '荟园', '宝积苑', '傅园'],
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: '12',
                fontWeight: 'normal'
            },
        },
        series: [
            {
                name: '景区数量',
                type: 'pie',
                radius: '65%',
                center: ['60%', '50%'],
                data: [
                    {value: 30, name: '梦泽园'},
                    {value: 20, name: '荟园'},
                    {value: 60, name: '宝积苑'},
                    {value: 100, name: '傅园'},

                ],
                label: {
                    normal: {
                        show: false,
                        position: 'inner',
                        formatter: function (data) { // 设置圆饼图中间文字排版
                            return data.value + "\n" + "用户数"
                        }
                    },
                    emphasis: {
                        show: true, //文字至于中间时，这里需为true
                        textStyle: { //设置文字样式
                            fontSize: '18',
                            color: "#fff"
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ],
        color: ['#e5684a', '#4e8ef0', '#37bf6b', '#fba43d']
    };

    qyzbEcharts.setOption(option);
    var index = 0;
    //设置默认选中高亮部分
    qyzbEcharts.dispatchAction({type: 'highlight', seriesIndex: 0, dataIndex: 0});
    // 当鼠标移入时，如果不是第一项，则把当前项置为选中，如果是第一项，则设置第一项为当前项
    qyzbEcharts.on('mouseover', function (e) {
        qyzbEcharts.dispatchAction({type: 'downplay', seriesIndex: 0, dataIndex: 0});
        if (e.dataIndex != index) {
            qyzbEcharts.dispatchAction({type: 'downplay', seriesIndex: 0, dataIndex: index});
        }
        if (e.dataIndex == 0) {
            qyzbEcharts.dispatchAction({type: 'highlight', seriesIndex: 0, dataIndex: e.dataIndex});
        }
    });

    //当鼠标离开时，把当前项置为选中
    qyzbEcharts.on('mouseout', function (e) {
        index = e.dataIndex;
        qyzbEcharts.dispatchAction({type: 'highlight', seriesIndex: 0, dataIndex: e.dataIndex});
    });
}

RYXJ.qyzbEchartsGetData = function () {
    var option = qyzbEcharts.getOption();

    //访问网络数据
    $.ajax({
        url: RYXJ.api.areaProportion,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                option.series[0].data = data.data.list;
                qyzbEcharts.setOption(option);
            }
        }
    })
}

var slfxEcharts;
// 异常数量分析
RYXJ.slfxEcharts = function () {
    slfxEcharts = echarts.init(document.getElementById('slfxEcharts'));
    ChartsName.push(slfxEcharts);
    var option = {
        title: {
            top: '10%',
            text: '异常数量分析',
            textStyle: {
                color: '#fff',
                fontSize: '18',
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            top: '20%',
            left: '40',
            right: '0',
            bottom: '20%'
        },
        xAxis: [
            {
                type: 'category',
                data: ['0:00', '4:00', '8:00', '20:00', '0:00'],
                axisLine: {
                    lineStyle: {
                        type: 'dotted',
                        color: '#06ebeb',//x线的颜色
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
                    show: false,
                },
            }
        ],
        yAxis: [
            {
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
                },

            }
        ],
        series: [
            {
                name: '2018',
                type: 'line',
                barMaxWidth: '20',
                data: [12, 9, 30, 45, 38],
                // stack: 'a',
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#ede360'
                        }, {
                            offset: 1,
                            color: 'rgba(237,237,96,.4)'
                        }])
                    }
                },
            },
        ],
        color: ['#ede360']
    };

    slfxEcharts.setOption(option);

}

RYXJ.slfxEchartsGetData = function () {
    var option = slfxEcharts.getOption();
    //访问网络数据
    $.ajax({
        url: RYXJ.api.exceptionNum,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                option.series[0].data = data.data.list;
                slfxEcharts.setOption(option);
            }
        }
    })
}

var rwtjEcharts;
// 人员统计
RYXJ.rwtjEcharts = function () {
    rwtjEcharts = echarts.init(document.getElementById('rwtjEcharts'));
    ChartsName.push(rwtjEcharts);
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            top: '10',
            data: ['异常处置数量', '巡检数量'],
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: '12',
                fontWeight: 'normal'
            },
            itemWidth: 20,  // 设置宽度
            itemHeight: 10, // 设置高度
        },
        grid: {
            top: '35',
            left: '40',
            right: '4%',
            bottom: '40'
        },
        xAxis: [
            {
                type: 'category',
                data: ['人员1', '人员2', '人员3', '人员4', '人员5'],
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
                },
            }
        ],
        yAxis: [
            {
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
                },

            }
        ],
        series: [
            {
                name: '异常处置数量',
                type: 'bar',
                barMaxWidth: '10',
                itemStyle: {
                    normal: {
                        barBorderRadius: 4,
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#e7e157'},
                                {offset: 1, color: '#f0981a'}

                            ]
                        )
                    }
                },
                data: [40, 40, 40, 30, 40]
            },
            {
                name: '巡检数量',
                type: 'bar',
                barMaxWidth: '10',
                itemStyle: {
                    normal: {
                        barBorderRadius: 4,
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#5efffd'},
                                {offset: 1, color: '#69a8a3'}

                            ]
                        )
                    }
                },
                data: [45, 45, 40, 45, 42]
            }
        ]
    };

    rwtjEcharts.setOption(option);
}

RYXJ.rwtjEchartsGetData = function () {
    var option = rwtjEcharts.getOption();
    //访问网络数据
    $.ajax({
        url: RYXJ.api.personnelStatistics,
        type: 'get',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                option.series[0].data = data.data.list[0];
                option.series[1].data = data.data.list[1];
                rwtjEcharts.setOption(option);
            }
        }
    })
}
/*****************************************统计模块end，现在没用**************************************************/



/*****************************************轨迹模块开始**************************************************/
//开始轨迹
RYXJ.trackStart = function(){
    trackPlayback.isWorking = true;
    trackPlayback.start();
    var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
    layerTree.setAlpha(node,1);
}

//暂停轨迹
RYXJ.trackPause = function(_this){

    if(!trackPlayback.isWorking) {
        $(_this).text("轨迹暂停");
        return;
    }

    if($(_this).text()=="轨迹暂停"){
        $(_this).text("轨迹继续");
    }else if($(_this).text()=="轨迹继续"){
        $(_this).text("轨迹暂停");
    }
    trackPlayback.pause();
}

//清除轨迹
RYXJ.trackStop = function(){
    trackPlayback.isWorking = false;
    trackPlayback.stop();
    var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
    layerTree.setAlpha(node,1);
}
/*****************************************轨迹模块结束**************************************************/









/*****************************************维修模块开始**************************************************/
var wxjlTableIns;
RYXJ.wxjlTable = function () {
    var id = RYXJ.GK.$wxjlTable;

    var width = ($('.wxjlTable').width()) / 4;
    var tableHeight = $(".wxjlTable").height();

    // 人员巡检
    var ryxjTableIns = RYXJ.table.render({
        elem: "#" + id
        , height: tableHeight
        , skin: 'nob'
        , method: 'post'
        , url: RYXJ.api.wxjlList
        , request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , where :{

        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.rows
            }
        }
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        , cols: [[ //标题栏
            {title: '序号', align: 'center', width: width, type: 'numbers'},
            {field: 'name', title: '名称', align: 'center'},
            {field: 'userName', title: '人员', align: 'center', width: width}
        ]]
        ,done:function(res){
            tdTitle();
        }
        , data: [{
            "taskId": "1"
            , "name": "华农南苑"
            , "userName": "李云迪"
        },]
        , page: {
            layout: ['prev', 'page', 'next', 'count'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            // ,groups: 1 //只显示 1 个连续页码
            // ,first: false //不显示首页
            // ,last: false //不显示尾页
        }
    });

    //监听单元格事件
    RYXJ.table.on('row(clickWxEvent)', function (obj) {
        var data = obj.data;
        RYXJ.rightWxBoxShow(data);
    });

    return ryxjTableIns;
}

// 右侧新增维修内容显示
RYXJ.rightWxBoxShow = function (wxDetail) {

    var content = RYXJ.com.$right.html();
    if(content == null || content.length == 0){
        RYXJ.com.$right.load(RYXJ.com.$prefix + "/wxxq", function (result) {

            RYXJ.changeState(wxDetail);
            RYXJ.laydate.render({
                elem: '#wx_repairTime'
                , type: 'datetime'
            });
            RYXJ.selectOptionInit(wxDetail);
            RYXJ.uploadFileInit();
        });
    }else{
        RYXJ.changeState(wxDetail);
        RYXJ.fillContent(wxDetail);
    }

    RYXJ.formAddListener();
    $('.rightBtn').removeClass('active');
    $(".rightBox").removeClass('rightHide');
    $(".rightBox").animate({right: "0"});
}

//根据是否传入值，确定是否显示新增还是删除
RYXJ.changeState = function(wxDetail){

    if($.common.isEmpty(wxDetail)){
        curFileList = null;
        $("#wxxqId").text("新增维修");
        $("#delWxBtn").hide();
        $("#formWx")[0].reset();
        layui.form.render();
        RYXJ.updateFileDiv();
        RYXJ.changeWXDiv(false);
    }else{
        RYXJ.changeWXDiv(true);
        $("#wxxqId").text("维修信息");
        $("#delWxBtn").show();
    }
}

//表单的提交事件
RYXJ.formAddListener = function(){

    layui.form.on('submit(saveWx)', function (data) {

        if($("#wx_description").val().length > 100){
            $.modal.msgError("您输入的字数过多");
            return;
        }

        var loadingIndex = layui.layer.load(2, { //icon支持传入0-2
            shade: [0.1, '#fff'], //0.1透明度的白色背景
            content: '上传中...',
            success: function (layero) {
                layero.find('.layui-layer-content').css({
                    'padding-top': '39px',
                    'width': '60px',
                    'color': '#ffffff'
                });
            }
        });

        var data = $("#formWx").serializeArray();
        var options=$('#wx_faultSelect option:selected');
        var files = $('.layui-upload-file')[0].files.length;
        var pipes = $("#pipeListDiv li[new]").length;
        data.push({"name": "faultType", "value": options.text()});
        $.ajax({
            url: RYXJ.api.wxjlAdd,
            type: 'post',
            data: data,
            contentType:'application/x-www-form-urlencoded',
            dataType: "json",
            success: function(result) {
                if(result.code == 0 && files == 0 && pipes == 0){//当没有选择文件和没有添加管线时，只需要提交表单即可
                    wxjlTableIns.reload();//刷新左侧的维修列表
                    RYXJ.rightBoxHide();//关闭右侧
                    // RYXJ.changeState();
                    // RYXJ.updateFileDiv();
                    layer.closeAll('loading');
                    layer.alert(result.msg)
                }else if(result.code == 0 && files != 0 && pipes == 0){//当选择了文件和没有添加管线时
                //这里上传图片包含新增表单和修改表单两种情况
                    if(!$.common.isEmpty(result.data)) {//如果返回的data不为空，说明是新增操作
                        $('#wx_id').val(result.data);//设置id给文件上传用
                    }
                    document.getElementById("wx_upload").click();//调用文件上传RYXJ.uploadFileInit
                }else if(result.code == 0 && files == 0 && pipes != 0){//当没有选择文件，但添加了管线时
                    if(!$.common.isEmpty(result.data)) {//如果返回的data不为空，说明是新增操作
                        $('#wx_id').val(result.data);//设置id给文件上传用
                    }
                    RYXJ.uploadPipe(true);//上传管线
                }else if(result.code == 0 && files != 0 && pipes != 0){//既要上传文件又要上传管线
                    if(!$.common.isEmpty(result.data)) {//如果返回的data不为空，说明是新增操作
                        $('#wx_id').val(result.data);//设置id给文件上传用
                    }
                    document.getElementById("wx_upload").click();//调用文件上传RYXJ.uploadFileInit
                    RYXJ.uploadPipe(false);//上传管线
                }
            }
        });
        return false;
    });
}

//故障类型selection初始化
RYXJ.selectOptionInit = function(wxDetail){
    $.ajax({
        type: "GET",
        url: RYXJ.api.faultType,
        data: {dictType:"aup_repair_faulttype"},
        dataType: "json",
        success: function(result){
            var data = result.data;
            var html = '';
            for(var i=0;i<data.length;i++){
                html +='<option value="'+data[i].dictValue+'">'+data[i].dictLabel+'</option>';
            }
            $('#wx_faultSelect').append(html);

            RYXJ.fillContent(wxDetail);//放这里是为了同步请求后再传入值
            layui.form.render();
        }
    });
}

//点击新增维修
RYXJ.addWx = function(){
    RYXJ.rightWxBoxShow();
}

//删除维修条目
RYXJ.delWx = function(){
    var id = $("#wx_id").val();
    layer.confirm('确定删除该条维修信息么？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: RYXJ.api.wxjlDel,
            data: {"ids":id},
            contentType:'application/x-www-form-urlencoded',
            dataType: "json",
            success: function(result){
                layer.alert(result.msg)
                if(result.code == 0){
                    wxjlTableIns.reload();
                    RYXJ.rightBoxHide();
                    // RYXJ.changeState();
                    // RYXJ.updateFileDiv();
                }
            }
        });
    });
}

//维修信息填充值
RYXJ.fillContent = function(wxDetail){
    if($.common.isEmpty(wxDetail)){
        $("#wx_id").val("");
        return;
    }

    //往只读的div中填入值
    $("#r_wx_name").text(wxDetail.name);
    $("#r_wx_userName").text(wxDetail.userName);
    $("#r_wx_repairTime").text(wxDetail.repairTime);
    $("#r_wx_address").text(wxDetail.address);
    $("#r_wx_location").text(wxDetail.location);
    $("#r_wx_location").val(wxDetail.location);
    $("#r_wx_faultType").text(wxDetail.faultType);
    $("#r_wx_description").text(wxDetail.description.length > 14 ? wxDetail.description.substring(0, 14) + "..": wxDetail.description);

    //往修改的div中填入值
    layui.form.val("formWx", {
        "id":wxDetail.id
        ,"name":wxDetail.name
        ,"userName":wxDetail.userName
        ,"repairTime":wxDetail.repairTime
        ,"address":wxDetail.address
        ,"location":wxDetail.location
        ,"faultId":wxDetail.faultId
        ,"description":wxDetail.description
    });

    //获取多媒体内容
    $.ajax({
        type: "POST",
        url: RYXJ.api.fileList,
        data: {"fileCode":wxDetail.id},
        contentType:'application/x-www-form-urlencoded',
        dataType: "json",
        success: function(result){
            if(result.code == 0){
                // wxjlTableIns.reload();
                // RYXJ.changeState();
                curFileList = result.rows;
                RYXJ.renderFileInfo(result.rows);
            }
        }
    });

    //获取管线内容
    RYXJ.pipeTableInit();
    RYXJ.delPipelineInit();
}

//管线表初始化数据
RYXJ.pipeTableInit = function () {
    $("#pipeListDiv").empty();
    $("#r_pipeListDiv").empty();
    //获取多媒体内容
    $.ajax({
        type: "POST",
        url: RYXJ.api.pipeList,
        data: {"taskId":$('#wx_id').val()},
        contentType:'application/x-www-form-urlencoded',
        dataType: "json",
        success: function(result){
            if(result.code == 0){
                // wxjlTableIns.reload();
                // RYXJ.changeState();
                var dataList = result.rows;
                for(i = 0; i < dataList.length; i++){
                    var item = dataList[i];
                    $("#r_pipeListDiv").append("                <li>" +
                    "          <span class=\"cl-3 fl txtC\">"+(i+1)+"</span>" +
                    "          <span class=\"cl-5 fl\">" +
                    "             <input type=\"text\" value="+item.name+" autocomplete=\"off\"></input>" +
                    "          </span>" +
                    "      </li>");
                    $("#pipeListDiv").append("                <li>" +
                    "          <span class=\"cl-3 fl txtC\">"+(i+1)+"</span>" +
                    "          <span class=\"cl-5 fl\">" +
                    "             <input type=\"text\" value="+item.name+" autocomplete=\"off\"></input>" +
                    "          </span>" +
                    "          <button type=button class=\"cl-2 fl\" id="+item.id+" source=\"net\">" + "删除" +"</button>" +
                    "      </li>");
                }

                $("#r_pipe_lable").css('display',dataList.length == 0 ?'none':'block');
            }
        }
    });

}

//添加管线数据
RYXJ.pipeTableAdd = function(picker){

    if(picker.expNo == ""){//当为管线数据时
        $("#pipeListDiv").append("<li new dataName="+ picker.name +" dataGuid="+ picker.guid +" dataLocation="+ picker.location
            +" dataSerialNumber="+picker.serialNumber +" dataSPoint="+picker.sPoint+" dataEPoint="+picker.ePoint +">" +
            "                    <span class=\"cl-3 fl txtC\">"+"待提交"+"</span>" +
            "                    <span class=\"cl-5 fl\">" +
            "                       <input type=\"text\" value="+picker.name+" autocomplete=\"off\"></input>" +
            "                    </span>" +
            "                    <button type=button class=\"cl-2 fl\" source=\"local\">" + "删除" +"</button>" +
            "                </li>");
    }else{//当为管点数据时
        $("#pipeListDiv").append("<li new dataName="+ picker.name +" dataGuid="+ picker.guid +" dataLocation="+ picker.location
            +" dataSerialNumber="+picker.serialNumber +" dataExpNo="+picker.expNo +">" +
            "                    <span class=\"cl-3 fl txtC\">"+"待提交"+"</span>" +
            "                    <span class=\"cl-5 fl\">" +
            "                       <input type=\"text\" value="+picker.name+" autocomplete=\"off\"></input>" +
            "                    </span>" +
            "                    <button type=button class=\"cl-2 fl\" source=\"local\">" + "删除" +"</button>" +
            "                </li>");
    }

}

//删除管线
RYXJ.delPipelineInit = function(){
    $('#updateWxDiv').on('click', '#pipeListDiv>li>button', function() {

        // this.parentNode.remove();
        var source = $(this).attr("source");
        var id = $(this).attr("id");
        var that = this;

        layui.layer.confirm('确定删除该条管线信息么？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            if(source == "local"){
                that.parentNode.remove();
                layer.msg("删除成功！");
            }else if(source == "net"){
                $.ajax({
                    type: "POST",
                    url: RYXJ.api.pipeDel,
                    data: {"ids":id},
                    contentType:'application/x-www-form-urlencoded',
                    dataType: "json",
                    success: function(result){
                        if(result.code == 0){
                            that.parentNode.remove();
                            layer.msg("删除成功！");
                        }
                    }
                });
            }
        });
    });
}

//上传管线设备
RYXJ.uploadPipe = function(isShowMsg){
    var taskId = $('#wx_id').val();
    var divList = $("#pipeListDiv li[new]");

    var aupRepairPipelinesArr = [];
    for(i = 0; i < divList.length; i++){
        var name = divList.eq(i).attr('dataName');
        var guid = divList.eq(i).attr('dataGuid');
        var location = divList.eq(i).attr('dataLocation');

        var serialNumber = divList.eq(i).attr('dataSerialNumber');
        var sPoint = divList.eq(i).attr('dataSPoint') || "";
        var ePoint = divList.eq(i).attr('dataEPoint') || "";
        var expNo = divList.eq(i).attr('dataExpNo') || "";


        var aupRepairPipelines = {};
        aupRepairPipelines.guid = guid;
        aupRepairPipelines.name = name;
        aupRepairPipelines.location = location;
        aupRepairPipelines.taskId = taskId;

        aupRepairPipelines.serialNumber = serialNumber;
        aupRepairPipelines.startPoint = sPoint;
        aupRepairPipelines.endPoint = ePoint;
        aupRepairPipelines.expNo = expNo;

        aupRepairPipelinesArr.push(aupRepairPipelines);
    }
    if(aupRepairPipelinesArr.length > 0){
        $.ajax({
            type: "POST",
            url: RYXJ.api.pipeAddList,
            data: JSON.stringify(aupRepairPipelinesArr),
            contentType : 'application/json; charset=UTF-8',
            dataType: "json",
            success: function(result){
                if(result.code == 0 && isShowMsg){
                    wxjlTableIns.reload();
                    RYXJ.rightBoxHide();//关闭右侧
                    layer.closeAll('loading');
                    layer.alert(result.msg)
                }
            }
        });
    }
}

RYXJ.getLocation = function(){
    layer.msg("您现在可以开始采集坐标了");
    pickFeature.isWorking = true;
    pickFeature.start("Position", function(pickedFeature){
        $("#wx_location").val(pickedFeature.x +","+pickedFeature.y+","+pickedFeature.z);

    });
}

RYXJ.stopPick = function(){
    pickFeature.stop();
    pickFeature.isWorking = false;

    layer.msg("已关闭采集模式");
}

RYXJ.stopFeaturePick = function(){
    pickFeature.stop();
    pickFeature.isWorking = false;
    layer.msg("已关闭采集模式");
    selectedLinePipes(layerCfg.ryxj.all);
}

/*RYXJ.getPipeline = function(){
    layer.msg("您现在可以开始拾取管线了");
    pickFeature.isWorking = true;
    var guid = layerTree.zTree.getNodeByParam("id","PIPELINE1_3D").guid;
    var tileset= viewer.scene.primitives.getPrimitiveByGuid(guid);
    var linkPropertyName = 'DbId';//'id';
    //使用目录结构扩展
    tileset.extendCatalog(linkPropertyName);
    tileset.setEnablePickFeatures(viewer, true);
    //监听拾取事件
    var pickedEvent = tileset.getPickedEvent();
    if (pickedEvent) {
        pickedEvent.addEventListener(function (res) {
            console.log(res);
        });
    }
}*/

/*RYXJ.getPipeline = function(){
    layer.msg("您现在可以开始拾取管线了");
    pickFeature.isWorking = true;
    // RYXJ.selectedMode(layerCfg.jcbz.znpg);
    RYXJ.selectedMode(layerCfg.ryxj.layer);

    pickFeature.start("Feature", function(pickedFeature){

        var properties = pickedFeature.getPropertyNames() || [];
        // for(i = 0; i < properties.length; i++){
        //    var obj = pickedFeature.getProperty(properties[i]);
        //     console.log(properties[i]+"----"+obj);
        // }
        var sPoint = "", ePoint = "", serialNumber, expNo = "";

        if(properties.indexOf("管点编号") != -1){
            serialNumber = pickedFeature.getProperty("管点编号");
            expNo = pickedFeature.getProperty("管点编号") || "";
        }else if(properties.indexOf("管线编号") != -1){
            serialNumber = pickedFeature.getProperty("管线编号");
            sPoint = pickedFeature.getProperty("S_POINT") || "";
            ePoint = pickedFeature.getProperty("E_POINT") || "";
        }else{//不在设计范围内的选项先return掉，不然会导致数据库的数据undefined
            layer.msg("未获取到属性");
            return;
        }
        pickedFeature.getProperty("");

        var guid = pickedFeature._content._tileset._guid.trim();
        var name = pickedFeature._content._tileset._name.trim();
        var center = pickedFeature._content._tileset.boundingSphere.center;
        var location = center.x+","+center.y+","+center.z;
        var picker = {
            guid:guid,
            name:name,
            location:location,
            serialNumber:serialNumber,
            expNo:expNo,
            sPoint:sPoint,
            ePoint:ePoint
        };
        RYXJ.pipeTableAdd(picker);
    });
}*/

//这部分代码由于数据修改，导致实现起来较为繁琐，需要结合initLayerTree.js里的var pickedEvent = tileset.getPickedEvent();拾取监听事件来完成，暂时屏蔽掉了拾取管线的模块
RYXJ.getPipeline = function(){
    layer.msg("您现在可以开始拾取管线了");
    pickFeature.isWorking = true;
    // RYXJ.selectedMode(layerCfg.jcbz.znpg);
    RYXJ.selectedMode(layerCfg.ryxj.layer);
    var treeNode = layerTree.zTree.getNodeByParam("id", "JS_LINE_3D");
    var tileset = viewer.scene.primitives.getPrimitiveByGuid(treeNode.guid);
    // var tileset = viewer.scene.primitives.getPrimitiveByGuid("JS_LINE_3D");
    var linkPropertyName = 'DbId';//'DbId';//'id';
    tileset.extendCatalog(linkPropertyName);
    tileset.setEnablePickFeatures(viewer, true);
    //监听拾取事件
    var pickedEvent = tileset.getPickedEvent();
    // if (pickedEvent) {
        pickedEvent.addEventListener(function (res) {
            if (pickColor != null) {
                Cesium.Color.clone(pickColor.color, res.feature.color);
            }
            var feature = res.feature; //拾取到的要素对象

        });
    // }
}

RYXJ.selectedMode = function (nameArr) {
    //获取第一层子节点
    var list = $("#tt")[0].children;
    var treeObj = layerTree.zTree;

    //清除半透明倾斜摄影
    if(layerTree && layerTree.zTree) {
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        if (node.checked == true && nameArr.length > 0) {
            layerTree.setAlpha(node, 1);
        }
    }

    //遍历，清除倾斜摄影之外的已勾选的图层
    for (var i = 0; i < list.length; i++) {
        var node = treeObj.getNodeByTId(list[i].id);
        var name = node.name;

        if(node.checked == true){
            treeObj.checkNode(node, "", true,true);
        }

    }
    //勾选选中的图层
    for (var j = 0; j < nameArr.length; j++) {
        if(layerTree && layerTree.zTree){
            var childrenNode = layerTree.zTree.getNodeByParam("id", nameArr[j], null);
            treeObj.checkNode(childrenNode, "", true, true);
        }
    }
}

//上传文件方法渲染
RYXJ.uploadFileInit = function () {
    layui.upload.render({
        elem: '#wx_selectFile', //绑定元素
        url: RYXJ.api.uploadFile, //上传接口
        data: {
            file_code: function(){

                return $('#wx_id').val();
            }
        },
        auto: false,
        before: function(obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
        },
        allDone: function(obj) { //当文件全部被提交后，才触发
            // console.log(obj.total); //得到总文件数
            // console.log(obj.successful); //请求成功的文件数
            // console.log(obj.aborted); //请求失败的文件数
            layer.closeAll('loading');
            layer.alert("上传成功");
            // wxjlTableIns.reload();
            // RYXJ.changeState();
            // RYXJ.updateFileDiv();

            wxjlTableIns.reload();
            RYXJ.rightBoxHide();
        },
        error: function() {
            //请求异常回调
            layer.closeAll('loading');
        },
        multiple: true,
        number: 5,
        accept: 'file', //允许上传的文件类型
        acceptMime: 'image/jpg, image/jpeg, image/bmp, image/png, video/mp4, video/quicktime, video/x-ms-wmv, video/x-matroska, ' +
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/msword',
        exts: 'jpg|png|bmp|jpeg|mp4|mov|wmv|mkv|doc|docx',
        size: 51200, //最大允许上传的文件大小50MB
        bindAction: '#wx_upload'
    });
}

//将图片视频文件展示
RYXJ.renderFileInfo = function(item) {

    RYXJ.updateFileDiv();

    if(String.isNullOrEmpty(item)) {
        return;
    }

    var imgList = [],videoList = [],fileList = [];
    for(i = 0; i < item.length; i++){

        if(item[i].fileType.indexOf("image") != -1){
            imgList.push(item[i]);
        }else if(item[i].fileType.indexOf("video") != -1){
            videoList.push(item[i]);
        }else if(item[i].fileType.indexOf("application") != -1){
            fileList.push(item[i]);
        }
    }

    RYXJ.updateFileDiv(imgList, videoList, fileList);

    document.getElementById('wxPic').innerHTML = function () {

        var prefix = "";
        // var url = filePath + "?text=图片加载中";
        if(ctx != "/"){
            prefix = ctx;
        }

        var arr = [];
        for(i = 0; i < imgList.length; i++){



            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<img class=\"img\" onclick=\"RYXJ.middleBoxShow('#wxPic')\" src="+prefix+imgList[i].filePath+" alt=\"\">\n" +
                "<i class=\"iconfont icon-guanbi2 pa close\" onclick=\"RYXJ.delFile("+"'"+imgList[i].fileId+"')\"></i>" +
                "</li>");
        }
        return arr.join('');
    }();

    document.getElementById('r_wxPic').innerHTML = function () {

        var prefix = "";
        if(ctx != "/"){
            prefix = ctx;
        }

        var arr = [];
        for(i = 0; i < imgList.length; i++){
            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<img class=\"img\" onclick=\"RYXJ.middleBoxShow('#r_wxPic')\" src="+prefix+imgList[i].filePath+" alt=\"\">\n" +
                "</li>");
        }
        return arr.join('');
    }();

    document.getElementById('wxVideo').innerHTML = function () {

        var prefix = "";
        if(ctx != "/"){
            prefix = ctx;
        }

        var arr = [];
        for(i = 0; i < videoList.length; i++){

            var name = videoList[i].filePath.substring(0, videoList[i].filePath.lastIndexOf("-"));
            var m = videoList[i].filePath.lastIndexOf("-")+1;
            var n = videoList[i].filePath.lastIndexOf("/");
            var suffix = videoList[i].filePath.substring(m, n);
            var realPath = name + "." + suffix;
            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<img class=\"img\" src="+prefix+videoList[i].filePath+" onclick=\"RYXJ.playVideo("+"'"+prefix+realPath+"'"+")\" alt=\"\">\n" +
                "<i class=\"iconfont icon-guanbi2 pa close\" onclick=\"RYXJ.delFile("+"'"+videoList[i].fileId+"')\"></i>" +
                "</li>");
        }
        return arr.join('');
    }();

    document.getElementById('r_wxVideo').innerHTML = function () {

        var prefix = "";
        if(ctx != "/"){
            prefix = ctx;
        }

        var arr = [];
        for(i = 0; i < videoList.length; i++){

            var name = videoList[i].filePath.substring(0, videoList[i].filePath.lastIndexOf("-"));
            var m = videoList[i].filePath.lastIndexOf("-")+1;
            var n = videoList[i].filePath.lastIndexOf("/");
            var suffix = videoList[i].filePath.substring(m, n);
            var realPath = name + "." + suffix;
            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<img class=\"img\" src="+prefix+videoList[i].filePath+" onclick=\"RYXJ.playVideo("+"'"+prefix+realPath+"'"+")\" alt=\"\">\n" +
                "</li>");
        }
        return arr.join('');
    }();

    document.getElementById('wxFile').innerHTML = function () {

        var prefix = "";
        if(ctx != "/"){
            prefix = ctx;
        }
        var arr = [];
        for(i = 0; i < fileList.length; i++){

            var name = fileList[i].filePath.substring(0, fileList[i].filePath.lastIndexOf("-"));
            var m = fileList[i].filePath.lastIndexOf("-")+1;
            var n = fileList[i].filePath.lastIndexOf("/");
            var suffix = fileList[i].filePath.substring(m, n);
            var realPath = name + "." + suffix;
            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<p style=\"text-align:center\">"+ fileList[i].fileName +"</p>" +
                "<img class=\"img\" src=\""+prefix+"bus/aupipes/lib/images/word.png\" onclick=\"RYXJ.downFile("+"'"+prefix+realPath+"'"+")\" alt=\"\">\n" +
                "<i class=\"iconfont icon-guanbi2 pa close\" onclick=\"RYXJ.delFile("+"'"+fileList[i].fileId+"')\"></i>" +
                "</li>");
        }
        return arr.join('');
    }();

    document.getElementById('r_wxFile').innerHTML = function () {

        var prefix = "";
        if(ctx != "/"){
            prefix = ctx;
        }

        var arr = [];
        for(i = 0; i < fileList.length; i++){

            var name = fileList[i].filePath.substring(0, fileList[i].filePath.lastIndexOf("-"));
            var m = fileList[i].filePath.lastIndexOf("-")+1;
            var n = fileList[i].filePath.lastIndexOf("/");
            var suffix = fileList[i].filePath.substring(m, n);
            var realPath = name + "." + suffix;
            arr.push("<li class=\"swiper-slide UploadImgBox\">\n" +
                "<p style=\"text-align:center\">"+ fileList[i].fileName +"</p>" +
                "<img class=\"img\" src=\""+prefix+"bus/aupipes/lib/images/word.png\" onclick=\"RYXJ.downFile("+"'"+prefix+realPath+"'"+")\" alt=\"\">\n" +
                "</li>");
        }
        return arr.join('');
    }();

    var mySwiper = new Swiper('.swiper-container', {
//			spaceBetween: 30,
//         loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    })
}

//更新文件的div展示
RYXJ.updateFileDiv = function(imgList, videoList, fileList){

    document.getElementById('wxPic').innerHTML = "";
    document.getElementById('r_wxPic').innerHTML = "";
    document.getElementById('wxVideo').innerHTML = "";
    document.getElementById('r_wxVideo').innerHTML = "";
    document.getElementById('wxFile').innerHTML = "";
    document.getElementById('r_wxFile').innerHTML = "";

    if(imgList == null || imgList.length == 0){
        $("#r_wx_pic_lable").hide();
        $("#wx_pic_lable").hide();
    }else{
        $("#r_wx_pic_lable").show();
        $("#wx_pic_lable").show();
    }

    if(videoList == null || videoList.length == 0){
        $("#r_wx_video_lable").hide();
        $("#wx_video_lable").hide();
    }else{
        $("#r_wx_video_lable").show();
        $("#wx_video_lable").show();
    }

    if(fileList == null || fileList.length == 0){
        $("#r_wx_file_lable").hide();
        $("#wx_file_lable").hide();
    }else{
        $("#r_wx_file_lable").show();
        $("#wx_file_lable").show();
    }

}

RYXJ.playVideo = function(url){
    if(url.substring(url.lastIndexOf(".")+1, url.length) == "wmv"){

        layer.confirm('不支持wmv格式的播放，是否下载？', {
            btn: ['下载','取消'] //按钮
        }, function(){
            window.open(url, '_blank');
            layer.msg('开始下载', {icon: 1});
        }, function(){
        });
        return;
    }

    var loadstr = '<video width="630px" height="320px" controls="controls" autobuffer="autobuffer"  autoplay="autoplay" loop="loop"><source src='+url+' type="video/mp4"></source></video>';
    layer.open({
        type: 1,
        title: '播放视频',
        content: loadstr,
        area: ['33%', '26%']
    });
}

RYXJ.downFile = function(url){
    layer.confirm('是否下载？', {
        btn: ['下载','取消'] //按钮
    }, function(){
        window.open(url, '_blank');
        layer.msg('开始下载', {icon: 1});
    }, function(){
    });
}

var curFileList;//当前选中维修的文件列表，当新增维修时，要置空，用于给文件删除时remove当前文件
RYXJ.delFile = function(id){
    layer.confirm('确定删除么？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: RYXJ.api.delFile,
            data: {"ids":id},
            contentType:'application/x-www-form-urlencoded',
            dataType: "json",
            success: function(result){
                layer.alert(result.msg)
                if(result.code == 0){
                    for(i = 0; i < curFileList.length; i++){
                        if(id == curFileList[i].fileId){
                            curFileList.pop(i);
                        }
                    }
                    wxjlTableIns.reload();
                    RYXJ.renderFileInfo(curFileList);
                }
            }
        });
    });
}

RYXJ.wxSearch = function(){
    var searchValue = $("#wxSearchValue").val()

    wxjlTableIns.reload({
        where:{
            name: searchValue,
            userName: searchValue
        }
    });
}

//维修详情弹框
var wxjlTableIns;
RYXJ.detailWx = function(){
    layer.open({
        type: 1,
        title: '维修详情',
        area: ['100%', '93.2%'],
        offset: 'b',
        content: $('#' + RYXJ.POP.$wxjlLayerBox),
        skin: 'layer-style',
        id: 'Box2'
    });

    $("#wxjlTime").val("");
    $("#wxjlName").val("");
    $('#wxjlType').val("全部");

    var tableHeight = $(".xjjlTable").height()

    RYXJ.laydate.render({
        elem: '#wxjlTime'
        , type: 'date'
        , range: '到'
        , format: 'yyyy-MM-dd'
    });

    $.ajax({
        type: "GET",
        url: RYXJ.api.faultType,
        data: {dictType:"aup_repair_faultType"},
        dataType: "json",
        success: function(result){
            var data = result.data;
            var html = '<option value="">'+"全部"+'</option>';
            for(var i=0;i<data.length;i++){
                html +='<option value="'+data[i].dictValue+'">'+data[i].dictLabel+'</option>';
            }
            $('#wxjlType').append(html);
            layui.form.render();
        }
    });

    wxjlTableIns = RYXJ.table.render({
        elem: '#wxjlTable2'
        , height: tableHeight
        // ,width:width
        , skin: 'nob'
        , method: 'post'
        // , url: RYXJ.api.inspectRecord
        , url: RYXJ.api.wxjlList
        , request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.rows
            }
        }
        , limit: 15
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        , cols: [[ //标题栏
            {field: 'serial', title: '序号', align: 'center',templet : function(d){
                    return d.LAY_TABLE_INDEX+1;
                }},
            {field: 'name', title: '名称', align: 'center',},
            {field: 'userName', title: '维修人员', align: 'center',},
            {field: 'repairTime', title: '维修时间', align: 'center'},
            {field: 'address', title: '详细位置', align: 'center'},
            {field: 'location', title: '坐标', align: 'center'},
            {field: 'faultType', title: '故障类型', align: 'center'},
            {field: 'deviceName', title: '设备名称', align: 'center'},
            {field: 'description', title: '详细描述', align: 'center'}
        ]]
        ,done:function(res){
            tdTitle();
        }
        , data: [{
        }]
        , page: {
            layout: ['prev', 'page', 'next'] //自定义分页布局
            // //,curr: 5 //设定初始在第 5 页
            // , groups: 1 //只显示 1 个连续页码
            // , first: false //不显示首页
            // , last: false //不显示尾页

        }
    });
}

RYXJ.queryWxjlList = function () {
    var faultId=$("#wxjlType option:selected").val();
    var wxjlName = $("#wxjlName").val();
    var startTime, endTime, time = $("#wxjlTime").val().split("到");
    // alert($("#inspectTime").val());
    if(time.length > 1){
        startTime = time[0].trim();
        endTime = time[1].trim();
    }else{
        startTime = "";
        endTime = "";
    }

    wxjlTableIns.reload({
        where:{
            userName: wxjlName,
            faultId: faultId,
            "params[beginTime]": startTime,
            "params[endTime]": endTime
        }
    });
}

RYXJ.exportWxjlExcel = function () {

    var msg = layer.msg('正在导出数据，请稍后...', {time: false});

    var startTime, endTime, time = $("#inspectTime").val().split("到");
    // alert($("#inspectTime").val());
    if(time.length > 1){
        startTime = time[0].trim();
        endTime = time[1].trim();
    }else{
        startTime = "";
        endTime = "";
    }
    var data = {
        userName: $("#wxjlName").val(),
        faultId: $("#wxjlType option:selected").val(),
        "params[beginTime]": startTime,
        "params[endTime]:": endTime
    }
    $.post(RYXJ.api.wxjlExportUrl, data, function(result) {
        layer.close(msg);//手动关闭
        layer.msg("导出成功！", {time: 1000});
        if (result.code == 0) {
            window.location.href = ctx + "system/common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
        } else if (result.code == 500) {
            layer.msg(result.msg)
        } else {
            layer.msg(result.msg)
        }
        // $.modal.closeLoading();
    });
}

RYXJ.changeWXDiv = function(isOnlyRead){
    if(isOnlyRead){
        $("#readOnlyWxDiv").show();
        $("#updateWxDiv").hide();
    }else{
        $("#readOnlyWxDiv").hide();
        $("#updateWxDiv").show();
    }
}
/*****************************************维修模块结束**************************************************/
