package com.spring.security.springsecuritysection4.service;

import com.spring.security.springsecuritysection4.model.Customer;
import com.spring.security.springsecuritysection4.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class CustomerService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository, final PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));
        Collection<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(customer.getRole()));
        return User.withUsername(username).password(customer.getPwd()).authorities(authorities).build();
    }

    public void registerCustomer(Customer customer) {
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer.getId() > 0) {
            System.out.println("Customer registered successfully with id: " + savedCustomer.getId());
        } else {
            throw new RuntimeException("Error occurred while registering the customer");
        }
    }
}
