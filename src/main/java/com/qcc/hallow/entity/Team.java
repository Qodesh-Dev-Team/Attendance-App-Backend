package com.qcc.hallow.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TEAM")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team extends EntityModel {

    private String name;
    @ManyToMany(mappedBy = "teams", cascade = CascadeType.ALL)
    private List<Role> roles;
}
