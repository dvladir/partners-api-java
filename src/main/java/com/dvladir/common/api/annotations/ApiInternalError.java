package com.dvladir.common.api.annotations;

import com.dvladir.common.api.dto.ErrorResponseDto;
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
    @ApiResponse(responseCode = "500", description = "Internal Error", content = {
        @Content(mediaType = "application/json", schemaProperties = {
            @SchemaProperty(
                name = "code",
                schema = @Schema(defaultValue = "INTERNAL_ERROR")
            ),
        })
    }),
    @ApiResponse(description = "Error Object", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
    }),
})
public @interface ApiInternalError {
}
