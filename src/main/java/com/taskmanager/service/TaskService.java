package com.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	  public TaskService(TaskRepository taskRepository) {
	        this.taskRepository = taskRepository;
	    }

	  public Page<Task> getAllTasks(String keyword, String status, String sortField, String sortDirection, int page, int size) {
	      Sort sort = Sort.by(sortField)  ;
	      sort = sortDirection.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
	      Pageable pageable = PageRequest.of(page, size, sort); 
	      
	      if(keyword !=null && !keyword.isEmpty()) {
	    	return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);  
	      }else if(status !=null && !status.isEmpty()) {
	    	  return taskRepository.findByStatus(status, pageable);	    	  
	      }else {
	           return taskRepository.findAll(pageable);
	      }
		 
	 }
	  	
	//Create Task
	public Task createTask(Task task) {
	     return taskRepository.save(task);
	 }
    
    // Save task
	 public Task saveTask(Task task) { 
		 return taskRepository.save(task); 
	}
	 
	public Task getTaskById(Long id) {
	        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
	}

    // Delete task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
