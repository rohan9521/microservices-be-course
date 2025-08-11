package com.learningproj.accounts.controller;

import com.learningproj.accounts.constants.AccountConstants;
import com.learningproj.accounts.dto.AccountsContactInfo;
import com.learningproj.accounts.dto.CustomerDto;
import com.learningproj.accounts.dto.ErrorResponseDto;
import com.learningproj.accounts.dto.ResponseDto;
import com.learningproj.accounts.exception.CustomerAlreadyExistsException;
import com.learningproj.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD APIs for Accounts in ABC Bank",
        description = "This controller provides CRUD operation for accounts along with validation"
)
public class AccountsController {

    @Autowired
    private Environment environment;

    @Value("${build}")
    private String buildVersion;

    @Autowired
    private AccountsContactInfo accountsContactInfo;

    @Autowired
    private IAccountService iAccountService;

    private Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Operation(
            summary = "Create account REST API",
            description = "REST API for creating a new customer and account in ABC Bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) throws CustomerAlreadyExistsException {
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch account REST API",
            description = "REST API for fetching customer and account details by providing mobile number in ABC Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update account REST API",
            description = "REST API for updating customer and account details by providing mobile number in ABC Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account updated"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete account REST API",
            description = "REST API for deleting customer and account details by providing mobile number in ABC Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @RateLimiter(name="getJavaVersion",fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public  ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("MAVEN_HOME"));
    }

    public  ResponseEntity<String> getJavaVersionFallback(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17");
    }

    @Retry(name="getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/contact-info")
    public  ResponseEntity<AccountsContactInfo> getAccountsContactInfo() throws TimeoutException {
        logger.debug("getAccountsContactInfo");
        throw new TimeoutException();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(accountsContactInfo);
    }

    public  ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        logger.debug("getAccountsContactInfoFallback method");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9(static)");
    }

    @GetMapping("/build-info")
    public  ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

}
