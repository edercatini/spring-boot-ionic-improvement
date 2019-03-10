package com.edercatini.spring.controller;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTest {

    private static final String ENTITY = "city";
    private static final String OBJECT_NAME = "Test";
    private static final String API_BASE_URL = "/api/" + ENTITY;
    private static final String INVALID_ENDPOINT = "/" + ENTITY;
    private static final String ENDPOINT_ID_PARAM = "/1";
    private static final String INVALID_REQUEST_BODY = "invalid";
    private static final String API_PAGE_URL = "/api/" + ENTITY + "/page";
    private static final Integer TOTAL_PAGES = 1;
    private static final Integer TOTAL_ELEMENTS = 2;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService service;

    @Test
    public void mustFindById() throws Exception {
        given(service.findById(anyLong())).willReturn(new City(OBJECT_NAME));

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindAll() throws Exception {
        List<City> objects = new ArrayList<>(asList(new City(OBJECT_NAME), new City(OBJECT_NAME)));
        given(service.findAll()).willReturn(new ArrayList<>(objects));

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$[1].name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindByPage() throws Exception {
        List<City> objects = new ArrayList<>(asList(new City(OBJECT_NAME), new City(OBJECT_NAME)));

        given(service.findByPage(anyInt(), anyInt(), anyString(), anyString()))
            .willReturn(new PageImpl<>(asList(new CityDto(OBJECT_NAME), new CityDto(OBJECT_NAME))));

        mvc.perform(MockMvcRequestBuilders.get(API_PAGE_URL)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.content[1].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
            .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS));
    }

    @Test
    public void mustReturnHttp404IfEndpointDoesNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(INVALID_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void mustSaveAnObject() throws Exception {
        City object = new City(OBJECT_NAME);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        given(service.save(any())).willReturn(new ArrayList<>(asList(object)));

        mvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void mustNotSaveDueToInvalidRequestBody() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(INVALID_REQUEST_BODY);
        given(service.save(any())).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void mustUpdateAnObject() throws Exception {
        City object = new City(OBJECT_NAME);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        doNothing().when(service).update(anyLong(), any());

        mvc.perform(MockMvcRequestBuilders.put(API_BASE_URL + ENDPOINT_ID_PARAM)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void mustDeleteAnObject() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(MockMvcRequestBuilders.delete(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}