package com.feather.cms.service;

import java.util.List;

import com.feather.cms.domain.CmsPage;

/**
 * 页面Service接口
 */
public interface ICmsPageService {
    /**
     * 查询页面
     * 
     * @param pageId
     *            页面ID
     * @return 页面
     */
    public CmsPage selectCmsPageById(Long pageId);

    /**
     * 查询页面列表
     * 
     * @param cmsPage
     *            页面
     * @return 页面集合
     */
    public List<CmsPage> selectCmsPageList(CmsPage cmsPage);

    /**
     * 新增页面
     * 
     * @param cmsPage
     *            页面
     * @return 结果
     */
    public int insertCmsPage(CmsPage cmsPage);

    /**
     * 修改页面
     * 
     * @param cmsPage
     *            页面
     * @return 结果
     */
    public int updateCmsPage(CmsPage cmsPage);

    /**
     * 批量删除页面
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteCmsPageByIds(String ids);

    /**
     * 删除页面信息
     * 
     * @param pageId
     *            页面ID
     * @return 结果
     */
    public int deleteCmsPageById(Long pageId);
}
