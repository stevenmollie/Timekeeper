package be.sbs.timekeeper.application.beans;


import com.fasterxml.jackson.annotation.JsonInclude;
//import org.springframework.data.mongodb.core.mapping.Document;

//@Document //TODO iumplement when Mongo dependencies are set
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
