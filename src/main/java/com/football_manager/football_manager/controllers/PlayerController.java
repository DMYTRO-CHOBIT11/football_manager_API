package com.football_manager.football_manager.controllers;

import com.football_manager.football_manager.dao.PlayerDao;
import com.football_manager.football_manager.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class PlayerController {
    @Autowired
    private PlayerDao playerDao;

    @PostMapping("/addPlayer")
    public ResponseEntity addPlayer(@Valid @RequestBody Player player){
        playerDao.addPlayer(player);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getPlayerById/{id}")
    public ResponseEntity getPlayerById(@PathVariable("id") long id){
        return  new ResponseEntity(playerDao.getPlayerById(id),HttpStatus.OK);
    }

    @PutMapping("/updatePlayer/{id}")
    public ResponseEntity updatePlayerById(@PathVariable("id")long id,@Valid @RequestBody Player player){
        playerDao.updatePlayerById(id,player);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/freePlayers")
    public ResponseEntity freePlayers(){
        return new ResponseEntity(playerDao.freePlayers(),HttpStatus.OK);
    }

    @DeleteMapping("/deletePlayer/{id}")
    public ResponseEntity deletePlayerById(@PathVariable("id")long id){
        return new ResponseEntity(playerDao.deletePlayerById(id),HttpStatus.OK);
    }

    @GetMapping("/getAllPlayers")
    public ResponseEntity getAllPlayers(){
        return  new ResponseEntity(playerDao.getAll(),HttpStatus.OK);
    }
}
