package br.com.mcm.ms_api_products.application.services;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mcm.ms_api_products.application.exceptions.ObjectNotFoundException;
import br.com.mcm.ms_api_products.infrastructure.entities.Product;
import br.com.mcm.ms_api_products.infrastructure.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "Product with ID -> %s not found";

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(String id) {
        return fetchOrFail(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(final Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product create(final Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(final String id, final Product product) {
        var productOnBase = fetchOrFail(id);

        if (product.getName() != null) {
            productOnBase.setName(product.getName());
        }
        if (product.getPrice() != null) {
            productOnBase.setPrice(product.getPrice());
        }
        if (product.getActive()) {
            productOnBase.activate();
        } else {
            productOnBase.deactivate();
        }

        return productRepository.save(productOnBase);
    }

    @Override
    @Transactional
    public void deleteById(final String id) {
        var product = fetchOrFail(id);
        if (Objects.nonNull(product)) {
            productRepository.deleteById(product.getId());
        }
    }

    @Transactional(readOnly = true)
    private Product fetchOrFail(final String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format(PRODUCT_NOT_FOUND, id)));
    }
}
