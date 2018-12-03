package be.sbs.timekeeper.application.controller;


import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.enums.ProjectStatus;
import be.sbs.timekeeper.application.service.ProjectService;
import be.sbs.timekeeper.application.valueobjects.ProjectStatusListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Project")
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "/{projectId}")
    public Project getProjectById(@PathVariable String projectId){
        return projectService.getById(projectId);
    }

    @GetMapping(path = "/_statuses")
    public ProjectStatusListResponse getListOfStatuses() {
        return new ProjectStatusListResponse(Arrays.asList(ProjectStatus.values()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Project> getAll(){
        return projectService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addProject(@RequestBody Project project){
        projectService.addProject(project);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProject(@RequestBody Project project) {
        projectService.updateProject(project);
    }


}
