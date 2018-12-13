package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.exception.ProjectNotFoundException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import be.sbs.timekeeper.application.repository.ProjectRepositoryCustom;
import be.sbs.timekeeper.application.valueobjects.FieldConverter;
import be.sbs.timekeeper.application.valueobjects.FieldValidator;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static be.sbs.timekeeper.application.enums.ProjectStatus.DONE;

@Service
public class ProjectService {

    public static final List<String> ALLOWED_PATCH_FIELDS_FOR_DONE_PROJECTS = Collections.singletonList("/status");
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectRepositoryCustom projectRepositoryCustom;
    @Autowired
    private TaskService taskService;

    public Project getById(String projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public void addProject(Project project) {
        FieldValidator.validatePOSTProject(project);
        FieldConverter.setDefaultProjectFields(project);
        projectRepository.insert(project);
    }

    public void updateProject(Project project) {
        FieldValidator.validatePUTProject(project);
        projectRepository.findById(project.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + project.getId() + ". the project doesn't exist!"));
        projectRepository.save(project);
    }

    public void applyPatch(String projectId, PatchOperation patchOperations) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + projectId + ". the project doesn't exist!"));
        FieldValidator.validatePATCHProject(patchOperations);
        if (isDoneAndNotValidOperation(patchOperations, existingProject)) {
            throw new BadRequestException("Cannot PATCH the dead line of a DONE project!");
        }
        FieldConverter.convertProjectFields(patchOperations);
        projectRepositoryCustom.saveOperation(projectId, patchOperations);
    }

    private boolean isDoneAndNotValidOperation(PatchOperation patchOperations, Project existingProject) {
        return !ALLOWED_PATCH_FIELDS_FOR_DONE_PROJECTS.contains(patchOperations.getPath())
                && existingProject.getStatus().equals(DONE);
    }

    public void deleteProject(String projectId) {
    	projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException("Cannot delete project: " + projectId + ". the project doesn't exist!"));

        //delete the project itself
    	projectRepository.deleteById(projectId);

        taskService.deleteTasksFromProject(projectId);
    }
}
