//拉闸限电
var LZXD = {
    fireParticle: null,//电力事故着火粒子对象
    supplyPowerPointParent: null,//拉闸限电需要高亮的阀门
    supplyPowerPolylineParent: null,//存放拉闸限电需要高亮的管线
    powerTubePos: null,//存放点击管线的点坐标
    powerTubeLines: null,//存放请求到的管线数据
    powerValves: null,//存放请求到的点数据
    electricityEffect: null, //存放电流脉冲动态流向效果
    powerLineOid: null,
    powerInitialFlowLine: null,//存放初始化电流脉冲效果
    highLightFloorParent: null,//存放高亮的楼宇父级对象
    houseId : null,//存放查询房屋ID
    bhType : null,
    pickedObject : null,
    //标绘事故点选取
    pick: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(0).addClass("active");
        LZXD.removeLeftCli();
        LZXD.addLeftClickEvent();
        /*var treeNode = layerTree.zTree.getNodeByParam("id", "HOUSE");
        pickFeature.treeNode = treeNode;
        pickFeature.start("Feature2D",function(data){
            LZXD.houseId = data.features[0].properties.FWID;
        });*/
    },
    //拉闸分析
    analyse: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(1).addClass("active");
       /* LZXD.houseId = '1576137776507' ;*/
        if(LZXD.houseId != null){
            if(LZXD.houseId == ""){
                layer.msg("房屋ID为空");
            }else{
                JCBZ.lzxd.lzxdLzfxNc(LZXD.houseId,LZXD.bhType,"");
            }
        } else {
            layer.msg("请先选择事故点!");
        }
        /*if (LZXD.powerLineOid != null) {
            //勾选对应的图层
            selectedLinePipe(layerCfg.jcbz.lzfx);

            LZXD.removeLeftCli();
            LZXD.newGetValves(LZXD.powerLineOid, LZXD.powerTubePos.height);
            JCBZ.jcbzData.ldNumList=[];
            JCBZ.lzxd.lzxdLzfxNc(LZXD.powerLineOid);
        } else {
            layer.msg("请先选择事故点!");
        }*/
    },
    //添加点击注册事件
    addLeftClickEvent: function () {
        gViewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
            var scene = gViewer.scene;
            if(LZXD.pickedObject != null){
                if (LZXD.pickedObject.id) {
                    if((LZXD.pickedObject.id._billboard._image._value.indexOf("peidianfang")!=-1)){
                        LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang.png';
                    }else if((LZXD.pickedObject.id._billboard._image._value.indexOf("loudong")!=-1)){
                        LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                    }
                }
            }
            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                LZXD.pickedObject = scene.pick(movement.position);
                if(LZXD.pickedObject){
                    if(LZXD.pickedObject.id){
                        if(LZXD.pickedObject.id._properties['_FWID']){
                            var id = LZXD.pickedObject.id._properties['_FWBM']._value;
                            LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                            if(id != undefined){
                                LZXD.houseId = id;
                                LZXD.bhType = "house";
                            }
                        }
                        if(LZXD.pickedObject.id._properties['_PDFID']){
                            var id = LZXD.pickedObject.id._properties['_PDFID']._value;
                            LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang-s.png';
                            if(id != undefined){
                                LZXD.houseId = id;
                                LZXD.bhType = "pdf";
                            }
                        }
                    }
                }
                /*var position = gViewer.scene.pickPosition(movement.position);//世界坐标
                var lonAndLat = kqWeb3d.worldCoordinatesToLat(position.x, position.y, position.z);
                LZXD.powerTubePos = {};
                LZXD.powerTubePos.lon = lonAndLat[0];  //经度
                LZXD.powerTubePos.lat = lonAndLat[1];   //维度
                LZXD.powerTubePos.height = lonAndLat[2];
                if (pickedObject) {
                    if (!LZXD.supplyPowerPointParent) {
                        LZXD.supplyPowerPointParent = gViewer.entities.add({
                            id: "gdgxd",//供电管线点
                            show: true
                        });
                    }
                    if (!LZXD.supplyPowerPolylineParent) {
                        LZXD.supplyPowerPolylineParent = gViewer.entities.add({
                            id: "gdgxx",//供电管线线
                            show: true
                        });
                    }
                    if (LZXD.supplyPowerPointParent && LZXD.supplyPowerPointParent._children.length > 0) {
                        LZXD.clearHigLightValves();
                    }
                    if (LZXD.supplyPowerPolylineParent && LZXD.supplyPowerPolylineParent._children.length > 0) {
                        LZXD.clearHigLightLines();
                    }
                    if (LZXD.fireParticle) {
                        gViewer.scene.primitives.remove(LZXD.fireParticle);
                        LZXD.fireParticle = null;
                    }
                    if (gViewer.entities.getById('dlsg')) {
                        gViewer.entities.removeById('dlsg');
                    }
                    if (LZXD.electricityEffect) {
                        LZXD.electricityEffect.destroy();
                        LZXD.electricityEffect = null;
                    }
                    addCesium3DTilesetPickEvent(pickedObject, lonAndLat);
                } else {
                    layer.msg("无效的管点");
                }*/
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

        function addCesium3DTilesetPickEvent(pickedFeature, position) {
            if (Cesium.defined(pickedFeature) && pickedFeature instanceof Cesium.Cesium3DTileFeature) {
                var properties = pickedFeature.getPropertyNames() || [];
                //var oid;
                if (properties.indexOf('oid') > -1) {
                    LZXD.powerLineOid = pickedFeature.getProperty('oid');
                }
                // if (featureName == '铜') {
                var name = pickedFeature._content.tileset.name;
                if (name == '供电管线段' || name == '供电管线点') {
                    GLOBAL3D.drawBillboard(position, 'dlsg', ctx + 'bus/aupipes/analyse/images/电力事故.png');
                    LZXD.particleFire(ctx + 'bus/aupipes/analyse/images/fire.png', position);
                } else {
                    layer.msg("请选择供电管线!");
                }
            } else {
                //clickHandler(movement);
            }
        }
    },
    //移除左键点击查询事件
    removeLeftCli: function () {
        if (gViewer) {
            gViewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },
    //着火粒子效果
    particleFire: function (imageUrl, staticPosition) {
        gViewer.clock.shouldAnimate = true;//粒子效果不显示可能是这个属性没设置
        var viewModel = {
            emissionRate: 10.0,
            gravity: 0,
            minimumParticleLife: 3,
            maximumParticleLife: 3,
            minimumSpeed: 3,
            maximumSpeed: 5,
            startScale: 3,
            endScale: 5,
            particleSize: 10,
        };
        var entity = gViewer.entities.add({
            position: Cesium.Cartesian3.fromDegrees(staticPosition[0], staticPosition[1], staticPosition[2] + 1)
        });

        function computeModelMatrix(entity, time) {
            return entity.computeModelMatrix(time, new Cesium.Matrix4());
        }

        var gravityScratch = new Cesium.Cartesian3();

        function applyGravity(p, dt) {
            var position = p.position;
            Cesium.Cartesian3.normalize(position, gravityScratch);
            Cesium.Cartesian3.multiplyByScalar(gravityScratch, viewModel.gravity * dt, gravityScratch);
            p.velocity = Cesium.Cartesian3.add(p.velocity, gravityScratch, p.velocity);
        }

        var emitterModelMatrix = new Cesium.Matrix4();
        var translation = new Cesium.Cartesian3();
        var rotation = new Cesium.Quaternion();
        var hpr = new Cesium.HeadingPitchRoll();
        var trs = new Cesium.TranslationRotationScale();

        //改变粒子系统的位置
        function computeEmitterModelMatrix() {
            hpr = Cesium.HeadingPitchRoll.fromDegrees(0.0, 0.0, 0.0, hpr);
            trs.translation = Cesium.Cartesian3.fromElements(0, 0, 0, translation);
            trs.rotation = Cesium.Quaternion.fromHeadingPitchRoll(hpr, rotation);
            return Cesium.Matrix4.fromTranslationRotationScale(trs, emitterModelMatrix);
        }

        LZXD.fireParticle = new Cesium.ParticleSystem({
            image: imageUrl,
            emissionRate: viewModel.emissionRate,
            startColor: new Cesium.Color(1, 1, 1, 1),
            endColor: new Cesium.Color(1, 0, 0, 0),
            startScale: viewModel.startScale,
            modelMatrix: computeModelMatrix(entity),
            endScale: viewModel.endScale,
            minimumParticleLife: viewModel.minimumParticleLife,
            maximumParticleLife: viewModel.maximumParticleLife,
            minimumSpeed: viewModel.minimumSpeed,
            maximumSpeed: viewModel.maximumSpeed,
            imageSize: new Cesium.Cartesian2(viewModel.particleSize, viewModel.particleSize),
            emissionRate: viewModel.emissionRate,
            lifetime: 6.0,
            //循环是否开启
            loop: true,
            emitter: new Cesium.CircleEmitter(1.0),
            emitterModelMatrix: computeEmitterModelMatrix(),
            updateCallback: applyGravity,
            sizeInMeters: true,
        });
        gViewer.scene.primitives.add(LZXD.fireParticle);
        gViewer.camera.flyTo({
            destination: Cesium.Cartesian3.fromDegrees(staticPosition[0], staticPosition[1] - 0.00095, staticPosition[2] + 80),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-45),
                roll: 0
            },
            duration: 1.5
        });
        gViewer.scene.preRender.addEventListener(function (scene, time) {
            /*var height = kqWeb3d.getCurrentExtent(gViewer);
            if (LZXD.fireParticle) {
                if (height.height > 300) {
                    LZXD.fireParticle.lifetime = 0;
                } else {
                    LZXD.fireParticle.lifetime = 6.0;
                }
            }*/
        });

    },
    //清除着火效果
    clearParticleFire: function (particles) {
        if (LZXD.particles) {
            gViewer.scene.primitives.remove(LZXD.particles);
        }
    },
    //高亮阀门
    powerHigLightValves: function (longtitude, lattitude, height) {
        //var color = new Cesium.Color(77 / 255, 201 / 255, 1, 0.9);
        var dataSoure = new Cesium.CustomDataSource();
        dataSoure.name = '高亮供电闸门';
        dataSoure.entities.add({
            parent: LZXD.supplyPowerPointParent,//爆管受影响的阀门 ：停水阀门
            position: Cesium.Cartesian3.fromDegrees(longtitude, lattitude, height),
            ellipse: {
                height: height,
                semiMinorAxis: 5,
                semiMajorAxis: 5,
                material: new Cesium.Kq3dEllipseFadeMaterialProperty({
                    color: Cesium.Color.RED
                })
            },
            show: true
        });
        gViewer.dataSources.add(dataSoure);
    },
    //清除高亮的阀门
    clearHigLightValves: function () {
        var dataSources = [];
        $.each(gViewer.dataSources._dataSources, function (dex, val) {
            if (val && val.name == '高亮供电闸门') {
                dataSources.push(val);
            }
        })
        if (dataSources.length > 0) {
            for (var x = 0; x < dataSources.length; x++) {
                gViewer.dataSources.remove(dataSources[x]);
            }
        }
    },
    //高亮管线
    powerHigLightLines: function (gViewer, powerLineParent, positionArr, width) {
        kqWeb3d.drawVolumeBox(gViewer, powerLineParent, positionArr, Cesium.Color.YELLOWGREEN, width);
    },
    //清除高亮的管线
    clearHigLightLines: function () {
        if (LZXD.supplyPowerPolylineParent) {
            $.each(LZXD.supplyPowerPolylineParent._children, function (dex, val) {
                gViewer.entities.remove(val);
            })
            gViewer.entities.remove(LZXD.supplyPowerPolylineParent);
            LZXD.supplyPowerPolylineParent = null;
        }
        /*for (var x = 0; x < gViewer.scene.primitive.length; x++){
            if (gViewer.scene.primitives.get(x).name == ('供热管线段' || '供热管线点')) {
                gViewer.scene.primitives.get(x).style=new Cesium.Cesium3DTileStyle({
                    color: {
                        conditions: [
                            ["true","rgba(255,255,255,1)"]
                        ]
                    }
                });
            }
        }*/
    },
    //根据oid获取有问题的管线和阀门点
    newGetValves: function (oid, height) {
        var url = layerCfg.lzxdUrl + "?oid=" + oid + "&type=";
        $.get(url, function (data) {
            if (data.code == 200) {
                LZXD.powerTubeLines = data.line;
                LZXD.powerValves = data.point;
                if (data.point && data.point.length > 0) {
                    var pointArr = data.point;
                    for (var x = 0; x < pointArr.length; x++) {
                        var postion = pointArr[x].geometry.coordinates;
                        var hei = pointArr[x].properties.PipeP_H;
                        LZXD.powerHigLightValves(postion[0], postion[1], hei);
                        kqWeb3d.flyToDegrees(gViewer.camera, postion[0], postion[1], hei, 0, -90, 1.5);
                    }
                    gViewer.entities.getById('gdgxd').show = true;
                }
                if (data.line && data.line.length > 0) {
                    for (var y = 0; y < data.line.length; y++) {
                        var posArr = [];
                        var startHei = data.line[y].properties.START_H;
                        var endHei = data.line[y].properties.END_H;
                        data.line[y].geometry.coordinates[0][2] = startHei;
                        data.line[y].geometry.coordinates[1][2] = endHei;
                        $.each(data.line[y].geometry.coordinates, function (index, value) {
                            posArr.push(value[0]);
                            posArr.push(value[1]);
                            posArr.push(value[2]);
                        });
                        var index = data.line[y].properties.D_S.indexOf('X');
                        var width = 0;
                        if (index > 0) {
                            var w_h = data.line[y].properties.D_S.split('X');
                            width = Number(w_h[0]) >= Number(w_h[1]) ? Number(w_h[0]) : Number(w_h[1]);
                        }
                        LZXD.powerHigLightLines(gViewer, LZXD.supplyPowerPolylineParent, posArr, (width + 100) / 2000);
                    }
                }

            } else {
                console.log('')
            }
        });
    },
    //配电房脉冲效果
    powerDirection: function () {
        var layerWork = null;
        Cesium.loadJson(' ./static/config/power.json').then(function (jsonData) {
            // Do sometding witd tde JSON object
            var jsonObj = jsonData;
            var n = [];//箭头数据
            var s = [];//点数据
            for (var item in jsonObj) {
                var coords = jsonObj[item];
                // var coords1 = coords['coord1'];
                // var coords2 = coords['coord2'];
                var coords1 = coords['coord1'];
                var coords2 = coords['coord2'];

                var coords1Lg = coords1[0];
                var coords1Li = coords1[1];
                var coords1Nm = coords1[2];

                var coords2Lg = coords2[0];
                var coords2Li = coords2[1];
                var coords2Nm = coords2[2];

                n.push({
                    fromName: coords1Nm,
                    toName: coords2Nm,
                    coords: [[Number(coords1Lg), Number(coords1Li)], [Number(coords2Lg), Number(coords2Li)]],
                });
                s.push({
                    name: coords2Nm,
                    value: [Number(coords2Lg), Number(coords2Li)]
                });
                var line = {
                    name: '流动线条',
                    type: "lines",
                    coordinateSystem: "GLMap",
                    zlevel: 2,
                    effect: {
                        show: !0,
                        period: 6,
                        trailLengtd: .1,
                        symbol: "arrow",
                        symbolSize: 20
                    },
                    lineStyle: {
                        normal: {
                            color: "#60ff44",
                            width: 3,
                            opacity: 0.9,
                            curveness: .2
                        }
                    },
                    data: n
                }

                var stroke = {
                    name: 'stroke',
                    type: "effectScatter",
                    coordinateSystem: "GLMap",
                    zlevel: 2,
                    rippleEffect: {
                        brushType: "stroke"
                    },
                    label: {
                        normal: {
                            show: !0,
                            position: "top",
                            formatter: "{b}"
                        }
                    },
                    symbolSize: function (number) {
                        return 20
                    },
                    itemStyle: {
                        normal: {
                            color: function (number) {
                                return number < 80 ? "green" : 120 < number ? "red" : "yellow"
                            }
                        }
                    },
                    data: s
                }
            }
            ;
            //fillTableMessage(n);
            var option = {
                animation: !1,
                GLMap: {},
                series: [line, stroke],
            };
            layerWork = null == layerWork ? layerWork = new Cesium.FlowEcharts(gViewer, option) : layerWork.updateOverlay(option);
            LZXD.electricityEffect = layerWork;
        }).otherwise(function (error) {
            console.log('load data error')
        });


    },
    //绘制电流流向效果
    drawPowerLine: function () {
        //半透明倾斜摄影
        if(layerTree.zTree){
            var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
            if (node.checked == true) {
                layerTree.setAlpha(node, 0.5);
            }

            LZXD.powerInitialFlowLine = gViewer.entities.add({
                id: "dlldgx",//电流流动管线
                show: true
            });
            Cesium.loadJson(ctx + '/bus/aupipes/供电管线段.json').then(function (t) {
                var geojson = {
                    "type": "FeatureCollection", "features": []
                }
                for (var x = 0; x < t.features.length; x++) {
                    var D_S = t.features[x].attributes.D_S;
                    var coordinates = t.features[x].geometry.paths[0];
                    var positions = [];
                    if (coordinates.length > 0) {
                        $.each(coordinates, function (key, value) {
                            //positions.push(Cesium.Cartesian3.fromDegrees(value[0], value[1],value[2]));
                            positions.push(value[0]);
                            positions.push(value[1]);
                            positions.push(value[2]);
                        })
                    }
                    gViewer.entities.add({
                        name: '电流流向',
                        parent: LZXD.powerInitialFlowLine,
                        polyline: {
                            positions: Cesium.Cartesian3.fromDegreesArrayHeights(positions),
                            width: 3,
                            disableDepthTestDistance: 350000,
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                            scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 2000, 0.5),
                            material: new Cesium.DynamicLineMaterialProperty({
                                //color: Cesium.Color.AQUA,
                                //duration: 2e3,
                                image: ctx + 'bus/aupipes/analyse/images/电流1.gif',
                                //repeat: new Cesium.Cartesian2(10, 1),
                                //isAxisY: false
                            }),
                            clampToGround: false
                        }
                    })
                }
                // gViewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(geojson, options)).then(function (t) {
                //  gViewer.flyTo(t);
                // });
            });
        }

    },
    //清除电流流向效果
    clearPowerDirection: function () {
        /*for (var x = 0; x < gViewer.dataSources.length; x++){
            if (gViewer.dataSources.get(x).name == '流动线条'){
                gViewer.dataSources.remove(gViewer.dataSources.get(x));
            }
        }*/
        if (LZXD.powerInitialFlowLine) {
            var children = gViewer.entities.getById('dlldgx')._children;
            for (var x = 0; x < children.length; x++) {
                gViewer.entities.remove(gViewer.entities.getById('dlldgx')._children[x]);
            }
            gViewer.entities.remove(gViewer.entities.getById('dlldgx'));
            LZXD.powerInitialFlowLine = null;
        }
    },
    //高亮楼宇
    highlightFloor: function () {
        gViewer.entities.add({
            name: '高亮楼宇',
            parent: LZXD.highLightFloorParent,
            show: true,
            polygon: {
                hierarchy: Cesium.Cartesian3.fromDegreesArray([114.34276, 30.47692, 114.34344, 30.47692, 114.34344, 30.47665, 114.34277, 30.47669]),
                material: Cesium.Color.LIMEGREEN.withAlpha(1)
            },
        });
    },
    //清除高亮楼宇效果
    clearHighFloor: function () {
        if (LZXD.highLightFloorParent) {
            var children = gViewer.entities.getById('glly')._children;
            for (var x = 0; x < children.length; x++) {
                gViewer.entities.remove(gViewer.entities.getById('glly')._children[x]);
            }
            LZXD.highLightFloorParent = null;
        }
    },
    clear: function () {
        layerTree.removeLocatedBuilding();
        if (LZXD.supplyPowerPointParent && LZXD.supplyPowerPointParent._children.length > 0) {
            LZXD.clearHigLightValves();
        }
        if (LZXD.supplyPowerPolylineParent && LZXD.supplyPowerPolylineParent._children.length > 0) {
            LZXD.clearHigLightLines();
        }
        if (LZXD.fireParticle) {
            gViewer.scene.primitives.remove(LZXD.fireParticle);
            LZXD.fireParticle = null;
        }
        if (LZXD.electricityEffect) {
            LZXD.electricityEffect.destroy();
            LZXD.electricityEffect = null;
        }
        JCBZ.lzxd.lzxdBhsgNc();
    },
    clearPic: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(2).addClass("active");
        LZXD.clear();
        LZXD.removeLeftCli();
        if (gViewer) {
            if (gViewer.entities.getById('dlsg')) {
                gViewer.entities.removeById('dlsg');
            }

            //清除预案动画的人物模型
            if (gViewer.entities.getById('ksjentity')) {
                //ksjBox = gViewer.entities.getById('ksjentity');
                gViewer.entities.removeById('ksjentity');
            }
            if (gViewer.entities.getById('ddhentity')) {
                //ksjBox = gViewer.entities.getById('ksjentity');
                gViewer.entities.removeById('ddhentity');
            }
            //ddhBox = gViewer.entities.getById('ddhentity')
            //gViewer.entities.remove(ddhBox)
        }
        LZXD.powerTubePos = {};
        LZXD.pickLineOid = null;
        LZXD.houseId = null;
        if(LZXD.pickedObject){
            if(LZXD.pickedObject.id){
                //LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                if((LZXD.pickedObject.id._billboard._image._value.indexOf("peidianfang")!=-1)){
                    LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/peidianfang.png';
                }else if((LZXD.pickedObject.id._billboard._image._value.indexOf("loudong")!=-1)){
                    LZXD.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                }
            }
            LZXD.pickedObject = null;
        }
    }


}

//绘制流向线
function drawArrowLines(postions, width) {
    var lines = gViewer.entities.add({
        name: "polyline",
        polyline: {
            positions: Cesium.Cartesian3.fromDegreesArrayHeights(postions),
            width: width,
            material: new Cesium.DynamicLineMaterialProperty({
                color: Cesium.Color.AQUA,
                duration: 3,
                image: "./static/images/arrow.png",
                repeat: new Cesium.Cartesian2(10, 1)
            }),
            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 200)
        }
    });
    gViewer.flyTo(lines);
}

function clearVideoStep() {
    setTimeout(function () {
        $(".planloadingcontent").remove();
        $(".planloadingcenterUl").find("li").removeClass("showYagl");

        if (particle1) {
            gViewer.scene.primitives.remove(particle1);
        }
    }, 5000);
}

//预案管理模块
var timer;
var change;
var isClick = false;
var ksjBox;
var ddhBox;
var pdf2Box;
var pdf3Box;
var pdf7Box;
var timer, particle1;
var planloadingcenterUl = document.getElementsByClassName("planloadingcenterUl");
var planloadingcenterli = document.getElementsByClassName("noshow");

//泵房停水预案
function showRightPanel() {
    var num = 0;
    $.getJSON(ctx + 'bus/aupipes/analyse/config/planLoading.json', function (result) {
        flyTo();

        function flyTo() {
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(result.initialflyTo.position.x, result.initialflyTo.position.y, result.initialflyTo.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-28.650940597217918),
                    roll: 0.0
                },
                duration: result.initialflyTo.time
            });

        }

        var jishu = 0;
        var changeTime = result.changetime;
        setTimeout(function () {
            play();
            timer = setInterval(play, changeTime)
        }, 5000);

        //第一步骤
        function step1() {
            if(!timer){return false;}
            jishu = 1;
            $(".controlBtn").css("display", "block");
            $(".planloading").show();
            $(".planloadingcontent").css("opacity", "1");
            planloadingcenterli[0].className = "noshow showYagl";
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <div class="planloadingtitle"></div>      
                    <img id="onegif" src="" style="width: 100%;height: 100%;">
                    <div class="warntext"></div>
                        </div>`;

            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });

            $("#onegif").attr('src', ctx + result.waterStopAlarm.image.background);
            $(".warntext").text(result.waterStopAlarm.label.text);
            $(".planloadingtitle").text(result.waterStopAlarm.title)
        }

        //第二步骤
        function step2() {
            if(!timer){return false;}
            jishu = 2;
            planloadingcenterli[0].className = "noshow";
            planloadingcenterli[1].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(result.informStaff.houqinposition.x, result.informStaff.houqinposition.y, result.informStaff.houqinposition.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-28.650940597217918),
                    roll: 0.0
                },
                duration: 2
            });
            setTimeout(function () {
                var position = Cesium.Cartesian3.fromDegrees(result.informStaff.houqinposition.x, result.informStaff.houqinposition.y, result.informStaff.houqinposition.z);
                var url = ctx + 'bus/aupipes/analyse/images/personModel/ksj2.gltf';
                var entity = gViewer.entities.add({
                    id: 'ksjentity',
                    position: position,
                    model: {
                        uri: url,
                        show: true,
                        scale: 0.20,
                    }
                });
                gViewer.zoomTo(entity);


                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                                <div class="planloadingcontenttext"></div>
                                <div class="planloadingtitle"></div>  
                                <div class="leftphone">
                                <img id="leftphonepng" src="" style="width: 100%;height: 100%;"></div>
                                <div class="rightphone">
                                <img id="rightphonepng" src="" style="width: 100%;height: 100%;"></div>
                                <div class="warntext"></div>
                        </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $(".planloadingcontenttext").text(result.informStaff.label.text)
                $(".warntext").text(result.informStaff.label.texttwo)
                $("#leftphonepng").attr('src', ctx + result.noticeOfWaterSupplyTime.image.phone);
                $("#rightphonepng").attr('src', ctx + result.informStaff.image.rightphone);
                $(".planloadingtitle").text(result.informStaff.title)
                setTimeout(function () {
                    $(".leftphone").slideDown()
                }, 500)
                setTimeout(function () {
                    $(".leftphone").css("display", "none");
                    $(".rightphone").slideDown()
                }, 2000)
            }, 2000)

        }

        //第三步骤
        function step3() {
            if(!timer){return false;}
            jishu = 3;
            planloadingcenterli[1].className = "noshow";
            planloadingcenterli[2].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            ksjBox = gViewer.entities.getById('ksjentity');
            gViewer.entities.remove(ksjBox);
            var position = Cesium.Cartesian3.fromDegrees(result.inflowTime.houqinposition.x, result.inflowTime.houqinposition.y, result.inflowTime.houqinposition.z);
            var url = ctx + 'bus/aupipes/analyse/images/personModel/ddh.gltf';
            var entity = gViewer.entities.add({
                id: 'ddhentity',
                position: position,
                model: {
                    uri: url,
                    show: true,
                    scale: 0.20,
                }
            });
            gViewer.zoomTo(entity);
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent"> 
                            <div class="planloadingcontenttext"></div>
                            <div class="texttop">
                            <img id="texttoppng" src="" style="width: 100%;height: 100%;"> </div>
                            <div class="textbottom">
                            <img id="textbottompng" src="" style="width: 100%;height: 100%;"> </div>
                            <div class="warntext"></div>
                            <div class="shuiwujituan"></div>
                    </div>`;
            fireDivtwo = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $(".warntext").text(result.inflowTime.label.texttwo);
            // $("#threepng").attr('src', result.informStaff.image.background);
            $(".planloadingcontenttext").text(result.inflowTime.label.text);
            $("#texttoppng").attr('src', ctx + result.inflowTime.image.texttop);
            $("#textbottompng").attr('src', ctx + result.inflowTime.image.textbottom);
            $(".shuiwujituan").text(result.inflowTime.label2.text2);
            setTimeout(function () {
                $(".shuiwujituan").css("opacity", "1");
                $(".textbottom").css("opacity", "1")
            }, 2500)
        }

        //第四步骤
        function step4() {
            if(!timer){return false;}
            jishu = 4;
            planloadingcenterli[2].className = "noshow";
            planloadingcenterli[3].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            ddhBox = gViewer.entities.getById('ddhentity');
            gViewer.entities.remove(ddhBox)
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent"> 
                                    <img id="fourpng" src="" style="width: 100%;height: 100%;">
                                    <div class="swjttz"></div>
                                    <div class="planloadingcontenttext"></div> 
                                    <div class="phone4">
                                    <img id="phone4png" src="" style="width: 100%;height: 100%;"></div>
                                    <div class="textcenter">
                                    <img id="textcenterpng" src="" style="width: 100%;height: 100%;">
                                    </div>
                                    <div class="warntext"></div>
                            </div>`;
            fireDivtwo = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $(".warntext").text(result.noticeOfWaterSupplyTime.label.texttwo);
            $(".swjttz").text(result.noticeOfWaterSupplyTime.label.textthree);
            $("#fourpng").attr('src', ctx + result.informStaff.image.background);
            $(".planloadingcontenttext").text(result.noticeOfWaterSupplyTime.label.text);
            $("#phone4png").attr('src', ctx + result.noticeOfWaterSupplyTime.image.phone);
            $("#textcenterpng").attr('src', ctx + result.noticeOfWaterSupplyTime.image.text);
        }

        //第五步骤
        function step5() {
            if(!timer){return false;}
            jishu = 5;
            planloadingcenterli[3].className = "noshow";
            planloadingcenterli[4].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(result.recoveryOfWaterSupply.position.x, result.recoveryOfWaterSupply.position.y, result.recoveryOfWaterSupply.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-28.650940597217918),
                    roll: 0.0
                },
                duration: 2.5
            });
            setTimeout(function () {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                                            <div class="planloadingtitle"></div>   
                                        <img id="fivepng" src="" style="width: 100%;height: 100%;">
                                        <div class="warntext"></div> 
                                </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $(".warntext").text(result.recoveryOfWaterSupply.label.texttwo);
                $("#fivepng").attr('src', ctx + result.recoveryOfWaterSupply.image.background);
                $(".planloadingtitle").text(result.recoveryOfWaterSupply.title);

                //最后清除关于动画的内容
                clearVideoStep();

            }, 2600)

        }

        function play() {
            switch (jishu) {
                case 0:
                    step1();
                    break;
                case 1:
                    step2();
                    break;
                case 2:
                    step3();
                    break;
                case 3:
                    step4();
                    break;
                case 4:
                    step5();
                    break;
                default:
                    clearInterval(timer)
            }
        }

        //视频操作按钮
        //播放
        $('.centerBtns').on('click', function () {
            changeTime = result.changetime;
            timer = setInterval(play, changeTime);
            play()
        });
        //暂停
        $('.stopBtns').on('click', function () {
            changeTime = result.changetime;
            clearInterval(timer)
        });
        //快进
        $('.rightBtns').on('click', function () {
            changeTime = result.kuaijinchangetime;
            clearInterval(timer);
            timer = setInterval(play, changeTime);
        });
        //快退
        $('.leftBtns').on('click', function () {
            changeTime = result.kuaituichangetime;
            clearInterval(timer);
            timer = setInterval(play, changeTime);
        })
    })

}

//配电房停电预案
function pdfVideoPlay() {
    var num = 0;
    var changeTime = 8000;
    $.getJSON(ctx + "bus/aupipes/analyse/config/pdftd.json", function (data) {
        changeTime = data.changetime;
        flyTo();
        function flyTo(){
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.xitongbaojing.position.x, data.xitongbaojing.position.y, data.xitongbaojing.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    runplanLoading();
                    num = 0;
                    timer = setInterval(runplanLoading, changeTime)
                }
            });
        }



        function planLoading1() {
            if(!timer){return false;}
            planloadingcenterli[0].className = "noshow showYagl";
            num = 1;
            $(".planloading").show();
            $(".planloadingcontent").css("opacity", "1");
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <div class="pdfsb">
                    <img id="onepng" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#onepng").attr('src', ctx + data.xitongbaojing.image.pdfsb);
            $(".warntext").text(data.xitongbaojing.text)
            // $(".planloadingcontenttwo").css("opacity", "1")
        }

        function planLoading2() {
            if(!timer){return false;}
            num = 2;
            planloadingcenterli[0].className = "noshow";
            planloadingcenterli[1].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.tongzhigongzuo.position.x, data.tongzhigongzuo.position.y, data.tongzhigongzuo.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    buzhou2()
                }
            });

            //layerTree.locateBuildingByID("0301", "TRANSFORMER_ROOM");

            function buzhou2() {
                if(!timer){return false;}
                var position = Cesium.Cartesian3.fromDegrees(data.tongzhigongzuo.position.x, data.tongzhigongzuo.position.y, data.tongzhigongzuo.position.z);
                var url = ctx + 'bus/aupipes/analyse/images/personModel/ksj2.gltf';
                var entity = gViewer.entities.add({
                    id: 'ksjentity',
                    position: position,
                    model: {
                        uri: url,
                        show: true,
                        scale: 0.20,
                    }
                });
                gViewer.zoomTo(entity);

                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                            <div class="planloadingcontenttext"></div>
                            <div class="planloadingtitle"></div>  
                            <div class="leftphone">
                            <img id="leftphonepng" src="" style="width: 100%;height: 100%;"></div>
                            <div class="rightphone">
                            <img id="rightphonepng" src="" style="width: 100%;height: 100%;"></div>
                            <div class="warntext"></div>
                    </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $(".planloadingcontenttext").text(data.tongzhigongzuo.label.text);
                $(".warntext").text(data.tongzhigongzuo.label.texttwo);
                $("#leftphonepng").attr('src', ctx + data.tongzhigongzuo.image.leftphone);
                $("#rightphonepng").attr('src', ctx + data.tongzhigongzuo.image.rightphone);
                $(".planloadingtitle").text(data.tongzhigongzuo.title);
                setTimeout(function () {
                    $(".leftphone").slideDown()
                }, 500)
                setTimeout(function () {
                    $(".leftphone").css("display", "none");
                    $(".rightphone").slideDown()
                }, 2000)

            }
        }

        function planLoading3() {
            if(!timer){return false;}
            num = 3;
            planloadingcenterli[1].className = "noshow";
            planloadingcenterli[2].className = "noshow showYagl";
            pdf1Box = gViewer.entities.getById('ksjentity');
            gViewer.entities.remove(pdf1Box);
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.gongzuorenyuankancha.position.x, data.gongzuorenyuankancha.position.y, data.gongzuorenyuankancha.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    buzhou3()
                }
            });

            function buzhou3() {
                if(!timer){return false;}
                var position = Cesium.Cartesian3.fromDegrees(data.gongzuorenyuankancha.position.x, data.gongzuorenyuankancha.position.y, data.gongzuorenyuankancha.position.z);
                var url = ctx + 'bus/aupipes/analyse/images/personModel/ksj2.gltf';
                var entity = gViewer.entities.add({
                    id: 'ksjentity',
                    position: position,
                    model: {
                        uri: url,
                        show: true,
                        scale: 0.20,
                    }
                });
                gViewer.zoomTo(entity);


                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent">
                    <div class="pdfsb">
                    <img id="threepngtwo" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
                fireDiv = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#threepngtwo").attr('src', ctx + data.gongzuorenyuankancha.image.gzrykc);
                $(".warntext").text(data.gongzuorenyuankancha.text)
            }
        }

        function planLoading4() {
            if(!timer){return false;}
            num = 4
            planloadingcenterli[2].className = "noshow";
            planloadingcenterli[3].className = "noshow showYagl";
            pdf2Box = gViewer.entities.getById('ksjentity');
            gViewer.entities.remove(pdf2Box);
            $(".planloadingcontent").remove();
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <img id="fourpngtwo" src="" style="width: 100%;height: 100%;">    
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#fourpngtwo").attr('src', ctx + data.qiyongbeigongdianyuan.image.background);
            $(".warntext").text(data.qiyongbeigongdianyuan.text)
        }

        function planLoading5() {
            if(!timer){return false;}
            num = 5;
            planloadingcenterli[3].className = "noshow";
            planloadingcenterli[4].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <img id="fivepngtwo" src="" style="width: 100%;height: 100%;">    
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#fivepngtwo").attr('src', ctx + data.tongzhixiangguanyonghu.image.background);
            $(".warntext").text(data.tongzhixiangguanyonghu.text)
        }

        function planLoading6() {
            if(!timer){return false;}
            num = 6
            // $(".pdfplanloadingcenterUl").scrollTop(130)
            $(".planloadingcenterUl").animate({scrollTop: 130}, 1000);
            planloadingcenterli[4].className = "noshow";
            planloadingcenterli[5].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.linshigongdian.position.x, data.linshigongdian.position.y, data.linshigongdian.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    buzhou6()
                }
            });

            function buzhou6() {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent">
                    <div class="pdfsb">
                    <img id="sixpngtwo" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
                fireDiv = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#sixpngtwo").attr('src', ctx + data.linshigongdian.image.lsgd);
                $(".warntext").text(data.linshigongdian.text)
            }
        }

        function planLoading7() {
            if(!timer){return false;}
            num = 7;
            $(".planloadingcenterUl").animate({scrollTop: 270}, 1000);
            planloadingcenterli[5].className = "noshow";
            planloadingcenterli[6].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.zuzhiweixiu.position.x, data.zuzhiweixiu.position.y, data.zuzhiweixiu.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    buzhou7()
                }
            });

            function buzhou7() {
                var position = Cesium.Cartesian3.fromDegrees(data.zuzhiweixiu.position.x, data.zuzhiweixiu.position.y, data.zuzhiweixiu.position.z);
                var url = ctx + 'bus/aupipes/analyse/images/personModel/ksj2.gltf';
                var entity = gViewer.entities.add({
                    id: 'ksjentity',
                    position: position,
                    model: {
                        uri: url,
                        show: true,
                        scale: 0.20,
                    }
                });
                gViewer.zoomTo(entity);
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent">
                    <div class="pdfsb">
                    <img id="sevenpngtwo" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
                fireDiv = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#sevenpngtwo").attr('src', ctx + data.zuzhiweixiu.image.gzryjxwx);
                $(".warntext").text(data.zuzhiweixiu.text)
            }
        }

        function planLoading8() {
            if(!timer){return false;}
            num = 8;
            $(".planloadingcenterUl").animate({scrollTop: 415}, 1000)
            planloadingcenterli[6].className = "noshow";
            planloadingcenterli[7].className = "noshow showYagl";
            ksj7Box = gViewer.entities.getById('ksjentity');
            gViewer.entities.remove(ksj7Box);
            $(".planloadingcontent").remove();
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <div class="pdfsb">
                    <img id="eightpngtwo" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#eightpngtwo").attr('src', ctx + data.huifugongdian.image.hfzcgd);
            $(".warntext").text(data.huifugongdian.text);

            clearVideoStep();

        }

        function runplanLoading() {
            // timer = setInterval(function () {
            switch (num) {
                case 0:
                    planLoading1();
                    break;
                case 1:
                    planLoading2();
                    break;
                case 2:
                    planLoading3();
                    break;
                case 3:
                    planLoading4();
                    break;
                case 4:
                    planLoading5();
                    break;
                case 5:
                    planLoading6();
                    break;
                case 6:
                    planLoading7();
                    break;
                case 7:
                    planLoading8()
                    break;
                default:
                    clearInterval(timer)
            }

            // }, changeTime);


        }

        //视频操作按钮
        //播放
        $('.centerBtns').on('click', function () {
            changeTime = data.changetime;
            timer = setInterval(runplanLoading, changeTime);
            runplanLoading()
        })
        //暂停
        $('.stopBtns').on('click', function () {
            changeTime = data.changetime;
            clearInterval(timer)
        })
        //快进
        $('.rightBtns').on('click', function () {
            changeTime = data.kuaijinchangetime;
            clearInterval(timer);
            timer = setInterval(runplanLoading, changeTime);
        })
        //快退
        $('.leftBtns').on('click', function () {
            changeTime = data.kuaituichangetime;
            clearInterval(timer);
            timer = setInterval(runplanLoading, changeTime);
        })

    })
}


//爆管停水预案
function bgtsVideoPlay() {
    var number = 0;
    var changeTime = 8000;
    if (particle1) {
        gViewer.scene.primitives.remove(particle1);
    }
    $.getJSON(ctx + "bus/aupipes/analyse/config/bgts.json", function (data) {
        number = 0;
        changeTime = data.changetime;
        flyTo();
        function flyTo(){
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.baoguantixing.position.x, data.baoguantixing.position.y - 0.0015, data.baoguantixing.position.z + 100),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-28),
                    roll: 0.0,
                    range : 500
                },
                complete: function () {
                    runbgtsplanLoading();
                    number = 0;
                    timer = setInterval(runbgtsplanLoading, changeTime)
                }
            });
        }


        function bgtsplanLoading1() {
            if(!timer){return false;}
            number = 1;
            $(".bgtsplanloading").show();
            $(".bgtscontrolBtn").css("display", "block");
            $(".planloadingcontent").css("opacity", "1");
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <div class="fxbg">
                    <img id="onepngbgts" src="" style="width: 100%;height: 100%;"></div>      
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#onepngbgts").attr('src', ctx + data.baoguantixing.image.fxbg);
            $(".warntext").text(data.baoguantixing.text);
            // $(".planloadingcontenttwo").css("opacity", "1")
            //爆管粒子
            planloadingcenterli[0].className = "noshow showYagl";
            gViewer.clock.shouldAnimate = true;//粒子效果不显示可能是这个属性没设置
            var viewModel = {
                emissionRate: 500.0,
                startColor: Cesium.Color.fromCssColorString('#ffffff'),
                endColor: Cesium.Color.fromCssColorString('#ffa000'),
                image: ctx + 'bus/aupipes/analyse/images/img/smoke1.png',
                particleLife: 0.5,
                speed: 15.0,
                imageSe: 20,
                gravity: 0.0,
                imageSize: new Cesium.Cartesian2(2.5, 5),
                emitterNumber: 1000.0,
                emitter: new Cesium.ConeEmitter(Cesium.Math.toRadians(35.0)),
                lifetime: 0.8,
                loop: true,
                mass: 10
            };
            var modelMatrix = Cesium.Transforms.eastNorthUpToFixedFrame(Cesium.Cartesian3.fromDegrees(data.baoguantixing.position.x, data.baoguantixing.position.y,data.baoguantixing.position.z));
            particle1 = gViewer.scene.primitives.add(new Cesium.ParticleSystem({
                startColor: viewModel.startColor,
                // startColor: Cesium.Color.BLACK,
                endColor: viewModel.endColor,
                image: viewModel.image,
                particleLife: viewModel.particleLife,
                speed: viewModel.speed,
                imageSize: viewModel.imageSize,
                emissionRate: viewModel.emissionRate,
                emitter: viewModel.emitter,
                lifetime: viewModel.lifetime,
                loop: true,
                mass: 10,
                modelMatrix: modelMatrix,
                updateCallback: applyGravity,
                show: true,
                sizeInMeters: true
            }));

            var gravityScratch = new Cesium.Cartesian3();

            function applyGravity(p, dt) {
                var position = p.position;

                Cesium.Cartesian3.normalize(position, gravityScratch);
                Cesium.Cartesian3.multiplyByScalar(gravityScratch, viewModel.gravity * dt, gravityScratch);
                p.velocity = Cesium.Cartesian3.add(p.velocity, gravityScratch, p.velocity);
            }

        }

        function bgtsplanLoading2() {
            if(!timer){return false;}
            number = 2;
            planloadingcenterli[0].className = "noshow";
            planloadingcenterli[1].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.tongzhigongzuo.position.x, data.tongzhigongzuo.position.y, data.tongzhigongzuo.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    buzhou2()
                }
            });

            function buzhou2() {
                var position = Cesium.Cartesian3.fromDegrees(data.tongzhigongzuo.position.x, data.tongzhigongzuo.position.y, data.tongzhigongzuo.position.z);
                var url = ctx + 'bus/aupipes/analyse/images/personModel/ksj2.gltf'
                var entity = gViewer.entities.add({
                    id: 'ksjentity',
                    position: position,
                    model: {
                        uri: url,
                        show: true,
                        scale: 0.20,
                    }
                });
                gViewer.zoomTo(entity);

                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                            <div class="planloadingcontenttext"></div>
                            <div class="planloadingtitle"></div>  
                            <div class="leftphone">
                            <img id="leftphonepng" src="" style="width: 100%;height: 100%;"></div>
                            <div class="rightphone">
                            <img id="rightphonepng" src="" style="width: 100%;height: 100%;"></div>
                            <div class="warntext"></div>
                    </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $(".planloadingcontenttext").text(data.tongzhigongzuo.label.text);
                $(".warntext").text(data.tongzhigongzuo.label.texttwo);
                $("#leftphonepng").attr('src', ctx + data.tongzhigongzuo.image.leftphone);
                $("#rightphonepng").attr('src', ctx + data.tongzhigongzuo.image.rightphone);
                $(".planloadingtitle").text(data.tongzhigongzuo.title);
                setTimeout(function () {
                    $(".leftphone").slideDown()
                }, 500);
                setTimeout(function () {
                    $(".leftphone").css("display", "none");
                    $(".rightphone").slideDown()
                }, 2000);

            }
        }

        function bgtsplanLoading3() {
            if(!timer){return false;}
            number = 3;
            planloadingcenterli[1].className = "noshow";
            planloadingcenterli[2].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            var bgtsBox;
            bgtsBox = gViewer.entities.getById('ksjentity');
            gViewer.entities.remove(bgtsBox);
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.quedingweixiu.position.x, data.baoguantixing.position.y - 0.0015, data.baoguantixing.position.z + 100),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-28),
                    roll: 0.0
                },
                complete: function () {
                    buzhou3()
                }
            });

            function buzhou3() {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent">
                    <img id="threepngtwo" src="" style="width: 100%;height: 100%;">     
                    <div class="warntext"></div>
                        </div>`;
                fireDiv = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#threepngtwo").attr('src', ctx + data.quedingweixiu.image.qdwxfa);
                $(".warntext").text(data.quedingweixiu.text)
            }
        }

        function bgtsplanLoading4() {
            if(!timer){return false;}
            number = 4;
            planloadingcenterli[2].className = "noshow";
            planloadingcenterli[3].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
            html = `<div class="planloadingcontent">
                    <img id="fourpngtwo" src="" style="width: 100%;height: 100%;">     
                    <div class="warntext"></div>
                        </div>`;
            fireDiv = new Cesium.DivPoint(gViewer, {
                html: html,
                position: position,
                anchor: [0, 0],
                noEvent: true,
                zIndex: 100
            });
            $("#fourpngtwo").attr('src', ctx + data.tongzhitingshui.image.background);
            $(".warntext").text(data.tongzhitingshui.text)
        }

        function bgtsplanLoading5() {
            if(!timer){return false;}
            number = 5;
            planloadingcenterli[3].className = "noshow";
            planloadingcenterli[4].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.guanbifamen.position.x, data.baoguantixing.position.y - 0.0015, data.baoguantixing.position.z + 100),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    bgtsbuzhou5()
                }
            });

            function bgtsbuzhou5() {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                                <div class="fxbg">
                                <img id="fivepngbgts" src="" style="width: 100%;height: 100%;"></div>      
                                <div class="warntext"></div>
                        </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#fivepngbgts").attr('src', ctx + data.guanbifamen.image.background);
                $(".warntext").text(data.guanbifamen.text)

            }
        }

        function bgtsplanLoading6() {
            if(!timer){return false;}
            number = 6;
            $(".planloadingcenterUl").animate({scrollTop: 130}, 1000)
            planloadingcenterli[4].className = "noshow";
            planloadingcenterli[5].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.zuzhiweixiu.position.x, data.baoguantixing.position.y - 0.0015, data.baoguantixing.position.z + 100),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    bgtsbuzhou6()
                }
            });

            function bgtsbuzhou6() {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                                <div class="fxbg">
                                <img id="sixpngbgts" src="" style="width: 100%;height: 100%;"></div>      
                                <div class="warntext"></div>
                        </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#sixpngbgts").attr('src', ctx + data.zuzhiweixiu.image.zzwx);
                $(".warntext").text(data.zuzhiweixiu.text)

            }
        }

        function bgtsplanLoading7() {
            if(!timer){return false;}
            number = 7;
            $(".planloadingcenterUl").animate({scrollTop: 270}, 1000)
            planloadingcenterli[5].className = "noshow";
            planloadingcenterli[6].className = "noshow showYagl";
            $(".planloadingcontent").remove();
            gViewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(data.huifugongshui.position.x, data.huifugongshui.position.y, data.huifugongshui.position.z),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-32.650940597217918),
                    roll: 0.0
                },
                complete: function () {
                    bgtsbuzhou7()
                }
            });

            function bgtsbuzhou7() {
                position = Cesium.Cartesian3.fromDegrees(0, 0, 0);
                html = `<div class="planloadingcontent"> 
                                <div class="fxbg">
                                <img id="sevenpngbgts" src="" style="width: 100%;height: 100%;"></div>      
                                <div class="warntext"></div>
                        </div>`;
                fireDivtwo = new Cesium.DivPoint(gViewer, {
                    html: html,
                    position: position,
                    anchor: [0, 0],
                    noEvent: true,
                    zIndex: 100
                });
                $("#sevenpngbgts").attr('src', ctx + data.huifugongshui.image.hfzcgs);
                $(".warntext").text(data.huifugongshui.text);

                //最后清除关于动画的内容
                clearVideoStep();
            }
        }

        function runbgtsplanLoading() {
            // timer = setInterval(function () {
            switch (number) {
                case 0:
                    bgtsplanLoading1();
                    break;
                case 1:
                    bgtsplanLoading2();
                    break;
                case 2:
                    bgtsplanLoading3();
                    break;
                case 3:
                    bgtsplanLoading4();
                    break;
                case 4:
                    bgtsplanLoading5();
                    break;
                case 5:
                    bgtsplanLoading6();
                    break;
                case 6:
                    bgtsplanLoading7();
                    break;
                default:
                    clearInterval(timer)
            }

            // }, changeTime);


        }

        //视频操作按钮
        //播放
        $('.centerBtns').on('click', function () {
            changeTime = data.changetime;
            timer = setInterval(runbgtsplanLoading, changeTime);
            runbgtsplanLoading()
        });
        //暂停
        $('.stopBtns').on('click', function () {
            changeTime = data.changetime;
            clearInterval(timer);
        });
        //快进
        $('.rightBtns').on('click', function () {
            changeTime = data.kuaijinchangetime;
            clearInterval(timer);
            timer = setInterval(runbgtsplanLoading, changeTime);
        });
        //快退
        $('.leftBtns').on('click', function () {
            changeTime = data.kuaituichangetime;
            clearInterval(timer);
            timer = setInterval(runbgtsplanLoading, changeTime);
        });

    })
}
