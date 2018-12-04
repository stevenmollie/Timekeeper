package be.sbs.timekeeper.application.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum Priority {
    VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH;

    @JsonCreator
    public static Priority fromString(String source) {
        return source == null
                ? null
                : Priority.valueOf(source.toUpperCase().trim().replace(" ", "_"));
    }

    @JsonValue
    public String getKey() {
        return StringUtils.capitalize(this.name().toLowerCase().replace("_", " "));
    }
}
