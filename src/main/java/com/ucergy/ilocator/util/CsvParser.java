package com.ucergy.ilocator.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by l_a_s on 04/06/2017.
 */
public final class CsvParser {

    public static ArrayList<String[]> ParseCsvFile(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(filePath).getFile()));
        String lineSource = "";
        ArrayList<String[]> pathList = new ArrayList<String[]>();

        while ((lineSource = br.readLine()) != null) {
            String line = lineSource.replaceAll("\"", "");

            String[] elements = line.split(",");
            String[] path = new String[elements.length];

            for (int i = 0; i < elements.length; i++) {

                String value = elements[i];
                path[i] = value;
            }
            pathList.add(path);

        }
        return pathList;
    }
    public static StringBuilder conditionBuilder(int userPathSize) {

        StringBuilder condition = new StringBuilder(200);

        if (userPathSize == 1) {

            condition.append("dataMatrix.get(i)[");
            condition.append(0);
            condition.append("].equals(userPosition.get(");
            condition.append(0);
            condition.append("))");

            return condition;

        } else {
            for (int i = 0; i < userPathSize; i++) {
                if (i < userPathSize - 1) {

                    condition.append("dataMatrix.get(i)[");
                    condition.append(userPathSize - userPathSize + i);
                    condition.append("].equals(userPosition.get(");
                    condition.append(userPathSize - userPathSize + i);
                    condition.append("))");
                    condition.append(" ");
                    condition.append("&&");
                    condition.append(" ");
                } else {
                    condition.append("dataMatrix.get(i)[");
                    condition.append(userPathSize - userPathSize + i);
                    condition.append("].equals(userPosition.get(");
                    condition.append(userPathSize - userPathSize + i);
                    condition.append("))");
                }
            }
            return condition;
        }
    }

    public static ArrayList<String> ParseCsvFileAndGetColumn(String filePath, int column) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(filePath).getFile()));
        String lineSource = "";
        ArrayList<String> pathList = new ArrayList<>();

        while ((lineSource = br.readLine()) != null) {

            String[] elements = lineSource.split(",");


            pathList.add(elements[column]);

        }
        return pathList;
    }
}
