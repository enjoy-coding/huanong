package com.feather.common.config;

import java.io.File;

/**
 * @author feather
 */
public interface Thumbnail {
    /**
     * 原始文件
     * 
     * @return
     */
    File getOriginal();

    /**
     * 缩略图宽
     * 
     * @return
     */
    int getWidth();

    void setWidth(int w);

    /**
     * 缩略图高
     * 
     * @return
     */
    int getHeight();

    void setHeight(int h);

    /**
     * 生成缩略图
     * 
     * @return 生成缩略图的数量
     */
    int of();

    /**
     * 得到生成的缩略图文件
     * 
     * @return 文件数组
     */
    File[] get();
}
