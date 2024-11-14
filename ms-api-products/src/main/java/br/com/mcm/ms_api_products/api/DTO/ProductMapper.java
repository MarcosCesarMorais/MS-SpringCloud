package br.com.mcm.ms_api_products.api.DTO;

import java.time.LocalDate;

import br.com.mcm.ms_api_products.infrastructure.entities.Product;

public interface ProductMapper {

    static ProductResponse mapper(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getActive());
    }

    static Product toEntity(final ProductRequest request) {
        final String id = null;
        final LocalDate createdAt = null;
        final LocalDate updatedAt = null;
        return Product.with(
                id,
                request.name(),
                request.price(),
                createdAt,
                updatedAt,
                false);
    }
}
