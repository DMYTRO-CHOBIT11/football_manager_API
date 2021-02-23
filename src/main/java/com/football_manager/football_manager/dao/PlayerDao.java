package com.football_manager.football_manager.dao;

import com.football_manager.football_manager.model.Player;

import java.util.List;

public interface PlayerDao {
    void addPlayer(Player player);
    Player getPlayerById(long id);
    List<Player> getAll();
    void updatePlayerById(long id, Player player);
    String deletePlayerById(long id);
}
