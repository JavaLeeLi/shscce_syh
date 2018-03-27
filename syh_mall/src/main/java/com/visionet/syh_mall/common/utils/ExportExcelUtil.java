package com.visionet.syh_mall.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.visionet.syh_mall.common.utils.image.DeleteUtil;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;

public class ExportExcelUtil {
	
	/**
     * 导出excel头部标题
     * @param title
     * @param cellRangeAddressLength
     * @return
     */
	/* public static HSSFWorkbook makeExcelHead(String title, int cellRangeAddressLength){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)16);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
        sheet.addMergedRegion(cellRangeAddress);
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(0);
        // 为标题设置背景颜色
        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellTitle.setCellValue(title);
        cellTitle.setCellStyle(styleTitle);
        return workbook;
    }*/
    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeSecondHead(String[] secondTitles){
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet("sheet");
    	sheet.setDefaultColumnWidth(15);
        // 创建用户属性栏
        HSSFRow rowField = sheet.createRow(0);
        HSSFCellStyle styleField = createStyle(workbook, (short)11);        
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);
        }
        return workbook;
    }
    
    /**
     * 鉴评专用设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeEvaluationSecondHead(String[] secondTitles){
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet("sheet");
    	sheet.setDefaultColumnWidth(15);
    	sheet.setDefaultRowHeightInPoints(90);
    	//sheet.setDefaultRowHeight((short)());
    	
        // 创建用户属性栏
        HSSFRow rowField = sheet.createRow(0);
        HSSFCellStyle styleField = createStyle(workbook, (short)11);        
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);            
        }
        return workbook;
    }
    
    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HorizontalAlignment.CENTER);
        styleData.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 1);
            T t = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++) {
                Object value = BeanUtils.getBeanProperty(t, beanPropertys[k]);
                HSSFCell cellData = rowData.createCell(k);
                if ("".equals(value) || value == null) {
                	cellData.setCellValue("");
                } else {
                	cellData.setCellValue(value.toString());
                }
                cellData.setCellStyle(styleData);
            }
        }
        return workbook;
    }

    /**
     * 鉴评专用导入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     * @throws IOException 
     */
    public static <T> HSSFWorkbook exportEvaluationExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) throws IOException {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HorizontalAlignment.CENTER);
        styleData.setVerticalAlignment(VerticalAlignment.CENTER);
        styleData.setWrapText(true);
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        styleData.setDataFormat(dataFormat.getFormat("@"));

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 1);
            rowData.setHeightInPoints(90);
            T t = dataList.get(j);
            List<String> list = null;
            for(int k=0; k<beanPropertys.length; k++) {
                list = new ArrayList<String>();
            	Object value = BeanUtils.getBeanProperty(t, beanPropertys[k]);
                HSSFCell cellData = rowData.createCell(k);
                if ("".equals(value) || value == null) {
                	cellData.setCellValue("");
                } else if (k==beanPropertys.length-1) {
                	String QRCode = value.toString();
                	String path = QRCodeUtil.QRcode(BeanUtils.getBeanProperty(t, beanPropertys[0]).toString(),QRCode);
                	QRCodeUtil.QRinsertExcel(workbook, sheet, path, j, k);
                	list.add(path);
                	DeleteUtil.deleteRun(list);
                } else {
                	cellData.setCellValue(value.toString());                	
                }
                cellData.setCellStyle(styleData);
            }            
        }
        return workbook;
    }
    
    /**
     * 提取公共的样式
     * @param workbook
     * @param fontSize
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();        
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    
}
