package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWaterquality;
import java.util.List;

/**
 * 水质Mapper接口
 *
 * @author fancy
 * @date 2020-03-13
 */
public interface AupWaterqualityMapper
{
    /**
     * 查询水质
     *
     * @param oid 水质ID
     * @return 水质
     */
    public AupWaterquality selectAupWaterqualityById(Long oid);

    /**
     * 查询水质列表
     *
     * @param aupWaterquality 水质
     * @return 水质集合
     */
    public List<AupWaterquality> selectAupWaterqualityList(AupWaterquality aupWaterquality);

    /**
     * 新增水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    public int insertAupWaterquality(AupWaterquality aupWaterquality);

    /**
     * 修改水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    public int updateAupWaterquality(AupWaterquality aupWaterquality);

    /**
     * 删除水质
     *
     * @param oid 水质ID
     * @return 结果
     */
    public int deleteAupWaterqualityById(Long oid);

    /**
     * 批量删除水质
     *
     * @param oids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWaterqualityByIds(String[] oids);
}