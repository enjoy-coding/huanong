package com.feather.smart.service.impl;

import com.feather.smart.mapper.ZhsqGgMapper;
import com.feather.smart.mapper.ZhsqSqglMapper;
import com.feather.smart.mapper.ZhsqZnafMapper;
import com.feather.smart.service.IZhsqSqglService;
import com.feather.smart.service.IZhsqZnafService;
import com.sun.org.apache.bcel.internal.generic.L2D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区资产Service业务层处理
 *
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqSqglServiceImpl implements IZhsqSqglService
{

    @Autowired
    ZhsqSqglMapper zhsqSqglMapper;
    @Override
    public List<Map> getCountGk(String sqid,String xqid) {
        return zhsqSqglMapper.getCountGk(sqid,xqid);
    }

    @Override
    public List<Map> getCountJc(String sqid,String xqid) {
        return zhsqSqglMapper.getCountJc(sqid,xqid);
    }

    @Override
    public List<Map> getCountLx(String sqid,String xqid) {
        return zhsqSqglMapper.getCountLx(sqid,xqid);
    }

    /**
     * 左侧人员统计
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> selectZcryCount(Map<String, Object> maps) {
        return zhsqSqglMapper.selectRyxjCount(maps);
    }

    /**
     * 年龄结构统计
     * @param maps
     * @return
     */
    @Override
    public Object[] getNlCount(Map<String, Object> maps) {
        Map<String, Object> nlCount = zhsqSqglMapper.getNlCount(maps);
        Object[] s={nlCount.get("a"),nlCount.get("b"),nlCount.get("c"),nlCount.get("d"),nlCount.get("e"),nlCount.get("f")};
        return s;
    }


    /**
     * 男女比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getNnBlCount(Map<String, Object> maps) {
        return zhsqSqglMapper.getNnBlCount(maps);
    }

    /**
     * 民族比例
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getMzBlCount(Map<String, Object> maps) {
        return zhsqSqglMapper.getMzBlCount(maps);
    }

    /**
     * 人员列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getRyList(Map<String, Object> maps) {
        return zhsqSqglMapper.getRyList(maps);
    }


    /**
     * 手环信息
     * @param maps 人员参数
     * @return 该人员的手环信息
     */
    @Override
    public Map<String,Object> getBraceletInfo(Map<String, Object> maps){
        return zhsqSqglMapper.getBraceletInfo(maps);
    }

    /**
     * 手环设备信息
     * @param maps 人员参数
     * @return
     */
    @Override
    public Map<String, Object> getBreatHeartInfo(Map<String, Object> maps) {
        return zhsqSqglMapper.getBreatHeartInfo(maps);
    }

    /**
     * 定位信息
     * @param maps 人员参数
     * @return 该人员的定位信息
     */
    @Override
    public Map<String,Object> getLocationInfo(Map<String,Object> maps){
        return zhsqSqglMapper.getLocationInfo(maps);
    }

    /**
     * 手环预警信息
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getWarringInfo(Map<String, Object> map) {
        return zhsqSqglMapper.getWarringInfo(map);
    }

    /**
     * 人员信息
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getRyInfo(Map<String, Object> maps) {
        return zhsqSqglMapper.getRyInfo(maps);
    }


    /**
     * 重点事件详情
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getZdsjInfo(Map<String, Object> maps) {
        return zhsqSqglMapper.getZdsjInfo(maps);
    }

    /**
     * 巡检任务详情
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getXjrwInfo(Map<String, Object> maps) {
        HashMap<String, Object> xjrwInfoMap = new HashMap<>();
        Map<String, Object> xjrwInfo = zhsqSqglMapper.getXjrwInfo(maps);
        List<Map<String, Object>> zbInfo = zhsqSqglMapper.getXjrwZbInfo(maps);
        xjrwInfoMap.putAll(xjrwInfo);
        xjrwInfoMap.put("zbInfo",zbInfo);
        return xjrwInfoMap;
    }

    /**
     * 左侧房屋统计
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> selectZcfwCount(Map<String, Object> maps) {
        Map<String, Object> stringObjectMap = zhsqSqglMapper.selectZcfwCount(maps);
        Map<String, Object> stringObjectMap1 = zhsqSqglMapper.selectZcfwRzlCount(maps);
        stringObjectMap.putAll(stringObjectMap1);
        return stringObjectMap;
    }

    /**
     * 左侧房屋图表统计
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> selectZcfwTbCount(Map<String, Object> maps) {
        return zhsqSqglMapper.selectZcfwTbCount(maps);
    }

    /**
     * 房屋列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getFwList(Map<String, Object> maps) {
        return zhsqSqglMapper.getFwList(maps);
    }

    /**
     * 楼栋列表
     * @param maps
     * @return
     */
    @Override
    public List<Map<String,Object>> getLdList(Map<String, Object> maps) {
        return zhsqSqglMapper.getLdList(maps);
    }

    /**
     * 房屋信息
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getFwInfo(Map<String, Object> maps) {
        List<Map<String, Object>> fwInfo = zhsqSqglMapper.getFwInfo(maps);
        int size = fwInfo.size();
        Object zz ="";
        Object ldmc ="";
        for (Map<String, Object> stringObjectMap : fwInfo) {
            zz = stringObjectMap.get("ZZ");
            break;
        }
        for (Map<String, Object> stringObjectMap : fwInfo) {
            ldmc = stringObjectMap.get("LDMC");
            break;
        }
        Map<String,Object> mapFw = new HashMap<>();
        mapFw.put("fwInfo",fwInfo);
        mapFw.put("mun",size);
        mapFw.put("dz",zz);
        mapFw.put("ldmc",ldmc);
        return mapFw;
    }

    /**
     * 楼栋列表
     * @param maps
     * @return
     */
    @Override
    public Map<String,Object> getLdInfo(Map<String, Object> maps) {
        List<Map<String, Object>> ldInfo = zhsqSqglMapper.getLdInfo(maps);
        int size = ldInfo.size();
        Object ldmc ="";
        for (Map<String, Object> stringObjectMap : ldInfo) {
            ldmc = stringObjectMap.get("LDMC");
            break;
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("ldmc",ldmc);
        stringObjectHashMap.put("mun",size);
        stringObjectHashMap.put("fwInfo",ldInfo);
        return stringObjectHashMap;
    }
}