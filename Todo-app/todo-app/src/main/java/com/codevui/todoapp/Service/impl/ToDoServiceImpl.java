package com.codevui.todoapp.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codevui.todoapp.Entity.ToDo;
import com.codevui.todoapp.Respository.ToDoRespository;
import com.codevui.todoapp.Service.ToDoService;

@Service
public class ToDoServiceImpl implements ToDoService {
    public static int increase = 0;

    @Autowired
    ToDoRespository toDoRespository;
    public void addTodo(String title) {
        ToDo todo = new ToDo();
        todo.setTitle(title);
        toDoRespository.save(todo);
    }
    public List<ToDo> getAllTodo() {
       return toDoRespository.findAll();
    }
    public void deleteToDo(int id) {
        toDoRespository.deleteById(id);
    }
    public void completeToDo(int id) {
        ToDo oldToDo = toDoRespository.findById(id).get();
        oldToDo.setCompleted(!oldToDo.isCompleted());
        toDoRespository.save(oldToDo);
    }
    
}
