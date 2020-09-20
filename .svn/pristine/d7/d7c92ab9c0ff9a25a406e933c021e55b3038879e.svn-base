package com.feather.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * App版本Controller
 * 
 * @author flogyin
 * @date 2019-09-25
 */
public class ZXingQrCode {
    private int width = 330;
    private int height = 330;
    private int color = 0xFF000000; // 默认是黑色
    private int bgColor = 0xFFFFFFFF; // 背景颜色
    private String content;
    private String label;
    private File logo;
    private Map<EncodeHintType, Object> hints;

    public ZXingQrCode() {
        this.hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        this.hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        this.hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        this.hints.put(EncodeHintType.MARGIN, 0);
        // this.hints.put(EncodeHintType.MAX_SIZE, 350);
        // this.hints.put(EncodeHintType.MIN_SIZE, 100);
    }

    public static void main(String[] args) throws WriterException {
        ZXingQrCode zXingCode = new ZXingQrCode();
        zXingCode.setContent("https://www.baidu.com/");
        // zXingCode.setLabel("跳转到百度的二维码");
        zXingCode.setLogo(new File("D:/hxdz.png"));
        try {
            zXingCode.create(new File("D:\\test1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String createBase64() throws IOException, WriterException {
        BufferedImage qrImage = createQrImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(qrImage, "png", baos);
            return Base64.encodeBase64URLSafeString(baos.toByteArray());
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                baos.close();
            } catch (Exception ex) {
            }
        }
    }

    public void create(File outFile) throws IOException, WriterException {
        BufferedImage qrImage = createQrImage();
        ImageIO.write(qrImage, "png", outFile);
    }

    public void create(OutputStream outStream) throws IOException, WriterException {
        BufferedImage qrImage = createQrImage();
        ImageIO.write(qrImage, "png", outStream);
        outStream.flush();
    }

    protected BufferedImage createQrImage() throws IOException, WriterException {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        MultiFormatWriter writer = new MultiFormatWriter();
        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix matrix = writer.encode(this.content, BarcodeFormat.QR_CODE, this.width, this.height, this.hints);
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        BufferedImage qrImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++)
                qrImage.setRGB(x, y, matrix.get(x, y) ? this.color : this.bgColor);
        }

        Graphics2D g = qrImage.createGraphics();

        /**
         * 读取Logo图片
         */
        if (this.logo != null && this.logo.exists() && this.logo.isFile()) {
            BufferedImage logoImage = ImageIO.read(this.logo);
            /**
             * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
             */
            int widthLogo = logoImage.getWidth(null) > qrImage.getWidth() * 3 / 10 ? (qrImage.getWidth() * 3 / 10)
                    : logoImage.getWidth(null),
                    heightLogo = logoImage.getHeight(null) > qrImage.getHeight() * 3 / 10
                            ? (qrImage.getHeight() * 3 / 10) : logoImage.getWidth(null);

            /**
             * logo放在中心
             */
            int x = (qrImage.getWidth() - widthLogo) / 2;
            int y = (qrImage.getHeight() - heightLogo) / 2;
            /**
             * logo放在右下角 int x = (image.getWidth() - widthLogo); int y =
             * (image.getHeight() - heightLogo);
             */

            // 开始绘制图片
            g.drawImage(logoImage, x, y, widthLogo, heightLogo, null);
            // g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            // g.setStroke(new BasicStroke(logoConfig.getBorder()));
            // g.setColor(logoConfig.getBorderColor());
            // g.drawRect(x, y, widthLogo, heightLogo);
            logoImage.flush();
        }
        g.dispose();

        // 把商品名称添加上去，商品名称不要太长哦，这里最多支持两行。太长就会自动截取啦
        if (this.label != null && this.label.length() > 0) {
            // 新的图片，把带logo的二维码下面加上文字
            BufferedImage outImage = new BufferedImage(this.width, this.height + 20, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            // 画二维码到新的面板
            outg.drawImage(qrImage, 0, 0, qrImage.getWidth(), qrImage.getHeight(), null);
            // 画文字到新的面板
            outg.setColor(Color.BLACK);
            outg.setFont(new Font("宋体", Font.BOLD, 26)); // 字体、字型、字号
            int strWidth = outg.getFontMetrics().stringWidth(this.label);
            if (strWidth >= this.width) {
                // 长度过长就截取前面部分
                // outg.drawString(productName, 0, image.getHeight() +
                // (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
                // 长度过长就换行
                String productName1 = this.label.substring(0, this.label.length() / 2);
                String productName2 = this.label.substring(this.label.length() / 2, this.label.length());
                int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                outg.drawString(productName1, this.width / 2 - strWidth1 / 2,
                        qrImage.getHeight() + (outImage.getHeight() - qrImage.getHeight()) / 2 + 6);
                BufferedImage outImage2 = new BufferedImage(this.width, this.height + 80,
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg2 = outImage2.createGraphics();
                outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                outg2.setColor(Color.BLACK);
                outg2.setFont(new Font("宋体", Font.BOLD, 30)); // 字体、字型、字号
                outg2.drawString(productName2, this.width / 2 - strWidth2 / 2,
                        outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 6);
                outg2.dispose();
                outImage2.flush();
                outImage = outImage2;
            } else {
                outg.drawString(this.label, this.width / 2 - strWidth / 2,
                        qrImage.getHeight() + (outImage.getHeight() - qrImage.getHeight()) / 2 + 6); // 画文字
            }
            outg.dispose();
            outImage.flush();
            qrImage = outImage;
        }

        qrImage.flush();
        return qrImage;
    }
}
