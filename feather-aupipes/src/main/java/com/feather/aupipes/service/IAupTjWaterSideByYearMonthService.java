package com.feather.aupipes.service;

import com.feather.aupipes.domain.statistics.AupTjWaterSideByYearMonth;
import java.util.List;
import java.util.Map;

/**
 * 统计用水分区每一年每一月的叠加用量和号对应用量Service接口
 *
 * @author fancy
 * @date 2020-05-22
 */
public interface IAupTjWaterSideByYearMonthService {
    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param id 统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量
     */
    AupTjWaterSideByYearMonth selectAupTjWaterSideByYearMonthById(Long id);

    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量列表
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量集合
     */
    List<AupTjWaterSideByYearMonth> selectAupTjWaterSideByYearMonthList(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量列表
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量集合
     */
    List<AupTjWaterSideByYearMonth> selectAupTjWaterSideByParamsList(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 新增统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    int insertAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 修改统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    int updateAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 批量删除统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupTjWaterSideByYearMonthByIds(String ids);

    /**
     * 删除统计用水分区每一年每一月的叠加用量和号对应用量信息
     *
     * @param id 统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 结果
     */
    int deleteAupTjWaterSideByYearMonthById(Long id);

    /**
     * 查询当前选中年度每个月的用水量以及上一个年度没一个月的用水量
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryMonthUseWater(Map<String, Object> params);


    /**
     * 能耗监管总的用水量
     *
     * @param params 参数
     * @return 结果
     */
    List<Map<String, Object>> queryRootUseWater(Map<String, Object> params);

    /**
     * 统计户的用水量，每个水表的用水量
     *
     * @param params 参数
     * @return 结果
     */
    List<Map<String, Object>> queryRootHouseUseWater(Map<String, Object> params);

    /**
     * 按条件查询用水平衡，汇总因一户多表而重复的户用量
     *
     * @param aupTjWaterSideByYearMonth 对象
     * @return 结果
     */
    List<AupTjWaterSideByYearMonth> queryAupTjByParams(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);


    List<Map<String, Object>> querySideUseNumber(Map<String, Object> params);


    AupTjWaterSideByYearMonth queryAupTjByParamsTotalRow(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);
}