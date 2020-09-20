package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupPower;

import java.util.List;
import java.util.Map;

/**
 * 配电房Mapper接口
 *
 * @author fancy
 * @date 2020-01-07
 */
public interface AupPowerMapper {
    /**
     * 查询配电房
     *
     * @param id 配电房ID
     * @return 配电房
     */
    public AupPower selectAupPowerById(Long id);

    /**
     * 查询配电房列表
     *
     * @param aupPower 配电房
     * @return 配电房集合
     */
    public List<AupPower> selectAupPowerList(AupPower aupPower);

    /**
     * 新增配电房
     *
     * @param aupPower 配电房
     * @return 结果
     */
    public int insertAupPower(AupPower aupPower);

    /**
     * 修改配电房
     *
     * @param aupPower 配电房
     * @return 结果
     */
    public int updateAupPower(AupPower aupPower);

    /**
     * 删除配电房
     *
     * @param id 配电房ID
     * @return 结果
     */
    public int deleteAupPowerById(Long id);

    /**
     * 批量删除配电房
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPowerByIds(String[] ids);

    public int selectAupPowerCountByPid(Long pid);

    List<Map> selectPdfInfoList(String pdfId, String fwId_, String hlId_);

    public AupPower checkAupPowerBsmNoUnque(String bsm);

    public int checkAupPowerHasChildren(Long pid);
}