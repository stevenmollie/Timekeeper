package be.sbs.timekeeper.application.service;

import be.sbs.timekeeper.application.beans.User;
import be.sbs.timekeeper.application.repository.UserRepository;
import com.sun.deploy.security.UserDeclinedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User login(User inputUser) {
        User outputUser = userRepository.findFirstByName(inputUser.getName(), inputUser.getPassword())
                          .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!passwordIsCorrect(inputUser.getPassword(), outputUser.getPassword())){
            throw new UserDeclinedException("User not authenticated");
        }

        outputUser.setToken(createToken());

        return userRepository.save(outputUser);
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
