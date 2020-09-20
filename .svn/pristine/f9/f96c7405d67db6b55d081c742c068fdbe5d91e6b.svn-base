package com.feather.aupipes.domain.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 10:41
 */
public class CustomFeature {

    /// <summary>
    /// 无参数构造方法
    /// </summary>
    public CustomFeature()
    {
        AttributeCollection = new ArrayList<CustomFieldInfo>();
    }

    /// <summary>
    /// 几何图形
    /// </summary>

    public CustomGeometry CustomGeometry ;
    /// <summary>
    /// 属性值
    /// </summary>

    public List<CustomFieldInfo> AttributeCollection ;

    /// <summary>
    /// 获取字段值
    /// </summary>
    /// <param name="fieldName">字段名称</param>
    /// <returns>字段值</returns>
    public String GetFieldValue(String fieldName)
    {

        if (AttributeCollection == null) {
            return null;
        }
        Optional<CustomFieldInfo> fis = AttributeCollection.stream().filter(s -> s.getName().equals(fieldName)).findFirst();
        if (!fis.isPresent()){
            return null;
        }
        return fis.get().getValue();
    }

    /// <summary>
    /// 查找属性中字段索引
    /// </summary>
    /// <param name="fieldName">字段名称</param>
    /// <returns>字段索引，-1表示没找到，索引从零开始</returns>
    public int GetFieldIndex(String fieldName)
    {
        if (AttributeCollection == null) {
            return -1;
        }
        Optional<CustomFieldInfo> fis =AttributeCollection.stream().filter(s -> s.getName().equals(fieldName)).findFirst();
        if (fis.isPresent()){
            return  AttributeCollection.indexOf(fis.get());
        }
        else
        {
            return  -1;
        }
    }

}
