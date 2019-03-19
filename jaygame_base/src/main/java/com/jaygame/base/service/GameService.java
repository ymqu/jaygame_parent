package com.jaygame.base.service;

import com.jaygame.base.dao.GameDao;
import com.jaygame.base.pojo.Game;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameService {
    @Autowired
    private GameDao gameDao;

    public List<Game> getGameInfo() {
        return gameDao.findAll();
    }

    public Game getGameDetail(Integer game_id){
        return gameDao.getOne(game_id);
    }
}
