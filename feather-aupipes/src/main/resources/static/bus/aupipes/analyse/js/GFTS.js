//关阀停水
var GFTS = {
    waterTubePos: {},  //存放点击给水管线的点坐标
    waterValves: null,     //存放爆管受影响的阀门。
    waterTubeLines: null,       //存放爆管受影响的管线。
    fmWaterParent: null,
    lineWaterParent: null,
    primitiveParticle: null,//爆管粒子对象
    pickLineOid: null,
    waterInitialFlowLine: null,//初始化水流流向管线
    pickedObject : null,
    type : null,
    pick: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(0).addClass("active");
        GFTS.removeLeftCli();
        GFTS.addLeftClickEvent();
    },
    analyse: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(1).addClass("active");
        if (GFTS.pickLineOid != null) {
            //勾选对应的图层
            JCBZ.selectedWater(layerCfg.jcbz.gfts);

            GFTS.removeLeftCli();
            /*GFTS.getWaterValve(GFTS.pickLineOid, GFTS.waterTubePos.height);*/
            JCBZ.lzxd.lzxdLzfxNc(GFTS.pickLineOid,"",GFTS.type);
        } else {
            layer.msg("请先进行标绘!");
        }
    },

    //添加点击注册事件
    addLeftClickEvent: function () {
        gViewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
            GFTS.clear();
            var scene = gViewer.scene;
            if(GFTS.pickedObject != null){
                if(GFTS.pickedObject.id){
                    GFTS.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                }
            }
            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                var pickedObject = scene.pick(movement.position);
                if(pickedObject){
                    if(pickedObject.id){
                        GFTS.pickedObject = pickedObject;
                        if(GFTS.pickedObject.id._properties['_FWBM']){
                            var id = GFTS.pickedObject.id._properties['_FWBM']._value;
                            GFTS.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                            if(id != undefined){
                                GFTS.type = "FW";
                                GFTS.pickLineOid = id;
                            }
                        }
                    }else{
                        var position = gViewer.scene.pickPosition(movement.position);//世界坐标
                        var lonAndLat = kqWeb3d.worldCoordinatesToLat(position.x, position.y, position.z);
                        GFTS.waterTubePos = {};
                        GFTS.waterTubePos.lon = lonAndLat[0];  //经度
                        GFTS.waterTubePos.lat = lonAndLat[1];   //维度
                        GFTS.waterTubePos.height = lonAndLat[2];
                        if (pickedObject) {

                            if (GFTS.primitiveParticle) {
                                gViewer.scene.primitives.remove(GFTS.primitiveParticle);
                                GFTS.primitiveParticle = null;
                            }
                            if (GFTS.lineWaterParent && GFTS.lineWaterParent._children.length > 0) {
                                GFTS.clearHigLightLines();
                            }
                            if (GFTS.fmWaterParent && GFTS.fmWaterParent._children.length > 0) {
                                GFTS.clearHigLightValves();
                            }
                            if (gViewer.entities.getById('sbgsg')) {
                                gViewer.entities.removeById('sbgsg');
                            }
                            if (!GFTS.fmWaterParent) {
                                GFTS.fmWaterParent = gViewer.entities.add({
                                    id: "gftsfm",//关阀停水阀门
                                    show: true
                                });
                            }
                            if (!GFTS.lineWaterParent) {
                                GFTS.lineWaterParent = gViewer.entities.add({
                                    id: "gftsgx",//关阀停水管线
                                    show: true
                                });
                            }
                            addCesium3DTilesetPickEvent(pickedObject, lonAndLat);
                        }else{
                            layer.msg("无效的管点");
                        }
                    }

                }
                /*var position = gViewer.scene.pickPosition(movement.position);//世界坐标
                var lonAndLat = kqWeb3d.worldCoordinatesToLat(position.x, position.y, position.z);
                GFTS.waterTubePos = {};
                GFTS.waterTubePos.lon = lonAndLat[0];  //经度
                GFTS.waterTubePos.lat = lonAndLat[1];   //维度
                GFTS.waterTubePos.height = lonAndLat[2];
                if (pickedObject) {

                    if (GFTS.primitiveParticle) {
                        gViewer.scene.primitives.remove(GFTS.primitiveParticle);
                        GFTS.primitiveParticle = null;
                    }
                    if (GFTS.lineWaterParent && GFTS.lineWaterParent._children.length > 0) {
                        GFTS.clearHigLightLines();
                    }
                    if (GFTS.fmWaterParent && GFTS.fmWaterParent._children.length > 0) {
                        GFTS.clearHigLightValves();
                    }
                    if (gViewer.entities.getById('sbgsg')) {
                        gViewer.entities.removeById('sbgsg');
                    }
                    if (!GFTS.fmWaterParent) {
                        GFTS.fmWaterParent = gViewer.entities.add({
                            id: "gftsfm",//关阀停水阀门
                            show: true
                        });
                    }
                    if (!GFTS.lineWaterParent) {
                        GFTS.lineWaterParent = gViewer.entities.add({
                            id: "gftsgx",//关阀停水管线
                            show: true
                        });
                    }
                    addCesium3DTilesetPickEvent(pickedObject, lonAndLat);
                }else{
                    layer.msg("无效的管点");
                }*/
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

        function addCesium3DTilesetPickEvent(pickedFeature, position) {
            if (Cesium.defined(pickedFeature) && pickedFeature instanceof Cesium.Cesium3DTileFeature) {
                // if (featureName == '铜') {
                var name = pickedFeature._content.tileset.name;
                if (name == '给水管线段' || name == '给水管线点' || name == '给水管线') {
                    var properties = pickedFeature.getPropertyNames() || [];
                    if (properties.indexOf('DbId') > -1) {
                        GFTS.type = 'JSGX';
                        GFTS.pickLineOid = pickedFeature.getProperty('DbId');    //管线oid
                    }
                    GLOBAL3D.drawBillboard(position, 'sbgsg', ctx + 'bus/aupipes/analyse/images/水爆管事故.png');
                    GFTS.waterParticle(gViewer, position);
                } else {
                    layer.msg("请选择给水管线!");
                }
            } else {
                //clickHandler(movement);
            }
        }
    },
    //移除左键点击查询事件
    removeLeftCli: function () {
        if(gViewer){
            gViewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },
    //获取有问题的管线和阀门点
    getWaterValve: function (oid, height) {
        var url = layerCfg.gftsUrl + oid;
        $.get(url, function (data) {
            if (data.code == 200) {
                GFTS.waterTubeLines = data.line;
                data.point = data.valve;
                GFTS.waterValves = data.point;
                if (data.point && data.point.length > 0) {
                    //根据建筑物id定位
                    //layerTree.locateBuildingByID(values,type);
                    var pointArr = data.point;
                    for (var x = 0; x < pointArr.length; x++) {
                        var postion = pointArr[x].geometry.coordinates;
                        var hei = pointArr[x].properties.PIPEP_H;
                        GFTS.higLightValves(postion[0], postion[1], hei);
                        kqWeb3d.flyToDegrees(gViewer.camera, postion[0], postion[1], hei, 0, -90, 1.5);
                    }
                }
                if (data.line && data.line.length > 0) {
                    for (var y = 0; y < data.line.length; y++) {
                        var posArr = [];
                        var startHei = data.line[y].properties.START_H;
                        var endHei = data.line[y].properties.END_H;
                        data.line[y].geometry.coordinates[0][2] = startHei;
                        data.line[y].geometry.coordinates[1][2] = endHei;
                        $.each(data.line[y].geometry.coordinates, function (index, value) {
                            //value[0][2]=startHei;
                            //value[1][2]=endHei;
                            posArr.push(value[0]);
                            posArr.push(value[1]);
                            posArr.push(value[2]);
                        });
                        var width = (Number(data.line[y].properties.D_S) + 100) / 2000;
                        GFTS.higLightLines(gViewer, GFTS.lineWaterParent, posArr, Cesium.Color.fromCssColorString('#228B22'), width);
                    }
                }

            } else {
                console.log('')
            }
        });
    },
    //爆管粒子效果
    waterParticle: function (gViewer, pos1) {
        gViewer.clock.shouldAnimate = true;//粒子效果不显示可能是这个属性没设置
        var viewModel = {
            emissionRate: 40.0,
            gravity: -19,
            minimumParticleLife: 1,
            maximumParticleLife: 1,
            minimumSpeed: 2,
            maximumSpeed: 4,
            startScale: 10,
            loop: true,
            endScale: 20,
            particleSize: 3,
        };

        function computeModelMatrix(entity) {
            // return entity.computeModelMatrix(time, new Cesium.Matrix4());
            var position = Cesium.Property.getValueOrUndefined(entity.position);
            var modelMatrix = Cesium.Transforms.eastNorthUpToFixedFrame(position);
            return modelMatrix;
        }

        var emitterModelMatrix = new Cesium.Matrix4();
        var translation = new Cesium.Cartesian3();
        var rotation = new Cesium.Quaternion();
        var hpr = new Cesium.HeadingPitchRoll();
        var trs = new Cesium.TranslationRotationScale();

        function computeEmitterModelMatrix() {
            //调节粒子的发射方向
            hpr = Cesium.HeadingPitchRoll.fromDegrees(0.0, 0.0, 0.0, hpr);
            //喷泉位置
            trs.translation = Cesium.Cartesian3.fromElements(0, 0, 5.4, translation);
            trs.rotation = Cesium.Quaternion.fromHeadingPitchRoll(hpr, rotation);
            return Cesium.Matrix4.fromTranslationRotationScale(trs, emitterModelMatrix);
        }

        var entity = gViewer.entities.add({
            position: Cesium.Cartesian3.fromDegrees(pos1[0], pos1[1], pos1[2])
        });
        GFTS.primitiveParticle = new Cesium.ParticleSystem({
            image: ctx + 'bus/aupipes/analyse/images/fountain2.png',
            startColor: new Cesium.Color(1, 1, 1, 0.6),
            endColor: new Cesium.Color(0.80, 0.86, 1, 0.4),
            startScale: viewModel.startScale,
            modelMatrix: computeModelMatrix(entity),
            endScale: viewModel.endScale,
            minimumParticleLife: viewModel.minimumParticleLife,
            maximumParticleLife: viewModel.maximumParticleLife,
            minimumSpeed: viewModel.minimumSpeed,
            maximumSpeed: viewModel.maximumSpeed,
            imageSize: new Cesium.Cartesian2(viewModel.particleSize, viewModel.particleSize),
            emissionRate: viewModel.emissionRate,
            lifetime: 3,
            //粒子发射器
            emitter: new Cesium.CircleEmitter(0.2),
            emitterModelMatrix: computeEmitterModelMatrix(),
            updateCallback: applyGravity,
            sizeInMeters: true,
            show: true

        })
        gViewer.scene.primitives.add(GFTS.primitiveParticle);
        var gravityScratch = new Cesium.Cartesian3();

        function applyGravity(p, dt) {
            var position = p.position;
            Cesium.Cartesian3.normalize(position, gravityScratch);
            Cesium.Cartesian3.multiplyByScalar(gravityScratch, viewModel.gravity * dt, gravityScratch);
            p.velocity = Cesium.Cartesian3.add(p.velocity, gravityScratch, p.velocity);
        }

        gViewer.camera.flyTo({
            destination: Cesium.Cartesian3.fromDegrees(pos1[0], pos1[1] - 0.00025, pos1[2] + 30),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-45),
                roll: 0
            },
            duration: 1.5
        });
        gViewer.scene.preRender.addEventListener(function (scene, time) {
            var height = kqWeb3d.getCurrentExtent(gViewer);
            if (GFTS.primitiveParticle) {
                if (height.height > 300) {
                    GFTS.primitiveParticle.lifetime = 0;
                } else {
                    GFTS.primitiveParticle.lifetime = 3;
                }
            }

        });
    },
    //高亮阀门
    higLightValves: function (longtitude, lattitude, height) {
        //var color = new Cesium.Color(77 / 255, 201 / 255, 1, 0.9);
        var dataSoure = new Cesium.CustomDataSource();
        dataSoure.name = '高亮供水阀门';
        dataSoure.entities.add({
            parent: GFTS.fmWaterParent,//爆管受影响的阀门 ：停水阀门
            position: Cesium.Cartesian3.fromDegrees(longtitude, lattitude, height + 2),
            ellipse: {
                height: height,
                semiMinorAxis: 1,
                semiMajorAxis: 1,
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
            if (val && val.name == '高亮供水阀门') {
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
    higLightLines: function (gViewer, lineWaterParent, positionArr, color, width) {
        kqWeb3d.drawVolume(gViewer, lineWaterParent, positionArr, color, width);
    },
    //清除高亮的管线
    clearHigLightLines: function () {
        if (GFTS.lineWaterParent) {
            $.each(GFTS.lineWaterParent._children, function (dex, val) {
                gViewer.entities.remove(val);
            })
            gViewer.entities.remove(GFTS.lineWaterParent);
            GFTS.lineWaterParent = null;
        }
    },
    //给水流向效果
    warterDirection: function () {
        //半透明倾斜摄影
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        if(node.checked == true){
            layerTree.setAlpha(node,0.5);
        }

        GFTS.warterInitialFlowLine = gViewer.entities.add({
            id: "ldgx",//初始化流动管线
            show: true
        });
        Cesium.loadJson(ctx + '/bus/aupipes/给水管线段.json').then(function (t) {
            var geojson = {
                "type": "FeatureCollection", "features": []
            }
            for (var x = 0; x < t.features.length; x++) {
                /* var obj = {};
                obj.type = "Feature";
                obj.geometry = { "type": "LineString", "coordinates": t.features[x].geometry.paths[0] };
                obj.properties = t.features[x].attributes;
                geojson.features.push(obj); */
                var D_S = t.features[x].attributes.D_S;
                var coordinates = t.features[x].geometry.paths[0];
                var positions = [];
                if (coordinates.length > 0) {
                    $.each(coordinates, function (key, value) {
                        positions.push(value[0]);
                        positions.push(value[1]);
                        positions.push(value[2]);
                    })
                }
                gViewer.entities.add({
                    name: '水流流向',
                    parent: GFTS.warterInitialFlowLine,
                    polyline: {
                        positions: Cesium.Cartesian3.fromDegreesArrayHeights(positions),
                        width: 3,
                        disableDepthTestDistance: 350000,
                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                        scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 2000, 0.5),
                        material: new Cesium.DynamicLineMaterialProperty({
                            // color: Cesium.Color.AQUA,    //颜色
                            duration: 2e3,    //流动快慢
                            image: ctx + 'bus/aupipes/analyse/images/directR.gif',
                            // repeat: new Cesium.Cartesian2(10, 1),    //重复的次数
                            // isAxisY: false
                        }),
                        clampToGround: false
                    }
                })
            }
            //gViewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(geojson, options)).then(function (t) {
            //gViewer.flyTo(t);
            //});
        })
    },
    //清除给水流向效果
    clearWaterDirection: function () {
        /* for (var x = 0; x < gViewer.dataSources.length; x++) {
            if (gViewer.dataSources.get(x).name == '给水流向') {
                gViewer.dataSources.remove(gViewer.dataSources.get(x));
                //gViewer.dataSources.get(x).show=false;
            }
        } */
        if (GFTS.warterInitialFlowLine) {
            var children = gViewer.entities.getById('ldgx')._children;
            for (var x = 0; x < children.length; x++) {
                gViewer.entities.remove(gViewer.entities.getById('ldgx')._children[x]);
            }
            gViewer.entities.remove(gViewer.entities.getById('ldgx'));
            GFTS.warterInitialFlowLine = null;
        }

    },
    clear: function () {
        if (GFTS.primitiveParticle) {
            gViewer.scene.primitives.remove(GFTS.primitiveParticle);
            GFTS.primitiveParticle = null;
        }
        if (GFTS.lineWaterParent && GFTS.lineWaterParent._children.length > 0) {
            GFTS.clearHigLightLines();
        }
        if (GFTS.fmWaterParent && GFTS.fmWaterParent._children.length > 0) {
            GFTS.clearHigLightValves();
        }
        if(gViewer){
            if (gViewer.entities.getById('sbgsg')) {
                gViewer.entities.removeById('sbgsg');
            }
        }
        JCBZ.lzxd.lzxdBhsgNc();
        if(GFTS.pickedObject){
            if(GFTS.pickedObject.id){
                GFTS.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
            }
            GFTS.pickedObject = null;
            GFTS.type = null;
        }
    },
    clearPic: function () {
        $(".zs-mapEdit2").find("li").removeClass("active");
        $(".zs-mapEdit2").find("li").eq(2).addClass("active");
        GFTS.clear();
        GFTS.removeLeftCli();
        if(gViewer){
            if (gViewer.entities.getById('sbgsg')) {
                gViewer.entities.removeById('sbgsg');
            }
        }
        GFTS.waterTubePos = {};
        GFTS.pickLineOid = null;
        //清除二维定位
        layerTree.removeLocatedBuilding();
        if(GFTS.pickedObject){
            if(GFTS.pickedObject.id){
                GFTS.pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
            }
            GFTS.pickedObject = null;
            GFTS.type = null;
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
                duration: -2e3,
                image: "./static/images/arrow.png",
                repeat: new Cesium.Cartesian2(10, 1),
                isAxisY: false
            }),
            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 200)
        }
    });
    gViewer.flyTo(lines);
}
