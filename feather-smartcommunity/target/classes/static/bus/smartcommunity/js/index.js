/* 首页 */
layui.use([ 'layer', 'element' ], function() { //独立版的layer无需执行这一句
	var $ = layui.jquery,
		element = layui.element,
		layer = layui.layer; //独立版的layer无需执行这一句
});

// 重点人员列表显示详情页
$('.KeyPersonnelList').on('click', 'li', function() {
	$('.KeyPersonnelLayerMain').addClass('hide');
	$('.KeyPersonnelDetail').removeClass('hide');
});
$('.KeyPersonnelDetail').on('click', '#KeyPersonnelBackBtn', function() {
	$('.KeyPersonnelLayerMain').removeClass('hide');
	$('.KeyPersonnelDetail').addClass('hide');
});

// 点击房屋列表显示详情页
$('.houseList').on('click', 'li', function() {
	$('.houseLayerMain').addClass('hide');
	$('.houseLayerDetail').removeClass('hide');
});
$('.houseLayerDetail').on('click', '#houseBackBtn', function() {
	$('.houseLayerMain').removeClass('hide');
	$('.houseLayerDetail').addClass('hide');
});

// 链接 进入后端管理
featherCmsScript.register({
	element : "#link-nav_console",
	onClick : function(cmsOptions, _this) {
		window.open(featherCmsScript.ctx + "index", "console");
	}
});

// 房屋统计
featherCmsScript.register({
	element : "#houseStatistics",
	onLoad : function(cmsOptions) {
        getHouseCount("SQ000001","")
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

// 车位信息
featherCmsScript.register({
	element : "#ParkingInfo",
	onLoad : function(cmsOptions) {
        getZhsqCwEchartsData("七宝社区","","");
	},
	onResize : function(cmsOptions) {
	},
	onClick : function(cmsOptions, _this) {}
});

// 事件信息--上报状态
featherCmsScript.register({
	element : "#eventStatus",
	onLoad : function(cmsOptions) {
        eventStatus("SQ000001","");
	},
	onResize : function(cmsOptions) {
	},
	onClick : function(cmsOptions, _this) {}
});
function eventStatus(sqid,xqid){
    var eventStatusDom = document.getElementById("eventStatus");
    var eventStatusCharts = echarts.init(eventStatusDom);
    var xdata=[];
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "screen/api/getSourceCount?sqid="+sqid+"&xqid="+xqid,
        async:false,
        success : function(res) {
            if(res.code==0){
                for(var i in res.data){
                    xdata.push(res.data[i].YCLY);
                    ydata.push(res.data[i].num);
                }
            }
        }
    })

    var option = {
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'cross',
                label : {
                    backgroundColor : '#6a7985'
                }
            }
        },
        grid : {
            left : '5%',
            right : '12%',
            top : '4%',
            containLabel : true,
            height : '90%'
        },
        xAxis : [ {
            type : 'category',
            boundaryGap : true,
            data : xdata,
            axisLabel : {
                show : true,
                textStyle : {
                    color : "#fff"
                }
            },
            axisLine : {
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
            axisTick : {
                alignWithLabel : true
            }
        } ],
        yAxis : [ {
            type : 'value',
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
                    color : "#fff"
                }
            },
            axisLine : {
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
        } ],
        series : [ {
            name : '上报状态/分类',
            type : 'bar',
            barMaxWidth : 14,
            data : ydata,
            color : function(params) {
                //给出颜色组
                //var colorList = ['#42e8c0','#43ade9'];
                //return colorList[params.dataIndex]
                var colorList = [
                    [ 'rgba(66,232,192,.9)', 'rgba(66,232,192,.1)' ],
                    [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
                    [ 'rgba(253,231,122,.9)', 'rgba(253,231,122,.1)' ],
                ];
                var index = params.dataIndex;
                if (params.dataIndex >= colorList.length) {
                    index = params.dataIndex - colorList.length;
                }
                return new echarts.graphic.LinearGradient(0, 0, 0, 1, [ {
                    offset : 0,
                    color : colorList[index][0]
                },
                    {
                        offset : 1,
                        color : colorList[index][1]
                    }
                ]);
            }
        }, ]
    };
    eventStatusCharts.setOption(option);
}
// 事件上报/派单
featherCmsScript.register({
	element : "#eventReport",
	onLoad : function(cmsOptions) {
        eventReport("SQ000001","");
	},
	onResize : function(cmsOptions) {
	},
	onClick : function(cmsOptions, _this) {}
});
function eventReport(sqid,xqid) {
    var eventReportDom = document.getElementById("eventReport");
    var eventReportCharts = echarts.init(eventReportDom);
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "screen/api/getStatusCount?sqid="+sqid+"&xqid="+xqid,
        async:false,
        success : function(res) {
            if(res.code==0){
                for(var i in res.data){
                    var json = {value:""+res.data[i].num+"",name:""+res.data[i].CZZT+""};
                    xdata.push(json);
                }

            }
        }
    })


    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'vertical',
            right : '10%	',
            y : 'center',
            textStyle : {
                color : 'rgba(255, 255, 255, 1)',
                fontSize : '12',
                fontWeight : 'normal'
            },
            itemWidth : 10,
            itemHeight : 6,
            data : [ '已处置', '未处置' ]
        },
        calculable : true,
        series : [ {
            name : '处置状态',
            type : 'pie',
            radius : [ '30%', '100%' ],
            center : [ '30%', '60%' ],
            roseType : 'area',
            label : {
                normal : {
                    show : false,
                },
            },
            data : xdata
        } ],
        color : [ '#fde77a', '#ff9d50', '#43e8be' ]
    };
    eventReportCharts.setOption(option);
}
// 设备信息
featherCmsScript.register({
	element : "#deviceInfo",
	onLoad : function(cmsOptions) {
        getSbxxCount('SQ000001','');
	},
	onResize : function(cmsOptions) {
	},
	onClick : function(cmsOptions, _this) {}
});

// 人员巡检
featherCmsScript.register({
	element : "#ryxjEchartPie",
	onLoad : function(cmsOptions) {
        getRyxjCount('SQ000001','')
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

// 投诉建议滚动效果
featherCmsScript.register({
	element : ".Suggestions-container",
	onLoad : function(cmsOptions) {
		//投诉建议向上滚动效果
		var SuggestionsSwiper = new Swiper('.Suggestions-container', {
			observer : true,
			observeParents : true,
			slidesPerView : 3,
			spaceBetween: 5,
			loop : true,
			direction : 'vertical', //向上
			autoplay : true,
			pagination : {
				el : '.swiper-pagination',
				clickable : true,
			},
		});
		//鼠标覆盖停止自动切换
		SuggestionsSwiper.el.onmouseover = function() {
			SuggestionsSwiper.autoplay.stop();
		}
		//鼠标离开开始自动切换
		SuggestionsSwiper.el.onmouseout = function() {
			SuggestionsSwiper.autoplay.start();
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

// 图表收缩
featherCmsScript.register({
	element : "#shrinkBtn",
	onClick : function(cmsOptions, _this) {
		if (!$(_this).hasClass('nemuOpen')) {
			$(_this).addClass('active nemuOpen').siblings('li').removeClass('active')
			$(".leftContent").animate({
				left : "-18vw"
			});
			$(".rightContent").animate({
				right : "-18vw"
			});
			$(".bottomContent").animate({
				bottom : "-22vw"
			});
		} else {
			$(_this).removeClass('active nemuOpen')
			$(".leftContent").animate({
				left : "0"
			});
			$(".rightContent").animate({
				right : "0"
			});
			$(".bottomContent").animate({
				bottom : "0"
			});
		}
	}
});

// 安防按钮
featherCmsScript.register({
	element : "#securityBtn",
	onClick : function(cmsOptions, _this) {
		$('.assetsMenuList').addClass('hide');
		featherCmsScript._map.clearPic();
		featherCmsScript._map.addLayerPic('SXT');
		if($('.SecurityMenuList').hasClass('hide')){
    		$('.SecurityMenuList').removeClass('hide');
    		$('.assetsMenuList').addClass('hide');
			$(this).addClass('active').siblings('li').removeClass('active');
		} else {
			$(this).removeClass('active');
			$('.SecurityMenuList').addClass('hide');
		}
	}
});

// 房屋按钮
/*featherCmsScript.register({
	element : "#houseBtn",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '500px', '340px' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#houseLayer'),
			skin : 'layer-style',
			id : 'houseLayerBox'
		});
	}
});*/


// 资产按钮
featherCmsScript.register({
	element : "#assetsBtn",
	onClick : function(cmsOptions, _this) {
		$('.SecurityMenuList').addClass('hide');

		featherCmsScript._map.clearPic();
		featherCmsScript._map.addLayerPic('LJT');
		if($('.assetsMenuList').hasClass('hide')){
			$('.assetsMenuList').removeClass('hide');
			$('.SecurityMenuList').addClass('hide');
			$(this).addClass('active').siblings('li').removeClass('active');
		} else {
			$(this).removeClass('active');
			$('.assetsMenuList').addClass('hide');
		}
	}
});

// 人员按钮
/*featherCmsScript.register({
	element : "#personnelBtn",
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '500px', '340px' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#KeyPersonnelLayer'),
			skin : 'layer-style',
			id : 'KeyPersonnelLayerBox'
		});
	}
});*/

// 异常弹框
/*featherCmsScript.register({
	element : "#abnormalBtn",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#abnormalLayer'),
			skin : 'layer-style',
			id : 'abnormalLayerBox'
		});
	}
});*/

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

// 道杆弹框左右切换
/*featherCmsScript.register({
	element : "#RoadPoleswiperWrapper",
	onLoad : function(cmsOptions) {
		var RoadPolesSwiper = new Swiper('#RoadPoleswiperWrapper', {
			observer : true,
			observeParents : true,
			navigation : {
				nextEl : '.swiper-button-next',
				prevEl : '.swiper-button-prev',
			},
			//spaceBetween: 30,
			autoplay : true
		});
		//鼠标覆盖停止自动切换
		RoadPolesSwiper.el.onmouseover = function() {
			RoadPolesSwiper.autoplay.stop();
		}
		//鼠标离开开始自动切换
		RoadPolesSwiper.el.onmouseout = function() {
			RoadPolesSwiper.autoplay.start();
		}
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {}
});*/

// 道杆弹框
/*featherCmsScript.register({
	element : "#vehicleLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#RoadPoleLayer'),
			skin : 'layer-style',
			id : 'RoadPoleLayerBox'
		});
	}
});*/

// 门禁左右切换
/*featherCmsScript.register({
	element : "#mjswiperWrapper",
	onLoad : function(cmsOptions) {
		var mjSwiper = new Swiper('#mjswiperWrapper', {
			observer : true,
			observeParents : true,
			navigation : {
				nextEl : '.swiper-button-next',
				prevEl : '.swiper-button-prev',
			},
			//spaceBetween: 30,
			autoplay : true
		});
		//鼠标覆盖停止自动切换
		mjSwiper.el.onmouseover = function() {
			mjSwiper.autoplay.stop();
		}
		//鼠标离开开始自动切换
		mjSwiper.el.onmouseout = function() {
			mjSwiper.autoplay.start();
		}
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {}
});*/

// 门禁弹框
/*featherCmsScript.register({
	element : "#AccessControlLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#AccessControlLayer'),
			skin : 'layer-style',
			id : 'AccessControlLayerBox'
		});
	}
});*/




// 闸机左右切换
featherCmsScript.register({
	element : "#GateWiperWrapper",
	onLoad : function(cmsOptions) {
		var Gatewiper = new Swiper('#GateWiperWrapper', {
			observer : true,
			observeParents : true,
			navigation : {
				nextEl : '.swiper-button-next',
				prevEl : '.swiper-button-prev',
			},
			//spaceBetween: 30,
			autoplay : true
		});
		//鼠标覆盖停止自动切换
		Gatewiper.el.onmouseover = function() {
			Gatewiper.autoplay.stop();
		}
		//鼠标离开开始自动切换
		Gatewiper.el.onmouseout = function() {
			Gatewiper.autoplay.start();
		}
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {}
});

// 闸机弹框
/*featherCmsScript.register({
	element : "#GateLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#GateLayer'),
			skin : 'layer-style',
			id : 'GateLayerBox'
		});
	}
});

// 摄像头弹框
featherCmsScript.register({
	element : "#cameraLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type: 1,
			title: false,
			area: ['400px', 'auto'],
			move: '.moveTxt',
			scrollbar: false,
			shade: 0,
			//fix: false,
			content: $('#cameraLayer'),
			skin: 'layer-style',
			id: 'cameraLayerBox'
		});
	}
});*/

// 垃圾桶弹框
/*featherCmsScript.register({
	element : "#ashbinLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#ashbinLayer'),
			skin : 'layer-style',
			id : 'ashbinLayerBox'
		});
	}
});

// 烟感弹框
featherCmsScript.register({
	element : "#smokeLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#smokeLayer'),
			skin : 'layer-style',
			id : 'smokeLayerBox'
		});
	}
});

// 充电桩弹框
featherCmsScript.register({
	element : "#chargingPileLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#chargingPileLayer'),
			skin : 'layer-style',
			id : 'chargingPileLayerBox'
		});
	}
});

// 路灯弹框
featherCmsScript.register({
	element : "#streetLampLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#streetLampLayer'),
			skin : 'layer-style',
			id : 'streetLampLayerBox'
		});
	}
});

// 消防栓弹框
featherCmsScript.register({
	element : "#fireFightingLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '400px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#fireFightingLayer'),
			skin : 'layer-style',
			id : 'fireFightingLayerBox'
		});
	}
});*/

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
// 右侧树下拉弹框
featherCmsScript.register({
    element : "",
    onLoad : function(cmsOptions) {
        ssjk("SQ000001","");
	},
    onResize : function(cmsOptions) {},
    onClick : function(cmsOptions, _this) {
    }
});
function ssjk(sqid,xqid){
    $.ajax({
        url: featherCmsScript.ctx + "znaf/api/getSsjk?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
           if(res.code==0){
           	var html='';
           	$("#ssjk").html('');
           	for(var i in res.data){
                    for(var key in res.data[i]){
                        var css='';
                        if(key=='进入人数'){
                            css='iconwaichurenyuan1';
                        }else if(key=='进入车辆'){
                            css='iconwaichucheliang1';
                        }else if(key=='外出人数'){
                            css='iconwaichurenyuan';
                        }else if(key=='外出车辆'){
                            css='iconwaichucheliang';
                        }
                        html+=`<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
							<h2 class="font_30 color_white numberFont right">`+res.data[i][key]+`</h2>
							<div class="txtC txt">
								<h5>
								<i class="iconfont `+css+` color_blue font_22"></i>
							</h5>
								<h3 class="color_blue font_14">`+key+`</h3>
							</div>
						</div>`;
                    }
			}
			$("#ssjk").html(html);
		   }
        }
    })

}


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
			if(treeNode.pId==null || treeNode.pId==0){
                getSbxxCount(treeNode.id,'');
                getTsjy(treeNode.id,'');
                eventStatus(treeNode.id,'');
                eventReport(treeNode.id,'');
                //人员巡检
                getRwzsCount(treeNode.id,'');
                getRyxjCount(treeNode.id,'');
                ssjk(treeNode.id,'');
                //居民信息
                getPersonInfo(treeNode.id,'');
                //房屋信息
                getHouse(treeNode.id,'');
                getHouseCount(treeNode.id,'');
			}else{
                getSbxxCount('',treeNode.id);
                getTsjy('',treeNode.id);
                eventStatus('',treeNode.id);
                eventReport('',treeNode.id);
                //人员巡检
                getRwzsCount(treeNode.pId,treeNode.id);
                getRyxjCount(treeNode.pId,treeNode.id);
                ssjk('',treeNode.id);
                //居民信息
                getPersonInfo('',treeNode.id);
                //房屋信息
                getHouse('',treeNode.id);
                getHouseCount('',treeNode.id);
            }

			//回显示名称到页面
			$('#positionSelectBtn').html('');
			var html = `<i class="iconfont iconloc-s font_22 color_blue mr_10"></i>
						<span id="treeName" value="`+treeNode.id +`">`+treeNode.name+`</span>`;
			$('#positionSelectBtn').html(html);
			//找到对应的小区进行定位
			featherCmsScript._map.localPositionByName(treeNode.name);
			if(featherCmsScript._map.geometryEntityArr.length > 0){
				featherCmsScript._map.addLayerPic(featherCmsScript._map.type,featherCmsScript._map.typeObject)
			}
            $('#positionSelectBtn').removeClass('active');
            $('#SelectTree').addClass('hide');
			//获取车位信息echarts列表数据
			getZhsqCwEchartsData(treeNode.name,treeNode.pId,treeNode.id);

		},
	}
};

function getTsjy(sqid,xqid){
    $.ajax({
        url: featherCmsScript.ctx + "screen/api/getSuggestionList?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
            $("#tsjy").html("");
            if(res.code==0){
                var html='';
                html += `<div class="Suggestions-container">
<ul class="swiper-wrapper SuggestionsList">`;
                for(var i in res.data){
                    html+='<li class="swiper-slide" id="'+res.data[i].TSJYID+'">'
                        +'<span class="time">['+res.data[i].XQMC+']</span>'
                        +'<a href="">'+res.data[i].JYNR+'</a></li>';
                }
                html +='</ul></div>';
                $("#tsjy").html(html);
                var SuggestionsSwiper = new Swiper('.Suggestions-container', {
                    slidesPerView : 3,
                    spaceBetween: 5,
                    loop : true,
                    direction : 'vertical', //向上
                    autoplay : true,
                    pagination : {
                        el : '.swiper-pagination',
                        clickable : true,
                    },
                });
                //鼠标覆盖停止自动切换
                SuggestionsSwiper.el.onmouseover = function() {
                    SuggestionsSwiper.autoplay.stop();
                }
                //鼠标离开开始自动切换
                SuggestionsSwiper.el.onmouseout = function() {
                    SuggestionsSwiper.autoplay.start();
                }
            }
        }
    });
    $.ajax({
        url: featherCmsScript.ctx + "screen/api/getAdviceList?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
            if(res.code==0){
                $("#tzgg").html("");
                var html='';
                for(var i in res.rows){
                    html+='<li class="swiper-slide">['+res.rows[i].fbsj+']'+res.rows[i].bt+'</li>';
                }
                $("#tzgg").html(html);
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
            }
        }
    });

}


function getSbxxCount(sqid,xqid) {
    $.ajax({
        url: featherCmsScript.ctx + "screen/api/getSbxxCount?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
            if (res.code == 0&&res.data.length>0) {
                var html='';
                $("#sbxx").html("");
                var sxt=[];
                var zj=[];
                var mj=[];
                var yg=[];
                for(var i in res.data){
                	html+=`<div class="layui-col-md3 layui-col-sm3">
								<h4 class="font_30 txtC mt_10 numberFont">`+res.data[i].sum+`</h4>
								<h6 class="font_16 color_blue txtC">`+res.data[i].sblx+`</h6>
							</div>`;
                	if(res.data[i].sblx=='摄像头'){
                        sxt.push({value : res.data[i].zx,name : '摄像头正常'},{value : res.data[i].lx,name : '摄像头异常'});

					}
                	if(res.data[i].sblx=='闸机'){
                        zj.push({value : res.data[i].zx,name : '闸机正常'},{value : res.data[i].lx,name : '闸机异常'});

					}
                	if(res.data[i].sblx=='门禁'){
                        mj.push({value : res.data[i].zx,name : '门禁正常'},{value : res.data[i].lx,name : '门禁异常'});

					}
                	if(res.data[i].sblx=='烟感'){
                        yg.push({value : res.data[i].zx,name : '烟感正常'},{value : res.data[i].lx,name : '烟感异常'});

					}

				}
                $("#sbxx").html(html);

                var deviceInfoDom = document.getElementById("deviceInfo");
                var deviceInfoCharts = echarts.init(deviceInfoDom);
                var option = {
                    tooltip : {
                        trigger : 'item',
                        formatter : "{a} <br/>{b}: {c} ({d}%)"
                    },
                    series : [ {
                        name : '设备情况',
                        type : 'pie', //环形图的type和饼图相同
                        center : [ '13%', '50%' ],
                        radius : [ '45%', '75%' ], //饼图的半径，第一个为内半径，第二个为外半径
                        avoidLabelOverlap : false,
                        hoverAnimation : false, // 经过不放大
                        color : [ '#23e6fa', '#474747' ],
                        label : {
                            normal : { //正常的样式
                                show : true,
                                position : 'center',
                                formatter:'',
                                color : '#fff',
                                fontSize : 12,
                                lineHeight : 20
                            },
                        },
                        itemStyle : {
                            show : false,
                            //borderColor:'#17acf6',
                            borderWidth : 1,
                        },
                        data : sxt
                    },
                        {
                            name : '设备情况',
                            type : 'pie', //环形图的type和饼图相同
                            center : [ '38%', '50%' ],
                            radius : [ '45%', '75%' ], //饼图的半径，第一个为内半径，第二个为外半径
                            avoidLabelOverlap : false,
                            hoverAnimation : false, // 经过不放大
                            color : [ '#fbe04b', '#474747' ],
                            label : {
                                normal : { //正常的样式
                                    show : true,
                                    position : 'center',
                                    formatter : "", // 中间显示的植
                                    color : '#fff',
                                    fontSize : 12,
                                    lineHeight : 20
                                },
                            },
                            itemStyle : {
                                show : false,
                                //borderColor:'#17acf6',
                                borderWidth : 1,
                            },
                            data : zj
                        },

                        {
                            name : '设备情况',
                            type : 'pie', //环形图的type和饼图相同
                            center : [ '62%', '50%' ],
                            radius : [ '45%', '75%' ], //饼图的半径，第一个为内半径，第二个为外半径
                            avoidLabelOverlap : false,
                            hoverAnimation : false, // 经过不放大
                            color : [ '#43e8be', '#474747' ],
                            label : {
                                normal : { //正常的样式
                                    show : true,
                                    position : 'center',
                                    formatter:'',
                                    color : '#fff',
                                    fontSize : 12,
                                    lineHeight : 20
                                },
                            },
                            itemStyle : {
                                show : false,
                                //borderColor:'#17acf6',
                                borderWidth : 1,
                            },
                            data : mj
                        },
                        {
                            name : '设备情况',
                            type : 'pie', //环形图的type和饼图相同
                            center : [ '88%', '50%' ],
                            radius : [ '45%', '75%' ], //饼图的半径，第一个为内半径，第二个为外半径
                            avoidLabelOverlap : false,
                            hoverAnimation : false, // 经过不放大
                            color : [ '#f9944c', '#474747' ],
                            label : {
                                normal : { //正常的样式
                                    show : true,
                                    position : 'center',
                                    formatter:'',
                                    color : '#fff',
                                    fontSize : 12,
                                    lineHeight : 20
                                },
                            },
                            itemStyle : {
                                show : false,
                                //borderColor:'#17acf6',
                                borderWidth : 1,
                            },
                            data : yg
                        }

                    ]
                };
                deviceInfoCharts.setOption(option);
                deviceInfoCharts.resize();
            }
        }
    })
}
//房屋信息
function getHouse(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "screen/api/rzl"+urlParams,
        success : function(res) {
            $("#fwxx").html("");
            if(res.code == 0){
                var str = '	<div class="layui-col-md6 layui-col-sm6 oh houseInfo">\n' +
                    '<h2 class="font_30 color_white numberFont right">'+res.data.zs+'</h2>\n' +
                    '<div class="txtC txt">\n' +
                    '<h5>\n' +
                    '<i class="iconfont iconfangwu color_blue font_18"></i>\n' +
                    '</h5>\n' +
                    '<h3 class="color_blue font_14">房屋总数</h3>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '<div class="layui-col-md6 layui-col-sm6 oh houseInfo">\n' +
                    '<h2 class="font_30 color_white numberFont right">'+res.data.rzl+'%</h2>\n' +
                    '<div class="txtC txt">\n' +
                    '<h5>\n' +
                    '<i class="iconfont icon80 color_blue font_18"></i>\n' +
                    '</h5>\n' +
                    '<h3 class="color_blue font_14">入住率</h3>\n' +
                    '</div>\n' +
                    '</div>'
                $("#fwxx").html(str);
            }
        }
    });
}
function getHouseCount(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    var houseStatisticsDom = document.getElementById("houseStatistics");
    var houseStatisticsCharts = echarts.init(houseStatisticsDom);
    //cmsOptions.object = houseStatisticsCharts;
    var seriesData = [];
    var titleData = [];
    $.ajax({
        url: featherCmsScript.ctx + "screen/api/tj" + urlParams,
        async:false,
        success: function (res) {
            if(res.code == 0){

                // var	 houseStatisticsCharts = featherCmsScript._optionsMapper["#houseStatistics"][0].object;
                // var options = houseStatisticsCharts.getOption();
                for (var i = 0; i < res.data.length; i++) {
                    seriesData.push(res.data[i].zs);
                    titleData.push(res.data[i].name);
                }
                // options.series[0].data = seriesData;
                // options.yAxis[0].data = titleData;
                // featherCmsScript._optionsMapper["#houseStatistics"][0].object.setOption(options);

            }

        }
    });

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
            data : titleData,
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
                        [ 'rgba(66,232,192,.9)', 'rgba(66,232,192,.1)' ],
                        [ 'rgba(67,173,233,.9)', 'rgba(67,173,233,.1)' ],
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
                    show : true,
                    position : 'right',
                    color : '#fff'
                }
            },
            data : seriesData
        } ]
    };
    houseStatisticsCharts.setOption(option);
}

//居民信息
function getPersonInfo(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "screen/api/getPersonInfo"+urlParams,
        success : function(res) {
            $("#jmxx").html("");
            $("#zdgzry").html("");
            $("#dyxx").html("");
            if (res.code == 0) {
                var str = '<div class="layui-col-md4 layui-col-sm4 txtC">'
                    + '	<h2 class="color_yellow font_30 numberFont">' + res.data[0].jmzs + '</h2>'
                    + '	<h5 class="color_blue font_14">居民总数</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 txtC">'
                    + '	<h2 class="color_yellow font_30 numberFont">' + res.data[0].czrk + '</h2>'
                    + '	<h5 class="color_blue font_14">常住人口</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 txtC">'
                    + '	<h2 class="color_yellow font_30 numberFont">' + res.data[0].wlrk + '</h2>'
                    + '	<h5 class="color_blue font_14">外来人口</h5>'
                    + '</div>';
                $("#jmxx").html(str);

                var str1 = '<div class="layui-col-md4 layui-col-sm4 oh mb_20">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].djry + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">独居人员</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 mb_20 oh">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].kclr + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">空巢老人</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 mb_20 oh">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].xmsf + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">刑满释放</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 oh">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].tyjr + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">退役军人</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 oh">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].dbry + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">低保人员</h5>'
                    + '</div>'
                    + '<div class="layui-col-md4 layui-col-sm4 oh">'
                    + '	<h2 class="color_white font_16 numberFont fr mr_10">' + res.data[0].cjry + '</h2>'
                    + '	<h5 class="color_navy_blue font_14">残疾人员</h5>'
                    + '</div>';
                $("#zdgzry").html(str1);

                //党员信息
                var str2 = '<div class="title">'
                    +'<a href="" class="txt color_white">党员信息</a>'
                    +'</div>'
                    +'<div class="layui-col-md4 layui-col-sm4 txtC">'
                    +'<h4 class="font_30 numberFont mh_10">'+ res.data[0].dzz +'</h4>'
                    +'<h5 class="color_blue font_14">党组织(个)</h5>'
                    +'<img class="dyzb_ico" src="images/dydb_ico1.png" alt="" />'
                    +'</div>'
                    +'<div class="layui-col-md4 layui-col-sm4 txtC">'
                    +'<h4 class="font_30 numberFont mh_10">' + res.data[0].dy + '</h4>'
                    +'<h5 class="color_blue font_14">党员(人)</h5>'
                    +'<img class="dyzb_ico" src="images/dydb_ico2.png" alt="" />'
                    +'</div>'
                    +'<div class="layui-col-md4 layui-col-sm4 txtC">'
                    +'<h4 class="font_30 numberFont mh_10">' + res.data[0].ybdy + '</h4>'
                    +'<h5 class="color_blue font_14">预备党员(人)</h5>'
                    +'<img class="dyzb_ico" src="images/dydb_ico3.png" alt="" />'
                    +'</div>';
                $("#dyxx").html(str2);
            }
        }
    });
}
//人员巡检
function getRwzsCount(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectXjrwCount"+urlParams,
        success : function(res) {
            $("#rwzs").html("");
            if (res.code == 0) {
                var str = '<h5 class="font_30 color_white numberFont">'+ res.data.rwzs +'</h5>'
                    +'<p class="font_14 color_blue">任务总数</p>';
                $("#rwzs").html(str);
            }
        }
    });
}
function getRyxjCount(sqid,xqid) {
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0

    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    var xdata=[];
    var legend_data=[];
    if(_this.startCharts){
        clearInterval(_this.startCharts);
    }

    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectRyxjCount"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                for(var i in res.data) {
                    legend_data[i]=res.data[i].XCZT
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].XCZT+""};
                    xdata.push(json);
                }
            }
        }
    })

    var ryxjEchart = echarts.init(document.getElementById('ryxjEchartPie'));
    //cmsOptions.object = ryxjEchart;
    //var legend_data = [ '未巡', '已巡检', '巡检中' ];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)"
        },
        legend : {
            //orient: 'vertical',
            right : '0',
            top : '0',
            // y: 'center',
            textStyle : {
                color : 'rgba(255, 255, 255, 1)',
                fontSize : '13',
                fontWeight : 'normal'
            },
            itemWidth : 10,
            itemHeight : 6,
            data : legend_data
        },
        series : [ {
            name : '巡检统计',
            type : 'pie',
            radius : [ '45%', '65%' ],
            center : [ '65%', '58%' ],
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
        color : [ '#1df5ac', '#f3a930', '#fee67c' ]
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

//根据选中社区和小区查询车位信息
function getZhsqCwEchartsData(name,sqId,xqId){  //小区名称、社区id、小区id
    $.ajax({
        async: false,
        url: featherCmsScript.ctx + 'screen/api/getSqCwXx?name='+name+'&sqid='+sqId+'&xqid='+xqId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
        	debugger;
        	var cwData=data.data;
            var cwkx=[cwData.allKxCount,cwData.allDsKxCount,cwData.allDxKxCount];
        	var cwzy=[cwData.allZyCount,cwData.allDsZyCount,cwData.allDxZyCount];
            cwEcharts(cwkx,cwzy);
        }
    });
}
//赋值加载首页车位图表数据
function cwEcharts(cwkx,cwzy) {
    var ParkingInfoDom = document.getElementById("ParkingInfo");
    var ParkingInfoCharts = echarts.init(ParkingInfoDom);
    var option = {
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'cross',
                label : {
                    backgroundColor : '#6a7985'
                }
            }
        },
        legend : {
            top : '-5',
            right : '10%',
            textStyle : {
                color : 'rgba(255, 255, 255, 1)',
                fontSize : '12',
                fontWeight : 'normal'
            },
            itemWidth : 10,
            itemHeight : 6,
            data : [ '已用', '空置' ]
        },
        grid : {
            left : '5%',
            right : '12%',
            bottom : '4%',
            containLabel : true,
            height : '80%'
        },
        xAxis : [ {
            type : 'category',
            boundaryGap : true,
            data : [ '全部车位', '地上车位', '地下车位' ],
            axisLabel : {
                show : true,
                textStyle : {
                    color : "#fff"
                }
            },
            axisLine : {
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
        } ],
        yAxis : [ {
            type : 'value',
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
                    color : "rgba(255, 255, 255, .6)"
                }
            },
            axisLine : {
                lineStyle : {
                    type : 'solid',
                    color : 'rgba(255, 255, 255, 0)'
                }
            },
        } ],
        series : [ {
            name : '已用',
            type : 'bar',
            barMaxWidth : 14,
            areaStyle : {},
            data : cwkx,
            label : {
                normal : {
                    show : true,
                    position : 'top',
                    color : 'rgba(255,255,255,.6',
                    fontSize : '12',
                }
            },
            itemStyle : {
                normal : {
                    color : new echarts.graphic.LinearGradient(
                        0, 0, 0, 1, [ {
                            offset : 1,
                            color : 'rgba(254,230,124,0.1)'
                        },
                            {
                                offset : 0.5,
                                color : 'rgba(254,230,124,0.4)'
                            },
                            {
                                offset : 0,
                                color : '#fee67c'
                            }
                        ]
                    )
                }
            }
        },
            {
                name : '空置',
                type : 'bar',
                barMaxWidth : 14,
                areaStyle : {},
                data : cwzy,
                label : {
                    normal : {
                        show : true,
                        position : 'top',
                        color : 'rgba(255,255,255,.6',
                        fontSize : '12',
                    }
                },
                itemStyle : {
                    normal : {
                        color : new echarts.graphic.LinearGradient(
                            0, 0, 0, 1, [ {
                                offset : 1,
                                color : 'rgba(37,233,193,0.1)'
                            },
                                {
                                    offset : 0.5,
                                    color : 'rgba(37,233,193,0.4)'
                                },
                                {
                                    offset : 0,
                                    color : '#43e9c1'
                                }
                            ]
                        )
                    }
                }
            }
        ],
    };
    ParkingInfoCharts.setOption(option);
}



