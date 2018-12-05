package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.Priority;
import be.sbs.timekeeper.application.enums.TaskStatus;

public class FieldConverter {

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

    public static void convertTaskFields(Task task) {
        if (task.getPriority() == null) task.setPriority(Priority.MEDIUM);
        if (task.getStatus() == null) task.setStatus(TaskStatus.READY_TO_START);
    }
}
