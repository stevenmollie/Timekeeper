package be.sbs.timekeeper.application.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import be.sbs.timekeeper.application.beans.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
	public List<Task> findByNameContainsOrDescriptionContains(String name, String description);
}
