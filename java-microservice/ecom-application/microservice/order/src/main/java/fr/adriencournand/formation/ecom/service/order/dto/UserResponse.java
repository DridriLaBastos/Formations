package fr.adriencournand.formation.ecom.service.order.dto;

import fr.adriencournand.formation.ecom.service.order.model.EUserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private EUserRole role;
    private AddressDTO address;
}
