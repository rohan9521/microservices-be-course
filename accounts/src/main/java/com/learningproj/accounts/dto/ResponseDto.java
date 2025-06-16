package com.learningproj.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema for response"
)
public class ResponseDto {
    @Schema(
            description = "Status code for response"

    )
    private String statusCode;

    @Schema(
            description = "Status message for response"
    )
    private String statusMessage;
}
