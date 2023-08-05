package com.codevui.todoapp.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codevui.todoapp.Entity.ToDo;

@Repository
public interface ToDoRespository extends JpaRepository<ToDo, Integer>{
    
}
