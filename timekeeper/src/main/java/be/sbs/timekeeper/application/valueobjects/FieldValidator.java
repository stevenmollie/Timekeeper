package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.exception.BadRequestException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FieldValidator {

    private static final List<String> PATCHABLE_FIELDS = Arrays.asList("/currentTime", "/deadLine", "/priority", "/status");
    private static final List<String> PERMITTED_PATCH_OP = Collections.singletonList("replace");

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
                || task.getName().isEmpty()
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
        return PATCHABLE_FIELDS.contains(operations.getPath())
                && PERMITTED_PATCH_OP.contains(operations.getOp())
                && operations.getValue() != null;
    }

    public static void validatePUTProject(Project project) {
        if (project.getId() == null
                || project.getName() == null
                || project.getName().trim().isEmpty()
                || project.getDescription() == null
                || project.getDeadLine() == null)
            throw new BadRequestException("Update not permitted for these values.");
    }

    public static void validatePOSTProject(Project project) {
        if (project.getName() == null || project.getName().isEmpty() || project.getId() != null) {
            throw new BadRequestException("cannot create " + project.toString());
        }
    }
}
