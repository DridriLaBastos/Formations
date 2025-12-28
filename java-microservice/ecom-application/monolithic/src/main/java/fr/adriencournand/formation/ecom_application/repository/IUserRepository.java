package fr.adriencournand.formation.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom_application.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

}
