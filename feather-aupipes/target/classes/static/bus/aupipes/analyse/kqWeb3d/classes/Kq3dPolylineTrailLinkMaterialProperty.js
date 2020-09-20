/**
 * Kq3dPolylineTrailLinkMaterialProperty.js short summary.
 * 
 * 流动纹理线.
 * 
 * @author liuyimeng  2019/05/28 23:57:00
 */
(function() {

    Cesium.Material.PolylineTrailLinkType = "Kq3dPolylineTrailLink";
    Cesium.Material._materialCache.addMaterial(Cesium.Material.PolylineTrailLinkType, {
        fabric: {
            type: Cesium.Material.PolylineTrailLinkType,
            uniforms: {
                color: new Cesium.Color(1.0, 0.0, 0.0, 0.5),
                // image: Cesium.Material.PolylineTrailLinkImage,
                glowPower: 0.0,
                time: 0
            },
            source: `
czm_material czm_getMaterial(czm_materialInput materialInput)
{
    czm_material material = czm_getDefaultMaterial(materialInput);
    vec2 st = materialInput.st;
    if(glowPower > 0.0) // 大于0时 使用发光线
    {
        float glow = glowPower / abs(st.t - 0.5) - (glowPower / 0.5);
        vec4 fragColor;
        // fragColor.rgb = mix(vec3(0.0, 0.0, 1.0), color.rgb, 1.0 - glow);
        fragColor.rgb = max(vec3(glow - 1.0 + color.rgb), color.rgb);
        fragColor.a = clamp(0.0, 1.0, glow) * color.a;
        //fragColor = czm_gammaCorrect(fragColor);

        material.emission = fragColor.rgb;
        material.alpha = (st.s < time)?fragColor.a:0.0;
    }
    else
    {
        material.alpha = (st.s < time)?st.s+0.8:0.0;
        material.diffuse = color.rgb;
    }
    
    return material;
}
`
                /* `
czm_material czm_getMaterial(czm_materialInput materialInput)
{
    czm_material material = czm_getDefaultMaterial(materialInput);
    vec2 st = materialInput.st;
    //vec4 colorImage = texture2D(image, vec2(fract(st.s - time), st.t));
    //material.alpha = colorImage.a * color.a;
    //material.diffuse = (colorImage.rgb+color.rgb)/2.0;
    material.alpha = (st.s < time)?st.s+0.8:0.0;
    material.diffuse = color.rgb;
    return material;
}

`
*/
        },
        translucent: true
    });


    /**
     * A {@link MaterialProperty} that maps to polyline fade {@link Material} uniforms.
     * 流动纹理线
     * @alias Kq3dPolylineTrailLinkMaterialProperty
     * @constructor
     *
     * @param {Object} [options] Object with the following properties:
     *                     对象包含以下属性：
     * @param {Property} [options.color=Color.RED] A Property specifying the {@link Color} of the polyline.
     *                     指定流动线颜色.          
     * @param {Double} [options.duration=1000] A Property specifying the duration of the polyline.
     *                     指定流动线持续时间. 
     * @param {Double} [options.glowPower=0.0] A Property specifying the glow power of the polyline.
     *                     发光力度. 
     * 
     * @example
     *    var positions = [
     *        Cesium.Cartesian3.fromDegrees(114.569874, 35.2565445, 100),
     *        Cesium.Cartesian3.fromDegrees(112.569874, 30.2565445, 100)
     *    ];
     *    viewer.entities.add({
     *       position: Cesium.Cartesian3.fromDegrees(114.569874, 35.2565445, 100),
     *       polyline: {
     *           positions:positions,
     *           material: new Cesium.Kq3dPolylineTrailLinkMaterialProperty({
     *               color: Cesium.Color.YELLOW,
     *               duration:5000
     *           })
     *       }
     *    });
     * 
     */
    function Kq3dPolylineTrailLinkMaterialProperty(options) {
        this._definitionChanged = new Cesium.Event();
        this._color = undefined;
        this._colorSubscription = undefined;
        this.color = Cesium.defaultValue(options.color, Cesium.Color.RED);
        this.duration = Cesium.defaultValue(options.duration, 1000);
        this._time = (new Date()).getTime();
        this.glowPower = Cesium.defaultValue(options.glowPower, 0.0);
    }

    Cesium.defineProperties(Kq3dPolylineTrailLinkMaterialProperty.prototype, {
        /**
         * Gets a value indicating if this property is constant.  A property is considered
         * constant if getValue always returns the same result for the current definition.
         * @memberof Kq3dPolylineTrailLinkMaterialProperty.prototype
         * @type {Boolean}
         * @readonly
         */
        isConstant: {
            get: function() {
                return false;
            }
        },
        /**
         * Gets the event that is raised whenever the definition of this property changes.
         * The definition is considered to have changed if a call to getValue would return
         * a different result for the same time.
         * @memberof Kq3dPolylineTrailLinkMaterialProperty.prototype
         * @type {Event}
         * @readonly
         */
        definitionChanged: {
            get: function() {
                return this._definitionChanged;
            }
        },
        /**
         * Gets or sets the Property specifying the {@link Color} of the polyline.
         * @memberof Kq3dPolylineTrailLinkMaterialProperty.prototype
         * @type {Property}
         */
        color: Cesium.createPropertyDescriptor('color')
    });

    /**
     * Gets the {@link Material} type at the provided time.
     *
     * @param {JulianDate} time The time for which to retrieve the type.
     * @returns {String} The type of material.
     */
    Kq3dPolylineTrailLinkMaterialProperty.prototype.getType = function(time) {
        return 'Kq3dPolylineTrailLink';
    };

    /**
     * Gets the value of the property at the provided time.
     *
     * @param {JulianDate} time The time for which to retrieve the value.
     * @param {Object} [result] The object to store the value into, if omitted, a new instance is created and returned.
     * @returns {Object} The modified result parameter or a new instance if the result parameter was not supplied.
     */
    Kq3dPolylineTrailLinkMaterialProperty.prototype.getValue = function(time, result) {
        if (!Cesium.defined(result)) {
            result = {};
        }
        result.color = Cesium.Property.getValueOrClonedDefault(this._color, time, Cesium.Color.WHITE, result.color);
        result.image = Cesium.Material.PolylineTrailLinkImage;
        //result.time = (((new Date()).getTime() - this._time)- this.duration) / this.duration;
        result.time = Math.min(((new Date()).getTime() - this._time), this.duration) / this.duration;
        //console.log(this._name, result.time);
        result.glowPower = this.glowPower;

        return result;
    };

    /**
     * Compares this property to the provided property and returns
     * <code>true</code> if they are equal, <code>false</code> otherwise.
     *
     * @param {Property} [other] The other property.
     * @returns {Boolean} <code>true</code> if left and right are equal, <code>false</code> otherwise.
     */
    Kq3dPolylineTrailLinkMaterialProperty.prototype.equals = function(other) {
        return this === other ||
            (other instanceof Kq3dPolylineTrailLinkMaterialProperty &&
                Cesium.Property.equals(this._color, other._color) &&
                Math.abs(this._time - other._time) < 0.001 &&
                Math.abs(this.duration - other.duration) < 0.001 &&
                Math.abs(this.glowPower - other.glowPower) < 0.001
            );
    };


    Cesium.Kq3dPolylineTrailLinkMaterialProperty = Kq3dPolylineTrailLinkMaterialProperty;

})();