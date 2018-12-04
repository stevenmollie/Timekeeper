package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.Priority;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PrioritiesListResponse {
    private List<Instance> priorities;

    public PrioritiesListResponse(List<Priority> priorities) {
        this.priorities = priorities.stream()
                .map(Instance::new)
                .collect(Collectors.toList());
    }

    public List<Instance> getProjectStatuses() {
        return priorities;
    }

    private class Instance {
        private Integer code;
        private String name;

        Instance(Priority priority) {
            this.code = Priority.getIndexOf(priority);
            this.name = StringUtils.capitalize(priority.name().replace("_", " ").toLowerCase());
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
