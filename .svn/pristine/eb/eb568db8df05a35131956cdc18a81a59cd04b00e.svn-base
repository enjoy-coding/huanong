featherCmsScript.register({
    element: "#cesiumContainer",
    onLoad: function (cmsOptions) {
        var mapObject = {
            isPCBrowser: Cesium.FeatureDetection.isPCBrowser(),
            viewer: null,
            tileset: null,
            model: null,
            geometryEntityArr: [],
            index: 0,
            type: '',    //加载设备的类型
            typeObject: '',  //加载设备的页面
            result: [],//存储重点人员的楼栋信息
            position: {},//存储点击的坐标
            geoJson: [],//存储图标点结果数组
            geoPointArr: [],//存储点状名称数组
            geoPlygonArr: [],//存储小区面状结果数组
            fwid:'',
            zbInfo:[],
            //边界渲染
            renderP: function (url, col) {
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        for (var i = 0; i < data.features.length; i++) {
                            mapObject.geoPlygonArr.push(data.features[i]);
                            var height = data.features[i].properties.Z;
                            var points = [];
                            for (var coord of data.features[i].geometry.coordinates[0]) {
                                points.push(coord[0], coord[1], height + 5);
                            }
                            color = Cesium.Color.fromCssColorString(col);
                            mapObject.createWall(data.features[i], color, height)
                        }
                    }
                });
            },
            //加载对应设备的图标
            addLayerPic: function (type, typeObject) {
                mapObject.type = type;
                mapObject.typeObject = typeObject;
                var value = $('#treeName').attr('value');
                var url = featherCmsScript._cfg.cfg[type].url;
                var img = featherCmsScript._cfg.cfg[type].img;
                var id = featherCmsScript._cfg.cfg[type].id;
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        //先清除之前的图标
                        mapObject.clearPic();
                        mapObject.removeLocatedBuilding();
                        mapObject.geoJson = data.features;
                        for (var i = 0; i < data.features.length; i++) {
                            if (data.features[i].properties['XQID'] == value || data.features[i].properties['SQID'] == value) {
                                var surfacePosition = Cesium.Cartesian3.fromDegrees(
                                    data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], 18
                                );
                                var heightPosition = Cesium.Cartesian3.fromDegrees(
                                    data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], 49
                                );
                                if (type == 'RY') {
                                    for (var j = 0; j < mapObject.result.length; j++) {
                                        if (mapObject.result[j].LDID == data.features[i].properties['LDID']) {
                                            var citizensBankPark = mapObject.viewer.entities.add({
                                                code: data.features[i].properties[id],
                                                position: heightPosition,
                                                billboard: { //图标
                                                    image: featherCmsScript.ctx + img,
                                                    width: 48,
                                                    height: 48,
                                                    scale: 1, //和原始大小相比的缩放比例
                                                    minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                                    distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                                    verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                                },
                                                polyline: {
                                                    positions: new Cesium.ConstantProperty([
                                                        surfacePosition,
                                                        heightPosition,
                                                    ]),
                                                    width: 2,
                                                    material: Cesium.Color.fromCssColorString('#4BE1EE')
                                                },
                                                label: { //文字标签
                                                    text: mapObject.result[j].LDMC + '(重点' + mapObject.result[j].MUN + '人)',
                                                    font: '12px 微软雅黑',
                                                    style: Cesium.LabelStyle.FILL_AND_OUTLINE,
                                                    fillColor: Cesium.Color.AQUA,
                                                    outlineWidth: 2,
                                                    verticalOrigin: Cesium.VerticalOrigin.BOTTOM, //垂直方向以底部来计算标签的位置
                                                    pixelOffset: new Cesium.Cartesian2(0, -36),  //偏移量
                                                    disableDepthTestDistance: 350000,
                                                    distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 1000),
                                                    scaleByDistance: new Cesium.NearFarScalar(200000, 1.2, 100000000000, 0.5)
                                                }
                                            });
                                            mapObject.geometryEntityArr.push(citizensBankPark);
                                            mapObject.viewer.flyTo(mapObject.viewer.entities, {
                                                offset: {
                                                    heading: Cesium.Math.toRadians(0.0),
                                                    pitch: Cesium.Math.toRadians(-30),
                                                    range: 1000
                                                }
                                            });
                                        }
                                    }
                                } else if(type == 'TCC'){
                                    var citizensBankPark = mapObject.viewer.entities.add({
                                        code: data.features[i].properties[id],
                                        tcclx:data.features[i].properties['TCCLX'],
                                        position: heightPosition,
                                        polyline: {
                                            positions: new Cesium.ConstantProperty([
                                                surfacePosition,
                                                heightPosition,
                                            ]),
                                            width: 2,
                                            material: Cesium.Color.fromCssColorString('#4BE1EE')
                                        },
                                        billboard: { //图标
                                            image: featherCmsScript.ctx + img,
                                            width: 48,
                                            height: 48,
                                            scale: 1, //和原始大小相比的缩放比例
                                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                        }
                                    });
                                    mapObject.geometryEntityArr.push(citizensBankPark);
                                    mapObject.viewer.flyTo(mapObject.viewer.entities, {
                                        offset: {
                                            heading: Cesium.Math.toRadians(0),
                                            pitch: Cesium.Math.toRadians(-30),
                                            range: 1000
                                        }
                                    });
                                }else {
                                    var citizensBankPark = mapObject.viewer.entities.add({
                                        code: data.features[i].properties[id],
                                        position: heightPosition,
                                        polyline: {
                                            positions: new Cesium.ConstantProperty([
                                                surfacePosition,
                                                heightPosition,
                                            ]),
                                            width: 2,
                                            material: Cesium.Color.fromCssColorString('#4BE1EE')
                                            /*material : new Cesium.PolylineGlowMaterialProperty({
                                                glowPower : 1.2,
                                                taperPower:1.2,
                                                color : Cesium.Color.fromCssColorString('#4BE1EE').withAlpha(0.8),
                                            })*/
                                        },
                                        billboard: { //图标
                                            image: featherCmsScript.ctx + img,
                                            width: 48,
                                            height: 48,
                                            scale: 1, //和原始大小相比的缩放比例
                                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                        }
                                    });
                                    mapObject.geometryEntityArr.push(citizensBankPark);
                                    mapObject.viewer.flyTo(mapObject.viewer.entities, {
                                        offset: {
                                            heading: Cesium.Math.toRadians(0),
                                            pitch: Cesium.Math.toRadians(-30),
                                            range: 1000
                                        }
                                    });
                                }
                            }
                        }
                        mapObject.viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                            var scene = mapObject.viewer.scene;
                            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                                var pickedObject = scene.pick(movement.position);

                                var position = mapObject.viewer.scene.pickPosition(movement.position); //单击位置
                                //将笛卡尔坐标转化为经纬度坐标
                                var cartographic = Cesium.Cartographic.fromCartesian(position);
                                var longitude = Cesium.Math.toDegrees(cartographic.longitude);//获取经度
                                var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                                mapObject.position = {x: longitude, y: latitude};
                                if (pickedObject && pickedObject.id && pickedObject.id._code) {
                                    if(type == 'TCC'){
                                        var tccIdLx = pickedObject.id._code +','+ pickedObject.id._tcclx;
                                        sqglCw.getTccXxInfo(tccIdLx);
                                        mapObject.localPosition(pickedObject.id._code, type);
                                        mapObject.replacePic(img, pickedObject.id._code);
                                    }else{
                                        mapObject.clickPic(img, pickedObject.id._code, type, typeObject);
                                    }
                                }
                            }
                        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
                    }
                });
            },
            //图标点击事件
            clickPic: function (img, id, type, typeObject) {
                var url;
                if (type == 'FW') {
                    url = featherCmsScript.ctx + 'screen/api/getRyXx?ldid=' + id;
                } else if (type == 'RY') {
                    url = featherCmsScript.ctx + 'screen/api/getZdRy?ldid=' + id;
                } else {
                    url = featherCmsScript.ctx + 'screen/api/getSbJc?type=' + type + '&id=' + id;
                }
                $.ajax({
                    "async": false,
                    "url": url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        if (typeObject == 'index') {
                            mapObject.layerOpenInfo(type, data);
                        } else if (typeObject == 'znaf') {
                            mjBox(type, id);
                        }else if (typeObject == 'sqgl') {
                            if(type == 'FW'){
                                mapObject.layerOpenInfo(type, data);
                            }else if(type == 'RY'){
                                getFwTk('',mapObject.fwid,'')
                            }
                        }else if(typeObject == 'zhzl'){
                            getZdSjTk(id);
                        } else {

                        }

                    }
                });
                mapObject.localPosition(id, type);
                mapObject.replacePic(img, id);
            },
            //替换选中图片
            replacePic: function (img, id) {
                for (var i in mapObject.geometryEntityArr) {
                    var imgArr = img.split('.');
                    if (mapObject.geometryEntityArr[i]._code == id) {
                        mapObject.geometryEntityArr[i]._billboard._image._value = featherCmsScript.ctx + imgArr[0] + '-selected.png';
                    } else {
                        mapObject.geometryEntityArr[i]._billboard._image._value = featherCmsScript.ctx + img;
                    }
                }
            },
            //全图
            full: function () {
                mapObject.viewer.scene.camera.flyTo({
                    destination: new Cesium.Cartesian3.fromDegrees(121.34612, 31.14985, 500),
                    orientation: {
                        heading: Cesium.Math.toRadians(0),
                        pitch: Cesium.Math.toRadians(-30.0),
                        roll: Cesium.Math.toRadians(0),
                    },
                    duration: 3.0
                });
            },
            //清除图标
            clearPic: function () {
                if (mapObject.geometryEntityArr.length > 0) {
                    for (var i in mapObject.geometryEntityArr) {
                        mapObject.viewer.entities.remove(mapObject.geometryEntityArr[i]);
                    }
                    mapObject.geometryEntityArr = [];
                }
                layer.closeAll();
                mapObject.removeLocatedBuilding();
                mapObject.viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
            },
            createWall: function (polygon, color, height) {
                var origin = turf.centroid(polygon).geometry.coordinates; //获取polygon的质心
                var bigPolygon = turf.transformScale(polygon, 1000, {
                    origin: origin
                }); //按照质心扩大多少倍
                var line = turf.polygonToLine(bigPolygon); //把面转化为线
                var wall = turf.buffer(line, 2); //创建缓冲区--变回面
                wall = turf.intersect(wall, bigPolygon) //返回相交外面是大的面，里面是小的面
                if (wall == null) return;
                wall = turf.transformScale(wall, 0.001, {
                    origin: origin
                });
                var polygonHierarchy = mapObject.createPolygonHierarchy(wall.geometry.coordinates); //获取挖空的面
                var entity = mapObject.viewer.entities.add({
                    polygon: {
                        hierarchy: polygonHierarchy,
                        height: height,
                        resolution: 1,
                        extrudedHeight: height + 0.5,
                        shadows: Cesium.ShadowMode.ENABLED,
                        material: color.withAlpha(1)
                    }
                }); //创建墙体，也是面
            },
            createPolygonHierarchy: function (coordinates) {
                var holes = [];
                for (var i = 1, len = coordinates.length; i < len; i++) {
                    holes.push(new Cesium.PolygonHierarchy(mapObject.coordinatesArrayToCartesianArray(coordinates[i], mapObject.crsFunction)));
                } //挖洞
                var positions = coordinates[0];
                var polygonHierarchy = new Cesium.PolygonHierarchy(mapObject.coordinatesArrayToCartesianArray(positions, mapObject.crsFunction), holes);
                return polygonHierarchy;
            },
            crsFunction: function (coordinates) {
                return Cesium.Cartesian3.fromDegrees(coordinates[0], coordinates[1], coordinates[2]);
            },
            coordinatesArrayToCartesianArray: function (coordinates, crsFunction) {
                var positions = new Array(coordinates.length);
                for (var i = 0; i < coordinates.length; i++) {
                    positions[i] = mapObject.crsFunction(coordinates[i]);
                }
                return positions;
            },
            //打开详细信息
            layerOpenInfo: function (type, data) {
                var html = '',
                    html1 = '',
                    area;
                if (type == 'SXT') {
                    $('#cameraLayer').html('');
                    html = `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                     <div class="layerContent">
		                     <div class="layui-row mw_10">
		                         <div class="layui-col-md8 layui-col-xs8">
		                           <span class="color_blue font_14 mr_10">位置:</span>
		                           <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                         </div>
		                         <div class="layui-col-md4 layui-col-xs4 txtR">
		                           <span class="color_blue font_14 mr_10">状态:</span>
		                           <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                         </div>
		                     </div>
		                     <div  class="cameraImg">
		                          <img src="images/video_img1.png" alt="" />
		                     </div> 
		            </div>`;
                    html1 = $('#cameraLayer').html(html);
                } else if (type == 'MJ') {
                    $('#AccessControlLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                        <div class="layui-row mw_10">
		                            <div class="layui-col-md8 layui-col-xs8">
		                              <span class="color_blue font_14 mr_10">位置:</span>
		                              <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                            </div>
		                            <div class="layui-col-md4 layui-col-xs4 txtR">
		                              <span class="color_blue font_14 mr_10">状态:</span>
		                              <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                            </div>
		                        </div>
		                        <div id="GateWiperWrapper" class="swiperWrapper">
		                            <ul class="vehicleList swiper-wrapper">`;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<li class="swiper-slide">
		                                            <img class="img" src="images/video_img1.png" alt="" />
		                                            <p>
		                                                <span class="color_blue">姓名：</span>
		                                                <span class="color_white">` + data.data[i].XM + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">性别：</span>
		                                                <span class="color_white">` + data.data[i].XB + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">人员类型：</span>
		                                                <span class="color_white">` + data.data[i].RYLX + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">进入类型：</span>
		                                                <span class="color_white">` + data.data[i].JCZT + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">时间：</span>
		                                                <span class="color_white">` + data.data[i].JCSJ + `</span>
		                                            </p>
		                                        </li>`;
                    }
                    html += `</ul>
		                                <div class="swiper-button-next"></div>
		                                <div class="swiper-button-prev"></div>
		                            </div>
		                    </div>`;
                    html1 = $('#AccessControlLayer').html(html);
                } else if (type == 'ZJ') {
                    $('#GateLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                    <div class="layui-row mw_10">
		                        <div class="layui-col-md8 layui-col-xs8">
		                          <span class="color_blue font_14 mr_10">位置:</span>
		                          <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                        </div>
		                        <div class="layui-col-md4 layui-col-xs4 txtR">
		                          <span class="color_blue font_14 mr_10">状态:</span>
		                          <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                        </div>
		                    </div>
		                    <div id="ZJPoleswiperWrapper" class="swiperWrapper">
		                        <ul class="vehicleList swiper-wrapper">`;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<li class="swiper-slide">
		                                            <img class="img" src="images/video_img1.png" alt="" />
		                                            <p>
		                                                <span class="color_blue">姓名：</span>
		                                                <span class="color_white">` + data.data[i].XM + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">性别：</span>
		                                                <span class="color_white">` + data.data[i].XB + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">人员类型：</span>
		                                                <span class="color_white">` + data.data[i].RYLX + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">进入类型：</span>
		                                                <span class="color_white">` + data.data[i].JCZT + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">时间：</span>
		                                                <span class="color_white">` + data.data[i].JCSJ + `</span>
		                                            </p>
		                                        </li>`;
                    }
                    html += `</ul>
		                        <div class="swiper-button-next"></div>
		                        <div class="swiper-button-prev"></div>
		                    </div>
		                </div>`;
                    html1 = $('#GateLayer').html(html);
                } else if (type == 'DG') {
                    $('#RoadPoleLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                    <div class="layui-row mw_10">
		                        <div class="layui-col-md8 layui-col-xs8">
		                          <span class="color_blue font_14 mr_10">位置:</span>
		                          <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                        </div>
		                        <div class="layui-col-md4 layui-col-xs4 txtR">
		                          <span class="color_blue font_14 mr_10">状态:</span>
		                          <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                        </div>
		                    </div>
		                    <div id="RoadPoleswiperWrapper" class="swiperWrapper">
		                        <ul class="vehicleList swiper-wrapper">`;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<li class="swiper-slide">
		                                            <img class="img" src="img/dg.jpg" alt="" />
		                                            <p>
		                                                <span class="color_blue">车牌号：</span>
		                                                <span class="color_white">` + data.data[i].CPHM + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">车主性别：</span>
		                                                <span class="color_white">` + data.data[i].XB + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">车辆类型：</span>
		                                                <span class="color_white">` + data.data[i].CX + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">进出类型：</span>
		                                                <span class="color_white">` + data.data[i].JCZT + `</span>
		                                            </p>
		                                            <p>
		                                                <span class="color_blue">时间：</span>
		                                                <span class="color_white">` + data.data[i].JCSJ + `</span>
		                                            </p>
		                                        </li>`;
                    }
                    html += `</ul>
		                        <div class="swiper-button-next"></div>
		                        <div class="swiper-button-prev"></div>
		                        </div>
		                </div>`;
                    html1 = $('#RoadPoleLayer').html(html);
                } else if (type == 'LJT') {
                    $("#ashbinLayer").html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
					<div class="layerContent">
						<div class="layui-row mw_10">
						    <div class="layui-col-md8 layui-col-xs8">
						      	<span class="color_blue font_14 mr_10">位置:</span>
						      	<span class="color_white font_14">` + data.data[0].WZ + `</span>
						    </div>
						    <div class="layui-col-md4 layui-col-xs4 txtR">
						      	<span class="color_blue font_14 mr_10">状态:</span>
						      	<span class="color_white font_14">` + data.data[0].SBZT + `</span>
						    </div>
						</div>
		                <ul class="vehicleList swiper-wrapper">`;
                    for (var i in data.data) {
                        html += `<li>
								<img class="img" src="images/ljt.jpg" alt="" />
								<p><span class="color_blue">垃圾类型：</span><span class="color_white">` + data.data[i].LJLX + `</span></p>
								<p><span class="color_blue">满载状态：</span><span class="color_white">` + data.data[i].MZZT + `</span></p>
								<p><span class="color_blue">温度：</span><span class="color_white">` + data.data[i].WD + `</span></p>
								<p><span class="color_blue">容量：</span><span class="color_white">` + data.data[i].SYRL + `</span></p>
								<p><span class="color_blue">重量：</span><span class="color_white">` + data.data[i].ZL + `</span></p>
							</li>`;
                    }
                    html += `</ul>
					    </div>`;
                    $("#ashbinLayer").html(html);
                    html1 = $("#ashbinLayer");
                } else if (type == 'YG') {
                    $("#smokeLayer").html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                        <div class="layui-row mw_10">
		                            <div class="layui-col-md8 layui-col-xs8">
		                              <span class="color_blue font_14 mr_10">位置:</span>
		                              <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                            </div>
		                            <div class="layui-col-md4 layui-col-xs4 txtR">
		                              <span class="color_blue font_14 mr_10">状态:</span>
		                              <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                            </div>
		                        </div>
		                        <ul class="vehicleList swiper-wrapper">
		                            <li>
		                                <img class="img" src="images/yg.jpg" alt="" />
		                                <p><span class="color_blue">烟感深度：</span><span class="color_white">` + data.data[0].WD + `</span></p>
		                            </li>
		                        </ul>
		                    </div>`;
                    $('#smokeLayer').html(html);
                    html1 = $('#smokeLayer');
                } else if (type == 'CDZ') {
                    $('#chargingPileLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                        <div class="layui-row mw_10">
		                            <div class="layui-col-md8 layui-col-xs8">
		                              <span class="color_blue font_14 mr_10">位置:</span>
		                              <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                            </div>
		                            <div class="layui-col-md4 layui-col-xs4 txtR">
		                              <span class="color_blue font_14 mr_10">状态:</span>
		                              <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                           </div>
		                        </div>
		                        <ul class="vehicleList swiper-wrapper">
		                            <li>
		                                <img class="img" src="images/cdz.jpg" alt="" />
		                                <p><span class="color_blue">剩余容量：</span><span class="color_white">` + data.data[0].SYRL + `</span></p>
		                                <p><span class="color_blue">单体电压：</span><span class="color_white">` + data.data[0].DTDY + `</span></p>
		                                <p><span class="color_blue">温度：</span><span class="color_white">` + data.data[0].WD + `</span></p>
		                            </li>
		                        </ul>
		                    </div>`;
                    $('#chargingPileLayer').html(html);
                    html1 = $('#chargingPileLayer');
                } else if (type == 'LD') {
                    $('#streetLampLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                        <div class="layui-row mw_10">
		                            <div class="layui-col-md8 layui-col-xs8">
		                              <span class="color_blue font_14 mr_10">位置:</span>
		                              <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                           </div>
		                            <div class="layui-col-md4 layui-col-xs4 txtR">
		                              <span class="color_blue font_14 mr_10">状态:</span>
		                              <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                            </div>
		                        </div>
		                        <ul class="vehicleList swiper-wrapper">
		                            <li>
		                                <img class="img" src="images/ld.jpg" alt="" />
		                                <p><span class="color_blue">亮度：</span><span class="color_white">` + data.data[0].LD + `</span></p>
		                                <p><span class="color_blue">电流：</span><span class="color_white">` + data.data[0].DL + `</span></p>
		                                <p><span class="color_blue">电压：</span><span class="color_white">` + data.data[0].DY + `</span></p>
		                                <p><span class="color_blue">功率：</span><span class="color_white">` + data.data[0].GL + `</span></p>
		                                <p><span class="color_blue">灯泡寿命：</span><span class="color_white">` + data.data[0].DPSM + `</span></p>
		                                <p><span class="color_blue">亮灯时间：</span><span class="color_white">18:00-07:00</span></p>
		                            </li>
		                        </ul>
		                    </div>`;
                    $('#streetLampLayer').html(html);
                    html1 = $('#streetLampLayer');
                } else if (type == 'XFS') {
                    $('#fireFightingLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data[0].SBMC + `</h4>
		                    <div class="layerContent">
		                        <div class="layui-row mw_10">
		                            <div class="layui-col-md8 layui-col-xs8">
		                                <span class="color_blue font_14 mr_10">位置:</span>
		                                <span class="color_white font_14">` + data.data[0].WZ + `</span>
		                            </div>
		                            <div class="layui-col-md4 layui-col-xs4 txtR">
		                                  <span class="color_blue font_14 mr_10">状态:</span>
		                                 <span class="color_white font_14">` + data.data[0].SBZT + `</span>
		                            </div>
		                        </div>
		                        <ul class="vehicleList swiper-wrapper">
		                            <li>
		                                <img class="img" src="images/xfs.jpg" alt="" />
		                                <p><span class="color_blue">消防水位：</span><span class="color_white">` + data.data[0].SW + `</span></p>
		                                <p><span class="color_blue">消防水压：</span><span class="color_white">` + data.data[0].SY + `</span></p>
		                            </li>
		                        </ul>
		                    </div>`;
                    $('#fireFightingLayer').html(html);
                    html1 = $('#fireFightingLayer');
                } else if (type == 'YC') {
                    $('#abnormalLayer').html('');
                    html += `<h4 class="moveTxt">` + data.data.ycly + `</h4>
		                    <div class="layerContent">
		                        <div class="abnormalForm">
		                            <ul>
		                                <li><label class="color_blue">异常内容：</label><span>` + data.data.ycnr + `</span></li>
		                                <li><label class="color_blue">异常级别：</label><span class="color_red"><i class="iconfont iconbaojing"></i>` + data.data.ycjb + `</span></li>
		                                <li><label class="color_blue">异常时间：</label><span>` + data.data.ycsj + `</span></li>
		                                <li><label class="color_blue">处置状态：</label><span class="color_yellow">` + data.data.czzt + `</span></li>
		                            </ul>
		                            <img width="100%" class="img mt_10" src="images/video_img1.png" alt="" />
		                        </div>
		                    </div>`;
                    $('#abnormalLayer').html(html);
                    html1 = $('#abnormalLayer');
                } else if (type == 'FW') {
                    var total = 0;
                    for (var key in data.data.fwXx) {
                        total += data.data.fwXx[key].length;
                    }
                    $('#houseLayer').html('');
                    html += `<div class="houseLayerMain">
		                        <h4 class="moveTxt">` + data.data.ldmc + `(房屋` + total + `间)</h4>
		                        <div class="layerContent pr">
		                             <div class="SearchBox pa">
		                                <input type="text" />
		                                <i class="iconfont iconsousuo pa searchIco"></i>
		                             </div>
		                             <div class="layui-tab layui-tab-brief tabLine" lay-filter="docDemoTabBrief">
		                                  <ul class="layui-tab-title">`;
                    for (var key in data.data.fwXx) {
                        if (key == '一单元') {
                            html += `<li class="layui-this">` + key + `</li>`;
                        } else {
                            html += `<li>` + key + `</li>`;
                        }
                    }
                    html += `</ul>
		                                  <div class="layui-tab-content">`;
                    for (var key in data.data.fwXx) {
                        if (key == '一单元') {
                            html += `<div class="layui-tab-item layui-show">`;
                        } else {
                            html += `<div class="layui-tab-item">`;
                        }
                        html += `<div class="layui-row">
		                                                <div class="layui-col-md3">
		                                                   <span class="legendLine mr_10">居住</span>
		                                                   <span class="legendLine legendLine_gray">空置</span>
		                                                </div>
		                                           </div>
		                                           <ul class="ResidenceList">`;
                        for (var i = 0; i < data.data.fwXx[key].length; i++) {
                            var style;
                            if (data.data.fwXx[key][i].FWRZQK == '入住') {
                                style = 'style_zz';
                            } else if (data.data.fwXx[key][i].FWRZQK == '空置') {
                                style = 'style_kz';
                            }
                            html += `<li class="` + style + `" onclick="featherCmsScript._map.lookHouseDetail(\`` + data.data.fwXx[key][i].FWID + `\`)">
		                                                    <span class="txtRT">` + data.data.fwXx[key][i].CQZT + `</span>
		                                                    <span>` + data.data.fwXx[key][i].MPH + `</span>
		                                                </li>`;
                        }
                        html += `</ul>
		                                      </div>`;
                    }
                    html += `</div>
		                             </div>
		                        </div>
		                  </div>
		                    <div class="houseLayerDetail hide pr">
		                     </div>`;
                    $('#houseLayer').html(html);
                    html1 = $('#houseLayer');
                } else if (type == 'RY') {
                    $('#KeyPersonnelLayer').html('');
                    html += `<div class="KeyPersonnelLayerMain">
		                        <h4 class="moveTxt">` + data.data.ldmc + `重点人员分布(` + data.data.num + `人)</h4>
		                        <div class="layerContent">
		                          <div class="layui-row">
		                              <div class="layui-col-md12 KeyPersonnelIcon">
		                                <span class="mr_10"><i class="iconfont iconlaoren color_yellow"></i>独居</span>
		                                <span class="mr_10"><i class="iconfont iconlaoren color_red"></i>空巢</span>
		                                  <span class="mr_10"><i class="iconfont iconrenyuan-shi color_blue"></i>低保</span>
		                                  <span class="mr_10"><i class="iconfont iconcanjiren color_yellow"></i>残疾</span>
		                                  <span class="mr_10"><i class="iconfont iconjunren color_green"></i>退役</span>
		                                  <span class="mr_10"><i class="iconfont iconren color_red"></i>刑满释放</span>
		                              </div>
		                          <ul class="ResidenceDetailList">`;
                    for (var i = 0; i < data.data.list.length; i++) {
                        var arr = data.data.list[i].ZZ.split(data.data.ldmc);
                        html += `<li>`;
                        var age = Number(data.data.list[i].NL);
                        if (data.data.list[i].XB == '男') {
                            if (age >= 60) {
                                html += `<img class="img" src="img/60m.jpg" alt="" />`;
                            } else if (age < 60 && age >= 20) {
                                html += `<img class="img" src="img/20-59m.jpg" alt="" />`;
                            } else {
                                html += `<img class="img" src="img/1-19m.jpg" alt="" />`;
                            }
                        } else {
                            if (age >= 60) {
                                html += `<img class="img" src="img/60w.png" alt="" />`;
                            } else if (age < 60 && age >= 20) {
                                html += `<img class="img" src="img/20-59w.jpg" alt="" />`;
                            } else {
                                html += `<img class="img" src="img/1-19w.jpg" alt="" />`;
                            }
                        }
                        html += `<div class="layui-row">
		                    <div class="layui-col-md6">
		                    <p>
		                    <span class="color_white">姓名:</span>
		                <span class="color_white">` + data.data.list[i].XM + `</span>
		                    </p>
		                    <p>
		                    <span class="color_white">民族:</span>
		                <span class="color_white">` + data.data.list[i].MZ + `</span>
		                    </p>
		                    <p>
		                        <span class="color_white">门牌号:</span>
		                        <span class="color_white">` + arr[1] + `</span>
		                    </p>
		                    <p>
                    <span class="color_white">标签:</span>
                        <span class="color_white">`;

                        if (data.data.list[i].SFDJ == '是') {
                            html += `<i class="iconfont iconlaoren color_yellow"></i>`;
                        }
                        if (data.data.list[i].SFKC == '是') {
                            html += `<i class="iconfont iconlaoren color_red"></i>`;
                        }
                        if (data.data.list[i].SFDB == '是') {
                            html += `<i class="iconfont iconrenyuan-shi color_blue"></i>`;
                        }
                        if (data.data.list[i].SFCJ == '是') {
                            html += `<i class="iconfont iconcanjiren color_yellow"></i>`;
                        }
                        if (data.data.list[i].SFTYJR == '是') {
                            html += `<i class="iconfont iconjunren color_green"></i>`;
                        }
                        if (data.data.list[i].SFXMSF == '是') {
                            html += `<i class="iconfont iconren color_red"></i>`;
                        }
                        html += `</span>
                </p>
		                    </div>
		                    <div class="layui-col-md6">
		                    <p>
		                    <span class="color_white">性别:</span>
		                <span class="color_white">` + data.data.list[i].XB + `</span>
		                    </p>
		                    <p>
		                    <span class="color_white">年龄:</span>
		                <span class="color_white">` + data.data.list[i].NL + `</span>
		                    </p>
		                    <p>
		                    <span class="color_white">电话:</span>
		                <span class="color_white">` + data.data.list[i].DH + `</span>
		                    </p>
		                    </div>
		                </div>
		                </li>`;
                    }
                    html += `</ul>
		                        </div>
		                      </div>`;
                    $('#KeyPersonnelLayer').html(html);
                    html1 = $('#KeyPersonnelLayer');
                }
                if (type == 'RY' || type == 'FW') {
                    area = ['500px', '340px'];
                } else {
                    area = ['400px', 'auto'];
                }

                layer.open({
                    type: 1,
                    title: false,
                    area: area,
                    move: '.moveTxt',
                    scrollbar: false,
                    shade: 0,
                    //fix: false,
                    content: html1,
                    skin: 'layer-style',
                    id: type + mapObject.index,
                    success: function (index, layero) {
                        mapObject.index++;
                        var Gatewiper = new Swiper('#GateWiperWrapper', {
                            observer: true,
                            observeParents: true,
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            //spaceBetween: 30,
                            autoplay: true
                        });
                        var Gatewipers = new Swiper('#RoadPoleswiperWrapper', {
                            observer: true,
                            observeParents: true,
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            //spaceBetween: 30,
                            autoplay: true
                        });
                        var GatewiperZj = new Swiper('#ZJPoleswiperWrapper', {
                            observer: true,
                            observeParents: true,
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            //spaceBetween: 30,
                            autoplay: true
                        });
                        if (Gatewiper.el) {
                            //鼠标覆盖停止自动切换
                            Gatewiper.el.onmouseover = function () {
                                Gatewiper.autoplay.stop();
                            }
                            //鼠标离开开始自动切换
                            Gatewiper.el.onmouseout = function () {
                                Gatewiper.autoplay.start();
                            };
                        }
                        if (Gatewipers.el) {
                            //鼠标覆盖停止自动切换
                            Gatewipers.el.onmouseover = function () {
                                Gatewipers.autoplay.stop();
                            }
                            //鼠标离开开始自动切换
                            Gatewipers.el.onmouseout = function () {
                                Gatewipers.autoplay.start();
                            };
                        }
                        if (GatewiperZj.el) {
                            //鼠标覆盖停止自动切换
                            GatewiperZj.el.onmouseover = function () {
                                GatewiperZj.autoplay.stop();
                            }
                            //鼠标离开开始自动切换
                            GatewiperZj.el.onmouseout = function () {
                                GatewiperZj.autoplay.start();
                            };
                        }
                    },
                    cancel: function (index, layero) {
                        layer.close(index);
                        if(mapObject.typeObject == 'zhzl'){
                            mapObject.clear();
                        }
                    }
                });
            },
            //查看住户详细信息
            lookHouseDetail: function (id) {
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + 'screen/api/getFwRy?fwid=' + id,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        $(".houseLayerDetail").html('');
                        if (data.data.length > 0) {
                            var html = `<i class="backBtn iconfont iconback pa" id="houseBackBtn"></i>
		                      <h4 class="moveTxt">` + data.data[0].ZZ + `(` + data.data.length + `人)</h4>
		                      <h5 class="color_white mw_10">住户信息</h5>
		                      <ul class="ResidenceDetailList">`;
                            for (var i = 0; i < data.data.length; i++) {
                                html += `<li>`;
                                var age = Number(data.data[i].NL);
                                if (data.data[i].XB == '男') {
                                    if (age >= 60) {
                                        html += `<img class="img" src="img/60m.jpg" alt="" />`;
                                    } else if (age < 60 && age >= 20) {
                                        html += `<img class="img" src="img/20-59m.jpg" alt="" />`;
                                    } else {
                                        html += `<img class="img" src="img/1-19m.jpg" alt="" />`;
                                    }
                                } else {
                                    if (age >= 60) {
                                        html += `<img class="img" src="img/60w.png" alt="" />`;
                                    } else if (age < 60 && age >= 20) {
                                        html += `<img class="img" src="img/20-59w.jpg" alt="" />`;
                                    } else {
                                        html += `<img class="img" src="img/1-19w.jpg" alt="" />`;
                                    }
                                }
                                html += `<div class="layui-row">
		                                  <div class="layui-col-md6">
		                                   <p>
		                                    <span class="color_white">姓名:</span>
		                                      <span class="color_white">` + data.data[i].XM + `</span>
		                                   </p>
		                                   <p>
		                                    <span class="color_white">民族:</span>
		                                      <span class="color_white">` + data.data[i].MZ + `</span>
		                                   </p>
		                                   <p>
		                                    <span class="color_white">电话:</span>
		                                      <span class="color_white">` + data.data[i].DH + `</span>
		                                   </p>
		                                  </div>
		                                  <div class="layui-col-md6">
		                                     <p>
		                                    <span class="color_white">性别:</span>
		                                      <span class="color_white">` + data.data[i].XB + `</span>
		                                   </p>
		                                   <p>
		                                    <span class="color_white">年龄:</span>
		                                      <span class="color_white">` + data.data[i].NL + `</span>
		                                   </p>
		                                  </div>
		                              </div>
		                         </li>`;
                            }
                            html += `</ul>`;
                            $('.houseLayerDetail').html(html);
                            $('.houseLayerMain').addClass('hide');
                            $('.houseLayerDetail').removeClass('hide');
                            $('.houseLayerDetail').on('click', '#houseBackBtn', function () {
                                $('.houseLayerMain').removeClass('hide');
                                $('.houseLayerDetail').addClass('hide');
                            });
                        }
                    }
                });
            },
            clear: function () {
                mapObject.removeLocatedBuilding();
                if (mapObject.geometryEntityArr.length > 0) {
                    for (var i in mapObject.geometryEntityArr) {
                        mapObject.viewer.entities.remove(mapObject.geometryEntityArr[i]);
                    }
                    mapObject.geometryEntityArr = [];
                }
                mapObject.viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
            },
            //初始化时间
            startTime: function () {
                var today = new Date();//定义日期对象
                var yyyy = today.getFullYear();//通过日期对象的getFullYear()方法返回年e799bee5baa6e59b9ee7ad9431333363363537
                var MM = today.getMonth() + 1;//通过日期对象的getMonth()方法返回年
                var dd = today.getDate();//通过日期对象的getDate()方法返回年
                var hh = today.getHours();//通过日期对象的getHours方法返回小时
                var mm = today.getMinutes();//通过日期对象的getMinutes方法返回分钟
                var ss = today.getSeconds();//通过日期对象的getSeconds方法返回秒
                // 如果分钟或小时的值小于10，则在其值前加0，比如如果时间是下午3点20分9秒的话，则显示15：20：09
                MM = mapObject.checkTime(MM);
                dd = mapObject.checkTime(dd);
                mm = mapObject.checkTime(mm);
                ss = mapObject.checkTime(ss);

                document.getElementById('time').innerHTML = hh + ":" + mm + ":" + ss;
                document.getElementById('data').innerHTML = yyyy + "年" + MM + "月" + dd + "日";
                setTimeout('featherCmsScript._map.startTime()', 1000);
            },
            checkTime: function (i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
            },
            //加载点状地名
            addLayerName: function (url, type) {
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        var id, name;
                        if (type == 'SQ') {
                            id = 'SQID';
                            name = 'SQMC';
                        } else {
                            id = 'XQID';
                            name = 'XQMC';
                        }
                        for (var i = 0; i < data.features.length; i++) {
                            mapObject.geoPointArr.push(data.features[i]);
                            var citizensBankPark = mapObject.viewer.entities.add({
                                id: data.features[i].properties[id],
                                position: Cesium.Cartesian3.fromDegrees(data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], data.features[i].geometry.coordinates[2]),
                                label: { //文字标签
                                    text: data.features[i].properties[name],
                                    font: '16px 微软雅黑',
                                    style: Cesium.LabelStyle.FILL_AND_OUTLINE,
                                    fillColor: Cesium.Color.AQUA,
                                    outlineWidth: 2,
                                    verticalOrigin: Cesium.VerticalOrigin.BOTTOM, //垂直方向以底部来计算标签的位置
                                    pixelOffset: new Cesium.Cartesian2(0, -18),  //偏移量
                                    disableDepthTestDistance: 350000,
                                    distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 1000),
                                    scaleByDistance: new Cesium.NearFarScalar(200000, 1.2, 100000000000, 0.5)
                                }
                            });
                        }

                    }
                })
            },
            //定位加载图标
            localPosition: function (id, type) {
                mapObject.removeLocatedBuilding();
                var url = featherCmsScript._cfg.cfg[type].url;
                var img = featherCmsScript._cfg.cfg[type].img;
                var nameId = featherCmsScript._cfg.cfg[type].id;
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        for (var i = 0; i < data.features.length; i++) {
                            if (id == data.features[i].properties[nameId]) {
                                var options = {
                                    billboard: { //图标
                                        image: '',
                                        width: 48,
                                        height: 48,
                                        scale: 1, //和原始大小相比的缩放比例
                                        minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                        verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                    }
                                }
                                var promise = Cesium.GeoJsonExtendDataSource.load(data.features[i], options);
                                promise.then(function (dataSource) {
                                    mapObject.viewer.dataSources.add(dataSource);
                                    dataSource.name = "locateBuilding";
                                    dataSource.autoAvoid(mapObject.viewer);
                                    mapObject.viewer.flyTo(dataSource, {
                                        offset: {
                                            heading: Cesium.Math.toRadians(0.0),
                                            pitch: Cesium.Math.toRadians(-45),
                                            range: 400
                                        }
                                    });
                                });
                            }
                        }
                    }
                });
            },
            // 移除已经定位的建筑物
            removeLocatedBuilding: function () {
                if (mapObject.viewer) {
                    var dataSources = mapObject.viewer.dataSources._dataSources;
                    var myDataSources = dataSources.filter(function (item) {
                        return item.name === "locateBuilding";
                    });
                    if (myDataSources && myDataSources.length > 0) {
                        for (var i = 0, len = myDataSources.length; i < len; i++) {
                            mapObject.viewer.dataSources.remove(myDataSources[i]);
                        }
                    }
                }
            },
            localPositionByName: function (name) {
                mapObject.removeLocatedBuilding();
                for (var i = 0; i < mapObject.geoPointArr.length; i++) {
                    var proper = mapObject.geoPointArr[i].properties;
                    var options = {
                        billboard: { //图标
                            image: '',
                            width: 48,
                            height: 48,
                            scale: 1, //和原始大小相比的缩放比例
                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                        }
                    };
                    if (proper['SQMC'] == name) {
                        var promise = Cesium.GeoJsonExtendDataSource.load(mapObject.geoPointArr[i], options);
                        promise.then(function (dataSource) {
                            mapObject.viewer.dataSources.add(dataSource);
                            dataSource.name = "locateBuilding";
                            dataSource.autoAvoid(mapObject.viewer);
                            mapObject.viewer.flyTo(dataSource, {
                                offset: {
                                    heading: Cesium.Math.toRadians(0.0),
                                    pitch: Cesium.Math.toRadians(-30),
                                    range: 1100
                                }
                            });
                        });
                    } else if (proper['XQMC'] == name) {
                        var promise = Cesium.GeoJsonExtendDataSource.load(mapObject.geoPointArr[i], options);
                        promise.then(function (dataSource) {
                            mapObject.viewer.dataSources.add(dataSource);
                            dataSource.name = "locateBuilding";
                            dataSource.autoAvoid(mapObject.viewer);
                            mapObject.viewer.flyTo(dataSource, {
                                offset: {
                                    heading: Cesium.Math.toRadians(0.0),
                                    pitch: Cesium.Math.toRadians(-30),
                                    range: 400
                                }
                            });
                        });
                    }
                }
            },
            localPicName: function (e) {
                mapObject.removeLocatedBuilding();
                    var options = {
                        billboard: { //图标
                            image: '',
                            width: 48,
                            height: 48,
                            scale: 1, //和原始大小相比的缩放比例
                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                        }
                    }
                    // geometry: {type: "Point", coordinates: Array(3)}
                    // properties: {ObjectId: 0, ObjectId_1: null, XQID: "XQ000002", XQMC: "佳宝三村", JJ: "", …}
                    // type: "Feature"
                    var promise = Cesium.GeoJsonExtendDataSource.load(e, options);
                    promise.then(function (dataSource) {
                        mapObject.viewer.dataSources.add(dataSource);
                        dataSource.name = "locateBuilding";
                        dataSource.autoAvoid(mapObject.viewer);
                        mapObject.viewer.flyTo(dataSource, {
                            offset: {
                                heading: Cesium.Math.toRadians(0.0),
                                pitch: Cesium.Math.toRadians(-30),
                                range: 1
                            }
                        });
                    });
            },
            //首页外其他页面点击事件
            otherClick: function (id, type, typeObject) {

                mapObject.type = type;
                mapObject.typeObject = typeObject;
                //先清除之前的图标
                mapObject.clear();
                //mapObject.localPositionYc(id, type, typeObject);
                //mapObject.localPosition(id, type);

                if(typeObject == 'index'){
                    mapObject.type = '';
                    mapObject.typeObject = '';

                    var img = featherCmsScript._cfg.cfg[type].img;
                    mapObject.clickPic(img, id, type, typeObject);

                    $('.zs-picture').removeClass('active');
                    $('.rightContent').addClass('CloseRight');
                }else if(typeObject == 'zhzl'){
                    //var img = featherCmsScript._cfg.cfg[type].img;
                    //mapObject.clickPic(img, id, type, typeObject);
                }
            },
            //定位加载图标
            localPositionYc: function (id, type, typeObject) {
                var url = featherCmsScript._cfg.cfg[type].url;
                var img = featherCmsScript._cfg.cfg[type].img;
                var nameId = featherCmsScript._cfg.cfg[type].id;
                $.ajax({
                    "async": false,
                    "url": featherCmsScript.ctx + url,
                    "type": 'GET',
                    "dataType": 'json',
                    "success": function (data) {
                        for (var i = 0; i < data.features.length; i++) {
                            var surfacePosition = Cesium.Cartesian3.fromDegrees(
                                data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], 18
                            );
                            var heightPosition = Cesium.Cartesian3.fromDegrees(
                                data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], 49
                            );
                            if (id == data.features[i].properties[nameId]) {
                                var citizensBankPark = mapObject.viewer.entities.add({
                                    code: data.features[i].properties[nameId],
                                    position: heightPosition,
                                    polyline: {
                                        positions: new Cesium.ConstantProperty([
                                            surfacePosition,
                                            heightPosition,
                                        ]),
                                        width: 2,
                                        material: Cesium.Color.fromCssColorString('#4BE1EE')
                                    },
                                    billboard: { //图标
                                        image: featherCmsScript.ctx + img,
                                        width: 48,
                                        height: 48,
                                        scale: 1, //和原始大小相比的缩放比例
                                        minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                        verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                    }
                                });
                                mapObject.geometryEntityArr.push(citizensBankPark);

                                mapObject.viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                                    var scene = mapObject.viewer.scene;
                                    if (scene.mode !== Cesium.SceneMode.MORPHING) {
                                        var pickedObject = scene.pick(movement.position);
                                        if (pickedObject && pickedObject.id && pickedObject.id._code) {
                                            mapObject.clickPic(img, pickedObject.id._code, type, typeObject);
                                        }
                                    }
                                }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
                            }
                        }
                    }
                });
            },
            getType: function (sblx){
                var type='';
                if(sblx=='摄像头'){
                    type='SXT';
                }else  if(sblx=='门禁'){
                    type='MJ';
                }else  if(sblx=='垃圾桶'){
                    type='LJT';
                }else  if(sblx=='车辆道杆'){
                    type='DG';
                }else  if(sblx=='充电桩'){
                    type='CDZ';
                }else  if(sblx=='路灯'){
                    type='LD';
                }else  if(sblx=='闸机'){
                    type='ZJ';
                }else  if(sblx=='烟感'){
                    type='YG';
                }else  if(sblx=='消防栓'){
                    type='XFS';
                }
                return type;
            },
            routeWay:function(data){
                trackPlayback.stop();
                data = mapObject.zbInfo;
                if(data.length > 0){
                    var geo = [];
                    var time = [];
                    for(var i = 0;i<data.length;i++){
                        var x = Number(data[i].X);
                        var y = Number(data[i].Y);
                        var z = 55;
                        geo.push([x,y,z]);
                        var date = data[i].SJ;
                        date = date.substring(0,19);
                        date = date.replace(/-/g,'/'); //必须把日期'-'转为'/'
                        var timestamp = new Date(date).getTime();
                        time.push(timestamp);
                    }
                    var historyGeoData = {
                        type: "FeatureCollection",
                        features: [{
                            "type": "Feature",
                            "geometry": {
                                "type": "MultiPoint",
                                "coordinates": geo
                            },
                            "properties": {
                                "time": time
                            }
                        }]
                    };

                    trackPlayback.start(historyGeoData);
                    trackPlayback.play(historyGeoData);
                }

            },
            update3dtilesMaxtrix: function (params) {
                //旋转
                let mx = Cesium.Matrix3.fromRotationX(Cesium.Math.toRadians(params.rx));
                let my = Cesium.Matrix3.fromRotationY(Cesium.Math.toRadians(params.ry));
                let mz = Cesium.Matrix3.fromRotationZ(Cesium.Math.toRadians(params.rz));
                let rotationX = Cesium.Matrix4.fromRotationTranslation(mx);
                let rotationY = Cesium.Matrix4.fromRotationTranslation(my);
                let rotationZ = Cesium.Matrix4.fromRotationTranslation(mz);
                //平移
                let position = Cesium.Cartesian3.fromDegrees(params.tx, params.ty, params.tz);
                let m = Cesium.Transforms.eastNorthUpToFixedFrame(position);

                // let scale = Cesium.Matrix4.fromUniformScale(0.85);
                // //缩放
                // Cesium.Matrix4.multiply(m, scale, m);
                //旋转、平移矩阵相乘
                Cesium.Matrix4.multiply(m, rotationX, m);
                Cesium.Matrix4.multiply(m, rotationY, m);
                Cesium.Matrix4.multiply(m, rotationZ, m);
                //赋值给tileset
                return m;
            }
        };
        featherCmsScript._map = mapObject;

        if (mapObject.isPCBrowser) {
            mapObject.viewer = new Cesium.Viewer('cesiumContainer', {
                imageryProvider: new Cesium.SingleTileImageryProvider({
                    url: Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
                }),
                animation: false,
                timeline: false,
                baseLayerPicker: false,
                geocoder: false,
                homeButton: false,
                infoBox: false,
                sceneModePicker: false,
                fullscreenButton: false,
                navigationHelpButton: false,
                languageStyle: Cesium.LanguagesStyle.zh_CN
            });
        } else {
            mapObject.viewer = new Cesium.Viewer('cesiumContainer', {
                imageryProvider: new Cesium.SingleTileImageryProvider({
                    url: Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
                }),
                animation: false,
                timeline: false,
                baseLayerPicker: false,
                geocoder: false,
                homeButton: false,
                infoBox: false,
                sceneModePicker: false,
                navigationHelpButton: false,
                showStatusBar: false,
                fullscreenButton: false,
                languageStyle: Cesium.LanguagesStyle.zh_CN
            });
        }
        var options = {
            name: 'arcGis-中国地图午夜蓝板',
            type: 'Imagery',
            dataSourceType: 'ArcGISMapServerImagery',
            url: 'https://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer'
        };
        mapObject.viewer.imageryLayers.addImageryProvider(new Cesium.ArcGisMapServerImageryProvider(options));
        //倾斜摄影
        mapObject.tileset = mapObject.viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
            url: 'http://27.17.43.14:8585/aupdata/html/data/tileset.json'
        }));
        //白模
        /*mapObject.model = new Cesium.Cesium3DTileset({
            url: 'http://27.17.43.14:8585/aupdata/html/bm/tileset.json'
        });*/
        //蓝模
        /*mapObject.model = new Cesium.Cesium3DTileset({
            url : 'http://27.17.43.14:8585/aupdata/html/bm2/tileset.json'
        });*/

        //BIM
        /*var tileset = mapObject.viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
            url: 'http://27.17.43.14:8585/aupdata/BIMDemo/1/tileset.json'
        }));

        tileset.readyPromise.then(function (tileset) {
            var params = {
                tx: 121.34601, //模型中心X轴坐标（经度，单位：十进制度）
                ty: 31.15614, //模型中心Y轴坐标（纬度，单位：十进制度）
                tz: 18, //模型中心Z轴坐标（高程，单位：米）
                rx: 0, //X轴（经度）方向旋转角度（单位：度）
                ry: 0, //Y轴（纬度）方向旋转角度（单位：度）
                rz: 30 //Z轴（高程）方向旋转角度（单位：度）
            };
            tileset._root.transform = mapObject.update3dtilesMaxtrix(params);
        });*/

        //mapObject.viewer.scene.primitives.add(mapObject.model);
        mapObject.viewer.zoomTo(mapObject.tileset);
        mapObject.renderP('bus/smartcommunity/json/SQ_M.json', '#4BE1EE');
        mapObject.renderP('bus/smartcommunity/json/XQ_M.json', '#3CBF9C');
        mapObject.addLayerName('bus/smartcommunity/json/SQ_D.json', 'SQ');
        mapObject.addLayerName('bus/smartcommunity/json/XQ_D.json', 'XQ');
        addflst()
        //图层按钮点击事件
        $(".zs-picture").click(function () {
            mapObject.clearPic();
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            } else {
                $('.zs-picture').removeClass('active');
                $(this).addClass('active');
                var type = $(this).attr('type');
                var typeObject = $(this).attr('typeObject');
                if (type == 'FW' || type == 'RY' || type == 'YC') {
                    $('.assetsMenuList').addClass('hide');
                    $('.SecurityMenuList').addClass('hide');
                }
                if (type == 'RY') {
                    $.ajax({
                        "async": false,
                        "url": featherCmsScript.ctx + 'screen/api/getZdRyLd',
                        "type": 'GET',
                        "dataType": 'json',
                        "success": function (data) {
                            mapObject.result = data.data;
                            mapObject.addLayerPic(type, typeObject);
                        }
                    });
                } else {
                    mapObject.addLayerPic(type, typeObject);
                }
            }
        });
        function addflst () {
            var url = featherCmsScript.ctx + 'bus/smartcommunity/gltf/Surface_À³Òð·çÉÐ_0.gltf';

            //entity方式添加楼栋模型 ZL
            var position = Cesium.Cartesian3.fromDegrees(121.342937, 31.153571, 18.212486);

            var heading = Cesium.Math.toRadians(0);
            var pitch = 0;
            var roll = 0;
            var hpr = new Cesium.HeadingPitchRoll(heading, pitch, roll);
            var orientation = Cesium.Transforms.headingPitchRollQuaternion(position, hpr);
            var entity = mapObject.viewer.entities.add({
                name: url,
                position: position,
                orientation: orientation,
                model: {
                    uri: url,
                    minimumPixelSize: 100,
                    maximumScale: 5.0,
                    scale: 0.7,
                    color: Cesium.Color.RED
                }
            });
            setTimeout(function () {
                mapObject.viewer.flyTo(entity);
            },4000)
        }

        //地图操作按钮
        $(".mapToolbarList li").click(function () {
            var icon = $(this).find('i').attr('class');
            switch (icon) {
                case 'iconfont iconicon': //指北
                    mapObject.viewer.scene.camera.flyTo({
                        destination: new Cesium.Cartesian3.fromDegrees(121.34612, 31.14985, 100),
                        orientation: {
                            heading: Cesium.Math.toRadians(0),
                            pitch: Cesium.Math.toRadians(-5),
                            roll: Cesium.Math.toRadians(0),
                        },
                        duration: 3.0
                    });
                    break;
                case 'iconfont iconquanping': //全屏
                    if (Cesium.Fullscreen.fullscreen) {
                        Cesium.Fullscreen.exitFullscreen();
                    } else {
                        Cesium.Fullscreen.requestFullscreen(document.body);
                    }
                    break;
                case 'iconfont iconjia': //放大
                    mapObject.viewer.camera.zoomIn(mapObject.viewer.camera.positionCartographic.height / Math.abs(Math.sin(mapObject.viewer.camera.pitch)) * 0.2);
                    break;
                case 'iconfont iconjian': //缩小
                    mapObject.viewer.camera.zoomOut(mapObject.viewer.camera.positionCartographic.height / Math.abs(Math.sin(mapObject.viewer.camera.pitch)) * 0.2);
                    break;
                case 'iconfont iconquantu': //全图
                    mapObject.full();
                    break;
                case 'iconfont iconshanchu-copy-copy': // 删除
                    mapObject.clearPic();
                    break;
            }
        });
    }
});


//历史轨迹回放对象
var trackPlayback = {
    historyPath: null,//历史轨迹线图层
    historyPathEntity: null,//历史轨迹线模拟对象
    isWorking:false,//用于切换模块时判断是否正在工作
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
    start: function (data) {debugger
        // viewer.animation.container.style.display="block";
        // viewer.animation.resize();
        // viewer.timeline.container.style.display="block";
        // viewer.timeline.resize();
        // viewer.statusBar.container.style.bottom="30px";
        var that = this;
        this.historyGeoData = data || this.historyGeoData;
        if (this.historyPath) {
            featherCmsScript._map.viewer.dataSources.remove(this.historyPath);
            this.historyPath = null;
        }
        var options = {
            billboard: {
                image: featherCmsScript.ctx + 'bus/smartcommunity/img/xunjiandian.png',
                width: 48,
                height: 48,
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
        featherCmsScript._map.viewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(this.historyGeoData, options)).then(function (ds) {
            that.historyPath = ds;
            featherCmsScript._map.viewer.flyTo(ds);
            that.play(that.historyGeoData);
        });
    },
    /**
     * 开始回放历史轨迹--模拟运动轨迹
     * @param {GeoJson} 轨迹点数据
     */
    play: function (historyGeoData) {
        if (this.historyPathEntity) {
            featherCmsScript._map.viewer.entities.remove(this.historyPathEntity);
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

        featherCmsScript._map.viewer.clock.startTime = this.startTime.clone();
        featherCmsScript._map.viewer.clock.stopTime = this.stopTime.clone();
        featherCmsScript._map.viewer.clock.currentTime = this.startTime.clone();
        featherCmsScript._map.viewer.clock.clockRange = Cesium.ClockRange.CLAMPED;
        featherCmsScript._map.viewer.clock.multiplier = 10;
        featherCmsScript._map.viewer.clock.shouldAnimate = true;
        //Set timeline to simulation bounds
        //featherCmsScript._map.viewer.timeline.zoomTo(this.startTime, this.stopTime);
        this.historyPathEntity = featherCmsScript._map.viewer.entities.add({

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
                image: featherCmsScript.ctx + 'bus/smartcommunity/img/xunjianrenyuan.png',
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
        featherCmsScript._map.viewer.clock.shouldAnimate = !featherCmsScript._map.viewer.clock.shouldAnimate;
    },
    stop: function () {
        // viewer.timeline.container.style.display="none";
        // viewer.animation.container.style.display="none";
        // viewer.statusBar.container.style.bottom="0";
        if (this.historyPathEntity) {
            featherCmsScript._map.viewer.entities.remove(this.historyPathEntity);
            this.historyPathEntity = null;
        }
        if (this.historyPath) {
            featherCmsScript._map.viewer.dataSources.remove(this.historyPath);
            this.historyPath = null;
        }
        featherCmsScript._map.viewer.clock.shouldAnimate = false;
    },
};


/* 获取参数位置方法
    * featherCmsScript._map.viewer.camera.heading
    * featherCmsScript._map.viewer.camera.pitch
    * featherCmsScript._map.viewer.camera.roll
    * featherCmsScript._map.viewer.camera.position
    */
function flyToposition() {
    //测试数据1
    var x = 0, y = 0, z = 0,
        heading =6.283185307179586,
        pitch = -0.5001314818527969,
        roll = 6.283185307179586;

    var position = {
        x: -2841754.3486750633,
        y: 4666129.3385656,
        z: 3280300.8064915454
    };
    //测试数据2
    /*position = {
        x: -2841731.582541161,
        y: 4666170.331203669,
        z: 3280256.279906369
    };
    heading = 6.008083911251645;
    pitch = -0.37902071632300083;
    roll = 6.282316206823882;*/


    featherCmsScript._map.viewer.camera.flyTo({
        destination: position,
        orientation: {
            // 指向
            heading: heading,
            // 视角
            pitch: pitch,
            roll: roll
        }
    })
}

