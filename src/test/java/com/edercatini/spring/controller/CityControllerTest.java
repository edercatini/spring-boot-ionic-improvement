package com.edercatini.spring.controller;

import com.edercatini.spring.model.City;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.edercatini.spring.dataBuilder.domain.CityDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends ControllerTest {

    private static final String ENTITY = "city";
    private static final String OBJECT_NAME = "City";
    private static final String API_BASE_URL = "/" + ENTITY;
    private static final String INVALID_ENDPOINT = "/api/" + ENTITY;
    private static final String ENDPOINT_ID_PARAM = "/1";
    private static final String API_PAGE_URL = "/" + ENTITY + "/page";
    private static final Integer TOTAL_PAGES = 1;
    private static final Integer TOTAL_ELEMENTS = 2;

    @MockBean
    private CityService service;

    @Test
    public void mustFindById() throws Exception {
        CustomResponse<City> response = new CustomResponse<>();
        response.setEntity(anObject().build());
        given(service.findById(anyLong())).willReturn(response);

        super.mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.entity.name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindAll() throws Exception {
        CustomResponse<City> customResponse = new CustomResponse<>();
        customResponse.setEntity(anObject().build());

        MultipleCustomResponse response = new MultipleCustomResponse();
        response.setEntities(asList(customResponse));

        given(service.findAll()).willReturn(response);

        super.mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.entities[0].entity.name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindByPage() throws Exception {
        given(service.findByPage(anyInt(), anyInt(), anyString(), anyString()))
            .willReturn(new PageImpl<>(asList(anObject().build(), anObject().build())));

        super.mvc.perform(MockMvcRequestBuilders.get(API_PAGE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.content[1].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
            .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS));
    }

    @Test
    public void mustReturnHttp404IfEndpointDoesNotExist() throws Exception {
        super.mvc.perform(MockMvcRequestBuilders.get(INVALID_ENDPOINT)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void mustSaveAnObject() throws Exception {
        City object = anObject().build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);

        super.mvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
            .content(json)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void mustUpdateAnObject() throws Exception {
        City object = anObject().build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);

        CustomResponse response = new CustomResponse();
        response.setEntity(anObject().build());

        given(service.findById(anyLong())).willReturn(response);
        doNothing().when(service).update(any());

        super.mvc.perform(MockMvcRequestBuilders.put(API_BASE_URL + ENDPOINT_ID_PARAM)
            .content(json)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void mustDeleteAnObject() throws Exception {
        doNothing().when(service).delete(anyLong());

        super.mvc.perform(MockMvcRequestBuilders.delete(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}