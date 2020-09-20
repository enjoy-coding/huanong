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
                selectZdryCount(treeNode.id,'')
                //重点人员分布图
                getZdryFb(treeNode.id,'','','','','','','')
                //年龄
                getZdNl(treeNode.id,'','','','','','','')
                //男女比例
                getZdNnBl(treeNode.id,'','','','','','','')
                //民族比例
                getZdMzBl(treeNode.id,'','','','','','','')
                //重点事件
                selectZdsjCount(treeNode.id,'')
            }else{
                sqid = '';
                xqid = treeNode.id;
                //重点人员统计
                selectZdryCount('',treeNode.id)
                //重点人员分布图
                getZdryFb('',treeNode.id,'','','','','','')
                //年龄
                getZdNl('',treeNode.id,'','','','','','')
                //男女比例
                getZdNnBl('',treeNode.id,'','','','','','')
                //民族比例
                getZdMzBl('',treeNode.id,'','','','','','')
                //重点事件
                selectZdsjCount('',treeNode.id)
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

featherCmsScript.register({
    element : "#zdry",
    onLoad : function(cmsOptions) {
        selectZdryCount('SQ000001','')
    },
    onResize : function(cmsOptions) {},
    onClick : function(cmsOptions, _this) {
    }
});

function selectZdryCount(sqid,xqid){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectZdryCount"+urlParams,
        success : function(res) {
            $("#zdry").html("");
            if (res.code == 0) {
                var str = `<dl class="total" onclick="getZdry(sqid,xqid,'','','','','','')">
                    <dt><img src="images/zhzl_icon1.png" alt="" /></dt>
                    <dd>
                    <h5 class="numberFont font_30 color_white">`+res.data.mun+`</h5>
                    <h6 class="color_yellow font_14">重点人员人数</h6>
                    </dd>
                    </dl>
                    <ul class="list">
                    <li id="livingAloneBtn" onclick="getZdry(sqid,xqid,'是','','','','','','独居老人')">
                    独居老人 <span class="number font_22 color_navy_blue numberFont">`+res.data.djlr+`</span>
                   </li>
                    <li onclick="getZdry(sqid,xqid,'','是','','','','','空巢老人')">
                    空巢老人 <span class="number font_22 color_navy_blue numberFont">`+res.data.kclr+`</span>
                    </li>
                    <li onclick="getZdry(sqid,xqid,'','','','','是','','低保人员')">
                    低保人员 <span class="number font_22 color_navy_blue numberFont">`+res.data.dbry+`</span>
                    </li>
                     <li onclick="getZdry(sqid,xqid,'','','','','','是','残疾人员')">
                     残疾人员 <span class="number font_22 color_navy_blue numberFont">`+res.data.cjlr+`</span>
                    </li>
                    <li onclick="getZdry(sqid,xqid,'','','','是','','','退役军人')">
                    退役军人 <span class="number font_22 color_navy_blue numberFont">`+res.data.twjr+`</span>
                    </li>
                    <li onclick="getZdry(sqid,xqid,'','','是','','','','刑满释放')">
                    刑满释放 <span class="number font_22 color_navy_blue numberFont">`+res.data.xmsf+`</span>
                    </li>
                    </ul>`;
                $("#zdry").html(str);
            }
        }
    })


    //重点事件
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectZdsjCount"+urlParams,
        success : function(res) {
            $("#sjzs").html("");
            $("#czzt").html("");
            $("#sjlx").html("");
            if (res.code == 0) {

                var str = "" +
                    "" +
                    "" +
                    ""
                var str1 =`<dt><img src="images/zhzl_icon3.png" alt="" /></dt>
								<dd onclick="getZdsjList(sqid,xqid,'','')">
									<h5 class="numberFont font_30 color_white">`+res.data.zs+`</h5>
									<h6 class="color_yellow font_14">事件总数</h6>
								</dd>`;
                $("#sjzs").html(str1);

                var str2 =`<li id="zdsjRightOpen" onclick="getZdsjList(sqid,xqid,'未处置','')">
									末处置 <span class="number font_22 color_navy_blue numberFont">`+res.data.wcz+`</span>
								</li>
								<li onclick="getZdsjList(sqid,xqid,'处置中','')">
									处置中 <span class="number font_22 color_navy_blue numberFont">`+res.data.czz+`</span>
								</li>
								<li onclick="getZdsjList(sqid,xqid,'已处置','')">
									已处置 <span class="number font_22 color_navy_blue numberFont">`+res.data.ycz+`</span>
								</li>`;
                $("#czzt").html(str2);

                var str3 =`<p class="oh" onclick="getZdsjList(sqid,xqid,'','治安事件')">治安事件<span class="fr numberFont color_navy_blue font_22">`+res.data.zasj+`</span></p>
								<div class="layui-progress layuiProgress">
									<div class="layui-progress-bar layui-bg-green" lay-percent="`+res.data.zasjL+`%"></div>
									<div class="layui-progress-text">`+res.data.zasjL+`%</div>
								</div>
								<p class="oh" onclick="getZdsjList(sqid,xqid,'','卫生事件')">卫生事件<span class="fr numberFont color_navy_blue font_22">`+res.data.wssj+`</span></p>
								<div class="layui-progress layuiProgress">
									<div class="layui-progress-bar layui-bg-orange" lay-percent="`+res.data.wssjL+`%"></div>
									<div class="layui-progress-text">`+res.data.wssjL+`%</div>
								</div>
								<p class="oh" onclick="getZdsjList(sqid,xqid,'','能源事件')">能源事件<span class="fr numberFont color_navy_blue font_22">`+res.data.nysj+`</span></p>
								<div class="layui-progress layuiProgress">
									<div class="layui-progress-bar layui-bg-red" lay-percent="`+res.data.nysjL+`%"></div>
									<div class="layui-progress-text">`+res.data.nysjL+`%</div>
								</div>
								<p class="oh" onclick="getZdsjList(sqid,xqid,'','矛盾纠纷')">矛盾纠纷<span class="fr numberFont color_navy_blue font_22">`+res.data.mdjf+`</span></p>
								<div class="layui-progress layuiProgress">
									<div class="layui-progress-bar layui-bg-blue" lay-percent="`+res.data.mdjfL+`%"></div>
									<div class="layui-progress-text">`+res.data.mdjfL+`%</div>
								</div>
                               <p class="oh" onclick="getZdsjList(sqid,xqid,'','报修事件')">报修事件<span class="fr numberFont color_navy_blue font_22">`+res.data.bxsj+`</span></p>
								<div class="layui-progress layuiProgress">
									<div class="layui-progress-bar layui-bg-blue" lay-percent="`+res.data.bxsjL+`%"></div>
									<div class="layui-progress-text">`+res.data.bxsjL+`%</div>
								</div>`;
                $("#sjlx").html(str3);
                layui.use([ 'element'], function() { //独立版的layer无需执行这一句
                    var element = layui.element;
                    element.init()
                });
            }
        }
    })

    //巡检任务
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectXjrwCount"+urlParams,
        success : function(res) {
            $("#xjrw").html("");
            if (res.code == 0) {
                var str = '<dl class="total" onclick="getXjrw(sqid,xqid,\'\',\'\')">'
                    + '<dt><img src="images/zhzl_icon4.png" alt="" /></dt>'
                    + '<dd>'
                    + '<h5 class="numberFont font_30 color_white">'+res.data.rwzs+'</h5>'
                    + '<h6 class="color_yellow font_14">任务总数</h6>'
                    + '</dd>'
                    + '</dl>'
                    + '<ul class="list">'
                    + '<li id="xjrwRightOpen" onclick="getXjrw(sqid,xqid,\'未巡\',\'\')">'
                    + ' 末巡 <span class="number font_22 color_navy_blue numberFont">'+res.data.wx+'</span>'
                    + '</li>'
                    + '<li onclick="getXjrw(sqid,xqid,\'在巡\',\'\')">'
                    + '在巡 <span class="number font_22 color_navy_blue numberFont">'+res.data.zx+'</span>'
                    + '</li>'
                    + '<li onclick="getXjrw(sqid,xqid,\'已巡\',\'\')">'
                    + '已巡 <span class="number font_22 color_navy_blue numberFont">'+res.data.yx+'</span>'
                    + '</li>'
                    + '</ul>';
                $("#xjrw").html(str);
            }
        }
    })

}


//人员列表
// featherCmsScript.register({
//     element : "",
//     onLoad : function(cmsOptions) {
//
//
//     },
//     onResize : function(cmsOptions) {},
//     onClick : function(cmsOptions, _this) {
//
//     }
// });
function getZdry(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj,name) {
    setTimeout(function () {
        dwName(name)
    },500)
    featherCmsScript._map.clearPic();
    getZdRyList(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj,'')

    //重点人员分布图
    getZdryFb(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj)
    //年龄
    getZdNl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj)
    //男女比例
    getZdNnBl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj)
    //民族比例
    getZdMzBl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj)
}
// 根据名称定位坐标
function dwName(name) {
    var cdArr=[];
    var resArr=null;
    $.ajax({
    url : featherCmsScript.ctx + 'bus/smartcommunity/json/莱茵风尚楼层及户定位.json',
        async:false,
        success : function(res) {
            resArr = res
        }
    })
    switch(name) {
        case '独居老人':
            cdArr = [resArr.features[0].geometry.rings[0]]
            fn(cdArr)
            break;
        case '空巢老人':
            cdArr = [resArr.features[1].geometry.rings[0]]
            fn(cdArr)
            break;
        case '低保人员':
            cdArr = [resArr.features[2].geometry.rings[0]]
            fn(cdArr)
            break;
        case '残疾人员':
            cdArr = [resArr.features[3].geometry.rings[0]]
            fn(cdArr)
            break;
        case '退役军人':
            cdArr = [resArr.features[4].geometry.rings[0]]
            fn(cdArr)
            break;
        case '刑满释放':
            cdArr = [resArr.features[5].geometry.rings[0]]
            fn(cdArr)
            break;
    }
    function fn(e) {
        var geo = {
            "type": "Feature",
            "geometry": {
                "type": "Polygon",
                "coordinates": e
            },
        }
        featherCmsScript._map.localPicName(geo);
    }
}
//搜索重点人员事件和巡检任务列表
function searchLB(lb) {
    if (lb == 'ZDRY'){
        var zdry = $("#searchZdry").val();
        getZdRyList(sqid,xqid,"","","","","","",zdry)
    } else if (lb == 'ZDSJ'){
        var zdsj = $("#searchZdsj").val();
        getZdsjList(sqid,xqid,"",zdsj)
    }else if (lb == 'XJRW'){
        var xjrw = $("#searchXjrw").val();
        getXjrw(sqid,xqid,"",xjrw)
    }
};
function getXjrw(sqid,xqid,xczt,xjrw) {

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&xczt="+xczt+"&xjrw="+xjrw;
    $.ajax({
        url:featherCmsScript.ctx + "zhzl/api/getXjrw"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'xjrwPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('xjrwList').innerHTML = function () {
                                var arr = [];

                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {

                                    html1 += `<div class="layui-row colum" id="zdsjLayerOpen" onclick="getXjRwTk('`+item.WYXCID+`')">
                                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>` + (parseInt(index) + 1) + `</span>
                                            </div>
                                           <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">
                                            <span title="">`+ item.RWMC +`</span>
                                            </div>
                                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">`+ item.XM +`</span>
                                            </div>
                                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span title="">`+ item.XCZT +`</span>
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

    $('.rightContent').removeClass('CloseRight');
    $('.xjrwRightList').removeClass('hide');
    $('.zdryRightList,.zdsjRightList').addClass('hide');
    featherCmsScript._map.clearPic();

}

function getZdsjList(sqid,xqid,czzt,sjlx){

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&czzt="+czzt+"&sjlx="+sjlx;
    $.ajax({
        url:featherCmsScript.ctx + "zhzl/api/getZdsjList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'sjPage',
                        limit:20
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('sjList').innerHTML = function () {
                                var arr = [];

                                var html1 = '';
                                thisData = res.data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                                layui.each(thisData, function (index, item) {

                                    html1 += `<div class="layui-row colum" onclick="getZdSjTk('`+item.YCID+`');featherCmsScript._map.otherClick('`+ item.YCID +`','YC','zhzl')">
                                                <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                    <span>` + (parseInt(index) + 1) + `</span>
                                                </div>
                                                <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                    <span title="">` + item.SJLX + `</span>
                                                </div>
                                                <div class="layui-col-md6 layui-col-sm6 layui-col-xs6">
                                                    <span title="">`+ item.YCNR +`</span>
                                                </div>
                                                <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                                    <span title="">`+ item.CZZT +`</span>
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

    $('.rightContent').removeClass('CloseRight');
    $('.zdsjRightList').removeClass('hide');
    $('.zdryRightList,.xjrwRightList').addClass('hide');
    featherCmsScript._map.clearPic();

}


function getZdRyList(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj,zdry){
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfdj="+sfdj+"&sfkc="+sfkc+"&sfxmsf="+sfxmsf+"&sftyjr="+sftyjr+"&sfdb="+sfdb+"&sfcj="+sfcj+"&zdry="+zdry;
    $.ajax({
        url:featherCmsScript.ctx + "zhzl/api/getZdRyList"+urlParams,
        success:function (res) {
            if(res.code==0){
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    laypage.render({
                        elem: 'ryPage',
                        limit:10
                        ,count: res.data.length
                        ,jump: function(obj,first) {
                            document.getElementById('ryList').innerHTML = function () {
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
                                    a=sfdj+" "+sfkc+" "+sfxmsf+" "+sftyjr+" "+sfdb+" "+sfcj

                                    html1 += '<li class="layui-row" onclick="getZdRyTk(\''+ (parseInt(index) + 1) +'\',\''+item.JMID+'\');featherCmsScript._map.otherClick(\''+ item.LDID +'\',`RY`,`zhzl`)">'
                                        + '<span class="number numberFont">' + (parseInt(index) + 1) + '</span>'
                                        + '<div class="layui-col-md5">'
                                        + '<span class="font_14 color_green">姓名：</span><span class="font_14 color_white">' + item.XM + '</span>'
                                        + '</div>'
                                        + '<div class="layui-col-md4">'
                                        + '<span class="font_14 color_green">性别：</span><span class="font_14 color_white">' + item.XB + '</span>'
                                        + '</div>'
                                        + '<div class="layui-col-md3">'
                                        + '<span class="font_14 color_green">年龄：</span><span class="font_14 color_white"><i class="iconfont "></i>'+ item.NL + '</span>'
                                        + '</div>'
                                        + '<div class="layui-col-md12">'
                                        + '<span class="font_14 color_green">标签：</span><span class="font_14 color_white"><i class="iconfont iconrenyuan-shi"></i>'+ a+ '</span>'
                                        + '</div>'
                                        + '</li>';
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

    $('.rightContent').removeClass('CloseRight');
    $('.zhzlBottomContent').addClass('leftShrink');
    $('.zdryRightList').removeClass('hide');
    $('.zdsjRightList,.xjrwRightList').addClass('hide');
    featherCmsScript.trigger("resize");
}

/*手环信息js*/
var layerIndex;
//人员弹框
function getZdRyTk(index,jmid) {


    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getRyInfo?jmid=" + jmid,
        success: function (res) {
            if (res.code == 0) {
                var html = '';
                $("#personnelInfoLayer").html('');

                html += '<h4 class="moveTxt">' + res.data.XM + '</h4><div class="layerContent"><ul class="personnelInfoListLayer"><li></li>';
                html += '<li>';
                var age = Number(res.data.NL);
                if (res.data.XB == '男') {
                    var imgSrc = "img/1-19m.jpg";
                    if (age >= 60) {
                        imgSrc = "img/60m.jpg";
                    } else if (age < 60 && age >= 20) {
                        imgSrc = "img/20-59m.jpg";
                    }

                } else {
                    imgSrc = "img/1-19w.jpg";
                    if (age >= 60) {
                        imgSrc = "img/60w.png";
                    } else if (age < 60 && age >= 20) {
                        imgSrc = "img/20-59w.jpg";
                    }
                }
                //基本信息
                html += '<img class="img" src="'+imgSrc+'" alt="" />';
                html +=
                    '<div class="layui-row">'+
                    '   <div class="layui-col-md7"> '+
                            '<p><span class="color_blue">姓名：</span><span class="color_white">' + res.data.XM + '</span></p> '+
                            '<p><span class="color_blue">民族：</span><span class="color_white">' + res.data.MZ + '</span></p> '+
                            '<p><span class="color_blue">居住地址：</span><span class="color_white">' + res.data.ZZ + '</span></p> ' +
                        '</div> '+
                        '<div class="layui-col-md5"> '+
                            '<p><span class="color_blue">性别：</span><span class="color_white">' + res.data.XB + '</span></p> '+
                            '<p><span class="color_blue">年龄：</span><span class="color_white">' + res.data.NL + '</span></p> '+
                            '<p><span class="color_blue">电话：</span><span class="color_white">'+ res.data.DH + '</span></p> '+
                        '</div> '+
                        '<div class="layui-col-md12"> '+
                            '<p><span class="color_blue">标签：</span><span class="color_white">'
                if (res.data.SFDJ == '是') {

                    html += '<i class="iconfont iconlaoren color_yellow"></i>';
                }
                if (res.data.SFKC == '是') {
                    html += '<i class="iconfont iconlaoren color_red"></i>';
                }
                if (res.data.SFDB == '是') {
                    html += '<i class="iconfont iconrenyuan-shi color_blue"></i>';
                }
                if (res.data.SFCJ == '是') {
                    html += '<i class="iconfont iconcanjiren color_yellow"></i>';
                }
                if (res.data.SFTYJR == '是') {
                    html += '<i class="iconfont iconjunren color_green"></i>';
                }
                if (res.data.SFXMSF == '是') {
                    html += '<i class="iconfont iconren color_red"></i>';
                }
                html += '</span></p></div>';
                //基本信息结束
                //智能手环
                html +='<div class="layui-col-md12 wristband"> ' +
                    '           </div>' +
                    '           <div>' +
                    '               <div class="layui-row">' +
                    '                   <div class="layui-col-md7">' +
                    '                       <p><span class="color_blue" >温度：</span><span class="color_white" id="sbattr1">67</span></p>' +
                    '                   </div>' +
                    '                   <div class="layui-col-md5">' +
                    '                       <p><span class="color_blue" >心率：</span><span class="color_white" id="sbattr2">120</span></p>' +
                    '                   </div>' +
                    '               </div>' +
                    '               <div class="layui-row">' +
                    '                   <div class="layui-col-md7">' +
                    '                       <p><span class="color_blue" >血氧：</span><span class="color_white" id="sbattr3">96</span></p>' +
                    '                   </div>' +
                    '                   <div class="layui-col-md5">' +
                    '                       <p><span class="color_blue" >血糖：</span><span class="color_white" id="sbattr4">6</span></p>' +
                    '                   </div>' +
                    '               </div>' +
                    '               <div class="layui-row">' +
                    '                   <div class="layui-col-md5">' +
                    '                       <p><span class="color_blue" >时间：</span><span class="color_white" id="sbattr5">2020-08-31 09:41:22</span></p>' +
                    '                   </div>' +
                    '                </div>';
                //手环结束

                //定位信息
                html +=' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >定位：</span><span class="color_white" id="sbattr6">36.253,113.656</span></p>' +
                            '</div>' +
                            '<div class="layui-col-md5">' +
                                '<p><span class="color_blue" >时间：</span><span class="color_white" id="sbattr7">2020-08-30 09:41:34</span></p>' +
                            '</div>' +
                        '</div>' ;
                //定位信息结束

                //定位信息
                html +=' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >电量：</span><span class="color_white" id="sbattr8">36.253,113.656</span></p>' +
                            '</div>' +
                            '<div class="layui-col-md5">' +
                                '<p><span class="color_blue" >步数：</span><span class="color_white" id="sbattr9">2020-08-30 09:41:34</span></p>' +
                            '</div>' +
                        '</div>' +
                        ' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >温度计电量：</span><span class="color_white" id="sbattr10">36.253,113.656</span></p>' +
                            '</div>' +
                            '<div class="layui-col-md5">' +
                                '<p><span class="color_blue" >时间：</span><span class="color_white" id="sbattr11">2020-08-30 09:41:34</span></p>' +
                            '</div>' +
                        '</div>';
                //定位信息结束


                //报警信息
                html +=' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >报警类型：</span><span class="color_white" id="sbattr12">跌倒报警</span></p>' +
                            '</div>' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >报警定位：</span><span class="color_white" id="sbattr13">36.253,113.656</span></p>' +
                            '</div>' +
                        '</div>' +
                        ' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >报警内容：</span><span class="color_white" id="sbattr14">跌倒</span></p>' +
                            '</div>' +
                        '</div>' +
                        ' <div class="layui-row">' +
                            '<div class="layui-col-md7">' +
                                '<p><span class="color_blue" >报警时间：</span><span class="color_white" id="sbattr15">2020-08-30 09:41:34</span></p>' +
                            '</div>' +
                        '</div>';
                //报警信息结束

                html +='   </div>' ;
                //结尾
                html +='</div></li> </ul></div>';
                $("#personnelInfoLayer").html(html);
                //心率血压血氧血糖
                getBraceletInfo(jmid);
                //定位
                getLocation(jmid);
                //设备电量/记步数/温度计电量
                getBeatHeart(jmid);
                //报警信息
                getWarringInfo(jmid);

                // 定时刷新手环信息
                var time = setInterval(function() {
                    getBraceletInfo(jmid);
                    getLocation(jmid);
                    getBeatHeart(jmid);
                    getWarringInfo(jmid);
                }, 10000);  //10s更新一次手环信息

            }
        }
    });


    layerIndex = layer.open({
        type: 1,
        title: false,
        offset:'15%',
        area: ['500px', '360px'],
        move: '.moveTxt',
        scrollbar: false,
        shade: 0,
        //fix: false,
        content: $('#personnelInfoLayer'),
        skin: 'layer-style',
        id: 'personnelInfoLayerOpenBox_'+jmid,
        cancel: function (index, layero) {
            featherCmsScript._map.clear();
        }
    });

    //户定位
    flyToposition();
}

/**
 * 获取定位信息
 * @param jmid
 */
function getLocation(jmid){
    $.get(featherCmsScript.ctx + "sqgl/api/getLocation?jmid=" + jmid,function(result){
        if(result.code == 0){
            $("#sbattr6").text(result.data.lat+","+result.data.lon);
            if(timedis(result.data.createDate)){
                $("#sbattr7").addClass("color_red");
                $("#sbattr7").removeClass("color_white");
            }else{
                $("#sbattr7").addClass("color_white");
                $("#sbattr7").removeClass("color_red");
            }
            $("#sbattr7").text(result.data.createDate);
        }
    });
}

/**
 * 判断时间间隔
 * @param start
 * @returns {boolean}
 */
function timedis(start){
    var date2 = new Date();    //结束时间
    var date3 = date2.getTime() - new Date(start).getTime();   //时间差的毫秒数
    var leave1=date3%(24*3600*1000);
    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000));
    //时间间隔大于4分钟，时间变为红色
    if(minutes>4){
        return true;
    }
    return false;
}
/**
 * 获取设备信息
 * @param jmid
 */
function getBeatHeart(jmid){
    $.get(featherCmsScript.ctx + "sqgl/api/getBreatHeartInfo?jmid=" + jmid,function(result){
        if(result.code == 0){
            $("#sbattr8").text(result.data.battery);
            $("#sbattr9").text(result.data.pedometer);
            $("#sbattr10").text(result.data.jtmBattery );
            if(timedis(result.data.createDate)){
                $("#sbattr11").addClass("color_red");
                $("#sbattr11").removeClass("color_white");
            }else{
                $("#sbattr11").addClass("color_white");
                $("#sbattr11").removeClass("color_red");
            }
            $("#sbattr11").text(result.data.createDate);

        }
    });
}

/**
 * 请求手环信息
 * @param jmid
 */
function getBraceletInfo(jmid){
    $.get(featherCmsScript.ctx + "sqgl/api/getBraceletInfo?jmid=" + jmid,function(result){
        if(result.code == 0){
            $("#sbattr1").text(result.data.temperature);
            $("#sbattr2").text(result.data.heartRate);
            $("#sbattr3").text(result.data.oxygen);
            $("#sbattr4").text(result.data.bloodSugar);
            if(timedis(result.data.createDate)){
                $("#sbattr5").addClass("color_red");
                $("#sbattr5").removeClass("color_white");
            }else{
                $("#sbattr5").addClass("color_white");
                $("#sbattr5").removeClass("color_red");
            }
            $("#sbattr5").text(result.data.createDate);
        }
    });
}

/**
 * 获取报警信息
 * @param jmid
 */
function getWarringInfo(jmid){
    $.get(featherCmsScript.ctx + "sqgl/api/getWarringInfo?jmid=" + jmid,function(result){
        if(result.code == 0){
            var lat ='36.253';
            var lon = '113.656'
            if(result.data.lat != undefined){
                lat = result.data.lat;
            }
            if(result.data.lon != undefined){
                lon = result.data.lon;
            }
             var location = lat +','+lon;
            var warringTypeCon = "";
            if(result.data.warningType == 2){
                warringTypeCon = "摔倒";
            }
            $("#sbattr12").text(warringTypeCon);
            $("#sbattr13").text(result.data.content);
            $("#sbattr14").text(location);
            if(timedis(result.data.createDate)){
                $("#sbattr15").addClass("color_red");
                $("#sbattr15").removeClass("color_white");
            }else{
                $("#sbattr15").addClass("color_white");
                $("#sbattr15").removeClass("color_red");
            }
            $("#sbattr15").text(result.data.createDate);

        }
    });
}
// 点击手环按钮展开详情
function wristbandDetailBtn(_this){
    if (!$('#'+_this.id).hasClass('active')) {
        $(_this).addClass('active');
        $('.wristbandDetailBox').show();
        layer.style(layerIndex, {
            height: '270px'
        });
        // //定时刷新手环信息
        // var time = setInterval(function() {
        //     timeUpdateBraceletInfo();
        // }, 10000);  //10s更新一次手环信息
    } else {
        $(_this).removeClass('active');
        $('.wristbandDetailBox').hide();
        layer.style(layerIndex, {
            height: '190px'
        });
    }
}
// //弹框
//     layer.open({
//         type : 1,
//         title : false,
//         area : [ '400px', '180px' ],
//         move : '.moveTxt',
//         scrollbar : false,
//         shade : 0,
//         //fix: false,
//         content : $('#personnelInfoLayer'),
//         skin : 'layer-style',
//         id : 'personnelInfoLayerOpenBox',
//         cancel:function(index, layero){
//             featherCmsScript._map.clear();
//         }
//     });
// }



//重点事件弹框
function getZdSjTk(ycid){
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getZdsjInfo?ycid="+ycid,
        success: function (res) {
            if (res.code == 0) {
                var html = '';
                $("#abnormalLayer").html('');

                html+=`<h4 class="moveTxt">`+res.data.SJLX+`</h4>
                        <div class="layerContent">
                            <div class="abnormalForm">
                                <ul>
                                    <li><label class="color_blue">异常内容：</label><span>`+res.data.YCNR+`</span></li>`
                var ycjb = res.data.YCJB;
                if(ycjb=='绿'){
                    html+=`<li><label class="color_blue">异常级别：</label><span class="color_red"><i class="iconfont iconbaojing"></i>三级</span></li>`
                }else if(ycjb=='黄'){
                    html+=`<li><label class="color_blue">异常级别：</label><span class="color_red"><i class="iconfont iconbaojing"></i>二级</span></li>`
                }else if(ycjb=='红'){
                    html+=`<li><label class="color_blue">异常级别：</label><span class="color_red"><i class="iconfont iconbaojing"></i>一级</span></li>`
                }
                html+=` <li><label class="color_blue">异常时间：</label><span>`+res.data.YCSJ+`</span></li>
                                    <li><label class="color_blue">处置状态：</label><span class="color_yellow">`+res.data.CZZT+`</span></li>`
                var czzt = res.data.CZZT;
                if (czzt=='已处置'||czzt=='处置中'){
                    html+=` <li><label class="color_blue">处置人员：</label><span>`+res.data.CZRY+`</span></li>
                        <li><label class="color_blue">处置时间：</label><span>`+res.data.CZRY+`</span></li>
                        <li><label class="color_blue">处置结果：</label><span>`+res.data.CZJG+`</span></li>`
                }

                html+=`</ul>
                                <img width="100%" class="img mt_10" src="images/video_img1.png" alt="" />
                            </div>
                        </div>`

                if (czzt=='未处置'){
                    html+=`<span class="btn pa layerBottomBtn">派发任务</span>`
                }
                $("#abnormalLayer").html(html);
            }
        }
    });

//弹框
    layer.open({
        type : 1,
        title : false,
        area : [ '300px', '380px' ],
        move : '.moveTxt',
        scrollbar : false,
        shade : 0,
        //fix: false,
        content : $('#abnormalLayer'),
        skin : 'layer-style',
        id : 'dzzLayerBox',
        cancel:function(index, layero){
            featherCmsScript._map.clear();
        }
    });
}

//巡检任务弹框
function getXjRwTk(wyxcid){
    $.ajax({
        url: featherCmsScript.ctx + "sqgl/api/getXjrwInfo?wyxcid="+wyxcid,
        success: function (res) {
            if (res.code == 0) {
                featherCmsScript._map.zbInfo = res.data.zbInfo;
                var html = '';
                $("#xjrwLayer").html('');
                html+=`<h4 class="moveTxt">巡检详情</h4>
                        <div class="layerContent">
                            <div class="abnormalForm">
                                <ul>
                                    <li><label class="color_blue">巡检名称：</label><span>`+res.data.RWMC+`</span></li>
                                    <li><label class="color_blue">巡检时间：</label><span>`+res.data.SJ+`</span></li>
                                    <li><label class="color_blue">巡检状态：</label><span>`+res.data.XCZT+`</span></li>
                                    <li><label class="color_blue">是否异常：</label><span>`+res.data.SFYC+`</span></li>
                                    <li><label class="color_blue">巡检记录：</label><span class="color_orange">从大门开始巡检</span></li>
                                    <li><label class="color_blue">巡检附件：</label><span>无</span></li>
                                </ul>
                                <img width="100%" class="img mt_10" src="images/video_img1.png" alt="" />`
                var xczt = res.data.XCZT;
                if (xczt != '未巡'){
                    html+=`<span class="zs-abnormalForm" onclick="featherCmsScript._map.routeWay();">查看轨迹</span>`
                }
                             html+=`</div>
                        </div>`
                $("#xjrwLayer").html(html);
            }
        }
    });

//弹框
    layer.open({
        type : 1,
        title : false,
        area : [ '320px', '400px' ],
        move : '.moveTxt',
        scrollbar : false,
        shade : 0,
        fix: false,
        content : $('#xjrwLayer'),
        skin : 'layer-style',
        id : 'doneLayerBox',
        cancel:function(){
            trackPlayback.stop();
        }
    });
}


// 重点人员分布TOP5
featherCmsScript.register({
    element : "#staffDistribution",
    onLoad : function(cmsOptions) {
        getZdryFb('SQ000001','','','','','','','')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function getZdryFb(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj){
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0;
    if(_this.startCharts){
        clearInterval(_this.startCharts)
    }
    var num;
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfdj="+sfdj+"&sfkc="+sfkc+"&sfxmsf="+sfxmsf+"&sftyjr="+sftyjr+"&sfdb="+sfdb+"&sfcj="+sfcj;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/getZdryFb"+urlParams,
        async:false,
        success : function(res) {
            $("#zdryfb").html("");
            if (res.code == 0) {
                var html='';
                for(var i in res.data) {
                    html += '<p>' + res.data[i].LDMC + '<span class="fr">' + res.data[i].MUN + '人</span></p>';
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].LDMC+""};
                    xdata.push(json);
                }
                $("#zdryfb").html(html);
            }
        }
    })

    var ryxjEcharts = echarts.init(document.getElementById('staffDistribution'));
    //cmsOptions.object = ryxjEchart;
    var legend_data = ['10栋', '11栋', '12栋', '13栋', '14栋'];

    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)",
            confine:true
        },
        series : [ {
            name : '重点人员占比统计',
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
        color : [ '#49da97', '#26addd', '#e87d2b','#f6aa23', '#fee67c']
    };

    ryxjEcharts.setOption(option);

    // 2、鼠标移动上去的时候的高亮动画
    ryxjEcharts.on('mouseover', function(param) {
        isSet = false;
        clearInterval(_this.startCharts);
        // 取消之前高亮的图形
        ryxjEcharts.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        });
        // 高亮当前图形
        ryxjEcharts.dispatchAction({
            type : 'highlight',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        });
        // 显示 tooltip
        ryxjEcharts.dispatchAction({
            type : 'showTip',
            seriesIndex : 0,
            dataIndex : param.dataIndex
        })
    });
    // 3、自动高亮展示
    var chartHover = function() {
        var dataLen = option.series[0].data.length
        num = charPie3currentIndex;
        // 取消之前高亮的图形
        ryxjEcharts.dispatchAction({
            type : 'downplay',
            seriesIndex : 0,
            dataIndex : charPie3currentIndex
        })
       /* charPie3currentIndex = charPie3currentIndex + 1;
        if(charPie3currentIndex == legend_data.length){
            charPie3currentIndex = 0;
        }*/
        charPie3currentIndex = (charPie3currentIndex + 1) % legend_data.length
        // 高亮当前图形
        ryxjEcharts.dispatchAction({
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
    ryxjEcharts.on('mouseout', function(param) {
        if (!isSet) {
            clearInterval(_this.startCharts);
            _this.startCharts = setInterval(chartHover, 2000)
            isSet = true
        }
    });
}

// 年龄结构
featherCmsScript.register({
    element : "#ageStructure",
    onLoad : function(cmsOptions) {
        getZdNl('SQ000001','','','','','','','')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function getZdNl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj){
    var houseStatisticsDom = document.getElementById("ageStructure");
    var houseStatisticsCharts = echarts.init(houseStatisticsDom);
    //cmsOptions.object = houseStatisticsCharts;

    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfdj="+sfdj+"&sfkc="+sfkc+"&sfxmsf="+sfxmsf+"&sftyjr="+sftyjr+"&sfdb="+sfdb+"&sfcj="+sfcj;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/getZdNl"+urlParams,
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
        getZdNnBl('SQ000001','','','','','','','')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function getZdNnBl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj){
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    if(_this.startCharts){
        clearInterval(_this.startCharts)
    }
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfdj="+sfdj+"&sfkc="+sfkc+"&sfxmsf="+sfxmsf+"&sftyjr="+sftyjr+"&sfdb="+sfdb+"&sfcj="+sfcj;
    var xdata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/getZdNnBl"+urlParams,
        async:false,
        success : function(res) {
            $("#nnrs").html("");
            if (res.code == 0) {
                var html='';
                html += '<p class="woman"><i class="iconfont iconwomen"></i>' + res.data[0].XB + ' <span class="fr color_white numberFont font_30">' + res.data[0].MUN + '</span></p>\n' +
                    '<p class="color_blue man"><i class="iconfont iconnancesuo"></i>' + res.data[1].XB + '  <span class="fr color_white numberFont font_30">' + res.data[1].MUN + '</span></p>'
                for(var i in res.data) {
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].XB+""};
                    xdata.push(json);
                }
                $("#nnrs").html(html);
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
    //4、鼠标移出之后，恢复自动高亮
    ryxjEchart.on('mouseout', function(param) {
        if (!isSet) {
            clearInterval(_this.startCharts);
            _this.startCharts = setInterval(chartHover, 2000)
            isSet = true
        }
    });
}

// 民族比例
featherCmsScript.register({
    element : "#MZScale",
    onLoad : function(cmsOptions) {
        getZdMzBl('SQ000001','','','','','','','')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function getZdMzBl(sqid,xqid,sfdj,sfkc,sfxmsf,sftyjr,sfdb,sfcj){
    var ryxjEchart = echarts.init(document.getElementById('MZScale'));
    //cmsOptions.object = ryxjEchart;
    var urlParams ="?xqid="+xqid+"&sqid="+sqid+"&sfdj="+sfdj+"&sfkc="+sfkc+"&sfxmsf="+sfxmsf+"&sftyjr="+sftyjr+"&sfdb="+sfdb+"&sfcj="+sfcj;
    var xdata=[];
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/getZdMzBl"+urlParams,
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

// 点击左侧重点人员内容右侧显示
featherCmsScript.register({
    element : "#livingAloneBtn",
    onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
        $('.rightContent').removeClass('CloseRight');
        $('.zhzlBottomContent').addClass('leftShrink');
        $('.zdryRightList').removeClass('hide');
        $('.zdsjRightList,.xjrwRightList').addClass('hide');
        featherCmsScript.trigger("resize");
    }
});

// 点击左侧重点事件内容右侧显示
featherCmsScript.register({
    element : "#zdsjRightOpen",
    onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
        $('.rightContent').removeClass('CloseRight');
        $('.zdsjRightList').removeClass('hide');
        $('.zdryRightList,.xjrwRightList').addClass('hide');
    }
});

// 点击左侧巡检任务内容右侧显示
featherCmsScript.register({
    element : "#xjrwRightOpen",
    onClick : function(cmsOptions, _this) {
//		$(_this).addClass('active');
        $('.rightContent').removeClass('CloseRight');
        $('.xjrwRightList').removeClass('hide');
        $('.zdryRightList,.zdsjRightList').addClass('hide');
    }
});

// 点击子导航--重点人员
featherCmsScript.register({
    element : "#zdrySubNav",
    onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
        $(_this).addClass('active').siblings("li").removeClass("active");
        // $('.rightContent').addClass('CloseRight');
        // $('.zhzlBottomContent').removeClass('leftShrink')
        $('.zdryLeftBox,.zhzlBottomContent').removeClass('hide');
        $('.zdsjLeftBox,.xjrwLeftBox').addClass('hide');
        //featherCmsScript.trigger("resize");
        $('.rightContent').addClass('CloseRight');
        $('.zhzlBottomContent').removeClass('leftShrink');
    }
});

// 点击子导航--重点事件
featherCmsScript.register({
    element : "#zdsjSubNav",
    onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
        $(_this).addClass('active').siblings("li").removeClass("active");
        //$('.rightContent').addClass('CloseRight');
        $('.zdsjLeftBox').removeClass('hide');
        $('.zdryLeftBox,.xjrwLeftBox,.zhzlBottomContent').addClass('hide');
        $('.rightContent').addClass('CloseRight');

    }
});

// 点击子导航--巡检任务
featherCmsScript.register({
    element : "#xjrwSubNav",
    onClick : function(cmsOptions, _this) {
        featherCmsScript._map.clearPic();
        $(_this).addClass('active').siblings("li").removeClass("active");
        //$('.rightContent').addClass('CloseRight');
        $('.xjrwLeftBox').removeClass('hide');
        $('.zdryLeftBox,.zdsjLeftBox,.zhzlBottomContent').addClass('hide');
        $('.rightContent').addClass('CloseRight');

    }
});

// 点击左侧内容右侧显示
featherCmsScript.register({
    element : "#zhzlRightCloseBtn",
    onClick : function(cmsOptions, _this) {
        $('.rightContent').addClass('CloseRight');
        $('.zhzlBottomContent').removeClass('leftShrink');
        featherCmsScript.trigger("resize");
    }
});

// 事件类型
featherCmsScript.register({
    element : "#sjlxEchart",
    onLoad : function(cmsOptions) {
        selectZdsjCount('SQ000001','')
    },
    onResize : function(cmsOptions) {
        //cmsOptions.object.resize();
    },
    onClick : function(cmsOptions, _this) {}
});

function selectZdsjCount(sqid,xqid) {
    var _this = this
    var isSet = true // 为了做判断：当鼠标移动上去的时候，自动高亮就被取消
    var charPie3currentIndex = 0
    if(_this.startCharts){
        clearInterval(_this.startCharts)
    }

    var urlParams ="?xqid="+xqid+"&sqid="+sqid;
    var ydata=[];
    $.ajax({
        url : featherCmsScript.ctx + "zhzl/api/selectZdsjTCount"+urlParams,
        async:false,
        success : function(res) {
            if (res.code == 0) {
                for(var i in res.data) {
                    var json = {value:""+res.data[i].MUN+"",name:""+res.data[i].SJLX+""};
                    ydata.push(json);
                }
            }
        }
    })

    var ryxjEchart = echarts.init(document.getElementById('sjlxEchart'));
    //cmsOptions.object = ryxjEchart;
    var legend_data = [ '治安事件', '卫生事件','能源事件','矛盾纠纷','报修事件'];
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b}: {c} ({d}%)",
            confine:true
        },
        series : [ {
            name : '事件类型',
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
            data : ydata
        } ],
        color : [ '#4ed696', '#23aed9','#f7924e','#fde577']
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
