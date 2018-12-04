package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.TaskStatus;

import java.util.List;
import java.util.stream.Collectors;

public class TaskStatusListResponse {
    private List<Instance<TaskStatus>> taskStatuses;

    public TaskStatusListResponse(List<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses.stream()
                .map(TaskStatusListResponse::extractInstance)
                .collect(Collectors.toList());
    }

    private static Instance<TaskStatus> extractInstance(TaskStatus status) {
        return new Instance<>(status, TaskStatus.class);
    }

    public List<Instance<TaskStatus>> getTaskStatuses() {
        return taskStatuses;
    }

}
