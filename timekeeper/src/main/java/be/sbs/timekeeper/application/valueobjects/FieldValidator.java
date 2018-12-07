package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
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

    private static final List<String> PATCHABLE_FIELDS_FOR_TASKS = Arrays.asList("/currentTime", "/priority", "/status");
    private static final List<String> PATCHABLE_FIELDS_FOR_PROJECTS = Arrays.asList("/deadLine", "/name", "/description", "/status");
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
            throw new BadRequestException("cannot create " + task.toString());
    }

    public static void validatePATCHTask(PatchOperation patchOperation) {
        if (!isValidTaskPatchRequest(patchOperation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidTaskPatchRequest(PatchOperation operations) {
        return PATCHABLE_FIELDS_FOR_TASKS.contains(operations.getPath())
                && PERMITTED_PATCH_OP.contains(operations.getOp())
                && operations.getValue() != null;
    }

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
            throw new BadRequestException("cannot create " + project.toString());
        }
    }

    public static void validatePATCHProject(PatchOperation operation) {
        if (!isValidProjectPatchRequest(operation))
            throw new BadRequestException("Patch not permitted for these values.");
    }

    private static boolean isValidProjectPatchRequest(PatchOperation operation) {
        return allowedFieldArePresent(operation)
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

    private static boolean allowedFieldArePresent(PatchOperation operation) {
        return PATCHABLE_FIELDS_FOR_PROJECTS.contains(operation.getPath())
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
}
