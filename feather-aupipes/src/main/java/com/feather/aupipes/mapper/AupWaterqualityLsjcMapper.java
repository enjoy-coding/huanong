package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWaterqualityLsjc;
import java.util.List;

/**
 * 流速水位监测Mapper接口
 *
 * @author fancy
 * @date 2020-03-13
 */
public interface AupWaterqualityLsjcMapper
{
    /**
     * 查询流速水位监测
     *
     * @param id 流速水位监测ID
     * @return 流速水位监测
     */
    public AupWaterqualityLsjc selectAupWaterqualityLsjcById(Long id);


    /**
     * 获取最近一条监测信息
     * @return
     */
    public  List<AupWaterqualityLsjc> selectAupWaterqualityLsjcVoByOid(Long oid);
    /**
     * 查询流速水位监测列表
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 流速水位监测集合
     */
    public List<AupWaterqualityLsjc> selectAupWaterqualityLsjcList(AupWaterqualityLsjc aupWaterqualityLsjc);

    /**
     * 新增流速水位监测
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 结果
     */
    public int insertAupWaterqualityLsjc(AupWaterqualityLsjc aupWaterqualityLsjc);

    /**
     * 修改流速水位监测
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 结果
     */
    public int updateAupWaterqualityLsjc(AupWaterqualityLsjc aupWaterqualityLsjc);

    /**
     * 删除流速水位监测
     *
     * @param id 流速水位监测ID
     * @return 结果
     */
    public int deleteAupWaterqualityLsjcById(Long id);

    /**
     * 批量删除流速水位监测
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWaterqualityLsjcByIds(String[] ids);
}