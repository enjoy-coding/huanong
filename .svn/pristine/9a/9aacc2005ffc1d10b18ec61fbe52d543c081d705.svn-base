package com.feather.aupipes.utils;

import com.feather.aupipes.domain.AupInspectDetail;
import com.feather.aupipes.utils.vo.AupInspectDetailVo;
import com.feather.aupipes.utils.vo.AupInspectServiceVo;
import com.feather.aupipes.utils.vo.AupInspectVo;
import com.feather.common.annotation.Excel;
import com.feather.common.annotation.Excel.ColumnType;
import com.feather.common.annotation.Excel.Type;
import com.feather.common.config.Global;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.exception.BusinessException;
import com.feather.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Excel相关处理
 *
 * @author feather
 */
public class InspectExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(InspectExcelUtil.class);

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    private List<AupInspectVo> aupInspectVoList;

    private AupInspectDetailVo aupInspectDetailVo;

    public void init(List<AupInspectVo> aupInspectVoList, String sheetName) {
        this.aupInspectVoList = aupInspectVoList;
        this.sheetName = sheetName;
        this.wb = new SXSSFWorkbook(500);

        createInspectExcelField();
    }

    public void init(AupInspectDetailVo aupInspectDetailVo, String sheetName) {
        this.aupInspectDetailVo = aupInspectDetailVo;
        this.sheetName = sheetName;
        this.wb = new SXSSFWorkbook(500);

        createDetailExcelField();
    }

    public AjaxResult exportInspectExcel(List<AupInspectVo> aupInspectVoList, String sheetName) {
        this.init(aupInspectVoList, sheetName);
        return exportInspectExcel();
    }

    public AjaxResult exportDetailExcel(AupInspectDetailVo aupInspectDetailVo, String sheetName) {
        this.init(aupInspectDetailVo, sheetName);
        return exportDetailExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public AjaxResult exportInspectExcel() {
        OutputStream out = null;
        try {
            this.sheet = wb.createSheet();
            this.styles = createStyles(wb);
            // 设置工作表的名称.
            wb.setSheetName(0, sheetName);


            // 产生一行
            Row row = sheet.createRow(0);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : fields) {
                Excel excel = (Excel) os[1];
                this.createCell(excel, row, column++);
            }

            fillInspectExcelData(0, row);

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public AjaxResult exportDetailExcel() {
        OutputStream out = null;
        try {
            this.sheet = wb.createSheet();
            this.styles = createStyles(wb);
            // 设置工作表的名称.
            wb.setSheetName(0, sheetName);


            // 产生一行
            Row row = sheet.createRow(0);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : fields) {
                Excel excel = (Excel) os[1];
                this.createCell(excel, row, column++);
            }

            fillDetailExcelData(0, row);

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     */
    public void fillInspectExcelData(int index, Row row) {
        //接下来需要封装三级list

        row = sheet.createRow( 1);
        for(int i = 0 ; i < aupInspectVoList.size(); i++){
            AupInspectVo aupInspectVo = aupInspectVoList.get(i);
            List<String> firstList = new ArrayList<>();
            firstList.add(aupInspectVo.getInspectName());
            firstList.add(aupInspectVo.getInspectArea());
            firstList.add(aupInspectVo.getDistributeMan());
            firstList.add(aupInspectVo.getInspectWorker());
            firstList.add(aupInspectVo.getDistributeTime());
            firstList.add(aupInspectVo.getStartTime());
            firstList.add(aupInspectVo.getEndTime());
            firstList.add(aupInspectVo.getInspectStatus());

            if(i != 0){//当巡检任务不为0，要新建一行
                row = sheet.createRow( row.getRowNum() + 1);
            }

            for(int j = 0; j < firstList.size(); j++){//列循环
                //在这里进行填充单元格的逻辑
                Cell cell = row.createCell(j);
                cell.setCellValue(firstList.get(j));
            }
            List<AupInspectDetailVo> aupInspectDetailVoList = aupInspectVo.getAupInspectDetailVoList();

            for(int j = 0 ; j < aupInspectDetailVoList.size(); j++){

                AupInspectDetailVo aupInspectDetailVo = aupInspectDetailVoList.get(j);
                List<String> secondList = new ArrayList<>();
                secondList.add(aupInspectDetailVo.getInspectAddress());
                secondList.add(aupInspectDetailVo.getAddressType());
                secondList.add(aupInspectDetailVo.getInspectTime());
                secondList.add(aupInspectDetailVo.getStatus());

                if(j != 0){//当巡检记录不为0，要新建一行
                    row = sheet.createRow( row.getRowNum() + 1);
                }

                for(int k = 0; k < secondList.size(); k++){
                    //在这里进行填充单元格的逻辑
                    Cell cell = row.createCell(firstList.size() + k);
                    cell.setCellValue(secondList.get(k));
                }

                List<AupInspectServiceVo> aupInspectServiceVoList = aupInspectDetailVo.getAupInspectServiceVoList();

                for(int k = 0; k < aupInspectServiceVoList.size(); k++){
                    AupInspectServiceVo aupInspectServiceVo = aupInspectServiceVoList.get(k);
                    List<String> thirdList = new ArrayList<>();
                    thirdList.add(aupInspectServiceVo.getServiceName());
                    thirdList.add(aupInspectServiceVo.getServiceStatus());
                    thirdList.add(aupInspectServiceVo.getServiceSituation());

                    if(k != 0){//当巡检记录不为0，要新建一行
                        row = sheet.createRow( row.getRowNum() + 1);
                    }

                    for(int l = 0; l < thirdList.size(); l++){
                        //在这里进行填充单元格的逻辑
                        Cell cell = row.createCell(firstList.size() + secondList.size() + l);
                        cell.setCellValue(thirdList.get(l));
                    }
                }
            }

        }

        MergedCell();
    }

    private void MergedCell(){

        Field[] firstProperty = AupInspectVo.class.getDeclaredFields();
        Field[] secondProperty = AupInspectDetailVo.class.getDeclaredFields();
        int firstProLength = firstProperty.length - 1;//用于第一层对象列表循环
        int secondProLength = secondProperty.length - 1;//用于第二层对象列表循环

        int rowNum = 1;
        for(int i = 0 ; i < aupInspectVoList.size(); i++){

            AupInspectVo aupInspectVo = aupInspectVoList.get(i);

            List<AupInspectDetailVo> aupInspectDetailVoList = aupInspectVo.getAupInspectDetailVoList();

            if(aupInspectDetailVoList.size() == 0){//如果第二层对象列表为空，则行号加1，直接进入下一层循环
                rowNum += 1;
                continue;
            }

//            int totalRowSize = 0;
            int startRow = rowNum;//记录行起点
            for(int j = 0; j < aupInspectDetailVoList.size(); j++){
                AupInspectDetailVo aupInspectDetailVo = aupInspectDetailVoList.get(j);

                List<AupInspectServiceVo> aupInspectServiceVoList = aupInspectDetailVo.getAupInspectServiceVoList();

                int rowSize = aupInspectServiceVoList.size() > 1 ? aupInspectServiceVoList.size() : 1;//获取三级对象所占的行数，若为空也要占一行
//                totalRowSize += rowSize;
                if(rowSize == 1){
                    rowNum += rowSize;
                    continue;
                }

                for(int k = 0; k < secondProLength; k++){//对第二层对象的列数进行遍历，合并几行由三级对象的行数确定

                    CellRangeAddress region = new CellRangeAddress(rowNum, rowNum + rowSize - 1,
                            firstProLength + k, firstProLength + k);
                    sheet.addMergedRegion(region);
                }
                rowNum += rowSize;//为下一层循环的开始往下挪一行
            }

            for(int j = 0; j < firstProLength; j++){//对第一层的列数进行遍历，合并几行由二级对象所占的总行数确定

                CellRangeAddress region = new CellRangeAddress(startRow, rowNum - 1,
                        j, j);//这里之所以减1，是因为上面+rowSize的时候多加了自身那一行，所以需要减1
                sheet.addMergedRegion(region);
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     */
    public void fillDetailExcelData(int index, Row row) {
        List<String> list = new ArrayList<>();
        list.add(aupInspectDetailVo.getInspectAddress());
        list.add(aupInspectDetailVo.getAddressType());
        list.add(aupInspectDetailVo.getInspectTime());
        list.add(aupInspectDetailVo.getStatus());

        List<AupInspectServiceVo> aupInspectServiceVoList = aupInspectDetailVo.getAupInspectServiceVoList();

        List<List<String>> secondList = new ArrayList<>();
        for(int i = 0; i < aupInspectServiceVoList.size(); i++){
            AupInspectServiceVo aupInspectServiceVo = aupInspectServiceVoList.get(i);

            List<String> secondValuelist = new ArrayList<>();
            secondValuelist.add(aupInspectServiceVo.getServiceName());
            secondValuelist.add(aupInspectServiceVo.getServiceStatus());
            secondValuelist.add(aupInspectServiceVo.getServiceSituation());

            secondList.add(secondValuelist);
        }

        row = sheet.createRow(1);
        // 得到导出对象.

        int firstLength = list.size();
        for(int i = 0 ; i < firstLength; i++){//开始填充数据，往第一列填充数据

            Cell cell = row.createCell((short) i);
            cell.setCellValue(list.get(i));
        }

        for(int i = 0; i < secondList.size(); i++){
            if(i != 0){
                row = sheet.createRow(i + 1);
            }
            for(int j = 0; j < secondList.get(i).size(); j++){
                Cell cell = row.createCell(j+firstLength);
                cell.setCellValue(secondList.get(i).get(j));
            }

        }

    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(styles.get("header"));
        return cell;
    }

    /**
     * 设置单元格信息
     *
     * @param value 单元格值
     * @param attr  注解相关
     * @param cell  单元格信息
     */
    @SuppressWarnings("deprecation")
    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (ColumnType.STRING == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(value == null ? attr.defaultValue() : value + attr.suffix());
        } else if (ColumnType.NUMERIC == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Long.parseLong(value + ""));
        }
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column) {
        if (attr.name().indexOf("注：") >= 0) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, AupInspectDetailVo vo, Field field, int column) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight((short) (attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(styles.get("data"));

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);

                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(value == null ? attr.defaultValue() : value + attr.suffix());
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    public Cell addSecondCell(Excel attr, Row row, AupInspectServiceVo vo, Field field, int column) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight((short) (attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(styles.get("data"));

                // 用于读取对象中的属性
                Object value = getSecondTargetValue(vo, field, attr);

                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(value == null ? attr.defaultValue() : value + attr.suffix());
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
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
     * 获取bean中的属性值
     *
     * @param vo    实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(AupInspectDetailVo vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.indexOf(".") > -1) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    private Object getSecondTargetValue(AupInspectServiceVo vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.indexOf(".") > -1) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到巡检记录所有定义字段
     */
    private void createInspectExcelField() {
        this.fields = new ArrayList<Object[]>();

        Class clazz0 = AupInspectVo.class;
        Class clazz1 = AupInspectDetailVo.class;
        Class clazz2 = AupInspectServiceVo.class;

        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz0.getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz1.getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz2.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }
        }
    }

    /**
     * 得到巡检记录所有定义字段
     */
    private void createDetailExcelField() {
        this.fields = new ArrayList<Object[]>();

        Class clazz1 = AupInspectDetailVo.class;
        Class clazz2 = AupInspectServiceVo.class;

        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz1.getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz2.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }
        }
    }

    /**
     * 放到字段集合中
     */
    private void putToField(Field field, Excel attr) {
        if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
            this.fields.add(new Object[]{field, attr});
        }
    }

}