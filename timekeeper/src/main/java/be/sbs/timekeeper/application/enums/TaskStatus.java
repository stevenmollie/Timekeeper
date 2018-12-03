package be.sbs.timekeeper.application.enums;

import java.util.stream.IntStream;

public enum TaskStatus {
    READY_TO_START, IN_PROGRESS, DONE, CANCELED;

    public static Integer getIndexOf(TaskStatus statuc) {
        return IntStream.range(0, TaskStatus.values().length)
                .filter(i -> TaskStatus.values()[i] == statuc)
                .findFirst()
                .getAsInt();

    }

}
