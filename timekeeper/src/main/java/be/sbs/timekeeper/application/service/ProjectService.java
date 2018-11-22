package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public Project getById(long projectId) {
        return null;
//        return projectRepository.findById("" + projectId).get(); //TODO after implementing Mongo
    }

    public List<Project> getAll() {
        return null;
//        return projectRepository.findAll(); //TODO after implementing Mongo
    }

    public void addProject(Project project) {
//        projectRepository.insert(project); //TODO after implementing Mongo
    }
}
