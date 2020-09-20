/**
 *
 */

(function () {

    var kqWeb3d = {
        /**
         * Cesium对象
         */
        Cesium: undefined,

        /**
         * @private
         */
        defined() {
            if (typeof this.Cesium == 'undefined' || typeof Cesium != 'object') {
                throw new Error('kqWeb3d.Cesium is not defined.');
                return false;
            }
            return true;
        },

        /**
         * 创建地图
         * @param  {Object} options
         *      options.id 地图存放容器
         *      options.url 地图配置路径
         * @return {Promise} 地图创建状态
         */
        createMap: function (options) {
            var that = this;
            var Cesium = this.Cesium;
            var promise = new Promise(function (resolve, reject) {
                if (!that.defined()) {
                    reject();
                } else {
                    options = Cesium.defaultValue(options, {});
                    Cesium.loadJson(options.url).then(function (data) {
                        // use the loaded
                        var map3d = Cesium.defaultValue(data.map3d, {});
                        var imageryProviders = [];
                        var imagerymapsConfig = Cesium.defaultValue(map3d.imagerymaps, []);
                        for (var i = 0; i < imagerymapsConfig.length; i++) {
                            if (Cesium.defined(imagerymapsConfig[i].isLoad) && !imagerymapsConfig[i].isLoad)
                                continue;
                            var p = that.createMapProvider(imagerymapsConfig[i]);
                            if (p) {
                                p.__visible = Cesium.defaultValue(imagerymapsConfig[i].visible, true);
                                p.brightness = Cesium.defaultValue(imagerymapsConfig[i].brightness, undefined);
                                imageryProviders.push(p);
                            }
                        }
                        var viewer = new Cesium.Viewer(options.id, {
                            imageryProvider: Cesium.defined(imageryProviders[0]) ? imageryProviders[0] : undefined,
                            baseLayerPicker: Cesium.defaultValue(map3d.baseLayerPicker, undefined),
                            sceneModePicker: Cesium.defaultValue(map3d.sceneModePicker, undefined),
                            geocoder: Cesium.defaultValue(map3d.geocoder, undefined),
                            homeButton: Cesium.defaultValue(map3d.homeButton, undefined),
                            navigationHelpButton: Cesium.defaultValue(map3d.geocoder, undefined),
                            infoBox: Cesium.defaultValue(map3d.infoBox, undefined),
                            animation: true, //Cesium.defaultValue(map3d.animation, undefined),
                            timeline: true, //Cesium.defaultValue(map3d.timeline, undefined),
                            fullscreenButton: Cesium.defaultValue(map3d.geocoder, undefined),
                            vrButton: Cesium.defaultValue(map3d.vrButton, undefined),
                            shadows: Cesium.defaultValue(map3d.shadows, undefined),
                            shouldAnimate: Cesium.defaultValue(map3d.shouldAnimate, undefined),
                            languageStyle: Cesium.defaultValue(map3d.lang, undefined),
                            showStatusBar: Cesium.defaultValue(map3d.showStatusBar, undefined),
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
                        if (Cesium.defined(map3d.animation) && map3d.animation == false)
                            viewer.animation.container.style.visibility = 'hidden';
                        if (Cesium.defined(map3d.timeline) && map3d.timeline == false)
                            viewer.timeline.container.style.visibility = 'hidden';
                        //viewer.animation.container.style.visibility = 'hidden';
                        //viewer.scene.globe.enableLighting = false;
                        viewer.statusBar.readyPromise.then(function () {
                            viewer.statusBar.hideCopyright();
                        });

                        if (imageryProviders.length >= 1) {
                            if (Cesium.defined(imageryProviders[0].brightness)) {
                                var layer0 = viewer.imageryLayers.get(0);
                                layer0.brightness = imageryProviders[0].brightness;
                            }
                            for (var i = 1; i < imageryProviders.length; i++) {
                                var imageLayer = viewer.imageryLayers.addImageryProvider(imageryProviders[i]);
                                if (imageryProviders[i].__visible == false)
                                    imageLayer.show = false;
                                if (Cesium.defined(imageryProviders[i].brightness)) {
                                    imageLayer.brightness = imageryProviders[i].brightness;
                                }
                                imageLayer._name = imageryProviders[i]._name;
                            }
                        }

                        //导航控件设置（指北针、缩放按钮、比例尺等）
                        if (Cesium.defined(map3d.navigation) && typeof map3d.navigation == 'object') {
                            var navigation = map3d.navigation;
                            var defaultResetView = Cesium.defaultValue(navigation.defaultResetView, {});
                            var lon = Cesium.defaultValue(defaultResetView.x, undefined),
                                lat = Cesium.defaultValue(defaultResetView.y, undefined),
                                height = Cesium.defaultValue(defaultResetView.z, undefined);
                            if (Cesium.defined(lon) && Cesium.defined(lat) && Cesium.defined(height)) {
                                navigation.defaultResetView = new Cesium.Cartographic(lon, lat, height);
                            } else {
                                delete navigation.defaultResetView;
                            }
                            viewer.extend(Cesium.viewerCesiumNavigationMixin, navigation);

                            if (Cesium.defined(navigation.enableCompass) && navigation.enableCompass == false) {
                                viewer.cesiumNavigation.navigationViewModel.showCompass = false;

                            }
                            if (Cesium.defined(navigation.enableZoomControls) && navigation.enableZoomControls == false) {
                                viewer.cesiumNavigation.navigationViewModel.controls[0].isActive = false;

                            }
                            if (Cesium.defined(navigation.enableDistanceLegend) && navigation.enableDistanceLegend == false) {
                                viewer.cesiumNavigation.distanceLegendDiv.style.display = 'none';

                            }
                            if (Cesium.defined(navigation.enableCompassOuterRing) && navigation.enableCompassOuterRing == false) { }
                        }

                        if (Cesium.defined(map3d.style)) {
                            viewer.scene.globe.enableLighting = Cesium.defaultValue(map3d.style.lighting, false);
                            viewer.scene.globe.showWaterEffect = Cesium.defaultValue(map3d.style.water, true);
                            viewer.scene.globe.depthTestAgainstTerrain = Cesium.defaultValue(map3d.style.testTerrain, false);
                            viewer.scene.skyAtmosphere.show = Cesium.defaultValue(map3d.style.atmosphere, undefined);
                            viewer.scene.fog.enabled = Cesium.defaultValue(map3d.style.fog, undefined);
                            viewer.scene.skyBox.show = Cesium.defaultValue(map3d.style.skyBox, true);
                            viewer.scene.sun.show = Cesium.defaultValue(map3d.style.sun, true);
                            viewer.scene.moon.show = Cesium.defaultValue(map3d.style.moon, true);
                            viewer.scene.highDynamicRange = Cesium.defaultValue(map3d.style.highDynamicRange, true);
                            viewer.scene.globe.showGroundAtmosphere = Cesium.defaultValue(map3d.style.groundAtmosphere, true);
                        }

                        if (Cesium.defined(map3d.center)) {
                            var lon = Cesium.defaultValue(map3d.center.x, undefined),
                                lat = Cesium.defaultValue(map3d.center.y, undefined),
                                height = Cesium.defaultValue(map3d.center.z, undefined),
                                heading = Cesium.defaultValue(map3d.center.heading, 0),
                                pitch = Cesium.defaultValue(map3d.center.pitch, -90),
                                roll = Cesium.defaultValue(map3d.center.roll, 0);
                            if (Cesium.defined(lon) && Cesium.defined(lat) && Cesium.defined(height)) {
                                setTimeout(function () {
                                    viewer.camera.flyTo({
                                        destination: Cesium.Cartesian3.fromDegrees(lon, lat, height),
                                        orientation: {
                                            heading: Cesium.Math.toRadians(heading),
                                            pitch: Cesium.Math.toRadians(pitch),
                                            roll: Cesium.Math.toRadians(roll)
                                        },
                                        duration: 1.5
                                    });
                                }, 3500);
                            }
                        }
                        if (Cesium.defined(map3d.home) && Cesium.defined(map3d.home.west) && Cesium.defined(map3d.home.south) &&
                            Cesium.defined(map3d.home.east) && Cesium.defined(map3d.home.north)) {
                            Cesium.Camera.DEFAULT_VIEW_RECTANGLE = Cesium.Rectangle.fromDegrees(map3d.home.west,
                                map3d.home.south, map3d.home.east, map3d.home.north);
                        }
                        if (Cesium.defined(map3d.time)) {
                            time = Cesium.JulianDate.fromDate(new Date(map3d.time));
                            viewer.clock.startTime = time
                            viewer.clock.endTime = time;
                            viewer.clock.currentTime = time;
                            viewer.timeline.zoomTo(time, time);
                        }

                        if (Cesium.defined(map3d.brightness) && map3d.brightness != 1) {
                            var stages = viewer.scene.postProcessStages;
                            var brightnessStage = stages.add(Cesium.PostProcessStageLibrary.createBrightnessStage());
                            brightnessStage.enabled = true;
                            brightnessStage.uniforms.brightness = Number(map3d.brightness);
                        }

                        if (map3d.autohiddenmaps) {
                            that.autoHideMapLayerByHeight(viewer, map3d.autohiddenmaps);
                        }

                        resolve(viewer);
                    }).otherwise(function (error) {
                        // an error occurred
                        reject(error);
                    });
                }
            });

            return promise;
        },

        /**
         * 添加影像图层
         * @param {Cesium.Viewer | Cesium.ImageryLayerCollection}viewer 三维视窗对象
         * @param {Object}options 图层配置
         * @return {Cesium.ImageryLayer} 影像图层对象那
         */
        addImageryLayer: function (viewer, options) {
            var Cesium = this.Cesium;
            var imgProvider = this.createMapProvider(options);
            var imageryLayer = undefined;
            if (Cesium.defined(imgProvider)) {
                if (viewer instanceof Cesium.Viewer) {
                    imageryLayer = viewer.imageryLayers.addImageryProvider(imgProvider);
                } else if (viewer instanceof Cesium.ImageryLayerCollection) {
                    imageryLayer = viewer.addImageryProvider(imgProvider);
                } else {
                    cosole.warn('The type of viewer must be Cesium.Viewer or Cesium.ImageryLayerCollection.');
                }
            }
            return imageryLayer;
        },
        /**
         * @param {Cesium.Viewer} viewer 三维视窗对象
         * @param {Object} options 3DTileset图层配制
         * @return {Promise} 3DTileset图层加载状态
         */
        add3DTilesetLayer: function (viewer, options) {
            var that = this;
            var Cesium = this.Cesium;
            var t = this.createCesium3DTileset(options);
            return new Promise(function (resolve, reject) {
                if (t) {
                    var tileset = viewer.scene.primitives.add(t);
                    tileset.readyPromise.then(function (tileset) {
                        if (Cesium.defined(options.position) && options.position instanceof Array && options.position.length >= 2) {
                            var longitude = options.position[0],
                                latitude = options.position[1],
                                height = options.position[2] || 0;
                            var position = Cesium.Cartesian3.fromDegrees(longitude, latitude, height);
                            var mat = Cesium.Transforms.eastNorthUpToFixedFrame(position);
                            var heading = Cesium.defaultValue(options.heading);
                            var rotationX = Cesium.Matrix4.fromRotationTranslation(Cesium.Matrix3.fromRotationZ(Cesium.Math.toRadians(heading)));
                            Cesium.Matrix4.multiply(mat, rotationX, mat);
                            tileset._root.transform = mat;
                        }
                        if (Cesium.defined(options.isFly) && options.isFly) {
                            var boundingSphere = tileset.boundingSphere;
                            viewer.camera.flyToBoundingSphere(boundingSphere, { duration: 1.5 });
                            //viewer.camera.flyTo({ destination: Cesium.Cartesian3.fromDegrees(longitude, latitude, height + 1000) });
                        }
                        if (Cesium.defined(options.style)) {
                            tileset.style = new Cesium.Cesium3DTileStyle(options.style);
                        }

                        resolve(tileset);
                    });
                    /*.otherwise(function(error) {
                        console.warn('3DTileset add failed.');
                        reject(error);
                    });*/
                } else {
                    reject(error);
                }
            });
        },

        /**
         * 创建地图提供者
         * @para{}
         */
        createMapProvider: function (options) {
            var Cesium = this.Cesium;
            options = Cesium.defaultValue(options, {});
            var imgProvider = undefined;
            var type = Cesium.defaultValue(options.type, '');
            type = type.toLowerCase();
            try {
                switch (type) {
                    case 'tianditu':
                        var key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.key = key;
                        options.mapStyle = Cesium.defaultValue(options.mapStyle, options.layer);
                        if (Cesium.defined(options.mapStyle) && options.mapStyle !== '') {
                            imgProvider = new Cesium.TiandituImageryProvider(options);
                        } else {
                            console.warn('TiandituImageryProvider:options.mapStyle is not defined');
                        }
                        break;
                    case 'bingmaps':
                        options.url = (Cesium.defined(options.url) && options.url !== '') ? options.url : undefined;
                        options.key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.mapStyle = Cesium.defaultValue(options.mapStyle, options.layer);
                        if (Cesium.defined(options.url) && Cesium.defined(options.mapStyle) && options.mapStyle !== '') {
                            imgProvider = new Cesium.BingMapsImageryProvider(options);
                        } else {
                            console.warn('BingMapsImageryProvider:options.url and options.mapStyle is not defined');
                        }
                        break;
                    case 'mapbox':
                        options.key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.mapId = Cesium.defaultValue(options.mapId, options.layer);
                        if (Cesium.defined(options.mapId) && options.mapId !== '') {
                            imgProvider = new Cesium.MapboxImageryProvider(options);
                        } else {
                            console.warn('MapboxImageryProvider:options.mapId is not defined');
                        }
                        break;
                    case 'openstreet':
                        var key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.key = key;
                        imgProvider = new Cesium.createOpenStreetMapImageryProvider(options);
                        break;
                    case 'googlemap':
                        options.key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.maptype = Cesium.defaultValue(options.maptype, options.layer);
                        if (Cesium.defined(options.maptype) && options.maptype !== '') {
                            imgProvider = new Cesium.GoogleMapsImageryProvider(options);
                        } else {
                            console.warn('GoogleMapsImageryProvider:options.maptype is not defined');
                        }
                        break;
                    case 'image':
                        options.url = (Cesium.defined(options.url) && options.url !== '') ? options.url : undefined;
                        if (Cesium.defined(options.url)) {
                            imgProvider = new Cesium.SingleTileImageryProvider(options);
                        } else {
                            console.warn('SingleTileImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'overlayimage':
                        options.url = (Cesium.defined(options.url) && options.url !== '') ? options.url : undefined;
                        if (Cesium.defined(options.url)) {
                            imgProvider = new Cesium.OverlayImageryProvider(options);
                        } else {
                            console.warn('OverlayImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'amaps':
                        options.key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.maptype = Cesium.defaultValue(options.maptype, options.layer);
                        if (Cesium.defined(options.maptype) && options.maptype !== '') {
                            imgProvider = new Cesium.AMapsImageryProvider(options);
                        } else {
                            console.warn('AMapsImageryProvider:options.maptype is not defined');
                        }
                        break;
                    case 'qqmaps':
                        options.key = (Cesium.defined(options.key) && options.key !== '') ? options.key : undefined;
                        options.maptype = Cesium.defaultValue(options.maptype, options.layer);
                        if (Cesium.defined(options.maptype) && options.maptype !== '') {
                            imgProvider = new Cesium.QQMapsImageryProvider(options);
                        } else {
                            console.warn('QQMapsImageryProvider:options.maptype is not defined');
                        }
                        break;
                    case 'baidumaps':
                        options.maptype = Cesium.defaultValue(options.maptype, options.layer);
                        if (Cesium.defined(options.maptype) && options.maptype !== '') {
                            imgProvider = new Cesium.BaiduMapsImageryProvider(options);
                        } else {
                            console.warn('BaiduMapsImageryProvider:options.maptype is not defined');
                        }
                        break;
                    case 'arcgismapserver':
                        options.token = (Cesium.defined(options.token) && options.token !== '') ? options.token : undefined;
                        options.layers = (Cesium.defined(options.layers) && options.layers !== '') ? options.layers : undefined;
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.ArcGisMapServerImageryProvider(options);
                        } else {
                            console.warn('ArcGisMapServerImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'kqgismapserver':
                        options.accessKey = (Cesium.defined(options.accessKey) && options.accessKey !== '') ? options.accessKey : undefined;
                        options.layers = (Cesium.defined(options.layers) && options.layers !== '') ? options.layers : undefined;
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.KQGISMapServerImageryProvider(options);
                        } else {
                            console.warn('KQGISMapServerImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'kqgis3dserver':
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.KQGIS3DServerImageryProvider(options);
                        } else {
                            console.warn('KQGIS3DServerImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'kqgis3dtilefile':
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.KQGIS3DTileFileImageryProvider(options);
                        } else {
                            console.warn('KQGIS3DTileFileImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'ogcwms':
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.WebMapServiceImageryProvider(options);
                        } else {
                            console.warn('WebMapServiceImageryProvider:options.url is not defined');
                        }
                        break;
                    case 'ogcwmts':
                        if (Cesium.defined(options.url) && options.url !== '') {
                            imgProvider = new Cesium.WebMapTileServiceImageryProvider(options);
                        } else {
                            console.warn('WebMapTileServiceImageryProvider:options.url is not defined');
                        }
                        break;
                    default:
                        break;
                }
                if (imgProvider)
                    imgProvider._name = options.name;
            } catch (e) {
                console.warn(e.message);
            }

            return imgProvider;
        },

        /**
         * 创建3DTileset对象
         *
         * @param  {Object} options 3DTileset对象配置
         * @return {Cesium.Cesium3DTileset} 3DTileset对象
         */
        createCesium3DTileset: function (options) {
            var Cesium = this.Cesium;
            var tileset = undefined;
            if (Cesium.defined(options.url) && options.url != '') {
                tileset = new Cesium.Cesium3DTileset(options);
            }
            return tileset;
        },

        /**
         * 根据高度自动隐藏
         */
        autoHideMapLayerByHeight: function (viewer, autohiddenmaps) {
            /*"autohiddenmaps": {
                "description": "按照高度自动隐藏的显示图层",
                "list": [{
                    "name": "中国地图午夜蓝板(ArcGISServer)",
                    "maxHeight": 1e32,
                    "minHeight": 5000
                }]
            }*/
            var maps = autohiddenmaps.list;
            if (maps instanceof Array && maps.length > 0) {
                var imageryLayers = viewer.imageryLayers,
                    len = imageryLayers.length;
                var mapNames = [];
                maps.forEach(function (map, key) {
                    var name = map.name;
                    mapNames.push(name);
                });
                var hiddens = [];
                for (var i = 0; i < len; i++) {
                    var imageryLayer = imageryLayers.get(i);
                    var name = imageryLayer._imageryProvider._name;
                    var idx = mapNames.indexOf(name);
                    if (idx > -1) {
                        hiddens.push({
                            maxVisibleHeight: maps[idx].maxVisibleHeight,
                            minVisibleHeight: maps[idx].minVisibleHeight,
                            imageryLayer: imageryLayer
                        });
                    }
                }
                if (hiddens.length == 0)
                    return;
                viewer.camera.moveEnd.addEventListener(function () {
                    var height = viewer.camera._positionCartographic.height;
                    hiddens.forEach(function (map, key) {
                        var maxVisibleHeight = map.maxVisibleHeight,
                            minVisibleHeight = map.minVisibleHeight;
                        if (height >= minVisibleHeight && height <= maxVisibleHeight) {
                            if (!map.imageryLayer.show)
                                map.imageryLayer.show = true;
                        } else {
                            if (map.imageryLayer.show)
                                map.imageryLayer.show = false;
                        }
                    });
                });
            }
        },

        /**
         * 飞到某点(世界坐标)
         *
         * @param  {Cesium.camera} camera 三维相机对象.
         * @param  {Cesium.Cartesian3} target 定位中心点.
         * @param  {Double} heading 相机朝向角（度）.
         * @param  {Double} pitch 相机翻转角（度）.
         * @param  {Double} duration 飞跃时间.
         */
        flyToCartesian3: function (camera, target, heading, pitch, duration) {
            var heading = Cesium.defaultValue(heading, 0),
                pitch = Cesium.defaultValue(pitch, -90),
                roll = Cesium.defaultValue(roll, 0),
                duration = Cesium.defaultValue(duration, 1.5);
            if (target instanceof Cesium.Cartesian3) {
                camera.flyTo({
                    destination: target,
                    orientation: {
                        heading: Cesium.Math.toRadians(heading),
                        pitch: Cesium.Math.toRadians(pitch),
                        roll: Cesium.Math.toRadians(roll)
                    },
                    duration: duration
                });
            }
        },
        /**
         * 飞到某点(经纬度)
         *
         * @param  {Cesium.camera} camera 三维相机对象.
         * @param  {Double} lon 定位中心点经度.
         * @param  {Double} lat 定位中心点纬度.
         * @param  {Double} height 定位中心点高度.
         * @param  {Double} heading 相机朝向角（度）.
         * @param  {Double} pitch 相机翻转角（度）.
         * @param  {Double} duration 飞跃时间.
         */
        flyToDegrees: function (camera, lon, lat, height, heading, pitch, duration) {
            var lon = Cesium.defaultValue(lon, undefined),
                lat = Cesium.defaultValue(lat, undefined),
                height = Cesium.defaultValue(height, undefined),
                heading = Cesium.defaultValue(heading, 0),
                pitch = Cesium.defaultValue(pitch, -90),
                roll = Cesium.defaultValue(roll, 0),
                duration = Cesium.defaultValue(duration, 1.5);
            if (Cesium.defined(lon) && Cesium.defined(lat) && Cesium.defined(height)) {
                camera.flyTo({
                    destination: Cesium.Cartesian3.fromDegrees(lon, lat, height),
                    orientation: {
                        heading: Cesium.Math.toRadians(heading),
                        pitch: Cesium.Math.toRadians(pitch),
                        roll: Cesium.Math.toRadians(roll)
                    },
                    duration: duration
                });
            }
        },
        /**
         * 相机飞到某个视角
         * @param {Array} view 视角信息数组
         */
        flyToView: function (view) {
            if (view instanceof Array && view.length >= 3) {
                gViewer.camera.flyTo({
                    destination: Cesium.Cartesian3.fromDegrees(view[0], view[1], view[2]), // 设置位置
                    orientation: {
                        heading: Cesium.Math.toRadians(view[3] || 0), // 方向
                        pitch: Cesium.Math.toRadians(view[4] || -90), // 倾斜角度
                        roll: 0
                    },
                    duration: 1.5,
                });
            }
        },
        /**
         * 定位到某点(世界坐标系)
         *
         * @param  {Cesium.camera} camera 三维相机对象.
         * @param  {Cesium.Cartesian3} target 定位中心点.
         * @param  {Double} heading 相机朝向角（度）.
         * @param  {Double} pitch 相机翻转角（度）.
         * @param  {Double} range 相机距离中心点的距离.
         */
        lookAtCartesian3: function (camera, target, heading, pitch, range) {
            var Cesium = this.Cesium;
            var offset = undefined;
            if (Cesium.defined(range)) {
                heading = Cesium.defaultValue(Cesium.Math.toRadians(heading), camera.heading);
                pitch = Cesium.defaultValue(Cesium.Math.toRadians(pitch), camera.pitch);
                offset = new Cesium.HeadingPitchRange(heading, pitch, range);
            }
            camera.lookAt(target, offset);
        },

        /**
         * 定位到某点(经纬度)
         *
         * @param  {Cesium.camera} camera 三维相机对象.
         * @param  {Double} lon 定位中心点经度.
         * @param  {Double} lat 定位中心点纬度.
         * @param  {Double} height 定位中心点高度.
         * @param  {Double} heading 相机朝向角（度）.
         * @param  {Double} pitch 相机翻转角（度）.
         * @param  {Double} range 相机距离中心点的距离.
         */
        lookAtDegrees: function (camera, lon, lat, height, heading, pitch, range) {
            var Cesium = this.Cesium;
            var target = Cesium.Cartesian3.fromDegrees(lon, lat, height);
            this.lookAtCartesian3(camera, target, heading, pitch, range);
        },

        /**
         * 定位到对象
         *
         * @param  {Cesium.Viewer} viewer 三维相机对象.
         * @param  {Cesium.Enity} target 定位中心点.
         * @param  {Double} heading 相机朝向角（度）.
         * @param  {Double} pitch 相机翻转角（度）.
         * @param  {Double} range 相机距离中心点的距离.
         */
        zoomTo: function (viewer, target, heading, pitch, range) {
            var Cesium = this.Cesium;
            var offset = undefined;
            if (Cesium.defined(range)) {
                heading = Cesium.defined(heading) ? Cesium.Math.toRadians(heading) : undefined;
                pitch = Cesium.defined(pitch) ? Cesium.Math.toRadians(pitch) : undefined;
                heading = Cesium.defaultValue(heading, viewer.camera.heading);
                pitch = Cesium.defaultValue(pitch, viewer.camera.pitch);
                offset = new Cesium.HeadingPitchRange(heading, pitch, range);
            }

            viewer.zoomTo(target, offset);
        },

        /**
         * 缩放
         * @param  {Cesium.Viewer|Cesium.Widget} viewer
         * @param  {Boolean} zoomIn 是否放大
         */
        zoom: function (viewer, zoomIn) {
            var Cesium = this.Cesium;
            var cartesian3Scratch = new Cesium.Cartesian3();
            var relativeAmount = 2;
            if (zoomIn) {
                relativeAmount = 1 / relativeAmount;
            }
            //viewer.analytics.logEvent('navigation', 'click', 'zoomIn');
            //this.isActive = true;
            if (Cesium.defined(viewer)) {
                var scene = viewer.scene;
                var sscc = scene.screenSpaceCameraController;
                // do not zoom if it is disabled
                if (!sscc.enableInputs || !sscc.enableZoom) {
                    return;
                }
                // TODO
                // if(scene.mode == SceneMode.COLUMBUS_VIEW && !sscc.enableTranslate) {
                //    return;
                // }
                var camera = scene.camera;
                var orientation;
                switch (scene.mode) {
                    case Cesium.SceneMode.MORPHING:
                        break;
                    case Cesium.SceneMode.SCENE2D:
                        camera.zoomIn(camera.positionCartographic.height * (1 - relativeAmount));
                        break;
                    default:
                        var focus;
                        if (Cesium.defined(viewer.trackedEntity)) {
                            focus = new Cesium.Cartesian3();
                        } else {
                            focus = this.getCameraFocus(viewer, false);
                        }

                        if (!Cesium.defined(focus)) {
                            // Camera direction is not pointing at the globe, so use the ellipsoid horizon point as
                            // the focal point.
                            var ray = new Cesium.Ray(camera.worldToCameraCoordinatesPoint(scene.globe.ellipsoid.cartographicToCartesian(camera.positionCartographic)), camera.directionWC);
                            focus = IntersectionTests.grazingAltitudeLocation(ray, scene.globe.ellipsoid);

                            orientation = {
                                heading: camera.heading,
                                pitch: camera.pitch,
                                roll: camera.roll
                            };
                        } else {
                            orientation = {
                                direction: camera.direction,
                                up: camera.up
                            };
                        }

                        var direction = Cesium.Cartesian3.subtract(camera.position, focus, cartesian3Scratch);
                        var movementVector = Cesium.Cartesian3.multiplyByScalar(direction, relativeAmount, direction);
                        var endPosition = Cesium.Cartesian3.add(focus, movementVector, focus);

                        if (Cesium.defined(viewer.trackedEntity) || scene.mode == Cesium.SceneMode.COLUMBUS_VIEW) {
                            // sometimes flyTo does not work (jumps to wrong position) so just set the position without any animation
                            // do not use flyTo when tracking an entity because during animatiuon the position of the entity may change
                            camera.position = endPosition;
                        } else {
                            camera.flyTo({
                                destination: endPosition,
                                orientation: orientation,
                                duration: 0.5,
                                convert: false
                            });
                        }
                }
            }
        },

        /**
         * gets the focus point of the camera
         * @param {Viewer|Widget} viewer The viewer
         * @param {boolean} inWorldCoordinates true to get the focus in world coordinates, otherwise get it in projection-specific map coordinates, in meters.
         * @param {Cartesian3} [result] The object in which the result will be stored.
         * @return {Cartesian3} The modified result parameter, a new instance if none was provided or undefined if there is no focus point.
         */
        getCameraFocus: function (viewer, inWorldCoordinates, result) {
            var Cesium = this.Cesium;
            var unprojectedScratch = new Cesium.Cartographic();
            var rayScratch = new Cesium.Ray();
            var scene = viewer.scene;
            var camera = scene.camera;

            if (scene.mode == Cesium.SceneMode.MORPHING) {
                return undefined;
            }

            if (!Cesium.defined(result)) {
                result = new Cesium.Cartesian3();
            }

            // TODO bug when tracking: if entity moves the current position should be used and not only the one when starting orbiting/rotating
            // TODO bug when tracking: reset should reset to default view of tracked entity

            if (Cesium.defined(viewer.trackedEntity)) {
                result = viewer.trackedEntity.position.getValue(viewer.clock.currentTime, result);
            } else {
                rayScratch.origin = camera.positionWC;
                rayScratch.direction = camera.directionWC;
                result = scene.globe.pick(rayScratch, scene, result);
            }

            if (!Cesium.defined(result)) {
                return undefined;
            }

            if (scene.mode == Cesium.SceneMode.SCENE2D || scene.mode == Cesium.SceneMode.COLUMBUS_VIEW) {
                result = camera.worldToCameraCoordinatesPoint(result, result);

                if (inWorldCoordinates) {
                    result = scene.globe.ellipsoid.cartographicToCartesian(scene.mapProjection.unproject(result, unprojectedScratch), result);
                }
            } else {
                if (!inWorldCoordinates) {
                    result = camera.worldToCameraCoordinatesPoint(result, result);
                }
            }

            return result;
        },

        startKeyHandler: function (viewer) {
            var scene = viewer.scene;
            var canvas = viewer.canvas;
            canvas.setAttribute('tabindex', '0'); // needed to put focus on the canvas
            canvas.onclick = function () {
                canvas.focus();
            };
            var ellipsoid = scene.globe.ellipsoid;

            // disable the default event handlers
            /*scene.screenSpaceCameraController.enableRotate = false;
            scene.screenSpaceCameraController.enableTranslate = false;
            scene.screenSpaceCameraController.enableZoom = false;
            scene.screenSpaceCameraController.enableTilt = false;
            scene.screenSpaceCameraController.enableLook = false;*/

            var startMousePosition;
            var mousePosition;
            var flags = {
                looking: false,
                moveForward: false,
                moveBackward: false,
                moveUp: false,
                moveDown: false,
                moveLeft: false,
                moveRight: false
            };

            var handler = new Cesium.ScreenSpaceEventHandler(canvas);

            handler.setInputAction(function (movement) {
                flags.looking = true;
                mousePosition = startMousePosition = Cesium.Cartesian3.clone(movement.position);
            }, Cesium.ScreenSpaceEventType.LEFT_DOWN);

            handler.setInputAction(function (movement) {
                mousePosition = movement.endPosition;
            }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);

            handler.setInputAction(function (position) {
                flags.looking = false;
            }, Cesium.ScreenSpaceEventType.LEFT_UP);

            function getFlagForKeyCode(keyCode) {
                switch (keyCode) {
                    case 'W'.charCodeAt(0):
                        return 'moveForward';
                    case 'S'.charCodeAt(0):
                        return 'moveBackward';
                    case 'Q'.charCodeAt(0):
                        return 'moveUp';
                    case 'E'.charCodeAt(0):
                        return 'moveDown';
                    case 'D'.charCodeAt(0):
                        return 'moveRight';
                    case 'A'.charCodeAt(0):
                        return 'moveLeft';
                    default:
                        return undefined;
                }
            }

            document.addEventListener('keydown', function (e) {
                var flagName = getFlagForKeyCode(e.keyCode);
                if (typeof flagName !== 'undefined') {
                    flags[flagName] = true;
                }
            }, false);

            document.addEventListener('keyup', function (e) {
                var flagName = getFlagForKeyCode(e.keyCode);
                if (typeof flagName !== 'undefined') {
                    flags[flagName] = false;
                }
            }, false);

            viewer.clock.onTick.addEventListener(function (clock) {
                var camera = viewer.camera;

                if (flags.looking) {
                    var width = canvas.clientWidth;
                    var height = canvas.clientHeight;

                    // Coordinate (0.0, 0.0) will be where the mouse was clicked.
                    var x = (mousePosition.x - startMousePosition.x) / width;
                    var y = -(mousePosition.y - startMousePosition.y) / height;

                    var lookFactor = 0.05;
                    camera.lookRight(x * lookFactor);
                    camera.lookUp(y * lookFactor);
                }

                // Change movement speed based on the distance of the camera to the surface of the ellipsoid.
                var cameraHeight = ellipsoid.cartesianToCartographic(camera.position).height;
                var moveRate = cameraHeight / 100.0;

                if (flags.moveForward) {
                    camera.moveForward(moveRate);
                }
                if (flags.moveBackward) {
                    camera.moveBackward(moveRate);
                }
                if (flags.moveUp) {
                    camera.moveUp(moveRate);
                }
                if (flags.moveDown) {
                    camera.moveDown(moveRate);
                }
                if (flags.moveLeft) {
                    camera.moveLeft(moveRate);
                }
                if (flags.moveRight) {
                    camera.moveRight(moveRate);
                }
            });
        },

        /**
         * 创建geojson对象
         *
         * @param  {Object} options GeoJsonExtendDataSource对象配置
         * @return {Cesium.GeoJsonExtendDataSource} GeoJsonExtendDataSource对象
         */
        createGeoJsonExtendIcons(url, options, callback) {
            var loadPromise = gViewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(url, options));
            loadPromise.then(function (dataSource) {
                //加载成功，返回 dataSource
                callback(dataSource)
            }, function (error) {
                //失败
                console.log('error');
            });
        },
        lookAt(x, y, z) {
            let center = Cesium.Cartesian3.fromDegrees(x, y, z)
            let heading = Cesium.Math.toRadians(50.0);
            let pitch = Cesium.Math.toRadians(-20.0);
            let range = 5000;
            gViewer.camera.lookAt(center, new Cesium.HeadingPitchRange(heading, pitch, range));
        },
        flyTo(x, y, z) {
            gViewer.camera.flyTo({
                destination: Cesium.Cartesian3.fromDegrees(x, y, z), // 设置位置
                // orientation:
                // {   heading : Cesium.Math.toRadians(50.0), // 方向
                //     pitch :Cesium.Math.toRadians(-20.0),// 倾斜角度
                //     roll : 0
                // },
                duration: 5, // 设置飞行持续时间，默认会根据距离来计算
                // complete: function () { // 到达位置后执行的回调函数
                //     console.log('到达目的地');
                // },
                // cancle: function () { // 如果取消飞行则会调用此函数
                //     console.log('飞行取消')
                // },
                // pitchAdjustHeight: -90, // 如果摄像机飞越高于该值，则调整俯仰俯仰的俯仰角度，并将地球保持在视口中。
                // maximumHeight:5000, // 相机最大飞行高度
                // flyOverLongitude: 100, // 如果到达目的地有2种方式，设置具体值后会强制选择方向飞过这个经度
            });
        },
        //将世界坐标转换成经纬度坐标
        worldCoordinatesToLat(x, y, z) {
            var arr = [];
            var ellipsoid = gViewer.scene.globe.ellipsoid;
            var cartesian3 = new Cesium.Cartesian3(x, y, z);
            var cartographic = ellipsoid.cartesianToCartographic(cartesian3);
            var lat = Cesium.Math.toDegrees(cartographic.latitude); //经度
            var lng = Cesium.Math.toDegrees(cartographic.longitude); //纬度
            var alt = cartographic.height; //高度
            arr.push(lng);
            arr.push(lat);
            arr.push(alt);
            return arr
        },
        /**
         * 绘制线 geometry对象
         *
         * @param  posArr 线的经纬度坐标点 例如：[110.0,42,130.0,42,135.0,30]
         * @return width 线的宽度
         */
        darwLine(posArr, width) {
            var Cesium = this.Cesium;
            var primitive = new Cesium.Primitive({
                geometryInstances: new Cesium.GeometryInstance({
                    geometry: new Cesium.PolylineGeometry({
                        positions: Cesium.Cartesian3.fromDegreesArray(posArr),
                        width: width,
                        vertexFormat: Cesium.PolylineMaterialAppearance.VERTEX_FORMAT
                    })
                }),
                appearance: new Cesium.PolylineMaterialAppearance({
                    material: Cesium.Material.fromType('Color')
                })

            });
            gViewer.scene.primitives.add(primitive);
        },
        //根据经纬度创建点
        createPoint(x, y, z, width, color, distance) {
            var entity = gViewer.entities.add({
                position: new Cesium.Cartesian3.fromDegrees(x, y, z),
                point: {
                    pixelSize: width,
                    color: color,
                    heightReference: Cesium.HeightReference.RELATIVE_TO_GROUND,
                    disableDepthTestDistance: Number.POSITIVE_INFINITY,
                    scaleByDistance: new Cesium.NearFarScalar(0, 1, 300, 1),
                    distanceDisplayCondition: distance
                }
            });
        },
        //根据czml创建点
        createCZMLPoint(url, options, callback) {
            var Cesium = this.Cesium;
            var loadPromise = gViewer.dataSources.add(Cesium.CzmlDataSource.load(url, options));
            loadPromise.then(function (ds) {
                callback(ds);
            }, function (error) {
                //失败
                console.log('error');
            });
        },
        //移除绘制的datasource
        removeDataSource(dataSource) {
            if (dataSource) {
                gViewer.dataSources.remove(dataSource, true);
                dataSource = undefined;
            }
        },
        //世界坐标系转成经纬度坐标系
        worldCoordinatesToLatAndLon(x, y, z) {
            var pos1 = Cesium.defined(x) ? x : 0;
            var pos2 = Cesium.defined(y) ? y : 0;
            var pos3 = Cesium.defined(z) ? z : 0;
            var ellipsoid = gViewer.scene.globe.ellipsoid;
            var cartesian3 = new Cesium.Cartesian3(pos1, pos2, pos3);
            var cartographic = ellipsoid.cartesianToCartographic(cartesian3);
            var lat = Cesium.Math.toDegrees(cartographic.latitude);
            var lng = Cesium.Math.toDegrees(cartographic.longitude);
            var alt = cartographic.height;
            return {
                lng: lng,
                lat: lat,
                alt: alt
            }
        },
        //绘制圆管线体
        drawVolume(viewer, parent, positionArr, color) {
            var entity = viewer.entities.add({
                parent: parent,
                polylineVolume: {
                    positions: Cesium.Cartesian3.fromDegreesArrayHeights(positionArr),
                    shape: GLOBAL3D.computeCircle(0.02),
                    material: color
                },
                show: true
            });
            gViewer.flyTo(entity);
        },
        //绘制长方体管线
        drawVolumeBox(viewer, parent, positionArr, color, width) {
            var box = viewer.entities.add({
                parent: parent,
                polylineVolume: {
                    positions: Cesium.Cartesian3.fromDegreesArrayHeights(positionArr),
                    shape: [new Cesium.Cartesian2(-width, -width), new Cesium.Cartesian2(width, -width), new Cesium.Cartesian2(width, width),
                    new Cesium.Cartesian2(-width, width)],
                    cornerType: Cesium.CornerType.BEVELED,
                    material: color,
                    //outline: true,
                    //outlineColor: Cesium.Color.BLACK
                }
            });
            gViewer.flyTo(box);
        },
        // 获取当前三维范围相机高度
        getCurrentExtent: function (viewer) {
            // 范围对象
            var extent = {};
            // 得到当前三维场景
            var scene = viewer.scene;
            // 得到当前三维场景的椭球体
            var ellipsoid = scene.globe.ellipsoid;
            var canvas = scene.canvas;
            // canvas左上角
            var car3_lt = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(0, 0), ellipsoid);
            // canvas右下角
            var car3_rb = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(canvas.width, canvas.height), ellipsoid);
            // 当canvas左上角和右下角全部在椭球体上
            if (car3_lt && car3_rb) {
                var carto_lt = ellipsoid.cartesianToCartographic(car3_lt);
                var carto_rb = ellipsoid.cartesianToCartographic(car3_rb);
                extent.xmin = Cesium.Math.toDegrees(carto_lt.longitude);
                extent.ymax = Cesium.Math.toDegrees(carto_lt.latitude);
               extent.xmax = Cesium.Math.toDegrees(carto_rb.longitude);
                extent.ymin = Cesium.Math.toDegrees(carto_rb.latitude);
            }
            // 当canvas左上角不在但右下角在椭球体上
            else if (!car3_lt && car3_rb) {
                var car3_lt2 = null;
                var yIndex = 0;
                do {
                    // 这里每次10像素递加，一是10像素相差不大，二是为了提高程序运行效率
                    yIndex <= canvas.height ? yIndex += 10 : canvas.height;
                    car3_lt2 = viewer.camera.pickEllipsoid(new Cesium.Cartesian2(0, yIndex), ellipsoid);
                } while (!car3_lt2);
                var carto_lt2 = ellipsoid.cartesianToCartographic(car3_lt2);
                var carto_rb2 = ellipsoid.cartesianToCartographic(car3_rb);
                extent.xmin = Cesium.Math.toDegrees(carto_lt2.longitude);
                extent.ymax = Cesium.Math.toDegrees(carto_lt2.latitude);
                extent.xmax = Cesium.Math.toDegrees(carto_rb2.longitude);
                extent.ymin = Cesium.Math.toDegrees(carto_rb2.latitude);
            }
            // 获取高度
            extent.height = Math.ceil(viewer.camera.positionCartographic.height);
            return extent;
        },
        //添加光晕线效果--全场景的线
        haloLines:function(){
            var viewModel = {
                show : true,
                glowOnly : false,
                contrast : 78,
                brightness : -0.2,
                delta : 0.5,
                sigma : 3.78,
                stepSize : 2.0
            };
            function updatePostProcess() {
                var bloom = gViewer.scene.postProcessStages.bloom;
                bloom.enabled = Boolean(viewModel.show);
                bloom.uniforms.glowOnly = Boolean(viewModel.glowOnly);
                bloom.uniforms.contrast = Number(viewModel.contrast);
                bloom.uniforms.brightness = Number(viewModel.brightness);
                bloom.uniforms.delta = Number(viewModel.delta);
                bloom.uniforms.sigma = Number(viewModel.sigma);
                bloom.uniforms.stepSize = Number(viewModel.stepSize);

            }
            updatePostProcess();
        }

    };

    /*global self,module*/
    if (typeof window !== 'undefined') {
        if (window.Cesium)
            kqWeb3d.Cesium = window.Cesium;
        window.kqWeb3d = kqWeb3d;
    } else if (typeof self !== 'undefined') {
        if (self.Cesium)
            kqWeb3d.Cesium = self.Cesium;
        self.kqWeb3d = kqWeb3d;
    } else if (typeof module !== 'undefined') {

        module.exports = kqWeb3d;
    } else {
        console.log('Unable to load kqWeb3d.');
    }
})();
