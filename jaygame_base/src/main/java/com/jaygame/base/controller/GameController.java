package com.jaygame.base.controller;

import com.jaygame.base.pojo.Game;
import com.jaygame.base.service.GameService;
import com.sun.org.apache.regexp.internal.RE;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/game")
@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping(value="/all")
    public Result getAllGame(){
        List<Game> gameInfo = gameService.getGameInfo();
        return new Result(true, StatusCode.OK, "all games", gameInfo);
    }

    @GetMapping(value="/detail")
    public Result getGameDetail(@RequestParam("game_id") String gameId){
        Integer id = Integer.parseInt(gameId);
        Game gameDetail = gameService.getGameDetail(id);
        System.out.println(gameDetail.toString());
        return new Result(true, StatusCode.OK, "game detail",gameDetail);
    }
}
