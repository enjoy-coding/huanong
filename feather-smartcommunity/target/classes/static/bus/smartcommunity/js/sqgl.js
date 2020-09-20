/* 首页 */
var sqid = 'SQ000001';
var xqid = '';
var sffzS = '常驻人口';
var sffzF = '外来人口';
var sffzK = '';
var searchType='';
var type1="";
var cllx1='0';
var rz = '入住';
var kz = '空置';
var pageNum = 1 ;
var pageSize = 10;
var pageTccCount = '';//空闲页面总条数
var pageKxCount = '';//空闲页面总条数
var pageYyCount = '';//空闲页面总条数
var pageLimitStart = 0 ; //起始条数
layui.use([ 'layer', 'element','laypage','table'], function() { //独立版的layer无需执行这一句
	var $ = layui.jquery,
		element = layui.element,
		laypage = layui.laypage,
		table = layui.table,
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
		   ,count: 20 //数据总数
		});
        //总页数低于页码总数
        laypage.render({
            elem: 'demo33'
            ,count: 20 //数据总数
        });
		//总页数低于页码总数
		laypage.render({
		   elem: 'demo4'
		   ,count: 50 //数据总数
		});
});

featherCmsScript.register({
	element : "",
	onLoad : function(cmsOptions) {
        $.ajax({
            url:featherCmsScript.ctx + 'zhzl/api/getTree',
            ascnc:false,
            success:function(res){
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
                //社区管理人员统计
                sqid = treeNode.id;
                xqid = '';
                selectZcryCount(treeNode.id,'');
				//年龄结构
                getNlCount(treeNode.id,'','');
				//男女比例
                getNnBlCount(treeNode.id,'','');
				//民族比例
                getMzBlCount(treeNode.id,'','');
                //房屋
                selectZcfwCount(treeNode.id,'')
                selectZcfwZtCount(treeNode.id,'')

                //设备信息
                getSbCountList(treeNode.id,'');
                sqglCw.getSqglCwInfo(treeNode.id,'');
                cltj(treeNode.id,'');
            }else{
                sqid = '';
                xqid = treeNode.id;
                //社区管理人员统计
                selectZcryCount('',treeNode.id);
                //年龄结构
                getNlCount('',treeNode.id,'');
                //男女比例
                getNnBlCount('',treeNode.id,'');
                //民族比例
                getMzBlCount('',treeNode.id,'');
                //房屋
                selectZcfwCount('',treeNode.id)
                selectZcfwZtCount('',treeNode.id);

                //设备信息
                getSbCountList('',treeNode.id);
                sqglCw.getSqglCwInfo('',treeNode.id);
                cltj('',treeNode.id);

            }
            layer.closeAll();
            hiddenRightbox();
		},
	}
};

function hiddenRightbox(){
    $('.rightContent').addClass('CloseRight');
    $('.zhzlBottomContent').removeClass('leftShrink');
    featherCmsScript.trigger("resize");
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

//左侧人员
featherCmsScript.register({
    element : "#zcry",
    onLoad : function(cmsOptions) {
        selectZcryCount('SQ000001','')
    },
    onResize : function(cmsOptions) {},
    onClick : function(cmsOptions, _this) {
    }
});

//左侧房屋
featherCmsScript.register({
    element : "#zcfw",
    onLoad : function(cmsOptions) {
        selectZcfwCount('SQ000001','')
    },
    onResize : function(cmsOptions) {},
    onClick : function(cmsOptions, _this) {
    }
});

function selectZcryCount(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/selectZcryCount"+urlParams,
        success : function(res) {
            $("#zcry").html("");
            if (res.code == 0) {
                var str = '<dl class="total" onclick="getRyCount(sqid,xqid,sffzK)">'
                    +'<dt><img src="images/sqgl_icon1.png" alt="" /></dt>'
                    +'<dd>'
                    +'<h5 class="numberFont font_30 color_white">'+res.data.zrs+'</h5>'
                    +'<h6 class="color_yellow font_14">人员人数</h6>'
                    +'</dd>'
                    +'</dl>'
                    +'<ul class="list">'
                    +'<li id="permanentPersonnelBtn" onclick="getRyCount(sqid,xqid,sffzS)">'
                    +'常驻人数 <span class="number font_22 color_navy_blue numberFont">'+res.data.czrs+'</span>'
                    +'</li>'
                    +'<li onclick="getRyCount(sqid,xqid,sffzF)">'
                    +'流动人数 <span class="number font_22 color_navy_blue numberFont">'+res.data.ldrs+'</span>'
                    +'</li>'
                    +'</ul>';
                $("#zcry").html(str);
            }
        }
    })
}


function selectZcfwCount(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/selectZcfwCount"+urlParams,
        success : function(res) {
            $("#fwzs").html("");
            $("#rzl").html("");
            $("#fwrzqk").html("");
            $("#fwsyyt").html("");
            if (res.code == 0) {
                var str1 = '<dl class="total" onclick="getLdCount(sqid,xqid)">'
                    +'<dt><img src="images/topMenuIco_02.png" alt="" /></dt>'
                    +'<dd>'
                    +'<h5 class="numberFont font_30 color_white">'+res.data.lds+'</h5>'
                    +'<h6 class="color_yellow font_14">楼栋数</h6>'
                    +'</dd>'
                    +'</dl>'
                $("#fwzs").html(str1);

                var str2 ='<ul class="list">'
                    +'<li id="houseOpenlist" onclick="getFwCount(sqid,xqid,sffzK,\'\')">'
                    +'房屋总数 <span class="number font_22 color_navy_blue numberFont">'+res.data.fwzs+'</span>'
                    +'</li>'
                    +'<li>'
                    +'入住率 <span class="number font_22 color_navy_blue numberFont">'+res.data.rzl+'%</span>'
                    +'</li>'
                    +'</ul>'
                $("#rzl").html(str2);

                var str3 ='<ul class="list">'
                    +'<li onclick="getFwCount(sqid,xqid,rz,\'\')">'
                    +'入住 <span class="number font_22 color_navy_blue numberFont">'+res.data.rz+'</span>'
                    +'</li>'
                    +'<li onclick="getFwCount(sqid,xqid,kz,\'\')">'
                    +'空置 <span class="number font_22 color_navy_blue numberFont">'+res.data.kz+'</span>'
                    +'</li>'
                    +'</ul>'
                $("#fwrzqk").html(str3);

                var str4 = '<div class="znaf_bjtj">'
                    +' <p class="oh" onclick="getFwCount(sqid,xqid,\'\',\'商业\')">商业<span class="fr numberFont color_navy_blue font_22">'+res.data.sy+'</span></p>'
                    +'<div class="layui-progress layuiProgress">'
                    +'<div class="layui-progress-bar layui-bg-green" lay-percent="'+res.data.syrzl+'%"></div>'
                    +'<div class="layui-progress-text">'+res.data.syrzl+'%</div>'
                    +'</div>'
                    +' <p class="oh" onclick="getFwCount(sqid,xqid,\'\',\'住宅\')">住宅<span class="fr numberFont color_navy_blue font_22">'+res.data.zz+'</span></p>'
                    +'<div class="layui-progress layuiProgress">'
                    +'<div class="layui-progress-bar layui-bg-orange" lay-percent="'+res.data.zzrzl+'%"></div>'
                    +'<div class="layui-progress-text">'+res.data.zzrzl+'%</div>'
                    +' </div>'
                    +' <p class="oh" onclick="getFwCount(sqid,xqid,\'\',\'其他\')">其它<span class="fr numberFont color_navy_blue font_22">'+res.data.qt+'</span></p>'
                    +'<div class="layui-progress layuiProgress">'
                    +'<div class="layui-progress-bar layui-bg-red" lay-percent="'+res.data.qtrzl+'%"></div>'
                    +'<div class="layui-progress-text">'+res.data.qtrzl+'%</div>'
                    +'</div>'
                    +'</div>'
                $("#fwsyyt").html(str4);
                layui.use([ 'element'], function() { //独立版的layer无需执行这一句
                    var element = layui.element;
                    element.init()
                });

            }
        }
    })
}

// 年龄结构
featherCmsScript.register({
	element : "#ageStructure",
	onLoad : function(cmsOptions) {
        getNlCount("SQ000001","","")
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getNlCount(sqid,xqid,sfcz){
    var houseStatisticsDom = document.getElementById("ageStructure");
    var houseStatisticsCharts = echarts.init(houseStatisticsDom);
    //cmsOptions.object = houseStatisticsCharts;
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfcz="+sfcz;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/getNlCount"+urlParams,
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
            data : [ '20以下', '20-30', '31-40','40-50', '51-60', '60以上' ],
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

// 男女比例
featherCmsScript.register({
	element : "#lNScale",
	onLoad : function(cmsOptions) {
        getNnBlCount('SQ000001','','')
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getNnBlCount(sqid,xqid,sfcz){
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfcz="+sfcz;
    var xdata=[];
    if(_this.startCharts){
        clearInterval(_this.startCharts);
    }
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/getNnBlCount"+urlParams,
        async:false,
        success : function(res) {
            $("#sqglnnbi").html("");
            if (res.code == 0) {
                var html='';
                html += '<p class="woman"><i class="iconfont iconwomen"></i>' + res.data[0].XB + ' <span class="fr color_white numberFont font_30">' + res.data[0].MUN + '</span></p>\n' +
                    '<p class="color_blue man"><i class="iconfont iconnancesuo"></i>' + res.data[1].XB + '  <span class="fr color_white numberFont font_30">' + res.data[1].MUN + '</span></p>'
                for(var i in res.data) {
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].XB+""};
                    xdata.push(json);
                }
                $("#sqglnnbi").html(html);
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
            clearInterval(_this.startCharts)
            _this.startCharts = setInterval(chartHover, 2000)
            isSet = true
        }
    });
}

// 民族比例
featherCmsScript.register({
	element : "#MZScale",
	onLoad : function(cmsOptions) {
        getMzBlCount('SQ000001','','');
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function getMzBlCount(sqid,xqid,sfcz){
    var ryxjEchart = echarts.init(document.getElementById('MZScale'));
    //cmsOptions.object = ryxjEchart;
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfcz="+sfcz;
    var xdata=[];
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/getMzBlCount"+urlParams,
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

//搜索人员房屋列表
function searchLB(lb) {
    if (lb == 'RY'){
        var ry = $("#searchRy").val();
        getRyList(sqid,xqid,"",ry)
    } else if (lb == 'FW'){
        var fw = $("#searchFw").val();
        getFwList(sqid,xqid,"","",fw)
    }else if (lb == 'LD'){
        var ld = $("#searchLd").val();
        getLdList(sqid,xqid,ld)
    }
};

function getRyList(sqid,xqid,sfcz,ry) {
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfcz="+sfcz+"&ry="+ry;
    $.ajax({
        url:featherCmsScript.ctx + "sqgl/api/getRyList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'sqryPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('sqryList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    var a = "";
                                    var sfdj = item.SFDJ;
                                    var sfkc = item.SFKC;
                                    var sfxmsf = item.SFXMSF;
                                    var sftyjr = item.SFTYJR;
                                    var sfdb = item.SFDB;
                                    var sfcj = item.SFCJ;
                                    if (sfdj=="是"){
                                        sfdj="独居人员"
                                    }else {
                                        sfdj=""
                                    }
                                    if (sfkc=="是"){
                                        sfkc="空巢老人"
                                    }else {
                                        sfkc=""
                                    }
                                    if (sfxmsf=="是"){
                                        sfxmsf="刑满释放人员"
                                    }else {
                                        sfxmsf=""
                                    }
                                    if (sftyjr=="是"){
                                        sftyjr="退役军人"
                                    }else {
                                        sftyjr=""
                                    }
                                    if (sfdb=="是"){
                                        sfdb="低保人员"
                                    }else {
                                        sfdb=""
                                    }
                                    if (sfcj=="是"){
                                        sfcj="残疾人"
                                    }else {
                                        sfcj=""
                                    }
                                    a=sfdj+" "+sfkc+" "+sfxmsf+" "+sftyjr+" "+sfdb+" "+sfcj;
                                    html1 += `<div class="layui-row colum" id="personnelInfoLayerOpen" onclick="getRyTk('`+ (parseInt(index) + 1) +`','`+item.JMID+`')">
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span>`+ (parseInt(index) + 1) +`</span>
                                        </div>
                                        <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                        <span title="">` + item.XM + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span title="">` + item.XB +`</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span title="">` + item.NL + `</span>
                                        </div>
                                        <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                        <span title="">` + a + `</span>
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
}


function getFwList(sqid,xqid,fwrzqk,fwlylx,fw) {
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&fwrzqk="+fwrzqk+"&fwlylx="+fwlylx+"&fw="+fw;
    $.ajax({
        url:featherCmsScript.ctx + "sqgl/api/getFwList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'sqfwPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('sqfwList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html1 += `<div class="layui-row colum" id="fwxxLayerOpen" onclick="getFwTk('`+ (parseInt(index) + 1) +`','`+item.FWID+`','');featherCmsScript._map.otherClick('`+item.LDID+`','RY','sqgl');">
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>`+ (parseInt(index) + 1) +`</span>
                                        </div>
                                        <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                            <span title="">` + item.LDMC + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">` + item.MPH + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">` + item.FWRZQK + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">` + item.FWLYLX + `</span>
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
}

function getLdList(sqid,xqid,ld) {
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&ld="+ld;
    $.ajax({
        url:featherCmsScript.ctx + "sqgl/api/getLdList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'sqldPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('sqldList').innerHTML = function () {
                                var arr = [];
                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html1 += `<div class="layui-row colum" id="ldxxLayerOpen" onclick="getLdTk('`+ (parseInt(index) + 1) +`','','`+item.LDID+`');featherCmsScript._map.otherClick('`+item.LDID+`','FW','sqgl');">
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>`+ (parseInt(index) + 1) +`</span>
                                        </div>
                                        <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                            <span title="">` + item.LDMC + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">` + item.CS + `</span>
                                        </div>
                                        <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                            <span title="">` + item.JZWMJ + `</span>
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
}

//点击左侧人员切换图表和右侧
function getRyCount(sqid,xqid,sfcz){
    //年龄结构
    getNlCount(sqid,xqid,sfcz);
    //男女比例
    getNnBlCount(sqid,xqid,sfcz);
    //民族比例
    getMzBlCount(sqid,xqid,sfcz);
    //人员列表
    getRyList(sqid,xqid,sfcz,'')

    //人员列表展开
    //		$(_this).addClass('active');
    $('.rightContent').removeClass('CloseRight');
    $('.zhzlBottomContent').addClass('leftShrink');
    featherCmsScript.trigger("resize");
    $('.ryxxBox').removeClass('hide');
    $('.fwxxBox,.carBox,.parkingSpaceBox,.deviceBox').addClass('hide');
    featherCmsScript._map.clearPic();

}


//点击左侧房屋切换右侧
function getFwCount(sqid,xqid,fwrzqk,fwlylx){
    //		$(_this).addClass('active');
    $('.rightContent').removeClass('CloseRight');
    $('.fwxxBox').removeClass('hide');
    $('.ryxxBox,.carBox,.parkingSpaceBox,.deviceBox,.ldxxBox').addClass('hide');
    //房屋列表
    $('#fwxxLayerOpen').html("");
    getFwList(sqid,xqid,fwrzqk,fwlylx,'')
    featherCmsScript._map.clearPic();

}
//点击左侧楼栋切换右侧
function getLdCount(sqid,xqid){
    $('.rightContent').removeClass('CloseRight');
    $('.ldxxBox').removeClass('hide');
    $('.ryxxBox,.carBox,.parkingSpaceBox,.deviceBox,.fwxxBox').addClass('hide');
    //楼栋列表
    $('#ldxxLayerOpen').html("");
    getLdList(sqid,xqid,'')
    featherCmsScript._map.clearPic();

}

//人员弹框
function getRyTk(index,jmid){
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getRyInfo?jmid="+jmid,
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
                                  <p><span class="color_blue">电话：</span><span class="color_white">`+res.data.DH+`</span></p>
                                </div>
                                <div class="layui-col-md12">
                                <p><span class="color_blue">标签：</span><span class="color_white">`

                                if (res.data.SFDJ == '是') {
                                    html += `<i class="iconfont iconlaoren color_yellow"></i>`;
                                }
                                if (res.data.SFKC == '是') {
                                    html += `<i class="iconfont iconlaoren color_red"></i>`;
                                }
                                if (res.data.SFDB == '是') {
                                    html += `<i class="iconfont iconrenyuan-shi color_blue"></i>`;
                                }
                                if (res.data.SFCJ == '是') {
                                    html += `<i class="iconfont iconcanjiren color_yellow"></i>`;
                                }
                                if (res.data.SFTYJR == '是') {
                                    html += `<i class="iconfont iconjunren color_green"></i>`;
                                }
                                if (res.data.SFXMSF == '是') {
                                    html += `<i class="iconfont iconren color_red"></i>`;
                                }

                                html += `</span></p>
                                           </div>
                                        </div>
                                        </li>
                                        </ul>
                                        </div>`;

                    $("#personnelInfoLayer").html(html);
                featherCmsScript._map.otherClick(res.data.LDID,'FW','sqgl');
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
        id : 'personnelInfoLayerOpenBox'
    });
}

//房屋弹框
function getFwTk(index,fwid,ldid){
    if(fwid != ""){
        featherCmsScript._map.fwid = fwid;
    }
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getFwInfo?fwid="+fwid+"&ldid="+ldid,
        success: function (res) {
            if (res.code == 0 && res.data.mun!=0) {
                var html = '';
                var html1 = '';
                $("#fwzz").html('');
                $("#zhxx").html('');
                html1+=
                    `<h4 class="moveTxt">`+res.data.dz+`(`+res.data.mun+`人)</h4>`

                $("#fwzz").html(html1);
                for(var i in res.data.fwInfo){
                    //sum+=res.data[i].num;

                    html+=`<li>`
                        var age = Number(res.data.fwInfo[i].NL);
                        if(res.data.fwInfo[i].XB == '男'){
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
                    html += `<div class="layui-row">
							    <div class="layui-col-md6">
							    	<p>
							    		<span class="color_white">姓名:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].XM+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">民族:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].MZ+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">是否房主:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].SFFZ+`</span>
							    	</p>
							    </div>
							    <div class="layui-col-md6">
							      	<p>
							    		<span class="color_white">性别:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].XB+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">年龄:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].NL+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">电话:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].DH+`</span>
							    	</p>
							    </div>
							    <div class="layui-col-md12">
							      	<p>
							    		<span class="color_white">标签:</span>
							      		<span class="color_white">`

							      		if (res.data.fwInfo[i].SFDJ == '是') {
                                    html += `<i class="iconfont iconlaoren color_yellow"></i>`;
                                }
                                if (res.data.fwInfo[i].SFKC == '是') {
                                    html += `<i class="iconfont iconlaoren color_red"></i>`;
                                }
                                if (res.data.fwInfo[i].SFDB == '是') {
                                    html += `<i class="iconfont iconrenyuan-shi color_blue"></i>`;
                                }
                                if (res.data.fwInfo[i].SFCJ == '是') {
                                    html += `<i class="iconfont iconcanjiren color_yellow"></i>`;
                                }
                                if (res.data.fwInfo[i].SFTYJR == '是') {
                                    html += `<i class="iconfont iconjunren color_green"></i>`;
                                }
                                if (res.data.fwInfo[i].SFXMSF == '是') {
                                    html += `<i class="iconfont iconren color_red"></i>`;
                                }
                            html += `</span>
                                            </p>
                                        </div>
                                    </div>
                                </li>`;
                $("#zhxx").html(html);
            }


                //弹框
                layer.open({
                    type : 1,
                    title : false,
                    area : [ '400px', 'auto' ],
                    move : '.moveTxt',
                    scrollbar : false,
                    shade : 0,
                    //fix: false,
                    content : $('#fwxxLayer'),
                    skin : 'layer-style',
                    id : 'fwxxLayerBox'
                });

            }
        }
    });

}


//楼栋弹框
function getLdTk(index,fwid,ldid){
    if(fwid != ""){
        featherCmsScript._map.fwid = fwid;
    }
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getLdInfo?ldid="+ldid,
        success: function (res) {
            if (res.code == 0) {
                var html = '';
                var html1 = '';
                $("#ldzz").html('');
                $("#fwxq").html('');
                html1+=
                    `<h4 class="moveTxt">`+res.data.ldmc+`(共`+res.data.mun+`户)</h4>`
                $("#ldzz").html(html1);
                for(var i in res.data.fwInfo){

                    html+=`<li>
                        <img class="img" src="images/video_img1.png" alt="" />`
                    html += `<div class="layui-row">
							    <div class="layui-col-md6">
							    	
							    	<p>
							    		<span class="color_white">房屋面积:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].FWMJ+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">门牌号:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].MPH+`</span>
							    	</p>
							    </div>
							    <div class="layui-col-md6">
							      	<p>
							    		<span class="color_white">入住情况:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].FWRZQK+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">使用用途:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].FWLYLX+`</span>
							    	</p>
							    	<p>
							    		<span class="color_white">产权状态:</span>
							      		<span class="color_white">`+res.data.fwInfo[i].CQZT+`</span>
							    	</p>
							    </div>
							    <div class="layui-col-md12">
							      	<p>
							    		<span class="color_white">房屋地址:</span>
							    		<span class="color_white">`+res.data.fwInfo[i].FWDZ+`</span>`
                    html += `</p>
                                        </div>
                                    </div>
                                </li>`;
                    $("#fwxq").html(html);
                }
            }
        }
    });

//弹框
    layer.open({
        type : 1,
        title : false,
        area : [ '400px', 'auto' ],
        move : '.moveTxt',
        scrollbar : false,
        shade : 0,
        //fix: false,
        content : $('#ldxxLayer'),
        skin : 'layer-style',
        id : 'fwxxLayerBox'
    });
}

//点击左侧人员切换右侧内容
featherCmsScript.register({
	element : "#permanentPersonnelBtn",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.zhzlBottomContent').addClass('leftShrink');
		featherCmsScript.trigger("resize");
		$('.ryxxBox').removeClass('hide');
		$('.fwxxBox,.carBox,.parkingSpaceBox,.deviceBox').addClass('hide');
	}
});

// 点击左侧房屋切换右侧内容
featherCmsScript.register({
	element : "#houseOpenlist",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.fwxxBox').removeClass('hide');
		$('.ryxxBox,.carBox,.parkingSpaceBox,.deviceBox').addClass('hide');
	}
});

// 点击左侧车辆切换右侧内容
featherCmsScript.register({
	element : "#carOpenlist",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.carBox').removeClass('hide');
		$('.ryxxBox,.fwxxBox,.parkingSpaceBox,.deviceBox').addClass('hide');
	}
});

// 点击左侧车位切换右侧内容
featherCmsScript.register({
	element : "#parkingSpaceRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.parkingSpaceBox').removeClass('hide');
		$('.ryxxBox,.fwxxBox,.carBox,.deviceBox').addClass('hide');
	}
});

// 点击左侧设备切换右侧内容
featherCmsScript.register({
	element : "#deviceRightOpen",
	onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
		$('.rightContent').removeClass('CloseRight');
		$('.deviceBox').removeClass('hide');
		$('.ryxxBox,.fwxxBox,.parkingSpaceBox,.carBox').addClass('hide');
	}
});

// 点击子导航--人员管理
featherCmsScript.register({
	element : "#personnelInforOpenBtn",
	onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
		$(_this).addClass('active').siblings("li").removeClass("active");
		$('.personnelLeftList,.zhzlBottomContent').removeClass('hide');
		$('.HouserLefrList,.carLefrList,.parkingSpaceLeft,.deviceLeftBox').addClass('hide');
		$('.rightContent').addClass('CloseRight');
		$('.zhzlBottomContent').removeClass('leftShrink');
	}
});

// 点击子导航--房屋
featherCmsScript.register({
	element : "#houseOpenBtn",
	onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
		$(_this).addClass('active').siblings("li").removeClass("active");
		$('.HouserLefrList').removeClass('hide');
		$('.personnelLeftList,.carLefrList,.parkingSpaceLeft,.deviceLeftBox,.zhzlBottomContent').addClass('hide');
		$('.rightContent').addClass('CloseRight');
	}
});

// 点击子导航--车辆
featherCmsScript.register({
	element : "#carOpenBtn",
	onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
		$(_this).addClass('active').siblings("li").removeClass("active");
		$('.carLefrList').removeClass('hide');
		$('.personnelLeftList,.HouserLefrList,.parkingSpaceLeft,.deviceLeftBox,.zhzlBottomContent').addClass('hide');
		$('.rightContent').addClass('CloseRight');
	}
});

// 点击子导航--车位
featherCmsScript.register({
	element : "#parkingSpaceOpenBtn",
	onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
        sqglCw.getSqglCwInfo(sqid,xqid);
		$(_this).siblings("li").removeClass("active");
		$('.parkingSpaceLeft').removeClass('hide');
		$('.personnelLeftList,.HouserLefrList,.carLefrList,.deviceLeftBox,.zhzlBottomContent').addClass('hide');
		$('.rightContent').addClass('CloseRight');
	}
});

// 点击子导航--设备
featherCmsScript.register({
	element : "#deviceOpenBtn",
	onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
		$(_this).addClass('active').siblings("li").removeClass("active");
		$('.deviceLeftBox').removeClass('hide');
		$('.personnelLeftList,.HouserLefrList,.carLefrList,.parkingSpaceLeft,.zhzlBottomContent').addClass('hide');
		$('.rightContent').addClass('CloseRight');
	}
});



// 点击关闭按钮
featherCmsScript.register({
	element : "#zhzlRightCloseBtn",
	onClick : function(cmsOptions, _this) {
		$('.rightContent').addClass('CloseRight');
		$('.zhzlBottomContent').removeClass('leftShrink');
		featherCmsScript.trigger("resize");
	}
});

// 人员信息弹框
featherCmsScript.register({
	element : "#personnelInfoLayerOpen",
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
			content : $('#personnelInfoLayer'),
			skin : 'layer-style',
			id : 'personnelInfoLayerOpenBox'
		});
	}
});

// 房屋弹框
featherCmsScript.register({
	element : "#fwxxLayerOpen",
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
			content : $('#fwxxLayer'),
			skin : 'layer-style',
			id : 'fwxxLayerBox'
		});
	}
});

// 车辆概况
featherCmsScript.register({
    element: "#clgk",
    onLoad: function (cmsOptions) {
     cltj("SQ000001","");
    },
    onResize: function (cmsOptions) {
    },
    onClick: function (cmsOptions, _this) {
    }
});
//车辆统计
function cltj(sqid,xqid){
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getCountGk?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
            if (res.code == 0) {
                var html='';
                var sum=0;
                $("#clgk").html("");
                for(var i in res.data){
                    sum+=res.data[i].num;
                }
                html+=`<li onclick="getClList('','')">车辆总数 <span class="number font_22 color_navy_blue numberFont">`+sum+`</span></li>`;
                for(var i in res.data){
                    if(res.data[i].cx=='大型'){
                        html+=`<li onclick="getClList('大型','')">大型车 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }else if(res.data[i].cx=='中型'){
                        html+=`<li  onclick="getClList('中型','')">中型车 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }else if(res.data[i].cx=='小型'){
                        html+=`<li onclick="getClList('小型','')">小型车 <span class="number font_22 color_navy_blue numberFont">`+res.data[i].num+`</span></li>`;
                    }
                }
                $("#clgk").html(html);
            }
        }
    });
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getCountJc?sqid="+sqid+"&xqid="+xqid,
        success: function (res) {
            if (res.code == 0) {
                var html='';

                $("#cljc").html("");
                var jc = res.data.jc;
                html+=`<li onclick="getCljcList('','')">进出总数<span class="number font_22 color_navy_blue numberFont">`
                for(var i in jc){
                    if(jc[i].jczt=='外出'){
                        html+=`<i class="color_yellow">`+jc[i].num+`</i>`;
                    }else{
                        html+=`<i class="color_white">/`+jc[i].num+`</i>`;
                    }
                }
                html+=`	</span></li>`;
                var lx = res.data.lx;
                for(var i=0;i<lx.length;i++){
                    if(lx[i].cllx=='本小区'){
                        if(i==0){
                            html+=`<li onclick="getCljcList('本小区','')" >小区车辆<span class="number font_22 color_navy_blue numberFont">`
                        }
                        if(lx[i].jczt=='外出'){
                            html+=`<i class="color_yellow">`+lx[i].num+`</i>`
                        }else{
                            html+=`<i class="color_white">/`+lx[i].num+`</i>`;
                        }
                        if(i==1){
                            html+=`</span></li>`;
                        }

                    }else{
                        if(i==2){
                            html+=`<li onclick="getCljcList('外来','')">外来车辆<span class="number font_22 color_navy_blue numberFont">`

                        }
                        if(lx[i].jczt=='外出'){
                            html+=`<i class="color_yellow">`+lx[i].num+`</i>`
                        }else{
                            html+=`<i class="color_white">/`+lx[i].num+`</i>`;
                        }
                        if(i==3){
                            html+=`</span></li>`;
                        }

                    }
                }
                $("#cljc").html(html);
            }
        }
    });
}
function getClList(cx,clhm) {
    cllx1='0';
    carRightBox();
    $.ajax({
        url: featherCmsScript.ctx + 'sqgl/api/getClList?sqid=' + sqid + "&xqid=" + xqid+"&cx="+cx+"&id="+"&clhm="+clhm,
        success: function (res) {
            if (res.code == 0) {
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'clxxList',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('clList').innerHTML = function () {
                                var arr = [];
                                var html = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                            html+=`<div class="layui-row colum" onclick="getDital('`+item.CLID+`')">
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span>1</span>
                                                    </div>
                                                    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                                        <span title="">`+item.CLHM+`</span>
                                                    </div>
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span title="">`+item.CX+`</span>
                                                    </div>
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span title="">`+item.CZXM+`</span>
                                                    </div>
                                                    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                                        <span title="">`+item.LXDH+`</span>
                                                    </div>
                                                </div>`;
                                });
                                arr.push(html);
                                return arr.join('');
                            }();
                        }
                    });
                });
            }
        }
    })
}
function getCljcList(cllx,clhm) {
    cllx1='1';
    carRightBox();
    $.ajax({
        url: featherCmsScript.ctx + 'sqgl/api/getCljcList?sqid=' + sqid + "&xqid=" + xqid+"&cllx="+cllx+"&id="+"&clhm="+clhm,
        success: function (res) {
            if (res.code == 0) {

                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'clxxList',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('clList').innerHTML = function () {
                                var arr = [];
                                var html = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                    html+=`<div class="layui-row colum" onclick="getDital('`+item.JCID+`')">
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span>1</span>
                                                    </div>
                                                    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                                        <span title="">`+item.CPHM+`</span>
                                                    </div>
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span title="">`+item.CX+`</span>
                                                    </div>
                                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                        <span title="">`+item.CZXM+`</span>
                                                    </div>
                                                    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
                                                        <span title="">`+item.LXDH+`</span>
                                                    </div>
                                                </div>`;
                                });
                                arr.push(html);
                                return arr.join('');
                            }();
                        }
                    });
                });

            }
        }
    })
}

// 车位列表弹框
featherCmsScript.register({
	element : "#parkingSpaceLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '500px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#parkingSpaceLayer'),
			skin : 'layer-style',
			id : 'parkingSpaceLayerBox'
		});
	}
});

// 设备弹框
featherCmsScript.register({
	element : "#deviceLayerOpen",
	onLoad : function(cmsOptions) {},
	onResize : function(cmsOptions) {},
	onClick : function(cmsOptions, _this) {
		layer.open({
			type : 1,
			title : false,
			area : [ '500px', 'auto' ],
			move : '.moveTxt',
			scrollbar : false,
			shade : 0,
			//fix: false,
			content : $('#deviceLayer'),
			skin : 'layer-style',
			id : 'parkingSpaceLayerBox'
		});
	}
});

// 房屋产权状态
featherCmsScript.register({
	element : "#fwcqzt",
	onLoad : function(cmsOptions) {
        selectZcfwZtCount('SQ000001','')
	},
	onResize : function(cmsOptions) {
		//cmsOptions.object.resize();
	},
	onClick : function(cmsOptions, _this) {}
});

function selectZcfwZtCount(sqid,xqid) {
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    if(_this.startCharts){
        clearInterval(_this.startCharts);
    }

    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "sqgl/api/selectZcfwTbCount"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                for (var i in res.data) {
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].CQZT+""};
                    ydata.push(json);
                }
            }
        }
    })

    var ryxjEchart = echarts.init(document.getElementById('fwcqzt'));
    //cmsOptions.object = ryxjEchart;
    var legend_data = [ '租房', '自有'];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)",
            confine:true
        },
        series : [ {
            name : '房屋产权状态',
            type : 'pie',
            radius : [ '55%', '75%' ],
            center : [ '50%', '50%' ],
            avoidLabelOverlap : false,
            label : {
                normal : {
                    show : false,
                    position : 'center'
                },
                emphasis : {
                    show : true,
                    formatter : function(params, ticket, callback) {
                        console.log(params)
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
            data : ydata
        } ],
        color : [ '#4bd896', '#fee67c']
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

//社区管理车位信息
var sqglCw = {

    /**
	 * 加载页面左侧列表统计数据
     */
	getSqglCwInfo : function (sqId,xqId) {
	    layer.closeAll();
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getCwInfoLeft?sqid=' + sqId + '&xqid=' + xqId,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                pageTccCount = data.data[0].tjLeftOn.tccCount;
                pageKxCount = data.data[0].tjLeftOn.allKxCount;
                pageYyCount = data.data[0].tjLeftOn.allZyCount;
                var str='';
                str += `<dl class="total">
									<dt><img src="images/sqgl_icon4.png" alt="" /></dt>
									<dd>
										<h5 class="numberFont font_30 color_white">` + data.data[0].tjLeftOn.allCwCount + `</h5>
										<h6 class="color_yellow font_16">车位总数</h6>
									</dd>
								</dl>
								<ul class="list">
									<li id="parkingSpaceRightOpen" onclick="sqglCw.parkingSpaceRightOpen();">
										<span class="font_16">停车场数</span> <span class="number font_22 color_navy_blue numberFont">` + data.data[0].tjLeftOn.tccCount + `</span>
									</li>
								</ul>
								<h6 class="title2 color_navy_blue ml_10">车位使用情况</h6>
								<ul class="list">
									<li id="houseOpenlistKxcw" class="border_b_gray" onclick="sqglCw.kxCw();">
										<span class="font_16">空闲车位</span> <span class="number font_22 color_navy_blue numberFont">` + data.data[0].tjLeftOn.allKxCount + `</span>
									</li>
								</ul>
								<div class="znaf_bjtj border_b_gray" style="padding:0 0.52vw 0.92vh 0.52vw">
									<p class="oh">地下空闲<span class="fr numberFont color_white font_22">` + data.data[0].tjLeftOn.allDxKxCount + `</span></p>
									<div class="layui-progress layuiProgress">
										<div class="layui-progress-bar layui-bg-red" lay-percent="` + data.data[0].tjLeftOn.dxKxBfb + `"></div>
										<div class="layui-progress-text">` + data.data[0].tjLeftOn.dxKxBfb + `%</div>
									</div>
									<p class="oh">地上空闲<span class="fr numberFont color_white font_22">` + data.data[0].tjLeftOn.allDsKxCount + `</span></p>
									<div class="layui-progress layuiProgress">
										<div class="layui-progress-bar layui-bg-orange" lay-percent="` + data.data[0].tjLeftOn.dsKxBfb + `"></div>
										<div class="layui-progress-text">` + data.data[0].tjLeftOn.dsKxBfb + `%</div>
									</div>
								</div>
								<ul class="list">
									<li id="houseOpenlistYycw" class="border_b_gray" onclick="sqglCw.yyCw();">
										<span class="font_16">已用车位</span> <span class="number font_22 color_navy_blue numberFont">` + data.data[0].tjLeftOn.allZyCount + `</span>
									</li>
								</ul>
								<div class="znaf_bjtj" style="padding:0 0.52vw 0.92vh 0.52vw">
									<p class="oh">地下已用<span class="fr numberFont color_white font_22">` + data.data[0].tjLeftOn.allDxZyCount + `</span></p>
									<div class="layui-progress layuiProgress">
										<div class="layui-progress-bar layui-bg-green" lay-percent="` + data.data[0].tjLeftOn.dxZyBfb + `"></div>
										<div class="layui-progress-text">` + data.data[0].tjLeftOn.dxZyBfb + `%</div>
									</div>
									<p class="oh">地上已用<span class="fr numberFont color_white font_22">` + data.data[0].tjLeftOn.allDsZyCount + `</span></p>
									<div class="layui-progress layuiProgress">
										<div class="layui-progress-bar layui-bg-blue" lay-percent="`+ data.data[0].tjLeftOn.dsZyBfb +`"></div>
										<div class="layui-progress-text">` + data.data[0].tjLeftOn.dsZyBfb + `%</div>
									</div>
								</div>` ;
                var str1='';
                str1+=`<h6 class="title2 color_navy_blue ml_10">停车场空位TOP5</h6>
							<div class="parkingSpaceTopList">`;
                for(var i = 0; i < data.data[1].tjLeftDown.length ;i++){
                	str1 += `<div class="layui-row">
									<div class="layui-col-md1">
								      	<span class="numberFont font_18">` + (i+1) + `</span>
								    </div>
									<div class="layui-col-md9">
								      	<span title>` + data.data[1].tjLeftDown[i].TCCMC + `</span>
								    </div>
								    <div class="layui-col-md2">
								      	<span class="numberFont font_18">` + data.data[1].tjLeftDown[i].kxCwCount + `</span>
								    </div>
								</div>`;
				}
				str1+=`</div>`;
                var html=str+str1;
                $(".parkingSpaceLeft").html(html);
                layui.use([ 'element'], function() { //独立版的layer无需执行这一句
                    var element = layui.element;
                    element.init()
                });
            }
        });
    },

    //点击查看停车场数
    parkingSpaceRightOpen : function () {
        layer.closeAll();
        sqglCw.clearPageElement();
        sqglCw.getTccNum(sqid,xqid,"");
        $('.rightContent').removeClass('CloseRight');
        $('.parkingSpaceBox').removeClass('hide');
        $('.ryxxBox,.fwxxBox,.carBox,.deviceBox').addClass('hide');
    },
    //清空分页加载参数；
    clearPageElement : function (){
	    pageNum = 1;
	    pageSize = 20 ;
	    pageLimitStart = 0;
    },
    /**
	 * 加载右侧停车场信息列表
     */
    getTccNum : function(sqid,xqid,tccmc){
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getTccInfoRight?sqid=' + sqid + '&xqid=' + xqid + '&tccmc=' + tccmc + '&pageNum=' + pageNum + '&pageSize=' + pageSize,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
            	debugger;
            	if(data.code == 0){
                    var str='';
                    str += `<div class="title mb_10">
							<a href="" class="txt color_white">停车场信息</a>
						</div>
						<div class="SearchBox2">
							<input type="text" id="searchTccInfo" placeholder="请输入停车场名称" />
							<i class="iconfont iconsousuo pa searchIco" onclick="sqglCw.searchTcc();"></i>
						</div>
						<div class="title3 mh_10"><span class="txt">停车场列表</span></div>
						<div class="layui-row tabThead">
							<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span >序号</span>
						    </div>
							<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">车场名称</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车场类型</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车位总数</span>
						    </div>
						    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">空闲车位</span>
						    </div>
						</div><div class="tabBody">`;
            		var str1="";
            		for (var i = 0 ; i < data.data.length ; i++){
                        var tccId = data.data[i].TCCID;
                        var tccLx = data.data[i].TCCLX;
            			str1 += `<div class="layui-row colum" id="parkingSpaceLayerOpen" onclick="sqglCw.parkingSpaceLayerOpen('`+tccId+`,`+tccLx+`');">
								<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span>`+ (i+pageLimitStart+1) +`</span>
							    </div>
								<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].TCCMC + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].TCCLX + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].cwNum + `</span>
							    </div>
							    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].kxCw + `</span>
							    </div>
							</div>`;
					}
                    str1+=`</div><div id="demo3" class="layuiPage"></div></div>`;
                    var html=''
                    html += (str+str1);
                    $(".parkingSpaceBox").html('');
                    $(".parkingSpaceBox").html(html);
                    //总页数低于页码总数
                    layui.use(['form','laypage'], function(){
                        laypage = layui.laypage;
                        laypage.render({
                            cont : demo3,
                            elem: 'demo3'
                            ,count: pageTccCount
                            ,curr: pageNum
                            ,limit: 20
                            ,limits: 20
                            ,layout: ['count', 'prev', 'page', 'next']
                            ,jump: function(obj,first){
                                console.log(obj)
                                pageNum = obj.curr;
                                pageSize = obj.limit;
                                if(obj.curr == 1){
                                    pageLimitStart = 0;
                                }else{
                                    pageLimitStart = (obj.curr-1) *20;
                                }
                                if(!first){
                                    // if ( pageNum !== pagePre ) {
                                    sqglCw.getTccNum(sqid,xqid,"");
                                    //}
                                }
                            }
                        });
                    });
				}
            }
        });
	},
    //搜索停车场
    searchTcc : function(){
        var searchTccInfo = $("#searchTccInfo").val();
        sqglCw.getTccNum(sqid,xqid,searchTccInfo);
    },
	//加载停车场详细列表
    parkingSpaceLayerOpen : function(tccIdLx){
    	//根据停车场Id获取停车场详细信息列表
    	sqglCw.getTccXxInfo(tccIdLx);
	},
    //根据停车场Id获取停车场详细信息列表
	getTccXxInfo : function(tccIdLx){
        var arr = tccIdLx.split(',');
        featherCmsScript._map.localPosition(arr[0], 'TCC');
        featherCmsScript._map.replacePic('bus/smartcommunity/img/p.png', arr[0]);
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getTccXxInfoCenter?tccId=' + tccIdLx,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                if(data.code == 0){
                    var str="";
                    str += `<h4 class="moveTxt">`+data.data[1].cwCount[0].TCCMC+`</h4>
								<div class="layui-row parkingSpaceNumberStatus">
									<div class="layui-col-md4 layui-col-xs4">
										<span class="color_blue">总车位：</span>`+data.data[1].cwCount[0].cwNum+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtC">
										<span class="color_blue">空闲车位：</span>`+data.data[1].cwCount[0].kxCw+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtR">
										<span class="color_blue">已用车位：</span>`+data.data[1].cwCount[0].zyCw+`
									</div>
								</div><div class="layerContent">
										<ul class="parkingSpaceLayerList">`;
                    var str1="";
                    for (var i = 0 ; i < data.data[0].cwInfolist.length ; i++){
							if(data.data[0].cwInfolist[i].CWZT=="空闲"){
								str1+=`<li>
											<p>`+data.data[0].cwInfolist[i].CWBH+`</p>
										</li>`;
							}else{
                                str1+=`<li>
											<p>`+data.data[0].cwInfolist[i].CWBH+`</p>
											<i class="iconfont iconwaichucheliang1 color_blue"></i>
										</li>`;
							}
                    }
                    str1+=`</ul></div>`;
                    var html='';
                    html+=(str+str1);
                    $("#parkingSpaceLayer").html('');
                    $("#parkingSpaceLayer").html(html);

                    layer.open({
                        type : 1,
                        title : false,
                        area : [ '500px', 'auto' ],
                        move : '.moveTxt',
                        scrollbar : false,
                        shade : 0,
                        //fix: false,
                        content : $('#parkingSpaceLayer'),
                        skin : 'layer-style',
                        id : 'parkingSpaceLayerBox'
                    })
                }
            }
        });
	},
    //点击查看空闲车位
    kxCw : function () {
        layer.closeAll();
        sqglCw.clearPageElement();
        sqglCw.getKxcwInfo(sqid,xqid,"");
        $('.rightContent').removeClass('CloseRight');
        $('.parkingSpaceBox').removeClass('hide');
        $('.ryxxBox,.fwxxBox,.carBox,.deviceBox').addClass('hide');
    },
    //加载空闲车位右侧列表信息
    getKxcwInfo : function (sqid,xqid,cwid){
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getKxcwInfo?sqid=' + sqid + '&xqid=' + xqid + '&cwid=' + cwid + '&pageNum=' + pageNum + '&pageSize=' + pageSize,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                if(data.code == 0){
                    var str='';
                    str += `<div class="title mb_10">
							<a href="" class="txt color_white">空闲车位信息</a>
						</div>
						<div class="SearchBox2">
							<input type="text" id="searchCwInfo" placeholder="请输入车位ID" />
							<i class="iconfont iconsousuo pa searchIco" onclick="sqglCw.searchCw();"></i>
						</div>
						<div class="title3 mh_10"><span class="txt">车位列表</span></div>
						<div class="layui-row tabThead">
							<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span >序号</span>
						    </div>
							<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">车位编号</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车位类型</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车位状态</span>
						    </div>
						    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">停车场</span>
						    </div>
						</div><div class="tabBody">`;
                    var str1 = '';
                    for (var i = 0 ; i < data.data.length ; i++){
                        var cwid = data.data[i].CWID;
                        var cwlx = data.data[i].CWLX;
                        var tccid = data.data[i].TCCID;
                        str1 += `<div class="layui-row colum" id="parkingSpaceLayerOpen" onclick="sqglCw.kxcwLayerOpen('`+cwid+`,`+cwlx+`,`+tccid+`');">
								<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span>`+ (i+pageLimitStart+1) +`</span>
							    </div>
								<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].CWID + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].CWLX + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].CWZT + `</span>
							    </div>
							    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].TCCMC + `</span>
							    </div>
							</div>`;
                    };
                    str1+=`</div><div id="demo33" class="layuiPage"></div></div>`;
                    var html=''
                    html += (str+str1);
                    $(".parkingSpaceBox").html('');
                    $(".parkingSpaceBox").html(html);
                    //总页数低于页码总数
                    layui.use(['form','laypage'], function(){
                        laypage = layui.laypage;
                        laypage.render({
                            cont : demo33,
                            elem: 'demo33'
                            ,count: pageKxCount
                            ,curr: pageNum
                            ,limit: 20
                            ,limits: 20
                            ,layout: ['count', 'prev', 'page', 'next']
                            ,jump: function(obj,first){
                                console.log(obj)
                                pageNum = obj.curr;
                                pageSize = obj.limit;
                                if(obj.curr == 1){
                                    pageLimitStart = 0;
                                }else{
                                    pageLimitStart = (obj.curr-1) *20;
                                }
                                if(!first){
                                   // if ( pageNum !== pagePre ) {
                                        sqglCw.getKxcwInfo(sqid,xqid,"");
                                    //}
                                }
                            }
                        });
                    });
                }
            }
        });
    },
    searchCw :function(){
        var searchCwInfo = $("#searchCwInfo").val();
        sqglCw.getKxcwInfo(sqid,xqid,searchCwInfo);
    },
    //加载空闲车位详细列表
    kxcwLayerOpen : function(cwxx){
        //根据停车场Id获取停车场详细信息列表
        sqglCw.getKxcwXxInfoList(cwxx);
    },
    //查看空闲车位详细信息列表
    getKxcwXxInfoList : function(cwxx){
        var split = cwxx.split(",");
        featherCmsScript._map.localPosition(split[2], 'TCC');
        featherCmsScript._map.replacePic('bus/smartcommunity/img/p.png', split[2]);
        var cwidClick = split[0];
        var cwidClick = cwidClick.substring(cwidClick.length-4,cwidClick.length);

        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getKxcwXxInfoList?cwxx=' + cwxx + '&sqid=' + sqid+ '&xqid=' + xqid,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                if(data.code == 0){
                    var str="";
                    str += `<h4 class="moveTxt">`+data.data[1].cwCount[0].TCCMC+`</h4>
								<div class="layui-row parkingSpaceNumberStatus">
									<div class="layui-col-md4 layui-col-xs4">
										<span class="color_blue">总车位：</span>`+data.data[1].cwCount[0].cwNum+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtC">
										<span class="color_blue">空闲车位：</span>`+data.data[1].cwCount[0].kxCw+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtR">
										<span class="color_blue">已用车位：</span>`+data.data[1].cwCount[0].zyCw+`
									</div>
								</div><div class="layerContent">
										<ul class="parkingSpaceLayerList">`;
                    var str1="";
                    for (var i = 0 ; i < data.data[0].kxcwXxInfoList.length ; i++){
                        if(data.data[0].kxcwXxInfoList[i].CWZT=="空闲"){
                            if(cwidClick == data.data[0].kxcwXxInfoList[i].CWBH){
                                str1+=`<li class="active">
											<p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
										</li>`;
                            }else{
                                str1+=`<li>
											<p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
										</li>`;
                            }
                        }else{
                            str1+=`<li>
                                        <p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
                                        <i class="iconfont iconwaichucheliang1 color_blue"></i>
                                    </li>`;
                        }
                    }
                    str1+=`</ul></div>`;
                    var html='';
                    html+=(str+str1);
                    $("#parkingSpaceLayer").html('');
                    $("#parkingSpaceLayer").html(html);

                    layer.open({
                        type : 1,
                        title : false,
                        area : [ '500px', 'auto' ],
                        move : '.moveTxt',
                        scrollbar : false,
                        shade : 0,
                        //fix: false,
                        content : $('#parkingSpaceLayer'),
                        skin : 'layer-style',
                        id : 'parkingSpaceLayerBox'
                    })
                }
            }
        });
    },
    //点击查看已用车位
    yyCw : function () {
        layer.closeAll();
        sqglCw.clearPageElement();
        sqglCw.getYycwInfo(sqid,xqid,"");
        $('.rightContent').removeClass('CloseRight');
        $('.parkingSpaceBox').removeClass('hide');
        $('.ryxxBox,.fwxxBox,.carBox,.deviceBox').addClass('hide');
    },
    //加载空闲车位右侧列表信息
    getYycwInfo : function (sqid,xqid,cwid){
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getYycwInfo?sqid=' + sqid + '&xqid=' + xqid + '&cwid=' + cwid + '&pageNum=' + pageNum + '&pageSize=' + pageSize,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                if(data.code == 0){
                    var str='';
                    str += `<div class="title mb_10">
							<a href="" class="txt color_white">已用车位信息</a>
						</div>
						<div class="SearchBox2">
							<input type="text" id="serachYyCwInfo" placeholder="请输入车位ID" />
							<i class="iconfont iconsousuo pa searchIco" onclick="sqglCw.searchYyCw()"></i>
						</div>
						<div class="title3 mh_10"><span class="txt">车位列表</span></div>
						<div class="layui-row tabThead">
							<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span >序号</span>
						    </div>
							<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">车位编号</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车位类型</span>
						    </div>
						    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
						      	<span title="">车位状态</span>
						    </div>
						    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
						      	<span title="">停车场</span>
						    </div>
						</div><div class="tabBody">`;
                    var str1 = '';
                    for (var i = 0 ; i < data.data.length ; i++){
                        var cwid = data.data[i].CWID;
                        var cwlx = data.data[i].CWLX;
                        var tccid = data.data[i].TCCID;
                        str1 += `<div class="layui-row colum" id="parkingSpaceLayerOpen" onclick="sqglCw.YycwLayerOpen('`+cwid+`,`+cwlx+`,`+tccid+`');">
								<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span>`+ (i+pageLimitStart+1) +`</span>
							    </div>
								<div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].CWID + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].CWLX + `</span>
							    </div>
							    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
							      	<span title="">` + data.data[i].CWZT + `</span>
							    </div>
							    <div class="layui-col-md3 layui-col-sm3 layui-col-xs3">
							      	<span title="">` + data.data[i].TCCMC + `</span>
							    </div>
							</div>`;
                    };
                    str1+=`</div><div id="demo333" class="layuiPage"></div></div>`;
                    var html=''
                    html += (str+str1);
                    $(".parkingSpaceBox").html('');
                    $(".parkingSpaceBox").html(html);
                    //总页数低于页码总数
                    layui.use(['form','laypage'], function(){
                        laypage = layui.laypage;
                        laypage.render({
                            cont : demo333,
                            elem: 'demo333'
                            ,count: pageYyCount
                            ,curr: pageNum
                            ,limit: 20
                            ,limits: 20
                            ,layout: ['count', 'prev', 'page', 'next']
                            ,jump: function(obj,first){
                                console.log(obj)
                                pageNum = obj.curr;
                                pageSize = obj.limit;
                                if(obj.curr == 1){
                                    pageLimitStart = 0;
                                }else{
                                    pageLimitStart = (obj.curr-1) *20;
                                }
                                if(!first){
                                    // if ( pageNum !== pagePre ) {
                                    sqglCw.getYycwInfo(sqid,xqid,"");
                                    //}
                                }
                            }
                        });
                    });
                }
            }
        });
    },
    searchYyCw : function(){
        var serachYyCwInfo = $("#serachYyCwInfo").val();
        sqglCw.getYycwInfo(sqid,xqid,serachYyCwInfo);
    },
    //加载已用车位详细列表
    YycwLayerOpen : function(cwxx){
        //根据停车场Id获取停车场详细信息列表
        sqglCw.getYycwXxInfoList(cwxx);
    },
    //查看已用车位详细信息列表
    getYycwXxInfoList : function(cwxx){
        var split = cwxx.split(",");
        featherCmsScript._map.localPosition(split[2], 'TCC');
        featherCmsScript._map.replacePic('bus/smartcommunity/img/p.png', split[2]);
        var cwidClick = split[0];
        var cwidClick = cwidClick.substring(cwidClick.length-4,cwidClick.length);
        $.ajax({
            async: false,
            url: featherCmsScript.ctx + 'smartcommunity/CW/getYycwXxInfoList?cwxx=' + cwxx + '&sqid=' + sqid+ '&xqid=' + xqid,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                if(data.code == 0){
                    var str="";
                    str += `<h4 class="moveTxt">`+data.data[1].cwCount[0].TCCMC+`</h4>
								<div class="layui-row parkingSpaceNumberStatus">
									<div class="layui-col-md4 layui-col-xs4">
										<span class="color_blue">总车位：</span>`+data.data[1].cwCount[0].cwNum+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtC">
										<span class="color_blue">空闲车位：</span>`+data.data[1].cwCount[0].kxCw+`
									</div>
									<div class="layui-col-md4 layui-col-xs4 txtR">
										<span class="color_blue">已用车位：</span>`+data.data[1].cwCount[0].zyCw+`
									</div>
								</div><div class="layerContent">
										<ul class="parkingSpaceLayerList">`;
                    var str1="";
                    for (var i = 0 ; i < data.data[0].kxcwXxInfoList.length ; i++){
                        if(data.data[0].kxcwXxInfoList[i].CWZT=="占用"){
                            if(cwidClick == data.data[0].kxcwXxInfoList[i].CWBH){
                                str1+=`<li class="active">
											<p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
											<i class="iconfont iconwaichucheliang1 color_blue"></i>
										</li>`;
                            }else{
                                str1+=`<li>
											<p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
											<i class="iconfont iconwaichucheliang1 color_blue"></i>
										</li>`;
                            }

                        }else{
                            str1+=`<li>
											<p>`+data.data[0].kxcwXxInfoList[i].CWBH+`</p>
										</li>`;
                        }
                    }
                    str1+=`</ul></div>`;
                    var html='';
                    html+=(str+str1);
                    $("#parkingSpaceLayer").html('');
                    $("#parkingSpaceLayer").html(html);

                    layer.open({
                        type : 1,
                        title : false,
                        area : [ '500px', 'auto' ],
                        move : '.moveTxt',
                        scrollbar : false,
                        shade : 0,
                        //fix: false,
                        content : $('#parkingSpaceLayer'),
                        skin : 'layer-style',
                        id : 'parkingSpaceLayerBox'
                    })
                }
            }
        });
    },



};

featherCmsScript.register({
    element : "",
    onLoad : function(cmsOptions) {
        getSbCountList("SQ000001","");
    }
});

function getSbCountList(sqid,xqid) {
    featherCmsScript._map.clearPic();
    $.ajax({
        url:featherCmsScript.ctx + 'znaf/api/getSblxYcCount?sqid='+sqid+"&xqid="+xqid,
        success:function(res) {
            if (res.code != 0) {
                return;
            }
            var html = '';
            var sum = 0;
            var ycsum = 0;
            $("#getSbCountList").html('');
            for (var i in res.data) {
                var yc = '';
                if (res.data[i].yc == undefined) {
                    yc = 0;
                } else {
                    yc = res.data[i].yc;
                }
                if(res.data[i].sblx=='闸机'||res.data[i].sblx=='车辆道杆'||res.data[i].sblx=='门禁'||res.data[i].sblx=='摄像头'){

                }else{
                    html += `<li onclick="getListPage('` + res.data[i].sblx + `','','')">
                                ` + res.data[i].sblx + `
                                <span class="number font_22 color_navy_blue numberFont">
                                    <i class="color_yellow">` + yc + `</i>
                                    <i class="color_white">/` + res.data[i].zs + `</i>
                                </span>
                            </li>`;
                    sum += res.data[i].zs;
                    ycsum += yc;
                }

            }
            $("#getSbCountList").html(html);
            $("#sbzs").html(sum);
            $("#yczs").html(ycsum);
        }
    });
}

function getListPage(sblx,sbmc,type){
     searchType=sblx;
     type1=type;
    layer.closeAll();
    var clickType = featherCmsScript._map.getType(sblx);
    featherCmsScript._map.addLayerPic(clickType, 'index');
    $.ajax({
        url:featherCmsScript.ctx + 'znaf/api/getListSb?sqid='+sqid+"&xqid="+xqid+"&sblx="+sblx+"&sbmc="+sbmc+"&zcid="+type,
        success:function(res) {

            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'sbxxPage',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('ListCountSb').innerHTML = function () {
                                var arr = [];
                                var html = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {
                                        html+=`<div class="layui-row colum" onclick="sbxxx('`+item.ZCID+`','`+item.SBLX+`')"><div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                <span>`+(parseInt(index)+1)+`</span>
                                            </div>
                                            <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                                <span title="">`+item.SBLX+`</span>
                                            </div>
                                            <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                                <span title="">`+item.SBMC+`</span>
                                            </div>
                                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                <span title="">`+item.SBZT+`</span>
                                            </div></div>`;

                                });
                                arr.push(html);
                                return arr.join('');
                            }();
                        }
                    });
                });

            }
        }
    })
    rightBox();
}


function sbxxx(zcid,sblx){

    var type='';
    if(sblx=='摄像头'){
        type='SXT';
    }else  if(sblx=='门禁'){
        type='MJ';
    }else  if(sblx=='垃圾桶'){
        type='LJT';
    }else  if(sblx=='车辆道杆'){
        type='DG';
    }else  if(sblx=='充电桩'){
        type='CDZ';
    }else  if(sblx=='路灯'){
        type='LD';
    }else  if(sblx=='闸机'){
        type='ZJ';
    }else  if(sblx=='烟感'){
        type='YG';
    }else  if(sblx=='消防栓'){
        type='XFS';
    }

    var url = featherCmsScript.ctx + 'screen/api/getSbJc?type=' + type + '&id=' + zcid;

    $.ajax({
        "async": false,
        "url": url,
        "type": 'GET',
        "dataType": 'json',
        "success": function (data) {
            sbxxx1(type, data);
        }
    });
}
function sbxxx1(type,data) {
    var img;
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

    if(img != undefined){
        featherCmsScript._map.clearPic();
        featherCmsScript._map.localPositionYc(data.data[0].ZCID,type, 'index');
        featherCmsScript._map.localPosition(data.data[0].ZCID,type);
        //featherCmsScript._map.replacePic(img,data.data[0].ZCID);
    }
    var html='';
    var html1='';
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
    }

    layer.open({
        type: 1,
        title: false,
        move: '.moveTxt',
        scrollbar: false,
        shade: 0,
        //fix: false,
        content: html1,
        skin: 'layer-style',
        id: "parkingSpaceLayerBox",
        success: function (index, layero) {
            featherCmsScript.register({
                element: "#GateWiperWrapper",
                onLoad: function (cmsOptions) {

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
    var Gatewiper1 = new Swiper('#mjswiperWrapper', {
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
    Gatewiper1.el.onmouseover = function () {
        Gatewiper1.autoplay.stop();
    }
    //鼠标离开开始自动切换
    Gatewiper1.el.onmouseout = function () {
        Gatewiper1.autoplay.start();
    }


}
function rightBox(){
    $('.rightContent').removeClass('CloseRight');
    $('.deviceBox').removeClass('hide');
    $('.ryxxBox,.fwxxBox,.parkingSpaceBox,.carBox').addClass('hide');
}

function carRightBox() {
    $('.rightContent').removeClass('CloseRight');
    $('.carBox').removeClass('hide');
    $('.ryxxBox,.fwxxBox,.parkingSpaceBox,.deviceBox').addClass('hide');
}

function searchSb() {
    var sbmc=$("#sbmc").val();
    getListPage(searchType,sbmc,type1);
}

function serarchCl() {
    var sbmc=$("#carName").val();
    if(cllx1=='0'){
        getClList('',sbmc);
    }else{
        getCljcList('',sbmc);
    }

}
