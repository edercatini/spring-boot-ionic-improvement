package com.edercatini.spring.repository;

import com.edercatini.spring.model.City;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.edercatini.spring.dataBuilder.domain.CityDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CityRepositoryTest extends RepositoryTest {

    private static final String OBJECT_NAME = "City";
    private static final Long INVALID_PARAM_ID = 9999999999999L;

    @Autowired
    private CityRepository repository;

    @Before
    public void setUp() {
        City object = anObject().build();
        repository.saveAll(asList(object));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustFindById() {
        List<City> objects = repository.findAll();
        Optional<City> object = repository.findById(objects.get(0).getId());
        assertThat(object.get().getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustFindAllItems() {
        List<City> objects = repository.findAll();
        assertThat(objects.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyItems() {
        this.tearDown();
        List<City> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }

    @Test
    public void mustSaveAnItem() {
        City object = anObject().build();
        City savedObject = repository.save(object);
        assertThat(savedObject.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustRemoveAllItems() {
        repository.deleteAll();
        List<City> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }
}