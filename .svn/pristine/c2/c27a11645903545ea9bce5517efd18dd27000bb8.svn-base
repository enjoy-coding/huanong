package com.feather.common.utils.file;

import com.feather.common.constant.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailExcel extends ThumbnailBase {
    private File thumb;
    private boolean withStyle;// 是否需要表格样式 包含 字体 颜色 边框 对齐方式

    public ThumbnailExcel(File file) {
        super(file);
        withStyle = true;
    }

    public boolean isWithStyle() {
        return withStyle;
    }

    public void setWithStyle(boolean withStyle) {
        this.withStyle = withStyle;
    }

    @Override
    public int of() {
        thumb = new File(getThumbnailDir() + File.separator + "1.html");
        try {
            String html = getHtml();
            byte[] data = html.getBytes(Constants.UTF8);
            FileUtils.writeByteArrayToFile(thumb, data);
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
    public String getFormat() {
        return "html";
    }

    private String getHtml() throws Exception {
        try (InputStream inputStream = new FileInputStream(getOriginal())) {
            try (Workbook wb = WorkbookFactory.create(inputStream)) {
                StringBuffer sb = new StringBuffer();
                Sheet sheet = wb.getSheetAt(0);// 获取第一个Sheet的内容
                int lastRowNum = sheet.getLastRowNum();
                Map<String, String> map[] = getRowSpanColSpanMap(sheet);
                sb.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><table style='border-collapse:collapse;' width='100%'>");
                Row row = null; // 兼容
                Cell cell = null; // 兼容
                for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
                    row = sheet.getRow(rowNum);
                    if (row == null) {
                        sb.append("<tr><td > &nbsp;</td></tr>");
                        continue;
                    }
                    sb.append("<tr>");
                    int lastColNum = row.getLastCellNum();
                    for (int colNum = 0; colNum < lastColNum; colNum++) {
                        cell = row.getCell(colNum);
                        if (cell == null) { // 特殊情况 空白的单元格会返回null
                            sb.append("<td>&nbsp;</td>");
                            continue;
                        }
                        String stringValue = getCellValue(cell);
                        if (map[0].containsKey(rowNum + "," + colNum)) {
                            String pointString = map[0].get(rowNum + "," + colNum);
                            map[0].remove(rowNum + "," + colNum);
                            int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                            int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                            int rowSpan = bottomeRow - rowNum + 1;
                            int colSpan = bottomeCol - colNum + 1;
                            sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
                        } else if (map[1].containsKey(rowNum + "," + colNum)) {
                            map[1].remove(rowNum + "," + colNum);
                            continue;
                        } else {
                            sb.append("<td ");
                        }
                        // 判断是否需要样式
                        if (isWithStyle()) {
                            dealExcelStyle(wb, sheet, cell, sb);// 处理单元格样式
                        }
                        sb.append(">");
                        if (stringValue == null || "".equals(stringValue.trim())) {
                            sb.append(" &nbsp; ");
                        } else {
                            // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                            sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
                        }
                        sb.append("</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
                return sb.toString();
            }
        }
    }

    @SuppressWarnings({ "unchecked" })
    private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
        Map<String, String> map0 = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        return new Map[] { map0, map1 };
    }

    /**
     * 获取表格单元格Cell内容
     */
    @SuppressWarnings("deprecation")
    private static String getCellValue(Cell cell) {
        String result = new String();
        switch (cell.getCellTypeEnum()) {
        case NUMERIC:// 数字类型
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                SimpleDateFormat dateFormat = null;
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                    dateFormat = new SimpleDateFormat("HH:mm");
                } else {// 日期
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                }
                Date date = cell.getDateCellValue();
                result = dateFormat.format(date);
            } else if (cell.getCellStyle().getDataFormat() == 58) {
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                double value = cell.getNumericCellValue();
                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                result = dateFormat.format(date);
            } else {
                double value = cell.getNumericCellValue();
                CellStyle style = cell.getCellStyle();
                DecimalFormat decimalFormat = new DecimalFormat();
                String temp = style.getDataFormatString();
                // 单元格设置成常规
                if (temp.equals("General")) {
                    decimalFormat.applyPattern("#");
                }
                result = decimalFormat.format(value);
            }
            break;
        case STRING:// String类型
            result = cell.getRichStringCellValue().toString();
            break;
        case BLANK:
            result = "";
            break;
        default:
            result = "";
            break;
        }
        return result;
    }

    /**
     * 处理表格样式
     */
    @SuppressWarnings("deprecation")
    private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            HorizontalAlignment horizontalAlignment = cellStyle.getAlignmentEnum();
            sb.append("align='" + convertAlignToHtml(horizontalAlignment) + "' ");// 单元格内容的水平对齐方式
            VerticalAlignment verticalAlignment = cellStyle.getVerticalAlignmentEnum();
            sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");// 单元格中内容的垂直排列方式

            if (wb instanceof XSSFWorkbook) {
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                sb.append("style='");
                if (xf.getBold()) {
                    sb.append("font-weight: bold;"); // 字体加粗
                }
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");

                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    sb.append("color:#" + xc.getARGBHex().substring(2) + ";"); // 字体颜色
                }

                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                // System.out.println("************************************");
                // System.out.println("BackgroundColorColor:
                // "+cellStyle.getFillBackgroundColorColor());
                // System.out.println("ForegroundColor:
                // "+cellStyle.getFillForegroundColor());//0
                // System.out.println("BackgroundColorColor:
                // "+cellStyle.getFillBackgroundColorColor());
                // System.out.println("ForegroundColorColor:
                // "+cellStyle.getFillForegroundColorColor());
                // String bgColorStr = bgColor.getARGBHex();
                // System.out.println("bgColorStr: "+bgColorStr);
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";"); // 背景颜色
                }
                sb.append(getBorderStyle(0, cellStyle.getBorderTopEnum(),
                        ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1, cellStyle.getBorderRightEnum(),
                        ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                sb.append(getBorderStyle(2, cellStyle.getBorderBottomEnum(),
                        ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                sb.append(getBorderStyle(3, cellStyle.getBorderLeftEnum(),
                        ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

            } else if (wb instanceof HSSFWorkbook) {

                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short fontColor = hf.getColor();
                sb.append("style='");
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFColor hc = palette.getColor(fontColor);
                if (hf.getBold()) {
                    sb.append("font-weight: blod;"); // 字体加粗
                }
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    sb.append("color:" + fontColorStr + ";"); // 字体颜色
                }
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                }
                sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTopEnum(), cellStyle.getTopBorderColor()));
                sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRightEnum(), cellStyle.getRightBorderColor()));
                sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeftEnum(), cellStyle.getLeftBorderColor()));
                sb.append(
                        getBorderStyle(palette, 2, cellStyle.getBorderBottomEnum(), cellStyle.getBottomBorderColor()));
            }
            sb.append("' ");
        }
    }

    /**
     * 单元格内容的水平对齐方式
     */
    private static String convertAlignToHtml(HorizontalAlignment alignment) {
        String align = "left";
        switch (alignment) {
        case LEFT:
            align = "left";
            break;
        case CENTER:
            align = "center";
            break;
        case RIGHT:
            align = "right";
            break;
        default:
            break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     */
    private static String convertVerticalAlignToHtml(VerticalAlignment alignment) {

        String valign = "middle";
        switch (alignment) {
        case BOTTOM:
            valign = "bottom";
            break;
        case CENTER:
            valign = "center";
            break;
        case TOP:
            valign = "top";
            break;
        default:
            break;
        }
        return valign;
    }

    private static String convertToStardColor(HSSFColor hc) {
        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.HSSFColorPredefined.AUTOMATIC.getIndex() == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }

    private static String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    static String[] bordesr = { "border-top:", "border-right:", "border-bottom:", "border-left:" };
    static String[] borderStyles = { "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ",
            "solid ", "solid", "solid", "solid", "solid", "solid" };

    private static String getBorderStyle(HSSFPalette palette, int index, BorderStyle style, short t) {
        int code = (int) style.getCode();
        if (code == 0) {
            return bordesr[index] + borderStyles[code] + "#d0d7e5 1px;";
        }
        String borderColorStr = convertToStardColor(palette.getColor(t));
        borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
        return bordesr[index] + borderStyles[code] + borderColorStr + " 1px;";
    }

    private static String getBorderStyle(int index, BorderStyle style, XSSFColor xc) {
        int code = (int) style.getCode();
        if (code == 0) {
            return bordesr[index] + borderStyles[code] + "#d0d7e5 1px;";
        }
        if (xc != null && !"".equals(xc)) {
            String borderColorStr = xc.getARGBHex();// t.getARGBHex();
            borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000"
                    : borderColorStr.substring(2);
            return bordesr[index] + borderStyles[code] + borderColorStr + " 1px;";
        }
        return "";
    }
}
