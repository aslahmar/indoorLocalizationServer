package com.ucergy.ilocator.service.spark;

import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.concat_ws;

/**
 * Created by l_a_s on 02/06/2017.
 */
class SparkCassandraDataSetApplication {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("SparkCassandraDatasetApplication")
                .config("spark.sql.warehouse.dir", "/Users/l_a_s/Downloads/ilocator\n")
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.cassandra.connection.port", "9042")
                .master("local[2]")
                .getOrCreate();

        //Read data
        Dataset<Row> usermoves = spark.read().format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "ilocator");
                        put("table", "usermoves");
                    }
                }).load();

        Dataset<Row> sessionRegion = usermoves.select("sessionId","region").
                groupBy("sessionID").agg(concat_ws(",", collect_list("region")).alias("path"));
        ;
        Dataset<Row> paths = sessionRegion.select("path");
        ;
        //Print data
        usermoves.show();
        paths.coalesce(1).write().option("header","false").format("com.databricks.spark.csv").save("/Users/l_a_s/Downloads/ilocator/usermoves") ;

        spark.stop();

        File sparkDirecotry = new File("/Users/l_a_s/Downloads/ilocator/usermoves");
        File pathFile = new File("/Users/l_a_s/Downloads/ilocator/src/main/resources/path.csv");

        if (sparkDirecotry.isDirectory()){
            for(File file: sparkDirecotry.listFiles()){
                if (file.getName().startsWith("part-")){
                    file.renameTo(pathFile);
                }
            }
        }
        try {
            FileUtils.deleteDirectory(sparkDirecotry);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

