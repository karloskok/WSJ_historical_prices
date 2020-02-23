package com.upwork;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApachePOIExcelRead {
    public static void  ReadOnlyCustomColumns(String excelName, int cNameColumn, int wsjNumberColumn, int wsjColumn, int prefixColumn) throws Exception {

        File excelFile = new File(getExcelFilePath(excelName));
        FileInputStream fis = new FileInputStream(excelFile);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter;

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            System.out.println("Row index: " + (rowIndex + 1));
            System.out.println("Is row null: " + (row == null));
            if (row != null) {

                formatter = new DataFormatter();

                Cell companyNameCell = row.getCell(cNameColumn);
                if(formatter.formatCellValue(companyNameCell) == "") break;
                Cell wsjCodeNumberCell = row.getCell(wsjNumberColumn);
                Cell wsjCodeCell = row.getCell(wsjColumn);
                Cell wsjPrefixCell = row.getCell(prefixColumn);
                if (wsjCodeNumberCell != null) {

                    String companyName = formatter.formatCellValue(companyNameCell);
                    String wsjCodeNumber = formatter.formatCellValue(wsjCodeNumberCell);
                    String wsjCode = formatter.formatCellValue(wsjCodeCell);
                    String wsjPrefix = formatter.formatCellValue(wsjPrefixCell);

                    if(wsjCodeNumber.contains("XXX")) {
                        System.out.println("WSJ code = " + wsjCodeNumber);
                        continue;
                    }

                    String url;

                    if(wsjCode != "")
                        url = buildLink(wsjCode, wsjPrefix, wsjCodeNumber);
                    else {
                        url = buildLink(wsjCodeNumber);
                        System.out.println("Empty WSJ code, use only WSJ number");
                        //System.out.println("Empty wsjCode: \nurl: " + url);

                    }

                    //System.out.println(rowIndex);
                    System.out.println("url: " + url);
                    DownloadCSV.downloadCSVFile(url, companyName, excelName);
                }
            }
        }
    }

    public static String buildLink(String wsjCodeId, String wsjPrefix, String wsjCode) throws ParseException {

        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date startDate = sdf.parse(SettingsData.startDate);
        Date endDate = sdf.parse(SettingsData.endDate);
        long diff = endDate.getTime() - startDate.getTime();

        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        String firmURL = "https://www.wsj.com/market-data/quotes" +
                "/" +wsjCodeId +
                "/" + wsjPrefix +
                "/" + wsjCode +
                "/historical-prices/download?MOD_VIEW=page&num_rows=" + diffDays + "&range_days=" + diffDays + "&startDate=" +
                SettingsData.startDate +
                "&endDate=" +
                SettingsData.endDate;

        return firmURL;
    }

    public static String buildLink(String wsjCode){
        String firmURL = "https://www.wsj.com/market-data/quotes/" +
                wsjCode +
                "/historical-prices/download?MOD_VIEW=page&num_rows=100&range_days=100&startDate=" +
                SettingsData.startDate +
                "&endDate=" +
                SettingsData.endDate;

        return firmURL;
    }

    public static String getExcelFilePath(String excelName) throws Exception{
        String excelFilePath = System.getProperty("user.dir");
        excelFilePath = excelFilePath.replace("\\", "/");
        excelFilePath += "/ExcelFiles/" + excelName;
        //System.out.println(settingsFilePath);
        return excelFilePath;
    }

}
