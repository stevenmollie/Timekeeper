package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

/*
    @Mock
    public ProjectRepository projectRepository;
    @InjectMocks
    public ProjectService projectService;

    @Captor
    private ArgumentCaptor<Project> projectArgumentCaptor;

    @Nested
    class getTests{

    }

    @Nested
    class postTests {

        static final String PROJECT_ID = "123456abc";

        @Test
        void test_addProject() {
            doNothing()
                    .when(projectRepository)
                    .insert(projectArgumentCaptor.capture());
            Project project = createProject(PROJECT_ID, "project", "");

            projectService.addProject(project);
            assertThat(projectArgumentCaptor.getValue())
                    .isEqualToComparingFieldByField(project);
        }

        @Test
        void test_addProject_throwsExceptionWhenNameIsEmpty(){
            assertThrows(
                    BadRequestException.class,
                    () -> projectService.addProject(createProject(PROJECT_ID, null, "some description")));
        }

        private Project createProject(String id, String name, String description){
            Project project = new Project();
            project.setId(id);
            project.setName(name);
            project.setDescription(description);
            System.out.println("project.toString() = " + project.toString());
            return project;
        }

    }
    */
}