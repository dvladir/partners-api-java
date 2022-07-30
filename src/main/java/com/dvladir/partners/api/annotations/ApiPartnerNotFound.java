package com.dvladir.partners.api.annotations;

import com.dvladir.common.api.dto.IdentifyDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(responseCode = "404", description = "Partner not found", content = {
    @Content(mediaType = "application/json", schemaProperties = {
        @SchemaProperty(
            name = "code",
            schema = @Schema(defaultValue = "PARTNER_NOT_FOUND")
        ),
        @SchemaProperty(
            name = "params",
            array = @ArraySchema(schema = @Schema(implementation = IdentifyDto.class))
        )
    }),
})
public @interface ApiPartnerNotFound {
}
