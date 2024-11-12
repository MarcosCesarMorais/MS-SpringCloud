package br.com.mcm.ms_api_products.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "StandardError")
public record StandardError(
        LocalDateTime dateTime,
        @Schema(example = "Invalid data") StandardErrorType typeError,

        @Schema(example = "One or more fields are invalid. Fill in correctly and try again.") String detail,

        @Schema(description = "List of objects or fields that generated the error") List<Field> fields) {

    public StandardError(
            LocalDateTime dateTime,
            StandardErrorType typeError,
            String detail,
            List<Field> fields) {
        this.dateTime = dateTime;
        this.typeError = typeError;
        this.detail = detail;
        this.fields = fields;
    }

    public StandardError(
            LocalDateTime dateTime,
            StandardErrorType typeError,
            String detail) {
        this(dateTime, typeError, detail, null);
    }
}
