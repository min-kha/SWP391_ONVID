package com.codevui.todoapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codevui.todoapp.Service.impl.ToDoServiceImpl;

@Controller
public class MainController {
    @Autowired
    ToDoServiceImpl toDoService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello" ;
    }

    
}
