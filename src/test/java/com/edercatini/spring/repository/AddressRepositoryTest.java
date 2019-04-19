package com.edercatini.spring.repository;

import com.edercatini.spring.builder.domain.CustomerDataBuilder;
import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.edercatini.spring.builder.domain.AddressDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressRepositoryTest {

    private static final String OBJECT_PUBLIC_PLACE = "Test";
    private static final Long INVALID_PARAM_ID = 9999999999999L;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository repository;

    @Before
    public void setUp() {
        Customer customer = CustomerDataBuilder.anObject().build();
        customerRepository.save(customer);

        Address object = anObject().withCustomer(customer).build();
        repository.saveAll(asList(object));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustFindById() {
        List<Address> objects = repository.findAll();
        Optional<Address> object = repository.findById(objects.get(0).getId());
        assertThat(object.get().getPublicPlace(), is(equalTo(OBJECT_PUBLIC_PLACE)));
    }

    @Test
    public void mustFindAllItems() {
        List<Address> objects = repository.findAll();
        assertThat(objects.get(0).getPublicPlace(), is(equalTo(OBJECT_PUBLIC_PLACE)));
    }

    @Test
    public void mustNotFindAnyItems() {
        this.tearDown();
        List<Address> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }

    @Test
    public void mustSaveAnItem() {
        Customer customer = CustomerDataBuilder.anObject().build();
        customerRepository.save(customer);

        Address object = anObject().withCustomer(customer).build();
        Address savedObject = repository.save(object);
        assertThat(savedObject.getPublicPlace(), is(equalTo(OBJECT_PUBLIC_PLACE)));
    }

    @Test
    public void mustRemoveAllItems() {
        repository.deleteAll();
        List<Address> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }
}