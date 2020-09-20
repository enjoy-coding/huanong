package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWarringCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预警类型Mapper接口
 *
 * @author fancy
 * @date 2019-12-20
 */
public interface AupWarringCategoryMapper
{
    /**
     * 查询预警类型
     *
     * @param id 预警类型ID
     * @return 预警类型
     */
    public AupWarringCategory selectAupWarringCategoryById(Long id);

    /**
     * 查询预警类型列表
     *
     * @param aupWarringCategory 预警类型
     * @return 预警类型集合
     */
    public List<AupWarringCategory> selectAupWarringCategoryList(AupWarringCategory aupWarringCategory);

    /**
     * 新增预警类型
     *
     * @param aupWarringCategory 预警类型
     * @return 结果
     */
    public int insertAupWarringCategory(AupWarringCategory aupWarringCategory);

    /**
     * 修改预警类型
     *
     * @param aupWarringCategory 预警类型
     * @return 结果
     */
    public int updateAupWarringCategory(AupWarringCategory aupWarringCategory);

    /**
     * 删除预警类型
     *
     * @param id 预警类型ID
     * @return 结果
     */
    public int deleteAupWarringCategoryById(Long id);

    /**
     * 批量删除预警类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWarringCategoryByIds(String[] ids);

    public AupWarringCategory  getWarringListByPid(@Param("pid") String pid);
}