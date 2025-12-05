package com.spring.security.springsecuritysection5.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoansController {

    @GetMapping("/myLoans")
    public String getLoanDetails() {
        return "Gere are the loan details from the DB";
    }
}
