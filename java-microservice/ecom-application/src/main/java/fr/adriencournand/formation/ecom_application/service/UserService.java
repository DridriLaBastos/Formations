package fr.adriencournand.formation.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom_application.model.User;
import fr.adriencournand.formation.ecom_application.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;

    public List<User> FetchAllUsers() {
        return userRepository.findAll();
    }

    public void AddUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> FetchUser(Long id) {
        return userRepository.findById(id);
    }

    public boolean UpdateUser(User user) {
        return userRepository.findById(user.getId()).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }
}
