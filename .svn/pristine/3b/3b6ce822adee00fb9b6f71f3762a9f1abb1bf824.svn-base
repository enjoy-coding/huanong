var mapViewer3D = (function () {
    var viewer;
    var isPCBrowser = Cesium.FeatureDetection.isPCBrowser();
    if (isPCBrowser) {
        viewer = new Cesium.Viewer('cesiumContainer',
            {
                imageryProvider: new Cesium.SingleTileImageryProvider(
                    {
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
        viewer = new Cesium.Viewer('cesiumContainer',
            {
                imageryProvider: new Cesium.SingleTileImageryProvider(
                    {
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
    viewer.imageryLayers.addImageryProvider(new Cesium.ArcGisMapServerImageryProvider(options));
    var tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
        url: 'http://27.17.43.14:8585/aupdata/html/data/tileset.json'
    }));
    //白模
    viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
        url: 'http://27.17.43.14:8585/aupdata/html/bm/tileset.json'
    }));

    //蓝模
    // viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
    //     url: 'http://27.17.43.14:8585/aupdata/html/bm2/tileset.json'
    // }));
    viewer.zoomTo(tileset);



    map.renderP(viewer, featherCmsScript.ctx +'bus/smartcommunity/json/SQ_M.json', '#4BE1EE');
    map.renderP(viewer, featherCmsScript.ctx +'bus/smartcommunity/json/XQ_M.json', '#3CBF9C');

    map.addLayerName(viewer, featherCmsScript.ctx +'bus/smartcommunity/json/SQ_D.json','SQ');
    map.addLayerName(viewer, featherCmsScript.ctx +'bus/smartcommunity/json/XQ_D.json','XQ');
    return {
        viewer: viewer
    };
})();



