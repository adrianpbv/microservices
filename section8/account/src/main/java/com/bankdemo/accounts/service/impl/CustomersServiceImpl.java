package com.bankdemo.accounts.service.impl;

import com.bankdemo.accounts.dto.AccountsDto;
import com.bankdemo.accounts.dto.CardsDto;
import com.bankdemo.accounts.dto.CustomerDetailsDto;
import com.bankdemo.accounts.dto.LoansDto;
import com.bankdemo.accounts.entity.Accounts;
import com.bankdemo.accounts.entity.Customer;
import com.bankdemo.accounts.exception.ResourceNotFoundException;
import com.bankdemo.accounts.mapper.AccountsMapper;
import com.bankdemo.accounts.mapper.CustomerMapper;
import com.bankdemo.accounts.repository.AccountsRepository;
import com.bankdemo.accounts.repository.CustomerRepository;
import com.bankdemo.accounts.service.ICustomersService;
import com.bankdemo.accounts.service.client.CardsFeignClient;
import com.bankdemo.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    // TODO step 9 Add the services to get data from other ms. OpenFeign takes care of fetching data and balancing the load and Eureka provides ip of the instance of the ms (discovery service)
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
