package com.ucergy.ilocator.service.spark.transformer;

import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import static org.apache.derby.iapi.reference.Property.PROPERTIES_FILE;
import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.concat_ws;

/**
 * Created by l_a_s on 04/06/2017.
 */
@Service
public class SparkCassandraUsersPathsByCategoriesGeneratorImpl implements SparkCassandraUsersPathsByCategoriesGenerator{

    public static final String CATEGORY_PATH_DIRECTORY = "/Users/l_a_s/Downloads/ilocator/userRegions";

    @Autowired
    private SparkSession spark;

    @Override
    public void getPathsByCategory() {


        Dataset<Row> userMoves = spark.read().format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "ilocator");
                        put("table", "usermoves");
                    }
                }).load();

        Dataset<Row> sessionRegion = userMoves.select("sessionId", "category").
                groupBy("sessionId").agg(concat_ws(",", collect_list("category")).alias("path"));

        Dataset<Row> paths = sessionRegion.select("path");


        File sparkDirecotryOld = new File(CATEGORY_PATH_DIRECTORY);

        if (sparkDirecotryOld.exists()) {
            try {
                FileUtils.deleteDirectory(sparkDirecotryOld);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        paths.coalesce(1).write().option("header", "false").format("com.databricks.spark.csv").save(CATEGORY_PATH_DIRECTORY);


        File sparkDirecotry = new File(CATEGORY_PATH_DIRECTORY);
        File pathFile = new File(new ClassPathResource("/target/classes/categoriesPath.csv").getPath());

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
