package com.feather.aupipes.service.impl;

import java.util.List;
import java.util.Map;

import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.statistics.AupTjWaterSideByYearMonth;
import com.feather.aupipes.mapper.AupTjWaterSideByYearMonthMapper;
import com.feather.aupipes.service.IAupTjWaterSideByYearMonthService;
import com.feather.common.core.text.Convert;

/**
 * 统计用水分区每一年每一月的叠加用量和号对应用量Service业务层处理
 *
 * @author fancy
 * @date 2020-05-22
 */
@Service
public class AupTjWaterSideByYearMonthServiceImpl implements IAupTjWaterSideByYearMonthService {
    @Autowired
    private AupTjWaterSideByYearMonthMapper aupTjWaterSideByYearMonthMapper;

    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param id
     *            统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量
     */
    @Override
    public AupTjWaterSideByYearMonth selectAupTjWaterSideByYearMonthById(Long id) {
        return aupTjWaterSideByYearMonthMapper.selectAupTjWaterSideByYearMonthById(id);
    }

    /**
     * 查询统计用水分区每一年每一月的叠加用量和号对应用量列表
     *
     * @param aupTjWaterSideByYearMonth
     *            统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 统计用水分区每一年每一月的叠加用量和号对应用量
     */
    @Override
    public List<AupTjWaterSideByYearMonth> selectAupTjWaterSideByYearMonthList(
            AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        return aupTjWaterSideByYearMonthMapper.selectAupTjWaterSideByYearMonthList(aupTjWaterSideByYearMonth);
    }

    /**
     * 新增统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth
     *            统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    @Override
    public int insertAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        return aupTjWaterSideByYearMonthMapper.insertAupTjWaterSideByYearMonth(aupTjWaterSideByYearMonth);
    }

    /**
     * 修改统计用水分区每一年每一月的叠加用量和号对应用量
     *
     * @param aupTjWaterSideByYearMonth
     *            统计用水分区每一年每一月的叠加用量和号对应用量
     * @return 结果
     */
    @Override
    public int updateAupTjWaterSideByYearMonth(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        return aupTjWaterSideByYearMonthMapper.updateAupTjWaterSideByYearMonth(aupTjWaterSideByYearMonth);
    }

    /**
     * 删除统计用水分区每一年每一月的叠加用量和号对应用量对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupTjWaterSideByYearMonthByIds(String ids) {
        return aupTjWaterSideByYearMonthMapper.deleteAupTjWaterSideByYearMonthByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除统计用水分区每一年每一月的叠加用量和号对应用量信息
     *
     * @param id
     *            统计用水分区每一年每一月的叠加用量和号对应用量ID
     * @return 结果
     */
    @Override
    public int deleteAupTjWaterSideByYearMonthById(Long id) {
        return aupTjWaterSideByYearMonthMapper.deleteAupTjWaterSideByYearMonthById(id);
    }

    @Override
    public List<AupTjWaterSideByYearMonth> selectAupTjWaterSideByParamsList(
            AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        return aupTjWaterSideByYearMonthMapper.selectAupTjWaterSideByYearMonthList(aupTjWaterSideByYearMonth);
    }

    /**
     * 查询当前选中年度每个月的用水量以及上一个年度没一个月的用水量
     * 
     * @param params 参数
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> queryMonthUseWater(Map<String, Object> params) {
        return aupTjWaterSideByYearMonthMapper.queryMonthUseWater(params);
    }

    /**
     * 根据树节点获取用量信息
     * 
     * @param params 参数
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> queryRootUseWater(Map<String, Object> params) {
        return aupTjWaterSideByYearMonthMapper.queryRootUseWater(params);
    }

    @Override
    public List<Map<String, Object>> queryRootHouseUseWater(Map<String, Object> params) {
        return aupTjWaterSideByYearMonthMapper.queryRootHouseUseWater(params);
    }

    /**
     * 按条件查询用水平衡，汇总因一户多表而重复的户用量
     * 
     * @param aupTjWaterSideByYearMonth 对象
     * @return 结果
     */
    @Override
    public List<AupTjWaterSideByYearMonth> queryAupTjByParams(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        return aupTjWaterSideByYearMonthMapper.queryAupTjByParams(aupTjWaterSideByYearMonth);
    }

    /**
     * 按条件查询用水平衡，计算合计
     * @param aupTjWaterSideByYearMonth 对象
     * @return 结果
     */
    @Override
    public AupTjWaterSideByYearMonth queryAupTjByParamsTotalRow(AupTjWaterSideByYearMonth aupTjWaterSideByYearMonth) {
        List<AupTjWaterSideByYearMonth> tjWaterSideByYearMonths = aupTjWaterSideByYearMonthMapper.queryAupTjByParams(aupTjWaterSideByYearMonth);
        //加入合计
        AupTjWaterSideByYearMonth hj = new AupTjWaterSideByYearMonth();
        double hjTableUseNumber = 0;
        double hjSumUseNumber = 0;
        for (AupTjWaterSideByYearMonth a : tjWaterSideByYearMonths) {
            hjTableUseNumber += a.getTableusenumber();
            hjSumUseNumber += a.getSumusenumber();
        }
        hj.setCachename("合计");
        hj.setTableusenumber(NumberUtils.formatNumber(hjTableUseNumber,2));
        hj.setSumusenumber(NumberUtils.formatNumber(hjSumUseNumber,2));
        return hj;
    }
    @Override
    public List<Map<String,Object>> querySideUseNumber(Map<String,Object> params){
        return aupTjWaterSideByYearMonthMapper.querySideUseNumber(params);
    }
}