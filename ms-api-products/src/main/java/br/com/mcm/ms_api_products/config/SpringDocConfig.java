package br.com.mcm.ms_api_products.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mcm.ms_api_products.exceptions.Field;
import br.com.mcm.ms_api_products.exceptions.StandardError;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MCM")
                        .version("v1")
                        .description("REST MS-API-PRODUCT")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("github")
                        .url("https://github.com/MarcosCesarMorais/MS-SpringCloud.git"))
                .tags(
                        Arrays.asList(
                                new Tag().name("Products").description("Product management")))
                .components(new Components()
                        .schemas(generateSchemas())
                        .responses(generateResponses()));
    }

    private Map<String, Schema> generateSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(StandardError.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Field.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> generateResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problem")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Invalid request")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Resource not found")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Resource does not have representation that could be accepted by the consumer")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Internal server error")
                .content(content));

        return apiResponseMap;
    }

}
