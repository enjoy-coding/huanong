package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.statistics.AupTjWaterSideByYearMonth;

import java.util.List;
import java.util.Map;

public interface  AupTjWaterSideByYearMonthMapper {
    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param id 统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量
     */
    public AupTjWaterSideByYearMonth selectAupTjWaterSideByYearMonthById(Long id);

    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量列表
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量集合
     */
    public List<AupTjWaterSideByYearMonth> selectAupTjWaterSideByYearMonthList(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 查询当前选中年度每个月的用水量以及上一个年度没一个月的用水量
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryMonthUseWater(Map<String,Object> params);


    /**
     * 新增统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    public int insertAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 修改统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth 统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    public int updateAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);

    /**
     * 删除统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param id 统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 结果
     */
    public int deleteAupTjWaterSideByYearMonthById(Long id);

    /**
     * 批量删除统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupTjWaterSideByYearMonthByIds(String[] ids);

    /**
     * 统计楼栋，片区，区域的用水量
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryRootUseWater(Map<String,Object> params);

    /**
     * 统计户的用水量，每个水表的用水量
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryRootHouseUseWater(Map<String,Object> params);

    /**
     * 按条件查询用水平衡，汇总因一户多表而重复的户用量
     * @param aupTjWaterSideByYearMonth
     * @return
     */
    public List<AupTjWaterSideByYearMonth> queryAupTjByParams(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth);



    public List<Map<String,Object>> querySideUseNumber(Map<String,Object> params);


}
