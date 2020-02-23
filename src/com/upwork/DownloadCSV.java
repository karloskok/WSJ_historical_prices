package com.upwork;

import java.io.*;
import java.net.URL;
import java.nio.file.*;



public class DownloadCSV {
    static void downloadCSVFile(String url, String fileName, String excelName){

        try {
            InputStream in = new URL(url).openStream();

            String subFolder = excelName.replace(" list.xlsx","");
            Path path = Paths.get("CSVFiles/" + subFolder);

            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            }

            File file = new File("CSVFiles/" + subFolder + "/" + fileName + ".csv");
            Files.copy(in, Paths.get(String.valueOf(file)), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
