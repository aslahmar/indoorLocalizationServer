package com.ucergy.ilocator;


import com.datastax.driver.core.utils.UUIDs;
import com.ucergy.ilocator.model.UserMoves;
import com.ucergy.ilocator.repository.UserMovesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class IlocatorApplication implements CommandLineRunner {

	@Autowired
	private UserMovesRepository repository;

	public void run(String... args) throws Exception {
		this.repository.deleteAll();

		// save a UserMoves
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile1","floor1","region1", "Food", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile2","floor1","region1", "food", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile3","floor1","region2", "shopping", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile4","floor1","region3", "clothes", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile5","floor1","region1", "bank", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile1","floor1","region2", "sport", Calendar.getInstance().getTime(), 12 ));
		this.repository.save(new UserMoves(UUIDs.timeBased(),"mobile1","floor1","region3", "bank", Calendar.getInstance().getTime(), 12 ));

		// fetch all userMoves
		System.out.println("UserMoves found with findAll():");
		System.out.println("-------------------------------");
		for (UserMoves userMoves : this.repository.findAll()) {
			System.out.println(userMoves.getCategory());
		}
		System.out.println();

	}


	public static void main(String[] args) {
		SpringApplication.run(IlocatorApplication.class, args);
	}
}
