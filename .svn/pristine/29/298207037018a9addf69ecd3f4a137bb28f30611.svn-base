package com.feather.aupipes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupInspectAddressMapper;
import com.feather.aupipes.domain.AupInspectAddress;
import com.feather.aupipes.service.IAupInspectAddressService;
import com.feather.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author fancy
 * @date 2020-01-09
 */
@Service
public class AupInspectAddressServiceImpl implements IAupInspectAddressService 
{
    @Autowired
    private AupInspectAddressMapper aupInspectAddressMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param addressId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public AupInspectAddress selectAupInspectAddressById(Long addressId)
    {
        return aupInspectAddressMapper.selectAupInspectAddressById(addressId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AupInspectAddress> selectAupInspectAddressList(AupInspectAddress aupInspectAddress)
    {
        return aupInspectAddressMapper.selectAupInspectAddressList(aupInspectAddress);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAupInspectAddress(AupInspectAddress aupInspectAddress)
    {
        return aupInspectAddressMapper.insertAupInspectAddress(aupInspectAddress);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param aupInspectAddress 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAupInspectAddress(AupInspectAddress aupInspectAddress)
    {
        return aupInspectAddressMapper.updateAupInspectAddress(aupInspectAddress);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectAddressByIds(String ids)
    {
        return aupInspectAddressMapper.deleteAupInspectAddressByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param addressId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectAddressById(Long addressId)
    {
        return aupInspectAddressMapper.deleteAupInspectAddressById(addressId);
    }
}
