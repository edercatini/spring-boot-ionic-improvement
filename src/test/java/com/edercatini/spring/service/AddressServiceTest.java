package com.edercatini.spring.service;

import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.repository.AddressRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static com.edercatini.spring.dataBuilder.domain.AddressDataBuilder.anObject;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class AddressServiceTest extends ServiceTest {

    private static final String OBJECT_PUBLIC_PLACE = "Test";
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 24;
    private static final String DIRECTION = "ASC";
    private static final String PROPERTIES = "NAME";
    private static final Integer TOTAL_PAGES = 1;
    private static final Long TOTAL_ELEMENTS = 2L;

    @MockBean
    private AddressRepository repository;

    @Autowired
    private AddressService service;

    @Test
    public void mustFindById() {
        given(repository.findById(anyLong())).willReturn(of(anObject().build()));
        CustomResponse<Address> object = service.findById(PARAM_ID);
        assertThat(object.getEntity().getPublicPlace(), is(equalTo(OBJECT_PUBLIC_PLACE)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        CustomResponse<Address> object = service.findById(PARAM_ID);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        MultipleCustomResponse objects = service.findAll();
        assertThat(objects.getEntities().size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustFindByPage() {
        given(repository.findAll(any(PageRequest.class))).willReturn(new PageImpl<>(asList(anObject().build(), anObject().build())));
        Page<Address> objects = service.findByPage(PAGE, SIZE, DIRECTION, PROPERTIES);
        assertThat(TOTAL_ELEMENTS, is(equalTo(objects.getTotalElements())));
        assertThat(TOTAL_PAGES, is(equalTo(objects.getTotalPages())));
    }

    @Test
    public void mustUpdateAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(anObject().build())));
        given(repository.findById(anyLong())).willReturn(Optional.of(new Address()));

        service.update(anObject().build());

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test
    public void mustDeleteAnObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(anObject().build()));
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