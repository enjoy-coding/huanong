(function() {
    Cesium.Material.ODLineType = 'Kq3dODLine';
    Cesium.Material._materialCache.addMaterial(Cesium.Material.ODLineType, {
        fabric: {
            type: 'Kq3dODLine',
            uniforms: {
                color: new Cesium.Color(1.0, 1.0, 0.0, 1.0),
                totoalFrameCount: 45,
                alphaStep: 0.1
            },
            source: `
czm_material czm_getMaterial(czm_materialInput materialInput)
{
    czm_material material = czm_getDefaultMaterial(materialInput);
    vec2 st = materialInput.st;
    //float t = time;
    float t = mod(czm_frameNumber, totoalFrameCount) / totoalFrameCount; 

    t *= 1.03;
    float alpha = smoothstep(t- 0.03, t, st.s) * step(-t, -st.s); 
    alpha += alphaStep;
    //alpha *= step(-0.4, -abs(0.5-st.t));  

    material.diffuse = color.rgb;
    material.alpha = alpha;
    return material;
}

`,
        },
        translucent: true
    });

    function Kq3dODLineMaterialProperty(color, alphaStep, totoalFrameCount) {
        this._definitionChanged = new Cesium.Event();
        this._color = undefined;
        this._colorSubscription = undefined;
        this.color = color;
        this._totoalFrameCount = undefined;
        this._totoalFrameCountSubscription = undefined;
        this.totoalFrameCount = totoalFrameCount;

        this._alphaStep = undefined;
        this._alphaStepSubscription = undefined;
        this.alphaStep = alphaStep;

    }

    Cesium.defineProperties(Kq3dODLineMaterialProperty.prototype, {
        isConstant: {
            get: function() {
                return Cesium.Property.isConstant(this._color);
            }
        },

        definitionChanged: {
            get: function() {
                return this._definitionChanged;
            }
        },

        color: Cesium.createPropertyDescriptor('color'),

        totoalFrameCount: Cesium.createPropertyDescriptor('totoalFrameCount'),

        alphaStep: Cesium.createPropertyDescriptor('alphaStep')
    });

    Kq3dODLineMaterialProperty.prototype.getType = function(time) {
        return 'Kq3dODLine';
    };

    Kq3dODLineMaterialProperty.prototype.getValue = function(time, result) {
        if (!Cesium.defined(result)) {
            result = {};
        }
        result.color = Cesium.Property.getValueOrClonedDefault(this._color, time, Cesium.Color.WHITE, result.color);
        // totoalFrameCount以后再考虑如何加进去 vtxf 20190122
        result.totoalFrameCount = Cesium.Property.getValueOrClonedDefault(this._totoalFrameCount, time, 45, result.color);

        result.alphaStep = Cesium.Property.getValueOrClonedDefault(this._alphaStep, time, 0.1, result.alphaStep);

        return result;
    };

    Kq3dODLineMaterialProperty.prototype.equals = function(other) {
        return this === other || //
            (other instanceof Kq3dODLineMaterialProperty && //
                Cesium.Property.equals(this._color, other._color));
    };

    Cesium.Kq3dODLineMaterialProperty = Kq3dODLineMaterialProperty;
})();