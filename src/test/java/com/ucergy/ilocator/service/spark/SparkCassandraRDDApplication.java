package com.ucergy.ilocator.service.spark;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.ucergy.ilocator.model.UserMoves;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

/**
 * Created by l_a_s on 02/06/2017.
 */
public class SparkCassandraRDDApplication {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("SparkCassandraRDDApplication")
                .setMaster("local[2]")
                .set("spark.cassandra.connection.host", "127.0.0.1")
                .set("spark.cassandra.connection.port", "9042");

        JavaSparkContext sc = new JavaSparkContext(conf);

        //Read
        JavaRDD<UserMoves> resultsRDD = javaFunctions(sc).cassandraTable("ilocator", "usermoves", CassandraJavaUtil.mapRowTo(UserMoves.class));

        //Print
        resultsRDD.foreach(data -> {
            System.out.println(data.getCategory());
            System.out.println(data.getMobileId());
            System.out.println(data.getFloor());
            System.out.println(data.getDate());
        });

        sc.stop();
    }
}