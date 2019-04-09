package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Session;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.ProjectStatus;
import be.sbs.timekeeper.application.enums.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class FieldConverter {

    public static void setDefaultSessionFields(Session session) {
        if (session.getStartTime() == null) session.setStartTime(LocalDateTime.now());
        if (session.getWorkTime() == null) session.setWorkTime(Duration.ZERO);
    }
    
    //TODO: check if this thing works with LocalDateTime fields or not
    public static void convertSessionFields(PatchOperation operation) {

    }
	
    public static void convertTaskFields(PatchOperation operation) {
        switch (operation.getPath()) {
            case "/status":
                operation.setValue(TaskStatus.fromString(operation.getValue()).name());
                break;
            case "/priority":
                operation.setValue(Priority.fromString(operation.getValue()).name());
                break;
        }
    }

    public static void setDefaultTaskFields(Task task) {
        if (task.getPriority() == null) task.setPriority(Priority.MEDIUM);
        if (task.getStatus() == null) task.setStatus(TaskStatus.READY_TO_START);
    }

    public static void setDefaultProjectFields(Project project) {
        if (project.getStatus() == null) project.setStatus(ProjectStatus.EMPTY);
    }

    public static void convertProjectFields(PatchOperation operation) {
        switch (operation.getPath()) {
            case "/status":
                operation.setValue(ProjectStatus.fromString(operation.getValue()).name());
                break;
        }
    }
}
