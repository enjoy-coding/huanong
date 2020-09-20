/**
 * 决策保障初始化
 * @type {{
 *  init: ModelManager.JCBZ.init
 *  , initData: ModelManager.JCBZ.initData
 *  , jcbzData: {
 *      lzxdDataArray: Array
 *      , aaa: Array
 *      , jcbzChildName: string
 *      , lzxdRightBoxData: string
 *      , prefix: string
 *      , znpgDataArray: Array
 *      , lzxdHqyhTabeData: Array
 *      , bhsgName: string
 *      }
 *  , loadLeftBox: ModelManager.JCBZ.loadLeftBox
 *  , cleatAll: ModelManager.JCBZ.cleatAll
 *  , jcbzLayuiBox: ModelManager.JCBZ.jcbzLayuiBox
 *  }}
 */
var JCBZ = ModelManager["JCBZ"] = {
    init: function (name) {
        //改变全局搜索的默认值
        objectType = 'subject';
        $(".searchLabel").find("input").eq(0)[0].checked = true;
        JCBZ = this;
        //JCBZ.initData(name);
        menuChangeInit();
        JCBZ.lzxd.loadLzxdLeftBox(name);
    },
    initData: function (name) {

    },
    /**
     * -------------------------------------------------------------------------------------------------------------------
     */
    //拉闸分析、关阀停水、智能排管
    jcbzData: {
        prefix: ctx + "screen/jcbz",//决策保障
        buildIngWaterUrl: ctx + "aupipes/buildingwatervalve",//决策保障
        jcbzChildName: "",
        sfChoose: "",//是否再次选择模块
        jcbzSubNavNum: "", //初始化选中的A标签
        lzxdDataArray: [],
        lzxdRightBoxData: "",
        lzxdHqyhTabeData: [],
        lzxdXxtsHqyhList: {},
        lzxdXxtsHqyhWxStr: '', //推送用户改成表格后的用户微信ID
        lzxdXxtsHqyhPhoneStr: '', //推送用户改成表格后的用户手机号
        lzxdXxtsHqyhUserIdStr: '', //推送用户改成表格后的用户Id
        bhsgName: "",//选中地图标绘事故点
        znpgDataArray: [],
        hqyhStatus: 0,  //hqyhTreeArray
        type: '',//预案管理的类型
        zTreeObj: '',
        typeName: '',
        ldNumList: [],     //树列表选择的楼栋信息
        indexLoad: '',
        gftsBhType: '',//存储标会的是房屋还是管线id
        fxTitleName: '',// 分析表格的titleName
        fxTitleName_: '',// 分析表格的titleName
        pageNum: 1,
        pageSize: 15,
        pageTccCount: 0,//空闲页面总条数
        pageLimitStart: 0,  //起始条数
        ldTsxxObj: {},
        ldTsxxObj1: {},
        ldTsxxDelObj: {},
        ldTsxxDelObj1: {},
        thLen: 0, //选取楼栋的div长度
    },
    /**
     * 加载左侧面板
     * @param url
     * @param fn
     */
    loadLeftBox: function (url, fn) {
        $("#left-panel").empty();
        $("#left-panel").load(url, fn);
    },
    // 信息推送弹框
    jcbzLayuiBox: function () {
        var status = JCBZ.jcbzData.hqyhStatus; //1 表示楼栋下无用户  2表示楼栋下有用户
        if (status == 1) {
            layer.msg("楼栋下无用户！");
            return;
        } else if (status == 0) {
            layer.msg("未影响楼栋！");
            return;
        } else {
            $.post(JCBZ.jcbzData.prefix + '/xxts1', function (data) {
                //var typeName = $(".tabBtn")[0].children[1].innerText
                //提交完成后的事件
                layer.open({
                    type: 2,
                    title: '信息推送',
                    area: ['560px', '450px'],
                    content: JCBZ.jcbzData.prefix + '/xxts?typeName=' + JCBZ.jcbzData.typeName,
                    skin: 'layer-style',
                    id: 'layuiBox'
                });
            })
        }
    },
    //加载新模块清除数据
    cleatAll: function () {
        $(".lzfxList").css("display", "none");
        $(".yxfwList").css("display", "none");
        $(".tsyhList").css("display", "none");
        bhsgName = "";
        bottonBoxHide();

        //如果有动画就清除预案动画的定时函数
        if (timer) {
            clearInterval(timer);
        }
        if (particle1) {
            gViewer.scene.primitives.remove(particle1);
        }
        $(".planloadingcontent").remove();

        LZXD.clearPic();
        GFTS.clearPic();
        Tube.clearTubes(gViewer);
        JCBZ.znpg.drawClear();
        if (pickColor != null) {
            //Cesium.Color.clone(pickColor.color,res.feature.color);
            pickColor = null;
        }
    },
    //tab栏切换点击
    initMenuBtnClick: function (name) {
        spIndex = 1;
        $(".tabElement").removeClass("layui-this");
        if (name == "" || name == null) {
            name = "lzfx";
        }
        if (name == "lzfx" || name == null) {
            JCBZ.lzxd.init();
            JCBZ.jcbzData.typeName = '拉闸分析';
            selectedLinePipes(layerCfg.jcbz.electricity);
        } else if (name == "gfts") {
            JCBZ.gfts.init();
            JCBZ.jcbzData.typeName = '关阀分析';
            JCBZ.selectedWater(layerCfg.jcbz.water);
        } else if (name == "znpg") {
            selectedLinePipes(layerCfg.jcbz.znpg);
            JCBZ.znpg.init();
        } else if (name == "yagl") {
            selectedLinePipes(layerCfg.jcbz.yagl);
            JCBZ.yagl.init();
        } else if (name == "cxfx") {
            JCBZ.jcbzData.typeName = '出线分析';
            selectedLinePipes(layerCfg.jcbz.yagl);
            JCBZ.cxfx.init();
        } else if (name == "kwfx") {
            JCBZ.kwfx.init();
        } else if (name == "tsxx") {
            JCBZ.tsxx.init();
            selectedLinePipes(layerCfg.jcbz.xxts);
        } else if (name == "hdmfx") {
            JCBZ.hdfx.init();
            //selectedLinePipes(layerCfg.jcbz.xxts);
        } else if (name == "tsxx") {
            JCBZ.tsxx.init();
            //selectedLinePipes(layerCfg.jcbz.xxts);
        } else if (name == "ldfm") {
            JCBZ.ldfm.init();
            selectedLinePipes(layerCfg.jcbz.ldfm); //初始化加载图层
        }

        $("." + name).addClass("layui-this");
        $(".zs-tabClick").on('click', 'li', function () {
            //切换模块清除点击事件
            GFTS.clear();
            LZXD.clear();
            GFTS.removeLeftCli();
            LZXD.removeLeftCli();
            Tube.clearTubes(gViewer);
            rightBoxClose();
            if (drawHandler) {
                drawShapeModel = null;
                drawHandler.stopDrawing();
                drawHandler.drawEndEvent.removeEventListener(layerTree.drawModel);
            }
            spIndex = 1;
            if (pickColor != null) {
                //Cesium.Color.clone(pickColor.color,res.feature.color);
                pickColor = null;
            }
            layerTree.removePrimitiveByGuid('drawing');
            //清除已经定位的配电房bim
            if (layerTree.bimTreeSceneFlag) {
                layerTree.outOfBimTreeScene();
            }
            var name = $(this).attr("name");
            if (name == "lzfx") {
                JCBZ.jcbzData.typeName = '拉闸分析';
                JCBZ.lzxd.init();
                selectedLinePipes(layerCfg.jcbz.electricity);
            } else if (name == "gfts") {
                JCBZ.jcbzData.typeName = '关阀分析';
                JCBZ.gfts.init();
                JCBZ.selectedWater(layerCfg.jcbz.water);
            } else if (name == "znpg") {
                selectedLinePipes(layerCfg.jcbz.znpg);
                JCBZ.znpg.init();
            } else if (name == "yagl") {
                selectedLinePipes(layerCfg.jcbz.yagl);
                JCBZ.yagl.init();
            } else if (name == "cxfx") {
                JCBZ.jcbzData.typeName = '出线分析';
                selectedLinePipes(layerCfg.jcbz.yagl);
                JCBZ.cxfx.init();
            } else if (name == "kwfx") {
                JCBZ.kwfx.init();
            } else if (name == "tsxx") {
                JCBZ.tsxx.init();
                selectedLinePipes(layerCfg.jcbz.xxts);
            } else if (name == "hdmfx") {
                JCBZ.hdfx.init();
                //selectedLinePipes(layerCfg.jcbz.xxts);
            } else if (name == "ldfm") {
                JCBZ.ldfm.init();
                selectedLinePipes(layerCfg.jcbz.ldfm);
            }
        });

    },
    //默认选中给水管线点和段
    selectedWater: function (nameArr) {
        var list = $("#tt")[0].children;

        for (var i = 0; i < list.length; i++) {
            var id = list[i].id;
            var treeObj = layerTree.zTree;
            var node = treeObj.getNodeByTId(list[i].id);
            var name = node.name;
            if (node.checked == true) {
                treeObj.checkNode(node, "", true, true);
            }
        }

        for (var j = 0; j < nameArr.length; j++) {
            if (treeObj) {
                var childrenNode = treeObj.getNodeByParam("id", nameArr[j], null);
                treeObj.checkNode(childrenNode, "", true, true);
            }
        }

    }

}

/**
 * 楼栋阀门关系关联
 */

JCBZ.ldfm = {

    initData:{
        //楼栋和阀门关系变量
        chooseType : '',//标绘选中的类型 房屋还是阀门
        fwStatus : 0, //0表示未选中， 1表示选中。
        pickedLdObject:null,
        pickedFmObject:[],
        fwBh:[''],
        fmOid : '', //当选中为阀门时候 存取OID

    },

    init : function(){
        JCBZ.ldfm.loadLdfm();
        //JCBZ.ldfm.clickLd();
    },

    //初始化楼栋阀门页面信息
    loadLdfm : function(){
        //清空左侧页面信息
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/ldfm", function (data) {
            //隐藏右侧
            rightBoxClose();
            layui.use('form', function () {
                var form = layui.form;
                form.render();
                form.render('checkbox'); //刷新select选择框渲染
                form.on('checkbox(switchBtn)', function (data) {
                    if (data.elem.checked == true) {
                        $("#areaName").css("display","block");
                    }else {
                        $("#areaName").css("display","none");
                    }
                    form.render();
                });
            });
            //清空数据
            JCBZ.ldfm.clearLdfmData();
        });
    },

    //标记楼栋查看
    clickLd : function(){
        if (viewer) {
            viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {

                //选中之前清除数据
                JCBZ.ldfm.clearLdfmData();
                JCBZ.ldfm.clearLdfm();

                var scene = viewer.scene;
                if(typeof scene.pick(movement.position) == 'undefined'){
                    return;
                }

                layerTree.removeLocatedBuilding();
                if (scene.mode !== Cesium.SceneMode.MORPHING) {
                    JCBZ.ldfm.initData.pickedLdObject = scene.pick(movement.position);
                    var pickedObject = JCBZ.ldfm.initData.pickedLdObject;
                    if (pickedObject) {
                        if (pickedObject.id) {
                            //标记了楼栋
                            if (pickedObject.id._properties["_FWBM"]) {
                                JCBZ.ldfm.initData.chooseType = "B";
                                var fwbh = pickedObject.id._properties["_FWBM"]._value;
                                var mc = pickedObject.id._properties["_MC"]._value;
                                var fwid = pickedObject.id._properties["_FWID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                $("#ldName").css("display","block");
                                $("#ldFmName").val(mc);
                                $("#upTitle").css("display","block");
                                $("#ldfmUpTitle").css("display","block");
                                JCBZ.ldfm.selectUpLdFm(JCBZ.ldfm.initData.chooseType,fwbh);

                            }
                            //标记了阀门
                            if(pickedObject.id._properties._EXP_NO){
                                JCBZ.ldfm.initData.chooseType = "V";
                                var fmValve = pickedObject.id._properties._EXP_NO._value;
                                JCBZ.ldfm.initData.fmOid = pickedObject.id._properties._OID._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-on.png';
                                $("#fmName").css("display","block");
                                $("#ldFmName").val(fmValve);
                                $("#areaDiv").css("display","block");
                                $("#upTitle").css("display","block");
                                $("#downTitle").css("display","block");
                                $("#ldfmUpTitle").css("display","block");
                                $("#ldfmDownTitle").css("display","block");
                                JCBZ.ldfm.selectUpLdFm(JCBZ.ldfm.initData.chooseType,fmValve);
                            }
                        }
                    }
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },

    //选中上游阀门
    chooseUpFm : function(){
        if (viewer) {
            viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                var scene = viewer.scene;
                if(typeof scene.pick(movement.position) == 'undefined'){
                    return;
                }
                if( JCBZ.ldfm.initData.pickedFmObject.length>=5){
                    layer.msg("上游阀门不可超过5个!");
                    return;
                }

                layerTree.removeLocatedBuilding();
                if (scene.mode !== Cesium.SceneMode.MORPHING) {
                    var pickedObject = scene.pick(movement.position);
                    if (pickedObject) {
                        if (pickedObject.id) {
                            //标记了阀门
                            if(pickedObject.id._properties._EXP_NO){
                                JCBZ.ldfm.initData.chooseType = "V";
                                var fmValve = pickedObject.id._properties._EXP_NO._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen.png';
                                JCBZ.ldfm.initData.pickedFmObject.push(fmValve);
                                //重新加载列表
                                var fmDataArr = JCBZ.ldfm.initData.pickedFmObject;
                                $("#ldfmUpList").html("");
                                var str= "";
                                var num=0;
                                var type="V";
                                JCBZ.ldfm.initData.pickedFmObject= [];
                                for(var i in fmDataArr){
                                    num++;
                                    str = JCBZ.ldfm.getUpList(num,fmDataArr[i],type,str)
                                }
                                $("#ldfmUpList").append(str);
                            }else{
                                layer.msg("请选择阀门");
                                return;
                            }
                        }
                    }
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },

    //标记楼栋查看影响阀门
    clickLd1: function () {
        if (viewer) {
            viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                var scene = viewer.scene;
                if(typeof scene.pick(movement.position) == 'undefined'){
                    return;
                }
                if(typeof scene.pick(movement.position).id._properties["_FWBM"] == 'undefined'){
                    if( JCBZ.ldfm.initData.fwStatus == 0){
                        layer.msg("请先标记房屋！")
                        return;
                    }
                    if( JCBZ.ldfm.initData.fwStatus == 1 ){
                        var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;
                        var pickedObject = scene.pick(movement.position);
                        if( !(typeof pickedObject.id._properties._EXP_NO._value == 'undefined') ){
                            var dataNum = JCBZ.ldfm.initData.pickedFmObject.indexOf(pickedObject.id._properties._EXP_NO._value);
                            if(dataNum!= -1){
                                //消除标记样式
                                for (var i in fmTcArr){
                                    if( pickedObject.id._properties._EXP_NO._value == fmTcArr[i]._properties._EXP_NO._value){
                                        fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-off.png';
                                        break;
                                    }
                                }
                                JCBZ.ldfm.initData.pickedFmObject.splice(dataNum, 1 );
                            }else{
                                JCBZ.ldfm.initData.pickedFmObject.push(pickedObject.id._properties._EXP_NO._value);
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen.png';
                            }
                            return;
                        }
                    }
                }
                if(JCBZ.ldfm.initData.pickedLdObject != null){
                    if (JCBZ.ldfm.initData.pickedLdObject.id) {
                        var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;
                        var pickedObject = scene.pick(movement.position);
                        if((JCBZ.ldfm.initData.pickedLdObject.id._billboard._image._value.indexOf("loudong")!=-1)){
                            JCBZ.ldfm.initData.pickedLdObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
                            if(pickedObject.id._properties["_FWBM"]._value == JCBZ.ldfm.initData.pickedLdObject.id._properties["_FWBM"]._value){
                                for (var i in fmTcArr){
                                    fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-off.png';
                                }
                                JCBZ.ldfm.clearLdfmData();
                                return ;
                            }
                        }
                    }
                }

                layerTree.removeLocatedBuilding();
                if (scene.mode !== Cesium.SceneMode.MORPHING) {
                    JCBZ.ldfm.initData.pickedLdObject = scene.pick(movement.position);
                    var pickedObject = JCBZ.ldfm.initData.pickedLdObject;
                    if (pickedObject) {
                        if (pickedObject.id) {
                            if (pickedObject.id._properties["_FWBM"]) {
                                var fwbh = pickedObject.id._properties["_FWBM"]._value;
                                var mc = pickedObject.id._properties["_MC"]._value;
                                var fwid = pickedObject.id._properties["_FWID"]._value;
                                pickedObject.id._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong-s.png';
                                JCBZ.ldfm.selectLdFm(fwbh);
                            }
                        }
                    }
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },

    //根据房屋或者阀门查询
    selectUpLdFm :function(type,fwbh){
        //已选择房屋，然后可以选择阀门
        JCBZ.ldfm.initData.fwStatus = 1;
        var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;
        if(JCBZ.ldfm.initData.pickedFmObject.length>0){
            var pickedFmObject = JCBZ.ldfm.initData.pickedFmObject;
            JCBZ.ldfm.initData.pickedFmObject.push(pickedObject.id._properties._EXP_NO._value);
            for (var k in pickedFmObject){
                for (var i in fmTcArr){
                    if(pickedFmObject[k]==fmTcArr[i]._properties._EXP_NO._value){
                        fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-off.png';
                    }
                }
            }
        }
        JCBZ.ldfm.initData.pickedFmObject = [];
        //首先根据房屋No查询该房屋是否绑定了阀门关系
        JCBZ.ldfm.fwNoSelectFm(fwbh);
    },

    //房屋No查询该房屋是否绑定了阀门关系
    fwNoSelectFm : function (fwbh){
        //查询上游阀们关系
        $.ajax({
            url : JCBZ.jcbzData.buildIngWaterUrl + '/api/getByTypeAndValue?chooseType='+ JCBZ.ldfm.initData.chooseType +'&value=' + fwbh,
            async:false,
            type:'GET',
            dataType:'json',
            success : function(data){
                if(data.code==0){
                    if(data.data.length>0){
                        var dataList = data.data;
                        $("#ldfmUpList").html("");
                        var str= "";
                        var num=0;
                        for(var i in dataList){
                            if(dataList[i].vbParent1!=null&&dataList[i].vbParent1!=""){
                                num++;
                                str = JCBZ.ldfm.getUpList(num,dataList[i].vbParent1,dataList[i].vbType,str)
                            }
                            if(dataList[i].vbParent2!=null&&dataList[i].vbParent2!=""){
                                num++;
                                str = JCBZ.ldfm.getUpList(num,dataList[i].vbParent2,dataList[i].vbType,str)
                            }
                            if(dataList[i].vbParent3!=null&&dataList[i].vbParent3!=""){
                                num++;
                                str = JCBZ.ldfm.getUpList(num,dataList[i].vbParent3,dataList[i].vbType,str)
                            }
                            if(dataList[i].vbParent4!=null&&dataList[i].vbParent4!=""){
                                num++;
                                str = JCBZ.ldfm.getUpList(num,dataList[i].vbParent4,dataList[i].vbType,str)
                            }
                            if(dataList[i].vbParent5!=null&&dataList[i].vbParent5!=""){
                                num++;
                                str = JCBZ.ldfm.getUpList(num,dataList[i].vbParent5,dataList[i].vbType,str)
                            }
                        }
                        $("#ldfmUpList").append(str);
                        //重新高亮楼栋影响的阀门
                        JCBZ.ldfm.highLightFm();
                        //重新渲染checkbox框
                        if(JCBZ.ldfm.initData.chooseType=="V"){
                            layui.use('form', function () {
                                var form = layui.form;
                                if (dataList[i].vbArea == "1") {
                                    $("#areaName").css("display","block");
                                    $("#areaName").val(dataList[i].vbAreaName);
                                    $("#areaCheckBox").prop("checked",true);
                                }else {
                                    $("#areaName").css("display","none");
                                    $("#areaCheckBox").prop("checked",false);
                                }
                                form.render();
                            });
                        }
                    }
                }
            },
        });
        //如果是阀门就查询下游阀门
        if(JCBZ.ldfm.initData.chooseType=="V"){
            $.ajax({
                url : JCBZ.jcbzData.buildIngWaterUrl + '/api/getByTypeAndValueForDown?chooseType='+ JCBZ.ldfm.initData.chooseType +'&value=' + fwbh,
                async:false,
                type:'GET',
                dataType:'json',
                success : function(data){
                    if(data.code==0){
                        if(data.data.length>0){
                            var dataList = data.data;
                            $("#ldfmDownList").html("");
                            var str= "";
                            var num=0;
                            for(var i in dataList){
                                num++;
                                str+=`<div class="layui-row ">
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>`+num+`</span>
                                        </div>
                                        <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                            <span>`+dataList[i].vbCode+`</span>
                                        </div>
                                        <!--<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>阀门</span>
                                        </div>-->
                                       </div>`;
                            }
                            $("#ldfmDownList").append(str);
                            //重新高亮楼栋影响的阀门
                            JCBZ.ldfm.highLightFm();
                        }
                    }
                },
            });
        }
    },

    //拼接上游阀门列表
    getUpList :function(num,valveNum,vbType,str){
        JCBZ.ldfm.initData.pickedFmObject.push(valveNum);
        str += `<div class="layui-row ">
                <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                    <span>`+num+`</span>
                </div>
                <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                    <span>`+valveNum+`</span>
                </div>
                <!--<div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                    <span>阀门</span>
                </div>-->
                <div class="layui-col-md2 layui-col-sm2 layui-col-xs2" onclick="JCBZ.ldfm.delFm(` + (`'`+valveNum+`'`) + `)">
                    <span>
                        <i class="iconfont icon-shanchu-copy-copy"></i>
                    </span>
                </div>
                </div>`;
        return str;
    },

    //删除阀门
    delFm: function(valveNum){
        var fmArr = JCBZ.ldfm.initData.pickedFmObject;
        for(var i in fmArr){
            if(fmArr[i]==valveNum){
                fmArr.splice(i,1);
                var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;
                //消除样式
                for (var i in fmTcArr){
                    if( valveNum == fmTcArr[i]._properties._EXP_NO._value){
                        fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-off.png';
                        break;
                    }
                }
            }
        }
        $("#ldfmUpList").html("");
        var str= "";
        var num=0;
        var type="V";
        JCBZ.ldfm.initData.pickedFmObject = [];
        for(var i in fmArr){
            num++;
            str = JCBZ.ldfm.getUpList(num,fmArr[i],type,str)
        }
        $("#ldfmUpList").append(str);
    },

    //重新高亮楼栋影响的阀门
    highLightFm : function(yxfm,fmTcArr){
        var pickedFmObject = JCBZ.ldfm.initData.pickedFmObject;
        var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;
        for (var k in pickedFmObject){
            for (var i in fmTcArr){
                if(pickedFmObject[k]==fmTcArr[i]._properties._EXP_NO._value){
                    fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen.png';
                }
            }
        }
    },

    //保存新的数据到后台或者修改
    saveLdfm : function (){
        if( JCBZ.ldfm.initData.pickedLdObject == null){
            layer.msg("未选择楼栋或阀门!");
            return;
        }
        var ld = "";
        var fm = JCBZ.ldfm.initData.pickedFmObject;
        var url = "";
        if(JCBZ.ldfm.initData.chooseType=="V"){
            ld = JCBZ.ldfm.initData.pickedLdObject.id._properties["_EXP_NO"]._value;
        }
        if(JCBZ.ldfm.initData.chooseType=="B"){
            ld = JCBZ.ldfm.initData.pickedLdObject.id._properties["_FWBM"]._value;
        }
        if( ld == ""){
            layer.msg("未选择楼栋或阀门!");
            return;
        }
        if(fm.length<=0){
            layer.msg("未选择上游阀门!");
            return;
        }
        //fm = fm.toString();
        var bwfJsonStr = `[{`;
        if(JCBZ.ldfm.initData.chooseType=="V"){
            var areaName = $("#areaName").val();
            if(areaName == ""){
                bwfJsonStr += `"vbCode":"`+ld +`","vbType":"`+JCBZ.ldfm.initData.chooseType +`","fmOid":"`+JCBZ.ldfm.initData.fmOid+`","vbArea":"0"`;
            }else{
                bwfJsonStr += `"vbCode":"`+ld +`","vbType":"`+JCBZ.ldfm.initData.chooseType +`","fmOid":"`+JCBZ.ldfm.initData.fmOid +`","vbArea":"1","vbAreaName":"`+areaName;
            }
        }else{
            bwfJsonStr += `"vbCode":"`+ld +`","vbType":"`+JCBZ.ldfm.initData.chooseType;
        }
        if(fm.length>0){
            for(var i in fm){

                bwfJsonStr += `","vbParent`+(parseInt(i)+1)+`":"`+fm[i];
            }
            bwfJsonStr += `"}]`;
        }
        $.ajax({
            //url : JCBZ.jcbzData.buildIngWaterUrl + '/save?buildingNo=' + ld + '&valveNo=' + fm,
            url : JCBZ.jcbzData.buildIngWaterUrl + '/save',
            data : {
                bwfJsonStr:bwfJsonStr
            },
            async : false,
            type : 'POST',
            dataType : 'json',
            success : function(data){
                if(data.code==0){
                    layer.msg("保存成功");
                }
            }
        });
    },
    //清除图层
    clearLdfm : function(){
        var ldTcArr = viewer.dataSources._dataSources[0].entities._entities._array;
        var fmTcArr = viewer.dataSources._dataSources[1].entities._entities._array;

        for (var i in fmTcArr){
            if((fmTcArr[i]._billboard._image._value.indexOf("famen")!=-1)){
                fmTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/famen-off.png';
            }
        }

        for (var i in ldTcArr){
            if((ldTcArr[i]._billboard._image._value.indexOf("loudong")!=-1)){
                ldTcArr[i]._billboard._image._value = ctx + 'bus/aupipes/img/3d/loudong.png';
            }
        }
        JCBZ.ldfm.clearLdfmData();
    },
    //清空数据
    clearLdfmData : function(){
        $("#ldFmName").val("");
        $("#areaDiv").css("display","none");
        $("#ldName").css("display","none");
        $("#fmName").css("display","none");
        $("#upTitle").css("display","none");
        $("#ldfmUpTitle").css("display","none");
        $("#ldfmUpList").html("");
        $("#downTitle").css("display","none");
        $("#ldfmDownTitle").css("display","none");
        $("#ldfmDownList").html("");
        JCBZ.jcbzData.fwStatus = 0;
        JCBZ.ldfm.initData.chooseType = '';
        JCBZ.ldfm.initData.pickedLdObject = null;
        JCBZ.ldfm.initData.pickedFmObject = [];
        layui.use('form', function () {
            var form = layui.form;
            $("#areaName").val("");
            $("#areaName").css("display","none");
            $("#areaCheckBox").prop("checked",false);
            form.render();
        });
    },
}

/*选取楼栋推送消息*/
JCBZ.tsxx = {
    //初始化方法
    init: function () {
        JCBZ.jcbzData.ldTsxxObj = {};
        JCBZ.jcbzData.ldTsxxObj1 = {};
        JCBZ.jcbzData.ldTsxxDelObj = {};
        JCBZ.jcbzData.ldTsxxDelObj1 = {};
        JCBZ.jcbzData.thLen = 0;
        JCBZ.jcbzData.pageTccCount = 0;
        JCBZ.jcbzData.isQxts = 0; //0表示未选择， 1表示选择
        JCBZ.tsxx.loadTsxx();
        JCBZ.tsxx.clickLd();
        //判断是点选还是多选
        JCBZ.tsxx.dxOrMx = 0;// 0点选   1面选

        //监听绘制图形
        drawHandler.drawEndEvent.addEventListener(layerTree.drawModel);
    },
    clickLd: function () {
        if (viewer) {
            viewer.screenSpaceEventHandler.setInputAction(function onLeftClick(movement) {
                var scene = viewer.scene;
                layerTree.removeLocatedBuilding();
                /*var drawType = Cesium.DrawMode.Circle;
                drawType = Cesium.DrawMode.Rectangle;
                drawHandler.startDrawingGeometry(drawType);*/
                if (scene.mode !== Cesium.SceneMode.MORPHING) {
                    var pickedObject = scene.pick(movement.position);
                    if (pickedObject) {
                        if (pickedObject.id) {
                            if (pickedObject.id._properties["_FWBM"]) {
                                var fwbh = pickedObject.id._properties["_FWBM"]._value;
                                var mc = pickedObject.id._properties["_MC"]._value;
                                var fwid = pickedObject.id._properties["_FWID"]._value;
                                JCBZ.tsxx.addBuild(fwbh, mc, fwid);
                            }
                        }
                    }
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
        }
    },
    //添加楼栋
    addBuild: function (fwBh, fwMc, fwId) {

        for (var i = 0; i < JCBZ.jcbzData.pageTccCount; i++) {
                   var strArr = [];
                   strArr = JCBZ.jcbzData.ldTsxxObj[i].split(",");
                   if (strArr[4] == fwBh && strArr[1] == fwMc) {
                       layer.msg("该楼栋已选择！");
                       return;
                   }
                }

        var areaNo = fwBh;
        var areaNoArr = areaNo.split(",");
        var areaName = fwMc;
        var areaNameArr = areaName.split(",");
        var thLen = '';
        layui.use(['table','layer'], function () {
            var $ = layui.jquery,
                layer = layui.layer
            var loadIndex = layer.load(1);
            $.ajax({
                url: JCBZ.jcbzData.prefix + "/getLdInfo?ldNum=" + areaNo,
                type: 'get',
                dataType: 'json',
                async: false,
                success: function (data) {
                    debugger;
                    if (data.code == 0) {
                        for(var i=0;i<data.data.length;i++){
                            thLen = JCBZ.jcbzData.thLen;
                            if (JCBZ.jcbzData.pageTccCount < 15) {
                                var str = `<div class="layui-row">
                                     <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span>` + (thLen + 1) + `</span>
                                    </div>
                                    <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                        <span>` + areaNameArr[i] + `</span>
                                    </div>
                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span>` + data.data[i].adminNum + `</span>
                                    </div>
                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                        <span>` + data.data[i].userNum + `</span>
                                    </div>
                                    <div class="layui-col-md2 layui-col-sm2 layui-col-xs2"  onclick="JCBZ.tsxx.delLd(` + (thLen) + `)">
                                        <span><i class="iconfont icon-shanchu-copy-copy"></i></span>
                                    </div>
                                </div>`;
                                $("#tsxxLdInfoList").append(str);
                            }
                            JCBZ.jcbzData.ldTsxxObj1[(thLen)] = (thLen + 1) + "," + areaNameArr[i] + "," + data.data[i].adminNum + "," + data.data[i].userNum + "," + areaNoArr[i];
                            JCBZ.jcbzData.ldTsxxObj = Object.assign(JCBZ.jcbzData.ldTsxxObj, JCBZ.jcbzData.ldTsxxObj1);
                            JCBZ.jcbzData.pageTccCount++;
                            JCBZ.jcbzData.thLen++;
                        }
                        //加载分页
                        JCBZ.tsxx.loadLdPage();
                    }
                    layer.close(loadIndex);
                }
            })
        });
    },
    //清除楼栋
    clearBuild: function () {
        drawShapeModel = null;
        layerTree.removePrimitiveByGuid("drawing");
        //重新初始化加载数据
        JCBZ.tsxx.init();
    },
    //加载楼栋分页
    loadLdPage: function () {
        var pageCount = 15;
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage
                , layer = layui.layer;
            laypage.render({
                elem: 'ldxqPage',
                count: JCBZ.jcbzData.pageTccCount,
                limit: pageCount,
                curr: (Math.ceil(JCBZ.jcbzData.pageTccCount / pageCount)),
                //layout: ['prev', 'next'],
                prev: '<',
                next: '>',
                layout: ['count', 'prev', 'page', 'next'],
                jump: function (obj, first) {
                    pageCount = 15;
                    if (JCBZ.jcbzData.pageTccCount > pageCount) {
                        var str = '';
                        var startNum = (obj.curr - 1) * pageCount
                        if (Math.ceil(JCBZ.jcbzData.pageTccCount / pageCount) > obj.curr) {
                            pageCount = (startNum + 15);
                        } else {
                            pageCount = JCBZ.jcbzData.pageTccCount;
                        }
                        for (var i = startNum; i < pageCount; i++) {
                            var detailData = [];
                            detailData = JCBZ.jcbzData.ldTsxxObj[(i)].split(",");
                            str += `<div class="layui-row">
                                         <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>` + (i + 1) + `</span>
                                        </div>
                                        <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                            <span>` + detailData[1] + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>` + detailData[2] + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                            <span>` + detailData[3] + `</span>
                                        </div>
                                        <div class="layui-col-md2 layui-col-sm2 layui-col-xs2"  onclick="JCBZ.tsxx.delLd(` + (i) + `)">
                                            <span><i class="iconfont icon-shanchu-copy-copy"></i></span>
                                        </div>
                                    </div>`;
                        }
                        $("#tsxxLdInfoList").html("");
                        $("#tsxxLdInfoList").append(str);
                    }
                }
            });
        })
    },

    //加载页面信息
    loadTsxx: function () {
        JCBZ.jcbzData.pageTccCount = 0;
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/tsxx", function (data) {
            //隐藏右侧
            rightBoxClose();
        });
    },

    //删除楼栋
    delLd: function (num) {
        //layer.confirm('确定删除么？', {
        //    btn: ['确定', '取消'] //按钮
        //}, function () {
        delete JCBZ.jcbzData.ldTsxxObj[num];
        JCBZ.jcbzData.ldTsxxDelObj = {};
        JCBZ.jcbzData.ldTsxxDelObj1 = {};
        var str = '';
        var ii = 0;
        for (var i = 0; i < (JCBZ.jcbzData.pageTccCount - 1); i++) {
            if (num > i) {
                var detailData = [];
                detailData = JCBZ.jcbzData.ldTsxxObj[i].split(",");
                str += `<div class="layui-row">
                             <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + (i + 1) + `</span>
                            </div>
                            <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                <span>` + detailData[1] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + detailData[2] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + detailData[3] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2"  onclick="JCBZ.tsxx.delLd(` + (i) + `)">
                                <span><i class="iconfont icon-shanchu-copy-copy"></i></span>
                            </div>
                        </div>`;
                JCBZ.jcbzData.ldTsxxDelObj1[(i)] = (i + 1) + "," + detailData[1] + "," + detailData[2] + "," + detailData[3] + "," + detailData[4];
                JCBZ.jcbzData.ldTsxxDelObj = Object.assign(JCBZ.jcbzData.ldTsxxDelObj, JCBZ.jcbzData.ldTsxxDelObj1);
            } else {
                var detailData = [];
                detailData = JCBZ.jcbzData.ldTsxxObj[i + 1].split(",");
                str += `<div class="layui-row">
                             <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + (i + 1) + `</span>
                            </div>
                            <div class="layui-col-md4 layui-col-sm4 layui-col-xs4">
                                <span>` + detailData[1] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + detailData[2] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2">
                                <span>` + detailData[3] + `</span>
                            </div>
                            <div class="layui-col-md2 layui-col-sm2 layui-col-xs2"  onclick="JCBZ.tsxx.delLd(` + (i) + `)">
                                <span><i class="iconfont icon-shanchu-copy-copy"></i></span>
                            </div>
                        </div>`
                JCBZ.jcbzData.ldTsxxDelObj1[(i)] = (i) + "," + detailData[1] + "," + detailData[2] + "," + detailData[3] + "," + detailData[4];
                JCBZ.jcbzData.ldTsxxDelObj = Object.assign(JCBZ.jcbzData.ldTsxxDelObj, JCBZ.jcbzData.ldTsxxDelObj1);
            }
        }
        JCBZ.jcbzData.ldTsxxObj = JCBZ.jcbzData.ldTsxxDelObj;
        $("#tsxxLdInfoList").html("");
        $("#tsxxLdInfoList").append(str);
        //JCBZ.jcbzData.thLen = $("#tsxxLdInfoList").children("div").length;
        JCBZ.jcbzData.thLen--;
        JCBZ.jcbzData.pageTccCount--;
        //加载分页
        JCBZ.tsxx.loadLdPage();
    },
    //选择楼栋推送消息
    ldxzSendMsg: function () {
        //提交完成后的事件
        if(JCBZ.jcbzData.isQxts == 0){
            if (JCBZ.jcbzData.pageTccCount == 0) {
                layer.msg("未选取楼栋！")
                return;
            }
        }

        layer.open({
            type: 2,
            title: '信息推送',
            area: ['560px', '450px'],
            content: JCBZ.jcbzData.prefix + '/ldXxts',
            skin: 'layer-style',
            id: 'layuiBox',
            end : function() {
                JCBZ.jcbzData.isQxts = 0;
            }
        });
    },
    //全校推送
    qxts: function (){
        JCBZ.jcbzData.isQxts = 1;
        JCBZ.tsxx.ldxzSendMsg();
        //layer.msg("已选择全校用户!")
    },

    //楼栋框选
    selectLd: function () {
        if(JCBZ.tsxx.dxOrMx == 0){
            JCBZ.tsxx.dxOrMx = 1;
        }else if (JCBZ.tsxx.dxOrMx == 1){
            JCBZ.tsxx.dxOrMx = 0;
        }
        layerTree.removePrimitiveByGuid("drawing");
        flagModel = true;
        drawHandler.startDrawingGeometry(Cesium.DrawMode.Polygon);
    },
    resultLd: function () {
        //多边形
        var geo = layerTree.transformCartesianArrayToGeoJson(drawShapeModel.positions);
        //矩形
        //var geo = layerTree.transformCartesianArrayToGeoJson(layerTree.transformRectangleToCartesianArray(drawShapeModel.rectangle));
        var geometry = {
            "type": geo.type,
            "coordinates": geo.coordinates
        };

        layerTree.searchBuild(layerCfg.xxtsLdId, geometry, function (res) {
            console.log(res);
            if (res.features.length > 0) {
                var fwbh = '';
                var mc = '';
                var fwid = '';
                for (var i = 0; i < res.features.length; i++) {
                    //判断楼栋选择
                    var m=0;
                    for (var k = 0; k < JCBZ.jcbzData.pageTccCount; k++) {
                        var strArr = [];
                        strArr = JCBZ.jcbzData.ldTsxxObj[k].split(",");
                        if (strArr[4] == res.features[i].properties.FWBM && strArr[1] == res.features[i].properties.MC) {
                            layer.msg("该楼栋已选择！");
                            m=1;
                            break;
                        }
                    }
                    if(m==0){
                        if (i == 0) {
                            fwbh += res.features[i].properties.FWBM;
                            mc += res.features[i].properties.MC;
                            fwid += res.features[i].properties.FWID;
                        } else {
                            fwbh += ("," + res.features[i].properties.FWBM);
                            mc += ("," + res.features[i].properties.MC);
                            fwid += ("," + res.features[i].properties.FWID);
                        }
                    }

                }
                if(fwbh!=""){
                    debugger
                    if(fwbh.substr(0,1)==","){
                        fwbh = fwbh.substr(1);
                        mc= mc.substr(1);
                        fwid = fwid.substr(1);
                    }
                    JCBZ.tsxx.addBuild(fwbh,mc,fwid);
                }
            }else{
                layer.msg("未选中楼栋");
            }
        })
    }


}

/*出线分析*/
JCBZ.cxfx = {
    init: function () {
        //JCBZ.lzxd.loadData();
        //清除右边div数据，加载新数据
        JCBZ.cleatAll();
        JCBZ.cxfx.loadCxfx();
    },
    loadCxfx: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/cxfx", function (data) {
            var url = ctx + "screen/yxjk/jdxx/powerHouse/tree";
            var treeId = "jcbz_monitor_tree";
            //隐藏右侧
            rightBoxClose();
            JCBZ.jcbzData.zTreeObj = ztree.initZTree(treeId, url, function (event, treeId, treeNode) {
                if (treeNode.maps.pid == "0") {
                    JCBZ.jcbzData.zTreeObj.expandNode(treeNode, !treeNode.open);
                    return;
                }
                //清除上一次操作的Bim模型结果
                GWYX.clearPickPoint();
                //20200304 当加载bim模型的时候才显示右侧内容，否则不显示
                var treeNode1 = layerTree.zTree.getNodeByParam("id", treeNode.maps.bsm);
                if (treeNode1 != null) {
                    rightBoxShow();
                    $('#right-panel').load(JCBZ.jcbzData.prefix + '/power');
                    //用来后面判断bim模型是否开启点选功能
                    pickType = "zsPoint";
                    //进入BimTree分层浏览场景
                    if (pickColor != null) {
                        //Cesium.Color.clone(pickColor.color,pickColor1);
                        pickColor = null;
                    }
                    layerTree.inToBimTreeScene(treeNode1);
                }
            },true,"cxfx_search",1);
        });
    }
};

/**
 * 拉闸分析
 * @type {{
 *  init: JCBZ.lzxd.init
 *  , lzxdLzfxNc: JCBZ.lzxd.lzxdLzfxNc
 *  , loadLzxdRightData: JCBZ.lzxd.loadLzxdRightData
 *  , loadLzxdRightBox: JCBZ.lzxd.loadLzxdRightBox
 *  , lzxdDownBox: JCBZ.lzxd.lzxdDownBox
 *  , loadLzxdData: JCBZ.lzxd.loadLzxdData
 *  , clearLeftTable: JCBZ.lzxd.clearLeftTable
 *  , loadData: JCBZ.lzxd.loadData
 *  , lzxdBhsgNc: JCBZ.lzxd.lzxdBhsgNc
 *  , loadLzxdLeftBox: JCBZ.lzxd.loadLzxdLeftBox
 *  }}
 */
JCBZ.lzxd = {
    init: function () {
        //JCBZ.lzxd.loadData();
        //清除右边div数据，加载新数据
        JCBZ.cleatAll();
        JCBZ.lzxd.loadLzxdRightBox();
    },
    /**
     * 拉闸分析查询结果集
     * @param 阀门列表
     * 根据阀门列表查询拉闸分析
     */
    lzxdResult: function (param) {

    },

    lzxdDownBox: function (oid) {
        $(".bottomLayer").load(JCBZ.jcbzData.prefix + "/dbxq?lzxdBhsgName=lzxd", function (data) {
            var lzNameStr = "lzxd";//判断是拉闸还是关阀
            JCBZ.lzxd.loadLzxdData(lzNameStr, oid);
        });
    },
    loadLzxdLeftBox: function (name) {
        JCBZ.loadLeftBox(JCBZ.jcbzData.prefix + "/sblb", function (data) {
            //主页跳转过来初始化高亮左侧图标
            JCBZ.lzxd.indexToJcbz(name);
            JCBZ.initMenuBtnClick(name);
        });
    },
    indexToJcbz: function (name) {
        switch (name) {
            case "lzfx":
                JCBZ.jcbzData.jcbzSubNavNum = 0;
                break;
            case "gfts":
                JCBZ.jcbzData.jcbzSubNavNum = 1;
                break;
            case "znpg":
                JCBZ.jcbzData.jcbzSubNavNum = 2;
                break;
            case "yagl":
                JCBZ.jcbzData.jcbzSubNavNum = 3;
                break;
            case "cxfx":
                JCBZ.jcbzData.jcbzSubNavNum = 4;
                break;
            default:
                break;
        }
        ;
        $(".mapIcon").removeClass("active");
        $(".jcbzSubNav").find("a").eq(JCBZ.jcbzData.jcbzSubNavNum).find("span").addClass("active");
    },
    loadLzxdRightBox: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/jcbzSgfxInit?name=lzxd", function (data) {

        });
    },
    //选取新地点 清空数据
    lzxdBhsgNc: function () {
        //隐藏数据
        $(".lzfxList").css("display", "none");
        $(".yxfwList").css("display", "none");
        $(".tsyhList").css("display", "none");
        bottonBoxHide();
        if (JCBZ.jcbzData.bhsgName == "csName") {
            JCBZ.jcbzData.bhsgName = "csName2";
        } else {
            JCBZ.jcbzData.bhsgName = "csName";
        }
        $(this).addClass('active')
    }
    ,
    //点击拉闸分析
    /*
     *  拉闸:oid 房屋id，或者配电房id， lzxdType： 标绘的是房屋还是配电房。
     *  关阀：oid 获取的房屋id或者管线， bhType：房屋/管线点
     */
    lzxdLzfxNc: function (oid, lzxdType, bhType) {
        //var name=$(this)[0].innerText.split(/\n/)[0];
        JCBZ.jcbzData.gftsBhType = '';
        var name = $(".tabBtn")[0].children[1].innerText
        if (name == "拉闸分析") {
            var s = "lzxd";
            JCBZ.lzxd.loadLzxdRightData(s, oid, lzxdType);
        } else if (name = "停水分析") {
            var s = "gfts";
            JCBZ.jcbzData.gftsBhType = bhType;
            JCBZ.lzxd.loadLzxdRightData(s, oid);
        }
        bottomBoxShow();
        var x = $('.tabBtnGroup1 .tabBtn').index(this);
        $(this).siblings('.tabBtn').removeClass('active')
        $(this).addClass('active')
        var $li = $(".bottomLayer .layui-tab-title li");
        var $content = $(".bottomLayer .layui-tab-item");
        $li.removeClass("layui-this").eq(0).addClass("layui-this");
        $content.removeClass("layui-show").eq(0).addClass("layui-show");
    }
    ,
    loadLzxdRightData: function (s, oid, lzxdType) {
        var url = "";
        if (s == "lzxd") {
            url = JCBZ.jcbzData.prefix + "/sgfx?lzxdType=" + lzxdType;
            JCBZ.jcbzData.fxTitleName = '配电房等级';
            JCBZ.jcbzData.fxTitleName_ = '附属物名称';
        } else if (s == "gfts") {
            url = JCBZ.jcbzData.prefix + "/gftsSgfx?gftsBhType=" + JCBZ.jcbzData.gftsBhType;
            JCBZ.jcbzData.fxTitleName = '位置';
            JCBZ.jcbzData.fxTitleName_ = '区域';
        }
        $.ajax({
            type: 'post',
            async: true,
            url: url + "&oid=" + oid,
            success: function (data) {
                $("#leftFxList").html("");
                $("#leftFxList").html(data);
                $(".lzfxList").css("display", "block");
                $(".yxfwList").css("display", "block");
                $(".tsyhList").css("display", "block");
                //加载下方数据
                if (s == "lzxd") {
                    JCBZ.lzxd.lzxdDownBox(oid);
                } else if (s == "gfts") {
                    JCBZ.gfts.lzxdGftsDownBox(oid);
                }
            }
        });
        //JCBZ.lzxd.loadLzxdData();
    },
    loadLzxdData: function (modelTypeName, oid) {
        layui.use(['element', 'tree', 'util', 'table', 'laypage'], function () {
            var $ = layui.jquery,
                element = layui.element,
                tree = layui.tree,
                layer = layui.layer,
                util = layui.util,
                table = layui.table,
                laypage = layui.laypage;

            //总页数大于页码总数
            laypage.render({
                elem: 'demo1'
                , count: 70 //数据总数
                , theme: '#2a9ea2'
                , jump: function (obj) {

                }
            });

            JCBZ.jcbzData.indexLoad = layer.load(2);
            //隐藏数据
            $("#lzfxList").css("display", "none");
            $("#yxfwList").css("display", "none");
            $("#tsyhList").css("display", "none");

            var width = ($('.bottomLayer').width() - 30) / 20;
            var tatleHeight = $(".moreTable").height() - 10
            //console.log(width)
            // 拉阀分析--详情
            table.render({
                elem: '#lffxTable',
                height: tatleHeight,
                skin: 'nob',
                url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=lzfx&modelTypeName=' + modelTypeName + '&oid=' + oid + '&bhType=' + JCBZ.jcbzData.gftsBhType //数据接口
                ,
                cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center', width: width},
                    {field: 'number', title: '编号', align: 'center'},
                    {field: 'name', title: JCBZ.jcbzData.fxTitleName_, align: 'center'},
                    {field: 'position', title: JCBZ.jcbzData.fxTitleName, align: 'center'},
                    {field: 'hlmc', title: '回路名称', align: 'center'},
                    {field: 'GH', title: '柜号', align: 'center'},
                    {field: 'address', title: '定位点', width: 50 }
                ]],
                response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
                ,
                data: [],
                done: function (res, curr, count) {
                    // 隐藏列
                    $(".layui-table-box").find("[data-field='address']").css("display", "none");
                    if (JCBZ.jcbzData.fxTitleName != "配电房等级") {
                        $(".layui-table-box").find("[data-field='hlmc']").css("display", "none");
                        $(".layui-table-box").find("[data-field='GH']").css("display", "none");
                    }
                },
                id: 'lffxFilter',
                page: true,
                limits: [5, 10, 20],
                limit: 5 //每页默认显示的数量
                ,
                parseData: function (res) {
                    var dataList = res.rows;
                    var arr = "";
                    for (var i = 0; i < dataList.length; i++) {
                        arr += dataList[i].number + ',';
                    }
                    if (JCBZ.jcbzData.typeName == '关阀分析') {
                        layerTree.locateBuildingByID(arr, "JS_POINT");
                    }
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

            table.on('row(lffxFilter)', function (obj) {
                //阀门定位
                if (JCBZ.jcbzData.fxTitleName == "配电房等级") {
                    layerTree.locateBuildingByID(obj.data.number, "TRANSFORMER_ROOM_ANNOTATION");
                } else if (JCBZ.jcbzData.fxTitleName != "配电房等级") {
                    if (obj.data.number != null) {
                        //var fmdw = obj.data.address.split(",");
                        layerTree.locateBuildingByID(obj.data.number, "JS_POINT");
                    }
                }
            });

            var tatleHeight = $(".moreTable").height();
            // 管线分析--详情
            /*table.render({
                elem: '#gxfxTable'
                , height: tatleHeight
                // ,width:width
                , skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                , url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=gxfx&modelTypeName=' + modelTypeName + '&oid=' + oid +'&bhType=' + JCBZ.jcbzData.gftsBhType//数据接口
                , cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center', width: width},
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
            table.on('row(gxfxFilter)', function (obj) {
                //管线定位
                layerTree.locateBuildingByID(obj.data.address,"JS_POINT");

            });*/

            // 影响范围--详情
            var tatleHeight = $(".moreTable").height()
            table.render({
                elem: '#yxfwTable'
                ,
                height: tatleHeight
                // ,width:width
                ,
                limits: [5, 10, 20]
                ,
                limit: 5 //每页默认显示的数量
                ,
                skin: 'nob'
                // ,size: 'sm' //小尺寸的表格
                ,
                url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=yxfw&modelTypeName=' + modelTypeName + '&oid=' + oid + '&bhType=' + JCBZ.jcbzData.gftsBhType//数据接口
                ,
                cols: [[ //标题栏
                    {field: 'serial', title: '序号', align: 'center', width: width},
                    {field: 'pinqu', title: '片区', align: 'center',},
                    {field: 'loudong', title: '楼栋', align: 'center'},
                    {field: 'type', title: '类型', align: 'center'},
                    {field: 'buildNum', title: '编号', width: 50, style: 'display:none;'}
                ]]
                ,
                response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
                ,
                data: []
                ,
                id: 'yxfwFilter'
                ,
                page: true
                ,
                parseData: function (res) {
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
                ,
                done: function (res, curr, count) {
                    // 隐藏列
                    $(".layui-table-box").find("[data-field='buildNum']").css("display", "none");
                }
            });

            table.on('row(yxfwFilter)', function (obj) {
                //定位
                layerTree.locateBuildingByID(obj.data.buildNum, "HOUSE_ANNOTATION");
            });

            // 获取用户--详情
            JCBZ.lzxd.getHqyhTree(tree, table, modelTypeName);
        });
    },

    //加载获取人员树数据
    getHqyhTree: function (tree, table, modelTypeName) {
        //获取列表
        // 影响范围--详情
        var width = ($('.bottomLayer').width() - 30) / 20;
        var tatleHeight = $(".moreTable").height()
        table.render({
            elem: '#hqyhTabe'
            ,
            height: tatleHeight
            // ,width:width
            ,
            limits: [5, 10, 20]
            ,
            limit: 5 //每页默认显示的数量
            ,
            skin: 'nob'
            // ,size: 'sm' //小尺寸的表格
            ,
            url: JCBZ.jcbzData.prefix + '/dbxqBoxData?type=tsyh&modelTypeName=' + modelTypeName + '&oid=&bhType=' + JCBZ.jcbzData.gftsBhType//数据接口
            ,
            cols: [[ //标题栏
                {field: 'serial', title: '序号', align: 'center', width: width},
                {field: 'name', title: '姓名', align: 'center',},
                {field: 'loudong', title: '楼栋', align: 'center'},
                {field: 'type', title: '类型', align: 'center'},
                {field: 'buildNum', title: '编号', width: 50, style: 'display:none;'},
                //{field: 'userId', title: '微信id', width: 50, style: 'display:none;'},
                //{field: 'phoneNum', title: '手机号', width: 50, style: 'display:none;'},
                {field: 'userId', title: '用户ID', width: 50, style: 'display:none;'}
            ]]
            ,
            response: {statusName: "status", statusCode: 200, dataName: "rows", countName: "total"}//状态要设置为200
            ,
            data: []
            ,
            id: 'hqyhFilter'
            ,
            page: true
            ,
            parseData: function (res) {
                if (typeof res.rows != "undefined") {
                    var dataList = res.rows;
                    var total = dataList.length; //请求的数据总数
                    //$(".jmyhCount").text("");
                    //$(".jmyhCount").text(total);
                    var list = []; //用来保存当前页显示的数据
                    //前端模拟分页，获取当前页、分页显示数据量
                    //var page = $(".layui-table-page").find(".layui-laypage-em").next().html();
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
            }
            ,
            done: function (res, curr, count) {
                // 隐藏列
                $(".layui-table-box").find("[data-field='buildNum']").css("display", "none");
                //$(".layui-table-box").find("[data-field='openId']").css("display", "none");
                //$(".layui-table-box").find("[data-field='phoneNum']").css("display", "none");
                $(".layui-table-box").find("[data-field='userId']").css("display", "none");
            }
        });
        //获取左侧人数数量
        //JCBZ.jcbzData.ldNumList = [];
        //获得选择的楼栋
        var jmyhcount = 0;
        var ssgly = 0;
        var hqb = 0;
        var jmgly = 0;
        var jxgly = 0;
        var jmyh = 0;
        //获取全部楼栋信息 勾选上面获得的楼栋信息
        //jQuery.get(JCBZ.jcbzData.prefix + "/lzxdUserTree?type="+modelTypeName +'&bhType=' + JCBZ.jcbzData.gftsBhType, "", function (data) {
        jQuery.get(JCBZ.jcbzData.prefix + '/dbxqBoxData?type=tsyh&modelTypeName=' + modelTypeName + '&oid=&bhType=' + JCBZ.jcbzData.gftsBhType, "", function (data) {
            if (typeof data.rows != "undefined") {
                var personList = data.rows;
                $(".jmyhCount").text("");
                $(".ssManageCount").text("");
                $(".hqbCount").text("");
                $(".jxManageCount").text("");
                if (personList.length > 0) {
                    if (personList.length == 0) {
                        JCBZ.jcbzData.hqyhStatus = 1;
                    } else {
                        JCBZ.jcbzData.hqyhStatus = 2;
                    }
                }
                for (var i = 0; i < personList.length; i++) {
                    if (personList[i].type == "宿舍管理员") {
                        ssgly++;
                    } else if (personList[i].type == "后保部") {
                        hqb++;
                    } else if (personList[i].type == "居民管理员") {
                        ssgly++;
                    } else if (personList[i].type == "教学管理员") {
                        jxgly++;
                    } else {
                        jmyh++;
                    }
                }
                //保存用户手机号和微信id
                for (var i = 0; i < personList.length; i++) {
                    if (personList[i].phoneNum == "") {
                        personList[i].phoneNum = "null";
                    }
                    if (i == 0) {
                        //JCBZ.jcbzData.lzxdXxtsHqyhWxStr += dataList[i].openId;
                        //JCBZ.jcbzData.lzxdXxtsHqyhPhoneStr += dataList[i].phoneNum;
                        JCBZ.jcbzData.lzxdXxtsHqyhUserIdStr += personList[i].userId;
                    } else {
                        //JCBZ.jcbzData.lzxdXxtsHqyhWxStr += "," + dataList[i].openId;
                        //JCBZ.jcbzData.lzxdXxtsHqyhPhoneStr += "," + dataList[i].phoneNum;
                        JCBZ.jcbzData.lzxdXxtsHqyhUserIdStr += ("," + personList[i].userId);
                    }
                }
                $(".ssManageCount").text(ssgly);
                $(".hqbCount").text(hqb);
                $(".jmyhCount").text(jmyh);
                $(".jxManageCount").text(jxgly);
            }
            layer.close(JCBZ.jcbzData.indexLoad);    //返回数据关闭loading
        })
    },
}

/**
 * 智能排管
 * @type {{
 *  init: JCBZ.znpg.init
 *  , znpgRightBox: JCBZ.znpg.znpgRightBox
 *  , clearZnpgLeftTable: JCBZ.znpg.clearZnpgLeftTable
 *  , loadZnpgData: JCBZ.znpg.loadZnpgData
 *  , znpgLeftTree: JCBZ.znpg.znpgLeftTree
 *  }}
 */
JCBZ.znpg = {
    init: function () {
        JCBZ.cleatAll();
        //清楚智能排管左边模块内容
        JCBZ.znpg.clearZnpgLeftTable();
    },
    znpgLeftTree: function () {
        JCBZ.loadLeftBox(JCBZ.jcbzData.prefix + "/znpgSblb", function (data) {

        });
    },
    //智能排管的绘制管线
    drawLine: function () {
        GeometryUtil.drawPolyline(gViewer, function () {

        });
    },
    //智能排管的绘制管线清除
    drawClear: function () {
        GeometryUtil.clearEntites(gViewer);
        Tube.clearTubes(gViewer);
    },
    znpgRightBox: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/znpg", function (data) {
            layui.use('form', function () {
                var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
                //但是，如果你的HTML是动态生成的，自动渲染就会失效,渲染select下拉框
                //因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();

                //加载管线规格下拉选择
                $.get(ctx + "system/dict/data/api/list?dictType=pipeNorms&pageSize=30", function (data) {
                    var html = '<option value=""></option>';
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    $("select[name='norms']").html(html);
                    form.render('select');
                });
                //加载管线类型下拉选择
                $.get(ctx + "system/dict/data/api/list?dictType=pipesType", function (data) {
                    var rows = data.data;
                    var html = '<option value=""></option>';
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    $("select[name='lineType']").html(html);
                    form.render('select');
                });
                //加载埋设方式下拉选择
                $.get(ctx + "system/dict/data/api/list?dictType=plantType", function (data) {
                    var html = '<option value=""></option>';
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    $("select[name='layerOutType']").html(html);
                    form.render('select');
                });
                form.render();
            });
        });
    },
    clearZnpgLeftTable: function () {
        //清除右边模块并加载内容
        JCBZ.znpg.znpgRightBox();
    }

}

/**
 * 关阀停水
 * @type {{
 *    init: JCBZ.gfts.init
 *  , loadGftsAllTable: JCBZ.gfts.loadGftsAllTable
 *  , loadGftsLeftBox: JCBZ.gfts.loadGftsLeftBox
 *  , loadGftsRightBox: JCBZ.gfts.loadGftsRightBox
 *  , lzxdGftsDownBox: JCBZ.gfts.lzxdGftsDownBox
 *  }}
 */
JCBZ.gfts = {
    init: function () {
        JCBZ.cleatAll();
        JCBZ.gfts.loadGftsAllTable();
    },
    loadGftsAllTable: function () {
        JCBZ.gfts.loadGftsRightBox();
        //JCBZ.gfts.lzxdGftsDownBox();   初始化不加载数据


    },
    /**
     * 加载关阀停水
     */
    loadGftsRightBox: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/jcbzSgfxInit?name=gfts");
    },
    lzxdGftsDownBox: function (oid) {
        $(".bottomLayer").html("");
        $(".bottomLayer").load(JCBZ.jcbzData.prefix + "/dbxq?lzxdBhsgName=gfts", function (data) {
            var gfNameStr = "gfts";//加载数据类型 判断拉闸还是关阀
            JCBZ.lzxd.loadLzxdData(gfNameStr, oid);
        });
    },
    /**
     *  查询关阀停水结果集
     *  参数: 阀门列表  param
     *  根据阀门列表查询数据
     *
     */
    gftsResult: function (param) {

    }
}

/**
 * 预案管理
 * @type {{
 *  downloadFile: JCBZ.yagl.downloadFile 文件下载
 *  , init: JCBZ.yagl.init 初始化
 *  , addYaList: JCBZ.yagl.addYaList 预案列表
 *  , stepPic: JCBZ.yagl.stepPic   步骤示意
 *  , optionStep: JCBZ.yagl.optionStep 预案列表右侧操作内容
 *  }}
 */
JCBZ.yagl = {
    init: function () {
        JCBZ.yagl.loadYaglRightBox();
    },
    loadYaglRightBox: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + '/yagl', function (data) {

            $(".zs_yalb").html("");
            var type = $(".zs_mapEdit li")[0].innerText;
            $(".zs_mapEdit li").eq(0).addClass('active');
            JCBZ.jcbzData.type = $(".zs_mapEdit li")[0].children[0].className;
            //加载预案列表
            JCBZ.yagl.addYaList(type);
            $(".zs_mapEdit").on('click', 'li', function () {
                $(".zs_mapEdit li").removeClass('active');
                $(this).addClass('active');
                $(".zs_yalb").html("");
                var type = $(this)[0].innerText;
                JCBZ.jcbzData.type = $(this)[0].children[0].className;
                //加载预案列表
                JCBZ.yagl.addYaList(type);
                JCBZ.yagl.selectedLinePipe(layerCfg.jcbz.all);


                LZXD.clearPic();
                GFTS.clearPic();
                //清除预案动画的定时函数
                if (timer) {
                    clearInterval(timer);
                    $(".planloadingcontent").css("display", "none");
                }
                if (particle1) {
                    gViewer.scene.primitives.remove(particle1);
                }

            });
        });
    },
    addYaList: function (type) {
        $('.zs_yalb').load(JCBZ.jcbzData.prefix + '/yalb?type=' + type, function (data) {
            rightBoxClose();
            $(".yalbBox").on('click', 'li', function () {
                $(".yalbBox li").removeClass('active');
                $(this).addClass('active');
                var src = $(this).attr('src');
                var fileName = $(this)[0].innerText;
                //显示右侧内容
                rightBoxShow();
                //加载右侧预案操作
                JCBZ.yagl.optionStep(src, fileName);
                //查看预案管理文件
                //JCBZ.yagl.stepPic(src, fileName);

            })
        });
    },
    //预案列表右侧操作内容
    optionStep: function (src, fileName) {
        $('#right-panel').load(JCBZ.jcbzData.prefix + '/yabz', function (data) {

            //加载步骤示意
            $('.zs_bzsy').load(JCBZ.jcbzData.prefix + '/bzsy', function (data) {
                JCBZ.yagl.videoStep(JCBZ.jcbzData.type);
            });

            $(".zs_look").click(function () {
                var name = $(this)[0].innerText;
                $(".zs_look").removeClass('active');
                $(this).addClass('active');
                if (name.trim() == '文档查看') {
                    JCBZ.yagl.stepPic(src, fileName);
                } else if (name.trim() == '动画演示') {
                    JCBZ.yagl.videoPlay();
                } else {
                    JCBZ.yagl.downloadFile(src, fileName);
                }
            })
        });
    },
    //步骤示意
    stepPic: function (src, fileName) {
        var indexNum = src.indexOf('-') + 1;
        var str = src.substr(indexNum).substring(0, 3);
        if (str == "pdf") {
            src = src.substring(0, indexNum - 1) + ".pdf";
        }
        var index = layer.open({
            type: 2,
            title: fileName,
            //content: ctx + src,
            content: src,
            area: ['70%', '90%'],
            maxmin: true
        });
        // layer.full(index);

    },
    //文件下载
    downloadFile: function (src, fileName) {
        var url = ctx + 'prd/attachment/api/series?url=' + src;
        jQuery.get(url, function (data) {
            var a = document.createElement("a");
            //a.href = data.data.original;
            a.href = (ctx + "profile" + data.data.original.split("profile")[1]);
            a.download = fileName;
            a.click();
        });
    },
    //动画演示
    videoPlay: function () {
        if (JCBZ.jcbzData.type.indexOf("iconfont icon-fanwei1") > -1) {
            $(".spczBox").css("display", "block");
            showRightPanel();
        } else if (JCBZ.jcbzData.type.indexOf("iconfont icon-zhuangjirongliang") > -1) {
            $(".spczBox").css("display", "block");
            pdfVideoPlay();
        } else if (JCBZ.jcbzData.type.indexOf("iconfont icon-humidity") > -1) {
            JCBZ.yagl.selectedLinePipe(layerCfg.jcbz.bgts);
            $(".spczBox").css("display", "block");
            bgtsVideoPlay();
        } else {
            layer.msg("暂无动画");
        }
    },
    //预案的一些步骤
    videoStep: function (type) {
        var html = "";
        var step = layerCfg.jcbz.yaglStep[type];
        if (step["step"].length > 0) {
            html += `<p>步骤示意</p>
                    <ul class="planloadingcenterUl">`;
            for (var i = 0; i < step["step"].length; i++) {
                html += `<li class="noshow">
                                <div class="alabo">` + (i + 1) + `</div>
                                <div class="buzhou">步骤` + step["step"][i] + `</div>
                                <div class="planloadingcenterUltext">` + step["label"][i] + `</div>
                            </li>`;
            }
            html += "</ul>";
        } else {
            html = "";
        }
        $(".planloadingcenter").html(html);

    },
    selectedLinePipe: function (nameArr) {
        //获取第一层子节点
        var list = $("#tt")[0].children;
        var treeObj = layerTree.zTree;

        //清除半透明倾斜摄影
        var node = layerTree.zTree.getNodeByParam("id", "OBLIQUE_PHOTOGRAPHY", null);
        if (node.checked == true && nameArr.length > 0 && JCBZ.jcbzData.type.indexOf("iconfont icon-humidity") > -1) {
            layerTree.setAlpha(node, 0.5);
        } else {
            if (node.checked == true) {
                layerTree.setAlpha(node, 1);
            }
        }


        //遍历，清除倾斜摄影之外的已勾选的图层
        for (var i = 0; i < list.length; i++) {
            var node = treeObj.getNodeByTId(list[i].id);
            var name = node.name;
            if (name != "倾斜摄影") {
                if (node.checked == true) {
                    treeObj.checkNode(node, "", true, true);
                }
            }
        }
        //勾选选中的图层
        for (var j = 0; j < nameArr.length; j++) {
            if (layerTree && layerTree.zTree) {
                var childrenNode = layerTree.zTree.getNodeByParam("id", nameArr[j], null);
                treeObj.checkNode(childrenNode, "", true, true);
            }
        }
    }
}

//开挖分析
JCBZ.kwfx = {
    data: {
        area: null,  //面积
        depth: null,     //开挖深度
        drawShape: null, //绘制的图形
        type: 0, //绘制的图形类型 默认圆 0圆 1矩形 2多边形
        geoJson: null,    //图形数据
        jsonArr: [],    //结果集合
        index: 1,    //查询的结果计数
        arrNum: null,
        gxdwData: [], //管线段定位点的数据
    },
    init: function () {
        JCBZ.cleatAll();
        //清楚智能排管左边模块内容
        JCBZ.kwfx.loadCxfx();
        //Listen draw end event. 监听绘制完成事件.
        drawHandler.drawEndEvent.addEventListener(layerTree.drawModel);
    },
    loadCxfx: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/kwfx", function (data) {
            //隐藏右侧
            rightBoxClose();
            layui.use('form', function () {
                var form = layui.form;
                form.render();
                form.render('checkbox');
            });
        });
    },
    //开始挖洞
    startWd: function () {
        if (drawShapeModel != null) {
            var geo;
            if (JCBZ.kwfx.data.type == 0) {
                // 笛卡尔坐标转经纬度（单位:弧度）
                var cartographic = Cesium.Cartographic.fromCartesian(drawShapeModel.center);
                // 弧度转度
                var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                geo = {longitude: longitude, latitude: latitude};
                var radius = drawShapeModel.radius;
                var obj = [{value: Math.PI * radius * radius}];
                this.calArea(obj);
                var geometry = {
                    type: "Point",
                    coordinates: [parseFloat(longitude), parseFloat(latitude)]
                };
                var bufferOptions = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/buffer",
                    geoSRS: "EPSG:4326",
                    outSRS: "EPSG:4326",
                    data: JSON.stringify(geometry),
                    radius: radius
                };
                layerTree.getBufferOfGeometry(bufferOptions, function (bufferGeometry) {
                    JCBZ.kwfx.data.geoJson = {
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
                    JCBZ.kwfx.startAnalysis();
                });
            } else if (JCBZ.kwfx.data.type == 1) {
                geo = layerTree.transformCartesianArrayToGeoJson(layerTree.transformRectangleToCartesianArray(drawShapeModel.rectangle));
                var options = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/area",
                    geoSRS: 'EPSG:4326',
                    outSRS: 'EPSG:3857',
                    data: JSON.stringify(
                        {
                            "type": geo.type,
                            "coordinates": geo.coordinates
                        })
                };
                JCBZ.kwfx.data.geoJson = {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {},
                            "geometry": {
                                "type": geo.type,
                                "coordinates": geo.coordinates
                            }
                        }
                    ]
                }
                JCBZ.kwfx.data.area = layerTree.caculateAreaOfGeometry(options, this.calArea);
                JCBZ.kwfx.startAnalysis();
            } else if (JCBZ.kwfx.data.type == 2) {
                geo = layerTree.transformCartesianArrayToGeoJson(drawShapeModel.positions);
                var options = {
                    url: aupipeService + "/KQGis/rest/services/geometryserver/area",
                    geoSRS: 'EPSG:4326',
                    outSRS: 'EPSG:3857',
                    data: JSON.stringify(
                        {
                            "type": geo.type,
                            "coordinates": geo.coordinates
                        })
                };
                JCBZ.kwfx.data.geoJson = {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {},
                            "geometry": {
                                "type": geo.type,
                                "coordinates": geo.coordinates
                            }
                        }
                    ]
                }
                layerTree.caculateAreaOfGeometry(options, this.calArea);
                JCBZ.kwfx.startAnalysis();
            }
        } else {
            layer.msg("请先绘制图形！");
        }
    },
    //开始分析
    startAnalysis: function () {
        selectedLinePipe([]);
        JCBZ.kwfx.data.index = 1;
        JCBZ.kwfx.data.jsonArr = [];
        JCBZ.kwfx.data.gxdwData = [];
        bottonBoxHide();
        JCBZ.kwfx.data.depth = $("input[name='depth']").val();//开挖深度
        var list = $(".switchBtn");
        var arr = [];
        for (var i = 0; i < list.length; i++) {
            if (list[i].checked == true) {
                arr = arr.concat(layerCfg.kwfxId[list[i].name]);
            }
        }
        JCBZ.kwfx.data.arrNum = arr;
        if (JCBZ.kwfx.data.depth.trim() != "") {
            if (arr.length > 0) {
                for (var i = 0; i < arr.length; i++) {
                    layerTree.searchAround(arr[i], JCBZ.kwfx.data.geoJson.features[0].geometry, JCBZ.kwfx.searchResult);
                }
            } else {
                layer.msg("请先勾选图层");
            }
        } else {
            layer.msg("请输入开挖深度！");
        }
    },
    //清除
    clear: function () {
        $("#kwxq").html('');
        layerTree.removePrimitiveByGuid('drawing');
        drawShapeModel = null;
        JCBZ.kwfx.data.area = null;
        bottonBoxHide();
        JCBZ.kwfx.data.jsonArr = [];
        JCBZ.kwfx.data.gxdwData = [];
    },
    //开始绘制
    draw: function () {
        JCBZ.kwfx.clear();
        JCBZ.kwfx.data.type = $("select[name='geoType']").val();//绘制方式
        var drawType = Cesium.DrawMode.Circle;
        if (JCBZ.kwfx.data.type == 0) {  //圆形
            drawType = Cesium.DrawMode.Circle;
        } else if (JCBZ.kwfx.data.type == 1) {    //矩形
            drawType = Cesium.DrawMode.Rectangle;
        } else if (JCBZ.kwfx.data.type == 2) {    //多边形
            drawType = Cesium.DrawMode.Polygon;
        }
        //Draw Polygon
        drawHandler.startDrawingGeometry(drawType);
    },
    //面积回调
    calArea: function (area) {
        JCBZ.kwfx.data.area = area[0].value;
        JCBZ.kwfx.data.depth = $("input[name='depth']").val();//开挖深度
        $("#kwxq").html("");
        if (JCBZ.kwfx.data.depth.trim() != "") {
            var depth = JCBZ.kwfx.data.depth;
            var kwtf = JCBZ.kwfx.data.area * depth;
            /*if(kwtf.indexOf(".")>=0){
                if(kwtf.split(".")[1].length>4){
                    kwtf = (kwtf.split(".")[0] + "." + kwtf.split(".")[1].substring("0","4"));
                }
            }*/
            var html = `<div class="title3 clGreen">开挖详情</div>
                    <ul class="sbxxList">
                    <li>开挖深度：<span>` + depth + `米</span></li>
                <li>开挖面积：<span>` + JCBZ.kwfx.data.area.toFixed("4") + `平方米</span></li>
                <li>开挖土方：<span>` + kwtf.toFixed("4") + `立方米</span></li>
                </ul>`;
            $("#kwxq").html(html);
        } else {
            layer.msg("请填写开挖深度！");
        }
    },
    //查询结果
    searchResult: function (data, id, name) {
        if (data.features) {
            var json = {name: name, id: id, data: data};
            JCBZ.kwfx.data.gxdwData.push(json);

            var arr = [];
            for (var i = 0; i < data.features.length; i++) {
                var obj = data.features[i].properties;
                obj.typeId = id;
                arr.push(obj);
            }
            var obj = {result: arr, id: id, name: name};
            JCBZ.kwfx.data.jsonArr.push(obj);
        }
        if (JCBZ.kwfx.data.arrNum.length < JCBZ.kwfx.data.index) {
            if (JCBZ.kwfx.data.jsonArr.length > 0) {
                JCBZ.kwfx.showResult();
                var obj = [];
                for (var i = 0; i < JCBZ.kwfx.data.jsonArr.length; i++) {
                    obj.push(layerCfg.kwfxLayer[JCBZ.kwfx.data.jsonArr[i].name])
                }
                selectedLinePipe(obj);
            } else {
                layer.msg("未搜索结果！");
            }

        }
    },
    showResult: function () {
        var width = ($('.bottomLayer').width() - 30) / 20;
        $("#bottom-panel").load(ctx + 'screen/jcbz/searchTab', function (res) {
            $("#searchTab").html("");
            $("#searchTabList").html("");

            var html = '';
            var content = '';
            var idArr = [], col = [], result = {};
            var data = JCBZ.kwfx.data.jsonArr;
            for (var key in data) {
                html += '<li>' + data[key].name + '</li>';
                content += `<div class="layui-tab-item">
                            <div class="table moreTable page">
                                <div id="address` + data[key].id + `" lay-filter="address` + data[key].id + `"></div>
                            </div>
                        </div>`;
                idArr.push("address" + data[key].id);
                var obj = [{
                    field: 'serial',
                    title: '序号',
                    align: 'center',
                    width: width,
                    templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>'
                }];
                if (data[key].name.indexOf('点') > -1) {
                    for (var i in layerCfg.kwfxPoint) {
                        var objChildren = {field: i, title: layerCfg.kwfxPoint[i], align: 'center'};
                        obj.push(objChildren);
                    }
                    col.push(obj);
                } else {
                    for (var i in layerCfg.kwfxLine) {
                        var objChildren = {field: i, title: layerCfg.kwfxLine[i], align: 'center'};
                        obj.push(objChildren);
                    }
                    col.push(obj);
                }

            }
            result = JCBZ.kwfx.data.jsonArr;
            $("#searchTab").html(html);
            $("#searchTabList").html(content);
            $("#searchTab").find("li").eq(0).addClass("layui-this");
            $("#searchTabList").find(".layui-tab-item").eq(0).addClass("layui-show");

            JCBZ.kwfx.tableRender(idArr, result, col);
        });
        bottomBoxShow();
    },
    tableRender: function (id, data, col) {
        layui.use(['table', 'laypage'], function () {
            var $ = layui.jquery;
            tableTab = layui.table;
            laypageTab = layui.laypage;
            var tatleHeight = $(".moreTable").height() - 10;
            for (var i = 0; i < data.length; i++) {
                var cols = col[i];
                var ids = id[i];
                if(data[i]["result"].length>0){
                    if(typeof data[i]["result"][0].D_TYPE !="undefined" ){
                        for(var k = 0;k<data[i]["result"].length;k++){
                            for (var p in layerCfg.kwfxMsfs) {
                                if( ("ms"+data[i]["result"][k].D_TYPE) == p){
                                    data[i]["result"][k].D_TYPE = layerCfg.kwfxMsfs[p];
                                }
                            }
                        }
                    }
                }

                tableTab.render({
                    elem: '#' + ids
                    , height: tatleHeight
                    , skin: 'nob'
                    , limit: 5
                    , limits: [5, 10, 20, 30]
                    , data: data[i]["result"]
                    , cols: [cols]
                    , page: true
                });

                tableTab.on('rowDouble(' + ids + ')', function (obj) {
                    layerTree.removeLocatedBuilding();
                    for (var i = 0; i < JCBZ.kwfx.data.gxdwData.length; i++) {
                        if (JCBZ.kwfx.data.gxdwData[i].id == obj.data.typeId) {
                            var data = JCBZ.kwfx.data.gxdwData[i].data.features;
                            for (var j = 0; j < data.length; j++) {
                                if (data[j].properties["OID"] == obj.data.OID) {
                                    var json = {
                                        features: [data[j]],
                                        fields: JCBZ.kwfx.data.gxdwData[i].data.fields,
                                        type: "FeatureCollection"
                                    };
                                    layerTree.addGeoJsonLayer(layerTree.reformattedGeoJsonData(json), layerTree.setOptionsByType("ADDRESS"));
                                }
                            }
                        }
                    }
                });
            }
        });
    }

};

//横断面分析
JCBZ.hdfx = {
    data: {
        drawShape: null,

    },
    init: function () {
        JCBZ.cleatAll();
        //清除左边模块内容
        JCBZ.hdfx.loadCxfx();
        //Listen draw end event. 监听绘制完成事件.
        drawHandler.drawEndEvent.addEventListener(layerTree.drawModel);
    },
    loadCxfx: function () {
        $(".zs-leftContentBox").html("");
        $(".zs-leftContentBox").load(JCBZ.jcbzData.prefix + "/hdmfx", function (data) {
            //隐藏右侧
            rightBoxClose();
            layui.use('form', function () {
                var form = layui.form;
                form.render();
                form.render('select');
            });
        });
    },
    //分析
    analysis: function (type) {
        //selectedLinePipe([]);
        if (drawShapeModel == null) {
            layer.msg("请先进行绘制！");
            return false;
        }
        var line = layerTree.transformCartesianArrayToGeoJsonLine(drawShapeModel.positions);
        var lineStr = {type: "LineString", coordinates: line.coordinates[0]};
        var list = $(".switchBtn");
        var str = '';
        var arr = [];
        for (var i = 0; i < list.length; i++) {
            if (list[i].checked == true) {
                str += list[i].name + ',';
                arr.push(list[i].name);
            }
        }
        var url = layerCfg.hdmfxUrl + '?lineType=' + str.substring(0, str.length - 1) + '&lineStr=' + JSON.stringify(lineStr);
        if (arr.length > 0) {
            $.get(url, function (res) {
                console.log(res);
                if (res.code == 0) {
                    if (res.data.length > 0) {
                        var width = $(".insidePageTop").width() * 0.6;
                        var height = $(document).height() * 0.7;
                        var html = `<div class='attributesBox' id='attributesBox'>
                            <div id='hdmfx' style='height: ` + height * 0.6 + `px;width:` + width * 0.95 + `px;'></div>
                            <div id='pgfxTabe'></div>
                            </div>`;
                        if (indexLayer != undefined) {
                            layer.close(indexLayer);
                            indexLayer = "";
                        }
                        indexLayer = layer.open({
                            type: 1,
                            title: '属性信息',
                            area: [width + 'px', height + 'px'],
                            offset: 'b',
                            shade: false,
                            content: html,
                            skin: 'layer-style',
                            success: function (layer) {
                                var linePoint = [[line.coordinates[0][0][0], line.coordinates[0][0][1]], [line.coordinates[0][1][0], line.coordinates[0][1][1]]];
                                console.log(linePoint);
                                var data = [];
                                var result = [];
                                for (var m = 0; m < res.data.length; m++) {
                                    var x = Number(res.data[m].crossPoint.x);
                                    var y = Number(res.data[m].crossPoint.y);
                                    var z = Number(res.data[m].crossPoint.m);
                                    var point = [x, y, z];
                                    data.push(point);
                                    var obj = {
                                        name: res.data[m].name,
                                        depth: res.data[m].crossPoint.m,
                                        id: res.data[m].feature.properties.PIPEL_ID,
                                        address: res.data[m].feature.properties.R_NAME,
                                        material: res.data[m].feature.properties.MATERIAL
                                    };
                                    result.push(obj);
                                }
                                var hdmCharts = echarts.init(document.getElementById('hdmfx'));


                                var option = {
                                    title: {
                                        text: '横断面分析',
                                        left: 'center',
                                        textStyle: {
                                            color: "#fff"
                                        }
                                    },
                                    tooltip: {
                                        trigger: 'axis',
                                        formatter: function (params) {
                                            var str = '经度：' + params[0].value[0] + '</br>'
                                            str += '纬度：' + params[0].value[1] + '</br>'
                                            str += '埋深：' + params[0].value[2] + '</br>'
                                            return str
                                        }
                                    },
                                    xAxis: {
                                        type: 'value',
                                        splitLine: {     //网格线
                                            "show": false
                                        },
                                        axisLine: {
                                            show: true,
                                            lineStyle: {
                                                color: '#fff',//x线的颜色
                                            }
                                        },
                                        axisLabel: {
                                            show: true,
                                            rotate: 38,
                                            textStyle: {
                                                color: "#fff"
                                            }
                                        },
                                        min: line.coordinates[0][0][0] > line.coordinates[0][1][0] ? line.coordinates[0][1][0].toFixed(5) : line.coordinates[0][0][0].toFixed(5),
                                        max: line.coordinates[0][0][0] > line.coordinates[0][1][0] ? line.coordinates[0][0][0].toFixed(5) : line.coordinates[0][1][0].toFixed(5),
                                        splitNumber: 20
                                    },
                                    yAxis: {
                                        type: 'value',
                                        splitLine: {     //网格线
                                            "show": false
                                        },
                                        axisLine: {
                                            show: true,
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
                                        min: 30.4635,
                                        max: 30.48,
                                    },
                                    series: [{
                                        name: 'scatter',
                                        type: 'scatter',
                                        emphasis: {
                                            label: {
                                                show: false,
                                                position: 'left',
                                                color: 'blue',
                                                fontSize: 16
                                            }
                                        },
                                        data: data
                                    }, {
                                        name: 'line',
                                        type: 'line',
                                        showSymbol: false,
                                        smooth: true,
                                        data: linePoint,
                                        lineStyle: {
                                            color: '#fff',//x线的颜色
                                        },
                                        markPoint: {
                                            itemStyle: {
                                                color: '#fff'
                                            },
                                            lineStyle: {
                                                color: '#fff',//x线的颜色
                                            },
                                            label: {
                                                show: true,
                                                position: 'left',
                                                formatter: '',
                                                color: '#fff',
                                                fontSize: 14
                                            },
                                            data: [{
                                                coord: data
                                            }]
                                        }
                                    }]
                                };
                                hdmCharts.setOption(option);

                                //var tatleHeight = $(".pgfxTabe").height() + 'px'
                                layui.use('table', function () {
                                    var table = layui.table;
                                    // 排管分析
                                    table.render({
                                        elem: '#pgfxTabe'
                                        //, height: tatleHeight
                                        , limit: 5// 这个要根据实际条数来确定，不能设置固定的
                                        , skin: 'nob'
                                        , cols: [[ //标题栏
                                            {type: 'numbers', title: '序号', minWidth: 50},
                                            {field: 'id', title: '编号', align: 'center'},
                                            {field: 'depth', title: '埋深', align: 'center'},
                                            {field: 'material', title: '材质', align: 'center'},
                                            {field: 'name', title: '图层', align: 'center'},

                                        ]]
                                        , data: result
                                        , page: {
                                            layout: ['prev', 'page', 'next'] //自定义分页布局
                                        }, done: function (res, curr, count) {

                                        }
                                    })
                                })

                            }
                        });

                    } else {
                        layer.msg('暂无匹配数据');
                    }

                }

            });
        } else {
            layer.msg('请先选择管线！');
        }


    },
    //清除
    clear: function () {
        layerTree.removePrimitiveByGuid('drawing');
        drawShapeModel = null;
    },
    //开始绘制
    draw: function () {
        JCBZ.kwfx.clear();
        drawHandler.startDrawingGeometry(Cesium.DrawMode.Polyline);
    },


};


