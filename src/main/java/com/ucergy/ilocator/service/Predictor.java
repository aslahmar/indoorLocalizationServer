package com.ucergy.ilocator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by utilisateur on 27/04/2017.
 */
public class Predictor {

    private static ArrayList<String[]> ParseCsvFile(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        String line = "";

        ArrayList<String[]> pathList = new ArrayList<String[]>();

        while ((line = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(line, ",");

            String[] path = new String[5];

            for (int i = 0; i < 5; i++) {

                String value = st.nextToken();
                path[i] = value;
            }
            pathList.add(path);

        }
        return pathList;
    }

    public  String getFrequentItem(ArrayList<String> userPosition) {

        List<String> nextItems = new ArrayList();
        ArrayList<String[]> dataMatrix = null;

        try {
            dataMatrix = ParseCsvFile("/Users/l_a_s/Desktop/SimulationData.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creating a List containing the different items
        List<String> standardPositions = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

        for (int i = 0; i < dataMatrix.size(); i++) {

            switch (userPosition.size()) {
                case 1:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0))) {
                        nextItems.add(dataMatrix.get(i)[1]);
                    }
                    break;
                case 2:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[1].equals(userPosition.get(1))) {
                        nextItems.add(dataMatrix.get(i)[2]);
                    }
                    break;
                case 3:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[1].equals(userPosition.get(1)) && dataMatrix.get(i)[2].equals(userPosition.get(2))) {
                        nextItems.add(dataMatrix.get(i)[3]);
                    }
                    break;
                case 4:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[0].equals(userPosition.get(0))
                            && dataMatrix.get(i)[0].equals(userPosition.get(0))) {
                        nextItems.add(dataMatrix.get(i)[4]);
                    }
                    break;
                case 5:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[1].equals(userPosition.get(1)) && dataMatrix.get(i)[2].equals(userPosition.get(2))
                            && dataMatrix.get(i)[3].equals(userPosition.get(3)) && dataMatrix.get(i)[4].equals(userPosition.get(4))) {
                        nextItems.add(dataMatrix.get(i)[5]);
                    }
                    break;
                case 6:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[0].equals(userPosition.get(0))
                            && dataMatrix.get(i)[3].equals(userPosition.get(3)) && dataMatrix.get(i)[4].equals(userPosition.get(4)) && dataMatrix.get(i)[5].equals(userPosition.get(5))) {

                        nextItems.add(dataMatrix.get(i)[6]);
                    }
                    break;
                case 7:
                    if (dataMatrix.get(i)[0].equals(userPosition.get(0)) && dataMatrix.get(i)[1].equals(userPosition.get(1)) && dataMatrix.get(i)[2].equals(userPosition.get(2))
                            && dataMatrix.get(i)[3].equals(userPosition.get(3)) && dataMatrix.get(i)[4].equals(userPosition.get(4)) && dataMatrix.get(i)[5].equals(userPosition.get(5))
                            && dataMatrix.get(i)[6].equals(userPosition.get(6))) {
                        nextItems.add(dataMatrix.get(i)[7]);
                    }
                    break;
                default:
                    System.out.print("too much positions");

            }

        }

        Map<String, Integer> positionFrequency = new HashMap();
        List<String> listDistinct = nextItems.stream().distinct().collect(Collectors.toList());

        for (String possible : listDistinct) {
            positionFrequency.put(possible, Collections.frequency(nextItems, possible));
        }

        return sortByValue(positionFrequency);

    }

    public  String sortByValue(Map<String, Integer> unsortMap) {
        String max = null;
        int maxValueInMap = (Collections.max(unsortMap.values()));  // This will return max value in the Hashmap
        for (Map.Entry<String, Integer> entry : unsortMap.entrySet()) {  // Itrate through hashmap
            if (entry.getValue() == maxValueInMap) {
                max = entry.getKey();     // Print the key with max value
            }
        }

        return max;
    }

}


