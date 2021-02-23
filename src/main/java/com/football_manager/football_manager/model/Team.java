package com.football_manager.football_manager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String country;
    private String city;
    private double budget;
    @OneToMany(mappedBy = "team")
    private Set<Player> players=new HashSet<>();

}
