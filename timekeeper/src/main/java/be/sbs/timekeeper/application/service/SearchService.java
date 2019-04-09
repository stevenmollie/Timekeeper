package be.sbs.timekeeper.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.beans.SearchResult;
import be.sbs.timekeeper.application.beans.Task;

@Service
public class SearchService {
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProjectService projectService;
	
	public SearchResult getSearchResult(String keyword) {
		List<Task> tasks;
		List<Project> projects;
		if(keyword == null) {
			tasks = taskService.getAll();
			projects = projectService.getAll();
		}else{
			tasks = taskService.getTasksByKeyword(keyword);
			projects = projectService.getProjectsByKeyword(keyword);
		}
		return new SearchResult(tasks, projects);
	}
}
