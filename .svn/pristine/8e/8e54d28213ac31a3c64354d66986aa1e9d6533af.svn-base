package com.feather.cms.mapper;

import java.util.List;

import com.feather.cms.domain.CmsFile;

/**
 * 文件Mapper接口
 */
public interface CmsFileMapper {
    /**
     * 查询文件
     * 
     * @param fileId
     *            文件ID
     * @return 文件
     */
    public CmsFile selectCmsFileById(String fileId);

    /**
     * 查询文件列表
     * 
     * @param cmsFile
     *            文件
     * @return 文件集合
     */
    public List<CmsFile> selectCmsFileList(CmsFile cmsFile);

    /**
     * 新增文件
     * 
     * @param cmsFile
     *            文件
     * @return 结果
     */
    public int insertCmsFile(CmsFile cmsFile);

    /**
     * 修改文件
     * 
     * @param cmsFile
     *            文件
     * @return 结果
     */
    public int updateCmsFile(CmsFile cmsFile);

    /**
     * 删除文件
     * 
     * @param fileId
     *            文件ID
     * @return 结果
     */
    public int deleteCmsFileById(String fileId);

    /**
     * 批量删除文件
     * 
     * @param fileIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteCmsFileByIds(String[] fileIds);
}
