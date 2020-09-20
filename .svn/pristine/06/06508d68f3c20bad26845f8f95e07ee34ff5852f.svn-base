package com.feather.common.utils.file;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class ThumbnailPPT extends ThumbnailBase {
    private File dir;
    private File[] thumb;

    public ThumbnailPPT(File file) {
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

    private File[] convert() throws Exception {
        String extension = FilenameUtils.getExtension(getOriginal().getName());
        if ("ppt".equalsIgnoreCase(extension)) {
            return convert2003();
        }
        if ("pptx".equalsIgnoreCase(extension)) {
            return convert2007();
        }
        return null;
    }

    private File[] convert2003() throws Exception {
        String format = getFormat();
        File[] picArray = null;
        try (InputStream inputStream = new FileInputStream(this.getOriginal())) {
            try (HSLFSlideShowImpl slideShow = new HSLFSlideShowImpl(inputStream); //
                    HSLFSlideShow ppt = new HSLFSlideShow(slideShow)//
            ) {
                Dimension pgsize = ppt.getPageSize();
                int size = ppt.getSlides().size();
                picArray = new File[size];
                for (int i = 0; i < size; i++) {
                    // 防止中文乱码
                    for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                        if (shape instanceof HSLFTextShape) {
                            HSLFTextShape tsh = (HSLFTextShape) shape;
                            for (HSLFTextParagraph p : tsh) {
                                for (HSLFTextRun r : p) {
                                    r.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    // clear the drawing area
                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle.Float(0, 0, pgsize.width, pgsize.height));

                    // render
                    ppt.getSlides().get(i).draw(graphics);

                    // save the output
                    String name = getFileSortName((i + 1), size);
                    String picFile = dir + File.separator + name + "." + format;
                    picArray[i] = new File(picFile);
                    try (FileOutputStream out = new FileOutputStream(picFile)) {
                        ImageIO.write(img, format, out);
                    }
                }
            }
        }
        return picArray;
    }

    public File[] convert2007() throws Exception {
        String dir = getThumbnailDir().getPath();
        String format = getFormat();
        File[] picArray = null;
        try (InputStream inputStream = new FileInputStream(this.getOriginal())) {
            try (XMLSlideShow ppt = new XMLSlideShow(inputStream)) {
                Dimension pgsize = ppt.getPageSize();
                int size = ppt.getSlides().size();
                picArray = new File[size];
                for (int i = 0; i < size; i++) {
                    // 防止中文乱码
                    for (XSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                        if (shape instanceof XSLFTextShape) {
                            XSLFTextShape tsh = (XSLFTextShape) shape;
                            for (XSLFTextParagraph p : tsh) {
                                for (XSLFTextRun r : p) {
                                    r.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    // clear the drawing area
                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle.Float(0, 0, pgsize.width, pgsize.height));

                    // render
                    ppt.getSlides().get(i).draw(graphics);

                    // save the output
                    String name = getFileSortName((i + 1), size);
                    String picFile = dir + File.separator + name + "." + format;
                    picArray[i] = new File(picFile);
                    try (FileOutputStream out = new FileOutputStream(picFile)) {
                        javax.imageio.ImageIO.write(img, format, out);
                    }
                }
            }
        }
        return picArray;
    }
}
