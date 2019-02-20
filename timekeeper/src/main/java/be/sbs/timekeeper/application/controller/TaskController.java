package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.exception.BadRequestException;
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
@RequestMapping
public class TaskController {
	
	private final TaskService taskService;
	private final ProjectService projectService;

	public TaskController(TaskService taskService, ProjectService projectService) {
		this.taskService = taskService;
		this.projectService = projectService;
	}


    //---- GET ------------------------------------------------------------------------------------
    @GetMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAll(){
		List<Task> all = taskService.getAll();
		return all;
	}

    @GetMapping(path = "/tasks/{projectId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getAllTaskFromProject(@PathVariable String projectId){
		//check if project exists
		Project project = projectService.getById(projectId);

		//if project exists get all the tasks from the project
		return taskService.getAllTasksFromProject(project);
	}

    @GetMapping(path = "/task/{taskId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Task getById(@PathVariable String taskId) {
		return taskService.getById(taskId);
	}

    @GetMapping(path = "/task/_statuses")
    public TaskStatusListResponse getListOfStatuses() {
        return new TaskStatusListResponse(Arrays.asList(TaskStatus.values()));
    }

    @GetMapping(path = "/task/_priorities")
    public PrioritiesListResponse getListOfPriorities() {
        return new PrioritiesListResponse(Arrays.asList(Priority.values()));
    }

    //---- POST -----------------------------------------------------------------------------------
    @PostMapping(path = "/task", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) {
        if (task.getProjectId() == null) throw new BadRequestException("The project id cannot be null");
    	
        //TODO this not right, a method result should always be used
        projectService.getById(task.getProjectId());
        taskService.addTask(task);
    }

    //---- PATCH ----------------------------------------------------------------------------------
    @PatchMapping(path = "/task/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void applyPatch(@PathVariable String taskId, @RequestBody PatchOperation patchOperations) {
        taskService.applyPatch(taskId, patchOperations);
    }

    //---- PUT ------------------------------------------------------------------------------------
    @PutMapping(path = "/task")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    //---- DELETE ---------------------------------------------------------------------------------
    @DeleteMapping(path = "/task/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
    }

}
