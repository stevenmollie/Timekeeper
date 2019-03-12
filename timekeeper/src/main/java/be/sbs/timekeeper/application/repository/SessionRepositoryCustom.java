package be.sbs.timekeeper.application.repository;

import be.sbs.timekeeper.application.beans.Session;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SessionRepositoryCustom {
    private MongoOperations mongoOperations;

    public SessionRepositoryCustom(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public List<Session> findSessionsByTaskId(String taskId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("taskId").is(taskId));
        return mongoOperations.find(query, Session.class);
    }
    
    /**
     * Get all Sessions from a specific User
     * (Useful for stats)
     * 
     * @param userId
     * @return
     */
    public List<Session> findSessionsByUserId(String userId) {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	return mongoOperations.find(query,  Session.class);
    }

    public List<Session> findSessionsByTaskIdAndUserId(String taskId, String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("taskId").is(taskId).and("userId").is(userId));
		return mongoOperations.find(query,  Session.class);
	}
    
    public Optional<Session> findActiveSessionByUserId(String userId) {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("endTime").is(null));
    	return Optional.ofNullable(mongoOperations.findOne(query, Session.class));
    }
    
    public void saveOperation(String sessionId, PatchOperation operation) {
        Query query = Query.query(Criteria.where("id").is(sessionId));
        Update update = new Update().set(operation.getPath().substring(1), operation.getValue());
        UpdateResult updateResult = mongoOperations.updateFirst(query, update, Session.class);
        if (!updateResult.wasAcknowledged()) {
            throw new MongoException("Could not add operation " + operation + " to session " + sessionId);
        }
    }
    
    public void deleteSessionsFromTaskId(String taskId) {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("taskId").is(taskId));
    	mongoOperations.findAllAndRemove(query, Session.class);
    }
    
    /**
     * Delete all sessions from a specific user.
     * Could be useful to do when a user is deleted
     * @param userId
     */
    public void deleteSessionsFromUserId(String userId) {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	mongoOperations.findAllAndRemove(query,  Session.class);
    }
}
