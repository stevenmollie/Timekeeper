package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.enums.ProjectStatus;
import be.sbs.timekeeper.application.exception.TaskNotFoundException;
import be.sbs.timekeeper.application.repository.TaskRepository;
import be.sbs.timekeeper.application.repository.TaskRepositoryCustom;
import be.sbs.timekeeper.application.valueobjects.FieldConverter;
import be.sbs.timekeeper.application.valueobjects.FieldValidator;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskRepositoryCustom taskRepositoryCustom;

    private final ProjectService projectService;

    private final UserService userService;

    public TaskService(TaskRepository taskRepository, TaskRepositoryCustom taskRepositoryCustom, ProjectService projectService, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskRepositoryCustom = taskRepositoryCustom;
        this.projectService = projectService;
        this.userService = userService;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksFromProject(Project project) {
        return taskRepositoryCustom.findTasksByProjectId(project.getId());
    }

    public Task getById(String taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public void addTask(Task task) {
        FieldConverter.setDefaultTaskFields(task);
        FieldValidator.validatePOSTTask(task);
        //check if project exists
        Project project = projectService.getById(task.getProjectId());
        taskRepository.insert(task);
        
        //change the status of the project
        if(project.getStatus() != ProjectStatus.IN_PROGRESS) {
        	project.setStatus(ProjectStatus.READY_TO_START);
        	projectService.updateProject(project);
        }
    }
    
    public void applyPatch(String taskId, PatchOperation patchOperation) {
        FieldValidator.validatePATCHTask(patchOperation);
        FieldConverter.convertTaskFields(patchOperation);
        taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Cannot patch task: " + taskId + ". the task doesn't exist!"));
        taskRepositoryCustom.saveOperation(taskId, patchOperation);
    }

    public void updateTask(Task task) {
        FieldValidator.validatePUTTask(task);
        taskRepository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException("Cannot update task: " + task.getId() + ". the task doesn't exist!"));
        //check if project exists
        projectService.getById(task.getProjectId());
        taskRepository.save(task);
    }

    public void deleteTask(String taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task : " + taskId + " doesn't exist!"));
        taskRepository.deleteById(taskId);
    }
    
    public void deleteTasksFromProject(String projectId) {
    	//check if project exists
    	projectService.getById(projectId);
    	taskRepositoryCustom.deleteTasksFromProject(projectId);
    }

	public void setTaskStatus(String taskId, TaskStatus taskStatus) {
		taskRepositoryCustom.updateTaskStatus(taskId, taskStatus);		
	}
}
