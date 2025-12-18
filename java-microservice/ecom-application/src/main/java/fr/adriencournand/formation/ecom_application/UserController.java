package fr.adriencournand.formation.ecom_application;

import java.util.List;

import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<User>> GetAllUsers() {
        return ResponseEntity.ok(userService.FetchAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> GetUser(@PathVariable Long id) {
        return userService.FetchUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> CreateUser(@RequestBody User user) {
        userService.AddUser(user);
        return ResponseEntity.ok("User Added");
    }
}
