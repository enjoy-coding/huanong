/**
 * Kq3dUtil.js short summary.
 * 
 * Cesium扩展的多功能函数.
 * 
 * @author liuyimeng  2019/05/29 00:24:00
 */
(function() {
    /**
     * Cesium扩展的多功能函数
     * 
     * @alias Kq3dUtil
     * @constructor
     * 
     */
    function Kq3dUtil() {};

    /**
     * 经纬度坐标数组转换成世界坐标
     * @param {Array} coordinates 经纬度坐标数组
     * @returns{Cartesian3} 世界坐标
     */
    Kq3dUtil.prototype.degrees2Cartesian3 = function(coordinates) {
        return Cesium.Cartesian3.fromDegrees(coordinates[0], coordinates[1], coordinates[2] || 0);
    };

    /**
     * 创建抛物线实体
     * @param  {Object} options 对象包含以下属性：
     *         {Object|Array} options.pt1 起点经纬度坐标，支持两种方式：{lon:125.36544,lat:25.369,height:0}或[125.36544,25.369,0]
     *         {Object|Array} options.pt2 终点经纬度坐标，支持两种方式：{lon:125.36544,lat:25.369,height:0}或[125.36544,25.369,0]
     *         {Double} options.height 抛物线最高点处的高度值
     *         {Int}[options.width=1] 线宽度
     *         {Cesium.Color}[options.color=Cesium.Color.RED] 线颜色
     *         {Double}[options.duration=1000] 持续时间，毫秒
     * @returns  {Cesium.Entity} 包含线的实体对象
     */
    Kq3dUtil.prototype.createParabolicLineEntity = function(options) {
        var that = this;
        options = Cesium.defaultValue(options, {});
        var width = Cesium.defaultValue(options.width, 1.0);
        var positions = that.parabolaEquation(options);
        var entity = undefined;
        if (positions.length > 0) {
            entity = new Cesium.Entity({
                polyline: {
                    positions: positions, //[Cesium.Kq3dUtil.degrees2Cartesian3(cds0), Cesium.Kq3dUtil.degrees2Cartesian3(cds1)], //
                    width: width,
                    material: new Cesium.Kq3dPolylineTrailLinkMaterialProperty({
                        color: options.color,
                        glowPower: options.glowPower,
                        duration: options.duration
                    })
                }
            });
        }
        return entity;
    };

    /**
     * 通过两点（经纬度）生成抛物线
     * @param  {Object} options 对象包含以下属性：
     *         {Object|Array} options.pt1 起点经纬度坐标，支持两种方式：{lon:125.36544,lat:25.369,height:0}或[125.36544,25.369,0]
     *         {Object|Array} options.pt2 终点经纬度坐标，支持两种方式：{lon:125.36544,lat:25.369,height:0}或[125.36544,25.369,0]
     *         {Double} options.height 抛物线最高点处的高度值
     * @returns  {Array<Cesium.Cartesian3>} 抛物线上点集合
     */
    Kq3dUtil.prototype.parabolaEquation = function(options) {
        options = Cesium.defaultValue(options, {});
        if (!Cesium.defined(options.pt1) || !Cesium.defined(options.pt2)) {
            throw new Error('options.pt1 and options.pt2 must require.');
        }

        if (options.pt1 instanceof Array) {
            if (options.pt1.length >= 2) {
                options.pt1 = {
                    lon: options.pt1[0],
                    lat: options.pt1[1],
                    height: options.pt1[2] || 0
                };
            } else {
                return [];
            }

        }
        if (options.pt2 instanceof Array) {
            if (options.pt2.length >= 2) {
                options.pt2 = {
                    lon: options.pt2[0],
                    lat: options.pt2[1],
                    height: options.pt2[2] || 0
                };
            } else {
                return [];
            }
        }

        var that = this;
        //方程 y=-(4h/L^2)*x^2+h h:顶点高度 L：横纵间距较大者
        var h = options.height && options.height > 5000 ? options.height : 5000;
        var L = Math.abs(options.pt1.lon - options.pt2.lon) > Math.abs(options.pt1.lat - options.pt2.lat) ? Math.abs(options.pt1.lon - options.pt2.lon) : Math.abs(options.pt1.lat - options.pt2.lat);
        var num = options.num && options.num > 50 ? options.num : 50;
        var result = [];
        var dlt = L / num;
        if (Math.abs(options.pt1.lon - options.pt2.lon) > Math.abs(options.pt1.lat - options.pt2.lat)) { //以lon为基准
            var delLat = (options.pt2.lat - options.pt1.lat) / num;
            if (options.pt1.lon - options.pt2.lon > 0) {
                dlt = -dlt;
            }
            for (var i = 0; i < num; i++) {
                var tempH = h - Math.pow((-0.5 * L + Math.abs(dlt) * i), 2) * 4 * h / Math.pow(L, 2);
                var lon = options.pt1.lon + dlt * i;
                var lat = options.pt1.lat + delLat * i;
                //result.push([lon, lat, tempH]);
                result.push(that.degrees2Cartesian3([lon, lat, tempH]));
            }
        } else { //以lat为基准
            var delLon = (options.pt2.lon - options.pt1.lon) / num;
            if (options.pt1.lat - options.pt2.lat > 0) {
                dlt = -dlt;
            }
            for (var i = 0; i < num; i++) {
                var tempH = h - Math.pow((-0.5 * L + Math.abs(dlt) * i), 2) * 4 * h / Math.pow(L, 2);
                var lon = options.pt1.lon + delLon * i;
                var lat = options.pt1.lat + dlt * i;
                //result.push([lon, lat, tempH]);
                result.push(that.degrees2Cartesian3([lon, lat, tempH]));
            }
        }

        result.push(that.degrees2Cartesian3([options.pt2.lon, options.pt2.lat, options.pt2.height || 0]));

        return result;
    };

    Kq3dUtil.prototype.createGuid = function() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0,
                v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    };



    Cesium.Kq3dUtil = new Kq3dUtil();
})();