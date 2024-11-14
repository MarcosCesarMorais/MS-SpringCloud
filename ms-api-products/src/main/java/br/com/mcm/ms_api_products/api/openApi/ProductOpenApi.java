package br.com.mcm.ms_api_products.api.openApi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.mcm.ms_api_products.api.DTO.ProductRequest;
import br.com.mcm.ms_api_products.api.DTO.ProductResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Products")
public interface ProductOpenApi {

        @Operation(summary = "Find product by ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "Invalid product ID", content = @Content(schema = @Schema(ref = "StandardError"))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(ref = "StandardError")))
        })
        ProductResponse findById(
                        @Parameter(description = "Product ID", example = "7cf650b7ea644f9b81c223dec8a2c816", required = true) String id);

        @Operation(summary = "Product list")
        Page<ProductResponse> findAll(Pageable pageable);

        @Operation(summary = "Register product", description = "Product registration")
        ProductResponse create(
                        @RequestBody(description = "Representation of a new product", required = true) ProductRequest request);

        @Operation(summary = "Update product by ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "Invalid product ID", content = @Content(schema = @Schema(ref = "StandardError"))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(ref = "StandardError")))
        })
        ResponseEntity<ProductResponse> update(
                        @Parameter(description = "Product ID", example = "7cf650b7ea644f9b81c223dec8a2c816", required = true) String id,
                        @RequestBody(description = "Representation of a product with updated data", required = true) ProductRequest request);

        @Operation(summary = "Delete a product by ID", responses = {
                        @ApiResponse(responseCode = "204"),
                        @ApiResponse(responseCode = "400", description = "Invalid product ID", content = @Content(schema = @Schema(ref = "StandardError"))),
                        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(ref = "StandardError"))) })
        ResponseEntity<Void> delete(
                        @Parameter(description = "Product ID", example = "7cf650b7ea644f9b81c223dec8a2c816", required = true) String id);
}
