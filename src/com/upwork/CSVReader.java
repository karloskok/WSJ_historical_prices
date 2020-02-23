package com.upwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVReader {
    public static double getCloseDataFromCSV(String subFolder, String csvName) throws IOException, ParseException {

        String csvFile = "CSVFiles/"  + subFolder + "/" + csvName;
        String line = "";
        String cvsSplitBy = ",";

        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date closeDate = sdf.parse(SettingsData.closeDate);

        double closeData = 0;
        FileReader readFile = null;

        try{
            readFile = new FileReader(csvFile);
        } catch (Exception e ) {
            System.out.println("File not exist: " + e);
        }

        if(readFile == null) return 0;

        BufferedReader br = new BufferedReader(readFile);

        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] country = line.split(cvsSplitBy);

            if(!country[0].contains("Date")){

                Date previousDate = sdf.parse(country[0]);
                long diff = closeDate.getTime() - previousDate.getTime();

                int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                if(diffDays == 0){
                    closeData = Double.valueOf(country[4]);
                    System.out.println("[diff= " + diffDays + ", company= " + csvFile + " ||  date= " + country[0] + " , close= " + closeData + "]");
                    break;
                }else if(diffDays >= 0){
                    closeData = Double.valueOf(country[4]);
                    System.out.println("[diff= " + diffDays + ", company= " + csvFile + " ||  date= " + country[0] + " , close= " + closeData + "]");
                    break;
                }
            }
            i++;
        }
        return closeData;
    }
}
