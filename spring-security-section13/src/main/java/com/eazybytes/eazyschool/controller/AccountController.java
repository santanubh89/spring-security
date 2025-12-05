package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Accounts;
import com.eazybytes.eazyschool.model.Customer;
import com.eazybytes.eazyschool.repository.AccountsRepository;
import com.eazybytes.eazyschool.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        Optional<Customer> __customer = customerRepository.findByEmail(email);
        if (__customer.isPresent()) {
            long customerId = __customer.get().getId();
            return accountsRepository.findByCustomerId(customerId);
        } else {
            return null;
        }
    }
}