package com.upwork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {

        SettingsData.readSettingsFile();

        //String countries[] = new String[SettingsData.countries.length];
        //System.arraycopy(SettingsData.countries, 0, countries, 0, SettingsData.countries.length);

        List<String> countries = new ArrayList<>();
        countries.addAll(SettingsData.countries);

        for (int i = 0; i < countries.size(); i++) {

            String countryListName = countries.get(i) + " list.xlsx";
            String country3Name = countries.get(i) + " 3.xlsx";

            System.out.println(countries.get(i));

            runReadWriteProcess(countryListName, country3Name);
        }
    }

    private static void runReadWriteProcess(String countryList, String country3) throws Exception {
        ApachePOIExcelRead.ReadOnlyCustomColumns(countryList, 0, 3, 33, 34);
        TimeUnit.SECONDS.sleep(2);
        ApachePOIExcelWrite.WriteOnlyCustomColumns(country3, 0, 12);
    }
}

