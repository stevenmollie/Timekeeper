package be.sbs.timekeeper.application.beans;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;


public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String token;
    private Boolean active;
    private String email;
    private String activationToken;
    private String resetPasswordToken;
    private String selectedTask;
    private String selectedProject;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime resetTime;

    public User(String id, String name, String password, String token, Boolean active, String email, String activationToken, String resetPasswordToken, LocalDateTime resetTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.token = token;
        this.active = active;
        this.email = email;
        this.activationToken = activationToken;
        this.resetPasswordToken = resetPasswordToken;
        this.resetTime = resetTime;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getActivationToken() {
		return activationToken;
	}
	
	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

    public String getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(String selectedTask) {
        this.selectedTask = selectedTask;
    }

    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }
    
	public LocalDateTime getResetTime() {
		return resetTime;
	}

	public void setResetTime(LocalDateTime resetTime) {
		this.resetTime = resetTime;
	}
}
