var indexLayer;     //属性查询的弹窗
var layerIndex;     //视频弹窗的序号
var layerInitWidth;     //视频弹窗的宽
var layerInitHeight;    //视频弹窗的高
// 点击按钮收缩左侧
$('.leftBox').on('click', '.leftBtn', function () {
    if (!$('.leftBtn').hasClass('active')) {
        $('.leftBtn').addClass('active');
        $(".leftBox").animate({left: "-18vw"});
    } else {
        $('.leftBtn').removeClass('active');
        $(".leftBox").animate({left: "0"});
    }
});

// 点击按钮收缩右侧
$('.rightBox').on('click', '.rightBtn', function () {
    if (!$('.rightBtn').hasClass('active')) {
        $('.rightBtn').addClass('active');
        $(".rightBox").animate({right: "-18vw"});
    } else {
        $('.rightBtn').removeClass('active');
        $(".rightBox").animate({right: "0"});
    }
});


// 地图按钮展开收缩
$('.contentBox').on('click', '.btnMore', function () {
    var txt = $(this).next('.mapIconTxt');
    if (!txt.hasClass('show')) {
        $(this).next('.mapIconTxt').removeClass('hide').addClass('show');
        $(this).addClass('active')
    } else {
        $(this).next('.mapIconTxt').removeClass('show').addClass('hide');
        $(this).removeClass('active')
    }
});

// 地图按钮展开收缩
$('.contentBox').on('click', '.layerBtn', function () {
    var txt = $(this).next('.mapIconTxt');
    if (!txt.hasClass('show')) {
        $(this).next('.mapIconTxt').removeClass('hide').addClass('show');
        $(this).addClass('active')
    } else {
        $(this).next('.mapIconTxt').removeClass('show').addClass('hide');
        $(this).removeClass('active')
    }
});

// 经过地图按钮显示内容--写法1
$('.mapBtnList').on('mouseenter', 'li', function () {//绑定鼠标进入事件
    // console.log("经过")
    $(this).find('.mapIconCont').removeClass('hide').addClass('show');
    $(this).addClass('active')
//   $(this).find('.rightMore').addClass('active')
});
$('.mapBtnList').on('mouseleave', 'li', function () {//绑定鼠标进入事件
    $(this).find('.mapIconCont').removeClass('show').addClass('hide');
    $(this).removeClass('active')
//  $(this).find('.rightMore').removeClass('active')
});

$(function () {
    //初始值 最大值 所用时间秒 闪烁次数
    function numberTwinkle(min, max, useTime, rate, el) {
        var min = Number(min);
        var max = Number(max);
        var useTime = Number(useTime);
        var rate = Number(rate);
        var jg = (max - min) / rate;
        var timeInterval = (useTime * 1000) / rate;
        var interval = setInterval(function () {
            min += jg;
            var minInt = Number(min.toFixed(0));
            $(el).html(minInt);
            if (minInt === max) {
                clearInterval(interval);
            }
        }, timeInterval);
    }

    numberTwinkle(1, 100, 2, 60, "#number1");
    numberTwinkle(1, 625, 2, 60, "#number2");
    numberTwinkle(1, 86, 2, 60, "#number3");
    numberTwinkle(1, 225, 2, 60, "#number4");
    numberTwinkle(1, 1210, 2, 60, "#number5");
    numberTwinkle(1, 52261, 2, 60, "#number6");
    numberTwinkle(1, 2235, 2, 60, "#number7");
    numberTwinkle(1, 2215, 2, 60, "#number8");
});

// 控制三个按钮点击table
$(".tabBtnGroup").on('click', '.tabBtn', function () {
    // 底部显示table切换
    $(".bottomLayer").animate({bottom: "0"});
    var x = $('.tabBtnGroup .tabBtn').index(this);
    $(this).siblings('.tabBtn').removeClass('active')
    $(this).addClass('active')
    // console.log(x)

    var $li = $(".bottomLayer .layui-tab-title li");
    var $content = $(".bottomLayer .layui-tab-item");

    $li.removeClass("layui-this").eq(x).addClass("layui-this");
    $content.removeClass("layui-show").eq(x).addClass("layui-show");
})

//    点击收缩地图按钮
$(".mapShrinkButton").click(function () {
    if (!$(this).hasClass("active")) {
        $(".mapBtnList").removeClass("hide")
        $(this).addClass("active");
        $(this).find(".triangle").addClass("active")
    } else {
        $(".mapBtnList").addClass("hide")
        $(this).removeClass("active");
        $(this).find(".triangle").removeClass("active")
    }
})

// 底部显示table切换
function closeBtn() {
    $(".bottomLayer").animate({bottom: "-29vh"});
    $("#searchName").val("");
    layerTree.removeLocatedBuilding();
}


// 点击地图弹框关闭按钮
function mapCloseBtn(i) {
    $(i).parent().parent('.mapTxtLayer').hide();
}

/**
 * 右侧关闭
 */
$("#rightBtn").click(function () {
    rightBoxClose();
})

/**
 * 清除所有定位框
 */
function clearDingweiBox(){
    if($("#dingwei").length>0){
        $("#dingwei").remove();
    }
    if($("#dingwei_water").length>0){
        $("#dingwei_water").remove();
    }
}

function closeHikCamera(){
    if (YXJK.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        YXJK.oWebControl.JS_HideWnd();
    }
    //清空右侧
    if (YJCZ.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        YJCZ.oWebControl.JS_HideWnd();
    }
    if (Hik.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        Hik.oWebControl.JS_HideWnd();
    }
}

//右侧关闭关闭
function rightBoxClose() {
    //清空右侧
    if (YXJK.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        YXJK.oWebControl.JS_HideWnd();
    }
    //清空右侧
    if (YJCZ.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        YJCZ.oWebControl.JS_HideWnd();
    }
    if (Hik.oWebControl != null) {
        //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
        Hik.oWebControl.JS_HideWnd();
    }
    clearRightWidth();
    $("#right-panel").css("overflow", "");
    $("#right-panel").empty();
    $(".rightBox").animate({right: "-18vw"}, function () {
        $(".rightBox").animate({bottom: "0vh"});
        $(".rightBox").css("height", "");
    });

    //清除预案动画内容
    /*if (timer) {
        clearInterval(timer);
    }
    $(".planloadingcontent").remove();
    LZXD.clearPic();*/

}

/**
 * 右侧面板展示
 */
function rightBoxShow(options) {
    $("#right-panel").empty();
    $(".rightBox").animate({right: "0vw"});
    if (!String.isNullOrEmpty(options) && options.height) {
        $(".rightBox").animate({height: options.height});
    }
    if (!String.isNullOrEmpty(options) && options.overflow) {
        $("#right-panel").css("overflow", options.overflow);
    }
}


/**
 * 右侧是否折叠
 */
function rightHasActive() {
    if ($(".rightBox").css("right") == "0px") {
        return false;
    }
    return true;
}

/**
 * 左侧面板展开
 */
function leftBoxShow() {
    //左边面板打开，初始化
    $("#left-panel").empty();
    if ($('.leftBtn').hasClass('active')) {
        $('.leftBtn').removeClass('active');
        $(".leftBox").animate({left: "0"});
    }
}

function bottomBoxShow() {
    $(".bottomLayer").animate({bottom: "0"});
}

function bottonBoxHide() {
    $(".bottomLayer").animate({bottom: "-29vh"});
}

/**
 * 菜单切换方法
 */
function menuChangeInit() {
    //清除左边
    $("#left-panel").empty();
    //清除右边
    $("#right-panel").empty();
    //清除底部
    $("#bottom-panel").empty();
    // //隐藏右侧
    // $(".rightBox").animate({right: "-18vw"});
    rightBoxClose();
    //底部隐藏
    $(".bottomLayer").animate({bottom: "-29vh"});
    //清除预警信息中间弹框
    $(".pa.mapTxtBox.mapTxtLayer").css("display", "none");
    //清除运行监控中间定位框
    $(".pa.zs_mapTxtBox.mapTxtLayer").remove();
    //清除地图页面内容
    $('.mapBtnList').find("i").eq(8).click();
    //清除图层
    //layerTree.removeAllLayers();
    //清除决策保障分析事件
    GFTS.clearPic();
    LZXD.clearPic();
    GWYX.clearPickPoint();
    pickType = null;
    if(pickColor != null){
        //Cesium.Color.clone(pickColor.color,res.feature.color);
        pickColor = null;
    }
    Tube.clearTubes(gViewer);
    JCBZ.znpg.drawClear();
    //清除运行监控
    layerTree.removeLocatedBuilding();
    //清除已经定位的配电房bim
    if (layerTree.bimTreeSceneFlag) {
        layerTree.outOfBimTreeScene();
    }
    //清除预案动画的定时函数
    if (timer) {
        clearInterval(timer);
    }
    if (particle1) {
        gViewer.scene.primitives.remove(particle1);
    }
    $(".planloadingcontent").remove();
    if (trackPlayback.isWorking) {//清除轨迹
        trackPlayback.isWorking = false;
        trackPlayback.stop();
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        layerTree.setAlpha(node, 1);
    }
    if (pickFeature.isWorking) {
        selectedLinePipes(layerCfg.ryxj.all);
        pickFeature.stop();
        pickFeature.isWorking = false;
    }

    clearRightWidth();
    clearOWebControl();

    //切换模块清除视频窗口
    if(Hik){
        Hik.uninit();
    }
    if(YXJK){
        if(YXJK.oWebControl){
            YXJK.oWebControl.JS_HideWnd();
            YXJK.oWebControl.JS_Disconnect().then(function () {
                    console.log("断开与插件服务连接成功!");
                },
                function () {
                    console.log("断开与插件服务连接失败!");
                });
        }
    }
    if(spIndex){
        spIndex = 1;
    }
    //切换清除开挖分析
    layerTree.removePrimitiveByGuid('drawing');
    if(drawHandler){
        drawShapeModel = null;
        drawHandler.stopDrawing();
        drawHandler.drawEndEvent.removeEventListener(layerTree.drawModel);
    }

    //清除定位框
    clearDingweiBox();

}
function clearRightWidth(){
    if($(".rightBox").hasClass("streetLampBox")){
        //修改右侧的样式
        $(".rightBox").removeClass("streetLampBox");
    }
    if(!$(".mapShrinkButton").hasOwnProperty("style")){
        //地图按钮
        $(".mapShrinkButton").removeAttr("style","");
    }
    if(!$(".searchBox").hasOwnProperty("style")){
        //地图按钮
        $(".searchBox").removeAttr("style");
    }
    if(!$('.mapBtnList').hasOwnProperty("style")){
        $(".mapBtnList").removeAttr("style");
    }
}
function clearMap() {
    //清除地图页面内容
    $('.mapBtnList').find("i").eq(8).click();

}

/**
 * todo
 * 切换的时候清除视频播放插件
 */
function clearOWebControl() {
    if (YXJK.oWebControl != null) {
        console.log("clear plugin");
    }
}

/**
 * ztree树
 * @type {number}
 */
var ztree = {
    initZTree: function (treeId, url, zTreeOnClick,isSearch,inputId) {
        var $self = this;
        var t = $("#" + treeId);
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
                    pIdKey: "pId",
                    code: "code",
                    rootPId: "",
                }
            },
            callback: {
                onClick: zTreeOnClick,
                onAsyncSuccess: $self.zTreeOnAsyncSuccess,
                asyncError: $self.zTreeOnAsyncError,
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
            },
        }
        var zTreeObj = $.fn.zTree.init(t, setting);
        //是否模糊搜索
        if(isSearch){
            new MtrSearchZTree(treeId, inputId);
        }
        return zTreeObj;
    },
    zTreeOnAsyncError: function (event, treeId, treeNode) {
    },
    // 节点加载完的回调函数，加载方式依旧是分批加载，但是不是等用户展开节点才去加
    // 载，而是让它自动完成加载，这里不好的地方是依旧要加载全部数据，所以必须等待
    // 它加载完才能使用搜索功能
    zTreeOnAsyncSuccess: function (event, treeId, treeNode) {
        var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
        var nodeList = zTreeObj.getNodes();
        zTreeObj.expandNode(nodeList[0], true);
        var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
        // 这个方法是将标准 JSON 嵌套格式的数据转换为简单 Array 格式
        var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
        for (var i = 0; i < nodes.length; i++) {
            // 判断节点是否已经加载过，如果已经加载过则不需要再加载
            if (!nodes[i].zAsync) {
                zTreeObj.reAsyncChildNodes(nodes[i], '', true);
            }
        }
    }
}

/**
 * 时间格式化
 * @param fmt
 * @returns {void | string}
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

//属性弹窗方法
function openAttributeLayer(trs, powerAttribute, type, power) {
    var html = "<div class='attributesBox' id='attributesBox'><table><tbody>";
    if (type == "treeBim") {
        if (power == "high") {
            for (var i in layerCfg.powerHighPro) {
                if (powerAttribute != undefined) {
                    if (i == "容量" || i == "长度") {
                        html += "<tr><th>" + i + "</th><td>" + trs[i] + "</td></tr>";
                    } else {
                        html += "<tr><th>" + i + "</th><td>" + powerAttribute[layerCfg.powerHighPro[i]] + "</td></tr>";
                    }
                } else {
                    html += "<tr><th>" + i + "</th><td>" + trs[i] + "</td></tr>";
                }
            }
        } else {
            for (var i in layerCfg.powerLowPro) {
                if (powerAttribute != undefined) {
                    html += "<tr><th>" + i + "</th><td>" + powerAttribute[layerCfg.powerLowPro[i]] + "</td></tr>";
                } else {
                    html += "<tr><th>" + i + "</th><td>" + trs[i] + "</td></tr>";
                }

            }
        }
    } else {
        html += trs;
    }
    html += "</tbody></table></div>";
    if (indexLayer != undefined) {
        layer.close(indexLayer);
        indexLayer = "";
    }
    indexLayer = layer.open({
        type: 1,
        title: '属性信息',
        area: ['400px', '220px'],
        offset: 'b',
        shade: false,
        content: html,
        skin: 'layer-style',
    });

    //弹框的位置
    var top = $("#layui-layer" + indexLayer)[0].offsetTop;
    $("#layui-layer" + indexLayer).css("top", (top - 30) + "px");

    //弹框的标题高度
    $("#layui-layer" + indexLayer).find(".layui-layer-title").css("height", "30px");
    $("#layui-layer" + indexLayer).find(".layui-layer-title").css("line-height", "30px");
    $("#layui-layer" + indexLayer).find(".layui-layer-setwin").css("top", "8px");

    //当类型为hlzs时，添加一个回路追溯按钮
    /* if(type == "hlzs"){
        $("#layui-layer" + indexLayer).append("<button type='button' class='zs_hlzs' onclick='hlzsClick("+id+")'>回路追溯</button>");
        //属性框的高度
        $("#layui-layer" + indexLayer).find(".layui-layer-content").css("height", "160px");
    } */
}

/**
 * 判断字符串对象是不是为空
 * @type {String.isNullOrEmpty}
 */
String.prototype.isNullOrEmpty = String.isNullOrEmpty = function () {
    if (this === String && arguments.length === 0)
        return true;
    var str = this === String ? arguments[0] : this.toString();
    if (typeof str === "string")
        return /^ *$/.test(str);
    for (var key in str)
        return false;
    return typeof str !== "number" && typeof str !== "boolean";
}


/*
$(function(){
	hookAjax({
		//拦截回调
		onreadystatechange:function(xhr){
			console.log("onreadystatechange called: %O",xhr)
		},
		onload:function(xhr){
			console.log("onload called: %O",xhr)
		},
		//拦截方法
		open:function(arg,xhr){
			console.log("open called: method:%s,url:%s,async:%s",arg[0],arg[1],arg[2])
		}
	});
});
*/

//回路追溯分析
function hlzsClick(id) {
    alert("回路追溯")
}

//加载百度地图午夜蓝
function addBMap() {
    viewer.imageryLayers.addImageryProvider(new Cesium.QQMapsImageryProvider({
        id: 9,
        name: "腾讯地图午夜蓝",
        type: "qqmaps",
        maptype: "map_dark",
        maximumLevel: 17,
        brightness: 1.5,
        visible: true
    }));
}

Hik = {
    config: {
        pubKey: '',
        initCount: 0,
        defaultWidth: 200,
        defaultHeight: 300,
        appKey: "28852076",
        appSecret: "SGt7HK1LiTPyW4NVRUpk",
        hostIp: "10.163.132.200",
        defaultCameraIndexCode: "ec21b4971eab40d2897914986614d952"
    },
    oWebControl: null,
    getPubKey: function (callback, oWebControl) {
        oWebControl.JS_RequestInterface({
            funcName: "getRSAPubKey",
            argument: JSON.stringify({
                keyLength: 1024
            })
        }).then(function (oData) {
            console.log(oData);
            if (oData.responseMsg.data) {
                Hik.config.pubKey = oData.responseMsg.data;
                console.log(Hik.config.pubKey);
                callback()
            }
        })
    },
    setEncrypt: function (value) {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(Hik.config.pubKey);
        return encrypt.encrypt(value);
    },
    initPlugin: function (domId, width, height) {
        var oWebControl = new WebControl({
            szPluginContainer: domId,                       // 指定容器id
            iServicePortStart: 15900,                           // 指定起止端口号，建议使用该值
            iServicePortEnd: 15909,
            szClassId: "23BF3B0A-2C56-4D97-9C03-0CB103AA8F11",   // 用于IE10使用ActiveX的clsid
            cbConnectSuccess: function () {                     // 创建WebControl实例成功
                Hik.oWebControl = oWebControl;
                oWebControl.JS_StartService("window", {         // WebControl实例创建成功后需要启动服务
                    dllPath: "./VideoPluginConnect.dll"         // 值"./VideoPluginConnect.dll"写死
                }).then(function () {                           // 启动插件服务成功
                    oWebControl.JS_CreateWnd(domId, width, height).then(function () { //JS_CreateWnd创建视频播放窗口，宽高可设定
                        Hik.initEnv(width, height, oWebControl);  // 创建播放实例成功后初始化
                    });
                }, function () { // 启动插件服务失败
                });
            },
            cbConnectError: function () { // 创建WebControl实例失败
                Hik.oWebControl = null;
                console.log("插件未启动，正在尝试启动，请稍候...");
                WebControl.JS_WakeUp("VideoWebPlugin://"); // 程序未启动时执行error函数，采用wakeup来启动程序
                Hik.config.initCount++;
                if (Hik.config.initCount < 3) {
                    setTimeout(function () {
                        Hik.initPlugin(domId, width, height);
                    }, 3000)
                } else {
                    console.log("插件启动失败，请检查插件是否安装！");
                }
            },
            cbConnectClose: function (bNormalClose) {
                console.log("cbConnectClose");
                Hik.oWebControl = null;
            }
        });
    },
    initEnv: function (width, height, oWebControl) {
        Hik.getPubKey(function () {
            var appKey = Hik.config.appKey;
            var secret = Hik.setEncrypt(Hik.config.appSecret);
            var ip = Hik.config.hostIp;
            var playMode = 0;
            var port = 80;
            var snapDir = "D:\\SnapDir";
            var videoDir = "D:\\VideoDir";
            var layout = "1x1";
            var enableHTTPS = 0;
            var encryptedFields = 'secret';
            var showToolbar = 1;
            var showSmart = 1;
            var buttonIDs = "";  //自定义工具条按钮
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
                    showSmart: showSmart                     //是否显示智能信息
                    //buttonIDs: buttonIDs                       //自定义工具条按钮
                })
            }).then(function (oData) {
                oWebControl.JS_Resize(width, height);  // 初始化后resize一次，规避firefox下首次显示窗口后插件窗口未与DIV窗口重合问题
            });
        }, oWebControl);
    },
    //视频插件初始化
    init: function (domId, width, height) {
        if (String.isNullOrEmpty(width)) {
            width = Hik.config.defaultWidth;
        }
        if (String.isNullOrEmpty(height)) {
            height = Hik.config.defaultHeight;
        }
        Hik.initPlugin(domId, width, height)
    },
    //调出视频预览插件
    startPreview: function (cameraIndexCode) {
        if (Hik.oWebControl == null) {
            console.log("oWebControl初始化失败，请检查原因!");
            return;
        }
        var streamMode = 0;                                     //主子码流标识：0-主码流，1-子码流
        var transMode = 1;                                      //传输协议：0-UDP，1-TCP
        var gpuMode = 0;                                        //是否启用GPU硬解，0-不启用，1-启用
        var wndId = -1;                                         //播放窗口序号（在2x2以上布局下可指定播放窗口）

        cameraIndexCode = cameraIndexCode.replace(/(^\s*)/g, "");
        cameraIndexCode = cameraIndexCode.replace(/(\s*$)/g, "");

        Hik.oWebControl.JS_RequestInterface({
            funcName: "startPreview",
            argument: JSON.stringify({
                cameraIndexCode: cameraIndexCode,                //监控点编号
                streamMode: streamMode,                         //主子码流标识
                transMode: transMode,                           //传输协议
                gpuMode: gpuMode,                               //是否开启GPU硬解
                wndId: wndId                                     //可指定播放窗口
            })
        })
    },
    stopAllPreview: function () {
        if (Hik.oWebControl == null) {
            console.log("oWebControl初始化失败，请检查原因!");
            return;
        }

        Hik.oWebControl.JS_RequestInterface({
            funcName: "stopAllPreview"
        });
    },
    //反初始化
    uninit: function () {
        if (Hik.oWebControl != null) {
            // 先让窗口隐藏，规避可能的插件窗口滞后于浏览器消失问题
            Hik.oWebControl.JS_HideWnd();
            Hik.oWebControl.JS_Disconnect().then(function () {
                    console.log("断开与插件服务连接成功!");
                },
                function () {
                    console.log("断开与插件服务连接失败!");
                });
        }
    }

}

//默认选中图层
function selectedLinePipe(nameArr) {
    //获取第一层子节点
    var list = $("#tt")[0].children;
    var treeObj = layerTree.zTree;

    //清除半透明倾斜摄影
    if (layerTree && layerTree.zTree) {
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        if (node.checked == true && nameArr.length > 0) {
            layerTree.setAlpha(node, 1);
        }
    }

    //遍历，清除倾斜摄影之外的已勾选的图层
    for (var i = 0; i < list.length; i++) {
        var node = treeObj.getNodeByTId(list[i].id);
        var name = node.name;
        if (name != "基础地图") {
            if (node.checked == true) {
                treeObj.checkNode(node, "", true, true);
            }
        }else{
            for(var j=0;j<node.children.length;j++){
                if(node.children[j].id == '3D_MAP'){
                    for(var key in node.children[j].children){
                        if(node.children[j].children[key].id != 'OBLIQUE_PHOTOGRAPHY'){
                            var nodes = treeObj.getNodeByParam('id',node.children[j].children[key].id,null);
                            treeObj.checkNode(nodes, "", true, true);
                        }else{
                            if(node.children[j].children[key].checked != true){
                                var nodes = treeObj.getNodeByParam('id',node.children[j].children[key].id,null);
                                treeObj.checkNode(nodes, "", true, true);
                            }
                        }
                    }
                }else{
                    var nodes = treeObj.getNodeByParam("id", node.children[j].id, null);
                    if(nodes.checked == true){
                        treeObj.checkNode(nodes, "", true, true);
                    }
                }
            }
        }
    }
    //勾选选中的图层
    for (var j = 0; j < nameArr.length; j++) {
        if (layerTree && layerTree.zTree) {
            var childrenNode = layerTree.zTree.getNodeByParam("id", nameArr[j], null);
            treeObj.checkNode(childrenNode, "", true, true);
        }
    }
}


//大模块切换默认选中给水管线点和段
function selectedLinePipes(nameArr) {
    //获取第一层子节点
    var list = $("#tt")[0].children;
    var treeObj = layerTree.zTree;

    //倾斜摄影不透明
    if (layerTree && layerTree.zTree) {
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        if (node.checked == true) {
            layerTree.setAlpha(node, 1);
        }
    }

    //遍历，清除倾斜摄影之外的已勾选的图层
    for (var i = 0; i < list.length; i++) {
        var node = treeObj.getNodeByTId(list[i].id);
        var name = node.name;
        if (name != "基础地图") {
            if (node.checked == true) {
                treeObj.checkNode(node, "", true, true);
            }
        }else{
            for(var j=0;j<node.children.length;j++){
                if(node.children[j].id == '3D_MAP'){
                    for(var key in node.children[j].children){
                        if(node.children[j].children[key].id != 'OBLIQUE_PHOTOGRAPHY'){
                            var nodes = treeObj.getNodeByParam('id',node.children[j].children[key].id,null);
                            treeObj.checkNode(nodes, "", true, true);
                        }else{
                            if(node.children[j].children[key].checked == false){
                                var nodes = treeObj.getNodeByParam('id',node.children[j].children[key].id,null);
                                treeObj.checkNode(nodes, "", true, true);
                            }
                        }
                    }
                }else{
                    var nodes = treeObj.getNodeByParam('id',node.children[j].id,null);
                    if(nodes.checked == true){
                        treeObj.checkNode(nodes, "", true, true);
                    }
                }
            }
        }
    }

    //勾选选中的图层
    for (var j = 0; j < nameArr.length; j++) {
        if (layerTree && layerTree.zTree) {
            var childrenNode = layerTree.zTree.getNodeByParam("id", nameArr[j], null);
            treeObj.checkNode(childrenNode, "", true, true);
        }
    }
}

//清除所有图层
function clearAllLayer() {
    //管网运行的操作全部清除
    if (typeof GWYX == "object") {
        GWYX.clearPickPoint();
    }
    //获取第一层子节点
    var list = $("#tt")[0].children;
    var treeObj = layerTree.zTree;

    //遍历，清除已勾选的图层
    for (var i = 0; i < list.length; i++) {
        var node = treeObj.getNodeByTId(list[i].id);
        var name = node.name;
        if (node.checked == true) {
            treeObj.checkNode(node, "", true, true);
        }
    }
}

//视频弹窗
var spIndex = 1;
function openVideo(id) {
    var html = "<div class='attributesBox' id='videoMain'></div>";
    if (indexLayer != undefined) {
        layer.close(indexLayer);
        indexLayer = "";
    }
    indexLayer = layer.open({
        type: 1,
        title: '视频监控',
        area: ['500px', '300px'],
        shade: false,
        content: html,
        resize: false,
        skin: 'layer-style',
        success: function (layero, index) {
            layerIndex = index;
            layerInitWidth = $("#layui-layer" + layerIndex).width();
            layerInitHeight = $("#layui-layer" + layerIndex).height() - 35;
            Hik.uninit();
            Hik.init("videoMain", layerInitWidth, layerInitHeight);
            setTimeout(function () {
                Hik.startPreview(id);
            }, 3000);
        },
        end: function () {
            Hik.uninit();
        },
        //拖动结束的回调
        moveEnd: function (layero) {
            Hik.uninit();
            layerInitWidth = $("#layui-layer" + layerIndex).width();
            layerInitHeight = $("#layui-layer" + layerIndex).height() - 35;
            Hik.init("videoMain", layerInitWidth, layerInitHeight);
            setTimeout(function () {
                Hik.startPreview(id);
            }, 2000);
        },
        //监听拉伸
        resizing: function (layero) {

        },
        cancel:function(){
            spIndex = 1;
        }
    });


    //弹框的标题高度
    $("#layui-layer" + indexLayer).find(".layui-layer-title").css("height", "30px");
    $("#layui-layer" + indexLayer).find(".layui-layer-title").css("line-height", "30px");
    $("#layui-layer" + indexLayer).find(".layui-layer-setwin").css("top", "8px");
}

//视频弹窗拉伸
/*$(window).resize(function() {
    resizeLayer(layerIndex,layerInitWidth,layerInitHeight);
});
function resizeLayer(layerIndex,layerInitWidth,layerInitHeight) {
    Hik.uninit();
    var docWidth = $(document).width();
    var docHeight = $(document).height();
    var minWidth = layerInitWidth > docWidth ? docWidth : layerInitWidth;
    var minHeight = layerInitHeight > docHeight ? docHeight : layerInitHeight;
    layer.style(layerIndex, {
        width: minWidth,
        height:minHeight
    });
    Hik.init("videoMain", layerInitWidth, layerInitHeight);
    setTimeout(function () {
        Hik.startPreview(id);
    },2000);
}　*/

var funGetDateStr = function (p_count) {
    var dd = new Date();
    dd.setDate(dd.getDate() + p_count);//获取p_count天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;//获取当前月份的日期
    if (m < 10) {
        m = '0' + m;
    }
    var d = dd.getDate();
    if (d < 10) {
        d = d;
    }
    return y + "-" + m + "-" + d;
}

var getPreThreeDay = function () {
    var three = funGetDateStr(-3).substring(8, 10);
    var two = funGetDateStr(-2).substring(8, 10);
    var one = funGetDateStr(-1).substring(8, 10);
    var array = [];
    array[0] = three
    array[1] = two;
    array[2] = one;
    return array;
}

ah.proxy({
    onRequest: (config, handler) => {
        // console.log(config.url)
        handler.next(config);
    },
    onResponse: (response, handler) => {
        var cttType = response.headers["content-type"];
        if (cttType && cttType.toLocaleLowerCase().indexOf("json") !== -1 && response.config.xhr.responseType != "arraybuffer" && (typeof response.response === "string")) {
            var text = response.response;
            if (text.indexOf("未登录或登录超时。请重新登录") !== -1) {
                top.location.href = ctx + "login";
                handler.reject({
                    config: response.config,
                    type: 'error'
                });
                return;
            }
            if (text.indexOf('"code":500') !== -1) {
                console.log(text);
                var vj = JSON.parse(response.response);
                if (vj.msg) {
                    setTimeout(function () {
                        layer.open({
                            type: 0,
                            title: false,
                            content: vj.msg,
                            offset: 'b',
                            icon: 5,
                            shade: 0,
                            resize: false,
                            time: 6000
                        });
                    }, 100);
                }
                //throw new SyntaxError("后端错误"+ vj.code);
            }
        }
        handler.next(response)
    }
});

//光标焦点事件
function showSearchLabel(){
    $(".searchLabel").css("display","block");
}

//首页的搜索
function searchIndex() {
    $(".searchLabel").css("display","none");
    var list = $("input[name='search']");
    for (var i = 0; i < list.length; i++) {
        if (list[i].checked == true) {
            var type = list[i].value;   //0 搜位置   1搜预警  2搜维修  3搜设备
            var name = $("#searchName").val().trim();  //搜索框内容
            if (type == 0) {
                queryAddress(name,type);
            }else if(type == 1){
                $.get(ctx + "search/warring",{keywords:name},  function (result) {
                    if(JSON.stringify(result.data) != "{}"){
                        resultShow(result.data,type);
                    }else{
                        layer.msg("未搜索到结果！");
                    }
                });
            }else if(type == 2){
                var res = {name: name, faultId: '', "params[beginTime]": '', "params[endTime]:": '', pageNum: 1, pageSize: 5000};
                $.post(ctx + "aupipes/repair/list",res, function (result) {
                    if(result.rows.length > 0){
                        resultShow(result.rows,type);
                    }else{
                        layer.msg("未搜索到结果！");
                    }
                });
            }else{
                $.get(ctx + "search/equipment",{keywords:name}, function (result) {
                    if(JSON.stringify(result.data) != "{}"){
                        resultShow(result.data,type);
                    }else{
                        layer.msg("未搜索到结果！");
                    }
                });
            }
        }
    }
}

var addressArr = {};
//查地名注记
function queryAddress(name,type) {
    var where = "NAME LIKE '%" + name + "%'";
    if(name == ""){
        where = "1=1";
    }
    var formData = new FormData();
    formData.append("geoSRS", "EPSG:4326");
    formData.append("outSRS", "EPSG:4326");
    formData.append("layerId", layerCfg.whereLayerId);
    formData.append("where", where);
    formData.append("geometry", "");
    formData.append("isOverlap", false);
    formData.append("startIndex", 0);
    formData.append("reqCount", 20000);
    $.ajax({
        type: "post",// 请求类型
        url: layerCfg.whereUrl + "/queryserver/query",// 请求URL
        data: formData,
        dataType: "json",// 数据返回类型
        processData: false,  // 不处理数据!
        contentType: false,   // 不设置内容类型!
        cache: false, // 是否缓存
        success: function (result) {
            addressArr = result;
            var arr = [];
            if(result.result.features){
                //selectedLinePipe(layerCfg.yxjk.leakWhere);
                for(var i=0;i<result.result.features.length;i++){
                    var obj = result.result.features[i].properties;
                    arr.push(obj);
                }
                resultShow(arr,type);
            }else{
                layer.msg("未搜索到结果！");
            }
        },
        error: function (error) {
            console.info(error);
        }
    });
}

var tableTab,laypageTab,objectType = 'index';
//结果列表数据展示
function resultShow(data,type){
    layui.use(['table', 'laypage'], function () {
        var $ = layui.jquery;
        tableTab = layui.table;
        laypageTab = layui.laypage;

        var width = ($('.bottomLayer').width() - 30) / 20;
        if(objectType == 'subject'){
            $("#bottom-panel").load(ctx + 'screen/jcbz/searchTab', function (res) {
                $("#searchTab").html("");
                $("#searchTabList").html("");

                var html = '';
                var content = '';
                var id = [],col = [],result = {};
                if(type == 0){
                    html += '<li class="layui-this">地名详情</li>';
                    content += `<div class="layui-tab-item layui-show">
                            <div class="table moreTable page">
                                <div id="addressaddress" lay-filter="addressaddress"></div>
                            </div>
                        </div>`;
                    var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                    for(var i in layerCfg.searchAddPro){
                        var objChildren = {field: i, title: layerCfg.searchAddPro[i], align: 'center'};
                        obj.push(objChildren);
                    }
                    col.push(obj);
                    id.push("addressaddress");
                    result = {address:data};
                }else if(type == 1){
                    for(var key in data){
                        html += '<li>'+ key +'</li>';
                        content += `<div class="layui-tab-item">
                            <div class="table moreTable page">
                                <div id="address`+key+`" lay-filter="address`+key+`"></div>
                            </div>
                        </div>`;
                        id.push("address"+key);
                        var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                        for(var i in layerCfg.searchLook){
                            var objChildren = {field: i, title: layerCfg.searchLook[i], align: 'center'};
                            obj.push(objChildren);
                        }
                        col.push(obj);
                    }

                    result = data;
                }else if(type == 2){
                    html += '<li class="layui-this">维修详情</li>';
                    content += `<div class="layui-tab-item layui-show">
                            <div class="table moreTable page">
                                <div id="addressaddress" lay-filter="addressaddress"></div>
                            </div>
                        </div>`;
                    id.push("addressaddress");
                    var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                    for(var i in layerCfg.searchRepPro){
                        var objChildren = {field: i, title: layerCfg.searchRepPro[i], align: 'center'};
                        obj.push(objChildren);
                    }
                    col.push(obj);
                    result = {address:data};
                }else{
                    for(var key in data){
                        if(data[key].length > 0){
                            html += '<li>'+ key +'</li>';
                            content += `<div class="layui-tab-item">
                            <div class="table moreTable page">
                                <div id="address`+key+`" lay-filter="address`+key+`"></div>
                            </div>
                        </div>`;
                            id.push("address"+key);
                            var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                            if(key == "探漏"){
                                for(var i in layerCfg.searchLeak){
                                    var objChildren = {field: i, title: layerCfg.searchLeak[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }else if(key == "电表"){
                                for(var i in layerCfg.searchElector){
                                    var objChildren = {field: i, title: layerCfg.searchElector[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }else if(key == "水表"){
                                for(var i in layerCfg.searchWater){
                                    var objChildren = {field: i, title: layerCfg.searchWater[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }else if(key == "监控"){
                                for(var i in layerCfg.searchCamera){
                                    var objChildren = {field: i, title: layerCfg.searchCamera[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }else if(key == "水质"){
                                for(var i in layerCfg.searchQuality){
                                    var objChildren = {field: i, title: layerCfg.searchQuality[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }else if(key == "路灯"){
                                for(var i in layerCfg.searchLight){
                                    var objChildren = {field: i, title: layerCfg.searchLight[i], align: 'center'};
                                    obj.push(objChildren);
                                }
                            }
                            col.push(obj);
                        }
                    }
                    result = data;
                }
                $("#searchTab").html(html);
                $("#searchTabList").html(content);
                $("#searchTab").find("li").eq(0).addClass("layui-this");
                $("#searchTabList").find(".layui-tab-item").eq(0).addClass("layui-show");

                tableRender(id, result ,col, type);
            });
        }else{
            $("#searchTab").html("");
            $("#searchTabList").html("");

            var html = '';
            var content = '';
            var id = [],col = [],result = {};
            if(type == 0){
                html += '<li class="layui-this">地名详情</li>';
                content += `<div class="layui-tab-item layui-show">
                            <div class="table moreTable page">
                                <div id="addressaddress" lay-filter="addressaddress"></div>
                            </div>
                        </div>`;
                var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                for(var i in layerCfg.searchAddPro){
                    var objChildren = {field: i, title: layerCfg.searchAddPro[i], align: 'center'};
                    obj.push(objChildren);
                }
                col.push(obj);
                id.push("addressaddress");
                result = {address:data};
            }else if(type == 1){
                for(var key in data){
                    html += '<li>'+ key +'</li>';
                    content += `<div class="layui-tab-item">
                            <div class="table moreTable page">
                                <div id="address`+key+`" lay-filter="address`+key+`"></div>
                            </div>
                        </div>`;
                    id.push("address"+key);
                    var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                    for(var i in layerCfg.searchLook){
                        var objChildren = {field: i, title: layerCfg.searchLook[i], align: 'center'};
                        obj.push(objChildren);
                    }
                    col.push(obj);
                }

                result = data;
            }else if(type == 2){
                html += '<li class="layui-this">维修详情</li>';
                content += `<div class="layui-tab-item layui-show">
                            <div class="table moreTable page">
                                <div id="addressaddress" lay-filter="addressaddress"></div>
                            </div>
                        </div>`;
                id.push("addressaddress");
                var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                for(var i in layerCfg.searchRepPro){
                    var objChildren = {field: i, title: layerCfg.searchRepPro[i], align: 'center'};
                    obj.push(objChildren);
                }
                col.push(obj);
                result = {address:data};
            }else{
                for(var key in data){
                    if(data[key].length > 0){
                        html += '<li>'+ key +'</li>';
                        content += `<div class="layui-tab-item">
                            <div class="table moreTable page">
                                <div id="address`+key+`" lay-filter="address`+key+`"></div>
                            </div>
                        </div>`;
                        id.push("address"+key);
                        var obj = [{field: 'serial', title: '序号', align: 'center', width: width, templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'}];
                        if(key == "探漏"){
                            for(var i in layerCfg.searchLeak){
                                var objChildren = {field: i, title: layerCfg.searchLeak[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }else if(key == "电表"){
                            for(var i in layerCfg.searchElector){
                                var objChildren = {field: i, title: layerCfg.searchElector[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }else if(key == "水表"){
                            for(var i in layerCfg.searchWater){
                                var objChildren = {field: i, title: layerCfg.searchWater[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }else if(key == "监控"){
                            for(var i in layerCfg.searchCamera){
                                var objChildren = {field: i, title: layerCfg.searchCamera[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }else if(key == "水质"){
                            for(var i in layerCfg.searchQuality){
                                var objChildren = {field: i, title: layerCfg.searchQuality[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }else if(key == "路灯"){
                            for(var i in layerCfg.searchLight){
                                var objChildren = {field: i, title: layerCfg.searchLight[i], align: 'center'};
                                obj.push(objChildren);
                            }
                        }
                        col.push(obj);
                    }
                }
                result = data;
            }
            $("#searchTab").html(html);
            $("#searchTabList").html(content);
            $("#searchTab").find("li").eq(0).addClass("layui-this");
            $("#searchTabList").find(".layui-tab-item").eq(0).addClass("layui-show");

            tableRender(id, result ,col, type);
        }



        bottomBoxShow();

    });

}

function tableRender(id, data ,col, type){
    var tatleHeight = $(".moreTable").height() - 10;
    for(var i=0;i<id.length;i++){
        var cols =col[i];
        var ids = id[i];
        var name = ids.substring(7);
        tableTab.render({
            elem: '#'+ids
            , height: tatleHeight
            , skin: 'nob'
            , limit: 5
            , limits: [5, 10, 20, 30]
            , data: data[name]
            , cols: [cols]
            , page: true
        });

        tableTab.on('rowDouble('+ ids +')', function(obj){
            if(type == 0){
                for(var i = 0;i<addressArr.result.features.length;i++){
                    if(obj.data.OID == addressArr.result.features[i].properties.OID){
                        var geoJsonData = {features:[addressArr.result.features[i]],fields:addressArr.result.fields,type:addressArr.result.type}
                        layerTree.removeLocatedBuilding();
                        layerTree.addGeoJsonLayer(layerTree.reformattedGeoJsonData(geoJsonData), layerTree.setOptionsByType("ADDRESS", true));
                    }
                }
            }else if(type == 1){
                positionToYj(obj.data.formSysName,obj.data.id,obj.data.code);
            }else if(type == 2){
                var point = obj.data.location.split(",");
                var json = {geometry: {type: "Point", coordinates: [Number(point[0]),Number(point[1]),Number(point[2])]},
                properties: obj.data,
                type: "Feature"};

                var geo = {features:[json],type: "FeatureCollection"};
                layerTree.removeLocatedBuilding();
                layerTree.addGeoJsonLayer(layerTree.reformattedGeoJsonData(geo), layerTree.setOptionsByType("ADDRESS", true));
            }else{
                if(obj.data.type == "路灯"){
                    positionToSb(obj.data.lid,"STREET_LIGHT");
                }else if(obj.data.type == "水质"){
                    positionToSb(obj.data.sid,"WATER_MONITOR");
                }else if(obj.data.type == "水表"){
                    positionToSb(obj.data.areaNo,"HOUSE");
                }else if(obj.data.type == "电表"){
                    positionToSb(obj.data.areaNo,"HOUSE");
                }else if(obj.data.type == "监控"){
                    positionToSb(obj.data.sid,"MONITOR");
                }else if(obj.data.type == "探漏"){
                    positionToSb(''+obj.data.placeId+'',"LEAKAGE");
                }
            }
        });
    }

}
//预警定位
function positionToYj(name,itid,id) {
    layerTree.removeLocatedBuilding();
    var positionid='';
    $.ajax({
        url:ctx + 'screen/yjcz/getPosition?itid=' + itid+'&name='+name,
        type:'POST',
        async:false,
        success:function(res){
            positionid=res;
        }
    });
    if (name == '水表') {
        layerTree.locateBuildingByID(positionid.Buildingno,"HOUSE");
    }else if(name == '电表'){
        layerTree.locateBuildingByID(positionid.Buildingno,"HOUSE");
    } else if (name == '监控') {
        layerTree.locateBuildingByID(positionid.cameraIndexCode,"MONITOR");
    } else if (name == '路灯') {
        if(positionid.lid==undefined){
            layerTree.locateBuildingByID(positionid.sid,"CONTROLLER");
        }else{
            layerTree.locateBuildingByID(positionid.lid,"STREET_LIGHT");
        }
    } else if(name=='探漏'){
        layerTree.locateBuildingByID(''+positionid.placeId+'',"LEAKAGE");
        //selectedLinePipe(layerCfg.yjcz.leakWhere);
    }else if(name == '水质'){
        layerTree.locateBuildingByID(positionid.sid,"WATER_MONITOR");
    }else if(name == '泵房'){
        layerTree.locateBuildingByID(positionid.bfid,"PUMP_HOUSE");
    }
}
//设备定位
function positionToSb(code,type){
    layerTree.locateBuildingByID(code,type);
}

//鼠标移除事件
function hideSearchLabel(){
    $(".searchLabel").css("display","none");
}

function addZero(z) {

return z <10 ?"0" + z : z;

}
$(function(){
	function getTime(){
	    var myDate = new Date();
	    var year = myDate.getFullYear();
	    var month = myDate.getMonth() +1;
	    if (month < 10) {
	    	month = "0" + month;
	    }
	    var date = myDate.getDate();
	    if (date < 10) {
	    	date = "0" + date;
	    }
	    var hours = myDate.getHours();
	    if (hours < 10) {
	    	hours = "0" + hours;
	    }
	    var minutes = myDate.getMinutes();
	    if (minutes < 10) {
	    	minutes = "0" + minutes;
	    }
	    var seconds = myDate.getSeconds();
	    if (seconds < 10) {
	    	seconds = "0" + seconds;
	    }
	    $("#currtime").html(year +"年"+ month +"月"+ date +"日 "+ hours +":"+ minutes +":"+ seconds);
	}
	window.setInterval(getTime, 1000);
});

