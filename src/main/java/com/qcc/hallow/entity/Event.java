package com.qcc.hallow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Cacheable(value = false)
@Table(name = "EVENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event extends EntityModel {
    private String name;
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean recurring;
    @OneToMany(mappedBy = "event")
    private List<Attendance> attendanceList;
}
