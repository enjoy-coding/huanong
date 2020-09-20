/**
 * TiandituImageryProvider short summary.
 * Provides tiled imagery using the Tianditu Imagery. 天地图影像服务提供者类
 * @author liuyimeng @KQGIS
 * date 2018/1/16 下午 06:22
 */

(function(){
    /**
     * Provides tiled imagery using the Tianditu Imagery.天地图影像服务提供者类
     *
     * @alias TiandituImageryProvider
     * @constructor
     *
     * @param {Object} options Object with the following properties:
     * @param {String} [options.mapStyle=TiandituMapsStyle.IMG_W] The type of Tianditu Maps imagery to load.
     *                 地图类型.
     * @param {String} [options.key=f70f0b1c42e7fdd3ba0e5e3fe6b99ec5] The key to use request Tianditu Maps imagery.
     *                 用于请求天地图服务的秘钥.
     * @param {Cesium.Credit|String} [options.credit] A credit for the data source, which is displayed on the canvas.
     *
     *
     * @example
     * // Example 1. Global Image by Map World .天地图全球影响
     * var imageProvider1 = new Cesium.TiandituImageryProvider({
     *     mapStyle : Cesium.TiandituMapsStyle.IMG_W,
     *     credit : new Cesium.Cesium.Credit('天地图全球影像')
     * });
     * viewer.imageryLayers.addImageryProvider(imageProvider1);
     *
     * @example
     * // Example 2. Chinese annotation of global image imagery by Map World .天地图全球影像中文注记服务（墨卡托投影）
     * var imageProvider2 = new Cesium.TiandituImageryProvider({
     *     mapStyle : Cesium.TiandituMapsStyle.CIA_W,
     *     key: 'f70f0b1c42e7fdd3ba0e5e3fe6b99ec5',
     *     credit : new Cesium.Cesium.Credit('天地图全球影像中文注记')
     * });
     * viewer.imageryLayers.addImageryProvider(shadedRelief2);
     *
     * @see UrlTemplateImageryProvider
     */
    function TiandituImageryProvider(options) {
        options = Cesium.defaultValue(options, Cesium.defaultValue.EMPTY_OBJECT);
        this._name = Cesium.defaultValue(options.name, undefined);
        this._mapStyle = Cesium.defaultValue(options.mapStyle, Cesium.TiandituMapsStyle.IMG_W);
        //this._url = 'http://t{0-7}.tianditu.com/' + this._mapStyle + '/wmts?';
        this._url = 'https://{s}.tianditu.gov.cn/DataServer?T={Layer}&x={x}&y={y}&l={level}&tk={tk}';
        this._layer = this._mapStyle;
        //this._style = options.style || 'default';
        //this._tileMatrixSetID = options.tileMatrixSetID || 'w';
        //this._tileMatrixLabels = options.tileMatrixLabels;
        // this._format = Cesium.defaultValue(options.format, 'tiles');
        //this._proxy = options.proxy;
        // his._tileDiscardPolicy = options.tileDiscardPolicy;
        this._tilingScheme = Cesium.defined(options.tilingScheme) ? options.tilingScheme : new Cesium.WebMercatorTilingScheme({ ellipsoid: options.ellipsoid });
        this._tileWidth = Cesium.defaultValue(options.tileWidth, 256);
        this._tileHeight = Cesium.defaultValue(options.tileHeight, 256);

        this._subdomains = options.subdomains || ['t0', 't1', 't2', 't3', 't5', 't6', 't7'];//'t4',
        this._key = Cesium.defaultValue(options.key, 'f70f0b1c42e7fdd3ba0e5e3fe6b99ec5');

        var credit = options.credit;
        if (typeof credit === 'string') {
            try {
                credit = new Cesium.Credit({
                    text: credit
                });
            } catch (e) {
                credit = new Cesium.Credit('<div>' + credit + '</div>');
            }
        }

        this._credit = credit;
        var tilingScheme = new Cesium.GeographicTilingScheme();
        this._minimumLevel = Cesium.defaultValue(options.minimumLevel, 0);
        this._maximumLevel = options.maximumLevel || undefined;
        this._customTags = {
           level: function(imageryProvider, x, y, level) {
               return level;
           }
        };
        var customTags = {
           level: function(imageryProvider, x, y, level) {
               return level + 1;
           }
        };

        switch (this._mapStyle) {
            case Cesium.TiandituMapsStyle.CIA_C: //中文影像注记
            case Cesium.TiandituMapsStyle.CVA_C: //中文矢量注记
            case Cesium.TiandituMapsStyle.EIA_C: //英文影像注记
            case Cesium.TiandituMapsStyle.EVA_C: //英文矢量注记
            case Cesium.TiandituMapsStyle.IMG_C: //影像地图
            case Cesium.TiandituMapsStyle.VEC_C: //矢量地图
            case Cesium.TiandituMapsStyle.TER_C: //地形晕渲图
                this._tilingScheme = tilingScheme;
                this._customTags = customTags;
                this._subdomains = ['t0', 't1', 't2', 't3', 't5', 't6', 't7'];//'t4',
                break;
            default:
                break;
        }

        this._rectangle = Cesium.defaultValue(options.rectangle, this._tilingScheme.rectangle);

        //根据变量组合成完成的请求地址
        this._url = this._url.replace('{Layer}', this._layer).replace('{tk}', this._key);
        var that = this;
        Cesium.UrlTemplateImageryProvider.call(this, {
            url: that._url,
            subdomains: that._subdomains,
            credit: that._credit,
            minimumLevel: that._minimumLevel,
            maximumLevel: that._maximumLevel,
            rectangle: that._rectangle,
            tilingScheme: that._tilingScheme,
            tileWidth: that._tileWidth,
            tileHeight: that._tileHeight,
            customTags: that._customTags
        });
    }


    //TiandituImageryProvider 继承自UrlTemplateImageryProvider
    // 创建一个没有实例方法的类
    var Super = function() {};
    Super.prototype = Cesium.UrlTemplateImageryProvider.prototype;
    //将实例作为子类的原型
    TiandituImageryProvider.prototype = new Super();

    Cesium.defineProperties(TiandituImageryProvider.prototype, {
        /**
         * Gets the name of the service hosting the imagery.
         * 获取地图提供者名称
         * @memberof TiandituImageryProvider.prototype
         * @type {String}
         * @readonly
         */
        name: {
            get: function() {
                return this._name;
            }
        },
        /**
         * Gets the URL of the service hosting the imagery.
         * 获取URL地址
         * @memberof TiandituImageryProvider.prototype
         * @type {String}
         * @readonly
         */
        url: {
            get: function() {
                return this._url;
            }
        },

        /**
         * Gets the width of each tile, in pixels. 
         * 获取瓦片宽度
         * @memberof TiandituImageryProvider.prototype
         * @type {Number}
         * @readonly
         */
        tileWidth: {
            get: function() {
                return this._tileWidth;
            }
        },

        /**
         * Gets the height of each tile, in pixels.
         * 获取瓦片高度
         * @memberof TiandituImageryProvider.prototype
         * @type {Number}
         * @readonly
         */
        tileHeight: {
            get: function() {
                return this._tileHeight;
            }
        },

        /**
         * Gets the type of Tinaditu Maps imagery to load.
         * 获取地图类型
         * @memberof TiandituImageryProvider.prototype
         * @type {TiandituMapsStyle}
         * @readonly
         */
        mapStyle: {
            get: function() {
                return this._mapStyle;
            }
        },

        /**
         * Gets the maximum level-of-detail that can be requested. 
         * 获取请求的最大级别
         * @memberof TiandituImageryProvider.prototype
         * @type {Number}
         * @readonly
         */
        maximumLevel: {
            get: function() {
                return this._maximumLevel;
            }
        },

        /**
         * Gets the minimum level-of-detail that can be requested. 
         * 获取请求的最小级别
         * @memberof TiandituImageryProvider.prototype
         * @type {Number}
         * @readonly
         */
        minimumLevel: {
            get: function() {
                return this._minimumLevel;
            }
        },

        /**
         * Gets the tiling scheme used by this provider. 
         * 获取瓦片划分方案
         * @memberof TiandituImageryProvider.prototype
         * @type {TilingScheme}
         * @readonly
         */
        tilingScheme: {
            get: function() {
                return this._tilingScheme;
            }
        },

        /**
         * Gets the rectangle, in radians, of the imagery provided by this instance.
         * 获取包围盒
         * @memberof TiandituImageryProvider.prototype
         * @type {Cesium.Rectangle}
         * @readonly
         */
        rectangle: {
            get: function() {
                return this._rectangle;
            }
        },

        /**
         * Gets the key of images returned by this imagery provider.
         * 获得地图提供者的秘钥.
         * @memberof TiandituImageryProvider.prototype
         * @type {String}
         * @readonly
         */
        key: {
            get: function() {
                return this._key;
            }
        },

        /**
         * Gets the credit to display Cesium.when this imagery provider is active. 
         * 获取地图认证.
         * @memberof TiandituImageryProvider.prototype
         * @type {Cesium.Credit}
         * @readonly
         */
        credit: {
            get: function() {
                return this._credit;
            }
        },

        /**
         * Gets a value indicating whether or not the images provided by this imagery provider
         * include an alpha channel. 
         * 获取是否有alpha通道.
         * @memberof TiandituImageryProvider.prototype
         * @type {Boolean}
         * @readonly
         */
        hasAlphaChannel: {
            get: function() {
                return true;
            }
        }
    });

    /**
     * Gets the credits to be displayed Cesium.when a given tile is displayed.
     *
     * @param {Number} x The tile X coordinate.
     * @param {Number} y The tile Y coordinate.
     * @param {Number} level The tile level;
     * @returns {Cesium.Credit[]} The credits to be displayed Cesium.when the tile is displayed.
     *
     * @exception {Cesium.DeveloperError} <code>getTileCredits</code> must not be called before the imagery provider is ready.
     */
    TiandituImageryProvider.prototype.getTileCredits = function(x, y, level) {
        return undefined;
    };
    
    Cesium.TiandituImageryProvider = TiandituImageryProvider;
})();
