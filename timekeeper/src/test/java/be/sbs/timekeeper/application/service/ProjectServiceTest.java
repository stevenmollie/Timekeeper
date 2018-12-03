package be.sbs.timekeeper.application.service;


import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Captor
    private ArgumentCaptor<Project> projectArgumentCaptor;

    private static final String PROJECT_ID = "123456abc";

    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("Adding Project tests")
    class AddProjectTests {

        @Nested
        @TestInstance(PER_CLASS)
        @DisplayName("Basic adding tests with allowed values")
        class BasicTests {

            @ParameterizedTest
            @MethodSource("allowedParametersValues")
            void test_addProject_withAllowedValues(String projectId, String name, String description, LocalDateTime deadLine) {
                Project project = new Project(projectId, name, description, deadLine);
                projectService.addProject(project);
                verify(projectRepository).insert(projectArgumentCaptor.capture());
                Project result = projectArgumentCaptor.getValue();
                reset(projectRepository);
                assertThat(project).isEqualToComparingFieldByField(result);
            }

            private Stream<Arguments> allowedParametersValues() {
                return Stream.of(
                        Arguments.of(null, "project", "Hello", null),
                        Arguments.of(null, "project", "", null),
                        Arguments.of(null, "project", "", LocalDateTime.now()),
                        Arguments.of(null, "project", "Hello", LocalDateTime.now()));
            }
        }

        @Nested
        @TestInstance(PER_CLASS)
        @DisplayName("Adding tests that throw Exceptions")
        class ExceptionsTests {

            @ParameterizedTest
            @MethodSource("notAllowedParametersValues")
            void test_addProject_withNotAllowedValues(String projectId, String name, String description, LocalDateTime deadLine) {
                assertThrows(BadRequestException.class,
                        () -> projectService.addProject(new Project(projectId, name, description, deadLine)));
            }

            private Stream<Arguments> notAllowedParametersValues() {
                return Stream.of(
                        Arguments.of(PROJECT_ID, "project", "Description", LocalDateTime.now()),
                        Arguments.of(null, "", "Description", null),
                        Arguments.of(null, null, "Description", LocalDateTime.now()));
            }
        }

    }

}