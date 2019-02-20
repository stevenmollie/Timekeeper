package be.sbs.timekeeper.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import be.sbs.timekeeper.application.beans.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
	
}
