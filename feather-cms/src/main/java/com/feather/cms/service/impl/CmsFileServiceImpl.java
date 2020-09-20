package com.feather.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.cms.domain.CmsFile;
import com.feather.cms.mapper.CmsFileMapper;
import com.feather.cms.service.ICmsFileService;
import com.feather.common.core.text.Convert;

/**
 * 文件Service业务层处理
 */
@Service
public class CmsFileServiceImpl implements ICmsFileService {
    @Autowired
    private CmsFileMapper cmsFileMapper;

    /**
     * 查询文件
     * 
     * @param fileId
     *            文件ID
     * @return 文件
     */
    @Override
    public CmsFile selectCmsFileById(String fileId) {
        return cmsFileMapper.selectCmsFileById(fileId);
    }

    /**
     * 查询文件列表
     * 
     * @param cmsFile
     *            文件
     * @return 文件
     */
    @Override
    public List<CmsFile> selectCmsFileList(CmsFile cmsFile) {
        return cmsFileMapper.selectCmsFileList(cmsFile);
    }

    /**
     * 新增文件
     * 
     * @param cmsFile
     *            文件
     * @return 结果
     */
    @Override
    public int insertCmsFile(CmsFile cmsFile) {
        return cmsFileMapper.insertCmsFile(cmsFile);
    }

    /**
     * 修改文件
     * 
     * @param cmsFile
     *            文件
     * @return 结果
     */
    @Override
    public int updateCmsFile(CmsFile cmsFile) {
        return cmsFileMapper.updateCmsFile(cmsFile);
    }

    /**
     * 删除文件对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCmsFileByIds(String ids) {
        return cmsFileMapper.deleteCmsFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文件信息
     * 
     * @param fileId
     *            文件ID
     * @return 结果
     */
    public int deleteCmsFileById(String fileId) {
        return cmsFileMapper.deleteCmsFileById(fileId);
    }
}
