package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Customer;
import com.eazybytes.eazyschool.model.Loans;
import com.eazybytes.eazyschool.repository.CustomerRepository;
import com.eazybytes.eazyschool.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;

    private final CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        Optional<Customer> __customer = customerRepository.findByEmail(email);
        if (__customer.isPresent()) {
            long customerId = __customer.get().getId();
            return loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
        } else {
            return null;
        }
    }

}