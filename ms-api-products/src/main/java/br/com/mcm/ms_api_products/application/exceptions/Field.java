package br.com.mcm.ms_api_products.application.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ObjectProblem")
public record Field(
                @Schema(example = "createAt") String campo,

                @Schema(example = "Creation date is invalid") String mensagem) {

}
