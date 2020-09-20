package com.feather.smart.service.impl;

import com.feather.smart.mapper.ZhsqZhdjMapper;
import com.feather.smart.service.IZhsqZhdjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智慧党建
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqZhdjServiceImpl implements IZhsqZhdjService
{

    @Autowired
    private ZhsqZhdjMapper zhsqZhdjMapper;
    /**
     * 统计党政队伍
     * @param maps
     * @return
     */
    @Override
    public Map<String, Object> selectDjdwCount(Map<String, Object> maps) {
        return zhsqZhdjMapper.selectDjdwCount(maps);
    }

    /**
     * 党组织列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getDzzList(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDzzList(maps);
    }

    /**
     * 党组织弹框
     * @param maps
     * @return
     */
    @Override
    public Map<String, Object> getDzzInfo(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDzzInfo(maps);
    }

    /**
     * 党员志愿者列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getDyZyzList(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDyZyzList(maps);
    }

    /**
     * 志愿者列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZyzList(Map<String, Object> maps) {
        return zhsqZhdjMapper.getZyzList(maps);
    }

    /**
     * 党员学历分布
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getDyxlFb(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDyxlFb(maps);
    }

    /**
     * 党员年龄分布
     * @param maps
     * @return
     */
    @Override
    public  List<Map<String,Object>> getDyNlFb(Map<String, Object> maps) {
        Map<String, Object> dyNlFb = zhsqZhdjMapper.getDyNlFb(maps);
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        Map<String, Object> objectObjectHashMap2 = new HashMap<>();
        Map<String, Object> objectObjectHashMap3 = new HashMap<>();
        objectObjectHashMap.put("FW","30岁以下");
        objectObjectHashMap.put("NL",dyNlFb.get("a"));
        objectObjectHashMap2.put("FW","30岁-50岁");
        objectObjectHashMap2.put("NL",dyNlFb.get("b"));
        objectObjectHashMap3.put("FW","50岁及以上");
        objectObjectHashMap3.put("NL",dyNlFb.get("c"));
        ArrayList<Map<String,Object>> objects = new ArrayList<>();
        objects.add(objectObjectHashMap);
        objects.add(objectObjectHashMap2);
        objects.add(objectObjectHashMap3);
        return objects;
    }

    /**
     * 党员党龄分布
     * @param maps
     * @return
     */
    @Override
    public Object[] getDyNl(Map<String, Object> maps) {
        Map<String, Object> dyNl = zhsqZhdjMapper.getDyNl(maps);
        Object[] s={dyNl.get("a"),dyNl.get("b"),dyNl.get("c"),dyNl.get("d"),dyNl.get("e"),dyNl.get("f"),dyNl.get("g")};
        return s;
    }

    /**
     * 党员性别比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getDyXb(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDyXb(maps);
    }
    /**
     * 党员民族比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getDyMz(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDyMz(maps);
    }

    /**
     * 党员弹框详情
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getDyZyzTk(Map<String, Object> maps) {
        return zhsqZhdjMapper.getDyZyzTk(maps);
    }

}
