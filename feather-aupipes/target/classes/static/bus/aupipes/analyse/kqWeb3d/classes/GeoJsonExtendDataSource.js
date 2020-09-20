/**
 * GeoJsonExtendDataSource short summary.
 * GeoJson数据源扩展支持， 支持ModelPoint类型(.gltf | .glb | .b3dm)
 * @author liuyimeng @KQGIS
 * date 2018/6/19 下午 16:52
 */
(function() {

    function defaultCrsFunction(coordinates) {
        var result = new Cesium.Cartesian3();
        coordinates[0] = (Cesium.defined(coordinates[0])) ? coordinates[0] : 0;
        coordinates[1] = (Cesium.defined(coordinates[1])) ? coordinates[1] : 0;
        coordinates[2] = (Cesium.defined(coordinates[2])) ? coordinates[2] : 0;
        Cesium.Cartesian3.fromDegrees(Number(coordinates[0]), Number(coordinates[1]), Number(coordinates[2]), Cesium.Ellipsoid.WGS84, result);
        return result;
    }

    var crsNames = {
        'urn:ogc:def:crs:OGC:1.3:CRS84': defaultCrsFunction,
        'EPSG:4326': defaultCrsFunction,
        'urn:ogc:def:crs:EPSG::4326': defaultCrsFunction
    };

    var crsLinkHrefs = {};
    var crsLinkTypes = {};
    var defaultMarkerSize = 48;
    var defaultMarkerSymbol;
    var defaultMarkerColor = Cesium.Color.ROYALBLUE;
    var defaultStroke = Cesium.Color.YELLOW;
    var defaultStrokeWidth = 2;
    var defaultFill = Cesium.Color.fromBytes(255, 255, 0, 100);
    var defaultClampToGround = false;
    var defaultRelativeToGround = false;
    var sizes = {
        small: 24,
        medium: 48,
        large: 64
    };

    var simpleStyleIdentifiers = ['title', 'description', //
        'marker-size', 'marker-symbol', 'marker-color', 'stroke', //
        'stroke-opacity', 'stroke-width', 'fill', 'fill-opacity', 'model-name'
    ];

    function defaultDescribe(properties, nameProperty) {
        var html = '';
        for (var key in properties) {
            if (properties.hasOwnProperty(key)) {
                if (key === nameProperty || simpleStyleIdentifiers.indexOf(key) !== -1) {
                    continue;
                }
                var value = properties[key];
                if (Cesium.defined(value)) {
                    if (typeof value === 'object') {
                        html += '<tr><th>' + key + '</th><td>' + defaultDescribe(value) + '</td></tr>';
                    } else {
                        html += '<tr><th>' + key + '</th><td>' + value + '</td></tr>';
                    }
                }
            }
        }

        if (html.length > 0) {
            html = '<table class="cesium-infoBox-defaultTable"><tbody>' + html + '</tbody></table>';
        }

        return html;
    }

    function createDescriptionCallback(describe, properties, nameProperty) {
        var description;
        return function(time, result) {
            if (!Cesium.defined(description)) {
                description = describe(properties, nameProperty);
            }
            return description;
        };
    }

    function defaultDescribeProperty(properties, nameProperty) {
        return new Cesium.CallbackProperty(createDescriptionCallback(defaultDescribe, properties, nameProperty), true);
    }

    //GeoJSON specifies only the Feature object has a usable id property
    //But since "multi" geometries create multiple entity,
    //we can't use it for them either.
    function createObject(geoJson, entityCollection, describe) {
        var id = geoJson.id;
        if (!Cesium.defined(id) || geoJson.type !== 'Feature') {
            id = Cesium.createGuid();
        } else {
            var i = 2;
            var finalId = id;
            while (Cesium.defined(entityCollection.getById(finalId))) {
                finalId = id + '_' + i;
                i++;
            }
            id = finalId;
        }

        var entity = entityCollection.getOrCreateEntity(id);
        var properties = geoJson.properties;
        if (Cesium.defined(properties)) {
            entity.properties = properties;

            var nameProperty;

            //Check for the simplestyle specified name first.
            var name = properties.title;
            if (Cesium.defined(name)) {
                entity.name = name;
                nameProperty = 'title';
            } else {
                //Else, find the name by selecting an appropriate property.
                //The name will be obtained based on this order:
                //1) The first case-insensitive property with the name 'title',
                //2) The first case-insensitive property with the name 'name',
                //3) The first property containing the word 'title'.
                //4) The first property containing the word 'name',
                var namePropertyPrecedence = Number.MAX_VALUE;
                for (var key in properties) {
                    if (properties.hasOwnProperty(key) && properties[key]) {
                        var lowerKey = key.toLowerCase();

                        if (namePropertyPrecedence > 1 && lowerKey === 'title') {
                            namePropertyPrecedence = 1;
                            nameProperty = key;
                            break;
                        } else if (namePropertyPrecedence > 2 && lowerKey === 'name') {
                            namePropertyPrecedence = 2;
                            nameProperty = key;
                        } else if (namePropertyPrecedence > 3 && /title/i.test(key)) {
                            namePropertyPrecedence = 3;
                            nameProperty = key;
                        } else if (namePropertyPrecedence > 4 && /name/i.test(key)) {
                            namePropertyPrecedence = 4;
                            nameProperty = key;
                        }
                    }
                }
                if (Cesium.defined(nameProperty)) {
                    entity.name = properties[nameProperty];
                }
            }

            var description = properties.description;
            if (description !== null) {
                entity.description = !Cesium.defined(description) ? describe(properties, nameProperty) : new Cesium.ConstantProperty(description);
            }
        }
        return entity;
    }

    function coordinatesArrayToCartesianArray(coordinates, crsFunction) {
        var positions = new Array(coordinates.length);
        for (var i = 0; i < coordinates.length; i++) {
            positions[i] = crsFunction(coordinates[i]);
        }
        return positions;
    }

    var geoJsonObjectTypes = {
        Feature: processFeature,
        FeatureCollection: processFeatureCollection,
        GeometryCollection: processGeometryCollection,
        LineString: processLineString,
        MultiLineString: processMultiLineString,
        MultiPoint: processMultiPoint,
        MultiPolygon: processMultiPolygon,
        Point: processPoint,
        Polygon: processPolygon,
        Topology: processTopology,
        ModelPoint: processModelPoint
    };

    var geometryTypes = {
        GeometryCollection: processGeometryCollection,
        LineString: processLineString,
        MultiLineString: processMultiLineString,
        MultiPoint: processMultiPoint,
        MultiPolygon: processMultiPolygon,
        Point: processPoint,
        Polygon: processPolygon,
        Topology: processTopology,
        ModelPoint: processModelPoint
    };

    // GeoJSON processing functions
    function processFeature(dataSource, feature, notUsed, crsFunction, options) {
        if (feature.geometry === null) {
            //Null geometry is allowed, so just create an empty entity instance for it.
            createObject(feature, dataSource._entityCollection, options.describe);
            return;
        }

        if (!Cesium.defined(feature.geometry)) {
            throw new Cesium.RuntimeError('feature.geometry is required.');
        }

        var geometryType = feature.geometry.type;
        var geometryHandler = geometryTypes[geometryType];
        if (!Cesium.defined(geometryHandler)) {
            throw new Cesium.RuntimeError('Unknown geometry type: ' + geometryType);
        }
        geometryHandler(dataSource, feature, feature.geometry, crsFunction, options);
    }

    function processFeatureCollection(dataSource, featureCollection, notUsed, crsFunction, options) {
        var features = featureCollection.features;
        for (var i = 0, len = features.length; i < len; i++) {
            processFeature(dataSource, features[i], undefined, crsFunction, options);
        }
    }

    function processGeometryCollection(dataSource, geoJson, geometryCollection, crsFunction, options) {
        var geometries = geometryCollection.geometries;
        for (var i = 0, len = geometries.length; i < len; i++) {
            var geometry = geometries[i];
            var geometryType = geometry.type;
            var geometryHandler = geometryTypes[geometryType];
            if (!Cesium.defined(geometryHandler)) {
                throw new Cesium.RuntimeError('Unknown geometry type: ' + geometryType);
            }
            geometryHandler(dataSource, geoJson, geometry, crsFunction, options);
        }
    }

    function createPoint(dataSource, geoJson, crsFunction, coordinates, options) {
        var style = ((options.style && options.style.toLowerCase() == 'billboard') ||
            Cesium.defined(options.billboard) || options.image) ? 'billboard' : 'point';
        var graphics;
        //Billboard
        if (Cesium.defined(options.billboard) || style == 'billboard') {
            style = 'billboard';
            var opts = {};
            if (Cesium.defined(options.billboard)) {
                opts = options.billboard;
            } else {
                opts = {
                    image: options.image,
                    scale: options.scale,
                    horizontalOrigin: options.horizontalOrigin,
                    verticalOrigin: options.verticalOrigin,
                    eyeOffset: options.eyeOffset,
                    pixelOffset: options.pixelOffset,
                    rotation: options.rotation,
                    alignedAxis: options.alignedAxis,
                    width: options.width,
                    height: options.height,
                    color: options.color,
                    scaleByDistance: options.scaleByDistance,
                    translucencyByDistance: options.translucencyByDistance,
                    pixelOffsetScaleByDistance: options.pixelOffsetScaleByDistance,
                    imageSubRegion: options.imageSubRegion,
                    sizeInMeters: options.sizeInMeters,
                    heightReference: options.heightReference,
                    distanceDisplayCondition: options.distanceDisplayCondition,
                    disableDepthTestDistance: options.disableDepthTestDistance
                };
            }
            graphics = new Cesium.BillboardGraphics(opts);

            //如果没有图片，就去调用系统 pin 图标
            var oImage = opts.image;
            if (!Cesium.defined(oImage)) {
                var color = options.markerColor;
                var symbol = options.markerSymbol;
                var size = options.markerSize;
                var properties = geoJson.properties;
                if (Cesium.defined(properties)) {
                    var cssColor = properties['marker-color'];
                    if (Cesium.defined(cssColor)) {
                        color = Cesium.Color.fromCssColorString(cssColor);
                    }

                    size = Cesium.defaultValue(sizes[properties['marker-size']], size);
                    var markerSymbol = properties['marker-symbol'];
                    if (Cesium.defined(markerSymbol)) {
                        symbol = markerSymbol;
                    }
                }

                var canvasOrPromise;
                if (Cesium.defined(symbol)) {
                    if (symbol.length === 1) {
                        canvasOrPromise = dataSource._pinBuilder.fromText(symbol.toUpperCase(), color, size);
                    } else {
                        canvasOrPromise = dataSource._pinBuilder.fromMakiIconId(symbol, color, size);
                    }
                } else {
                    canvasOrPromise = dataSource._pinBuilder.fromColor(color, size);
                }
                var promise = Cesium.when(canvasOrPromise).then(function(image) {
                    graphics.image = new Cesium.ConstantProperty(image);
                }).otherwise(function() {
                    graphics.image = new Cesium.ConstantProperty(dataSource._pinBuilder.fromColor(color, size));
                });
                dataSource._promises.push(promise);
            }
        } else if (!Cesium.defined(options.label) || (Cesium.defined(options.label) && Cesium.defined(options.point))) {
            style = 'point';
            var opts = {};
            if (Cesium.defined(options.point)) {
                opts = options.point;
            } else {
                opts = {
                    color: options.color,
                    pixelSize: options.pixelSize,
                    outlineColor: options.outlineColor,
                    outlineWidth: options.outlineWidth,
                    scaleByDistance: options.scaleByDistance,
                    translucencyByDistance: options.translucencyByDistance,
                    heightReference: options.heightReference,
                    distanceDisplayCondition: options.distanceDisplayCondition,
                    disableDepthTestDistance: Cesium.defaultValue(options.disableDepthTestDistance, 1000000) //禁用深度检测的距离
                };
            }
            graphics = new Cesium.PointGraphics(opts);
        }

        var entity = createObject(geoJson, dataSource._entityCollection, options.describe);
        entity[style] = graphics;
        entity.position = new Cesium.ConstantPositionProperty(crsFunction(coordinates));

        /**添加标注 */
        if (Cesium.defined(options.label) && entity.properties) {
            var t = true;
            var properties = entity.properties.getValue();
            if (Cesium.defined(options.label.where) &&
                typeof options.label.where == 'string' && options.label.where !== '') {
                var where = options.label.where;
                where = where.replace(/\$/g, 'properties.');
                t = eval(where);
            }
            if (!Cesium.defined(t) || t) {
                options.label.verticalOrigin = Cesium.defaultValue(options.label.verticalOrigin, Cesium.VerticalOrigin.BOTTOM);
                var express = Cesium.defaultValue(options.label.express, undefined);
                var text = (express && Cesium.defined(properties[express])) ? properties[express] : undefined;
                options.label.text = text;
                var label = new Cesium.LabelGraphics(options.label);
                entity.label = label;
            }
        }
    }

    function processPoint(dataSource, geoJson, geometry, crsFunction, options) {
        createPoint(dataSource, geoJson, crsFunction, geometry.coordinates, options);
    }

    function processMultiPoint(dataSource, geoJson, geometry, crsFunction, options) {
        var coordinates = geometry.coordinates;
        for (var i = 0; i < coordinates.length; i++) {
            createPoint(dataSource, geoJson, crsFunction, coordinates[i], options);
        }
    }

    // ModelPoint 模型点的解析
    function createModelPoint(dataSource, geoJson, crsFunction, coordinates, orientations, options) {
        var baseUri = dataSource._baseUri;
        //解析属性
        var properties = geoJson.properties;
        var modelName = properties['model-name']; //模型名称
        var url = baseUri + '/' + modelName;
        var promise = Cesium.when(Cesium.loadText(url)).then(function() {
            var minimumPixelSize = Cesium.defined(properties['minimum-pixel-size']) ? properties['minimum-pixel-size'] : options.minimumPixelSize,
                maximumScale = Cesium.defined(properties['maximum-scale']) ? properties['maximum-scale'] : options.maximumScale,
                heightReference = properties['height-reference'] || options.heightReference;
            var entity = createObject(geoJson, dataSource._entityCollection, options.describe);
            entity.position = new Cesium.ConstantPositionProperty(crsFunction(coordinates));

            if (Cesium.defined(orientations)) {
                var position = new Cesium.Cartesian3.fromDegrees(coordinates[0], coordinates[1], coordinates[2] || 0);
                var heading = Cesium.Math.toRadians(orientations[0] || 0);
                var pitch = Cesium.Math.toRadians(orientations[1] || 0);
                var roll = Cesium.Math.toRadians(orientations[2] || 0);
                var hpr = new Cesium.HeadingPitchRoll(heading, pitch, roll);
                var orientation = Cesium.Transforms.headingPitchRollQuaternion(position, hpr);
                entity.orientation = orientation;
            }

            var opts = {
                scale: options.scale,
                minimumPixelSize: minimumPixelSize,
                maximumScale: maximumScale,
                incrementallyLoadTextures: options.incrementallyLoadTextures,
                runAnimations: options.runAnimations,
                clampAnimations: options.clampAnimations,
                nodeTransformations: options.nodeTransformations,
                shadows: options.shadows,
                heightReference: heightReference,
                distanceDisplayCondition: options.distanceDisplayCondition,
                silhouetteColor: options.silhouetteColor,
                silhouetteSize: options.silhouetteSize,
                color: options.color,
                colorBlendMode: options.colorBlendMode,
                colorBlendAmount: options.colorBlendMode
            };

            var graphics = new Cesium.ModelGraphics(opts);

            graphics.uri = url;

            entity.model = graphics;

            /**添加标注 */
            if (Cesium.defined(options.label) && entity.properties) {
                var properties = entity.properties.getValue();
                var express = Cesium.defaultValue(options.label.express, undefined);
                var text = (express && Cesium.defined(properties[express])) ? properties[express] : undefined;
                options.label.text = text;
                var label = new Cesium.LabelGraphics(options.label);
                entity.label = label;
            }
        }).otherwise(function() {});

        return promise;

    }

    function processModelPoint(dataSource, geoJson, geometry, crsFunction, options) {
        var promise = createModelPoint(dataSource, geoJson, crsFunction, geometry.coordinates, geometry.orientations, options);
        dataSource._promises.push(promise);
    }

    //以上是模型点的解析

    function createLineString(dataSource, geoJson, crsFunction, coordinates, options) {
        var entity = createObject(geoJson, dataSource._entityCollection, options.describe);
        var graphics;

        if (Cesium.defined(options.corridor)) {
            var opts = options.corridor;
            graphics = new Cesium.CorridorGraphics(opts);
            entity.corridor = graphics;
        } else {
            var opts = {};
            if (Cesium.defined(options.polyline)) {
                opts = options.polyline;
            } else {
                opts = {
                    followSurface: options.followSurface,
                    width: options.width,
                    material: options.material,
                    depthFailMaterial: options.depthFailMaterial,
                    granularity: options.granularity,
                    shadows: options.shadows,
                    distanceDisplayCondition: options.distanceDisplayCondition
                };
            }

            graphics = new Cesium.PolylineGraphics(opts);
            entity.polyline = graphics;
        }


        graphics.positions = new Cesium.ConstantProperty(coordinatesArrayToCartesianArray(coordinates, crsFunction));

        //graphics.distanceDisplayCondition = new Cesium.DistanceDisplayCondition(options.minimumVisibleDistance, options.maximumVisibleDistance);

        /**添加标注 */
        if (Cesium.defined(options.label) && entity.properties) {
            var properties = entity.properties.getValue();
            var express = Cesium.defaultValue(options.label.express, undefined);
            var text = (express && Cesium.defined(properties[express])) ? properties[express] : undefined;
            options.label.text = text;
            var label = new Cesium.LabelGraphics(options.label);
            entity.label = label;
        }
    }

    function processLineString(dataSource, geoJson, geometry, crsFunction, options) {
        createLineString(dataSource, geoJson, crsFunction, geometry.coordinates, options);
    }

    function processMultiLineString(dataSource, geoJson, geometry, crsFunction, options) {
        var lineStrings = geometry.coordinates;
        for (var i = 0; i < lineStrings.length; i++) {
            createLineString(dataSource, geoJson, crsFunction, lineStrings[i], options);
        }
    }

    function createPolygon(dataSource, geoJson, crsFunction, coordinates, options) {
        if (coordinates.length === 0 || coordinates[0].length === 0) {
            return;
        }

        if (!Cesium.defined(options.outline)) {
            options.outline = true;
        }
        var opts = {};
        if (Cesium.defined(options.polygon)) {
            opts = options.polygon;
        } else {
            opts = {
                height: options.height,
                extrudedHeight: options.extrudedHeight,
                fill: options.fill,
                material: options.material,
                outline: options.outline,
                outlineColor: options.outlineColor,
                outlineWidth: options.outlineWidth,
                stRotation: options.stRotation,
                granularity: options.granularity,
                perPositionHeight: options.perPositionHeight,
                closeTop: options.closeTop,
                closeBottom: options.closeBottom,
                shadows: options.shadows,
                distanceDisplayCondition: options.distanceDisplayCondition
            };
        }

        var polygon = new Cesium.PolygonGraphics(opts);

        var holes = [];
        for (var i = 1, len = coordinates.length; i < len; i++) {
            holes.push(new Cesium.PolygonHierarchy(coordinatesArrayToCartesianArray(coordinates[i], crsFunction)));
        }

        var positions = coordinates[0];
        polygon.hierarchy = new Cesium.ConstantProperty(new Cesium.PolygonHierarchy(coordinatesArrayToCartesianArray(positions, crsFunction), holes));
        /*if (options.heightReference != Cesium.HeightReference.CLAMP_TO_GROUND) {
            polygon.height = 0;
        }
        if (options.clampToGround) {
            polygon.perPositionHeight = new Cesium.ConstantProperty(false);
        }*/
        var entity = createObject(geoJson, dataSource._entityCollection, options.describe);
        entity.polygon = polygon;

        /**添加标注 */
        if (Cesium.defined(options.label) && entity.properties) {
            var properties = entity.properties.getValue();
            var express = Cesium.defaultValue(options.label.express, undefined);
            var text = (express && Cesium.defined(properties[express])) ? properties[express] : undefined;
            options.label.text = text;
            var label = new Cesium.LabelGraphics(options.label);
            entity.label = label;
        }
    }

    function processPolygon(dataSource, geoJson, geometry, crsFunction, options) {
        createPolygon(dataSource, geoJson, crsFunction, geometry.coordinates, options);
    }

    function processMultiPolygon(dataSource, geoJson, geometry, crsFunction, options) {
        var polygons = geometry.coordinates;
        for (var i = 0; i < polygons.length; i++) {
            createPolygon(dataSource, geoJson, crsFunction, polygons[i], options);
        }
    }

    function processTopology(dataSource, geoJson, geometry, crsFunction, options) {
        for (var property in geometry.objects) {
            if (geometry.objects.hasOwnProperty(property)) {
                var feature = Cesium.topojson.feature(geometry, geometry.objects[property]);
                var typeHandler = geoJsonObjectTypes[feature.type];
                typeHandler(dataSource, feature, feature, crsFunction, options);
            }
        }
    }

    /**
     * A {@link Cesium.DataSource} which processes both
     * {@link http://www.geojson.org/|GeoJSON} and {@link https://github.com/mbostock/Cesium.topojson|TopoJSON} data.
     * {@link https://github.com/mapbox/simplestyle-spec|simplestyle-spec} properties will also be used if they
     * are present.
     *
     * @alias GeoJsonExtendDataSource
     * @constructor
     *
     * @param {String} [name] The name of this data source.  If undefined, a name will be taken from
     *                        the name of the GeoJSON file.
     *
     * @demo {@link http://cesiumjs.org/Cesium/Apps/Sandcastle/index.html?src=GeoJSON%20and%20TopoJSON.html|Cesium Sandcastle GeoJSON and TopoJSON Demo}
     * @demo {@link http://cesiumjs.org/Cesium/Apps/Sandcastle/index.html?src=GeoJSON%20simplestyle.html|Cesium Sandcastle GeoJSON simplestyle Demo}
     *
     * @example
     * var viewer = new Cesium.Viewer('cesiumContainer');
     * var options = {
     *       billboard: {
     *           image: 'images/Attractions_01.png',
     *           heightReference: 0,
     *           minimumVisibleDistance: 0,
     *           maximumVisibleDistance: 350000,
     *           disableDepthTestDistance: 350000,
     *           distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 350000),
     *           width: 0,
     *           height: 0,
     *           scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 100000, 0.3)
     *      },
     *       label: {
     *           express: 'NAME',
     *           where: '$NAME.indexOf("医院")>-1', //满足条件的才显示 js代码
     *           font: '14px 等线体',
     *           disableDepthTestDistance: 350000,
     *           distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 350000),
     *           //['${Height} >= 100'
     *           scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 100000, 0.3)
     *       }
     *   };
     *   viewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load('3d/data/地名.json', options)).then(function(t) {
     *           t.autoAvoid(viewer);
     *   });
     */
    function GeoJsonExtendDataSource(name) {
        this._name = name;
        this._changed = new Cesium.Event();
        this._error = new Cesium.Event();
        this._isLoading = false;
        this._loading = new Cesium.Event();
        this._entityCollection = new Cesium.EntityCollection(this);
        this._promises = [];
        this._pinBuilder = new Cesium.PinBuilder();
        this._entityCluster = new Cesium.EntityCluster();
        this._baseUri = '';

        //TODO KQGIS
        //#region
        this._guid = Cesium.createGuid();

        //#endregion
    }

    /**
     * Creates a Promise to a new instance loaded with the provided GeoJSON or TopoJSON data.
     *
     * @param {String|Object} data A url, GeoJSON object, or TopoJSON object to be loaded.
     * @param {Object} [options] An object with the following properties:
     * @param {String} [options.name] The dataSource's name. 数据源名称
     * @param {String} [options.sourceUri] Overrides the url to use for resolving relative links.
     * @param {GeoJsonExtendDataSource~describe} [options.describe=GeoJsonExtendDataSource.defaultDescribeProperty] A function
     *                  which returns a Property object (or just a string), which converts the properties into an html description.
     * @param {String} [options.style] The point symbol style. Valuse point、billboard.
     *                  Effect to point.
     * @param {Property} [options.image] A Property specifying the Image, URI, or Canvas to use for the billboard.
     *                  Effect to point(billboard).
     * @param {Property} [options.scale=1.0] A numeric Property specifying the scale to apply to the image size.
     *                  Effect to point(billboard).
     *                  A numeric Property specifying a uniform linear scale. Effect to model.
     * @param {Property} [options.horizontalOrigin=Cesium.HorizontalOrigin.CENTER] A Property specifying the {@link Cesium.HorizontalOrigin}.
     *                  Effect to point(billboard).
     * @param {Property} [options.verticalOrigin=Cesium.VerticalOrigin.CENTER] A Property specifying the {@link Cesium.VerticalOrigin}.
     *                  Effect to point(billboard).
     * @param {Property} [options.eyeOffset=[0,0,0] | options.eyeOffset=Cesium.Cartesian3.ZERO] The array or a {@link Cesium.Cartesian3}
     *                  Property specifying the eye offset.
     *                  Effect to point(billboard).
     * @param {Property} [options.pixelOffset=[0,0] | options.pixelOffset=Cesium.Cartesian2.ZERO] The array or a {@link Cesium.Cartesian2}
     *                  Property specifying the pixel offset.
     *                  Effect to point(billboard).
     * @param {Property} [options.rotation=0] A numeric Property specifying the rotation about the alignedAxis.
     *                  Effect to point(billboard).
     * @param {Property} [options.alignedAxis=[0,0,0] | Cesium.Cartesian3.ZERO] The array or A {@link Cesium.Cartesian3} Property
     *                  specifying the unit vector axis of rotation.
     *                  Effect to point(billboard).
     * @param {Property} [options.width] A numeric Property specifying the width of the billboard in pixels,
     *                  overriding the native size.
     *                  A numeric Property specifying the distance between the edges of the corridor.
     *                  A numeric Property specifying the width of the polyine.
     *
     *                  Effect to point(billboard)、polyline(corridor\polyline).
     * @param {Property} [options.height] A numeric Property specifying the height of the billboard in pixels,
     *                 overriding the native size. Effect to billboard.
     *
     *                 A numeric Property specifying the altitude of the corridor relative to the
     *                  ellipsoid surface.Effect to corridor.
     *
     *                 A numeric Property specifying the altitude of the polygon relative to the ellipsoid surface.
     *                 Effect to polygon.
     * @param {Property} [options.color=Cesium.Color.WHITE] A Property specifying the tint {@link Cesium.Color} of the image or the point.
     *                   Effect to point(billboard).
     *                   A Property specifying the {@link Cesium.Color} that blends with the model's rendered color.
     *                   Effect to model.
     *                   Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *
     * @param {Property} [options.scaleByDistance] A {@link Cesium.NearFarScalar} Property used to scale the point based on
     *                  distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.translucencyByDistance] A {@link Cesium.NearFarScalar} Property used to set translucency
     *                  based on distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.pixelOffsetScaleByDistance] A {@link Cesium.NearFarScalar} Property used to set pixelOffset
     *                  based on distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.imageSubRegion] A Property specifying a {@link BoundingRectangle} that defines a
     *                  sub-region of the image to use for the billboard, rather than the entire image, measured in pixels from the bottom-left.
     *                  Effect to point(billboard).
     * @param {Boolean} [options.sizeInMeters] A boolean Property specifying whether this billboard's size should be
     *                  measured in meters.
     *                  Effect to point(billboard).
     * @param {Int} [options.heightReference=Cesium.HeightReference.NONE] What the height is relative to.
     *                  0:NONE 绝对高度
     *                  1:CLAMP_TO_GROUND 贴地
     *                  2:RELATIVE_TO_GROUND 相对高度
     *                  Effect to point(billboard).
     * @param {Double} [options.minimumVisibleDistance=GeoJsonExtendDataSource.] The smallest distance in the interval
     *                  where the object is visible.
     *                  最小可视距离,默认0.0.
     *                  Effect to point.
     * @param {Double} [options.maximumVisibleDistance=GeoJsonExtendDataSource.] The largest distance in the interval
     *                  where the object is visible.
     *                  最大可视距离.
     *                  Effect to point、polyline(lineString)、polygon.
     * @param {Double} [options.disableDepthTestDistance] A Property specifying the distance from the camera at which to
     *                  disable the depth test to.
     *                  禁用深度检测距离，当对象到相机的距离小于该值时不再进行深度检测.
     *                  Effect to point、polyline(lineString)、polygon.
     * @param {Property} [options.pixelSize=1] A numeric Property specifying the size in pixels.
     * @param {Property} [options.outlineColor=Cesium.Color.BLACK] A Property specifying the {@link Cesium.Color} of the outline.
     *                   Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                   Effect to point.
     * @param {Property} [options.outlineWidth=0] A numeric Property specifying the the outline width in pixels.
     *                   Effect to point.
     * @param {Property} [options.cornerType=Cesium.CornerType.ROUNDED] A {@link Cesium.CornerType} Property specifying the style of
     *                  the corners.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.extrudedHeight] A numeric Property specifying the altitude of the corridor's extruded
     *                  face relative to the ellipsoid surface.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.fill=true] A boolean Property specifying whether the corridor is filled with the
     *                  provided material. Effect to polyline(corridor).
     *                  A boolean Property specifying whether the polygon is filled with the provided material.Effect to polygon.
     * @param {Object} [options.material=Cesium.Color.GREEN] The material used to fill the corridor.
     *                  The material used to draw the polyline.
     *                  The material used to fill the polygon.
     *                  使用颜色
     *                  {
     *                      type:'color',
     *                      color:'rgba(255,2,1,1)',// 'rgb(255,2,1)' '#ffffff'
     *                  }，
     *
     *                  使用纹理
     *                  {
     *                     type:'image',
     *                     image:'url',
     *                     repeat:[1.0,1.0]|1,
     *                     transparent:true
     *                  }
     *                  Effect to polyline(corridor\polyline)、polygon.
     *
     * @param {Property} [options.outline=false] A boolean Property specifying whether the corridor is outlined.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.outlineColor=Cesium.Color.BLACK] A Property specifying the {@link Cesium.Color} of the outline.
     *                  Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                  Effect to polyline(corridor)、poly.
     * @param {Property} [options.outlineWidth=1.0] A numeric Property specifying the width of the outline.
     *                  Effect to polyline(corridor).
     *                  A boolean Property specifying whether the polygon is outlined. Effect to polygon.
     *
     * @param {Property} [options.granularity=Cesium.Math.RADIANS_PER_DEGREE] A numeric Property specifying the distance
     *                  between each latitude and longitude.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.shadows=Cesium.ShadowMode.DISABLED] An enum Property specifying whether the corridor/polyline/el casts
     *                  or receives shadows from each light source.
     *                  Effect to polyline(corridor\polyline)、polygon、model.
     * @param {Property} [options.followSurface=true] A boolean Property specifying whether the line segments should be
     *                  great arcs or linearly connected.
     *                  Effect to polyline(polyline).
     * @param {Object} [options.depthFailMaterial] The material used to  draw the polyline
     *                  Cesium.when it is below the terrain.
     *                  使用颜色
     *                  {
     *                      type:'color',
     *                      color:'rgba(255,2,1,1)',// 'rgb(255,2,1)' '#ffffff'
     *                  }，
     *
     *                  使用纹理
     *                  {
     *                     type:'image',
     *                     image:'url',
     *                     repeat:[1.0,1.0]|1,
     *                     transparent:true
     *                  }
     *                  Effect to polyline(polyline).
     * @param {Property} [options.granularity=Cesium.Math.RADIANS_PER_DEGREE] A numeric Property specifying the angular
     *                  distance between each latitude and longitude if followSurface is true.
     *                  Effect to polyline(polyline)、polygon.
     *
     * @param {Property} [options.extrudedHeight] A numeric Property specifying the altitude of the polygon's extruded
     *                  face relative to the ellipsoid surface.
     *                  Effect to polygon.
     * @param {Property} [options.stRotation=0.0] A numeric property specifying the rotation of the polygon texture
     * counter-clockwise from north.
     * @param {Property} [options.perPositionHeight=false] A boolean specifying whether or not the the height of each
     *                  position is used.
     *                  Effect to polygon.
     * @param {Boolean} [options.closeTop=true] When false, leaves off the top of an extruded polygon open.
     *                  Effect to polygon.
     * @param {Boolean} [options.closeBottom=true] When false, leaves off the bottom of an extruded polygon open.
     *                  Effect to polygon.

     * @param {Property} [options.minimumPixelSize=0.0] A numeric Property specifying the approximate minimum pixel size
     *                  of the model regardless of zoom.
     *                  Effect to model.
     * @param {Property} [options.maximumScale] The maximum scale size of a model. An upper limit for minimumPixelSize.
     *                   Effect to model.
     * @param {Property} [options.incrementallyLoadTextures=true] Determine if textures may continue to stream in after
     *                  the model is loaded.
     *                  Effect to model.
     * @param {Property} [options.runAnimations=true] A boolean Property specifying if glTF animations specified in
     *                  the model should be started.
     *                  Effect to model.
     * @param {Property} [options.clampAnimations=true] A boolean Property specifying if glTF animations should
     *                  hold the last pose for time durations with no keyframes.
     *                  Effect to model.
     * @param {Property} [options.nodeTransformations] An object, where keys are names of nodes, and valuesare
     *                  {@link TranslationRotationScale} Properties describing the transformation to apply to that node.
     *                  Effect to model.
     * @param {Property} [options.silhouetteColor=Cesium.Color.RED] A Property specifying the {@link Cesium.Color} of the silhouette.
     *                  Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                  Effect to model.
     *                  轮廓颜色，只针对模型生效.
     * @param {Property} [options.silhouetteSize=0.0] A numeric Property specifying the size of the silhouette in pixels.
     *                   Effect to model.
     *                   轮廓宽度，只针对模型生效.
     * @param {Property} [options.colorBlendMode=Cesium.ColorBlendMode.HIGHLIGHT] An enum Property specifying how the color
     *                  blends with the model.
     *                  Effect to model.
     * @param {Property} [options.colorBlendAmount=0.5] A numeric Property specifying the color strength Cesium.when the
     *                  <code>colorBlendMode</code> is <code>MIX</code>. A value of 0.0 results in the model's rendered
     *                  color while a alue of 1.0 results in a solid color, with any value in-between resulting in a mix
     *                  of the two.
     *                  Effect to model.
     *
     * @param {Number} [options.markerSize=48] The default size of the map pin created for each point, in pixels.
     *                  Effect to point(billboard).
     * @param {String} [options.markerSymbol] The default symbol of the map pin
     *                  created for each point.
     * @param {Cesium.Color} [options.markerColor] The default color of the map pin created
     *                  for each point.
     * @param {Boolean} [options.clampToGround=false] true if we want the geometry
     *                  features (polygons or linestrings) clamped to the ground. If true, lines will use corridors so use Entity.corridor instead of Entity.polyline.
     *                   对象是否贴地.
     * @param {Boolean} [options.relativeToGround=GeoJsonExtendDataSource.relativeToGround] true if we want the geometry features (polygons or linestrings or modelpoints) relative to the ground.
     *                  If true, lines will use corridors so use Entity.corridor instead of Entity.polyline. And if clampToGround is true ,the parameter does not effect.
     *                  对象高程是否相对于地形
     *
     * @returns {Promise.<GeoJsonExtendDataSource>} a promise that will resolve Cesium.when the GeoJSON is loaded.
     *
     * @example
     *
     *   var viewer = new Cesium.Viewer('cesiumContainer');
     *   //esriGeoJson 数据路径或 esriGeoJson对象
     *   var data = 'http://localhost:9009/data/china.json';
     *   //点
     *   var options = {
     *        style:'point',
     *        color:'rgba(255,255,255,1)',
     *        pixelSize:5,
     *        outlineColor:'rgba(255,255,0,1)',
     *        outlineWidth:2,
     *        heightReference:0,
     *        minimumVisibleDistance:0,
     *        maximumVisibleDistance:100000000,
     *        disableDepthTestDistance:500000
     *   };
     *
     *   //billboard
     *   var options2 = {
     *        style:'billboard',
     *        image:'image/logo.png',
     *        scale:1.0,
     *        horizontalOrigin:0,
     *        verticalOrigin:0,
     *        eyeOffset:[0,0,0],
     *        pixelOffset:[0,0],
     *        width:32,
     *        height:32,
     *        color:'rgba(255,255,255,1)',
     *        heightReference:0,
     *        minimumVisibleDistance:0,
     *        maximumVisibleDistance:100000000,
     *        disableDepthTestDistance:500000
     *   };
     *
     *   //线
     *   var options3 = {
     *       followSurface:false,
     *       width:2,
     *       material: 'rgba(255,255,0,1)',
     *       //granularity:1
     *       shadows:0,
     *       minimumVisibleDistance:0,
     *       maximumVisibleDistance:100000000
     *   };
     *
     *   //面
     *   var options4 = {
     *      //height:0
     *      //extrudedHeighty
     *      fill:true,
     *      material:'rgba(255,255,0,1)',
     *      outline:true,
     *      outlineColor:'rgba(0,255,0,1)',
     *      outlineWidth:2,
     *      stRotation:0,
     *      granularity:0.0005,
     *      perPositionHeight:false,
     *      closeTop:true,
     *      closeBottom:true,
     *      shadows:0
     *      minimumVisibleDistance:0,
     *      maximumVisibleDistance:100000000
     *   };
     *
     *   var loadPromise  =  viewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(data,options));
     *
     *   loadPromise.then(function(dataSource){
     *      //加载成功，返回 dataSource
     *      console.log('success');
     *   },function(error){
     *      //失败
     *      console.log('error');
     *   });
     *
     */
    GeoJsonExtendDataSource.load = function(data, options) {
        return new GeoJsonExtendDataSource().load(data, options);
    };

    Cesium.defineProperties(GeoJsonExtendDataSource, {
        /**
         * Gets or sets the default size of the map pin created for each point, in pixels.
         * @memberof GeoJsonExtendDataSource
         * @type {Number}
         * @default 48
         */
        markerSize: {
            get: function() {
                return defaultMarkerSize;
            },
            set: function(value) {
                defaultMarkerSize = value;
            }
        },
        /**
         * Gets or sets the default symbol of the map pin created for each point.
         * This can be any valid {@link http://mapbox.com/maki/|Maki} identifier, any single character,
         * or blank if no symbol is to be used.
         * @memberof GeoJsonExtendDataSource
         * @type {String}
         */
        markerSymbol: {
            get: function() {
                return defaultMarkerSymbol;
            },
            set: function(value) {
                defaultMarkerSymbol = value;
            }
        },
        /**
         * Gets or sets the default color of the map pin created for each point.
         * @memberof GeoJsonExtendDataSource
         * @type {Cesium.Color}
         * @default Cesium.Color.ROYALBLUE
         */
        markerColor: {
            get: function() {
                return defaultMarkerColor;
            },
            set: function(value) {
                defaultMarkerColor = value;
            }
        },
        /**
         * Gets or sets default of whether to clamp to the ground.
         * @memberof GeoJsonExtendDataSource
         * @type {Boolean}
         * @default false
         */
        clampToGround: {
            get: function() {
                return defaultClampToGround;
            },
            set: function(value) {
                defaultClampToGround = value;
            }
        },
        /**
         * Gets or sets default of whether to relative to the ground.
         * @memberof GeoJsonExtendDataSource
         * @type {Boolean}
         * @default false
         */
        relativeToGround: {
            get: function() {
                return defaultRelativeToGround;
            },
            set: function(value) {
                defaultRelativeToGround = value;
            }
        },

        /**
         * Gets an object that maps the name of a crs to a callback function which takes a GeoJSON coordinate
         * and transforms it into a WGS84 Earth-fixed Cartesian.  Older versions of GeoJSON which
         * supported the EPSG type can be added to this list as well, by specifying the complete EPSG name,
         * for example 'EPSG:4326'.
         * @memberof GeoJsonExtendDataSource
         * @type {Object}
         */
        crsNames: {
            get: function() {
                return crsNames;
            }
        },

        /**
         * Gets an object that maps the href property of a crs link to a callback function
         * which takes the crs properties object and returns a Promise that resolves
         * to a function that takes a GeoJSON coordinate and transforms it into a WGS84 Earth-fixed Cartesian.
         * Items in this object take precedence over those Cesium.defined in <code>crsLinkHrefs</code>, assuming
         * the link has a type specified.
         * @memberof GeoJsonExtendDataSource
         * @type {Object}
         */
        crsLinkHrefs: {
            get: function() {
                return crsLinkHrefs;
            }
        },

        /**
         * Gets an object that maps the type property of a crs link to a callback function
         * which takes the crs properties object and returns a Promise that resolves
         * to a function that takes a GeoJSON coordinate and transforms it into a WGS84 Earth-fixed Cartesian.
         * Items in <code>crsLinkHrefs</code> take precedence over this object.
         * @memberof GeoJsonExtendDataSource
         * @type {Object}
         */
        crsLinkTypes: {
            get: function() {
                return crsLinkTypes;
            }
        }
    });

    Cesium.defineProperties(GeoJsonExtendDataSource.prototype, {
        /**
         * Gets or sets a human-readable name for this instance.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {String}
         */
        name: {
            get: function() {
                if (!Cesium.defined(this._name))
                    return this._guid;

                return this._name;
            },
            set: function(value) {
                if (this._name !== value) {
                    this._name = value;
                    this._changed.raiseEvent(this);
                }
            }
        },

        //TODO KQGIS
        //#region
        /**
         * Gets guis for this instance.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {String}
         */
        guid: {
            get: function() {
                return this._guid;
            }
        },

        //#endregion

        /**
         * This Cesium.DataSource only defines static data, therefore this property is always undefined.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {DataSourceClock}
         */
        clock: {
            value: undefined,
            writable: false
        },
        /**
         * Gets the collection of {@link Entity} instances.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Cesium.EntityCollection}
         */
        entities: {
            get: function() {
                return this._entityCollection;
            }
        },
        /**
         * Gets a value indicating if the data source is currently loading data.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Boolean}
         */
        isLoading: {
            get: function() {
                return this._isLoading;
            }
        },
        /**
         * Gets an event that will be raised Cesium.when the underlying data changes.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Cesium.Event}
         */
        changedEvent: {
            get: function() {
                return this._changed;
            }
        },
        /**
         * Gets an event that will be raised if an error is encountered during processing.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Cesium.Event}
         */
        errorEvent: {
            get: function() {
                return this._error;
            }
        },
        /**
         * Gets an event that will be raised Cesium.when the data source either starts or stops loading.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Cesium.Event}
         */
        loadingEvent: {
            get: function() {
                return this._loading;
            }
        },
        /**
         * Gets whether or not this data source should be displayed.
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Boolean}
         */
        show: {
            get: function() {
                return this._entityCollection.show;
            },
            set: function(value) {
                this._entityCollection.show = value;
            }
        },

        /**
         * Gets or sets the clustering options for this data source. This object can be shared between multiple data sources.
         *
         * @memberof GeoJsonExtendDataSource.prototype
         * @type {Cesium.EntityCluster}
         */
        clustering: {
            get: function() {
                return this._entityCluster;
            },
            set: function(value) {
                //>>includeStart('debug', pragmas.debug);
                if (!Cesium.defined(value)) {
                    throw new Cesium.DeveloperError('value must be Cesium.defined.');
                }
                //>>includeEnd('debug');
                this._entityCluster = value;
            }
        }
    });

    /**
     * Asynchronously loads the provided GeoJSON or TopoJSON data, replacing any existing data.
     *
     * @param {String|Object} data A url, GeoJSON object, or TopoJSON object to be loaded.
     * @param {Object} [options] An object with the following properties:
     * @param {String} [options.name] The dataSource's name. 数据源名称
     * @param {String} [options.sourceUri] Overrides the url to use for resolving relative links.
     * @param {GeoJsonExtendDataSource~describe} [options.describe=GeoJsonExtendDataSource.defaultDescribeProperty] A function
     *                  which returns a Property object (or just a string), which converts the properties into an html description.
     * @param {String} [options.style] The point symbol style. Valuse point、billboard.
     *                  Effect to point.
     * @param {Property} [options.image] A Property specifying the Image, URI, or Canvas to use for the billboard.
     *                  Effect to point(billboard).
     * @param {Property} [options.scale=1.0] A numeric Property specifying the scale to apply to the image size.
     *                  Effect to point(billboard).
     *                  A numeric Property specifying a uniform linear scale. Effect to model.
     * @param {Property} [options.horizontalOrigin=Cesium.HorizontalOrigin.CENTER] A Property specifying the {@link Cesium.HorizontalOrigin}.
     *                  Effect to point(billboard).
     * @param {Property} [options.verticalOrigin=Cesium.VerticalOrigin.CENTER] A Property specifying the {@link Cesium.VerticalOrigin}.
     *                  Effect to point(billboard).
     * @param {Property} [options.eyeOffset=[0,0,0] | options.eyeOffset=Cesium.Cartesian3.ZERO] The array or a {@link Cesium.Cartesian3}
     *                  Property specifying the eye offset.
     *                  Effect to point(billboard).
     * @param {Property} [options.pixelOffset=[0,0] | options.pixelOffset=Cesium.Cartesian2.ZERO] The array or a {@link Cesium.Cartesian2}
     *                  Property specifying the pixel offset.
     *                  Effect to point(billboard).
     * @param {Property} [options.rotation=0] A numeric Property specifying the rotation about the alignedAxis.
     *                  Effect to point(billboard).
     * @param {Property} [options.alignedAxis=[0,0,0] | Cesium.Cartesian3.ZERO] The array or A {@link Cesium.Cartesian3} Property
     *                  specifying the unit vector axis of rotation.
     *                  Effect to point(billboard).
     * @param {Property} [options.width] A numeric Property specifying the width of the billboard in pixels,
     *                  overriding the native size.
     *                  A numeric Property specifying the distance between the edges of the corridor.
     *                  A numeric Property specifying the width of the polyine.
     *
     *                  Effect to point(billboard)、polyline(corridor\polyline).
     * @param {Property} [options.height] A numeric Property specifying the height of the billboard in pixels,
     *                 overriding the native size. Effect to billboard.
     *
     *                 A numeric Property specifying the altitude of the corridor relative to the
     *                  ellipsoid surface.Effect to corridor.
     *
     *                 A numeric Property specifying the altitude of the polygon relative to the ellipsoid surface.
     *                 Effect to polygon.
     * @param {Property} [options.color=Cesium.Color.WHITE] A Property specifying the tint {@link Cesium.Color} of the image or the point.
     *                   Effect to point(billboard).
     *                   A Property specifying the {@link Cesium.Color} that blends with the model's rendered color.
     *                   Effect to model.
     *                   Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *
     * @param {Property} [options.scaleByDistance] A {@link Cesium.NearFarScalar} Property used to scale the point based on
     *                  distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.translucencyByDistance] A {@link Cesium.NearFarScalar} Property used to set translucency
     *                  based on distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.pixelOffsetScaleByDistance] A {@link Cesium.NearFarScalar} Property used to set pixelOffset
     *                  based on distance from the camera.
     *                  Effect to point(billboard).
     * @param {Property} [options.imageSubRegion] A Property specifying a {@link BoundingRectangle} that defines a
     *                  sub-region of the image to use for the billboard, rather than the entire image, measured in pixels from the bottom-left.
     *                  Effect to point(billboard).
     * @param {Boolean} [options.sizeInMeters] A boolean Property specifying whether this billboard's size should be
     *                  measured in meters.
     *                  Effect to point(billboard).
     * @param {Int} [options.heightReference=Cesium.HeightReference.NONE] What the height is relative to.
     *                  0:NONE 绝对高度
     *                  1:CLAMP_TO_GROUND 贴地
     *                  2:RELATIVE_TO_GROUND 相对高度
     *                  Effect to point(billboard).
     * @param {Double} [options.minimumVisibleDistance=GeoJsonExtendDataSource.] The smallest distance in the interval
     *                  where the object is visible.
     *                  最小可视距离,默认0.0.
     *                  Effect to point.
     * @param {Double} [options.maximumVisibleDistance=GeoJsonExtendDataSource.] The largest distance in the interval
     *                  where the object is visible.
     *                  最大可视距离.
     *                  Effect to point、polyline(lineString)、polygon.
     * @param {Double} [options.disableDepthTestDistance] A Property specifying the distance from the camera at which to
     *                  disable the depth test to.
     *                  禁用深度检测距离，当对象到相机的距离小于该值时不再进行深度检测.
     *                  Effect to point、polyline(lineString)、polygon.
     * @param {Property} [options.pixelSize=1] A numeric Property specifying the size in pixels.
     * @param {Property} [options.outlineColor=Cesium.Color.BLACK] A Property specifying the {@link Cesium.Color} of the outline.
     *                   Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                   Effect to point.
     * @param {Property} [options.outlineWidth=0] A numeric Property specifying the the outline width in pixels.
     *                   Effect to point.
     * @param {Property} [options.cornerType=Cesium.CornerType.ROUNDED] A {@link Cesium.CornerType} Property specifying the style of
     *                  the corners.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.extrudedHeight] A numeric Property specifying the altitude of the corridor's extruded
     *                  face relative to the ellipsoid surface.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.fill=true] A boolean Property specifying whether the corridor is filled with the
     *                  provided material. Effect to polyline(corridor).
     *                  A boolean Property specifying whether the polygon is filled with the provided material.Effect to polygon.
     * @param {Object} [options.material=Cesium.Color.GREEN] The material used to fill the corridor.
     *                  The material used to draw the polyline.
     *                  The material used to fill the polygon.
     *                  使用颜色
     *                  {
     *                      type:'color',
     *                      color:'rgba(255,2,1,1)',// 'rgb(255,2,1)' '#ffffff'
     *                  }，
     *
     *                  使用纹理
     *                  {
     *                     type:'image',
     *                     image:'url',
     *                     repeat:[1.0,1.0]|1,
     *                     transparent:true
     *                  }
     *                  Effect to polyline(corridor\polyline)、polygon.
     *
     * @param {Property} [options.outline=false] A boolean Property specifying whether the corridor is outlined.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.outlineColor=Cesium.Color.BLACK] A Property specifying the {@link Cesium.Color} of the outline.
     *                  Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                  Effect to polyline(corridor)、poly.
     * @param {Property} [options.outlineWidth=1.0] A numeric Property specifying the width of the outline.
     *                  Effect to polyline(corridor).
     *                  A boolean Property specifying whether the polygon is outlined. Effect to polygon.
     *
     * @param {Property} [options.granularity=Cesium.Math.RADIANS_PER_DEGREE] A numeric Property specifying the distance
     *                  between each latitude and longitude.
     *                  Effect to polyline(corridor).
     * @param {Property} [options.shadows=Cesium.ShadowMode.DISABLED] An enum Property specifying whether the corridor/polyline/el casts
     *                  or receives shadows from each light source.
     *                  Effect to polyline(corridor\polyline)、polygon、model.
     * @param {Property} [options.followSurface=true] A boolean Property specifying whether the line segments should be
     *                  great arcs or linearly connected.
     *                  Effect to polyline(polyline).
     * @param {Object} [options.depthFailMaterial] The material used to  draw the polyline
     *                  Cesium.when it is below the terrain.
     *                  使用颜色
     *                  {
     *                      type:'color',
     *                      color:'rgba(255,2,1,1)',// 'rgb(255,2,1)' '#ffffff'
     *                  }，
     *
     *                  使用纹理
     *                  {
     *                     type:'image',
     *                     image:'url',
     *                     repeat:[1.0,1.0]|1,
     *                     transparent:true
     *                  }
     *                  Effect to polyline(polyline).
     * @param {Property} [options.granularity=Cesium.Math.RADIANS_PER_DEGREE] A numeric Property specifying the angular
     *                  distance between each latitude and longitude if followSurface is true.
     *                  Effect to polyline(polyline)、polygon.
     *
     * @param {Property} [options.extrudedHeight] A numeric Property specifying the altitude of the polygon's extruded
     *                  face relative to the ellipsoid surface.
     *                  Effect to polygon.
     * @param {Property} [options.stRotation=0.0] A numeric property specifying the rotation of the polygon texture
     * counter-clockwise from north.
     * @param {Property} [options.perPositionHeight=false] A boolean specifying whether or not the the height of each
     *                  position is used.
     *                  Effect to polygon.
     * @param {Boolean} [options.closeTop=true] When false, leaves off the top of an extruded polygon open.
     *                  Effect to polygon.
     * @param {Boolean} [options.closeBottom=true] When false, leaves off the bottom of an extruded polygon open.
     *                  Effect to polygon.

     * @param {Property} [options.minimumPixelSize=0.0] A numeric Property specifying the approximate minimum pixel size
     *                  of the model regardless of zoom.
     *                  Effect to model.
     * @param {Property} [options.maximumScale] The maximum scale size of a model. An upper limit for minimumPixelSize.
     *                   Effect to model.
     * @param {Property} [options.incrementallyLoadTextures=true] Determine if textures may continue to stream in after
     *                  the model is loaded.
     *                  Effect to model.
     * @param {Property} [options.runAnimations=true] A boolean Property specifying if glTF animations specified in
     *                  the model should be started.
     *                  Effect to model.
     * @param {Property} [options.clampAnimations=true] A boolean Property specifying if glTF animations should
     *                  hold the last pose for time durations with no keyframes.
     *                  Effect to model.
     * @param {Property} [options.nodeTransformations] An object, where keys are names of nodes, and valuesare
     *                  {@link TranslationRotationScale} Properties describing the transformation to apply to that node.
     *                  Effect to model.
     * @param {Property} [options.silhouetteColor=Cesium.Color.RED] A Property specifying the {@link Cesium.Color} of the silhouette.
     *                  Supports 'rgba(255,255,255,1)','#ffffff','rgb(255,255,255)' format.
     *                  Effect to model.
     *                  轮廓颜色，只针对模型生效.
     * @param {Property} [options.silhouetteSize=0.0] A numeric Property specifying the size of the silhouette in pixels.
     *                   Effect to model.
     *                   轮廓宽度，只针对模型生效.
     * @param {Property} [options.colorBlendMode=Cesium.ColorBlendMode.HIGHLIGHT] An enum Property specifying how the color
     *                  blends with the model.
     *                  Effect to model.
     * @param {Property} [options.colorBlendAmount=0.5] A numeric Property specifying the color strength Cesium.when the
     *                  <code>colorBlendMode</code> is <code>MIX</code>. A value of 0.0 results in the model's rendered
     *                  color while a alue of 1.0 results in a solid color, with any value in-between resulting in a mix
     *                  of the two.
     *                  Effect to model.
     *
     * @param {Number} [options.markerSize=48] The default size of the map pin created for each point, in pixels.
     *                  Effect to point(billboard).
     * @param {String} [options.markerSymbol] The default symbol of the map pin
     *                  created for each point.
     * @param {Cesium.Color} [options.markerColor] The default color of the map pin created
     *                  for each point.
     * @param {Boolean} [options.clampToGround=false] true if we want the geometry
     *                  features (polygons or linestrings) clamped to the ground. If true, lines will use corridors so use Entity.corridor instead of Entity.polyline.
     *                   对象是否贴地.
     * @param {Boolean} [options.relativeToGround=GeoJsonExtendDataSource.relativeToGround] true if we want the geometry features (polygons or linestrings or modelpoints) relative to the ground.
     *                  If true, lines will use corridors so use Entity.corridor instead of Entity.polyline. And if clampToGround is true ,the parameter does not effect.
     *                  对象高程是否相对于地形
     *
     * @returns {Promise.<GeoJsonExtendDataSource>} a promise that will resolve Cesium.when the GeoJSON is loaded.
     *
     * @example
     *      模型对应的json文件格式为：
     *          {
     *              "type": "FeatureCollection",
     *               "features": [{
     *                  "type": "Feature",
     *                  "geometry": {
     *                      "type": "ModelPoint",
     *                      "coordinates": [117.11814, 29.195168,100], //模型位置
     *                      "orientations":[0,0,0]                     //模型姿态（可选）
     *                  },
     *                  "properties": {
     *                      "name":"模型名称",                          //模型名称
     *                      "model-name": "CesiumAir/Cesium_Air.glb",   //模型名称（和json文件的基础路径组合在一起得到模型的全路径）
     *                      "minimum-pixel-size": 0,                    //像素大小 （可选，默认使用全局设置）
     *                      "maximum-scale": 2000,                      //最大缩放比例 （可选）
     *                      "height-reference":0,                      //高程模型（可选） 0--绝对高度 1--贴地 2--相对于地形
     *                      "others":""                                //其它属性
     *                  }
     *              }]
      *         }
     */
    GeoJsonExtendDataSource.prototype.load = function(data, options) {
        //>>includeStart('debug', pragmas.debug);
        if (!Cesium.defined(data)) {
            throw new Cesium.DeveloperError('data is required.');
        }
        //>>includeEnd('debug');

        options = Cesium.defaultValue(options, Cesium.defaultValue.EMPTY_OBJECT);
        // TODO KQGIS
        // #region
        //基础路径
        if (typeof data == 'string')
            this._baseUri = Cesium.getBaseUri(data);
        if (Cesium.defined(options.name)) {
            this._name = options.name;
        }
        //#endregion

        Cesium.DataSource.setLoading(this, true);
        var promise = data;

        var sourceUri = options.sourceUri;
        if (typeof data === 'string') {
            if (!Cesium.defined(sourceUri)) {
                sourceUri = data;
            }
            promise = Cesium.loadJson(data);
        }

        var eyeOffset = options.eyeOffset;
        if (Cesium.defined(eyeOffset)) {
            if (eyeOffset instanceof Array) {
                eyeOffset = new Cesium.Cartesian3(eyeOffset[0] || 0, eyeOffset[1] || 0, eyeOffset[2] || 0);
            } else if (!eyeOffset instanceof Cesium.Cartesian3) {
                eyeOffset = undefined;
            }
        }
        var pixelOffset = options.pixelOffset;
        if (Cesium.defined(pixelOffset)) {
            if (pixelOffset instanceof Array) {
                pixelOffset = new Cesium.Cartesian2(pixelOffset[0] || 0, pixelOffset[1] || 0);
            } else if (!pixelOffset instanceof Cesium.Cartesian2) {
                pixelOffset = undefined;
            }
        }
        var alignedAxis = options.alignedAxis;
        if (Cesium.defined(alignedAxis)) {
            if (alignedAxis instanceof Array) {
                alignedAxis = new Cesium.Cartesian3(alignedAxis[0] || 0, alignedAxis[1] || 0, alignedAxis[2] || 0);
            } else if (!alignedAxis instanceof Cesium.Cartesian3) {
                alignedAxis = undefined;
            }
        }

        var distanceDisplayCondition = new Cesium.DistanceDisplayCondition(options.minimumVisibleDistance || 0, options.maximumVisibleDistance || Number.MAX_VALUE);

        var scaleByDistance = Cesium.defaultValue(options.scaleByDistance, undefined);
        if ((scaleByDistance instanceof Array) && scaleByDistance.length >= 4) {
            scaleByDistance = new Cesium.NearFarScalar(scaleByDistance[0], scaleByDistance[1], scaleByDistance[2], scaleByDistance[3]);
            scaleByDistance = new Cesium.ConstantProperty(scaleByDistance);
        }
        if (!(scaleByDistance instanceof Cesium.ConstantProperty))
            scaleByDistance = undefined;

        var translucencyByDistance = Cesium.defaultValue(options.translucencyByDistance, undefined);
        if ((translucencyByDistance instanceof Array) && translucencyByDistance.length >= 4) {
            translucencyByDistance = new Cesium.NearFarScalar(translucencyByDistance[0], translucencyByDistance[1],
                translucencyByDistance[2], translucencyByDistance[3]);
            translucencyByDistance = new Cesium.ConstantProperty(translucencyByDistance);
        }
        if (!(translucencyByDistance instanceof Cesium.ConstantProperty))
            translucencyByDistance = undefined;

        var pixelOffsetScaleByDistance = Cesium.defaultValue(options.pixelOffsetScaleByDistance, undefined);
        if ((pixelOffsetScaleByDistance instanceof Array) && pixelOffsetScaleByDistance.length >= 4) {
            pixelOffsetScaleByDistance = new Cesium.NearFarScalar(pixelOffsetScaleByDistance[0], pixelOffsetScaleByDistance[1],
                pixelOffsetScaleByDistance[2], pixelOffsetScaleByDistance[3]);
            pixelOffsetScaleByDistance = new Cesium.ConstantProperty(pixelOffsetScaleByDistance);
        }
        if (!(pixelOffsetScaleByDistance instanceof Cesium.ConstantProperty))
            pixelOffsetScaleByDistance = undefined;

        var width = options.width,
            height = options.height,
            pixelSize = options.pixelSize || options.markerSize || 5;

        var heightReference = Cesium.defaultValue(options.heightReference, Cesium.HeightReference.NONE);

        if (options.clampToGround) {
            heightReference = Cesium.HeightReference.CLAMP_TO_GROUND;
        } else if (options.relativeToGround) {
            heightReference = Cesium.HeightReference.RELATIVE_TO_GROUND;
        }

        //css颜色值转换成Color
        function cssColor2Color(color, defaultColor) {
            if (typeof color == 'string') {
                return Cesium.Color.fromCssColorString(color);
            } else if (color instanceof Cesium.Color) {
                return color;
            }

            return defaultColor;
        }

        //获取材质 颜色或贴图
        function getMaterialProperty(opts) {
            if (typeof opts !== 'string' || typeof opts !== 'object') {
                opts = Cesium.defaultValue(opts, Cesium.defaultValue.EMPTY_OBJECT);
                if (typeof opts == 'string') {
                    var color = opts;
                    opts = {};
                    opts.type = 'color';
                    opts.color = color;
                }
                if (opts.type == 'image') {
                    var url = opts.image,
                        color = cssColor2Color(opts.color, Cesium.Color.WHITE),
                        repeat = undefined,
                        transparent = Cesium.defaultValue(opts.transparent, true);

                    if (opts.repeat && opts.repeat instanceof Array) {
                        repeat = new Cesium.Cartesian2(opts.repeat[0] || 1.0, opts.repeat[1] || 1.0);
                    } else if (opts.repeat && typeof opts.repeat == 'number') {
                        repeat = new Cesium.Cartesian2(opts.repeat, opts.repeat);
                    }

                    return new Cesium.ImageMaterialProperty({
                        image: url,
                        color: color,
                        repeat: repeat,
                        transparent: transparent
                    });
                } else if (opts.type == 'color') {
                    return new Cesium.ColorMaterialProperty(cssColor2Color(opts.color, Cesium.Color.YELLOW));
                }
            }
            return new Cesium.ColorMaterialProperty(Cesium.Color.GREEN);
        }

        options = {
            describe: Cesium.defaultValue(options.describe, defaultDescribeProperty),
            //Point
            style: Cesium.defaultValue(options.style, undefined), //billboard\point
            /** Billboard **/
            image: options.image,
            scale: Cesium.defaultValue(options.scale, 1.0),
            horizontalOrigin: Cesium.defaultValue(options.horizontalOrigin, Cesium.HorizontalOrigin.CENTER),
            verticalOrigin: Cesium.defaultValue(options.verticalOrigin, Cesium.VerticalOrigin.BOTTOM),
            eyeOffset: Cesium.defaultValue(eyeOffset, Cesium.Cartesian3.ZERO),
            pixelOffset: Cesium.defaultValue(pixelOffset, Cesium.Cartesian2.ZERO),
            rotation: Cesium.defaultValue(options.rotation, 0),
            alignedAxis: Cesium.defaultValue(alignedAxis, Cesium.Cartesian3.ZERO),
            width: width,
            height: height,
            color: cssColor2Color(options.color, Cesium.Color.WHITE),
            scaleByDistance: Cesium.defaultValue(scaleByDistance, undefined),
            translucencyByDistance: Cesium.defaultValue(translucencyByDistance, undefined),
            pixelOffsetScaleByDistance: Cesium.defaultValue(pixelOffsetScaleByDistance, undefined),
            imageSubRegion: Cesium.defaultValue(options.imageSubRegion, undefined),
            sizeInMeters: Cesium.defaultValue(options.sizeInMeters, undefined),
            heightReference: heightReference,
            distanceDisplayCondition: distanceDisplayCondition,
            disableDepthTestDistance: Cesium.defaultValue(options.disableDepthTestDistance, undefined),
            /** Point */
            //color
            pixelSize: pixelSize,
            outlineColor: cssColor2Color(options.outlineColor, Cesium.Color.YELLOW),
            outlineWidth: Cesium.defaultValue(options.outlineWidth, 1),
            //scaleByDistance
            //translucencyByDistance
            //heightReference
            //distanceDisplayCondition
            //disableDepthTestDistance

            /** Polyline **/
            followSurface: Cesium.defaultValue(options.followSurface, undefined),
            //width
            material: getMaterialProperty(options.material),
            depthFailMaterial: Cesium.defined(options.depthFailMaterial) ? getMaterialProperty(options.depthFailMaterial) : undefined,
            granularity: Cesium.defaultValue(options.granularity, undefined),
            shadows: Cesium.defaultValue(options.shadows, Cesium.ShadowMode.DISABLED),
            //distanceDisplayCondition

            /** Corridor **/
            //width
            cornerType: Cesium.defaultValue(options.cornerType, Cesium.CornerType.ROUNDED),
            //height
            extrudedHeight: Cesium.defaultValue(options.extrudedHeight, undefined),
            fill: Cesium.defaultValue(options.fill, true),
            //material
            outline: options.outline,
            //outlineColor:cssColor2Color(options.outlineColor,Cesium.Color.BLACK),
            //outlineWidth:Cesium.defaultValue(options.outlineWidth,1.0),
            //granularity
            //shadows
            //distanceDisplayCondition

            /** Polygon **/
            //height
            //extrudedHeight
            //fill
            //material
            //outline
            //outlineColor
            //outlineWidth
            stRotation: Cesium.defaultValue(options.stRotation, 0),
            //granularity
            perPositionHeight: Cesium.defaultValue(options.perPositionHeight, false),
            closeTop: Cesium.defaultValue(options.closeTop, true),
            closeBottom: Cesium.defaultValue(options.closeBottom, true),
            //shadows
            // distanceDisplayCondition
            /** Model **/
            //scale
            minimumPixelSize: Cesium.defaultValue(options.minimumPixelSize, 0),
            maximumScale: Cesium.defaultValue(options.maximumScale, undefined),
            incrementallyLoadTextures: Cesium.defaultValue(options.incrementallyLoadTextures, undefined),
            runAnimations: Cesium.defaultValue(options.runAnimations, true),
            clampAnimations: Cesium.defaultValue(options.clampAnimations, true),

            //nodeTransformations
            //shadows
            //heightReference
            //distanceDisplayCondition
            silhouetteColor: cssColor2Color(options.silhouetteColor, Cesium.Color.RED),
            silhouetteSize: Cesium.defaultValue(options.silhouetteSize, 0),
            //color
            colorBlendMode: Cesium.defaultValue(options.colorBlendMode, Cesium.ColorBlendMode.HIGHLIGHT),
            colorBlendAmount: Cesium.defaultValue(options.colorBlendAmount, 0.5),

            markerSize: Cesium.defaultValue(options.markerSize, defaultMarkerSize),
            markerSymbol: Cesium.defaultValue(options.markerSymbol, defaultMarkerSymbol),
            markerColor: Cesium.defaultValue(options.markerColor, defaultMarkerColor),
            //strokeWidthProperty: new Cesium.ConstantProperty(Cesium.defaultValue(options.strokeWidth, defaultStrokeWidth)),
            //strokeMaterialProperty: new Cesium.ColorMaterialProperty(Cesium.defaultValue(options.stroke, defaultStroke)),
            //fillMaterialProperty: new Cesium.ColorMaterialProperty(Cesium.defaultValue(options.fill, defaultFill)),
            clampToGround: Cesium.defaultValue(options.clampToGround, defaultClampToGround),
            relativeToGround: Cesium.defaultValue(options.relativeToGround, defaultRelativeToGround),

            point: options.point,
            billboard: options.billboard,
            label: options.label,
            corridor: options.corridor,
            polyline: options.polyline,
            polygon: options.polygon
        };


        var that = this;
        return Cesium.when(promise, function(geoJson) {
            return load(that, geoJson, options, sourceUri);
        }).otherwise(function(error) {
            Cesium.DataSource.setLoading(that, false);
            that._error.raiseEvent(that, error);
            console.log(error);
            return Cesium.when.reject(error);
        });
    };

    function load(that, geoJson, options, sourceUri) {
        var name;
        if (Cesium.defined(sourceUri) && !Cesium.defined(that._name)) {
            name = Cesium.getFilenameFromUri(sourceUri);
        }

        if (Cesium.defined(name) && that._name !== name) {
            that._name = name;
            that._changed.raiseEvent(that);
        }

        var typeHandler = geoJsonObjectTypes[geoJson.type];
        if (!Cesium.defined(typeHandler)) {
            throw new Cesium.RuntimeError('Unsupported GeoJSON object type: ' + geoJson.type);
        }

        //Check for a Coordinate Reference System.
        var crs = geoJson.crs;
        var crsFunction = crs !== null ? defaultCrsFunction : null;

        if (Cesium.defined(crs)) {
            if (!Cesium.defined(crs.properties)) {
                throw new Cesium.RuntimeError('crs.properties is undefined.');
            }

            var properties = crs.properties;
            if (crs.type === 'name') {
                crsFunction = crsNames[properties.name];
                if (!Cesium.defined(crsFunction)) {
                    throw new Cesium.RuntimeError('Unknown crs name: ' + properties.name);
                }
            } else if (crs.type === 'link') {
                var handler = crsLinkHrefs[properties.href];
                if (!Cesium.defined(handler)) {
                    handler = crsLinkTypes[properties.type];
                }

                if (!Cesium.defined(handler)) {
                    throw new Cesium.RuntimeError('Unable to resolve crs link: ' + JSON.stringify(properties));
                }

                crsFunction = handler(properties);
            } else if (crs.type === 'EPSG') {
                crsFunction = crsNames['EPSG:' + properties.code];
                if (!Cesium.defined(crsFunction)) {
                    throw new Cesium.RuntimeError('Unknown crs EPSG code: ' + properties.code);
                }
            } else {
                throw new Cesium.RuntimeError('Unknown crs type: ' + crs.type);
            }
        }

        return Cesium.when(crsFunction, function(crsFunction) {
            that._entityCollection.removeAll();

            // null is a valid value for the crs, but means the entire load process becomes a no-op
            // because we can't assume anything about the coordinates.
            if (crsFunction !== null) {
                typeHandler(that, geoJson, geoJson, crsFunction, options);
            }

            return Cesium.when.all(that._promises, function() {
                that._promises.length = 0;
                Cesium.DataSource.setLoading(that, false);
                return that;
            });
        });
    }

    /**
     * This callback is displayed as part of the GeoJsonExtendDataSource class.
     * @callback GeoJsonExtendDataSource~describe
     * @param {Object} properties The properties of the feature.
     * @param {String} nameProperty The property key that Cesium estimates to have the name of the feature.
     */


    /**
     * @private
     * 自动避让
     */
    GeoJsonExtendDataSource.prototype.autoAvoid = function(viewer) {
        var that = this;
        if (!Cesium.defined(viewer))
            return false;
        that._viewer = viewer;
        viewer.scene.postRender.addEventListener(function() {
            that.postRender();
        });
    };
    /**
     * 实时计算标注遮挡
     */
    GeoJsonExtendDataSource.prototype.postRender = function() {
        var that = this;
        if (!that.show) {
            return;
        }
        var showItems = [];
        var entities = that.entities._entities;
        for (var i = 0; i < entities.length; ++i) {
            var entitiy = entities.values[i];
            if (!entitiy.label) continue;
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
     *@private
     *两个实体标注碰撞检测 
     */
    GeoJsonExtendDataSource.prototype.isCollsionWithRect = function(entitiy1, entitiy2) {
        var b1 = Cesium.SceneTransforms.wgs84ToWindowCoordinates(this._viewer.scene, entitiy1.position._value);
        var b2 = Cesium.SceneTransforms.wgs84ToWindowCoordinates(this._viewer.scene, entitiy2.position._value);
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
    GeoJsonExtendDataSource.prototype.getLabelSize = function(label) {
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


    Cesium.GeoJsonExtendDataSource = GeoJsonExtendDataSource;
})();