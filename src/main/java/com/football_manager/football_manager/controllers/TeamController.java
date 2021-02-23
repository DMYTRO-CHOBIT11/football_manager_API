package com.football_manager.football_manager.controllers;

import com.football_manager.football_manager.dao.TeamDao;
import com.football_manager.football_manager.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TeamController {
    @Autowired
    public TeamDao teamDao;

    @PostMapping("/addTeam")
    public ResponseEntity addTeam(@RequestBody Team team){
        teamDao.addTeam(team);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity getTeamById(@PathVariable("id") long id){
        return  new ResponseEntity(teamDao.getTeamById(id),HttpStatus.OK);
    }
    @PutMapping("/updateTeam/{id}")
    public ResponseEntity updateTeamById(@PathVariable("id")long id,@RequestBody Team team){
        teamDao.updateTeamById(id,team);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity deleteTeamById(@PathVariable("id")long id){
        return new ResponseEntity(teamDao.deleteTeamById(id),HttpStatus.OK);
    }

    @GetMapping("/transfer")
    public ModelAndView method(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @PostMapping("/transfer")
    public ModelAndView transfer(@RequestParam long player_id,long team_buy_id,
                                   long team_sell_id,int commission){
        System.out.println("HERE! "+player_id+" "+team_buy_id+" "+team_sell_id+" "+commission);
        teamDao.transfer(player_id, team_buy_id, team_sell_id, commission);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping("/getAllTeams")
    public ResponseEntity getAllTeams(){
        return  new ResponseEntity(teamDao.getAll(),HttpStatus.OK);
    }
}
