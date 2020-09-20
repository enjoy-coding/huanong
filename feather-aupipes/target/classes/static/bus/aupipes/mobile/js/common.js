var menuwidth  = 240; // 边栏宽度
var menuspeed  = 400; // 边栏滑出耗费时间
var $bdy       = $('body');
var $container = $('#cesiumContainer');
var $burger    = $('#hamburgermenu');
var negwidth   = "-"+menuwidth+"px";
var poswidth   = menuwidth+"px";
//初始化
$(function(){
    $('.menubtn').on('click',function(e){
        layerTree.removeLocatedBuilding('local');
        layerTree.clearPic();
        if(viewer){
            changeLayer([]);
            pickedObject = null;
            picImg = null;
            customDraw.clearDrawnShape();
            viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
        if($bdy.hasClass('openmenu')) {
            jsAnimateMenu('close');
        } else {
            jsAnimateMenu('open');
        }
    });

    $('.overlay').on('click', function(e){
        if($bdy.hasClass('openmenu')) {
            jsAnimateMenu('close');
        }
    });

    // 点击图层按钮
    $("#layerBtn").on("click", function() {
        if($("#layerBoxContent").hasClass("hide")) {
            $(this).addClass('active');
            $("#layerBoxContent").removeClass("hide")
        } else {
            $(this).removeClass('active');
            $("#layerBoxContent").addClass("hide");
            $("#menuList li").removeClass('active')
        }
    });

    // 点击菜单按钮
    $("#menuBtn").on("click", function() {
        if($("#menuList").hasClass("hide")) {
            $(this).addClass('active');
            $("#menuList").removeClass("hide")
        } else {
            $(this).removeClass('active');
            $("#menuList").addClass("hide")
        }
    });

    // 弹出层
    $("#menuList li").on("click", function() {
        $(this).addClass("active").siblings("li").removeClass("active");
        $("#layerBox").removeClass("hide")
    });
    $("#layerBox #layerCloseBtn").on("click", function() {
        $("#layerBox").addClass("hide")
    });

    // 2D切换
    $("#mapSwitch").on("click", function() {
        var list = $(".layerChecked");
        if($("#mapSwitch i").hasClass("icond1")) {
            //$(this).find('i').removeClass("icond1").addClass("icond");
            //加载影像
            for(var i=0;i<list.length;i++){
                if(list[i].checked == true && list[i].id == "OBLIQUE_PHOTOGRAPHY"){
                    list[i].checked = false;
                    var obj=layerTree.allNodes.find(function (obj) {
                        return obj.id === "OBLIQUE_PHOTOGRAPHY"
                    });
                    layerTree.removeLayersByLeafNodes([obj]);
                }else if(list[i].checked == false && list[i].id == "dom"){
                    var obj=layerTree.allNodes.find(function (obj) {
                        return obj.id === "dom"
                    });
                    list[i].checked = true;
                    layerTree.addLayersByLeafNodes([obj]);
                    //layerTree.locateHomeF(cfg.positionF[0], cfg.positionF[1], cfg.positionF[2]);
                }
            }
        } else {
            $(this).find('i').removeClass("icond icon").addClass("icond1 icon");
            //加载倾斜摄影
            for(var i=0;i<list.length;i++){
                if(list[i].checked == true && list[i].id == "dom"){
                    list[i].checked = false;
                    var obj=layerTree.allNodes.find(function (obj) {
                        return obj.id === "dom"
                    });
                    layerTree.removeLayersByLeafNodes([obj]);
                }else if(list[i].checked == false && list[i].id == "OBLIQUE_PHOTOGRAPHY"){
                    var obj=layerTree.allNodes.find(function (obj) {
                        return obj.id === "OBLIQUE_PHOTOGRAPHY"
                    });
                    list[i].checked = true;
                    layerTree.addLayersByLeafNodes([obj]);
                    layerTree.locateHome(cfg.position[0], cfg.position[1], cfg.position[2]);
                }
            }
        }
        form.render();

    });

});

//左侧菜单的显示隐藏
function jsAnimateMenu(tog) {
    if (tog == 'open') {
        $("#result").css("display","none");
        $("#result").html("");
        $("#detail").css("display","none");
        $("#detail").html("");
        $("#tapInfo").css("display","none");
        $("#tapInfo").html("");
        $bdy.addClass('openmenu');
        $container.animate({marginRight: negwidth, marginLeft: poswidth}, menuspeed);
        $burger.animate({width: poswidth}, menuspeed);
        $('.overlay').animate({left: poswidth}, menuspeed);

        clearAll();
    }
    if (tog == 'close') {
        $bdy.removeClass('openmenu');
        $container.animate({marginRight: "0", marginLeft: "0"}, menuspeed);
        $burger.animate({width: "0"}, menuspeed);
        $('.overlay').animate({left: "0"}, menuspeed);
    }
}

//属性信息返回点击
function backTo() {
    $("#detail").css("display","none");
    $("#detail").html("");
}

//属性信息返回点击
function backToDetail() {
    $("#result").css("display","block");
    $("#tapInfo").css("display","none");
    $("#tapInfo").html("");
}

//关闭信息列表
function closeInfo(){
    $("#result").css("display","none");
    $("#result").html("");
    layerTree.removeLocatedBuilding('local');
    customDraw.clearDrawnShape();
    //jsAnimateMenu("open");
}

//展开或者关闭
function closeResultMore(){
    if ($(".searchResult").hasClass("searchResultClose")) {
        $(".searchResult").removeClass("searchResultClose");
        $(".shrink").find('i').eq(0).removeClass('iconshousuoshangjiantou').addClass('iconshousuoxiajiantou');

        clearAll();
    } else {
        $(".searchResult").addClass('searchResultClose');
        $(".shrink").find('i').eq(0).removeClass('iconshousuoxiajiantou').addClass('iconshousuoshangjiantou');
    }
}

//搜索结果列表的高
function searchResultHeight(){
    var searchTab = $(".searchResultTab").height();
    var searchResultListHeight = $("#searchResult").height() - searchTab - 42 + 'px';
    $(".searchResultList").height(searchResultListHeight);
}

//获取列表高度
function xjlbResultHeight() {
    var xjlbForm = $("#xjlbForm").height();
    var xjlbResultHeight = ($("#xjlbBottomLayer").height() - xjlbForm - 115) + 'px';
    $("#xjlbListBox").height(xjlbResultHeight)
}

//清除所有
function clearAll(){
    //移除轨迹
    trackPlayback.stop();
    //清除定位
    layerTree.removeLocatedBuilding();
}

ah.proxy({
    onRequest: (config, handler) => {
        handler.next(config);
    },
    onResponse: (response, handler) => {
    	var cttType = response.headers["content-type"];
        if (cttType && cttType.toLocaleLowerCase().indexOf("json") !== -1 && response.config.xhr.responseType !="arraybuffer" && (typeof response.response === "string")) {
        	var text = response.response;
        	if (text.indexOf('"code":500') !== -1) {
        		console.log(text);
        		var vj = JSON.parse(response.response);
        		if (vj.msg) {
	        		if (vj.msg == "未登录或登录超时。请重新登录") {
	        			top.location.href = ctx + "/login";
	        		} else {
	        			setTimeout(function(){
        		    		layer.open({
        						type: 0,
        						title: false,
        						content: result.msg,
        						offset: 'b',
        						icon: 5,
        						shade: 0,
        						resize: false,
        						time: 6000
        					});
        		    	}, 100);
	        		}
        		}
        		handler.reject({
                    config: response.config,
                    type: 'error'
                });
        		return;
        		//throw new SyntaxError("后端错误"+ result.code);
        	}
        }
        handler.next(response)
    }
});

var wxJsSdk = {
    _ready: false,
    getLocation: function (func) {
        var tempFunc = function() {
            wx.getLocation({
                success: function (res) {
                    func(res);
                },
                cancel: function (res) {
                    alert('未能获取地理位置');
                },
                fail: function (err) {
                    //定位错误则关闭本操作，给出提示
                    if(menuType == '关阀停水'){
                        jsAnimateMenu('open');
                    }
                    layer.close(loading);
                    layer.msg('定位失败！');
                }
            });
        };
        if (wxJsSdk._ready) {
            tempFunc();
        } else {
            $.ajax({
                url: weixinService +"/getJsConfig",
                data: {
                    url: location.href.split("#")[0] //encodeURIComponent()
                },
                dataType: "json",
                error: function (request) {
                    alert("请求微信配置发生错误");
                },
                success: function (data) {
                    if (data && data.code == 0) {
                        var config = data.data;
                        wx.config({
                            debug: false,
                            appId: config.appId,
                            timestamp: config.timestamp,
                            nonceStr: config.noncestr,
                            signature: config.signature,
                            jsApiList: ['checkJsApi', 'openLocation', 'getLocation']
                        });
                        wx.checkJsApi({
                            jsApiList: ['getLocation'],
                            success: function (res) {
                                if (res.checkResult.getLocation == false) {
                                    alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');
                                    return;
                                }
                            }
                        });
                        wx.ready(function () {
                        	wxJsSdk._ready = true;
                            tempFunc();
                        });
                        wx.error(function (res) {
                            alert("验证出错");
                        });
                    } else if (data.msg) {
                    	alert(data.msg);
                    } else {
                        alert("系统错误");
                    }
                }
            });
        }
    }
};

//切换图层
function changeLayer(arr,type){
    var list = $(".layerChecked");
    for(var i=0;i<list.length;i++) {
        //先清除除底图外的图层
        if(list[i].checked == true && list[i].id != "dom" && list[i].id != "OBLIQUE_PHOTOGRAPHY"){
            list[i].checked = false;
            var obj = layerTree.allNodes.find(function (obj) {
                return obj.id === list[i].id
            });
            layerTree.removeLayersByLeafNodes([obj]);
        }
    }
    //加载对应图层
    for(var i=0;i<arr.length;i++){
        for(var j=0;j<list.length;j++){
            if(arr[i] == list[j].id){
                list[j].checked = true;
                var obj=layerTree.allNodes.find(function (obj) {
                    return obj.id === arr[i]
                });
                layerTree.addLayersByLeafNodes([obj]);
            }
        }
    }
    form.render();
    if(type != undefined){
        lookPick(type);
    }
}

//点击图标查看信息
var pickedObject = null,picImg = null;
function lookPick(type){
    if(viewer){
        viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
            var scene = viewer.scene;
            if(pickedObject != null){
                if(pickedObject.id){
                    if(pickedObject.id._billboard){
                        if(type == 'STREET_LIGHT'){
                            pickedObject.id._billboard._image._value = picImg;
                        }else if(type == 'LEAKAGE'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shenlou.png';
                        }else if(type == 'MONITOR'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/jiankong.png';
                        }else if(type == 'WATER_MONITOR'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shuizhi.png';
                        }else if(type == 'CONTROLLER'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/kaiguan.png';
                        }else if(type == 'TRANSFORMER_ROOM'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang.png';
                        }else if(type == 'PUMP_HOUSE'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/bengfang.png';
                        }else if(type == 'HOUSE_ANNOTATION'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                        }else if(type == 'HOUSE_ANNOTATION_HS'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                        }else if(type == 'HOUSE_ANNOTATION_HD'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                        }else if(type == 'HOUSE_ANNOTATION_SDYH'){
                            pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                        }
                    }
                }
            }
            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                pickedObject = scene.pick(movement.position);
                if(pickedObject){
                    if(pickedObject.id){
                        loading = layer.load(0, {
                            shade: false
                        });
                        if(type == 'STREET_LIGHT'){
                            if(pickedObject.id._properties["_LID"]){
                                var id=pickedObject.id._properties["_LID"]._value;
                                picImg = pickedObject.id._billboard._image._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/ludeng-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'CONTROLLER'){
                            if(pickedObject.id._properties["_SID"]){
                                var id=pickedObject.id._properties["_SID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/kaiguan-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'LEAKAGE'){
                            if(pickedObject.id._properties["_PLACEID"]){
                                var id=pickedObject.id._properties["_PLACEID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shenlou-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'MONITOR'){
                            if(pickedObject.id._properties["_SID"]){
                                var id=pickedObject.id._properties["_SID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/jiankong-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'WATER_MONITOR'){
                            if(pickedObject.id._properties["_OID"]){
                                pickName = pickedObject.id._properties["_SNAME"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/shuizhi-s.png';
                                var id=pickedObject.id._properties["_OID"]._value;
                                lookDetail("",id);
                            }
                        }else if(type == 'TRANSFORMER_ROOM'){
                            if(pickedObject.id._properties["_PDFID"]){
                                var id=pickedObject.id._properties["_PDFID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang-s.png';
                                var obj=layerTree.allNodes.find(function (obj) {
                                    return obj.id === id
                                });
                                if(obj){
                                    layer.close(loading);
                                    tileset = new Cesium.Cesium3DTileset({
                                        url: aupipeService + obj.dataSourceUrl
                                    });
                                    viewer.scene.primitives.add(tileset);
                                    tileset.readyPromise.then(function (tileset) {
                                        viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                                            duration: 1.5
                                        });
                                    })
                                }else{
                                    layer.close(loading);
                                }
                            }
                        }else if(type == 'PUMP_HOUSE'){
                            if(pickedObject.id._properties["_BFID"]){
                                var id=pickedObject.id._properties["_BFID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/bengfang-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'HOUSE_ANNOTATION'){
                            if(pickedObject.id._properties["_FWBM"]){
                                var id=pickedObject.id._properties["_FWBM"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                lookDetail("",id);
                            }
                        }else if(type == 'HOUSE_ANNOTATION_HS'){    //耗水
                            if(pickedObject.id._properties["_FWBM"]){
                                var id=pickedObject.id._properties["_FWBM"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                $.get(ctx + 'aupipes/nhjg/getAupSideList?typeno='+ id +'&type=water',function(res){
                                    if(res[0]){
                                        var root = res[0].cacheId;
                                        nhjgQuery(root);
                                    }else{
                                        layer.close(loading);
                                    }
                                })
                            }
                        }else if(type == 'HOUSE_ANNOTATION_HD'){    //耗电
                            if(pickedObject.id._properties["_FWBM"]){
                                var id=pickedObject.id._properties["_FWBM"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                $.get(ctx + 'aupipes/nhjg/getAupSideList?typeno='+ id +'&type=e',function(res){
                                    if(res[1]){
                                        var root = res[1].cacheId;
                                        nhjgQuery(root);
                                    }else{
                                        layer.close(loading);
                                    }
                                })
                            }
                        }else if(type == 'HOUSE_ANNOTATION_SDYH'){    //耗电
                            if(pickedObject.id._properties["_FWBM"]){
                                var id=pickedObject.id._properties["_FWBM"]._value;
                                var name=pickedObject.id._properties["_MC"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                $.get(ctx + 'screen/yxjk/meterInfo?typeid='+ id +'&queryType=building',function(res){
                                    if(res.data.length > 0){
                                        $("#detail").html("");
                                        $("#detail").load(ctx + "mobile/sdyhDetail", function (data) {
                                            layer.close(loading);
                                            var vm = new Vue({
                                                el: '.listDetail',
                                                data: {
                                                    list: res.data[0],//属性信息
                                                    name: name,
                                                }
                                            });
                                            $("#detail").css("display", "block");
                                        });
                                    }else{
                                        layer.close(loading);
                                    }
                                })
                            }
                        }
                    }
                }

            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
    }
}
//获取url传参的正则匹配方法   name 要获取的参数名称
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}
