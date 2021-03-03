package com.football_manager.football_manager.dao;

import com.football_manager.football_manager.model.Player;
import com.football_manager.football_manager.model.Team;

import java.util.List;

public interface TeamDao {
    void addTeam(Team team);
    Team getTeamById(long id);
    List<Team> getAll();
    void updateTeamById(long id,Team team);
    String deleteTeamById(long id);
    void transfer(long player_id,long buyTeamId,long sellTeamId,int commission);
    double transferCost(int commission, Player player);
    void terminateTheContract(long player_id);
    void addFreePlayer(long player_id,long team_id);
}
