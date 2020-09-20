package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupWaterquality;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 水质Service接口
 *
 * @author fancy
 * @date 2020-03-13
 */
public interface IAupWaterqualityService
{
    /**
     * 查询水质
     *
     * @param oid 水质ID
     * @return 水质
     */
    AupWaterquality selectAupWaterqualityById(Long oid);

    List<Ztree> getWaterQualityTree();

    /**
     * 查询水质列表
     *
     * @param aupWaterquality 水质
     * @return 水质集合
     */
    List<AupWaterquality> selectAupWaterqualityList(AupWaterquality aupWaterquality);

    /**
     * 新增水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    int insertAupWaterquality(AupWaterquality aupWaterquality);

    /**
     * 修改水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    int updateAupWaterquality(AupWaterquality aupWaterquality);

    /**
     * 批量删除水质
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupWaterqualityByIds(String ids);

    /**
     * 删除水质信息
     *
     * @param oid 水质ID
     * @return 结果
     */
    int deleteAupWaterqualityById(Long oid);

    void timeSetWaterQuality();

}