package com.ucergy.ilocator.service.impl;

import com.ucergy.ilocator.model.Place;
import com.ucergy.ilocator.service.PlaceService;
import com.ucergy.ilocator.service.RegionsPredictor;
import com.ucergy.ilocator.util.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ucergy.ilocator.util.CsvParser.ParseCsvFile;

/**
 * Created by utilisateur on 27/04/2017.
 */

@Service
public class RegionsPredictorImpl implements RegionsPredictor {

    @Autowired
    private PlaceService placeService;

    private static LinkedHashMap<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
        LinkedHashMap<String, Integer> sortedImportant = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        sortedImportant = keepImportantElement(sortedMap);
        printMap(sortedImportant);
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

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
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

    private static String sortByMinDistance(Map<String, Double> unsortMap) {
        String min = null;
        Map.Entry<String, Double> minEntry = null;

        for (Map.Entry<String, Double> entry : unsortMap.entrySet()) {
            if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                minEntry = entry;
            }
        }
        min = minEntry.getKey();

        return min;
    }

    @Override
    public String getFrequentItem(ArrayList<String> userPosition, ArrayList<String> userCategories) {

        List<String> nextItems = new ArrayList<>();
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        List<Place> places = new ArrayList<>();

        ArrayList<String[]> regionsDataMatrix = null;

        boolean flag = true;
        String predicate = null;
        try {
            regionsDataMatrix = ParseCsvFile("regionsPath.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        nextItems = getNextItems(userPosition, regionsDataMatrix);

        if (nextItems.isEmpty()) {

            predicate = getFrequentItemByCategory(userPosition, userCategories, userPosition.get(userPosition.size() - 1));

        } else if (nextItems.size() > 1) {

            Map<String, Integer> positionFrequency = new HashMap<>();
            List<String> listDistinct = nextItems.stream().distinct().collect(Collectors.toList());
            // Si la liste contient 1 element c'est lui le prédicted
            if (listDistinct.size() == 1) {

                predicate = listDistinct.get(0);
                // Si plus on récupère les trois premier
            } else {

                predicate = getPredicateWhenManyItems(nextItems, positionFrequency, listDistinct, places);

            }
        }
        return predicate;
    }

    private List<String> getNextItems(ArrayList<String> userPosition, ArrayList<String[]> regionsDataMatrix) {

        List<String> nextItems = new ArrayList<>();
        boolean flag;
        for (int i = 0; i < regionsDataMatrix.size(); i++) {
            flag = true;

            if (regionsDataMatrix.get(i).length > userPosition.size()) {

                for (int j = 0; j < userPosition.size(); j++) {

                    if (!regionsDataMatrix.get(i)[j].equalsIgnoreCase(userPosition.get(j))) {

                        flag = false;
                        j = userPosition.size();
                    }
                }
                if (flag) {
                    nextItems.add(regionsDataMatrix.get(i)[userPosition.size()]);
                }
            }

        }
        return nextItems;
    }

    public String getFrequentItemByCategory(ArrayList<String> userPosition, ArrayList<String> userCategories, String userLastPosition) {

        ArrayList<String[]> categoriesDataMatrix = null;
        List<String> nextItems = new ArrayList<>();
        String category = null;
        String predicate = null;
        try {
            categoriesDataMatrix = ParseCsvFile("categoriesPath.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        nextItems = getNextItems(userCategories, categoriesDataMatrix);

        if (nextItems.isEmpty()) {

            predicate = "Empty";

        } else if (nextItems.size() > 1) {

            Map<String, Integer> positionFrequency = new HashMap<>();
            List<String> listDistinct = nextItems.stream().distinct().collect(Collectors.toList());
            Map<String, Double> placeDistance = new HashMap<>();
            List<Place> places = new ArrayList<>();
            List<Place> placesFiltred = new ArrayList<>();
            List<Place> placesFiltredByCategory = new ArrayList<>();

            // Si la liste contient 1 element c'est lui le prédicted

            if (listDistinct.size() == 1) {

                predicate = listDistinct.get(0);
                // Si plus on récupère les trois premier
            } else if (listDistinct.size() > 1) {

                for (String possible : listDistinct) {
                    positionFrequency.put(possible, Collections.frequency(nextItems, possible));
                }
                category = sortByValueMax(positionFrequency);

                places = placeService.findAll();
                Place currentPlace = placeService.findByName(userLastPosition);
                Point currentPoint = new Point(currentPlace.getEntryPointX(), currentPlace.getEntryPointY());

                for (Place place : places) {

                    if (place.getCategory().equalsIgnoreCase(category)) {

                        placesFiltredByCategory.add(place);

                    }
                }

                for (Place placeCategory : placesFiltredByCategory) {

                    for (String region : userPosition) {

                        if (!region.equalsIgnoreCase(placeCategory.getName()) && placeService.findByName(region).getCategory().equalsIgnoreCase(placeCategory.getCategory())) {

                            placesFiltred.add(placeCategory);

                        }
                    }
                }

                if (placesFiltred.isEmpty()) {
                    predicate = "Empty";
                } else {
                    for (Place placeFiltred : placesFiltred) {

                        Point regionPoint = new Point(placeFiltred.getEntryPointX(), placeFiltred.getEntryPointY());
                        double distance = distance(currentPoint, regionPoint);
                        placeDistance.put(placeFiltred.getName(), distance);
                    }

                    printMap(placeDistance);
                    predicate = sortByMinDistance(placeDistance);
                }
            }
        }

        return predicate;
    }

    private String getPredicateWhenManyItems(List<String> nextItems, Map<String, Integer> positionFrequency, List<String> listDistinct, List<Place> places) {
        Map<String, Integer> sortedMap;
        String predicate;
        for (String possible : listDistinct) {
            positionFrequency.put(possible, Collections.frequency(nextItems, possible));
        }
        printMap(positionFrequency);
        sortedMap = sortByValue(positionFrequency);

        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            Place placeTmp = new Place();
            placeTmp = placeService.findByName(entry.getKey());
            places.add(placeTmp);
        }

        predicate = places.stream()
                .max(Comparator.comparing(Place::getImportantVisit))
                .get().getName();
        return predicate;
    }

    private double distance(Point a, Point b) {
        float yDiff = 0;
        float xDiff = 0;
        double distance = 0;
        yDiff = Math.abs(a.getY() - b.getY());
        xDiff = Math.abs(a.getX() - b.getX());
        distance = Math.sqrt((yDiff) * (yDiff) + (xDiff) * (xDiff));

        return distance;
    }
}



