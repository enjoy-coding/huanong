package com.feather.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.feather.cms.domain.CmsContent;
import com.feather.cms.mapper.CmsContentMapper;
import com.feather.cms.service.ICmsContentService;

/**
 * 文件内容Service业务层处理
 */
@Service
@CacheConfig(cacheNames = "cms:content")
public class CmsContentServiceImpl implements ICmsContentService {
    @Autowired
    private CmsContentMapper cmsContentMapper;

    /**
     * 查询文件内容
     * 
     * @param fileId
     *            文件ID
     * @return 文件内容
     */
    @Override
    @Cacheable(key = "':'+ #root.args[0]", unless = "#result.getFileContent() == null")
    public CmsContent selectCmsContentById(String fileId) {
        return cmsContentMapper.selectCmsContentById(fileId);
    }

    /**
     * 新增文件内容
     * 
     * @param cmsContent
     *            文件内容
     * @return 结果
     */
    @CachePut(key = "':'+ #root.args[0].getFileId()")
    public int insertCmsContent(CmsContent cmsContent) {
        return cmsContentMapper.insertCmsContent(cmsContent);
    }

    /**
     * 修改文件内容
     * 
     * @param cmsContent
     *            文件内容
     * @return 结果
     */
    @CachePut(key = "':'+ #root.args[0].getFileId()")
    public int updateCmsContent(CmsContent cmsContent) {
        return cmsContentMapper.updateCmsContent(cmsContent);
    }

    /**
     * 删除文件内容
     * 
     * @param fileId
     *            文件ID
     * @return 结果
     */
    @CacheEvict(key = "':'+ #root.args[0]")
    public int deleteCmsContentById(String fileId) {
        return cmsContentMapper.deleteCmsContentById(fileId);
    }
}
