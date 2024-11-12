package br.com.mcm.ms_api_products.api.DTO;

import br.com.mcm.ms_api_products.entities.Product;

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
}
