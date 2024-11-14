package br.com.mcm.ms_api_products.infrastructure.entities;

import java.time.LocalDate;

import br.com.mcm.ms_api_products.utils.IdUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_produto")
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "preco", nullable = false)
    private Double price;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate createdAt;

    @Column(name = "data_alteracao")
    private LocalDate updatedAt;

    @Column(name = "ativo")
    private Boolean active;

    public Product() {
    }

    private Product(final String id, final String name, final Double price, final LocalDate createdAt,
            final LocalDate updatedAt, final Boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public static Product newProduct(final String name, final Double price) {
        final var id = IdUtils.uuid();
        final var createAt = LocalDate.now();
        final LocalDate updatedAt = null;
        final var active = true;
        return new Product(id, name, price, createAt, updatedAt, active);
    }

    public static Product with(final String id, final String name, final Double price, final LocalDate createdAt,
            final LocalDate updatedAt, final Boolean active) {
        return new Product(id, name, price, createdAt, updatedAt, active);
    }

    public static Product with(final Product product) {
        return with(product.getId(), product.getName(), product.getPrice(), product.getCreatedAt(),
                product.getUpdatedAt(), product.getActive());
    }

    public static Product updateProduct(final String name, final Double price, final Boolean active) {
        String id = null;
        LocalDate createdAt = null;
        LocalDate updatedAt = null;
        return new Product(id, name, price, createdAt, updatedAt, active);
    }

    public Product activate() {
        this.updatedAt = LocalDate.now();
        this.active = true;
        return this;
    }

    public Product deactivate() {
        this.updatedAt = LocalDate.now();
        this.active = false;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
