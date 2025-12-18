package fr.adriencournand.formation.ecom_application;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    // @Autowired
    // Present in the formation I followed but not needed with
    // @RequiredArgsConstructor on the newer version of SpringBoost
    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> GetAllUsers() {
        return userService.FetchAllUsers();
    }

    @PostMapping("/api/users")
    public String CreateUser(@RequestBody User user) {
        userService.AddUser(user);
        return "User Added";
    }
}
