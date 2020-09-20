package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupRepair;
import java.util.List;

/**
 * 维修记录Mapper接口
 * 
 * @author fancy
 * @date 2020-04-01
 */
public interface AupRepairMapper 
{
    /**
     * 查询维修记录
     * 
     * @param id 维修记录ID
     * @return 维修记录
     */
    public AupRepair selectAupRepairById(Long id);

    /**
     * 查询维修记录列表
     * 
     * @param aupRepair 维修记录
     * @return 维修记录集合
     */
    public List<AupRepair> selectAupRepairList(AupRepair aupRepair);

    /**
     * 新增维修记录
     * 
     * @param aupRepair 维修记录
     * @return 结果
     */
    public int insertAupRepair(AupRepair aupRepair);

    /**
     * 修改维修记录
     * 
     * @param aupRepair 维修记录
     * @return 结果
     */
    public int updateAupRepair(AupRepair aupRepair);

    /**
     * 删除维修记录
     * 
     * @param id 维修记录ID
     * @return 结果
     */
    public int deleteAupRepairById(Long id);

    /**
     * 批量删除维修记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupRepairByIds(String[] ids);
}
