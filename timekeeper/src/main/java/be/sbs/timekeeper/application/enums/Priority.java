package be.sbs.timekeeper.application.enums;

import java.util.stream.IntStream;

public enum Priority {
    VER_LOW, LOW, MEDIUM, HIGH, VERY_HIGH;

    public static Integer getIndexOf(Priority statuc) {
        return IntStream.range(0, Priority.values().length)
                .filter(i -> Priority.values()[i] == statuc)
                .findFirst()
                .getAsInt();

    }
}
