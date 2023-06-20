package com.group2.onvidapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int responseID;
    private int answer;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Question question;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User student;
}
