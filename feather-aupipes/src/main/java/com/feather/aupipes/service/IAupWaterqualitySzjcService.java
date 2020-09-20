package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupWaterquality;
import com.feather.aupipes.domain.AupWaterqualitySzjc;
import java.util.List;

/**
 * 水质监测Service接口
 *
 * @author fancy
 * @date 2020-03-13
 */
public interface IAupWaterqualitySzjcService
{
    /**
     * 查询水质监测
     *
     * @param id 水质监测ID
     * @return 水质监测
     */
    AupWaterqualitySzjc selectAupWaterqualitySzjcById(Long id);

    AupWaterqualitySzjc selectAupWaterqualitySzjcByOid(Long oid);

    /**
     * 查询水质监测列表
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 水质监测集合
     */
    List<AupWaterqualitySzjc> selectAupWaterqualitySzjcList(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 新增水质监测
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 结果
     */
    int insertAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 修改水质监测
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 结果
     */
    int updateAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 批量删除水质监测
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupWaterqualitySzjcByIds(String ids);

    /**
     * 删除水质监测信息
     *
     * @param id 水质监测ID
     * @return 结果
     */
    int deleteAupWaterqualitySzjcById(String id);

    /**
     * 定时插入水质监测
     */
    void timeSetWaterQualitySzjc(List<AupWaterquality> waterqualityList);
}