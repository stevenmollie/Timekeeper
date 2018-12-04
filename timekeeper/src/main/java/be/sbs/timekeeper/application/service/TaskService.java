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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskRepositoryCustom taskRepositoryCustom;

    private final List<String> PATCHABLE_FIELDS = Arrays.asList("/currentTime", "/deadLine");
    private final List<String> PERMITTED_PATCH_OP = Collections.singletonList("replace");

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void addTask(Task task) {
        validateNewTask(task);
        taskRepository.insert(task);
    }

    private void validateNewTask(Task task) {
        if (task.getName() == null || task.getName().isEmpty() || task.getId() != null || task.getProjectId() == null || task.getPriority() == null)
            throw new BadRequestException("cannot create " + task.toString());
    }

    public List<Task> getAllTasksFromProject(Project p) {
        return taskRepositoryCustom.findTasksByProjectId(p.getId());
    }

    public Task findById(String taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public void applyPatch(String taskId, PatchOperation patchOperations) {
        validatePatchOperation(patchOperations);
        taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Cannot patch task: " + taskId + ". the task doesn't exist!"));
        taskRepositoryCustom.saveOperation(taskId, patchOperations);
    }

    private void validatePatchOperation(PatchOperation patchOperations) {
        if (!isValidTaskPatchRequest(patchOperations))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private boolean isValidTaskPatchRequest(PatchOperation operations) {
        return PATCHABLE_FIELDS.contains(operations.getPath()) && PERMITTED_PATCH_OP.contains(operations.getOp()) && operations.getValue() != null;
    }

    public void updateTask(Task task) {
        if (notAllFieldsPresent(task))
            throw new BadRequestException("Update not permitted for these values.");
        taskRepository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException("Cannot update task: " + task.getId() + ". the task doesn't exist!"));
        taskRepository.save(task);
    }

    private boolean notAllFieldsPresent(Task task) {
        return task.getId() == null
                || task.getName() == null
                || task.getName().trim().isEmpty()
                || task.getDescription() == null
                || task.getCurrentTime() == null
                || task.getPriority() == null
                || task.getProjectId() == null;
    }

}
