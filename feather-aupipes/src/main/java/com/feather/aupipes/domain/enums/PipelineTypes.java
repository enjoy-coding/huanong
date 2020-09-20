package com.feather.aupipes.domain.enums;

/**
 * 功能描述： 管线类型枚举
 *
 * @Author: fang xinliang
 * @Date: 2020/2/6 18:51
 */
public enum PipelineTypes {

    /// <summary>
    /// 未知（无效类型）
    /// </summary>
    Unknown ,

    /// <summary>
    /// 电力
    /// </summary>
    Electricity  ,

    /// <summary>
    /// 路灯
    /// </summary>
    Streetlamp ,

    /// <summary>
    /// 电信
    /// </summary>
    Telegraphy  ,

    /// <summary>
    /// 电视
    /// </summary>
    Television  ,

    /// <summary>
    /// 给水
    /// </summary>
    FeedWater ,

    /// <summary>
    /// 排水
    /// </summary>
    DeWater ,

    /// <summary>
    /// 污水
    /// </summary>
    Sewage  ,

    /// <summary>
    /// 雨水
    /// </summary>
    Rain  ,

    /// <summary>
    /// 燃气
    /// </summary>
    Gas  ,

    /// <summary>
    /// 热力
    /// </summary>
    Energetics ,

    /// <summary>
    /// 工业
    /// </summary>
    Industry ,

    /// <summary>
    /// 道路
    /// </summary>
    Road ;

    /*
    /// <summary>
    /// 未知（无效类型）
    /// </summary>
    Unknown("Unknown",0),

    /// <summary>
    /// 电力
    /// </summary>
    Electricity ("dl",1000),

    /// <summary>
    /// 路灯
    /// </summary>
    Streetlamp("ld",1200),

    /// <summary>
    /// 电信
    /// </summary>
    Telegraphy ("dx",2000),

    /// <summary>
    /// 电视
    /// </summary>
    Television ("tv",2100),

    /// <summary>
    /// 给水
    /// </summary>
    FeedWater("FeedWater",3000),

    /// <summary>
    /// 排水
    /// </summary>
    DeWater( "DeWater",4000),

    /// <summary>
    /// 污水
    /// </summary>
    Sewage ("Sewage",4001),

    /// <summary>
    /// 雨水
    /// </summary>
    Rain ("Rain",4002),

    /// <summary>
    /// 燃气
    /// </summary>
    Gas ("Gas",5000),

    /// <summary>
    /// 热力
    /// </summary>
    Energetics ("Energetics",6000),

    /// <summary>
    /// 工业
    /// </summary>
    Industry ("Industry",7000),

    /// <summary>
    /// 道路
    /// </summary>
    Road ( "Road",8100);


    private final String typeName;
    private final int code;

    PipelineTypes(String typeName, int code) {
        this.typeName=typeName;
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }

   */
}
