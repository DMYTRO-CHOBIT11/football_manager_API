package com.football_manager.football_manager.dao_Implements;

import com.football_manager.football_manager.dao.PlayerDao;
import com.football_manager.football_manager.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerDaoImplements implements PlayerDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addPlayer(Player player) {
        String query="insert into Player(full_name,birthday,start_career) values(?,?,?)";
        entityManager.createNativeQuery(query)
                .setParameter(1,player.getFull_name())
                .setParameter(2,player.getBirthday())
                .setParameter(3,player.getStart_career())
                .executeUpdate();
    }

    @Override
    public Player getPlayerById(long id) {
//        Player player=entityManager.createQuery("select player from Player player where player.id=:id",Player.class)
//                .setParameter("id",id).getSingleResult();
        Player player=entityManager.find(Player.class,id);
        return player;
    }

    @Override
    public List<Player> getAll() {
        List<Player> players= entityManager.createQuery("select player from Player player",Player.class).getResultList();
        return players;
    }

    @Override
    @Transactional
    public void updatePlayerById(long id, Player player) {
        Player updatePlayer=entityManager.find(Player.class,id);
        updatePlayer.setFull_name(player.getFull_name());
        updatePlayer.setBirthday(player.getBirthday());
        entityManager.persist(updatePlayer);
    }

    @Override
    @Transactional
    public String deletePlayerById(long id) {
        entityManager.createNativeQuery("delete from Player where id=:id",Player.class)
                .setParameter("id",id).executeUpdate();
        return "Player with Id= "+id+" was deleted!";
    }

    @Override
    public List<Player> freePlayers() {
        return getAll().stream().filter(player -> {
            if (player.getTeam()==null){
                return true;
            }return false;
        }).collect(Collectors.toList());
    }
}
