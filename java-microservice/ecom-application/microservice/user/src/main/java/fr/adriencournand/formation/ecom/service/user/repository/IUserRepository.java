package fr.adriencournand.formation.ecom.service.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom.service.user.model.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

}
