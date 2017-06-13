package com.ucergy.ilocator.service.spark;

import com.ucergy.ilocator.service.spark.transformer.SparkCassandraUsersPathsGenerator;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.junit4.SpringRunner;

import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.concat_ws;

/**
 * Created by l_a_s on 02/06/2017.
 */

@RunWith(SpringRunner.class)
public class SparkCassandraDatasetApplicationTest implements CommandLineRunner {

    @Autowired
    SparkCassandraUsersPathsGenerator sparkCassandraUsersPathsGenerator;

    @Override
    public void run(String... args) throws Exception {
        sparkCassandraUsersPathsGenerator.generatePath();

    }

    @Ignore
    public void testGeneratePath() {
        //SparkCassandraUsersPathsGenerator sparkCassandraDatasetApplication = new SparkCassandraUsersPathsGeneratorImpl();
        SpringApplication.run(SparkCassandraDatasetApplicationTest.class, "void");

    }

}

