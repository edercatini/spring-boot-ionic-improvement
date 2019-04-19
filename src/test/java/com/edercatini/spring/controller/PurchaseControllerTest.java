package com.edercatini.spring.controller;

import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.model.Purchase;
import com.edercatini.spring.service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.edercatini.spring.builder.domain.PurchaseDataBuilder.anObject;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    private static final String ENTITY = "purchase";
    private static final String API_BASE_URL = "/" + ENTITY;
    private static final String INVALID_ENDPOINT = "/api/" + ENTITY;
    private static final String ENDPOINT_ID_PARAM = "/1";
    private static final String API_PAGE_URL = "/" + ENTITY + "/page";
    private static final Integer TOTAL_PAGES = 1;
    private static final Integer TOTAL_ELEMENTS = 2;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PurchaseService service;

    @Test
    public void mustFindById() throws Exception {
        CustomResponse<Purchase> response = new CustomResponse<>();
        response.setEntity(anObject().build());
        given(service.findById(anyLong())).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void mustFindAll() throws Exception {
        CustomResponse<Purchase> customResponse = new CustomResponse<>();
        customResponse.setEntity(anObject().build());

        MultipleCustomResponse response = new MultipleCustomResponse();
        response.setEntities(asList(customResponse));

        given(service.findAll()).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get(API_BASE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void mustFindByPage() throws Exception {
        given(service.findByPage(anyInt(), anyInt(), anyString(), anyString()))
            .willReturn(new PageImpl<>(asList(anObject().build(), anObject().build())));

        mvc.perform(MockMvcRequestBuilders.get(API_PAGE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
            .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS));
    }

    @Test
    public void mustReturnHttp404IfEndpointDoesNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(INVALID_ENDPOINT)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void mustSaveAnObject() throws Exception {
        Purchase object = anObject().build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);

        mvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
            .content(json)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void mustUpdateAnObject() throws Exception {
        Purchase object = anObject().build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);

        CustomResponse response = new CustomResponse();
        response.setEntity(anObject().build());

        given(service.findById(anyLong())).willReturn(response);
        doNothing().when(service).update(any());

        mvc.perform(MockMvcRequestBuilders.put(API_BASE_URL + ENDPOINT_ID_PARAM)
            .content(json)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void mustDeleteAnObject() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(MockMvcRequestBuilders.delete(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}