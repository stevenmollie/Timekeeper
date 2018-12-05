package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.service.ProjectService;
import be.sbs.timekeeper.application.service.TaskService;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import be.sbs.timekeeper.application.valueobjects.PrioritiesListResponse;
import be.sbs.timekeeper.application.valueobjects.TaskStatusListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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


    //---- GET ------------------------------------------------------------------------------------
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAll(){
		List<Task> all = taskService.getAll();
		return all;
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

    @GetMapping(path = "/_statuses")
    public TaskStatusListResponse getListOfStatuses() {
        return new TaskStatusListResponse(Arrays.asList(TaskStatus.values()));
    }

    @GetMapping(path = "/_priorities")
    public PrioritiesListResponse getListOfPriorities() {
        return new PrioritiesListResponse(Arrays.asList(Priority.values()));
    }

    //---- POST -----------------------------------------------------------------------------------
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTAsk(@RequestBody Task task) {
        //TODO this not right, a method result should always be used
        projectService.getById(task.getProjectId());
        taskService.addTask(task);
    }

    //---- PATCH ----------------------------------------------------------------------------------
    @PatchMapping(path = "/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void applyPatch(@PathVariable String taskId, @RequestBody PatchOperation patchOperations) {
        taskService.applyPatch(taskId, patchOperations);
    }

    //---- PUT ------------------------------------------------------------------------------------
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    //---- DELETE ---------------------------------------------------------------------------------
    @DeleteMapping(path = "/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
    }

}
