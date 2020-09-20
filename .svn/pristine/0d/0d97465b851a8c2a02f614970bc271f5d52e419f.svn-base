package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupFileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 华农文件Service接口
 * 
 * @author fancy
 * @date 2020-04-04
 */
public interface IAupFileInfoService
{
    /**
     * 查询华农文件
     * 
     * @param fileId 华农文件ID
     * @return 华农文件
     */
    AupFileInfo selectAupFileInfoById(Long fileId);

    /**
     * 查询华农文件列表
     * 
     * @param aupFileInfo 华农文件
     * @return 华农文件集合
     */
    List<AupFileInfo> selectAupFileInfoList(AupFileInfo aupFileInfo);

    /**
     * 新增华农文件
     * 
     * @param multipartFiles 华农文件
     * @return 结果
     */
    int insertAupFileInfo(MultipartFile[] multipartFiles, String code) throws IOException;

    /**
     * 修改华农文件
     * 
     * @param aupFileInfo 华农文件
     * @return 结果
     */
    int updateAupFileInfo(AupFileInfo aupFileInfo);

    /**
     * 批量删除华农文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupFileInfoByIds(String ids);
}
