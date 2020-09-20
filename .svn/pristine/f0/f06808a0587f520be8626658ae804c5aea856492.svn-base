package com.feather.prd.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.config.UidWorker;
import com.feather.common.core.domain.UrlSeries;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.common.utils.file.ThumbnailFactory;
import com.feather.common.utils.http.HttpUtils;
import com.feather.common.utils.http.HttpUtils.InputStreamCallback;
import com.feather.framework.util.ShiroUtils;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.domain.PrdAttachment.AttachmentInfo;
import com.feather.prd.mapper.PrdAttachmentMapper;
import com.feather.prd.service.IPrdAttachmentService;

/**
 * 附件信息Service业务层处理
 * 
 * @author flogyin
 * @date 2019-09-26
 */
@Service
public class PrdAttachmentServiceImpl implements IPrdAttachmentService {
    @Autowired
    private PrdAttachmentMapper prdAttachmentMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询附件信息
     * 
     * @param fileId
     *            附件信息ID
     * @return 附件信息
     */
    @Override
    public PrdAttachment selectPrdAttachmentById(Long fileId) {
        return prdAttachmentMapper.selectPrdAttachmentById(fileId);
    }

    /**
     * 查询附件信息列表
     * 
     * @param prdAttachment
     *            附件信息
     * @return 附件信息
     */
    @Override
    public List<PrdAttachment> selectPrdAttachmentList(PrdAttachment prdAttachment) {
        return prdAttachmentMapper.selectPrdAttachmentList(prdAttachment);
    }

    /**
     * 新增附件信息
     * 
     * @param prdAttachment
     *            附件信息
     * @return 结果
     */
    @Override
    public int insertPrdAttachment(PrdAttachment prdAttachment) {
        if (prdAttachment.getFileId() == null) {
            prdAttachment.setFileId(uidWorker.getNextId());
        }
        return prdAttachmentMapper.insertPrdAttachment(prdAttachment);
    }

    /**
     * 修改附件信息
     * 
     * @param prdAttachment
     *            附件信息
     * @return 结果
     */
    @Override
    public int updatePrdAttachment(PrdAttachment prdAttachment) {
        return prdAttachmentMapper.updatePrdAttachment(prdAttachment);
    }

    /**
     * 删除附件信息对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePrdAttachmentByIds(String ids) {
        return prdAttachmentMapper.deletePrdAttachmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除附件信息信息
     * 
     * @param fileId
     *            附件信息ID
     * @return 结果
     */
    @Override
    public int deletePrdAttachmentById(Long fileId) {
        return prdAttachmentMapper.deletePrdAttachmentById(fileId);
    }

    /**
     * 根据url获取系列地址（对应的缩略图地址或原始地址）
     */
    @Override
    public UrlSeries getFileSeries(String url, String domain) {
        UrlSeries urlSeries = ThumbnailFactory.getUrlSeries(url);
        if (urlSeries.getOriginal() != null) {
            urlSeries.setOriginal(domain + urlSeries.getOriginal());
        }
        String[] thumbnail = urlSeries.getThumbnail();
        if (thumbnail != null) {
            for (int i = 0; i < thumbnail.length; i++) {
                thumbnail[i] = domain + thumbnail[i];
            }
        }
        return urlSeries;
    }

    /**
     * 上传附件信息信息
     */
    @Override
    public AttachmentInfo upload(MultipartFile file, Boolean thumb, boolean saveDb) {
        String url = null;
        try {
            url = FileUploadUtils.upload(file, null, thumb != null ? thumb : false);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        PrdAttachment prdAttachment = new PrdAttachment();
        prdAttachment.setFileName(file.getOriginalFilename());
        prdAttachment.setFilePath(url);
        prdAttachment.setFileSize(file.getSize());
        if (saveDb) {
            String loginName = ShiroUtils.getLoginName();
            Date time = new Date();
            prdAttachment.setFileId(uidWorker.getNextId());
            prdAttachment.setCreateBy(loginName);
            prdAttachment.setCreateTime(time);
            prdAttachment.setUpdateBy(loginName);
            prdAttachment.setUpdateTime(time);
            insertPrdAttachment(prdAttachment);
        }
        return prdAttachment.topAttachmentInfo();
    }

    /**
     * 上传附件信息信息
     */
    @Override
    public List<AttachmentInfo> upload(MultipartFile[] files, Boolean thumb, boolean saveDb) {
        List<AttachmentInfo> list = new ArrayList<>();
        for (MultipartFile item : files) {
            list.add(upload(item, thumb, saveDb));
        }
        return list;
    }

    /**
     * 上传附件信息信息
     */
    @Override
    public AttachmentInfo upload(String link, String extension, boolean saveDb) {
        byte[] bytes = (byte[]) HttpUtils.send(link, null, new InputStreamCallback() {
            @Override
            public Object handle(InputStream is) throws IOException {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    while ((length = is.read(bytes)) > 0) {
                        baos.write(bytes, 0, length);
                    }
                    return baos.toByteArray();
                }
            }
        }, false);

        String url = null;
        try {
            url = FileUploadUtils.upload(bytes, extension, null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        PrdAttachment prdAttachment = new PrdAttachment();
        prdAttachment.setFilePath(url);
        prdAttachment.setFileSize(FileUploadUtils.getFileByUrl(url, false).length());
        if (saveDb) {
            String loginName = ShiroUtils.getLoginName();
            Date time = new Date();
            prdAttachment.setFileId(uidWorker.getNextId());
            prdAttachment.setCreateBy(loginName);
            prdAttachment.setCreateTime(time);
            prdAttachment.setUpdateBy(loginName);
            prdAttachment.setUpdateTime(time);
            insertPrdAttachment(prdAttachment);
        }
        return prdAttachment.topAttachmentInfo();
    }
}
