package com.ucergy.ilocator.service.impl;

import com.ucergy.ilocator.model.Place;
import com.ucergy.ilocator.repository.PlaceRepository;
import com.ucergy.ilocator.service.PlaceService;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraGetImportantRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by l_a_s on 08/06/2017.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private SparkCassandraGetImportantRegion sparkCassandraGetImportantRegion;

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place findByName(String region) {
        return placeRepository.findByName(region);
    }

    @Override
    public void updateRegionImportantVisit(int importantVisit, String region) {
        placeRepository.updateRegionImportantVisit(importantVisit, region);
    }

    @Override
    public void updateRegionsImportantVisit() {

        for (Place place : placeRepository.findAll()) {
            sparkCassandraGetImportantRegion.updatePlacesWithImportantVisit();
        }
    }


}
