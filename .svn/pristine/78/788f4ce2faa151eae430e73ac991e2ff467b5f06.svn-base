package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupFileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 华农文件Mapper接口
 * 
 * @author fancy
 * @date 2020-04-04
 */
public interface AupFileInfoMapper 
{
    /**
     * 查询华农文件
     * 
     * @param fileId 华农文件ID
     * @return 华农文件
     */
    public AupFileInfo selectAupFileInfoById(Long fileId);

    /**
     * 查询华农文件列表
     * 
     * @param aupFileInfo 华农文件
     * @return 华农文件集合
     */
    public List<AupFileInfo> selectAupFileInfoList(AupFileInfo aupFileInfo);

    public int insertAupFileInfos(@Param("list")List<AupFileInfo> aupFileInfos);
    /**
     * 新增华农文件
     * 
     * @param aupFileInfo 华农文件
     * @return 结果
     */
    public int insertAupFileInfo(AupFileInfo aupFileInfo);

    /**
     * 修改华农文件
     * 
     * @param aupFileInfo 华农文件
     * @return 结果
     */
    public int updateAupFileInfo(AupFileInfo aupFileInfo);

    /**
     * 删除华农文件
     * 
     * @param fileId 华农文件ID
     * @return 结果
     */
    public int deleteAupFileInfoById(Long fileId);

    /**
     * 批量删除华农文件
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupFileInfoByIds(String[] fileIds);
}
