package com.ucergy.ilocator.controller;

import com.ucergy.ilocator.model.UserMovesReceived;
import com.ucergy.ilocator.service.RegionsPredictor;
import com.ucergy.ilocator.service.spark.transformer.SparkPredictor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by l_a_s on 04/06/2017.
 */
@Controller
@RequestMapping("/")
public class PredictionController {

    @Autowired
    RegionsPredictor regionsPredictor;

    @Autowired
    SparkPredictor sparkPredictor;

    @RequestMapping(value = "/predictor", method = RequestMethod.POST)
    @ResponseBody
    public String getSearchUserProfiles(@RequestBody UserMovesReceived userData, HttpServletRequest request) {

        ArrayList<String> userPath = new ArrayList<>();
        ArrayList<String> userCategories = new ArrayList<>();
        userPath.add(userData.getRegion());
        userPath.add("H&M");
        userPath.add("Mcdo");
        userPath.add("Zara");
        userPath.add("Sephora");
        userPath.add("Celio");
        userPath.add("Mcdo");
        userPath.add("H&M");
        userPath.add("Celio");
        userPath.add("H&M");
        userPath.add("Mcdo");
        userPath.add("Zara");
        userPath.add("Sephora");
        userPath.add("Celio");
        userPath.add("Mcdo");


        userCategories.add(userData.getCategory());

        String nextDestination = regionsPredictor.getFrequentItem(userPath, userCategories);
        // String nextDestination = sparkPredictor.predict(userPath, userCategories);
        return nextDestination;
    }
}
