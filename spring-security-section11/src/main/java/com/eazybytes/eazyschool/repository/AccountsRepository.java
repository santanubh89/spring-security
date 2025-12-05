package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    @PreAuthorize("hasRole('USER')")
    Accounts findByCustomerId(long customerId);

}