package com.codevui.todoapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codevui.todoapp.Entity.ToDo;

@Service    
public interface ToDoService {
    public void addTodo(String title);

    public List<ToDo> getAllTodo();

    public void deleteToDo(int id);

    public void completeToDo(int id);
}
