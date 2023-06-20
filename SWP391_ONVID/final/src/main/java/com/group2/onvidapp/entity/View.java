package com.group2.onvidapp.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Builder

// ViewID(PK): int
// CourseID(FK):int
// Date: date
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int viewId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;

    private Date date;
}
