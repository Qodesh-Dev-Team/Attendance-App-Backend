package com.qcc.hallow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ATTENDANCE")
@Cacheable(false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance extends EntityModel {

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @OneToOne
    @JoinColumn(name = "person_id")  // Foreign key to Person
    private Person person;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean present;
}
