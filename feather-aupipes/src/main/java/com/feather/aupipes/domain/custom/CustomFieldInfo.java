package com.feather.aupipes.domain.custom;

/**
 * 功能描述： 字段信息
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 10:44
 */
public class CustomFieldInfo {


    // 名称
    public String Name;
    /// 值
    public String Value;
    /// 类型
    public String Type;


    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
