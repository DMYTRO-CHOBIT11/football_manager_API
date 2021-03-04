package com.football_manager.football_manager.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Input player name")
    private String full_name;

//    @NotEmpty(message = "Input date of birthday")
    private Date birthday;

//    @NotEmpty(message = "Input date of start career ")
    private Date start_career;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
