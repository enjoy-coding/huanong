package com.feather.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.cms.domain.CmsPage;
import com.feather.cms.mapper.CmsPageMapper;
import com.feather.cms.service.ICmsPageService;
import com.feather.common.core.text.Convert;

/**
 * 页面Service业务层处理
 */
@Service
public class CmsPageServiceImpl implements ICmsPageService {
    @Autowired
    private CmsPageMapper cmsPageMapper;

    /**
     * 查询页面
     * 
     * @param pageId
     *            页面ID
     * @return 页面
     */
    @Override
    public CmsPage selectCmsPageById(Long pageId) {
        return cmsPageMapper.selectCmsPageById(pageId);
    }

    /**
     * 查询页面列表
     * 
     * @param cmsPage
     *            页面
     * @return 页面
     */
    @Override
    public List<CmsPage> selectCmsPageList(CmsPage cmsPage) {
        return cmsPageMapper.selectCmsPageList(cmsPage);
    }

    /**
     * 新增页面
     * 
     * @param cmsPage
     *            页面
     * @return 结果
     */
    @Override
    public int insertCmsPage(CmsPage cmsPage) {
        return cmsPageMapper.insertCmsPage(cmsPage);
    }

    /**
     * 修改页面
     * 
     * @param cmsPage
     *            页面
     * @return 结果
     */
    @Override
    public int updateCmsPage(CmsPage cmsPage) {
        return cmsPageMapper.updateCmsPage(cmsPage);
    }

    /**
     * 删除页面对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCmsPageByIds(String ids) {
        return cmsPageMapper.deleteCmsPageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除页面信息
     * 
     * @param pageId
     *            页面ID
     * @return 结果
     */
    public int deleteCmsPageById(Long pageId) {
        return cmsPageMapper.deleteCmsPageById(pageId);
    }
}
