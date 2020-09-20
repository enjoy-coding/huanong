"use strict";
"use strict";
/**
 * 运行监管
 * @type {{init: ModelManager.YXJK.init}}
 */
var YXJK = ModelManager["YXJK"] = {
    config: {
        appKey: "28852076",
        appSecret: "SGt7HK1LiTPyW4NVRUpk",
        hostIp: "10.163.132.200",
        defaultCameraIndexCode: "ec21b4971eab40d2897914986614d952"
    },
    oWebControl: null,
    element: null,
    laypage: null,
    laydate: null,
    table: null,
    tree: null,
    zTreeObj: null,
    ChartsName: [],
    pickedObject: null,
    picImg: null,

    //初始化方法
    init: function (menuCode) {
        //改变全局搜索的默认值
        objectType = 'subject';
        $(".searchLabel").find("input").eq(3)[0].checked = true;
        //初始化
        menuChangeInit();
        //左侧显示
        leftBoxShow();
        //初始化layer
        YXJK.common.initLayer();
        //加载设备统计
        YXJK.com.$left.load(YXJK.com.$prefix + "/yxjk", function (result) {
            //初始化勾选图层
            //selectedLinePipes(layerCfg.yxjk.all);
            $(".ztreeBox").css("height", "62vh");
            $.get(YXJK.com.$prefix + "/sbtj", function (d) {
                var data = d.data.sbtj;
                //根据20191228 提出需求从左到右，从上到下依次为配电房、泵房、楼栋、水表、电表、路灯、探漏、监控、水质
                //配电房
                $("#powerHouse_zs").text(data[0].total);
                //泵房
                $("#pump_zs").text(data[1].total);

                $("#meter_zx").text(data[3].number);
                $("#meter_zs").text(data[3].total);
                //路灯
                $("#streetlight_zx").text(data[4].number);
                $("#streetlight_zs").text(data[4].total);
                //探漏
                $("#leak_zx").text(data[5].number);
                $("#leak_zs").text(data[5].total);
                //监控
                $("#monitor_zx").text(data[6].number);
                $("#monitor_zs").text(data[6].total);
                //水质
                $("#waterQuality_zx").text(data[7].number);
                $("#waterQuality_zs").text(data[7].total);
            });

            //判断选中
            if (menuCode == "" || menuCode == "undefined" || menuCode == null) {
                YXJK.loadTree();
            } else {
                //移除其他样式
                $("#" + YXJK.mo.$yxjk_monitor_count + " div").each(function () {
                    $(this).removeClass('active');
                });
                //增加当前样式
                $("#" + menuCode).addClass("active");
                //更新树结构
                YXJK.loadTree(menuCode);
            }


            //遍历设备列表div
            $("#" + YXJK.mo.$yxjk_monitor_count + " div").click(function () {
                $("#" + YXJK.mo.$yxjk_monitor_count + " div").each(function () {
                    $(this).removeClass('active');
                });
                $(this).addClass("active");
                //更新树结构
                YXJK.loadTree($(this).attr("id"));
            });

            //离线按钮
            $("#unlineState").click(function(){
                $.get(ctx+"screen/yxjk/jdxx/streetlight/unline",function(res){

                });
            })

        });

    },
    loadTree: function (code) {
        //清除定位
        $("#dingwei").remove();
        clearMap();
        clearOWebControl();
        rightBoxClose();
        YXJK.common.clearSearch();//清除搜索框
        YXJK.common.initBseTree();
        //清除路灯加宽的样式
        YXJK.common.removeStreeLightRightPageCss();
        //清除已经定位的建筑物
        layerTree.removeLocatedBuilding();
        //清除已经定位的配电房bim
        if (layerTree.bimTreeSceneFlag) {
            layerTree.outOfBimTreeScene();
        }
        if (viewer) {
            viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
        //清除追溯操作
        GWYX.clearPickPoint();
        pickType = null;
        code = code == "" || code == "undefined" || code == null ? "powerHouse" : code;
        YXJK.clickPic(code);
        switch (code) {
            case "powerHouse":
                selectedLinePipe(layerCfg.yxjk.all);
                YXJK.power.initTree();
                break;
            case "pump":
                selectedLinePipe(layerCfg.yxjk.bHouse);
                YXJK.pump.initTree();
                break;
            case "streetlight":
                YXJK.common.initStreetLightTree();
                selectedLinePipe(layerCfg.yxjk.light);
                YXJK.streetLight.initTree();
                break;
            case "meter":
                YXJK.common.initTabTree();
                selectedLinePipe(layerCfg.yxjk.electricity);
                YXJK.meter.initTree();
                break;
            case "leak":
                selectedLinePipe(layerCfg.yxjk.leak);
                YXJK.leak.initTree();
                break;
            case "monitor":
                selectedLinePipe(layerCfg.yxjk.monitor);
                YXJK.monitor.initTree();
                break;
            case "waterQuality":
                YXJK.waterQuality.initTree();
                selectedLinePipe(layerCfg.yxjk.waterQuality);
                break;
            default:
                //隐藏右侧
                rightBoxClose();
                var url = YXJK.com.$prefix + "/jdxx/other/tree";
                var treeId = YXJK.mo.$yxjk_monitor_tree;
                ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
                });
                break;
        }
    }

};

/**
 * 通用方法
 * @type {{initLayer: YXJK.common.initLayer, removeStreeLightRightPageCss: YXJK.common.removeStreeLightRightPageCss, addStreeLightRightPageCss: YXJK.common.addStreeLightRightPageCss}}
 */
YXJK.common = {
    initLayer: function () {
        layui.use(['element', 'tree', 'table', 'laydate', 'laypage', 'form'], function () {
            var $ = layui.jquery;
            layui.$.support.cors = true;
            YXJK.element = layui.element;
            YXJK.tree = layui.tree;
            YXJK.table = layui.table;
            YXJK.laydate = layui.laydate;
            YXJK.laypage = layui.laypage;
            YXJK.form = layui.form;

        });
    }
    /**
     * 设置路灯点击加宽右侧
     */
    , addStreeLightRightPageCss: function () {
        if (!$(".rightBox").hasClass("streetLampBox")) {
            //修改右侧的样式
            $(".rightBox").addClass("streetLampBox");
            $(".rightBox").animate({right: "0vw"});
        }

        if (!$(".searchBox").hasOwnProperty("style")) {
            //地图按钮
            $(".searchBox").css("right", "36vw");
        }
        if (!$(".mapShrinkButton").hasOwnProperty("style")) {
            //地图按钮
            $(".mapShrinkButton").css("right", "36vw");
        }
        if (!$(".mapBtnList").hasOwnProperty("style")) {
            //地图按钮
            $(".mapBtnList").css("right", "36vw");
        }

    }
    /**
     * 移除右侧加宽
     */
    , removeStreeLightRightPageCss: function () {
        if ($(".rightBox").hasClass("streetLampBox")) {
            //修改右侧的样式
            $(".rightBox").removeClass("streetLampBox");
            //修改右侧的样式
            $(".rightBox").animate({right: "-36vm"});
        }
        if (!$(".mapShrinkButton").hasOwnProperty("style")) {
            //地图按钮
            $(".mapShrinkButton").removeAttr("style", "");
        }
        if (!$(".searchBox").hasOwnProperty("style")) {
            //地图按钮
            $(".searchBox").removeAttr("style");
        }
        if (!$('.mapBtnList').hasOwnProperty("style")) {
            $(".mapBtnList").removeAttr("style");
        }
    },
    /**
     * 获取时间
     * @param targetDate
     * @returns {string}
     */
    getFullDate: function (targetDate) {
        var nowDate = new Date();
        var cloneNowDate = new Date();
        var fullYear = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1; // getMonth 方法返回 0-11，代表1-12月
        var endOfMonth = new Date(fullYear, month, 0).getDate(); // 获取本月最后一天

        var D, y, m, d;
        if (targetDate) {
            D = new Date(targetDate);
            y = D.getFullYear();
            m = D.getMonth() + 1;
            d = D.getDate();
        } else {
            y = fullYear;
            m = month;
            d = date;
        }
        m = m > 9 ? m : '0' + m;
        d = d > 9 ? d : '0' + d;
        return y + '-' + m + '-' + d;
    }
    /**
     * 获取最小日期
     * @returns {string}
     */
    , getMinDay: function () {
        return '1900-1-1';
    }
    /**
     * 获取最大日期
     * @returns {string}
     */
    , getMaxDay: function () {
        var now = new Date();
        return now.getFullYear() +
            "-" + (now.getMonth() +
                1) +
            "-" + now.getDate();
    },
    /**
     * 日期小于10的前面追加0
     * @param obj
     * @returns {string|*}
     * @constructor
     */
    Appendzero: function (obj) {
        if (obj < 10) return "0" + obj; else return obj;
    },
    /**
     * 获取昨天
     */
    getPreDay: function () {
        var day1 = new Date();
        day1.setTime(day1.getTime() - 24 * 60 * 60 * 1000);
        var s1 = day1.getFullYear() + "-" + YXJK.common.Appendzero((day1.getMonth() + 1)) + "-" + YXJK.common.Appendzero(day1.getDate());
        return s1;
    },
    /**
     * 获取今天
     * @returns {string}
     */
    getCurrDay: function () {
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear() + "-" + YXJK.common.Appendzero((day2.getMonth() + 1)) + "-" + YXJK.common.Appendzero(day2.getDate());
        return s2;
    }
    ,initStreetLightTree:function(){
        $("#streetlightTabTree").show();
        $("#meterTabTree").hide();
        $("#baseTree").hide();
    }
    ,initTabTree:function(){
        $("#streetlightTabTree").hide();
        $("#meterTabTree").show();
        $("#baseTree").hide();
    }
    ,initBseTree:function(){
        $("#meterTabTree").hide();
        $("#baseTree").show();
        $("#streetlightTabTree").hide();
    }
    /**
     * 清楚搜索框
     */
    ,clearSearch:function(){
        $("#yxjk_search").val("");
        $("#yxjk_search1").val("");
        $("#yxjk_search2").val("");
        $("#yxjk_search3").val("");
        $("#yxjk_search4").val("");
            
    }
}

/**
 * 常量
 * @type {{
 * $bottom: (jQuery|HTMLElement), 底部
 * $prefix: string,  请求头链接
 * $left: (jQuery|HTMLElement), 左边
 * $target: string, 源
 * $right: (jQuery|HTMLElement) 右边
 * }}
 */
YXJK.com = {
    $prefix: ctx + "screen/yxjk",
    $target: "yxjk",
    $left: $("#left-panel"),
    $right: $("#right-panel"),
    $bottom: $("#bottom-panel")
};
YXJK.mo = {
    //设备统计id （用来遍历设备列表中每一个div）
    $yxjk_monitor_count: "yxjk_monitor_count",
    //设备树id (用来绑定js数据结构)
    $yxjk_monitor_tree: "yxjk_monitor_tree",
    $yxjk_search: "yxjk_search",
    $yxjk_search1: "yxjk_search1",
    $yxjk_search2: "yxjk_search2",
    $yxjk_search3: "yxjk_search3",
    $yxjk_search4: "yxjk_search4",
};
YXJK.pu = {
    //泵房详情信息表格 （用来绑定泵房详情table）
    $yxjk_pump_detail_table: "yxjk_pump_detail_table",
    char: "",
    scpl_char: "",
    sxyw_char: "",
    ckyl_char: "",
    sdyl_char: ""
};


/**
 * 水质
 * @type {{initTree: waterQuality.initTree}}
 */
YXJK.waterQuality = {
    initTree: function () {
        var url = YXJK.com.$prefix + "/jdxx/waterQuality/tree";
        var treeId = YXJK.mo.$yxjk_monitor_tree;
        //隐藏右侧
        rightBoxClose();
        YXJK.zTreeObj = ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
            YXJK.position.init(treeNode.id, "waterQuality", treeNode.code, treeNode.maps.sid, false, "WATER_MONITOR");
            YXJK.waterQuality.rightInit(treeNode.id);
        }, true, YXJK.mo.$yxjk_search,0);
    },
    rightInit: function (id) {
        rightBoxShow();
        YXJK.common.removeStreeLightRightPageCss();
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/waterQuality/page/" + id, function (result) {
            //加载数据
            $.get(YXJK.com.$prefix + "/jdxx/waterQuality/" + id, function (result) {
                var data = result.data;
                var lsjc = data.lsjc;
                var szjc = data.szjc;
                var type = data.type;
                if (type == "水质") {
                    $("#ygfrjy").text(szjc.ygfrjy);
                    $("#dcsddl").text(szjc.dcsddl);
                    $("#zd").text(szjc.zd);
                    $("#ph").text(szjc.ph);
                    $("#orp").text(szjc.orp);
                    $("#wd").text(szjc.wd);
                    $("#sz_jcsj").text(szjc.jcsj);
                } else {
                    $("#ls").text(lsjc.ls);
                    $("#sw").text(lsjc.sw);
                    $("#ls_jcsj").text(lsjc.jcsj);
                }
            })
        });
    }
}

/**
 * 配电房
 * @type {{init: YXJK.power.init  //显示右侧信息
 * , eddyEcharts: YXJK.power.eddyEcharts //额定电压
 * , edglEcharts: YXJK.power.edglEcharts //限定电压
 * }}
 */
YXJK.power = {
    initTree: function () {
        var url = YXJK.com.$prefix + "/jdxx/powerHouse/tree";
        var treeId = YXJK.mo.$yxjk_monitor_tree;
        //隐藏右侧
        rightBoxClose();
        YXJK.zTreeObj = ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
            // if (treeNode.maps.pid == "0") {
            //     YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
            // }
            rightBoxClose();
            layerTree.outOfBimTreeScene();
            //20200304 当加载bim模型的时候才显示右侧内容，否则不显示
            var treeNode1 = layerTree.zTree.getNodeByParam("id", treeNode.maps.bsm);
            if (treeNode1 != null) {
                //YXJK.power.init(treeNode);
                // layerTree.onClick(null,null,treeNode1);
                // if (!treeNode1.checked){
                //     layerTree.zTree.checkNode(treeNode1,true,true,true);
                // }
                //进入BimTree分层浏览场景
                //layerTree.inToBimTreeScene(treeNode1);
                //定位
                layerTree.locateBuildingByID(treeNode.maps.bsm, "TRANSFORMER_ROOM_ANNOTATION");
            }
        }, true, YXJK.mo.$yxjk_search,1);
    },
    init: function (treeNode) {
        rightBoxShow();
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/power/detail/" + treeNode.id, function (result) {
            //YXJK.power.edglEcharts();
            //YXJK.power.eddyEcharts();
        }, YXJK.mo.$yxjk_search);

    },
    eddyEcharts: function () {
        var eddyEcharts = echarts.init(document.getElementById('eddyEcharts'));
        ChartsName.push(eddyEcharts);
        var option = {
            tooltip: {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '',
                    type: 'gauge',
                    radius: '100%',
                    center: ['50%', '50%'],
                    detail: {
                        formatter: '{value}%',
                        color: '#fff',
                        fontSize: '18'
                    },
                    data: [{value: 20, name: ''}],
                    pointer: {
                        width: 5, // 指针宽度
                    },
                    axisLine: {
                        show: true,
                        lineStyle: { // 属性lineStyle控制线条样式
                            color: [ //表盘颜色
                                [0.2, "#45bf36"],//0-20%处的颜色
                                [0.8, "#f4a02c"],//20%-80%处的颜色
                                [1, "#3b8ad7"]//80%-100%处的颜色
                            ],
                            width: 8//表盘宽度
                        }
                    },
                    splitLine: { //分割线样式（及10、20等长线样式）
                        length: 10,
                        lineStyle: { // 属性lineStyle控制线条样式
                            width: 2
                        }
                    },
                    axisTick: { //刻度线样式（及短线样式）
                        length: 8
                    },
                    axisLabel: { //文字样式（及“10”、“20”等文字样式）
                        color: "#fff",
                        distance: 2 //文字离表盘的距离
                    },
                }
            ]
        };
        eddyEcharts.setOption(option);
    },
    edglEcharts: function () {
        var edglEcharts = echarts.init(document.getElementById('edglEcharts'));
        ChartsName.push(edglEcharts);
        var option = {
            tooltip: {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '',
                    type: 'gauge',
                    radius: '100%',
                    center: ['50%', '50%'],
                    detail: {
                        formatter: '{value}%',
                        color: '#fff',
                        fontSize: '18'
                    },
                    data: [{value: 20, name: ''}],
                    pointer: {
                        width: 5, // 指针宽度
                    },
                    axisLine: {
                        show: true,
                        lineStyle: { // 属性lineStyle控制线条样式
                            color: [ //表盘颜色
                                [0.2, "#45bf36"],//0-20%处的颜色
                                [0.8, "#f4a02c"],//20%-80%处的颜色
                                [1, "#3b8ad7"]//80%-100%处的颜色
                            ],
                            width: 8//表盘宽度
                        }
                    },
                    splitLine: { //分割线样式（及10、20等长线样式）
                        length: 10,
                        lineStyle: { // 属性lineStyle控制线条样式
                            width: 2
                        }
                    },
                    axisTick: { //刻度线样式（及短线样式）
                        length: 8
                    },
                    axisLabel: { //文字样式（及“10”、“20”等文字样式）
                        color: "#fff",
                        distance: 2 //文字离表盘的距离
                    },
                }
            ]
        };
        edglEcharts.setOption(option);
    }
}


/**
 * 水电用户
 * @type {{
 *          init: YXJK.meter.init
 *          , initUnlineTree: YXJK.meter.initUnlineTree
 *          , initTree: YXJK.meter.initTree
 *          , initAllTree: YXJK.meter.initAllTree
 *          }}
 */
YXJK.meter = {
    initTree:function(){
        YXJK.meter.initAllTree();
        YXJK.meter.initUnlineTree();
    },
    initAllTree: function () {
        //获取单选按钮的值，默认显示为全部
       YXJK.ztree.initZTree("yxjk_tree_all", YXJK.com.$prefix + "/meterTree", function (event, treeId, treeNode) {
            YXJK.meter.init(treeNode.id, treeNode);
            if (treeNode.level == 2 || treeNode.level == 3) {
                //楼栋和房屋定位
                layerTree.locateBuildingByID(treeNode.maps.dingweiCode, "HOUSE_ANNOTATION");
            }
        }, YXJK.mo.$yxjk_search1,0);

    },
    initUnlineTree:function(){
        //离线状态的树
        YXJK.ztree.initZTree("yxjk_tree_unline", YXJK.com.$prefix + "/meterTree/unline", function (event, treeId, treeNode) {
            YXJK.meter.init(treeNode.id, treeNode);
            if (treeNode.level == 2 || treeNode.level == 3) {
                //楼栋和房屋定位
                layerTree.locateBuildingByID(treeNode.maps.dingweiCode, "HOUSE_ANNOTATION");
            }
        }, YXJK.mo.$yxjk_search2,100);
    },
    init: function (id, Obj) {
        rightBoxShow();
        YXJK.common.removeStreeLightRightPageCss();
        YXJK.com.$right.load(YXJK.com.$prefix + "/meter", function (result) {
            //根节点
            if (Obj.level < 3) {
                $("#area").show();
                $("#house").hide();
                $.get(YXJK.com.$prefix + "/meterInfo?typeid=" + Obj.maps.id + "&queryType=" + Obj.maps.type, function (res) {
                    if (res.code == 0 && res.data.length > 0) {
                        var info = res.data[0];
                        $("#baseInfo").empty();
                        $("#baseInfo").append('<li>名称：<span>' + info.name + '</span></li>\n' +
                            '                    <li>表具数量：<span>' + info.meterCount + '</span></li>');
                        $("#waterMeterInfo").empty();
                        $("#waterMeterInfo").append('<li>本月用量：<span>' + info.waterMonthUseNumber + '吨</span></li>' +
                            '                    <li>昨日用量：<span>' + info.yesterdayWaterNumber + '吨</span></li>' +
                            '                    <li>水表数量：<span>' + info.waterCount + '</span></li>' +
                            '                    <li>水表在线数量：<span>' + info.waterOnlineCount + '</span></li>' +
                            '                    <li>水表离线数量：<span>' + info.waterLineCount + '</span></li>' +
                            '                    <li>水表质疑数量：<span>' + info.waterZyCount + '</span></li>' +
                            '                    <li>最后一次读取时间：<span>' + info.lastReadTime + '</span></li>'
                        );
                        $("#eleMeterInfo").empty();
                        $("#eleMeterInfo").append('<li>本月用量：<span>' + info.eleMonthUseNumber + '度</span></li>' +
                            '                    <li>昨日用量：<span>' + info.yesterdayEleNumber + '度</span></li>' +

                            '                    <li>电表数量：<span>' + info.eleCount + '</span></li>' +
                            '                    <li>电表在线数量：<span>' + info.eleOnlineCount + '</span></li>' +
                            '                    <li>电表离线数量：<span>' + info.eleLineCount + '</span></li>' +
                            '                    <li>电表质疑数量：<span>' + info.eleZyCount + '</span></li>' +
                            '                    <li>最后一次读取时间：<span>' + info.lastReadTime + '</span></li>'
                        );
                    }
                });
            } else {
                $("#area").hide();
                $("#house").show();
                $.get(YXJK.com.$prefix + "/meterInfo?typeid=" + Obj.maps.id + "&queryType=" + Obj.maps.type, function (res) {
                    if (res.code == 0 || res.data.length > 0) {
                        $("#houseBaseInfo").empty();
                        var houseInfo = res.data[0];
                        var html = " <li>房主：<span>" + houseInfo.HouseOwnerName + "</span></li>\n" +
                            "                    <li>联系电话：<span>" + houseInfo.phoneNumber + "</span></li>\n" +
                            "                    <li>详细地址：<span>" + houseInfo.houseAddress + "</span></li>\n" +
                            "                    <li>房屋面积：<span>" + houseInfo.houseUseArea + "㎡</span></li>\n" +
                            "                    <li>房屋类型：<span>" + houseInfo.houseType + "</span></li>\n" +
                            "                    <li>账号类型：<span>" + houseInfo.categoryName + "</span></li>\n";
                        $("#houseBaseInfo").append(html);
                        $("#meterType").empty();
                        $("#meterType").append(" <h6>仪表信息</h6>");
                        for (var i = 0; i < res.data.length; i++) {
                            var meterInfo = res.data[i];
                            var unit = "吨";
                            if (meterInfo.meterType == "电表") {
                                unit = "度";
                            }
                            var yesterNumber = meterInfo.yesterDayUseNumber;
                            if (!isNaN(meterInfo.yesterDayUseNumber)) {
                                yesterNumber += unit;
                            }
                            var div = " <div class=\"table\">\n" +
                                "                <ul class=\"sbxxList\">\n" +
                                "                    <li>表类型：<span>" + meterInfo.meterType + "</span></li>\n" +
                                "                    <li>表名称：<span>" + meterInfo.meterName + "</span></li>\n" +
                                "                    <li>表号：<span>" + meterInfo.meterNo + "</span></li>\n" +
                                "                    <li>表状态：<span>" + meterInfo.meterStatus + "</span></li>\n" +
                                "                    <li>当前读数：<span>" + meterInfo.endNumber + unit + "</span></li>\n" +
                                "                    <li>昨日用量：<span>" + yesterNumber + "</span></li>\n" +
                                "                    <li>最后一次读表时间：<span>" + meterInfo.lastReadTime + "</span></li>\n" +
                                "\n" +
                                "                </ul>\n" +
                                "            </div>";
                            $("#meterType").append(div);
                        }
                    }
                });
            }
        });
    }
}


/**
 * 泵房
 * @type {{
 *      init: YXJK.pump.init
 *      , addTotalGauge1: YXJK.pump.addTotalGauge1 tab1仪盘表
 *      , addGauge: YXJK.pump.addGauge  仪盘表初始化信息
 *      , addTotalGauge2: YXJK.pump.addTotalGauge2 tab2 仪盘表
 *      }}
 */
YXJK.pump = {
    initTree: function () {
        var treeUrl = YXJK.com.$prefix + "/jdxx/pump/tree";
        var treeId = YXJK.mo.$yxjk_monitor_tree;

        rightBoxClose();
        ztree.initZTree(treeId, treeUrl, function (event, treeId, treeNode) {
            if (treeNode.hasOwnProperty('children')) {
                YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
                return;
            }
            //定位
            YXJK.position.init(treeNode.id, "pump", treeNode.code, treeNode.maps.bfid, false, "PUMP_HOUSE_ANNOTATION");
            //泵房右侧信息
            YXJK.pump.init(treeNode.id, treeNode);
        }, true, YXJK.mo.$yxjk_search,1);
    },
    init: function (id, treeNode) {
        rightBoxShow();
        YXJK.common.removeStreeLightRightPageCss();
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/pump", function (result) {
            //20200324修改
            $("#pump_name").text(treeNode.name);
            $("#pump_level").text(treeNode.maps.PLevel);
            //获取泵组个数
            $.get(YXJK.com.$prefix + "/jdxx/pump/point/" + treeNode.id, function (result) {
                if (result.code == 0 && result.data.length > 0) {
                    //移除所有
                   var tabtitle = $(".layui-tab-title li");
                    $.each(tabtitle, function (i) {
                        YXJK.element.tabDelete('demo', $(this).attr("lay-id"));//如果存在就移除默认tab
                    });

                    for (var i = 0; i < result.data.length; i++) {
                        var pointId = result.data[i].id;
                        var point = result.data[i];
                        var title = point.pointName;
                        var attrName = point.attrName;
                        var szylInfo = '<div class="rcxjXxxx">\n' +
                            '                            <h3>市政压力:<span id="szyl_' + pointId + '"></span></h3>\n' +
                            '                        </div>';
                        var contentBase = '<div class = "gaugeBox"><div class="gaugeBox_div">';
                        for (var j = 0; j < attrName.split(",").length; j++) {
                            contentBase += '<div id="char_' + j + '_' + pointId + '" class="pump_char" ></div>';
                        }
                        contentBase += "</div></div>";
                        var contentStatus = '<div class="bplbBox"><h3>泵组数:<span id="pumCount_' + pointId + '"></span>个</h3><div class="table"><div id="yxjk_pump_detail_table_' + pointId + '"></div></div></div>';
                        var content = szylInfo + contentBase + contentStatus;

                        YXJK.element.tabAdd('demo', {
                            title: title,
                            content: content,
                            id: result.data[i].id
                        })
                        if (i == 0) {
                            YXJK.element.tabChange('demo', pointId);
                        }
                    }

                } else {
                    YXJK.pump.showDefaultPumpBase();
                    YXJK.pump.showDefaultPumpStates();
                }
            });


            YXJK.element.on('tab(demo)', function (elem) {
                var id = $(this).attr("lay-id");
                if (elem.index == 0) {
                    YXJK.pump.showPumpPointBase(id);
                    YXJK.pump.showPumpPointStatus(id);
                }
                if (elem.index == 1) {
                    YXJK.pump.showPumpPointBase(id);
                    YXJK.pump.showPumpPointStatus(id);
                }
            })
        });
    },
    /**
     * 对接数据库
     * @param tabId
     */
    showPumpPointBase: function (tabId) {
        var gaugeUrl = YXJK.com.$prefix + "/jdxx/pump/echarts/gauge/" + tabId;
        $.get(gaugeUrl, function (gaugeResult) {
            if (gaugeResult.code == 0 && gaugeResult.data.length > 0) {
                var szyl = gaugeResult.data[0].szyl;
                $("#szyl_" + tabId).text(!isNaN(szyl) ? szyl + "Mpa" : szyl);
                var attrName = gaugeResult.data[0].attrName;
                var attrValue = gaugeResult.data[0].attrValue;
                for (var i = 0; i < attrName.split(",").length; i++) {
                    var name = attrName.split(",")[i];
                    var value = attrValue.split(",")[i];
                    var id = "char_" + i + "_" + tabId;
                    var unit = "bar";
                    if (name == "水箱液位") {
                        unit = "m";
                    }
                    if(name=="输出频率"){
                        unit = "hz";
                    }
                    YXJK.pump.addGauge(YXJK.pu.char, id, value, name, unit);
                }
            }
        });
    },
    /**
     * 对接数据库
     * @param tabId
     */
    showPumpPointStatus: function (tabId) {
        var stateTableUrl = YXJK.com.$prefix + "/jdxx/pump/state/real/table/" + tabId;
        var tableId = "#yxjk_pump_detail_table_" + tabId
        $.get(stateTableUrl, function (stateTableResult) {
            var stateTableDate = stateTableResult.data;
            if (stateTableDate.length > 0) {
                $("#pumCount_" + tabId).text(stateTableDate[0].aupPumpPoint.punpcount);
                //显示详情表格
                YXJK.table.render({
                    elem: tableId
                    , skin: 'nob'
                    , cols: [[ //标题栏
                        {field: 'statusname', title: '名称', align: 'center', width: '20%'},
                        {field: 'status', title: '状态', align: 'center', width: '20%'},
                        {field: 'readtime', title: '读取时间', align: 'center', width: '65%'},
                    ]],
                    data: stateTableDate
                });
            }
        });
    },
    /**
     * 默认
     */
    showDefaultPumpBase: function () {
        var attrName = ['输出频率','水箱液位','出口压力','设定压力'];
        var attrValue = [0,0,0,0];
        for (var i = 0; i < attrName.length; i++) {
            var name = attrName[i];
            var value = attrValue[i];
            var id = "char_" + i;
            var unit = "bar";
            if (name == "水箱液位") {
                unit = "m";
            }
            if(name=="输出频率"){
                unit = "hz";
            }
            YXJK.pump.addGauge(YXJK.pu.char, id, value, name, unit);
        }
    },
    /**
     * 默认状态信息
     */
    showDefaultPumpStates: function () {
        var stateTableUrl = YXJK.com.$prefix + "/jdxx/pump/state/table";
        $.get(stateTableUrl, function (stateTableResult) {
            var stateTableDate = stateTableResult.data;
            //显示详情表格
            YXJK.table.render({
                elem: '#' + YXJK.pu.$yxjk_pump_detail_table
                , skin: 'nob'
                , cols: [[ //标题栏
                    {field: 'stateName', title: '状态名称', align: 'center'},
                    {field: 'currentState', title: '启用时间', align: 'center'},
                    {field: 'enableTime', title: '当前状态', align: 'center'},
                ]],
                data: stateTableDate
            });
        });
    },
    addGauge: function (myChar, id, number, name, unit) {
        if (myChar != "" && myChar != null && typeof myChar != 'undefined') {
            myChar.dispose();
        }
        myChar = echarts.init(document.getElementById(id));
        var option = {
            title: {
                show: true
            },
            tooltip: {
                formatter: "{a} <br/>{b} : {c}"+unit
            },
            series: [{
                name: "泵房信息", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
                type: "gauge", // 系列类型
                radius: "90%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
                center: ["45%", "45%"], // 仪表盘位置(圆心坐标)
                startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
                endAngle: -45, // 仪表盘结束角度,默认 -45
                clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
                axisLine: { // 仪表盘轴线(轮廓线)相关配置。
                    show: false, // 是否显示仪表盘轴线(轮廓线),默认 true。
                    lineStyle: { // 仪表盘轴线样式。
                        opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
                        width: 5 //轴线宽度,默认 30。
                    }
                },
                splitLine: { // 分隔线样式。
                    show: true, // 是否显示分隔线,默认 true。
                    length: 8 // 分隔线线长。支持相对半径的百分比,默认 30。

                },
                axisTick: { // 刻度(线)样式。
                    show: true, // 是否显示刻度(线),默认 true。
                    splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
                    fontSize: 12,
                    length: 4, // 刻度线长。支持相对半径的百分比,默认 8。
                    lineStyle: { // 刻度线样式。
                        color: "#eee", //线的颜色,默认 #eee。
                        opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
                        width: 1, //线度,默认 1。
                        type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
                        shadowBlur: 5, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
                        shadowColor: "#fff" //阴影颜色。支持的格式同color。
                    }
                },
                axisLabel: { // 刻度标签。
                    show: true, // 是否显示标签,默认 true。
                    distance: 5, // 标签与刻度线的距离,默认 5。
                    color: "#fff", // 文字的颜色,默认 #fff。
                    fontSize: 10, // 文字的字体大小,默认 5。
                    formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
                },
                emphasis: { // 高亮的 仪表盘指针样式
                    itemStyle: {
                        //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
                    }
                },
                title: { // 仪表盘标题。
                    show: true, // 是否显示标题,默认 true。
                    offsetCenter: [0, "120%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
                    color: "#fff", // 文字的颜色,默认 #333。
                    fontSize: 14 // 文字的字体大小,默认 15。
                },
                pointer: {
                    width: 6,
                    length: "90%"
                },
                itemStyle: {
                    borderColor: "#fff",
                    borderWidth: 1,
                    color: "red",
                    shadowColor: "rgba(116, 10, 9)",
                    shadowBlur: 10
                },
                detail: {
                    show: true,
                    splitNumber: 15,
                    offsetCenter: [0, "90%"],
                    formatter: function (params) {
                        return params+unit;
                    },
                    textStyle: {
                        color: '#8df7f3',
                        fontSize: 18
                    }
                },
                data: [{value: number, name: name}]
            }]
        };
        if(name =="输出频率"){
            option.series[0].min = 0;
            option.series[0].max = 50;
            option.series[0].axisLine.lineStyle.color = [[50, '#6E96AB']];
        }else {
            if (number < 10) {
                option.series[0].min = 0;
                option.series[0].max = 10;
            } else {
                option.series[0].min = 0;
                option.series[0].max = 100;
            }
        }
        myChar.setOption(option, true);
    }

}


/**
 * 探漏
 * @type {{
 *  init: YXJK.leak.init 初始化方法
 *  , xhbdsData: YXJK.leak.xhbdsData  信号波动
 *  , ycxssData: YXJK.leak.ycxssData  异常数据
 *  }}
 */
YXJK.leak = {
    xhbd_Charts: null,
    ycxs_Charts: null,
    /**
     * 初始化树
     */
    initTree: function () {
        var url = YXJK.com.$prefix + "/jdxx/leak/tree";
        var treeId = YXJK.mo.$yxjk_monitor_tree;

        ztree.initZTree(treeId, url, function (event, treeId, treeNode) {

            if (treeNode.hasOwnProperty('children')) {
                YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
                return;
            }
            //定位
            YXJK.position.init(treeNode.id, "leak", treeNode.title, treeNode.id, false, "LEAKAGE");
            selectedLinePipe(layerCfg.yxjk.leakWhere);
            //探漏
            YXJK.leak.init(treeNode);
        }, true, YXJK.mo.$yxjk_search,0);
    },
    init: function (treeNode) {
        //判断右侧是否展开
        if (rightHasActive()) {
            rightBoxShow();
        } else {
            //默认展示总水表的能耗统计
            YXJK.com.$right.empty();
        }
        YXJK.common.removeStreeLightRightPageCss();
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/leak/" + treeNode.id, function (result) {
            //指定时间的信号振幅数据，异常系数，平均振幅数据
            YXJK.leak.leakRealTimeEcharts(treeNode.maps.siteno, "", function (data) {
                //处理接收的json数据
                console.log(data);
                var xhbdData = data.Data[0];
                var ycxssData = data.Data[1];

                var title1 = treeNode.maps.siteno + "/" + data.RecDatetime;
                var title2 = treeNode.maps.siteno + "/" + data.RecDatetime;
                YXJK.leak.xhbdsData("xhbd_Charts", xhbdData.Categories, xhbdData.SeriesData, title1);
                YXJK.leak.ycxssData("ycxs_Charts", ycxssData.Categories, ycxssData.SeriesData, ycxssData.SeriesData2, title2);

            });
            //监控数据
            $(".jcxxBtn").click(function (obj) {
                YXJK.jcsjData('探漏数据', 'jcsj', treeNode.maps.siteno);
            });
        });
    }
    /**
     *所有设备在指定时间的信号振幅数据，异常系数，平均振幅数据
     */
    , leakRealTimeEcharts: function (id, recDateTime, fn) {
        var data = {};
        if (recDateTime == undefined || recDateTime == "") {
            data = {
                siteno: id
            }
        } else {
            data = {
                recDateTime: recDateTime,//也可以去掉这个字段就会取到此站//点最新一条数据
                siteno: id
            }
        }
        $.ajax({
            url: top.celouService + "/Ajax/HZNYGetCurveGraphValue",
            type: "post",
            data: data,
            dataType: "jsonp",
            success: fn,
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
    }
    /**
     * 信号波动
     */
    , xhbdsData: function (elementId, Categories, SeriesData, title) {
        var xhbd_Charts = echarts.init(document.getElementById(elementId));
        var chartOption = {
            title: {
                text: title,
                subtext: '',
                x: 'center',
                y: 'bottom',
                textStyle: {
                    fontFamily: 'Arial, Verdana, sans...',
                    fontSize: 12,
                    fontStyle: 'normal',
                    fontWeight: 'normal',
                    color: '#fff'
                },
            },
            color: ['#97904a'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '4%',
                right: '5%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: [
                {
                    name: '毫秒',//100μs
                    type: 'category',
                    boundaryGap: false,
                    data: Categories,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        //show:false,
                        lineStyle: {
                            // type: 'dotted',
                            color: '#fff',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                }
            ],
            yAxis: [
                {
                    name: 'K(振动强度)',
                    type: 'value',
                    splitArea: {show: true},
                    axisLine: {
                        lineStyle: {
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
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },
                }
            ],
            series: [
                {
                    type: "bar",
                    itemStyle: {
                        normal: {
                            lineStyle: {
                                color: 'red',
                                width: 0.9
                            }

                        }
                    },
                    data: SeriesData,
                    hoverAnimation: false,
                    showSymbol: false,
                }
            ]
        };
        xhbd_Charts.setOption(chartOption);
    }
    , ycxssData: function (elementId, xAxisData, SeriasData, SeriasData2, title) {
        var ycxs_Charts = echarts.init(document.getElementById(elementId));
        var option = {
            title: {
                text: title,
                subtext: '',
                x: 'center',
                y: 'bottom',
                textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                    fontFamily: 'Arial, Verdana, sans...',
                    fontSize: 12,
                    fontStyle: 'normal',
                    fontWeight: 'normal',
                    color: '#fff'
                },
            },
            tooltip: {
                show: true
            },
            grid: {
                left: '3%',
                right: '1%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: [],
                    axisLine: {
                        lineStyle: {
                            color: '#fff',//x线的颜色
                        }
                    },
                }
            ],
            yAxis: [
                {
                    name: '异常系数',//'K(振动强度)',
                    type: 'value',
                    splitArea: {show: true},
                    max: 1.0,
                    min: 0.0,
                    axisLabel: {
                        formatter: '{value} ',
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',//x线的颜色
                        }
                    },
                    splitLine: {
                        show: false
                    },
                },
                {
                    type: 'value',
                    name: '',
                    min: 0,
                    max: 20,
                    interval: 5,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',//x线的颜色
                        }
                    },
                    splitLine: {
                        show: false
                    },
                }
            ],
            series: [

                {
                    name: '',
                    type: 'line',
                    yAxisIndex: 0,
                    symbolSize: 1,   //设定实心点的大小
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#9e5020'
                            }])
                        }
                    },
                    data: SeriasData
                },
                {
                    name: '111',
                    type: 'line',
                    yAxisIndex: 1,
                    symbolSize: 2,   //设定实心点的大小
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient
                            (0, 0, 0, 1, [{
                                offset: 0,
                                color: '#9e5020'
                            }])
                        }
                    },
                    data: SeriasData2
                }
            ]
        };
        ycxs_Charts.setOption(option);
    }
    //探漏信号波动振幅
    , jcsj_xhbdData: function (elementId, Categories, SeriesData, title) {
        if (YXJK.xhbd_Charts != null) {
            YXJK.xhbd_Charts.dispose();
        }
        YXJK.xhbd_Charts = echarts.init(document.getElementById(elementId));
        var option = {
            title: {
                text: "信号振幅_" + title,
                subtext: '',
                x: 'center',
                y: 'bottom',
                textStyle: {
                    fontFamily: 'Arial, Verdana, sans...',
                    fontSize: 12,
                    fontStyle: 'normal',
                    fontWeight: 'normal',
                    color: '#fff'
                },
            },
            color: ['#97904a'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '4%',
                right: '10%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: [
                {
                    name: "毫秒",
                    type: 'category',
                    data: Categories,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        //show:false,
                        lineStyle: {
                            // type: 'dotted',
                            color: '#fff',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                }
            ],
            yAxis: [
                {
                    name: 'K(振动强度)',
                    type: 'value',
                    axisLine: {
                        //show:false,
                        lineStyle: {
                            // type: 'dotted',
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
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },
                }
            ],
            series: [
                {
                    type: "bar",
                    itemStyle: {
                        normal: {
                            lineStyle: {
                                color: 'red',
                                width: 0.9
                            }

                        }
                    },
                    data: SeriesData,
                    hoverAnimation: false,
                    showSymbol: false,
                }
            ]
        };
        YXJK.xhbd_Charts.setOption(option);
    }
    //探漏
    , jcsj_ycxsData: function (elementId, xAxisData, SeriasData, SeriasData2, title) {
        if (YXJK.ycxs_Charts != null) {
            YXJK.ycxs_Charts.dispose();

        }
        YXJK.ycxs_Charts = echarts.init(document.getElementById(elementId));
        var option = {
            title: {
                text: "异常系数_" + title,
                subtext: '',
                x: 'center',//水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                y: 'bottom',
                textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                    fontFamily: 'Arial, Verdana, sans...',
                    fontSize: 12,
                    fontStyle: 'normal',
                    fontWeight: 'normal',
                    color: '#fff'
                },
            },
            grid: {
                left: '10%',
                right: '1%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                    axisLine: {
                        //show:false,
                        lineStyle: {
                            // type: 'dotted',
                            color: '#fff',//x线的颜色
                        }
                    },
                }
            ],
            yAxis: [
                {
                    name: '异常系数',//'K(振动强度)',
                    type: 'value',
                    splitArea: {show: true},
                    max: 1.0,
                    min: 0.0,
                    axisLabel: {
                        formatter: '{value} ',
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',//x线的颜色
                        }
                    },
                    splitLine: {
                        show: false
                    },
                },
                {
                    type: 'value',
                    name: '',
                    min: 0,
                    max: 20,
                    interval: 5,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',//x线的颜色
                        }
                    },
                    splitLine: {
                        show: false
                    },
                }
            ],
            series: [

                {
                    name: '异常系数',
                    type: 'line',
                    yAxisIndex: 0,
                    symbolSize: 0,   //设定实心点的大小
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#9e5020'
                            }])
                        }
                    },
                    data: SeriasData
                },
                {
                    name: '平均振幅',
                    type: 'line',
                    yAxisIndex: 1,
                    symbolSize: 0,   //设定实心点的大小
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#9e5020'
                            }])
                        }
                    },
                    data: SeriasData2
                }
            ]
        };
        YXJK.ycxs_Charts.setOption(option);
    }
}
/**
 * 分页
 * @type {{
 *          initPage: page.initPage
 *          , getPageParams: (function(*): {curPage: string, pageSize: number})
 *          , renderTemplate: page.renderTemplate
 *          , renderPageData: page.renderPageData}}
 */
var page = {
    /**
     * 分页模板的渲染方法
     * @param templateId 分页需要渲染的模板的id
     * @param resultContentId 模板渲染后显示在页面的内容的容器id
     * @param data 服务器返回的json对象
     */
    renderTemplate: function (templateId, resultContentId, data) {
        layui.use(['form', 'laytpl'], function () {
            var laytpl = layui.laytpl;
            laytpl($("#" + templateId).html()).render(data, function (html) {
                $("#" + resultContentId).html(html);
            });
        });
    },
    /**
     * layuilaypage 分页封装
     * @param laypagepId 分页控件p层的id
     * @param pageParams 分页的参数
     * @param templateId 分页需要渲染的模板的id
     * @param resultContentId 模板渲染后显示在页面的内容的容器id
     * @param url 向服务器请求分页的url链接地址
     */
    renderPageData: function (laypagepId, pageParams, templateId, resultContentId, url) {
        if (pageParams == null) {
            pageParams = {
                curPage: 1,
                pageSize: 5
            }
        }
        $.ajax({
            url: url,
            method: 'post',
            data: pageParams,
            dataType: "jsonp",
            async: true,
            complete: function (XHR, TS) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if ("error" == textStatus) {
                    error("服务器未响应，请稍候再试");
                } else {
                    error("操作失败，textStatus=" + textStatus);
                }
            },
            success: function (data) {
                var jsonObj;
                if ('object' == typeof data) {
                    jsonObj = data;
                } else {
                    jsonObj = JSON.parse(data);
                }
                if (jsonObj.data.length == 0) {
                    return;
                }

                var leakId = jsonObj.data[0].SiteNO;
                var recDateTime = jsonObj.data[0].RecDateTime;

                $("#leakTitle").text(recDateTime + " 监测数据");
                //默认第一个
                //指定时间的信号振幅数据，异常系数，平均振幅数据
                YXJK.leak.leakRealTimeEcharts(leakId, recDateTime, function (data1) {
                    //处理接收的json数据
                    var xhbdData = data1.Data[0];
                    var ycxssData = data1.Data[1];

                    var title1 = leakId + "/" + data1.RecDatetime;
                    var title2 = leakId + "/" + data1.RecDatetime;
                    YXJK.leak.jcsj_xhbdData("xhbd_char", xhbdData.Categories, xhbdData.SeriesData, title1);
                    YXJK.leak.jcsj_ycxsData("ycxs_char", ycxssData.Categories, ycxssData.SeriesData, ycxssData.SeriesData2, title2);
                });
                //表格行点击
                $("#leaktable tbody").on("click", "tr", function () {
                    var td = $(this).find("td");
                    var siteno = td.eq(1).text();
                    var RecDateTime = td.eq(3).text();
                    YXJK.leak.leakRealTimeEcharts(siteno, RecDateTime, function (data) {
                        //处理接收的json数据
                        // console.log(data);
                        var xhbdData = data.Data[0];
                        var ycxssData = data.Data[1];

                        var title1 = siteno + "/" + data.RecDatetime;
                        var title2 = siteno + "/" + data.RecDatetime;
                        $("#leakTitle").text(data.RecDatetime + " 监测数据");
                        YXJK.leak.jcsj_xhbdData("xhbd_char", xhbdData.Categories, xhbdData.SeriesData, title1);
                        YXJK.leak.jcsj_ycxsData("ycxs_char", ycxssData.Categories, ycxssData.SeriesData, ycxssData.SeriesData2, title2);

                    });
                });

                for (var i = 0; i < jsonObj.data.length; i++) {
                    jsonObj.data[i].state = "正常";
                }
                //返回数据绑定末班
                page.renderTemplate(templateId, resultContentId, jsonObj);
                //重新初始化分页插件
                YXJK.laypage.render({
                    elem: laypagepId,
                    cont: laypagepId,
                    curr: jsonObj.curPage,
                    limit: pageParams.pageSize,
                    count: jsonObj.totalRows,
                    limits: [5],
                    layout: ['prev', 'page', 'next', 'skip', 'count'],  //这里面的顺序可以调换，我这里顺序调换过了
                    skip: true,
                    jump: function (obj, first) {//obj是一个object类型。包括了分页的所有配置信息。first一个Boolean类，检测页面是否初始加载。非常有用，可避免无限刷新。
                        pageParams.curPage = obj.curr;
                        pageParams.pageSize = pageParams.pageSize;
                        if (!first) {
                            page.renderPageData(laypagepId, pageParams, templateId, resultContentId, url);

                        }
                    }

                });
            }

        });
    },
    getPageParams: function (id) {
        var today = new Date().format("yyyy/MM/dd");
        var startTime = $("#leak_stime").val() == undefined || $("#leak_stime").val() == '' ? YXJK.common.getFullDate(new Date().setDate(1)) : $("#leak_stime").val();
        var endTime = $("#leak_etime").val() == undefined || $("#leak_etime").val() == '' ? today : $("#leak_etime").val();
        var curPage = $(".layui-laypage-em").next().html() == undefined ? "1" : $(".layui-laypage-em").next().html();
        var pageSize = 5;
        var pageParams = {
            curPage: curPage,
            pageSize: pageSize
        };
        pageParams.siteno = id;
        pageParams.startTime = startTime;
        pageParams.endTime = endTime;
        return pageParams;
    },
    initPage: function (id) {
        page.renderPageData("imovie-page-div", page.getPageParams(id), "page_template_id",
            "page_template_body_id", top.celouService + "/Ajax/HZNYSearchPlacesDataV2");
    }
}

/**
 * 路灯
 * @type {{
 *          loadControlRight: YXJK.streetLight.loadControlRight
 *          , queryAllControl: YXJK.streetLight.queryAllControl
 *          , exportUrl: string, loadRootRight: YXJK.streetLight.loadRootRight
 *          , queryStreeLightControl: YXJK.streetLight.queryStreeLightControl
 *          , loadStreeLightRight: YXJK.streetLight.loadStreeLightRight
 *          , initUnlineTree: YXJK.streetLight.initUnlineTree
 *          , initTree: YXJK.streetLight.initTree
 *          , initAllTree: YXJK.streetLight.initAllTree
 *         }}
 */
YXJK.streetLight = {
    exportUrl: YXJK.com.$prefix + "/streetlight/export",
    initTree: function () {
        //点击首先显示右侧的样式
        YXJK.streetLight.loadRootRight();
        YXJK.streetLight.initAllTree();
        YXJK.element.on('tab(streetlight_tree_tab)', function (elem) {
            NHJG.initDingwei();
            if (elem.index == 1) {
                //勾选初始化图层
                YXJK.streetLight.initUnlineTree();
            }
        });

    },
    initAllTree:function(){
        YXJK.ztree.initZTree("streetlight_tree_all", YXJK.com.$prefix + "/jdxx/streetlight/tree", function (event, treeId, treeNode) {
            //20200306
            if (treeNode.maps.hasOwnProperty('sname')) {
                //控制器定位
                YXJK.position.init(treeNode.maps.sid, "streetlightControl", "streetlightControl", treeNode.maps.sid, false, "CONTROLLER");
                //右侧显示控制器信息
                YXJK.streetLight.loadControlRight(treeNode);

            } else if (treeNode.maps.hasOwnProperty('name')) {
                //路灯定位
                YXJK.position.init(treeNode.maps.lid, "streetlight", treeNode.code, treeNode.maps.lid, false, "STREET_LIGHT");
                //显示右侧数据
                YXJK.streetLight.loadStreeLightRight(treeNode);
            } else {
                YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
                return;
            }

        }, true, YXJK.mo.$yxjk_search3,1);
    },
    initUnlineTree:function(){
        YXJK.ztree.initZTree("streetlight_tree_unline", YXJK.com.$prefix + "/jdxx/streetlight/tree/unline", function (event, treeId, treeNode) {
            //20200306
            if (treeNode.maps.hasOwnProperty('sname')) {
                //控制器定位
                YXJK.position.init(treeNode.maps.sid, "streetlightControl", "streetlightControl", treeNode.maps.sid, false, "CONTROLLER");
                //右侧显示控制器信息
                YXJK.streetLight.loadControlRight(treeNode);

            } else if (treeNode.maps.hasOwnProperty('name')) {
                //路灯定位
                YXJK.position.init(treeNode.maps.lid, "streetlight", treeNode.code, treeNode.maps.lid, false, "STREET_LIGHT");
                //显示右侧数据
                YXJK.streetLight.loadStreeLightRight(treeNode);
            } else {
                YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
                return;
            }

        }, true, YXJK.mo.$yxjk_search4,100);
    },
    /**
     * 显示路灯右侧页面信息
     */
    loadRootRight: function () {
        if (rightHasActive()) {
            rightBoxShow();
        } else {
            YXJK.com.$right.html("")
        }
        YXJK.common.addStreeLightRightPageCss();
        //2020 0306修改为先加载页面，再请求数据
        YXJK.com.$right.load(YXJK.com.$prefix + "/all/streetlightControl", function (result) {
            YXJK.laydate.render({
                elem: '#beginTime'
                , min: YXJK.common.getMinDay()
                , max: YXJK.common.getMaxDay()
                , change: function (value, date, endDate) {
                    $('#beginTime').val(value);
                    $('.layui-laydate').remove();//删除
                }

            });
            YXJK.laydate.render({
                elem: '#endTime'
                , min: YXJK.common.getMinDay()
                , max: YXJK.common.getMaxDay()
                , change: function (value, date, endDate) {
                    $('#endTime').val(value);
                    $('.layui-laydate').remove();//删除
                }

            });

            YXJK.streetLight.queryStreeLightControl();
            YXJK.streetLight.queryAllControl();
            //查询按钮点击
            $("#streetlightControlQuery").click(function (e) {

                YXJK.streetLight.queryStreeLightControl();
            })
        });
    },
    /**
     * 查询所有路灯控制器的总用电量，功率，亮灯数
     */
    queryStreeLightControl: function () {
        //获取查询时间框,默认为今天
        var beginYear = "";
        var beginMonth = ""
        var beginDay = ""

        var endYear = "";
        var endMonth = "";
        var endDay = "";

        var beginTime = YXJK.common.getPreDay();
        var endTime = YXJK.common.getCurrDay();
        if ($("#beginTime").val() != null && $("#beginTime").val() != "") {

            beginYear = $("#beginTime").val().split("-")[0];
            beginMonth = parseInt($("#beginTime").val().split("-")[1], 10);
            beginDay = parseInt($("#beginTime").val().split("-")[2]);

        } else {
            $("#beginTime").val(beginTime);
            beginYear = beginTime.split("-")[0];
            beginMonth = parseInt(beginTime.split("-")[1], 10);
            beginDay = parseInt(beginTime.split("-")[2], 10);
        }
        if ($("#endTime").val() != null && $("#endTime").val() != "") {
            endYear = $("#endTime").val().split("-")[0];
            endMonth = parseInt($("#endTime").val().split("-")[1], 10);
            endDay = parseInt($("#endTime").val().split("-")[2], 10);
        } else {
            $("#endTime").val(endTime);
            endYear = endTime.split("-")[0];
            endMonth = parseInt(endTime.split("-")[1], 10);
            endDay = parseInt(endTime.split("-")[2], 10);
        }
        //默认查询
        var data = {
            "params": {
                "beginYear": beginYear,
                "beginMonth": beginMonth,
                "beginDay": beginDay,
                "endYear": endYear,
                "endMonth": endMonth,
                "endDay": endDay
            }
        }
        $.post(ctx + "screen/yxjk/streelightControl/queryParams", data, function (res) {
            if (res.code == 0 && res.data != null) {
                $("#totalEnergy").text(res.data.totalEnergy + "kw");
                $("#fullpower").text(res.data.luxsTotal + "lux");
                var lightcount = res.data.lightcount;
                var totlacount = res.data.totlacount;

                $("#lightCount").html(lightcount + " <span class='clGreen'>/" + totlacount + "</span>");
            }
        })
    },
    /**
     * 查询所有控制器
     */
    queryAllControl: function () {
        $.get(ctx + "screen/yxjk/streelightControl/getAll", function (res) {
            if (res.code == 0 && res.data.length != null) {
                $("#controls").empty();
                var ul = "";
                for (var i = 0; i < res.data.length; i++) {
                    var info = res.data[i];
                    var status = info.status == "1" ? "在线" : "离线";
                    var li = '<li>\n' +
                        '                        <div class="layui-row">\n' +
                        '                            <div class="layui-col-md12 layui-col-sm12 layui-col-xs12">\n' +
                        '                                <p><label>控制器：</label><span>' + info.sname + '</span></p>\n' +
                        '                            </div>\n' +
                        '                            <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">\n' +
                        '                                <p><label>状态：</label><span>' + status + '</span></p>\n' +
                        '                            </div>\n' +
                        '                            <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">\n' +
                        '                                <p><label>电压：</label><span>' + info.uavg + '</span></p>\n' +
                        '                            </div>\n' +
                        '                            <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">\n' +
                        '                                <p><label>功率：</label><span>' + info.pt + '</span></p>\n' +
                        '                            </div>\n' +
                        '                            <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">\n' +
                        '                                <p><label>电流：</label><span>' + info.it + '</span></p>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </li>';
                    ul += li;
                }
            }
            $("#controls").append(ul);
        })

    },
    /**
     * 点击控制器树显示某一个控制器信息
     * @param treeNode
     */
    loadControlRight: function (treeNode) {
        if (rightHasActive()) {
            rightBoxShow();
        } else {
            YXJK.com.$right.empty();
        }
        //修改右侧的样式
        YXJK.common.removeStreeLightRightPageCss();
        //2020 0306修改为先加载页面，再请求数据
        YXJK.com.$right.load(YXJK.com.$prefix + "/one/streetlightControl", function (result) {
            $.get(ctx + "screen/yxjk/streelightControl/getOneById?sid=" + treeNode.maps.sid + "&cuid=" + treeNode.maps.cuid, function (res) {
                if (res.code == 0 && res.data != null) {
                    var status = res.data.status == "1" ? "在线" : "离线";
                    $("#name").text(treeNode.name);
                    $("#breakers").text(res.data.breakers);
                    $("#lucs").text(res.data.lucs);
                    $("#status").text(status);
                    $("#i").text(res.data.pt);
                    $("#u").text(res.data.uavg);
                    $("#p").text(res.data.pt);

                }
            });
        });

    }
    /**
     * 路灯右侧页面信息
     * @param treeNode
     */
    , loadStreeLightRight: function (treeNode) {

        if (rightHasActive()) {
            rightBoxShow();
        } else {
            YXJK.com.$right.empty();
        }
        //修改右侧的样式
        YXJK.common.removeStreeLightRightPageCss();
        //2020 0306修改为先加载页面，再请求数据
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/streetlight", function (result) {
            //请求数据
            $.get(YXJK.com.$prefix + "/jdxx/streetlight/detail/" + treeNode.maps.luid, function (ldResult) {
                var lddata = ldResult.data;

                $("#name").text(lddata.name);
                $("#status").text(lddata.states);
                $("#i").text(lddata.i);
                $("#u").text(lddata.u);
                $("#p").text(lddata.p);
                $("#pf").text(lddata.pf);
                $("#power").text(lddata.power);
                $("#sj").text(lddata.jcsj);

            });

            //监控数据
            $(".jcsjBtn").click(function (obj) {
                YXJK.jcsjData('路灯数据', 'xhjcsj', treeNode.maps.luid);
            });

        });
    }
}

/**
 * 监控
 * @type {{init: YXJK.monitor.init, hikivisionStartPreview: YXJK.monitor.hikivisionStartPreview, hikivisionInit: YXJK.monitor.hikivisionInit, initTree: YXJK.monitor.initTree}}
 */
YXJK.monitor = {
    initTree: function () {
        var url = YXJK.com.$prefix + "/jdxx/monitor/tree";
        var treeId = YXJK.mo.$yxjk_monitor_tree;


        ztree.initZTree(treeId, url, function (event, treeId, treeNode) {

            if (treeNode.hasOwnProperty('children')) {
                YXJK.zTreeObj.expandNode(treeNode, !treeNode.open);
                return;
            } else {
                YXJK.monitor.init(treeNode.id);
            }

            layerTree.locateBuildingByID(treeNode.id, "MONITOR");


        }, true, YXJK.mo.$yxjk_search,0);
    },
    init: function (cameraIndexCode) {
        //判断右侧是否展开
        // var options = {
        //     height: "30vh",
        //     overflow: "hidden"
        // }
        // rightBoxShow(options);
        rightBoxShow();
        YXJK.common.removeStreeLightRightPageCss();
        YXJK.com.$right.load(YXJK.com.$prefix + "/jdxx/monitor/" + cameraIndexCode, function (result) {
            YXJK.monitor.hikivisionInit(cameraIndexCode);
        });
    },
    hikivisionInit: function (cameraIndexCode) {
        //声明公用变量
        var pubKey = '';

        // 创建播放实例
        function initPlugin(domId, width, height, initCount) {
            //如果插件存在，则直接添加视频监控，如果插件不存在 才初始化插件
            if (YXJK.oWebControl) {
                YXJK.oWebControl.JS_ShowWnd();
                YXJK.monitor.hikivisionStartPreview(YXJK.oWebControl, cameraIndexCode);
            } else {
                var oWebControl = new WebControl({
                    szPluginContainer: domId,                       // 指定容器id
                    iServicePortStart: 15900,                           // 指定起止端口号，建议使用该值
                    iServicePortEnd: 15909,
                    szClassId: "23BF3B0A-2C56-4D97-9C03-0CB103AA8F11",   // 用于IE10使用ActiveX的clsid
                    cbConnectSuccess: function () {                     // 创建WebControl实例成功
                        YXJK.oWebControl = oWebControl;
                        oWebControl.JS_StartService("window", {         // WebControl实例创建成功后需要启动服务
                            dllPath: "./VideoPluginConnect.dll"         // 值"./VideoPluginConnect.dll"写死
                        }).then(function () {                           // 启动插件服务成功
                            oWebControl.JS_CreateWnd(domId, width, height).then(function () { //JS_CreateWnd创建视频播放窗口，宽高可设定
                                init(width, height, oWebControl, cameraIndexCode);  // 创建播放实例成功后初始化
                            });
                        }, function () { // 启动插件服务失败
                        });
                    },
                    cbConnectError: function () { // 创建WebControl实例失败
                        oWebControl = null;
                        $(domId).html("插件未启动，正在尝试启动，请稍候...");
                        WebControl.JS_WakeUp("VideoWebPlugin://"); // 程序未启动时执行error函数，采用wakeup来启动程序
                        initCount++;
                        if (initCount < 3) {
                            setTimeout(function () {
                                initPlugin(domId, width, height, initCount);
                            }, 3000)
                        } else {
                            $(domId).html("插件启动失败，请检查插件是否安装！");
                        }
                    },
                    cbConnectClose: function (bNormalClose) {
                        // 异常断开：bNormalClose = false
                        // JS_Disconnect正常断开：bNormalClose = true
                        console.log("cbConnectClose");
                        oWebControl = null;
                    }
                });
            }
        }

        //初始化
        function init(width, height, oWebControl, cameraIndexCode) {
            getPubKey(function () {

                ////////////////////////////////// 请自行修改以下变量值	////////////////////////////////////
                var appKey = YXJK.config.appKey;                          //综合安防管理平台提供的appkey，必填
                var secret = setEncrypt(YXJK.config.appSecret);   //综合安防管理平台提供的secret，必填
                var ip = YXJK.config.hostIp;                           //综合安防管理平台IP地址，必填
                if (String.isNullOrEmpty(cameraIndexCode)) {
                    cameraIndexCode = YXJK.config.defaultCameraIndexCode;
                }
                var playMode = 0;                                  //初始播放模式：0-预览，1-回放
                var port = 80;                                    //综合安防管理平台端口，若启用HTTPS协议，默认443
                var snapDir = "D:\\SnapDir";                       //抓图存储路径
                var videoDir = "D:\\VideoDir";                     //紧急录像或录像剪辑存储路径
                var layout = "1x1";                                //playMode指定模式的布局
                var enableHTTPS = 0;                               //是否启用HTTPS协议与综合安防管理平台交互，是为1，否为0
                var encryptedFields = 'secret';					   //加密字段，默认加密领域为secret
                var showToolbar = 1;                               //是否显示工具栏，0-不显示，非0-显示
                var showSmart = 1;                                 //是否显示智能信息（如配置移动侦测后画面上的线框），0-不显示，非0-显示
                var buttonIDs = "";  //自定义工具条按钮
                ////////////////////////////////// 请自行修改以上变量值	////////////////////////////////////
                oWebControl.JS_RequestInterface({
                    funcName: "init",
                    argument: JSON.stringify({
                        appkey: appKey,                            //API网关提供的appkey
                        secret: secret,                            //API网关提供的secret
                        ip: ip,                                    //API网关IP地址
                        playMode: playMode,                        //播放模式（决定显示预览还是回放界面）
                        port: port,                                //端口
                        snapDir: snapDir,                          //抓图存储路径
                        videoDir: videoDir,                        //紧急录像或录像剪辑存储路径
                        layout: layout,                            //布局
                        enableHTTPS: enableHTTPS,                  //是否启用HTTPS协议
                        encryptedFields: encryptedFields,          //加密字段
                        showToolbar: showToolbar,                  //是否显示工具栏
                        showSmart: showSmart,                      //是否显示智能信息
                        buttonIDs: buttonIDs                       //自定义工具条按钮
                    })
                }).then(function (oData) {
                    YXJK.monitor.hikivisionStartPreview(oWebControl, cameraIndexCode);
                    oWebControl.JS_Resize(width, height);  // 初始化后resize一次，规避firefox下首次显示窗口后插件窗口未与DIV窗口重合问题
                });
            }, oWebControl);
        }

        //获取公钥
        function getPubKey(callback, oWebControl) {
            oWebControl.JS_RequestInterface({
                funcName: "getRSAPubKey",
                argument: JSON.stringify({
                    keyLength: 1024
                })
            }).then(function (oData) {
                console.log(oData);
                if (oData.responseMsg.data) {
                    pubKey = oData.responseMsg.data;
                    callback()
                }
            })
        }

        //RSA加密
        function setEncrypt(value) {
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(pubKey);
            return encrypt.encrypt(value);
        }

        initPlugin("playWnd", 280, 177, 0);
    },
    hikivisionStartPreview: function (oWebControl, cameraIndexCode) {
        var streamMode = 0;                                     //主子码流标识：0-主码流，1-子码流
        var transMode = 1;                                      //传输协议：0-UDP，1-TCP
        var gpuMode = 0;                                        //是否启用GPU硬解，0-不启用，1-启用
        var wndId = -1;                                         //播放窗口序号（在2x2以上布局下可指定播放窗口）

        cameraIndexCode = cameraIndexCode.replace(/(^\s*)/g, "");
        cameraIndexCode = cameraIndexCode.replace(/(\s*$)/g, "");

        oWebControl.JS_RequestInterface({
            funcName: "startPreview",
            argument: JSON.stringify({
                cameraIndexCode: cameraIndexCode,                //监控点编号
                streamMode: streamMode,                         //主子码流标识
                transMode: transMode,                           //传输协议
                gpuMode: gpuMode,                               //是否开启GPU硬解
                wndId: wndId                                     //可指定播放窗口
            })
        })
    }
}


/**
 * 弹层
 * @param name
 * @param type
 */
YXJK.jcsjData = function (name, type, id) {
    var width, height, offset;
    if (type == 'jcsj') {
        width = $(".insidePageTop").width() * 0.6 + "px";
        height = $(document).height() * 0.7 + "px";
        offset = 'b';

    } else if (type == 'lssj') {
        width = $(".insidePageTop").width() * 0.6 + "px";
        height = $(document).height() * 0.6 + "px";

    } else if (type == 'xhjcsj') {
        width = $(".insidePageTop").width() * 0.6 + "px";
        height = $(document).height() * 0.7 + "px";

    }
    var content = YXJK.com.$prefix + '/jcsj/' + type + "?id=" + id;
    layer.open({
        id: 'sssp',
        title: name,
        type: 2,
        area: [width, height],
        fixed: false, //不固定
        maxmin: false,
        offset: offset,
        skin: 'layer-style',
        // shade: 0,
        content: content,
    })
    ;


}


/**
 * 监控数据
 * @type
 * {
 *   {
 *      init: YXJK_JCSJ.init,
 *      leak_xhbdData: YXJK_JCSJ.leak_xhbdData,
 *      lineCharts: YXJK_JCSJ.lineCharts,
 *      leak_ycxsData: YXJK_JCSJ.leak_ycxsData
 *   }
 * }
 */
var YXJK_JCSJ = {
    init: function (id) {
        layui.use(['util', 'form', 'laypage', 'layer', 'laydate', 'table'], function () {
            var $ = layui.jquery;
            YXJK.form = layui.form;
            YXJK.laydate = layui.laydate;
            YXJK.laypage = layui.laypage;
            YXJK.table = layui.table;


            //时间日期选择
            YXJK.laydate.render({
                elem: '#leak_stime',
                // type: 'datetime',
                format: 'yyyy-MM-dd'
            });
            //时间日期选择
            YXJK.laydate.render({
                elem: '#leak_etime',
                format: 'yyyy-MM-dd',
                // type: 'datetime'
            });

            YXJK.laydate.render({
                elem: '#xhjc_sttime',
                format: 'yyyy-MM-dd',
                // type: 'datetime'
            });
            //时间日期选择
            YXJK.laydate.render({
                elem: '#xhjc_endtime',
                format: 'yyyy-MM-dd',
                // type: 'datetime'
            });

        });
        var $self = this;
        //历史数据
        if (jclx == "lssj") {
            $("#lssj").css("display", "block");
            $self.lineCharts();
            //检测数据
        } else if (jclx == "jcsj") {
            $("#jcsj").css("display", "block");
            //初始化今天的日期
            var today = new Date().format("yyyy-MM-dd");
            //分页监测数据
            page.initPage(id);


            YXJK.leak.xhbdsData("xhbd_char");
            YXJK.leak.ycxssData("ycxs_char");

            //探漏
            $("#leak_query").click(function (f) {
                //分页监测数据
                page.initPage(id);

            });
            $("#leak_reset").click(function (f) {
                //分页监测数据
                $("#leak_stime").val("");
                $("#leak_etime").val("");
                page.initPage(id);

            });
            //路灯
        } else if (jclx == "xhjcsj") {
            $("#xhjcsj").css("display", "block");
            //路灯的
            YXJK_JCSJ.queryJcsj(id);
            $("#query").click(function (q) {
                YXJK_JCSJ.queryJcsj(id);
            });
            //重置
            $("#reset").click(function (o) {
                $("#xhjc_sttime").val("");
                $("#xhjc_endtime").val("");
                YXJK_JCSJ.queryJcsj(id);
            });
            //导出

            $("#exp").click(function (obj) {
                var msg = layer.msg('正在导出数据，请稍后...', {time: false});
                var data = {
                    luid: id,
                    startTime: $("#xhjc_sttime").val() == null || $("#xhjc_sttime").val() == "" || $("#xhjc_sttime").val() == undefined ? "" : $("#xhjc_sttime").val() + " 00:00:00",
                    endTime: $("#xhjc_endtime").val() == null || $("#xhjc_endtime").val() == "" || $("#xhjc_endtime").val() == undefined ? "" : $("#xhjc_endtime").val() + " 23:59:59",
                }
                //alert(data.luid+"-----------"+data.startTime+"--------"+data.endTime);
                $.post(YXJK.streetLight.exportUrl, data, function (result) {
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
            })

        }
    },
    queryJcsj: function (id) {
        /**
         * 路灯检测数据
         * @type {string}
         */
        var startTime = $("#xhjc_sttime").val() == null || $("#xhjc_sttime").val() == "" || $("#xhjc_sttime").val() == undefined ? "" : $("#xhjc_sttime").val() + " 00:00:00";
        var endTime = $("#xhjc_endtime").val() == null || $("#xhjc_endtime").val() == "" || $("#xhjc_endtime").val() == undefined ? "" : $("#xhjc_endtime").val() + " 23:59:59";
        // 历史预警 table
        YXJK.table.render({
            elem: '#lsgzTable',
            height: '460',
            skin: 'nob',
            // size: 'lg',
            limit: 10
            // ,size: 'sm' //小尺寸的表格
            ,
            url: YXJK.com.$prefix + "/jdxx/streetlight/jscj/table?luid=" + id + "&startTime=" + startTime + "&endTime=" + endTime //数据接口
            ,
            cols: [[ //标题栏
                {type: 'numbers', title: '序号', align: 'center', width: '5%'},
                {field: 'name', title: '设备名称', align: 'center', width: '10%'},
                {field: 'i', title: '电流(A)', align: 'center', width: '10%'},
                {field: 'u', title: '电压(v)', align: 'center', width: '10%'},
                {field: 'p', title: '功率(KW)', align: 'center', width: '10%'},
                {field: 'pf', title: '功率因素', align: 'center', width: '10%'},
                {field: 'e', title: '电量', align: 'center', width: '20%'},
                {field: 'time', title: '监测时间', align: 'center', width: '25%'},
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
        })

    },
    /**
     * 历史数据
     */
    lineCharts: function () {
        var myChar = echarts.init(document.getElementById('tbLine'));
        // ChartsName.push(myChar);
        var option = {
            xAxis: {
                type: 'category',
                //data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                axisLine: {
                    lineStyle: {
                        //type: 'dotted',
                        color: '#fff', //x线的颜色
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
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    //show:false,
                    lineStyle: {
                        // type: 'dotted',
                        color: '#fff', //x线的颜色
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
                        color: 'rgba(6,235,235,.4)', //x线的颜色
                    }
                },
            },
            series: [{
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line',
                smooth: true,
                symbolSize: 0,
                itemStyle: {
                    normal: {
                        //color:'#fc8a0f', //折点颜色
                        lineStyle: {
                            color: '#ff9c35' //折线颜色
                        }
                    }
                }
            }]
        };
        myChar.setOption(option, true);
    }

}


/**
 * 定位
 * @type {{init: YXJK.position.init}}
 */
YXJK.position = {
    /**
     * 定位
     * @param id 节点id
     * @param code 节点类型
     * @param areaNo 节点no
     * @param no 定位no
     * @param isTis 是否显示定位详情
     * @param type 定位类型
     */
    init: function (id, code, areaNo, no, isTis, type) {
        $("#dingwei").remove();
        if (isTis == null || isTis) {
            var dwUrl = YXJK.com.$prefix + "/position?id=" + id + "&code=" + code + "&areaNo=" + areaNo;
            $.get(dwUrl, function (result) {
                $(".contentBox.insidePageBox.pr").append(result);
                layerTree.locateBuildingByID(no, type);
                //  if(code=="electricity"||code=="water") { //水电表信息单独处理
                //      $("#waterOrEle").show();
                //      $("#base").hide();
                //      $("#waterHouseOrEleHouse").hide();
                //
                //      var tableType = code=="electricity"?0:1;
                //      var tableName = code=="electricity"?"电表":"水表";
                //      var unit = code=="electricity"?"°":"m³";
                //      $.get(YXJK.com.$prefix + "/buidling/meterInfo?buildingNo=" + areaNo + "&tableType=" + tableType + "&queryType=queryMeter", function (result) {
                //          if (result.code == 0) {
                //              if (result.data.length > 0) {
                //                  //名称
                //                  $("#waterOrEleTitle").text(result.data[0].name);
                //                  $("#buildMeter").empty();
                //                  var p = "<p>详细地址:<span>"+result.data[0].detailName+"</span></p>" +
                //                      "<p>建筑面积:<span>"+result.data[0].buildArea+"m²</span></p>" +
                //                      "<p>表总数:<span>"+result.data[0].meterTotalCount+"</span></p>" +
                //                      "<p>"+tableName+"数量:<span>"+result.data[0].meterCount+"</span></p>"
                //                  $("#buildMeter").append(p);
                //
                //              }
                //          }
                //      });
                //      $.get(YXJK.com.$prefix + "/buidling/meterInfo?buildingNo=" + areaNo + "&tableType=" + tableType + "&queryType=queryNumber", function (result) {
                //          if (result.code == 0) {
                //              if (result.data.length > 0) {
                //                  $("#buildMeter2").empty();
                //                  var p = "<p>本月用量:<span>"+result.data[0].monthUseNumber+""+unit+"</span></p>" +
                //                      "<p>当天用量:<span>"+result.data[0].dayUseNumber+""+unit+"</span></p>"
                //                  $("#buildMeter2").append(p);
                //              }
                //          }
                //      });
                //  }
                // else if(code=="electricityHouse"||code=="waterHouse") {
                //      $("#base").hide();
                //      $("#waterOrEle").hide();
                //      $("#waterHouseOrEleHouse").show();
                //      var tableType = code == "electricityHouse" ? 0 : 1;
                //      var unit = code == "electricityHouse" ? "°" : "m³";
                //      $.get(YXJK.com.$prefix + "/house/meterInfo?houseNo=" + areaNo + "&tableType=" + tableType, function (result) {
                //          if (result.code == 0) {
                //              if (result.data.length > 0) {
                //                  $("#waterOrEleHouseTitle").text(result.data[0].name);
                //                  $("#houseMeter").empty();
                //                  var p = "<p>详细地址:<span>" + result.data[0].name + "</span></p>" +
                //                      "<p>房屋类型:<span>" + result.data[0].HouseTypeName + "</span></p>" +
                //                      "<p>房屋面积:<span>" + result.data[0].buildArea + "m²</span></p>" +
                //                      "<p>当前已用量:<span>" + result.data[0].thisUseNumber + "" + unit + "</span></p>" +
                //                      "<p>最后一次读数:<span>" + result.data[0].endNumber + "</span></p>" +
                //                      "<p>读表时间:<span>" + result.data[0].readTime + "</span></p>"
                //                  $("#houseMeter").append(p)
                //              }
                //          }
                //      });
                //  }else{
                //     $("#base").show();
                //     $("#waterOrEle").hide();
                //     $("#waterHouseOrEleHouse").hide();
                //  }
            })

        } else {
            layerTree.locateBuildingByID(no, type);
        }

    }
}


// 窗口重新初始化
window.onresize = function () {
    for (var i = 0; i < YXJK.ChartsName.length; i++) {
        YXJK.ChartsName[i].resize();
    }
};

/**
 * 树节点
 * @type {{zTreeOnAsyncSuccess: YXJK.ztree.zTreeOnAsyncSuccess, initZTree: (function(*=, *=, *=, *=, *): *), zTreeOnAsyncError: YXJK.ztree.zTreeOnAsyncError}}
 */
YXJK.ztree = {
    /**
     * 初始化树
     * @param treeId 树id
     * @param url 请求地址
     * @param zTreeOnClick 点击事件
     * @param inputId 搜索框id
     * @param expendIndex 展开到那一层，从0开始
     * @returns {*}
     */
    initZTree: function (treeId, url, zTreeOnClick, inputId,expandType) {
        var $self = this;
        var t = $("#" + treeId);
        var setting = {
            view: {
                dblClickExpand: false,
                showIcon: false,
                showLine: true,
                // nameIsHTML: true,//支持后台html拼接
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id_",
                    pIdKey: "pid_",
                    code: "code",
                    rootPId: "0",
                }
            },
            callback: {
                onClick: zTreeOnClick,
                onAsyncSuccess:function (event, treeId, treeNode) {
                    var zTreeObj = $.fn.zTree.getZTreeObj(treeId);

                    // 这个方法是将标准 JSON 嵌套格式的数据转换为简单 Array 格式
                    var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
                    for (var i = 0; i < nodes.length; i++) {
                        // 判断节点是否已经加载过，如果已经加载过则不需要再加载
                        if (!nodes[i].zAsync) {
                            zTreeObj.reAsyncChildNodes(nodes[i], '', true);
                        }
                    }
                    if(expandType==100){
                        zTreeObj.expandAll(true);
                    }else if(expandType==0){
                        //展开节点
                        var nodeList = zTreeObj.getNodes();
                        zTreeObj.expandNode(nodeList[0], true);
                    }

                },
                asyncError:function (event, treeId, treeNode) {
                },
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
            },
        }
        var zTreeObj = $.fn.zTree.init(t, setting);
        new MtrSearchZTree(treeId, inputId);
        return zTreeObj;
    },

}


//运行监控图标点击事件
YXJK.clickPic = function (type) {
    if (viewer) {
        viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
            var scene = viewer.scene;
            if (YXJK.pickedObject != null) {
                if(YXJK.pickedObject.id){
                    if (type == 'streetlight') {
                        YXJK.pickedObject.id._billboard._image._value = YXJK.picImg;
                    } else if (type == 'leak') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shenlou.png';
                    } else if (type == 'monitor') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/jiankong.png';
                    } else if (type == 'waterQuality') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shuizhi.png';
                    } else if (type == 'powerHouse') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang.png';
                    } else if (type == 'pump') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/bengfang.png';
                    } else if (type == 'meter') {
                        YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                    }
                }
            }
            layerTree.removeLocatedBuilding();
            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                YXJK.pickedObject = scene.pick(movement.position);
                //清除上一次操作的Bim模型结果
                GWYX.clearPickPoint();
                if(pickColor != null){
                    //Cesium.Color.clone(pickColor.color,pickColor1);
                    pickColor = null;
                }
                if (YXJK.pickedObject) {
                    if (YXJK.pickedObject.id) {
                        if (YXJK.pickedObject.id._properties["_PDFID"] && type == 'powerHouse') {  //配电房
                            var id = YXJK.pickedObject.id._properties["_PDFID"]._value;
                            var name = YXJK.pickedObject.id._properties["_MC"]._value;
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang-s.png';
                            var treeNode2 = layerTree.zTree.getNodeByParam("id", id);
                            var treeNode = YXJK.zTreeObj.getNodeByParam("name", name, null);
                            if (treeNode2 != null) {
                                if(treeNode){
                                    layerTree.locateBuildingByID(treeNode.maps.bsm, "TRANSFORMER_ROOM_ANNOTATION");
                                    YXJK.zTreeObj.selectNode(treeNode);
                                }
                                layerTree.inToBimTreeScene(treeNode2);
                            }
                        } else if (YXJK.pickedObject.id._properties["_LID"] && type == 'streetlight') {    //路灯
                            var code = YXJK.pickedObject.id._properties["_LID"]._value;
                            var treeNode = YXJK.zTreeObj.getNodeByParam("code", code, null);
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.maps.lid, "STREET_LIGHT");
                                YXJK.zTreeObj.selectNode(treeNode);
                            }
                            YXJK.picImg = YXJK.pickedObject.id._billboard._image._value
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/ludeng-s.png';
                            //请求数据
                            $.get(YXJK.com.$prefix + "/app/streetlight/detail/" + code, function (ldResult) {
                                var lddata = ldResult.data;
                                var html = '';
                                html += '<ul class="sbxxList">' +
                                    '<li>设备名称：<span>' + lddata.name + '</span></li> ' +
                                    '<li>状态：<span>' + lddata.states + '</span></li> ' +
                                    '<li>电流：<span>' + lddata.i + '</span></li> ' +
                                    '<li>电压：<span>' + lddata.u + '</span></li> ' +
                                    '<li>功率：<span>' + lddata.p + '</span></li> ' +
                                    '<li>功率因素：<span>' + lddata.pf + '</span></li> ' +
                                    '<li>电量：<span>' + lddata.power + '</span></li> ' +
                                    '<li>时间：<span>' + lddata.jcsj + '</span></li>' +
                                    '</ul>';
                                layer.open({
                                    type: 1,
                                    title: '路灯详情',
                                    area: ['500px', '400px'],
                                    content: html,
                                    skin: 'layer-style'
                                })
                            });
                        } else if (YXJK.pickedObject.id._properties["_MC"] && type == 'pump') {   //泵房
                            var name = YXJK.pickedObject.id._properties["_MC"]._value;
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/bengfang-s.png';
                            var treeNode = YXJK.zTreeObj.getNodeByParam("name", name, null);
                            //泵房右侧信息
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.maps.bfid, "PUMP_HOUSE_ANNOTATION");
                                YXJK.zTreeObj.selectNode(treeNode);
                                YXJK.pump.init(treeNode.id, treeNode);
                            }
                        } else if (YXJK.pickedObject.id._properties["_FWBM"] && type == 'meter') {    //水电用户
                            var code = YXJK.pickedObject.id._properties["_FWBM"]._value;
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                            var tree = $.fn.zTree.getZTreeObj("yxjk_tree_all");
                            var treeNode = tree.getNodeByParam("code", code, null);
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.maps.dingweiCode, "HOUSE_ANNOTATION");
                                tree.selectNode(treeNode);
                                YXJK.meter.init(treeNode.id, treeNode);
                            }
                        } else if (YXJK.pickedObject.id._properties["_PLACENAME"] && type == 'leak') {    //渗漏
                            var code = YXJK.pickedObject.id._properties["_PLACENAME"]._value;
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shenlou-s.png';
                            var treeNode = YXJK.zTreeObj.getNodeByParam("code", code, null);
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.id, "LEAKAGE");
                                YXJK.zTreeObj.selectNode(treeNode);
                                YXJK.leak.init(treeNode);
                            }
                        } else if (YXJK.pickedObject.id._properties["_SID"] && type == 'monitor') {    //监控
                            var id = YXJK.pickedObject.id._properties["_SID"]._value;
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/jiankong-s.png';
                            var treeNode = YXJK.zTreeObj.getNodeByParam("id", id, null);
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.id, "MONITOR");
                                YXJK.zTreeObj.selectNode(treeNode);
                                YXJK.monitor.init(treeNode.id);
                            }
                        } else if (YXJK.pickedObject.id._properties["_SNAME"] && type == 'waterQuality') {    //水质
                            var name = YXJK.pickedObject.id._properties["_SNAME"]._value;
                            name = name.replace(/（/,"(");
                            name = name.replace(/）/,")");
                            YXJK.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shuizhi-s.png';
                            var treeNode = YXJK.zTreeObj.getNodeByParam("name", name, null);
                            if (treeNode) {
                                layerTree.locateBuildingByID(treeNode.maps.sid, "WATER_MONITOR");
                                YXJK.zTreeObj.selectNode(treeNode);
                                YXJK.waterQuality.rightInit(treeNode.id);
                            }
                        }
                    }
                }
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
    }
}
