package fr.adriencournand.formation.ecom.service.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom.service.order.model.CartItem;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(String userId, String productId);

    void deleteByUserIdAndProductId(String userId, String productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);

}
