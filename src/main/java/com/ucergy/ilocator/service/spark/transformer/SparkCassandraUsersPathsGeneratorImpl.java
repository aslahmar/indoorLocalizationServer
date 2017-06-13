package com.ucergy.ilocator.service.spark.transformer;

import com.ucergy.ilocator.service.RegionsPredictor;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.concat_ws;

/**
 * Created by l_a_s on 02/06/2017.
 */
@Service
public class SparkCassandraUsersPathsGeneratorImpl implements SparkCassandraUsersPathsGenerator {

    public static final String REGIONS_PATH = "/Users/l_a_s/Downloads/ilocator/userMoves";
    @Autowired
    private SparkSession spark;

    @Autowired
    private SparkConf sparkConf;

    @Autowired
    private RegionsPredictor regionsPredictor;

    @Override
    public void generatePath() {

        Dataset<Row> userMoves = spark.read().format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "ilocator");
                        put("table", "usermoves");
                    }
                }).load();

        Dataset<Row> sessionRegion = userMoves.select("sessionId", "region").
                groupBy("sessionID").agg(concat_ws(",", collect_list("region")).alias("path"));

        Dataset<Row> paths = sessionRegion.select("path");


        File sparkDirecotryOld = new File(REGIONS_PATH);

        if (sparkDirecotryOld.exists()) {
            try {
                FileUtils.deleteDirectory(sparkDirecotryOld);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        paths.coalesce(1).write().option("header", "false").format("com.databricks.spark.csv").save(REGIONS_PATH);

      //  spark.stop();

        File sparkDirecotry = new File(REGIONS_PATH);
        File pathFile = new File(new ClassPathResource("/target/classes/regionsPath.csv").getPath());

        if (sparkDirecotry.isDirectory()) {
            for (File file : sparkDirecotry.listFiles()) {
                if (file.getName().startsWith("part-")) {
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

