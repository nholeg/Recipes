package recipes.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    final UserService userService;
    final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        Optional<User> userByEmail = userService.findUserByEmail(user.getEmail());
        if (userByEmail.isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(UserRoles.USER.toString());
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}