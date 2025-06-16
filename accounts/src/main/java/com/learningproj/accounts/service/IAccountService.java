package com.learningproj.accounts.service;

import com.learningproj.accounts.dto.AccountsDto;
import com.learningproj.accounts.dto.CustomerDto;
import com.learningproj.accounts.exception.CustomerAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
     void createAccount(CustomerDto customerDto) throws CustomerAlreadyExistsException;
     CustomerDto fetchAccount(String mobileNumber);
     boolean updateAccount(CustomerDto customerDto);
     boolean deleteAccount(String mobileNumber);
}
