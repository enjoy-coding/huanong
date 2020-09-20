package com.feather.prd.mapper;

import java.util.List;

import com.feather.prd.domain.PrdAppversion;

/**
 * App版本Mapper接口
 * 
 * @author flogyin
 * @date 2019-09-25
 */
public interface PrdAppversionMapper {
    /**
     * 查询App版本
     * 
     * @param versionId
     *            App版本ID
     * @return App版本
     */
    public PrdAppversion selectPrdAppversionById(Long versionId);

    /**
     * 查询App版本列表
     * 
     * @param PrdAppversion
     *            App版本
     * @return App版本集合
     */
    public List<PrdAppversion> selectPrdAppversionList(PrdAppversion PrdAppversion);

    /**
     * 查询App版本列表
     * 
     * @param PrdAppversion
     *            App版本
     * @return App版本集合
     */
    public List<PrdAppversion> selectPrdAppversionLastByPackage(String versionPackage);

    /**
     * 新增App版本
     * 
     * @param PrdAppversion
     *            App版本
     * @return 结果
     */
    public int insertPrdAppversion(PrdAppversion PrdAppversion);

    /**
     * 修改App版本
     * 
     * @param PrdAppversion
     *            App版本
     * @return 结果
     */
    public int updatePrdAppversion(PrdAppversion PrdAppversion);

    /**
     * 删除App版本
     * 
     * @param versionId
     *            App版本ID
     * @return 结果
     */
    public int deletePrdAppversionById(Long versionId);

    /**
     * 批量删除App版本
     * 
     * @param versionIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePrdAppversionByIds(String[] versionIds);
}