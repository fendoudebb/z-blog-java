package com.msj.blog.util;

import com.msj.blog.entity.dto.excel.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * zbj: create on 2018/08/08 15:35
 */
@Component
public class ExcelUtil {

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public ByteArrayOutputStream exportExcel2003(ExcelData data) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        return exportExcel(workbook, data);
    }

    public ByteArrayOutputStream exportExcel2007(ExcelData data) {
        XSSFWorkbook wb = new XSSFWorkbook();
        return exportExcel(wb, data);
    }

    private ByteArrayOutputStream exportExcel(Workbook workbook, ExcelData data) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            Sheet sheet = workbook.createSheet(sheetName);
            writeExcel(workbook, sheet, data);
            IOUtils.writeAndClose(workbook, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    private void writeExcel(Workbook workbook, Sheet sheet, ExcelData data) {
        int rowIndex;
        rowIndex = writeTitlesToExcel(workbook, sheet, data.getTitles());
        writeRowsToExcel(workbook, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);
    }

    private int writeTitlesToExcel(Workbook workbook, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex;

        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        Color clr = new Color(182, 184, 192);
//        cellStyle.setFillForegroundColor(clr.getRGB());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(titleFont);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;
        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(cellStyle);
            colIndex++;
        }
        rowIndex++;
        return rowIndex;
    }

    private static void writeRowsToExcel(Workbook workbook, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex;

        Font dataFont = workbook.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(dataFont);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(cellStyle);
                colIndex++;
            }
            rowIndex++;
        }
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    /*public static void main(String[] args) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList<>();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = new ArrayList<>();
        row.add("11111111111");
        row.add("22222222222");
        row.add("3333333333");
        row.add("dasdasdas");
        rows.add(row);

        data.setRows(rows);

        File f = new File("c:/test.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        exportExcel(data, out);
        out.close();
    }*/

}
