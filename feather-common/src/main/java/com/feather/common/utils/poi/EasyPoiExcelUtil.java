package com.feather.common.utils.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.feather.common.config.Global;
import com.feather.common.exception.BusinessException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * easypoi导出excel
 */
public class EasyPoiExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 导入导出数据列表
     */
    private List<T> list;
    /**
     * 实体对象
     */
    public Class<T> clazz;

    public EasyPoiExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }


    /**
     * excelpoi导出
     * @param title 标题
     * @param sheetName sheet
     * @param list 数据源
     * @return
     */
    public  void exportExecl(String title, String sheetName,List<T> list){
        FileOutputStream fos = null;
        Workbook wb = null;
        try{
            wb = ExcelExportUtil.exportExcel(new ExportParams(title,sheetName),clazz,list);
            String filename = encodingFilename(sheetName);
            fos = new FileOutputStream("D:/excel/"+filename);
            wb.write(fos);
        }catch (Exception o){
            log.error("导出Excel异常{}", o.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取下载路径
     *
     * @param filename
     *            文件名称
     */
    public String getAbsoluteFile(String filename) {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }
}
