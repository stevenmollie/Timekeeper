package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.Priority;

import java.util.List;
import java.util.stream.Collectors;

public class PrioritiesListResponse {
    private List<Instance<Priority>> priorities;

    public PrioritiesListResponse(List<Priority> priorities) {
        this.priorities = priorities.stream()
                .map(PrioritiesListResponse::extractInstance)
                .collect(Collectors.toList());
    }

    private static Instance<Priority> extractInstance(Priority priority) {
        return new Instance<>(priority, Priority.class);
    }

    public List<Instance<Priority>> getProjectStatuses() {
        return priorities;
    }

}
