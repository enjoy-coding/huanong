package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.AupArea;
import com.feather.aupipes.mapper.AupAreaMapper;
import com.feather.aupipes.service.AupAreaService;
import com.feather.common.core.domain.LayuiTreeStringResult;
import com.feather.common.utils.bean.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Descripttion: 区域实现类
 * @version: 0.1
 * @Author: dufan
 * @Date: 2020-07-01 10:58:59
 * @LastEditors: dufan
 * @LastEditTime: 2020-07-02 09:14:36
 */
@Service
public class AupAreaServiceImpl implements AupAreaService {

    @Autowired
    private AupAreaMapper aupAreaMapper;

    @Override
    public List<AupArea> selectAupAreaList(AupArea aupArea) {
        return aupAreaMapper.selectAupAreaList(aupArea);
    }

    @Override
    public List<Map<String, Object>> selectAupAreaUpdateHistoryList(AupArea area) {
        return aupAreaMapper.selectAupAreaUpdateHistoryList(area);
    }

    @Override
    public List<LayuiTreeStringResult> selectAupAreaLayuiResultList(AupArea aupArea) {
        List<AupArea> aupAreaList = aupAreaMapper.selectAupAreaList(aupArea);
        // 构造返回数据结构LayuiTreeResult
        List<LayuiTreeStringResult> layuiTreeResultList = this.contrastLayuiResultTable(aupAreaList);
        return layuiTreeResultList;
    }

    /**
     * 构造layuiTreeResult的表格树
     * 
     * @param aupAreaList
     * @return
     */
    public List<LayuiTreeStringResult> contrastLayuiResultTable(List<AupArea> aupAreaList) {
        // 构造返回数据结构LayuiTreeResult
        List<LayuiTreeStringResult> layuiTreeResultList = new ArrayList<LayuiTreeStringResult>();
        for (int i = 0; i < aupAreaList.size(); i++) {
            LayuiTreeStringResult layuiTreeResult = selectLayuiTreeAupArea(aupAreaList.get(i));
            if (Integer.parseInt(layuiTreeResult.getParams().get("level").toString()) < 4) {
                layuiTreeResult.setHaveChild(true);
            } else {
                layuiTreeResult.setHaveChild(false);
            }
            layuiTreeResultList.add(layuiTreeResult);
        }
        return layuiTreeResultList;
    }

    /**
     * 设置对象为layuiTree
     * 
     * @param power
     * @return
     */
    public LayuiTreeStringResult selectLayuiTreeAupArea(AupArea aupArea) {
        LayuiTreeStringResult treeResult = new LayuiTreeStringResult();
        treeResult.setName(aupArea.getName());
        treeResult.setId(aupArea.getId());
        treeResult.setPid(aupArea.getPid());
        treeResult.setParams(BeanUtils.beanToMap(aupArea));
        return treeResult;
    }

    /**
     * 查询水电表用户列表树
     */
    @Override
    public List<AupArea> queryMeterArea(AupArea aupArea){
        return aupAreaMapper.queryMeterArea(aupArea);
    }

    /**
     * 查询水电表用户详情用能信息
     */
    @Override
    public List<Map<String, Object>> queryMeterInfo(Map<String, Object> parmas) {
       return aupAreaMapper.queryMeterInfo(parmas);
    }

    @Override
    public AupArea selectAupAreaById(String id){
        return aupAreaMapper.selectAupAreaById(id);
    }
    
    
}