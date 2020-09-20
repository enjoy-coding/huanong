/**
 * global short summary.
 * 系统全局变量及公有方法函数
 *
 * @author liuyimeng
 * date 2019/5/8 上午 00:10
 */

var g3DTilesets = []; //3DTileset图层集合
var gPoisLayers = []; //地名地址图层集合
//图层名称配置
var gLayers = {
    simpleModelLayerName: undefined, //白膜模型图层名称
    simpleModelLayer: undefined, //白膜模型图层对象
    obliqueLayerName: undefined, //倾斜摄影模型图层名称
    obliqueLayer: undefined //倾斜摄影模型图层对象
};

var GLOBAL3D = {
    /**
     * 初始化中心位置
     */
    center: undefined,

    /**
     * 页面基础地址
     */
    baseURL: (function() {
        var href = document.location.href;
        if (href.lastIndexOf('/') !== href.length - 1)
            return document.location.href.substring(0, document.location.href.lastIndexOf('/') + 1)
        return href;
        //var pathName = document.location.pathname;
        //return document.location.href.substring(0, document.location.href.lastIndexOf(pathName) + 1);
    })(),

    /**
     * 三维初始化
     * 
     * @param  {Function} callback 初始化完成回调函数
     */
    initialize: function(callback) {
        if (!kqWeb3d.Cesium) {
            kqWeb3d.Cesium = Cesium;
        }
        var that = this;
        kqWeb3d.createMap({
            id: "cesiumContainer",
            url: ctx + "bus/aupipes/analyse/config/config.json"
        }).then(function(viewer) {
            gViewer = viewer;
            setTimeout(function() {
                that.center = {
                    position: viewer.camera.position.clone(),
                    heading: Cesium.Math.toDegrees(viewer.camera.heading),
                    pitch: Cesium.Math.toDegrees(viewer.camera.pitch)
                };
            }, 3500);

            if (typeof callback == 'function') {
                callback(viewer);
            }

            that.loadLayerDataByConfig(ctx + "bus/aupipes/analyse/config/layers.json");
            //that.createLODLineByRoadCenterLine();
            // kqWeb3d.startKeyHandler(viewer);

            //YDYL3d.loadCityConfig().then(function() {
            //   YDYL3d.addCityMarkers('中蒙俄经济走廊');
            //});

            YDYL3d.initializeRoomMarkers();
            YDYL3d.autoExecuteAction();
        }, function(error) {
            console.warn(error);
        });
    },

    createLODLineByRoadCenterLine: function() {
        var timeDuration = 2,
            moveBaseDuration = 1;
        Cesium.loadJson('../data/道路中心线.json').then(function(ret) {
            var routePaths = [];
            var i = 0;
            if (ret.features) {
                var features = ret.features;
                features.map(function(item) {
                    var positions = [];
                    var coordinates = item.geometry.coordinates;
                    if (item.geometry.type == 'MultiLineString') {
                        coordinates.map(function(coordinates2) {
                            coordinates2.map(function(item2) {
                                positions.push(Cesium.Cartesian3.fromDegrees(item2[0], item2[1], 3));
                            });
                            routePaths.push({
                                positions: positions,
                                startTime: timeDuration * Math.random(),
                                duration: moveBaseDuration + 1.0 * Math.random()
                            });
                        });

                    } else if (item.geometry.type == 'LineString') {
                        coordinates.map(function(item2) {
                            positions.push(Cesium.Cartesian3.fromDegrees(item2[0], item2[1], 3));
                        });
                        routePaths.push({
                            positions: positions,
                            startTime: timeDuration * Math.random(),
                            duration: moveBaseDuration + 1.0 * Math.random()
                        });
                    }
                });
            }
            var time = 0;
            var disposeListener = gViewer.scene.preUpdate.addEventListener(function() {
                time += 0.01;
                if (time >= timeDuration) {
                    time = 0.0;
                }
            });
            //routePaths.splice(0, 1800);
            window.flightLinesPrimitive = Cesium.Kq3dCreateODLinesPrimitive(routePaths, 5, function(instanceIndex, frameState) {
                var st = routePaths[instanceIndex].startTime;
                var dr = routePaths[instanceIndex].duration;
                var diff = time > st ? time - st : time + timeDuration - st;
                var timeRatio = Math.min(diff / dr, 1.0);

                // Cesium.Cartesian4.fromElements(timeRatio, 0.3, 1.0, 1.0, result)

                return timeRatio;
            });
            gViewer.scene.primitives.add(window.flightLinesPrimitive);
        });
    },

    /**
     * 重置视角
     */
    resetView: function() {
        if (Cesium.defined(this.center) && gViewer) {
            var center = this.center;
            kqWeb3d.flyToCartesian3(gViewer.camera, center.position, center.heading, center.pitch);
        }
    },

    /**
     * 通过配置加载图层数据
     * 
     * @param  {String} url
     */
    loadLayerDataByConfig: function(url) {
        var that = this;
        Cesium.loadJson(url).then(function(ret) {
            if (Cesium.defined(ret['3dtiels'])) {
                that.parse3DTilesData(ret['3dtiels']);
            }
            if (Cesium.defined(Cesium.LayerLabelCollection) && Cesium.defined(ret['pois'])) {
                that.parsePoisData(ret['pois']);
            }
        }).otherwise(function(error) {
            console.warn(error);
        });;
    },
    /**
     * 解析3dTiles数据
     */
    parse3DTilesData: function(config) {
        var namesConfig = config.names;
        var config3dtiels = config.list;
        for (var i = 0; i < config3dtiels.length; i++) {
            var ps = kqWeb3d.add3DTilesetLayer(gViewer, config3dtiels[i]);
            ps.then(function(tileset) {
                //alert(tileset.name);
                g3DTilesets.push(tileset);
            });
        }
        for (var j = 0; j < namesConfig.length; j++) {
            var nameConfig = namesConfig[j];
            if (Cesium.defined(nameConfig.index) && Cesium.defined(nameConfig.name)) {
                if (nameConfig.name == 'SimpleModelLayer') {
                    var idx = nameConfig.index;
                    gLayers.simpleModelLayerName = config3dtiels[idx].name;
                } else if (nameConfig.name == 'ObliqueLayer') {
                    var idx = nameConfig.index;
                    gLayers.obliqueLayerName = config3dtiels[idx].name;
                }
            }
        }
    },

    /**
     * 解析地名地址数据
     * @param {Object} config 
     */
    parsePoisData: function(config) {
        var configPois = config.list;
        for (var i = 0; i < configPois.length; i++) {
            var config = configPois[i];
            config = Cesium.defaultValue(config, {});
            var options = {};
            options.express = config.express;
            options.where = config.where;
            options.enableAutoAvoid = config.enableAutoAvoid;
            options.show = config.show;
            options.useOneHeight = config.useOneHeight;
            options.height = config.height;
            options.style = config;
            var style = options.style;
            style.fillColor = (function() {
                if (config.fillColor && typeof config.fillColor == 'string')
                    return Cesium.Color.fromCssColorString(config.fillColor)
                return undefined;
            })();
            style.outlineColor = (function() {
                if (config.outlineColor && typeof config.outlineColor == 'string')
                    return Cesium.Color.fromCssColorString(config.outlineColor)
                return undefined;
            })();
            style.backgroundColor = (function() {
                if (config.backgroundColor && typeof config.backgroundColor == 'string')
                    return Cesium.Color.fromCssColorString(config.outlineColor)
                return undefined;
            })();

            style.backgroundPadding = (function() {
                if (style.backgroundPadding && style.backgroundPadding instanceof Array &&
                    style.backgroundPadding.length > 1) {
                    return new Cesium.Cartesian2(style.backgroundPadding[0],
                        style.backgroundPadding[1]);
                } else if (style.backgroundPadding instanceof Cesium.Cartesian2) {
                    return style.backgroundPadding;
                }
                return undefined;
            })();

            style.distanceDisplayCondition = (function() {
                if (style.distanceDisplayCondition && style.distanceDisplayCondition instanceof Array &&
                    style.distanceDisplayCondition.length > 1) {
                    return new Cesium.DistanceDisplayCondition(style.distanceDisplayCondition[0],
                        style.distanceDisplayCondition[1]);
                } else if (style.distanceDisplayCondition instanceof Cesium.DistanceDisplayCondition) {
                    return style.distanceDisplayCondition;
                }
                return undefined;
            })();
            style.scaleByDistance = (function() {
                if (style.scaleByDistance && style.scaleByDistance instanceof Array &&
                    style.scaleByDistance.length > 3) {
                    return new Cesium.NearFarScalar(style.scaleByDistance[0], style.scaleByDistance[1],
                        style.scaleByDistance[2], style.scaleByDistance[3]);
                } else if (style.scaleByDistance instanceof Cesium.NearFarScalar) {
                    return style.scaleByDistance;
                }
                return undefined;
            })();

            style.translucencyByDistance = (function() {
                if (style.translucencyByDistance && style.translucencyByDistance instanceof Array &&
                    style.translucencyByDistance.length > 3) {
                    return new Cesium.NearFarScalar(style.translucencyByDistance[0], style.translucencyByDistance[1],
                        style.translucencyByDistance[2], style.translucencyByDistance[3]);
                } else if (style.translucencyByDistance instanceof Cesium.NearFarScalar) {
                    return style.translucencyByDistance;
                }
                return undefined;
            })();

            style.pixelOffsetScaleByDistance = (function() {
                if (style.pixelOffsetScaleByDistance && style.pixelOffsetScaleByDistance instanceof Array &&
                    style.pixelOffsetScaleByDistance.length > 3) {
                    return new Cesium.NearFarScalar(style.pixelOffsetScaleByDistance[0], style.pixelOffsetScaleByDistance[1],
                        style.pixelOffsetScaleByDistance[2], style.pixelOffsetScaleByDistance[3]);
                } else if (style.pixelOffsetScaleByDistance instanceof Cesium.NearFarScalar) {
                    return style.pixelOffsetScaleByDistance;
                }
                return undefined;
            })();

            var l = new Cesium.LayerLabelCollection(gViewer, options);
            l.load('3d/data/地名.json');
            gPoisLayers.push(l);
        }
    },

    /**
     * 切换模型图层
     */
    /**
     * @param  {int} t 模型显示标识 0-都不显示，1-显示素模 2-显示倾斜摄影
     */
    toggleModelLayer: function(t) {
        if (!gLayers.obliqueLayer || !gLayers.simpleModelLayer) {
            for (var i = 0; i < g3DTilesets.length; i++) {
                if (g3DTilesets[i].name == gLayers.simpleModelLayerName) {
                    gLayers.simpleModelLayer = g3DTilesets[i];
                } else if (g3DTilesets[i].name == gLayers.obliqueLayerName) {
                    gLayers.obliqueLayer = g3DTilesets[i];
                }
            }
        }
        switch (t) {
            case 0:
                setShow(gLayers.simpleModelLayer, false);
                setShow(gLayers.obliqueLayer, false);
                break;
            case 1:
                setShow(gLayers.simpleModelLayer, true);
                setShow(gLayers.obliqueLayer, false);
                break;
            case 2:
                setShow(gLayers.simpleModelLayer, false);
                setShow(gLayers.obliqueLayer, true);
                break;
            default:
                break;
        }

        function setShow(tileset, v) {
            if (tileset)
                tileset.show = v;
        }
    },

    //保存当前视角信息
    saveCurrentView: function() {
        /*! @source http://purl.eligrey.com/github/FileSaver.js/blob/master/FileSaver.js */
        var saveAs = saveAs || function(e) {
            "use strict";
            if (typeof navigator !== "undefined" && /MSIE [1-9]\./.test(navigator.userAgent)) { return }
            var t = e.document,
                n = function() { return e.URL || e.webkitURL || e },
                r = t.createElementNS("http://www.w3.org/1999/xhtml", "a"),
                i = "download" in r,
                o = function(e) {
                    var t = new MouseEvent("click");
                    e.dispatchEvent(t)
                },
                a = /Version\/[\d\.]+.*Safari/.test(navigator.userAgent),
                f = e.webkitRequestFileSystem,
                u = e.requestFileSystem || f || e.mozRequestFileSystem,
                s = function(t) {
                    (e.setImmediate || e.setTimeout)(function() { throw t }, 0)
                },
                c = "application/octet-stream",
                d = 0,
                l = 500,
                w = function(t) { var r = function() { if (typeof t === "string") { n().revokeObjectURL(t) } else { t.remove() } }; if (e.chrome) { r() } else { setTimeout(r, l) } },
                p = function(e, t, n) { t = [].concat(t); var r = t.length; while (r--) { var i = e["on" + t[r]]; if (typeof i === "function") { try { i.call(e, n || e) } catch (o) { s(o) } } } },
                v = function(e) { if (/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type)) { return new Blob(["\ufeff", e], { type: e.type }) } return e },
                y = function(t, s, l) {
                    if (!l) { t = v(t) }
                    var y = this,
                        m = t.type,
                        S = false,
                        h, R, O = function() { p(y, "writestart progress write writeend".split(" ")) },
                        g = function() {
                            if (R && a && typeof FileReader !== "undefined") {
                                var r = new FileReader;
                                r.onloadend = function() {
                                    var e = r.result;
                                    R.location.href = "data:attachment/file" + e.slice(e.search(/[,;]/));
                                    y.readyState = y.DONE;
                                    O()
                                };
                                r.readAsDataURL(t);
                                y.readyState = y.INIT;
                                return
                            }
                            if (S || !h) { h = n().createObjectURL(t) }
                            if (R) { R.location.href = h } else { var i = e.open(h, "_blank"); if (i == undefined && a) { e.location.href = h } }
                            y.readyState = y.DONE;
                            O();
                            w(h)
                        },
                        b = function(e) { return function() { if (y.readyState !== y.DONE) { return e.apply(this, arguments) } } },
                        E = { create: true, exclusive: false },
                        N;
                    y.readyState = y.INIT;
                    if (!s) { s = "download" }
                    if (i) {
                        h = n().createObjectURL(t);
                        r.href = h;
                        r.download = s;
                        setTimeout(function() {
                            o(r);
                            O();
                            w(h);
                            y.readyState = y.DONE
                        });
                        return
                    }
                    if (e.chrome && m && m !== c) {
                        N = t.slice || t.webkitSlice;
                        t = N.call(t, 0, t.size, c);
                        S = true
                    }
                    if (f && s !== "download") { s += ".download" }
                    if (m === c || f) { R = e }
                    if (!u) { g(); return }
                    d += t.size;
                    u(e.TEMPORARY, d, b(function(e) {
                        e.root.getDirectory("saved", E, b(function(e) {
                            var n = function() {
                                e.getFile(s, E, b(function(e) {
                                    e.createWriter(b(function(n) {
                                        n.onwriteend = function(t) {
                                            R.location.href = e.toURL();
                                            y.readyState = y.DONE;
                                            p(y, "writeend", t);
                                            w(e)
                                        };
                                        n.onerror = function() { var e = n.error; if (e.code !== e.ABORT_ERR) { g() } };
                                        "writestart progress write abort".split(" ").forEach(function(e) { n["on" + e] = y["on" + e] });
                                        n.write(t);
                                        y.abort = function() {
                                            n.abort();
                                            y.readyState = y.DONE
                                        };
                                        y.readyState = y.WRITING
                                    }), g)
                                }), g)
                            };
                            e.getFile(s, { create: false }, b(function(e) {
                                e.remove();
                                n()
                            }), b(function(e) { if (e.code === e.NOT_FOUND_ERR) { n() } else { g() } }))
                        }), g)
                    }), g)
                },
                m = y.prototype,
                S = function(e, t, n) { return new y(e, t, n) };
            if (typeof navigator !== "undefined" && navigator.msSaveOrOpenBlob) { return function(e, t, n) { if (!n) { e = v(e) } return navigator.msSaveOrOpenBlob(e, t || "download") } }
            m.abort = function() {
                var e = this;
                e.readyState = e.DONE;
                p(e, "abort")
            };
            m.readyState = m.INIT = 0;
            m.WRITING = 1;
            m.DONE = 2;
            m.error = m.onwritestart = m.onprogress = m.onwrite = m.onabort = m.onerror = m.onwriteend = null;
            return S
        }(typeof self !== "undefined" && self || typeof window !== "undefined" && window || this.content);
        if (typeof module !== "undefined" && module.exports) { module.exports.saveAs = saveAs } else if (typeof define !== "undefined" && define !== null && define.amd != null) { define([], function() { return saveAs }) };

        var position = gViewer.camera.position
        var heading = gViewer.camera.heading;
        var pitch = gViewer.camera.pitch;
        var ccc = Cesium.Cartographic.fromCartesian(position)

        var x = Cesium.Math.toDegrees(ccc.longitude);
        var y = Cesium.Math.toDegrees(ccc.latitude);
        var z = ccc.height;
        heading = Cesium.Math.toDegrees(gViewer.camera.heading);
        pitch = Cesium.Math.toDegrees(gViewer.camera.pitch);

        var view = {
            "x": x,
            "y": y,
            "z": z,
            "heading": heading,
            "pitch": pitch,
            "roll": 0,
            "description": "初始化相机位置"
        };

        JSON.stringify(view)
        var filename = 'center_view.json';
        var geoJsonString = '"center": ' + JSON.stringify(view, null, '\t');
        var blob = new Blob([geoJsonString], { type: "text/plain;charset=utf-8" });
        saveAs(blob, filename); //保存的文件
    },
    //创建绘制圆柱体的半径
    computeCircle(radius) {
        var positions = [];
        for (var i = 0; i < 360; i++) {
            var radians = Cesium.Math.toRadians(i);
            positions.push(new Cesium.Cartesian2(radius * Math.cos(radians), radius * Math.sin(radians)));
        }
        return positions;
    },

	//添加图标
    drawBillboard: function (position, id, url) {
        viewer.entities.add({
            name: '',
            id: id,
            position: Cesium.Cartesian3.fromDegrees(position[0], position[1], position[2]),
            billboard: new Cesium.BillboardGraphics({
                image: url,
                scale: 1,
                width: 17,
                height: 20,
                scaleByDistance: new Cesium.NearFarScalar(0, 1, 100, 2),
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 200),
                heightReference: Cesium.HeightReference.NONE,
                pixelOffset: new Cesium.Cartesian3(0, 0, 1000),
                horizontalOrigin: Cesium.HorizontalOrigin.CENTER
            }),
            show: true
        })
    }
};

$('body').keyup(function(e) {
    var keyCode = e.keyCode || e.which || e.charCode;
    var ctrlKey = e.altKey || e.metaKey;
    if (ctrlKey && keyCode == 86) {
        GLOBAL3D.saveCurrentView();
    }
    //e.preventDefault();
    return false;
});




/*
  流纹纹理线
  color 颜色
  duration 持续时间 毫秒
*/
function PolylineTrailLinkMaterialProperty(color, duration) {
    this._definitionChanged = new Cesium.Event();
    this._color = undefined;
    this._colorSubscription = undefined;
    this.color = color;
    this.glowPower = 0.2;
    this.duration = duration;
    this._time = (new Date()).getTime();
}
Cesium.defineProperties(PolylineTrailLinkMaterialProperty.prototype, {
    isConstant: {
        get: function() {
            return false;
        }
    },
    definitionChanged: {
        get: function() {
            return this._definitionChanged;
        }
    },
    color: Cesium.createPropertyDescriptor('color')
});
PolylineTrailLinkMaterialProperty.prototype.getType = function(time) {
    return 'PolylineTrailLink';
};
PolylineTrailLinkMaterialProperty.prototype.getValue = function(time, result) {
    if (!Cesium.defined(result)) {
        result = {};
    }
    result.color = Cesium.Property.getValueOrClonedDefault(this._color, time, Cesium.Color.WHITE, result.color);
    result.image = Cesium.Material.PolylineTrailLinkImage;
    // result.time = (((new Date()).getTime() - this._time)- this.duration) / this.duration;
    result.time = Math.min(((new Date()).getTime() - this._time), this.duration) / this.duration;
    return result;
};
PolylineTrailLinkMaterialProperty.prototype.equals = function(other) {
    return this === other ||
        (other instanceof PolylineTrailLinkMaterialProperty &&
            Cesium.Property.equals(this._color, other._color));
};
Cesium.PolylineTrailLinkMaterialProperty = PolylineTrailLinkMaterialProperty;


Cesium.Material.PolylineTrailLinkType = 'PolylineTrailLink';
Cesium.Material.PolylineTrailLinkImage = "./sampledata/images/colors1.png";
Cesium.Material.PolylineTrailLinkSource = "czm_material czm_getMaterial(czm_materialInput materialInput)\n\
                                                  {\n\
                                                       czm_material material = czm_getDefaultMaterial(materialInput);\n\
                                                       vec2 st = materialInput.st;\n\
                                                        float glow = glowPower / abs(st.t - 0.5) - (glowPower / 0.5);\n\
                                                        material.diffuse=vec3(1.0, 1.0, 1.0);\n\
                                                       if (st.t > 0.5) discard;\n\
                                                       material.alpha = st.t*2.0;\n\
                                                       return material;\n\
                                                        \n\
                                                        vec4 fragColor;\n\
                                                        // fragColor.rgb = mix(vec3(0.0, 0.0, 1.0), color.rgb, 1.0 - glow);\n\
                                                        fragColor.rgb = max(vec3(glow - 1.0 + color.rgb), color.rgb);\n\
                                                        fragColor.a = clamp(0.0, 1.0, glow) * color.a;\n\
                                                        //fragColor = czm_gammaCorrect(fragColor);\n\
                                                        \n\
                                                        material.emission = fragColor.rgb;\n\
                                                       material.alpha = (st.s < time)?fragColor.a:0.0;\n\
                                                       return material;\n\
                                                   }";
Cesium.Material._materialCache.addMaterial(Cesium.Material.PolylineTrailLinkType, {
    fabric: {
        type: Cesium.Material.PolylineTrailLinkType,
        uniforms: {
            color: new Cesium.Color(1.0, 0.0, 0.0, 0.5),
            glowPower: 110.1,
            image: Cesium.Color.RED,
            time: 0
        },
        source: Cesium.Material.PolylineTrailLinkSource
    },
    translucent: function(material) {
        return true;
    }
});

var fadeMaterial = new Cesium.PolylineTrailLinkMaterialProperty(Cesium.Color.ORANGE, 3000);