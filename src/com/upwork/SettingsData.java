package com.upwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SettingsData {

    public static String startDate = "";
    public static String endDate = "";
    public static String closeDate = "";

    //public static String countries[] = {"Israel", "HK"};//, "Netherlands"};
    public static List<String> countries;

    public static void readSettingsFile() throws FileNotFoundException {

        File file =
                new File(getUserSettingsFilePath());
        Scanner sc = new Scanner(file);

        countries = new ArrayList<>();

        int i=0;
        while (sc.hasNextLine()){
            String line = sc.nextLine().toString();
            //System.out.println(line);

            if(i==0)
                startDate = line;
            else if(i==1)
                endDate = line;
            else if(i==2)
                closeDate = line;
            else if(i==3){
                String[] lines = line.split(",");

                for (String l: lines) {
                    l = l.replaceAll("\\s", "");
                    countries.add(l);
                    System.out.println(l);
                }
            }

            i++;
        }
    }

    public static String getUserSettingsFilePath() throws FileNotFoundException{

        String filePath = System.getProperty("user.dir");
        filePath = filePath.replace("\\", "/");
        filePath += "/Settings/userSettings.txt";

        return filePath;
    }

}

