package be.sbs.timekeeper.application.repository;

import be.sbs.timekeeper.application.beans.Project;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class ProjectRepositoryCustom {

    private MongoOperations mongoOperations;

    public ProjectRepositoryCustom(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void saveOperation(String projectId, PatchOperation operation) {
        Query query = Query.query(Criteria.where("id").is(projectId));
        Update update = new Update().set(operation.getPath().substring(1), operation.getValue());
        UpdateResult updateResult = mongoOperations.updateFirst(query, update, Project.class);
        if (!updateResult.wasAcknowledged()) {
            throw new MongoException("Could not add operation " + operation + " to project " + projectId);
        }
    }

}
