package be.sbs.timekeeper.application.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {
	
	@Id
	private String id;
	private String taskId;
	private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime workTime;

    public Session(String id, String taskId, String userId, LocalDateTime startTime, LocalDateTime endTime, LocalTime workTime) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workTime = workTime;
    }

    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

    public LocalTime getWorkTime() {
		return workTime;
	}

    public void setWorkTime(LocalTime workTime) {
		this.workTime = workTime;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Session{");
        sb.append("id='").append(id).append('\'');
        sb.append(", taskId='").append(taskId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", workTime='").append(workTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
