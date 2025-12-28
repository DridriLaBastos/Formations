package fr.adriencournand.formation.ecom.service.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom.service.user.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

}
