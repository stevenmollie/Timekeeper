package be.sbs.timekeeper.application.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import be.sbs.timekeeper.application.beans.Task;

@Component
public class TaskRepositoryCustom {
	private MongoOperations mongoOperations;

	public TaskRepositoryCustom(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	public List<Task> findTasksByProjectId(String projectId){
		Query query = new Query();
		query.addCriteria(Criteria.where("projectId").is(projectId));
		return mongoOperations.find(query, Task.class);
	}
	
	
}
