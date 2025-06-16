package com.learningproj.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name="Accounts",
        description = "This schema holds accounts information"
)
public class AccountsDto {
    private Long customerId;

    @Schema(
            description = "Account number for ABC bank account"
    )
    @NotNull(message = "Account number cant be null")
    private Long accountNumber;

    @Schema(
            description = "Account type for ABC bank account"
    )
    @NotNull(message = "Account type cant be null")
    @NotEmpty(message = "Account type cant be empty")
    private String accountType;

    @Schema(
            description = "Branch address for ABC bank account"
    )
    @NotBlank(message = "Branch address cant be blank")
    private String branchAddress;
}
