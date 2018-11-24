package be.sbs.timekeeper.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import be.sbs.timekeeper.application.beans.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
	
}
