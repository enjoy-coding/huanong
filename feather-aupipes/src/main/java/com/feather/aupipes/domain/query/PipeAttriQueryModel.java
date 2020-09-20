package com.feather.aupipes.domain.query;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 11:41
 */
public class PipeAttriQueryModel extends  QueryModel {

    /// <summary>
    /// 图层名称
    /// </summary>

    public String getLayerName() {
        return LayerName;
    }

    public void setLayerName(String layerName) {
        LayerName = layerName;
    }

    public String getPipeID() {
        return PipeID;
    }

    public void setPipeID(String pipeID) {
        PipeID = pipeID;
    }

    public String LayerName ;

    /// <summary>
    /// 管线ID值
    /// </summary>

    public String PipeID ;


    /// <summary>
    /// 无参数构造方法
    /// </summary>
    public PipeAttriQueryModel()
    {
        this.LayerName = "";
        this.PipeID = "";
    }

    public boolean Check()
    {
        return StringUtils.isEmpty(this.LayerName) || StringUtils.isEmpty(this.PipeID);
    }
}
