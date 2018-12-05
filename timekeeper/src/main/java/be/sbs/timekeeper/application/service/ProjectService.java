package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.ProjectNotFoundException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import be.sbs.timekeeper.application.valueobjects.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project getById(String projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public void addProject(Project project) {
        FieldValidator.validatePOSTProject(project);
        projectRepository.insert(project);
    }

    public void updateProject(Project project) {
        FieldValidator.validatePUTProject(project);
        projectRepository.findById(project.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + project.getId() + ". the project doesn't exist!"));
        projectRepository.save(project);
    }

}
