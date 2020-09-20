package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupControlEnergy;
import com.feather.aupipes.mapper.AupControlEnergyMapper;
import com.feather.aupipes.service.IAupControlEnergyService;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 控制器能源消耗Service业务层处理
 * 
 * @author fancy
 * @date 2020-06-03
 */
@Service
public class AupControlEnergyServiceImpl implements IAupControlEnergyService 
{
    @Autowired
    private AupControlEnergyMapper aupControlEnergyMapper;

    /**
     * 查询控制器能源消耗
     * 
     * @param id 控制器能源消耗ID
     * @return 控制器能源消耗
     */
    @Override
    public AupControlEnergy selectAupControlEnergyById(String id)
    {
        return aupControlEnergyMapper.selectAupControlEnergyById(id);
    }

    /**
     * 查询控制器能源消耗列表
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 控制器能源消耗
     */
    @Override
    public List<AupControlEnergy> selectAupControlEnergyList(AupControlEnergy aupControlEnergy)
    {
        return aupControlEnergyMapper.selectAupControlEnergyList(aupControlEnergy);
    }

    /**
     * 新增控制器能源消耗
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 结果
     */
    @Override
    public int insertAupControlEnergy(AupControlEnergy aupControlEnergy)
    {
        return aupControlEnergyMapper.insertAupControlEnergy(aupControlEnergy);
    }

    /**
     * 修改控制器能源消耗
     * 
     * @param aupControlEnergy 控制器能源消耗
     * @return 结果
     */
    @Override
    public int updateAupControlEnergy(AupControlEnergy aupControlEnergy)
    {
        return aupControlEnergyMapper.updateAupControlEnergy(aupControlEnergy);
    }

    /**
     * 删除控制器能源消耗对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupControlEnergyByIds(String ids)
    {
        return aupControlEnergyMapper.deleteAupControlEnergyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除控制器能源消耗信息
     * 
     * @param id 控制器能源消耗ID
     * @return 结果
     */
    @Override
    public int deleteAupControlEnergyById(String id)
    {
        return aupControlEnergyMapper.deleteAupControlEnergyById(id);
    }

    @Override
    public Map<String,Object> selectAupControlEnergyByTime(AupControlEnergy aupControlEnergy) {
        return aupControlEnergyMapper.selectAupControlEnergyByTime(aupControlEnergy);
    }
}
