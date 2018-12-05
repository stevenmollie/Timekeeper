package be.sbs.timekeeper.application.beans;

import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String projectId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime currentTime;
    private Priority priority;
    private TaskStatus status;

    public Task(String id, String name, String description, String projectId, LocalDateTime currentTime, Priority priority, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.currentTime = currentTime;
        this.priority = priority;
        this.status = status;
    }

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", projectId='").append(projectId).append('\'');
        sb.append(", currentTime=").append(currentTime);
        sb.append(", priority=").append(priority);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
