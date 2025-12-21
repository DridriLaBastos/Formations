package fr.adriencournand.formation.ecom_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom_application.model.CartItem;
import fr.adriencournand.formation.ecom_application.model.Product;
import fr.adriencournand.formation.ecom_application.model.User;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

}
