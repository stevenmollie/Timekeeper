package be.sbs.timekeeper.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
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

	public void updateTask(String taskId, PatchOperation patchOperations) {
		if (!isValidPatchRequest(patchOperations))
			throw new BadRequestException("Patch not permitted for these values.");
		taskRepositoryCustom.saveOperation(taskId, patchOperations);
	}

	private boolean isValidPatchRequest(PatchOperation operations) {
		try{
			LocalDate.parse(operations.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		}catch (Exception e){
			return false;
		}
		return operations.getPath().equals("/currentTime")
				&& operations.getOp().equals("replace");
	}
}
