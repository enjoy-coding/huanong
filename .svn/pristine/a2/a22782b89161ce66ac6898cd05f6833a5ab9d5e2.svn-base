package com.feather.common.utils.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class ThumbnailVideo extends ThumbnailBase {
    /**
     * 抓取帧
     */
    public static final int DEFAULT_FRAME = 6;

    /**
     * 过滤帧数，避免出现全黑的图片
     */
    private int frame;
    private File thumb;

    public ThumbnailVideo(File file) {
        super(file);
        frame = DEFAULT_FRAME;
    }

    @Override
    public int of() {
        thumb = new File(getThumbnailDir() + File.separator + "1." + getFormat());
        try {
            fetch();
            // ff();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return 1;
    }

    @Override
    public File[] get() {
        return new File[] { thumb };
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * 获取视频的特定帧并保存为图片
     * 
     * @throws Exception
     */
    private void fetch() throws Exception {
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(getOriginal())) {
            ff.start();
            int lenght = ff.getLengthInFrames();

            Frame frame = null;
            for (int i = 0; i < lenght; i++) {
                frame = ff.grabFrame();
                if ((i > 5) && (frame.image != null)) {
                    break;
                }
            }

            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcBi = converter.getBufferedImage(frame);
            int owidth = srcBi.getWidth();
            int oheight = srcBi.getHeight();
            int width = owidth;
            int height = oheight;
            if (width > getWidth() || height > getHeight()) {
                // 等比例缩放
                width = getWidth();
                height = (int) (((double) width / owidth) * oheight);
                if (height > getHeight()) {
                    height = getHeight();
                    width = (int) (((double) height / oheight) * owidth);
                }
            }
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(bi, getFormat(), thumb);
        }
    }

    /**
     * 获取视频时长，单位为秒
     * 
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file) {
        Long times = 0L;
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file)) {
            ff.start();
            times = ff.getLengthInTime() / (1000 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
