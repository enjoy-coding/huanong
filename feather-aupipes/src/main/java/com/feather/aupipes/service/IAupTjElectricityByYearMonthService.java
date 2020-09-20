package com.feather.aupipes.service;

import com.feather.aupipes.domain.statistics.AupTjElectricityByYearMonth;
import java.util.List;
/**
 * 用电平衡统计Service接口
 * 
 * @author fancy
 * @date 2020-08-03
 */
public interface IAupTjElectricityByYearMonthService
{
    /**
     * 查询用电平衡统计
     * 
     * @param id 用电平衡统计ID
     * @return 用电平衡统计
     */
    AupTjElectricityByYearMonth selectAupTjElectricityByYearMonthById(Long id);

    /**
     * 查询用电平衡统计列表
     * 
     * @param aupTjElectricityByYearMonth 用电平衡统计
     * @return 用电平衡统计集合
     */
    List<AupTjElectricityByYearMonth> selectAupTjElectricityByYearMonthList(AupTjElectricityByYearMonth aupTjElectricityByYearMonth);

      /**
     * 根据条件查询用电平衡统计结果
     */
    List<AupTjElectricityByYearMonth> queryAupTjElectricityByParams(AupTjElectricityByYearMonth aupTjElectricityByYearMonth);

    /**
     * 根据条件查询用电平衡得到汇总行
     * @param aupTjElectricityByYearMonth 用电平衡统计
     * @return 结果
     */
    AupTjElectricityByYearMonth queryAupTjElectricityByParamsTotalRow(AupTjElectricityByYearMonth aupTjElectricityByYearMonth);

  }