package com.edercatini.spring.repository;

import com.edercatini.spring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional(readOnly = true)
    Optional<Customer> findByMail(String mail);
}