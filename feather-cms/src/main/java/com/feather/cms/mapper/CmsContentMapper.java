package com.feather.cms.mapper;

import com.feather.cms.domain.CmsContent;

/**
 * 文件内容Mapper接口
 */
public interface CmsContentMapper {
    /**
     * 查询文件内容
     * 
     * @param fileId
     *            文件ID
     * @return 文件内容
     */
    public CmsContent selectCmsContentById(String fileId);

    /**
     * 新增文件内容
     * 
     * @param cmsContent
     *            文件内容
     * @return 结果
     */
    public int insertCmsContent(CmsContent cmsContent);

    /**
     * 修改文件内容
     * 
     * @param cmsContent
     *            文件内容
     * @return 结果
     */
    public int updateCmsContent(CmsContent cmsContent);

    /**
     * 删除文件内容
     * 
     * @param fileId
     *            文件ID
     * @return 结果
     */
    public int deleteCmsContentById(String fileId);
}
