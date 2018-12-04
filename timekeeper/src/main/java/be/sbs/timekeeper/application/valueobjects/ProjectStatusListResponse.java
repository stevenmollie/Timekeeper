package be.sbs.timekeeper.application.valueobjects;

import be.sbs.timekeeper.application.enums.ProjectStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectStatusListResponse {
    private List<Status> projectStatuses;

    public ProjectStatusListResponse(List<ProjectStatus> projectStatuses) {
        this.projectStatuses = projectStatuses.stream()
                .map(Status::new)
                .collect(Collectors.toList());
    }

    public List<Status> getProjectStatuses() {
        return projectStatuses;
    }

    private class Status {
        private Integer code;
        private String name;

        Status(ProjectStatus status) {
            this.code = ProjectStatus.getIndexOf(status);
            this.name = StringUtils.capitalize(status.name().replace("_", " ").toLowerCase());
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
