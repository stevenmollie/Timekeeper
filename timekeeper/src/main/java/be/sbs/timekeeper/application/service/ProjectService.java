package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.exception.ProjectNotFoundException;
import be.sbs.timekeeper.application.repository.ProjectRepository;
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
        if (project.getName() == null || project.getName().isEmpty() || project.getId() != null) {
            throw new BadRequestException("cannot create " + project.toString());
        }
       projectRepository.insert(project);
    }

    public void updateProject(Project project) {
        if (notAllFieldsPresent(project))
            throw new BadRequestException("Update not permitted for these values.");
        projectRepository.findById(project.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Cannot update project: " + project.getId() + ". the project doesn't exist!"));
        projectRepository.save(project);
    }

    private boolean notAllFieldsPresent(Project project) {
        return project.getId() == null
                || project.getName() == null
                || project.getDescription() == null
                || project.getDeadLine() == null;

    }
}
