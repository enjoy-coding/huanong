var gViewer = null;
var g3DTileset = [];

window.onload = function () {
    Main.initGlobal(function (gViewer) {
        Cesium.loadJson('./static/config/layers.json').then(function (config) {
            Main.loadModelDataByConfig(gViewer, config['3dtiels'].list);
            $('.menu ul li').on('click', function () {
                var name = $(this).attr('name');
                Main.showRightPanel(name);
            })
            //电流流向效果
            //LZXD.powerDirection();
            //水流流向效果
            GFTS.warterDirection();

            //    drawArrowLines([114.34856982363836, 30.475479852374665, 0.27473395059354133,114.34856713878722, 30.47585933293809,-0.0003852402566719961], 5);
            //     gViewer.camera.flyTo({
            //         destination: Cesium.Cartesian3.fromDegrees(114.34856982363836, 30.475479852374665, 21.082)
            //     });

        });
    });
}

var Main = {
    //初始化地球
    initGlobal: function (callback) {
        Cesium.loadJson('static/config/config.json').then(function (data) {
            var map3d = Cesium.defaultValue(data.map3d, {});
            var imageryProviders = [];
            var imagerymapsConfig = Cesium.defaultValue(map3d.imagerymaps, []);
            for (var i = 0; i < imagerymapsConfig.length; i++) {
                if (Cesium.defined(imagerymapsConfig[i].isLoad) && !imagerymapsConfig[i].isLoad)
                    continue;
                var p = kqWeb3d.createMapProvider(imagerymapsConfig[i]);
                if (p) {
                    p.__visible = Cesium.defaultValue(imagerymapsConfig[i].visible, true);
                    p.brightness = Cesium.defaultValue(imagerymapsConfig[i].brightness, undefined);
                    imageryProviders.push(p);
                }
            }
            var viewer = new Cesium.Viewer('cesiumContainer', {
                imageryProvider: Cesium.defined(imageryProviders[3]) ? imageryProviders[3] : undefined,
                baseLayerPicker: Cesium.defaultValue(map3d.baseLayerPicker, undefined),
                sceneModePicker: Cesium.defaultValue(map3d.sceneModePicker, undefined),
                geocoder: Cesium.defaultValue(map3d.geocoder, undefined),
                homeButton: Cesium.defaultValue(map3d.homeButton, undefined),
                navigationHelpButton: Cesium.defaultValue(map3d.geocoder, undefined),
                infoBox: Cesium.defaultValue(map3d.infoBox, undefined),
                animation: false, //Cesium.defaultValue(map3d.animation, undefined),
                timeline: false, //Cesium.defaultValue(map3d.timeline, undefined),
                fullscreenButton: Cesium.defaultValue(map3d.geocoder, undefined),
                vrButton: Cesium.defaultValue(map3d.vrButton, undefined),
                shadows: Cesium.defaultValue(map3d.shadows, undefined),
                shouldAnimate: Cesium.defaultValue(map3d.shouldAnimate, undefined),
                languageStyle: Cesium.defaultValue(map3d.lang, undefined),
                showStatusBar: Cesium.defaultValue(map3d.showStatusBar, undefined), //显示右下角坐标轴
                sceneMode: Cesium.defaultValue(map3d.sceneMode, undefined),
                projectionPicker: Cesium.defaultValue(map3d.projectionPicker, undefined),
                selectionIndicator: Cesium.defaultValue(map3d.selectionIndicator, undefined),
                navigationInstructionsInitiallyVisible: Cesium.defaultValue(map3d.navigationInstructionsInitiallyVisible, undefined),
                scene3DOnly: Cesium.defaultValue(map3d.scene3DOnly, undefined),
                useDefaultRenderLoop: Cesium.defaultValue(map3d.useDefaultRenderLoop, undefined),
                showRenderLoopErrors: Cesium.defaultValue(map3d.showRenderLoopErrors, undefined),
                automaticallyTrackDataSourceClocks: Cesium.defaultValue(map3d.automaticallyTrackDataSourceClocks, undefined),
                orderIndependentTranslucency: Cesium.defaultValue(map3d.orderIndependentTranslucency, undefined),
                terrainExaggeration: Cesium.defaultValue(map3d.terrainExaggeration, undefined),
                requestRenderMode: Cesium.defaultValue(map3d.requestRenderMode, undefined),
            });

            //viewer.imageryLayers.addImageryProvider(imageryProviders[1]);
            viewer.imageryLayers.addImageryProvider(new Cesium.TiandituImageryProvider({
                mapStyle: Cesium.TiandituMapsStyle.CIA_C,
            }));
            gViewer = viewer;
            gViewer.scene.highDynamicRange = false;
            gViewer.imageryLayers.get(1).show = false;
            callback(gViewer);
        });
    },

    // 根据配置加载模型数据
    loadModelDataByConfig: function (viewer, configs) {
        for (var t = 0; t < configs.length; t++) {
            var tileConfig = configs[t];
            (function (tileConfig) {
                if (tileConfig.isKq3dServer) {
                    var request = new Cesium.KQGIS3DServerRequest({
                        url: tileConfig.serverUrl
                    });
                    var ps = request.getDatasetInfoByName(tileConfig.wkName, tileConfig.dsName, tileConfig.dsType);
                    var reqPromise = new Promise(function (resolve, reject) {
                        ps.then(function (ret) {
                            var datasetId = -1;
                            var dataclassId = -1;
                            datasetId = ret[0].id;
                            if (datasetId == -1)
                                reject();
                            request.getDatasetInfo(datasetId).then(function (ret2) {
                                if (ret2.error)
                                    reject();
                                ret2.dataclasses.forEach(function (item) {
                                    if (item.name == tileConfig.dcName && item.type == tileConfig.dcType) {
                                        dataclassId = item.id;
                                        return;
                                    }
                                });
                                resolve(dataclassId);
                            })
                        });
                    });
                    reqPromise.then(function (id) {
                            var tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
                                url: tileConfig.serverUrl,
                                isKq3dServer: true,
                                dataclassId: id
                            }));
                            tileset.readyPromise.then(function (tileset) {
                                if (tileConfig.lon && tileConfig.lat) {
                                    var longitude = tileConfig.lon,
                                        latitude = tileConfig.lat,
                                        height = tileConfig.height || 0;
                                    var position = Cesium.Cartesian3.fromDegrees(longitude, latitude, height);
                                    var mat = Cesium.Transforms.eastNorthUpToFixedFrame(position);
                                    var heading = Cesium.defaultValue(tileConfig.heading || 0);
                                    var rotationX = Cesium.Matrix4.fromRotationTranslation(Cesium.Matrix3.fromRotationZ(Cesium.Math.toRadians(heading)));
                                    Cesium.Matrix4.multiply(mat, rotationX, mat);
                                    tileset._root.transform = mat;
                                } else if (tileConfig.offsetZ || (tileConfig.lon && tileConfig.lat)) {
                                    //往上移动一段距离
                                    var heightOffset = Number(tileConfig.offsetZ); //50.00;
                                    var boundingSphere = tileset.boundingSphere;
                                    var cartographic = Cesium.Cartographic.fromCartesian(boundingSphere.center);
                                    var longitude = cartographic.longitude,
                                        latitude = cartographic.latitude;
                                    if (tileConfig.lon && tileConfig.lat) {
                                        //对经纬度进行偏移
                                        longitude = Cesium.Math.toRadians(tileConfig.lon);
                                        latitude = Cesium.Math.toRadians(tileConfig.lat);
                                    }
                                    var surface = Cesium.Cartesian3.fromRadians(cartographic.longitude, cartographic.latitude, 0.0);
                                    var offset = Cesium.Cartesian3.fromRadians(longitude, latitude, heightOffset);
                                    var translation = Cesium.Cartesian3.subtract(offset, surface, new Cesium.Cartesian3());
                                    tileset.modelMatrix = Cesium.Matrix4.fromTranslation(translation);

                                    console.log(cartographic.longitude, cartographic.latitude);
                                }
                                viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                                    duration: 1.5
                                });
                            });
                        },
                        function () {
                            console.log('服务配置读取失败.');
                        });
                } else {
                    var tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
                        name: tileConfig.name,
                        url: tileConfig.url,
                        shadows: Cesium.ShadowMode.ENABLED
                    }));
                    tileset.readyPromise.then(function (tileset) {
                        if (tileConfig.color) {
                            tileset.style = new Cesium.Cesium3DTileStyle({
                                color: {
                                    conditions: [
                                        //['${name} === "mesh_26"', 'rgba(220,20,60,1)'],
                                        ['${name} !== "aa"', tileConfig.color],
                                        ["true", tileConfig.color]
                                    ]
                                }
                            });
                        }
                        viewer.camera.flyToBoundingSphere(tileset.boundingSphere, {
                            duration: 1.5
                        });
                        //flyTo([118.33018, 29.72563, 0, -90, 0, 5])
                    });
                }

            })(tileConfig);
        }
    },
    //添加图标
    drawBillboard: function (position, id, url) {
        gViewer.entities.add({
            name: '',
            id: id,
            position: Cesium.Cartesian3.fromDegrees(position[0], position[1], position[2]),
            billboard: new Cesium.BillboardGraphics({
                image: url,
                scale: 1,
                width: 5,
                height: 6,
                scaleByDistance: new Cesium.NearFarScalar(0, 1, 100, 2),
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 200),
                heightReference: Cesium.HeightReference.NONE,
                pixelOffset: new Cesium.Cartesian3(0, 0, 1000),
                horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
                verticalOrigin:Cesium.VerticalOrigin.CENTER
            }),
            show: true
        })
    },
    showRightPanel: function (name) {
        if (name) {
            switch (name) {
                case 'lzxd':
                    LZXD.removeLeftCli();
                    LZXD.addLeftClickEvent();
                    $('.buttons > div').hide();
                    $('.lzxdButtons').show();
                    $('.lzxd-analyse').on('click', function () {
                        if (Object.keys(LZXD.powerTubePos).length > 0) {
                            LZXD.getPowerValves(LZXD.powerTubePos.lon, LZXD.powerTubePos.lat, LZXD.powerTubePos.height);
                        }
                        // if (LZXD.powerLineOid) {
                        //     LZXD.newGetValves(LZXD.powerLineOid);
                        // }
                    });
                    $('.lzxd-scope').on('click', function () {
                        LZXD.powerTubeLines = [{ "coordinates": [[114.35017816696076, 30.47592350894414], [114.3496513954916, 30.47592465159839], [114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.3489455401472, 30.475828708207368], [114.34894431801269, 30.475836144174236], [114.34894647366204, 30.475840504214469], [114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.35017891485572, 30.475917733967927], [114.35017816696076, 30.47592350894414]], "type": "LineString" }, { "coordinates": [[114.3503288492057, 30.47592388003373], [114.35017816696076, 30.47592350894414]], "type": "LineString" }, { "coordinates": [[114.35032851948776, 30.47593155716296], [114.35032846066798, 30.47592967208603], [114.35032885738477, 30.475927037103717], [114.3503288492057, 30.47592388003373]], "type": "LineString" }, { "coordinates": [[114.35048919369057, 30.475922511134536], [114.3503288492057, 30.47592388003373]], "type": "LineString" }, { "coordinates": [[114.350438972597, 30.475827419169108], [114.35044450521237, 30.47583079592228], [114.350488933359, 30.47586407857232], [114.35048958440368, 30.47586761276579], [114.35048816307588, 30.47591564046392], [114.35048919369057, 30.475922511134536]], "type": "LineString" }, { "coordinates": [[114.35151476108432, 30.475925451921893], [114.3510920540437, 30.47592491272403], [114.35048919369057, 30.475922511134536]], "type": "LineString" }, { "coordinates": [[114.3511861035169, 30.47554060379761], [114.3512586470593, 30.475863802901338], [114.35137018306034, 30.47585387784764], [114.35150065171745, 30.475860426796449], [114.35150530576893, 30.475860711922207], [114.35151405924074, 30.475862564545268], [114.35151476108432, 30.475925451921893]], "type": "LineString" }, { "coordinates": [[114.35194099896508, 30.475925249580845], [114.35169529279092, 30.47592683243242], [114.35151476108432, 30.475925451921893]], "type": "LineString" }, { "coordinates": [[114.35195106801356, 30.476130424346328], [114.3519422512756, 30.476125757601908], [114.35194041457425, 30.47611601165522], [114.35194099896508, 30.475925249580845]], "type": "LineString" }, { "coordinates": [[114.35118757435964, 30.475540708080353], [114.3511861035169, 30.47554060379761]], "type": "LineString" }, { "coordinates": [[114.3511443919362, 30.4755408784684], [114.3511861035169, 30.47554060379761]], "type": "LineString" }, { "coordinates": [[114.35274808221185, 30.47592269941049], [114.3523501761307, 30.47592409970944], [114.35194099896508, 30.475925249580845]], "type": "LineString" }, { "coordinates": [[114.35195206776709, 30.47614564709414], [114.35195106801356, 30.476130424346328]], "type": "LineString" }, { "coordinates": [[114.3519553086419, 30.47614258123593], [114.35195652491136, 30.476141973596247], [114.35196016152102, 30.47613965459567], [114.35195106801356, 30.476130424346328]], "type": "LineString" }, { "coordinates": [[114.35275316461173, 30.475922784887417], [114.35274808221185, 30.47592269941049]], "type": "LineString" }, { "coordinates": [[114.35274713148094, 30.4758537130966], [114.3527472513237, 30.475915521529474], [114.35274808221185, 30.47592269941049]], "type": "LineString" }, { "coordinates": [[114.35195556269277, 30.476145184350857], [114.35195602594657, 30.47614512301405], [114.3519553086419, 30.47614258123593]], "type": "LineString" }, { "coordinates": [[114.35188205783628, 30.476146116381849], [114.35191163421273, 30.47614327638222], [114.35191053622734, 30.47615071204042], [114.35191242185094, 30.476150896378138], [114.35195206776709, 30.47614564709414]], "type": "LineString" }, { "coordinates": [[114.35195556269277, 30.476145184350857], [114.3519553086419, 30.47614258123593]], "type": "LineString" }, { "coordinates": [[114.35195106867168, 30.476165350797705], [114.35195163203446, 30.476159837897609], [114.35195275370089, 30.4761560914622], [114.35195206776709, 30.47614564709414]], "type": "LineString" }, { "coordinates": [[114.35346660252412, 30.475920690979938], [114.3532483692854, 30.475922391725228], [114.35275316461173, 30.475922784887417]], "type": "LineString" }, { "coordinates": [[114.35273374404004, 30.47585278605961], [114.35274713148094, 30.4758537130966]], "type": "LineString" }, { "coordinates": [[114.35275356208745, 30.475869825856369], [114.35275390799129, 30.47587131326399], [114.35275316461173, 30.475922784887417]], "type": "LineString" }, { "coordinates": [[114.35274711391894, 30.47584465553355], [114.35274713148094, 30.4758537130966]], "type": "LineString" }, { "coordinates": [[114.35195206776709, 30.47614564709414], [114.35195556269277, 30.476145184350857]], "type": "LineString" }, { "coordinates": [[114.3519563682826, 30.47615343877149], [114.35195556269277, 30.476145184350857]], "type": "LineString" }, { "coordinates": [[114.35357835044822, 30.475919026023634], [114.35346660252412, 30.475920690979938]], "type": "LineString" }, { "coordinates": [[114.35346709941176, 30.476088800240189], [114.35346660252412, 30.475920690979938]], "type": "LineString" }, { "coordinates": [[114.3527452850776, 30.475594847519138], [114.35274311662656, 30.475839176197139], [114.35274711391894, 30.47584465553355]], "type": "LineString" }, { "coordinates": [[114.35313394193732, 30.475452583592938], [114.35311770083196, 30.475455081076544], [114.35296801395724, 30.475464840266047], [114.35296770042406, 30.475729558741699], [114.3528775924058, 30.475732156855807], [114.35287758700449, 30.47583692721034], [114.35276046152512, 30.47583778526672], [114.35275684574772, 30.475837786025964], [114.35274968757793, 30.47583821129797], [114.35274709982852, 30.47583738843271], [114.35274711391894, 30.47584465553355]], "type": "LineString" }, { "coordinates": [[114.3535630877684, 30.47585540239539], [114.35357811461394, 30.475915147950649], [114.35357835044822, 30.475919026023634]], "type": "LineString" }, { "coordinates": [[114.35358490861265, 30.47591913452817], [114.35357835044822, 30.475919026023634]], "type": "LineString" }, { "coordinates": [[114.35357224511412, 30.47582231831318], [114.35357774285237, 30.475872212514977], [114.3535842832987, 30.47591612345616], [114.35358490861265, 30.47591913452817]], "type": "LineString" }, { "coordinates": [[114.35392519645876, 30.47592206321012], [114.35358490861265, 30.47591913452817]], "type": "LineString" }, { "coordinates": [[114.35392485733093, 30.4758807062334], [114.3539243767255, 30.475912810646738], [114.35392519645876, 30.47592206321012]], "type": "LineString" }, { "coordinates": [[114.35562886915216, 30.475955458371698], [114.35457496616734, 30.475935389256617], [114.35392519645876, 30.47592206321012]], "type": "LineString" }, { "coordinates": [[114.35563177974969, 30.47589954286256], [114.3556306947056, 30.475900718455], [114.3556261200608, 30.47590824480538], [114.35563060962405, 30.475908827900946], [114.35562834967662, 30.475948920099517], [114.35562886915216, 30.475955458371698]], "type": "LineString" }, { "coordinates": [[114.3557019822624, 30.47595566490618], [114.35562886915216, 30.475955458371698]], "type": "LineString" }, { "coordinates": [[114.35569879053792, 30.476082345122749], [114.3557019822624, 30.47595566490618]], "type": "LineString" }, { "coordinates": [[114.35616029365444, 30.475954621637244], [114.3560130372178, 30.47596014718434], [114.3557019822624, 30.47595566490618]], "type": "LineString" }, { "coordinates": [[114.3555633005419, 30.47611702763411], [114.35556224462305, 30.47608437717152], [114.3556893746676, 30.47608279475389], [114.35569879053792, 30.476082345122749]], "type": "LineString" }, { "coordinates": [[114.35569935156193, 30.476178806252105], [114.35569879053792, 30.476082345122749]], "type": "LineString" }, { "coordinates": [[114.35556314325716, 30.476117019042645], [114.3555633005419, 30.47611702763411]], "type": "LineString" }, { "coordinates": [[114.3555302663045, 30.476163012770429], [114.355538356057, 30.476162738150685], [114.35554818863908, 30.47616146655076], [114.3555640124095, 30.47616103554175], [114.3555633005419, 30.47611702763411]], "type": "LineString" }];
                        if (LZXD.powerTubeLines.length > 0) {
                            for (var x = 0; x < LZXD.powerTubeLines.length > 0; x++) {
                                var posArr = [];
                                $.each(LZXD.powerTubeLines[x].coordinates, function (index, value) {
                                    posArr.push(value[0]);
                                    posArr.push(value[1]);
                                })
                                LZXD.powerHigLightLines(gViewer, LZXD.supplyPowerPolylineParent, posArr);
                            }
                            //gViewer.entities.getById('gftsgx').show = true;
                        }
                    });
                    $('.lzxd-getUser').on('click', function () {

                    });
                    break;
                case 'gfts':
                    GFTS.removeLeftCli();
                    GFTS.addLeftClickEvent();
                    $('.buttons > div').hide();
                    $('.gftsButtons').show();
                    $('.gfts-analyse').on('click', function () {
                        if (Object.keys(GFTS.waterTubePos).length > 0) {
                            GFTS.getWaterValve(GFTS.waterTubePos.lon, GFTS.waterTubePos.lat, GFTS.waterTubePos.height);
                        }
                    });
                    $('.gfts-scope').on('click', function () {
                        GFTS.waterTubeLines = [{ "coordinates": [[114.35017816696076, 30.47592350894414], [114.3496513954916, 30.47592465159839], [114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.3489455401472, 30.475828708207368], [114.34894431801269, 30.475836144174236], [114.34894647366204, 30.475840504214469], [114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.34894487544548, 30.475922953698285]], "type": "LineString" }, { "coordinates": [[114.35017891485572, 30.475917733967927], [114.35017816696076, 30.47592350894414]], "type": "LineString" }, { "coordinates": [[114.3503288492057, 30.47592388003373], [114.35017816696076, 30.47592350894414]], "type": "LineString" }, { "coordinates": [[114.35032851948776, 30.47593155716296], [114.35032846066798, 30.47592967208603], [114.35032885738477, 30.475927037103717], [114.3503288492057, 30.47592388003373]], "type": "LineString" }, { "coordinates": [[114.35048919369057, 30.475922511134536], [114.3503288492057, 30.47592388003373]], "type": "LineString" }, { "coordinates": [[114.350438972597, 30.475827419169108], [114.35044450521237, 30.47583079592228], [114.350488933359, 30.47586407857232], [114.35048958440368, 30.47586761276579], [114.35048816307588, 30.47591564046392], [114.35048919369057, 30.475922511134536]], "type": "LineString" }, { "coordinates": [[114.35151476108432, 30.475925451921893], [114.3510920540437, 30.47592491272403], [114.35048919369057, 30.475922511134536]], "type": "LineString" }, { "coordinates": [[114.3511861035169, 30.47554060379761], [114.3512586470593, 30.475863802901338], [114.35137018306034, 30.47585387784764], [114.35150065171745, 30.475860426796449], [114.35150530576893, 30.475860711922207], [114.35151405924074, 30.475862564545268], [114.35151476108432, 30.475925451921893]], "type": "LineString" }, { "coordinates": [[114.35194099896508, 30.475925249580845], [114.35169529279092, 30.47592683243242], [114.35151476108432, 30.475925451921893]], "type": "LineString" }, { "coordinates": [[114.35195106801356, 30.476130424346328], [114.3519422512756, 30.476125757601908], [114.35194041457425, 30.47611601165522], [114.35194099896508, 30.475925249580845]], "type": "LineString" }, { "coordinates": [[114.35118757435964, 30.475540708080353], [114.3511861035169, 30.47554060379761]], "type": "LineString" }, { "coordinates": [[114.3511443919362, 30.4755408784684], [114.3511861035169, 30.47554060379761]], "type": "LineString" }, { "coordinates": [[114.35274808221185, 30.47592269941049], [114.3523501761307, 30.47592409970944], [114.35194099896508, 30.475925249580845]], "type": "LineString" }, { "coordinates": [[114.35195206776709, 30.47614564709414], [114.35195106801356, 30.476130424346328]], "type": "LineString" }, { "coordinates": [[114.3519553086419, 30.47614258123593], [114.35195652491136, 30.476141973596247], [114.35196016152102, 30.47613965459567], [114.35195106801356, 30.476130424346328]], "type": "LineString" }, { "coordinates": [[114.35275316461173, 30.475922784887417], [114.35274808221185, 30.47592269941049]], "type": "LineString" }, { "coordinates": [[114.35274713148094, 30.4758537130966], [114.3527472513237, 30.475915521529474], [114.35274808221185, 30.47592269941049]], "type": "LineString" }, { "coordinates": [[114.35195556269277, 30.476145184350857], [114.35195602594657, 30.47614512301405], [114.3519553086419, 30.47614258123593]], "type": "LineString" }, { "coordinates": [[114.35188205783628, 30.476146116381849], [114.35191163421273, 30.47614327638222], [114.35191053622734, 30.47615071204042], [114.35191242185094, 30.476150896378138], [114.35195206776709, 30.47614564709414]], "type": "LineString" }, { "coordinates": [[114.35195556269277, 30.476145184350857], [114.3519553086419, 30.47614258123593]], "type": "LineString" }, { "coordinates": [[114.35195106867168, 30.476165350797705], [114.35195163203446, 30.476159837897609], [114.35195275370089, 30.4761560914622], [114.35195206776709, 30.47614564709414]], "type": "LineString" }, { "coordinates": [[114.35346660252412, 30.475920690979938], [114.3532483692854, 30.475922391725228], [114.35275316461173, 30.475922784887417]], "type": "LineString" }, { "coordinates": [[114.35273374404004, 30.47585278605961], [114.35274713148094, 30.4758537130966]], "type": "LineString" }, { "coordinates": [[114.35275356208745, 30.475869825856369], [114.35275390799129, 30.47587131326399], [114.35275316461173, 30.475922784887417]], "type": "LineString" }, { "coordinates": [[114.35274711391894, 30.47584465553355], [114.35274713148094, 30.4758537130966]], "type": "LineString" }, { "coordinates": [[114.35195206776709, 30.47614564709414], [114.35195556269277, 30.476145184350857]], "type": "LineString" }, { "coordinates": [[114.3519563682826, 30.47615343877149], [114.35195556269277, 30.476145184350857]], "type": "LineString" }, { "coordinates": [[114.35357835044822, 30.475919026023634], [114.35346660252412, 30.475920690979938]], "type": "LineString" }, { "coordinates": [[114.35346709941176, 30.476088800240189], [114.35346660252412, 30.475920690979938]], "type": "LineString" }, { "coordinates": [[114.3527452850776, 30.475594847519138], [114.35274311662656, 30.475839176197139], [114.35274711391894, 30.47584465553355]], "type": "LineString" }, { "coordinates": [[114.35313394193732, 30.475452583592938], [114.35311770083196, 30.475455081076544], [114.35296801395724, 30.475464840266047], [114.35296770042406, 30.475729558741699], [114.3528775924058, 30.475732156855807], [114.35287758700449, 30.47583692721034], [114.35276046152512, 30.47583778526672], [114.35275684574772, 30.475837786025964], [114.35274968757793, 30.47583821129797], [114.35274709982852, 30.47583738843271], [114.35274711391894, 30.47584465553355]], "type": "LineString" }, { "coordinates": [[114.3535630877684, 30.47585540239539], [114.35357811461394, 30.475915147950649], [114.35357835044822, 30.475919026023634]], "type": "LineString" }, { "coordinates": [[114.35358490861265, 30.47591913452817], [114.35357835044822, 30.475919026023634]], "type": "LineString" }, { "coordinates": [[114.35357224511412, 30.47582231831318], [114.35357774285237, 30.475872212514977], [114.3535842832987, 30.47591612345616], [114.35358490861265, 30.47591913452817]], "type": "LineString" }, { "coordinates": [[114.35392519645876, 30.47592206321012], [114.35358490861265, 30.47591913452817]], "type": "LineString" }, { "coordinates": [[114.35392485733093, 30.4758807062334], [114.3539243767255, 30.475912810646738], [114.35392519645876, 30.47592206321012]], "type": "LineString" }, { "coordinates": [[114.35562886915216, 30.475955458371698], [114.35457496616734, 30.475935389256617], [114.35392519645876, 30.47592206321012]], "type": "LineString" }, { "coordinates": [[114.35563177974969, 30.47589954286256], [114.3556306947056, 30.475900718455], [114.3556261200608, 30.47590824480538], [114.35563060962405, 30.475908827900946], [114.35562834967662, 30.475948920099517], [114.35562886915216, 30.475955458371698]], "type": "LineString" }, { "coordinates": [[114.3557019822624, 30.47595566490618], [114.35562886915216, 30.475955458371698]], "type": "LineString" }, { "coordinates": [[114.35569879053792, 30.476082345122749], [114.3557019822624, 30.47595566490618]], "type": "LineString" }, { "coordinates": [[114.35616029365444, 30.475954621637244], [114.3560130372178, 30.47596014718434], [114.3557019822624, 30.47595566490618]], "type": "LineString" }, { "coordinates": [[114.3555633005419, 30.47611702763411], [114.35556224462305, 30.47608437717152], [114.3556893746676, 30.47608279475389], [114.35569879053792, 30.476082345122749]], "type": "LineString" }, { "coordinates": [[114.35569935156193, 30.476178806252105], [114.35569879053792, 30.476082345122749]], "type": "LineString" }, { "coordinates": [[114.35556314325716, 30.476117019042645], [114.3555633005419, 30.47611702763411]], "type": "LineString" }, { "coordinates": [[114.3555302663045, 30.476163012770429], [114.355538356057, 30.476162738150685], [114.35554818863908, 30.47616146655076], [114.3555640124095, 30.47616103554175], [114.3555633005419, 30.47611702763411]], "type": "LineString" }];
                        if (GFTS.waterTubeLines.length > 0) {
                            for (var x = 0; x < GFTS.waterTubeLines.length > 0; x++) {
                                var posArr = [];
                                $.each(GFTS.waterTubeLines[x].coordinates, function (index, value) {
                                    posArr.push(value[0]);
                                    posArr.push(value[1]);
                                })
                                GFTS.higLightLines(gViewer, GFTS.lineWaterParent, posArr);
                            }
                            // gViewer.entities.getById('gftsgx').show = true;
                        }
                    });
                    // $('.gfts-getUser').on('click', function () {

                    // });
                    break;
                case 'tc':
                    gViewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
                    if (GFTS.primitiveParticle) {
                        gViewer.scene.primitives.remove(GFTS.primitiveParticle);
                    }
                    if (GFTS.lineWaterParent && GFTS.lineWaterParent._children.length > 0) {
                        GFTS.clearHigLightLines();
                    }
                    if (GFTS.fmWaterParent && GFTS.fmWaterParent._children.length > 0) {
                        GFTS.clearHigLightValves();
                    }
                    if (LZXD.supplyPowerPointParent &&LZXD.supplyPowerPointParent._children.length > 0) {
                        LZXD.clearHigLightValves();
                    }
                    if (LZXD.supplyPowerPolylineParent &&LZXD.supplyPowerPolylineParent._children.length > 0) {
                        LZXD.clearHigLightLines();
                    }
                    if (gViewer.entities.getById('sbgsg')) {
                        gViewer.entities.removeById('sbgsg');
                    }
                    if (gViewer.entities.getById('dlsg')) {
                        gViewer.entities.removeById('dlsg');
                    }
                    if (LZXD.fireParticle) {
                        gViewer.scene.primitives.remove(LZXD.fireParticle);
                    }
                    if (LZXD.electricityEffect) {
                        LZXD.electricityEffect.destroy();
                        LZXD.electricityEffect = null;
                    }
                    break;
                case 'planLoadingbtn':
                    // for(var each in setTimeoutObj){
                    //     console.log(each)
                    //     clearTimeout(setTimeoutObj[each]);
                    // }
                    if(isClick){

                    }else{
                        isClick=true
                        $.getJSON("planLoading.json", function (result) {
                            flyTo();
                            function flyTo() {
                                gViewer.scene.camera.flyTo({
                                    destination: new Cesium.Cartesian3.fromDegrees(result.initialflyTo.position.x, result.initialflyTo.position.y, result.initialflyTo.position.z),
                                    orientation: {
                                        heading: Cesium.Math.toRadians(0),
                                        pitch: Cesium.Math.toRadians(-28.650940597217918),
                                        roll: 0.0
                                    },
                                    duration:result.initialflyTo.time
                                });

                            }
                            setTimeoutObj["timer1"]=setTimeout(function(){
                                $(".planloading").show()
                                $(".planloadingcontent").css("opacity", "1")
                                // for (var x = 0; x < planloadingcenterli.length; x++) {
                                //     planloadingcenterli[x].index = x;
                                //     planloadingcenterli[x].onclick = function () {
                                //         for (var y = 0; y < planloadingcenterli.length; y++) {
                                //             planloadingcenterli[y].className = "noshow";
                                //             // planloadingcontent[y].className = "noactive";
                                //         }
                                //         this.className = "noshow show";
                                //         // planloadingcontent[this.index].className = "noactive active";
                                //     }
                                // }
                                setTimeoutObj["timer11"] = setInterval(function () {
                                    for (var y = 0; y < planloadingcenterli.length; y++) {
                                        planloadingcenterli[y].className = "noshow";
                                        // planloadingcontent[y].className = "noactive";
                                    }
                                    num++;
                                    if (num > 4) {
                                        num = 0;
                                    }
                                    planloadingcenterli[num].className = "noshow show"
                                    // planloadingcontent[num].className = "noactive active";
                                }, 6000)

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

                                $("#onegif").attr('src', result.waterStopAlarm.image.background);
                                $(".warntext").text(result.waterStopAlarm.label.text)
                                $(".planloadingtitle").text(result.waterStopAlarm.title)


                                //第二步骤

                                setTimeoutObj["timer2"]=setTimeout(function () {
                                    $(".planloadingcontent").remove()
                                    // var scene=gViewer.scene;
                                    // var position1 = Cesium.Cartesian3.fromDegrees(115.994472, 40.12562, 200);
                                    // var modelMatrix = Cesium.Transforms.eastNorthUpToFixedFrame(position1);//笛卡尔坐标转换成4X4的矩阵
                                    // var model=scene.primitives.add(Cesium.Model.fromGltf({
                                    //     url: 'personmodel/kanshouji.gltf',
                                    //     // show: true,                     // 确定是否显示模型基元
                                    //     modelMatrix: modelMatrix,//4x4转换矩阵，用于将模型从模型坐标转换为世界坐标
                                    //     scale: 200.0,                     // 应用于此模型的统一比例。
                                    //     // minimumPixelSize: 1000,          // 模型的最小最小像素大小，与缩放无关
                                    //     // maximumScale: 20000,             // 模型的最大比例
                                    //     // allowPicking: false,            // 时true，每个glTF网格和基本元素均可使用Scene#pick。
                                    //     // debugShowBoundingVolume: false, // 仅用于调试。为DrawCommand模型中的每个绘制边界球。
                                    //     // debugWireframe: false  //仅用于调试。在线框中绘制模型。
                                    // }));
                                    // gViewer.camera.flyTo({//设置视角
                                    //     destination : Cesium.Cartesian3.fromDegrees(115.994472, 40.12562, 1000)
                                    // });


                                    // var position = Cesium.Cartesian3.fromDegrees(123.0744619, 38.0503706, 50);
                                    // var heading = Cesium.Math.toRadians(135);//度———弧度
                                    // var pitch = 0;
                                    // var roll = 0;
                                    // var hpr = new Cesium.HeadingPitchRoll(heading, pitch, roll);
                                    // var orientation = Cesium.Transforms.headingPitchRollQuaternion(position, hpr);

                                    //  var url = 'personmodel/kanshouji.gltf'
                                    //  var color=Cesium.Color.LIME
                                    // var entity = gViewer.entities.add({
                                    //     id:'entity',
                                    //     position: position,
                                    //     orientation: orientation,
                                    //     model: {
                                    //         uri: url,
                                    //         minimumPixelSize: 100,
                                    //         maximumScale: 200,
                                    //         show:true,
                                    //         silhouetteColor:Cesium.Color.WHITE,
                                    //         color: color,
                                    //         scale: 200.0,
                                    //     }
                                    // });
                                    //  gViewer.camera.flyTo({//设置视角
                                    //     destination : Cesium.Cartesian3.fromDegrees(123.0744619, 38.0503706, 500)
                                    // });

                                    var position = Cesium.Cartesian3.fromDegrees(115.994472, 40.12562, 1000);
                                    var url = '/personmodel/ksj2.gltf'
                                    var entity = gViewer.entities.add({
                                        id:'ksjentity',
                                        position: position,
                                        model: {
                                            uri: url,
                                            show:true,
                                            scale: 0.20,
                                        }
                                    });
                                    gViewer.zoomTo(entity)
                                    // gViewer.trackedEntity = entity;


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
                                    $("#leftphonepng").attr('src', result.noticeOfWaterSupplyTime.image.phone);
                                    $("#rightphonepng").attr('src', result.informStaff.image.rightphone);
                                    $(".planloadingtitle").text(result.informStaff.title)
                                    setTimeoutObj["timer6"]=setTimeout(function () {
                                        $(".leftphone").slideDown()
                                    }, 1000)
                                    setTimeoutObj["timer7"]=setTimeout(function () {
                                        $(".leftphone").css("display","none")
                                        $(".rightphone").slideDown()
                                    }, 3000)


                                }, result.informStaff.time)


                                //第三步骤
                                setTimeoutObj["timer3"]=setTimeout(function () {
                                    $(".planloadingcontent").remove()
                                    ksjBox = gViewer.entities.getById('ksjentity');
                                    gViewer.entities.remove(ksjBox)
                                    var position = Cesium.Cartesian3.fromDegrees(result.informStaff.position.x, result.informStaff.position.y, result.informStaff.position.z);
                                    var url = '/personmodel/ddh.gltf'
                                    var entity = gViewer.entities.add({
                                        id:'ddhentity',
                                        position: position,
                                        //orientation: orientation,
                                        model: {
                                            uri: url,
                                            //minimumPixelSize: 100,
                                            //maximumScale: 200,
                                            show:true,
                                            //silhouetteColor:Cesium.Color.WHITE,
                                            //color: color,
                                            scale: 0.20,
                                        },point:{pixelSize:20}
                                    });
                                    gViewer.zoomTo(entity)
                                    // gViewer.trackedEntity = entity;
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
                                    $(".warntext").text(result.inflowTime.label.texttwo)
                                    // $("#threepng").attr('src', result.informStaff.image.background);
                                    $(".planloadingcontenttext").text(result.inflowTime.label.text)
                                    $("#texttoppng").attr('src', result.inflowTime.image.texttop);
                                    $("#textbottompng").attr('src', result.inflowTime.image.textbottom);
                                    $(".shuiwujituan").text(result.inflowTime.label2.text2)

                                    setTimeoutObj["timer8"]=setTimeout(function () {
                                        $(".texttop").css("display", "none");
                                        gViewer.scene.camera.flyTo({
                                            destination: new Cesium.Cartesian3.fromDegrees(result.informStaff.flytoposition.x, result.informStaff.flytoposition.y, result.informStaff.flytoposition.z),
                                            orientation: {
                                                heading: Cesium.Math.toRadians(0),
                                                pitch: Cesium.Math.toRadians(-28.650940597217918),
                                                roll: 0.0
                                            },
                                            duration:1.5
                                        });
                                    }, 3000)
                                    setTimeoutObj["timer9"]=setTimeout(function(){
                                        $(".shuiwujituan").css("opacity", "1")
                                        $(".textbottom").css("opacity", "1")
                                    },3500)
                                }, result.inflowTime.time)



                                //第四步骤
                                setTimeoutObj["timer4"]=setTimeout(function () {
                                    $(".planloadingcontent").remove()
                                    ddhBox = gViewer.entities.getById('ddhentity')
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
                                    $(".warntext").text(result.noticeOfWaterSupplyTime.label.texttwo)
                                    $(".swjttz").text(result.noticeOfWaterSupplyTime.label.textthree)
                                    $("#fourpng").attr('src', result.informStaff.image.background);
                                    $(".planloadingcontenttext").text(result.noticeOfWaterSupplyTime.label.text)
                                    $("#phone4png").attr('src', result.noticeOfWaterSupplyTime.image.phone);
                                    $("#textcenterpng").attr('src', result.noticeOfWaterSupplyTime.image.text);
                                }, result.noticeOfWaterSupplyTime.time)


                                //第五步骤
                                setTimeoutObj["timer5"]=setTimeout(function () {
                                    $(".planloadingcontent").remove()
                                    gViewer.scene.camera.flyTo({
                                        destination: new Cesium.Cartesian3.fromDegrees(result.recoveryOfWaterSupply.position.x, result.recoveryOfWaterSupply.position.y, result.recoveryOfWaterSupply.position.z),
                                        orientation: {
                                            heading: Cesium.Math.toRadians(0),
                                            pitch: Cesium.Math.toRadians(-28.650940597217918),
                                            roll: 0.0
                                        },
                                        duration:2.5
                                    });
                                    setTimeoutObj["timer10"]=setTimeout(function(){
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
                                        $(".warntext").text(result.recoveryOfWaterSupply.label.texttwo)
                                        $("#fivepng").attr('src', result.recoveryOfWaterSupply.image.background);
                                        $(".planloadingtitle").text(result.recoveryOfWaterSupply.title)
                                    },2600)


                                }, result.recoveryOfWaterSupply.time)


                                setTimeout(function () {
                                    clearInterval(setTimeoutObj["timer11"]);
                                }, result.recoveryOfWaterSupply.time)
                            },3100)


                        })}
                    break;
                default:
                    break;
            }
        }

    }

}
