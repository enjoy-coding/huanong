package com.feather.prd.mapper;

import java.util.List;

import com.feather.prd.domain.PrdAttachment;

/**
 * 文件信息Mapper接口
 * 
 * @author flogyin
 * @date 2019-09-26
 */
public interface PrdAttachmentMapper {
    /**
     * 查询文件信息
     * 
     * @param fileId
     *            文件信息ID
     * @return 文件信息
     */
    public PrdAttachment selectPrdAttachmentById(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param prdAttachment
     *            文件信息
     * @return 文件信息集合
     */
    public List<PrdAttachment> selectPrdAttachmentList(PrdAttachment prdAttachment);

    /**
     * 新增文件信息
     * 
     * @param prdAttachment
     *            文件信息
     * @return 结果
     */
    public int insertPrdAttachment(PrdAttachment prdAttachment);

    /**
     * 修改文件信息
     * 
     * @param prdAttachment
     *            文件信息
     * @return 结果
     */
    public int updatePrdAttachment(PrdAttachment prdAttachment);

    /**
     * 删除文件信息
     * 
     * @param fileId
     *            文件信息ID
     * @return 结果
     */
    public int deletePrdAttachmentById(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePrdAttachmentByIds(String[] fileIds);
}
