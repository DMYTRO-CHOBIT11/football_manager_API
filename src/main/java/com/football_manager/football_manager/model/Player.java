package com.football_manager.football_manager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String full_name;
    private Date birthday;
    private Date start_career;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
