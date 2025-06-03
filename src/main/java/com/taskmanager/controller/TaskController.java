package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;

@Controller
@RequestMapping("/tasks")	
public class TaskController {
	
		@Autowired
	   private final TaskService taskService;

	   public TaskController(TaskService taskService) {
	        this.taskService = taskService;
	   }
			
		/*
		 * @GetMapping("/login") public String showLoginPage(@RequestParam(value =
		 * "error", required = false) String error, Model model) { if (error != null) {
		 * model.addAttribute("error", "Invalid username or password"); } return
		 * "login"; // This returns login.html }
		 */
	   
	   @GetMapping
	    public String listTasks(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(required = false) String keyword,
	            @RequestParam(required = false) String status,
	            @RequestParam(defaultValue = "title") String sortField,
	            @RequestParam(defaultValue = "asc") String sortDirection,
	            Model model) {

	        Page<Task> taskPage = taskService.getAllTasks(keyword, status, sortField, sortDirection, page, size);

	        model.addAttribute("tasks", taskPage.getContent());
	        model.addAttribute("totalPages", taskPage.getTotalPages());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("sortField", sortField);
	        model.addAttribute("sortDirection", sortDirection);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("status", status);

	        return "task";
	    }
	   
		  // Show task form for adding new task	  
		  @GetMapping("/new") 
		  public String showCreateTaskPage(Model model) {
			  model.addAttribute("task", new Task()); 
		  return "newTask"; 
		  }
		

		  @PostMapping("/save")
		  public String saveTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
		      taskService.saveTask(task);
		      redirectAttributes.addFlashAttribute("successMessage", "Task saved successfully!");
		      return "redirect:/tasks";
		  }
			
		  @GetMapping("/edit/{id}")
		  public String showEditForm(@PathVariable Long id, Model model) {
		      Task task = taskService.getTaskById(id);
		      if (task == null) {
		          return "redirect:/tasks?error=TaskNotFound"; // Redirect with an error message
		      }
		      model.addAttribute("task", task);
		      return "newTask";
		  }

		  @GetMapping("/delete/{id}")
		  public String deleteTask(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		      taskService.deleteTask(id);
		      redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
		      return "redirect:/tasks";
		  }

}
