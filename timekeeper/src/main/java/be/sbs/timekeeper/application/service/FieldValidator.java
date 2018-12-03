package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.valueobjects.PatchOperation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FieldValidator {

    public static boolean isValidTaskPatchRequest(PatchOperation operations) {
        switch (operations.getPath()) {
            case "/currentTime":
                return validateCurrentTime(operations);
            case "/deadLine":
                return validateDeadLine(operations);
            default:
                return false;
        }
    }

    private static boolean validateCurrentTime(PatchOperation operations) {
        if (isNotCorrectDateFormat(operations)) return false;
        return operations.getPath().equals("/currentTime")
                && operations.getOp().equals("replace");
    }

    private static boolean validateDeadLine(PatchOperation operations) {
        if (isNotCorrectDateFormat(operations)) return false;
        return operations.getPath().equals("/deadLine")
                && operations.getOp().equals("replace");
    }

    private static boolean isNotCorrectDateFormat(PatchOperation operations) {
        try {
            LocalDate.parse(operations.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}
