package be.sbs.timekeeper.application.enums;

import java.util.stream.IntStream;

public enum ProjectStatus {
    READY_TO_START, IN_PROGRESS, DONE, CANCELED, EMPTY;

    public static Integer getIndexOf(ProjectStatus statuc) {
        return IntStream.range(0, ProjectStatus.values().length)
                .filter(i -> ProjectStatus.values()[i] == statuc)
                .findFirst()
                .getAsInt();

    }
}
