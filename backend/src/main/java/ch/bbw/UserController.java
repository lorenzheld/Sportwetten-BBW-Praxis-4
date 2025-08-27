package ch.bbw;

import ch.bbw.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService = new UserService();

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        User user = userService.authenticate(
                credentials.get("username"),
                credentials.get("password"));

        if (user != null) {
            return ResponseEntity.ok().body("Login successful");
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

}