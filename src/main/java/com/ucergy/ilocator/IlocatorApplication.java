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

import java.util.Calendar;


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

        // save a UserMoves
        // Session 1
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session1", "mobile1", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session1", "mobile1", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session1", "mobile1", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session1", "mobile1", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session1", "mobile1", 4, "Sephora", "Cosmetic", Calendar.getInstance().getTime(), 132));

        // Session 2
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session2", "mobile2", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session2", "mobile2", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session2", "mobile2", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session2", "mobile2", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session2", "mobile2", 4, "Sephora", "Cosmetic", Calendar.getInstance().getTime(), 132));

        // Session 3
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session3", "mobile3", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session3", "mobile3", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session3", "mobile3", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session3", "mobile3", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session3", "mobile3", 4, "Sephora", "Cosmetic", Calendar.getInstance().getTime(), 132));

        // Session 4
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session4", "mobile4", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session4", "mobile4", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session4", "mobile4", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session4", "mobile4", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));

        // Session 5
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session5", "mobile5", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session5", "mobile5", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session5", "mobile5", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session5", "mobile5", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));

        // Session 6
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session6", "mobile6", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session6", "mobile6", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session6", "mobile6", 4, "Sephora", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session6", "mobile6", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));

        // Session 7
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session7", "mobile7", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session7", "mobile7", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session7", "mobile7", 4, "Sephora", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session7", "mobile7", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));

        // Session 8
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session8", "mobile8", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session8", "mobile8", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session8", "mobile8", 4, "Sephora", "Clothes", Calendar.getInstance().getTime(), 223));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session8", "mobile8", 4, "Zara", "Clothes", Calendar.getInstance().getTime(), 323));

        // Session 9
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session9", "mobile9", 4, "H&M", "Cosmetic", Calendar.getInstance().getTime(), 132));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session9", "mobile9", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 156));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session9", "mobile9", 4, "Mcdo", "Food", Calendar.getInstance().getTime(), 123));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session9", "mobile9", 4, "Celio", "Clothes", Calendar.getInstance().getTime(), 223));

        // Session 10
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "H&M", "Clothes", Calendar.getInstance().getTime(), 900));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "BNP", "Bank", Calendar.getInstance().getTime(), 324));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "Sephora", "bank", Calendar.getInstance().getTime(), 220));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "H&M", "CLothes", Calendar.getInstance().getTime(), 10));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "BNP", "Bank", Calendar.getInstance().getTime(), 120));
        this.repository.save(new UserMoves(UUIDs.timeBased(), "Session10", "mobile10", 4, "UGC", "Cinema", Calendar.getInstance().getTime(), 112));


        this.placeRepository.save(new Place("Sephora", 4, "Cosmetic", 1, 2, 0));
        this.placeRepository.save(new Place("H&M", 4, "Clothes", 3, 4, 0));
        this.placeRepository.save(new Place("Zara", 4, "Clothes", 8, 19, 0));
        this.placeRepository.save(new Place("Celio", 4, "Clothes", 5, 6, 0));
        this.placeRepository.save(new Place("UGC", 4, "Cinema", 16, 9, 0));
        this.placeRepository.save(new Place("Mcdo", 4, "Food", 9, 12, 0));

        //sparkCassandraGetImportantRegion.updatePlacesWithImportantVisit();


    }
}
