package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.CustomerTypes;
import com.edercatini.spring.service.CustomerService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private static final String ENTITY = "customer";
    private static final String OBJECT_NAME = "Test";
    private static final String OBJECT_MAIL = "test@test.com";
    private static final String OBJECT_DOCUMENT = "99999999999";
    private static final CustomerTypes OBJECT_TYPE = CustomerTypes.PHYSICAL_PERSON;
    private static final Set<String> PHONES = new HashSet<>();
    private static final String API_BASE_URL = "/" + ENTITY;
    private static final String INVALID_ENDPOINT = "/api/" + ENTITY;
    private static final String ENDPOINT_ID_PARAM = "/1";
    private static final String INVALID_REQUEST_BODY = "invalid";
    private static final String API_PAGE_URL = "/" + ENTITY + "/page";
    private static final Integer TOTAL_PAGES = 1;
    private static final Integer TOTAL_ELEMENTS = 2;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @Test
    public void mustFindById() throws Exception {
        given(service.findById(anyLong())).willReturn(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE));

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindAll() throws Exception {
        List<Customer> objects = new ArrayList<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE), new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE)));
        given(service.findAll()).willReturn(new ArrayList<>(objects));

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$[1].name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindByPage() throws Exception {
        List<Customer> objects = new ArrayList<>(asList(new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE), new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE)));

        given(service.findByPage(anyInt(), anyInt(), anyString(), anyString()))
            .willReturn(new PageImpl<>(asList(new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE.getId(), PHONES), new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE.getId(), PHONES))));

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
        Customer object = new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE);
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
        Customer object = new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE);
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