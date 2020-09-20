
package com.feather.aupipes.service.impl;


import java.util.List;

import com.feather.aupipes.domain.statistics.AupTjElectricityByYearMonth;
import com.feather.aupipes.mapper.AupTjElectricityByYearMonthMapper;
import com.feather.aupipes.service.IAupTjElectricityByYearMonthService;

import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用电平衡统计Service业务层处理
 * 
 * @author fancy
 * @date 2020-08-03
 */
@Service
public class AupTjElectricityByYearMonthServiceImpl implements IAupTjElectricityByYearMonthService{
    @Autowired
    private AupTjElectricityByYearMonthMapper aupElectricitySideMapper;

    
    /**
     * 查询用电平衡统计
     * 
     * @param id 用电平衡统计ID
     * @return 用电平衡统计
     */
    @Override
    public AupTjElectricityByYearMonth selectAupTjElectricityByYearMonthById(Long id){
        return aupElectricitySideMapper.selectAupTjElectricityByYearMonthById(id);
    }

    /**
     * 查询用电平衡统计列表
     * 
     * @param aupTjElectricityByYearMonth 用电平衡统计
     * @return 用电平衡统计集合
     */
    @Override
    public List<AupTjElectricityByYearMonth> selectAupTjElectricityByYearMonthList(AupTjElectricityByYearMonth aupTjElectricityByYearMonth){
        return aupElectricitySideMapper.selectAupTjElectricityByYearMonthList(aupTjElectricityByYearMonth);
    }


    
      /**
     * 根据条件查询用电平衡统计结果
     */
    @Override
    public List<AupTjElectricityByYearMonth> queryAupTjElectricityByParams(AupTjElectricityByYearMonth aupTjElectricityByYearMonth){
        List<AupTjElectricityByYearMonth> aupTjElectricityByYearMonthList = aupElectricitySideMapper.queryAupTjElectricityByParams(aupTjElectricityByYearMonth);
        for (AupTjElectricityByYearMonth t:aupTjElectricityByYearMonthList) {
            if(t.getPreSumUseNumber()!=0){
                double sumUseNumberHb = ((t.getSumUseNumber() - t.getPreSumUseNumber())/t.getPreSumUseNumber())*100;
                int hbIcon = 0;
                if(sumUseNumberHb>0){
                    hbIcon = 1;
                }
                t.setSumUseNumberHb(NumberUtils.formatNumber(sumUseNumberHb,2));
                t.setHbIcon(hbIcon);
            }
        }
        return aupTjElectricityByYearMonthList;
    }

    /**
     * 计算合计行，统计所有分页的合计
     * @param aupTjElectricityByYearMonth 对象
     * @return 结果
     */
    @Override
    public AupTjElectricityByYearMonth queryAupTjElectricityByParamsTotalRow(AupTjElectricityByYearMonth aupTjElectricityByYearMonth){
        List<AupTjElectricityByYearMonth> aupTjElectricityByYearMonthList = aupElectricitySideMapper.queryAupTjElectricityByParams(aupTjElectricityByYearMonth);
        double sumNumber = 0;
        double scaleSumNumber = 0;
        double tableNumber = 0;
        double scaleTableNumber = 0;
        for (AupTjElectricityByYearMonth e:aupTjElectricityByYearMonthList) {
            sumNumber += e.getSumUseNumber();
            scaleSumNumber += e.getScaleSumUseNumber();
            tableNumber += e.getTableUseNumber();
            scaleTableNumber += e.getScaleTableUseNumber();
        }
        AupTjElectricityByYearMonth sum = new AupTjElectricityByYearMonth();
        sum.setCacheName("合计");
        sum.setScaleSumUseNumber(NumberUtils.formatNumber(scaleSumNumber,2));
        sum.setSumUseNumber(NumberUtils.formatNumber(sumNumber,2));
        sum.setTableUseNumber(NumberUtils.formatNumber(tableNumber,2));
        sum.setScaleTableUseNumber(NumberUtils.formatNumber(scaleTableNumber,2));
        return sum;
    }


}
