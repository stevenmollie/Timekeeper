package be.sbs.timekeeper.application.repository;

import java.util.List;

import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import be.sbs.timekeeper.application.beans.Task;

@Component
public class TaskRepositoryCustom {
    private MongoOperations mongoOperations;

    public TaskRepositoryCustom(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public List<Task> findTasksByProjectId(String projectId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        return mongoOperations.find(query, Task.class);
    }

    public void saveOperation(String taskId, PatchOperation operation) {
        Query query = Query.query(Criteria.where("id").is(taskId));
        Update update = new Update().set("currentTime", operation.getValue());
        UpdateResult updateResult = mongoOperations.updateFirst(query, update, Task.class);
        if (!updateResult.wasAcknowledged()) {
            throw new MongoException("Could not add operation " + operation + " to task " + taskId);
        }
    }
}
