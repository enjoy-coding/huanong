package com.feather.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;

public class ThumbnailWord extends ThumbnailBase implements PicturesManager {
    private static final String IMG_FOLDER = "img";

    private File dir;
    private File imgDir;
    private File thumb;

    public ThumbnailWord(File file) {
        super(file);
    }

    @Override
    public int of() {
        dir = getThumbnailDir();
        imgDir = new File(dir + File.separator + IMG_FOLDER);
        thumb = new File(dir + File.separator + "1.html");
        try {
            convert();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return 1;
    }

    @Override
    public File[] get() {
        return new File[] { thumb };
    }

    @Override
    public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
            float heightInches) {
        File file = new File(imgDir + File.separator + suggestedName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(content);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // 这里可以指定word文档中图片的路径
        return IMG_FOLDER + "/" + suggestedName;
    }

    private void convert() throws Exception {
        String extension = FilenameUtils.getExtension(getOriginal().getName());
        if ("doc".equalsIgnoreCase(extension)) {
            imgDir.mkdir();
            convert2003();
        }
        if ("docx".equalsIgnoreCase(extension)) {
            convert2007();
        }
    }

    private void convert2003() throws Exception {
        try (InputStream inputStream = new FileInputStream(getOriginal())) {
            try (OutputStream outputStream = new FileOutputStream(thumb)) {
                // 加载word文档生成 HWPFDocument对象
                HWPFDocument wordDocument = new HWPFDocument(inputStream);
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                        DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                // 设置图片存放的位置
                wordToHtmlConverter.setPicturesManager(this);
                // 解析word文档
                wordToHtmlConverter.processDocument(wordDocument);
                Document htmlDocument = wordToHtmlConverter.getDocument();

                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outputStream);

                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer serializer = factory.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void convert2007() throws Exception {
        try (InputStream inputStream = new FileInputStream(getOriginal())) {
            try (OutputStream outputStream = new FileOutputStream(thumb)) {
                // 加载word文档生成 XWPFDocument对象
                XWPFDocument document = new XWPFDocument(inputStream);
                // 解析 XHTML配置 (URIResolver来设置图片存放的目录)
                XHTMLOptions options = XHTMLOptions.create();
                FileImageExtractor extractor = new FileImageExtractor(dir);
                options.setExtractor(extractor);
                // 将 XWPFDocument转换成XHTML
                XHTMLConverter.getInstance().convert(document, outputStream, options);
            }
        }
    }
}
