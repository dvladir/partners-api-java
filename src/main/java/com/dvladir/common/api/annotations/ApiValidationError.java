package com.dvladir.common.api.annotations;

import com.dvladir.common.api.dto.ValidationErrorContainerDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Validation error", content = {
        @Content(mediaType = "application/json", schemaProperties = {
            @SchemaProperty(
                name = "code",
                schema = @Schema(defaultValue = "VALIDATION_ERROR")
            ),
            @SchemaProperty(
                name = "params",
                array = @ArraySchema(schema = @Schema(implementation = ValidationErrorContainerDto.class))
            )
        })
    })
})
public @interface ApiValidationError {
}
