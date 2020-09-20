/* 首页 */
var sqid = 'SQ000001';
var xqid = '';
layui.use([ 'layer', 'element','laypage'], function() { //独立版的layer无需执行这一句
	var $ = layui.jquery,
		element = layui.element,
		laypage = layui.laypage,
		layer = layui.layer; //独立版的layer无需执行这一句

		//总页数低于页码总数
		laypage.render({
		   elem: 'demo0'
		   ,count: 50 //数据总数
		});

		//总页数低于页码总数
		laypage.render({
		   elem: 'demo1'
		   ,count: 50 //数据总数
		});
		//总页数低于页码总数
		laypage.render({
		   elem: 'demo2'
		   ,count: 50 //数据总数
		});
		//总页数低于页码总数
		laypage.render({
		   elem: 'demo3'
		   ,count: 50 //数据总数
		});
});

featherCmsScript.register({
	element : "",
	onLoad : function(cmsOptions) {
		$.ajax({
			url:featherCmsScript.ctx + 'zhzl/api/getTree',
			ascnc:false,
			success:function(res) {
				$.fn.zTree.init($("#zTree"), setting, res.data);
			}
		});
	}
});

// 通知公告
featherCmsScript.register({
	element : "#newsContent",
	onLoad : function(cmsOptions) {
		var newsSwiper = new Swiper('#newsContent', {
			direction : 'vertical', //向上
			//spaceBetween: 30,
			//loop: true,
			autoplay : true,
			pagination : {
				el : '.swiper-pagination',
				clickable : true
			}
		});
		//鼠标覆盖停止自动切换
		newsSwiper.el.onmouseover = function() {
			newsSwiper.autoplay.stop();
		}
		//鼠标离开开始自动切换
		newsSwiper.el.onmouseout = function() {
			newsSwiper.autoplay.start();
		}
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {}
});

// 地图工具栏收缩
featherCmsScript.register({
	element : "#mapToolbarBtn",
	onClick : function(cmsOptions, _this) {
		if (!$('#mapToolbarBtn').hasClass('active')) {
			$(_this).addClass('active')
			$('.mapToolbarList').show()
		} else {
			$(_this).removeClass('active')
			$('.mapToolbarList').hide()
		}
	}
});

// 右侧树下拉弹框
featherCmsScript.register({
	element : "#positionSelectBtn",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		if($('#SelectTree').hasClass('hide')){
			$('#SelectTree').removeClass('hide');
			$(_this).addClass('active');
		} else {
			$(_this).removeClass('active');
			$('#SelectTree').addClass('hide');
		}
	}
});
// ztree公共数据
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: false,
		chkStyle: "checkbox",
		chkboxType: { "Y": "p", "N": "s" }
	},
	callback: {
		onClick: function (event, treeId, treeNode) {
			//回显示名称到页面
			$('#positionSelectBtn').html('');
			var html = `<i class="iconfont iconloc-s font_22 color_blue mr_10"></i>
						<span id="treeName" value="`+treeNode.id +`">`+treeNode.name+`</span>`;
			$('#positionSelectBtn').html(html);
			featherCmsScript._map.localPositionByName(treeNode.name);
			/*if(featherCmsScript._map.geometryEntityArr.length > 0){
				featherCmsScript._map.addLayerPic(featherCmsScript._map.type,featherCmsScript._map.typeObject)
			}*/
			$('#positionSelectBtn').removeClass('active');
			$('#SelectTree').addClass('hide');


            if(treeNode.pId==null || treeNode.pId==0){
                //重点人员统计
                sqid = treeNode.id;
                xqid = '';
                selectDjdwCount();
                getDyxlFb('')
                getDyNl('')
                getDyXb('')
                getDyMz('')
            }else{
                sqid = '';
                xqid = treeNode.id;
                selectDjdwCount()
                getDyxlFb('')
                getDyNl('')
                getDyXb('')
                getDyMz('')
            }
		},
	}
};
var data = [
    {
        id: 1,
        pId: 0,
        name: "七宝社区",
        open:true,//该节点默认打开
        children: [
			{name: "佳宝新村"},
			{name: "佳宝三村"},
			{name: "豪世胜地"},
			{name: "莱茵风尚"},
			{name: "陈家塘"},
			{name: "万兆家园"},
			{name: "牡丹公寓"},
			{name: "牡丹新村"},
			{name: "青南小区"},
			{name: "京都苑"},
			{name: "白浪新村"},
			{name: "横沥新村"},
			{name: "万泰公寓"},
			{name: "嘉丽苑"},
			{name: "吴宝新村"},
			{name: "明泉花园"},
		]
    },
];

// 党员学历分布
featherCmsScript.register({
	element : "#dynlfbEcharts",
	onLoad : function(cmsOptions) {
        getDyxlFb('')
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getDyxlFb(zyz){
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    if(_this.startCharts){
        clearInterval(_this.startCharts);
    }

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&zyz="+zyz;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/getDyxlFb"+urlParams,
        async:false,
        success : function(res) {
            $("#dyxlfb").html("");
            if (res.code == 0) {
                var html='';
                html +=`<div class="title">
										<span class="txt color_white">党员学历分布</span>
									</div>`
                for(var i in res.data) {
                    html += `<p>`+ res.data[i].XL +`<span class="fr"><i class="color_orange numberFont">` + res.data[i].MUN +`</i>人</span></p>`
                }
                $("#dyxlfb").html(html);
            }
        }
    })

    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/getDyNlFb"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                for(var i in res.data) {
                    var json = {value:""+res.data[i].NL+"",name:""+res.data[i].FW+""};
                    xdata.push(json);
                }
            }
        }
    })


    var ryxjEchart = echarts.init(document.getElementById('dynlfbEcharts'));
    //cmsOptions.object = ryxjEchart;
    var legend_data = ['30岁以下',  '30岁-50岁', '50岁及以下'];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)",
            confine:true
        },
        series : [ {
            name : '党员年龄占比统计',
            type : 'pie',
            radius : [ '55%', '80%' ],
            center : [ '45%', '50%' ],
            avoidLabelOverlap : false,
            label : {
                normal : {
                    show : false,
                    position : 'center'
                },
                emphasis : {
                    show : true,
                    formatter : function(params, ticket, callback) {
                        // console.log(params)
                        var value = params.data.value;
                        var name = params.data.name;
                        var str = value + '\n' + name
                        return str;
                    },
                    textStyle : {
                        fontSize : '12',
                        fontWeight : 'normal',
                        color : '#fff'
                    },
                }
            },
            labelLine : {
                normal : {
                    show : false
                }
            },
            data : xdata
        } ],
        color : [ '#4adb98', '#26acdf', '#fee67c']
    };

    ryxjEchart.setOption(option);

    // 2、鼠标移动上去的时候的高亮动画
    ryxjEchart.on('mouseover', function(param) {
        isSet = false
        clearInterval(_this.startCharts)
        // 取消之前高亮的图形
        ryxjEchart.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        // 高亮当前图形
        ryxjEchart.dispatchAction({
            type : 'highlight',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        })
        // 显示 tooltip
        ryxjEchart.dispatchAction({
            type : 'showTip',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        })
    });
    // 3、自动高亮展示
    var chartHover = function() {
        var dataLen = option.series[0].data.length
        // 取消之前高亮的图形
        ryxjEchart.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        charPie3currentIndex = (charPie3currentIndex + 1) % legend_data.length
        // 高亮当前图形
        ryxjEchart.dispatchAction({
            type : 'highlight',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        // 显示 tooltip
        // proportionStatisticsCharts.dispatchAction({
        //     type: 'showTip',
        //     seriesIndex: 0,
        //     dataIndex: charPie3currentIndex
        // })
    };
    _this.startCharts = setInterval(chartHover, 2000);
    // 4、鼠标移出之后，恢复自动高亮
    ryxjEchart.on('mouseout', function(param) {
        if (!isSet) {
            clearInterval(_this.startCharts);
            _this.startCharts = setInterval(chartHover, 2000)
            isSet = true
        }
    });
}


// 党员党龄分布
featherCmsScript.register({
    element : "#ageStructure",
    onLoad : function(cmsOptions) {
        getDyNl('')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function getDyNl(zyz){
    var houseStatisticsDom = document.getElementById("ageStructure");
    var houseStatisticsCharts = echarts.init(houseStatisticsDom);
    //cmsOptions.object = houseStatisticsCharts;

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&zyz="+zyz;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/getDyNl"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                xdata = res.data
            }
        }
    })



    var option = {
        grid : {
            left : '5%',
            right : '12%',
            top : '0',
            containLabel : true,
            height : '92%'
        },
        xAxis : {
            name : 'score',
            axisLabel : {
                show : true
            },
            splitLine : {
                show : true,
                lineStyle : {
                    type : 'dashed',
                    opacity : 0.4
                }
            },
            axisLabel : {
                show : true,
                textStyle : {
                    color : "rgba(255,255,255,.6)"
                }
            },
            axisTick : {
                show : true
            },
            axisLine : {
                show : true,
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
        },
        yAxis : {
            data : [ '1-10年', '10-20年', '20-30年','30-40年', '40-50年', '50-60年','60-70年' ],
            type : 'category',
            splitLine : {
                show : false,
            },
            axisLabel : {
                show : true,
                textStyle : {
                    color : "#c2fff0"
                }
            },
            axisLine : {
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
        },
        series : [ {
            type : 'bar',
            barWidth : 14,
            itemStyle : {
                //barBorderRadius: [4, 4, 4, 4],
                color : function(params) {
                    //给出颜色组
                    //var colorList = ['#42e8c0','#43ade9'];
                    //return colorList[params.dataIndex]
                    var colorList = [
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ]
                    ];
                    var index = params.dataIndex;
                    if (params.dataIndex >= colorList.length) {
                        index = params.dataIndex - colorList.length;
                    }
                    return new echarts.graphic.LinearGradient(1, 0, 0, 0, [ {
                        offset : 0,
                        color : colorList[index][0]
                    },
                        {
                            offset : 1,
                            color : colorList[index][1]
                        }
                    ]);
                }
            },
            label : {
                normal : {
                    show : false,
                    position : 'right',
                    color : '#fff'
                }
            },
            data : xdata
        } ]
    };
    houseStatisticsCharts.setOption(option);
}

// 党员性别比例
featherCmsScript.register({
	element : "#lNScale",
	onLoad : function(cmsOptions) {
        getDyXb('')
	},
	onResize : function(cmsOptions) {
//		cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getDyXb(zyz){
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    if(_this.startCharts){
        clearInterval(_this.startCharts);
    }

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&zyz="+zyz;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/getDyXb"+urlParams,
        async:false,
        success : function(res) {
            $("#dyxb").html("");
            if (res.code == 0) {
                var html='';
                html += '<p class="woman"><i class="iconfont iconwomen"></i>' + res.data[0].XB + ' <span class="fr color_white numberFont font_30">' + res.data[0].MUN + '</span></p>\n' +
                    '<p class="color_blue man"><i class="iconfont iconnancesuo"></i>' + res.data[1].XB + '  <span class="fr color_white numberFont font_30">' + res.data[1].MUN + '</span></p>'
                for(var i in res.data) {
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].XB+""};
                    xdata.push(json);
                }
                $("#dyxb").html(html);
            }
        }
    })


    var ryxjEchart = echarts.init(document.getElementById('lNScale'));
    //cmsOptions.object = ryxjEchart;
    var legend_data = ['女', '男'];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)"
        },
        series : [ {
            name : '男女比例',
            type : 'pie',
            radius : [ '55%', '80%' ],
            center : [ '45%', '50%' ],
            avoidLabelOverlap : false,
            label : {
                normal : {
                    show : false,
                    position : 'center'
                },
                emphasis : {
                    show : true,
                    formatter : function(params, ticket, callback) {
                        // console.log(params)
                        var percent = params.percent;
                        var str = percent +'%'
                        return str;
                    },
                    textStyle : {
                        fontSize : '12',
                        fontWeight : 'normal',
                        color : '#fff'
                    },
                }
            },
            labelLine : {
                normal : {
                    show : false
                }
            },
            data : xdata
        } ],
        color : [ '#f6c96b', '#4de09c']
    };

    ryxjEchart.setOption(option);

    // 2、鼠标移动上去的时候的高亮动画
    ryxjEchart.on('mouseover', function(param) {
        isSet = false
        clearInterval(_this.startCharts)
        // 取消之前高亮的图形
        ryxjEchart.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        // 高亮当前图形
        ryxjEchart.dispatchAction({
            type : 'highlight',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        })
        // 显示 tooltip
        ryxjEchart.dispatchAction({
            type : 'showTip',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        })
    });
    // 3、自动高亮展示
    var chartHover = function() {
        var dataLen = option.series[0].data.length
        // 取消之前高亮的图形
        ryxjEchart.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        charPie3currentIndex = (charPie3currentIndex + 1) % legend_data.length
        // 高亮当前图形
        ryxjEchart.dispatchAction({
            type : 'highlight',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
        // 显示 tooltip
        // proportionStatisticsCharts.dispatchAction({
        //     type: 'showTip',
        //     seriesIndex: 0,
        //     dataIndex: charPie3currentIndex
        // })
    };
    _this.startCharts = setInterval(chartHover, 2000);
    // 4、鼠标移出之后，恢复自动高亮
    ryxjEchart.on('mouseout', function(param) {
        if (!isSet) {
            clearInterval(_this.startCharts);
            _this.startCharts = setInterval(chartHover, 2000)
            isSet = true
        }
    });
}

// 党员民族比例
featherCmsScript.register({
	element : "#MZScale",
	onLoad : function(cmsOptions) {
        getDyMz('')
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getDyMz(zyz){
    var ryxjEchart = echarts.init(document.getElementById('MZScale'));
    //cmsOptions.object = ryxjEchart;

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&zyz="+zyz;
    var xdata=[];
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/getDyMz"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                for(var i in res.data) {
                    xdata[i]=res.data[i].MZ
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].MZ+""};
                    ydata.push(json);
                }
            }
        }
    })

    var legend_data = ['汉族', '回族',' 苗族', '土家族', '藏族'];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: '10%',
            y: 'center',
            textStyle: {
                color: 'rgba(255, 255, 255, 1)',
                fontSize: '12',
                fontWeight: 'normal'
            },
            itemWidth: 10,  // 设置宽度
            itemHeight: 4, // 设置高度
            data: xdata
        },
        series : [ {
            name : '民族比例',
            type : 'pie',
            radius : [ '40%', '80%' ],
            center : [ '70%', '50%' ],
            avoidLabelOverlap : false,
            label : {
                normal : {
                    show : false,
                    position : 'center'
                },
                emphasis : {
                    show : false,
                    formatter : function(params, ticket, callback) {
                        // console.log(params)
                        var percent = params.percent;
                        var str = percent +'%'
                        return str;
                    },
                    textStyle : {
                        fontSize : '12',
                        fontWeight : 'normal',
                        color : '#fff'
                    },
                }
            },
            labelLine : {
                normal : {
                    show : false
                }
            },
            data : ydata
        } ],
        color : [ '#fee67c', '#f4a82e', '#f4812e','#7377e2','#26addd','#49da97']
    };

    ryxjEchart.setOption(option);
}

// 关闭右侧弹框
featherCmsScript.register({
	element : ".rightCloseBtn",
	onClick : function(cmsOptions, _this) {
		$('.rightContent').addClass('CloseRight');
		$('.zhzlBottomContent').removeClass('leftShrink');
		featherCmsScript.trigger("resize");
	}
});
// 点击左侧--党组织
featherCmsScript.register({
	element : "#dzzRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.zhzlBottomContent').addClass('leftShrink');
		$('.dzzRightList').removeClass('hide');
		$('.doneRightList,.processingRightList,.unprocessedRightList').addClass('hide');
		featherCmsScript.trigger("resize");
	}
});
// 点击左侧--已办结
featherCmsScript.register({
	element : "#doneRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.zhzlBottomContent').addClass('leftShrink');
		$('.doneRightList').removeClass('hide');
		$('.dzzRightList,.processingRightList,.unprocessedRightList').addClass('hide');
		featherCmsScript.trigger("resize");
	}
});

// 点击左侧--处理中
featherCmsScript.register({
	element : "#processingRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.zhzlBottomContent').addClass('leftShrink');
		$('.processingRightList').removeClass('hide');
		$('.dzzRightList,.doneRightList,.unprocessedRightList').addClass('hide');
		featherCmsScript.trigger("resize");
	}
});

// 点击左侧--未处理
featherCmsScript.register({
	element : "#unprocessedRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.zhzlBottomContent').addClass('leftShrink');
		$('.unprocessedRightList').removeClass('hide');
		$('.dzzRightList,.doneRightList,.processingRightList').addClass('hide');
		featherCmsScript.trigger("resize");
	}
});

// 社区党组织强框
featherCmsScript.register({
	element : "#dzzLayerOpen",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '300px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#dzzLayer'),
			skin : 'layer-style',
			id : 'dzzLayerBox'
		});
	}
});

// 已办结强框
featherCmsScript.register({
	element : "#doneLayerOpen",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '300px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#doneLayer'),
			skin : 'layer-style',
			id : 'doneLayerBox'
		});
	}
});

// 处理中强框
featherCmsScript.register({
	element : "#processingLayerOpen",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '300px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#processingLayer'),
			skin : 'layer-style',
			id : 'processingLayerBox'
		});
	}
});

// 末办理强框
featherCmsScript.register({
	element : "#unprocessedLayerOpen",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '300px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#unprocessedLayer'),
			skin : 'layer-style',
			id : 'unprocessedLayerBox'
		});
	}
});

featherCmsScript.register({
    element : "#dzdw",
    onLoad : function(cmsOptions) {
        selectDjdwCount()
    },
    onResize : function(cmsOptions) {},
    onClick : function(cmsOptions, _this) {
    }
});



function selectDjdwCount(){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "zhdj/api/selectDjdwCount"+urlParams,
        success : function(res) {
            $("#dzdw").html("");
            //$("#zyz").html("");
            if (res.code == 0) {
                var str1 = `<li id="dzzRightOpen" onclick="getDzz(sqid,xqid,'')">
								党组织 <span class="number font_22 color_navy_blue numberFont">`+res.data.dzz+`</span>
							</li>
							<li onclick="getDy(sqid,xqid)">
								党员 <span class="number font_22 color_navy_blue numberFont">`+res.data.dy+`</span>
							</li>
							<li onclick="getZyz(sqid,xqid,'是')">
								基层志愿者 <span class="number font_22 color_navy_blue numberFont">`+res.data.zyz+`</span>
							</li>`;
                $("#dzdw").html(str1);

                // var str2 = `<li>
				// 				事项总数 <span class="number font_22 color_navy_blue numberFont">225</span>
				// 			</li>
				// 			<li id="doneRightOpen">
				// 				已办结 <span class="number font_22 color_navy_blue numberFont">77</span>
				// 			</li>
				// 			<li id="processingRightOpen">
				// 				处理中 <span class="number font_22 color_navy_blue numberFont">74</span>
				// 			</li>
				// 			<li id="unprocessedRightOpen">
				// 				未处理 <span class="number font_22 color_navy_blue numberFont">74</span>
				// 			</li>`;
                // $("#zyz").html(str2);
            }
        }
    })

}


function searchList(a) {
    if (a=='dzzxx'){
        var mc = $("#dzzxx").val();
        getDzz(sqid,xqid,mc)
    } else if (a=='dyxx'){
        var dyxx = $("#dyxx").val();
        getDyList(sqid,xqid,dyxx)
    } else if (a=='zyzxx'){
        var zyzxx = $("#zyzxx").val();
        getZyzList(sqid,xqid,'是',zyzxx)
    }

}

function getDzz(sqid,xqid,mc){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&mc="+mc;
    $.ajax({
        url:featherCmsScript.ctx + "zhdj/api/getDzzList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'dzzPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('dzzList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html1 += `<div class="layui-row colum" id="dzzLayerOpen" onclick="getDzzTk('`+item.DZZID+`')">
													<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
														<span>` + (parseInt(index) + 1) +`</span>
												   </div>
													<div class="layui-col-md6 layui-col-sm6 layui-col-xs6">
														<span title="">` + item.MC + `</span>
													</div>
													<div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
														<span title="">2010.09.24</span>
													</div>
												</div>`;
                                });
                                arr.push(html1);
                                return arr.join('');
                            }();
                        }
                    });
                });
            }
        }
    })

    //		$(_this).addClass('active');
    $('.rightContent').removeClass('CloseRight');
    $('.zhzlBottomContent').addClass('leftShrink');
    $('.dzzRightList').removeClass('hide');
    $('.doneRightList,.processingRightList,.unprocessedRightList').addClass('hide');
    featherCmsScript.trigger("resize");
    featherCmsScript._map.clearPic();
}

//党组织弹框
function getDzzTk(dzzid){
    $.ajax({
        url: featherCmsScript.ctx + "zhdj/api/getDzzInfo?dzzid="+dzzid,
        success: function (res) {
            if (res.code == 0) {
                var html = '';
                $("#dzzLayer").html('');
                html+=`<h4 class="moveTxt">社区党组织</h4>
							<div class="layerContent">
								<div class="abnormalForm">
									<ul>
										<li><label class="color_blue">位置：</label><span>`+res.data.wz+`</span></li>
										<li><label class="color_blue">成立时间：</label><span>2020-05-12 14：00：00</span></li>
										<li><label class="color_blue">党员人数：</label><span>`+res.data.dyrs+`</span></li>
										<li><label class="color_blue">志愿者人数：</label><span>`+res.data.zyzrs+`</span></li>
									</ul>
									<img width="100%" class="img mt_10" src="images/video_img1.png" alt="" />
								</div>
							</div>`
                $("#dzzLayer").html(html);
            }
        }
    });

//弹框
    layer.open({
        type : 1,
        title : false,
        area : [ '300px', 'auto' ],
        move : '.moveTxt',
        scrollbar : false,
        shade : 0,
        //fix: false,
        content : $('#dzzLayer'),
        skin : 'layer-style',
        id : 'dzzLayerBox'
    });
}


//党员弹框
function getDyZyzTk(dyid){
    $.ajax({
        url: featherCmsScript.ctx + "zhdj/api/getDyZyzTk?dyid="+dyid,
        success: function (res) {
            if (res.code == 0) {
                var html = '';
                $("#personnelInfoLayer").html('');

                html+=`<h4 class="moveTxt">`+res.data.XM+`</h4>
                            <div class="layerContent">
                            <ul class="personnelInfoListLayer">
                            <li>`
                html += `<li>`;
                var age = Number(res.data.NL);
                if(res.data.XB == '男'){
                    if(age >=60){
                        html += `<img class="img" src="img/60m.jpg" alt="" />`;
                    }else if(age<60 && age>=20){
                        html += `<img class="img" src="img/20-59m.jpg" alt="" />`;
                    }else{
                        html += `<img class="img" src="img/1-19m.jpg" alt="" />`;
                    }
                }else{
                    if(age >=60){
                        html += `<img class="img" src="img/60w.png" alt="" />`;
                    }else if(age<60 && age>=20){
                        html += `<img class="img" src="img/20-59w.jpg" alt="" />`;
                    }else{
                        html += `<img class="img" src="img/1-19w.jpg" alt="" />`;
                    }
                }

                html+=
                    `<div class="layui-row">
                                <div class="layui-col-md7">
                                  <p><span class="color_blue">姓名：</span><span class="color_white">`+res.data.XM+`</span></p>
                                  <p><span class="color_blue">民族：</span><span class="color_white">`+res.data.MZ+`</span></p>
                                     <p><span class="color_blue">居住地址：</span><span class="color_white">`+res.data.ZZ+`</span></p>
                            
                                </div>
                                <div class="layui-col-md5">
                                  <p><span class="color_blue">性别：</span><span class="color_white">`+res.data.XB+`</span></p>
                                  <p><span class="color_blue">年龄：</span><span class="color_white">`+res.data.NL+`</span></p>
                                  <p><span class="color_blue">电话：</span><span class="color_white">`+res.data.LXDH+`</span></p>
                                </div>
                                <div class="layui-col-md12">
                                <p><span class="color_blue">所属党组织：</span><span class="color_white">`+res.data.SSDZZ+`</span></p>
                                           </div>
                                        </div>
                                        </li>
                                        </ul>
                                        </div>`;

                $("#personnelInfoLayer").html(html);
            }
        }
    });

//弹框
    layer.open({
        type : 1,
        title : false,
        area : [ '400px', '180px' ],
        move : '.moveTxt',
        scrollbar : false,
        shade : 0,
        //fix: false,
        content : $('#personnelInfoLayer'),
        skin : 'layer-style',
        id : 'personnelInfoLayerOpenBox',
        cancel:function(index, layero){
            featherCmsScript._map.clear();
        }
    });
}
function getDy(sqid,xqid) {
    featherCmsScript._map.clearPic();
    getDyList(sqid,xqid,'')
    //党员学历分布
    getDyxlFb('')
    //党员年龄
    getDyNl('')
    //党员男女比例
    getDyXb('')
    //党员民族比例
    /getDyMz('')
}
function getZyz(sqid,xqid,zyz) {
    featherCmsScript._map.clearPic();
    getZyzList(sqid,xqid,zyz,'')
    // //党员学历分布
    getDyxlFb(zyz)
    // //党员年龄
    getDyNl(zyz)
    // //党员男女比例
    getDyXb(zyz)
    // //党员民族比例
    getDyMz(zyz)
}

function getDyList(sqid,xqid,xm){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&xm="+xm;
    $.ajax({
        url:featherCmsScript.ctx + "zhdj/api/getDyZyzList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'dyzyzPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('dyzyzList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html1 += `<div class="layui-row colum" id="dyzyzLayerOpen" onclick="getDyZyzTk('`+item.DYID+`')">
													<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
														<span>` + (parseInt(index) + 1) +`</span>
												   </div>
													<div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
														<span title="">` + item.XM + `</span>
													</div>
													<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
														<span title="">`+ item.XL +`</span>
													</div>
													<div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
														<span title="">`+ item.DYLX +`</span>
													</div>
												</div>`;
                                });
                                arr.push(html1);
                                return arr.join('');
                            }();
                        }
                    });
                });
            }
        }
    })



    //		$(_this).addClass('active');
    $('.rightContent').removeClass('CloseRight');
    $('.zhzlBottomContent').addClass('leftShrink');
    $('.dyzyzRightList').removeClass('hide');
    $('.doneRightList,.processingRightList,.unprocessedRightList,.dzzRightList').addClass('hide');
    featherCmsScript.trigger("resize");
}

function getZyzList(sqid,xqid,zyz,zyzxx) {
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&zyz="+zyz+"&zyzxx="+zyzxx;
    $.ajax({
        url:featherCmsScript.ctx + "zhdj/api/getZyzList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'zyzPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('zyzList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html1 += `<div class="layui-row colum" id="zyzLayerOpen" onclick="getDyZyzTk('`+item.DYID+`')">
													<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
														<span>` + (parseInt(index) + 1) +`</span>
												   </div>
													<div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
														<span title="">` + item.XM + `</span>
													</div>
													<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
														<span title="">`+ item.XL +`</span>
													</div>
													<div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
														<span title="">`+ item.DYLX +`</span>
													</div>
												</div>`;
                                });
                                arr.push(html1);
                                return arr.join('');
                            }();
                        }
                    });
                });
            }
        }
    })
    //		$(_this).addClass('active');
    $('.rightContent').removeClass('CloseRight');
    $('.zhzlBottomContent').addClass('leftShrink');
    $('.zyzRightList').removeClass('hide');
    $('.doneRightList,.processingRightList,.unprocessedRightList,.dyzyzRightList,.dzzRightList').addClass('hide');
    featherCmsScript.trigger("resize");
}

