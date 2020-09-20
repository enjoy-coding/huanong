package com.feather.common.utils.file;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailPicture extends ThumbnailBase {
    private File thumb;

    public ThumbnailPicture(File file) {
        super(file);
    }

    @Override
    public int of() {
        String format = getFormat();
        thumb = new File(getThumbnailDir() + File.separator + "1." + format);
        try {
            Thumbnails.of(getOriginal()).size(getWidth(), getHeight()).toFile(thumb);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return 1;
    }

    @Override
    public File[] get() {
        return new File[] { thumb };
    }
}
