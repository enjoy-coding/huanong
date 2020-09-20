/**
 * Kq3dEllipseFadeMaterialProperty.js short summary.
 * 
 * 椭圆渐变材质属性.
 * 
 * @author liuyimeng  2019/02/25 13:57:00
 */
(function() {
    Cesium.Material.EllipseFadeType = "Kq3dEllipseFade";
    Cesium.Material._materialCache.addMaterial(Cesium.Material.EllipseFadeType, {
        fabric: {
            type: Cesium.Material.EllipseFadeType,
            uniforms: {
                color: new Cesium.Color(1, 0, 0, 1),
                time: 1
            },
            source: `
czm_material czm_getMaterial(czm_materialInput materialInput)
{ 
    czm_material material = czm_getDefaultMaterial(materialInput);
    material.diffuse = 1.5 * color.rgb;
    vec2 st = materialInput.st;
    float dis = distance(st,vec2(0.5,0.5));
    float per = fract(time);
    if(dis > per * 0.5){
        discard;
    }else {
        material.alpha = color.a * dis / per / 2.0 ;
    }
    return material;
}

`
        },
        translucent: true
    });


    /**
     * A {@link MaterialProperty} that maps to ellipse fade {@link Material} uniforms.
     * 一种材质属性映射到椭圆渐变材质
     * @alias Kq3dEllipseFadeMaterialProperty
     * @constructor
     *
     * @param {Object} [options] Object with the following properties:
     *                     对象包含以下属性：
     * @param {Property} [options.color=Color.WHITE] A Property specifying the {@link Color} of the ellipse.
     *                     指定椭圆的颜色.          
     * 
     * @example
     *    viewer.entities.add({
     *       position: Cesium.Cartesian3.fromDegrees(114.569874, 35.2565445, 100),
     *       ellipse: {
     *           height: 0,
     *           semiMinorAxis: 5e4,
     *           semiMajorAxis: 5e4,
     *           material: new Cesium.Kq3dEllipseFadeMaterialProperty({
     *               color: Cesium.Color.WHITE
     *           })
     *       }
     *    });
     * 
     */
    function Kq3dEllipseFadeMaterialProperty(options) {
        this._definitionChanged = new Cesium.Event();
        this._color = undefined;
        this._colorSubscription = undefined;
        this.color = options.color;
        this._time = undefined;
    }

    Cesium.defineProperties(Kq3dEllipseFadeMaterialProperty.prototype, {
        /**
         * Gets a value indicating if this property is constant.  A property is considered
         * constant if getValue always returns the same result for the current definition.
         * @memberof Kq3dEllipseFadeMaterialProperty.prototype
         * @type {Boolean}
         * @readonly
         */
        isConstant: {
            get: function() {
                return Cesium.Property.isConstant(this._color);
            }
        },
        /**
         * Gets the event that is raised whenever the definition of this property changes.
         * The definition is considered to have changed if a call to getValue would return
         * a different result for the same time.
         * @memberof Kq3dEllipseFadeMaterialProperty.prototype
         * @type {Event}
         * @readonly
         */
        definitionChanged: {
            get: function() {
                return this._definitionChanged;
            }
        },
        /**
         * Gets or sets the Property specifying the {@link Color} of the ellipse.
         * @memberof Kq3dEllipseFadeMaterialProperty.prototype
         * @type {Property}
         */
        color: Cesium.createPropertyDescriptor("color")
    });

    /**
     * Gets the {@link Material} type at the provided time.
     *
     * @param {JulianDate} time The time for which to retrieve the type.
     * @returns {String} The type of material.
     */
    Kq3dEllipseFadeMaterialProperty.prototype.getType = function(time) {
        return "Kq3dEllipseFade";
    };

    /**
     * Gets the value of the property at the provided time.
     *
     * @param {JulianDate} time The time for which to retrieve the value.
     * @param {Object} [result] The object to store the value into, if omitted, a new instance is created and returned.
     * @returns {Object} The modified result parameter or a new instance if the result parameter was not supplied.
     */
    Kq3dEllipseFadeMaterialProperty.prototype.getValue = function(time, result) {
        if (!Cesium.defined(result)) {
            result = {};
        }
        result.color = Cesium.Property.getValueOrClonedDefault(this._color, time, Cesium.Color.WHITE, result.color);
        Cesium.defined(this._time) || (this._time = time.secondsOfDay);
        result.time = time.secondsOfDay - this._time;

        return result;
    };

    /**
     * Compares this property to the provided property and returns
     * <code>true</code> if they are equal, <code>false</code> otherwise.
     *
     * @param {Property} [other] The other property.
     * @returns {Boolean} <code>true</code> if left and right are equal, <code>false</code> otherwise.
     */
    Kq3dEllipseFadeMaterialProperty.prototype.equals = function(other) {
        return this === other ||
            (other instanceof Kq3dEllipseFadeMaterialProperty &&
                Cesium.Property.equals(this._color, other._color));
    };

    Cesium.Kq3dEllipseFadeMaterialProperty = Kq3dEllipseFadeMaterialProperty;

})();