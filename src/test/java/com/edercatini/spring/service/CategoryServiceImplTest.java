package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CategoryRepository;
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
public class CategoryServiceImplTest {

    private static final String OBJECT_NAME = "Category";
    private static final Long PARAM_ID = 1L;
    private static final Integer NO_ELEMENTS = 0;

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;

    @Test
    public void mustFindById() {
        given(repository.findById(anyLong())).willReturn(of(new Category(OBJECT_NAME)));
        Category object = service.findById(PARAM_ID);
        assertThat(object.getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotFindById() {
        doReturn(empty()).when(repository).findById(anyLong());
        Category object = service.findById(PARAM_ID);
    }

    @Test
    public void mustFindAllObjects() {
        given(repository.findAll()).willReturn(new ArrayList<>(asList(new Category(OBJECT_NAME))));
        List<Category> categories = service.findAll();
        assertThat(categories.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustNotFindAnyObject() {
        given(repository.findAll()).willReturn(new ArrayList<>());
        List<Category> categories = service.findAll();
        assertThat(categories.size(), is(equalTo(NO_ELEMENTS)));
    }

    @Test
    public void mustSaveAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Category(OBJECT_NAME))));
        CategoryDto dto = new CategoryDto(OBJECT_NAME);
        List<Category> object = service.save(dto);
        assertTrue(object.get(0) instanceof Category);
        assertThat(object.get(0).getName(), is(equalTo(OBJECT_NAME)));
    }

    @Test
    public void mustUpdateAnObject() {
        given(repository.saveAll(anyList())).willReturn(new ArrayList<>(asList(new Category(OBJECT_NAME))));
        given(repository.findById(anyLong())).willReturn(Optional.of(new Category()));

        service.update(PARAM_ID, new CategoryDto(OBJECT_NAME));

        verify(repository, atMost(1)).findById(anyLong());
        verify(repository, atMost(1)).saveAll(anyList());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void mustNotUpdateAnObjectDueToInvalidId() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        service.update(PARAM_ID, new CategoryDto(OBJECT_NAME));
    }

    @Test
    public void mustDeleteAnObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(new Category(OBJECT_NAME)));
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