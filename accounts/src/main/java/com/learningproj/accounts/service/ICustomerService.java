package com.learningproj.accounts.service;

import com.learningproj.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId);
}
