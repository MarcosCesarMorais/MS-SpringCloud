package br.com.mcm.ms_api_products.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mcm.ms_api_products.entities.Product;

public interface ProductService {

    Product findById(String id);

    Page<Product> findAll(Pageable pageable);

    Product create(Product product);

    Product update(String id, Product product);

    void deleteById(String id);
}
