/**
 * Description:
 * @author zhoushan
 * @date 2020/6/22
 * @version 1.0.0
 * @Time 9:14
 */
var app = new Vue({
    el: '#app',
    data: {
        ctx: ctx,
        yxjk: [{number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0},
            {number: 0, total: 0}
        ],    //运行监控
        yjcz: [],    //预警处置
        water: {useNumberYesterDay: 0, useNumberMonth: 0, useNumberYear: 0},  //能耗水
        ele: {useNumberYesterDay: 0, useNumberMonth: 0, useNumberYear: 0},    //能耗电
        ldqk: {},    //路灯情况
        xjwx: {},    //巡检统计
        bx: {},      //报修
        swdb: {},    //事务督办
        loginId: '',    //登录id
        bfInfo: []    //泵房信息

    },
    created: function () {
        this.initYxjk();
        this.initYjcz();
        this.initNhjg();
        this.initLdqk();
        this.initPumpInfo();
        this.initWxxj();
        this.getUser();
    },
    mounted: function () {

    },
    methods: {
        //运行监控
        initYxjk: function () {
            $.get(ctx + "api/screen/index/sbtj", function (d) {
                app.yxjk = d.data.sbtj;
            });
        },
        //预警处置
        initYjcz: function () {
            $.post(ctx + "screen/yjcz/getAllInfo?formSysName=&state=0&level=&dateTime1=&dateTime2=&pageSize=20&pageNum=1", function (data) {
                app.yjcz = data.rows;
                app.$nextTick(() => {
                    app.initSwiper();
                });
            });
        },
        //能耗监管
        initNhjg: function () {
            /*$.get(ctx + "api/screen/index/nhjgTotal", function (data) {
                if(data.data.water != null){
                    app.water = data.data.water;
                }
                if(data.data.ele != null){
                    app.ele = data.data.ele;
                }
            });*/
            $.ajax({
                url: "http://nydt.hzau.edu.cn/api/OpenApi/GetEnergy",
                success: function (data) {
                    if (data.status == 200) {
                        var result = data.ReturnMessage;
                        result = JSON.parse(result);
                        app.water.useNumberYesterDay = accDiv(result.WaterReadDay, 10000);
                        app.water.useNumberMonth = accDiv(result.WaterReadMonth, 10000);
                        app.water.useNumberYear = accDiv(result.WaterReadYear, 10000);
                        app.ele.useNumberYesterDay = accDiv(result.ElectricReadDay, 10000);
                        app.ele.useNumberMonth = accDiv(result.ElectricReadMonth, 10000);
                        app.ele.useNumberYear = accDiv(result.ElectricReadYear, 10000);
                    }
                }
            })
        },
        //路灯情况
        initLdqk: function () {
            $.get(ctx + "api/screen/index/ldqk", function (d) {
                app.ldqk = d.data.ldqk;
            });
        },
        //预警swiper重新初始化
        initSwiper: function () {
            var mySwiper = new Swiper('#fragment-yjcz', {
                direction: 'vertical', //向上
                spaceBetween: 10,
                loop: true,
                autoplay: true,
                slidesPerView: 1,
            });
            //鼠标覆盖停止自动切换
            mySwiper.el.onmouseover = function () {
                mySwiper.autoplay.stop();
            };
            //鼠标离开开始自动切换
            mySwiper.el.onmouseout = function () {
                mySwiper.autoplay.start();
            };
        },
        //泵房
        initSwiperBf: function () {
            var mySwiper3 = new Swiper('#homeBfxx', {
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                spaceBetween: 10,
                loop: true,
                autoplay: true
            });
            //鼠标覆盖停止自动切换
            mySwiper3.el.onmouseover = function () {
                mySwiper3.autoplay.stop();
            }
            //鼠标离开开始自动切换
            mySwiper3.el.onmouseout = function () {
                mySwiper3.autoplay.start();
            }
        },
        //泵房信息
        initPumpInfo: function () {
            $.get(ctx + "screen/yxjk/app/query?queryType=pump", function (data) {
                if (data.code == 0) {
                    for (var i in data.data) {
                        //if (data.data[i].name == '中心泵房' || data.data[i].name == '西苑泵房') {
                        var obj = {name: data.data[i].name};
                        var res = data.data[i];
                        obj.bz = [];
                        for (var key in res.points) {
                            var names = res.points[key][0].attrName.split(",");
                            var values = res.points[key][0].attrValue.split(",");
                            var obj1 = {bzmc: key};
                            for (var c in names) {
                                if (names[c] == '出口压力') {
                                    obj1.ckyl = values[c];
                                    obj1.ckmc = names[c];
                                } else if (names[c] == '水箱液位') {
                                    obj1.sxyw = values[c];
                                    obj1.sxmc = names[c];
                                }
                            }
                            obj.bz.push(obj1);
                        }
                        app.bfInfo.push(obj);

                        app.$nextTick(() => {
                            app.initSwiperBf();
                        });
                        //}
                    }
                }
            })
        }
        ,
        //巡检维修
        initWxxj: function () {
            //$.get(ctx + "api/screen/index/inspectNum", function (d) {
            $.get(ctx + "api/screen/index/inspectNumCurM", function (d) {
                var data = d.data;
                // app.xjwx.num = 0;
                for (var i in data) {
                    if (data[i].name == '未巡') {
                        app.xjwx.wx = data[i].value;
                    } else if (data[i].name == '在巡') {
                        app.xjwx.zx = data[i].value;
                    } else if (data[i].name == '已巡') {
                        app.xjwx.yx = data[i].value;
                    } else if (data[i].name == '当月') {
                        app.xjwx.dy = data[i].value;
                    } else if (data[i].name == '总数') {
                        app.xjwx.num = data[i].value;
                    }
                    // if(data[i].name != '当月'){
                    //     app.xjwx.num += parseInt(data[i].value);
                    // }

                }
            });
            /*$.ajax({
                url: "http://nydt.hzau.edu.cn/api/OpenApi/GetPatrol",
                success: function (data) {
                    if (data.status == 200) {
                        var result = data.ReturnMessage;
                        result = JSON.parse(result);
                        app.xjwx.num = result.Sum;
                        app.xjwx.wx = result.Stay;
                        app.xjwx.zx = result.Ongoing;
                        app.xjwx.yx = result.Completed;
                        app.xjwx.dy = result.CurrentMonth;
                    }
                }
            });*/
            $.ajax({
                url: "http://nydt.hzau.edu.cn/api/OpenApi/GetRepair",
                success: function (data) {
                    if (data.status == 200) {
                        var result = data.ReturnMessage;
                        result = JSON.parse(result);
                        app.bx.num = result.RepairTotal;   //
                        app.bx.zx = result.CurrentMonthTotal;  //当月数
                        app.bx.wx = result.CurrentMonthOngoing;  //待审核
                        app.bx.dcl = result.CurrentMonthDeal;   //待处理
                        app.bx.yx = result.CurrentMonthCompleted;   //已完成
                    }
                }
            });
            $.ajax({
                url: "http://nydt.hzau.edu.cn/api/OpenApi/GetBusiness",
                success: function (data) {
                    if (data.status == 200) {
                        var result = data.ReturnMessage;
                        result = JSON.parse(result);
                        app.swdb.dy = result.CurrentMonthTotal;
                        app.swdb.num = result.Total;
                        app.swdb.zx = result.CurrentMonthOngoing;
                        app.swdb.wx = result.CurrentMonthNotDeal;
                        app.swdb.yx = result.CurrentMonthCompleted;
                    }
                }
            });
        },
        //获取用户名信息
        getUser: function () {
            $.get(ctx + 'getUser', function (res) {
                app.loginId = res.loginName;
                $("#jnjgURL").attr("href", "http://jnpt.hzau.edu.cn/GOTOLogin.aspx?username=" + res.loginName);
                $("#AqglURL").attr("href", "http://211.69.128.193/wcms/AuthLogin?username=" + res.loginName);
            });
        },
        //泵房子系统
        openbf: function () {
            location.href = "http://211.69.132.74:9380/vue/#/login?userPhone=15927210578&userPwd=123456";
        },
        //路灯子系统
        openld: function () {
            $.ajax({
                url: 'http://nydt.hzau.edu.cn/api/Centre/ludengLogin?no=' + app.loginId,
                type: 'get',
                success: function (res) {
                    var data = JSON.parse(res.ReturnMessage);
                    location.href = data.url;
                }
            })
        },
        //探漏子系统
        opentl: function () {
            $.ajax({
                url: top.celouService + "/View/Login/SSO.aspx",
                type: "post",
                data: {
                    username: "hzny_sso", password: "9badd6533beccd1c5df95e28dfbc140a",
                    action: "authorize", encrypt: "true",
                },
                dataType: "jsonp",  //指定服务器返回的数据类型
                jsonp: "callback",   //指定参数名称
                jsonpCallback: "CeLouResponseHandler",  //指定回调函数名称
                success: function (data) {
                    //处理接收的json数据
                    location.href = top.celouService;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        },
        localToYj:function(name,id){
            window.sessionStorage.setItem("yjName", name);
            window.sessionStorage.setItem("yjId", id);
            location.href = ctx + "mobile/index?menuCode=yjcz";
        },


    }
});

function accDiv(arg1, arg2) {
    var num = 0;
    if (arg1 > 0) {
        num = toDecimal2(num = arg1 / arg2)
    } else {
        num = 0;
    }
    return num;
}

function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
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
