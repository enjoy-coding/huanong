package com.feather.aupipes.service.impl;

import com.feather.aupipes.mapper.AupTimingTaskMapper;
import com.feather.aupipes.service.IAupTimingTaskService;
import com.feather.common.annotation.DataSource;
import com.feather.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 15807
 */
@Service
public class IAupTimingTaskServiceImpl implements IAupTimingTaskService {
    @Autowired
    private AupTimingTaskMapper aupTimingTaskMapper;

    @DataSource(value = DataSourceType.A)
    @Override
    public double getMonthWaterData(String table) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("table",table);
        return aupTimingTaskMapper.getMonthWaterData(map);
    }

    @DataSource(value = DataSourceType.A)
    @Override
    public double getMonthElectricityData(String table) {
        return aupTimingTaskMapper.getMonthElectricityData(table);
    }
}
