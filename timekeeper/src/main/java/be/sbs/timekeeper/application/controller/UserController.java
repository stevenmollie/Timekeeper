package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.beans.User;
import be.sbs.timekeeper.application.exception.UserNotFoundException;
import be.sbs.timekeeper.application.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getName())){
            throw new UserNotFoundException("Username and password must be filled in");
        }
        return userService.login(user);
    }
    
    @PostMapping("/register")
    public User register(@RequestBody User user) {
    	System.out.println("register");
    	if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getName())) {
    		throw new UserNotFoundException("Username and password must be filled in");
    	}
    	
    	return userService.register(user);
    }
}
