package be.sbs.timekeeper.application.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String projectId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private OffsetDateTime currentTime;
	
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

    public OffsetDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(OffsetDateTime currentTime) {
        this.currentTime = currentTime;
    }
}
