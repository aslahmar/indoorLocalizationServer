package com.ucergy.ilocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by utilisateur on 27/04/2017.
 */


public class PredictorFormated {

    public static void main(String[] args) throws IOException {

        ArrayList<String> userPosition = new ArrayList<String>();
        userPosition.add("region1");


        String frequent = null;

        try {

            frequent = PredictorFormated.getFrequentItem(userPosition);

        } catch (NoSuchElementException e) {

            System.out.println("erreur");
            e.printStackTrace();
        }

        System.out.print("l'item frequent est :" + frequent);
    }

    public static ArrayList<String[]> ParseCsvFile(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
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

    public static String getFrequentItem(ArrayList<String> userPosition) {

        List<String> nextItems = new ArrayList<>();
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<String[]> dataMatrix = null;
        boolean flag = true;
        String predicate = null;
        try {
            dataMatrix = ParseCsvFile("/Users/l_a_s/Downloads/ilocator/src/main/resources/regionsPath.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < dataMatrix.size(); i++) {
            flag = true;

            if (dataMatrix.get(i).length > userPosition.size()) {

                for (int j = 0; j < userPosition.size(); j++) {

                    if (!dataMatrix.get(i)[j].equals(userPosition.get(j))) {

                        flag = false;
                        j = userPosition.size();
                    }
                }
                if (flag) {
                    nextItems.add(dataMatrix.get(i)[userPosition.size()]);
                }
            }

        }
        if (nextItems.isEmpty()) {

            predicate = null;

        } else if (nextItems.size() > 1) {

            Map<String, Integer> positionFrequency = new HashMap<>();
            List<String> listDistinct = nextItems.stream().distinct().collect(Collectors.toList());
            // Si la liste contient 1 element c'est lui le prédicted
            if (listDistinct.size() == 1) {

                predicate = listDistinct.get(0);
                // Si plus on récupère les trois premier
            } else {

                for (String possible : listDistinct) {
                    positionFrequency.put(possible, Collections.frequency(nextItems, possible));
                }
                printMap(positionFrequency);
                sortedMap = sortByValue(positionFrequency);


            }
        }
        return predicate;
    }

    private static String sortByValueMax(Map<String, Integer> unsortMap) {
        String max = null;
        int maxValueInMap = (Collections.max(unsortMap.values()));  // This will return max value in the Hashmap
        for (Map.Entry<String, Integer> entry : unsortMap.entrySet()) {  // Itrate through hashmap
            if (entry.getValue() == maxValueInMap) {
                max = entry.getKey();     // Print the key with max value
            }
        }

        return max;
    }

    private static LinkedHashMap<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
        LinkedHashMap<String, Integer> sortedImportant = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        sortedImportant = keepImportantElement(sortedMap);
        return sortedImportant;
    }

    private static LinkedHashMap<String, Integer> keepImportantElement(LinkedHashMap<String, Integer> sortedMap) {

        int sum = sortedMap.values().stream().mapToInt(Number::intValue).sum();
        LinkedHashMap<String, Integer> sortedImportant = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {  // Itrate through hashmap

            if (((double) entry.getValue() / (double) sum) * 100 > 30) {
                sortedImportant.put(entry.getKey(), entry.getValue());
            }
        }

        return sortedImportant;
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

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }
}


