package com.ucergy.ilocator.service;

import com.ucergy.ilocator.model.UserMoves;

import java.util.List;
import java.util.Map;

/**
 * Created by l_a_s on 02/06/2017.
 */
public interface userMovesService {

    List<UserMoves> findAllUserMoves();

    void saveUserMoves(UserMoves userMoves);

    List<UserMoves> findUserMovesBySession(String Session);

}
