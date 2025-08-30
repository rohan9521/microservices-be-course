package com.learningproj.accounts.controller;

import com.learningproj.accounts.dto.CustomerDetailsDto;
import com.learningproj.accounts.dto.ErrorResponseDto;
import com.learningproj.accounts.service.IAccountService;
import com.learningproj.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD APIs for customers details in ABC Bank",
        description = "This controller provides CRUD operation for customers details"
)
@RestController
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("abcBank-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber) {
        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber,correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

    }
}
