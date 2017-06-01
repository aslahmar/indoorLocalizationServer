package com.ucergy.ilocator.repository;

import cassandra.Customer;
import com.ucergy.ilocator.model.UserMoves;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by l_a_s on 01/06/2017.
 */
public interface UserMovesRepository extends CrudRepository <UserMoves, String> {

    //@Query("Select * from customer where firstname=?0")
    public UserMovesRepository findByDateAndMobileId(Date date, String mobileId);

}
