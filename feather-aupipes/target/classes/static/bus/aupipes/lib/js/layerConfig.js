var layerCfg = {
    //分析程序端口
    url: ctx + "pipeV2/",
    //智能排管分析地址
    znpgUrl: ctx + "pipe/capacityAnalysis",
    //管网运行拾取配电箱分析接口
    gwyxPdfUrl: ctx + "pipe/gethldata?hlid=",
    //管网运行根据回路ID分析，楼栋信息等
    gwyxLdInfo: ctx + "screen/jcbz/getHlInfo?hlId=",
    //拉闸限电分析
    lzxdUrl: ctx + "pipe/getdatabyoid",
    //关阀停水分析
    gftsUrl: ctx + "pipe/getValve3?oid=",
    //横断面分析
    hdmfxUrl: ctx +"pipe/traSectionAnalysis",
    //地名注记
    whereUrl: aupipeService + "/KQGis/rest/services/dlg",
    whereLayerId: "6",
    //预警处置
    yjcz: {
        all: [],     //全部
        bHouse: ["PUMP_HOUSE_ANNOTATION"],   //泵房
        light: ["STREET_LIGHT", "CONTROLLER"],//路灯
        leak: ["LEAKAGE"],//探漏
        electricity: ["HOUSE_ANNOTATION"],//电表
        water: ["HOUSE_ANNOTATION"],//水表
        monitor: ["MONITOR"],//监控
        waterQuality: ["WATER_MONITOR"],//水质
        look: [],//巡检
        leakWhere: ["LEAKAGE", "JS_LINE_3D"],//探漏定位
    },
    //运行监控
    yxjk: {
        all: ["TRANSFORMER_ROOM_ANNOTATION"],     //配电房
        bHouse: ["PUMP_HOUSE_ANNOTATION"],   //泵房
        house: ["HOUSE"],   //楼栋
        light: ["STREET_LIGHT", "CONTROLLER"],//路灯
        leak: ["LEAKAGE"],//探漏
        electricity: ["HOUSE_ANNOTATION"],//电表
        water: ["HOUSE_ANNOTATION"],//水表
        monitor: ["MONITOR"],//监控
        waterQuality: ["WATER_MONITOR"],//水质
        leakWhere: ["LEAKAGE", "JS_LINE_3D"],//探漏定位
        cxzs: ["HOUSE_ANNOTATION", "GD_LINE_3D", "TRANSFORMER_ROOM_ANNOTATION"]//出线追溯
    },
    //人员巡检
    ryxj: {
        all: [],
        layer: ["PIPELINE_3D"]
    },
    //决策保障
    jcbz: {
        all: [],//进入模块初始化
        water: ["HOUSE_ANNOTATION", 'JS_LINE_3D', 'dom'],//关阀
        electricity: ['HOUSE_ANNOTATION', 'TRANSFORMER_ROOM_ANNOTATION'],//拉闸
        znpg: ["PIPELINE_3D"],//智能排管
        yagl: [],//预案管理
        lzfx: ["TRANSFORMER_ROOM", "HOUSE_ANNOTATION", 'GD_POINT_3D', 'GD_LINE_3D'],//拉闸分析
        gfts: ["dom","HOUSE_ANNOTATION", 'JS_LINE_3D', 'JS_POINT', 'JS_LINE'],//关阀停水
        bgts: [ 'JS_LINE_3D'],//爆管停水预案
        xxts: ['HOUSE_ANNOTATION'],//信息推送
        //楼栋阀门的楼栋查询图层编号
        ldfm : ['HOUSE_ANNOTATION','gsfm'],
        yaglStep: {
            "iconfont icon-zhuangjirongliang": {
                step: ["一", "二", "三", "四", "五", "六", "七", "八"],
                label: ["系统报警", "通知工作人员", "工作人员现场勘查", "启用备供电源", "通知相关用户", "临时供电", "组织维修", "恢复正常供电"]
            },
            "iconfont icon-humidity": {
                step: ["一", "二", "三", "四", "五", "六", "七"],
                label: ["爆管提醒", "通知工作人员", "确定维修方案", "通知停水", "关闭相应阀门", "组织维修", "恢复正常供水"]
            },
            "iconfont icon-fanwei1": {
                step: ["一", "二", "三", "四", "五"],
                label: ["泵房进水压力表", "通知工作人员", "确定来水时间", "通知恢复供水时间", "恢复供水"]
            },
            "iconfont icon-famen": {
                step: [],
                label: []
            },
            "iconfont icon-weixiu": {
                step: [],
                label: []
            },
            "iconfont icon-baojing": {
                step: [],
                label: []
            },
        }
    },
    //能耗监管
    nhjg: {
        water: ["HOUSE_ANNOTATION"],//耗水
        electricity: ["HOUSE_ANNOTATION"],//耗电
        house: ["HOUSE_ANNOTATION"],//建筑物
    },
    //高压配电箱展示属性
    powerHighPro: {ID: "回路id", "柜号": "柜号", "回路名称": "回路名称", "容量": "容量", "线径": "电缆线径", "长度": "长度", "备注": "备注"},
    powerLowPro: {"ID": "回路id", "柜号": "柜号", "回路名称": "回路名称", "空开型号": "空开型号", "电缆线径": "电缆线径", "变比": "变比", "备注": "备注"},


    //首页全局搜索配置
    //搜地址
    searchAddPro: {OID: "OID", NAME: '名称', LNG: '经度', LAT: '纬度'},
    //搜设备   路灯 水表 电表 探漏 摄像头 水质
    searchLeak: {name: "名称"},
    searchQuality: {sid: "编号", sname: "名称"},
    searchLight: {lid: "编号", name: "名称"},
    searchCamera: {id: "编号", name: "名称"},
    searchWater: {areaNo: "楼栋编号", areaName: "名称", localMonthUseNumber: "当月用水量(吨)"},
    searchElector: {areaNo: "楼栋编号", areaName: "名称", localMonthUseNumber: "当月用电量(度)"},
    //搜预警  路灯 探漏 巡检 电表 水表 水质 监控 泵房
    searchLook: {name: '名称', level: '预警等级', state: '预警状态', author: '来源', content: '内容', dateTime: '时间'},
    //搜维修
    searchRepPro: {name: '名称', faultType: '类型', description: '描述', address: '位置'},

    //开挖分析的图层id配置
    kwfxId:{ys:[{name:'雨水点',id:29},{name:'雨水段',id:31}],ws:[{name:'污水点',id:34},{name:'污水段',id:36}],js:[{name:'给水点',id:12},{name:'给水段',id:14}],gd:[{name:'供电点',id:23},{name:'供电段',id:25}],ld:[{name:'路灯点',id:18},{name:'路灯段',id:20}],trq:[{name:'天然气点',id:40},{name:'天然气段',id:42}]},
    //kwfxPoint:{PIPEP_ID:'编码',YSDM:'要素代码',B_DEPTH:'埋深',PIPEP_TYPE:'类型',R_NAME:'位置'},
    kwfxPoint:{PIPEP_ID:'编码',B_DEPTH:'埋深',PIPEP_TYPE:'类型',R_NAME:'位置'},
    //kwfxLine:{PIPEL_ID:'编码',YSDM:'要素代码',S_DEEP:'起点埋深',MATERIAL:'材质',R_NAME:'位置'},
    kwfxLine:{PIPEL_ID:'编码',R_NAME:'位置',D_TYPE:'埋设方式',D_S:'管径',MATERIAL:'管线材质',S_DEEP:'起点埋深',E_DEEP:'终点埋深'},
    kwfxLayer:{"给水段":'JS_LINE_3D',"供电段":'GD_LINE_3D',"路灯段":'LD_LINE_3D',"污水段":'WS_LINE_3D',"雨水段":'YS_LINE_3D',"天然气段":'TRQ_LINE_3D'},
    kwfxMsfs:{ ms1:'直埋',ms2:'管埋',ms3:'管块',ms4:'方沟',ms5:'架空',ms6:'拱沟',ms7:'排管',ms8:'人防',ms9:'隧道',ms10:'其他',ms11:'组合'},


    //横断面分析的图层id配置
    hdfxId:{ys:[{name:'雨水段',id:31}],ws:[{name:'污水段',id:36}],gs:[{name:'给水段',id:14}],dl:[{name:'供电段',id:25}],ld:[{name:'路灯段',id:20}],trq:[{name:'天然气段',id:42}]},
    hdfxLine:{PIPEL_ID:'编码',YSDM:'要素代码',S_DEEP:'起点埋深',MATERIAL:'材质',R_NAME:'位置'},
    hdfxLayer:{"给水段":'JS_LINE_3D',"供电段":'GD_LINE_3D',"路灯段":'LD_LINE_3D',"污水段":'WS_LINE_3D',"雨水段":'YS_LINE_3D',"天然气段":'TRQ_LINE_3D'},


    //信息推送的楼栋查询图层编号
    xxtsLdId : 51,

};
