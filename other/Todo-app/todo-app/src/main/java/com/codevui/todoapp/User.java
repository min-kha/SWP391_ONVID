package com.codevui.todoapp;


import lombok.Builder;

import lombok.ToString;

@Builder
@ToString
public class User {
    private int id;
    private String name;
    private int age;
    private double salary;
}
