package com.feather.common.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.config.Global;
import com.feather.common.config.Thumbnail;
import com.feather.common.constant.Constants;
import com.feather.common.exception.file.FileException;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.security.Md5Utils;

/**
 * 文件上传工具类
 *
 * @author feather
 */
public class FileUploadUtils {
    private static long MAX_SIZE = getMaxSize();
    private static int MAX_NAME_LENGTH = getMaxNameLength();
    private static String[] NOT_ALLOW_EXTENSION = getNotAllowExtension();

    private static int counter = 0;

    /**
     * @param file
     *            上传的文件
     * @param url
     *            上传的文件要对应的url
     * @param thumb
     *            上传的文件是否要生成缩略图
     * @return 如生成缩略图，返回上传成功的第一个缩略图的文件名，否则返回原始图的文件名
     * @throws IOException
     */
    public static final String upload(File file, String url, boolean thumb) throws IOException {
        assertFile(file.length(), file.getName().length(), FilenameUtils.getExtension(file.getName()));

        File desc = getFileByUrl(url, true);
        if (desc == null) {
            String baseDir = Global.getUploadPath();
            String fileName = extractFilename(file);
            desc = getAbsoluteFile(baseDir, fileName);
            url = getUrl(baseDir, fileName);
        }
        FileUtils.copyFile(file, desc);
        if (thumb) {
            String[] names = ofThumbnail(desc);
            url = names[0];
        }
        return url;
    }

    /**
     * @param content
     *            上传的内容
     * @param extension
     *            上传的文件后缀
     * @param url
     *            上传的文件要对应的url
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String content, String extension, String url) throws IOException {
        byte[] bytes = content.getBytes(Constants.UTF8);
        return upload(bytes, extension, url);
    }

    /**
     * @param bytes
     *            上传的内容
     * @param extension
     *            上传的文件后缀
     * @param url
     *            上传的文件要对应的url
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(byte[] bytes, String extension, String url) throws IOException {
        File desc = getFileByUrl(url, true);
        if (desc == null) {
            String baseDir = Global.getUploadPath();
            String fileName = extractFilename("", extension);
            desc = getAbsoluteFile(baseDir, fileName);
            url = getUrl(baseDir, fileName);
        }
        try (FileOutputStream fos = new FileOutputStream(desc)) {
            fos.write(bytes);
        }
        return url;
    }

    /**
     * 文件上传
     *
     * @param file
     *            上传的文件
     * @param url
     *            上传的文件要对应的url
     * @param thumb
     *            上传的文件是否要生成缩略图
     * @return 如生成缩略图，返回上传成功的第一个缩略图的文件名，否则返回原始图的文件名
     * @throws FileException
     *             文件超大/文件名太长
     * @throws IOException
     *             比如读写文件出错时
     */
    public static final String upload(MultipartFile file, String url, boolean thumb) throws IOException {
        assertFile(file.getSize(), file.getOriginalFilename().length(), getExtension(file));

        File desc = getFileByUrl(url, true);
        if (desc == null) {
            String fileName = extractFilename(file);
            String baseDir = Global.getUploadPath();
            desc = getAbsoluteFile(baseDir, fileName);
            url = getUrl(baseDir, fileName);
        }

        file.transferTo(desc);
        if (thumb) {
            String[] names = ofThumbnail(desc);
            url = names[0];
        }
        return url;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        return extractFilename(fileName, extension);
    }

    public static final String extractFilename(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return extractFilename(fileName, extension);
    }

    public static final String extractFilename(File file) {
        String fileName = file.getName();
        String extension = FilenameUtils.getExtension(file.getName());
        return extractFilename(fileName, extension);
    }

    public static final String extractFilename(String fileName, String extension) {
        fileName = DateUtils.datePath() + "/" + encodingFilename(fileName)
                + (StringUtils.isNotEmpty(extension) ? ("." + extension) : "");
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    public static final String getUrl(File file) {
        String localPath = new File(Global.getProfile()).getPath();
        String url = StringUtils.substringAfter(file.getPath(), localPath);
        url = Constants.RESOURCE_PREFIX + StringUtils.replace(url, "\\", "/");
        return url;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file
     *            表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    /**
     * 根据url获取本地文件
     *
     * @param url
     * @param mustExists
     *            是否检查必须存在，如果不存在则返回空
     * @return
     */
    public static final File getFileByUrl(String url, boolean mustExists) {
        File desc = null;
        if (StringUtils.isNotEmpty(url)) {
            String localPath = Global.getProfile();
            String downloadPath = localPath + StringUtils.substringAfter(url, Constants.RESOURCE_PREFIX);
            File file = new File(downloadPath);
            if (!mustExists || (mustExists && file.exists() && file.isFile())) {
                desc = file;
            }
        }
        return desc;
    }

    private static final long getMaxSize() {
        String str = Global.getConfig("spring.servlet.multipart.max-file-size");
        if (StringUtils.isNotEmpty(str)) {
            str = str.toUpperCase();
            if (str.endsWith("KB")) {
                long size = Long.parseLong(str.substring(0, str.length() - 2), 10);
                return size * 1024L;
            } else if (str.endsWith("MB")) {
                long size = Long.parseLong(str.substring(0, str.length() - 2), 10);
                return size * 1024L * 1024L;
            } else if (str.endsWith("GB")) {
                long size = Long.parseLong(str.substring(0, str.length() - 2), 10);
                return size * 1024L * 1024L * 1024L;
            }
            return Long.parseLong(str, 10);
        }
        return 0L;
    }

    private static final int getMaxNameLength() {
        String str = Global.getConfig("feather.upload.maxFileNameLength");
        if (StringUtils.isNotEmpty(str)) {
            return Integer.parseInt(str, 10);
        }
        return 0;
    }

    private static final String[] getNotAllowExtension() {
        String str = Global.getConfig("feather.upload.notAllowExtension");
        if (StringUtils.isNotEmpty(str)) {
            String[] arr = str.split(",");
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].trim().toLowerCase();
            }
            return arr;
        }
        return null;
    }

    private static final void assertFile(long size, int nameLength, String extension) throws FileException {
        if (MAX_SIZE != -1L && size > MAX_SIZE) {
            throw new FileException("upload.exceed.maxSize", new Object[] { MAX_SIZE / 1024L / 1024L });
        }
        if (nameLength > FileUploadUtils.MAX_NAME_LENGTH) {
            throw new FileException("upload.filename.exceed.length", new Object[] { MAX_NAME_LENGTH });
        }
        int length = NOT_ALLOW_EXTENSION != null ? NOT_ALLOW_EXTENSION.length : 0;
        for (int i = 0; i < length; i++) {
            if (NOT_ALLOW_EXTENSION[i].equalsIgnoreCase(extension)) {
                throw new FileException("upload.extension.notAllow", new String[] { extension });
            }
        }
    }

    private static final String getUrl(String uploadDir, String fileName) {
        int dirLastIndex = uploadDir.lastIndexOf("/") + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String url = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return url;
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String fileName) {
        fileName = fileName.replace("_", " ");
        fileName = Md5Utils.hash(fileName + System.nanoTime() + counter++);
        return fileName;
    }

    private static String[] ofThumbnail(File desc) {
        Thumbnail thumbnail = ThumbnailFactory.newThumbnail(desc);
        if (thumbnail == null) {
            throw new RuntimeException("不支持生成缩略图的文件类型");
        }
        thumbnail.of();
        File[] files = thumbnail.get();
        String[] urls = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = FileUploadUtils.getUrl(files[i]);
        }
        return urls;
    }
}