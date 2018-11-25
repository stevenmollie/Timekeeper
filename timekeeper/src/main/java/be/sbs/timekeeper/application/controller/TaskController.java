package be.sbs.timekeeper.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;//moet erbij voor testing (GDG20181125)

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.service.ProjectService;
import be.sbs.timekeeper.application.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping(path = "/Task")
public class TaskController {
	
	private final TaskService taskService;
	private final ProjectService projectService;

	public TaskController(TaskService taskService, ProjectService projectService) {
		this.taskService = taskService;
		this.projectService = projectService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAll(){
		return taskService.getAll();
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addTAsk(@RequestBody Task task) {
		Project p = projectService.getById(task.getProjectId());
		taskService.addTask(task);
	}
	
	@GetMapping(path = "/GetTasksFromProject/{projectId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAllTaskFromProject(@PathVariable String projectId){
		//check if project exists
		Project p = projectService.getById(projectId);
		
		//if project exists get all the tasks from the project
		return taskService.getAllTasksFromProject(p);
	}
	
	@GetMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Task findById(@PathVariable String taskId) {
		return taskService.findById(taskId);
	}
	
	
}
