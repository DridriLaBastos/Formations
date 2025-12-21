package fr.adriencournand.formation.ecom_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.adriencournand.formation.ecom_application.dto.UserRequest;
import fr.adriencournand.formation.ecom_application.dto.UserResponse;
import fr.adriencournand.formation.ecom_application.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    // @Autowired
    // Present in the formation I followed but not needed with
    // @RequiredArgsConstructor on the newer version of SpringBoost
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> GetAllUsers() {
        return ResponseEntity.ok(userService.FetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> GetUser(@PathVariable Long id) {
        return userService.FetchUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<String> CreateUser(@RequestBody UserRequest user) {
        userService.AddUser(user);
        return ResponseEntity.ok("User Added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody UserRequest user) {
        boolean updateSuccess = userService.UpdateUser(id, user);
        return new ResponseEntity<>(updateSuccess ? "User Updated" : "Error",
                updateSuccess ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
