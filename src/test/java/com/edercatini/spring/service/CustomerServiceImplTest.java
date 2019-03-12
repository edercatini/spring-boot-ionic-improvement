package com.edercatini.spring.service;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.CustomerTypes;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    private static final String OBJECT_NAME = "Customer";
    private static final String OBJECT_MAIL = "Mail";
    private static final String OBJECT_DOCUMENT = "9999999999";
    private static final CustomerTypes OBJECT_TYPE = CustomerTypes.PHYSICAL_PERSON;
    private static final Set<String> PHONES = new HashSet<>();
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 24;
    private static final String DIRECTION = "ASC";
    private static final String PROPERTIES = "NAME";
    private static final Integer TOTAL_PAGES = 1;
    private static final Long TOTAL_ELEMENTS = 2L;

    @MockBean
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    @Test
    public void mustFindById() {
        given(repository.findById(anyLong())).willReturn(of(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE)));
        Customer object = service.findById(PARAM_ID);
        assertThat(object.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        Customer object = service.findById(PARAM_ID);
    }

    @Test
    public void mustFindAllObjects() {
        given(repository.findAll()).willReturn(new ArrayList<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE))));
        List<Customer> categories = service.findAll();
        assertThat(categories.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        List<Customer> objects = service.findAll();
        assertThat(objects.size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustFindByPage() {
        given(repository.findAll(any(PageRequest.class)))
            .willReturn(new PageImpl<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE), new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE))));

        Page<CustomerDto> objects = service.findByPage(PAGE, SIZE, DIRECTION, PROPERTIES);
        assertThat(TOTAL_ELEMENTS, is(equalTo(objects.getTotalElements())));
        assertThat(TOTAL_PAGES, is(equalTo(objects.getTotalPages())));
    }

    @Test
    public void mustSaveAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE))));
        CustomerDto dto = new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE.getId(), PHONES);
        List<Customer> object = service.save(dto);
        assertTrue(object.get(0) instanceof Customer);
        assertThat(object.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustUpdateAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE))));
        given(repository.findById(anyLong())).willReturn(Optional.of(new Customer()));

        service.update(PARAM_ID, new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE.getId(), PHONES));

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotUpdateAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.update(PARAM_ID, new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE.getId(), PHONES));
    }

    @Test
    public void mustDeleteAnObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE)));
        doNothing().when(repository).deleteById(anyLong());
        service.delete(PARAM_ID);

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).deleteById(anyLong());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotDeleteAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.delete(PARAM_ID);
    }
}