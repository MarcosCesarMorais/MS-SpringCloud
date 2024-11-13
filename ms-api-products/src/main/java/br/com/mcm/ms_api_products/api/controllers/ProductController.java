package br.com.mcm.ms_api_products.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcm.ms_api_products.api.DTO.ProductMapper;
import br.com.mcm.ms_api_products.api.DTO.ProductRequest;
import br.com.mcm.ms_api_products.api.DTO.ProductResponse;
import br.com.mcm.ms_api_products.api.controllers.openApi.ProductOpenApi;
import br.com.mcm.ms_api_products.entities.Product;
import br.com.mcm.ms_api_products.services.ProductServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController implements ProductOpenApi {

    private final ProductServiceImpl productService;
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable(name = "id") String id) {
        return ProductMapper.mapper(productService.findById(id));
    }

    @Override
    @GetMapping
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productService.findAll(pageable).map(ProductMapper::mapper);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        var product = Product.newProduct(productRequest.name(), productRequest.price());
        var productSave = ProductMapper.mapper(productService.create(product));

        LOG.info(String.format("Product with ID %s, created successfully", productSave.id()));

        return productSave;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
        var productRequest = Product.updateProduct(request.name(), request.price(), request.active());
        var productUpdated = ProductMapper.mapper(productService.update(id, productRequest));
        return ResponseEntity.ok(productUpdated);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {
        productService.deleteById(id);
        LOG.info(String.format("Product with ID %s, delete successfully", id));

        return ResponseEntity.noContent().build();
    }

}
