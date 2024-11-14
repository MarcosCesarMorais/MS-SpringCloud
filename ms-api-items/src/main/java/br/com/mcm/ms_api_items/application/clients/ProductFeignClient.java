package br.com.mcm.ms_api_items.application.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.mcm.ms_api_items.api.dtos.Product;

@FeignClient(url = "localhost:8001/v1/products")
public interface ProductFeignCliente {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product findById(@PathVariable String id);
}
