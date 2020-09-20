var map = {
    geometryEntityArr: [],
    index: 0,
    //边界渲染
    renderP: function (viewer, url, col) {
        $.ajax({
            "async": false,
            "url": featherCmsScript.ctx + url,
            "type": 'GET',
            "dataType": 'json',
            "success": function (data) {
                for (var i = 0; i < data.features.length; i++) {
                    var height = data.features[i].properties.Z;
                    var points = [];
                    for (var coord of data.features[i].geometry.coordinates[0]) {
                        points.push(coord[0], coord[1], height + 5);
                    }
                    color = Cesium.Color.fromCssColorString(col);
                    map.createWall(viewer, data.features[i], color, height)
                }
            }
        });
    },
    //加载对应设备的图标
    addLayerPic: function (type) {
        var id, url, img;
        if (type == 'SXT') {
            id = 'SXTID';
            url = 'bus/smartcommunity/json/SXT.json';
            img = 'bus/smartcommunity/img/camera-selected.png';
        } else if (type == 'MJ') {
            id = 'MJID';
            url = 'bus/smartcommunity/json/MJ.json';
            img = 'bus/smartcommunity/img/door-selected.png';
        } else if (type == 'ZJ') {
            id = 'ZJID';
            url = 'bus/smartcommunity/json/ZJ.json';
            img = 'bus/smartcommunity/img/zj-selected.png';
        } else if (type == 'DG') {
            id = 'DGID';
            url = 'bus/smartcommunity/json/DG.json';
            img = 'bus/smartcommunity/img/dg-selected.png';
        } else if (type == 'LJT') {
            id = 'LJTID';
            url = 'bus/smartcommunity/json/LJT.json';
            img = 'bus/smartcommunity/img/ljt-selected.png';
        } else if (type == 'YG') {
            id = 'YGID';
            url = 'bus/smartcommunity/json/YG.json';
            img = 'bus/smartcommunity/img/smoke-selected.png';
        } else if (type == 'CDZ') {
            id = 'CDZID';
            url = 'bus/smartcommunity/json/CDZ.json';
            img = 'bus/smartcommunity/img/cdz.png';
        } else if (type == 'LD') {
            id = 'LDID';
            url = 'bus/smartcommunity/json/ZNLD.json';
            img = 'bus/smartcommunity/img/light-selected.png';
        } else if (type == 'XFS') {
            id = 'XFSID';
            url = 'bus/smartcommunity/json/XFS.json';
            img = 'bus/smartcommunity/img/xfs-selected.png';
        } else if (type == 'YC') {
            id = 'YCID';
            url = 'bus/smartcommunity/json/YC.json';
            img = 'bus/smartcommunity/img/warn-selected.png';
        } else if (type == 'FW') {
            id = 'LDID';
            url = 'bus/smartcommunity/json/LD_D.json';
            img = 'bus/smartcommunity/img/xq-selected.png';
        } else if (type == 'RY') {
            id = 'LDID';
            url = 'bus/smartcommunity/json/LD_D.json';
            img = 'bus/smartcommunity/img/xq.png';
        }

        $.ajax({
            "async": false,
            "url": featherCmsScript.ctx + url,
            "type": 'GET',
            "dataType": 'json',
            "success": function (data) {
                //先清除之前的图标
                map.clearPic();
                for (var i = 0; i < data.features.length; i++) {
                    var citizensBankPark = mapViewer3D.viewer.entities.add({
                        //name: '摄像头',
                        code: data.features[i].properties[id],
                        position: Cesium.Cartesian3.fromDegrees(data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], data.features[i].geometry.coordinates[2]),
                        /*point: { //点
                            pixelSize: 20,
                            color: Cesium.Color.RED,
                            outlineColor: Cesium.Color.WHITE,
                            outlineWidth: 1
                        },
                        label: { //文字标签
                            text: '摄像头',
                            font: '12pt monospace',
                            style: Cesium.LabelStyle.FILL,
                            outlineWidth: 2,
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM, //垂直方向以底部来计算标签的位置
                            pixelOffset: new Cesium.Cartesian2(0, -9)   //偏移量
                        },*/
                        billboard: { //图标
                            image: featherCmsScript.ctx + img,
                            width: 48,
                            height: 48,
                            scale: 1,//和原始大小相比的缩放比例
                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                        }
                    });
                    map.geometryEntityArr.push(citizensBankPark);
                    //mapViewer3D.viewer.zoomTo(mapViewer3D.viewer.entities);
                    mapViewer3D.viewer.flyTo(mapViewer3D.viewer.entities, {
                        offset: {
                            heading: Cesium.Math.toRadians(0.0),
                            pitch: Cesium.Math.toRadians(-30),
                            range: 1000
                        }
                    });
                }
                mapViewer3D.viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                    var scene = mapViewer3D.viewer.scene;
                    if (scene.mode !== Cesium.SceneMode.MORPHING) {
                        var pickedObject = scene.pick(movement.position);
                        if (pickedObject && pickedObject.id && pickedObject.id._code) {
                            var url;
                            if (type == 'FW') {
                                url = featherCmsScript.ctx + 'screen/api/getRyXx?ldid=' + pickedObject.id._code;
                            } else if (type == 'RY') {
                                url = featherCmsScript.ctx + 'screen/api/getZdRy?ldid=' + pickedObject.id._code;
                            } else {
                                url = featherCmsScript.ctx + 'screen/api/getSbJc?type=' + type + '&id=' + pickedObject.id._code;
                            }
                            $.ajax({
                                "async": false,
                                "url": url,
                                "type": 'GET',
                                "dataType": 'json',
                                "success": function (data) {
                                    map.layerOpenInfo(type, data);
                                }
                            });
                        }
                    }
                }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

            }
        });
    },
    //打开详细信息
    layerOpenInfo: function (type, data) {
        var html = '', html1 = '', area;
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
                    <div id="mjswiperWrapper" class="swiperWrapper">
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
                <div id="GateWiperWrapper" class="swiperWrapper">
                    <ul class="vehicleList swiper-wrapper">`;
            for (var i = 0; i < data.data.length; i++) {
                html += `<li class="swiper-slide">
                                        <img class="img" src="images/video_img1.png" alt="" />
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
                    <span class="color_white font_14">在线</span>
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
            $('#houseLayer').html('');
            html += `<div class="houseLayerMain">
                    <h4 class="moveTxt">` + data.data.ldmc + `(` + data.data.num + `人)</h4>
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
                    html += `<li class="` + style + `" onclick="map.lookHouseDetail(\`` + data.data.fwXx[key][i].FWID + `\`)">
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
                          <!--<div class="layui-col-md12 KeyPersonnelIcon pr">
                            <div class="SearchBox pa">
                            <input type="text" />
                            <i class="iconfont iconsousuo pa searchIco"></i>
                      </div>-->
                      <ul class="ResidenceDetailList">`;
            for (var i = 0; i < data.data.list.length; i++) {
                var arr = data.data.list[i].ZZ.split(data.data.ldmc);
                html += `<li>
            <img class="img" src="images/video_img1.png" alt="" />
                <div class="layui-row">
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
                <span class="color_white">是否房主:</span>
            <span class="color_white">` + data.data.list[i].SFFZ + `</span>
                </p>
                <p>
                    <span class="color_white">门牌号:</span>
                    <span class="color_white">` + arr[1] + `</span>
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
                <!--<div class="layui-col-md6">
                
            </div>-->
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
            id: type + map.index,
            success: function (index, layero) {
                map.index++;

                featherCmsScript.register({
                    element: "#GateWiperWrapper",
                    onLoad: function (cmsOptions) {
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
                        //鼠标覆盖停止自动切换
                        Gatewiper.el.onmouseover = function () {
                            Gatewiper.autoplay.stop();
                        }
                        //鼠标离开开始自动切换
                        Gatewiper.el.onmouseout = function () {
                            Gatewiper.autoplay.start();
                        }
                    },
                    onResize: function (cmsOptions) {
                    },
                    onClick: function (cmsOptions) {
                    }
                });
            },
            cancel: function (index, layero) {
                layer.close(index)
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
                        html += `<li>
                          <img class="img" src="images/video_img1.png" alt="" />
                          <div class="layui-row">
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
                                <span class="color_white">是否房主:</span>
                                  <span class="color_white">` + data.data[i].SFFZ + `</span>
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
                               <p>
                                <span class="color_white">电话:</span>
                                  <span class="color_white">` + data.data[i].DH + `</span>
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
    //全图
    full: function () {
        mapViewer3D.viewer.scene.camera.flyTo({
            destination: new Cesium.Cartesian3.fromDegrees(121.34612, 31.14985, 500),
            orientation: {
                heading: Cesium.Math.toRadians(0),
                pitch: Cesium.Math.toRadians(-30.0),
                roll: Cesium.Math.toRadians(0),
            },
            duration: 3.0
        });
    },
    //创建墙体，也是面
    createWall: function (viewer, polygon, color, height) {
        var origin = turf.centroid(polygon).geometry.coordinates;//获取polygon的质心
        var bigPolygon = turf.transformScale(polygon, 1000, {origin: origin});//按照质心扩大多少倍
        var line = turf.polygonToLine(bigPolygon);//把面转化为线
        var wall = turf.buffer(line, 2);//创建缓冲区--变回面
        wall = turf.intersect(wall, bigPolygon)//返回相交外面是大的面，里面是小的面
        if (wall == null) return;
        wall = turf.transformScale(wall, 0.001, {origin: origin});//
        var polygonHierarchy = map.createPolygonHierarchy(wall.geometry.coordinates);//获取挖空的面
        var entity = viewer.entities.add({
            polygon: {
                hierarchy: polygonHierarchy,
                height: height,
                resolution: 1,
                extrudedHeight: height + 0.5,
                shadows: Cesium.ShadowMode.ENABLED,
                material: color.withAlpha(1)
            }
        });
    },
    createPolygonHierarchy: function (coordinates) {
        var holes = [];
        for (var i = 1, len = coordinates.length; i < len; i++) {
            holes.push(new Cesium.PolygonHierarchy(map.coordinatesArrayToCartesianArray(coordinates[i], map.crsFunction)));
        }//挖洞
        var positions = coordinates[0];
        var polygonHierarchy = new Cesium.PolygonHierarchy(map.coordinatesArrayToCartesianArray(positions, map.crsFunction), holes);
        return polygonHierarchy;
    },
    crsFunction: function (coordinates) {
        return Cesium.Cartesian3.fromDegrees(coordinates[0], coordinates[1], coordinates[2]);
    },
    coordinatesArrayToCartesianArray: function (coordinates, crsFunction) {
        var positions = new Array(coordinates.length);
        for (var i = 0; i < coordinates.length; i++) {
            positions[i] = crsFunction(coordinates[i]);
        }
        return positions;
    },
    //清除图标
    clearPic: function () {
        if (map.geometryEntityArr.length > 0) {
            for (var i in map.geometryEntityArr) {
                mapViewer3D.viewer.entities.remove(map.geometryEntityArr[i]);
            }
            map.geometryEntityArr = [];
        }
        layer.closeAll();

        mapViewer3D.viewer.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
    },
    //加载点状地名
    addLayerName: function (viewer,url, type) {
        $.ajax({
            "async": false,
            "url": featherCmsScript.ctx + url,
            "type": 'GET',
            "dataType": 'json',
            "success": function (data) {
                var id,name;
                if(type == 'SQ'){
                    id = 'SQID';
                    name = 'SQMC';
                }else{
                    id = 'XQID';
                    name = 'XQMC';
                }

                for (var i = 0; i < data.features.length; i++) {
                    var citizensBankPark = viewer.entities.add({
                        id: data.features[i].properties[id],
                        position: Cesium.Cartesian3.fromDegrees(data.features[i].geometry.coordinates[0], data.features[i].geometry.coordinates[1], data.features[i].geometry.coordinates[2]),
                        label: { //文字标签
                            text: data.features[i].properties[name],
                            font: '12pt monospace',
                            style: Cesium.LabelStyle.FILL,
                            fillColor: Cesium.Color.AQUA,
                            outlineWidth: 2,
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM, //垂直方向以底部来计算标签的位置
                            pixelOffset: new Cesium.Cartesian2(0, -9)   //偏移量
                        },
                        /*billboard: { //图标
                            image: featherCmsScript.ctx + img,
                            width: 48,
                            height: 48,
                            scale: 1,//和原始大小相比的缩放比例
                            minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                            verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                        }*/
                    });
                    //viewer.zoomTo(viewer.entities);
                }

            }
        })
    }


};


//图层按钮点击事件
$(".zs-picture").click(function () {
    map.clearPic();
    var type = $(this).attr('type');
    if (type == 'FW' || type == 'RY' || type == 'YC') {
        $('.assetsMenuList').addClass('hide');
        $('.SecurityMenuList').addClass('hide');
    }
    map.addLayerPic(type);

});

//地图操作按钮
$(".mapToolbarList li").click(function () {
    var icon = $(this).find('i').attr('class');
    switch (icon) {
        case 'iconfont iconicon':   //指北
            mapViewer3D.viewer.scene.camera.flyTo({
                destination: new Cesium.Cartesian3.fromDegrees(121.34612, 31.14985, 100),
                orientation: {
                    heading: Cesium.Math.toRadians(0),
                    pitch: Cesium.Math.toRadians(-5),
                    roll: Cesium.Math.toRadians(0),
                },
                duration: 3.0
            });
            break;
        case 'iconfont iconquanping':   //全屏
            if (Cesium.Fullscreen.fullscreen) {
                Cesium.Fullscreen.exitFullscreen();
            } else {
                Cesium.Fullscreen.requestFullscreen(document.body);
            }
            break;
            break;
        case 'iconfont iconjia':    //放大
            mapViewer3D.viewer.camera.zoomIn(mapViewer3D.viewer.camera.positionCartographic.height / Math.abs(Math.sin(mapViewer3D.viewer.camera.pitch)) * 0.2);
            break;
        case 'iconfont iconjian':   //缩小
            mapViewer3D.viewer.camera.zoomOut(mapViewer3D.viewer.camera.positionCartographic.height / Math.abs(Math.sin(mapViewer3D.viewer.camera.pitch)) * 0.2);
            break;
        case 'iconfont iconquantu':     //全图
            map.full();
            break;
        case 'iconfont iconshanchu-copy-copy':     // 删除
            map.clearPic();
            break;

        default:
    }
});

function startTime() {
    var today = new Date();//定义日期对象
    var yyyy = today.getFullYear();//通过日期对象的getFullYear()方法返回年e799bee5baa6e59b9ee7ad9431333363363537
    var MM = today.getMonth() + 1;//通过日期对象的getMonth()方法返回年
    var dd = today.getDate();//通过日期对象的getDate()方法返回年
    var hh = today.getHours();//通过日期对象的getHours方法返回小时
    var mm = today.getMinutes();//通过日期对象的getMinutes方法返回分钟
    var ss = today.getSeconds();//通过日期对象的getSeconds方法返回秒
    // 如果分钟或小时的值小于10，则在其值前加0，比如如果时间是下午3点20分9秒的话，则显示15：20：09
    MM = checkTime(MM);
    dd = checkTime(dd);
    mm = checkTime(mm);
    ss = checkTime(ss);

    document.getElementById('time').innerHTML = hh + ":" + mm + ":" + ss;
    document.getElementById('data').innerHTML = yyyy + "年" + MM + "月" + dd + "日";
    setTimeout('startTime()', 1000);
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}
