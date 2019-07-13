package com.edercatini.spring.repository;

import com.edercatini.spring.model.State;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.edercatini.spring.dataBuilder.domain.StateDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StateRepositoryTest extends RepositoryTest {

    private static final String OBJECT_NAME = "State";
    private static final Long INVALID_PARAM_ID = 9999999999999L;

    @Autowired
    private StateRepository repository;

    @Before
    public void setUp() {
        State object = anObject().build();
        repository.saveAll(asList(object));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustFindById() {
        List<State> objects = repository.findAll();
        Optional<State> object = repository.findById(objects.get(0).getId());
        assertThat(object.get().getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustFindAllItems() {
        List<State> objects = repository.findAll();
        assertThat(objects.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyItems() {
        this.tearDown();
        List<State> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }

    @Test
    public void mustSaveAnItem() {
        State object = anObject().build();
        State savedObject = repository.save(object);
        assertThat(savedObject.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustRemoveAllItems() {
        repository.deleteAll();
        List<State> objects = repository.findAll();
        assertThat(objects.size(), is(equalTo(0)));
    }
}