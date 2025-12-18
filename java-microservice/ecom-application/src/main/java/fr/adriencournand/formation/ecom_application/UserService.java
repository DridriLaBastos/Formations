package fr.adriencournand.formation.ecom_application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public List<User> FetchAllUsers() {
        return userList;
    }

    public List<User> AddUser(User user) {
        userList.add(user);
        return userList;
    }
}
