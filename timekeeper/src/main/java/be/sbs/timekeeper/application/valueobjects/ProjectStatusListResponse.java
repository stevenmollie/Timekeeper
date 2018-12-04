package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.ProjectStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectStatusListResponse {
    private List<Instance<ProjectStatus>> projectStatuses;

    public ProjectStatusListResponse(List<ProjectStatus> projectStatuses) {
        this.projectStatuses = projectStatuses.stream()
                .map(ProjectStatusListResponse::extractInstance)
                .collect(Collectors.toList());
    }

    private static Instance<ProjectStatus> extractInstance(ProjectStatus status) {
        return new Instance<>(status, ProjectStatus.class);
    }

    public List<Instance<ProjectStatus>> getProjectStatuses() {
        return projectStatuses;
    }


}
