package fr.adriencournand.formation.ecom_application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom_application.dto.AddressDTO;
import fr.adriencournand.formation.ecom_application.dto.UserRequest;
import fr.adriencournand.formation.ecom_application.dto.UserResponse;
import fr.adriencournand.formation.ecom_application.model.Address;
import fr.adriencournand.formation.ecom_application.model.User;
import fr.adriencournand.formation.ecom_application.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;

    public List<UserResponse> FetchAllUsers() {
        return userRepository.findAll().stream().map(UserService::MapUserToUserResponse).collect(Collectors.toList());
    }

    public void AddUser(UserRequest user) {
        userRepository.save(MapUserRequestToUser(user));
    }

    public Optional<UserResponse> FetchUser(Long id) {
        return userRepository.findById(id).map(UserService::MapUserToUserResponse);
    }

    public boolean UpdateUser(Long id, UserRequest updateUserRequest) {
        return userRepository.findById(id).map(existingUser -> {
            UpdateUserFromRequest(existingUser, updateUserRequest);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private static User MapUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }

        return user;
    }

    static private UserResponse MapUserToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setEmail(String.valueOf(user.getEmail()));
        response.setFirstName(String.valueOf(user.getFirstName()));
        response.setLastName(String.valueOf(user.getLastName()));
        response.setPhone(String.valueOf(user.getPhone()));
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());
            addressDTO.setCity(user.getAddress().getCity());

            response.setAddress(addressDTO);
        }

        return response;
    }

    static private void UpdateAddressFromAddressDTO(Address address, AddressDTO addressDTO) {
        if (addressDTO.getCity() != null) {
            address.setCity(addressDTO.getCity());
        }
        if (addressDTO.getCountry() != null) {
            address.setCountry(addressDTO.getCountry());
        }
        if (addressDTO.getState() != null) {
            address.setState(addressDTO.getState());
        }
        if (addressDTO.getStreet() != null) {
            address.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getZipCode() != null) {
            address.setZipCode(addressDTO.getZipCode());
        }
    }

    static private void UpdateUserFromRequest(User user, UserRequest userRequest) {
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getPhone() != null) {
            user.setPhone(userRequest.getPhone());
        }
        if (userRequest.getAddress() != null) {
            Address address = user.getAddress();

            if (address == null) {
                address = new Address();
                user.setAddress(address);
            }

            UpdateAddressFromAddressDTO(address, userRequest.getAddress());
        }
    }
}
