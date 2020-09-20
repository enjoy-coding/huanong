objectType = 'index';
var ScreenIndex = {
    prefix: ctx + "screen/index",
    ChartsName: [],
    loginId:'',
    /**
     * 设备统计
     */
    loadSbtj: function () {
        getUser();
       $.get(ctx + "api/screen/index/sbtj", function (d) {
            var data = d.data.sbtj;
            //配电房
            $("#sbtj_pdf_zx").text(data[0].number);
            $("#sbtj_pdf_zs").text(data[0].total);
            //水表
            $("#sbtj_sb_zx").text(data[5].number);
            $("#sbtj_sb_zs").text(data[5].total);
           //电表
            $("#sbtj_db_zx").text(data[4].number);
            $("#sbtj_db_zs").text(data[4].total);
            //电表
            $("#sbtj_sfdb_zx").text(data[9].number);
            $("#sbtj_sfdb_zs").text(data[9].total);
            //盛帆水表
            $("#sbtj_sfsb_zx").text(data[8].number);
            $("#sbtj_sfsb_zs").text(data[8].total);
            //盛帆电表
            $("#sbtj_jk_zx").text(data[7].number);
            $("#sbtj_jk_zs").text(data[7].total);
            //路灯
            $("#sbtj_ld_zx").text(data[2].number);
            $("#sbtj_ld_zs").text(data[2].total);
            //泵房
            $("#sbtj_bf_zx").text(data[1].number);
            $("#sbtj_bf_zs").text(data[1].total);
            //探漏
            $("#sbtj_tl_zx").text(data[3].number);
            $("#sbtj_tl_zs").text(data[3].total);
            //水质
            $("#sbtj_jc_zx").text(data[6].number);
            $("#sbtj_jc_zs").text(data[6].total);
            //安防
            $("#sbtj_jk_zx").text(data[7].number);
            $("#sbtj_jk_zs").text(data[7].total);

        })
    },
    /**
     * 预警处置
     */
    loadYjcz: function () {
        $("#fragment-yjcz").load(ctx + "screen/yjcz/syyjxq?pageNum=1&pageSize=50", function (result) {
            // 预警处置循环向上切换
            var mySwiper2 = new Swiper('#fragment-yjcz', {
                direction: 'vertical', //向上
                spaceBetween: 30,
                loop: true,
                autoplay: true
            });

            //鼠标覆盖停止自动切换
            mySwiper2.el.onmouseover = function () {
                mySwiper2.autoplay.stop();
            };

            //鼠标离开开始自动切换
            mySwiper2.el.onmouseout = function () {
                mySwiper2.autoplay.start();
            }

        });
    },
    /**
     * 预案模拟
     */
    loadYamn: function () {
        $.get(ctx + "api/screen/index/yazs", function (d) {
            var data = d.data.yazs;
            $("#yamnNum").text(data);
        });
    },
    /**
     * 路灯情况
     */
    loadLdqk: function () {
        $.get(ctx + "api/screen/index/ldqk", function (d) {
            var data = d.data.ldqk;
            //开关灯时间
            var openTime = data.openLightTime;
            $("#ldqk_gdsj").text(data.closeLightTime);
            $("#ldqk_kdsj").text(data.openLightTime);
            $("#ldqk_kzsltj").text(data.controllerOnlineCount + "/" + data.controllerTotalCount);
            $("#ldqk_sltj").text(data.lightsCount + "/" + data.totalCount);
            $("#ldqk_rydl").text(data.powerConsumption);

        });
    },
    /**
     * 智能安防
     */
    loadZnaf: function () {
        $.get(ctx + "api/screen/index/znaf", function (d) {
            var data = d.data.znaf;
            //门禁
            $("#znaf_mj").text(data.accessControl);
            //红外线报警
            //20200312改为沁水
            $("#znaf_hwxbj").text(data.waring);
            //烟感
            $("#znaf_yg").text(data.smokeAlarm);
            //摄像头
            $("#znaf_sxt").text(data.camera);
            //小动物入侵
            // $("#znaf_xdwrq").text(data.animals;
        });
    },
    /**
     * 维修巡检
     */
    loadWxxj: function () {
      /*  $.get(ctx + "api/screen/index/wxxj", function (d) {
            var data = d.data.wxxj;
            //已完成数
            $("#wxxj_ywc").text(data.completedNum);
            //维修总数
            $("#wxxj_wxzs").text(data.repairedNum);
            //任务总数
            $("#wxxj_rwzs").text(data.taskNum);
            //摄像头
            $("#wxxj_djzs").text(data.inspectionNum);
        });*/
        // $.get(ctx + "aupipes/inspect/selectAupInspectCountByStatus", function (d) {
        $.get(ctx + "api/screen/index/inspectNumCurM", function (d) {
            var data = d.data;
            for(var i in data){
                if(data[i].name=='未巡'){
                    $("#wxxj_wx").text(data[i].value);
                }else if(data[i].name=='在巡'){
                    $("#wxxj_zx").text( data[i].value);
                }else if(data[i].name=='已巡'){
                    $("#wxxj_yx").text(data[i].value);
                }else if(data[i].name=='当月'){
                    $("#wxxj_czs").text(data[i].value);
                }else if(data[i].name=='总数'){
                    $("#wxxj_zs").text(data[i].value);
                }
            }

        });

        //报修数量
        $.get("http://nydt.hzau.edu.cn/api/OpenApi/GetRepair", function (d) {
            if(d.status == 200){
                var result = d.ReturnMessage;
                var data = JSON.parse(result)
                $("#wxxj_clz").text(data.CurrentMonthOngoing);
                $("#wxxj_dcl").text(data.CurrentMonthDeal);
                $("#wxxj_ywc").text(data.CurrentMonthCompleted);
                $("#bx_zs").text(data.RepairTotal);
                $("#bx_curMonth").text(data.CurrentMonthTotal);
            }
        });


        $.get("http://nydt.hzau.edu.cn/api/OpenApi/GetBusiness", function (d) {
            if(d.status == 200){
                var result = d.ReturnMessage;
                var data = JSON.parse(result)
                $("#sw_clz").text(data.CurrentMonthOngoing);
                $("#sw_dcl").text(data.CurrentMonthNotDeal);
                $("#sw_ywc").text(data.CurrentMonthCompleted);
                $("#sw_zs").text(data.Total);
                $("#sw_curMonth").text(data.CurrentMonthTotal);
            }
        });
    },

    loadNhtjUseNumber: function () {
        $.ajax({
            url:"http://nydt.hzau.edu.cn/api/OpenApi/GetEnergy",
            success:function(data){
                if (data.status ==200){
                    var result = data.ReturnMessage;
                    result = JSON.parse(result);
                    var ElectricReadDay = result.ElectricReadDay;//昨日用电量
                    var WaterReadDay = result.WaterReadDay;//昨日用水量
                    var ElectricReadMonth = result.ElectricReadMonth;//月用电量
                    var WaterReadMonth = result.WaterReadMonth;//月用水量
                    var ElectricReadYear = result.ElectricReadYear;//年用电量
                    var WaterReadYear= result.WaterReadYear;//年用水量
                    //昨日用电量
                            $("#nhtj_yesterday_usenumber_ele").text(accDiv(ElectricReadDay,10000));
                            //昨日用水量
                            $("#nhtj_yesterday_usenumber_water").text(accDiv(WaterReadDay,10000));
                            // //总用电量
                            $("#nhtj_total_zydl").text(accDiv(ElectricReadYear,10000));
                            //总用水量
                            $("#nhtj_total_zysl").text(accDiv(WaterReadYear,10000));
                            //月用电量
                            $("#nhtj_total_yydl").text(accDiv(ElectricReadMonth,10000));
                            //月用水量
                            $("#nhtj_total_yysl").text(accDiv(WaterReadMonth,10000));
                }
            }
        });
        //请求接口
        // $.ajax({
        //     url: ctx + "api/screen/index/nhjgTotal",
        //     success: function (data) {
        //         //console.log(data);
        //         var water = data.data.water;
        //         var ele = data.data.ele;
        //         //昨日用电量
        //         $("#nhtj_yesterday_usenumber_ele").text(ele.useNumberYesterDay);
        //         //昨日用水量
        //         $("#nhtj_yesterday_usenumber_water").text(water.useNumberYesterDay);
        //         // //总用电量
        //         $("#nhtj_total_zydl").text(ele.useNumberYear);
        //         //总用水量
        //         $("#nhtj_total_zysl").text(water.useNumberYear);
        //         //月用电量
        //         $("#nhtj_total_yydl").text(ele.useNumberMonth);
        //         //月用水量
        //         $("#nhtj_total_yysl").text(water.useNumberMonth);
        //         // $("#nhtj_rate_ydhb").empty();
        //         // $("#nhtj_rate_slhb").empty();
        //         // $("#nhtj_rate_ydshl").empty();
        //         // $("#nhtj_rate_ysshl").empty();
        //         //
        //         // $("#nhtj_rate_ydhb").text(ele.hb);//用电环比
        //         // $("#nhtj_rate_slhb").text(water.hb);//用水环比
        //         // $("#nhtj_rate_ydshl").text(ele.tb);//用电同比
        //         // $("#nhtj_rate_ysshl").text(water.tb);//用水同比
        //         // setIsUpDowmByUseNumber(ele.hbIsUp, "nhtj_rate_ydhb");
        //         // setIsUpDowmByUseNumber(ele.tbIsUp, "nhtj_rate_ydshl");
        //         // setIsUpDowmByUseNumber(water.hbIsUp, "nhtj_rate_slhb");
        //         // setIsUpDowmByUseNumber(water.tbIsUp, "nhtj_rate_ysshl");
        //     }
        // });
    },

    /**
     * 泵房信息
     */
    loadBfxx: function () {
        $.get(ctx + "api/screen/index/pump", function (res) {
            if(res.code == 0&& res.data.length>0){
                var data = res.data;
                $("#fragment-bfxx").empty();
                var ul_html = "";
                for(var i =0;i<data.length;i++){
                    var pump = data[i];
                    var points = pump.pointAttrs;
                    var li_html = '<li class="swiper-slide">'+
                                    '<div class="zxbfTxt">'+
                                    '<h5>'+pump.pumpName+'</h5>'+
                                    ' <div class="number oh">';
                        for(var j=0;j<points.length;j++){
                            var point = points[j];
                            var points_html = ' <h4 class="name">'+point.pointName+'</h4>';
                            var pointAttrs = point.attrs;
                            if(pointAttrs.length>=2){
                                for(var k =0;k<2;k++){
                                    var attrName =pointAttrs[k].attrName;
                                    var attrValue = pointAttrs[k].attrValue;
                                    var unit = "bar";
                                    if(attrName=="水箱液位"){
                                        unit = "m"
                                    }
                                    var points_attr_html = '<dl>'+
                                                               '<dd class="clGreen">'+attrName+'</dd>'+
                                                                '<dd id="bfxx_ckyl1">'+attrValue+(unit)+'</dd>'+
                                                            '</dl>';
                                    points_html += points_attr_html;
                                }
                            }
                              li_html +=  points_html;               
                        }
                    
                    li_html += '</div></div>'+
                                '<div class="zxbfEcharts pr">'+
                                '<div id="bfxxGslEcharts_'+pump.pumpId+'" class="echarts"></div>\n' +
                                    '<div class="right pa">\n' +
                                        '<h5>'+pump.pumpName+'</h5>\n' +
                                        '<h6>运行数</h6>\n' +
                                        '<h2 id="bfxx_yxyc1">'+pump.workCount+'</h2>\n' +
                                    '</div>\n' +
                                '</div>\n';
                    li_html += '</li>';
                    ul_html+=li_html;
                }
               
                $("#fragment-bfxx").html(ul_html);
                for (var i = 0; i < data.length; i++) {
                    ScreenIndex.loadPumpEcharts(data[i].pumpId);
                }
            }

        });
    },
    /**
     * 中心泵房
     */
    loadPumpForCenter: function () {
        var bfxxGslEcharts = echarts.init(document.getElementById("bfxxGslEcharts11"));

        ScreenIndex.ChartsName.push(bfxxGslEcharts);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: '12%',
                x:'center',
                data: ['供水量'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10 // 设置高度
            },
            grid: {
                top: '30%',
                left: '40',
                right: '10%',
                bottom: '20'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1号', '2号', '3号'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    name: '日供水量(吨)',
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
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
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)'//x线的颜色
                        }
                    }

                }
            ],
            series: [
                {
                    name: '供水量',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [1102, 362, 755],
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#85d892'
                            }, {
                                offset: 1,
                                color: 'rgba(133,216,146,.4)'
                            }])
                        }
                    }
                }
            ],
            color: ['#91ffa0', '#fecb8a']
        };
        bfxxGslEcharts.setOption(option);
    },
    loadPumpForXiYuan : function () {
        var bfxxGslEcharts = echarts.init(document.getElementById("bfxxGslEcharts22"));

        ScreenIndex.ChartsName.push(bfxxGslEcharts);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: '12%',
                x:'center',
                data: ['供水量'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10 // 设置高度
            },
            grid: {
                top: '30%',
                left: '40',
                right: '10%',
                bottom: '20'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1号', '2号', '3号'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    name: '日供水量(吨)',
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
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
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)'//x线的颜色
                        }
                    }

                }
            ],
            series: [
                {
                    name: '供水量',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [1102, 362, 755],
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#85d892'
                            }, {
                                offset: 1,
                                color: 'rgba(133,216,146,.4)'
                            }])
                        }
                    }
                }
            ],
            color: ['#91ffa0', '#fecb8a']
        };
        bfxxGslEcharts.setOption(option);
    },
    loadPumpEcharts: function (id) {
        var myDate = new Date();
        //获取当前年
        var year = myDate.getFullYear();
        //获取当前月
        var month = myDate.getMonth() + 1;

        var time = year +'年'+month+'月';
        var bfxxGslEcharts = echarts.init(document.getElementById("bfxxGslEcharts_"+id));
        ScreenIndex.ChartsName.push(bfxxGslEcharts);
        var day = getPreThreeDay();
        var option = {
            tooltip: {
                trigger: 'axis',
                formatter: time+"{b}日<br/>{a} : {c}吨"
            },
            legend: {
                top: '12%',
                x:'center',
                data: ['供水量'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10 // 设置高度
            },
            grid: {
                top: '30%',
                left: '40',
                right: '10%',
                bottom: '20'
            },
            xAxis: [
                {
                    type: 'category',
                    data: day,
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false,
                    }
                }
            ],
            yAxis: [
                {
                    name: '日供水量(吨)',
                    type: 'value',
                    scale: true,
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb'//x线的颜色
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
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    }

                }
            ],
            series: [
                {
                    name: '供水量',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [1102, 362, 755],
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#85d892'
                            }, {
                                offset: 1,
                                color: 'rgba(133,216,146,.4)'
                            }])
                        }
                    },
                }
            ],
            color: ['#91ffa0', '#fecb8a']
        };
        bfxxGslEcharts.setOption(option);
        $.get(ctx+"api/screen/bfxx/echarts?pumpId="+id,function(res){
                var seriesData = [1102, 362, 755];
                if(res.code == 0 && res.data != null){
                    seriesData[0]=res.data.thisUseNumber;
                    seriesData[1]=res.data.thisUseNumber1;
                    seriesData[2]=res.data.thisUseNumber2;
                    var option  = bfxxGslEcharts.getOption();
                    option.series[0].data=seriesData;
                    bfxxGslEcharts.setOption(option);
                }
        })

    },
    /**
     * 加载首页配电房数据
     */
    loadPowerHouse:function(){
        $.get(ctx + "api/screen/power",function(result){
            if(result.code == 0){
                $("#powerHouseInfo").empty();
                var ul ="";
                for (var i = 0; i <result.data.length ; i++) {
                    var power = result.data[i];
                    var li = "<li class='swiper-slide' ><div class='left fl'>" +
                            "<i class='iconfont icon-peidianfang'></i><h5>"+power.name+"</h5>" +
                        "</div>"+
                        "<div class='right fr'><h6><span>变压器:</span><i>"+power.byq+"</i></h6><h6><span>回路:</span><i>"+power.hl+"</i></h6></div>"
                    ul += li;

                }
                $("#powerHouseInfo").append(ul);
                // js智能安防
                var mySwiper3 = new Swiper('#DistributionRoom',{
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev'
                    },
                    spaceBetween: 30,
                    loop: true,
                    autoplay: true
                });
                //鼠标覆盖停止自动切换
                mySwiper3.el.onmouseover = function(){
                    mySwiper3.autoplay.stop();
                };

                //鼠标离开开始自动切换
                mySwiper3.el.onmouseout = function(){
                    mySwiper3.autoplay.start();
                }
            }
        })
    }

};


function accDiv(arg1,arg2){
    var num = 0;
    if (arg1>0){
        num = toDecimal2(num = arg1/arg2)
    }else{
        num = 0;
    }
    return num;
}
function toDecimal2(x) {
    if (isNaN(parseFloat(x))) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

$(function () {

    layui.element.on('tab(nhtjTitle)', function (elem) {
        window.onresize();

    });
    // ScreenIndex.loadPumpForCenter();//中心泵房
    // ScreenIndex.loadPumpForXiYuan();//西苑泵房
    ScreenIndex.loadSbtj();
    ScreenIndex.loadYjcz();
    ScreenIndex.loadYamn();
    ScreenIndex.loadLdqk();
    ScreenIndex.loadYamn();
    ScreenIndex.loadZnaf();
    ScreenIndex.loadWxxj();
    ScreenIndex.loadNhtjUseNumber();
    ScreenIndex.loadBfxx();
    ScreenIndex.loadPowerHouse();

    var socket = new SockJS("/api/websocket"); 
    stompClient = Stomp.over(socket); 
    stompClient.connect({}, function(frame) {
        //console.log("Connected: " + frame);
        // 订阅
        stompClient.subscribe("/aup/websocket/msg-push", function(respnose){
        	// TODO:
        	layer.open({
                content: respnose.body
            });
        });
    });
});

window.onresize = function () {
    for (var i = 0; i < ScreenIndex.ChartsName.length; i++) {
        ScreenIndex.ChartsName[i].resize();
    }
};


function setIsUpDowmByUseNumber(isUp,id){
    if(isUp){
        $("#"+id).append("<i class=\"iconfont icon-jiantou-copy\"></i>");
    } else {
        $("#"+id).append("<i class=\"iconfont icon-jiantou-copy1\"></i>");
    }
}
function openld(){
    $.ajax({
        url: 'http://nydt.hzau.edu.cn/api/Centre/ludengLogin?no=' + ScreenIndex.loginId,
        type: 'get',
        success: function (res) {
            var data = JSON.parse(res.ReturnMessage);
            window.open(data.url);
        }
    });
}

function getUser() {
    $.get(ctx+'getUser', function (res) {
        ScreenIndex.loginId = res.loginName;
        $("#jnjgURL").attr("href", "http://jnpt.hzau.edu.cn/GOTOLogin.aspx?username=" + res.loginName);
        $("#AqglURL").attr("href", "http://211.69.128.193/wcms/AuthLogin?username=" + res.loginName);
        $("#jlsf").attr("href", "https://cas.hzau.edu.cn/cas/login?service=http%3a%2f%2fnydt.hzau.edu.cn%2fMainSysUser%2fSsoLogin%3ftargetUrl%3d%7bbase64%7daHR0cDovL255ZHQuaHphdS5lZHUuY24vTWFpblN5c1VzZXIvU3NvTG9naW4%3d");
    });

}

function  openbf() {
    window.open("http://211.69.132.74:9380/vue/#/login?userPhone=15927210578&userPwd=123456");
}

function opentl() {
    $.ajax({
        url: top.celouService +"/View/Login/SSO.aspx",
        type: "post",
        data: {
            username: "hzny_sso", password: "9badd6533beccd1c5df95e28dfbc140a",
            action: "authorize", encrypt: "true"
        },
        dataType: "jsonp",  //指定服务器返回的数据类型
        jsonp: "callback",   //指定参数名称
        jsonpCallback: "CeLouResponseHandler",  //指定回调函数名称
        success: function (data) {
            //处理接收的json数据
            window.open(top.celouService);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }

    });
}



