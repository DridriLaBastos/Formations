package fr.adriencournand.formation.ecom_application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    private Long currentUserId = 1L;

    public List<User> FetchAllUsers() {
        return userList;
    }

    public List<User> AddUser(User user) {
        user.setId(currentUserId);
        currentUserId += 1;
        userList.add(user);
        return userList;
    }

    public Optional<User> FetchUser(Long id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public boolean UpdateUser(User user) {
        return userList.stream().filter(_user -> _user.getId().equals(user.getId())).findFirst().map(foundUser -> {
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            return true;
        }).orElse(false);
    }
}
