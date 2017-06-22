package com.ucergy.ilocator;

// $example on$

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowth.FreqItemset;

import java.util.Arrays;

// $example off$

public class JavaAssociationRulesExamples {

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf().setAppName("SOME APP NAME").setMaster("local[2]").set("spark.executor.memory", "1g");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        // $example on$
        JavaRDD<FPGrowth.FreqItemset<String>> freqItemsets = sc.parallelize(Arrays.asList(
                new FreqItemset<>(new String[]{"a", "b", "c"}, 15L),
                new FreqItemset<>(new String[]{"b"}, 35L),
                new FreqItemset<>(new String[]{"a", "b"}, 12L)
        ));

        AssociationRules arules = new AssociationRules()
                .setMinConfidence(0.5);
        JavaRDD<AssociationRules.Rule<String>> results = arules.run(freqItemsets);

        for (AssociationRules.Rule<String> rule : results.collect()) {
            System.out.println("******************************" +
                    rule.javaAntecedent() + " => " + rule.javaConsequent() + ", " + rule.confidence() + "**************************");
        }
        // $example off$

        sc.stop();
    }
}