package com.qcc.hallow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ROLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends EntityModel {

    private String name;
    
    @ManyToMany
    @JoinTable(
        name = "role_team",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;

    @OneToMany(mappedBy = "role")
    private List<Person> personList;
}
