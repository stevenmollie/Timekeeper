package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Session;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.ProjectStatus;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FieldValidator {

	private static final List<String> PATCHABLE_FIELDS_FOR_SESSIONS = Arrays.asList("/startTime", "/endTime", "/workTime");
    private static final List<String> PATCHABLE_FIELDS_FOR_TASKS = Arrays.asList("/currentTime", "/priority", "/status");
    private static final List<String> PATCHABLE_FIELDS_FOR_PROJECTS = Arrays.asList("/deadLine", "/name", "/description", "/status");
    private static final List<String> PATCHABLE_FIELDS_FOR_USERS = Arrays.asList("/selectedTask");
    private static final List<String> PERMITTED_PATCH_OP = Collections.singletonList("replace");

    private enum DateType {
        DATE_ONLY("yyyy-MM-dd"),
        DATE_AND_TIME("yyyy-MM-dd'T'HH:mm:ss");

        private String format;

        DateType(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    
    //---Validators for Session
    
    public static void validatePUTSession(Session session) {
    	//modifying sessions linked to tasks should always be allowed, regardless of task status
        if (session.getId() == null
                || session.getTaskId() == null
                || session.getUserId() == null
                || session.getStartTime() == null
                || session.getEndTime() == null
                || session.getWorkTime() == null)
            throw new BadRequestException("Update not permitted for these values.");
    }

    public static void validatePOSTSession(Session session, TaskStatus taskStatus) {
        if (session.getTaskId() == null
                || session.getUserId() == null
                || session.getId() != null) {
            throw new BadRequestException("Cannot create " + session.toString());
        }
        if(taskStatus == TaskStatus.DONE || taskStatus == TaskStatus.CANCELED) {
    		throw new BadRequestException("Cannot create session for a task that has the status " + taskStatus.name());
    	}
    }

    public static void validatePATCHSession(PatchOperation patchOperation) {
        if (!isValidSessionPatchRequest(patchOperation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidSessionPatchRequest(PatchOperation operation) {
        return allowedFieldsArePresent(operation, PATCHABLE_FIELDS_FOR_SESSIONS)
                && valueOfSessionPatchRequestIsValid(operation);
    }

    private static boolean valueOfSessionPatchRequestIsValid(PatchOperation operation) {
        switch (operation.getPath()) {
        	case "/startTime":
            case "/endTime":
                return isValidDateFormat(DateType.DATE_AND_TIME, operation.getValue());
            case "/workTime":
                return isValidTimeFormat(operation.getValue());
        }
        return false;
    }
    
    

	//---validators for Task
    
    public static void validatePUTTask(Task task) {
        if (task.getId() == null
                || task.getName() == null
                || task.getName().trim().isEmpty()
                || task.getDescription() == null
                || task.getCurrentTime() == null
                || task.getPriority() == null
                || task.getStatus() == null
                || task.getProjectId() == null)
            throw new BadRequestException("Update not permitted for these values.");
    }

    public static void validatePOSTTask(Task task) {
        if (task.getName() == null
                || task.getName().trim().isEmpty()
                || task.getId() != null
                || task.getProjectId() == null
                || task.getStatus() != TaskStatus.READY_TO_START
                || task.getPriority() == null)
            throw new BadRequestException("Cannot create " + task.toString());
    }

    public static void validatePATCHTask(PatchOperation patchOperation) {
        if (!isValidTaskPatchRequest(patchOperation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidTaskPatchRequest(PatchOperation operation) {
        return allowedFieldsArePresent(operation, PATCHABLE_FIELDS_FOR_TASKS)
        		&& valueOfTaskPatchRequestIsValid(operation);
    }

    private static boolean valueOfTaskPatchRequestIsValid(PatchOperation operation) {
    	try {
	        switch (operation.getPath()) {
		        case "/currentTime":
		            return isValidDateFormat(DateType.DATE_AND_TIME , operation.getValue());
		        case "/priority":
	        		Priority.fromString(operation.getValue());
	        		return true;
	            case "/status":
                    TaskStatus.fromString(operation.getValue());
                    return true;
	        }
    	} catch (Exception e) {
    		return false;
    	}
        return false;
    }
    
    
    
    //---validators for Project
    
    public static void validatePUTProject(Project project) {
        if (project.getId() == null
                || project.getName() == null
                || project.getName().trim().isEmpty()
                || project.getDescription() == null
                || project.getStatus() == null
                || project.getDeadLine() == null)
            throw new BadRequestException("Update not permitted for these values.");
    }

    public static void validatePOSTProject(Project project) {
        if (project.getName() == null
                || project.getName().isEmpty()
                || (project.getStatus() != null && project.getStatus() != ProjectStatus.EMPTY)
                || project.getId() != null) {
            throw new BadRequestException("Cannot create " + project.toString());
        }
    }

    public static void validatePATCHProject(PatchOperation operation) {
        if (!isValidProjectPatchRequest(operation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidProjectPatchRequest(PatchOperation operation) {
        return allowedFieldsArePresent(operation, PATCHABLE_FIELDS_FOR_PROJECTS)
                && valueOfProjectPatchRequestIsValid(operation);
    }

    private static boolean valueOfProjectPatchRequestIsValid(PatchOperation operation) {
        switch (operation.getPath()) {
            case "/deadLine":
                return isValidDateFormat(DateType.DATE_ONLY, operation.getValue());
            case "/name":
                return !operation.getValue().isEmpty();
            case "/status":
                try {
                    ProjectStatus.fromString(operation.getValue());
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "/description":
                return true;
        }
        return false;
    }
    
    private static boolean allowedFieldsArePresent(PatchOperation operation, List<String> allowedOperations) {
    	return allowedOperations.contains(operation.getPath())
    			&& PERMITTED_PATCH_OP.contains(operation.getOp())
    			&& operation.getValue() != null;
    }

    public static boolean isValidDateFormat(DateType type, String value) {
        LocalDateTime ldt;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type.getFormat());

        try {
            ldt = LocalDateTime.parse(value, formatter);
            String result = ldt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, formatter);
                String result = ld.format(formatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, formatter);
                    String result = lt.format(formatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }
        return false;
    }
    
    private static boolean isValidTimeFormat(String value) {
    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    	try {
			LocalTime lt = LocalTime.parse(value, timeFormatter);
			String result = lt.format(timeFormatter);
			return result.equals(value);
    	} catch (DateTimeParseException exp) {
    		return false;
    	}
	}


    //---validators for User

    public static void validatePATCHUser(PatchOperation operation) {
        if (!isValidUserPatchRequest(operation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidUserPatchRequest(PatchOperation operation) {
        return allowedFieldsArePresent(operation, PATCHABLE_FIELDS_FOR_USERS);
    }
}
