package com.edercatini.spring.service;

import com.edercatini.spring.model.Customer;
import com.edercatini.spring.repository.CustomerRepository;
import com.edercatini.spring.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) {
        return new UserSecurity(this.findCustomerByMail(mail));
    }

    private Customer findCustomerByMail(String mail) {
        Optional<Customer> customer = customerRepository.findByMail(mail);
        return customer.orElseThrow(() -> new UsernameNotFoundException("The following mail: " + mail + " does not exist"));
    }
}