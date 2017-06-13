package com.ucergy.ilocator.service.spark.transformer;

import com.ucergy.ilocator.service.RegionsPredictor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.concat_ws;

/**
 * Created by l_a_s on 11/06/2017.
 */
@Service
public class SparkPredictorImpl implements SparkPredictor {

    @Autowired
    private SparkSession spark;

    @Autowired
    private RegionsPredictor regionsPredictor;

    @Override
    public String predict(ArrayList<String> userPosition, ArrayList<String> userCategories) {

        String predicate = null;
        ArrayList<String[]> regionsList = new ArrayList<>();
        ArrayList<String[]> categoriesList = new ArrayList<>();
        String[] regionsLine = new String[20];
        String[] categoriesLine = new String[20];

        Dataset<Row> userMoves = spark.read().format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "ilocator");
                        put("table", "usermoves");
                    }
                }).load();

        Dataset<Row> sessionRegion = userMoves.select("sessionId", "region").
                groupBy("sessionID").agg(concat_ws(",", collect_list("region")).alias("path"));

        List<Row> regionsPath = sessionRegion.select("path").collectAsList();

        Dataset<Row> sessionCategory = userMoves.select("sessionId", "category").
                groupBy("sessionId").agg(concat_ws(",", collect_list("category")).alias("path"));

        List<Row> categoriesPath = sessionCategory.select("path").collectAsList();

        for (Row categorieRow : categoriesPath) {

            String[] elements = categorieRow.getString(0).split(",");
            String[] path = new String[elements.length];

            for (int i = 0; i < elements.length; i++) {

                String value = elements[i];
                path[i] = value;
            }

            categoriesList.add(path);
        }

        for (Row regionRow : regionsPath) {
            String[] regionElements = regionRow.getString(0).split(",");
            String[] regionPath = new String[regionElements.length];

            for (int j = 0; j < regionElements.length; j++) {

                String value = regionElements[j];
                regionPath[j] = value;
            }
            regionsList.add(regionPath);
        }

      //  predicate = regionsPredictor.getFrequentItem(userPosition,userCategories,regionsList,categoriesList);

        return predicate;
    }


}
