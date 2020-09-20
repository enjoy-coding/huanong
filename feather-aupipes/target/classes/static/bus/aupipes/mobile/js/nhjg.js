var NHJG={
    data: {
        ChartsName: []
    },
    initEcharts: function () {
        this.echarts.$useNumber = null;
        this.echarts.$category = null;
        this.echarts.$area = null;
        this.echarts.$person = null;

    },
    echarts: {
        $useNumber: null,//用量图表id
        $category: null,//类别
        $area: null,//面积
        $person: null,//人均
    },
    //总量统计
    queryTotal:function(cacheId,year,important){
        var url = ctx+"screen/nhjg/queryTotalUseNumber?cacheId="+cacheId+"&year="+year+"&important="+important;
        var gl = "损耗率";
        if(cacheId.indexOf("roote") == 0){
            gl="环比";
            $("#perCapitaWater")[0].textContent='人均电耗';
            $("#areaWater")[0].textContent='面积电耗';
        }else{
            $("#perCapitaWater")[0].textContent='人均水耗';
            $("#areaWater")[0].textContent='面积水耗';
        }
        $.get(url,function(res){
            if(res.code ==0) {
                $("#nhtj_total").empty();
                var html = '';
                html += `<li><label>当前年用量:` + res.data.yearUseNumber + `</label></li>`;
                html += `<li><label>本月用量:` + res.data.monthTableUseNumber + `</label></li>`;
                html += `<li><label>`+gl+`:` + res.data.gl + `</label></li>`;

                $("#nhtj_total").append(html);
            }
            })
    },
    // 用量
    sdphylEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$useNumber == null) {
            NHJG.echarts.$useNumber = echarts.init(document.getElementById('sdphylEcharts'));
        } else {
            NHJG.echarts.$useNumber.clear();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$useNumber);

        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: '0',
                right: '0',
                data: ['上年度', '本年度'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10, // 设置高度
            },
            grid: {
                top: '30%',
                left: '50',
                right: '4%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
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
                    },
                }
            ],
            yAxis: [
                {
                    name: '单位（吨）',
                    type: 'value',
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
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },

                }
            ],
            series: [
                {
                    name: '上年度',
                    type: 'bar',
                    barMaxWidth: '10',
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [4,4,0,0],
                            color: "#00fffb"
                        }
                    },
                    data: [6600, 4100, 6200, 12000, 2000]
                },
                {
                    name: '本年度',
                    type: 'bar',
                    barMaxWidth: '10',
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [4,4,0,0],
                            color: '#f4a12b'
                        }
                    },
                    data: [6400, 4200, 6300, 10000, 3000]
                }
            ]
        };

        var cacheId_ = cacheId==null?"rootw": cacheId;
        option.yAxis[0].name = cacheId_.indexOf("rootw")==0?"单位（吨）":"单位（度）";

        NHJG.echarts.$useNumber.setOption(option);
        var url = ctx+"screen/nhjg/queryMonthUseNumber?cacheId="+cacheId_+"&year="+year+"&important="+important;
        $.get(url,function(res){
            if(res.code==0){
                var names = [];//月份
                var data1 = [];//上年度
                var data2 = [];//本年度
                for (var i = 0; i < res.data.length; i++) {
                    var d = res.data[i];
                    data1.push(d.useNumber2);//本年
                    data2.push(d.useNumber1);//上年
                    names.push(d.name);

                }
                var options = NHJG.echarts.$useNumber.getOption();
                options.series[0].data = data1;
                options.series[1].data = data2;
                options.xAxis[0].data = names;
                NHJG.echarts.$useNumber.setOption(options);
            }
        })
        window.onresize = NHJG.echarts.$useNumber.resize();
    },

    /**
     * 类别信息
     */
    lbxxEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$category == null) {
            NHJG.echarts.$category = echarts.init(document.getElementById('lbxxEcharts'));
        } else {
            NHJG.echarts.$useNumber.clear();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$category);
        var option = {
            tooltip: {
                trigger: 'axis',
                formatter: "{a} <br/>{b} : {c}"+NHJG.setUnit(cacheId),
            },
            legend: {
                top: '0',
                // data: ['月用量', '同比', '环比'],
                data: ['月用量'],
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '12',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10, // 设置高度
            },
            grid: {
                top: '25%',
                left: '40',
                right: '10%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ["居民用户", "学生用户", "运行用户", "经营用户", "特殊行业"],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    axisLabel: {
                        interval:0,//代表显示所有x轴标签显示
                        // rotate:45, //代表逆时针旋转45度
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                },
            ],
            yAxis: [
                {
                    name: NHJG.setUnit(cacheId),
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
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
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },

                }
            ],
            series: [
                {
                    name: '月用量',
                    type: 'line',
                    barMaxWidth: '10',
                    splitLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },
                    itemStyle: {
                        normal: {
                            barBorderRadius: 4
                        }
                    },
                    data: [400, 300, 150, 120]
                },

            ],
            color: ['#1ee4b1', '#56efff', '#fff102']
        };
        NHJG.echarts.$category.setOption(option);
        //类别
        $.get(ctx + "screen/nhjg/nhtj/echarts/category?cacheId=" + cacheId + "&year=" + year+ "&important=" + important, function (result) {
            if (result.code ==0 && result.data.length > 0) {
                var data = result.data;
                var legend = [];
                for (var i = 0; i < data.length; i++) {
                    legend.push(data[i].name);
                }
                var op = NHJG.echarts.$category.getOption();
                op.xAxis[0].data = legend;
                op.series[0].data = data;
                NHJG.echarts.$category.setOption(op);
            }
        });
        window.onresize = NHJG.echarts.$category.resize();
    },
    /*
     *面积水耗
     */
    mjshEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$area == null) {
            NHJG.echarts.$area = echarts.init(document.getElementById('mjshEcharts'));
        } else {
            NHJG.echarts.$area.clear();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$area);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '10%',
                left: '40',
                right: '5%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1', '2', '3', '4', '5'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    boundaryGap: false,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value',
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
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },

                }
            ],
            series: [
                {
                    name: '2018',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [0, 0, 0, 0, 0],
                    itemStyle: {
                        normal: {
                            label: {
                                show: false,
                                formatter: "{c}",
                                textStyle: {
                                    color: '#fff'
                                },
                                position: 'bottom'
                            },
                        }
                    }
                },
            ],
            color: ['#00eaff']
        };
        NHJG.echarts.$area.setOption(option);
        //面积
        $.get(ctx + "screen/nhjg/nhtj/echarts/area?cacheId=" + cacheId + "&year=" + year+"&important="+important, function (result) {
            if (result.code == 0&&result.data.length > 0) {
                var data = result.data;
                var op = NHJG.echarts.$area.getOption();
                var xAxisData = [];
                var seriesData = [];
                for (var i = 0; i < data.length; i++) {
                    xAxisData.push(data[i].name);
                    seriesData.push(data[i].value);
                }
                op.xAxis[0].data = xAxisData;
                op.series[0].name = year;
                op.series[0].data = seriesData;
                NHJG.echarts.$area.setOption(op);
            }

        });
        window.onresize = NHJG.echarts.$area.resize();
    },
    // 人均水耗
    rjshEcharts: function (cacheId, year,important) {
        if (NHJG.echarts.$person == null) {
            NHJG.echarts.$person = echarts.init(document.getElementById('rjshEcharts'));
        } else {
            NHJG.echarts.$person.clear();
        }
        NHJG.data.ChartsName.push(NHJG.echarts.$person);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '10%',
                top: '10%',
                left: '40',
                right: '5%',
                bottom: '20%'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1', '2', '3', '4', '5'],
                    axisLine: {
                        lineStyle: {
                            type: 'dotted',
                            color: '#06ebeb',//x线的颜色
                        }
                    },
                    boundaryGap: false,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value',
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
                        lineStyle: {
                            type: 'dotted',
                            color: 'rgba(6,235,235,.4)',//x线的颜色
                        }
                    },

                }
            ],
            series: [
                {
                    name: '2018',
                    type: 'line',
                    barMaxWidth: '20',
                    data: [0, 0, 0, 0, 0],
                    itemStyle: {
                        normal: {
                            label: {
                                show: false,
                                formatter: "{c}",
                                textStyle: {
                                    color: '#fff'
                                },
                                position: 'bottom'
                            },
                        }
                    }
                },
            ],
            color: ['#00ee98']
        };
        NHJG.echarts.$person.setOption(option);
        //人均
        $.get(ctx + "screen/nhjg/nhtj/echarts/person?cacheId=" + cacheId + "&year=" + year+"&important="+important, function (result) {
            if (result.code == 0 && result.data.length > 0) {
                var data = result.data;
                var op = NHJG.echarts.$person
                    .getOption();
                var xAxisData = [];
                var seriesData = [];
                for (var i = 0; i < data.length; i++) {
                    xAxisData.push(data[i].name);
                    seriesData.push(data[i].value);
                }
                op.xAxis[0].data = xAxisData;
                op.series[0].name = year;
                op.series[0].data = seriesData;
                NHJG.echarts.$person.setOption(op);
            }

        });
        window.onresize = NHJG.echarts.$person.resize();
    },
    /**
     * 根据请求类型设置单位
     * @param cacheId
     */
    setUnit:function(cacheId){
        var unit = "吨";
        if(cacheId.indexOf("roote") == 0){
            unit = "度";
        }
        return unit;
    },

}
