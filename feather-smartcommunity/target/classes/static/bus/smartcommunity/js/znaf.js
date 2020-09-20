/* 首页 */

var sqid='SQ000001';
var xqid='';
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
            if(treeNode.pId==null || treeNode.pId==0){
            	sqid=treeNode.id;
            	xqid="";
			}else{
                sqid="";
                xqid=treeNode.id;
			}
			getSxtList("出口入口");
            sbtj();
            mjList();
            znafYcList();
            mjCount();
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
			if(featherCmsScript._map.geometryEntityArr.length > 0){
				featherCmsScript._map.addLayerPic(featherCmsScript._map.type,featherCmsScript._map.typeObject)
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

// 报警处置
featherCmsScript.register({
	element : "#znaf_bjzs",
	onLoad : function(cmsOptions) {
		var _this = this
		var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
		var charPie3currentIndex = 0

		var ryxjEchart = echarts.init(document.getElementById('znaf_bjzs'));
		cmsOptions.object = ryxjEchart;
		var legend_data = [ '已处置', '未处置', '处置中' ];
		var option = {
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b}: {c} ({d}%)"
			},
			series : [ {
				name : '进出人员占比统计',
				type : 'pie',
				radius : [ '55%', '75%' ],
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
				data : [ {
					value : 10,
					name : '已处置'
				},
					{
						value : 25,
						name : '未处置'
					},
					{
						value : 85,
						name : '处置中'
					}
				]
			} ],
			color : [ '#3fdc97', '#ed8641', '#fee67c' ]
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
				_this.startCharts = setInterval(chartHover, 2000)
				isSet = true
			}
		});
	},
	onResize : function(cmsOptions) {
		cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});


// 视频按钮点击事件
featherCmsScript.register({
	element : "#videoOpenBtn",
	onClick : function(cmsOptions, _this) {
		//$(_this).addClass('active').siblings('li').removeClass('active');
		$('.znaf_VideoList').removeClass('hide');
		$('.znaf_gateAccessList,.znaf_gateList,.znaf_roadPoleList').addClass('hide');
		$('.rightContent').removeClass('CloseRight');
		layer.closeAll();
	}
});

// 门禁按钮点击事件
featherCmsScript.register({
	element : "#gateAccessOpenBtn",
	onClick : function(cmsOptions, _this) {
		//$(_this).addClass('active').siblings('li').removeClass('active');
		$('.znaf_gateAccessList').removeClass('hide');
		$('.znaf_VideoList,.znaf_gateList,.znaf_roadPoleList').addClass('hide');
		$('.rightContent').removeClass('CloseRight');
		layer.closeAll();
	}
});

// 闸机按钮点击事件
featherCmsScript.register({
	element : "#gateListOpenBtn",
	onClick : function(cmsOptions, _this) {
		//$(_this).addClass('active').siblings('li').removeClass('active');
		$('.znaf_gateList').removeClass('hide');
		$('.znaf_VideoList,.znaf_gateAccessList,.znaf_roadPoleList').addClass('hide');
		$('.rightContent').removeClass('CloseRight');
		layer.closeAll();
	}
});

// 道杆按钮点击事件
featherCmsScript.register({
	element : "#roadPoleOpenBtn",
	onClick : function(cmsOptions, _this) {
		//$(_this).addClass('active').siblings('li').removeClass('active');
		$('.znaf_roadPoleList').removeClass('hide');
		$('.znaf_VideoList,.znaf_gateList,.znaf_gateAccessList').addClass('hide');
		$('.rightContent').removeClass('CloseRight');
		layer.closeAll();
	}
});

// 左下角内容滚动效果
featherCmsScript.register({
	element : ".znafNewsContainer",
	onLoad : function(cmsOptions) {
		//投诉建议向上滚动效果
		var SuggestionsSwiper = new Swiper('.znafNewsContainer', {
			slidesPerView : 5,
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

function sbtj() {
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api//getCountSb?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                var html='';
                $("#sbtj").html("");
                for(var i in res.data){
                    if(res.data[i].sblx=='摄像头'){
                        html+=`<li>视频 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }else if(res.data[i].sblx=='门禁'){
                        html+=`<li>门禁 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }else if(res.data[i].sblx=='闸机'){
                        html+=`<li>闸机 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }else if(res.data[i].sblx=='车辆道杆'){
                        html+=`<li>道杆 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }
                }
                $("#sbtj").html(html);
            }
        }
    })
}

//设备统计
featherCmsScript.register({
	element : "#sbtj",
	onLoad : function(cmsOptions) {
        sbtj();
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
	}
});
function znafYcList() {
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getYcList?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                var html='';
                $("#znafYcList").html("");
                for(var i in res.data){
                    html+=`<li class="swiper-slide" onclick="featherCmsScript._map.otherClick(\``+ res.data[i].YCID +`\`,'YC','index')">
							<span>[异常行为]</span>`+res.data[i].YCSJ+` `+res.data[i].YCNR+`
						</li>`;
                }
                $("#znafYcList").html(html);
            }
            var SuggestionsSwiper = new Swiper('.znafNewsContainer', {
                slidesPerView : 5,
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
            };
            //鼠标离开开始自动切换
            SuggestionsSwiper.el.onmouseout = function() {
                SuggestionsSwiper.autoplay.start();
            }
        }
    })
}

//异常列表
featherCmsScript.register({
	element : "#znafYcList",
	onLoad : function(cmsOptions) {
        znafYcList();
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
	}
});

function mjCount() {
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getCountMj?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                var html='';
                $("#mjCount").html("");
                for(var i in res.data){
                    if(res.data[i].jczt=='进入'){
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">进入人数</h3>
								</div>
							</div>`;
                    }else{
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">外出人数</h3>
								</div>
							</div>`;
                    }
                }
                $("#mjCount").html(html);
            }
        }
    })
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getCountZj?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                var html='';
                $("#zjCount").html("");
                for(var i in res.data){
                    if(res.data[i].jczt=='进入'){
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">进入人数</h3>
								</div>
							</div>`;
                    }else{
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">外出人数</h3>
								</div>
							</div>`;
                    }
                }
                $("#zjCount").html(html);
            }
        }
    })
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getCountDg?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                var html='';
                $("#dgCount").html("");
                for(var i in res.data){
                    if(res.data[i].jczt=='进入'){
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">进入人数</h3>
								</div>
							</div>`;
                    }else{
                        html+=`	<div class="layui-col-md6 layui-col-sm6 oh houseInfo mb_10">
								<h2 class="font_30 color_white numberFont right">`+res.data[i].num+`</h2>
								<div class="txtC txt">
									<h5><i class="iconfont iconwaichurenyuan1 color_blue font_22"></i></h5>
									<h3 class="color_blue font_14">外出人数</h3>
								</div>
							</div>`;
                    }
                }
                $("#dgCount").html(html);
            }
        }
    })
}

//门禁统计
featherCmsScript.register({
	element : "#mjCount",
	onLoad : function(cmsOptions) {
        mjCount();
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {

	}
});
//门禁列表
function mjList(){
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getMjjcList?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'mjPage',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('mjList').innerHTML = function () {
                                var arr = [];

                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {

                                    html1 += `<li class="" id="gateAccessLayerOpen" onclick="mjBox('MJ','`+item.MJID+`','MJ')">
														<span class="number pa numberFont">` + (parseInt(index) + 1) + `</span>
														<img class="img" src="images/personneInfo_img.png"/>
														<span class="btn">` + item.JCZT + `</span>
														<p>姓名：<span>` + item.XM + `</span></p>
														<p>进入时间：<span>` + item.JCSJ + `</span></p>
														<p>抓拍地点：<span>小区入口</span></p>
												   </li>`;
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
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getZjjcList?sqid="+sqid+"&xqid="+xqid,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'zjPage',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('zjList').innerHTML = function () {
                                var arr = [];

                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {

                                    html1 += `<li class="" id="gateAccessList" onclick="mjBox('ZJ','`+item.ZJID+`','ZJ')">
														<span class="number pa numberFont">` + (parseInt(index) + 1) + `</span>
														<img class="img" src="images/personneInfo_img.png"/>
														<span class="btn">` + item.JCZT + `</span>
														<p>姓名：<span>` + item.XM + `</span></p>
														<p>进入时间：<span>` + item.JCSJ + `</span></p>
														<p>抓拍地点：<span>小区入口</span></p>
												   </li>`;
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
    $.ajax({
        url:featherCmsScript.ctx + "znaf/api/getDgjcList",
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'dgPage',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('dgList').innerHTML = function () {
                                var arr = [];

                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {

                                    html1 += `<li class="" id="gateAccessList" onclick="mjBox('DG','`+item.DGID+`','DG')">
														<span class="number pa numberFont">` + (parseInt(index) + 1) + `</span>
														<img class="img" src="images/personneInfo_img.png"/>
														<span class="btn">` + item.JCZT + `</span>
														<p>姓名：<span>` + item.CZXM + `</span></p>
														<p>进入时间：<span>` + item.JCSJ + `</span></p>
														<p>抓拍地点：<span>小区入口</span></p>
												   </li>`;
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

}



featherCmsScript.register({
	element : "#mjList",
	onLoad : function(cmsOptions) {
		mjList();
	},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {

	}
});
function mjBox(type,id,name) {
	var img;
	if(name != undefined){
		if (type == 'SXT') {
			img = 'bus/smartcommunity/img/camera.png';
		} else if (type == 'MJ') {
			img = 'bus/smartcommunity/img/door.png';
		} else if (type == 'ZJ') {
			img = 'bus/smartcommunity/img/zj.png';
		} else if (type == 'DG') {
			img = 'bus/smartcommunity/img/dg.png';
		} else if (type == 'LJT') {
			img = 'bus/smartcommunity/img/ljt.png';
		} else if (type == 'YG') {
			img = 'bus/smartcommunity/img/smoke.png';
		} else if (type == 'CDZ') {
			img = 'bus/smartcommunity/img/cdz.png';
		} else if (type == 'LD') {
			img = 'bus/smartcommunity/img/light.png';
		} else if (type == 'XFS') {
			img = 'bus/smartcommunity/img/xfs.png';
		} else if (type == 'YC') {
			img = 'bus/smartcommunity/img/warn.png';
		}
	}

	$.ajax({
		url: featherCmsScript.ctx + "screen/api/getSbJc?type="+type+"&id="+id,
		success: function (res) {
			if (res.code == 0) {
				if(type=='SXT'){
                    var html='';
                    $("#sxtTc").html('');
                    if(img != undefined){
                        featherCmsScript._map.localPosition(id,type);
                        featherCmsScript._map.replacePic(img,id);
                    }
                    for(var i in res.data){
                    	html+=`<div class="layui-row mw_10">
					    <div class="layui-col-md8 layui-col-xs8">
					      	<span class="color_blue font_14 mr_10">位置:</span>
					      	<span class="color_white font_14">`+res.data[i].WZ+`</span>
					    </div>
					    <div class="layui-col-md4 layui-col-xs4 txtR">
					      	<span class="color_blue font_14 mr_10">状态:</span>
					      	<span class="color_white font_14">`+res.data[i].SBZT+`</span>
					    </div>
					</div>
					<div  class="cameraImg">
						<img src="images/video_img1.png" alt="" />
					</div>`;
					}
                    $("#sxtTc").html(html);

				}else{
                    var html='';
                    if(img != undefined){
                        featherCmsScript._map.localPosition(id,type);
                        featherCmsScript._map.replacePic(img,id);
                    }
                    $("#znafGateAccessLayer").html('');
                    html+=`<h4 class="moveTxt">`+res.data[0].SBMC+`</h4>
				<div class="layerContent">
					<div id="znafSwiperWrapper" class="swiperWrapper">
						<ul class="vehicleList swiper-wrapper">`
                    for(var i in res.data){
                        html+=`<li class="swiper-slide">
								<span class="btn TipBtn pa">`+res.data[i].JCZT+`</span>
								<img class="img" src="images/video_img1.png" alt="" />
								<p>
									<span class="color_blue">姓名：</span>
									<span class="color_white">`+res.data[i].XM+`</span>
								</p>
								<p>
									<span class="color_blue">抓拍时间：</span>
									<span class="color_white">`+res.data[i].JCSJ+`</span>
								</p>
								<p>
									<span class="color_blue">抓拍位置：</span>
									<span class="color_white">`+res.data[i].WZ+`</span>
								</p>
								<p>
									<span class="color_blue">住址：</span>
									<span class="color_white">`+res.data[i].ZZ+`</span>
								</p>
								<p>
									<span class="color_blue">标签：</span>
									<span class="color_white"></span>
								</p>
							</li>`;
                    }
                    html+=`</ul><div class="swiper-button-next"></div>
						<div class="swiper-button-prev"></div>
					</div>
				</div>`;
                    $("#znafGateAccessLayer").html(html);
                    var mjSwiper = new Swiper('#znafSwiperWrapper', {
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
				}





			}
		}
	})
  if(type=='SXT'){
      layer.open({
          type : 1,
          title : false,
          area : [ '400px', 'auto' ],
          move : '.moveTxt',
          scrollbar : false,
          shade : 0,
          //fix: false,
          content : $('#cameraLayer'),
          skin : 'layer-style',
          id : 'cameraLayerBox'
      });
  }else{
      layer.open({
          type : 1,
          title : false,
          area : [ '400px', 'auto' ],
          move : '.moveTxt',
          scrollbar : false,
          shade : 0,
          //fix: false,
          content : $('#znafGateAccessLayer'),
          skin : 'layer-style',
          id : 'cameraLayerBox'
      });
  }


}
// 摄像头列表
featherCmsScript.register({
    element: "#gateAccessLayerOpen",
    onLoad: function (cmsOptions) {
        getSxtList("出口入口",sqid,"");
    },
    onResize: function (cmsOptions) {
    },
    onClick: function (cmsOptions, _this) {
    }
})

function getSxtList(wzlx){
    $.ajax({
        url: featherCmsScript.ctx + "znaf/api/getSxtList?wzlx="+wzlx+"&sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
        	if(res.code==0){
        		if(wzlx=='出口入口'){
        			var html='';
                    $("#zdList").html('');
        			$("#crList").html('');
        			for(var i in res.data){
        				html+=`<li id="VideoListLayerOpen" onclick="mjBox('SXT','`+res.data[i].SXTID+`','index')">
												<div class="oh videoTxt">
													<p class="fl">`+res.data[i].SBMC+`</p>
													<p class="fl">2020/02/24 15:24:12</p>
												</div>
												<img src="images/video_img1.png" alt="" />
											</li>`;
					}
                    $("#crList").html(html);
				}else{
                    var html='';
                    $("#zdList").html('');
                    for(var i in res.data){
                        html+=`<li id="VideoListLayerOpen" onclick="mjBox('SXT','`+res.data[i].SXTID+`','index')">
												<div class="oh videoTxt">
													<p class="fl">`+res.data[i].SBMC+`</p>
													<p class="fl">2020/02/24 15:24:12</p>
												</div>
												<img src="images/video_img1.png" alt="" />
											</li>`;
                    }
                    $("#zdList").html(html);
				}
			}
        }
    })
}

// 视频弹框
featherCmsScript.register({
	element : "#VideoListLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {

	}
});

// 门禁左右切换
featherCmsScript.register({
	element : "#znafSwiperWrapper",
	onLoad : function(cmsOptions) {
		var mjSwiper = new Swiper('#znafSwiperWrapper', {
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
});

// 门禁弹框
featherCmsScript.register({
	element : "#gateAccessLayerOpen",
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
			content : $('#znafGateAccessLayer'),
			skin : 'layer-style',
			id : 'cameraLayerBox'
		});
	}
});

// 点击左侧内容右侧显示
featherCmsScript.register({
	element : ".rightCloseBtn",
	onClick : function(cmsOptions, _this) {
		$('.rightContent').addClass('CloseRight');
	}
});

