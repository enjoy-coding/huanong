package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWaterqualitySzjc;
import java.util.List;

/**
 * 水质监测Mapper接口
 *
 * @author fancy
 * @date 2020-03-13
 */
public interface AupWaterqualitySzjcMapper
{
    /**
     * 查询水质监测
     *
     * @param id 水质监测ID
     * @return 水质监测
     */
    public AupWaterqualitySzjc selectAupWaterqualitySzjcById(Long id);

    /**
     * 查询最新一条水质监测
     *
     * @param oid 水质监测ID
     * @return 水质监测
     */
    public List<AupWaterqualitySzjc> selectAupWaterqualitySzjcByOid(Long oid);


    /**
     * 查询水质监测列表
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 水质监测集合
     */
    public List<AupWaterqualitySzjc> selectAupWaterqualitySzjcList(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 新增水质监测
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 结果
     */
    public int insertAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 修改水质监测
     *
     * @param aupWaterqualitySzjc 水质监测
     * @return 结果
     */
    public int updateAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc);

    /**
     * 删除水质监测
     *
     * @param id 水质监测ID
     * @return 结果
     */
    public int deleteAupWaterqualitySzjcById(String id);

    /**
     * 批量删除水质监测
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWaterqualitySzjcByIds(String[] ids);
}