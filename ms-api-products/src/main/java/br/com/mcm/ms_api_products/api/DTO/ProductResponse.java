package br.com.mcm.ms_api_products.api.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponse(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("price") Double price,
                @JsonProperty("createdAt") LocalDate createdAt,
                @JsonProperty("updatedAt") LocalDate updatedAt,
                @JsonProperty("active") Boolean active) {
}
