package com.edercatini.spring.service;

import com.edercatini.spring.domain.Product;
import com.edercatini.spring.dto.ProductDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class ProductServiceImplTest {

    private static final String OBJECT_NAME = "Product";
    private static final Double OBJECT_PRICE = 1000D;
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;

    @MockBean
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @Test
    public void mustFindById() {
        given(repository.findById(anyLong())).willReturn(of(new Product(OBJECT_NAME, OBJECT_PRICE)));
        Product object = service.findById(PARAM_ID);
        assertThat(object.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        Product object = service.findById(PARAM_ID);
    }

    @Test
    public void mustFindAllObjects() {
        given(repository.findAll()).willReturn(new ArrayList<>(asList(new Product(OBJECT_NAME, OBJECT_PRICE))));
        List<Product> categories = service.findAll();
        assertThat(categories.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        List<Product> categories = service.findAll();
        assertThat(categories.size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustSaveAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Product(OBJECT_NAME, OBJECT_PRICE))));
        ProductDto dto = new ProductDto(OBJECT_NAME, OBJECT_PRICE);
        List<Product> object = service.save(dto);
        assertTrue(object.get(0) instanceof Product);
        assertThat(object.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustUpdateAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Product(OBJECT_NAME, OBJECT_PRICE))));
        given(repository.findById(anyLong())).willReturn(Optional.of(new Product()));

        service.update(PARAM_ID, new ProductDto(OBJECT_NAME, OBJECT_PRICE));

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotUpdateAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.update(PARAM_ID, new ProductDto(OBJECT_NAME, OBJECT_PRICE));
    }

    @Test
    public void mustDeleteAnObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(new Product(OBJECT_NAME, OBJECT_PRICE)));
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