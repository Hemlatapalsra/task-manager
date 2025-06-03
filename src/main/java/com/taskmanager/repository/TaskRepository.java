package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    Page<Task> findAll(Pageable pageable);
    Page<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, 
    		String description, Pageable pageable);
    
    // Filter by status
    Page<Task> findByStatus(String status, Pageable pageable);

}
