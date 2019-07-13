package com.edercatini.spring.controller;

import com.edercatini.spring.service.ProductService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static com.edercatini.spring.dataBuilder.domain.ProductDataBuilder.anObject;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest extends ControllerTest {

    private static final String ENTITY = "product";
    private static final String OBJECT_NAME = "Product";
    private static final Double OBJECT_PRICE = 1000d;
    private static final String API_BASE_URL = "/" + ENTITY;
    private static final String INVALID_ENDPOINT = "/api/" + ENTITY;
    private static final String ENDPOINT_ID_PARAM = "/1";
    private static final String API_PAGE_URL = "/" + ENTITY + "/page";
    private static final Integer TOTAL_PAGES = 1;
    private static final Integer TOTAL_ELEMENTS = 2;

    @MockBean
    private ProductService service;

    @Test
    public void mustFindById() throws Exception {
        given(service.findById(anyLong())).willReturn(mockResponse(anObject().build()));

        mvc.perform(get(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.entity.name").value(OBJECT_NAME));
    }

    @Test
    public void mustFindAll() throws Exception {
        given(service.findAll()).willReturn(mockMultipleResponse(anObject().build()));

        mvc.perform(get(API_BASE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.entities[0].entity.name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.entities[0].entity.price").value(OBJECT_PRICE));
    }

    @Test
    public void mustFindByPage() throws Exception {
        given(service.findByPage(anyInt(), anyInt(), anyString(), anyString()))
            .willReturn(mockPageRequest(anObject().build()));

        mvc.perform(get(API_PAGE_URL)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.content[0].price").value(OBJECT_PRICE))
            .andExpect(jsonPath("$.content[1].name").value(OBJECT_NAME))
            .andExpect(jsonPath("$.content[1].price").value(OBJECT_PRICE))
            .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
            .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void mustReturnHttp404IfEndpointDoesNotExist() throws Exception {
        mvc.perform(get(INVALID_ENDPOINT)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void mustSaveAnObject() throws Exception {
        mvc.perform(post(API_BASE_URL)
            .content(mockObjectAsJson(anObject().build()))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    public void mustDenyPostOperationForNonAdminUsers() throws Exception {
        mvc.perform(post(API_BASE_URL)
                .content(mockObjectAsJson(anObject().build()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void mustUpdateAnObject() throws Exception {
        given(service.findById(anyLong())).willReturn(mockResponse(anObject().build()));
        doNothing().when(service).update(any());

        mvc.perform(put(API_BASE_URL + ENDPOINT_ID_PARAM)
            .content(mockObjectAsJson(anObject().build()))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    public void mustDenyPutOperationForNonAdminUsers() throws Exception {
        mvc.perform(put(API_BASE_URL + ENDPOINT_ID_PARAM)
                .content(mockObjectAsJson(anObject().build()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void mustDeleteAnObject() throws Exception {
        doNothing().when(service).delete(anyLong());

        mvc.perform(delete(API_BASE_URL + ENDPOINT_ID_PARAM)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    public void mustDenyDeleteOperationForNonAdminUsers() throws Exception {
        mvc.perform(delete(API_BASE_URL + ENDPOINT_ID_PARAM)
                .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}