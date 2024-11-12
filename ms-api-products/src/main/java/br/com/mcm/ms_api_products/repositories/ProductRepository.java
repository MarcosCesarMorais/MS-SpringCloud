package br.com.mcm.ms_api_products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mcm.ms_api_products.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
