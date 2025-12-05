package com.spring.security.springsecuritysection3.repository;

import com.spring.security.springsecuritysection3.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Optional<Customer> findByEmail(String email);

}
