package com.football_manager.football_manager.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Input team name")
    private String name;

    @NotBlank(message = "Input country name")
    private String country;

    @NotBlank(message = "Input city name")
    private String city;

    @DecimalMin(value = "1",message = "Input budget")
    private double budget;

    @OneToMany(mappedBy = "team",orphanRemoval = true)
    private Set<Player> players=new HashSet<>();

}
