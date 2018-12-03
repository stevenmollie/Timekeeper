package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.exception.TaskNotFoundException;
import be.sbs.timekeeper.application.repository.TaskRepository;
import be.sbs.timekeeper.application.repository.TaskRepositoryCustom;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskRepositoryCustom taskRepositoryCustom;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void addTask(Task task) {
        if (task.getName() == null || task.getName().isEmpty() || task.getId() != null || task.getProjectId() == null)
            throw new BadRequestException("cannot create " + task.toString());
        taskRepository.insert(task);
    }

    public List<Task> getAllTasksFromProject(Project p) {
        return taskRepositoryCustom.findTasksByProjectId(p.getId());
    }

    public Task findById(String taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public void applyPatch(String taskId, PatchOperation patchOperations) {
        if (!FieldValidator.isValidTaskPatchRequest(patchOperations))
            throw new BadRequestException("Patch not permitted for these values.");
        taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Cannot patch task: " + taskId + ". the task doesn't exist!"));
        taskRepositoryCustom.saveOperation(taskId, patchOperations);
    }

    public void updateTask(Task task) {
        taskRepository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException("Cannot update task: " + task.getId() + ". the task doesn't exist!"));
        taskRepository.save(task);
    }
}
