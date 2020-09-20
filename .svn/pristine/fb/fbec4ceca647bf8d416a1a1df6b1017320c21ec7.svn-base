package com.feather.smart.service.impl;

import com.feather.smart.mapper.ZhsqZhzlMapper;
import com.feather.smart.service.IZhsqZhzlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合治理
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqZhzlServiceImpl implements IZhsqZhzlService
{
    @Autowired
    private ZhsqZhzlMapper zhsqZhzlMapper;


    /**
     * 统计重点人员
     * @param maps
     * @return
     */
    @Override
    public Map<String, Object> selectZdryCount(Map<String, Object> maps) {
        return zhsqZhzlMapper.selectZdryCount(maps);
    }

    /**
     * 统计重点人员分布情况
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZdryFb(Map<String, Object> maps) {
        return zhsqZhzlMapper.getZdryFb(maps);
    }

    /**
     * 统计重点人员年龄结构
     * @param maps
     * @return
     */
    @Override
    public Object[] getZdNl(Map<String, Object> maps) {
        Map<String, Object> zdNl = zhsqZhzlMapper.getZdNl(maps);
        Object[] s={zdNl.get("a"),zdNl.get("b"),zdNl.get("c"),zdNl.get("d"),zdNl.get("e"),zdNl.get("f")};
        return s;
    }

    /**
     * 统计重点人员男女比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZdNnBl(Map<String, Object> maps) {
        return zhsqZhzlMapper.getZdNnBl(maps);
    }

    /**
     * 统计重点人员民族比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZdMzBl(Map<String, Object> maps) {
        return zhsqZhzlMapper.getZdMzBl(maps);
    }

    /**
     * 重点人员列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZdRyList(Map<String, Object> maps) {
        return zhsqZhzlMapper.getZdRyList(maps);
    }
    /**
     * 巡检任务统计
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> selectXjrwCount(Map<String, Object> maps) {
        return zhsqZhzlMapper.selectXjrwCount(maps);
    }

    /**
     * 首页人员巡检
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> selectRyxjCount(Map<String, Object> maps) {
        return zhsqZhzlMapper.selectRyxjCount(maps);
    }

    /**
     * 巡检任务
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getXjrw(Map<String, Object> maps) {
        return zhsqZhzlMapper.getXjrw(maps);
    }

    /**
     * 重点事件
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> selectZdsjCount(Map<String, Object> maps) {
        Map<String, Object> stringObjectMap = zhsqZhzlMapper.selectZdsjCount(maps);
        Map<String, Object> stringObjectMap1 = zhsqZhzlMapper.selectZdsjFslCount(maps);
        stringObjectMap.putAll(stringObjectMap1);
        return stringObjectMap;
    }

    /**
     * 重点事件扇形图
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> selectZdsjTCount(Map<String, Object> maps) {
        return zhsqZhzlMapper.selectZdsjTCount(maps);
    }
    /**
     * 重点事件列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getZdsjList(Map<String, Object> maps) {
        return zhsqZhzlMapper.getZdsjList(maps);
    }
}
