package com.learningproj.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error response",
        description = "Schema for error response"
)
public class ErrorResponseDto {
    @Schema(
            description = "Api path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code for error occurred"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message for error occurred"
    )
    private String errorMessage;

    @Schema(
            description = "Error time for error occurred"
    )
    private LocalDateTime errorTime;
}
