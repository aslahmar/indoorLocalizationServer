package com.ucergy.ilocator.service.spark.transformer;

import com.ucergy.ilocator.service.PlaceService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

/**
 * Created by l_a_s on 07/06/2017.
 */
@Service
public class SparkCassandraGetImportantRegionImpl implements SparkCassandraGetImportantRegion {

    @Autowired
    private SparkSession spark;

    @Autowired
    private PlaceService placeService;



    @Override
    public void updatePlacesWithImportantVisit() {

        Map<String, Integer> regionImportant = new HashMap<>();
        Dataset<Row> userMoves = spark.read().format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "ilocator");
                        put("table", "usermoves");
                    }
                }).load();


        List<Row> sessionRegion = userMoves.filter(col("duration").gt(150))
                .groupBy("region").count().collectAsList();

        for (Row row : sessionRegion){
            String region = row.get(0).toString();
            Long importantVisit = (Long)row.get(1);
            int important = importantVisit.intValue();
            regionImportant.put(region, important);

        }

     //   System.out.println("************ la valeur pour Sephora est:" + regionImportant.get("Sephora"));
       // System.out.println("************ la valeur pour UGC est:" + regionImportant.get("UGC"));

        for (Map.Entry<String, Integer> entry : regionImportant.entrySet()) {  // Itrate through hashmap
            placeService.updateRegionImportantVisit(entry.getValue(), entry.getKey());

        }

    }


}
