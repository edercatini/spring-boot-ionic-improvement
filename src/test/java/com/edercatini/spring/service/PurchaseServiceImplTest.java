package com.edercatini.spring.service;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.domain.Purchase;
import com.edercatini.spring.dto.PurchaseDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.PurchaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.edercatini.spring.builder.domain.PurchaseDataBuilder.anObject;
import static com.edercatini.spring.builder.dto.PurchaseDtoDataBuilder.dto;
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
public class PurchaseServiceImplTest {

    private static final String CUSTOMER_NAME = "Customer";
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 24;
    private static final String DIRECTION = "ASC";
    private static final String PROPERTIES = "NAME";
    private static final Integer TOTAL_PAGES = 1;
    private static final Long TOTAL_ELEMENTS = 2L;

    @MockBean
    private PurchaseRepository repository;

    @Autowired
    private PurchaseService service;

    @Test
    public void mustFindById() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);

        given(repository.findById(anyLong())).willReturn(of(anObject().withCustomer(customer).build()));
        Purchase object = service.findById(PARAM_ID);
        assertThat(object.getCustomer().getName(), is(equalTo(CUSTOMER_NAME)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        Purchase object = service.findById(PARAM_ID);
    }

    @Test
    public void mustFindAllObjects() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        given(repository.findAll()).willReturn(new ArrayList<>(asList(anObject().withCustomer(customer).build())));

        List<Purchase> categories = service.findAll();
        assertThat(categories.get(0).getCustomer().getName(), is(equalTo(CUSTOMER_NAME)));
    }

    @Test
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        List<Purchase> objects = service.findAll();
        assertThat(objects.size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustFindByPage() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        given(repository.findAll(any(PageRequest.class)))
            .willReturn(new PageImpl<>(asList(anObject().build(), anObject().withCustomer(customer).build())));

        Page<PurchaseDto> objects = service.findByPage(PAGE, SIZE, DIRECTION, PROPERTIES);
        assertThat(TOTAL_ELEMENTS, is(equalTo(objects.getTotalElements())));
        assertThat(TOTAL_PAGES, is(equalTo(objects.getTotalPages())));
    }

    @Test
    public void mustSaveAnObject() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(anObject().withCustomer(customer).build())));

        PurchaseDto dto = dto().withCustomer(customer).build();
        List<Purchase> object = service.save(dto);
        assertTrue(object.get(0) instanceof Purchase);
        assertThat(object.get(0).getCustomer().getName(), is(equalTo(CUSTOMER_NAME)));
    }

    @Test
    public void mustUpdateAnObject() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(anObject().withCustomer(customer).build())));
        given(repository.findById(anyLong())).willReturn(Optional.of(new Purchase()));

        service.update(PARAM_ID, dto().build());

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotUpdateAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.update(PARAM_ID, dto().build());
    }

    @Test
    public void mustDeleteAnObject() {
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        given(repository.findById(anyLong())).willReturn(Optional.of(anObject().withCustomer(customer).build()));

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