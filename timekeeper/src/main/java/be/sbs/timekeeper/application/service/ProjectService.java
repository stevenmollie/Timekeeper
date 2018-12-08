package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.exception.ProjectNotFoundException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import be.sbs.timekeeper.application.repository.ProjectRepositoryCustom;
import be.sbs.timekeeper.application.repository.TaskRepositoryCustom;
import be.sbs.timekeeper.application.valueobjects.FieldConverter;
import be.sbs.timekeeper.application.valueobjects.FieldValidator;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

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
        FieldValidator.validatePATCHProject(patchOperations);
        FieldConverter.convertProjectFields(patchOperations);
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + projectId + ". the project doesn't exist!"));
        projectRepositoryCustom.saveOperation(projectId, patchOperations);
    }
    
    public void deleteProject(String projectId) {
    	projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + projectId + ". the project doesn't exist!"));
    	
    	//delete the project itself
    	projectRepository.deleteById(projectId);
    	
    	taskService.deleteTasksFromProject(projectId);
    }
}
