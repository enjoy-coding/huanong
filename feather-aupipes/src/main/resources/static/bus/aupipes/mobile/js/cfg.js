var cfg = {
    fmgxUrl: ctx + "pipe/getValveRelation?id=",
    position:[114.34866, 30.45810, 1000],
    positionF:[114.34943, 30.47423, 500],
    level: ["", "一级预警", "二级预警", "三级预警"],   //预警级别   1 一级  2二级  3三级
    status: ["未处置", "已处置"],   //预警状态  0未  1已
    type: {
        "路灯":"STREET_LIGHT","控制器":"CONTROLLER","探漏":"LEAKAGE","监控":"MONITOR","泵房":"PUMP_HOUSE_ANNOTATION","电表":"HOUSE","水表":"HOUSE","水质":"WATER_MONITOR"
    },
    //管线点
    point:{OID:"PIPEP_ID", PIPEP_TYPE:"PIPEP_TYPE", SUBSID:"SUBSID", B_DEPTH:"B_DEPTH"},
    //管线段
    line:{OID:"PIPEL_ID", MATERIAL:"MATERIAL", D_S:"D_S", R_NAME:"R_NAME"},

    //周边搜索详细信息
    //管线点
    POINT:{OID: "OID",ANGLE: '旋转角度', BMBSM: '要素标识码', B_DEPTH: '埋深', EXP_NO: "物探点号", FEATURE: "特征", MAP_NO: "图上点号", MAP_NUM: "图幅号", MC_MATERIA: "井盖材质", MC_SHAPE: "井盖形状", MC_SPECIFY: "井盖规格", PIPEP_H: '管点高程', PIPEP_ID: "观点编号", PIPEP_MEMO: "管点备注", PIPEP_STAT: "观点状态", PIPEP_TYPE: "管点类型", R_NAME: "道路名称", STRUCT_COD: "构筑物编码", SUBSID: "附属物", SURF_H: '地面高程', S_CODE: '点符号代码', WELL_DEPTH: '井深', X: 'X坐标', Y: 'Y坐标', YSDM: "要素代码"},
    //管线段
    LINE:{OID: "OID",BMBSM: '要素标识码', B_CODE: "权属单位代码", D_S: "管径", D_TYPE: "埋设类型", END_H: '终点高程', END_X: '终点X坐标', END_Y: '终点Y坐标', E_DEEP: '终点埋深', E_POINT: "终点物探点号", LOCAL_TYPE: "管线位置类型", LOGIC_ID: "逻辑线号", MAP_ID: "图上线号", MAP_NUM: "图幅号", MATERIAL: "材质", MDATE: "建设年代", PIPEL_ID: "管线编号", PIPEL_MEMO: "管线备注", PIPEL_STAT: "使用状态", PIPE_LEN: "管线长度", P_D_S: "保护材料断面尺寸", P_EDEEP: '保护材料材质', P_MATERIAL: "保护材料材质", P_SDEEP: '保护材料起点埋深', R_NAME: "道路名称", START_H: '起点高程', START_X: '起点X坐标', START_Y: '起点Y坐标', S_DEEP: '起点埋深', S_POINT: "起点物探点号", YSDM: "要素代码"},
    //监控
    MONITOR:{OID:"OID",SNAME:"名称",QY:"区域",WZ:"位置",LNG:'经度',LAT:'纬度'},
    //水质监测
    WATER_MONITOR:{OID:"OID",SNAME:"名称",JCLX:"监测类型",JCLXBM:"监测编码",QY:"区域",WZ:"位置", LAT: '纬度', LNG: '经度'},
    //门禁
    DOOR_CONTROL:{OID:"OID",SNAME:"名称",QY:"区域",WZ:"位置", LAT: '纬度', LNG: '经度'},
    //控制器
    CONTROLLER:{ cuid: "控制器编号",uavg: "电压（V）",it: '电流（A）', pt: '功率（W）', pe: '电量（KWH）', status: "在线状态",dtime:'数据采集时间',poles:'路灯个数'},
    //渗漏
    LEAKAGE:{OID:"OID",AREANAME: "期数", LAT: '纬度', LNG: '经度', PIPEDEPTH: '埋深', PIPEDIAMET: '监测直径', PIPEMATERI: "材质", PIPEP_ID: "PIPEP_ID", PLACEADDRE: "位置", PLACENAME: '位置编号', SITENO: '编号'},
    //路灯
    STREET_LIGHT:{name:"名称",states:"状态",u:"电压(V)",i:"电流(A)",p:'功率(W)',pf:'功率因素',power:"电量(KWH)",jcsj:"监测时间"},
    //维修记录
    record:{name:'名称',faultType:'设备类型',description:'描述',address:'位置',repairTime:'维修时间',userName:'维修人员'},

    //运行监控的显示字段
    LEAKAGE_YXJK:{placename:"名称",leakstate:"状态",placeaddress:"位置",pipematerial:"管径",pipedepth:"埋深",pipematerial:"材质"},
    MONITOR_YXJK:{cameraName:"名称",cameraTypeName:"类型"},
    WATER_MONITOR_SZ_YXJK:{ygfrjy:"荧光法溶解氧",dcsddl:"电磁式电导率",zd:"浊度",ph:"pH值",orp:"ORP",wd:"温度",jcsj:"监测时间"},
    WATER_MONITOR_LS_YXJK:{ls:"流速",sw:"水位",jcsj:"监测时间"},
    WATER_CONTROLLER_YXJK:{name:"名称",status:"状态",dl:"电流",dy:"电压",p:"功率"},

    //开挖分析
    kwfxId:{ys:[{name:'雨水点',id:29,layer:'YS_POINT'},{name:'雨水段',id:31,layer:'YS_LINE'}],ws:[{name:'污水点',id:34,layer:'WS_POINT'},{name:'污水段',id:36,layer:'WS_LINE'}],js:[{name:'给水点',id:12,layer:'JS_POINT'},{name:'给水段',id:14,layer:'JS_LINE'}],gd:[{name:'供电点',id:23,layer:'GD_POINT'},{name:'供电段',id:25,layer:'GD_LINE'}],ld:[{name:'路灯点',id:18,layer:'LD_POINT'},{name:'路灯段',id:20,layer:'LD_LINE'}],trq:[{name:'天然气点',id:40,layer:'TRQ_POINT'},{name:'天然气段',id:42,layer:'TRQ_LINE'}]},
    kwfxLayer:{"给水点":'JS_POINT',"给水段":'JS_LINE',"供电点":'GD_POINT',"供电段":'GD_LINE',"路灯点":'LD_POINT',"路灯段":'LD_LINE',"污水点":'WS_POINT',"污水段":'WS_LINE',"雨水点":'YS_POINT',"雨水段":'YS_LINE',"天然气点":'TRQ_POINT',"天然气段":'TRQ_LINE'},

    //关阀停水
    gftsLayer:[{id:'JS_POINT'},{id:'JS_LINE'}],
    gftsUrl: ctx + "pipe/getValve3?oid=",

    //拉闸限电
    lzxdLayer:[{id:'HOUSE'}],

};
