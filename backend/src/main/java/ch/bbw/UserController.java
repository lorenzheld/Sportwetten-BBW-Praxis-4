package ch.bbw;

import ch.bbw.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}