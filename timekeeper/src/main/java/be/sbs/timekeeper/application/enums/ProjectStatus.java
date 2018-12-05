package be.sbs.timekeeper.application.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum ProjectStatus {
    READY_TO_START, IN_PROGRESS, DONE, CANCELED, EMPTY;

    @JsonCreator
    public static ProjectStatus fromString(String source) {
        return source == null
                ? null
                : ProjectStatus.valueOf(source.toUpperCase().trim().replace(" ", "_"));
    }

    @JsonValue
    public String getKey() {
        return StringUtils.capitalize(this.name().toLowerCase().replace("_", " "));
    }

}
