package be.sbs.timekeeper.application.repository;

import be.sbs.timekeeper.application.beans.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
