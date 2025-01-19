package com.qcc.hallow.entity;

import com.qcc.hallow.entity.enums.PersonStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "PERSON")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person extends EntityModel {
    private String name;
    private String email;
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String region;
    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    @Lob
    private byte[] image = new byte[0];

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(mappedBy = "person")  // The 'person' field in Attendance
    private Attendance attendance;

}
