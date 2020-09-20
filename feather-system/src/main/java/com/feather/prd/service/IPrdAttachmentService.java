package com.feather.prd.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.feather.common.core.domain.UrlSeries;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.domain.PrdAttachment.AttachmentInfo;

/**
 * 附件信息Service接口
 * 
 * @author flogyin
 * @date 2019-09-26
 */
public interface IPrdAttachmentService {
    /**
     * 查询附件信息
     * 
     * @param fileId
     *            附件信息ID
     * @return 附件信息
     */
    public PrdAttachment selectPrdAttachmentById(Long fileId);

    /**
     * 查询附件信息列表
     * 
     * @param prdAttachment
     *            附件信息
     * @return 附件信息集合
     */
    public List<PrdAttachment> selectPrdAttachmentList(PrdAttachment prdAttachment);

    /**
     * 新增附件信息
     * 
     * @param prdAttachment
     *            附件信息
     * @return 结果
     */
    public int insertPrdAttachment(PrdAttachment prdAttachment);

    /**
     * 修改附件信息
     * 
     * @param prdAttachment
     *            附件信息
     * @return 结果
     */
    public int updatePrdAttachment(PrdAttachment prdAttachment);

    /**
     * 批量删除附件信息
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePrdAttachmentByIds(String ids);

    /**
     * 删除附件信息信息
     * 
     * @param fileId
     *            附件信息ID
     * @return 结果
     */
    public int deletePrdAttachmentById(Long fileId);

    /**
     * 根据url获取系列地址（对应的缩略图地址或原始地址）
     */
    public UrlSeries getFileSeries(String url, String basePath);

    /**
     * 上传附件信息信息
     */
    public AttachmentInfo upload(MultipartFile file, Boolean thumb, boolean saveDb);

    /**
     * 上传附件信息信息
     */
    public List<AttachmentInfo> upload(MultipartFile[] files, Boolean thumb, boolean saveDb);

    /**
     * 上传附件信息信息
     */
    public AttachmentInfo upload(String link, String extension, boolean saveDb);
}