package com.spring.security.springsecuritysection4.resources;

import com.spring.security.springsecuritysection4.model.Customer;
import com.spring.security.springsecuritysection4.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerRegistrationController {

    private final CustomerService customerService;

    public CustomerRegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*curl --location '/customer/register' \
            --header 'Content-Type: application/json' \
            --data-raw '{
            "email": "test@example.com",
            "pwd": "test123",
            "role": "user"
    }'*/

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        try {
            customerService.registerCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering customer: " + e.getMessage());
        }
    }

}
