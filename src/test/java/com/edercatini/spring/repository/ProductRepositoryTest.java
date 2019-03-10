package com.edercatini.spring.repository;

import com.edercatini.spring.domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    private static final String OBJECT_NAME = "Product";
    private static final Double OBJECT_PRICE = 1000D;
    private static final Long INVALID_PARAM_ID = 9999999999999L;

    @Autowired
    private ProductRepository repository;

    @Before
    public void setUp() {
        Product object = new Product(OBJECT_NAME, OBJECT_PRICE);
        repository.saveAll(asList(object));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustFindById() {
        List<Product> objects = repository.findAll();
        Optional<Product> object = repository.findById(objects.get(0).getId());
        assertThat(object.get().getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindById() {
        Optional<Product> object = repository.findById(INVALID_PARAM_ID);
        assertTrue(object.isEmpty());
    }

    @Test
    public void mustFindAllItems() {
        List<Product> objects = repository.findAll();
        assertThat(objects.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyItems() {
        this.tearDown();
        List<Product> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }

    @Test
    public void mustSaveAnItem() {
        Product object = new Product(OBJECT_NAME, OBJECT_PRICE);
        Product savedObject = repository.save(object);
        assertThat(savedObject.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustRemoveAllItems() {
        repository.deleteAll();
        List<Product> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }
}