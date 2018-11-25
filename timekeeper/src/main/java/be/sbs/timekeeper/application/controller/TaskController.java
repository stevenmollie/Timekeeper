package be.sbs.timekeeper.application.controller;

import java.util.List;

import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
		//TODO this not right, a method result should always be used
		projectService.getById(task.getProjectId());
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

	@PatchMapping(path = "/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apply(@PathVariable String taskId, @RequestBody PatchOperation patchOperations) {
		taskService.updateTask(taskId, patchOperations);
	}
}
