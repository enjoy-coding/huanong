package com.feather.prd.domain;

import java.text.DecimalFormat;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息对象 prd_attachment
 * 
 * @author flogyin
 * @date 2019-09-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrdAttachment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Long fileSize;

    @Excel(name = "文件大小")
    private String sizeStr;

    public String getSizeStr() {
        return getFormatSize(fileSize != null ? fileSize : 0);
    }

    public static String getFormatSize(long s) {
        double size = s;
        String scale = "K";
        size = size / 1024;
        if (size >= 1000D) {
            size = size / 1000;
            scale = "M";
        }
        String str = "0";
        if (size > 0) {
            str = new DecimalFormat("0.#").format(size) + " " + scale;
        }
        return str;
    }

    public AttachmentInfo topAttachmentInfo() {
        AttachmentInfo info = new AttachmentInfo();
        info.setFileId(fileId);
        info.setFileName(fileName);
        info.setFilePath(filePath);
        info.setFileSize(fileSize);
        info.setSizeStr(sizeStr);
        return info;
    }

    @Data
    @NoArgsConstructor
    public static class AttachmentInfo {
        private Long fileId;
        private String fileName;
        private String filePath;
        private Long fileSize;
        private String sizeStr;
    }
}
