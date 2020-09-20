package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupControlEnergy;
import java.util.List;
import java.util.Map;

/**
 * 控制器能源消耗Mapper接口
 * 
 * @author fancy
 * @date 2020-06-03
 */
public interface AupControlEnergyMapper 
{
    /**
     * 查询控制器能源消耗
     * 
     * @param id 控制器能源消耗ID
     * @return 控制器能源消耗
     */
    public AupControlEnergy selectAupControlEnergyById(String id);

    /**
     * 查询控制器能源消耗列表
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 控制器能源消耗集合
     */
    public List<AupControlEnergy> selectAupControlEnergyList(AupControlEnergy aupControlEnergy);

    /**
     * 新增控制器能源消耗
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 结果
     */
    public int insertAupControlEnergy(AupControlEnergy aupControlEnergy);

    /**
     * 修改控制器能源消耗
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 结果
     */
    public int updateAupControlEnergy(AupControlEnergy aupControlEnergy);

    /**
     * 删除控制器能源消耗
     * 
     * @param id 控制器能源消耗ID
     * @return 结果
     */
    public int deleteAupControlEnergyById(String id);

    /**
     * 批量删除控制器能源消耗
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupControlEnergyByIds(String[] ids);

    /**
     * 根据时间范围统计控制器的值
     * @param aupControlEnergy
     * @return
     */
    public Map<String,Object> selectAupControlEnergyByTime(AupControlEnergy aupControlEnergy);
}
