package com.ucergy.ilocator.service.impl;

import com.ucergy.ilocator.model.UserMoves;
import com.ucergy.ilocator.repository.UserMovesRepository;
import com.ucergy.ilocator.service.userMovesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by l_a_s on 02/06/2017.
 */
public class userMovesServiceImpl implements userMovesService {

    @Autowired
    UserMovesRepository userMovesRepository;

    @Override
    public List<UserMoves> findAllUserMoves() {

        return userMovesRepository.findAll();
    }

    @Override
    public void saveUserMoves(UserMoves userMoves) {

        userMovesRepository.save(userMoves);

    }

    @Override
    public List<UserMoves> findUserMovesBySession(String sessionId) {

        return userMovesRepository.findAllBySessionId(sessionId);

    }
}
