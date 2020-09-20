var element, form, laydate,formSelects;   //使layui的属性变为全局变量
var menuType;   //从来存储点击的菜单类型
var yjArr = [], xjArr = [], wxArr = [];//存储预警信息、巡检信息、维修信息
var page = 1, pageSize = 5;
var total;  //存储查询的总数
var lat; // 纬度，浮点数，范围为90 ~ -90
var long; //经度
var resultArr = [], arrChecked = []; //搜周边的结果
var pickPosition;
var searchIndex = 1;
var pickName;
var radiusGf = 50;
var loading = null; //存储loading过程
layui.use(['element', 'form', 'laydate'], function () {
    element = layui.element,
        form = layui.form,
        laydate = layui.laydate;
    //监听Tab切换
    /*element.on('tab(layerBoxContent)', function() {
      console.log('tab 切换')
    });*/

    //点击侧边导航栏
    element.on('nav(demo)', function (elem) {
        menuType = elem.text();
        layerTree.clearPic();
        //changeLayer([]);
        if (elem.text() == '巡检维修' || elem.text() == "运行监控" || elem.text() == '决策保障' || elem.text() == '我的' || elem.text() == '能耗监管') {
            //layer.msg(elem.text());
        } else {
            jsAnimateMenu('close');
            $("#result").html("");
            $("#result").css("display", "block");
            switch (elem.text()) {
                case '搜周边':
                    $("#result").load(ctx + "mobile/search", function (data) {
                        var vm = new Vue({
                            el: '.searchList',
                            data: {
                                pipe: layerTree.zNodes["2dPipe"],//管线数据
                                device: layerTree.zNodes["device"],//智能设备
                            }
                        });
                        form.render();
                        //周边搜索提交按钮
                        wxJsSdk.getLocation(function (res) {
                            lat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                            long = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                            pickPosition = {longitude: long, latitude: lat};
                            $("input[name=position]")[0].value = long + ',' + lat;
                        });
                        form.on('submit(searchSubmit)', function (data) {
                            layerTree.clearPic();
                            loading = layer.load(0, {
                                shade: false
                            });
                            resultArr = [];
                            searchIndex = 1;
                            var radius = $("input[name='radius']")[0].value.trim();
                            radius = radius == "" ? 50 : radius;
                            //var point = $("input[name='position']")[0].value;
                            var list = $(".switchBtn");
                            arrChecked = [];
                            layerTree.removeLocatedBuilding('local');
                            // 获取缓冲图形
                            var geometry = {
                                type: "Point",
                                coordinates: [parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude)]
                            };
                            layerTree.clearPic();
                            var citizensBankPark = viewer.entities.add({
                                position: Cesium.Cartesian3.fromDegrees(parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude), 10),
                                billboard: {
                                    image: ctx + 'bus/aupipes/mobile/images/dingwei.png',
                                    width: 24,
                                    height: 24,
                                    scale: 1, //和原始大小相比的缩放比例
                                    minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                                    distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                                    verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                }
                            });
                            geometryEntityArr.push(citizensBankPark);
                            var bufferOptions = {
                                url: aupipeService + "/KQGis/rest/services/geometryserver/buffer",
                                geoSRS: "EPSG:4326",
                                outSRS: "EPSG:4326",
                                data: JSON.stringify(geometry),
                                radius: radius
                            };
                            layerTree.getBufferOfGeometry(bufferOptions, function (bufferGeometry) {
                                //添加缓冲面
                                var geoJson = {
                                    "type": "FeatureCollection",
                                    "features": [
                                        {
                                            "type": "Feature",
                                            "properties": {},
                                            "geometry": {
                                                "type": bufferGeometry.type,
                                                "coordinates": bufferGeometry.coordinates
                                            }
                                        }
                                    ]
                                }
                                layerTree.addGeoJsonLayer(geoJson, layerTree.setOptionsByType());
                                for (var i = 0; i < list.length; i++) {
                                    if (list[i].checked == true && list[i].id != "record") {
                                        arrChecked.push(list[i]);
                                        var obj = layerTree.allNodes.find(function (obj) {
                                            return obj.id === list[i].id
                                        });
                                        layerTree.searchAround(pickPosition, radius, obj, bufferGeometry, queryResult);
                                    } else if (list[i].checked == true && list[i].id == "record") {
                                        arrChecked.push(list[i]);
                                        var obj = {id: "record", name: "维修记录"};
                                        layerTree.searchRepairRecords(pickPosition, radius, obj, queryResult);
                                    }
                                }
                                if (arrChecked.length == 0) {
                                    layer.msg("请先选择图层！")
                                    layer.closeAll('loading');
                                }
                            });
                        });

                        $("#cancel").on("click", function () {
                            $("#result").css("display", "none");
                            $("#result").html("");
                            //jsAnimateMenu("open");
                        });

                        // 隐藏搜索页对面
                        $(".searchPositin").on("click", function () {
                            $("#searchBox").addClass("searchCollapse");
                            pickFeature.start("Position", pickFeature.callback);
                        });

                        /*$(".searchClose").on("click", function () {
                            $("#searchBox").removeClass("searchCollapse")
                        });*/

                        form.on('switch(switchBtnAll)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchBtn").prop("checked", true);
                                $(".switchDxgxBtn").prop("checked", true);
                                $(".switchBtnZnsb").prop("checked", true);
                                $(".switchBtnNone").prop("checked", false);
                            }
                            form.render();
                        });
                        form.on('switch(switchBtnNone)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchBtn").prop("checked", false);
                                $(".switchDxgxBtn").prop("checked", false);
                                $(".switchBtnZnsb").prop("checked", false);
                                $(".switchBtnAll").prop("checked", false);
                            }
                            form.render();
                        });

                        //地下管线
                        form.on('switch(switchBtnDxgxAll)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchDxgxBtn").prop("checked", true);
                                $(".switchBtnDxgxNone").prop("checked", false);
                            }
                            form.render();
                        });
                        form.on('switch(switchBtnDxgxNone)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchDxgxBtn").prop("checked", false);
                                $(".switchBtnDxgxAll").prop("checked", false);
                            }
                            form.render();
                        });
                        //智能设备
                        form.on('switch(switchBtnZnsbAll)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchBtnZnsb").prop("checked", true);
                                $(".switchBtnZnsbNone").prop("checked", false);
                            }
                            form.render();
                        });
                        form.on('switch(switchBtnZnsbNone)', function (data) {
                            if (data.elem.checked == true) {
                                $(".switchBtnZnsb").prop("checked", false);
                                $(".switchBtnZnsbAll").prop("checked", false);
                            }
                            form.render();
                        });

                    });
                    break;
                case '巡查列表':
                    $("#result").load(ctx + "mobile/xjlb", function (data) {
                        xjlbResultHeight();
                        //渲染开始和结束时间
                        laydate.render({elem: '#test3'});
                        laydate.render({elem: '#test4'});
                        form.render();
                        //默认加载初始数据
                        page = 1, xjArr = [];
                        var res = {inspectStatus: '', startTime: '', endTime: '', pageNum: page, pageSize: pageSize};
                        searchXjResult(res);
                        //监听提交
                        form.on('submit(xjFormSearch)', function (data) {
                            $("#xjListBox").html("");
                            $(".listMore").css("display", "block");
                            xjArr = [];
                            page = 1;
                            var test1 = $("#test3").val(),
                                test2 = $("#test4").val();

                            res = {
                                inspectStatus: data.field.status,
                                startTime: test1,
                                endTime: test2,
                                pageNum: page,
                                pageSize: pageSize
                            };
                            searchXjResult(res);
                        });

                        $(".listMore").on("click", function () {
                            res.pageNum = page + 1;
                            page = page + 1;
                            if (total > xjArr.length) {
                                searchXjResult(res);
                            } else {
                                $(".listMore").css("display", "none");
                            }
                        });
                    });
                    break;
                case '预警信息':
                    $("#result").load(ctx + "mobile/yjxx", function (data) {
                        xjlbResultHeight();

                        //渲染开始和结束时间
                        laydate.render({elem: '#test1'});
                        laydate.render({elem: '#test2'});
                        //加载预警类型下拉选择
                        $.post(ctx + "aupipes/yjtables/list", function (data) {
                            var html = '';
                            for (var i = 0; i < data.rows.length; i++) {
                                if (data.rows[i].name == "全部") {
                                    html += '<option value="">' + data.rows[i].name + '</option>';
                                } else {
                                    html += '<option value="' + data.rows[i].name + '">' + data.rows[i].name + '</option>';
                                }
                            }
                            $("select[name='type']").html(html);
                            form.render('select');
                        });
                        form.render();

                        //默认加载初始数据
                        page = 1, yjArr = [];
                        var res = {
                            state: '0',
                            level: '',
                            formSysName: '',
                            dateTime1: '',
                            dateTime2: '',
                            pageNum: page,
                            pageSize: pageSize
                        };
                        searchYjResult(res);
                        //监听提交
                        form.on('submit(yjFormSearch)', function (data) {
                            $("#yjListBox").html("");
                            $(".listMore").css("display", "block");
                            yjArr = [];
                            page = 1;
                            var test1 = $("#test1").val(),
                                test2 = $("#test2").val();

                            res = {
                                state: data.field.status,
                                level: data.field.level,
                                formSysName: data.field.type,
                                dateTime1: test1,
                                dateTime2: test2,
                                pageNum: page,
                                pageSize: pageSize
                            };
                            searchYjResult(res);
                        });

                        $(".listMore").on("click", function () {
                            res.pageNum = page + 1;
                            page = page + 1;
                            if (total > yjArr.length) {
                                searchYjResult(res);
                            } else {
                                $(".listMore").css("display", "none");
                            }
                        });

                    });
                    break;
                case '维修档案':
                    $("#result").load(ctx + "mobile/wxda", function (data) {
                        xjlbResultHeight();

                        //渲染开始和结束时间
                        laydate.render({elem: '#test5'});
                        laydate.render({elem: '#test6'});
                        //加载维修类型下拉选择
                        $.get(ctx + "system/dict/data/api/list", {
                            dictType: "aup_repair_faulttype",
                            pageSize: 30
                        }, function (data) {
                            var html = '';
                            for (var i = 0; i < data.data.length; i++) {
                                html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                            }
                            $("select[name='type']").html(html);
                            form.render('select');
                        });
                        form.render();

                        //默认加载初始数据
                        page = 1, wxArr = [];
                        var res = {
                            description: '',
                            faultId: '',
                            "params[beginTime]": '',
                            "params[endTime]:": '',
                            pageNum: page,
                            pageSize: pageSize
                        };
                        searchWxResult(res);
                        //监听提交
                        form.on('submit(wxFormSearch)', function (data) {
                            $("#wxListBox").html("");
                            $(".listMore").css("display", "block");
                            page = 1, wxArr = [];
                            var test1 = $("#test5").val(),
                                test2 = $("#test6").val();

                            res = {
                                description: data.field.detail,
                                faultId: data.field.type,
                                "params[beginTime]": test1,
                                "params[endTime]:": test2,
                                pageNum: page,
                                pageSize: pageSize
                            };
                            searchWxResult(res);
                        });

                        $(".listMore").on("click", function () {
                            res.pageNum = page + 1;
                            page = page + 1;
                            if (total > wxArr.length) {
                                searchWxResult(res);
                            } else {
                                $(".listMore").css("display", "none");
                            }
                        });

                    });
                    break;
                case "泵房":
                    /* $("#result").load(ctx + "mobile/yxjk", function (data) {
                         xjlbResultHeight();
                         $.get(ctx + 'screen/yxjk/app/query?queryType=pump', function (data) {
                             $("#xjlbListBox").html('');
                             resultArr = data;
                             var html = `<ul>`;
                             for (var i = 0; i < data.data.length; i++) {
                                 html += `<li>
                                         <span class="number">` + (i + 1) + `</span>
                                         <div class="layui-row txt of">
                                             <div class="layui-col-xs6">
                                                 ` + data.data[i].name + `
                                             </div>
                                             <div class="layui-col-xs6 txtR">
                                             市政压力：<span>` + data.data[i].szyl + `</span>
                                             </div>
                                         </div>
                                         <div class="link">
                                             <a href="#" onclick="lookDetail(\`` + elem.text() + `\`, \`` + data.data[i].id + `\`)" class="more"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                                             <a href="#" class="xjgj" onclick="locateBuildingByID(\`` + data.data[i].dingweiCode + `\`, 'PUMP_HOUSE_ANNOTATION');"><i class="iconfont iconyundanguiji"></i>查看定位</a>
                                         </div>
                                     </li>`;
                             }
                             html += '</ul>';
                             $("#xjlbListBox").html(html);
                         })
                     });*/
                    changeLayer(['PUMP_HOUSE_ANNOTATION']);
                    lookPick('PUMP_HOUSE');
                    break;
                case "路灯":
                    changeLayer(['STREET_LIGHT'], 'STREET_LIGHT');
                    //lookPick('STREET_LIGHT');
                    /*$("#result").load(ctx + "mobile/yxjk", function (data) {
                        xjlbResultHeight();
                        $.get(ctx + 'screen/yxjk/app/query?queryType=light', function (data) {
                            $("#xjlbListBox").html('');
                            resultArr = data;
                            var html = `<ul>`;
                            for (var i = 0; i < data.data.length; i++) {
                                html += `<li>
                                        <span class="number">` + (i + 1) + `</span>
                                        <div class="layui-row txt of">
                                            <div class="layui-col-xs6">
                                                ` + data.data[i].name + `
                                            </div>
                                            <div class="layui-col-xs6 txtR">
                                            路灯总数：<span>` + data.data[i].totalCount + `</span>
                                            </div>
                                        </div>
                                        <div class="link">
                                            <a href="#" onclick="lookDetail(\`` + data.data[i].sid + `\`, \`` + data.data[i].sid + `\`)" class="more"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                                            <a href="#" class="xjgj" onclick="locateBuildingByID(\`` + data.data[i].sid + `\`, 'CONTROLLER');"><i class="iconfont iconyundanguiji"></i>查看定位</a>
                                        </div>
                                    </li>`;
                            }
                            html += '</ul>'
                            $("#xjlbListBox").html(html);
                        })
                    });*/
                    break;
                case "探漏":
                    changeLayer(['LEAKAGE'], 'LEAKAGE');
                    break;
                case "配电房":
                    changeLayer(['TRANSFORMER_ROOM_ANNOTATION'], 'TRANSFORMER_ROOM');
                    break;
                case "水电用户":
                    changeLayer(['HOUSE_ANNOTATION'], 'HOUSE_ANNOTATION_SDYH');
                    break;
                case "安防":
                    changeLayer(['MONITOR'], 'MONITOR');
                    break;
                case "水质":
                    changeLayer(['WATER_MONITOR'], 'WATER_MONITOR');
                    break;
                case "配电房":
                    changeLayer(['TRANSFORMER_ROOM_ANNOTATION'], 'TRANSFORMER_ROOM');
                    break;
                case "控制器":
                    changeLayer(['CONTROLLER'], 'CONTROLLER');
                    break;
                case "开挖分析":
                    kwfx.init();
                    $("#result").load(ctx + "mobile/fx", function (data) {
                        xjlbResultHeight();
                        form.render();
                    });
                    break;
                case '拉闸限电':
                    changeLayer(['HOUSE_ANNOTATION', 'GD_POINT', 'GD_LINE'], 'HOUSE_ANNOTATION');
                    break;
                case '关阀停水':
                    loading = layer.load(0, {
                        shade: false
                    });
                    $("#result").load(ctx + "mobile/searchResult", function (data) {
                        queryGfts(cfg.gftsLayer, radiusGf);
                    });
                    break;
                case '智能排管':

                    break;
                case "耗水":
                    changeLayer(['HOUSE_ANNOTATION'], 'HOUSE_ANNOTATION_HS');
                    nhjgQuery('rootw');
                    break;
                case "耗电":
                    changeLayer(['HOUSE_ANNOTATION'], 'HOUSE_ANNOTATION_HD');
                    nhjgQuery('roote');
                    break;
                case "下发任务":
                    ydxj.xfrw();
                    break;
                default:
                    layer.msg(elem.text());
                    break;
            }
        }


    });
});

//查看周边搜索详情
function lookDetail(name, id) {
    $("#detail").html("");
    $("#detail").css("display", "block");
    switch (menuType) {
        case '搜周边':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                for (var i = 0; i < resultArr.length; i++) {
                    if (name == resultArr[i].id) {
                        var result = resultArr[i].data;
                        for (var j = 0; j < result.length; j++) {
                            if (result[j].properties["OID"] == id || result[j].properties["id"] == id || result[j].properties["SID"] == id) {
                                pickName = result[j].properties["SNAME"];
                                var obj;
                                if (name == "STREET_LIGHT") {
                                    $.get(ctx + "screen/yxjk/app/streetlight/detail/" + result[j].properties["LID"], function (res) {
                                        $(".listPipe").html("");
                                        var html = "";
                                        obj = cfg.STREET_LIGHT;
                                        for (var key in obj) {
                                            html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data[key] + `</p>
                                            </li>`;
                                        }
                                        $(".listPipe").html(html);
                                    });
                                } else if (name == "LEAKAGE") {
                                    $.get(ctx + "screen/yxjk/app/leakApp/" + id, function (res) {
                                        var html = "";
                                        obj = cfg.LEAKAGE_YXJK;
                                        for (var key in obj) {
                                            var status = res.data[key];
                                            if (obj[key] == "状态") {
                                                status = status == '0' ? '正常' : '异常';
                                            }
                                            html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + status + `</p>
                                            </li>`;
                                        }
                                        $(".listPipe").html(html);
                                    });
                                } else if (name == "WATER_MONITOR") {
                                    $.get(ctx + "screen/yxjk/jdxx/waterQuality/" + id, function (res) {
                                        $(".listPipe").html("");
                                        var html = "";
                                        html += `<li>
                                                <label class="left fl">名称:</label>
                                                <p class="right fr">` + pickName + `</p>
                                            </li>
                                            <li>
                                                <label class="left fl">类型:</label>
                                                <p class="right fr">` + res.data.type + `</p>
                                            </li>`;
                                        if (res.data.type == "水质") {
                                            obj = cfg.WATER_MONITOR_SZ_YXJK;
                                            for (var key in obj) {
                                                html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data.szjc[key] + `</p>
                                            </li>`;
                                            }
                                        } else {
                                            obj = cfg.WATER_MONITOR_LS_YXJK;
                                            for (var key in obj) {
                                                html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data.lsjc[key] + `</p>
                                            </li>`;
                                            }
                                        }
                                        $(".listPipe").html(html);
                                    });
                                } else if (name == "CONTROLLER") {
                                    $.get(ctx + "screen/yxjk/app/streetController/" + id, function (res) {
                                        $(".listPipe").html("");
                                        var html = "";
                                        obj = cfg.CONTROLLER;
                                        for (var key in obj) {
                                            var datetime = '';

                                            html += `<li><label class="left fl">` + obj[key] + `:</label>`;
                                            if (key == 'dtime') {
                                                var date = new Date(parseInt(res.data[key]));//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                                                var Y = date.getFullYear() + '-';
                                                var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                                                var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
                                                var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
                                                var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
                                                var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                                                datetime = Y + M + D + h + m + s;
                                                html += ` <p class="right fr">` + datetime + `</p> </li>`;
                                            } else if (key == 'status') {
                                                var status = '';
                                                if (res.data[key] == '1') {
                                                    status = '在线';
                                                } else {
                                                    status = '离线';
                                                }
                                                html += ` <p class="right fr">` + status + `</p> </li>`;
                                            } else {
                                                html += ` <p class="right fr">` + res.data[key] + `</p> </li>`;
                                            }
                                        }
                                        $(".listPipe").html(html);
                                    });
                                } else {
                                    if (name.indexOf("POINT") > -1) {
                                        obj = cfg.POINT;
                                    } else if (name.indexOf("LINE") > -1) {
                                        obj = cfg.LINE;
                                    } else if (name == "MONITOR") {
                                        obj = cfg.MONITOR;
                                    } else if (name == "DOOR_CONTROL") {
                                        obj = cfg.DOOR_CONTROL;
                                    } else if (name == "record") {
                                        obj = cfg.record;
                                    }
                                    $(".listPipe").html("");
                                    var html = "";
                                    for (var key in obj) {
                                        html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + result[j].properties[key] + `</p>
                                            </li>`;
                                    }
                                    $(".listPipe").html(html);
                                }
                            }
                        }
                    }
                }
            });
            break;
        case '巡查列表':
            $("#detail").html("");
            $("#detail").load(ctx + "mobile/xjlbDetail", function (data) {
                //请求详细信息数据
                $("#result").css("display", "none");
                $("#detail").css("display", "block");
                ydxj.id = id;
                //修改当前任务状态为
                //加载任务信息
                $.post(ctx + "aupipes/inspect/list", {id: id}, function (data1) {
                    if (data1.code == 0) {
                        if (data1.rows[0].remark == null) {
                            data1.rows[0].remark = "";
                        }
                        ydxj.taskName = data1.rows[0].inspectName;
                        ydxj.inspectWorker = data1.rows[0].inspectWorker;
                        ydxj.inspectWorkerId = data1.rows[0].inspectWorkerId;
                        ydxj.inspectAddress = data1.rows[0].inspectArea;
                        var str = "";
                        str += `<div class="layui-row layui-col-space5">
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">任务名称：</label>
                                    <div class="layui-input-block">
                                        <input type="text" class="layui-input" value="` + data1.rows[0].inspectName + `" readonly="readonly">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">巡检人员：</label>
                                    <div class="layui-input-block">
                                        <input type="text" class="layui-input" value="` + data1.rows[0].inspectWorker + `" readonly="readonly">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">巡检区域：</label>
                                    <div class="layui-input-block">
                                        <input type="text" class="layui-input" value="` + data1.rows[0].inspectArea + `" readonly="readonly">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">巡检状态：</label>
                                    <div class="layui-input-block">
                                        <select name="interest" id="interestLookDetail" lay-filter="aihao" disabled="disabled" readonly="readonly">
                                            <option value=""></option>
                                            <option value="0">待巡</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注说明：</label>
                                    <div class="layui-input-block">
                                        <textarea name="desc" placeholder="请输入内容" class="layui-textarea">` + data1.rows[0].remark + `</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="exceptionRecord">
                            </div>
                        </div>`;
                        $("#xjlbDetail").html("");
                        $("#xjlbDetail").html(str);

                        layui.use('form', function () {
                            var form = layui.form;
                            //加载巡检任务类型下拉选择
                            $.get(ctx + "system/dict/data/api/list", {
                                dictType: "aup_inspect_status",
                                pageSize: 30
                            }, function (data2) {
                                var html = '';
                                for (var i = 0; i < data2.data.length; i++) {
                                    html += '<option value="' + data2.data[i].dictValue + '">' + data2.data[i].dictLabel + '</option>';
                                }
                                $("select[name='interest']").html(html);
                                form.render('select');
                                //默认勾选
                                var select = document.getElementById("interestLookDetail");
                                for (var i = 0; i < select.options.length; i++) {
                                    if (select.options[i].value == data1.rows[0].inspectStatus) {
                                        select.options[i].selected = true;
                                        break;
                                    }
                                }
                                form.render('select');
                            });
                        });
                        //加载设备列表
                        $.post(ctx + "aupipes/detail/list", {taskId: id}, function (data) {
                            if (data.code == 0) {
                                var str = "";
                                for (var i = 0; i < data.rows.length; i++) {
                                    //加载设备状态下拉选择
                                    str += `<li onclick="ydxj.ydxjLookXjjl('` + data.rows[i].id + "'," + data.rows[i].serviceStatus + "," + data.rows[i].serviceTypeId + ",'" + data.rows[i].description + `');">
                            <!--<li>--><span class="number">` + (i + 1) + `</span>
                            <div class="layui-row">
                                <!-- <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                    <label for="">设备名称：</label>` + data.rows[i].serviceName + `
                                </div>  -->
                                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                    <label for="">设备状态：</label>` + ydxj.serviceStatusJson[data.rows[i].serviceStatus] + `
                                </div>
                                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                    <label for="">设备类型：</label>` + ydxj.serviceTypeJson[data.rows[i].serviceTypeId] + `
                                </div>
                                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                    <label for="">巡检地点：</label>` + data.rows[i].serviceAddress + `
                                </div>
                            </div>
                        </li>`;
                                }
                                $(".exceptionRecord").html("");
                                $(".exceptionRecord").html(str);
                            }
                        });
                    }
                });
            });
            break;
        case '预警信息':
            $("#detail").load(ctx + "mobile/yjxxDetail", function (data) {
                $(".listDetail").css("display", "none");
                $.post(ctx + "screen/yjcz/yjxqByid?id=" + id + "&name=" + name, function (data) {
                    if (data.ImageUrl1) {
                        if (ctx != '/') {
                            data.ImageUrl1 = ctx + data.ImageUrl1;
                        }
                    }
                    var vm = new Vue({
                        el: '.listDetail',
                        data: {
                            list: data,//属性信息
                            name: '',
                            info: {
                                num: 1,
                                p: 0,
                                i: 0,
                                u: 0,
                                e: 0,
                                pf: 0,
                                readTime: "2020-04-09 15:31:20",
                                voltage: 0,
                                name: ''
                            },//监测信息
                        }
                    });
                    vm.list.state = cfg.status[vm.list.state];
                    vm.list.level = cfg.level[vm.list.level];

                    $(".listDetail").css("display", "block");

                    //请求监测信息
                    $.post(ctx + "screen/yjcz/getInfomation?id=" + id + "&name=" + name, function (data) {
                        //当data不为空
                        if (data != "null" || data != "无此编号") {
                            if (vm.list.formSysName == "路灯" || vm.list.formSysName == "水质") {
                                vm.info = data[0];
                                vm.info.sname = {sname: ''};
                            } else if (vm.list.formSysName == "泵房") {
                                vm.info.name = data[0].value;
                                vm.info.voltage = data[1].value;
                            } else if (vm.list.formSysName == "探漏") {
                                xhbdsData({siteNO: vm.list.code});
                            } else {
                                vm.info = data;
                            }

                            if (vm.list.formSysName == "水质" || vm.list.formSysName == "监控") {
                                $.post(ctx + "screen/yjcz/getPosition?itid=" + id + "&name=" + name, function (data) {
                                    vm.info.sname = data;
                                    vm.name = data.sname;
                                });
                            }
                        }
                    });
                });
            });
            break;
        case '维修档案':
            $("#detail").load(ctx + "mobile/wxdaDetail", function (data) {
                $(".listDetail").css("display", "none");
                $.post(ctx + "aupipes/info/list", {fileTarget: id, pageSize: 50}, function (data) {
                    var obj = {};
                    for (var i = 0; i < wxArr.length; i++) {
                        if (id == wxArr[i].id) {
                            obj = wxArr[i];
                        }
                    }
                    var vm = new Vue({
                        el: '.listDetail',
                        data: {
                            list: obj,
                            pipeList: []//管线列表
                        }
                    });

                    $(".materials").html("");
                    var content = "";
                    for (var j = 0; j < data.rows.length; j++) {
                        var name = data.rows[j].filePath.substring(0, data.rows[j].filePath.lastIndexOf("-"));
                        var m = data.rows[j].filePath.lastIndexOf("-") + 1;
                        var n = data.rows[j].filePath.lastIndexOf("/");
                        var suffix = data.rows[j].filePath.substring(m, n);
                        var realPath = name + "." + suffix;
                        if (data.rows[j].fileType == "image/png") {
                            content += `<div class="layui-col-xs4 layui-col-sm3 layui-col-md3">
                                        <img src="` + ctx + `` + realPath + `" alt=""/>
                                    </div>`;
                        } else if (data.rows[j].fileType == "video/mp4") {
                            content += `<div class="layui-col-xs4 layui-col-sm3 layui-col-md3">
                                        <video src="` + ctx + `` + realPath + `" controls="controls"></video>
                                    </div>`;
                        }
                    }
                    $(".materials").html(content);

                    $.post(ctx + "aupipes/pipeline/list", {taskId: id, pageSize: 50}, function (data) {
                        vm.pipeList = data.rows;
                        $(".listDetail").css("display", "block");
                    });
                    //$(".listDetail").css("display", "block");
                });
            });
            break;
        case '泵房':
            $("#detail").load(ctx + "mobile/yxjkDetail", function (data) {
                $(".listDetail").css("display", "none");
                $.get(ctx + "screen/yxjk/app/query?queryType=pump", function (data) {
                    resultArr = data;
                    layer.close(loading);
                    for (var i = 0; i < resultArr.data.length; i++) {
                        if (resultArr.data[i].dingweiCode == id) {
                            var arr = [];
                            for (var key in resultArr.data[i].points) {
                                var names = resultArr.data[i].points[key][0].attrName.split(",");
                                var values = resultArr.data[i].points[key][0].attrValue.split(",");
                                var pumpVaue = [];

                                for (var c in names) {
                                    var k = {};
                                    var mm = names[c];
                                    var dw;
                                    if (names[c] == "出口压力" || names[c] == "设定压力") {
                                        dw = 'Bar';
                                    } else if (names[c] == "水箱液位") {
                                        dw = 'm';
                                    } else if (names[c] == "输出频率") {
                                        dw = 'Hz';
                                    }
                                    k[mm] = values[c] + dw;
                                    pumpVaue.push(k);
                                }
                                var szyl = values[names.length] ? values[names.length] : 0.0;

                                var obj = {
                                    name: key,
                                    list: resultArr.data[i].points[key],
                                    num: resultArr.data[i].points[key].length,
                                    pump: pumpVaue,
                                    szyl: szyl
                                };
                                arr.push(obj);
                            }

                            var vm = new Vue({
                                el: '.listDetail',
                                data: {
                                    list: resultArr.data[i],
                                    arr: arr
                                }
                            });
                            $(".listDetail").css("display", "block");
                        }
                    }
                })
            });
            break;
        case '路灯':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                $.get(ctx + "screen/yxjk/app/streetlight/detail/" + id, function (res) {
                    $(".listPipe").html("");
                    var html = "";
                    obj = cfg.STREET_LIGHT;
                    for (var key in obj) {
                        html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data[key] + `</p>
                                            </li>`;
                    }
                    layer.close(loading);
                    $(".listPipe").html(html);
                });
            });
            break;
        case '探漏':
            $("#detail").load(ctx + "mobile/leakDetail", function (data) {
                $.get(ctx + "screen/yxjk/app/leakApp/" + id, function (res) {
                    $(".listPipe").html("");
                    var html = "";
                    obj = cfg.LEAKAGE_YXJK;
                    for (var key in obj) {
                        var status = res.data[key];
                        if (obj[key] == "状态") {
                            status = status == '0' ? '正常' : '异常';
                        }
                        html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + status + `</p>
                                            </li>`;
                    }

                    xhbdsData({siteno: res.data.siteno});
                    layer.close(loading);
                    $(".listPipe").html(html);
                });
            });
            break;
        case '安防':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                $.get(ctx + "screen/yxjk/app/monitorApp/" + id, function (res) {
                    $(".listPipe").html("");
                    var html = "";
                    obj = cfg.MONITOR_YXJK;
                    for (var key in obj) {
                        html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data[key] + `</p>
                                            </li>`;
                    }
                    layer.close(loading);
                    $(".listPipe").html(html);
                });
            });
            break;
        case '水质':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                $.get(ctx + "screen/yxjk/jdxx/waterQuality/" + id, function (res) {
                    $(".listPipe").html("");
                    var html = "";
                    html += `<li>
                                <label class="left fl">名称:</label>
                                <p class="right fr">` + pickName + `</p>
                            </li>
                            <li>
                                <label class="left fl">类型:</label>
                                <p class="right fr">` + res.data.type + `</p>
                            </li>`;
                    if (res.data.type == "水质") {
                        obj = cfg.WATER_MONITOR_SZ_YXJK;
                        for (var key in obj) {
                            html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data.szjc[key] + `</p>
                                            </li>`;
                        }
                    } else {
                        obj = cfg.WATER_MONITOR_LS_YXJK;
                        for (var key in obj) {
                            html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + res.data.lsjc[key] + `</p>
                                            </li>`;
                        }
                    }
                    $(".listPipe").html(html);
                    layer.close(loading);
                });
            });
            break;
        case '控制器':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                $.get(ctx + "screen/yxjk/app/streetController/" + id, function (res) {
                    $(".listPipe").html("");
                    var html = "";
                    obj = cfg.CONTROLLER;
                    for (var key in obj) {
                        var datetime = '';
                        html += `<li><label class="left fl">` + obj[key] + `:</label>`;
                        if (key == 'dtime') {
                            var date = new Date(parseInt(res.data[key]));//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                            var Y = date.getFullYear() + '-';
                            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                            var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
                            var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
                            var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
                            var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                            datetime = Y + M + D + h + m + s;
                            html += ` <p class="right fr">` + datetime + `</p> </li>`;
                        } else if (key == 'status') {
                            var status = '';
                            if (res.data[key] == '1') {
                                status = '在线';
                            } else {
                                status = '离线';
                            }
                            html += ` <p class="right fr">` + status + `</p> </li>`;
                        } else {
                            html += ` <p class="right fr">` + res.data[key] + `</p> </li>`;
                        }
                    }
                    layer.close(loading);
                    $(".listPipe").html(html);
                });
            });
            break;
        case '开挖分析':
            var obj;
            if (name.indexOf("点") > -1) {
                obj = cfg.POINT;
            } else {
                obj = cfg.LINE;
            }
            for (var i = 0; i < kwfx.jsonArr.length; i++) {
                if (kwfx.jsonArr[i].name == name) {
                    for (var j = 0; j < kwfx.jsonArr[i].result.length; j++) {
                        if (id = kwfx.jsonArr[i].result[j]['OID']) {
                            var result = kwfx.jsonArr[i].result[j];
                            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                                $(".listPipe").html("");
                                var html = "";
                                for (var key in obj) {
                                    html += `<li>
                                            <label class="left fl">` + obj[key] + `:</label>
                                            <p class="right fr">` + result[key] + `</p>
                                        </li>`;
                                }
                                $(".listPipe").html(html);
                            });
                        }
                    }
                }
            }
            break;
        case '拉闸限电':
            loading = layer.load(0, {
                shade: false
            });

            $.get(ctx + "screen/jcbz/appLzxd?fwId=" + id, function (res) {
                var list = [], info = [];
                if (res.data) {
                    for (var i = 0; i < res.data.pdfInfo.length; i++) {
                        list.push(res.data.pdfInfo[i][0]);
                    }
                    for (var j = 0; j < res.data.fwInfo.length; j++) {
                        info.push(res.data.fwInfo[j][0]);
                    }
                    $("#detail").load(ctx + "mobile/lzxdDetail", function (data) {
                        layer.close(loading);
                        var vm = new Vue({
                            el: '.listDetail',
                            data: {
                                list: list,//属性信息
                                info: info,
                            }
                        });
                    })
                } else {
                    layer.close(loading);
                    layer.msg("暂无影响数据！");
                }
            });
            break;
        case '关阀停水':
            $("#detail").load(ctx + "mobile/searchDetail", function (data) {
                for (var i = 0; i < resultArr.length; i++) {
                    if (name == resultArr[i].id) {
                        var result = resultArr[i].data;
                        for (var j = 0; j < result.length; j++) {
                            if (result[j].properties["OID"] == id || result[j].properties["id"] == id || result[j].properties["SID"] == id) {
                                pickName = result[j].properties["SNAME"];
                                var obj;
                                if (name.indexOf("POINT") > -1) {
                                    obj = cfg.POINT;
                                } else if (name.indexOf("LINE") > -1) {
                                    obj = cfg.LINE;
                                }
                                $(".listPipe").html("");
                                var html = "";
                                for (var key in obj) {
                                    html += `<li>
                                                <label class="left fl">` + obj[key] + `:</label>
                                                <p class="right fr">` + result[j].properties[key] + `</p>
                                            </li>`;
                                }
                                $(".listPipe").html(html);
                            }
                        }
                    }
                }
            });
            break;
        case '智能排管':

            break;
        case "下发任务":
            alert("1069")
            break;
        case '我的':
            layer.msg(elem.text());
            break;
        default:
            layer.msg(elem.text());
            break;
    }
}

//预警信息查询
function searchYjResult(res) {
    $.post(ctx + "screen/yjcz/getAllInfo?formSysName=" + res.formSysName + "&state=" + res.state + "&level=" + res.level + "&dateTime1=" + res.dateTime1 + "&dateTime2=" + res.dateTime2 + "&pageSize=" + res.pageSize + "&pageNum=" + res.pageNum, function (data) {
        total = data.total;
        var html = '';
        for (var i = 0; i < data.rows.length; i++) {
            yjArr.push(data.rows[i]);
            var typeName = data.rows[i].formSysName;
            html += `<li>
                    <span class="number">` + yjArr.length + `</span>
                    <div class="layui-row txt of">
                    <div class="layui-col-xs6">
                    预警级别：<img class="yjjbIcon" src="` + ctx + `bus/aupipes/mobile/images/grade` + data.rows[i].level + `.gif" alt=""/><span>` + cfg.level[data.rows[i].level] + `</span>
                    </div>
                    <div class="layui-col-xs6 txtR">
                    预警类别：<span>` + data.rows[i].formSysName + `</span>
                </div>
                </div>
              <div class="link">
                    <a href="#" class="more" onclick="lookDetail(\`` + data.rows[i].formSysName + `\`,\`` + data.rows[i].id + `\`)"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                    <a href="#" class="xjgj" onclick="getPosition(\`` + data.rows[i].id + `\`,\`` + data.rows[i].formSysName + `\`)"><i class="iconfont iconyundanguiji"></i>查看定位</a>
                </div>
                </li>`;
        }
        $("#yjListBox").append(html);
    });

}

function getPosition(id, name) {
    clearAll();
    //定位`
    $.post(ctx + "screen/yjcz/getPosition?itid=" + id + "&name=" + name, function (result) {
        var positionid = result;
        closeResultMore();
        if (name == '水表') {
            layerTree.locateBuildingByID(positionid.Buildingno, "HOUSE");
        } else if (name == '电表') {
            layerTree.locateBuildingByID(positionid.Buildingno, "HOUSE");

        } else if (name == '监控') {
            layerTree.locateBuildingByID(positionid.cameraIndexCode, "MONITOR");

        } else if (name == '路灯') {
            if (positionid.lid == undefined) {
                layerTree.locateBuildingByID(positionid.sid, "CONTROLLER");
            } else {
                layerTree.locateBuildingByID(positionid.lid, "STREET_LIGHT");
            }
        } else if (name == '探漏') {
            layerTree.locateBuildingByID('' + positionid.placeId + '', "LEAKAGE");
        } else if (name == '水质') {
            layerTree.locateBuildingByID(positionid.sid, "WATER_MONITOR");
        } else if (name == '泵房') {
            layerTree.locateBuildingByID(positionid.bfid, "PUMP_HOUSE_ANNOTATION");
        }

    });


}

//巡检信息查询
function searchXjResult(res) {
    $.post(ctx + "aupipes/inspect/list", res, function (data) {
        total = data.total;
        var html = '';
        for (var i = 0; i < data.rows.length; i++) {
            xjArr.push(data.rows[i]);
            html += `<li>
                <span class="number">` + xjArr.length + `</span>
                <div class="layui-row txt of">
                    <div class="layui-col-xs12">
                        巡检位置：<span>` + data.rows[i].inspectArea + `</span>
                    </div>
                    <div class="layui-col-xs12">
                        巡检人：<span>` + data.rows[i].inspectWorker + `</span>
                    </div>
                </div>
                <div class="link">`;
            if (data.rows[i].inspectStatus == 1) {   //在巡
                /*html += `<a href="#" class="more" onclick="lookDetail(\`` + data.rows[i].username + `\`,\`` + data.rows[i].id + `\`)"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                         <a href="#" class="xjgj" onclick="trackPlay(\`` + data.rows[i].id + `\`)"><i class="iconfont iconyundanguiji"></i>巡检轨迹</a>`;*/
                html += `<a href="#" class="ksxj" onclick="startLook(\`` + data.rows[i].id + `\`)" style="width: 100%;"><i class="iconfont iconkaishixunjian"></i>开始巡检</a>`;
            } else if (data.rows[i].inspectStatus == 0) { //未巡
                html += `<a href="#" class="ksxj" onclick="startLook(\`` + data.rows[i].id + `\`)" style="width: 100%;"><i class="iconfont iconkaishixunjian"></i>开始巡检</a>`;
            } else {
                html += `<a href="#" class="more" onclick="lookDetail(\`` + data.rows[i].inspectWorker + `\`,\`` + data.rows[i].id + `\`)"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                         <a href="#" class="xjgj" onclick="trackPlay(\`` + data.rows[i].id + `\`)"><i class="iconfont iconyundanguiji"></i>巡检轨迹</a>`;
            }
            html += `</div>
            </li>`;
        }
        $("#xjListBox").append(html);
    });
    /**
     * 加载字典选项
     */
    //加载设备类型下拉选择
    $.get(ctx + "system/dict/data/api/list", {
        dictType: "aup_service_type",
        pageSize: 30
    }, function (data) {


        var obj = {}, obj1 = {};
        for (var i = 0; i < data.data.length; i++) {
            obj1[data.data[i].dictValue] = data.data[i].dictLabel;
            ydxj.serviceTypeJson = Object.assign(obj, obj1);
        }
    });
    //加载设备状态下拉选择
    $.get(ctx + "system/dict/data/api/list", {
        dictType: "aup_service_status",
        pageSize: 30
    }, function (data) {
        var obj = {}, obj1 = {};
        for (var i = 0; i < data.data.length; i++) {
            obj1[data.data[i].dictValue] = data.data[i].dictLabel;
            ydxj.serviceStatusJson = Object.assign(obj, obj1);
        }
    });

}

//巡检轨迹查看
function trackPlay(id) {
    $.post(ctx + "aupipes/coordinate/geojson", {taskId: id % 5}, function (result) {
        if (result.code == 0) {
            //显示轨迹前先收缩结果列表，避免遮挡
            closeResultMore();
            trackPlayback.historyGeoData = result.data;
            trackPlayback.isWorking = true;
            trackPlayback.start();
        }
    });
}

//开始巡检
function startLook(id) {
    $("#result").css("display", "none");
    $("#detail").html("");
    $("#detail").load(ctx + "aupipes/detail/startLook?id=" + id, function (data) {
        $("#detail").css("display", "block");
        ydxj.id = id;
        //修改当前任务状态为
        $.post(ctx + "aupipes/inspect/edit", {"id": id, "inspectStatus": "1"}, function (result) {
            //加载任务信息
            $.post(ctx + "aupipes/inspect/list", {id: id}, function (data1) {
                if (data1.code == 0) {
                    if (data1.rows[0].remark == null) {
                        data1.rows[0].remark = "";
                    }
                    ydxj.taskName = data1.rows[0].inspectName;
                    ydxj.inspectWorker = data1.rows[0].inspectWorker;
                    ydxj.inspectWorkerId = data1.rows[0].inspectWorkerId;
                    ydxj.inspectAddress = data1.rows[0].inspectArea;
                    var str = "";
                    str += `<div class="layui-row layui-col-space5">
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                    <div class="layui-form-item">
                        <label class="layui-form-label">任务名称：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" value="` + data1.rows[0].inspectName + `">
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                    <div class="layui-form-item">
                        <label class="layui-form-label">巡检人员：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" value="` + data1.rows[0].inspectWorker + `">
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                    <div class="layui-form-item">
                        <label class="layui-form-label">巡检区域：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" value="` + data1.rows[0].inspectArea + `">
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                    <div class="layui-form-item">
                        <label class="layui-form-label">巡检状态：</label>
                        <div class="layui-input-block">
                            <select name="interest" id="interest" lay-filter="aihao">
                                <option value=""></option>
                                <option value="0">待巡</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注说明：</label>
                        <div class="layui-input-block">
                            <textarea name="desc" placeholder="请输入内容" class="layui-textarea">` + data1.rows[0].remark + `</textarea>
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                    <div class="layui-form-item">
                        <label class="layui-form-label">设备巡检：</label>
                        <div class="layui-input-block sbxjBtn txtC">
                            <button type="button" class="layui-btn bg_green" onclick="ydxj.ydxj1Add();">新增</button>
                            <button type="button" class="layui-btn bg_green" onclick="ydxj.ydxj1Stop();">结束</button>
                        </div>
                    </div>
                </div>
            </div>`;
                    $("#ydxj1XjlbForm").html("");
                    $("#ydxj1XjlbForm").html(str);

                    layui.use('form', function () {
                        var form = layui.form;
                        //加载巡检任务类型下拉选择
                        $.get(ctx + "system/dict/data/api/list", {
                            dictType: "aup_inspect_status",
                            pageSize: 30
                        }, function (data2) {
                            var html = '';
                            for (var i = 0; i < data2.data.length; i++) {
                                html += '<option value="' + data2.data[i].dictValue + '">' + data2.data[i].dictLabel + '</option>';
                            }
                            $("select[name='interest']").html(html);
                            form.render('select');
                            //默认勾选
                            var select = document.getElementById("interest");
                            for (var i = 0; i < select.options.length; i++) {
                                if (select.options[i].value == data1.rows[0].inspectStatus) {
                                    select.options[i].selected = true;
                                    break;
                                }
                            }
                            form.render('select');
                        });
                    });
                }
            });
        });
        //加载设备列表
        $.post(ctx + "aupipes/detail/list", {taskId: id}, function (data) {
            if (data.code == 0) {
                var str = "";
                for (var i = 0; i < data.rows.length; i++) {
                    str += `<div class="layui-row layui-col-space5">
                                <div class="layui-col-xs2 layui-col-md2 layui-col-md2">
                                    ` + (i + 1) + `
                                </div>
                                <div class="layui-col-xs2 layui-col-md2 layui-col-md2">
                                    ` + ydxj.serviceStatusJson[data.rows[i].serviceStatus] + `
                                </div>
                                <div class="layui-col-xs2 layui-col-md2 layui-col-md2">
                                    ` + ydxj.serviceTypeJson[data.rows[i].serviceTypeId] + `
                                </div>
                                <div class="layui-col-xs2 layui-col-md2 layui-col-md2">
                                    ` + data.rows[i].serviceAddress + `
                                </div>
                                <div class="layui-col-xs4 layui-col-md4 layui-col-md4">
                                    <button class="layui-btn bg_greens" onclick="ydxj.ydxj1Edit('` + data.rows[i].id + "'," + data.rows[i].serviceStatus + "," + data.rows[i].serviceTypeId + ",'" + data.rows[i].description + `');">编辑</button>
                                    <button class="layui-btn bg_greens" onclick="ydxj.deleteNum('` + data.rows[i].id + `')">删除</button>
                                </div>
                            </div>`
                }
                $(".wxdaTableBody").html("");
                $(".wxdaTableBody").html(str);
            }
        });
    });
}

//维修信息查询
function searchWxResult(res) {
    $.post(ctx + "aupipes/repair/list", res, function (data) {
        total = Number(data.total);
        var html = '';
        for (var i = 0; i < data.rows.length; i++) {
            wxArr.push(data.rows[i]);
            html += `<li>
                <span class="number">` + wxArr.length + `</span>
                <div class="layui-row txt of">
                    <div class="layui-col-xs6">
                        维修名称：<span>` + data.rows[i].name + `</span>
                    </div>
                    <div class="layui-col-xs6 txtR">
                        维修人员：<span>` + data.rows[i].userName + `</span>
                    </div>
                </div>
                <div class="link xjlbLink">
                    <a href="#" class="more" onclick="lookDetail(\`` + data.rows[i].userName + `\`,\`` + data.rows[i].id + `\`)"><i class="iconfont iconxiangqing-"></i>查看详情</a>
                </div>
            </li>`;
        }
        $("#wxListBox").append(html);
    });
}


/**
 * 信号波动
 */
function xhbdsData(data) {
    var Categories = '';
    var SeriesData = '';
    $.ajax({
        url: top.celouService + "/Ajax/HZNYGetCurveGraphValue",
        type: "post",
        async: false,
        data: data,
        dataType: "jsonp",
        success: function (data) {
            var xhbdData = data.Data[0];
            var ycxssData = data.Data[1];
            Categories = xhbdData.Categories;
            SeriesData = xhbdData.SeriesData;
            var title = data.RecDatetime;
            leak_Charts(Categories, SeriesData, title);
            ycxs_Chart(ycxssData.SeriesData, ycxssData.SeriesData2, title);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

function leak_Charts(Categories, SeriesData, title) {
    var xhbd_Charts = echarts.init(document.getElementById("xhbd_Charts"));
    var chartOption = {
        title: {
            text: title,
            subtext: '',
            x: 'center',
            y: 'bottom',
            textStyle: {
                fontFamily: 'Arial, Verdana, sans...',
                fontSize: 12,
                fontStyle: 'normal',
                fontWeight: 'normal',
                color: '#fff'
            },
        },
        color: ['#97904a'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '15%',
            right: '15%',
            bottom: '20px',
            top: '30px',
            containLabel: true
        },
        xAxis: [
            {
                name: '毫秒',//100μs
                type: 'category',
                boundaryGap: false,
                data: Categories,
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    //show:false,
                    lineStyle: {
                        // type: 'dotted',
                        color: '#fff',//x线的颜色
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#fff"
                    }
                },
            }
        ],
        yAxis: [
            {
                name: 'K(振动强度)',
                type: 'value',
                splitArea: {show: true},
                axisLine: {
                    lineStyle: {
                        color: '#fff',//x线的颜色
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#fff"
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: 'rgba(6,235,235,.4)',//x线的颜色
                    }
                },
            }
        ],
        series: [
            {
                type: "bar",
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: 'red',
                            width: 0.9
                        }

                    }
                },
                data: SeriesData,
                hoverAnimation: false,
                showSymbol: false,
            }
        ]
    };
    xhbd_Charts.setOption(chartOption);
}

function ycxs_Chart(SeriasData, SeriasData2, title) {
    var ycxs_Charts = echarts.init(document.getElementById("ycxs_Charts"));
    var option = {
        title: {
            text: title,
            subtext: '',
            x: 'center',
            y: 'bottom',
            textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                fontFamily: 'Arial, Verdana, sans...',
                fontSize: 12,
                fontStyle: 'normal',
                fontWeight: 'normal',
                color: '#fff'
            },
        },
        tooltip: {
            show: true
        },
        grid: {
            left: '15%',
            right: '15%',
            bottom: '20px',
            top: '30px',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#fff',//x线的颜色
                    }
                },
            }
        ],
        yAxis: [
            {
                name: '异常系数',//'K(振动强度)',
                type: 'value',
                splitArea: {show: true},
                max: 1.0,
                min: 0.0,
                axisLabel: {
                    formatter: '{value} ml'
                },
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: "#fff"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff',//x线的颜色
                    }
                },
                splitLine: {
                    show: false
                },
            },
            {
                type: 'value',
                name: '',
                min: 0,
                max: 20,
                interval: 5,
                axisLabel: {
                    formatter: '{value} °C'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#fff"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff',//x线的颜色
                    }
                },
                splitLine: {
                    show: false
                },
            }
        ],
        series: [

            {
                name: '',
                type: 'line',
                yAxisIndex: 0,
                symbolSize: 1,   //设定实心点的大小
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#9e5020'
                        }])
                    }
                },
                data: SeriasData
            },
            {
                name: '111',
                type: 'line',
                yAxisIndex: 1,
                symbolSize: 2,   //设定实心点的大小
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient
                        (0, 0, 0, 1, [{
                            offset: 0,
                            color: '#9e5020'
                        }])
                    }
                },
                data: SeriasData2
            }
        ]
    };
    ycxs_Charts.setOption(option);
}

//阀门关系
function lookTapInfo(id, depth) {
    $("#tapInfo").load(ctx + "mobile/tapDetail", function (data) {
        $("#result").css("display", "none");
        $.get(cfg.fmgxUrl + id, function (result) {
            var vm = new Vue({
                el: ".fmLayerC",
                data: {
                    syList: result.syfm,
                    xyList: result.xyfm,
                    dqList: {id: id, depth: depth}
                }
            });
            $("#tapInfo").css("display", "block");
        })
    });
}

//搜周边的结果
function queryResult(data, id, name) {
    if (data.features) {
        $(".searchResultTab").html("");
        $(".searchResultListInfo").html("");

        var obj = {
            id: id,
            name: name,
            data: data.features,
            features: {features: data.features, fields: data.fields, type: data.type}
        };
        resultArr.push(obj);
        //搜索结果类型
        var html = "";
        for (var i = 0; i < resultArr.length; i++) {
            if (i == 0) {
                html += `<span id=` + resultArr[i].id + ` class="spanTab active" onclick="spanTabChange(\`` + resultArr[i].id + `\`)">` + resultArr[i].name + `</span>`;
            } else {
                html += `<span id=` + resultArr[i].id + ` class="spanTab" onclick="spanTabChange(\`` + resultArr[i].id + `\`)">` + resultArr[i].name + `</span>`;
            }
        }
        $(".searchResultTab").html(html);

        //搜索结果类型对应列表
        var htmlList = "";
        if (resultArr[0].data) {
            for (var j = 0; j < resultArr[0].data.length; j++) {
                var name = resultArr[0].data[j];
                if (resultArr[0].name.indexOf("管线点") > -1) {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>编号: <i>` + name.properties[cfg.point.OID] + `</i></span>
                            <span class="txtC">埋深: <i>` + name.properties[cfg.point.B_DEPTH] + `</i></span>
                            <span class="txtR">附属物: <i>` + name.properties[cfg.point.SUBSID] + `</i></span>
                        </p>
                        <div class="pr txt">
                            管点类型: <i>` + name.properties[cfg.point.PIPEP_TYPE] + `</i>
                            <div class="more">`;
                    if (name.properties["SUBSID"] == "阀门") {
                        htmlList += `<a href="#" onclick="lookTapInfo(\`` + name.properties["PIPEP_ID"] + `\`,\`` + name.properties["WELL_DEPTH"] + `\`)">阀门关系</a>`;
                    }
                    htmlList += `<a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                } else if (resultArr[0].name.indexOf("管线段") > -1) {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                    <span class="number">` + (j + 1) + `</span>
                    <p>
                        <span>编号: <i>` + name.properties[cfg.line.OID] + `</i></span>
                        <span class="txtC">材质: <i>` + name.properties[cfg.line.MATERIAL] + `</i></span>
                        <span class="txtR">管径: <i>` + name.properties[cfg.line.D_S] + `</i></span>
                    </p>
                    <div class="pr txt">
                        道路名称: <i>` + name.properties[cfg.line.R_NAME] + `</i>
                        <div class="more">
                            <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                        </div>
                    </div>
                </li>`;
                } else if (resultArr[0].id == "LEAKAGE") {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                    <span class="number">` + (j + 1) + `</span>
                    <p>
                        <span>OID: <i>` + name.properties["OID"] + `</i></span>
                        <span class="txtC">材质: <i>` + name.properties["PIPEMATERI"] + `</i></span>
                        <span class="txtR">埋深: <i>` + name.properties["PIPEDEPTH"] + `</i></span>
                    </p>
                    <div class="pr txt">
                        位置: <i>` + name.properties["PLACEADDRE"] + `</i>
                        <div class="more">
                            <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                        </div>
                    </div>
                </li>`;
                } else if (resultArr[0].id == "STREET_LIGHT") {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                    <span class="number">` + (j + 1) + `</span>
                    <p>
                        <span>OID: <i>` + name.properties["OID"] + `</i></span>
                        <span class="txtC">数量: <i>` + name.properties["NUM"] + `</i></span>
                    </p>
                    <div class="pr txt">
                        名称: <i>` + name.properties["NAME"] + `</i>
                        <div class="more">
                            <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                        </div>
                    </div>
                </li>`;
                } else if (resultArr[0].id == "CONTROLLER") {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            名称: <i>` + name.properties["SNAME"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["SID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                } else if (resultArr[0].id == "record") {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["id"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>名称: <i>` + name.properties["name"] + `</i></span>
                            <span class="txtC">维修类型: <i>` + name.properties["faultType"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            维修人员: <i>` + name.properties["userName"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["id"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                } else {
                    htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                            <span class="txtC">区域: <i>` + name.properties["QY"] + `</i></span>
                            <span class="txtR">位置: <i>` + name.properties["WZ"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            名称: <i>` + name.properties["SNAME"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                }
            }
        }
        $(".searchResultListInfo").html(htmlList);

    }
    if (searchIndex > arrChecked.length) {
        layer.close(loading);
        if (resultArr.length > 0) {
            var arr = [];
            for (var key in resultArr) {
                arr.push(resultArr[key].id);
            }
            changeLayer(arr);
            $('.searchBox').addClass("hide");
            $('.searchResult').removeClass("hide");
            searchResultHeight()
        } else {
            layer.msg("周边未匹配到符合的结果！");
        }

    }
}

//搜周边的tab切换
function spanTabChange(id) {
    $(".spanTab").removeClass("active");
    $("span[id=" + id + "]").addClass("active");
    for (var i = 0; i < resultArr.length; i++) {
        if (resultArr[i].id == id) {
            $(".searchResultListInfo").html("");
            //搜索结果类型对应列表
            var htmlList = "";
            if (resultArr[i].data) {
                for (var j = 0; j < resultArr[i].data.length; j++) {
                    var name = resultArr[i].data[j];
                    if (resultArr[i].name.indexOf("管线点") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>编号: <i>` + name.properties[cfg.point.OID] + `</i></span>
                            <span class="txtC">埋深: <i>` + name.properties[cfg.point.B_DEPTH] + `</i></span>
                            <span class="txtR">附属物: <i>` + name.properties[cfg.point.SUBSID] + `</i></span>
                        </p>
                        <div class="pr txt">
                            管点类型: <i>` + name.properties[cfg.point.PIPEP_TYPE] + `</i>
                                      <div class="more">`;
                        if (name.properties["SUBSID"] == "阀门") {
                            htmlList += `<a href="#" onclick="lookTapInfo(\`` + name.properties["PIPEP_ID"] + `\`,\`` + name.properties["WELL_DEPTH"] + `\`)">阀门关系</a>`;
                        }
                        htmlList += `<a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].name.indexOf("管线段") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>编号: <i>` + name.properties[cfg.line.OID] + `</i></span>
                            <span class="txtC">材质: <i>` + name.properties[cfg.line.MATERIAL] + `</i></span>
                            <span class="txtR">管径: <i>` + name.properties[cfg.line.D_S] + `</i></span>
                        </p>
                        <div class="pr txt">
                            道路名称: <i>` + name.properties[cfg.line.R_NAME] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].id == "LEAKAGE") {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                            <span class="txtC">材质: <i>` + name.properties["PIPEMATERI"] + `</i></span>
                            <span class="txtR">埋深: <i>` + name.properties["PIPEDEPTH"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            位置: <i>` + name.properties["PLACEADDRE"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].id == "STREET_LIGHT") {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                            <span class="txtC">数量: <i>` + name.properties["NUM"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            名称: <i>` + name.properties["NAME"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].id == "CONTROLLER") {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            名称: <i>` + name.properties["SNAME"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].id == "record") {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["id"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>名称: <i>` + name.properties["name"] + `</i></span>
                            <span class="txtC">维修类型: <i>` + name.properties["faultType"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            维修人员: <i>` + name.properties["userName"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["id"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>OID: <i>` + name.properties["OID"] + `</i></span>
                            <span class="txtC">区域: <i>` + name.properties["QY"] + `</i></span>
                            <span class="txtR">位置: <i>` + name.properties["WZ"] + `</i></span>
                        </p>
                        <div class="pr txt">
                            名称: <i>` + name.properties["SNAME"] + `</i>
                            <div class="more">
                                <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    }
                }
            }
            $(".searchResultListInfo").html(htmlList);
            searchResultHeight()
        }
    }
}

//定位
function locationWhere(name, oid) {
    layerTree.removeLocatedBuilding();
    closeResultMore();
    for (var i = 0; i < resultArr.length; i++) {
        if (resultArr[i].id == name) {
            var obj = resultArr[i].features;
            for (var j = 0; j < obj.features.length; j++) {
                if (resultArr[i].id == "record") {
                    if (obj.features[j].properties["id"] == oid) {
                        var arr = [];
                        arr.push(obj.features[j]);
                        var json = {features: arr, type: "FeatureCollection"};
                        layerTree.addGeoJsonLayerByPoint(layerTree.reformattedGeoJsonData(json), layerTree.setOptionsByType("MOBILE_LOCALTION"));
                    }
                } else {
                    if (obj.features[j].properties["OID"] == oid) {
                        var arr = [];
                        arr.push(obj.features[j]);
                        var json = {features: arr, fields: obj.fields, type: "FeatureCollection"};
                        layerTree.addGeoJsonLayerByPoint(layerTree.reformattedGeoJsonData(json), layerTree.setOptionsByType("MOBILE_LOCALTION"));
                    }
                }

            }
        }
    }
}

//一般的定位方法
function locateBuildingByID(id, type) {
    closeResultMore();
    layerTree.locateBuildingByID(id, type);
}

//关阀停水
function queryGfts(lay, radius) {
    //pickPosition = {longitude: 114.35790, latitude: 30.47544}
   wxJsSdk.getLocation(function (res) {
        lat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        long = res.longitude; // 经度，浮点数，范围为180 ~ -180。
        pickPosition = {longitude: long, latitude: lat};

        resultArr = [];
        searchIndex = 1;
        arrChecked = [];
        layerTree.removeLocatedBuilding('local');
        // 获取缓冲图形
        var geometry = {
            type: "Point",
            coordinates: [parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude)]
        };
        layerTree.clearPic();
        var citizensBankPark = viewer.entities.add({
            position: Cesium.Cartesian3.fromDegrees(parseFloat(pickPosition.longitude), parseFloat(pickPosition.latitude), 10),
            billboard: {
                image: ctx + 'bus/aupipes/mobile/images/dingwei.png',
                width: 24,
                height: 24,
                scale: 1, //和原始大小相比的缩放比例
                minimumPixelSize: 100, //最小尺寸，防止太小而看不见
                distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0.0, 10000000),
                verticalOrigin: Cesium.VerticalOrigin.BOTTOM
            }
        });
        geometryEntityArr.push(citizensBankPark);
        var bufferOptions = {
            url: aupipeService + "/KQGis/rest/services/geometryserver/buffer",
            geoSRS: "EPSG:4326",
            outSRS: "EPSG:4326",
            data: JSON.stringify(geometry),
            radius: 100
        };
        layerTree.getBufferOfGeometry(bufferOptions, function (bufferGeometry) {
            //添加缓冲面
            var geoJson = {
                "type": "FeatureCollection",
                "features": [
                    {
                        "type": "Feature",
                        "properties": {},
                        "geometry": {
                            "type": bufferGeometry.type,
                            "coordinates": bufferGeometry.coordinates
                        }
                    }
                ]
            }
            layerTree.addGeoJsonLayer(geoJson, layerTree.setOptionsByType());
            for (var i = 0; i < lay.length; i++) {
                arrChecked.push(lay[i]);
                var obj = layerTree.allNodes.find(function (obj) {
                    return obj.id === lay[i].id
                });
                layerTree.searchAround(pickPosition, radius, obj, bufferGeometry, queryGftsResult);
            }
        });
    });
}

//关阀停水管线结果
function queryGftsResult(data, id, name) {
    if (data.features) {
        $(".searchResultTab").html("");
        $(".searchResultListInfo").html("");

        var obj = {
            id: id,
            name: name,
            data: data.features,
            features: {features: data.features, fields: data.fields, type: data.type}
        };
        resultArr.push(obj);
    }
    if (searchIndex > arrChecked.length) {
        if (resultArr.length > 0) {
            layer.close(loading);
            //搜索结果类型
            var html = "";
            for (var i = 0; i < resultArr.length; i++) {
                if (i == 0) {
                    html += `<span id=` + resultArr[i].id + ` class="spanTab active" onclick="spanTabFxChange(\`` + resultArr[i].id + `\`)">` + resultArr[i].name + `</span>`;
                } else {
                    html += `<span id=` + resultArr[i].id + ` class="spanTab" onclick="spanTabFxChange(\`` + resultArr[i].id + `\`)">` + resultArr[i].name + `</span>`;
                }
            }
            $(".searchResultTab").html(html);

            //搜索结果类型对应列表
            var htmlList = "";
            if (resultArr[0].data) {
                for (var j = 0; j < resultArr[0].data.length; j++) {
                    var name = resultArr[0].data[j];
                    if (resultArr[0].name.indexOf("管线点") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>编号: <i>` + name.properties[cfg.point.OID] + `</i></span>
                            <span class="txtC">埋深: <i>` + name.properties[cfg.point.B_DEPTH] + `</i></span>
                        </p>
                        <div class="pr txt">
                            管点类型: <i>` + name.properties[cfg.point.PIPEP_TYPE] + `</i>
                            <div class="more">
                                <a href="#" onclick="gftsAnalyse(\`` + name.properties["R_NAME"] + `\`,\`` + name.properties["OID"] + `\`,\`` + name.properties["PIPEP_ID"] + `\`)">停水分析</a>
                                <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[0].name.indexOf("管线段") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">
                    <span class="number">` + (j + 1) + `</span>
                    <p>
                        <span>编号: <i>` + name.properties[cfg.line.OID] + `</i></span>
                        <span class="txtC">材质: <i>` + name.properties[cfg.line.MATERIAL] + `</i></span>
                        <span class="txtR">管径: <i>` + name.properties[cfg.line.D_S] + `</i></span>
                    </p>
                    <div class="pr txt">
                        道路名称: <i>` + name.properties[cfg.line.R_NAME] + `</i>
                        <div class="more">
                            <a href="#" onclick="gftsAnalyse(\`` + name.properties["R_NAME"] + `\`,\`` + name.properties["OID"] + `\`,\`` + name.properties["PIPEL_ID"] + `\`)">停水分析</a>
                            <a href="#" onclick="lookDetail(\`` + resultArr[0].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                        </div>
                    </div>
                </li>`;
                    }
                }
            }
            $(".searchResultListInfo").html(htmlList);
            var arr = [];
            for (var key in resultArr) {
                arr.push(resultArr[key].id);
            }
            changeLayer(arr);
            $('.searchBox').addClass("hide");
            $('.searchResult').removeClass("hide");
            searchResultHeight();
        } else {
            radiusGf = radiusGf + 35;
            queryGfts(cfg.gftsLayer, radiusGf);
        }
    }
}

//停水分析
function gftsAnalyse(name, oid, num) {
    var url = cfg.gftsUrl + oid;
    //var url = ctx + 'aupipes/waterrel/api/getImpactBuildingByValue?value=' + num;
    $.get(url, function (data) {
        if (data.code == 0) {
            if (data.fw.length > 0) {
                $("#detail").html("");
                $("#detail").css("display", "block");
                $("#detail").load(ctx + "mobile/gftsDetail", function (res) {
                    var vm = new Vue({
                        el: '.listDetail',
                        data: {
                            list: data.fw,//属性信息
                            name: name,
                            info: data.closevalve,
                            no: num
                        }
                    });
                });
            } else {
                layer.msg("暂无影响数据！");
            }
        }
    });
}

//分析切换tab
function spanTabFxChange(id) {
    $(".spanTab").removeClass("active");
    $("span[id=" + id + "]").addClass("active");
    for (var i = 0; i < resultArr.length; i++) {
        if (resultArr[i].id == id) {
            $(".searchResultListInfo").html("");
            //搜索结果类型对应列表
            var htmlList = "";
            if (resultArr[i].data) {
                for (var j = 0; j < resultArr[i].data.length; j++) {
                    var name = resultArr[i].data[j];
                    if (resultArr[i].name.indexOf("管线点") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                        <span class="number">` + (j + 1) + `</span>
                        <p>
                            <span>编号: <i>` + name.properties[cfg.point.OID] + `</i></span>
                            <span class="txtC">埋深: <i>` + name.properties[cfg.point.B_DEPTH] + `</i></span>
                        </p>
                        <div class="pr txt">
                            管点类型: <i>` + name.properties[cfg.point.PIPEP_TYPE] + `</i>
                                      <div class="more">
                                      <a href="#" onclick="gftsAnalyse(\`` + name.properties["R_NAME"] + `\`,\`` + name.properties["OID"] + `\`,\`` + name.properties["PIPEP_ID"] + `\`)">停水分析</a>
                                      <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                            </div>
                        </div>
                    </li>`;
                    } else if (resultArr[i].name.indexOf("管线段") > -1) {
                        htmlList += `<li onclick="locationWhere(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">
                            <span class="number">` + (j + 1) + `</span>
                            <p>
                                <span>编号: <i>` + name.properties[cfg.line.OID] + `</i></span>
                                <span class="txtC">材质: <i>` + name.properties[cfg.line.MATERIAL] + `</i></span>
                                <span class="txtR">管径: <i>` + name.properties[cfg.line.D_S] + `</i></span>
                            </p>
                            <div class="pr txt">
                                道路名称: <i>` + name.properties[cfg.line.R_NAME] + `</i>
                                <div class="more">
                                    <a href="#" onclick="gftsAnalyse(\`` + name.properties["R_NAME"] + `\`,\`` + name.properties["OID"] + `\`,\`` + name.properties["PIPEL_ID"] + `\`)">停水分析</a>
                                    <a href="#" onclick="lookDetail(\`` + resultArr[i].id + `\`,\`` + name.properties["OID"] + `\`)">查看详情</a>
                                </div>
                            </div>
                        </li>`;
                    }
                }
            }
            $(".searchResultListInfo").html(htmlList);
            searchResultHeight()
        }
    }
}

//能耗监管耗水耗电
function nhjgQuery(id) {
    $("#result").css("display", "block");
    var myDate = new Date();
    var tYear = myDate.getFullYear();
    $("#result").load(ctx + "mobile/nhjg", function (data) {
        layer.close(loading);
        NHJG.queryTotal(id, tYear, 0);
        //图表初始化
        NHJG.initEcharts();

        NHJG.sdphylEcharts(id, tYear, 0);
        NHJG.lbxxEcharts(id, tYear, 0);
        NHJG.mjshEcharts(id, tYear, 0);
        NHJG.rjshEcharts(id, tYear, 0);
    });
}
