(function() {

    var ellipsoidGeodesic;

    function getEllipsoidGeodesic() {
        if (typeof ellipsoidGeodesic === 'undefined') {
            ellipsoidGeodesic = new Cesium.EllipsoidGeodesic();
        }

        return ellipsoidGeodesic;
    }

    function heightFunction(ratio, distance) {
        var hr = distance / 30000000;
        hr = Math.min(1.0, hr);
        hr = 1.0 - Math.pow(1.0 - hr, 2.0);
        return Math.sin(Math.PI * ratio) * 1000000 * hr;
    }

    function createTransmitPolyline(startPosition, endPosition) {
        var ellipsoidGeodesic = getEllipsoidGeodesic();

        var rawPositions = Cesium.Cartesian3.fromDegreesArray([startPosition[0], startPosition[1],
            endPosition[0], endPosition[1]
        ]);

        var heights = [0, 0];
        if (typeof startPosition[2] !== 'undefined' &&
            typeof endPosition[2] !== 'undefined') {
            heights = [startPosition[2], endPosition[2]];
        }

        var positions = Cesium.PolylinePipeline.generateCartesianArc({
            positions: rawPositions,
            height: heights
        });

        var start = Cesium.Cartographic.fromDegrees(startPosition[0], startPosition[1]);
        var end = Cesium.Cartographic.fromDegrees(endPosition[0], endPosition[1]);
        ellipsoidGeodesic.setEndPoints(start, end);

        var distance = ellipsoidGeodesic.surfaceDistance;

        var length = positions.length;
        positions.forEach(function(position, index) {
            var ratio = index / length;
            var h = heightFunction(ratio, distance);
            var cart = Cesium.Cartographic.fromCartesian(position);
            cart.height += h;
            Cesium.Cartesian3.fromRadians(cart.longitude, cart.latitude, cart.height, Cesium.Ellipsoid.WGS84, positions[index]);
        });

        return positions;
    }

    function createODLineApperance(color) {
        if (!Cesium.defined(color))
            color = new Cesium.Color(0.8, 0.8, 0.0, 1.0);

        var polylineAppearance = new Cesium.PolylineMaterialAppearance({
            material: new Cesium.Material({
                fabric: {
                    type: 'Color',
                    uniforms: {
                        color: color //new Cesium.Color(0.8, 0.8, 0.0, 1.0)
                    }
                },
                translucent: true
            })
        });

        var renamedVS = Cesium.ShaderSource.replaceMain(polylineAppearance._vertexShaderSource, 'czm_twp_main');
        var twpMain =
            'varying vec4 v_twp; \n' +
            'void main() \n' +
            '{ \n' +
            '    czm_twp_main(); \n' +
            '    v_twp = czm_batchTable_twp(batchId); \n' +
            '}';

        polylineAppearance._vertexShaderSource = renamedVS + '\n' + twpMain;

        var renamedFS = Cesium.ShaderSource.replaceMain(polylineAppearance._fragmentShaderSource, 'czm_twp2_main');
        var twp2Main =
            'varying vec4 v_twp; \n' +
            'void main() \n' +
            '{ \n' +
            '    czm_twp2_main(); \n' +
            '    float t = v_twp.x;' +
            '    t *= 1.03;' +
            '    float alpha = smoothstep(t - 0.03, t, v_st.s) * step(-t, -v_st.s); ' +
            //'    alpha += 0.2;' +
            '    alpha += 0.4;' +
            //'    alpha *= step(-0.1, -abs(0.5-v_st.t));  ' +
            '    gl_FragColor.a *= alpha;' +
            '}';
        polylineAppearance._fragmentShaderSource = renamedFS + '\n' + twp2Main;

        return polylineAppearance;
    }

    function createODLineGeometryInstance(positions, width) {
        var gi = new Cesium.GeometryInstance({
            geometry: new Cesium.PolylineGeometry({
                positions: positions,
                width: width, //3.0,
                vertexFormat: Cesium.PolylineMaterialAppearance.VERTEX_FORMAT
            }),
            attributes: {
                //color : Cesium.ColorGeometryInstanceAttribute.fromColor(Cesium.Color.fromRandom()),
                twp: new Cesium.GeometryInstanceAttribute({
                    componentDatatype: Cesium.ComponentDatatype.FLOAT,
                    componentsPerAttribute: 4,
                    normalize: false,
                    value: [0.0, 0.5, 1.0, 1.0]
                })
            },
        });

        return gi;
    }

    var scratchTWP = new Cesium.Cartesian4(0.0, 0.0, 0.0, 0.0);

    /**
     * OD线回调函数，用来指定OD线的进度
     * @callback Kq3dODLinesPostionCallback
     * @exports Kq3dODLinesPostionCallback
     *
     * @param {number} instanceIndex OD线的索引
     * @param {Cesium.FrameState} frameState 传递的是viewer.scene.frameState
     * @returns {number} 返回OD线的进度位置
     *
     * @example
     * function (instanceIndex, frameState) {
     *     var st = routePaths[instanceIndex].startTime;
     *     var dr = routePaths[instanceIndex].duration;
     *     var diff = time > st ? time - st : time + timeDuration - st;
     *     var timeRatio = Math.min(diff / dr, 1.0);
     *     return timeRatio;
     * }
     */

    /**
     * 创建OD线
     * @exports Kq3dCreateODLinesPrimitive
     * @param {*} routePaths 用来存储OD线的数据点，有两种形式：
     * @param {DOUBLE} width  OD线的宽度：
     * @param {Kq3dODLinesPostionCallback} postionCallback 用来实时修改OD线的进度位置
     * @returns {Cesium.Primitive} 返回创建好的od线
     * @example
     * routePaths数据的两种存储形式如下：
     *  {
     *      positions: points,
     *  }
     * 
     *  {
     *      startPos: airports[route[1]].coord,
     *      endPos: airports[route[2]].coord,
     *  }
     */
    function Kq3dCreateODLinesPrimitive(routePaths, width, color, postionCallback) {
        var geometryInstances = [];
        routePaths.forEach(function(route) {
            var positions = route.positions || createTransmitPolyline(route.startPos, route.endPos);
            var gi = createODLineGeometryInstance(positions, width);
            geometryInstances.push(gi);
        });

        var odLinesPrimitive = new Cesium.Primitive({
            geometryInstances: geometryInstances,
            appearance: createODLineApperance(color)
        });

        odLinesPrimitive.update = function(frameState) {
            const primitive = this;

            if (typeof primitive._batchTable !== 'undefined') {
                var attributeName = 'twp';
                var attributeIndex = primitive._batchTableAttributeIndices[attributeName];
                var ni = primitive._batchTable._numberOfInstances;
                for (var i = 0; i < ni; ++i) {
                    var instanceIndex = i;
                    scratchTWP.x = postionCallback(instanceIndex, frameState);
                    primitive._batchTable.setBatchedAttribute(instanceIndex, attributeIndex, scratchTWP);
                }
            }

            Cesium.Primitive.prototype.update.bind(primitive)(frameState);
        }

        return odLinesPrimitive;
    }

    Cesium.Kq3dCreateODLinesPrimitive = Kq3dCreateODLinesPrimitive;
})();