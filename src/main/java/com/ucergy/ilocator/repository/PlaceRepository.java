package com.ucergy.ilocator.repository;

import com.ucergy.ilocator.model.Place;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by l_a_s on 06/06/2017.
 */
@Repository
public interface PlaceRepository extends CrudRepository<Place, String> {

    List<Place> findAll();

    Place findByName(String region);

    @Query("update ilocator.place set importantVisit =?0 where name =?1")
    void updateRegionImportantVisit(int importantVisit, String region);



}
