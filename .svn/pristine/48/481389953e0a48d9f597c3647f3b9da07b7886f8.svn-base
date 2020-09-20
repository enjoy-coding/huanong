package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupServiceInfo;
import com.feather.aupipes.mapper.AupServiceInfoMapper;
import com.feather.aupipes.service.IAupServiceInfoService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 设备Service业务层处理
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Service
public class AupServiceInfoServiceImpl implements IAupServiceInfoService 
{
    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private AupServiceInfoMapper aupServiceInfoMapper;

    /**
     * 查询设备
     * 
     * @param id 设备ID
     * @return 设备
     */
    @Override
    public AupServiceInfo selectAupServiceInfoById(Long id)
    {
        return aupServiceInfoMapper.selectAupServiceInfoById(id);
    }

    /**
     * 查询设备列表
     * 
     * @param aupServiceInfo 设备
     * @return 设备
     */
    @Override
    public List<AupServiceInfo> selectAupServiceInfoList(AupServiceInfo aupServiceInfo)
    {
        return aupServiceInfoMapper.selectAupServiceInfoList(aupServiceInfo);
    }

    /**
     * 新增设备
     * 
     * @param aupServiceInfo 设备
     * @return 结果
     */
    @Override
    public int insertAupServiceInfo(AupServiceInfo aupServiceInfo)
    {
        aupServiceInfo.setId(uidWorker.getNextId());
        aupServiceInfo.setCreateTime(new Date());
        return aupServiceInfoMapper.insertAupServiceInfo(aupServiceInfo);
    }

    /**
     * 修改设备
     * 
     * @param aupServiceInfo 设备
     * @return 结果
     */
    @Override
    public int updateAupServiceInfo(AupServiceInfo aupServiceInfo)
    {
        return aupServiceInfoMapper.updateAupServiceInfo(aupServiceInfo);
    }

    /**
     * 删除设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupServiceInfoByIds(String ids)
    {
        return aupServiceInfoMapper.deleteAupServiceInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备信息
     * 
     * @param id 设备ID
     * @return 结果
     */
    @Override
    public int deleteAupServiceInfoById(Long id)
    {
        return aupServiceInfoMapper.deleteAupServiceInfoById(id);
    }
}
