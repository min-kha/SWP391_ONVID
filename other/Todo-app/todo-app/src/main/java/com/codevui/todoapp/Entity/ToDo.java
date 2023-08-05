package com.codevui.todoapp.Entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "Todo")
public class ToDo {
    //dinh nghia id la khoa chinh
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title" , columnDefinition = "nvarchar(MAX)")
    private String title;

    @Column(name = "completed")
    private boolean completed;
}
