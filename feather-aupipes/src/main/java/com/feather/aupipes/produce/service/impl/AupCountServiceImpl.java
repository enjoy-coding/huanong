package com.feather.aupipes.produce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.mapper.AupCountMapper;
import com.feather.aupipes.produce.service.AupCountService;

@Service
public class AupCountServiceImpl implements AupCountService {
    @Autowired
    private AupCountMapper aupCountMapper;

    /**
     * 更新设备统计数据
     */
    @Override
    public void updateEquipmentCount() {
        aupCountMapper.updateEquipmentCount();
    }

    @Override
    public void insertAupArea() {
        aupCountMapper.insertAupArea();
    }

    @Override
    public void insertHouseMeterInfo() {
        aupCountMapper.insertHouseMeterInfo();
    }

    @Override
    public void insertWaterUseNumber() {
        aupCountMapper.insertWaterUseNumber();
    }

    @Override
    public void  insetElectricityUseNumber(){ aupCountMapper.insetElectricityUseNumber(); }

    @Override
    public void updateHouseMeterInfo() {
        aupCountMapper.updateHouseMeterInfo();
    }

    @Override
    public void updateMeterInfo() {
        aupCountMapper.updateMeterInfo();
    }

    @Override
    public void updateMeterUseNumber() {
        aupCountMapper.updateMeterUseNumber();
    }

    @Override
    public void updateTotalUseNumber() {
        aupCountMapper.updateTotalUseNumber();
    }
}
