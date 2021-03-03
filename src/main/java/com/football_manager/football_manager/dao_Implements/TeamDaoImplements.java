package com.football_manager.football_manager.dao_Implements;

import com.football_manager.football_manager.dao.PlayerDao;
import com.football_manager.football_manager.dao.TeamDao;
import com.football_manager.football_manager.model.Player;
import com.football_manager.football_manager.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TeamDaoImplements implements TeamDao {
    private EntityManager entityManager;
    private PlayerDao playerDao;

    @Autowired
    public TeamDaoImplements(EntityManager entityManager, PlayerDao playerDao) {
        this.entityManager = entityManager;
        this.playerDao = playerDao;
    }

    @Override
    @Transactional
    public void addTeam(Team team){
        String query="insert into Team(name,country,city,budget) values(?,?,?,?)";
        entityManager.createNativeQuery(query)
                .setParameter(1,team.getName())
                .setParameter(2,team.getCountry())
                .setParameter(3,team.getCity())
                .setParameter(4,team.getBudget())
                .executeUpdate();
    }

    @Override
    @Transactional
    public Team getTeamById(long id){
        Team team=entityManager.createQuery("select team from Team team where team.id=:id",Team.class).setParameter("id",id).getSingleResult();
        return team;
    }
    @Override
    @Transactional
    public List<Team>getAll(){
        List<Team> teams= entityManager.createQuery("select team from Team team",Team.class).getResultList();
        return teams;
    }

    @Transactional
    @Override
    public void updateTeamById(long id,Team team) {
        Team updateTeam=entityManager.find(Team.class,id);
        updateTeam.setName(team.getName());
        updateTeam.setCountry(team.getCountry());
        updateTeam.setCity(team.getCity());
        updateTeam.setBudget(team.getBudget());
        entityManager.persist(updateTeam);
    }

    @Override
    @Transactional
    public String deleteTeamById(long id){
        entityManager.createNativeQuery("delete from Team where id=:id").setParameter("id",id).executeUpdate();
        return "Team with ID= "+id+" was deleted!";
    }

    @Override
    @Transactional
    public void transfer(long player_id, long buyTeamId, long sellTeamId,int commission) {
        Team team_buy=entityManager.find(Team.class,buyTeamId);
        Team team_sell=entityManager.find(Team.class,sellTeamId);
        Player p=playerDao.getPlayerById(player_id);
        double costForPlayer=transferCost(commission, p);

        if (team_buy.getBudget()>costForPlayer){

            entityManager.createQuery("update Team set budget=:newBudget where id=:id")
                    .setParameter("newBudget",team_buy.getBudget()-costForPlayer)
                    .setParameter("id",team_buy.getId())
                    .executeUpdate();

            entityManager.createQuery("update Team set budget=:newBudget where id=:id")
                    .setParameter("newBudget",team_sell.getBudget()+costForPlayer)
                    .setParameter("id",team_sell.getId())
                    .executeUpdate();

            entityManager.createQuery("update Player set team=:team_buy where id=:id")
                    .setParameter("team_buy",team_buy)
                    .setParameter("id",player_id)
                    .executeUpdate();
        }else System.out.println("---not enough money---");
    }
    @Override
    public double transferCost(int commission,Player player){
        LocalDate today = LocalDate.now();
        Period age = Period.between(player.getBirthday().toLocalDate(), today);
        int years = age.getYears();
        long experience= ChronoUnit.MONTHS.between(
                player.getStart_career().toLocalDate().withDayOfMonth(1),
                today.withDayOfMonth(1));
        double sum=experience * 10000/years;
        double com=(sum*commission)/100;
        return com+sum;
    }

    @Override
    @Transactional
    public void terminateTheContract(long player_id) {
        entityManager.createQuery("update Player set team.id=:newId where id=:id")
                .setParameter("newId",null)
                .setParameter("id",player_id)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void addFreePlayer(long player_id,long team_id) {
        System.out.println("player"+player_id);
        System.out.println("team"+team_id);
        Team team=entityManager.find(Team.class,team_id);
        entityManager.createQuery("update Player set team=:newTeam where id=:id")
                .setParameter("newTeam",team)
                .setParameter("id",player_id)
                .executeUpdate();
    }

}
