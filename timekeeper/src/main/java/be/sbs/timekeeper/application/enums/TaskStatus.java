package be.sbs.timekeeper.application.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum TaskStatus {
    READY_TO_START, IN_PROGRESS, DONE, CANCELED;


    @JsonCreator
    public static TaskStatus fromString(String source) {
        return source == null
                ? null
                : TaskStatus.valueOf(source.toUpperCase().trim().replace(" ", "_"));
    }

    @JsonValue
    public String getKey() {
        return StringUtils.capitalize(this.name().toLowerCase().replace("_", " "));
    }

}
