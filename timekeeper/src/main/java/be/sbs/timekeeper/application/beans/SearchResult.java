package be.sbs.timekeeper.application.beans;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	private List<Task> tasks;
	private List<Project> projects;
	
	public SearchResult() {
		tasks = new ArrayList<>();
		projects = new ArrayList<>();
	}
	
	public SearchResult(List<Task> tasks, List<Project> projects) {
		this.tasks = tasks;
		this.projects = projects;
	}
	
	public List<Task> getTasks(){
		return tasks;
	}
	public List<Project> getProjects(){
		return projects;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
