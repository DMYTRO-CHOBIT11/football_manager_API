package com.football_manager.football_manager.controllers;

import com.football_manager.football_manager.dao.TeamDao;
import com.football_manager.football_manager.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TeamController {
    @Autowired
    public TeamDao teamDao;

    @PostMapping("/addTeam")
    public ResponseEntity addTeam(@Valid @RequestBody Team team){
        teamDao.addTeam(team);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity getTeamById(@PathVariable("id") long id){
        return  new ResponseEntity(teamDao.getTeamById(id),HttpStatus.OK);
    }

    @PutMapping("/updateTeam/{id}")
    public ResponseEntity updateTeamById(@PathVariable("id")long id,@Valid @RequestBody Team team){
        teamDao.updateTeamById(id,team);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity deleteTeamById(@PathVariable("id")long id){
        return new ResponseEntity(teamDao.deleteTeamById(id),HttpStatus.OK);
    }

    @GetMapping("/transfer/{player_id}/{team_buy_id}/{team_sell_id}/{commission}")
    public ResponseEntity transfer(@PathVariable long player_id,@PathVariable long team_buy_id,
                                 @PathVariable long team_sell_id,@PathVariable int commission){
        teamDao.transfer(player_id, team_buy_id, team_sell_id, commission);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getAllTeams")
    public ResponseEntity getAllTeams(){
        return new ResponseEntity(teamDao.getAll(),HttpStatus.OK);
    }

    @GetMapping("/terminateContract/{id}")
    public ResponseEntity transfer(@PathVariable("id") long player_id){
        teamDao.terminateTheContract(player_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/addFreePlayer/{player_id}/{team_id}")
    public ResponseEntity addFreePlayer(@PathVariable(value = "player_id") long player_id
            ,@PathVariable("team_id")long team_id){
        teamDao.addFreePlayer(player_id, team_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
