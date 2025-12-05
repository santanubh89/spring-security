package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Accounts;
import com.eazybytes.eazyschool.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {
        Accounts byCustomerId = accountsRepository.findByCustomerId(id);
        log.info("Account details for customer id: " + byCustomerId.getCustomerId());
        return byCustomerId;
    }

    @GetMapping("/myAccount/preauth1")
    @PreAuthorize("hasRole('MANAGER')")
    public String getAccountDetailsPreAuthorized1() {
        return "OK";
    }

    @GetMapping("/myAccount/preauth2")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public String getAccountDetailsPreAuthorized2() {
        return "OK";
    }

    @GetMapping("/myAccount/preauth3")
    @PreAuthorize("hasAuthority('VIEWACCOUNT')")
    public String getAccountDetailsPreAuthorized3() {
        return "OK";
    }

    // /myAccount/preauth4/foo@example.com
    @GetMapping("/myAccount/preauth4/{id}")
    @PreAuthorize("#id == authentication.principal")
        public String getAccountDetailsPreAuthorized4(@PathVariable String id, Authentication authentication) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        System.out.println("id: " + authentication.getPrincipal());
        return "OK";
    }

    ///myAccount/postauth1/12345 will work
    @GetMapping("/myAccount/postauth1/{customerId}")
    @PostAuthorize("returnObject.customerId == #customerId")
    public Accounts getAccountDetailsPostAuthorized1(@PathVariable long customerId) {
        Accounts a = new Accounts();
        a.setCustomerId(12345L);
        a.setAccountNumber(123L);
        a.setAccountType("Savings");
        log.info("Inside post authorize method");
        return a;
    }

    @PostMapping("/myAccount/prefilter")
    @PreFilter("filterObject.accountType == 'Savings'")
    public List<Accounts> saveAccountsPreFilter(@RequestBody List<Accounts> accountsList) {
        Accounts a = accountsList.getFirst();
        if (null != a) {
            a.setBranchAddress("Spring Boot");
            a.setCreateDt(new Date(System.currentTimeMillis()));
        }
        log.info("Inside pre filter method: {}", accountsList);
        return accountsList;
    }

    @PostMapping("/myAccount/postfilter")
    @PostFilter("filterObject.accountType == 'Savings'")
    public List<Accounts> saveAccountsPostFilter(@RequestBody List<Accounts> accountsList) {
        log.info("Inside post filter method: {}", accountsList);
        List<Accounts> returnList = new ArrayList<>(accountsList);
        return returnList;
    }

}