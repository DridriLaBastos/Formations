package fr.adriencournand.formation.ecom.service.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adriencournand.formation.ecom.service.order.model.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

}
