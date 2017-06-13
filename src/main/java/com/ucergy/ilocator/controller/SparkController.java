package com.ucergy.ilocator.controller;

import com.ucergy.ilocator.model.Place;
import com.ucergy.ilocator.service.PlaceService;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraGetImportantRegion;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraUsersPathsByCategoriesGenerator;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraUsersPathsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l_a_s on 06/06/2017.
 */
@Controller
public class SparkController {

    @Autowired
    private SparkCassandraUsersPathsByCategoriesGenerator sparkCassandraUsersPathsByCategoriesGenerator;

    @Autowired
    private SparkCassandraUsersPathsGenerator sparkCassandraUsersPathsGenerator;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private SparkCassandraGetImportantRegion sparkCassandraGetImportantRegion;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {

        return "/index";
    }

    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    public String getRegionPage() {

        return "/regions";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String getCategoriesPage() {

        return "/categories";
    }

    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public String getPlacesPage() {

        return "/places";
    }

    @RequestMapping(value = "/regionsPath", method = RequestMethod.POST)
    public String generateRegionPath(Model model) {
        model.addAttribute("response", "Mise à jour du chemin en fonction des régions effectuée");
        sparkCassandraUsersPathsGenerator.generatePath();

        return "/OK";
    }

    @RequestMapping(value = "/categoriesPath", method = RequestMethod.POST)
    public String generateCategoriesPath(Model model) {
        model.addAttribute("response", "Mise à jour du chemin en fonction des categories effectuée");
        sparkCassandraUsersPathsByCategoriesGenerator.getPathsByCategory();

        return "/OK";
    }

    @RequestMapping(value = "/importantVisit", method = RequestMethod.POST)
    public String generateImportantVisit(Model model) {
        model.addAttribute("response", "Mise à jour des visites importantes effectuée");

        List<Place> places = new ArrayList<>();
        places = placeService.findAll();

        for (Place place : places) {
            sparkCassandraGetImportantRegion.updatePlacesWithImportantVisit();
        }

        return "/OK";
    }
}
