package com.learningproj.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name="Customer",
        description = "This schema holds customer information"
)
public class CustomerDto {
    private Long customerId;

    @Schema(
            description = "Customer Name",
            example = "Raja"
    )
    @NotNull(message = "Name cant be null")
    @Size(min = 5,max=30, message = "Length of the name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Customer email",
            example = "rajaro99999@gmail.com"
    )
    @NotEmpty(message = "Email cant be empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Customer mobile number",
            example = "0123456789"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    @Valid
    private AccountsDto accountsDto;
}
