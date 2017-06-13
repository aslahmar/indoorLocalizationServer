package com.ucergy.ilocator.service.spark.transformer;

import java.util.ArrayList;

/**
 * Created by l_a_s on 11/06/2017.
 */
public interface SparkPredictor {

    String predict(ArrayList<String> userPosition, ArrayList<String> userCategories);
}
