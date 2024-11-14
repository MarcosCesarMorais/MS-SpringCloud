package br.com.mcm.ms_api_products.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
                @NotBlank @JsonProperty("name") String name,

                @DecimalMin(value = "0.01") @JsonProperty(value = "price", required = true) Double price,

                @JsonProperty("active") Boolean active) {
}
