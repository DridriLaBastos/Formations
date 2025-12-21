package fr.adriencournand.formation.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.adriencournand.formation.ecom_application.model.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

}
