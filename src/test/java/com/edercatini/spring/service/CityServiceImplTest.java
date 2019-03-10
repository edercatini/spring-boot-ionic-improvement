package com.edercatini.spring.service;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CityRepository;
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
public class CityServiceImplTest {

    private static final String OBJECT_NAME = "City";
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;

    @MockBean
    private CityRepository repository;

    @Autowired
    private CityService service;

    @Test
    public void mustFindById() {
        given(repository.findById(anyLong())).willReturn(of(new City(OBJECT_NAME)));
        City object = service.findById(PARAM_ID);
        assertThat(object.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        City object = service.findById(PARAM_ID);
    }

    @Test
    public void mustFindAllObjects() {
        given(repository.findAll()).willReturn(new ArrayList<>(asList(new City(OBJECT_NAME))));
        List<City> categories = service.findAll();
        assertThat(categories.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        List<City> categories = service.findAll();
        assertThat(categories.size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustSaveAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new City(OBJECT_NAME))));
        CityDto dto = new CityDto(OBJECT_NAME);
        List<City> object = service.save(dto);
        assertTrue(object.get(0) instanceof City);
        assertThat(object.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustUpdateAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new City(OBJECT_NAME))));
        given(repository.findById(anyLong())).willReturn(Optional.of(new City()));

        service.update(PARAM_ID, new CityDto(OBJECT_NAME));

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotUpdateAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.update(PARAM_ID, new CityDto(OBJECT_NAME));
    }

    @Test
    public void mustDeleteAnObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(new City(OBJECT_NAME)));
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