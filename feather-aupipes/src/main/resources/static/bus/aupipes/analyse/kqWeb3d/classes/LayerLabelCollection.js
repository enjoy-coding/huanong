/**
 *  short summary.
 * 图层标注避让测试
 *
 * @author liuyimeng
 * date 2019/5/14 上午 23:10
 */

(function() {
    /**
     * 图层标注集合对象
     * @param  {Cesium.Viewer} viewer
     * @param  {Object} options  Object with the following properties: 
     * @param {Property} [options.name] 图层标注集合名称.
     * @param {Property} [options.enableAutoAvoid=false] 文字是否自动避让.
     * @param {Property} [options.show=true] 是否显示. 
     * @param {Property} [options.express=''] 标注的字段名称.
     *  @param {Property}[options.where='$NAME.indexOf("医院")>-1'] 标注表达式 满足条件的才显示 js代码.
     * @param {Property} [options.useOneHeight=false] 是否使用同一个高度值. 
     * @param {Property} [options.height=0] 标注默认高度.
     * @param {Property} [options.style] 标注样式,Object with the following properties:
     * @param {Property} [options.style.font='30px sans-serif'] 标注字体.
     * @param {Property} [options.style.style=Cesium.LabelStyle.FILL] 标注样式. {@link Cesium.LabelStyle}.
     * @param {Property} [options.style.fillColor=Cesium.Color.WHITE] 标注颜色. {@link Cesium.Color}.
     * @param {Property} [options.style.outlineColor=Cesium.Color.BLACK] 文字边线颜色. {@link Cesium.Color}.
     * @param {Property} [options.style.outlineWidth=1.0] 文字边线宽度.
     * @param {Property} [options.style.showBackground=false] 是否显示背景.
     * @param {Property} [options.style.backgroundColor=new Cesium.Color(0.165, 0.165, 0.165, 0.8)] 背景颜色. {@link Cesium.Color}.
     * @param {Property} [options.style.backgroundPadding=new Cesium.Cartesian2(7, 5)] 内边框距离（像素）. {@link Cesium.Cartesian2} 
     * @param {Property} [options.style.scale=1.0] 文字缩放.
     * @param {Property} [options.style.horizontalOrigin=Cesium.HorizontalOrigin.CENTER] 水平对齐. {@link Cesium.HorizontalOrigin}.
     * @param {Property} [options.style.verticalOrigin=Cesium.VerticalOrigin.BOTTOM] 垂直对齐. {@link Cesium.VerticalOrigin}.
     * @param {Property} [options.style.eyeOffset=Cesium.Cartesian3.ZERO] 视点偏移.{@link Cesium.Cartesian3}
     * @param {Property} [options.style.pixelOffset=Cesium.Cartesian2.ZERO] 像素偏移.{@link Cartesian2}.
     * @param {Property} [options.style.translucencyByDistance] 开启半透明相机远近距离 {@link Cesium.NearFarScalar} .
     * @param {Property} [options.style.pixelOffsetScaleByDistance] 开启像素偏移相机远近距离 {@link Cesium.NearFarScalar} .
     * @param {Property} [options.style.scaleByDistance] 开启文字缩放相机远近距离 {@link Cesium.NearFarScalar}.
     * @param {Property} [options.style.heightReference=Cesium.HeightReference.NONE] 高度跟随方式.{@link Cesium.HeightReference} .
     * @param {Property} [options.style.distanceDisplayCondition] 显示距离.
     * @param {Property} [options.style.disableDepthTestDistance] 深度检测距离.
     *
     * @example
     * var c = new Cesium.LayerLabelCollection(gViewer, {
     *       express: 'NAME',
     *       where: '$NAME.indexOf("医院")>-1', //满足条件的才显示 js代码
     *       enableAutoAvoid: true,
     *       style: {
     *           font: '14px 等线体',
     *           disableDepthTestDistance: 350000,
     *           distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 350000),
     *           scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 100000, 0.3)
     *       }
     *   })
        c.load('3d/data/地名.json');
     */
    function LayerLabelCollection(viewer, options) {
        options = Cesium.defaultValue(options, {});
        this._id = parseInt(Math.random() * 100000000);
        this._name = Cesium.defaultValue(options.name, undefined);
        this._viewer = Cesium.defaultValue(viewer, undefined);
        if (!Cesium.defined(viewer)) {
            throw new Error('viewer is not defined');
        }
        this._express = Cesium.defaultValue(options.express, undefined);
        if (!Cesium.defined(options.express)) {
            throw new Error('options.express is not defined');
        }

        this._where = Cesium.defaultValue(options.where, undefined);
        this._show = Cesium.defaultValue(options.show, true);
        this._enableAutoAvoid = Cesium.defaultValue(options.enableAutoAvoid, false);
        this._style = Cesium.defaultValue(options.style, {});

        if (!Cesium.defined(this._style.verticalOrigin)) {
            this._style.verticalOrigin = Cesium.VerticalOrigin.BOTTOM;
        }
        this._datasource = new Cesium.GeoJsonDataSource(this._name);
        this._entities = new Cesium.EntityCollection(this._datasource);
        this._datasource._entityCollection = this._entities;
        this._changed = new Cesium.Event();
        this._useOneHeight = Cesium.defaultValue(options.useOneHeight, false);
        this._height = Cesium.defaultValue(options.height, 0);
        var that = this;
        this.crsNames = {
            'urn:ogc:def:crs:OGC:1.3:CRS84': that.defaultCrsFunction,
            'EPSG:4326': that.defaultCrsFunction,
            'urn:ogc:def:crs:EPSG::4326': that.defaultCrsFunction
        };

        this.geoJsonObjectTypes = {
            Feature: that.processFeature,
            FeatureCollection: that.processFeatureCollection,
            Point: that.processLabel
        };

        this.geometryTypes = {
            Point: that.processLabel
        };
        if (this._enableAutoAvoid)
            this.autoAvoid();
    }

    /**
     * 加载geojson点数据
     * @param  {String|Cesium.Resource} data geojson的数据文件
     */
    LayerLabelCollection.prototype.load = function(data) {
        //>>includeStart('debug', pragmas.debug);
        if (!Cesium.defined(data)) {
            throw new Error('data is required.');
        }
        var promise = data;
        var sourceUri = undefined;
        if (typeof data === 'string' || (data instanceof Cesium.Resource)) {
            data = Cesium.Resource.createIfNeeded(data);
            promise = data.fetchJson();
            sourceUri = Cesium.defaultValue(sourceUri, data.getUrlComponent());
        }

        var that = this;
        return Cesium.when(promise, function(geoJson) {
            return load(that, geoJson, sourceUri);
        }).otherwise(function(error) {
            console.log(error);
            return when.reject(error);
        });

        function load(that, geoJson, sourceUri) {
            var typeHandler = that.geoJsonObjectTypes[geoJson.type];
            if (!Cesium.defined(typeHandler)) {
                throw new RuntimeError('Unsupported GeoJSON object type: ' + geoJson.type);
            }
            //Check for a Coordinate Reference System.
            var crs = geoJson.crs;
            var crsFunction = crs !== null ? that.defaultCrsFunction : null;

            if (Cesium.defined(crs)) {
                if (!Cesium.defined(crs.properties)) {
                    throw new RuntimeError('crs.properties is undefined.');
                }
                var properties = crs.properties;
                if (crs.type === 'name') {
                    crsFunction = that.crsNames[properties.name];
                    if (!Cesium.defined(crsFunction)) {
                        throw new RuntimeError('Unknown crs name: ' + properties.name);
                    }
                } else if (crs.type === 'EPSG') {
                    crsFunction = that.crsNames['EPSG:' + properties.code];
                    if (!Cesium.defined(crsFunction)) {
                        throw new RuntimeError('Unknown crs EPSG code: ' + properties.code);
                    }
                } else {
                    throw new RuntimeError('Unknown crs type: ' + crs.type);
                }
            }

            return Cesium.when(crsFunction, function(crsFunction) {
                // null is a valid value for the crs, but means the entire load process becomes a no-op
                // because we can't assume anything about the coordinates.
                if (crsFunction !== null) {
                    typeHandler(that, geoJson, crsFunction);
                    that.viewer.dataSources.add(that._datasource);
                }
            });
        };
    };
    /**
     * @private
     * 默认坐标转换
     * @param  {Array} coordinates 坐标串 [x,y,z]
     */
    LayerLabelCollection.prototype.defaultCrsFunction = function(that, coordinates) {
        var height = Cesium.defaultValue(coordinates[2], undefined);
        if (that._useOneHeight) {
            height = that._height;
        }
        return Cesium.Cartesian3.fromDegrees(coordinates[0], coordinates[1], height);
    };


    // GeoJSON processing functions
    /**
     * @private
     */
    LayerLabelCollection.prototype.processFeature = function(that, feature, crsFunction) {
        if (feature.geometry === null) {
            return;
        }
        if (!Cesium.defined(feature.geometry)) {
            return;
        }
        var geometryType = feature.geometry.type;
        var geometryHandler = that.geometryTypes[geometryType];
        if (!Cesium.defined(geometryHandler)) {
            return;
        }
        geometryHandler(that, feature, feature.geometry, crsFunction);
    };
    /**
     * @private
     */
    LayerLabelCollection.prototype.processFeatureCollection = function(that, featureCollection, crsFunction) {
        var features = featureCollection.features;
        for (var i = 0, len = features.length; i < len; i++) {
            that.processFeature(that, features[i], crsFunction);
        }
    }

    /**
     * 创建标注
     * @private
     */
    LayerLabelCollection.prototype.createLabel = function(that, geoJson, crsFunction, coordinates) {
        var style = that.style;
        style.show = that.show;
        var express = that._express,
            where = that._where;
        //var id = geoJson.id;
        var properties = geoJson.properties;
        if (Cesium.defined(properties) && Cesium.defined(properties[express])) {
            var t = true;
            if (Cesium.defined(where) && typeof where == 'string' && where !== '') {
                where = where.replace(/\$/g, 'properties.');
                t = eval(where);
            }
            if (!Cesium.defined(t) || t) {
                var text = (express && Cesium.defined(properties[express])) ? properties[express] : undefined;
                style.text = text;
                var entity = new Cesium.Entity({
                    //id: parseInt('label_' + Math.random() * 10000000000000000),
                    position: crsFunction(that, coordinates),
                    label: style
                });
                that._entities.add(entity);
            }
        }
    };
    /**
     * @private
     */
    LayerLabelCollection.prototype.processLabel = function(that, geoJson, geometry, crsFunction) {
        that.createLabel(that, geoJson, crsFunction, geometry.coordinates);
    };

    /**
     * 销毁图层标注对象
     */
    LayerLabelCollection.prototype.destroy = function() {
        var that = this;
        that.show = false;
        this.viewer.dataSources.add(this._datasource);
        this.viewer.scene.postRender.removeEventListener(that.postRender);
    };

    /**
     * 实时计算标注遮挡
     */
    LayerLabelCollection.prototype.postRender = function(that) {
        var that = this;
        if (!that.show) {
            return;
        }
        var showItems = [];
        var entities = that.entities._entities;
        for (var i = 0; i < entities.length; ++i) {
            var entitiy = entities.values[i];
            if (i == 0) {
                entitiy.show = true;
                showItems.push(entitiy);
            } else {
                var show = true;
                for (var j = 0; j < showItems.length; j++) {
                    var item = showItems[j];
                    if (that.isCollsionWithRect(item, entitiy)) { //相交
                        show = false;
                    }
                }
                if (show) {
                    entitiy.show = true;
                    showItems.push(entitiy);
                } else {
                    entitiy.show = false;
                }
            }
        }
    };

    /**
     * @private
     * 自动避让
     */
    LayerLabelCollection.prototype.autoAvoid = function() {
        var that = this;
        this.viewer.scene.postRender.addEventListener(function() { that.postRender(); });
    };

    Cesium.defineProperties(LayerLabelCollection.prototype, {

        id: {
            get: function() {
                return this._id;
            }
        },

        /**
         * Gets or sets a human-readable name for this instance.
         * @memberof LayerLabelCollection.prototype
         * @type {String}
         */
        name: {
            get: function() {
                return this._name;
            },
            set: function(value) {
                if (this._name !== value) {
                    this._name = value;
                    this._changed.raiseEvent(this);
                }
            }
        },

        viewer: {
            get: function() {
                return this._viewer;
            }
        },

        show: {
            get: function() {
                return this._show;
            },
            set: function(value) {
                if (value !== this._show) {
                    this._entities.show = value;
                    this._show = value;
                }
            }
        },

        entities: {
            get: function() {
                return this._entities;
            }
        },

        style: {
            get: function() {
                return this._style;
            }
        },
        changed: {
            get: function() {
                return this._changed;
            }
        }
    });
    /**
     * @private
     * 两个实体标注碰撞检测
     */
    LayerLabelCollection.prototype.isCollsionWithRect = function(entitiy1, entitiy2) {
        var b1 = Cesium.SceneTransforms.wgs84ToWindowCoordinates(this.viewer.scene, entitiy1.position._value);
        var b2 = Cesium.SceneTransforms.wgs84ToWindowCoordinates(this.viewer.scene, entitiy2.position._value);
        if (b1 && b2 && !isNaN(b1.x) && !isNaN(b1.y) && !isNaN(b2.x) && !isNaN(b2.y)) {
            var obj1 = this.getLabelSize(entitiy1.label);
            b1.width = obj1.width;
            b1.height = obj1.height;
            var obj2 = this.getLabelSize(entitiy2.label);
            b2.width = obj2.width;;
            b2.height = obj2.height;;
            var x1 = b1.x,
                y1 = b1.y,
                w1 = b1.width,
                h1 = b1.height;
            var x2 = b2.x,
                y2 = b2.y,
                w2 = b2.width,
                h2 = b2.height;
            if (x1 >= x2 && x1 >= x2 + w2) {
                return false;
            } else if (x1 <= x2 && x1 + w1 <= x2) {
                return false;
            } else if (y1 >= y2 && y1 >= y2 + h2) {
                return false;
            } else if (y1 <= y2 && y1 + h1 <= y2) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * 
     * @param {} label 
     */
    LayerLabelCollection.prototype.getLabelSize = function(label) {
        var text = label.text;
        var fontsize = 30;
        if (label.font) {
            var font = label.font.getValue();
            var newStr = font.replace(/[^0-9]/ig, "");
            if (newStr !== '') {
                fontsize = Number(newStr);
            }
        }
        var canvas = document.createElement('canvas'); //创建canvas标签
        var ctx = canvas.getContext('2d');
        var width = ctx.measureText(text).width + fontsize * 2,
            height = fontsize * 2;
        return { width: width, height: height };
    };

    Cesium.LayerLabelCollection = LayerLabelCollection;
})();