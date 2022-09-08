package com.gowthum.springboot.learnspringboot.todo;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

//@Controller
@SessionAttributes("name")
public class TodoController {
	
	private Todoservice todoService;
	
	TodoController(Todoservice todoService){
		this.todoService=todoService;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model){
		List<Todo> todos = todoService.findByUsername(getLoggedInUsername());
		model.addAttribute("todos", todos);
		return "listTodos";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap model){
		Todo todo=new Todo(0, getLoggedInUsername(), "", LocalDate.now(), false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		if(result.hasErrors()){
			return "todo";
		}
		todoService.addTodo((String)model.get("name"), todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id){
		todoService.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodo(@RequestParam int id,ModelMap model){
		Todo todo = todoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		if(result.hasErrors()){
			return "todo";
		}
		todo.setUsername(getLoggedInUsername());
		todoService.updateTodo(todo);
		return "redirect:list-todos";
	}
	
	private String getLoggedInUsername(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}