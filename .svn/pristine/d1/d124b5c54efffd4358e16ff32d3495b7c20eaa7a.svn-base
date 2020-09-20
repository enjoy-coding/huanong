package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupWarringCategory;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 预警类型Service接口
 *
 * @author fancy
 * @date 2019-12-20
 */
public interface IAupWarringCategoryService
{
    /**
     * 查询预警类型
     *
     * @param id 预警类型ID
     * @return 预警类型
     */
    AupWarringCategory selectAupWarringCategoryById(Long id);

    /**
     * 查询预警类型列表
     *
     * @param aupWarringCategory 预警类型
     * @return 预警类型集合
     */
    List<AupWarringCategory> selectAupWarringCategoryList(AupWarringCategory aupWarringCategory);


    List<Ztree> selectWarringCategoryTree(String name);

    /**
     * 新增预警类型
     *
     * @param aupWarringCategory 预警类型
     * @return 结果
     */
    int insertAupWarringCategory(AupWarringCategory aupWarringCategory);

    /**
     * 修改预警类型
     *
     * @param aupWarringCategory 预警类型
     * @return 结果
     */
    int updateAupWarringCategory(AupWarringCategory aupWarringCategory);

    /**
     * 批量删除预警类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupWarringCategoryByIds(String ids);

    /**
     * 删除预警类型信息
     *
     * @param id 预警类型ID
     * @return 结果
     */
    int deleteAupWarringCategoryById(Long id);
}