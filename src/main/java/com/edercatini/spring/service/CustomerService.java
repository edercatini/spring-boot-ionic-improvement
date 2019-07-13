package com.edercatini.spring.service;

import com.edercatini.spring.enums.Role;
import com.edercatini.spring.exception.AuthorizationException;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.Customer;
import com.edercatini.spring.repository.CustomerRepository;
import com.edercatini.spring.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class CustomerService extends AbstractService<Customer> {

    @Value("${application.validate.user:false}")
    private Boolean mustValidateUser;

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

    @Override
    public CustomResponse findById(Long id) {

        if (mustValidateUser) {
            UserSecurity user = UserService.authenticated();

            if (isNull(user) || !user.hasRole(Role.ADMIN) || !id.equals(user.getId())) {
                throw new AuthorizationException("Access denied");
            }
        }

        return super.findById(id);
    }
}