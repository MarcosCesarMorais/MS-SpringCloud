package br.com.mcm.ms_api_products.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductRequest(
                @JsonProperty("name") String name,
                @JsonProperty("price") Double price,
                @JsonProperty("active") Boolean active) {

}
