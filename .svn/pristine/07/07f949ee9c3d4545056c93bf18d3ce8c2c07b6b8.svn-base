package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPlansType;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 预案类型Service接口
 *
 * @author fancy
 * @date 2020-01-02
 */
public interface IAupPlansTypeService {
    /**
     * 查询预案类型
     *
     * @param id 预案类型ID
     * @return 预案类型
     */
     AupPlansType selectAupPlansTypeById(Long id);

    /**
     * 查询预案类型列表
     *
     * @param aupPlansType 预案类型
     * @return 预案类型集合
     */
     List<AupPlansType> selectAupPlansTypeList(AupPlansType aupPlansType);

    /**
     * 新增预案类型
     *
     * @param aupPlansType 预案类型
     * @return 结果
     */
     int insertAupPlansType(AupPlansType aupPlansType);

    /**
     * 修改预案类型
     *
     * @param aupPlansType 预案类型
     * @return 结果
     */
     int updateAupPlansType(AupPlansType aupPlansType);

    /**
     * 批量删除预案类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAupPlansTypeByIds(String ids);

    /**
     * 删除预案类型信息
     *
     * @param id 预案类型ID
     * @return 结果
     */
     int deleteAupPlansTypeById(Long id);

    List<Ztree> selectWarringCategoryTree();
}
