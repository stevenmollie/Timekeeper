package be.sbs.timekeeper.application.valueobjects;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;

class Instance<T extends Enum<T>> {
    private Integer code;
    private String name;

    Instance(T instance, Class<T> clazz) {
        this.code = this.getIndexOf(instance, clazz);
        this.name = StringUtils.capitalize(instance.name().replace("_", " ").toLowerCase());
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getIndexOf(T instance, Class<T> clazz) {
        List<T> ts = new ArrayList<>(EnumSet.allOf(clazz));
        return IntStream.range(0, ts.size())
                .filter(i -> ts.get(i) == instance)
                .findFirst()
                .getAsInt();

    }
}
