package com.ucergy.ilocator;


import com.datastax.driver.core.utils.UUIDs;
import com.ucergy.ilocator.model.Place;
import com.ucergy.ilocator.model.UserMoves;
import com.ucergy.ilocator.repository.PlaceRepository;
import com.ucergy.ilocator.repository.UserMovesRepository;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraGetImportantRegion;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraUsersPathsByCategoriesGenerator;
import com.ucergy.ilocator.service.spark.transformer.SparkCassandraUsersPathsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SpringBootApplication
public class IlocatorApplication implements CommandLineRunner {

    @Autowired
    SparkCassandraUsersPathsGenerator sparkCassandraUsersPathsGenerator;
    @Autowired
    SparkCassandraUsersPathsByCategoriesGenerator sparkCassandraUsersPathsByCategoriesGenerator;
    @Autowired
    SparkCassandraGetImportantRegion sparkCassandraGetImportantRegion;
    @Autowired
    private UserMovesRepository repository;
    @Autowired
    private PlaceRepository placeRepository;


    public static void main(String[] args) {
        SpringApplication.run(IlocatorApplication.class, args);
    }

    public void run(String... args) throws Exception {


        this.repository.deleteAll();
        this.placeRepository.deleteAll();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date date = calendar.getTime();
        //String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
     
        
        
        // save a UserMoves
        // 2017-06-25-15:10:04-1 1
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-11", "mobile1", 4, "HM", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-11", "mobile1", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-11", "mobile1", 4, "ZARA", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-11", "mobile1", 4, "CELIO", "CLOTHES", date, 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-11", "mobile1", 4, "SEPHORA", "COSMETICS", date, 132));

        // 2017-06-25-15:10:04-1 2
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-12", "mobile2", 4, "HM", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-12", "mobile2", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-12", "mobile2", 4, "ZARA", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-12", "mobile2", 4, "CELIO", "CLOTHES", date, 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-12", "mobile2", 4, "SEPHORA", "COSMETICS", date, 132));

        // 2017-06-25-15:10:04-1 3
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-13", "mobile3", 4, "HM", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-13", "mobile3", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-13", "mobile3", 4, "ZARA", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-13", "mobile3", 4, "CELIO", "CLOTHES", date, 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-13", "mobile3", 4, "SEPHORA", "COSMETICS", date, 132));

        // 2017-06-25-15:10:04-1 4
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-14", "mobile4", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-14", "mobile4", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-14", "mobile4", 4, "HM", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-14", "mobile4", 4, "CELIO", "CLOTHES", date, 323));

        // 2017-06-25-15:10:04-1 5
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-15", "mobile5", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-15", "mobile5", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-15", "mobile5", 4, "HM", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-15", "mobile5", 4, "CELIO", "CLOTHES", date, 323));

        // 2017-06-25-15:10:04-1 6
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-16", "mobile6", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-16", "mobile6", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-16", "mobile6", 4, "HM", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-16", "mobile6", 4, "CELIO", "CLOTHES", date, 323));

        // 2017-06-25-15:10:04-1 7
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-17", "mobile7", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-17", "mobile7", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-17", "mobile7", 4, "HM", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-17", "mobile7", 4, "CELIO", "CLOTHES", date, 323));

        // 2017-06-25-15:10:04-1 8
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-18", "mobile8", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-18", "mobile8", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-18", "mobile8", 4, "HM", "CLOTHES", date, 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-18", "mobile8", 4, "CELIO", "CLOTHES", date, 323));

        // 2017-06-25-15:10:04-1 9
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-19", "mobile9", 4, "HM", "COSMETICS", date, 132));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-19", "mobile9", 4, "ZARA", "CLOTHES", date, 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-19", "mobile9", 4, "MCDO", "FOOD", date, 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-19", "mobile9", 4, "CELIO", "CLOTHES", date, 223));

        // 2017-06-25-15:10:04-1 10
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-110", "mobile10", 4, "HM", "CLOTHES", date, 900));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-110", "mobile10", 4, "ZARA", "CINEMA", date, 220));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-110", "mobile10", 4, "MCDO", "CLOTHES", date, 10));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "2017-06-25-15:10:04-110", "mobile10", 4, "UGC", "CINEMA", date, 112));


        this.placeRepository.save(new Place("SEPHORA", 4, "COSMETICS", 1, 2, 0));
        this.placeRepository.save(new Place("HM", 4, "CLOTHES", 3, 4, 0));
        this.placeRepository.save(new Place("ZARA", 4, "CLOTHES", 8, 19, 0));
        this.placeRepository.save(new Place("CELIO", 4, "CLOTHES", 5, 6, 0));
        this.placeRepository.save(new Place("UGC", 4, "CINEMA", 16, 9, 0));
        this.placeRepository.save(new Place("MCDO", 4, "FOOD", 9, 12, 0));

        //sparkCassandraGetImportantRegion.updatePlacesWithImportantVisit();


    }
}
