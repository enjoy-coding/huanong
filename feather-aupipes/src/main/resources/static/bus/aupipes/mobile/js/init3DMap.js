var lang = getLanguagePackageByLang.returnPackageByLang();
var viewer;
var gViewer; // 等于 viewer
var measureHandler;//声明测量类
var jsonObj;//JSON化后的图层树配置文件
var tileset;    //让bim成为全局变量
var form;   //全局的layui form变量
var drawHandler;//声明绘制类

lang.then(function (data) {
    getLanguagePackageByLang.translationLangByKey("lang", data);
    onload();
    setTimeout(function(){
        var menuCode = GetQueryString("menuCode");
        $("#"+ menuCode).click();
        var yjName = window.sessionStorage.getItem("yjName");
        var yjId = window.sessionStorage.getItem("yjId");
        if(yjName){
            lookDetail(yjName, yjId)
        }

    },1000);

}, function () {
    onload();
});

//初始化三维模型
function onload() {
    var isPCBrowser = Cesium.FeatureDetection.isPCBrowser();
    if (isPCBrowser) {
        viewer = new Cesium.Viewer(
            'cesiumContainer',
            {
                imageryProvider: new Cesium.SingleTileImageryProvider(
                    {
                        url: Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
                    }),
                animation: true,
                timeline: true,
                baseLayerPicker: false,
                geocoder: false,
                homeButton: false,
                infoBox: false,
                sceneModePicker: false,
                fullscreenButton: false,
                navigationHelpButton: false
            });
    } else {
        viewer = new Cesium.Viewer('cesiumContainer', {
            imageryProvider: new Cesium.SingleTileImageryProvider(
                {
                    url: Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
                }),
            animation: true,
            timeline: true,
            baseLayerPicker: false,
            geocoder: false,
            homeButton: false,
            infoBox: false,
            sceneModePicker: false,
            navigationHelpButton: false,
            showStatusBar: false,
            fullscreenButton: false
        });
    }
    viewer.timeline.container.style.display = "none";
    viewer.animation.container.style.display = "none";
    gViewer = viewer;

    //logo隐藏
    $(".cesium-credit-logoContainer").css("display", "none");
    $(".cesium-widget-credits").css("display", "none");

    measureHandler = new Cesium.MeasureHandler(viewer);

    //开启深度检测
    viewer.scene.globe.depthTestAgainstTerrain = true;
    //移除鼠标双击事件
    viewer.cesiumWidget.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
    //viewer.scene.morphTo2D();//二维
    //Initialize DrawHandler
    drawHandler = new Cesium.DrawHandler(viewer, {followEllipsoid: false,showTooltip: true, dblClickEnd: true});
    //禁止手机界面地图旋转
    viewer.scene.screenSpaceCameraController.enableTilt = false;

    //获取图层树配置文件
    $.ajax({
        "async": false,
        "url": ctx + 'bus/aupipes/config.xml' + '?_=' + staticResourcesVersion,
        "type": 'GET',
        "dataType": 'xml',
        "timeout": 16000,
        "cache": false,
        "error": function () {
            console.log('加载图层树配置文件失败');
        },
        "success": function (xmlDoc) {
            var x2js = new X2JS();
            jsonObj = x2js.xml2json(xmlDoc);
            layerTree.initTree(jsonObj, "");
        }
    });
}

var layerTree = {
    allNodes: [],//全部图层数据
    zNodes: [],//分类图层数据
    alphaBar: null,//透明度滑动条
    viewModel: {//监听滑动条变化，改变alpha的值，设置地表透明度
        overGroundAlpha: 1
    },
    //初始化加载图层
    initTree: function (jsonObj, type) {
        //移除三维球上已加载的图层
        this.removeAllLayers();
        var obj = this.deepCopy(jsonObj);
        if (obj.config.layer instanceof Array) {
            this.zNodes = this.reformattedJsonObj(obj.config.layer, type);
        } else {
            //处理只有根节点的情形
            this.zNodes = this.reformattedJsonObj([obj.config.layer], type);
        }
        //展示对应图层在页面上
        this.addLayerToHtml(this.zNodes);
        //加载默认图层到地图上
        for (var i = 0; i < this.allNodes.length; i++) {
            if (this.allNodes[i].id == "dom") {
                this.addLayersByLeafNodes([this.allNodes[i]]);
            }
        }
        //定位至初始位置
        //this.locateHome(cfg.position[0], cfg.position[1], cfg.position[2]);//倾斜摄影
        this.locateHomeF(cfg.positionF[0], cfg.positionF[1], cfg.positionF[2]);//影像
        wxJsSdk.getLocation(function(res){
            var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
            var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
            layerTree.locateHomeF(longitude, latitude, 500);//影像

            var citizensBankPark = viewer.entities.add({
                position: Cesium.Cartesian3.fromDegrees(longitude, latitude, 10),
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
        });
    },
    //设置指定叶子节点对应图层的透明度
    setAlpha: function (leafNode, alpha) {
        switch (leafNode.dataSourceType) {
            case "Cesium3DTileset":
                var primitive = viewer.scene.primitives.getPrimitiveByGuid(leafNode.guid);
                if (primitive && primitive.style && primitive.style.color) {
                    var colorString = primitive.style.color.expression;
                    colorString = colorString.substr(0, colorString.lastIndexOf(",") + 1);
                    primitive.style = new Cesium.Cesium3DTileStyle({
                        color: colorString + parseFloat(alpha) + ')")'
                    });
                } else {
                    primitive.style = new Cesium.Cesium3DTileStyle({
                        color: 'color("rgba(255,255,255,' + parseFloat(alpha) + ')")'
                    });
                }
                break;
            case "KQGISMapServerImageryProvider":
                var imageryLayer = viewer.imageryLayers.getLayerByGuid(leafNode.guid);
                imageryLayer.alpha = parseFloat(alpha);
                break;
            default:
        }
    },
    //对象深拷贝
    deepCopy: function (obj) {
        var result = Array.isArray(obj) ? [] : {};
        for (var key in obj) {
            if (obj.hasOwnProperty(key)) {
                if (typeof obj[key] === 'object' && obj[key] !== null) {
                    result[key] = this.deepCopy(obj[key]);   //递归复制
                } else {
                    result[key] = obj[key];
                }
            }
        }
        return result;
    },
    searchAroundKw: function (treeNode, geometry, callback) {
        var where;
        if(treeNode.name.indexOf("点") > -1){
            where = "1=1 and B_DEPTH<=" + kwfx.depth;
        }else{
            where = "1=1 and S_DEEP<=" + kwfx.depth;
        }
        var queryOptions = {
            url: aupipeService + "/KQGis/rest/services/huanong/queryserver/overlap",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            layerId: treeNode.id,
            geometry: JSON.stringify(geometry),
            where: where,
            startIndex: 0,
            reqCount: 1000, // 返回1000条数据
            isOverlap: true
        };
        // 使用缓冲图形来查询
        layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
            kwfx.index++;
            if (geoJsonData) {
                if (typeof callback === "function") {
                    callback(geoJsonData,treeNode.id,treeNode.name);
                } else {
                    console.log(geoJsonData);
                }
            }
        });
    },
    //根据场景类型重新构造JSON化的图层树数据，使其满足zTree的数据结构
    reformattedJsonObj: function (arr, type) {
        var json = {};
        json["dlg"] = [], json["pipe"] = [], json["image"] = [], json["device"] = [], json["2dPipe"] = [];
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "建筑物") {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        if(arr[i].children.layer[j].id == 'BIM模型'){
                            if(arr[i].children.layer[j].children.layer instanceof Array){
                                for(var key in arr[i].children.layer[j].children.layer){
                                    if(arr[i].children.layer[j].children.layer[key].children.layer instanceof Array){
                                        for(var m in arr[i].children.layer[j].children.layer[key].children.layer){
                                            this.allNodes.push(arr[i].children.layer[j].children.layer[key].children.layer[m]);
                                        }
                                    }else{
                                        this.allNodes.push(arr[i].children.layer[j].children.layer[key].children.layer);
                                    }
                                }
                            }else{
                                this.allNodes.push(arr[i].children.layer[j].children.layer[key]);
                            }
                        }else{
                            json["dlg"].push(arr[i].children.layer[j]);
                            this.allNodes.push(arr[i].children.layer[j]);
                        }
                    }
                } else {
                    json["dlg"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            } else if (arr[i].name == "管线数据") {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        if(arr[i].children.layer[j].id=='PIPELINE_3D'){
                            if(arr[i].children.layer[j].children.layer instanceof Array){
                                for(var key in arr[i].children.layer[j].children.layer){
                                    json["pipe"].push(arr[i].children.layer[j].children.layer[key]);
                                    this.allNodes.push(arr[i].children.layer[j].children.layer[key]);
                                }
                            }else{
                                json["pipe"].push(arr[i].children.layer[j].children.layer);
                                this.allNodes.push(arr[i].children.layer[j].children.layer);
                            }
                        }else{
                            if(arr[i].children.layer[j].children.layer instanceof Array){
                                for(var key in arr[i].children.layer[j].children.layer){
                                    json["2dPipe"].push(arr[i].children.layer[j].children.layer[key]);
                                    this.allNodes.push(arr[i].children.layer[j].children.layer[key]);
                                }
                            }else{
                                json["2dPipe"].push(arr[i].children.layer[j].children.layer);
                                this.allNodes.push(arr[i].children.layer[j].children.layer);
                            }
                        }
                    }
                } else {
                    json["pipe"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            } else if(arr[i].name == "基础地图"){
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        if(arr[i].children.layer[j].id == '3D_MAP' || arr[i].children.layer[j].id == '影像地图'){
                            if(arr[i].children.layer[j].children != ""){
                                if(arr[i].children.layer[j].children.layer instanceof Array){
                                    for(var key in arr[i].children.layer[j].children.layer){
                                        json["image"].push(arr[i].children.layer[j].children.layer[key]);
                                        this.allNodes.push(arr[i].children.layer[j].children.layer[key]);
                                    }
                                }else{
                                    json["image"].push(arr[i].children.layer[j].children.layer);
                                    this.allNodes.push(arr[i].children.layer[j].children.layer);
                                }
                            }else{
                                json["image"].push(arr[i].children.layer[j]);
                                this.allNodes.push(arr[i].children.layer[j]);
                            }
                        }
                    }
                } else {
                    json["image"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            }else if (arr[i].name == "倾斜摄影" || arr[i].name == "影像") {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        json["image"].push(arr[i].children.layer[j]);
                        this.allNodes.push(arr[i].children.layer[j]);
                    }
                } else {
                    json["image"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            } else if (arr[i].name == "智能设备") {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        json["device"].push(arr[i].children.layer[j]);
                        this.allNodes.push(arr[i].children.layer[j]);
                    }
                } else {
                    json["device"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            } else if (arr[i].name == "管线") {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        json["2dPipe"].push(arr[i].children.layer[j]);
                        this.allNodes.push(arr[i].children.layer[j]);
                    }
                } else {
                    json["2dPipe"].push(arr[i]);
                    this.allNodes.push(arr[i]);
                }
            } else {
                if (arr[i].children && arr[i].children != "") {
                    for (var j = 0; j < arr[i].children.layer.length; j++) {
                        this.allNodes.push(arr[i].children.layer[j]);
                    }
                } else {
                    this.allNodes.push(arr[i]);
                }
            }
        }
        return json;
    },
    //把数据展示到页面中
    addLayerToHtml: function (json) {
        layui.use('form', function () {
            form = layui.form;
            //循环拼接数据
            for (key in json) {
                var html = "";
                for (var i = 0; i < json[key].length; i++) {
                    var checked = "";
                    if(json[key][i].id == 'dom'){
                        checked = "checked";
                        html += '<li>' + json[key][i].name + '<input class="pa layerSwichBtn layerChecked" type="checkbox" name="' + json[key][i].id + '" id="' + json[key][i].id + '" lay-skin="switch" lay-filter="switchText" lay-text="ON|OFF" ' + checked + '></li>';
                    }else{
                        html += '<li>' + json[key][i].name + '<input class="pa layerSwichBtn layerChecked" type="checkbox" name="' + json[key][i].id + '" id="' + json[key][i].id + '" lay-skin="switch" lay-filter="switchText" lay-text="ON|OFF" ' + checked + '></li>';
                    }
                }
                $("#" + key).html(html);
            }
            //数据渲染
            form.render();
            //监听提交
            form.on('switch(switchText)', function (data) {
                var obj = layerTree.allNodes.find(function (obj) {
                    return obj.id === data.elem.name
                });
                if (data.elem.checked == true) {
                    layerTree.addLayersByLeafNodes([obj]);
                } else {
                    layerTree.removeLayersByLeafNodes([obj]);
                }
            });
        });
    },
    // 将 Cesium.Rectangle 转化为笛卡尔坐标串
    transformRectangleToCartesianArray: function (rectangle) {
        var northWest = Cesium.Rectangle.northwest(rectangle);
        var northEast = Cesium.Rectangle.northeast(rectangle);
        var southEast = Cesium.Rectangle.southeast(rectangle);
        var southWest = Cesium.Rectangle.southwest(rectangle);
        var cartographicArray = [northWest, northEast, southEast, southWest];
        var cartesianArray = [];
        for (var i = 0; i < cartographicArray.length; i++) {
            var cartesian3 = Cesium.Cartesian3.fromRadians(cartographicArray[i].longitude, cartographicArray[i].latitude, cartographicArray[i].height);
            cartesianArray.push(cartesian3);
        }
        return cartesianArray;
    },
    // 将笛卡尔坐标串转换为GeoJson -> Feature
    transformCartesianArrayToGeoJson: function (positions) {
        if (!Array.isArray(positions)) return;
        var feature = {
            type: "Polygon",
            coordinates: [[]]
        };
        for (var i = 0; i < positions.length; i++) {
            // 笛卡尔坐标转经纬度（单位:弧度）
            var cartographic = Cesium.Cartographic.fromCartesian(positions[i]);
            // 弧度转度
            var longitude = Cesium.Math.toDegrees(cartographic.longitude);
            var latitude = Cesium.Math.toDegrees(cartographic.latitude);
            var coordinate = [longitude, latitude];
            feature.coordinates[0].push(coordinate);
        }
        // 构造闭合的geometry
        if (feature.coordinates[0][0]) {
            feature.coordinates[0].push(feature.coordinates[0][0]);
        }
        return feature;
    },
    //定位至初始位置
    locateHome: function (x, y, z) {
        viewer.scene.camera.flyTo({
            destination: new Cesium.Cartesian3.fromDegrees(x, y, z),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-30.0),
                roll: Cesium.Math.toRadians(0),
            },
            duration: 3.0
        });
    },
    //计算指定图形的面积
    caculateAreaOfGeometry: function (options, callback) {
        var formData = new FormData();
        formData.append("geoSRS", options.geoSRS || 'EPSG:4326');
        formData.append("areaSRS", options.outSRS ||'EPSG:3857');
        formData.append("data", options.data); // 指定图形
        $.ajax({
            type: "post",// 请求类型
            url: options.url || aupipeService + "/KQGis/rest/services/geometryserver/area",// 请求URL
            data: formData,
            dataType: "json",// 数据返回类型
            processData: false,  // 不处理数据!
            contentType: false,   // 不设置内容类型!
            cache: false, // 是否缓存
            success: function (result) {
                var areas = result.result;
                callback(areas);
            },
            error: function (error) {
                console.info(error);
                callback([]);
            }
        });
    },
    //俯视
    locateHomeF: function (x, y, z) {
        viewer.scene.camera.flyTo({
            destination: new Cesium.Cartesian3.fromDegrees(x, y, z),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-90.0),
                roll: Cesium.Math.toRadians(0),
            },
            duration: 3.0
        });
    },
    //根据叶子节点中的数据地址加载对应图层
    addLayersByLeafNodes: function (leafNodes) {
        for (var i = 0; i < leafNodes.length; i++) {
        	var url = leafNodes[i].dataSourceUrl + "?_=" + staticResourcesVersion;
        	if (url.indexOf("http:") == -1 && url.indexOf("https:") == -1) {
        		url = aupipeService + url;
            }
        	leafNodes[i].dataSourceUrl = url;

            switch (leafNodes[i].dataSourceType) {
                case "Cesium3DTileset":
                    var isQxsy = leafNodes[i].id === "OBLIQUE_PHOTOGRAPHY";
                    var cesium3DTileset = isQxsy ?
                        new Cesium.Cesium3DTileset({
                            url: url,
                            name: leafNodes[i].name,
                            maximumScreenSpaceError: 512,
                            foveatedConeSize: 0.4
                        }) :
                        new Cesium.Cesium3DTileset({
                            url: url,
                            name: leafNodes[i].name,
                            colorBlendMode: Cesium.Cesium3DTileColorBlendMode.MIX,
                            colorBlendAmount: 0,
                            skipLevelOfDetail: true,
                            baseScreenSpaceError: 1024,
                            skipScreenSpaceErrorFactor: 16,
                            skipLevels: 1,
                            immediatelyLoadDesiredLevelOfDetail: false,
                            loadSiblings: false,
                            cullWithChildrenBounds: true
                        });
                    var tileset = viewer.scene.primitives.add(cesium3DTileset);
                    if (leafNodes[i].color) {//根据配置文件中的颜色来加载3DTiles,仅针对管线模型数据
                        tileset.style = new Cesium.Cesium3DTileStyle({
                            color: 'color("' + leafNodes[i].color + '")'
                        });
                        //减轻缩放到不同级别时，部分管线模型不显示的问题
                        tileset.maximumScreenSpaceError = 0;
                    }
                    if (isQxsy) {
                        var qxsyTileset = tileset;
                        viewer.camera.moveEnd.addEventListener(function () {
                            if (!qxsyTileset.show) {
                                return;
                            }
                            var maximumScreenSpaceErrorMap = {
                                defaultError: 512,  //默认的屏幕误差
                                distanceArray: [300, 800, 1500, 3000, 5000, 12000],
                                errorArray: [16, 32, 64, 256, 512, 426]
                            };
                            try {
                                var windowPosition = new Cesium.Cartesian2(viewer.canvas.width / 2, viewer.canvas.height / 2);
                                var car3 = viewer.scene.pickPosition(windowPosition);
                                if (!car3) {
                                    car3 = viewer.scene.camera.pickEllipsoid(windowPosition);
                                }
                                if (!car3) return;
                                var dis = Cesium.Cartesian3.distance(car3, viewer.camera.positionWC);
                                console.log(dis);
                                var match = false;
                                for (var i = 0; i < maximumScreenSpaceErrorMap.distanceArray.length; i++) {
                                    if (dis <= maximumScreenSpaceErrorMap.distanceArray[i]) {
                                        qxsyTileset.maximumScreenSpaceError = maximumScreenSpaceErrorMap.errorArray[i];
                                        match = true;
                                        break;
                                    }
                                }
                                if (!match) {
                                    qxsyTileset.maximumScreenSpaceError = maximumScreenSpaceErrorMap.defaultError;
                                }
                                console.log(qxsyTileset.maximumScreenSpaceError);
                            } catch (error) {
                                console.log(error);
                            }
                        });
                    }
                    (function (leafNode, that) {
                        tileset.readyPromise.then(function (tileset) {
                            //倾斜摄影数据整体偏移
                            if (leafNode.id === "OBLIQUE_PHOTOGRAPHY") {
                                that.transformTileset(tileset, { x: 0.000045, y: -0.00002 });
                            }

                        });

                    })(leafNodes[i], this);
                    leafNodes[i].guid = tileset.guid;//树节点绑定图层guid
                    //                    tileset.readyPromise.then(function (tileset) {
                    //                        viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                    //                            duration: 1.5
                    //                        });
                    //                    });
                    break;
                case "KQGISMapServerImageryProvider":
                    var imageryLayer = viewer.imageryLayers.addImageryProvider(new Cesium.KQGISMapServerImageryProvider({
                        name: 'KQGISMapServerImageryProvider',
                        url: leafNodes[i].dataSourceUrl,
                        layers: leafNodes[i].layers,
                        queryLayerId: leafNodes[i].queryLayerId
                    }));
                    leafNodes[i].guid = imageryLayer.guid;//树节点绑定图层guid
                    break;
                case "GeoJsonExtendDataSource":
                    (function (leafNode, that) {
                        var options = {
                            url: leafNode.dataSourceUrl.substr(0, leafNode.dataSourceUrl.lastIndexOf("/")) + "/queryserver/query",
                            geoSRS: "EPSG:4326",
                            outSRS: "EPSG:4326",
                            layerId: leafNode.queryLayerId,
                            where: "1=1",
                            startIndex: 0,
                            reqCount: 1000,
                            isOverlap: false
                        };
                        that.queryGeoJsonDataBySQL(options, function (geoJsonData) {
                            var reformattedGeoJson = that.reformattedGeoJsonData(geoJsonData);
                            var optionsByType = that.setOptionsByType(leafNode.id);
                            var promise = Cesium.GeoJsonExtendDataSource.load(reformattedGeoJson, optionsByType);
                            promise.then(function (dataSource) {
                                dataSource.name = leafNode.name;
                                viewer.dataSources.add(dataSource);
                                leafNode.guid = dataSource.guid;//树节点绑定图层guid
                                if (leafNode.id === "STREET_LIGHT") {
                                    that.getStatusOfAllStreetLight(function (statusArray) {
                                        if (statusArray && statusArray.length > 0) {
                                            //Get the array of entities
                                            var entities = dataSource.entities.values;
                                            for (var i = 0; i < entities.length; i++) {
                                                var entity = entities[i];
                                                for (var j = 0; j < statusArray.length; j++) {
                                                    if (entity.name === statusArray[j].name) {
                                                        if (statusArray[j].status === "开启") {
                                                            entity.billboard.image = ctx + 'bus/aupipes/img/3d/ludeng.png';
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        });
                    })(leafNodes[i], this);
                    break;
                default:

            }
        }
    },
    //根据叶子节点移除对应图层
    removeLayersByLeafNodes: function (leafNodes) {
        for (var i = 0; i < leafNodes.length; i++) {
            if (leafNodes[i].guid != undefined) {
                switch (leafNodes[i].dataSourceType) {
                    case "Cesium3DTileset":
                        var primitive = viewer.scene.primitives.getPrimitiveByGuid(leafNodes[i].guid);
                        viewer.scene.primitives.remove(primitive);
                        break;
                    case "KQGISMapServerImageryProvider":
                        var imageryLayer = viewer.imageryLayers.getLayerByGuid(leafNodes[i].guid);
                        viewer.imageryLayers.remove(imageryLayer, false);
                        break;
                    case "GeoJsonExtendDataSource":
                        var dataSource = viewer.dataSources.getDataSourceByGuid(leafNodes[i].guid);
                        viewer.dataSources.remove(dataSource);
                        break;
                    default:
                }
            }
        }
    },
    //移除所有图层
    removeAllLayers: function () {
        if (viewer) {
            //清除3dtileset数据
            //gViewer.scene.primitives.remove(tileset);
            //清除所有勾选的图层，不能使用removeAll()方法，避免出错
            //clearAllLayer();
            //viewer.imageryLayers.removeAll(false);
            var layerArray = viewer.imageryLayers._layers;
            if (layerArray && layerArray.length > 0) {
                //保留初始化三维球时加载的图片
                for (var i = 1, len = layerArray.length; i < len; i++) {
                    viewer.imageryLayers.remove(layerArray[i], false);
                }
            }
        }
    },
    //根据ID定位建筑物
    locateBuildingByID: function (values, type) {
        var that = this;
        //var allNodes = this.zTree.transformToArray(this.zTree.getNodes());
        var nodes = layerTree.allNodes.filter(function (item) {
            //return item.dataSourceType==="KQGISMapServerImageryProvider"&&item.id===type;
            return item.id === type;
        });
        var condition = "", queryLayerValues = values.split(",");
        for (var i = 0; i < queryLayerValues.length; i++) {
            condition += nodes[0].queryLayerField + "='" + queryLayerValues[i] + "' or "
        }
        condition = condition.substr(0, condition.length - 4);
        var options = {
            url: aupipeService + "/KQGis/rest/services/huanong/queryserver/query",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            layerId: nodes[0].queryLayerId,
            where: condition,
            startIndex: 0,
            reqCount: 200,
            isOverlap: false
        };
        this.queryGeoJsonDataBySQL(options, function (geoJsonData) {
            that.removeLocatedBuilding();
            that.addGeoJsonLayer(that.reformattedGeoJsonData(geoJsonData), that.setOptionsByType(type));
        });
    },
    /**
     * 搜周边（可查二维图层服务）
     * @param point  Object 点坐标,比如：  {longitude:114.354125, latitude:30.472093}
     * @param radius Number 缓冲半径，单位：m
     * @param treeNode Object 查询的图层信息,比如路灯： this.zNodes.device[0]
     * @param callback Function 查询完成回调函数
     */
    searchAround: function (point, radius, treeNode,bufferGeometry, callback) {
        //if (!bufferGeometry) return;
        var queryOptions = {
            url: aupipeService + "/KQGis/rest/services/huanong/queryserver/query",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            layerId: treeNode.queryLayerId,
            geometry: JSON.stringify(bufferGeometry),
            where: "1=1",
            startIndex: 0,
            reqCount: 1000, // 返回200条数据
            isOverlap: true
        };
        // 使用缓冲图形来查询
        layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
            searchIndex++;
            if (geoJsonData) {
                if (typeof callback === "function") {
                    callback(geoJsonData,treeNode.id,treeNode.name);
                } else {
                    console.log(geoJsonData);
                }
            }
        });
    },
    /**
     * 搜周边（空间过滤巡检维修记录）
     * @param point  Object 点坐标,比如：{longitude:114.35553, latitude:30.47621}
     * @param radius Number 缓冲半径，单位：m
     * @param callback Function 查询完成回调函数
     */
    searchRepairRecords: function (point, radius, treeNode, callback) {
        $.ajax({
            type: 'POST',
            url: ctx + 'aupipes/repair/geojson' + '?_=' + staticResourcesVersion,
            success: function (response) {
                if(response.msg === '操作成功'){
                    // 获取所有巡检维修点
                    var repairPoints = response.data;
                    // 使用TURF,前端计算缓冲区
                    var bufferedPolygon = turf.buffer(turf.point([Number(point.longitude), Number(point.latitude)]), radius / 1000, {units: 'kilometers'});
                    // 使用TURF,前端过滤落在缓冲区内的巡检点
                    var ptsWithin = turf.pointsWithinPolygon(repairPoints, bufferedPolygon);
                    searchIndex++;
                    if(ptsWithin.features.length > 0){
                        callback(ptsWithin,treeNode.id,treeNode.name);
                    }

                }
            },
            error: function (error) {
                console.info(error);
                callback({});
            }
        });
    },
    //获取指定图形的外缓冲区
    getBufferOfGeometry: function (options, callback) {
        var formData = new FormData();
        formData.append("geoSRS", options.geoSRS);
        formData.append("outSRS", options.outSRS);
        formData.append("data", options.data); // 指定图形
        formData.append("radius", options.radius); // 缓冲半径，单位：m
        formData.append("sideType", "outer");
        formData.append("version", "2.0");
        $.ajax({
            type: "post",// 请求类型
            url: options.url,// 请求URL
            data: formData,
            dataType: "json",// 数据返回类型
            processData: false,  // 不处理数据!
            contentType: false,   // 不设置内容类型!
            cache: false, // 是否缓存
            success: function (result) {
                var bufferGeometry = result.result;
                callback(bufferGeometry);
            },
            error: function (error) {
                console.info(error);
                callback({});
            }
        });
    },
    //根据SQL条件查询GeoJson格式数据
    queryGeoJsonDataBySQL: function (options, callback) {
        var formData = new FormData();
        formData.append("geoSRS", options.geoSRS);
        formData.append("outSRS", options.outSRS);
        formData.append("layerId", options.layerId);
        formData.append("where", options.where);
        formData.append("geometry", options.geometry);
        formData.append("isOverlap", options.isOverlap);
        formData.append("startIndex", options.startIndex);
        formData.append("reqCount", options.reqCount);
        $.ajax({
            type: "post",// 请求类型
            url: options.url,// 请求URL
            data: formData,
            dataType: "json",// 数据返回类型
            processData: false,  // 不处理数据!
            contentType: false,   // 不设置内容类型!
            cache: false, // 是否缓存
            success: function (result) {
                var geoJsonData = result.result;
                callback(geoJsonData);
            },
            error: function (error) {
                console.info(error);
                callback({});
            }
        });
    },
    // 重新格式化GeoJson数据，添加高程值
    reformattedGeoJsonData: function (geoJson) {
        if (geoJson && geoJson.features && geoJson.features.length > 0) {
            for (var i = 0; i < geoJson.features.length; i++) {
                var type = geoJson.features[i].geometry.type;
                var height = geoJson.features[i].properties.HIGH || geoJson.features[i].properties.H;
                if (type === "Point") {
                    geoJson.features[i].geometry.coordinates.push(height);
                } else if (type === "MultiPoint" || type === "LineString") {
                    for (var j = 0; j < geoJson.features[i].geometry.coordinates.length; j++) {
                        geoJson.features[i].geometry.coordinates[j].push(height);
                    }
                } else if (type === "MultiLineString" || type === "Polygon") {
                    for (var k = 0; k < geoJson.features[i].geometry.coordinates[0].length; k++) {
                        geoJson.features[i].geometry.coordinates[0][k].push(height);
                    }
                }
            }
        }
        return geoJson;
    },
    //根据定位类型设置定位图层样式
    setOptionsByType: function (type, flag) {
        var options = {
            label: {
                style: Cesium.LabelStyle.FILL_AND_OUTLINE,
                outlineWidth: 2,
                fillColor: Cesium.Color.AQUA,
                pixelOffset: new Cesium.Cartesian2(0, -18),
                // express: 'name',
                font: '14px 微软雅黑',
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 1000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)
            },
            billboard: {
                // image: ctx + 'bus/aupipes/img/3d/ludeng.png',
                width: 32,
                height: 32,
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 1000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)
            },
            polygon: {
                width:10,
                material: Cesium.Color.fromCssColorString('#4BE1EE')
                /*material: Cesium.Color.fromAlpha(Cesium.Color.AQUA, 0.1),
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 5000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)*/
            },
            polyline: {
                width: 12,
                material: Cesium.Color.fromCssColorString('#4BE1EE')
            }
        };
        if (type === "LEAKAGE") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/shenlou.png';
            options.label.express = "PLACEADDRE";
        }
        if (type === "STREET_LIGHT") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/ludenghui.png';
            options.label.express = "NAME";
        }
        if (type === "CONTROLLER") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/kaiguan.png';
            options.label.express = "SNAME";
        }
        if (type === "TRANSFORMER_ROOM") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/peidianfang.png';
            options.label.express = "MC";
        }
        if (type === "PUMP_HOUSE") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/bengfang.png';
            options.label.express = "MC";
        }
        if (type === "MONITOR") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/jiankong.png';
            options.label.express = "SNAME";
        }
        if (type === "DOOR_CONTROL") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/kaiguan.png';
            options.label.express = "WZ";
        }
        if (type === "WATER_MONITOR") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/shuizhi.png';
            options.label.express = "SNAME";
        }
        if (type === "MOBILE_LOCALTION") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/xunjiandian.png';
            options.label.express = "SNAME";
        }
        if (type === "TRANSFORMER_ROOM_ANNOTATION") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/peidianfang.png';
            options.label.express = "MC";
        }
        if (type === "PUMP_HOUSE_ANNOTATION") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/bengfang.png';
            options.label.express = "MC";
        }
        if (type === "HOUSE_ANNOTATION") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/loudong.png';
            options.label.express = "MC";
        }
        //单独设置定位图标
        if (flag) {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/location.png';
            options.billboard.scale = 1.3;
            options.billboard.pixelOffset = new Cesium.Cartesian2(0, -2);
        }
        return options;
    },
    clearPic:function(){
        if (geometryEntityArr.length > 0) {
            for (var i in geometryEntityArr) {
                viewer.entities.remove(geometryEntityArr[i]);
            }
            geometryEntityArr = [];
        }
    },
    // 添加并定位到GeoJson图层
    addGeoJsonLayer: function (geoJson, options) {
        if (!geoJson.features) return;
        var promise = Cesium.GeoJsonExtendDataSource.load(geoJson, options);
        promise.then(function (dataSource) {
            viewer.dataSources.add(dataSource);
            dataSource.name = "locateBuilding"; // 设置数据源名称，用于检索并删除
            //单独处理面的定位,根据面状数据高程值生成柱状体
            if (geoJson.features[0].geometry.type === "Polygon") {
                //Get the array of entities
                var entities = dataSource.entities.values;
                for (var i = 0; i < entities.length; i++) {
                    var entity = entities[i];
                    //Extrude the polygon based on the HIGH/H.
                    if($("#mapSwitch i").hasClass("icond1")) {
                        entity.polygon.height = 40;
                    }else{
                        entity.polygon.height = 1.5;
                    }
                    entity.polygon.outline = true;
                    entity.polygon.outlineColor = Cesium.Color.FORESTGREEN;
                    entity.polygon.outlineWidth = 5;
                    entity.polygon.material = Cesium.Color.fromAlpha(Cesium.Color.AQUA, 0.1);
                }
                dataSource.name = "localposition";
            }
            dataSource.autoAvoid(viewer);
            if($("#mapSwitch i").hasClass("icond1")) {
                viewer.flyTo(dataSource, {
                    offset: {
                        heading: Cesium.Math.toRadians(0.0),
                        pitch: Cesium.Math.toRadians(-90),
                        range: 100
                    }
                });
            }else{
                viewer.flyTo(dataSource, {
                    offset: {
                        heading: Cesium.Math.toRadians(0.0),
                        pitch: Cesium.Math.toRadians(-90),
                        range: 100
                    }
                });
            }

        });
    },
    // 添加并定位到GeoJson图层
    addGeoJsonLayerByPoint: function (geoJson, options) {
        if (!geoJson.features) return;
        var promise = Cesium.GeoJsonExtendDataSource.load(geoJson, options);
        promise.then(function (dataSource) {
            viewer.dataSources.add(dataSource);
            //单独处理面的定位,根据面状数据高程值生成柱状体
            if (geoJson.features[0].geometry.type === "Polygon") {
                //Get the array of entities
                var entities = dataSource.entities.values;
                for (var i = 0; i < entities.length; i++) {
                    var entity = entities[i];
                    //Extrude the polygon based on the HIGH/H.
                    entity.polygon.extrudedHeight = entity.properties.HIGH || entity.properties.H;
                }
            }
            dataSource.name = "locateBuilding"; // 设置数据源名称，用于检索并删除
            dataSource.autoAvoid(viewer);

            if($("#mapSwitch i").hasClass("icond1")) {
                viewer.flyTo(dataSource, {
                    offset: {
                        heading: Cesium.Math.toRadians(0.0),
                        pitch: Cesium.Math.toRadians(-90),
                        range: 100
                    }
                });
            }else{
                viewer.flyTo(dataSource, {
                    offset: {
                        heading: Cesium.Math.toRadians(0.0),
                        pitch: Cesium.Math.toRadians(-90),
                        range: 100
                    }
                });
            }
        });

    },
    // 移除已经定位的建筑物
    removeLocatedBuilding: function (type) {
        if (viewer) {
            var dataSources = viewer.dataSources._dataSources;
            var myDataSources = dataSources.filter(function (item) {
                return item.name === "locateBuilding";
            });
            var localSources = dataSources.filter(function (item) {
                return item.name === "localposition";
            });
            if (myDataSources && myDataSources.length > 0) {
                for (var i = 0, len = myDataSources.length; i < len; i++) {
                    viewer.dataSources.remove(myDataSources[i]);
                }
            }
            if(type == 'local'){
                if (localSources && localSources.length > 0) {
                    for (var i = 0, len = localSources.length; i < len; i++) {
                        viewer.dataSources.remove(localSources[i]);
                    }
                }
            }
        }
    },
    //删除开挖分析的绘制图形
    removePrimitiveByGuid: function (guid) {
        if(viewer){
            var primitives = viewer.scene.primitives._primitives;
            for (var i = 0; i < primitives.length; i++) {
                if (primitives[i].guid === guid) {
                    viewer.scene.primitives.remove(primitives[i]);
                    break;
                }
            }
        }
    },
    // 获取所有路灯的开/关状态
    getStatusOfAllStreetLight: function (callback) {
        $.ajax({
            type: 'POST',
            url: ctx + 'aupipes/energy/getAllLightStatus' + '?_=' + staticResourcesVersion,
            success: function (response) {
                if(response && response.length > 0){
                    callback(response);
                }
            },
            error: function (error) {
                console.info(error);
                callback([]);
            }
        });
    },
    changeLayer: function (id) {
        console.log(id);
    }


};
var pickFeature = {
    // HTML overlay for showing feature name on mouseover
    // nameOverlay: null,
    // Information about the currently selected feature
    isWorking:false,//用于切换模块时判断是否正在工作
    selected: {
        feature: undefined,
        originalColor: new Cesium.Color()
    },
    // An entity object which will hold info about the currently selected feature for infobox display
    selectedEntity: new Cesium.Entity(),
    // Get default left click handler for when a feature is not picked on left click
    clickHandler: null,
    // Information about the currently highlighted feature
    highlighted: {
        feature: undefined,
        originalColor: new Cesium.Color()
    },
    // 拾取类型，Feature:拾取要素，Position:拾取位置（坐标），Feature2D:二维服务拾取要素
    type: "Feature",
    treeNode: null,
    //拾取完成后回调函数，返回拾取到的要素/位置
    callback: function (pickedFeature) {
        if (this.type === "Feature") {
            // Set feature infobox description
            var properties = pickedFeature.getPropertyNames() || [];
            var featureName = 'Feature Property';
            if (properties.indexOf('name') > -1 || properties.indexOf('names') > -1) {
                featureName = pickedFeature.getProperty('name') || pickedFeature.getProperty('names');
            }
            this.selectedEntity.name = featureName;
            this.selectedEntity.description = 'Loading <div class="cesium-infoBox-loading"></div>';
            viewer.selectedEntity = this.selectedEntity;
            var trs = '';
            properties.forEach(function (property) {
                trs += '<tr><th>' + property + '</th><td>' + pickedFeature.getProperty(property) + '</td></tr>';
            });
            this.selectedEntity.description = '<table class="cesium-infoBox-defaultTable"><tbody>' + trs + '</tbody></table>';
        } else if (this.type === "Position" || this.type === "Feature2D") {
            //console.log("Position", pickedFeature);
            pickPosition = {longitude: pickedFeature.x, latitude: pickedFeature.y};
            $("input[name='position']")[0].value = pickedFeature.x + "," + pickedFeature.y;
            $("#searchBox").removeClass("searchCollapse");

        }
    },
    start: function (type, callback) {
        this.type = type;
        if (typeof callback === "function") {
            this.callback = callback;
        }
        // this.nameOverlay = nameOverlay;
        this.clickHandler = this.clickHandler || viewer.screenSpaceEventHandler.getInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        // Color a feature yellow on hover.
        this.type === "Feature" && viewer.screenSpaceEventHandler.setInputAction(this.onMouseMove.bind(this), Cesium.ScreenSpaceEventType.MOUSE_MOVE);

        // Color a feature on selection and show metadata in the InfoBox.
        viewer.screenSpaceEventHandler.setInputAction(this.onLeftClick.bind(this), Cesium.ScreenSpaceEventType.LEFT_CLICK);
    },
    onMouseMove: function (movement) {
        // If a feature was previously highlighted, undo the highlight
        if (Cesium.defined(this.highlighted.feature)) {
            this.highlighted.feature.color = this.highlighted.originalColor;
            this.highlighted.feature = undefined;
        }
        // Pick a new feature
        var pickedFeature = viewer.scene.pick(movement.endPosition);
        if (!Cesium.defined(pickedFeature)) {
            // this.nameOverlay.style.display = 'none';
            return;
        }
        // A feature was picked, so show it's overlay content
        // this.nameOverlay.style.display = 'block';
        // this.nameOverlay.style.bottom = viewer.canvas.clientHeight - movement.endPosition.y + 'px';
        // this.nameOverlay.style.left = movement.endPosition.x + 'px';
        var properties = pickedFeature.getPropertyNames() || [];
        var name = pickedFeature.getProperty('name') || pickedFeature.getProperty('names');
        if (!Cesium.defined(name)) {
            name = pickedFeature.getProperty(properties[0])
        }
        // this.nameOverlay.textContent = name;
        // Highlight the feature if it's not already selected.
        if (pickedFeature !== this.selected.feature) {
            this.highlighted.feature = pickedFeature;
            Cesium.Color.clone(pickedFeature.color, this.highlighted.originalColor);
            pickedFeature.color = Cesium.Color.YELLOW;
        }
    },
    onLeftClick: function (movement) {
        if (this.type === "Feature") {
            // If a feature was previously selected, undo the highlight
            if (Cesium.defined(this.selected.feature)) {
                this.selected.feature.color = this.selected.originalColor;
                this.selected.feature = undefined;
            }
            // Pick a new feature
            var pickedFeature = viewer.scene.pick(movement.position);
            if (!Cesium.defined(pickedFeature)) {
                this.clickHandler(movement);
                return;
            }
            // Select the feature if it's not already selected
            if (this.selected.feature === pickedFeature) {
                return;
            }
            this.selected.feature = pickedFeature;
            // Save the selected feature's original color
            if (pickedFeature === this.highlighted.feature) {
                Cesium.Color.clone(this.highlighted.originalColor, this.selected.originalColor);
                this.highlighted.feature = undefined;
            } else {
                Cesium.Color.clone(pickedFeature.color, this.selected.originalColor);
            }
            // Highlight newly selected feature
            pickedFeature.color = Cesium.Color.LIME;
            //执行回调函数，返回拾取到的元素
            this.callback(pickedFeature);
        } else if (this.type === "Position" || this.type === "Feature2D") {
            var position = viewer.scene.pickPosition(movement.position);
            if (position) {
                var cartographic = Cesium.Cartographic.fromCartesian(position);
                var longitude = Cesium.Math.toDegrees(cartographic.longitude).toFixed(5);
                var latitude = Cesium.Math.toDegrees(cartographic.latitude).toFixed(5);
                var height = cartographic.height.toFixed(3);
                if (this.type === "Position") {
                    this.callback({x: longitude, y: latitude, z: height});
                }
                if (this.type === "Feature2D") {
                    var treeNode = this.treeNode || layerTree.zTree.getNodeByParam("id", "JS_LINE");
                    // 获取点坐标半径 1m 的缓冲图形
                    var geometry = {type: "Point", coordinates: [parseFloat(longitude), parseFloat(latitude)]};
                    var bufferOptions = {
                        url: treeNode.dataSourceUrl.substr(0, treeNode.dataSourceUrl.lastIndexOf("huanong")) + "geometryserver/buffer",
                        geoSRS: "EPSG:4326",
                        outSRS: "EPSG:4326",
                        data: JSON.stringify(geometry),
                        radius: 1
                    };
                    layerTree.getBufferOfGeometry(bufferOptions,function (bufferGeometry) {
                        if (!bufferGeometry) return;
                        var queryOptions = {
                            url: aupipeService + "/KQGis/rest/services/huanong/queryserver/query",
                            geoSRS: "EPSG:4326",
                            outSRS: "EPSG:4326",
                            layerId: treeNode.queryLayerId,
                            geometry: JSON.stringify(bufferGeometry),
                            where: "1=1",
                            startIndex: 0,
                            reqCount: 200,
                            isOverlap: true
                        };
                        // 使用缓冲图形来查询
                        layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
                            if (geoJsonData) {
                                pickFeature.callback(geoJsonData);
                            }
                        });
                    });
                }
            }
        }
    },
    stop: function () {
        if (this.type === "Feature") {
            //undo the highlight
            if (Cesium.defined(this.selected.feature)) {
                this.selected.feature.color = this.selected.originalColor;
                this.selected.feature = undefined;
            }
            if (Cesium.defined(this.highlighted.feature)) {
                this.highlighted.feature.color = this.highlighted.originalColor;
                this.highlighted.feature = undefined;
            }
            // this.nameOverlay.style.display = 'none';
            viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
            viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        } else if (this.type === "Position" || this.type === "Feature2D") {
            viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    }
};
//历史轨迹回放对象
var trackPlayback = {
    historyPath: null,//历史轨迹线图层
    historyPathEntity: null,//历史轨迹线模拟对象
    isWorking: false,//用于切换模块时判断是否正在工作
    startTime: null,//历史轨迹模拟起始时间
    stopTime: null,//历史轨迹模拟终止时间
    historyGeoData: {
        type: "FeatureCollection",
        features: [{
            "type": "Feature",
            "geometry": {
                "type": "MultiPoint",
                "coordinates": [[119.12, 32.05], [119.12, 32.051], [119.12, 32.052], [119.12, 32.053], [119.12, 32.054], [119.12, 32.055], [119.12, 32.056], [119.12, 32.057], [119.12, 32.058], [119.12, 32.059], [119.12, 32.060]]
            },
            "properties": {
                "time": [1562116459, 1562216459, 1562316459, 1562416459, 1562516459, 1562616459, 1562716459, 1562816459, 1562916459, 1563016459, 1563116459]
            }
        }
        ]
    },
    /**
     * 随机生成一组轨迹数据
     * @param {Cesium.Entity} entity
     */
    randomGenerateHistoryData: function (entity) {
        var properties = entity.properties.getValue();
        this.historyGeoData.features[0].geometry.coordinates = [];
        for (var key in properties) {
            this.historyGeoData.features[0].properties[key] = properties[key];
        }
        this.historyGeoData.features[0].properties.time = [];
        this.historyGeoData.features[0].properties.TYPE = 'jkmb-history';
        var position = entity.position.getValue();
        var car = Cesium.Cartographic.fromCartesian(position);
        var lon = Cesium.Math.toDegrees(car.longitude),
            lat = Cesium.Math.toDegrees(car.latitude);
        var time = new Date().getTime();
        for (var i = 6; i >= 0; i--) {
            var coords = [lon - i * 0.01, lat],
                time2 = dateForm(time - i * 60000);
            this.historyGeoData.features[0].geometry.coordinates.push(coords);
            this.historyGeoData.features[0].properties.time.push(time2);
        }
        return this.historyGeoData;
    },
    /**
     * 开始回放历史轨迹--添加历史轨迹线图层
     * @param {GeoJson} 轨迹点数据
     */
    start: function (data) {
        // viewer.animation.container.style.display="block";
        // viewer.animation.resize();
        // viewer.timeline.container.style.display="block";
        // viewer.timeline.resize();
        // viewer.statusBar.container.style.bottom="30px";
        var that = this;
        this.historyGeoData = data || this.historyGeoData;
        if (this.historyPath) {
            viewer.dataSources.remove(this.historyPath);
            this.historyPath = null;
        }
        var options = {
            billboard: {
                image: ctx + 'bus/aupipes/img/3d/xunjiandian.png',
                width: 16,
                height: 20,
                horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
                verticalOrigin: Cesium.VerticalOrigin.BOTTOM
            }
        };

        //随机生成一组随机历史数据后续来是后台
        // var historyGeoData;
        // var entity = this.layer3d.entities.getById(id);
        // if (entity) {
        // 	historyGeoData = this.randomGenerateHistoryData(entity);
        // }
        // if (!historyGeoData)
        // 	return;
        viewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(this.historyGeoData, options)).then(function (ds) {
            that.historyPath = ds;
            viewer.flyTo(ds);
            that.play(that.historyGeoData);
        });
    },
    /**
     * 开始回放历史轨迹--模拟运动轨迹
     * @param {GeoJson} 轨迹点数据
     */
    play: function (historyGeoData) {
        if (this.historyPathEntity) {
            viewer.entities.remove(this.historyPathEntity);
            this.historyPathEntity = null;
        }
        var positionProperty = new Cesium.SampledPositionProperty();
        var coordinates = historyGeoData.features[0].geometry.coordinates;

        for (var i = 0; i < coordinates.length; i++) {
            var coordinate = coordinates[i];
            var position = Cesium.Cartesian3.fromDegrees(coordinate[0], coordinate[1], coordinate[2]);
            var timeS = historyGeoData.features[0].properties.time[i];
            var time = Cesium.JulianDate.fromDate(new Date(timeS));
            positionProperty.addSample(time, position);
            if (i == 0) {
                this.startTime = time;
            } else if (i == coordinates.length - 1) {
                this.stopTime = time;
            }
        }

        viewer.clock.startTime = this.startTime.clone();
        viewer.clock.stopTime = this.stopTime.clone();
        viewer.clock.currentTime = this.startTime.clone();
        viewer.clock.clockRange = Cesium.ClockRange.CLAMPED;
        viewer.clock.multiplier = 10;
        viewer.clock.shouldAnimate = true;
        //Set timeline to simulation bounds
        viewer.timeline.zoomTo(this.startTime, this.stopTime);
        this.historyPathEntity = viewer.entities.add({

            //Set the entity availability to the same interval as the simulation time.
            availability: new Cesium.TimeIntervalCollection([new Cesium.TimeInterval({
                start: this.startTime,
                stop: this.stopTime
            })]),

            //Use our computed positions
            position: positionProperty,

            //Automatically compute orientation based on position movement.
            orientation: new Cesium.VelocityOrientationProperty(positionProperty),

            //Load the Cesium plane model to represent the entity
            billboard: {
                image: ctx + 'bus/aupipes/img/3d/xunjianrenyuan.png',
                horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
                verticalOrigin: Cesium.VerticalOrigin.BOTTOM
            },
            //Show the path as a pink line sampled in 1 second increments.
            path: {
                resolution: 1,
                material: new Cesium.PolylineGlowMaterialProperty({
                    glowPower: 0.1,
                    color: Cesium.Color.fromCssColorString('#1EF0ED')
                }),
                width: 10
            }
        });

        this.historyPathEntity.position.setInterpolationOptions({
            interpolationDegree: 2,
            interpolationAlgorithm: Cesium.HermitePolynomialApproximation
        });
    },
    /**
     *暂停回放历史轨迹
     */
    pause: function () {
        viewer.clock.shouldAnimate = !viewer.clock.shouldAnimate;
    },
    stop: function () {
        // viewer.timeline.container.style.display="none";
        // viewer.animation.container.style.display="none";
        // viewer.statusBar.container.style.bottom="0";
        if (this.historyPathEntity) {
            viewer.entities.remove(this.historyPathEntity);
            this.historyPathEntity = null;
        }
        if (this.historyPath) {
            viewer.dataSources.remove(this.historyPath);
            this.historyPath = null;
        }
        viewer.clock.shouldAnimate = false;
    },
};
