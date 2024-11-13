package br.com.mcm.ms_api_products.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.mcm.ms_api_products.entities.Product;
import br.com.mcm.ms_api_products.exceptions.ObjectNotFoundException;
import br.com.mcm.ms_api_products.repositories.ProductRepository;
import br.com.mcm.ms_api_products.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductTestUnit {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should return a list with only one product")
    void shouldReturnAList_withOnlyOneProduct() {
        int page = 0;
        int size = 1;
        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        var name = "Quake 3";
        Double price = 250.00;
        var createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        var active = true;

        var product = Product.with(id, name, price, createdAt, updatedAt, active);
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product), pageable, 1);
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        Page<Product> products = productService.findAll(pageable);

        assertNotNull(products);
        assertEquals(1, products.getTotalElements());
        assertEquals(1, products.getNumberOfElements());
        assertEquals(0, products.getNumber());
        assertEquals(size, products.getSize());
        assertTrue(products.hasContent());
        assertEquals(id, products.getContent().get(0).getId());
        assertEquals(name, products.getContent().get(0).getName());
        assertEquals(price, products.getContent().get(0).getPrice());
        assertEquals(createdAt, products.getContent().get(0).getCreatedAt());
        assertEquals(updatedAt, products.getContent().get(0).getUpdatedAt());
        assertEquals(active, products.getContent().get(0).getActive());

        verify(productRepository).findAll(pageable);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Should return a product when a valid ID is provided")
    void shouldReturnAProduct_whenAValidIdIsProvided() {

        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        var name = "Quake 3";
        Double price = 250.00;
        var createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        var active = true;
        var product = Product.with(id, name, price, createdAt, updatedAt, active);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product productSave = productService.findById(id);

        assertNotNull(productSave);
        assertEquals(id, productSave.getId());
        assertEquals(name, productSave.getName());
        assertEquals(price, productSave.getPrice());
        assertEquals(createdAt, productSave.getCreatedAt());
        assertEquals(updatedAt, productSave.getUpdatedAt());
        assertEquals(active, productSave.getActive());
    }

    @Test
    @DisplayName("Should throw an exception when the ID does not exist")
    public void shouldThrowAnException_WhenTheIdDoesNotExist() {
        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class,
                () -> productService.findById(id));
        assertEquals("Product with ID -> 32b0fde4941c4e17a4f8b92b93f6998a not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete a product when the ID exists")
    void shouldDeleteAProduct_whenTheIdExists() {
        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        var name = "Quake 3";
        Double price = 250.00;
        var createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        var active = true;
        var product = Product.with(id, name, price, createdAt, updatedAt, active);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
        verify(productRepository, times(1)).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Should update a product when the period exists")
    void shouldUpdateAProduct_whenThePeriodExists() {
        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        var name = "Quake 3";
        Double price = 250.00;
        var createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        var active = true;
        var productNew = Product.with(id, "Quake 2", 200.00, createdAt, LocalDate.now(), active);
        var productOld = Product.with(id, name, price, createdAt, updatedAt, active);

        when(productRepository.findById(id)).thenReturn(Optional.of(productOld));
        when(productRepository.save(any(Product.class))).thenReturn(productOld);
        var productUpdate = productService.update(id, productNew);

        assertNotNull(productUpdate);
        assertEquals(productNew.getName(), productUpdate.getName());
        assertEquals(productNew.getPrice(), productUpdate.getPrice());
        assertEquals(productNew.getId(), productUpdate.getId());
        assertEquals(productNew.getActive(), productUpdate.getActive());
        assertEquals(productNew.getCreatedAt(), productUpdate.getCreatedAt());
        assertEquals(productNew.getUpdatedAt(), productUpdate.getUpdatedAt());

        verify(productRepository).findById(id);
        verify(productRepository).save(argThat(product -> product.getId().equals(productNew.getId()) &&
                product.getName().equals(productNew.getName()) &&
                product.getPrice().equals(productNew.getPrice()) &&
                product.getActive().equals(productNew.getActive()) &&
                product.getCreatedAt().equals(productNew.getCreatedAt()) &&
                product.getUpdatedAt().equals(productNew.getUpdatedAt())));
    }

    @Test
    @DisplayName("Should throw an exception when updating with a non-existent ID")
    void shouldThrowAnException_whenUpdatingWithANonExistentID() {
        var id = "32b0fde4941c4e17a4f8b92b93f6998a";
        var name = "Quake 3";
        Double price = 250.00;
        var createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        var active = true;
        var product = Product.updateProduct(name, price, active);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class,
                () -> productService.update(id, product));

        assertEquals("Product with ID -> 32b0fde4941c4e17a4f8b92b93f6998a not found", exception.getMessage());
    }
}
