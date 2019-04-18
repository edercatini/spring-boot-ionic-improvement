package com.edercatini.spring.repository;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.enums.CustomerTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.edercatini.spring.builder.domain.CustomerDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    private static final String OBJECT_NAME = "Customer";
    private static final String OBJECT_MAIL = "Mail";
    private static final String OBJECT_DOCUMENT = "9999999999";
    private static final CustomerTypes OBJECT_TYPE = CustomerTypes.PHYSICAL_PERSON;
    private static final Set<String> PHONES = new HashSet<>();
    private static final Long INVALID_PARAM_ID = 9999999999999L;

    @Autowired
    private CustomerRepository repository;

    @Before
    public void setUp() {
        Customer object = anObject().build();
        repository.saveAll(asList(object));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustFindById() {
        List<Customer> objects = repository.findAll();
        Optional<Customer> object = repository.findById(objects.get(0).getId());
        assertThat(object.get().getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustFindAllItems() {
        List<Customer> objects = repository.findAll();
        assertThat(objects.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyItems() {
        this.tearDown();
        List<Customer> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }

    @Test
    public void mustSaveAnItem() {
        Customer object = anObject().build();
        Customer savedObject = repository.save(object);
        assertThat(savedObject.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustRemoveAllItems() {
        repository.deleteAll();
        List<Customer> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }
}