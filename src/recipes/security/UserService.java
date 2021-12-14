package recipes.security;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findById(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
