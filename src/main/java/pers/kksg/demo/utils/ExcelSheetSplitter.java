package pers.kksg.demo.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @project: demo
 * @description:
 * @author: lvqiang
 * @create: 2025-06-16 10:18
 **/
public class ExcelSheetSplitter {

    public static void main(String[] args) {
        String inputFilePath = "/Users/lvqiang/Desktop/反欺诈指标测试样本.xlsx";  // 输入文件路径
        String outputDirectory = "/Users/lvqiang/Desktop/split/";  // 输出目录

        try {
            splitExcelSheets(inputFilePath, outputDirectory);
            System.out.println("Sheet拆分完成！文件保存在: " + outputDirectory);
        } catch (Exception e) {
            System.err.println("处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void splitExcelSheets(String inputFilePath, String outputDirectory) throws Exception {
        try (FileInputStream inputFile = new FileInputStream(inputFilePath);
             Workbook inputWorkbook = new XSSFWorkbook(inputFile)) {

            // 预先缓存列宽
            Map<Sheet, Integer[]> columnWidthsCache = new HashMap<>();
            for (int i = 0; i < inputWorkbook.getNumberOfSheets(); i++) {
                Sheet sheet = inputWorkbook.getSheetAt(i);
                columnWidthsCache.put(sheet, getMaxColumnIndexAndWidths(sheet));
            }

            Iterator<Sheet> sheetIterator = inputWorkbook.sheetIterator();

            int sheetCount = 0;
            while (sheetIterator.hasNext()) {
                Sheet sourceSheet = sheetIterator.next();
                String sheetName = sourceSheet.getSheetName();
                System.out.println("正在处理工作表: " + sheetName + " (" + (++sheetCount) + "/" + inputWorkbook.getNumberOfSheets() + ")");

                // 创建新工作簿
                try (Workbook outputWorkbook = new XSSFWorkbook()) {
                    Sheet newSheet = outputWorkbook.createSheet(sanitizeSheetName(sheetName));

                    // 复制工作表内容
                    long copyStart = System.currentTimeMillis();
                    copySheet(sourceSheet, newSheet, columnWidthsCache.get(sourceSheet), outputWorkbook);
                    System.out.println("复制数据耗时: " + (System.currentTimeMillis() - copyStart) + "ms");

                    // 保存为独立文件
                    String outputPath = outputDirectory + sanitizeFileName(sheetName) + ".xlsx";
                    try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                        outputWorkbook.write(fileOut);
                    }
                }
            }
        }
    }

    // 高性能的Sheet复制方法
    private static void copySheet(Sheet source, Sheet destination, Integer[] maxColInfo, Workbook newWorkbook) {
        if (maxColInfo == null) return;

        int maxColIndex = maxColInfo[0];

        // 1. 设置列宽（优先）
        for (int col = 0; col < maxColIndex; col++) {
            destination.setColumnWidth(col, maxColInfo[col + 1]);
        }

        // 2. 复制合并区域（一次性操作）
        for (int i = 0; i < source.getNumMergedRegions(); i++) {
            destination.addMergedRegion(source.getMergedRegion(i));
        }

        // 3. 复制行和单元格
        Map<CellStyle, CellStyle> styleCache = new HashMap<>(); // 样式缓存提高性能
        boolean hasMergedRegions = source.getNumMergedRegions() > 0;

        for (int rowIndex = 0; rowIndex <= source.getLastRowNum(); rowIndex++) {
            Row sourceRow = source.getRow(rowIndex);
            if (sourceRow == null) continue;

            Row newRow = destination.createRow(rowIndex);
            newRow.setHeight(sourceRow.getHeight());

            // 跳过合并区域中的单元格（大幅减少处理量）
            if (hasMergedRegions && isRowInMergedRegion(source, rowIndex)) {
                continue;
            }

            for (int cellIndex = 0; cellIndex <= sourceRow.getLastCellNum(); cellIndex++) {
                Cell sourceCell = sourceRow.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (sourceCell == null) continue;

                // 快速跳过合并区域内的单元格
                if (hasMergedRegions && isCellInMergedRegion(source, rowIndex, cellIndex)) {
                    continue;
                }

                Cell newCell = newRow.createCell(cellIndex);
                fastCopyCell(sourceCell, newCell, newWorkbook, styleCache);
            }
        }
    }

    // 快速复制单元格（使用样式缓存）
    private static void fastCopyCell(Cell sourceCell, Cell newCell, Workbook newWorkbook, Map<CellStyle, CellStyle> styleCache) {
        // 使用样式缓存避免重复创建
        CellStyle sourceStyle = sourceCell.getCellStyle();
        CellStyle newStyle = styleCache.get(sourceStyle);

        if (newStyle == null) {
            newStyle = newWorkbook.createCellStyle();
            newStyle.cloneStyleFrom(sourceStyle);
            styleCache.put(sourceStyle, newStyle);
        }

        newCell.setCellStyle(newStyle);

        // 复制单元格值
        switch (sourceCell.getCellType()) {
            case STRING:
                newCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(sourceCell)) {
                    newCell.setCellValue(sourceCell.getDateCellValue());
                } else {
                    newCell.setCellValue(sourceCell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                newCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(sourceCell.getCellFormula());
                break;
            case BLANK:
                newCell.setBlank();
                break;
            default:
                break;
        }
    }

    // 高性能方法：获取最大列索引和列宽
    private static Integer[] getMaxColumnIndexAndWidths(Sheet sheet) {
        int maxColumnIndex = -1;
        int[] columnWidths = new int[0];

        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            int lastCellIndex = row.getLastCellNum();
            if (lastCellIndex > maxColumnIndex) {
                maxColumnIndex = lastCellIndex;

                // 扩展列宽数组
                int[] newWidths = new int[maxColumnIndex];
                System.arraycopy(columnWidths, 0, newWidths, 0, columnWidths.length);
                columnWidths = newWidths;
            }

            for (int cellIndex = 0; cellIndex < maxColumnIndex; cellIndex++) {
                if (sheet.getColumnWidth(cellIndex) > columnWidths[cellIndex]) {
                    columnWidths[cellIndex] = sheet.getColumnWidth(cellIndex);
                }
            }
        }

        // 结果结构: [maxColumnIndex, columnWidth0, columnWidth1, ...]
        Integer[] result = new Integer[columnWidths.length + 1];
        result[0] = maxColumnIndex;
        for (int i = 0; i < columnWidths.length; i++) {
            result[i + 1] = columnWidths[i];
        }

        return result;
    }

    // 检查行是否在合并区域内
    private static boolean isRowInMergedRegion(Sheet sheet, int rowIndex) {
        return sheet.getMergedRegions().stream()
                .anyMatch(region -> rowIndex >= region.getFirstRow() && rowIndex <= region.getLastRow());
    }

    // 检查单元格是否在合并区域内
    private static boolean isCellInMergedRegion(Sheet sheet, int rowIndex, int columnIndex) {
        return sheet.getMergedRegions().stream()
                .anyMatch(region -> rowIndex >= region.getFirstRow() && rowIndex <= region.getLastRow() &&
                        columnIndex >= region.getFirstColumn() && columnIndex <= region.getLastColumn());
    }

    // 清理Sheet名称
    private static String sanitizeSheetName(String name) {
        return name;
    }

    // 清理文件名
    private static String sanitizeFileName(String name) {
        return name.replaceAll("[\\\\/*?:\"<>|]", "_");
    }
}