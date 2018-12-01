package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Captor
    private ArgumentCaptor<Project> projectArgumentCaptor;

    static final String PROJECT_ID = "123456abc";

    @Test
    void test_addProject() {
        Project project = createProject(PROJECT_ID, "project", "");
        projectService.addProject(project);
        verify(projectRepository).insert(projectArgumentCaptor.capture());
        Project result = projectArgumentCaptor.getValue();
        assertThat(project).isEqualToComparingFieldByField(result);
    }


    @Test
    void test_addProject_throwsExceptionWhenNameIsEmpty() {
        assertThrows(BadRequestException.class,
                () -> projectService.addProject(createProject(PROJECT_ID, null, "some description")));
    }

    private Project createProject(String id, String name, String description) {
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }
}