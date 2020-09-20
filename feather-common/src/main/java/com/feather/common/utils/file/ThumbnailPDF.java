package com.feather.common.utils.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ThumbnailPDF extends ThumbnailBase {

    private File dir;
    private File[] thumb;

    public ThumbnailPDF(File file) {
        super(file);
    }

    @Override
    public int of() {
        dir = getThumbnailDir();
        try {
            thumb = convert();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        int count = 0;
        if (thumb != null) {
            count = thumb.length;
        }
        return count;
    }

    @Override
    public File[] get() {
        return thumb;
    }

    public File[] convert() throws Exception {
        File[] picArray = null;
        String format = getFormat();
        try (InputStream inputStream = new FileInputStream(getOriginal())) {
            try (PDDocument doc = PDDocument.load(inputStream)) {
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                picArray = new File[pageCount];
                for (int i = 0; i < pageCount; i++) {
                    // Windows native DPI
                    BufferedImage image = renderer.renderImageWithDPI(i, 144);
                    String name = getFileSortName((i + 1), pageCount);
                    File pic = new File(dir + File.separator + name + "." + format);
                    ImageIO.write(image, format, pic);
                    picArray[i] = pic;
                }
            }
        }
        return picArray;
    }
}
