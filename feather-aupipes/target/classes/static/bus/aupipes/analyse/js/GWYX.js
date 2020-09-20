var result; //存储分析结果数据
var dataArr;    //存储追溯影响的管线和配电房
var dataFw;
var dataZsArr = [{serial: 1, number: "0302", name: "西苑北配电房", position: "梧桐路", id: "0204", idType: "TRANSFORMER_ROOM_ANNOTATION"},
    {
        serial: 2,
        number: "0215",
        name: "供电管线段",
        position: "学府路",
        id: "215",
        idType: "GD_LINE_3D",
        geo: [114.34627489949989, 30.47387718531552, 1000]
    },
    {
        serial: 3,
        number: "0163",
        name: "供电管线段",
        position: "学府路",
        id: "163",
        idType: "GD_LINE_3D",
        geo: [114.34627489949989, 30.47387718531552, 1000]
    }];
var dataFwArr = [{serial: 1, pinqu: "西苑", loudong: "楼栋", type: "教学楼", id: "000062", idType: "HOUSE"},
    {serial: 2, pinqu: "西苑", loudong: "楼栋", type: "教学楼", id: "000071", idType: "HOUSE"}];
//管网运行
var GWYX = {
    // gwyxTubes: null,
    // gwyxValves: null,
    gwyxParentTubes: null,
    flowLineParent: null,
    bindEvents: function () {
        //出线追溯
        $('.cxzs').on('click', 'button', function () {
            GWYX.clearAll();
            if (!GWYX.gwyxParentTubes) {
                GWYX.gwyxParentTubes = gViewer.entities.add({
                    id: "gwyxTubes",
                    show: true
                });
            }
            GWYX.cxzsFun();
        });
        //进线追溯
        $('.jxzs').on('click', 'button', function () {
            GWYX.clearAll();
            if (!GWYX.gwyxParentTubes) {
                GWYX.gwyxParentTubes = gViewer.entities.add({
                    id: "gwyxTubes",
                    show: true
                });
            }
            GWYX.rxzsFun();
        });
        $('.clearGwyx').on('click', function () {
            GWYX.clearAll();
        })
    },
    flyToStation: function (x, y, z) {
        var camera = gViewer.camera;
        var target = Cesium.Cartesian3.fromDegrees(x, y, z);
        kqWeb3d.flyToCartesian3(camera, target, '', '', '');
    },
    lzfxFun: function () {
        GWYX.clearAll();
        $(".analyseResult").html("");
        bottonBoxHide();
        //关闭layer弹出层
        layer.closeAll();
        //清除定位
        layerTree.removeLocatedBuilding();
        if (hlzsId != null && hlzsId != "") {
            GWYX.getLzfxValue(hlzsId, '', "down");
        } else {
            layer.msg("请选择配电箱模型！");
        }
    },
    cxzsFun: function () {
        // $('#cesiumContainer').css({ 'width': '50vw' })
        // $('#cesiumContainerRight').show();
        /*GWYX.clearAll();
        if (!GWYX.gwyxParentTubes) {
            GWYX.gwyxParentTubes = gViewer.entities.add({
                id: "gwyxTubes",
                show: true
            });
        }*/
        GWYX.clearAll();
        $(".analyseResult").html("");
        bottonBoxHide();
        //关闭layer弹出层
        layer.closeAll();
        //清除定位
        layerTree.removeLocatedBuilding();

        if (hlzsId != null && hlzsId != "") {
            GWYX.getValues(hlzsId, '', "down");
        } else {
            layer.msg("请选择配电箱模型！");
        }
    },
    rxzsFun: function () {
        /*GWYX.clearAll();
        if (!GWYX.gwyxParentTubes) {
            GWYX.gwyxParentTubes = gViewer.entities.add({
                id: "gwyxTubes",
                show: true
            });
        }*/
        GWYX.clearAll();
        $(".analyseResult").html("");
        bottonBoxHide();
        //关闭layer弹出层
        layer.closeAll();
        //清除定位
        layerTree.removeLocatedBuilding();

        if (hlzsId != null && hlzsId != "") {
            GWYX.getValues(hlzsId, '', "up");
        } else {
            layer.msg("请选择配电箱模型");
        }
    },
    //根据oid获取有问题的管线和阀门点
    getValues: function (oid, height, type) {
        var url = layerCfg.gwyxPdfUrl + oid;
        $.get(url, function (data) {
            if (data.code == 0) {
                if (data.point && data.point.length > 0) {
                    var pointArr = data.point;
                    for (var x = 0; x < pointArr.length; x++) {
                        var postion = pointArr[x].geometry.coordinates;
                        var height = pointArr[x].properties.PIPEP_H;
                        GWYX.gwyxHighlightValues(postion[0], postion[1], height);
                        kqWeb3d.flyToDegrees(gViewer.camera, postion[0], postion[1], height, 0, -90, 1.5);
                    }
                }
                //清除流动线
                GWYX.clearFlowLine();
                if (data.line && data.line.length > 0) {
                    if (!GWYX.flowLineParent) {
                        GWYX.flowLineParent = gViewer.entities.add({
                            id: "gwyxdllx",//管网运行电流流向
                            show: true
                        });
                    }
                    for (var y = 0; y < data.line.length; y++) {
                        var posArr = [];
                        var height = data.line[y].properties.S_DEEP;
                        var OID = data.line[y].properties.OID;
                        GWYX.flowLine(data.line[y].geometry.coordinates, GWYX.flowLineParent, height, OID);
                    }
                }

                //出线分析详情
                GWYX.analyseInfo(type, data);

            } else {
                console.log('')
            }
        });
    },
    //高亮阀门
    gwyxHighlightValues: function (longtitude, lattitude, height) {
        var hei = Cesium.defined(height) ? height : undefined;
        var dataSoure = new Cesium.CustomDataSource();
        dataSoure.name = 'gwyxfm';//管网运行阀门
        dataSoure.entities.add({
            position: Cesium.Cartesian3.fromDegrees(longtitude, lattitude, hei),
            ellipse: {
                height: hei,
                semiMinorAxis: 5,
                semiMajorAxis: 5,
                material: new Cesium.Kq3dEllipseFadeMaterialProperty({
                    color: new Cesium.Color(77 / 255, 201 / 255, 1, 0.9)
                })
            },
            show: true
        });
        gViewer.dataSources.add(dataSoure);
    },
    //清除高亮的阀门
    gwyxClearValves: function () {
        var dataSources = [];
        if (gViewer) {
            $.each(gViewer.dataSources._dataSources, function (dex, val) {
                if (val && val.name == 'gwyxfm') {
                    dataSources.push(val);
                }
            })
        }

        if (dataSources.length > 0) {
            for (var x = 0; x < dataSources.length; x++) {
                gViewer.dataSources.remove(dataSources[x]);
            }
        }
    },
    //高亮管线
    gwyxHighlightTubes: function (gViewer, parentTubes, positionArr) {
        kqWeb3d.drawVolumeBox(gViewer, parentTubes, positionArr, Cesium.Color.DEEPPINK.withAlpha(0.6));
    },
    //绘制电流流向效果
    flowLine: function (coordinates, parent, height, OID) {
        var positions = [];
        if (coordinates.length > 0) {
            $.each(coordinates, function (key, value) {
                // positions.push(Cesium.Cartesian3.fromDegrees(value[0], value[1],height));
                positions.push(value[0], value[1], height);
            })
        }
        var t = gViewer.entities.add({
            name: '管网运行电流流向',
            pipeId: OID,
            parent: parent,
            polyline: {
                positions: Cesium.Cartesian3.fromDegreesArrayHeights(positions),
                width: 10,
                disableDepthTestDistance: 350000,
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 2000, 0.5),
                material: new Cesium.DynamicLineMaterialProperty({
                    image: ctx + 'bus/aupipes/analyse/images/电流.gif'
                }),
                //material: new Cesium.Kq3dODLineMaterialProperty(Cesium.Color.fromCssColorString("rgba(255,0,0,1.0)")),
                clampToGround: false
            }
        });
        gViewer.clock.shouldAnimate = true;
        gViewer.flyTo(t);
    },
    //清除流向效果
    clearFlowLine: function () {
        if (GWYX.flowLineParent) {
            var children = gViewer.entities.getById('gwyxdllx')._children;
            for (var x = 0; x < children.length; x++) {
                gViewer.entities.remove(gViewer.entities.getById('gwyxdllx')._children[x]);
            }
            GWYX.flowLineParent = null;
            gViewer.entities.remove(gViewer.entities.getById('gwyxdllx'));
        }

    },
    //清除高亮的管线
    gwxyClearTubes: function () {
        if (GWYX.gwyxParentTubes) {
            $.each(GWYX.gwyxParentTubes._children, function (dex, val) {
                gViewer.entities.remove(val);
            })
            gViewer.entities.remove(GWYX.gwyxParentTubes);
            GWYX.gwyxParentTubes = null;
        }
    },
    clearAll: function () {
        GWYX.gwyxClearValves();
        //GWYX.gwxyClearTubes();
        GWYX.clearFlowLine();
    },
    //管网运行选择事件
    pickPoint: function () {
        pickType = "zsPoint";
        hlzsId = null;
        tileset.setEnablePickFeatures(viewer, true);
    },
    //清除管网运行选择
    clearPickPoint: function () {
        hlzsId = null;
        pickProtry = null;
        GWYX.clearAll();
        $(".analyseResult").html("");
        bottonBoxHide();
        //关闭layer弹出层
        layer.closeAll();
        //清除定位
        layerTree.removeLocatedBuilding();
    },
    //关闭bim模型选取事件
    closePick: function () {
        tileset.setEnablePickFeatures(viewer, false);
    },
    //管网运行属性查看
    lookProtry: function () {
        if (pickProtry != undefined || pickProtry != null) {
            if (hlzsId != null) {
                if (pickProtry["族"] && pickProtry["族"].indexOf("低压配电箱") > -1) {
                    $.get(ctx + "screen/jcbz/getHlAttriBute?hlId=" + pickProtry["ID"], function (result) {
                        var powerAttribute;
                        if (result.data.hlAttribute && result.data.hlAttribute.length > 0) {
                            powerAttribute = result.data.hlAttribute[0];
                        }
                        openAttributeLayer(pickProtry, powerAttribute, "treeBim", "low");
                    });
                } else if (pickProtry["族"] && pickProtry["族"].indexOf("高压配电箱") > -1) {
                    $.get(ctx + "screen/jcbz/getHlAttriBute?hlId=" + pickProtry["ID"], function (result) {
                        var powerAttribute;
                        if (result.data.hlAttribute && result.data.hlAttribute.length > 0) {
                            powerAttribute = result.data.hlAttribute[0];
                        }
                        openAttributeLayer(pickProtry, powerAttribute, "treeBim", "high");
                    });
                } else {
                    openAttributeLayer(pickProtry, "", "", "");
                }
            } else {
                openAttributeLayer(pickProtry, "", "", "");
            }
        } else {
            layer.msg("请先进行选择！");
        }

    },
    analyseInfo: function (type, data) {
        if (data.fw.length > 0 || data.line != null) {
            selectedLinePipe(layerCfg.yxjk.cxzs);
            dataArr = [];
            dataFw = [];
            var line = 0, pdf = 0, fw = 0;   //影响的管线、配电房、房屋
            var dormHouse = 0, businessHouse = 0, teachHouse = 0, residentHouse = 0;
            //获取楼栋详细信息
            $.get(layerCfg.gwyxLdInfo + hlzsId, function (result) {
                if (result.data.pdfInfo && result.data.pdfInfo.length > 0) {
                    pdf = result.data.pdfInfo.length;
                    for (var i = 0; i < result.data.pdfInfo.length; i++) {
                        var obj = {
                            name: result.data.pdfInfo[i][0].name,
                            id: result.data.pdfInfo[i][0].BSM,
                            number: result.data.pdfInfo[i][0].BSM,
                            position: "",
                            idType: "TRANSFORMER_ROOM_ANNOTATION"
                        };
                        dataArr.push(obj);
                    }
                }
                if (data.line && data.line.length > 0) {
                    line = data.line.length;
                    for (var i = 0; i < data.line.length; i++) {
                        var obj = {
                            name: '供电管线段',
                            id: data.line[i].properties.OID,
                            number: data.line[i].properties.E_POINT,
                            height: data.line[i].properties.S_DEEP,
                            position: data.line[i].properties.R_NAME,
                            idType: "GD_LINE_3D",
                            geo: data.line[i].geometry
                        };
                        dataArr.push(obj);
                    }
                }
                if (result.data.fwInfo && result.data.fwInfo.length > 0) {
                    fw = result.data.fwInfo.length;
                    for (var i = 0; i < result.data.fwInfo.length; i++) {
                        var obj = {
                            name: result.data.fwInfo[i][0].buildingname,
                            id: result.data.fwInfo[i][0].areano,
                            pinqu: result.data.fwInfo[i][0].areaname,
                            type: result.data.fwInfo[i][0].buildingtype,
                            idType: "HOUSE"
                        };
                        dataFw.push(obj);
                        var buildingtype=result.data.fwInfo[i][0].buildingtype;
                        if (buildingtype == "居民楼") {
                            residentHouse++;
                        } else if (buildingtype == "教学楼") {
                            teachHouse++;
                        } else if (buildingtype == "宿舍楼") {
                            dormHouse++;
                        } else if (buildingtype == "商业楼") {
                            businessHouse++;
                        }
                    }
                }
                var name;
                $(".bottomLayer").html("");
                var htmlContent;
                if (type == "down") {
                    name = "出线";
                    htmlContent = `<div class="lzfxList" style="display:block">
                            <div class="title3">出线分析</div>
                            <ul class="yjtjList lzfxNumber">
                                <li class="active">
                                    <p>配电房</p>
                                    <h6>` + pdf + `</h6> <i class="iconfont icon-dian1"></i>
                                </li>
                                <li class="active">
                                    <p>供电管线</p>
                                    <h6>` + line + `</h6> <i class="iconfont icon-guanxian"></i>
                                </li>
                            </ul>
                        </div>
                        <div class="yxfwList" style="display:block">
                            <div class="title3">影响范围</div>
                            <div class="yxfwNumber pr">
                                <div class="jml pa">
                                    <h5>` + residentHouse + `</h5>
                                    <p>居民楼</p>
                                </div>
                                <div class="ssl pa">
                                    <h5>` + dormHouse + `</h5>
                                    <p>宿舍楼</p>
                                </div>
                                <div class="jxl pa">
                                    <h5>` + teachHouse + `</h5>
                                    <p>教学楼</p>
                                </div>
                                <div class="syl pa">
                                    <h5>` + businessHouse + `</h5>
                                    <p>商业楼</p>
                                </div>
                            </div>
                        </div>`;
                } else {
                    name = "进线";
                    htmlContent = `<div class="lzfxList" style="display:block">
                            <div class="title3">进线分析</div>
                            <ul class="yjtjList lzfxNumber">
                                <li class="active">
                                    <p>配电房</p>
                                    <h6>` + pdf + `</h6> <i class="iconfont icon-dian1"></i>
                                </li>
                                <li class="active">
                                    <p>供电管线</p>
                                    <h6>` + line + `</h6> <i class="iconfont icon-guanxian"></i>
                                </li>
                            </ul>
                        </div>`;
                }
                $(".analyseResult").html(htmlContent);

                var html = `<div class="pr vh">
                    <span class="closeBtn pa" onclick="closeBtn()"><i class="iconfont icon-guanbi"></i></span>
                    <!--拉闸分析-->
                    <div class="layui-tab layuiTab2" lay-filter="bottomTab">
                        <ul class="layui-tab-title">
                            <li class="layui-this">` + name + `分析详情</li>
                            <li class="zsFw">影响范围详情</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                                <div class="table moreTable page">
                                    <div id="zsfxInfo"></div>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <div class="table moreTable page">
                                    <div id="zsfxWhere"></div>
                                </div>
                            </div>
                          
                        </div>
                    </div>
                </div>`;
                $(".bottomLayer").html(html);
                if (type == "down") {
                    $(".zsFw").css("display", "line-block");
                } else {
                    $(".zsFw").css("display", "none");
                }
                bottomBoxShow();
                layui.use(['table', 'laypage'], function () {
                    var $ = layui.jquery,
                        table = layui.table,
                        laypage = layui.laypage;
                    var width = ($('.bottomLayer').width() - 30) / 20;
                    var tatleHeight = $(".moreTable").height() - 10
                    // 追溯分析--详情
                    table.render({
                        elem: '#zsfxInfo'
                        , height: tatleHeight
                        , skin: 'nob'
                        , limit: 5
                        , limits: [5, 10, 20, 30]
                        , data: dataArr
                        , cols: [[ //标题栏
                            {
                                field: 'serial',
                                title: '序号',
                                align: 'center',
                                width: width,
                                templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'
                            },
                            {field: 'number', title: '编号', align: 'center',},
                            {field: 'name', title: '名称', align: 'center'},
                            {field: 'position', title: '位置', align: 'center'}
                        ]]
                        , page: true
                        , done: function (res, curr, count) {
                            $('[lay-id="zsfxInfo"]').find('.layui-table-body').find("table").find("tbody").children("tr").on('dblclick', function () {
                                var id = JSON.stringify($('[lay-id="zsfxInfo"]').find('.layui-table-body').find("table").find("tbody").find(".layui-table-hover").data('index'));
                                var obj = res.data[id];

                                if (obj.name == "供电管线段") {
                                    GWYX.addFlowLine(obj.geo.coordinates, GWYX.flowLineParent, obj.height, obj.id);
                                } else {
                                    layerTree.locateBuildingByID(obj.id, obj.idType);
                                }

                            })
                        }
                    });

                    // 影响范围--详情
                    table.render({
                        elem: '#zsfxWhere'
                        , height: tatleHeight
                        // ,width:width
                        , limit: 5 // 这个要根据实际条数来确定，不能设置固定的
                        , limits: [5, 10, 20, 30]
                        , skin: 'nob'
                        , data: dataFw
                        , cols: [[ //标题栏
                            {
                                field: 'serial',
                                title: '序号',
                                align: 'center',
                                width: width,
                                templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'
                            },
                            {field: 'pinqu', title: '片区', align: 'center',},
                            {field: 'name', title: '楼栋', align: 'center'},
                            {field: 'type', title: '类型', align: 'center'}
                        ]]
                        , page: true
                        , done: function (res, curr, count) {
                            $('[lay-id="zsfxWhere"]').find('.layui-table-body').find("table").find("tbody").children("tr").on('dblclick', function () {
                                var id = JSON.stringify($('[lay-id="zsfxWhere"]').find('.layui-table-body').find("table").find("tbody").find(".layui-table-hover").data('index'));
                                var obj = res.data[id];
                                if (obj.name == "供电管线段") {
                                    GWYX.addFlowLine(obj.geo.coordinates, GWYX.flowLineParent, obj.height, obj.id);
                                } else {
                                    layerTree.locateBuildingByID(obj.id, obj.idType);
                                }


                            })
                        }
                    });
                });
            });
        } else {
            layer.msg("无影响的配电房和管线！");
        }
    },
    //管线定位
    addFlowLine: function (coordinates, parent, height, oid) {
        $.each(parent._children, function (dex, val) {
            gViewer.entities.remove(val);
        });
        for (var i = 0; i < dataArr.length; i++) {
            if (dataArr[i].name == "供电管线段" && dataArr[i].id != oid) {
                var positions = [];
                if (dataArr[i].geo.coordinates.length > 0) {
                    $.each(dataArr[i].geo.coordinates, function (key, value) {
                        positions.push(value[0], value[1], height);
                    })
                }
                var t = gViewer.entities.add({
                    name: '管网运行电流流向',
                    pipeId: dataArr[i].id,
                    parent: parent,
                    polyline: {
                        positions: Cesium.Cartesian3.fromDegreesArrayHeights(positions),
                        width: 10,
                        disableDepthTestDistance: 350000,
                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                        scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 2000, 0.5),
                        material: new Cesium.DynamicLineMaterialProperty({
                            image: ctx + 'bus/aupipes/analyse/images/电流.gif'
                        }),
                        //material: new Cesium.Kq3dODLineMaterialProperty(Cesium.Color.fromCssColorString("rgba(255,0,0,1.0)")),
                        clampToGround: false
                    }
                });
            } else if (dataArr[i].name == "供电管线段" && dataArr[i].id == oid) {
                var positions = [];
                if (coordinates.length > 0) {
                    $.each(coordinates, function (key, value) {
                        positions.push(value[0], value[1], height);
                    })
                }
                var t = gViewer.entities.add({
                    name: '管网运行电流流向',
                    pipeId: oid,
                    parent: parent,
                    polyline: {
                        positions: Cesium.Cartesian3.fromDegreesArrayHeights(positions),
                        width: 10,
                        disableDepthTestDistance: 350000,
                        distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 3000),
                        scaleByDistance: new Cesium.NearFarScalar(1000, 1.2, 2000, 0.5),
                        /*material: new Cesium.DynamicLineMaterialProperty({
                            image: ctx + 'bus/aupipes/analyse/images/电流.gif'
                        }),*/
                        material: new Cesium.Kq3dODLineMaterialProperty(Cesium.Color.fromCssColorString("rgba(255,0,0,1.0)")),
                        clampToGround: false
                    }
                });
                gViewer.clock.shouldAnimate = true;
                gViewer.flyTo(t);
            }
        }

    },
    getLzfxValue: function (oid, height, type) {
        var url = layerCfg.gwyxPdfUrl + oid;
        $.get(url, function (data) {
            if (data.code == 0) {
                if (data.point && data.point.length > 0) {
                    var pointArr = data.point;
                    for (var x = 0; x < pointArr.length; x++) {
                        var postion = pointArr[x].geometry.coordinates;
                        var height = pointArr[x].properties.PIPEP_H;
                        GWYX.gwyxHighlightValues(postion[0], postion[1], height);
                        kqWeb3d.flyToDegrees(gViewer.camera, postion[0], postion[1], height, 0, -90, 1.5);
                    }
                }
                //清除流动线
                GWYX.clearFlowLine();
                if (data.line && data.line.length > 0) {
                    if (!GWYX.flowLineParent) {
                        GWYX.flowLineParent = gViewer.entities.add({
                            id: "gwyxdllx",//管网运行电流流向
                            show: true
                        });
                    }
                    for (var y = 0; y < data.line.length; y++) {
                        var posArr = [];
                        var height = data.line[y].properties.S_DEEP;
                        var OID = data.line[y].properties.OID;
                        GWYX.flowLine(data.line[y].geometry.coordinates, GWYX.flowLineParent, height, OID);
                    }
                }

                //出线分析详情
                GWYX.analyseLzfxInfo(type, data);

            } else {
                console.log('')
            }
        });
    },
    analyseLzfxInfo: function (type, data) {
        if (data.fw.length > 0 || data.line != null) {
            dataArr = [];
            dataFw = [];
            var line = 0, pdf = 0, fw = 0;   //影响的管线、配电房、房屋
            var dormHouse = 0, businessHouse = 0, teachHouse = 0, residentHouse = 0;
            //获取楼栋详细信息
            $.get(layerCfg.gwyxLdInfo + hlzsId, function (result) {
                if (result.data.pdfInfo && result.data.pdfInfo.length > 0) {
                    pdf = result.data.pdfInfo.length;
                    for (var i = 0; i < result.data.pdfInfo.length; i++) {
                        var obj = {
                            name: result.data.pdfInfo[i][0].name,
                            id: result.data.pdfInfo[i][0].BSM,
                            number: result.data.pdfInfo[i][0].BSM,
                            position: "",
                            idType: "TRANSFORMER_ROOM_ANNOTATION"
                        };
                        dataArr.push(obj);
                    }
                }
                if (data.line && data.line.length > 0) {
                    line = data.line.length;
                    for (var i = 0; i < data.line.length; i++) {
                        var obj = {
                            name: '供电管线段',
                            id: data.line[i].properties.OID,
                            number: data.line[i].properties.E_POINT,
                            height: data.line[i].properties.S_DEEP,
                            position: data.line[i].properties.R_NAME,
                            hlmc: result.data.pdfInfo[i][0].HLMC,
                            GH: result.data.pdfInfo[i][0].GH,
                            idType: "GD_LINE_3D",
                            geo: data.line[i].geometry
                        };
                        dataArr.push(obj);
                    }
                }
                if (result.data.fwInfo && result.data.fwInfo.length > 0) {
                    fw = result.data.fwInfo.length;
                    for (var i = 0; i < result.data.fwInfo.length; i++) {
                        var obj = {
                            name: result.data.fwInfo[i][0].buildingname,
                            id: result.data.fwInfo[i][0].areano,
                            pinqu: result.data.fwInfo[i][0].areaname,
                            type: result.data.fwInfo[i][0].buildingtype,
                            idType: "HOUSE"
                        };
                        dataFw.push(obj);
                        var buildingtype=result.data.fwInfo[i][0].buildingtype;
                        if (buildingtype == "居民楼") {
                            residentHouse++;
                        } else if (buildingtype == "教学楼") {
                            teachHouse++;
                        } else if (buildingtype == "宿舍楼") {
                            dormHouse++;
                        } else if (buildingtype == "商业楼") {
                            businessHouse++;
                        }
                    }
                }
                $(".bottomLayer").html("");
                var htmlContent = `<div class="lzfxList" style="display:block">
                            <div class="title3">出线分析</div>
                            <ul class="yjtjList lzfxNumber">
                                <li class="active">
                                    <p>配电房</p>
                                    <h6>` + pdf + `</h6> <i class="iconfont icon-dian1"></i>
                                </li>
                                <li class="active">
                                    <p>供电管线</p>
                                    <h6>` + line + `</h6> <i class="iconfont icon-guanxian"></i>
                                </li>
                            </ul>
                        </div>
                        <div class="yxfwList" style="display:block">
                            <div class="title3">影响范围</div>
                            <div class="yxfwNumber pr">
                                <div class="jml pa">
                                    <h5>` + residentHouse + `</h5>
                                    <p>居民楼</p>
                                </div>
                                <div class="ssl pa">
                                    <h5>` + dormHouse + `</h5>
                                    <p>宿舍楼</p>
                                </div>
                                <div class="jxl pa">
                                    <h5>` + teachHouse + `</h5>
                                    <p>教学楼</p>
                                </div>
                                <div class="syl pa">
                                    <h5>` + businessHouse + `</h5>
                                    <p>商业楼</p>
                                </div>
                            </div>
                        </div>`;

                $(".analyseResult").html(htmlContent);

                var html = `<div class="pr vh">
                    <span class="closeBtn pa" onclick="closeBtn()"><i class="iconfont icon-guanbi"></i></span>
                    <!--拉闸分析-->
                    <div class="layui-tab layuiTab2" lay-filter="bottomTab">
                        <ul class="layui-tab-title">
                            <li class="layui-this">出线分析详情</li>
                            <li>影响楼栋详情</li>
                            <!--<li>推送用户详情</li>-->
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                                <div class="table moreTable page">
                                    <div id="zsfxInfo"></div>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <div class="table moreTable page">
                                    <div id="zsfxWhere"></div>
                                </div>
                            </div>
                            <!--<div class="layui-tab-item pr">
                                <div class="table moreTable page zs-page">
                                    <div id="hqyhTabe" style="height: 20vh;overflow-y: auto" ></div>
                                </div>
                                &lt;!&ndash;消息发送按钮&ndash;&gt;
                                <div class="pa xxfsBtn">
                                    <button type="button" class="xxts" onclick="JCBZ.jcbzLayuiBox()"><i class="iconfont icon-dian"></i>信息推送</button>
                                </div>
                            </div>-->
                        </div>
                    </div>
                </div>`;
                $(".bottomLayer").html(html);
                bottomBoxShow();
                layui.use(['table', 'laypage','tree'], function () {
                    var $ = layui.jquery,
                        table = layui.table,
                        tree = layui.tree,
                        laypage = layui.laypage;
                    var width = ($('.bottomLayer').width() - 30) / 20;
                    var tatleHeight = $(".moreTable").height() - 10
                    // 追溯分析--详情
                    table.render({
                        elem: '#zsfxInfo'
                        , height: tatleHeight
                        , skin: 'nob'
                        , limit: 5
                        , limits: [5, 10, 20, 30]
                        , data: dataArr
                        , cols: [[ //标题栏
                            {
                                field: 'serial',
                                title: '序号',
                                align: 'center',
                                width: width,
                                templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'
                            },
                            {field: 'number', title: '编号', align: 'center',},
                            {field: 'name', title: '名称', align: 'center'},
                            {field: 'position', title: '位置', align: 'center'},
                            {field: 'hlmc', title: '回路名称', align: 'center'},
                            {field: 'GH', title: '柜号', align: 'center'}
                        ]]
                        , page: true
                        , done: function (res, curr, count) {
                            $('[lay-id="zsfxInfo"]').find('.layui-table-body').find("table").find("tbody").children("tr").on('dblclick', function () {
                                var id = JSON.stringify($('[lay-id="zsfxInfo"]').find('.layui-table-body').find("table").find("tbody").find(".layui-table-hover").data('index'));
                                var obj = res.data[id];

                                if (obj.name == "供电管线段") {
                                    GWYX.addFlowLine(obj.geo.coordinates, GWYX.flowLineParent, obj.height, obj.id);
                                } else {
                                    layerTree.locateBuildingByID(obj.id, obj.idType);
                                }

                            })
                        }
                    });

                    // 影响范围--详情
                    table.render({
                        elem: '#zsfxWhere'
                        , height: tatleHeight
                        // ,width:width
                        , limit: 5 // 这个要根据实际条数来确定，不能设置固定的
                        , limits: [5, 10, 20, 30]
                        , skin: 'nob'
                        , data: dataFw
                        , cols: [[ //标题栏
                            {
                                field: 'serial',
                                title: '序号',
                                align: 'center',
                                width: width,
                                templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'
                            },
                            {field: 'pinqu', title: '片区', align: 'center',},
                            {field: 'name', title: '楼栋', align: 'center'},
                            {field: 'type', title: '类型', align: 'center'}
                        ]]
                        , page: true
                        , done: function (res, curr, count) {
                            $('[lay-id="zsfxWhere"]').find('.layui-table-body').find("table").find("tbody").children("tr").on('dblclick', function () {
                                var id = JSON.stringify($('[lay-id="zsfxWhere"]').find('.layui-table-body').find("table").find("tbody").find(".layui-table-hover").data('index'));
                                var obj = res.data[id];
                                if (obj.name == "供电管线段") {
                                    GWYX.addFlowLine(obj.geo.coordinates, GWYX.flowLineParent, obj.height, obj.id);
                                } else {
                                    layerTree.locateBuildingByID(obj.id, obj.idType);
                                }
                            })
                        }
                    });

                    // 获取用户--详情
                    //JCBZ.lzxd.getHqyhTree(tree);


                });
            });
        } else {
            layer.msg("暂无影响数据！");
        }
    },


}


/**
 * 智能排管模块
 */

// Global Var
var pipe_analyse_serice_url = layerCfg.znpgUrl;
// 图形编辑
var GeometryUtil = {
    geometryEntityArr: [],
    pickPoint84Arr: [],
    geoJson: {
        type: '',
        coordinates: []
    },
    tooltipObj: getTooltipDiv(),
    cratePoint: function (viewer, position, config) {
        var config = config ? config : {};
        var pointGeometry = viewer.entities.add({
            name: "点几何对象",
            position: position,
            point: {
                color: Cesium.Color.SKYBLUE,
                pixelSize: 3,
                outlineColor: Cesium.Color.YELLOW,
                outlineWidth: 2,
                disableDepthTestDistance: Number.POSITIVE_INFINITY
            }
        });
        return pointGeometry;
    },
    drawPoint: function (viewer) {
        var that = this;
        var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
        handler.setInputAction(function (movement) {
            var tooltipObj = document.getElementById("tooltip");
            tooltipObj.style.left = movement.endPosition.x + 10 + "px";
            tooltipObj.style.top = movement.endPosition.y + 20 + "px";
        }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        handler.setInputAction(function (movement) {
            var position = viewer.camera.pickEllipsoid(movement.position, viewer.scene.globe.ellipsoid);
            var pointEntity = that.cratePoint(viewer, position);
            that.geometryEntityArr.push(pointEntity);
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        handler.setInputAction(function () {
            handler.destroy();//关闭事件句柄
            handler = null;
        }, Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
        handler.setInputAction(function () {
            handler.destroy();//关闭事件句柄
            handler = null;
        }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);


    },
    createPolyline: function (viewer, positions, config) {
        if (positions.length < 1) return;
        var config = config ? config : {};
        var polylineGeometry = viewer.entities.add({
            name: "线几何对象",
            polyline: {
                positions: positions,
                width: config.width ? config.width : 5.0,
                material: new Cesium.PolylineGlowMaterialProperty({
                    color: config.color ? new Cesium.Color.fromCssColorString(config.color) : Cesium.Color.GOLD,
                }),
                depthFailMaterial: new Cesium.PolylineGlowMaterialProperty({
                    color: config.color ? new Cesium.Color.fromCssColorString(config.color) : Cesium.Color.GOLD,
                }),
            }
        });
        return polylineGeometry;
    },
    drawPolyline: function (viewer, callback) {
        var that = this;
        that.resetCacheData(viewer);
        that.geoJson.type = 'LineString';
        var scene = viewer.scene;
        scene.globe.depthTestAgainstTerrain = true;
        var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
        var tempPoints = [];
        var tooltipObj = that.tooltipObj;
        var tempLine = null;
        //鼠标移动事件
        handler.setInputAction(function (movement) {
            tooltipObj.style.display = "block";
            tooltipObj.style.left = movement.endPosition.x + 10 + "px";
            tooltipObj.style.top = movement.endPosition.y + 20 + "px";

            if (tempPoints.length >= 1) {
                var cartesian = scene.pickPosition(movement.endPosition);
                if (tempLine == null) {
                    tempLine = that.createPolyline(viewer, [tempPoints[tempPoints.length - 1], cartesian]);
                    that.geometryEntityArr.push(tempLine);
                } else {
                    tempLine.polyline.positions = new Cesium.CallbackProperty(function () {
                        return [tempPoints[tempPoints.length - 1], cartesian];
                    }, false);//防止闪烁，在移动的过程
                }
            }
        }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);

        //左键点击操作
        handler.setInputAction(function (click) {
            var cartesian = scene.pickPosition(click.position);
            tempPoints.push(cartesian);
            if (Cesium.defined(cartesian)) {
                var cartographic = Cesium.Cartographic.fromCartesian(cartesian);
                var lng = Cesium.Math.toDegrees(cartographic.longitude);
                var lat = Cesium.Math.toDegrees(cartographic.latitude);
                var height = cartographic.height;//模型高度
                var mapPosition = {x: lng, y: lat, z: height};
                var xyzArr = [lng, lat, height];
                that.pickPoint84Arr.push(mapPosition);
                that.geoJson.coordinates.push(xyzArr);
            }
            var point = that.cratePoint(viewer, tempPoints[tempPoints.length - 1]);
            that.geometryEntityArr.push(point);
            //var pointline = that.createPolyline(viewer, [tempPoints[tempPoints.length - 2], tempPoints[tempPoints.length - 1]]);
            if (Cesium.defined(tempLine)) {
                tempLine.polyline.positions = new Cesium.CallbackProperty(function () {
                    return tempPoints;
                }, false);//防止闪烁，在移动的过程
            }
            tooltipObj.innerHTML = "请绘制下一个点(" + (tempPoints.length + 1) + ")或右键结束";
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

        handler.setInputAction(function (click) {
            if (Cesium.defined(tempLine)) {
                tempLine.polyline.positions = new Cesium.CallbackProperty(function () {
                    return tempPoints;
                }, false);//防止闪烁，在移动的过程
            }
            tooltipObj.style.display = "none";
            tooltipObj.innerHTML = "左键单击绘制,右键结束绘制";
            callback();
            handler.destroy();//关闭事件句柄
            handler = null;
            Cursor.resetCursor(viewer);
        }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);

    },
    clearEntites: function (viewer) {
        var that = this;
        if (that.geometryEntityArr.length > 0) {
            for (var i in that.geometryEntityArr) {
                viewer.entities.remove(that.geometryEntityArr[i]);
            }
            that.geometryEntityArr = [];
            that.pickPoint84Arr = [];
            that.geoJson = {type: '', coordinates: []};
        }
    },
    hideEntities: function () {
        var that = this;
        if (that.geometryEntityArr.length > 0) {
            for (var i in that.geometryEntityArr) {
                that.geometryEntityArr[i].show = false;
            }
        }
    },
    removeAllInputAction: function () {

    },
    resetCacheData: function (viewer) {
        var that = this;
        that.clearEntites(viewer);
        Cursor.editeCursor(viewer);
    },


}


function getTooltipDiv() {
    var tooltipDiv = document.getElementById("tooltip");
    if (tooltipDiv == null) {
        tooltipDiv = document.createElement('div');
        tooltipDiv.setAttribute("style", "position:absolute;display: none; color: #ffffff;border:solid white 1px");
        tooltipDiv.setAttribute("id", "tooltip");
        tooltipDiv.innerHTML = "左键单击绘制,右键结束绘制";
        document.body.append(tooltipDiv);
    }
    return tooltipDiv;
};

var Cursor = {
    editeCursor: function (viewer) {
        var canvas = viewer.scene.canvas;
        canvas.style.cursor = 'crosshair';
    },
    resetCursor: function (viewer) {
        var canvas = viewer.scene.canvas;
        canvas.style.cursor = 'default';
    }
}

// 管线操作
var Tube = {
    tempObj: {},
    /**
     * 所有管件entity
     */
    tubeEntites: [],
    createTube: function (viewer, radius_, depth, pointArray, material) {
        function computeCircle(radius) {
            var positions = [];
            for (var i = 0; i < 360; i++) {
                var radians = Cesium.Math.toRadians(i);
                positions.push(new Cesium.Cartesian2(radius * Math.cos(radians), radius * Math.sin(radians)));
            }
            return positions;
        }

        var cesiumPolylineArr = [];
        if (pointArray instanceof Array) {
            var length = pointArray.length;
            if (length > 0) {
                for (var i = 0; i < length; i++) {
                    cesiumPolylineArr.push(Number(pointArray[i].x));
                    cesiumPolylineArr.push(Number(pointArray[i].y));
                    var z = Number(pointArray[i].z) - Number(depth)
                    cesiumPolylineArr.push(z);
                }
            }
        } else {
            console.error("参数[pointArray]不符合要求！")
            return;
        }

        // [114.34400, 30.47354, 100.0,114.35400, 30.47354, 100.0]
        var circleTubeEntity = viewer.entities.add({
            name: 'CircleTube',
            polylineVolume: {
                positions: Cesium.Cartesian3.fromDegreesArrayHeights(cesiumPolylineArr),
                shape: computeCircle(radius_),
                material: Cesium.Color.RED
            }
        });
        this.tubeEntites.push(circleTubeEntity);
        viewer.flyTo(circleTubeEntity);
    },
    /**
     * 创建圆形管
     * @param viewer
     * @param radius_
     * @param depth
     * @param pointArray
     * @param material
     */
    createCircleTube: function (viewer, options, pointArray, isFly) {
        var that = this;
        GeometryUtil.hideEntities(viewer);

        function computeCircle(radius) {
            var positions = [];
            for (var i = 0; i < 360; i++) {
                var radians = Cesium.Math.toRadians(i);
                positions.push(new Cesium.Cartesian2(radius * Math.cos(radians), radius * Math.sin(radians)));
            }
            return positions;
        }

        var cesiumPolylineArr = [];
        if (pointArray instanceof Array) {
            var length = pointArray.length;
            if (length > 0) {
                for (var i = 0; i < length; i++) {
                    cesiumPolylineArr.push(Number(pointArray[i].x));
                    cesiumPolylineArr.push(Number(pointArray[i].y));
                    var z = Number(pointArray[i].z) - Number(options.depth);
                    cesiumPolylineArr.push(z);
                }
            }
        } else {
            console.error("参数[pointArray]不符合要求！")
            return;
        }

        // [114.34400, 30.47354, 100.0,114.35400, 30.47354, 100.0]
        var circleTubeEntity = viewer.entities.add({
            name: 'CircleTube',
            polylineVolume: {
                positions: Cesium.Cartesian3.fromDegreesArrayHeights(cesiumPolylineArr),
                shape: computeCircle(Number(options.gxgg) / 2000),
                material: Cesium.Color.RED
            }
        });
        that.tubeEntites.push(circleTubeEntity);
        isFly = isFly || true;
        if (isFly) {
            viewer.flyTo(circleTubeEntity);
        }
        return circleTubeEntity;
    },
    /**
     * 创建方形管
     * @param viewer
     */
    createSquareTube: function (viewer, options, pointArray, isFly) {
        var that = this;
        GeometryUtil.hideEntities(viewer);

        function getShape(width, height) {
            return [new Cesium.Cartesian2(-width / 2000, -height / 2000),
                new Cesium.Cartesian2(width / 2000, -height / 2000),
                new Cesium.Cartesian2(width / 2000, height / 2000),
                new Cesium.Cartesian2(-width / 2000, height / 2000)]
        }

        var cesiumPolylineArr = [];
        if (pointArray instanceof Array) {
            var length = pointArray.length;
            if (length > 0) {
                for (var i = 0; i < length; i++) {
                    cesiumPolylineArr.push(Number(pointArray[i].x));
                    cesiumPolylineArr.push(Number(pointArray[i].y));
                    var z = Number(pointArray[i].z) - Number(options.depth)
                    cesiumPolylineArr.push(z);
                }
            }
        } else {
            console.error("参数[pointArray]不符合要求！")
            return;
        }
        var squareTubeEntity = viewer.entities.add({
            name: 'SquareTube',
            polylineVolume: {
                positions: Cesium.Cartesian3.fromDegreesArrayHeights(cesiumPolylineArr),
                shape: getShape(options.width, options.height),
                cornerType: Cesium.CornerType.MITERED,
                material: Cesium.Color.RED,
                outline: true,
                outlineColor: Cesium.Color.RED
            }
        });
        that.tubeEntites.push(squareTubeEntity);
        isFly = isFly || true;
        if (isFly) {
            viewer.flyTo(squareTubeEntity);
        }
        return squareTubeEntity;
    },
    /**
     * 清除所有临时渲染管件
     * @param viewer
     */
    clearTubes: function (viewer) {
        if (this.tubeEntites.length > 0) {
            for (var i in this.tubeEntites) {
                viewer.entities.remove(this.tubeEntites[i]);
            }
            this.tubeEntites = [];
        }
    },
    checkTubeGG: function (gxgg) {
        if (Cesium.defined(gxgg)) {
            if (gxgg.indexOf('X') > 0) {
                return gxgg.split("X");
            } else {
                return [gxgg];
            }
        } else {
            return [];
        }
    },
    //生成模型事件
    createAnalyseTude: function () {
        Tube.clearTubes(gViewer);
        $(".pgfxTabe").find("div").eq(1).html("");
        var depth = $("input[name='depth']").val();//开挖深度
        var gxgg = $("select[name='norms']").val();//管线规格
        var gxlx = $("select[name='lineType']").val();//管线类型
        if (depth == '') {
            layer.msg('请输入开挖深度！');
            return;
        }
        if (gxgg == '') {
            layer.msg('请选择管线规格！');
            return;
        }
        if (gxlx == '') {
            layer.msg('请选择管线类型！');
            return;
        }
        var arrNorms = [];
        if (gxgg) {
            arrNorms = gxgg.split("x");
        }

        if (GeometryUtil.pickPoint84Arr.length > 1) {
            if (arrNorms.length > 1) {
                Tube.tempObj.depth = depth;
                Tube.tempObj.width = arrNorms[0];
                Tube.tempObj.height = arrNorms[1];
                Tube.tempObj.gxgg = gxgg;
                Tube.tempObj.gxlx = gxlx;
                Tube.createSquareTube(gViewer, Tube.tempObj, GeometryUtil.pickPoint84Arr);
            } else {
                Tube.tempObj.depth = depth;
                Tube.tempObj.gxgg = gxgg;
                Tube.tempObj.gxlx = gxlx;
                Tube.createCircleTube(gViewer, Tube.tempObj, GeometryUtil.pickPoint84Arr);
            }
        } else {
            layer.msg(`请先划线!`);
        }
    },
    //排管分析点击事件
    clickAnalyseTude: function () {
        if (Tube.tubeEntites.length > 0) {
            var hcbj = $("input[name='buff']").val();//缓冲半径
            var msfs = $("select[name='layerOutType']").val();//埋设方式
            var dllx = $("select[name='roadsCategory']").val();//道路类型
            if (hcbj == '') {
                layer.msg('请输入缓冲半径');
            }
            if (msfs == '') {
                layer.msg('请选择埋设方式')
            }
            if (dllx == '') {
                layer.msg('请选择道路类型')
            }
            Tube.tempObj.hcbj = hcbj;
            Tube.tempObj.msfs = msfs;
            Tube.tempObj.dllx = dllx;

            var param = {};
            param.buff = Tube.tempObj.hcbj;
            param.lineStr = JSON.stringify(GeometryUtil.geoJson);
            param.lineType = Tube.tempObj.gxlx;
            param.layerOutType = Tube.tempObj.msfs;
            param.radius = Tube.tempObj.gxgg;
            param.depth = Tube.tempObj.depth;
            param.roadsCategory = Tube.tempObj.dllx;

            sendRequest(pipe_analyse_serice_url, param).then(function (data) {
                if (data != null && data.code == 0) {
                    var distance_data = data.distance_data;
                    result = data;      //存储分析的总结果
                    //result.depth_data = data.depth_data;
                    if (distance_data.length > 0) {

                        for (var i = 0; i < distance_data.length; i++) {
                            var data = distance_data[i].data;

                            var name = data.name;
                            var geometry = data.geometry;
                            var properties = data.properties;

                            //给result中绑定oid
                            result.distance_data[i].oid = properties["OID"];
                            // 方案一
                            /*var primitives = gViewer.scene.primitives;
                            for (var j=0; j<primitives.length; j++) {
                                primitives.get(j).style = new Cesium.Cesium3DTileStyle({
                                    color : {
                                        conditions : [
                                            ['${OID} === "'+properties.OID+'"', 'color("blue")'],
                                            ['true', 'color()']
                                        ]
                                    }
                                });
                            }*/
                            // 方案二
                            var coordinates = geometry.coordinates;
                            if (coordinates.length == 2) {
                                var pointArray = [];
                                var point1 = {}, point2 = {};
                                point1.x = coordinates[0][0];
                                point1.y = coordinates[0][1];
                                point1.z = properties.START_H;
                                point2.x = coordinates[1][0];
                                point2.y = coordinates[1][1];
                                point2.z = properties.END_H;
                                pointArray.push(point1, point2);
                                var ggArr = Tube.checkTubeGG(properties.D_S);
                                var ggArrLth = ggArr.length;
                                var obj = {};
                                obj.oid = properties.OID;
                                obj.depth = 0;
                                var entity = null;
                                if (ggArrLth == 1) {
                                    obj.gxgg = ggArr[0];
                                    entity = Tube.createCircleTube(gViewer, obj, pointArray, false);
                                } else if (ggArrLth == 2) {
                                    obj.width = ggArr[0];
                                    obj.height = ggArr[1];
                                    entity = Tube.createSquareTube(gViewer, obj, pointArray, false);
                                } else {
                                    console.warn("管线规格解析错误")
                                    return;
                                }
                                HighLighUtil.entityArr.push(entity);
                                HighLighUtil.entityMap[properties.OID] = entity;
                            }
                        }
                        gViewer.flyTo(HighLighUtil.entityArr);

                        var tatleHeight = $(".pgfxTabe").height() + 'px'
                        layui.use('table', function () {
                            var table = layui.table;
                            // 排管分析
                            table.render({
                                elem: '#pgfxTabe'
                                , height: tatleHeight
                                , limit: 10// 这个要根据实际条数来确定，不能设置固定的
                                , skin: 'nob'
                                , cols: [[ //标题栏
                                    {type: 'numbers', title: '序号', minWidth: 50},
                                    {field: 'oid', title: '编号', align: 'center'},
                                    {field: 'name', title: '图层', align: 'center'}
                                ]]
                                , data: result.distance_data
                                , page: {
                                    layout: ['prev', 'page', 'next'] //自定义分页布局
                                }, done: function (res, curr, count) {
                                    $('.pgfxTabe').find('.layui-table-body').find("table").find("tbody").children("tr").on('dblclick', function () {
                                        var id = JSON.stringify($('.pgfxTabe').find('.layui-table-body').find("table").find("tbody").find(".layui-table-hover").data('index'));
                                        var obj = res.data[id];

                                        HighLighUtil.flyToSelected(viewer, obj.oid);
                                        var html = '<div class="attributesBox"><table><tbody>';
                                        html += '<tr><th>编号</th><td>' + obj.oid + '</td></tr>';
                                        html += '<tr><th>图层</th><td>' + obj.name + '</td></tr>';
                                        if (obj.h_distance >= obj.h_s_distance) {
                                            html += '<tr><th>水平净距</th><td>' + obj.h_distance + '</td></tr>';
                                        } else {
                                            html += '<tr style="color: red;"><th>水平净距</th><td>' + obj.h_distance + '</td></tr>';
                                            html += '<tr style="color: red;"><th>水平分析</th><td>' + obj.h_msg + '</td></tr>';
                                        }
                                        html += '<tr><th>水平标准</th><td>' + obj.h_s_distance + '</td></tr>';
                                        if (obj.v_distance >= obj.v_s_distance) {
                                            html += '<tr><th>垂直净距</th><td>' + obj.v_distance + '</td></tr>';
                                        } else {
                                            html += '<tr style="color: red;"><th>垂直净距</th><td>' + obj.v_distance + '</td></tr>';
                                            html += '<tr style="color: red;"><th>垂直分析</th><td>' + obj.v_msg + '</td></tr>';
                                        }
                                        html += '<tr><th>垂直标准</th><td>' + obj.v_s_distance + '</td></tr>';
                                        html += '<tr><th>埋深</th><td>' + result.depth_data.s_depth + '</td></tr>';
                                        html += '<tr><th>埋深分析</th><td>' + result.depth_data.msg + '</td></tr>';
                                        html += '</tbody></table></div>';

                                        if (indexLayer != undefined) {
                                            layer.close(indexLayer);
                                            indexLayer = "";
                                        }
                                        indexLayer = layer.open({
                                            type: 1,
                                            title: '详细信息',
                                            area: ['400px', '300px'],
                                            offset: 'auto',
                                            shade: false,
                                            content: html,
                                            skin: 'layer-style'
                                        });

                                        //弹框的标题高度
                                        $("#layui-layer" + indexLayer).find(".layui-layer-title").css("height", "30px");
                                        $("#layui-layer" + indexLayer).find(".layui-layer-title").css("line-height", "30px");
                                        $("#layui-layer" + indexLayer).find(".layui-layer-setwin").css("top", "8px");
                                    })
                                }
                            });

                        })

                    } else {
                        layer.msg('分析结果为空');
                    }
                }
            }, function (err) {
                console.log(err);
            });
        } else {
            layer.msg('请先生成管线模型');
        }
    }

}


/*document.getElementById('btn-clear').addEventListener('click', function () {
    Tube.clearTubes(gViewer);
});*/

var piEntityParent = [];

function createPi(viewer, polylineArr) {
    function computeCircle(radius) {
        var positions = [];
        for (var i = 0; i < 360; i++) {
            var radians = Cesium.Math.toRadians(i);
            positions.push(new Cesium.Cartesian2(radius * Math.cos(radians), radius * Math.sin(radians)));
        }
        return positions;
    }

    // [114.34400, 30.47354, 100.0,114.35400, 30.47354, 100.0]
    var circleTubeEntity = viewer.entities.add({
        name: 'CircleTube',
        polylineVolume: {
            positions: Cesium.Cartesian3.fromDegreesArrayHeights(polylineArr),
            shape: computeCircle(1000),
            material: Cesium.Color.RED
        }
    });
    return circleTubeEntity;
}

var HighLighUtil = {
    entityArr: [],
    entityMap: {},
    clearEntity: function (viewer) {
        var that = this;
        if (that.entityArr.length > 0) {
            for (var i in that.entityArr) {
                viewer.entities.remove(that.entityArr[i]);
            }
            that.entityArr = [];
            that.entityMap = {};
        }
    },
    flyToSelected: function (viewer, id) {
        var that = this;
        var entity = that.entityMap[id];
        if (Cesium.defined(entity)) {
            viewer.flyTo(entity);
        }
    }
}

function request(url, param, successCallback, errorCallback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: param,
        async: Tube,
        success: successCallback,
        error: errorCallback
    });
}

function sendRequest(url, param) {
    return new Promise(function (resolve, reject) {
        request(url, param, resolve, reject);
    })
}
