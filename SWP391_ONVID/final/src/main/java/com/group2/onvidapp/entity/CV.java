package com.group2.onvidapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

// TeacherID(FK, UQ): int
// StaffID(FK, UQ): int
// Status: int
@Entity
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CvId;

    private Date date;
    private String pdfLink;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private User staff;
}
