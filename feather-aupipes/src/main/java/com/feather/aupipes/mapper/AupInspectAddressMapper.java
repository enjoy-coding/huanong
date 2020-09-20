package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspectAddress;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author fancy
 * @date 2020-01-09
 */
public interface AupInspectAddressMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param addressId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public AupInspectAddress selectAupInspectAddressById(Long addressId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AupInspectAddress> selectAupInspectAddressList(AupInspectAddress aupInspectAddress);

    /**
     * 新增【请填写功能名称】
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 结果
     */
    public int insertAupInspectAddress(AupInspectAddress aupInspectAddress);

    /**
     * 修改【请填写功能名称】
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 结果
     */
    public int updateAupInspectAddress(AupInspectAddress aupInspectAddress);

    /**
     * 删除【请填写功能名称】
     * 
     * @param addressId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAupInspectAddressById(Long addressId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param addressIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectAddressByIds(String[] addressIds);
}
