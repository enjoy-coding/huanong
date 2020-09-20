package com.feather.cms.mapper;

import java.util.List;

import com.feather.cms.domain.CmsPage;

/**
 * 页面Mapper接口
 */
public interface CmsPageMapper {
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
     * 删除页面
     * 
     * @param pageId
     *            页面ID
     * @return 结果
     */
    public int deleteCmsPageById(Long pageId);

    /**
     * 批量删除页面
     * 
     * @param pageIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteCmsPageByIds(String[] pageIds);
}
