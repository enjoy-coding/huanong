package com.feather.common.utils.file;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.feather.common.config.Global;
import com.feather.common.config.Thumbnail;
import com.feather.common.core.domain.FileSeries;
import com.feather.common.core.domain.UrlSeries;

public class ThumbnailFactory {
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String PDF = "pdf";
    public static final String PPT = "ppt";

    public static final char THUMBNAIL_DIR_SEPARATOR = '-';

    private static Map<String, ThumbnailSize> sizeConfig = getSizeConfig();

    private static FileFilter thumbnailFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return !pathname.isDirectory();
        }
    };

    private ThumbnailFactory() {
    }

    /**
     * 根据url创建缩略图处理实例
     * 
     * @param url
     * @return
     */
    public static Thumbnail newThumbnail(String url) {
        File file = FileUploadUtils.getFileByUrl(url, true);
        return newThumbnail(file);
    }

    /**
     * 根据文件创建缩略图处理实例
     * 
     * @param file
     * @return
     */
    public static Thumbnail newThumbnail(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        if (extension == null) {
            extension = "";
        }
        extension = extension.toLowerCase();

        String type = null;
        Thumbnail thumbnail = null;
        switch (extension) {
        // 处理图片缩略图
        case "jpg":
            ;
        case "jpeg":
            ;
        case "png":
            ;
        case "bmp": {
            type = IMAGE;
            thumbnail = new ThumbnailPicture(file);
            break;
        }
        // 处理视频缩略图
        case "mp4":
            ;
        case "flv":
            ;
        case "mov":
            ;
        case "wmv":
            ;
        case "mkv": {
            type = VIDEO;
            thumbnail = new ThumbnailVideo(file);
            break;
        }
        // 处理PPT缩略图
        case "ppt":
            ;
        case "pptx": {
            type = PPT;
            thumbnail = new ThumbnailPPT(file);
            break;
        }
        // 处理Excel缩略图
        case "xls":
            ;
        case "xlsx": {
            thumbnail = new ThumbnailExcel(file);
            break;
        }
        // 处理Word缩略图
        case "doc":
            ;
        case "docx": {
            thumbnail = new ThumbnailWord(file);
            break;
        }
        // 处理pdf缩略图
        case "pdf":
            type = PDF;
            thumbnail = new ThumbnailPDF(file);
            break;
        }
        if (type != null && thumbnail != null) {
            ThumbnailSize size = sizeConfig.get(type);
            if (size != null) {
                thumbnail.setWidth(size.width);
                thumbnail.setHeight(size.height);
            }
        }
        return thumbnail;
    }

    /**
     * 根据url获取对应的地址系列
     * 
     * @param url
     * @return
     */
    public static final UrlSeries getUrlSeries(String url) {
        File file = FileUploadUtils.getFileByUrl(url, true);
        return getUrlSeries(file);
    }

    /**
     * 根据文件获取对应的文件系列
     * 
     * @param file
     * @return
     */
    public static final UrlSeries getUrlSeries(File file) {
        FileSeries fs = getFileSeries(file);
        UrlSeries us = new UrlSeries();

        File original = fs.getOriginal();
        if (original != null) {
            us.setOriginal(FileUploadUtils.getUrl(original));
        }
        File[] thumbnail = fs.getThumbnail();
        int length = thumbnail != null ? thumbnail.length : 0;
        if (length > 0) {
            String[] urls = new String[length];
            for (int i = 0; i < length; i++) {
                urls[i] = FileUploadUtils.getUrl(thumbnail[i]);
            }
            us.setThumbnail(urls);
        }
        return us;
    }

    /**
     * 根据url获取对应的文件系列
     * 
     * @param url
     * @return
     */
    public static final FileSeries getFileSeries(String url) {
        File file = FileUploadUtils.getFileByUrl(url, true);
        return getFileSeries(file);
    }

    /**
     * 根据文件获取对应的文件系列
     * 
     * @param file
     * @return
     */
    public static final FileSeries getFileSeries(File file) {
        FileSeries fs = new FileSeries();
        if (file != null) {
            File thumbDir = null;
            if (isOriginal(file)) {
                // 用原始图 找 缩略图
                fs.setOriginal(file);
                thumbDir = getThumbnailDir(file);
            } else {
                // 用缩略图 找 原始图
                File original = getOriginalFile(file);
                fs.setOriginal(original);
                thumbDir = getThumbnailDir(original);
            }
            if (thumbDir.exists() && thumbDir.isDirectory()) {
                File[] list = thumbDir.listFiles(thumbnailFilter);
                fs.setThumbnail(list);
            }
        }
        return fs;
    }

    public static final boolean isOriginal(File file) {
        String baseName = FilenameUtils.getBaseName(file.getName());
        return baseName.length() == 32;
    }

    /**
     * 根据（上传）文件得到缩略图文件目录
     * 
     * @param file
     * @return
     */
    public static final File getThumbnailDir(File file) {
        String name = file.getName();
        String baseName = FilenameUtils.getBaseName(name);
        String extension = FilenameUtils.getExtension(name);
        File dir = new File(file.getParent() + "/" + baseName + THUMBNAIL_DIR_SEPARATOR + extension);
        return dir;
    }

    private static final File getOriginalFile(File file) {
        String parent = file.getParent();
        int at = parent.lastIndexOf(THUMBNAIL_DIR_SEPARATOR);
        if (at > 0) {
            File originalFile = new File(parent.substring(0, at) + "." + parent.substring(at + 1));
            if (originalFile.exists() && originalFile.isFile()) {
                return originalFile;
            }
        }
        return file;
    }

    private static Map<String, ThumbnailSize> getSizeConfig() {
        Map<String, ThumbnailSize> map = new HashMap<>();

        String imageStr = Global.getConfig("feather.upload.thumbnail." + IMAGE);
        ThumbnailSize imageSize = ThumbnailSize.parse(imageStr);
        if (imageSize != null) {
            map.put(IMAGE, imageSize);
        }

        String vedeoStr = Global.getConfig("feather.upload.thumbnail." + VIDEO);
        ThumbnailSize vedeoSize = ThumbnailSize.parse(vedeoStr);
        if (vedeoSize != null) {
            map.put(VIDEO, vedeoSize);
        }

        String pdfStr = Global.getConfig("feather.upload.thumbnail." + PDF);
        ThumbnailSize pdfSize = ThumbnailSize.parse(pdfStr);
        if (pdfSize != null) {
            map.put(PDF, pdfSize);
        }

        String pptStr = Global.getConfig("feather.upload.thumbnail." + PPT);
        ThumbnailSize pptSize = ThumbnailSize.parse(pptStr);
        if (pptSize != null) {
            map.put(PPT, pptSize);
        }

        return map;
    }

    public static class ThumbnailSize {
        public int width;
        public int height;

        public ThumbnailSize() {
        }

        public ThumbnailSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public static ThumbnailSize parse(String str) {
            ThumbnailSize size = null;
            if (str != null) {
                String[] arr = str.split(",");
                if (arr.length == 2) {
                    size = new ThumbnailSize(Integer.parseInt(arr[0], 10), Integer.parseInt(arr[1], 10));
                }
            }
            return size;
        }
    }
}
