package com.upwork;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApachePOIExcelWrite {

    public static void  WriteOnlyCustomColumns(String excelName, int cNameColumn, int wsjColumn) throws Exception {

        File excelFile = new File(getExcelFilePath(excelName));
        FileInputStream fis = new FileInputStream(excelFile);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        FileOutputStream out = new FileOutputStream(createOutputPath(excelName));

        String subFolder = excelName.replace(" 3.xlsx","");

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {

                XSSFCell companyNameCell = row.getCell(cNameColumn);
                XSSFCell wsjCodeCell = row.createCell(wsjColumn);

                double val = CSVReader.getCloseDataFromCSV(subFolder , companyNameCell.toString() + ".csv");

                System.out.println(companyNameCell.toString() + " : " + val);
                wsjCodeCell.setBlank();
                if(val != 0)
                    wsjCodeCell.setCellValue(val);
                else
                    wsjCodeCell.setCellValue("");
            }
        }
        workbook.write(out);
        out.close();
        workbook.close();
        fis.close();
    }

    public static String getExcelFilePath(String excelName) throws Exception{
        String excelFilePath = System.getProperty("user.dir");
        excelFilePath = excelFilePath.replace("\\", "/");

        excelFilePath += "/ExcelFiles/" + excelName;
        return excelFilePath;
    }

    public  static String createOutputPath(String excelName){

        String excelFilePath = System.getProperty("user.dir");
        excelFilePath = excelFilePath.replace("\\", "/");

        String subFolder = excelName.replace(" 3.xlsx","");

        Path path = Paths.get("ExcelFiles/" + subFolder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        excelFilePath += "/ExcelFiles/" + subFolder + "/" + subFolder + " 3-results.xlsx";
        return excelFilePath;
    }
}
