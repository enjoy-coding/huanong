package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupInspectAddress;
import java.util.List;

/**
 * 检查Service接口
 * 
 * @author fancy
 * @date 2020-01-09
 */
public interface IAupInspectAddressService
{
    /**
     * 查询检查
     * 
     * @param addressId 检查ID
     * @return 检查
     */
    AupInspectAddress selectAupInspectAddressById(Long addressId);

    /**
     * 查询检查列表
     * 
     * @param aupInspectAddress 检查
     * @return 检查集合
     */
    List<AupInspectAddress> selectAupInspectAddressList(AupInspectAddress aupInspectAddress);

    /**
     * 新增检查
     * 
     * @param aupInspectAddress 检查
     * @return 结果
     */
    int insertAupInspectAddress(AupInspectAddress aupInspectAddress);

    /**
     * 修改检查
     * 
     * @param aupInspectAddress 检查
     * @return 结果
     */
    int updateAupInspectAddress(AupInspectAddress aupInspectAddress);

    /**
     * 批量删除检查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupInspectAddressByIds(String ids);

    /**
     * 删除检查信息
     * 
     * @param addressId 检查ID
     * @return 结果
     */
    int deleteAupInspectAddressById(Long addressId);
}
