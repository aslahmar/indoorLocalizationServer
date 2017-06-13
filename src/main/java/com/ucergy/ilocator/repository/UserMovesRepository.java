package com.ucergy.ilocator.repository;

import com.ucergy.ilocator.model.UserMoves;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by l_a_s on 01/06/2017.
 */
@Repository
public interface UserMovesRepository extends CrudRepository <UserMoves, UUID> {

    //@Query("Select * from customer where firstname=?0")
    public UserMovesRepository findByDateAndMobileId(Date date, String mobileId);

    @Query("Select * from usermoves ")
    public List<UserMoves> findAll();

    public List<UserMoves> findAllBySessionId(String sessionId);

}
