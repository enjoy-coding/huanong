package com.feather.common.utils.file;

import java.io.File;

import com.feather.common.config.Thumbnail;

public abstract class ThumbnailBase implements Thumbnail {
    public static final String DEFAULT_FORMAT = "jpg";

    private File original;
    private int width;
    private int height;
    private String format;
    private File thumbnailDir;

    public ThumbnailBase(File file) {
        original = file;
        format = DEFAULT_FORMAT;

        thumbnailDir = ThumbnailFactory.getThumbnailDir(file);
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }
    }

    @Override
    public File getOriginal() {
        return original;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int w) {
        width = w;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int h) {
        height = h;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    protected File getThumbnailDir() {
        return thumbnailDir;
    }

    protected String getFileSortName(int index, int total) {
        int places = String.valueOf(total).length();
        return String.format("%0" + places + "d", index);
    }
}
