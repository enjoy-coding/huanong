package com.feather.cms.service;

import java.util.List;

import com.feather.cms.domain.CmsArgs;

/**
 * 参数Service接口
 */
public interface ICmsArgsService {
    /**
     * 查询参数
     * 
     * @param argsId
     *            参数ID
     * @return 参数
     */
    public CmsArgs selectCmsArgsById(Long argsId);

    /**
     * 查询参数列表
     * 
     * @param cmsArgs
     *            参数
     * @return 参数集合
     */
    public List<CmsArgs> selectCmsArgsList(CmsArgs cmsArgs);

    /**
     * 新增参数
     * 
     * @param cmsArgs
     *            参数
     * @return 结果
     */
    public int insertCmsArgs(CmsArgs cmsArgs);

    /**
     * 修改参数
     * 
     * @param cmsArgs
     *            参数
     * @return 结果
     */
    public int updateCmsArgs(CmsArgs cmsArgs);

    /**
     * 批量删除参数
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteCmsArgsByIds(String ids);

    /**
     * 删除参数信息
     * 
     * @param argsId
     *            参数ID
     * @return 结果
     */
    public int deleteCmsArgsById(Long argsId);
}
