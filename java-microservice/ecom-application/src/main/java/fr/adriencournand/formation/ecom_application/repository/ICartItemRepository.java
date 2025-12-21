package fr.adriencournand.formation.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom_application.model.CartItem;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

}
