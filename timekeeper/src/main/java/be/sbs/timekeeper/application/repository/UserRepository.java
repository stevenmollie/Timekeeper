package be.sbs.timekeeper.application.repository;

import be.sbs.timekeeper.application.beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByName(String name);
    Optional<User> findFirstByToken(String token);
    Optional<User> findFirstByEmail(String email);
}
