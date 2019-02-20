package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.beans.Session;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.enums.TaskStatus;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.service.SessionService;
import be.sbs.timekeeper.application.service.TaskService;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class SessionController {
	private final SessionService sessionService;
	private final TaskService taskService;

	public SessionController(SessionService sessionService, TaskService taskService) {
		this.sessionService = sessionService;
		this.taskService = taskService;
	}

    //---- GET ------------------------------------------------------------------------------------
    @GetMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Session> getAll(){
		List<Session> all = sessionService.getAll();
		return all;
	}

    @GetMapping(path = "/sessions/{taskId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Session> getAllSessionsFromTask(@PathVariable String taskId){
		//check if task exists, if not the following statement throws an exception and execution is interrupted
    	Task task = taskService.getById(taskId);

		return sessionService.getAllSessionsFromTask(task);
	}

    @GetMapping(path = "/session/{sessionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Session getById(@PathVariable String sessionId) {
		return sessionService.getById(sessionId);
	}

    //---- POST -----------------------------------------------------------------------------------
    @PostMapping(path = "/session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestBody Session session) {
    	if (session.getTaskId() == null) throw new BadRequestException("The taskId cannot be null");
    	
    	/*
    	 * if (session.getUserId() == null) throw new BadRequestException("The userId cannot be null");
    	 * User user = userService.getById(session.getUserId());
    	 */
        
        sessionService.addSession(session);
    }

    //---- PATCH ----------------------------------------------------------------------------------
    @PatchMapping(path = "/session/{sessionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void applyPatch(@PathVariable String sessionId, @RequestBody PatchOperation patchOperations) {
        sessionService.applyPatch(sessionId, patchOperations);
    }

    //---- PUT ------------------------------------------------------------------------------------
    @PutMapping(path = "/session")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateSession(@RequestBody Session session) {
        sessionService.updateSession(session);
    }

    //---- DELETE ---------------------------------------------------------------------------------
    @DeleteMapping(path = "/session/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable String sessionId) {
        sessionService.deleteSession(sessionId);
    }
}
