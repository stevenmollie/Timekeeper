package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.User;
import be.sbs.timekeeper.application.exception.UserAlreadyExistsException;
import be.sbs.timekeeper.application.exception.UserNotActiveException;
import be.sbs.timekeeper.application.exception.UserNotFoundException;
import be.sbs.timekeeper.application.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
    public User getByToken(String token) {
    	return userRepository.findFirstByToken(token).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
    public User login(User inputUser) {
        User outputUser = userRepository.findFirstByName(inputUser.getName())
                          .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordIsCorrect(inputUser.getPassword(), outputUser.getPassword())){
            throw new UserNotFoundException("Incorrect password");
        }
        
        if(!outputUser.getActive()) {
        	throw new UserNotActiveException("User not active");
        }

        outputUser.setToken(createToken());

        return userRepository.save(outputUser);
    }
    
    public User register(User inputUser) {
    	checkIfUserExists(inputUser);
    	
    	inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));
    	inputUser.setActive(false);
    	User outputUser = userRepository.insert(inputUser);
    	return outputUser;
    }

	private void checkIfUserExists(User inputUser) {
		userRepository.findFirstByName(inputUser.getName())
    		.ifPresent(user -> throwUserExistsException());
	}

	private void throwUserExistsException() {
		throw new UserAlreadyExistsException("User already exists");
	}
    
    
    private boolean passwordIsCorrect(String inputPassword, String outputPassword) {
        return passwordEncoder.matches(inputPassword, outputPassword);
    }

    public boolean userAuthenticated(String token){
        //TODO: memory leak
        return userRepository.findFirstByToken(token).isPresent();
    }

    private String createToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 128;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
