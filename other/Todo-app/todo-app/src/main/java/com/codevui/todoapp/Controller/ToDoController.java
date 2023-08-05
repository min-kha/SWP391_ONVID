package com.codevui.todoapp.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codevui.todoapp.Entity.ToDo;
import com.codevui.todoapp.Service.ToDoService;

@Controller
public class ToDoController {

    @Autowired
    ToDoService toDoService;
    @RequestMapping("/list-todo")
    public String listToDo(Model model){
        List<ToDo> lists = toDoService.getAllTodo();
        model.addAttribute("listTodos", lists);
        return "listToDo";
    }
    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(HttpServletRequest req){
        String title = req.getParameter("title");
        toDoService.addTodo(title);
        return "redirect:/list-todo";
    }

    @GetMapping("/delete-todo")
    public String deleteToDo(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        toDoService.deleteToDo(id);
        return "redirect:/list-todo";
    }
    @GetMapping("/complete-todo")
    public String completeToDo(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        toDoService.completeToDo(id);
        return "redirect:/list-todo";
    }
}
