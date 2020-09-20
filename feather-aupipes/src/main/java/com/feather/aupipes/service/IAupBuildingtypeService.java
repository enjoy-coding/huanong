package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingtype;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author fancy
 * @date 2020-07-03
 */
public interface IAupBuildingtypeService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public AupBuildingtype selectAupBuildingtypeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aupBuildingtype 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AupBuildingtype> selectAupBuildingtypeList(AupBuildingtype aupBuildingtype);

    /**
     * 新增【请填写功能名称】
     * 
     * @param aupBuildingtype 【请填写功能名称】
     * @return 结果
     */
    public int insertAupBuildingtype(AupBuildingtype aupBuildingtype);

    /**
     * 修改【请填写功能名称】
     * 
     * @param aupBuildingtype 【请填写功能名称】
     * @return 结果
     */
    public int updateAupBuildingtype(AupBuildingtype aupBuildingtype);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupBuildingtypeByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAupBuildingtypeById(Long id);
}
