package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupServiceInfo;
import java.util.List;

/**
 * 设备Service接口
 * 
 * @author fancy
 * @date 2020-06-08
 */
public interface IAupServiceInfoService
{
    /**
     * 查询设备
     * 
     * @param id 设备ID
     * @return 设备
     */
    AupServiceInfo selectAupServiceInfoById(Long id);

    /**
     * 查询设备列表
     * 
     * @param aupServiceInfo 设备
     * @return 设备集合
     */
    List<AupServiceInfo> selectAupServiceInfoList(AupServiceInfo aupServiceInfo);

    /**
     * 新增设备
     * 
     * @param aupServiceInfo 设备
     * @return 结果
     */
    int insertAupServiceInfo(AupServiceInfo aupServiceInfo);

    /**
     * 修改设备
     * 
     * @param aupServiceInfo 设备
     * @return 结果
     */
    int updateAupServiceInfo(AupServiceInfo aupServiceInfo);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupServiceInfoByIds(String ids);

    /**
     * 删除设备信息
     * 
     * @param id 设备ID
     * @return 结果
     */
    int deleteAupServiceInfoById(Long id);
}
