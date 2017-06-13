package com.ucergy.ilocator.service;

import com.ucergy.ilocator.model.Place;

import java.util.List;

/**
 * Created by l_a_s on 08/06/2017.
 */

public interface PlaceService {

    List<Place> findAll();

    Place findByName(String region);

    void updateRegionImportantVisit(int importantVisit, String region);

    void updateRegionsImportantVisit();


}
