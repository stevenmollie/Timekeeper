package be.sbs.timekeeper.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.exception.ProjectNotFoundException;
import be.sbs.timekeeper.application.exception.TaskNotFoundException;
import be.sbs.timekeeper.application.repository.TaskRepository;
import be.sbs.timekeeper.application.repository.TaskRepositoryCustom;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskRepositoryCustom taskRepositoryCustom;
	
	public List<Task> getAll(){
		return taskRepository.findAll();
	}
	
	public void addTask(Task task) {
		taskRepository.insert(task);
	}
	
	public List<Task> getAllTasksFromProject(Project p){
		return taskRepositoryCustom.findTasksByProjectId(p.getId());
	}
	
	public Task findById(String taskId) {
		return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
	}
}
