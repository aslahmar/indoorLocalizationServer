package com.ucergy.ilocator.service.spark.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created by achat1 on 9/22/15.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SparkConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .appName("SparkCassandraDatasetApplicationImpl")
                .config("spark.sql.warehouse.dir", "/Users/l_a_s/Downloads/ilocator\n")
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.cassandra.connection.port", "9042")
                .config("spark.driver.allowMultipleContexts", "true")
                .master("local[4]")
                .getOrCreate();
    }

    @Bean
    public SparkConf sparkConfiguration() {

        return new SparkConf()
                .setAppName("SOME APP NAME")
                .setMaster("local[2]")
                .set("spark.executor.memory", "1g")
                .set("spark.driver.allowMultipleContexts", "true")
                ;

    }

}