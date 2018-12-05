package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.exception.TaskNotFoundException;
import be.sbs.timekeeper.application.repository.TaskRepository;
import be.sbs.timekeeper.application.repository.TaskRepositoryCustom;
import be.sbs.timekeeper.application.valueobjects.FieldConverter;
import be.sbs.timekeeper.application.valueobjects.FieldValidator;
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

    public List<Task> getAllTasksFromProject(Project p) {
        return taskRepositoryCustom.findTasksByProjectId(p.getId());
    }

    public Task findById(String taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public void addTask(Task task) {
        FieldConverter.convertTaskFields(task);
        FieldValidator.validatePOSTTask(task);
        taskRepository.insert(task);
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
        taskRepository.save(task);
    }

    public void deleteTask(String taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task : " + taskId + " doesn't exist!"));
        taskRepository.deleteById(taskId);
    }
}
