var tileset;    //让bim成为全局变量
var cameraId;   //存储运行监控配电房bim模型摄像头id
var pickColor = null;   //点击模型的颜色
var pickColor1 = null;
var drawShapeModel = null; //绘制的图形
var flagModel = false;   //定义一个布尔值，在决策分析-信息推送模块用到
var objBimLayer = null;
var bimNameId = null;

//图层树
var layerTree = {
    zTree: null,//zTree对象
    expandFlag: false,//图层树节点展开状态
    rMenu: null,//zTree右键菜单
    alphaBar: null,//透明度滑动条
    viewModel: {//监听滑动条变化，改变alpha的值，设置地表透明度
        overGroundAlpha: 1
    },
    guid: '',    //guid
    zNodes: [],//zTree数据
    setting: {//zTree配置
        check: {
            enable: true
        },
        view: {
            selectedMulti: false,
            fontCss: {color: "#1ef0ed"},
            showIcon: false
        },
        callback: {}
    },
    bimTreeSceneFlag: false,//记录是否进入BimTree分层浏览场景，默认为未进入（false）
    //根据场景类型初始化zTree
    //type：yjcz代表预警处置、yxjc代表运行监控、ryxj代表人员巡检、jcbz代表决策保障、hnjg代表耗能监管
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
        $.fn.zTree.init($("#tt"), this.setting, this.zNodes);
        this.zTree = $.fn.zTree.getZTreeObj("tt");
        this.expandFlag = false;
        this.zTree.setting.callback = {
            onClick: this.onClick.bind(this),
            //onRightClick: this.onRightClick.bind(this),
            onCheck: this.onCheck.bind(this)
        };
        if (!this.rMenu) this.addRigthMenu();
        if (!this.alphaBar) this.addAlphaBar();
        //根据图层树配置加载默认勾选的图层
        var checkedNodes = this.zTree.getCheckedNodes(true);
        var checkedLeafNodes = checkedNodes.filter(function (item) {
            return !item.isParent;
        });
        this.addLayersByLeafNodes(checkedLeafNodes);
        //图层树执行一次checkNode,仅用于更新树节点半勾选状态，不触发onCheck事件回调函数。
        var treeNode = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY");
        layerTree.zTree.checkNode(treeNode, true, true, false);
        //相机至初始位置
        // this.locateHome();
        //视图定位到指定的点
        //this.flyToPoint(114.35253,30.47472,500);
    },
    //切换图层树节点全部展开/收起状态
    toggleExpandState: function () {
        if (this.zTree) {
            this.expandFlag = this.zTree.expandAll(!this.expandFlag);
            return this.expandFlag;
        }
    },
    //图层树节点点击
    onClick: function (event, treeId, treeNode) {
        //控制仅叶子节点已被勾选时点击定位
        if (!treeNode.isParent && treeNode.checked) {
            this.locateLayerByLeafNode(treeNode);
            //图层已加载时显示透明度工具条
            this.openAlphaBar();
        }
    },
    //图层树节点勾选/反勾选
    onCheck: function (event, treeId, treeNode) {
        if (treeNode.checked) {
            var unCheckedLeafNodes = this.getAllLeafNodes(treeNode, [], false);
            var reversedUnCheckedLeafNodes = unCheckedLeafNodes.reverse();
            this.addLayersByLeafNodes(reversedUnCheckedLeafNodes);
        } else {
            var checkedLeafNodes = this.getAllLeafNodes(treeNode, [], true);
            this.removeLayersByLeafNodes(checkedLeafNodes);
        }
    },
    //显示右键菜单-图层树节点右键点击
    onRightClick: function (event, treeId, treeNode) {
        if (!treeNode.isParent) {//控制仅叶子节点弹出右键菜单
            this.zTree.selectNode(treeNode);
            var y = event.clientY + document.body.scrollTop;
            var x = event.clientX + document.body.scrollLeft;
            if (this.rMenu) this.rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
            $("body").bind("mousedown", this.onBodyMouseDown);
        }
    },
    //添加配电房BIM缩略图弹窗
    addBimThumbnailPopup: function (leafNode) {
        var that = this;
        var imgUrl = leafNode.dataSourceUrl.substr(0, leafNode.dataSourceUrl.lastIndexOf("/")) + "/thumb100.png";
        var primitive = viewer.scene.primitives.getPrimitiveByGuid(leafNode.guid);
        var html = `<div class="thumbBIM">
                        <img src=${imgUrl} alt="" style="display:block;cursor:pointer;" title="查看BIM分层数据">
                    </div>`;
        var position = primitive.boundingSphere.center;
        primitive.bimThumbnailPopup = new Cesium.DivPoint(viewer, {
            html: html,
            position: position,
            anchor: [0, 0],
            maxVisibleDistance: 150,
            noEvent: false
        });
        $('.thumbBIM img').click(function () {
            //BIM数据切换至分层浏览场景
            that.inToBimTreeScene(leafNode);
        });
    },
    //添加bimTree选项卡
    addBimTreeTab: function (tabTitle) {
        var $tabTitle = $("li[lay-id='tabBimLayer']");
        if ($tabTitle.length === 0) {
            //动态添加tabBimLayer
            layui.element.tabAdd('layerTreeTab', {
                title: tabTitle
                , content: '<div class="ztreeBox"><ul id="bimTree" class="ztree"></ul></div>'
                , id: 'tabBimLayer'
            });
        } else {
            $("#bimTree").empty();//清除前一次渲染结果
            tabTitle += '<i class="layui-icon layui-unselect layui-tab-close">ဆ</i>';
            $tabTitle.html(tabTitle);
            $tabTitle.on('click', 'i', function () {
                // layui.element.tabDelete('layerTreeTab', 'tabBimLayer');
                layerTree.outOfBimTreeScene();
            });
        }
        //切换到tabBimLayer
        layui.element.tabChange('layerTreeTab', 'tabBimLayer');
    },
    //进入BimTree分层浏览场景
    inToBimTreeScene: function (treeNode) {
        this.bimTreeSceneFlag = true;
        this.removeAllLayers();
        // if (this.zTree) this.zTree.checkAllNodes(false);
        this.addBimTreeTab(treeNode.name);
        // GWYX.clearPickPoint();
        //解析bimTree
        bimLayerTree.initTree(treeNode);
    },
    //退出BimTree分层浏览场景
    outOfBimTreeScene: function () {
        if (this.bimTreeSceneFlag) {
            this.bimTreeSceneFlag = false;
            layui.element.tabDelete('layerTreeTab', 'tabBimLayer');
        }
        if (pickColor != null) {
            //Cesium.Color.clone(pickColor.color,pickColor1);
            pickColor = null;
        }
        if (objBimLayer) {
            layerTree.removeLayersByLeafNodes([objBimLayer]);
            objBimLayer = null;
        }

    },
    //添加右键菜单
    addRigthMenu: function () {
        var rMenuContent = '';
        rMenuContent += '<div id="rMenu">' +
            '<ul>' +
            '<li onclick="layerTree.openAlphaBar();">透明度</li>' +
            '<li onclick="layerTree.layerLocation();">图层定位</li>' +
            '</ul>' +
            '</div>';
        $("#cesiumContainer").after(rMenuContent);
        this.rMenu = $("#rMenu");
    },
    //隐藏右键菜单/透明度工具条-点击右键菜单/透明度工具条之外区域
    onBodyMouseDown: function (event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            if (!(event.target.id == "alphaBar" || $(event.target).parents("#alphaBar").length > 0)) {
                if (layerTree.rMenu.length > 0) layerTree.rMenu.css({"visibility": "hidden"});
                if (layerTree.alphaBar.length > 0) layerTree.alphaBar.css({"visibility": "hidden"});
                $("body").unbind("mousedown", this.onBodyMouseDown);
            }
        }
    },
    //添加透明度工具条
    addAlphaBar: function () {
        var alphaContent = '';
        alphaContent += '<div id="alphaBar" style="position: absolute;top:10.4vh; left:23vw;height: 3.8vh;z-index: 1000;padding: 0 5px;color:#1ef0ed;border: 1px solid #526f82;visibility: hidden;">'
            + '<label style="padding: 0 5px;float: left;line-height: 3.8vh;">图层透明度:</label>'
            + '<input style="height:3.8vh;cursor:pointer;" type="range" min="0" max="1" step="0.02" title="调整地上图层透明度" value="1" data-bind="value: overGroundAlpha,valueUpdate: \'input\'">'
            + '</div>';
        $("#cesiumContainer").after(alphaContent);
        this.alphaBar = $("#alphaBar");

        Cesium.knockout.track(this.viewModel);
        Cesium.knockout.applyBindings(this.viewModel, document.getElementById('alphaBar'));
        Cesium.knockout.getObservable(this.viewModel, 'overGroundAlpha').subscribe(// 设置地表图层透明度
            function (newValue) {
                var nodes = layerTree.zTree.getSelectedNodes();
                if (nodes[0]) {
                    layerTree.setAlpha(nodes[0], newValue)
                } else {
                    console.log("请选择一个图层");
                }
            }
        );
    },
    //显示透明度工具条
    openAlphaBar: function () {
        var nodes = this.zTree.getSelectedNodes();
        var currentLayerAlpha = 1;//获取图层当前透明度，默认不透明
        currentLayerAlpha = this.getAlpha(nodes[0]);
        if (this.alphaBar) this.alphaBar.css({"visibility": "visible"});
        $("#alphaBar input").val(currentLayerAlpha);
        $("body").bind("mousedown", this.onBodyMouseDown);
    },
    //获取指定叶子节点对应图层的透明度
    getAlpha: function (leafNode) {
        switch (leafNode.dataSourceType) {
            case "Cesium3DTileset":
                var primitive = viewer.scene.primitives.getPrimitiveByGuid(leafNode.guid);
                return primitive && primitive.style && primitive.style.color ? primitive.style.color.evaluateColor().alpha : 1;
                break;
            case "KQGISMapServerImageryProvider":
                var imageryLayer = viewer.imageryLayers.getLayerByGuid(leafNode.guid);
                return imageryLayer.alpha;
                break;
            default:
                return 1;
        }
    },
    //设置指定叶子节点对应图层的透明度
    setAlpha: function (leafNode, alpha) {
        switch (leafNode.dataSourceType) {
            case "Cesium3DTileset":
                if (leafNode.guid) {
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
                }
                break;
            case "KQGISMapServerImageryProvider":
                var imageryLayer = viewer.imageryLayers.getLayerByGuid(leafNode.guid);
                imageryLayer.alpha = parseFloat(alpha);
                break;
            default:
        }
    },
    //根据叶子节点中的数据地址加载对应图层
    addLayersByLeafNodes: function (leafNodes) {
        var parentIdArr = [];
        for (var i = 0; i < leafNodes.length; i++) {
            var url = leafNodes[i].dataSourceUrl + "?_=" + staticResourcesVersion;
            if (url.indexOf("http:") == -1 && url.indexOf("https:") == -1) {
                url = aupipeService + url;
            }
            leafNodes[i].dataSourceUrl = url;

            if (parentIdArr.indexOf(leafNodes[i].parentTId) == -1 && leafNodes[i].parentTId != null) {
                //var nodeParent = layerTree.zTree.getNodeByTId(leafNodes[i].parentTId);
                if (leafNodes[i].id == "LEAKAGE") {
                    parentIdArr.push(leafNodes[i].id);
                }
            }
            switch (leafNodes[i].dataSourceType) {
                case "Cesium3DTileset":
                    var isQxsy = leafNodes[i].id === "OBLIQUE_PHOTOGRAPHY";
                    var cesium3DTileset = isQxsy ?
                        new Cesium.Cesium3DTileset({
                            url: url,
                            name: leafNodes[i].name,
                            //maximumScreenSpaceError: 512,
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

                    viewer.zoomTo(cesium3DTileset, new Cesium.HeadingPitchRange(0.0, Cesium.Math.toRadians(-45), 500));

                    if (leafNodes[i].color) {//根据配置文件中的颜色来加载3DTiles,仅针对管线模型数据
                        tileset.style = new Cesium.Cesium3DTileStyle({
                            color: 'color("' + leafNodes[i].color + '")'
                        });
                        //减轻缩放到不同级别时，部分管线模型不显示的问题
                        tileset.maximumScreenSpaceError = 0;
                    }
                    /*if (isQxsy) {
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
                                // console.log(dis);
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
                    }*/
                    (function (leafNode, tileset, that) {
                        tileset.readyPromise.then(function (tileset) {
                            // viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                            //     duration: 1.5
                            // });
                            //倾斜摄影数据整体偏移
                            if (leafNode.id === "OBLIQUE_PHOTOGRAPHY") {
                                that.transformTileset(tileset, {x: 0.000045, y: -0.00002});
                            }
                            if (leafNode.id === "JS_LINE_3D" || leafNode.id === "GD_LINE_3D" || leafNode.id === "LD_LINE_3D" || leafNode.id === "TRQ_LINE_3D" || leafNode.id === "WS_LINE_3D" || leafNode.id === "YS_LINE_3D") {
                                var linkPropertyName = 'DbId';//'id';
                                //使用目录结构扩展
                                tileset.extendCatalog(linkPropertyName);
                                tileset.setEnablePickFeatures(viewer, true);
                                //监听拾取事件
                                var pickedEvent = tileset.getPickedEvent();
                                if (pickedEvent) {
                                    pickedEvent.addEventListener(function (res) {
                                        console.log(res);
                                        var name = res.feature._content._tileset._name;
                                        var pipeInfoArr = [];
                                        for (var i = 0; i < pipeLayerProperties.config.layer.length; i++) {
                                            if (res.properties.name.indexOf("-") > -1) {
                                                if (name == pipeLayerProperties.config.layer[i].id) {
                                                    pipeInfoArr = pipeLayerProperties.config.layer[i];
                                                }
                                            } else {
                                                if (name + '点' == pipeLayerProperties.config.layer[i].id) {
                                                    pipeInfoArr = pipeLayerProperties.config.layer[i];
                                                }
                                            }
                                        }
                                        var trs = "";
                                        for (var j = 0; j < res.properties.categories[0].props.names.length; j++) {
                                            var property = res.properties.categories[0].props.names[j];
                                            if (pipeInfoArr[property]) {
                                                trs = trs + '<tr><th>' + pipeInfoArr[property] + '</th><td>' + res.properties.categories[0].props.values[j] + '</td></tr>';
                                            }
                                        }

                                        var html = $(res.tableHTML).find("tbody")[0].innerHTML;
                                        openAttributeLayer(trs, "", "");
                                    });
                                }
                            }
                            //配电房bim成功加载后，添加bim缩略图弹框
                            // if (leafNode.dataSourceWithTree) {
                            //     that.addBimThumbnailPopup(leafNode);
                            // }
                        });
                    })(leafNodes[i], tileset, this);
                    leafNodes[i].guid = tileset.guid;//树节点绑定图层guid
                    break;
                case "KQGISMapServerImageryProvider":
                    var imageryLayer = viewer.imageryLayers.addImageryProvider(new Cesium.KQGISMapServerImageryProvider({
                        name: 'KQGISMapServerImageryProvider',
                        url: leafNodes[i].dataSourceUrl + "?_=" + staticResourcesVersion,
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


                            //如果是BIM模型注记
                            if (leafNode.id === "BIM") {
                                if (geoJsonData && geoJsonData.features.length > 0) {
                                    var arrA = geoJsonData.features.filter(function (item) {
                                        if (item.properties.PDFID == bimNameId) {
                                            return item;
                                        }
                                    })
                                    geoJsonData.features = arrA;
                                }

                            }
                            var reformattedGeoJson = that.reformattedGeoJsonData(geoJsonData);

                            var optionsByType = that.setOptionsByType(leafNode.id);
                            var promise = Cesium.GeoJsonExtendDataSource.load(reformattedGeoJson, optionsByType);
                            promise.then(function (dataSource) {
                                dataSource.name = leafNode.name;
                                viewer.dataSources.add(dataSource);
                                leafNode.guid = dataSource.guid;//树节点绑定图层guid
                                // 根据路灯的开关状态渲染不同图标
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
                                //如果是BIM模型注记
                                if (leafNode.id === "BIM") {
                                    var entities = dataSource.entities.values;
                                    for (var i = 0; i < entities.length; i++) {
                                        var entity = entities[i];
                                        var point = reformattedGeoJson.features[i].geometry.coordinates;

                                        var surfacePosition = Cesium.Cartesian3.fromDegrees(
                                            //point[0], point[1], (point[2] - 1)
                                            point[0], point[1], (point[2])
                                        );
                                        var heightPosition = Cesium.Cartesian3.fromDegrees(
                                            point[0], point[1], (point[2] + 1)
                                        );
                                        entity.position = heightPosition;
                                        entity.polyline = {
                                            positions: new Cesium.ConstantProperty([
                                                surfacePosition,
                                                heightPosition,
                                            ]),
                                            width: 2,
                                            material: Cesium.Color.fromCssColorString('#4BE1EE')
                                        };
                                        entity.label = { //文字标签
                                            text: reformattedGeoJson.features[i].properties.MC,
                                            font: '12px 微软雅黑',
                                            style: Cesium.LabelStyle.FILL_AND_OUTLINE,
                                            fillColor: Cesium.Color.AQUA,
                                            outlineWidth: 2,
                                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM, //垂直方向以底部来计算标签的位置
                                            //pixelOffset: new Cesium.Cartesian2(0, -48),  //偏移量
                                            disableDepthTestDistance: 350000,
                                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 1000),
                                            scaleByDistance: new Cesium.NearFarScalar(200000, 1.2, 100000000000, 0.5)
                                        }
                                    }

                                }

                            });
                        });
                    })(leafNodes[i], this);
                    break;
                default:
            }
        }

        //如果是管线模型或者智能设备，倾斜摄影透明化
        if (parentIdArr.length > 0) {
            //半透明倾斜摄影
            var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
            if (node.checked == true) {
                layerTree.setAlpha(node, 0.5);
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
                        // if (leafNodes[i].dataSourceWithTree) {
                        //     if (primitive && primitive.bimThumbnailPopup) {
                        //         primitive.bimThumbnailPopup.destroy();
                        //         primitive.bimThumbnailPopup = null;
                        //     }
                        // }
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
            //viewer.scene.primitives.removeAll();
            //清除3dtileset数据
            gViewer.scene.primitives.remove(tileset);

            //清除所有勾选的图层，不能使用removeAll()方法，避免出错
            clearAllLayer();

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
    // overwrite 根据Guid删除指定的primitive
    removePrimitiveByGuid: function (guid) {
        if (viewer) {
            var primitives = viewer.scene.primitives._primitives;
            for (var i = 0; i < primitives.length; i++) {
                if (primitives[i].guid === guid) {
                    viewer.scene.primitives.remove(primitives[i]);
                    break;
                }
            }
        }

    },
    //根据叶子节点定位至对应图层
    locateLayerByLeafNode: function (leafNode) {
        switch (leafNode.dataSourceType) {
            case "Cesium3DTileset":
                var primitive = viewer.scene.primitives.getPrimitiveByGuid(leafNode.guid);
                viewer.flyTo(primitive, {
                    offset: {
                        heading: Cesium.Math.toRadians(0.0),
                        pitch: Cesium.Math.toRadians(-45),
                        range: 100
                    }
                });
                break;
            case "KQGISMapServerImageryProvider":
                //Fly to a Rectangle with a top-down view
                if (leafNode.extent) {
                    var coor = leafNode.extent.split(',');
                    viewer.camera.flyTo({
                        destination: Cesium.Rectangle.fromDegrees(coor[0], coor[1], coor[2], coor[3])
                    });
                } else {
                    var imageryLayer = viewer.imageryLayers.getLayerByGuid(leafNode.guid);
                    viewer.flyTo(imageryLayer);
                }
                break;
            case "GeoJsonExtendDataSource":
                var dataSource = viewer.dataSources.getDataSourceByGuid(leafNode.guid);
                viewer.flyTo(dataSource);
                break;
            default:
        }
    },
    // 3DTiles数据整体偏移，偏移量单位弧度
    transformTileset: function (tileset, offset) {
        var boundingSphere = tileset.boundingSphere;
        var cartographic = Cesium.Cartographic.fromCartesian(boundingSphere.center);
        var before = Cesium.Cartesian3.fromRadians(cartographic.longitude, cartographic.latitude, cartographic.height);
        var after = Cesium.Cartesian3.fromRadians(cartographic.longitude + offset.x / 180 * Math.PI, cartographic.latitude + offset.y / 180 * Math.PI, cartographic.height);
        var translation = Cesium.Cartesian3.subtract(after, before, new Cesium.Cartesian3());
        tileset.modelMatrix = Cesium.Matrix4.fromTranslation(translation);
    },
    // 视图飞到一个点位置
    flyToPoint: function (lon, lat, alt) {
        var entities = viewer.entities.values;
        for (var i = 0; i < entities.length; i++) {
            if (entities[i].id === 'flyToPoint') {
                viewer.entities.remove(entities[i]);
                break;
            }
        }
        var entity = new Cesium.Entity({
            id: 'flyToPoint',
            position: Cesium.Cartesian3.fromDegrees(lon, lat),
            point: {
                pixelSize: 10,
                color: Cesium.Color.WHITE.withAlpha(0.9),
                outlineColor: Cesium.Color.WHITE.withAlpha(0.9),
                outlineWidth: 1
            }
        });
        viewer.entities.add(entity);
        viewer.flyTo(entity, {
            offset: {
                heading: Cesium.Math.toRadians(0.0),
                pitch: Cesium.Math.toRadians(-45),
                range: alt
            }
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

    // 转坐标
    transformCartesianArrayToGeoJsonLine: function (positions) {
        if (!Array.isArray(positions)) return;
        var feature = {
            type: "LineString",
            coordinates: [[]]
        };
        for (var i = 0; i < positions.length; i++) {
            // 笛卡尔坐标转经纬度（单位:弧度）
            var cartographic = Cesium.Cartographic.fromCartesian(positions[i]);
            // 弧度转度
            var longitude = Cesium.Math.toDegrees(cartographic.longitude);
            var latitude = Cesium.Math.toDegrees(cartographic.latitude);
            var coordinate = [longitude, latitude, cartographic.height];
            feature.coordinates[0].push(coordinate);
        }
        return feature;
    },
    //设置相机初始位置
    locateHome: function () {
        viewer.scene.camera.flyTo({
            destination: new Cesium.Cartesian3.fromDegrees(114.34866, 30.45810, 1000),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-30.0),
                roll: Cesium.Math.toRadians(0),
            },
            duration: 3.0
        });
    },
    // 设置相机俯视，仅改变视角，不改变相机当前可见范围
    lookDown: function () {
        // 获取相机当前可见范围
        var rectangle = viewer.scene.camera.computeViewRectangle();
        viewer.scene.camera.flyTo({
            destination: rectangle,
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-90.0),// 平视为0度，-90是俯视
                roll: Cesium.Math.toRadians(0),
            },
            duration: 3.0
        });
    },
    // 右键菜单-图层定位
    layerLocation: function () {
        var nodes = this.zTree.getSelectedNodes();
        this.locateLayerByLeafNode(nodes[0]);
    },
    //获取某个树节点所有的(已勾选/未勾选)叶子节点
    getAllLeafNodes: function (treeNode, leafNodes, checked) {
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes && childrenNodes.length > 0) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    this.getAllLeafNodes(childrenNodes[i], leafNodes, checked);
                }
            }
        } else {
            if (treeNode.checkedOld === checked) {
                leafNodes.push(treeNode);
                treeNode.checkedOld = !checked;
            }
        }
        return leafNodes;
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
    //根据场景类型重新构造JSON化的图层树数据，使其满足zTree的数据结构
    reformattedJsonObj: function (arr, type) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].children && arr[i].children.layer) {
                if (arr[i].children.layer instanceof Array) {
                    this.reformattedJsonObj(arr[i].children.layer, type);
                    arr[i].children = arr[i].children.layer;
                } else {
                    //处理树节点只有一个子节点的情形
                    this.reformattedJsonObj([arr[i].children.layer], type);
                    arr[i].children = [arr[i].children.layer];
                }
            }
            //处理不同场景下图层树不同的默认勾选状态
            if (typeof arr[i].checked === "object") {
                arr[i].checked = arr[i].checked["_" + type] ? arr[i].checked["_" + type] : arr[i].checked["__text"];
            }
        }
        return arr;
    },
    //根据ID定位建筑物
    locateBuildingByID: function (values, type) {
        var that = this;
        var allNodes = this.zTree.transformToArray(this.zTree.getNodes());
        var nodes = allNodes.filter(function (item) {
            // return item.dataSourceType==="KQGISMapServerImageryProvider"&&item.id===type;
            return item.id === type;
        });
        if (type == 'LEAKAGE') {
            layerTree.guid = nodes[0].guid;
        }
        var condition = "", queryLayerValues = values.split(",");
        for (var i = 0; i < queryLayerValues.length; i++) {
            condition += nodes[0].queryLayerField + "='" + queryLayerValues[i] + "' or "
        }
        condition = condition.substr(0, condition.length - 4);
        var url = nodes[0].dataSourceUrl;
        if (url.indexOf("http") > -1 || url.indexOf("https") > -1) {
            url = nodes[0].dataSourceUrl.substr(0, nodes[0].dataSourceUrl.lastIndexOf("/"));
        } else {
            url = aupipeService + nodes[0].dataSourceUrl.substr(0, nodes[0].dataSourceUrl.lastIndexOf("/"));
        }
        var options = {
            url: url + "/queryserver/query",
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
            that.addGeoJsonLayer(that.reformattedGeoJsonData(geoJsonData), that.setOptionsByType(type, true));
        });
    },
    //根据编号查信息
    queryOIDByPipe: function (values, type) {
        var that = this;
        var allNodes = this.zTree.transformToArray(this.zTree.getNodes());
        var nodes = allNodes.filter(function (item) {
            // return item.dataSourceType==="KQGISMapServerImageryProvider"&&item.id===type;
            return item.id === type;
        });
        var condition = "", queryLayerValues = values.split(",");
        for (var i = 0; i < queryLayerValues.length; i++) {
            condition += "PipeP_ID='" + queryLayerValues[i] + "' or "
        }
        condition = condition.substr(0, condition.length - 4);
        var url = nodes[0].dataSourceUrl;
        if (url.indexOf("http:") > -1 || url.indexOf("https:") > -1) {
            url = nodes[0].dataSourceUrl.substr(0, nodes[0].dataSourceUrl.lastIndexOf("/"));
        } else {
            url = aupipeService + nodes[0].dataSourceUrl.substr(0, nodes[0].dataSourceUrl.lastIndexOf("/"));
        }
        var options = {
            url: url + "/queryserver/query",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            layerId: nodes[0].queryLayerId,
            where: condition,
            startIndex: 0,
            reqCount: 200,
            isOverlap: false
        };
        var oid = '';
        this.queryGeoJsonDataBySQL(options, function (geoJsonData) {
            console.log(geoJsonData);
            if (geoJsonData.features == undefined) {
                oid = '';
            } else {
                oid = geoJsonData.features[0].properties.OID;
            }
        });
        return oid;
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
            async: false,
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
    //计算指定图形的面积
    caculateAreaOfGeometry: function (options, callback) {
        var formData = new FormData();
        formData.append("geoSRS", options.geoSRS || 'EPSG:4326');
        formData.append("areaSRS", options.outSRS || 'EPSG:3857');
        formData.append("data", options.data); // 指定图形
        $.ajax({
            type: "post",// 请求类型
            url: options.url || aupipeService + '/KQGis/rest/services/geometryserver/area',// 请求URL
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
    // 重新格式化GeoJson数据，添加高程值
    reformattedGeoJsonData: function (geoJson) {
        if (geoJson && geoJson.features && geoJson.features.length > 0) {
            for (var i = 0; i < geoJson.features.length; i++) {
                var type = geoJson.features[i].geometry.type;
                var height = geoJson.features[i].properties.HIGH || geoJson.features[i].properties.H || geoJson.features[i].properties.Z;
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
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)
            },
            billboard: {
                // image: ctx + 'bus/aupipes/img/3d/ludeng.png',
                width: 32,
                height: 32,
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)
            },
            polygon: {
                material: Cesium.Color.fromAlpha(Cesium.Color.AQUA, 0.5),
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                scaleByDistance: new Cesium.NearFarScalar(200, 1.2, 10000, 0.5)
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
        if (type === "ADDRESS") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/xunjiandian.png';
            options.label.express = "NAME";
        }
        if (type === "JS_POINT") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/shuibiao.png';
            options.label.express = "PipeP_ID";
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
        if (type === "BIM") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/peidianfang.png';
            options.billboard.width = 0;
            options.billboard.height = 0;
            options.label.express = "MC";
        }
        if (type === "gsfm") {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/famen-off.png';
            options.label.express = "EXP_NO";
        }
        //单独设置定位图标
        if (flag) {
            options.billboard.image = ctx + 'bus/aupipes/img/3d/location.png';
            options.billboard.scale = 1.3;
            options.billboard.pixelOffset = new Cesium.Cartesian2(0, -2);
        }
        return options;
    },
    // 添加并定位到GeoJson图层
    addGeoJsonLayer: function (geoJson, options) {
        if (!geoJson) return;
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
                    //entity.polygon.extrudedHeight = entity.properties.HIGH || entity.properties.H;
                    entity.polygon.height = 1;

                    entity.polygon.outline = true;
                    entity.polygon.outlineColor = Cesium.Color.FORESTGREEN;
                    entity.polygon.outlineWidth = 10;
                    entity.polygon.material = Cesium.Color.fromAlpha(Cesium.Color.AQUA, 0.1);
                }
            }
            dataSource.name = "locateBuilding"; // 设置数据源名称，用于检索并删除
            dataSource.autoAvoid(viewer);
            viewer.flyTo(dataSource, {
                offset: {
                    heading: Cesium.Math.toRadians(0.0),
                    pitch: Cesium.Math.toRadians(-45),
                    range: 400
                }
            });
        });
    },
    // 移除已经定位的建筑物
    removeLocatedBuilding: function () {
        if (viewer) {
            var dataSources = viewer.dataSources._dataSources;
            var myDataSources = dataSources.filter(function (item) {
                return item.name === "locateBuilding";
            });
            if (myDataSources && myDataSources.length > 0) {
                for (var i = 0, len = myDataSources.length; i < len; i++) {
                    viewer.dataSources.remove(myDataSources[i]);
                }
            }
        }
    },
    searchAround: function (treeNode, geometry, callback) {
        var where;
        if (treeNode.name.indexOf("点") > -1) {
            where = "1=1 and B_DEPTH<=" + JCBZ.kwfx.data.depth;
        } else {
            where = "1=1 and S_DEEP<=" + JCBZ.kwfx.data.depth;
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
        JCBZ.kwfx.data.index++;
        // 使用缓冲图形来查询
        layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
            if (geoJsonData) {
                if (typeof callback === "function") {
                    callback(geoJsonData, treeNode.id, treeNode.name, JCBZ.kwfx.data.index);
                } else {
                    console.log(geoJsonData);
                }
            }
        });
    },
    //查找楼栋
    searchBuild: function (id, geometry, callback) {
        var queryOptions = {
            url: aupipeService + "/KQGis/rest/services/huanong/queryserver/query",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            layerId: id,
            geometry: JSON.stringify(geometry),
            where: '',
            startIndex: 0,
            reqCount: 1000, // 返回1000条数据
            isOverlap: true
        };
        // 使用缓冲图形来查询
        layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
            if (geoJsonData) {
                if (typeof callback === "function") {
                    callback(geoJsonData);
                } else {
                    console.log(geoJsonData);
                }
            }
        });
    },
    // 获取所有路灯的开/关状态
    getStatusOfAllStreetLight: function (callback) {
        $.ajax({
            type: 'POST',
            url: ctx + 'aupipes/energy/getAllLightStatus' + '?_=' + staticResourcesVersion,
            success: function (response) {
                if (response && response.length > 0) {
                    callback(response);
                }
            },
            error: function (error) {
                console.info(error);
                callback([]);
            }
        });

    },
    drawModel: function (event) {
        switch (event.drawMode) {
            case Cesium.DrawMode.Polyline:
                event.positions.pop();
                drawShapeModel = new Cesium.DrawHandler.PolylinePrimitive({
                    guid: 'drawing',
                    positions: event.positions,
                    perPositionHeight: true
                });
                drawShapeModel.material = new Cesium.Material({
                    fabric: {
                        type: 'Color',
                        uniforms: {
                            color: new Cesium.Color(1.0, 0.0, 0.0, 0.5)
                        }
                    }
                });
                viewer.scene.primitives.add(drawShapeModel);
                break;
            case Cesium.DrawMode.Polygon:
                drawShapeModel = new Cesium.DrawHandler.PolygonPrimitive({
                    guid: 'drawing',
                    positions: event.positions,
                    //material: Cesium.Material.fromType('Checkerboard'),
                    perPositionHeight: true
                });
                drawShapeModel.material = new Cesium.Material({
                    fabric: {
                        type: 'Color',
                        uniforms: {
                            color: new Cesium.Color(1.0, 0.0, 0.0, 0.5)
                        }
                    }
                });
                viewer.scene.primitives.add(drawShapeModel);
                break;
            case Cesium.DrawMode.Rectangle:
                //var rectangle = event.rectangle;
                drawShapeModel = new Cesium.DrawHandler.RectanglePrimitive({
                    guid: 'drawing',
                    rectangle: event.rectangle,
                    //material: Cesium.Material.fromType(Cesium.Material.StripeType)
                });
                drawShapeModel.material = new Cesium.Material({
                    fabric: {
                        type: 'Color',
                        uniforms: {
                            color: new Cesium.Color(1.0, 0.0, 0.0, 0.5)
                        }
                    }
                });
                viewer.scene.primitives.add(drawShapeModel);
                break;
            case Cesium.DrawMode.Circle:
                drawShapeModel = new Cesium.DrawHandler.CirclePrimitive({
                    guid: 'drawing',
                    center: event.center,
                    radius: event.radius,
                    material: Cesium.Material.fromType(Cesium.Material.RimLightingType)
                });
                viewer.scene.primitives.add(drawShapeModel);
                break;
            default:
                break;
        }
        drawHandler.stopDrawing();
        if (flagModel) {
            flagModel = false;
            JCBZ.tsxx.resultLd();
        }

    }


};

//BIM模型数据分层
var bimLayerTree = {
    zTree: null,//zTree对象
    expandFlag: false,
    setting: {
        check: {
            enable: true
        },
        view: {
            fontCss: {color: "#1ef0ed"},
            showIcon: false
        },
        async: {
            enable: true,
        },
        callback: {
            onClick: function (e, treeId, treeNode) {
                //console.log(treeNode.name);
            },
            onCheck: function (e, treeId, treeNode) {
                //console.log(treeNode.name);
            },
            onRightClick: function (e, treeId, treeNode) {
                //console.log(treeNode.name);
            },
            beforeExpand: function (treeId, treeNode) {
                if (treeNode.level == 1) {
                    return false;
                }
            },
        }
    },
    //根据BIM模型数据解析zTree树
    initTree: function (node) {
        this.expandFlag = false;
        var url = node.dataSourceUrl + "?_=" + staticResourcesVersion;
        if (url.indexOf("http:") == -1 && url.indexOf("https:") == -1) {
            url = aupipeService + url;
        }
        node.dataSourceUrl = url;

        //循环取出bim注记
        if (bimLayerInfo) {
            bimNameId = node.id;
            if (!(bimLayerInfo.config.layer instanceof Array)) {
                bimLayerInfo.config.layer = [bimLayerInfo.config.layer];
            }
            bimLayerInfo.config.layer.forEach(function (item) {
                //if(item.layers === node.id){
                objBimLayer = {
                    dataSourceUrl: item.dataSourceUrl,
                    id: item.id,
                    queryLayerId: item.queryLayerId,
                    name: item.name,
                    dataSourceType: item.dataSourceType
                };
                //}
            });
            if (objBimLayer) {
                layerTree.addLayersByLeafNodes([objBimLayer]);
            }
        }


        tileset = new Cesium.Cesium3DTileset({
            url: url
        });
        viewer.scene.primitives.add(tileset);
        tileset.readyPromise.then(function (tileset) {
            viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                duration: 1.5
            });
            var linkPropertyName = 'DbId';//'DbId';//'id';
            tileset.extendCatalog(linkPropertyName);
            //目录树添加到页面容器
            tileset.initializeZTreeDOM('bimTree', viewer, bimLayerTree.setting, false).then(res => {
                //var treeObj = res.treeObj;//ztree目录树对象
                //var zTreeDOM = ztreeDOM; //ztree目录树对应的DOM对象
                //var ztreeDOMContainer = container //装载ztree目录树容器
                bimLayerTree.zTree = res.treeObj;

                //墙，门楼等不勾选
                var nodes = bimLayerTree.zTree.getNodes();
                if (nodes.length > 0) {
                    for (var j = 0; j < nodes[0].children.length; j++) {
                        var name = nodes[0].children[j].name;
                        if (name == "墙" || name == "天花板" || name == "门") {
                            var childrenNode = bimLayerTree.zTree.getNodeByParam("name", nodes[0].children[j].name, null);
                            bimLayerTree.zTree.checkNode(childrenNode, "", true, true);
                        }
                    }
                    var arr = $("#bimTree").find("ul").find("li span");
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].classList.value.indexOf("center_close")) {
                            if (i == (arr.length - 4)) {
                                $(arr[i]).removeClass("center_close").addClass("bottom_docu");
                            } else {
                                $(arr[i]).removeClass("center_close").addClass("center_docu");
                            }

                        }
                    }
                }

            });
            //开启拾取
            if (pickType == "zsPoint") {
                tileset.setEnablePickFeatures(viewer, false);
            } else {
                tileset.setEnablePickFeatures(viewer, true);
            }
            //监听拾取事件
            var pickedEvent = tileset.getPickedEvent();
            if (pickedEvent) {
                pickedEvent.addEventListener(function (res) {
                    if (pickColor != null) {
                        Cesium.Color.clone(pickColor.color, res.feature.color);
                    }
                    var feature = res.feature; //拾取到的要素对象
                    pickColor = feature;
                    pickColor1 = res.feature.color;
                    feature.color = Cesium.Color.LIME;

                    var html = $(res.tableHTML).find("tbody")[0].innerHTML;

                    var obj = {};
                    var arr = res.properties.categories;
                    for (var i = 0; i < arr.length; i++) {
                        for (var j = 0; j < arr[i].props.names.length; j++) {
                            obj[arr[i].props.names[j]] = arr[i].props.values[j];
                        }
                    }
                    hlzsId = null;
                    pickProtry = null;
                    if (pickType == "zsPoint") {
                        if (obj) {
                            if (obj["族"] && obj["族"].indexOf("配电箱") > -1) {
                                hlzsId = obj["ID"];
                                //hlzsId = "01030012";
                            }
                            if (obj["族"] && obj["族"].indexOf("摄像头") > -1) {
                                cameraId = obj["ID"];

                                $.get(ctx + "screen/yxjk/jdxx/monitorInfo/" + cameraId, function (result) {
                                    if (result.data.list != null) {
                                        if (spIndex == 1) {
                                            spIndex = 2;
                                            openVideo(result.data.list.cameraIndexCode);
                                        }
                                    }
                                });
                            }
                        }
                        if (obj["族"] && obj["族"].indexOf("低压配电箱") > -1) {
                            pickProtry = obj;
                        } else if (obj["族"] && obj["族"].indexOf("高压配电箱") > -1) {
                            pickProtry = obj;
                        } else {
                            pickProtry = html;
                        }
                    } else {
                        //属性弹窗
                        if (obj["族"] && obj["族"].indexOf("高压配电箱") > -1) {
                            $.get(ctx + "screen/jcbz/getHlAttriBute?hlId=" + obj["ID"], function (result) {
                                var powerAttribute;
                                if (result.data.hlAttribute && result.data.hlAttribute.length > 0) {
                                    powerAttribute = result.data.hlAttribute[0];
                                }
                                openAttributeLayer(obj, powerAttribute, "treeBim", "high");
                            });
                        } else if (obj["族"] && obj["族"].indexOf("低压配电箱") > -1) {
                            $.get(ctx + "screen/jcbz/getHlAttriBute?hlId=" + obj["ID"], function (result) {
                                var powerAttribute;
                                if (result.data.hlAttribute && result.data.hlAttribute.length > 0) {
                                    powerAttribute = result.data.hlAttribute[0];
                                }
                                openAttributeLayer(obj, powerAttribute, "treeBim", "low");
                            });
                        } else if (obj["族"] && obj["族"].indexOf("摄像头") > -1) {
                            cameraId = obj["ID"];

                            $.get(ctx + "screen/yxjk/jdxx/monitorInfo/" + cameraId, function (result) {
                                if (result.data.list != null) {
                                    if (spIndex == 1) {
                                        spIndex = 2;
                                        openVideo(result.data.list.cameraIndexCode);
                                    }
                                }
                            });
                        } else {
                            openAttributeLayer(html, "", "");
                        }
                    }
                });
            }
        });
    },
    //切换图层树节点全部展开/收起状态
    toggleExpandState: function () {
        /*if(this.zTree) {
            this.expandFlag=this.zTree.expandAll(!this.expandFlag);
            return this.expandFlag;
        }*/
    }
};
