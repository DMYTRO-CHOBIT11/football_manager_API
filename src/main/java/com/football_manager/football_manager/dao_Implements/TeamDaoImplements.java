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
import java.time.Period;
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
        System.out.println(player_id+" "+buyTeamId+" "+sellTeamId+" "+commission);
        Team team_buy=entityManager.find(Team.class,buyTeamId);
        System.out.println(team_buy.getName());
        Team team_sell=entityManager.find(Team.class,sellTeamId);
        Player p=playerDao.getPlayerById(player_id);
        System.out.println("Player= "+p.getFull_name());
        double costForPlayer=transferCost(commission, player_id);
        System.out.println(costForPlayer);
        team_buy.setBudget(team_buy.getBudget()-costForPlayer);
        team_sell.getPlayers().remove(p);
        team_sell.setBudget(team_sell.getBudget()+costForPlayer);
        team_buy.getPlayers().add(p);
        entityManager.persist(team_buy);
        entityManager.persist(team_sell);
        p.setTeam(team_buy);
        entityManager.createQuery("update Player set team=:team_buy where id=:id")
                .setParameter("team_buy",team_buy)
                .setParameter("id",player_id)
                .executeUpdate();
    }
    @Override
    public double transferCost(int commission,long player_id){
        Player player=playerDao.getPlayerById(player_id);
        LocalDate today = LocalDate.now();
        Period age = Period.between(player.getBirthday().toLocalDate(), today);
        Period period=Period.between(player.getStart_career().toLocalDate(),today);
        int years = age.getYears();
        int experience=period.getMonths();
        System.out.println("Age "+years);
        double sum=experience * 10000/years;
        System.out.println("sum "+sum);
        double com=(sum*commission)/100;
        System.out.println(commission);
        System.out.println("com "+com);
        return com+sum;
    }

}
