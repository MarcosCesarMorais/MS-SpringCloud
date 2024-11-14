package br.com.mcm.ms_api_products.application.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "StandardError")
public record StandardError(
        @Schema(example = "2024/11/13") LocalDateTime dateTime,
        @Schema(example = "Invalid message") String type,
        @Schema(example = "One or more fields are invalid. Fill in correctly and try again.") String detail,
        @Schema(description = "List of objects or fields that generated the error") List<Field> fields) {

    public StandardError(
            final LocalDateTime dateTime,
            final String type,
            final String detail,
            List<Field> fields) {
        this.dateTime = dateTime;
        this.type = type;
        this.detail = detail;
        this.fields = fields;
    }

    public StandardError(
            final LocalDateTime dateTime,
            final String type,
            final String detail) {
        this(dateTime, type, detail, null);
    }
}
