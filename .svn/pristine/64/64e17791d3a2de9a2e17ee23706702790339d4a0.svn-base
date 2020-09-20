var YJCZ = ModelManager["YJCZ"] = {
    element :null,
    table:'',
    treeNodeName:'',
    config: {
        appKey: "28852076",
        appSecret: "SGt7HK1LiTPyW4NVRUpk",
        hostIp: "10.163.132.200",
        defaultCameraIndexCode: "ec21b4971eab40d2897914986614d952"
    },
    oWebControl: null,
    data: {
        currMenuValue:'',
        jsonObj:'',
        yczname:'',
        iconid:'',
        yjczTotal0:'',
        yjcztotal1:'',
        curr:'',
        counts:0,
        yjcontent:'',

        ChartsName: [],
        type:'泵房'
    },
    init: function (name,currMenuValue) {
        //改变全局搜索的默认值
        objectType = 'subject';
        $(".searchLabel").find("input").eq(1)[0].checked = true;
        //参数name：二级菜单名（如：预警报警、历史预警、预警统计）,它等于 currMenuName
        //         为空时表示是通过一级菜单打开
        YJCZ.currMenuValue=currMenuValue;

        if ($('.leftBtn').hasClass('active')) {
            $('.leftBtn').removeClass('active');
            $(".leftBox").animate({left:"0"});
        }

        setTimeout(function(){
            menuChangeInit();
            YJCZ.initpage1();
            YJCZ.init2();
            var layerType = currModelName.toLowerCase();
            YJCZ.clickPic();
        },2000);


    },

    initpage1: function () {
        $("#left-panel").html('');
        $("#left-panel").load(ctx + 'screen/yjcz/bjtj', function (data) {
            YJCZ.getbjxx("");
            YJCZ.getPage();//分页
            YJCZ.getPage1();//分页
            //layerTree.initTree(jsonObj, "yjcz");
            YJCZ.getinfo();
        });

        $("#right-panel").html('');

        $.ajax({
            url: ctx + 'screen/yjcz/bjxq',
            type: 'post',
            async: false,
            success: function (data) {
                if(document.getElementsByClassName("bjxxMoreContent").length==0){
                    $(".contentBox.insidePageBox.pr").append(data);
                }

            }
        })

    },
    getinfo:function (itid) {

        if(YJCZ.currMenuValue!=undefined&&YJCZ.currMenuValue!=''){

            $.ajax({
                url:ctx + 'screen/yjcz/getmiddleBox?itid=' + YJCZ.currMenuValue,
                type:'POST',
                async:false,
                success:function(res){
                    if(res[0].formSysName!='探漏'){
                        YJCZ.getyjxq(res[0].id,res[0].formSysName);
                        YJCZ.rightBoxShow(res[0].formSysName);
                    }
                    YJCZ.yjczname = res[0].formSysName;
                    YJCZ.middleBoxShow(res[0].formSysName,res[0].id,res[0].code);

                }
            })
        }
    },
    getAllinfo:function(itid){
        $.ajax({
            url:ctx + 'screen/yjcz/getmiddleBox?itid=' + itid,
            type:'POST',
            async:false,
            success:function(res){
                if(res[0].formSysName!='探漏'){
                    YJCZ.getyjxq(res[0].id,res[0].formSysName);
                    YJCZ.rightBoxShow(res[0].formSysName);
                }

                YJCZ.middleBoxShow(res[0].formSysName,res[0].id,res[0].code);

            }
        })
    },
    getLzfx:function(itid){
        $("#bottom-panel").html("");
        $.ajax({
            url: ctx + 'screen/yjcz/lzfx?itid='+itid,
            type: 'post',
            async: false,
            success: function (data) {
                $("#bottom-panel").html(data);
                //YJCZ.init1();
                //YJCZ.getList();
            }
        })
    },
    getbjxx: function (yjczname, iconid,page,limit,bjxx,yjcontent) {
        //清空右侧
        if (YJCZ.oWebControl != null) {
            //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
            YJCZ.oWebControl.JS_HideWnd();
        }
        if(yjcontent==undefined){
            yjcontent='';
        }
        $(".pa.mapTxtBox.mapTxtLayer.yjcz").html("");
        YJCZ.yjczname=yjczname;
        YJCZ.iconid = iconid;
        YJCZ.getListhidden();
        $(".rightBox").animate({right: "-18vw"});
        if(YJCZ.yjczname!=""){
            $(".layui-col-md4.layui-col-sm4.pr.sbtjProgress.progressTxt").removeClass("active");
        }
        YJCZ.type=yjczname;
        //切换模块时，关闭layer弹窗
        layer.closeAll();
        //关闭视频窗口
        Hik.uninit();
        $("#" + YJCZ.iconid).addClass("active");
        $.ajax({
            url: ctx + 'screen/yjcz/bjxx?formSysName=' + YJCZ.yjczname+'&bjxx='+bjxx+'&content='+yjcontent+'&pageNum='+page+'&pageSize='+limit,
            type: 'post',
            async: false,
            success: function (data) {
                $("#bjxx").html(data);
                YJCZ.yjczTotal0 = $("#bjxxstate0").attr("value");
                YJCZ.yjczTotal1 = $("#bjxxstate1").attr("value");
                YJCZ.counts=0;
               YJCZ.change1(bjxx);
               if(page==undefined){
                   YJCZ.getPage();
                   YJCZ.getPage1();
               }
              // YJCZ.loginLight();
                //勾选对应图层
                setTimeout(function(){
                    YJCZ.selectLayer(YJCZ.yjczname);
                },200)
            }
        })
    },
    searchContent:function (bjxx) {
        var yjcontent='';
        if(bjxx=='bjxx0'){
          yjcontent =$("#yjContent0").val();
        }else{
            yjcontent =$("#yjContent1").val();
        }
        $.ajax({
            url: ctx + 'screen/yjcz/bjxx?formSysName=' + YJCZ.yjczname+'&bjxx='+bjxx+'&content='+yjcontent+'&pageNum='+page+'&pageSize='+limit,
            type: 'post',
            async: false,
            success: function (data) {
                $("#bjxx").html(data);
                if(bjxx=='bjxx0'){
                    $("#yjContent0").val(yjcontent);
                }else{
                    $("#yjContent1").val(yjcontent);
                }
                YJCZ.yjczTotal0 = $("#bjxxstate0").attr("value");
                YJCZ.yjczTotal1 = $("#bjxxstate1").attr("value");
                YJCZ.counts=0;
                YJCZ.change1(bjxx);
                    YJCZ.getPage(yjcontent);
                    YJCZ.getPage1(yjcontent);

                // YJCZ.loginLight();
            }
        })
    },

    //勾选图层的判断
    selectLayer: function (name) {
        layerTree.removeLocatedBuilding();

        switch (name) {
            case "泵房":
                selectedLinePipe(layerCfg.yjcz.bHouse);
                break;
            case "路灯":
                selectedLinePipe(layerCfg.yjcz.light);
                break;
            case "探漏":
                selectedLinePipe(layerCfg.yjcz.leak);
                break;
            case "水电":
                selectedLinePipe(layerCfg.yjcz.electricity);
                break;
            case "水表":
                selectedLinePipe(layerCfg.yjcz.water);
                break;
            case "巡检":
                selectedLinePipe(layerCfg.yjcz.look);
                break;
            case "监控":
                selectedLinePipe(layerCfg.yjcz.monitor);
                break;
            case "水质":
                selectedLinePipe(layerCfg.yjcz.waterQuality);
                break;
            case "全部":
                selectedLinePipe(layerCfg.yjcz.all);
                break;
            default:
                selectedLinePipes(layerCfg.yjcz.all);
                break;
        }


    },
    loginLight:function(){
        $.ajax({
            url: ctx + 'screen/yjcz/loginLight',
            type: 'post',
            async: true,
            success: function (data) {
            }
        })
    },
    change1:function(bjxx){
        if(YJCZ.counts==1){
            YJCZ.counts=0;
            return;
        }
        if(bjxx=='bjxx0'){
            YJCZ.counts++;
            $("#bjxx0").css('display','block');
            $("#bjxx1").css('display','none');
            $("#wcz").click();

        }else if(bjxx=='bjxx1'){
            YJCZ.counts++;
            $("#bjxx0").css('display','none');
            $("#bjxx1").css('display','block');
            $("#ycz").click();

        }
    },
    change:function(bjxx){
        if(YJCZ.counts==1){
            YJCZ.counts=0;
            return;
        }
        if(bjxx=='bjxx0'){
            YJCZ.counts++;
            YJCZ.getPage();
            $("#bjxx0").css('display','block');
            $("#bjxx1").css('display','none');
            $("#wcz").click();

        }else if(bjxx=='bjxx1'){
            YJCZ.counts++;
            YJCZ.getPage1();
            $("#bjxx0").css('display','none');
            $("#bjxx1").css('display','block');
            $("#ycz").click();

        }
    },
    getPage: function (content) {
        layui.use('laypage', function () {
            var laypage = layui.laypage;

            //执行一个laypage实例
            laypage.render({
                elem: 'bjxx0' //注意，这里的 test1 是 ID，不用加 # 号
                , count: YJCZ.yjczTotal0, //数据总数，从服务端得到
                limit: 10,   //每页条数设置
                skin:'nob',
                groups:3,
                prev: '<em><</em>',
                next: '<em>></em>',
                jump: function (obj, first) {
                    //obj包含了当前分页的所有参数，比如：
                    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    console.log(obj.limit); //得到每页显示的条数
                    page = obj.curr;  //改变当前页码
                    limit = obj.limit;
                    //首次不执行
                    if (!first) {
                        YJCZ.getbjxx(YJCZ.yjczname,YJCZ.iconid,page,limit,'bjxx0',content)  //加载数据
                    }
                }
            });
        })
    },
    getPage1: function (content) {
        layui.use('laypage', function () {
            var laypage = layui.laypage;

            //执行一个laypage实例
            laypage.render({
                elem: 'bjxx1' //注意，这里的 test1 是 ID，不用加 # 号
                , count: YJCZ.yjczTotal1, //数据总数，从服务端得到
                limit: 10,   //每页条数设置
                skin:'nob',
                prev: '<em><</em>',
                next: '<em>></em>',
                jump: function (obj, first) {
                    //obj包含了当前分页的所有参数，比如：
                    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    console.log(obj.limit); //得到每页显示的条数
                    page = obj.curr;  //改变当前页码
                    limit = obj.limit;
                    //首次不执行
                    if (!first) {
                        YJCZ.getbjxx(YJCZ.yjczname,YJCZ.iconid,page,limit,'bjxx1',content)  //加载数据
                    }
                }
            });
        })
    },




    getyjxq: function (itid,name,oid) {
        //当在全部的时候，点击对应的报警勾选相应的图层
        if(name=='探漏'){
            if(YJCZ.yjczname == ""){
                layerTree.removeLocatedBuilding();
                YJCZ.selectLayer(name);
            }
            if(oid==undefined){
                oid='';
            }
            $.ajax({
                url: ctx + 'screen/yjcz/yjxq?itid=' + itid+"&name="+name+"&oid="+oid,
                type: 'post',
                async: false,
                success: function (data) {
                    $("#right-panel").html(data);
                }
            })
        }
        if(name!='探漏'){
            if(YJCZ.yjczname == ""){
                layerTree.removeLocatedBuilding();
                YJCZ.selectLayer(name);
            }
            $.ajax({
                url: ctx + 'screen/yjcz/yjxq?itid=' + itid+"&name="+name+"&oid=''",
                type: 'post',
                async: false,
                success: function (data) {
                    $("#right-panel").html(data);
                }
            })
        }



    },
    middleBoxShow: function (name,itid,id) {
        $(".pa.mapTxtBox.mapTxtLayer.yjcz").html("");
        var m;
        $.ajax({
            url:ctx + 'screen/yjcz/getmiddleBox?itid=' + itid,
            type:'POST',
            async:false,
            success:function(res){
                m=res;
                var html='';
                //$("#middleBoxInfomation").html('');
                html+=`<div class="pr txt">
			<span class="mapCloseBtn pa" onclick="YJCZ.middleBoxhidden()"><i class="iconfont icon-guanbi"></i></span>
			<div class="topBg pa"></div>
			<div class="bottomBg pa"></div><div class="contentTxt" id="middleBoxInfomation">`;
                html+=`	<h2>设备编号：`+res[0].code+`</h2>
				<div class="lineBlue mh15"></div>
				<p>设备类型：<span>`+res[0].formSysName+`</span></p>
				<p>设备名称：<span>`+res[0].formSysName+`</span></p>
				<p>设备位置：<span>`+res[0].path+`</span></p>
				<p>设备原因：<span>`+res[0].name+`</span></p>
				<!--<div class="mapTxtBtn ph15 tabBtnGroup">-->
					<!--<button type="button" onclick="YJCZ.rightBoxShow1()">详细信息</button>-->
<!--
					<button type="button" onclick="YJCZ.yjhcLayuiBox()">预警核查</button>
-->
					`;
                /*if(res[0].name=='水表'){
                    html+= `<button  type="button"  onclick="YJCZ.getLzfx(`+res[0].id+`)">关阀停水</button>
					<button type="button" onclick="YJCZ.hcczLayuiBox()">关闭预警</button>
				     </div>`;
                }else if(res[0].name=='电表'){
                    html+= `<button  type="button"  onclick="YJCZ.getLzfx(`+res[0].id+`)">拉闸分析</button>
					<button type="button" onclick="YJCZ.hcczLayuiBox()">关闭预警</button>
				     </div>`;
                }else{
                    html+= `<button type="button" onclick="YJCZ.hcczLayuiBox()">关闭预警</button>
				     </div>`;
                }*/
                html+=`</div>`;
                $(".pa.mapTxtBox.mapTxtLayer.yjcz").html(html);
            }
        })

            var positionid='';
            $.ajax({
                url:ctx + 'screen/yjcz/getPosition?itid=' + itid+'&name='+name,
                type:'POST',
                async:false,
                success:function(res){
                    positionid=res;
                }
            })

        $("#yjxq").css("display", "none");
        $(".yjxqdetail").css("display", "none");
        $("#yjczd").css("display", "none");
        if (name == '水电') {
            $("#sb").css("display", "block");
            $.ajax({
                url:ctx + 'screen/yjcz/findListByPage?code=' + m[0].code,
                type:'POST',
                async:false,
                success:function(res){
                   var html='';
                   $("#sbxx").html('');
                   html+=`<li>时间：<span>`+res.readTime+`</span></li>
                    <li>电压值：<span>`+res.voltage+`V</span></li>`;
                    $("#sbxx").html(html);
                }
            })
            layerTree.locateBuildingByID(positionid.dingweiCode,"HOUSE_ANNOTATION");
           // YJCZ.syzsEcharts();     // 水压指数
           // $(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
        }else if(name == '电表'){
            layerTree.locateBuildingByID(positionid.Buildingno,"HOUSE_ANNOTATION");
            $("#db").css("display", "block");
           // YJCZ.dyzsEcharts();     // 水压指数
           // $(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
            $.ajax({
                url:ctx + 'screen/yjcz/findListByPage?path=' + m[0].path+'&typename=电表',
                type:'POST',
                async:false,
                success:function(res){
                    var html='';
                    $("#dbxx").html('');
                    html+=`<li>时间：<span>`+res.readTime+`</span></li>
                    <li>电压值：<span>`+res.voltage+`V</span></li>`;
                    $("#dbxx").html(html);
                }
            })

        } else if (name == '监控') {

            $("#sp").css("display", "block");
            $("#spxx").css("display", "none");
            $("#zptp").css("display", "none");
           // $(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
            if(positionid!=""){
                layerTree.locateBuildingByID(positionid.cameraIndexCode,"MONITOR");
                YJCZ.getCamera(positionid.cameraIndexCode);
                $("#spxx").css("display", "block");
                $("#zptp").css("display", "block");

            }
        } else if (name == '路灯') {
            $("#ld").css("display", "block");
            var lighturl='';
            if(positionid.lid==undefined){
                layerTree.locateBuildingByID(positionid.sid,"CONTROLLER");
                lighturl=ctx + 'screen/yjcz/getControlInfoByCuid?cuid=' + positionid.cuid+"&sid="+positionid.sid;
                $.ajax({
                    url: lighturl,
                    type: 'post',
                    async: true,
                    success: function (data) {
                        if(data!=null&&data!=""){
                            $("#ldsj").html("");
                            var html='';
                            html+=` <li><span class="clGreen">灯头：</span><span>`+data.poles+`</span></li>
                    <li><span class="clGreen">电流(A)：</span><span>`+data.it+`</span></li>
                    <li><span class="clGreen">电压(V)：</span><span>`+data.uavg+`</span></li>
                    <li><span class="clGreen">功率(W)：</span><span>`+data.pt+`</span></li>
                    <li><span class="clGreen">功率因数：</span><span>`+data.pft+`</span></li>
                    <li><span class="clGreen">电量：</span><span>`+data.pe+`</span></li>`;
                            $("#ldsj").html(html);
                        }
                    }
                })
            }else{
                layerTree.locateBuildingByID(positionid.lid,"STREET_LIGHT");
                lighturl=ctx + 'screen/yjcz/getLightInfo?itid=' + itid+"&name="+name;
                $.ajax({
                    url: lighturl,
                    type: 'post',
                    async: true,
                    success: function (data) {
                        if(data!=null&&data!=""){
                            $("#ldsj").html("");
                            var html='';
                            html+=` <li><span class="clGreen">灯头：</span><span>1</span></li>
                    <li><span class="clGreen">电流(A)：</span><span>`+data.i+`</span></li>
                    <li><span class="clGreen">电压(V)：</span><span>`+data.u+`</span></li>
                    <li><span class="clGreen">功率(W)：</span><span>`+data.p+`</span></li>
                    <li><span class="clGreen">功率因数：</span><span>`+data.pf+`</span></li>
                    <li><span class="clGreen">电量：</span><span>`+data.e+`</span></li>`;
                            $("#ldsj").html(html);
                        }
                    }
                })
            }


           // YJCZ.init1(true,0);
           // $(".pa.mapTxtBox.mapTxtLayer").css("display", "block");



        } else if(name=='探漏'){


            layerTree.locateBuildingByID(''+positionid.placeId+'',"LEAKAGE");
            var oid =layerTree.queryOIDByPipe(''+positionid.pipeId+'',"LEAKAGE");
            YJCZ.getyjxq(itid,name,oid);
            $("#tl").css("display", "block");
            YJCZ.rightBoxShow(name);

            YJCZ.wxjl(layerTree.guid);
            //$(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
            var data = {
                siteNO: id
            }
            YJCZ.xhbdsData(data);
            selectedLinePipe(layerCfg.yjcz.leakWhere);
            YJCZ.getLzfx(itid);
            $("#yjczd").css("display", "block");
            YJCZ.getListhidden();
            $("#leftFxList").css('display','none');
            //YJCZ.lzxdLzfxNc(positionid.pipeId);
            YJCZ.lzxdLzfxNc(oid);
        }else if(name == '水质'){
            layerTree.locateBuildingByID(positionid.sid,"WATER_MONITOR");
            $("#szDetail").html('');
            $("#sz").css("display", "block");
            $.ajax({
                url:ctx + 'screen/yxjk/jdxx/waterQuality/page/'+positionid.oid,
                type:'GET',
                async:false,
                success:function(res){
                    $("#szDetail").html(res);
                }
            })
            $.ajax({
                url:ctx + 'screen/yxjk/jdxx/waterQuality/'+positionid.oid,
                type:'GET',
                async:false,
                success:function(res){
                    var lsjc=res.data.lsjc;
                    var szjc = res.data.szjc;
                    var type = res.data.type
                    // layerTree.locateBuildingByID(''+res.sid+'',"LEAKAGE");
                    if(type=='流速水位'){
                        $("#ls").text(lsjc.ls);
                        $("#sw").text(lsjc.sw);
                        $('#ls_jcsj').text(lsjc.jcsj);
                    }else{
                        $("#ygfrjy").text(szjc.ygfrjy);
                        $("#dcsddl").text(szjc.dcsddl);
                        $('#zd').text(szjc.zd);
                        $("#ph").text(szjc.ph);
                        $("#orp").text(szjc.orp);
                        $('#wd').text(szjc.wd);
                        $('#sz_jcsj').text(szjc.jcsj);
                    }

                }
            })
           //$(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
        }else if(name == '泵房'){
            $("#bf").css("display", "block");
            layerTree.locateBuildingByID(positionid.bfid,"PUMP_HOUSE_ANNOTATION");
           /*//$(".pa.mapTxtBox.mapTxtLayer").css("display", "block");
            $.ajax({
                url: ctx + 'screen/yxjk/jdxx/pump/base/table?id='+positionid.id,
                type: 'GET',
                async: true,
                success: function (data) {
                    $("#bfDetail").html("");
                    if(data!=null){
                        var html='';
                        html+=`<li><span class="clGreen">名称：</span><span>`+data.data[0].value+`</span></li>`;
                        $("#bfDetail").html(html);
                    }
                }
            });*/
            $.ajax({
                url: ctx + 'screen/yjcz/jdxx/pump',
                type: 'GET',
                async: false,
                success: function (res) {
                    $("#bfjc").html('');
                    $("#bfjc").html(res);
                    YJCZ.getPump(positionid.id,positionid.name);
                }
            });


        }
        //YJCZ.getListhidden();
    },


    getCamera:function(code){
        YJCZ.hikivisionInit(code);
    },
    hikivisionInit: function (cameraIndexCode) {
        //声明公用变量
        var pubKey = '';

        // 创建播放实例
        function initPlugin(domId, width, height, initCount) {
            //如果插件存在，则直接添加视频监控，如果插件不存在 才初始化插件
            if (YJCZ.oWebControl) {
                YJCZ.oWebControl.JS_ShowWnd();
                YJCZ.hikivisionStartPreview(YJCZ.oWebControl, cameraIndexCode);
            } else {
                var oWebControl = new WebControl({
                    szPluginContainer: domId,                       // 指定容器id
                    iServicePortStart: 15900,                           // 指定起止端口号，建议使用该值
                    iServicePortEnd: 15909,
                    szClassId: "23BF3B0A-2C56-4D97-9C03-0CB103AA8F11",   // 用于IE10使用ActiveX的clsid
                    cbConnectSuccess: function () {                     // 创建WebControl实例成功
                        YJCZ.oWebControl = oWebControl;
                        oWebControl.JS_StartService("window", {         // WebControl实例创建成功后需要启动服务
                            dllPath: "./VideoPluginConnect.dll"         // 值"./VideoPluginConnect.dll"写死
                        }).then(function () {                           // 启动插件服务成功
                            oWebControl.JS_CreateWnd(domId, width, height).then(function () { //JS_CreateWnd创建视频播放窗口，宽高可设定
                                init(width, height, oWebControl, cameraIndexCode);  // 创建播放实例成功后初始化
                            });
                        }, function () { // 启动插件服务失败
                        });
                    },
                    cbConnectError: function () { // 创建WebControl实例失败
                        oWebControl = null;
                        $(domId).html("插件未启动，正在尝试启动，请稍候...");
                        WebControl.JS_WakeUp("VideoWebPlugin://"); // 程序未启动时执行error函数，采用wakeup来启动程序
                        initCount++;
                        if (initCount < 3) {
                            setTimeout(function () {
                                initPlugin(domId, width, height, initCount);
                            }, 3000)
                        } else {
                            $(domId).html("插件启动失败，请检查插件是否安装！");
                        }
                    },
                    cbConnectClose: function (bNormalClose) {
                        // 异常断开：bNormalClose = false
                        // JS_Disconnect正常断开：bNormalClose = true
                        console.log("cbConnectClose");
                        oWebControl = null;
                    }
                });
            }
        }

        //初始化
        function init(width, height, oWebControl, cameraIndexCode) {
            getPubKey(function () {

                ////////////////////////////////// 请自行修改以下变量值	////////////////////////////////////
                var appKey = YJCZ.config.appKey;                          //综合安防管理平台提供的appkey，必填
                var secret = setEncrypt(YJCZ.config.appSecret);   //综合安防管理平台提供的secret，必填
                var ip = YJCZ.config.hostIp;                           //综合安防管理平台IP地址，必填
                if (String.isNullOrEmpty(cameraIndexCode)) {
                    cameraIndexCode = YJCZ.config.defaultCameraIndexCode;
                }
                var playMode = 0;                                  //初始播放模式：0-预览，1-回放
                var port = 80;                                    //综合安防管理平台端口，若启用HTTPS协议，默认443
                var snapDir = "E:\\SnapDir";                       //抓图存储路径
                var videoDir = "E:\\VideoDir";                     //紧急录像或录像剪辑存储路径
                var layout = "1x1";                                //playMode指定模式的布局
                var enableHTTPS = 0;                               //是否启用HTTPS协议与综合安防管理平台交互，是为1，否为0
                var encryptedFields = 'secret';					   //加密字段，默认加密领域为secret
                var showToolbar = 1;                               //是否显示工具栏，0-不显示，非0-显示
                var showSmart = 1;                                 //是否显示智能信息（如配置移动侦测后画面上的线框），0-不显示，非0-显示
                var buttonIDs = "";  //自定义工具条按钮
                ////////////////////////////////// 请自行修改以上变量值	////////////////////////////////////
                oWebControl.JS_RequestInterface({
                    funcName: "init",
                    argument: JSON.stringify({
                        appkey: appKey,                            //API网关提供的appkey
                        secret: secret,                            //API网关提供的secret
                        ip: ip,                                    //API网关IP地址
                        playMode: playMode,                        //播放模式（决定显示预览还是回放界面）
                        port: port,                                //端口
                        snapDir: snapDir,                          //抓图存储路径
                        videoDir: videoDir,                        //紧急录像或录像剪辑存储路径
                        layout: layout,                            //布局
                        enableHTTPS: enableHTTPS,                  //是否启用HTTPS协议
                        encryptedFields: encryptedFields,          //加密字段
                        showToolbar: showToolbar,                  //是否显示工具栏
                        showSmart: showSmart,                      //是否显示智能信息
                        buttonIDs: buttonIDs                       //自定义工具条按钮
                    })
                }).then(function (oData) {
                    YJCZ.hikivisionStartPreview(oWebControl, cameraIndexCode);
                    oWebControl.JS_Resize(width, height);  // 初始化后resize一次，规避firefox下首次显示窗口后插件窗口未与DIV窗口重合问题
                });
            }, oWebControl);
        }

        //获取公钥
        function getPubKey(callback, oWebControl) {
            oWebControl.JS_RequestInterface({
                funcName: "getRSAPubKey",
                argument: JSON.stringify({
                    keyLength: 1024
                })
            }).then(function (oData) {
                console.log(oData);
                if (oData.responseMsg.data) {
                    pubKey = oData.responseMsg.data;
                    callback()
                }
            })
        }

        //RSA加密
        function setEncrypt(value) {
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(pubKey);
            return encrypt.encrypt(value);
        }

        initPlugin("hkCamera", 293, 200, 0);
    },
    hikivisionStartPreview: function (oWebControl, cameraIndexCode) {
        var streamMode = 0;                                     //主子码流标识：0-主码流，1-子码流
        var transMode = 1;                                      //传输协议：0-UDP，1-TCP
        var gpuMode = 0;                                        //是否启用GPU硬解，0-不启用，1-启用
        var wndId = -1;                                         //播放窗口序号（在2x2以上布局下可指定播放窗口）

        cameraIndexCode = cameraIndexCode.replace(/(^\s*)/g, "");
        cameraIndexCode = cameraIndexCode.replace(/(\s*$)/g, "");

        oWebControl.JS_RequestInterface({
            funcName: "startPreview",
            argument: JSON.stringify({
                cameraIndexCode: cameraIndexCode,                //监控点编号
                streamMode: streamMode,                         //主子码流标识
                transMode: transMode,                           //传输协议
                gpuMode: gpuMode,                               //是否开启GPU硬解
                wndId: wndId                                     //可指定播放窗口
            })
        })
    },
    amplificationImg:function ( url) {
            if(ctx=="/"){
                url=url;
            }else{
                url=ctx+url;
            }

            var height = 600;//拿的图片原来宽高，建议自定义宽高
            var width = 800;
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                shadeClose: true,
                area: [width + 'px', height + 'px'], //宽高
                content: "<img src="+ url + " style='width:800px;height:600px'/>"
            });
        },



    //中间隐藏
    middleBoxhidden: function () {
        $(".pa.mapTxtBox.mapTxtLayer").css("display", "none");
    },
    rightBoxShow1: function (name) {
        if (!$('.closeBtn.pa').hasClass('active')) {
            $('.closeBtn.pa').addClass('active');
            $(".rightBox").animate({right: "-18vw"});
            if (YJCZ.oWebControl != null) {
                //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
                YJCZ.oWebControl.JS_HideWnd();
            }
            if (Hik.oWebControl != null) {
                //如果视频插件不为空 ，隐藏div的时候对视频窗口首先进行隐藏
                Hik.oWebControl.JS_HideWnd();
            }
        } else {
            $('.closeBtn.pa').removeClass('active');
            $(".rightBox").animate({right: "0"});
        }
    },
    // 右侧内容显示
    rightBoxShow: function () {
        $('.rightBox').removeClass('rightHide')
        $('.rightBtn').addClass('show');
        $(".rightBox").animate({right: "0"});
    },
    //底部表格显示
    getList: function () {
        $(".bottomLayer").animate({bottom: "0"});
        $("#yjczd").css("display", "block");
        //预警处置高亮
        //YJCZ.element.tabChange('nhtjTitle', 'yjczd'); //切换到：预警处置
    },
    //底部表格隐藏
    getListhidden: function () {
        $(".bottomLayer").animate({bottom: "-29vh"});
    },
    changeKg:function () {
        if($("#kg").hasClass("layui-form-onswitch")){
            $("#kg").removeClass("layui-form-onswitch");
            $("#ldIcon").children('i').css("color", 'rgba(175,175,175,1')
            checkTxt = true;
            //YJCZ.init1(checkTxt, 0)
        }else{
            $("#kg").addClass("layui-form-onswitch");
            checkTxt = false;
            //YJCZ.init1(checkTxt, 100);
            $("#ldIcon").children('i').css("color", 'rgba(241,152,26,1)')
        }
    },
    init1: function (i,v) {
        layui.use(['slider', 'form'], function () {
            var $ = layui.$
                , slider = layui.slider
                , form = layui.form;

            // 如果开关是关闭的状态就不能滑动滑块
            var checkTxt = true

            // 监听滑块亮度
            sliderControls(i,v);
            function sliderControls(i, v) {
                slider.render({
                    elem: '#slideTest7'
                    , tips: false // 关闭默认提示层
                    , disabled: i  // 设置是否禁用状态
                    , value: v    // 设置初始化值
                    , step: 20
                    , change: function (value) {
                        if (value < 10) {
                            $("#ldIcon").children('i').css("color", 'rgba(175,175,175,1')
                        } else {
                            $("#ldIcon").children('i').css("color", 'rgba(241,152,26,' + (value / 100) + ')')
                        }
                    }
                });
            }

        })


        layui.use(['element', 'util', 'table', 'laypage', 'laydate', 'upload', 'layer',], function () {
            table = layui.table
            var $ = layui.jquery,
                layer = layui.layer,
                util = layui.util,
                laypage = layui.laypage,
                upload = layui.upload,
                laydate = layui.laydate;
            YJCZ.element = layui.element,

                //获取用户--分页
                laypage.render({
                    elem: 'hqyhPage'
                    , count: 70 //数据总数
                    , theme: '#2a9ea2'
                    , jump: function (obj) {
                        // console.log(obj)
                    }
                });

            var width = ($('.bottomLayer').width() - 30) / 20;
            var tatleHeight = $(".moreTable").height()
            // 拉阀分析--详情
            table.render({
                elem: '#lffxTable'
                , height: tatleHeight
                // ,width:width
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                // ,url: '/demo/table/user/' //数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'number', title: '编号', align: 'center'},
                    {field: 'name', title: '名称', align: 'center'},
                    {field: 'position', title: '位置', align: 'center'}
                ]]
                , data: [{
                    "serial": "1"
                    , "number": "01245"
                    , "name": "南苑总阀"
                    , "position": "南洞1-11栋"
                }, {
                    "serial": "2"
                    , "number": "01245"
                    , "name": "南苑总阀"
                    , "position": "南洞1-11栋"
                }, {
                    "serial": "3"
                    , "number": "01245"
                    , "name": "南苑总阀"
                    , "position": "南洞1-11栋"
                }, {
                    "serial": "4"
                    , "number": "01245"
                    , "name": "南苑总阀"
                    , "position": "南洞1-11栋"
                }, {
                    "serial": "5"
                    , "number": "01245"
                    , "name": "南苑总阀"
                    , "position": "南洞1-11栋"
                }]
                , page: {
                    layout: ['prev', 'page', 'next'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    , groups: 1 //只显示 1 个连续页码
                    , first: false //不显示首页
                    , last: false //不显示尾页

                }
            });

            // 影响范围--详情
            table.render({
                elem: '#yxfwTable'
                , height: tatleHeight
                // ,width:width
                , limit: 5 // 这个要根据实际条数来确定，不能设置固定的
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                // ,url: '/demo/table/user/' //数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'pinqu', title: '片区', align: 'center',},
                    {field: 'loudong', title: '楼栋', align: 'center'},
                    {field: 'type', title: '类型', align: 'center'}
                ]]
                , data: [{
                    "serial": "1"
                    , "pinqu": "生物农药中心"
                    , "loudong": "微生物国家"
                    , "type": "教学科研"
                }, {
                    "serial": "2"
                    , "pinqu": "生物农药中心"
                    , "loudong": "微生物国家"
                    , "type": "教学科研"
                }, {
                    "serial": "3"
                    , "pinqu": "生物农药中心"
                    , "loudong": "微生物国家"
                    , "type": "教学科研"
                }, {
                    "serial": "4"
                    , "pinqu": "生物农药中心"
                    , "loudong": "微生物国家"
                    , "type": "教学科研"
                }, {
                    "serial": "5"
                    , "pinqu": "生物农药中心"
                    , "loudong": "微生物国家"
                    , "type": "教学科研"
                }]
                , page: {
                    layout: ['prev', 'page', 'next'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    , groups: 1 //只显示 1 个连续页码
                    , first: false //不显示首页
                    , last: false //不显示尾页

                }
            });

            // 获取用户--详情
            table.render({
                elem: '#hqyhTabe'
                , height: tatleHeight
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                // ,url: '/demo/table/user/' //数据接口
                , cols: [[ //标题栏
                    {type: 'checkbox', width: 40},
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'name', title: '姓名', align: 'center',},
                    {field: 'method', title: '联络方式', align: 'center'},
                    {field: 'weixin', title: '微信', align: 'center'}
                ]]
                , data: [{
                    "serial": "1"
                    , "name": "李云迪1"
                    , "method": "13900000000"
                    , "weixin": "stfsf12"
                }, {
                    "serial": "2"
                    , "name": "李云迪2"
                    , "method": "13900000000"
                    , "weixin": "stfsf12"
                }, {
                    "serial": "3"
                    , "name": "李云迪3"
                    , "method": "13900000000"
                    , "weixin": "stfsf12"
                }, {
                    "serial": "4"
                    , "name": "李云迪4"
                    , "method": "13900000000"
                    , "weixin": "stfsf12"
                }, {
                    "serial": "5"
                    , "name": "李云迪5"
                    , "method": "13900000000"
                    , "weixin": "stfsf12"
                }]
                , page: {
                    layout: ['prev', 'page', 'next'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    , groups: 1 //只显示 1 个连续页码
                    , first: false //不显示首页
                    , last: false //不显示尾页

                }
            });


            //  点击预警表格
            table.on('rowDouble(lsyjTable)', function (obj) {
                console.log(obj.data) //得到当前行数据
                layer.open({
                    type: 2,
                    title: '预警处置',
                    area: ['900px', '650px'],
                    content: 'from.html',
                    skin: 'layer-style',
                    id: 'layuiBox'
                });
            });

            //普通图片上传
            var uploadInst = upload.render({
                elem: '#upload1'
                , url: ''
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        console.log(index, file, result)
                        // $('#uploadText').attr('src', result); //图片链接（base64）

                    });
                }
                , done: function (res) {
                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                    //上传成功
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    // var demoText = $('#demoText');
                    return layer.msg('上传失败');
                }
            });
        });
    },

    init2: function () {
        // 窗口重新初始化
        window.onresize = function () {
            for (var i = 0; i < YJCZ.data.ChartsName.length; i++) {
                YJCZ.data.ChartsName[i].resize();
            }
        };
    },


// 水压指数
    syzsEcharts: function () {
        var syzsEcharts = echarts.init(document.getElementById('syzsEcharts'));
        YJCZ.data.ChartsName.push(syzsEcharts);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '35',
                left: '40',
                right: '4%',
                bottom: '20'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['2015', '2016', '2017', '2018', '2019'],
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
                    name: '水压指数',
                    type: 'line',
                    data: [20, 30, 35, 28, 40],
                    color: '#90d2ab'
                }
            ]
        };
        syzsEcharts.setOption(option);
    },
// 电压指数
    dyzsEcharts: function () {
        var dyzsEcharts = echarts.init(document.getElementById('dyzsEcharts'));
        YJCZ.data.ChartsName.push(dyzsEcharts);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '35',
                left: '40',
                right: '4%',
                bottom: '20'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['2015', '2016', '2017', '2018', '2019'],
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
                    name: '电压指数',
                    type: 'line',
                    data: [20, 30, 35, 28, 40],
                    color: '#90d2ab'
                }
            ]
        };
        dyzsEcharts.setOption(option);
    },

// 预警级别
    yjjbEcharts: function (name,formSysName,content,yjstate,yjjb,author,test1,test2) {
        var yjjbdata=[0,0,0];
        $.ajax({
            url: ctx+"screen/yjcz/getLevelCount?name="+name+"&formSysName="+formSysName+"&content="+content+"&state="+yjstate+"&level="+yjjb+"&author="+author+"&dateTime1="+test1+"&dateTime2="+test2,
            type: "post",
            async:false,
            success: function(data){
                for(var i=1;i<=3;i++){
                    for(var j in data.data){
                        if(data.data[j].level==i){
                            yjjbdata.splice(parseInt(i)-1,1,data.data[j].num);
                        }
                    }
                }


            }
        })

        var yjjbEcharts = echarts.init(document.getElementById('yjjbEcharts'));
        YJCZ.data.ChartsName.push(yjjbEcharts);
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                top: '10%',
                left: '40',
                right: '4%',
                bottom: '40'
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['一级', '二级', '三级'],
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
                    name: '预警',
                    type: 'bar',
                    barMaxWidth: '20',
                    itemStyle: {
                        normal: {
                            barBorderRadius: 6,
                            color: function (params) {
                                //    给出颜色组
                                var colorList = ['#ff5c30', '#f79533', '#61f02c'];
                                return colorList[params.dataIndex]
                            },
                            label: {
                                formatter: "{c}",
                                show: true,
                                position: "top",
                                textStyle: {
                                    fontWeight: "bolder",
                                    fontSize: "12",
                                    color: "#fff"
                                }
                            }
                        }
                    },
                    data: yjjbdata
                }
            ]
        };
        yjjbEcharts.setOption(option);
    },

// 预警类型
    yjlxEcharts: function (name,formSysName,content,yjstate,yjjb,author,test1,test2) {
        var yjlxdata=[];
        $.ajax({
            url: ctx+"screen/yjcz/getNameCount?name="+name+"&formSysName="+formSysName+"&content="+content+"&state="+yjstate+"&level="+yjjb+"&author="+author+"&dateTime1="+test1+"&dateTime2="+test2,
            type: "post",
            async:false,
            success: function(data){
                for(var i in data.data){
                    var datavalue={value:data.data[i].num,name:''+data.data[i].name+''};
                    yjlxdata.push(datavalue);
                }
            }
        })


        var yjlxEcharts = echarts.init(document.getElementById('yjlxEcharts'));
        YJCZ.data.ChartsName.push(yjlxEcharts);
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            calculable: true,
            series: [
                {
                    name: '预警类型',
                    type: 'pie',
                    radius: ['30%', '75%'],
                    center: ['50%', '50%'],
                    roseType: 'area',
                    data:yjlxdata
                }
            ],
            color: [
                '#37bf6a', '#96bfff', '#8378ea', '#9d96f5', '#e7bcf3', '#e690d1',
                '#e062ae', '#fb7293', '#fba43c', '#ffdb5c', '#9fe6b8', '#67e0e3',
                '#6fe2e4', '#34a1da'
            ]
        };
        yjlxEcharts.setOption(option);
    },

// 预警处置
    yjczEcharts: function (name,formSysName,content,yjstate,yjjb,author,test1,test2) {
        var yjczdata=[];
        var yjczName=[];
        $.ajax({
            url: ctx+"screen/yjcz/getStateCount?name="+name+"&formSysName="+formSysName+"&content="+content+"&state="+yjstate+"&level="+yjjb+"&author="+author+"&dateTime1="+test1+"&dateTime2="+test2,
            type: "post",
            async:false,
            success: function(data){
                var m=0;
                var k=0;
                for(var i in data.data){
                    if(data.data[i].state==1){
                         k+=data.data[i].num;
                    }else{
                        m+=data.data[i].num;
                    }
                }
                var datavaluey={value:k,name:'已处置'};
                var datavaluew={value:m,name:'未处置'};
                yjczdata.push(datavaluey);
                yjczdata.push(datavaluew);

            }
        })

        var yjczEcharts = echarts.init(document.getElementById('yjczEcharts'));
        YJCZ.data.ChartsName.push(yjczEcharts);
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                // orient: 'vertical',
                // y: 'center',
                textStyle: {
                    color: 'rgba(255, 255, 255, 1)',
                    fontSize: '13',
                    fontWeight: 'normal'
                },
                itemWidth: 20,  // 设置宽度
                itemHeight: 10, // 设置高度
                data: ['已处置', '未处置']
            },
            series: [{
                name: '预警处置',
                type: 'pie',
                radius: ['45%', '65%'],
                center: ['50%', '60%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                    },
                    emphasis: {
                        show: true,
                        formatter: '{b|{b}} \n\n{d|{d}}  ',  //label 的内容
                        rich: {
                            d: {
                                fontSize: '22',
                                fontFamily: '微软雅黑',
                            }
                        },
                        textStyle: {
                            fontSize: '16',
                            fontWeight: 'normal',
                            color: '#fff'
                        },
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: yjczdata
            }],
            color: ['#37a2da', '#ffdb5c']
        };
        yjczEcharts.setOption(option);
        var index = 0;
        //默认显示第一个
        yjczEcharts.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: 0
        });
        //设置默认选中高亮部分
        yjczEcharts.on('mouseover', function (e) {
            yjczEcharts.dispatchAction({type: 'downplay',seriesIndex: 0,dataIndex:0});
            if (e.dataIndex != index) {
                yjczEcharts.dispatchAction({
                    type: 'downplay',
                    seriesIndex: 0,
                    dataIndex: index
                });

            }
            if(e.dataIndex == 0){
                yjczEcharts.dispatchAction({type: 'highlight',seriesIndex: 0,dataIndex:e.dataIndex});
            }
        });
        yjczEcharts.on('mouseout', function (e) {
            index = e.dataIndex;
            yjczEcharts.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: e.dataIndex
            });
        });
    },


// 预案查看
    yackLayuiBox: function () {
        layer.open({
            type: 2,
            title: '预案查看',
            area: ['700px', '380px'],
            content: ctx + 'screen//yjcz/yack',
            skin: 'layer-style',
            id: 'yackLayuiBox'
        });
    },
// 历史故障
    lsgzLayuiBox: function () {
        layer.open({
            type: 2,
            title: '历史故障',
            area: ['900px', '650px'],
            content: ctx + 'screen/yjcz/lsgz',
            skin: 'layer-style',
            id: 'lsgzLayuiBox'
        });
    },

// 预警核查
    yjhcLayuiBox: function () {
        layer.open({
            type: 2,
            title: '预警核查',
            area: ['700px', '400px'],
            content: ctx + 'screen/yjcz/yjhc',
            skin: 'layer-style',
            id: 'yjhcLayuiBox'
        });
    },

// 核查处置
    hcczLayuiBox: function () {
        layer.open({
            type: 2,
            title: '关闭预警',
            area: ['700px', '670px'],
            content: ctx + "screen/yjcz/hccz",
            skin: 'layer-style',
            id: 'hcczLayuiBox'
        });
    },
// 信息推送弹框
    xxtsLayuiBox: function (itid) {
        debugger;
        var layuiTree = JCBZ.jcbzData.lzxdXxtsHqyhList[0];
        var jsons = JSON.stringify(layuiTree);
        jsons = encodeURI(encodeURI(jsons));
        if (layuiTree == undefined) {
            layer.msg("未影响楼栋！");
            return;
        }

        layer.open({
            type: 2,
            title: '信息推送',
            area: ['560px', '450px'],
            content: ctx + 'screen/yjcz/xxts',
            skin: 'layer-style',
            id: 'layuiBox'
        });
    },
    resetTable:function(){
        $("#content").val("");
        $("#yjjb").each(function (i, j) {
            $(j).find("option:selected").attr("selected", false);
            $(j).find("option").first().attr("selected", true);
        });
        $("#yjstate").each(function (i, j) {
            $(j).find("option:selected").attr("selected", false);
            $(j).find("option").first().attr("selected", true);
        });

        $("#author").val("");
        $("#test1").val("");
        $("#test2").val("");
        YJCZ.lsyjTable("","");
        //图表加载
        YJCZ.yjlxEcharts("","","","","","","","");     // 预警类型
        YJCZ.yjjbEcharts("","","","","","","","");     // 预警级别
        YJCZ.yjczEcharts("","","","","","","","");     // 预警处置
   },



    bjxxMoreBtn1: function(){
        var name = $("#serarchWarring").val("");
        YJCZ.lsyjTable("","");
        //图表加载
        YJCZ.yjlxEcharts("","","","","","","","");     // 预警类型
        YJCZ.yjjbEcharts("","","","","","","","");     // 预警级别
        YJCZ.yjczEcharts("","","","","","","","");     // 预警处置
        var obj = ztree.initZTree("zTree2", ctx+"aupipes/warringCategory/treeData?name=", function (event, treeId, treeNode) {
            YJCZ.treeNodeName='';
            if(treeNode.level=='1'||treeNode.level=='0'){
                if(treeNode.name=='全部'){
                    treeNode.name='';
                }
                YJCZ.treeNodeName=treeNode.name+'0';
                YJCZ.lsyjTable('',treeNode.name);
                //图表加载
                YJCZ.yjlxEcharts("",treeNode.name,"","","","","","");     // 预警类型
                YJCZ.yjjbEcharts("",treeNode.name,"","","","","","");     // 预警级别
                YJCZ.yjczEcharts("",treeNode.name,"","","","","","");     // 预警处置
            }else {
                YJCZ.treeNodeName=treeNode.name;
                YJCZ.lsyjTable(treeNode.name,"");
                //图表加载
                YJCZ.yjlxEcharts(treeNode.name,"","","","","","","");     // 预警类型
                YJCZ.yjjbEcharts(treeNode.name,"","","","","","","");     // 预警级别
                YJCZ.yjczEcharts(treeNode.name,"","","","","","","");     // 预警处置
            }
        })

    },



//   报警详情
    bjxxMoreBtn: function () {
        var name = $("#serarchWarring").val();
         YJCZ.lsyjTable("","");
        //图表加载
        YJCZ.yjlxEcharts("","");     // 预警类型
        YJCZ.yjjbEcharts("","");     // 预警级别
        YJCZ.yjczEcharts("","");     // 预警处置
        ztree.initZTree("zTree2", ctx+"aupipes/warringCategory/treeData?name="+name, function (event, treeId, treeNode) {
            YJCZ.treeNodeName='';
            if(treeNode.level=='0'){
                YJCZ.treeNodeName=treeNode.name+'0';
                YJCZ.lsyjTable('',treeNode.name);
                //图表加载
                YJCZ.yjlxEcharts("",treeNode.name);     // 预警类型
                YJCZ.yjjbEcharts("",treeNode.name);     // 预警级别
                YJCZ.yjczEcharts("",treeNode.name);     // 预警处置
            }else {
                YJCZ.treeNodeName=treeNode.name;
                YJCZ.lsyjTable(treeNode.name,"");
                //图表加载
                YJCZ.yjlxEcharts(treeNode.name,"");     // 预警类型
                YJCZ.yjjbEcharts(treeNode.name,"");     // 预警级别
                YJCZ.yjczEcharts(treeNode.name,"");     // 预警处置


            }
            console.log(111);
        });
    },
    lsyjTable:function (name,formSysName) {
        var content='';
        var yjjb='';
        var yjstate='';
        var author='';
        var test1='';
        var test2='';
        if(name==''||formSysName==''){
            $("#content").val("");
            $("#yjjb").each(function (i, j) {
                $(j).find("option:selected").attr("selected", false);
                $(j).find("option").first().attr("selected", true);
            });
            $("#yjstate").each(function (i, j) {
                $(j).find("option:selected").attr("selected", false);
                $(j).find("option").first().attr("selected", true);
            });

            $("#author").val("");
            $("#test1").val("");
            $("#test2").val("");
        }else{
             content = $("#content").val();
             yjjb = $("#yjjb option:selected").val();
             yjstate = $("#yjstate option:selected").val();
             author=$("#author").val();
             test1 = $("#test1").val();
             test2 = $("#test2").val();
            var l = YJCZ.treeNodeName.substr(YJCZ.treeNodeName.length-1,1);
            var name='';
            var formSysName='';
            if(l==0){
                formSysName=YJCZ.treeNodeName.substr(0,YJCZ.treeNodeName.length-1);
                name='';
            }else{
                name=YJCZ.treeNodeName;
            }
        }
        layui.use(['element', 'util', 'table', 'laypage', 'laydate', 'upload', 'layer',], function () {
            table = layui.table
            var $ = layui.jquery,
                layer = layui.layer,
                util = layui.util,
                laypage = layui.laypage,
                upload = layui.upload,
                laydate = layui.laydate;


            layer.open({
                type: 1,
                title: '报警详情',
                area: ['100%', '93.2%'],
                offset: 'b',
                content: $('.bjxxMoreContent'),
                skin: 'layer-style',
                shade:1,
                id: 'Box'
            });
            form=layui.form;
            form.render("select");    //下拉框的初始化
            laydate.render({           //时间的初始化
                elem: '#test1'
            });
            laydate.render({
                elem: '#test2'
            });
            //图表加载
            YJCZ.yjlxEcharts(name,formSysName,content,yjstate,yjjb,author,test1,test2);     // 预警类型
            YJCZ.yjjbEcharts(name,formSysName,content,yjstate,yjjb,author,test1,test2);     // 预警级别
            YJCZ.yjczEcharts(name,formSysName,content,yjstate,yjjb,author,test1,test2);     // 预警处置


            var bjxxHeight = $(".lsyjTable").height()
            // 历史预警 table
            table.render({
                elem: '#lsyjTable'
                , height: bjxxHeight
                // ,width:width
                , skin: 'nob'

                , limit: 10
                // ,size: 'sm' //小尺寸的表格
                ,url: ctx + 'screen/yjcz/getAllInfo?name='+name+"&formSysName="+formSysName+"&content="+content+"&state="+yjstate+"&level="+yjjb+"&author="+author+"&dateTime1="+test1+"&dateTime2="+test2 //数据接口
                , cols: [[ //标题栏
                    {type: 'numbers', title: '序号', align: 'center'},
                    {field: 'formSysName', title: '预警名称', align: 'center'},
                    {field: 'content', title: '预警内容', align: 'center'},
                    {field: 'state', title: '预警状态', align: 'center',templet:function (m) {
                            if(m.state==1){
                                return "已处置";
                            }else{
                                return "未处置";
                            }
                        }},
                    {field: 'author', title: '预警来源', align: 'center'},
                    {field: 'level', title: '预警级别', align: 'center',templet:function (m) {
                            if(m.level==1){
                                return "一级预警";
                            }else if(m.level==2){
                                return "二级预警";
                            }else if(m.level==3){
                                return "三级预警";
                            }
                        }},
                    {field: 'dateTime', title: '预警时间', align: 'center'},
                    {field: 'name', title: '预警原因', align: 'center'},
                    {field: 'code', title: '设备编号', align: 'center'}
                ]]
                ,done:function(res){
                    YJCZ.tdTitle();
                }
                , response: {dataName: "rows", countName: "total"}//状态要设置为200
                , request: {
                    pageName: 'pageNum', //页码的参数名称，默认：page
                    limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
                    ,limits:[10,20,30,50]
                }
            })
        });

    },
    tdTitle:function (){
    $('th').each(function(index,element){
        $(element).attr('title',$(element).text());
    });
    $('td').each(function(index,element){
        $(element).attr('title',$(element).text());
    });
},

    /**
     * 信号波动
     */
     xhbdsData: function (data) {
         var Categories='';
         var SeriesData='';
        $.ajax({
            url: top.celouService +"/Ajax/HZNYGetCurveGraphValue",
            type: "post",
            async:false,
            data: data,
            dataType: "jsonp",
            success: function(data){
                var xhbdData = data.Data[0];
                var ycxssData = data.Data[1];
                 Categories=xhbdData.Categories;
                 SeriesData=xhbdData.SeriesData;
                 var title=data.RecDatetime;
                YJCZ.xhbd_Charts(Categories,SeriesData,title);
                YJCZ.ycxs_Charts(ycxssData.SeriesData, ycxssData.SeriesData2,title);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
      },
      xhbd_Charts:function(Categories,SeriesData,title){
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
                  left: '3%',
                  right: '5%',
                  bottom: '10%',
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
      },

    getPump:function(pid,name){

        layui.use(['element', 'tree', 'table', 'laypage', 'form'], function () {
            var $ = layui.jquery;
           var element = layui.element;
            var tree = layui.tree;
           YJCZ.table = layui.table;
           var  laypage = layui.laypage;
           var form = layui.form;

            //获取泵组个数
            $.get(ctx + "screen/yxjk/jdxx/pump/point/"+pid, function (result) {
                if(result.code==0&&result.data.length>0){
                    var tabtitle = $(".layui-tab-title li");
                    $.each(tabtitle, function (i) {
                        YXJK.element.tabDelete('demo', $(this).attr("lay-id"));//如果存在就移除默认tab
                    });
                    for (var i = 0;i<result.data.length;i++) {
                        var pointId = result.data[i].id;
                        var point = result.data[i];
                        var title = point.pointName;
                        var attrName = point.attrName;
                        var szylInfo = '<div class="rcxjXxxx">\n' +
                            '                            <h3>市政压力:<span id="szyl_'+pointId+'"></span></h3>\n' +
                            '                        </div>';
                        var contentBase = '<div class = "gaugeBox"><div class="gaugeBox_div">';
                        for (var j = 0; j < attrName.split(",").length; j++) {
                            contentBase += '<div id="char_' + j + '_' + pointId + '" class="pump_char" ></div>';
                        }
                        contentBase += "</div></div>";
                        var contentStatus = '<div class="bplbBox"><h3>泵组数:<span id="pumCount_' + pointId + '"></span>个</h3><div class="table"><div id="yxjk_pump_detail_table_' + pointId + '"></div></div></div>';
                        var content = szylInfo + contentBase + contentStatus;

                        element.tabAdd('demo', {
                            title: title,
                            content: content,
                            id: result.data[i].id
                        })
                        if (i == 0) {
                            element.tabChange('demo', pointId);
                        }
                    }
                }else{
                        YJCZ.showDefaultPumpBase();
                        YJCZ.showDefaultPumpStates();
                }

            });


            element.on('tab(demo)', function (elem) {
                var id =$(this).attr("lay-id");
                if (elem.index == 0) {
                    YJCZ.showPumpPointBase(id);
                    YJCZ.showPumpPointStatus(id);
                }
                if (elem.index == 1) {
                    YJCZ.showPumpPointBase(id);
                    YJCZ.showPumpPointStatus(id);
                }
            })

        });
    },
    /**
     * 对接数据库
     * @param tabId
     */
    showPumpPointBase:function(tabId){
        var gaugeUrl = YXJK.com.$prefix + "/jdxx/pump/echarts/gauge/" + tabId;
        $.get(gaugeUrl, function (gaugeResult) {
            if(gaugeResult.code == 0 && gaugeResult.data.length>0 ){
                var szyl = gaugeResult.data[0].szyl;
                $("#szyl_"+tabId).text(!isNaN(szyl)?szyl+"Mpa":szyl);
                var attrName = gaugeResult.data[0].attrName;
                var attrValue = gaugeResult.data[0].attrValue;
                for (var i = 0; i <attrName.split(",").length ; i++) {
                    var name = attrName.split(",")[i];
                    var value = attrValue.split(",")[i];
                    var id = "char_"+i+"_"+tabId;
                    var unit = "bar";
                    //单位设置
                    if(name=="水箱液位"){
                        unit = "m";
                    }
                    YJCZ.addGauge(YXJK.pu.char, id, value, name,unit);
                }
            }
        });
    },
    /**
     * 对接数据库
     * @param tabId
     */
    showPumpPointStatus:function(tabId){
        var stateTableUrl = ctx + "screen/yxjk/jdxx/pump/state/real/table/"+tabId;
        var tableId = "#yxjk_pump_detail_table_"+tabId
        $.get(stateTableUrl, function (stateTableResult) {
            var stateTableDate = stateTableResult.data;
            if(stateTableDate.length>0){
                $("#pumCount_"+tabId).text(stateTableDate[0].aupPumpPoint.punpcount);
                //显示详情表格
                YJCZ.table.render({
                    elem: tableId
                    , skin: 'nob'
                    , cols: [[ //标题栏
                        {field: 'statusname', title: '名称', align: 'center',width:'20%'},
                        {field: 'status', title: '状态', align: 'center',width:'20%'},
                        {field: 'readtime', title: '读取时间', align: 'center',width:'65%'},
                    ]],
                    data: stateTableDate
                });
            }
        });
    },
    /**
     * 默认
     */
    showDefaultPumpBase:function(){
        YJCZ.addGauge(YXJK.pu.scpl_char, "scpl_char_", 37.4, "输出频率","Hz");
        YJCZ.addGauge(YXJK.pu.sxyw_char, "sxyw_char_", 4.1, "水箱液位","m");
        YJCZ.addGauge(YXJK.pu.ckyl_char, "ckyl_char_", 6.0, "出口压力","bar");
        YJCZ.addGauge(YXJK.pu.sdyl_char, "sdyl_char_", 6.0, "设定压力","bar");
    },
    /**
     * 默认状态信息
     */
    showDefaultPumpStates:function(){
        var stateTableUrl = ctx + "screen/yxjk/jdxx/pump/state/table";
        $.get(stateTableUrl, function (stateTableResult) {
            var stateTableDate = stateTableResult.data;
            //显示详情表格
            YJCZ.table.render({
                elem: '#' + YXJK.pu.$yxjk_pump_detail_table
                , skin: 'nob'
                , cols: [[ //标题栏
                    {field: 'stateName', title: '状态名称', align: 'center'},
                    {field: 'currentState', title: '启用时间', align: 'center'},
                    {field: 'enableTime', title: '当前状态', align: 'center'},
                ]],
                data: stateTableDate
            });
        });
    },




    addTotalGauge1: function (tabId) {
        var ckyl_char, bpwd_char, sxyw_char, bppl_char;
        var gaugeUrl = ctx + "screen/yxjk/jdxx/pump/echarts/gauge/" + tabId;
        $.get(gaugeUrl, function (gaugeResult) {
            var data = gaugeResult.data;
            YJCZ.addGauge(ckyl_char, "ckyl_char", data.num1, "出口压力("+data.num1+")");
            YJCZ.addGauge(bpwd_char, "bpwd_char", data.num2, "变频温度("+data.num2+")");
            YJCZ.addGauge(sxyw_char, "sxyw_char", data.num3, "水箱液位("+data.num3+")");
            YJCZ.addGauge(bppl_char, "bppl_char", data.num4, "变频频率("+data.num4+")");
        });

    },

    addTotalGauge2: function (tabId) {
        var ckyl1_char, bpwd1_char, sxyw1_char, bppl1_char;
        var gaugeUrl = ctx + "screen/yxjk/jdxx/pump/echarts/gauge/" + tabId;
        $.get(gaugeUrl, function (gaugeResult) {
            var data = gaugeResult.data;
            YJCZ.addGauge(ckyl1_char, "ckyl1_char", data.num1, "出口压力("+data.num1+")");
            YJCZ.addGauge(bpwd1_char, "bpwd1_char", data.num2, "变频温度("+data.num2+")");
            YJCZ.addGauge(sxyw1_char, "sxyw1_char", data.num3, "水箱液位("+data.num3+")");
            YJCZ.addGauge(bppl1_char, "bppl1_char", data.num4, "变频频率("+data.num4+")");
        });
    },
    addGauge: function (myChar, id, number, name,unit) {
        if (myChar != "" && myChar != null && typeof myChar != 'undefined') {
            myChar.dispose();
        }
        myChar = echarts.init(document.getElementById(id));
        var option = {
            title: {
                show: true,
            },
            tooltip: {
                formatter: "{a} <br/>{b} : {c}"+unit,
            },
            series: [{
                name: "泵房信息", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
                type: "gauge", // 系列类型
                radius: "90%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
                center: ["45%", "45%"], // 仪表盘位置(圆心坐标)
                startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
                endAngle: -45, // 仪表盘结束角度,默认 -45
                clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
                axisLine: { // 仪表盘轴线(轮廓线)相关配置。
                    show: false, // 是否显示仪表盘轴线(轮廓线),默认 true。
                    lineStyle: { // 仪表盘轴线样式。
                        opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
                        width: 5, //轴线宽度,默认 30。

                    },
                },
                splitLine: { // 分隔线样式。
                    show: true, // 是否显示分隔线,默认 true。
                    length: 8, // 分隔线线长。支持相对半径的百分比,默认 30。

                },
                axisTick: { // 刻度(线)样式。
                    show: true, // 是否显示刻度(线),默认 true。
                    splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
                    fontSize: 12,
                    length: 4, // 刻度线长。支持相对半径的百分比,默认 8。
                    lineStyle: { // 刻度线样式。
                        color: "#eee", //线的颜色,默认 #eee。
                        opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
                        width: 1, //线度,默认 1。
                        type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
                        shadowBlur: 5, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
                        shadowColor: "#fff", //阴影颜色。支持的格式同color。
                    },
                },
                axisLabel: { // 刻度标签。
                    show: true, // 是否显示标签,默认 true。
                    distance: 5, // 标签与刻度线的距离,默认 5。
                    color: "#fff", // 文字的颜色,默认 #fff。
                    fontSize: 10, // 文字的字体大小,默认 5。
                    formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
                },
                emphasis: { // 高亮的 仪表盘指针样式
                    itemStyle: {
                        //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
                    }
                },
                title: { // 仪表盘标题。
                    show: true, // 是否显示标题,默认 true。
                    offsetCenter: [0, "120%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
                    color: "#fff", // 文字的颜色,默认 #333。
                    fontSize: 14, // 文字的字体大小,默认 15。
                },
                pointer: {
                    width: 6,
                    length: "90%"
                },
                itemStyle: {
                    borderColor: "#fff",
                    borderWidth: 1,
                    color: "red",
                    shadowColor: "rgba(116, 10, 9)",
                    shadowBlur: 10
                },
                detail: {
                    show: true,
                    splitNumber: 15,
                    offsetCenter: [0, "90%"],
                    formatter: function (params) {
                        return params+unit;
                    },
                    textStyle: {
                        color: '#8df7f3',
                        fontSize: 18
                    }
                },
                data: [{value: number, name: name}],
            }]
        };
        if(name =="输出频率"){
            option.series[0].min = 0;
            option.series[0].max = 50;
            option.series[0].axisLine.lineStyle.color = [[50, '#6E96AB']];
        }else {
            if (number < 10) {
                option.series[0].min = 0;
                option.series[0].max = 10;
            } else {
                option.series[0].min = 0;
                option.series[0].max = 100;
            }
        }
        myChar.setOption(option, true);
    },


eddyEcharts:function(){
        var eddyEcharts = echarts.init(document.getElementById('eddyEcharts'));
        ChartsName.push(eddyEcharts);
        var option = {
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '',
                    type: 'gauge',
                    radius: '100%',
                    center: ['50%', '50%'],
                    detail: {
                        formatter:'{value}%',
                        color: '#fff',
                        fontSize: '18'
                    },
                    data: [{value: 20, name: ''}],
                    pointer: {
                        width:5, // 指针宽度
                    },
                    axisLine : {
                        show : true,
                        lineStyle : { // 属性lineStyle控制线条样式
                            color : [ //表盘颜色
                                [ 0.2, "#45bf36" ],//0-20%处的颜色
                                [ 0.8, "#f4a02c" ],//20%-80%处的颜色
                                [ 1,"#3b8ad7" ]//80%-100%处的颜色
                            ],
                            width : 8//表盘宽度
                        }
                    },
                    splitLine : { //分割线样式（及10、20等长线样式）
                        length : 10,
                        lineStyle : { // 属性lineStyle控制线条样式
                            width : 2
                        }
                    },
                    axisTick : { //刻度线样式（及短线样式）
                        length :8
                    },
                    axisLabel : { //文字样式（及“10”、“20”等文字样式）
                        color : "#fff",
                        distance : 2 //文字离表盘的距离
                    },
                }
            ]
        };
        eddyEcharts.setOption(option);
    },
    edglEcharts:function(){
        var edglEcharts = echarts.init(document.getElementById('edglEcharts'));
        ChartsName.push(edglEcharts);
        var option = {
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '',
                    type: 'gauge',
                    radius: '100%',
                    center: ['50%', '50%'],
                    detail: {
                        formatter:'{value}%',
                        color: '#fff',
                        fontSize: '18'
                    },
                    data: [{value: 20, name: ''}],
                    pointer: {
                        width:5, // 指针宽度
                    },
                    axisLine : {
                        show : true,
                        lineStyle : { // 属性lineStyle控制线条样式
                            color : [ //表盘颜色
                                [ 0.2, "#45bf36" ],//0-20%处的颜色
                                [ 0.8, "#f4a02c" ],//20%-80%处的颜色
                                [ 1,"#3b8ad7" ]//80%-100%处的颜色
                            ],
                            width : 8//表盘宽度
                        }
                    },
                    splitLine : { //分割线样式（及10、20等长线样式）
                        length : 10,
                        lineStyle : { // 属性lineStyle控制线条样式
                            width : 2
                        }
                    },
                    axisTick : { //刻度线样式（及短线样式）
                        length :8
                    },
                    axisLabel : { //文字样式（及“10”、“20”等文字样式）
                        color : "#fff",
                        distance : 2 //文字离表盘的距离
                    },
                }
            ]
        };
        edglEcharts.setOption(option);
    },

      ycxs_Charts:function(SeriasData,SeriasData2,title){
              ycxs_Charts = echarts.init(document.getElementById("ycxs_Charts"));
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
                      left: '3%',
                      right: '1%',
                      bottom: '10%',
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
          },
          importTables:function () {
              var l = YJCZ.treeNodeName.substr(YJCZ.treeNodeName.length-1,1);
              var name='';
              var formSysName='';
             if(name==0){
                 formSysName=YJCZ.treeNodeName.substr(0,YJCZ.treeNodeName.length-1);
             }else{
                 name=YJCZ.treeNodeName;
             }
              var content = $("#content").val();
             var  yjjb = $("#yjjb option:selected").val();
              var yjstate = $("#yjstate option:selected").val();
              var author=$("#author").val();
              var test1 = $("#test1").val();
              var test2 = $("#test2").val();
              window.location.href=ctx + 'screen/yjcz/importTables?content='+content+"&state="+yjstate+"&level="+yjjb+"&author="+author+"&dateTime1="+test1+"&dateTime2="+test2+'&name='+name+'&formSysName='+formSysName;
          },




        wxjl:function(guid){
       /*  var taskid='';
         $.ajax({
             url:ctx+'aupipes/pipeline/list?guid='+guid,
             type:'post',
             async:false,
             success:function(data){
                 console.log(data);
                 taskid=data.rows[0].taskId;
             }
         })*/


            var id = RYXJ.GK.$wxjlTable;

            var width = ($('.wxjlTable').width()) / 4;
            var tableHeight = 250;

            // 人员巡检
            var ryxjTableIns = RYXJ.table.render({
                elem: "#" + id
                , height: tableHeight
                , skin: 'nob'
                , method: 'post'
                , url: RYXJ.api.wxjlList+"?faultId=7"
                , request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , where :{

                }
                , parseData: function (res) {
                    return {
                        "code": res.code,
                        "msg": res.msg,
                        "count": res.total,
                        "data": res.rows
                    }
                }
                // ,size: 'sm' //小尺寸的表格
                // ,url: '/demo/table/user/' //数据接口
                , cols: [[ //标题栏
                    {title: '序号', align: 'center', width: width, type: 'numbers'},
                    {field: 'name', title: '名称', align: 'center'},
                    {field: 'userName', title: '人员', align: 'center', width: width}
                ]]
                , data: [{
                    "taskId": "1"
                    , "name": "华农南苑"
                    , "userName": "李云迪"
                },]
                , page: {
                    layout: ['prev', 'page', 'next', 'count'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    // ,groups: 1 //只显示 1 个连续页码
                    // ,first: false //不显示首页
                    // ,last: false //不显示尾页
                }
            });

            //监听单元格事件
            RYXJ.table.on('row(clickWxEvent)', function (obj) {
                var data = obj.data;
                YJCZ.wxjlInfo(data);
            });

            return ryxjTableIns;
        },


     // 预案查看
    wxjlInfo: function (data) {
        layer.open({
            type: 1,
            title: '维修详情',
            area: ['500px', '300px'],
            content: YJCZ.wxxq(data),
            skin: 'layer-style'

        });
    },
    wxxq:function(data){

                 var html='';
                  html+=` <ul class="sbxxList">
                    <li>维修名称：<span>`+data.name+`</span></li>
                    <li>维修人员：<span>`+data.userName+`</span></li>
                    <li>维修时间：<span>`+data.repairTime+`</span></li>
                    <li>详细位置：<span>`+data.address+`</span></li>
                    <li>详细坐标：<span>`+data.location+`</span></li>
                    <li>故障类型：<span>`+data.faultType+`</span></li>
                    <li>详细描述：<span>`+data.description+`</span></li>
                </ul>`;
                return html;

    },
    gffx:function(){
        $("#leftFxList").css('display','block');
        YJCZ.getList();
    },
    //关阀停水
    lzxdLzfxNc: function (oid) {
        var s = "gfts";
        oid=250;
        YJCZ.loadLzxdRightData(s,oid);
    },
    //关阀停水
    loadLzxdRightData: function (s,oid) {
         YJCZ.lzxdGftsDownBox(oid);
        //JCBZ.lzxd.loadLzxdData();
    },

    lzxdGftsDownBox: function (oid) {
            var gfNameStr = "gfts";//加载数据类型 判断拉闸还是关阀
            $("#lffxTable").html("");
            $("#gxfxTable").html("");
            $("#yxfwTable").html("");
            $("#hqyhTabe").html("");
            if(oid!=''){
                $.ajax({
                    type: 'post',
                    async: false,
                    url: JCBZ.jcbzData.prefix + '/gftsSgfx?gftsBhType=JSGX&oid='+oid,
                    success: function (data) {
                        $("#leftFxList").html("");
                        $("#leftFxList").html(data);
                        $(".lzfxList").css("display", "block");
                        $(".yxfwList").css("display", "block");
                        $(".tsyhList").css("display", "block");
                        YJCZ.loadLzxdData(gfNameStr,oid);
                    }
                });
            }
    },

    loadLzxdData: function (modelTypeName,oid) {
        layui.use(['element', 'tree', 'util', 'table', 'laypage'], function () {
            var $ = layui.jquery,
                element = layui.element,
                tree = layui.tree,
                layer = layui.layer,
                util = layui.util,
                table = layui.table,
                laypage = layui.laypage;

            //总页数大于页码总数
        /*    laypage.render({
                elem: 'demo1'
                , count: 70 //数据总数
                , theme: '#2a9ea2'
                , jump: function (obj) {

                }
            });*/


            $("#yxfwList").css("display", "none");
            $("#tsyhList").css("display", "none");
            // 拉阀分析--详情
            table.render({
                elem: '#lffxTable'
                , height: tatleHeight
                // ,width:width
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                , url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=lzfx&modelTypeName=' + modelTypeName + '&oid=' + oid+'&bhType=JSGX' //数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'number', title: '编号', align: 'center',},
                    {field: 'name', title: '附属物名称', align: 'center'},
                    {field: 'position', title: '位置', align: 'center'},
                    {field: 'address', title: '定位点', width: 50, style: 'display:none;'}
                ]]
                , response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
                , data: []
                , done: function (res, curr, count) {
                    // 隐藏列
                    $(".layui-table-box").find("[data-field='address']").css("display", "none");
                }
                , id: 'lffxFilter'
                , page: true
                , limits: [5, 10, 20]
                , limit: 5 //每页默认显示的数量
                , parseData: function (res) {
                    var dataList = res.rows;
                    var total = dataList.length; //请求的数据总数
                    var list = []; //用来保存当前页显示的数据
                    //前端模拟分页，获取当前页、分页显示数据量
                    //var page = $(".layui-table-page").find(".layui-laypage-em").next().html();
                    var page = $("#lffxTable").next().find(".layui-laypage-em").next().html();
                    var limit = $(".layui-table-page").find(".layui-laypage-limits select").val();
                    console.log(page + ", " + limit);
                    if (page == undefined || page == null || page == "") {
                        page = 1;
                    }
                    if (limit == undefined || limit == null || limit == "") {
                        limit = 5;
                    }
                    //数据从哪条数据开始
                    var start = (page - 1) * limit;
                    //数据从哪条数据结束
                    var end = page * limit;
                    list = dataList.slice(start, end);//取分页数据
                    return {
                        "status": 200,
                        "msg": "",
                        "total": total,
                        "rows": list,
                    }
                },
            });


            var tatleHeight = $(".moreTable").height() ;
            // 管线分析--详情
            table.render({
                elem: '#gxfxTable'
                , height: tatleHeight
                // ,width:width
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                , url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=gxfx&modelTypeName=' + modelTypeName + '&oid=' + oid+'&bhType=JSGX' //数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'number', title: '编号', align: 'center',},
                    {field: 'name', title: '附属物名称', align: 'center'},
                    {field: 'position', title: '位置', align: 'center'},
                    {field: 'address', title: '定位点', width: 50, style: 'display:none;'}
                ]]
                , response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
                , data: []
                , done: function (res, curr, count) {
                    // 隐藏列
                    $(".layui-table-box").find("[data-field='address']").css("display", "none");
                }
                , id: 'gxfxFilter'
                , page: true
                , limits: [5, 10, 20]
                , limit: 5 //每页默认显示的数量
                , parseData: function (res) {
                    var dataList = res.rows;
                    var total = dataList.length; //请求的数据总数
                    var list = []; //用来保存当前页显示的数据
                    //前端模拟分页，获取当前页、分页显示数据量
                    //var page = $(".layui-table-page").find(".layui-laypage-em").next().html();
                    var page = $("#gxfxTable").next().find(".layui-laypage-em").next().html();
                    var limit = $(".layui-table-page").find(".layui-laypage-limits select").val();
                    console.log(page + ", " + limit);
                    if (page == undefined || page == null || page == "") {
                        page = 1;
                    }
                    if (limit == undefined || limit == null || limit == "") {
                        limit = 5;
                    }
                    //数据从哪条数据开始
                    var start = (page - 1) * limit;
                    //数据从哪条数据结束
                    var end = page * limit;
                    list = dataList.slice(start, end);//取分页数据
                    return {
                        "status": 200,
                        "msg": "",
                        "total": total,
                        "rows": list,
                    }
                },
            });



            // 影响范围--详情
            var tatleHeight = $(".moreTable").height()
            table.render({
                elem: '#yxfwTable'
                , height: tatleHeight
                // ,width:width
                , limits: [5, 10, 20]
                , limit: 5 //每页默认显示的数量
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                , url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=yxfw&modelTypeName=' + modelTypeName+ '&oid=' + oid+'&bhType=JSGX'//数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center'},
                    {field: 'pinqu', title: '片区', align: 'center',},
                    {field: 'loudong', title: '楼栋', align: 'center'},
                    {field: 'type', title: '类型', align: 'center'},
                    {field: 'buildNum', title: '编号', width: 50, style: 'display:none;'}
                ]]
                , response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
                , data: []
                , id: 'yxfwFilter'
                /*, page: {
                    layout: ['prev', 'page', 'next'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    , groups: 1 //只显示 1 个连续页码
                    , first: false //不显示首页
                    , last: false //不显示尾页

                }*/
                , page: true
                , parseData: function (res) {
                    var dataList = res.rows;
                    var total = dataList.length; //请求的数据总数
                    var list = []; //用来保存当前页显示的数据
                    //前端模拟分页，获取当前页、分页显示数据量
                    //var page = $(".layui-table-page").find(".layui-laypage-em").next().html();
                    var page = $("#yxfwTable").next().find(".layui-laypage-em").next().html();
                    var limit = $(".layui-table-page").find(".layui-laypage-limits select").val();
                    console.log(page + ", " + limit);
                    if (page == undefined || page == null || page == "") {
                        page = 1;
                    }
                    if (limit == undefined || limit == null || limit == "") {
                        limit = 5;
                    }
                    //数据从哪条数据开始
                    var start = (page - 1) * limit;
                    //数据从哪条数据结束
                    var end = page * limit;
                    list = dataList.slice(start, end);//取分页数据
                    return {
                        "status": 200,
                        "msg": "",
                        "total": total,
                        "rows": list,
                    }
                }
                , done: function (res, curr, count) {
                    // 隐藏列
                    $(".layui-table-box").find("[data-field='buildNum']").css("display", "none");
                }
            });

            table.on('row(yxfwFilter)', function (obj) {
                //定位
                layerTree.locateBuildingByID(obj.data.buildNum, "HOUSE_ANNOTATION");
            });
            // 获取用户--详情
            YJCZ.getHqyhTree(tree,table,modelTypeName);
        });
    },

    //加载获取人员树数据
    getHqyhTree: function (tree,table,modelTypeName) {
        //获取列表
        // 影响范围--详情
        var width = ($('.bottomLayer').width() - 30) / 20;
        var tatleHeight = $(".moreTable").height()
        table.render({
            elem: '#hqyhTabe'
            , height: tatleHeight
            // ,width:width
            , limits: [5, 10, 20]
            , limit: 5 //每页默认显示的数量
            , skin: 'nob'
            // ,size: 'sm' //小尺寸的表格
            , url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=tsyh&modelTypeName='+modelTypeName+'&oid=&bhType=JSGX'//数据接口
            , cols: [[ //标题栏
                {field: 'serial', title: '序号', align: 'center', width: width},
                {field: 'name', title: '姓名', align: 'center',},
                {field: 'loudong', title: '楼栋', align: 'center'},
                {field: 'type', title: '类型', align: 'center'},
                {field: 'buildNum', title: '编号', width: 50, style: 'display:none;'},
                {field: 'userId', title: '用户ID', width: 50, style: 'display:none;'}
            ]]
            , response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
            , data: []
            , id: 'hqyhFilter'
            , page: true
            , parseData: function (res) {
                var dataList = res.rows;
                var total = dataList.length; //请求的数据总数
                var list = []; //用来保存当前页显示的数据
                //前端模拟分页，获取当前页、分页显示数据量
                var page = $("#hqyhTabe").next().find(".layui-laypage-em").next().html();
                var limit = $(".layui-table-page").find(".layui-laypage-limits select").val();
                console.log(page + ", " + limit);
                if (page == undefined || page == null || page == "") {
                    page = 1;
                }
                if (limit == undefined || limit == null || limit == "") {
                    limit = 5;
                }
                //数据从哪条数据开始
                var start = (page - 1) * limit;
                //数据从哪条数据结束
                var end = page * limit;
                list = dataList.slice(start, end);//取分页数据
                return {
                    "status": 200,
                    "msg": "",
                    "total": total,
                    "rows": list,
                }
            }
            , done: function (res, curr, count) {
                // 隐藏列
                $(".layui-table-box").find("[data-field='buildNum']").css("display", "none");
                $(".layui-table-box").find("[data-field='userId']").css("display", "none");
            }
        });
        //获得选择的楼栋
        var jmyhcount = 0;
        var ssgly=0;
        var hqb=0;
        var jmgly=0;
        var jxgly=0;
        var jmyh=0;
        //获取全部楼栋信息 勾选上面获得的楼栋信息
        jQuery.get(JCBZ.jcbzData.prefix + '/dbxqBoxData?type=tsyh&modelTypeName='+modelTypeName+'&oid=&bhType=JSGX', "", function (data) {
            debugger;
            var personList=data.rows;
            $(".jmyhCount").text("");
            $(".ssManageCount").text("");
            $(".hqbCount").text("");
            $(".jxManageCount").text("");
            if(personList.length>0){
                if(personList.length==0){
                    JCBZ.jcbzData.hqyhStatus = 1;
                }else{
                    JCBZ.jcbzData.hqyhStatus = 2;
                }
            }
            for(var i=0;i<personList.length;i++){
                if(personList[i].type=="宿舍管理员"){
                    ssgly++;
                }else if(personList[i].type=="后勤部"){
                    hqb++;
                }else if(personList[i].type=="居民管理员"){
                    ssgly++;
                }else if(personList[i].type=="教学管理员"){
                    jxgly++;
                }else{
                    jmyh++;
                }
            }
            //保存用户手机号和微信id
            for(var  i=0 ;i<personList.length;i++){
                if(personList[i].phoneNum==""){
                    personList[i].phoneNum="null";
                }
                if(i==0){
                    JCBZ.jcbzData.lzxdXxtsHqyhUserIdStr += personList[i].userId;
                }else{
                    JCBZ.jcbzData.lzxdXxtsHqyhUserIdStr += ("," + personList[i].userId);
                }
            }
            $(".ssManageCount").text(ssgly);
            $(".hqbCount").text(hqb);
            $(".jmyhCount").text(jmyh);
            $(".jxManageCount").text(jxgly);
            layer.close(JCBZ.jcbzData.indexLoad);    //返回数据关闭loading
        })
    },
};


//运行监控图标点击事件
YJCZ.clickPic = function(){
    if(viewer){
        viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
            var scene = viewer.scene;
            if (scene.mode !== Cesium.SceneMode.MORPHING) {
                var pickedObject = scene.pick(movement.position);
                if(pickedObject){
                    if(pickedObject.id){
                        var id;
                        if(YJCZ.type=='泵房'){  //泵房
                            id=pickedObject.id._properties["_BFID"]._value;
                            $.post(ctx + 'aupipes/pump/list?bfid='+id,function(data) {
                                 var code = data.rows[0].id;
                                $.get(ctx + 'aupipes/warring/getAupWarringList?code='+code,function(res) {
                                    if(res.length>0){
                                        YJCZ.getAllinfo(res[0].id);
                                    }
                                });
                            });

                        }else if(YJCZ.type=='路灯'){    //控制器
                            id = pickedObject.id._properties["_CUID"]._value;
                            if(pickedObject.id._properties["_LUID"]){

                            }else{
                                //请求数据
                                $.get(ctx+'aupipes/warring/getAupWarringList?code='+id, function (res) {
                                    if(res.length>0){
                                        YJCZ.getAllinfo(res[0].id);
                                    }
                                });
                            }

                        }else if(YJCZ.type=='探漏'){
                            id = pickedObject.id._properties["_SITENO"]._value;
                            //请求数据
                            $.get(ctx+'aupipes/warring/getAupWarringList?code='+id, function (res) {
                                if(res.length>0){
                                    YJCZ.getAllinfo(res[0].id);
                                }
                            });
                        }else if(YJCZ.type=='水电'){
                            var content = pickedObject.id._properties["_MC"]._value;
                            //请求数据
                            $.get( ctx + 'screen/yjcz/getAllInfo?name='+name+"&formSysName=水电&content="+content+"&state=&level=&author=&dateTime1=&dateTime2=", function (data) {
                                if(data.rows.length>0){
                                    YJCZ.getAllinfo(data.rows[0].id);
                                }
                            });
                        }else if(YJCZ.type=='监控'){
                            id = pickedObject.id._properties["_SID"]._value;
                            layerTree.locateBuildingByID(id, "MONITOR");
                            //请求数据
                            $.get(ctx+'aupipes/warring/getAupWarringList?code='+id, function (res) {
                                if(res.length>0){
                                    YJCZ.getAllinfo(res[0].id);
                                }
                            });
                        }
                    }
                }
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
    }
}



