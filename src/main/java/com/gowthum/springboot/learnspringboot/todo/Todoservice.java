package com.gowthum.springboot.learnspringboot.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class Todoservice {
	private static List<Todo> todos=new ArrayList<>();
	private static int todoCount=0;
	static {
		todos.add(new Todo(++todoCount, "Gowthum", "Learn Core Java", LocalDate.now(), false));
		todos.add(new Todo(++todoCount, "Gowthum", "Learn Spring Boot", LocalDate.now(), false));
		todos.add(new Todo(++todoCount, "Gowthum", "Learn Angular", LocalDate.now(), false));
	}
	
	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate = 
				todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public void addTodo(String username,String description,LocalDate targetDate, boolean done){
		Todo todo=new Todo(++todoCount, username, description, targetDate, done);
		todos.add(todo);
	}
	
	public void deleteById(int id){
		Predicate<? super Todo> filter=todo -> todo.getId()==id;
		todos.removeIf(filter);
	}

	public Todo findById(int id) {
		Predicate<? super Todo> filter=todo -> todo.getId()==id;
		return todos.stream().filter(filter).findFirst().get();
	}

	public void updateTodo(Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}
}
