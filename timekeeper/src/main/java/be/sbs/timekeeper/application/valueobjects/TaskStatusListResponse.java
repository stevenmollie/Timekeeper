package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.TaskStatus;

import java.util.List;
import java.util.stream.Collectors;

public class TaskStatusListResponse {
    private List<Status> taskStatuses;

    public TaskStatusListResponse(List<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses.stream()
                .map(Status::new)
                .collect(Collectors.toList());
    }

    public List<Status> getTaskStatuses() {
        return taskStatuses;
    }

    private class Status {
        private Integer code;
        private String name;

        Status(TaskStatus status) {
            this.code = TaskStatus.getIndexOf(status);
            this.name = status.name().replace("_", " ").toLowerCase();
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

}
