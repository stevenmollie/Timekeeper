package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.beans.Session;
import be.sbs.timekeeper.application.beans.Task;
import be.sbs.timekeeper.application.beans.User;
import be.sbs.timekeeper.application.exception.BadRequestException;
import be.sbs.timekeeper.application.service.SessionService;
import be.sbs.timekeeper.application.service.TaskService;
import be.sbs.timekeeper.application.service.UserService;
import be.sbs.timekeeper.application.valueobjects.PatchOperation;
import org.springframework.http.HttpHeaders;
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
	private final UserService userService;

	public SessionController(SessionService sessionService, TaskService taskService, UserService userService) {
		this.sessionService = sessionService;
		this.taskService = taskService;
		this.userService = userService;
	}

    //---- GET ------------------------------------------------------------------------------------
    @GetMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Session> getAllSessions(@RequestParam(required = false) String taskId, 
			@RequestParam(required = false) String userId){
    	
    	if(taskId == null && userId == null) {
    		//no request parameters: get everything
    		return sessionService.getAll();
    	}else {
    		if(taskId != null) {
    			//check if task exists, if not the following statement throws an exception and execution is interrupted
    	    	Task task = taskService.getById(taskId);
    	    	
    	    	if(userId != null) {
    	    		User user = userService.getById(userId);
    	    		return sessionService.getAllSessionsFromTaskAndUser(task, user);
    	    	}else {
    	    		return sessionService.getAllSessionsFromTask(task);
    	    	}
    		}
    		
    		//if we reach this code, then taskId wasn't passed, only userId was passed
    			
    		//check if user exists, if not the following statement will throw an exception
    		User user = userService.getById(userId);
    		return sessionService.getAllSessionsFromUser(user);
    	}
	}

    @GetMapping(path = "/session/{sessionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Session getById(@PathVariable String sessionId) {
		return sessionService.getById(sessionId);
	}
    
    @GetMapping(path = "/_active-session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Session getActiveSession(@RequestHeader HttpHeaders headers){
        User user = getUser(headers);
        return sessionService.getActiveSessionByUser(user);
    }

    @PostMapping(path = "/session/_start")
    @ResponseStatus(HttpStatus.OK)
    public void startSession(@RequestParam String taskId, @RequestHeader HttpHeaders headers) {
        User user = getUser(headers);
        sessionService.startSessionFor(user, taskId);
    }

    @PostMapping(path = "/session/_stop")
    @ResponseStatus(HttpStatus.OK)
    public void stopSession(@RequestHeader HttpHeaders headers) {
        User user = getUser(headers);
        sessionService.stopSessionFor(user);
    }

    private User getUser(@RequestHeader HttpHeaders headers) {
        String token = headers.get("token").get(0);
        return userService.getByToken(token);
    }

    //---- POST -----------------------------------------------------------------------------------
    @PostMapping(path = "/session", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestBody Session session) {
    	if (session.getTaskId() == null) throw new BadRequestException("The taskId cannot be null");
    	if (session.getUserId() == null) throw new BadRequestException("The userId cannot be null");
    	User user = userService.getById(session.getUserId());
    	
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
