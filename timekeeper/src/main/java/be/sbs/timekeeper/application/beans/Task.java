package be.sbs.timekeeper.application.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Task {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String projectId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime currentTime;
}
