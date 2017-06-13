package com.ucergy.ilocator.service;

import java.util.ArrayList;

/**
 * Created by l_a_s on 04/06/2017.
 */
public interface RegionsPredictor {

    String getFrequentItem(ArrayList<String> userPosition, ArrayList<String> userCategories);

}
