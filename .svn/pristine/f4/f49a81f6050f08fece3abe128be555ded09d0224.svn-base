package com.feather.aupipes.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.feather.aupipes.domain.AupFileInfo;
import com.feather.aupipes.mapper.AupFileInfoMapper;
import com.feather.aupipes.service.IAupFileInfoService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.file.FileUploadUtils;

/**
 * 华农文件Service业务层处理
 * 
 * @author fancy
 * @date 2020-04-04
 */
@Service
public class AupFileInfoServiceImpl implements IAupFileInfoService {
    @Autowired
    private AupFileInfoMapper aupFileInfoMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询华农文件
     * 
     * @param fileId
     *            华农文件ID
     * @return 华农文件
     */
    @Override
    public AupFileInfo selectAupFileInfoById(Long fileId) {
        return aupFileInfoMapper.selectAupFileInfoById(fileId);
    }

    /**
     * 查询华农文件列表
     * 
     * @param aupFileInfo
     *            华农文件
     * @return 华农文件
     */
    @Override
    public List<AupFileInfo> selectAupFileInfoList(AupFileInfo aupFileInfo) {
        return aupFileInfoMapper.selectAupFileInfoList(aupFileInfo);
    }

    /**
     * 新增华农文件
     *
     * @return 结果
     */
    // @Transactional
    @Override
    public int insertAupFileInfo(MultipartFile[] multipartFiles, String code) throws IOException {
        List<AupFileInfo> aupFileInfos = new ArrayList<AupFileInfo>();

        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            AupFileInfo aupFileInfo = new AupFileInfo();
            String url = FileUploadUtils.upload(multipartFile, null, true);
            aupFileInfo.setFileId(uidWorker.getNextId());
            aupFileInfo.setFileCode(code);
            aupFileInfo.setFileTarget(new Long(888));
            aupFileInfo.setFileName(multipartFile.getOriginalFilename());
            aupFileInfo.setFileType(multipartFile.getContentType());
            aupFileInfo.setFilePath(url);
            aupFileInfo.setCreateTime(DateUtils.getNowDate());
            aupFileInfos.add(aupFileInfo);
        }

        return aupFileInfoMapper.insertAupFileInfos(aupFileInfos);
    }

    /**
     * 修改华农文件
     * 
     * @param aupFileInfo
     *            华农文件
     * @return 结果
     */
    @Override
    public int updateAupFileInfo(AupFileInfo aupFileInfo) {
        return aupFileInfoMapper.updateAupFileInfo(aupFileInfo);
    }

    /**
     * 删除华农文件对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupFileInfoByIds(String ids) {
        return aupFileInfoMapper.deleteAupFileInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除华农文件信息
     * 
     * @param fileId
     *            华农文件ID
     * @return 结果
     */
    public int deleteAupFileInfoById(Long fileId) {
        return aupFileInfoMapper.deleteAupFileInfoById(fileId);
    }
}
