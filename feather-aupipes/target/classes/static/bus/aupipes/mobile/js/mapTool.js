/*
* 地图上的一些操作事件
* */
var geometryEntityArr = [];
//地图操作点击事件
$('.mapEditBtn').on('click', 'span', function () {
    var type = $(this).find('i').eq(0).attr('class');
    switch (type) {
        case "iconfont icond1 icon"://3D

            break;
        case "iconfont iconjia icon"://放大
            viewer.camera.zoomIn(viewer.camera.positionCartographic.height / Math.abs(Math.sin(viewer.camera.pitch)) * 0.2);
            break;
        case "iconfont iconjian icon"://缩小
            viewer.camera.zoomOut(viewer.camera.positionCartographic.height / Math.abs(Math.sin(viewer.camera.pitch)) * 0.2);
            break;
        case "iconfont icondishangliulan icon"://地上透明
            //找到倾斜摄影
            var obj = layerTree.allNodes.find(function (obj) {
                return obj.id === "OBLIQUE_PHOTOGRAPHY"
            });
            $(this).toggleClass("active");
            if ($(this).hasClass("active")) {
                if (obj.checked) {
                    layerTree.setAlpha(obj, 0.5);
                } else {
                    console.log("未勾选倾斜摄影图层!");
                }
            } else {
                if (obj.checked) {
                    layerTree.setAlpha(obj, 1);
                } else {
                    console.log("未勾选倾斜摄影图层!");
                }
            }
            break;
        case "iconfont iconquantu icon"://全图
            if ($("#mapSwitch i").hasClass("icond1")) {
                layerTree.locateHome(cfg.position[0], cfg.position[1], cfg.position[2]);
            } else {
                layerTree.locateHomeF(cfg.positionF[0], cfg.positionF[1], cfg.positionF[2]);
            }
            break;
        case "iconfont iconposition-fill icon"://定位
            layerTree.clearPic();
            wxJsSdk.getLocation(function (res) {
                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                var speed = res.speed; // 速度，以米/每秒计
                var accuracy = res.accuracy; // 位置精度
                var height = '';
                var citizensBankPark = viewer.entities.add({
                    position: Cesium.Cartesian3.fromDegrees(longitude, latitude, 60),
                    billboard: {
                        image: ctx + 'bus/aupipes/mobile/images/dingwei.png',
                        width: 24,
                        height: 24,
                        scale: 1, //和原始大小相比的缩放比例
                        minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                        verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                    }
                });
                geometryEntityArr.push(citizensBankPark);
                if ($("#mapSwitch i").hasClass("icond1")) {
                    viewer.flyTo(citizensBankPark, {
                        offset: {
                            heading: Cesium.Math.toRadians(0.0),
                            pitch: Cesium.Math.toRadians(-90),
                            range: 500
                        }
                    });
                } else {
                    viewer.flyTo(citizensBankPark, {
                        offset: {
                            heading: Cesium.Math.toRadians(0.0),
                            pitch: Cesium.Math.toRadians(-90),
                            range: 500
                        }
                    });
                }
            });

            break;
        case "iconfont iconsouzhoubian icon"://周边
            $("#result").html("");
            $("#result").css("display", "block");
            $("#result").load(ctx + "mobile/search", function (data) {
                var vm = new Vue({
                    el: '.searchList',
                    data: {
                        pipe: layerTree.zNodes["2dPipe"],//管线数据
                        device: layerTree.zNodes["device"],//智能设备
                    }
                });
                form.render();
                //周边搜索提交按钮
                wxJsSdk.getLocation(function (res) {
                    lat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    long = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                    pickPosition = {longitude: long, latitude: lat};
                    $("input[name=position]")[0].value = long + ',' + lat;
                });
                form.on('submit(searchSubmit)', function (data) {
                    layerTree.clearPic();
                    loading = layer.load(0, {
                        shade: false
                    });
                    resultArr = [];
                    searchIndex = 1;
                    var radius = $("input[name='radius']")[0].value.trim();
                    radius = radius == "" ? 50 : radius;
                    //var point = $("input[name='position']")[0].value;
                    var list = $(".switchBtn");
                    arrChecked = [];
                    layerTree.removeLocatedBuilding('local');
                    // 获取缓冲图形
                    var geometry = {
                        type: "Point",
                        coordinates: [parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude)]
                    };
                    layerTree.clearPic();
                    var citizensBankPark = viewer.entities.add({
                        position: Cesium.Cartesian3.fromDegrees(parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude), 10),
                        billboard: {
                            image: ctx + 'bus/aupipes/mobile/images/dingwei.png',
                            width: 24,
                            height: 24,
                            scale: 1, //和原始大小相比的缩放比例
                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                        }
                    });
                    geometryEntityArr.push(citizensBankPark);
                    var bufferOptions = {
                        url: aupipeService + "/KQGis/rest/services/geometryserver/buffer",
                        geoSRS: "EPSG:4326",
                        outSRS: "EPSG:4326",
                        data: JSON.stringify(geometry),
                        radius: radius
                    };
                    layerTree.getBufferOfGeometry(bufferOptions, function (bufferGeometry) {
                        //添加缓冲面
                        var geoJson = {
                            "type": "FeatureCollection",
                            "features": [
                                {
                                    "type": "Feature",
                                    "properties": {},
                                    "geometry": {
                                        "type": bufferGeometry.type,
                                        "coordinates": bufferGeometry.coordinates
                                    }
                                }
                            ]
                        }
                        layerTree.addGeoJsonLayer(geoJson, layerTree.setOptionsByType());
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].checked == true && list[i].id != "record") {
                                arrChecked.push(list[i]);
                                var obj = layerTree.allNodes.find(function (obj) {
                                    return obj.id === list[i].id
                                });
                                layerTree.searchAround(pickPosition, radius, obj, bufferGeometry, queryResult);
                            } else if (list[i].checked == true && list[i].id == "record") {
                                arrChecked.push(list[i]);
                                var obj = {id: "record", name: "维修记录"};
                                layerTree.searchRepairRecords(pickPosition, radius, obj, queryResult);
                            }
                        }
                        if (arrChecked.length == 0) {
                            layer.msg("请先选择图层！")
                        }
                    });
                });

                $("#cancel").on("click", function () {
                    $("#result").css("display", "none");
                    $("#result").html("");
                    //jsAnimateMenu("open");
                });

                // 隐藏搜索页对面
                $(".searchPositin").on("click", function () {
                    $("#searchBox").addClass("searchCollapse");
                    pickFeature.start("Position", pickFeature.callback);
                });

                /*$(".searchClose").on("click", function () {
                    $("#searchBox").removeClass("searchCollapse")
                });*/

                form.on('switch(switchBtnAll)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchBtn").prop("checked", true);
                        $(".switchDxgxBtn").prop("checked", true);
                        $(".switchBtnZnsb").prop("checked", true);
                        $(".switchBtnNone").prop("checked", false);
                    }
                    form.render();
                });
                form.on('switch(switchBtnNone)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchBtn").prop("checked", false);
                        $(".switchDxgxBtn").prop("checked", false);
                        $(".switchBtnZnsb").prop("checked", false);
                        $(".switchBtnAll").prop("checked", false);
                    }
                    form.render();
                });

                //地下管线
                form.on('switch(switchBtnDxgxAll)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchDxgxBtn").prop("checked", true);
                        $(".switchBtnDxgxNone").prop("checked", false);
                    }
                    form.render();
                });
                form.on('switch(switchBtnDxgxNone)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchDxgxBtn").prop("checked", false);
                        $(".switchBtnDxgxAll").prop("checked", false);
                    }
                    form.render();
                });
                //智能设备
                form.on('switch(switchBtnZnsbAll)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchBtnZnsb").prop("checked", true);
                        $(".switchBtnZnsbNone").prop("checked", false);
                    }
                    form.render();
                });
                form.on('switch(switchBtnZnsbNone)', function (data) {
                    if (data.elem.checked == true) {
                        $(".switchBtnZnsb").prop("checked", false);
                        $(".switchBtnZnsbAll").prop("checked", false);
                    }
                    form.render();
                });

            });
            break;
        case "iconfont iconkaiwafenxi icon"://开挖
            $("#result").html("");
            $("#result").css("display", "block");
            kwfx.init();
            $("#result").load(ctx + "mobile/fx", function (data) {
                xjlbResultHeight();
                form.render();
            });
            break;
        case "iconfont iconshui icon"://关阀
            $("#result").html("");
            $("#result").css("display", "block");
            loading = layer.load(0, {
                shade: false
            });
            $("#result").load(ctx + "mobile/searchResult", function (data) {
                queryGfts(cfg.gftsLayer, radiusGf);
            });
            break;
        case "iconfont iconshandian icon"://拉闸
            $("#result").html("");
            $("#result").css("display", "block");
            changeLayer(['HOUSE_ANNOTATION', 'GD_POINT', 'GD_LINE'], 'HOUSE_ANNOTATION');
            break;
        default:
    }
});

form.on('switch(switchKwfxAll)', function (data) {
    if (data.elem.checked == true) {
        $(".switchBtn").prop("checked", true);
        $(".switchKwfxNone").prop("checked", false);
    }
    if (data.elem.checked == false) {
        $(".switchBtn").prop("checked", false);
        $(".switchKwfxNone").prop("checked", false);
    }
    form.render();
});
form.on('switch(switchKwfxNone)', function (data) {
    if (data.elem.checked == true) {
        $(".switchBtn").prop("checked", false);
        $(".switchKwfxAll").prop("checked", false);
    }
    if (data.elem.checked == false) {
        $(".switchBtn").prop("checked", false);
        $(".switchKwfxAll").prop("checked", false);
    }
    form.render();
});

//自定义绘制类(移动端使用)
var customDraw = {
    drawingMode: "polygon",
    activeShapePoints: [],
    activeShape: undefined,
    floatingPoint: undefined,
    handler: null,
    point:null,
    finalShape: null,
    callback: function (cartesianArray) {
        console.log(layerTree.transformCartesianArrayToGeoJson(this.unique(cartesianArray)));
    },
    //数组去重
    unique: function (arr) {
        if (!Array.isArray(arr)) {
            console.log('type error!')
            return
        }
        var array = [];
        for (var i = 0; i < arr.length; i++) {
            if (array.indexOf(arr[i]) === -1) {
                array.push(arr[i])
            }
        }
        return array;
    },
    createPoint: function (worldPosition) {
        var point = viewer.entities.add({
            position: worldPosition,
            code:'tempPoint',
            point: {
                color: Cesium.Color.WHITE,
                pixelSize: 5,
                heightReference: Cesium.HeightReference.CLAMP_TO_GROUND,
            },
        });
        return point;
    },
    drawShape: function (positionData) {
        var shape;
        if (this.drawingMode === "line") {
            shape = viewer.entities.add({
                polyline: {
                    positions: positionData,
                    clampToGround: true,
                    width: 3,
                },
            });
        } else if (this.drawingMode === "polygon") {
            shape = viewer.entities.add({
                polygon: {
                    hierarchy: positionData,
                    material: new Cesium.Color(1.0, 0.0, 0.0, 0.3),
                    height: 1.5,
                },
            });
        }
        return shape;
    },
    // Redraw the shape so it's not dynamic and remove the dynamic shape.
    terminateShape: function () {
        // this.activeShapePoints.pop();
        this.finalShape = this.drawShape(this.activeShapePoints);
        if (this.drawingMode === "line") {
            this.callback(this.finalShape.line.hierarchy._value);
        } else if (this.drawingMode === "polygon") {
            this.callback(this.finalShape.polygon.hierarchy._value);
        }
        viewer.entities.remove(this.floatingPoint);
        viewer.entities.remove(this.activeShape);
        this.floatingPoint = undefined;
        this.activeShape = undefined;
        this.activeShapePoints = [];
        this.handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        this.handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        this.handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
    },
    onLeftClick: function (event) {
        // We use `viewer.scene.pickPosition` here instead of `viewer.camera.pickEllipsoid` so that
        // we get the correct point when mousing over terrain.
        var earthPosition = viewer.scene.pickPosition(event.position);
        // `earthPosition` will be undefined if our mouse is not over the globe.
        if (Cesium.defined(earthPosition)) {
            if (this.activeShapePoints.length === 0) {
                this.floatingPoint = this.createPoint(earthPosition);
                this.activeShapePoints.push(earthPosition);
                var dynamicPositions = new Cesium.CallbackProperty(function () {
                    if (customDraw.drawingMode === "polygon") {
                        return new Cesium.PolygonHierarchy(customDraw.activeShapePoints);
                    }
                    return customDraw.activeShapePoints;
                }, false);
                this.activeShape = this.drawShape(dynamicPositions);
            }
            this.activeShapePoints.push(earthPosition);
            this.createPoint(earthPosition);
        }
    },
    onMouseMove: function (event) {
        if (Cesium.defined(this.floatingPoint)) {
            var newPosition = viewer.scene.pickPosition(event.endPosition);
            if (Cesium.defined(newPosition)) {
                this.floatingPoint.position.setValue(newPosition);
                this.activeShapePoints.pop();
                this.activeShapePoints.push(newPosition);
            }
        }
    },
    onRightClick: function (event) {
        this.terminateShape();
    },
    // 初始化绘制
    init: function (callback) {
        if (!viewer.scene.pickPositionSupported) {
            window.alert("This browser does not support pickPosition.");
        }
        viewer.screenSpaceEventHandler.removeInputAction(
            Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK
        );
        this.floatingPoint = undefined;
        this.activeShape = undefined;
        this.activeShapePoints = [];
        if (typeof callback === "function") {
            this.callback = callback;
        }
        this.handler = new Cesium.ScreenSpaceEventHandler(viewer.canvas);
        this.handler.setInputAction(this.onLeftClick.bind(this), Cesium.ScreenSpaceEventType.LEFT_CLICK);
        this.handler.setInputAction(this.onMouseMove.bind(this), Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        this.handler.setInputAction(this.onRightClick.bind(this), Cesium.ScreenSpaceEventType.RIGHT_CLICK);
    },
    //清除已绘制的图形
    clearDrawnShape: function () {
        if (this.finalShape) {
            viewer.entities.remove(this.finalShape);
        }
        if(viewer){
            var children = viewer.entities.values;
            for (var x = 0; x < children.length; x++) {
                if(children[x].code == 'tempPoint'){
                    viewer.entities.remove(children[x]);
                    x=x-1;
                }
            }
            this.activeShapePoints = [];
        }
    }
};

var kwfx = {
    area: null,  //面积
    depth: null,     //开挖深度
    drawShape: null, //绘制的图形
    type: 0, //绘制的图形类型 默认圆 0圆 1矩形 2多边形
    geoJson: null,    //图形数据
    jsonArr: [],    //结果集合
    index: 1,    //查询的结果计数
    arrNum: null,
    gxdwData: [], //管线段定位点的数据
    init: function () {
        //Listen draw end event. 监听绘制完成事件.
        drawHandler.drawEndEvent.addEventListener(function (event) {
            switch (event.drawMode) {
                case Cesium.DrawMode.Polygon:
                    kwfx.drawShape = new Cesium.DrawHandler.PolygonPrimitive({
                        guid: 'drawing',
                        positions: event.positions,
                        perPositionHeight: true
                    });
                    kwfx.drawShape.material = new Cesium.Material({
                        fabric: {
                            type: 'Color',
                            uniforms: {
                                color: new Cesium.Color(1.0, 0.0, 0.0, 0.2)
                            }
                        }
                    });
                    viewer.scene.primitives.add(kwfx.drawShape);
                    break;
                case Cesium.DrawMode.Rectangle:
                    kwfx.drawShape = new Cesium.DrawHandler.RectanglePrimitive({
                        guid: 'drawing',
                        rectangle: event.rectangle,
                        //material: Cesium.Material.fromType(Cesium.Material.StripeType)
                    });
                    kwfx.drawShape.material = new Cesium.Material({
                        fabric: {
                            type: 'Color',
                            uniforms: {
                                color: new Cesium.Color(1.0, 0.0, 0.0, 0.2)
                            }
                        }
                    });
                    viewer.scene.primitives.add(kwfx.drawShape);
                    break;
                case Cesium.DrawMode.Circle:
                    kwfx.drawShape = new Cesium.DrawHandler.CirclePrimitive({
                        guid: 'drawing',
                        center: event.center,
                        radius: event.radius,
                        material: Cesium.Material.fromType(Cesium.Material.RimLightingType)
                    });
                    viewer.scene.primitives.add(kwfx.drawShape);
                    break;
                default:
                    break;
            }
            drawHandler.stopDrawing();
            closeResultMore();
        });
    },
    //开始绘制
    draw: function () {
        customDraw.init(function (res) {
            kwfx.drawShape = res;
        });

        kwfx.clear();
        closeResultMore();
    },
    end: function () {
        customDraw.terminateShape();
    },
    //开始挖洞
    startWd: function () {
        //结束绘制
        customDraw.terminateShape();
        //开始分析
        kwfx.depth = $("input[name='depth']").val();//开挖深度
        if (kwfx.drawShape != null) {
            if (kwfx.depth.trim() != "") {
                var geo = layerTree.transformCartesianArrayToGeoJson(kwfx.drawShape);
                var options = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/area",
                    geoSRS: 'EPSG:4326',
                    outSRS: 'EPSG:3857',
                    data: JSON.stringify(
                        {
                            "type": geo.type,
                            "coordinates": geo.coordinates
                        })
                };
                kwfx.geoJson = {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {},
                            "geometry": {
                                "type": geo.type,
                                "coordinates": geo.coordinates
                            }
                        }
                    ]
                }
                layerTree.caculateAreaOfGeometry(options, kwfx.calArea);
                kwfx.startAnalysis();
            } else {
                layer.msg("请输入开挖深度！");
            }
            /*if (kwfx.type == 0) {
                // 笛卡尔坐标转经纬度（单位:弧度）
                var cartographic = Cesium.Cartographic.fromCartesian(kwfx.drawShape.center);
                // 弧度转度
                var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                geo = {longitude: longitude, latitude: latitude};
                var radius = kwfx.drawShape.radius;
                var obj = [{value: Math.PI * radius * radius}];
                this.calArea(obj);
                var geometry = {
                    type: "Point",
                    coordinates: [parseFloat(longitude), parseFloat(latitude)]
                };
                var bufferOptions = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/buffer",
                    geoSRS: "EPSG:4326",
                    outSRS: "EPSG:4326",
                    data: JSON.stringify(geometry),
                    radius: radius
                };
                layerTree.getBufferOfGeometry(bufferOptions, function (bufferGeometry) {
                    kwfx.geoJson = {
                        "type": "FeatureCollection",
                        "features": [
                            {
                                "type": "Feature",
                                "properties": {},
                                "geometry": {
                                    "type": bufferGeometry.type,
                                    "coordinates": bufferGeometry.coordinates
                                }
                            }
                        ]
                    }
                    kwfx.startAnalysis();
                });
            } else if (kwfx.type == 1) {
                geo = layerTree.transformCartesianArrayToGeoJson(layerTree.transformRectangleToCartesianArray(kwfx.drawShape.rectangle));
                var options = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/area",
                    geoSRS: 'EPSG:4326',
                    outSRS: 'EPSG:3857',
                    data: JSON.stringify(
                        {
                            "type": geo.type,
                            "coordinates": geo.coordinates
                        })
                };
                kwfx.geoJson = {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {},
                            "geometry": {
                                "type": geo.type,
                                "coordinates": geo.coordinates
                            }
                        }
                    ]
                }
                kwfx.area = layerTree.caculateAreaOfGeometry(options, this.calArea);
                kwfx.startAnalysis();
            } else if (kwfx.type == 2) {
                geo = layerTree.transformCartesianArrayToGeoJson(kwfx.drawShape.positions);
                var options = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/area",
                    geoSRS: 'EPSG:4326',
                    outSRS: 'EPSG:3857',
                    data: JSON.stringify(
                        {
                            "type": geo.type,
                            "coordinates": geo.coordinates
                        })
                };
                kwfx.geoJson = {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {},
                            "geometry": {
                                "type": geo.type,
                                "coordinates": geo.coordinates
                            }
                        }
                    ]
                }
                layerTree.caculateAreaOfGeometry(options, this.calArea);
                kwfx.startAnalysis();
            }*/
        } else {
            layer.msg("请先绘制图形！");
        }
    },
    //开始分析
    startAnalysis: function () {
        loading = layer.load(0, {
            shade: false
        });
        changeLayer([]);
        //$('#analysisResult').hide();
        $("#searchResult").css("display","none");
        $('#fxtcBox').removeClass('layui-show');
        $('#analysisResult').show();
        $(".fxtj").css("display","block");
        kwfx.index = 1;
        kwfx.jsonArr = [];
        kwfx.gxdwData = [];
        var list = $(".switchBtn");
        var arr = [];
        for (var i = 0; i < list.length; i++) {
            if (list[i].checked == true) {
                arr = arr.concat(cfg.kwfxId[list[i].name]);
            }
        }
        kwfx.arrNum = arr;
        if (arr.length > 0) {
            for (var i = 0; i < arr.length; i++) {
                layerTree.searchAroundKw(arr[i], kwfx.geoJson.features[0].geometry, kwfx.searchResult);
            }
        } else {
            layer.msg("请先勾选图层");
        }
    },
    //清除
    clear: function () {
        $('#fxtcBox').addClass('layui-show');
        layerTree.removePrimitiveByGuid('drawing');
        kwfx.drawShape = null;
        kwfx.area = null;
        kwfx.jsonArr = [];
        kwfx.gxdwData = [];
        changeLayer([]);
        $('#analysisResult').hide();
        customDraw.clearDrawnShape();
    },
    //面积回调
    calArea: function (area) {
        kwfx.area = area[0].value;
        kwfx.depth = $("input[name='depth']").val();//开挖深度
        if (kwfx.depth.trim() != "") {
            $("#sd")[0].innerText = kwfx.depth + '（m）';
            $("#mj")[0].innerText = kwfx.area + '（m2）';
            $("#tj")[0].innerText = kwfx.area * kwfx.depth + '（m3）';
        } else {
            layer.msg("请填写开挖深度！");
        }
    },
    //查询结果
    searchResult: function (data, id, name) {
        if (data.features) {
            var json = {name: name, id: id, data: data};
            kwfx.gxdwData.push(json);

            var arr = [];
            for (var i = 0; i < data.features.length; i++) {
                var obj = data.features[i].properties;
                obj.typeId = id;
                arr.push(obj);
            }
            var obj = {result: arr, id: id, name: name};
            kwfx.jsonArr.push(obj);
        }
        if (kwfx.arrNum.length < kwfx.index) {
            layer.close(loading);
            if (kwfx.jsonArr.length > 0) {
                kwfx.showResult();
                var obj = [];
                for (var i = 0; i < kwfx.jsonArr.length; i++) {
                    obj.push(cfg.kwfxLayer[kwfx.jsonArr[i].name]);
                }
                changeLayer(obj);
            } else {
                layer.msg("未搜索结果！");
            }
        }
    },
    showResult: function () {
        var data = kwfx.jsonArr;
        var html = '';
        var content = '';
        $("#searchResultTab").html('');
        $("#searchResultList").html('');
        for (var key in data) {
            if (key == "0") {
                html += '<span class="active" id="' + data[key].id + '" onclick="kwfx.changeTab(`' + data[key].name + '`,`' + data[key].id + '`)">' + data[key].name + '</span>';
                for (var i = 0; i < data[key].result.length; i++) {
                    if (data[key].name.indexOf('点') > -1) {
                        content += `<li onclick="kwfx.position(\`` + data[key].name + `\`,\`` + data[key].result[i]['OID'] + `\`)">
                            <span class="number">` + (i + 1) + `</span>
                            <p>
                                <!-- <span>OID: <i>` + data[key].result[i]['OID'] + `</i></span> -->
                                <span class="txtC">编号: <i>` + data[key].result[i]['PIPEP_ID'] + `</i></span>
                                <span class="txtR">类型: <i>` + data[key].result[i]['SUBSID'] + `</i></span>
                            </p>
                            <div class="pr txt">
                                位置: <i>` + data[key].result[i]['R_NAME'] + `</i>
                                <div class="more">
                                    <a href="#" onclick="lookDetail(\`` + data[key].name + `\`, \`` + data[key].result[i]['OID'] + `\`)">查看详情</a>
                                </div>
                            </div>
                        </li>`;
                    } else {
                        content += `<li onclick="kwfx.position(\`` + data[key].name + `\`,\`` + data[key].result[i]['OID'] + `\`)">
                            <span class="number">` + (i + 1) + `</span>
                            <p>
                                <span>OID: <i>` + data[key].result[i]['OID'] + `</i></span>
                                <span class="txtC">编号: <i>` + data[key].result[i]['PIPEL_ID'] + `</i></span>
                                <span class="txtR">管径: <i>` + data[key].result[i]['D_S'] + `</i></span>
                            </p>
                            <div class="pr txt">
                                位置: <i>` + data[key].result[i]['R_NAME'] + `</i>
                                <div class="more">
                                    <a href="#" onclick="lookDetail(\`` + data[key].name + `\`, \`` + data[key].result[i]['OID'] + `\`)">查看详情</a>
                                </div>
                            </div>
                        </li>`;
                    }
                }
            } else {
                html += '<span id="' + data[key].id + '" onclick="kwfx.changeTab(`' + data[key].name + '`,`' + data[key].id + '`)">' + data[key].name + '</span>';
            }
        }
        $("#searchResultTab").html(html);
        $("#searchResultList").html(content);
        $("#searchResult").css("display","block");
        kwfx.searchResultHeight();
    },
    changeTab: function (name, id) {
        $("#searchResultTab").find('span').removeClass('active');
        $("#" + id).addClass('active');
        var data = kwfx.jsonArr;
        var content = '';
        $("#searchResultList").html('');
        for (var key in data) {
            if (data[key].name == name) {
                for (var i = 0; i < data[key].result.length; i++) {
                    if (data[key].name.indexOf('点') > -1) {
                        content += `<li onclick="kwfx.position(\`` + data[key].name + `\`,\`` + data[key].result[i]['OID'] + `\`)">
                            <span class="number">` + (i + 1) + `</span>
                            <p>
                                <span>OID: <i>` + data[key].result[i]['OID'] + `</i></span>
                                <span class="txtC">编号: <i>` + data[key].result[i]['PIPEP_ID'] + `</i></span>
                                <span class="txtR">类型: <i>` + data[key].result[i]['SUBSID'] + `</i></span>
                            </p>
                            <div class="pr txt">
                                位置: <i>` + data[key].result[i]['R_NAME'] + `</i>
                                <div class="more">
                                    <a href="#" onclick="lookDetail(\`` + data[key].name + `\`, \`` + data[key].result[i]['OID'] + `\`)">查看详情</a>
                                </div>
                            </div>
                        </li>`;
                    } else {
                        content += `<li onclick="kwfx.position(\`` + data[key].name + `\`,\`` + data[key].result[i]['OID'] + `\`)">
                            <span class="number">` + (i + 1) + `</span>
                            <p>
                                <span>OID: <i>` + data[key].result[i]['OID'] + `</i></span>
                                <span class="txtC">编号: <i>` + data[key].result[i]['PIPEL_ID'] + `</i></span>
                                <span class="txtR">管径: <i>` + data[key].result[i]['D_S'] + `</i></span>
                            </p>
                            <div class="pr txt">
                                位置: <i>` + data[key].result[i]['R_NAME'] + `</i>
                                <div class="more">
                                    <a href="#" onclick="lookDetail(\`` + data[key].name + `\`, \`` + data[key].result[i]['OID'] + `\`)">查看详情</a>
                                </div>
                            </div>
                        </li>`;
                    }
                }
            }
        }
        $("#searchResultList").html(content);
    },
    position: function (name, id) {
        layerTree.removeLocatedBuilding();
        closeResultMore();
        for (var i = 0; i < kwfx.gxdwData.length; i++) {
            if (kwfx.gxdwData[i].name == name) {
                var data = kwfx.gxdwData[i].data.features;
                for (var j = 0; j < data.length; j++) {
                    if (data[j].properties["OID"] == id) {
                        var json = {
                            features: [data[j]],
                            fields: kwfx.gxdwData[i].data.fields,
                            type: "FeatureCollection"
                        };
                        layerTree.addGeoJsonLayer(layerTree.reformattedGeoJsonData(json), layerTree.setOptionsByType("MOBILE_LOCALTION"));
                    }
                }
            }
        }
    },
    //搜索结果列表的高
    searchResultHeight: function () {
        var fxLayer = $(".fxLayer").height();
        var foldPanels = $(".foldPanels").height();
        var titleStyle1 = $(".titleStyle1").height();
        var fxtj = $(".fxtj").height();
        var searchTab = $(".searchResultTab").height();

        var searchResultListHeight = fxLayer - foldPanels - titleStyle1 - searchTab - fxtj - 102 + 'px';
        $(".searchResultList").height(searchResultListHeight);
    }


};
