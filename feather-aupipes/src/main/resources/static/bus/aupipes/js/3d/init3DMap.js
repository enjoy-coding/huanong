var lang = getLanguagePackageByLang.returnPackageByLang();
var viewer;
var gViewer; // 等于 viewer
var measureHandler;//声明测量类
var drawHandler;//声明绘制类
var jsonObj;//JSON化后的图层树配置文件
var hlzsId = null;	//回路追溯的id
var pickType;   //全局变量，用来区分出线追溯选取事件和地图按钮选取事件
var pickProtry; //存储管网运行中，选取点的BIM模型属性
var pipeLayerProperties; //存储管线模型的属性信息
var bimLayerInfo = null;	//存储bim模型的标准图层
//分屏对象
var screenSplit = {
	slider: null,
	primitive: null,
	moveActive: false,
	screenSpaceEventHandler: null,
	//卷帘初始化
	init: function (slider, primitive) {
		this.slider = slider;
		this.primitive = primitive;
		this.slider.style.visibility = "visible";
		if (this.primitive) this.primitive.splitDirection = Cesium.ImagerySplitDirection.LEFT;
		this.screenSpaceEventHandler = this.screenSpaceEventHandler || new Cesium.ScreenSpaceEventHandler(slider);
		viewer.scene.imagerySplitPosition = (this.slider.offsetLeft) / this.slider.parentElement.offsetWidth;
		this.screenSpaceEventHandler.setInputAction(function () {
			this.moveActive = true;
		}, Cesium.ScreenSpaceEventType.LEFT_DOWN);
		this.screenSpaceEventHandler.setInputAction(function () {
			this.moveActive = true;
		}, Cesium.ScreenSpaceEventType.PINCH_START);

		this.screenSpaceEventHandler.setInputAction(this.move, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
		this.screenSpaceEventHandler.setInputAction(this.move, Cesium.ScreenSpaceEventType.PINCH_MOVE);

		this.screenSpaceEventHandler.setInputAction(function () {
			this.moveActive = false;
		}, Cesium.ScreenSpaceEventType.LEFT_UP);
		this.screenSpaceEventHandler.setInputAction(function () {
			this.moveActive = false;
		}, Cesium.ScreenSpaceEventType.PINCH_END);
	},
	//卷帘slider移动
	move: function (movement) {
		if (!this.moveActive) {
			return;
		}
		var relativeOffset = movement.endPosition.x;
		var splitPosition = (this.slider.offsetLeft + relativeOffset) / this.slider.parentElement.offsetWidth;
		this.slider.style.left = 100.0 * splitPosition + '%';
		viewer.scene.imagerySplitPosition = splitPosition;
	},
	//销毁卷帘
	destroy: function () {
		if (this.primitive) this.primitive.splitDirection = Cesium.ImagerySplitDirection.NONE;
		if (this.slider) {
			this.slider.style.visibility = "hidden";
			this.slider.style.left = "50%";
		}
		this.screenSpaceEventHandler = this.screenSpaceEventHandler && this.screenSpaceEventHandler.destroy();
	}
};
//拾取对象
var pickFeature = {
	// HTML overlay for showing feature name on mouseover
	// nameOverlay: null,
	// Information about the currently selected feature
	isWorking:false,//用于切换模块时判断是否正在工作
	selected: {
		feature: undefined,
		originalColor: new Cesium.Color()
	},
	// An entity object which will hold info about the currently selected feature for infobox display
	selectedEntity: new Cesium.Entity(),
	// Get default left click handler for when a feature is not picked on left click
	clickHandler: null,
	// Information about the currently highlighted feature
	highlighted: {
		feature: undefined,
		originalColor: new Cesium.Color()
	},
	// 拾取类型，Feature:拾取要素，Position:拾取位置（坐标），Feature2D:二维服务拾取要素
	type: "Feature",
	treeNode: null,
	//拾取完成后回调函数，返回拾取到的要素/位置
	callback: function (pickedFeature) {
		if (this.type === "Feature") {
			// Set feature infobox description
			var properties = pickedFeature.getPropertyNames() || [];
			var featureName = 'Feature Property';
			if (properties.indexOf('name') > -1 || properties.indexOf('names') > -1) {
				featureName = pickedFeature.getProperty('name') || pickedFeature.getProperty('names');
			}
			this.selectedEntity.name = featureName;
			this.selectedEntity.description = 'Loading <div class="cesium-infoBox-loading"></div>';
			viewer.selectedEntity = this.selectedEntity;
			var trs = '';
			properties.forEach(function (property) {
				trs += '<tr><th>' + property + '</th><td>' + pickedFeature.getProperty(property) + '</td></tr>';
			});
			this.selectedEntity.description = '<table class="cesium-infoBox-defaultTable"><tbody>' + trs + '</tbody></table>';
		} else if (this.type === "Position" || this.type === "Feature2D") {
			console.log("Position", pickedFeature);
		}
	},
	start: function (type, callback) {
		this.type = type;
		if (typeof callback === "function") {
			this.callback = callback;
		}
		// this.nameOverlay = nameOverlay;
		this.clickHandler = this.clickHandler || viewer.screenSpaceEventHandler.getInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
		// Color a feature yellow on hover.
		// this.type === "Feature" && viewer.screenSpaceEventHandler.setInputAction(this.onMouseMove.bind(this), Cesium.ScreenSpaceEventType.MOUSE_MOVE);

		// Color a feature on selection and show metadata in the InfoBox.
		viewer.screenSpaceEventHandler.setInputAction(this.onLeftClick.bind(this), Cesium.ScreenSpaceEventType.LEFT_CLICK);
	},
	onMouseMove: function (movement) {
		// If a feature was previously highlighted, undo the highlight
		if (Cesium.defined(this.highlighted.feature)) {
			this.highlighted.feature.color = this.highlighted.originalColor;
			this.highlighted.feature = undefined;
		}
		// Pick a new feature
		var pickedFeature = viewer.scene.pick(movement.endPosition);
		if (!Cesium.defined(pickedFeature)) {
			// this.nameOverlay.style.display = 'none';
			return;
		}
		if (pickedFeature.primitive && pickedFeature.primitive.name === '倾斜摄影') {
			return;
		}
		// A feature was picked, so show it's overlay content
		// this.nameOverlay.style.display = 'block';
		// this.nameOverlay.style.bottom = viewer.canvas.clientHeight - movement.endPosition.y + 'px';
		// this.nameOverlay.style.left = movement.endPosition.x + 'px';
		// var properties = pickedFeature.getPropertyNames() || [];
		// var name = pickedFeature.getProperty('name') || pickedFeature.getProperty('names');
		// if (!Cesium.defined(name)) {
		// 	name = pickedFeature.getProperty(properties[0])
		// }
		// this.nameOverlay.textContent = name;
		// Highlight the feature if it's not already selected.
		if (pickedFeature !== this.selected.feature) {
			this.highlighted.feature = pickedFeature;
			Cesium.Color.clone(pickedFeature.color, this.highlighted.originalColor);
			pickedFeature.color = Cesium.Color.YELLOW;
		}
	},
	onLeftClick: function (movement) {
		if (this.type === "Feature") {
			// If a feature was previously selected, undo the highlight
			if (Cesium.defined(this.selected.feature) && this.selected.feature.tileset) {
				this.selected.feature.color = this.selected.originalColor;
				this.selected.feature = undefined;
			}
			// Pick a new feature
			var pickedFeature = viewer.scene.pick(movement.position);
			if (!Cesium.defined(pickedFeature)) {
				this.clickHandler(movement);
				return;
			}
			if (pickedFeature.primitive && pickedFeature.primitive.name === '倾斜摄影') {
				return;
			}
			// Select the feature if it's not already selected
			if (this.selected.feature === pickedFeature) {
				return;
			}
			this.selected.feature = pickedFeature;
			// Save the selected feature's original color
			if (pickedFeature === this.highlighted.feature) {
				Cesium.Color.clone(this.highlighted.originalColor, this.selected.originalColor);
				this.highlighted.feature = undefined;
			} else {
				Cesium.Color.clone(pickedFeature.color, this.selected.originalColor);
			}
			// Highlight newly selected feature
			pickedFeature.color = Cesium.Color.LIME;
			//执行回调函数，返回拾取到的元素
			this.callback(pickedFeature);
		} else if (this.type === "Position" || this.type === "Feature2D") {
			var position = viewer.scene.pickPosition(movement.position);
			if (position) {
				var cartographic = Cesium.Cartographic.fromCartesian(position);
				var longitude = Cesium.Math.toDegrees(cartographic.longitude).toFixed(5);
				var latitude = Cesium.Math.toDegrees(cartographic.latitude).toFixed(5);
				var height = cartographic.height.toFixed(3);
				if (this.type === "Position") {
					this.callback({x: longitude, y: latitude, z: height});
				}
				if (this.type === "Feature2D") {
					var treeNode = this.treeNode || layerTree.zTree.getNodeByParam("id", "JS_LINE");
					// 获取点坐标半径 1m 的缓冲图形
					var geometry = {type: "Point", coordinates: [parseFloat(longitude), parseFloat(latitude)]};
					var bufferOptions = {
						url: treeNode.dataSourceUrl.substr(0, treeNode.dataSourceUrl.lastIndexOf("huanong")) + "geometryserver/buffer",
						geoSRS: "EPSG:4326",
						outSRS: "EPSG:4326",
						data: JSON.stringify(geometry),
						radius: 1
					};
					layerTree.getBufferOfGeometry(bufferOptions,function (bufferGeometry) {
						if (!bufferGeometry) return;
						var queryOptions = {
							url: treeNode.dataSourceUrl.substr(0, treeNode.dataSourceUrl.lastIndexOf("/")) + "/queryserver/query",
							geoSRS: "EPSG:4326",
							outSRS: "EPSG:4326",
							layerId: treeNode.queryLayerId,
							geometry: JSON.stringify(bufferGeometry),
							where: "1=1",
							startIndex: 0,
							reqCount: 200,
							isOverlap: true
						};
						// 使用缓冲图形来查询
						layerTree.queryGeoJsonDataBySQL(queryOptions, function (geoJsonData) {
							if (geoJsonData) {
								pickFeature.callback(geoJsonData);
							}
						});
					});
				}
			}
		}
	},
	stop: function () {
		if (this.type === "Feature") {
			//undo the highlight
			if (Cesium.defined(this.selected.feature)) {
				this.selected.feature.color = this.selected.originalColor;
				this.selected.feature = undefined;
			}
			if (Cesium.defined(this.highlighted.feature)) {
				this.highlighted.feature.color = this.highlighted.originalColor;
				this.highlighted.feature = undefined;
			}
			// this.nameOverlay.style.display = 'none';
			viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
		} else if (this.type === "Position" || this.type === "Feature2D") {
			viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
		}
	}
};
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
	start: function (data) {
		// viewer.animation.container.style.display="block";
		// viewer.animation.resize();
		// viewer.timeline.container.style.display="block";
		// viewer.timeline.resize();
		// viewer.statusBar.container.style.bottom="30px";
		var that = this;
		this.historyGeoData = data || this.historyGeoData;
		if (this.historyPath) {
			viewer.dataSources.remove(this.historyPath);
			this.historyPath = null;
		}
		var options = {
			billboard: {
				image: ctx + 'bus/aupipes/img/3d/xunjiandian.png',
				width: 16,
				height: 20,
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
		viewer.dataSources.add(Cesium.GeoJsonExtendDataSource.load(this.historyGeoData, options)).then(function (ds) {
			that.historyPath = ds;
			viewer.flyTo(ds);
			that.play(that.historyGeoData);
		});
	},
	/**
	 * 开始回放历史轨迹--模拟运动轨迹
	 * @param {GeoJson} 轨迹点数据
	 */
	play: function (historyGeoData) {
		if (this.historyPathEntity) {
			viewer.entities.remove(this.historyPathEntity);
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

		viewer.clock.startTime = this.startTime.clone();
		viewer.clock.stopTime = this.stopTime.clone();
		viewer.clock.currentTime = this.startTime.clone();
		viewer.clock.clockRange = Cesium.ClockRange.CLAMPED;
		viewer.clock.multiplier = 10;
		viewer.clock.shouldAnimate = true;
		//Set timeline to simulation bounds
		viewer.timeline.zoomTo(this.startTime, this.stopTime);
		this.historyPathEntity = viewer.entities.add({

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
				image: ctx + 'bus/aupipes/img/3d/xunjianrenyuan.png',
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
		viewer.clock.shouldAnimate = !viewer.clock.shouldAnimate;
	},
	stop: function () {
		// viewer.timeline.container.style.display="none";
		// viewer.animation.container.style.display="none";
		// viewer.statusBar.container.style.bottom="0";
		if (this.historyPathEntity) {
			viewer.entities.remove(this.historyPathEntity);
			this.historyPathEntity = null;
		}
		if (this.historyPath) {
			viewer.dataSources.remove(this.historyPath);
			this.historyPath = null;
		}
		viewer.clock.shouldAnimate = false;
	},
};

lang.then(function(data) {
	getLanguagePackageByLang.translationLangByKey("lang", data)
	onload()
	//addBMap ();
}, function() {
	onload();
	//addBMap ();
})

function onload() {
	var isPCBrowser = Cesium.FeatureDetection.isPCBrowser();
	if (isPCBrowser) {
		viewer = new Cesium.Viewer(
				'cesiumContainer',
				{
					imageryProvider : new Cesium.SingleTileImageryProvider(
							{
								url : Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
							}),
					animation : true,
					timeline : true,
					baseLayerPicker : false,
					geocoder : false,
					homeButton : false,
					infoBox:false,
					sceneModePicker : false,
					fullscreenButton:false,
					navigationHelpButton : false,
					languageStyle : Cesium.LanguagesStyle.zh_CN
				});
	} else {
		viewer = new Cesium.Viewer('cesiumContainer',{
					imageryProvider : new Cesium.SingleTileImageryProvider(
							{
								url : Cesium.buildModuleUrl('Assets/Textures/earth_color_low_4096.jpg')
							}),
					animation : true,
					timeline : true,
					baseLayerPicker : false,
					geocoder : false,
					homeButton : false,
					infoBox:false,
					sceneModePicker : false,
					navigationHelpButton : false,
					showStatusBar : false,
					fullscreenButton:false,
			        languageStyle : Cesium.LanguagesStyle.zh_CN
				});
	}
	viewer.timeline.container.style.display="none";
	viewer.animation.container.style.display="none";
	gViewer = viewer;

	// Add google map
//	var googleSatellite= viewer.imageryLayers.addImageryProvider(new Cesium.GoogleMapsImageryProvider({
//				name : 'Google Satellite',
//				maptype : "satellite"
//			}));


	measureHandler = new Cesium.MeasureHandler(viewer);
	//Initialize DrawHandler
	drawHandler = new Cesium.DrawHandler(viewer, {followEllipsoid: false,showTooltip: true, dblClickEnd: false});

	screenSpaceEventHandler = new Cesium.ScreenSpaceEventHandler(document.getElementById('slider'));//声明分屏类

	//开启碰撞检测
//	viewer.scene.screenSpaceCameraController.enableCollisionDetection = false;
    //开启深度检测
	viewer.scene.globe.depthTestAgainstTerrain = true;
	//移除鼠标双击事件
	viewer.cesiumWidget.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
//
//    Cesium.Kq3dUndergroundMixin(viewer.scene);
//    //开启地下模式
//    viewer.scene.kq3dUndergroundManager.undergroundEnabled = false;
//    //开启地形透明
//    viewer.scene.kq3dUndergroundManager.transparentEnabled = false;
//    //设置地形透明度，透明度在0-1之间
//    viewer.scene.kq3dUndergroundManager.transparent = 1;

	//获取图层树配置文件
	$.ajax({
	    "async": false,
	    "url": ctx + 'bus/aupipes/config.xml' + '?_=' + staticResourcesVersion,
	    "type": 'GET',
	    "dataType": 'xml',
	    "timeout": 16000,
	    "cache": false,
	    "error": function () {
	        console.log('加载图层树配置文件失败');
	    },
	    "success": function (xmlDoc) {
			var x2js = new X2JS();
		    jsonObj = x2js.xml2json(xmlDoc);
			//根据场景类型初始化zTree
		    if($("#tt").length>0) layerTree.initTree(jsonObj, "");
	    }
	});

	//获取图层树配置文件
	$.ajax({
		"async": false,
		"url": ctx + 'bus/aupipes/layerProperty.xml' + '?_=' + staticResourcesVersion,
		"type": 'GET',
		"dataType": 'xml',
		"timeout": 16000,
		"cache": false,
		"success": function (xmlDoc) {
			var x2js = new X2JS();
			//读取各个管线点。段的属性
			pipeLayerProperties = x2js.xml2json(xmlDoc);
		}
	});

	//获取bim标注图层
	$.ajax({
		"async": false,
		"url": ctx + 'bus/aupipes/bimConfig.xml' + '?_=' + staticResourcesVersion,
		"type": 'GET',
		"dataType": 'xml',
		"timeout": 16000,
		"cache": false,
		"success": function (xmlDoc) {
			var x2js = new X2JS();
			bimLayerInfo = x2js.xml2json(xmlDoc);
		}
	});


}

layui.use('element', function(){
	  var element = layui.element;
	  var currentTabId="tabLayer";
	  var btnTreeExpand=$(".layui-btn,.site-demo-active").eq(0);//图层树“展开/收起”按钮

	  //监听tab页切换
	  element.on('tab(layerTreeTab)', function(data){
		  var tab=$(this);
		  currentTabId=tab.attr("lay-id");
		  if(currentTabId == 'tabLayer'){
    		  if(layerTree.expandFlag){
    			  btnTreeExpand.text('收起');
    		  }else{
    			  btnTreeExpand.text('展开');
    		  }
          }else if(currentTabId == 'tabBimLayer'){
        	  if(bimLayerTree.expandFlag){
    			  btnTreeExpand.text('收起');
    		  }else{
    			  btnTreeExpand.text('展开');
    		  }
          }
	  });

	  //监听tab页删除
	  element.on('tabDelete(layerTreeTab)', function(data){
		  btnTreeExpand.text('展开');
		  layerTree.outOfBimTreeScene();
          layerTree.removeAllLayers();
          //根据场景类型初始化zTree
		  // if($("#tt").length>0) layerTree.initTree(jsonObj, "");
		  //选中配电房注记、倾斜摄影
		  selectedLinePipes(["TRANSFORMER_ROOM_ANNOTATION"]);
	  });


	  //按钮点击
	  $('.site-demo-active').on('click', function(){
		  var type = $(this).data('type')
	      if(type == 'treeExpand'){//点击“收起/展开”按钮
	    	  if(currentTabId == 'tabLayer'){
	    		  if(layerTree.toggleExpandState()){
	    			  btnTreeExpand.text('收起');
	    		  }else{
	    			  btnTreeExpand.text('展开');
	    		  }
	          }else if(currentTabId == 'tabBimLayer'){
	        	  if(bimLayerTree.toggleExpandState()){
	    			  btnTreeExpand.text('收起');
	    		  }else{
	    			  btnTreeExpand.text('展开');
	    		  }
	          }
	      }
	  });
});


//地图按钮（一级菜单）点击事件
$('.mapBtnList').on('click', 'li', function () {
	var type = $(this).find('i').eq(0).attr('class');
	switch (type) {
		case "iconfont icon-fushi": // 俯视
			layerTree.lookDown();
			break;
		case "iconfont icon-jia"://放大（镜头拉近）
			viewer.camera.zoomIn(viewer.camera.positionCartographic.height / Math.abs(Math.sin(viewer.camera.pitch)) * 0.2);
			break;
		case "iconfont icon-jian"://缩小（镜头拉远）
			viewer.camera.zoomOut(viewer.camera.positionCartographic.height / Math.abs(Math.sin(viewer.camera.pitch)) * 0.2);
			break;
		case "iconfont icon-icon"://正北(还原至初始状态)
			viewer.scene.camera.flyTo({
				destination: new Cesium.Cartesian3.fromDegrees(114.34268, 30.47686, 1),
				orientation: {
					heading: Cesium.Math.toRadians(-10),
					pitch: Cesium.Math.toRadians(-5),
					roll: Cesium.Math.toRadians(0),
				},
				duration: 3.0
			});
			break;
		case "iconfont icon-huabanfuben"://图例
			$(this).find('div').toggleClass("hide");
			$(this).find('span').toggleClass("active");
			break;
		case "iconfont icon-dishangliulan"://地上透明
			var treeNode = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY");
			layerTree.zTree.selectNode(treeNode);
			if (treeNode.checked) {
				layerTree.setAlpha(treeNode, 0.5);
				layerTree.openAlphaBar();//显示透明度工具条
			} else {
				console.log("未勾选倾斜摄影图层!");
			}
			break;
		case "iconfont icon-dixialiulan"://地下浏览
			//viewer.scene.kq3dUndergroundManager.undergroundEnabled = !viewer.scene.kq3dUndergroundManager.undergroundEnabled;
			viewer.camera.setView({
				destination: Cesium.Cartesian3.fromRadians(viewer.camera.positionCartographic.longitude, viewer.camera.positionCartographic.latitude, 35.0),
				orientation: {
					pitch: Cesium.Math.toRadians(-5)
				}
			});
			break;
		case "iconfont icon-fenping"://卷帘
			$(this).find('span').toggleClass("active");
			if ($(this).find('span').hasClass("active")) {
				var slider = document.getElementById('slider');
				var treeNode = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY");
				var primitive = viewer.scene.primitives.getPrimitiveByGuid(treeNode.guid);
				screenSplit.init(slider, primitive);
			} else {
				screenSplit.destroy();
			}
			break;
		case "iconfont icon-xuanqu"://选取
			$(this).find('span').toggleClass("active");
			if ($(this).find('span').hasClass("active")) {
				// HTML overlay for showing feature name on mouseover
				// var nameOverlay = document.createElement('div');
				// viewer.container.appendChild(nameOverlay);
				// nameOverlay.id = 'nameOverlay';
				// nameOverlay.className = 'backdrop';
				// nameOverlay.style.display = 'none';
				// nameOverlay.style.position = 'absolute';
				// nameOverlay.style.bottom = '0';
				// nameOverlay.style.left = '0';
				// nameOverlay.style['pointer-events'] = 'none';
				// nameOverlay.style.padding = '4px';
				// nameOverlay.style.color = '#1ef0ed';
				// nameOverlay.style.border = '1px solid #29dad0';
				// nameOverlay.style['-webkit-box-shadow'] = 'inset 0 0 0.5vw rgba(63,255,255,1)';
				// nameOverlay.style['-moz-box-shadow'] = 'inset 0 0 0.5vw rgba(63,255,255,1)';
				// nameOverlay.style['box-shadow'] = 'inset 0 0 0.5vw rgba(63,255,255,1)';
				// nameOverlay.style.backgroundColor = 'rgba(11, 97, 112, 0.6)';
				pickFeature.start("Feature", function (pickedFeature) {
					if(typeof pickedFeature.getPropertyNames === "undefined") return;
					var properties = pickedFeature.getPropertyNames() || [];
					var trs = '';
					//判断获取的是否是管线模型
					var pipeName;
					var pipeInfoArr;
					if (pickedFeature && pickedFeature.tileset) {
						pipeName = pickedFeature.tileset._name;
						for (var i = 0; i < pipeLayerProperties.config.layer.length; i++) {
							if (pipeLayerProperties.config.layer[i].id == pipeName) {
								pipeInfoArr = pipeLayerProperties.config.layer[i];
							}
						}
					}
					properties.forEach(function (property) {
						if (property.toLowerCase() !== 'id') {
							if (pipeName && pipeName.indexOf("管线") > -1 && pipeInfoArr) {
								trs = trs + '<tr><th>' + pipeInfoArr[property] + '</th><td>' + pickedFeature.getProperty(property) + '</td></tr>';
							} else {
								trs = trs + '<tr><th>' + property + '</th><td>' + pickedFeature.getProperty(property) + '</td></tr>';
							}
						}
					});
					//this.selectedEntity.description = '<table class="cesium-infoBox-defaultTable"><tbody>' + trs + '</tbody></table>';
					if (pickType == "zsPoint") {

					} else {
						//属性弹窗
						openAttributeLayer(trs, hlzsId, "hlzs");
					}
				});
			} else {
				pickFeature.stop();
			}
			break;
		case "iconfont icon-shanchu-copy-copy"://清除
			if (measureHandler) measureHandler.clear();//清除测量信息
			if (viewer) layerTree.removeLocatedBuilding();//清除水表、渗漏定位信息
			if ($(this).siblings().eq(5).find('span').hasClass("active")) {//清除卷帘状态
				screenSplit.destroy();
				$(this).siblings().eq(5).find('span').removeClass("active");
			}
			if ($(this).siblings().eq(2).find('span').hasClass("active")) {//清除拾取状态
				pickFeature.stop();
				$(this).siblings().eq(2).find('span').removeClass("active");
			}
			break;
		case "iconfont icon-quanping"://全屏
			if (Cesium.Fullscreen.fullscreen) {
				Cesium.Fullscreen.exitFullscreen();
			} else {
				Cesium.Fullscreen.requestFullscreen(document.body);
			}
			break;
		case "iconfont icon-quantu"://全图
			layerTree.locateHome();
			break;
		case "iconfont icon-ic_shuiliu"://水流流向
			$(this).find('span').toggleClass("active");
			if ($(this).find('span').hasClass("active")) {
				GFTS.warterDirection();
			} else {
				GFTS.clearWaterDirection();
			}
			break;
		case "iconfont icon-dianliu"://电流流向
			$(this).find('span').toggleClass("active");
			if ($(this).find('span').hasClass("active")) {
				LZXD.drawPowerLine();
			} else {
				LZXD.clearPowerDirection();
			}
			break;
		default:
	}
});

//地图按钮（二级菜单）点击事件
$('.mapIconCont,.ruler').on('click', 'span', function() {
	var type = $(this).text();
	switch (type) {
	case "水平测量"://测量水平距离
		measureHandler.startMeasure(Cesium.MeasureMode.Distance);
		break;
	case "水平面积"://测量水平面积
		measureHandler.startMeasure(Cesium.MeasureMode.Area);
		break;
	case "坐标测量"://坐标测量
		   measureHandler.startMeasure(Cesium.MeasureMode.Coordinate);
		break;
	case "地表面积"://测量地表面积
		measureHandler.startMeasure(Cesium.MeasureMode.TerrainArea);
		break;
	case "地表测量"://测量地表距离
		 measureHandler.startMeasure(Cesium.MeasureMode.TerrainDistance);
		break;
	case "垂直测量"://垂直距离测量（结果为两点间的垂直距离）
		 measureHandler.startMeasure(Cesium.MeasureMode.VerticalDistance);
		break;
	case "净距测量"://地表距离测量（结果是模型表面两点间距离）
		 measureHandler.startMeasure(Cesium.MeasureMode.ModelDistance);
		break;
	default:
	}
});
