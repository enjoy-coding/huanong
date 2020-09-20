/**
 * kqWeb3dLibsInclude.js 
 * description:load kqWeb3d development javascript library; Auto running
 */
(function() {
    var isWinRT = (typeof Windows === "undefined") ? false : true;
    var r = new RegExp("(^|(.*?\\/))(kqWeb3dLibsInclude\.js)(\\?|$)"),
        scripts = document.getElementsByTagName('script'),
        src = "",
        mUrl = [],
        baseurl = "";
    for (var i = 0; i < scripts.length; i++) {
        src = scripts[i].getAttribute('src');
        if (src) {
            var mUrl = src.match(r);
            if (mUrl) {
                baseurl = mUrl[1];
                break;
            }
        }
    }
    //import javascript lib
    function importJavaScriptLib(jsurl) {
        if (!isWinRT) {
            var script = '<' + 'script type="text/javascript" src="' + jsurl + '"' + '><' + '/script>';
            document.writeln(script);
        } else {
            var script = document.createElement("script");
            script.src = jsurl;
            document.getElementsByTagName("head")[0].appendChild(script);
        }
    };
    //load javascript lib
    function loadKqWeb3DLibs() {
        importJavaScriptLib(baseurl + 'Kq3dUtil.js');
        importJavaScriptLib(baseurl + 'GeoJsonExtendDataSource.js');
        importJavaScriptLib(baseurl + 'LayerLabelCollection.js');
        importJavaScriptLib(baseurl + 'Kq3dODLineMaterialProperty.js');
        importJavaScriptLib(baseurl + 'Kq3dCreateODLinesPrimitive.js');
        importJavaScriptLib(baseurl + 'Kq3dEllipseFadeMaterialProperty.js');
        importJavaScriptLib(baseurl + 'Kq3dPolylineTrailLinkMaterialProperty.js');
        importJavaScriptLib(baseurl + 'TiandituImageryProvider.js');
    };
    loadKqWeb3DLibs();
})();