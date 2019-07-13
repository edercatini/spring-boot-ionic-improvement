package com.edercatini.spring.controller;

import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @Autowired
    MockMvc mvc;

    String mockObjectAsJson(Object dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

    public CustomResponse<Object> mockResponse(Object object) {
        CustomResponse<Object> response = new CustomResponse<>();
        response.setEntity(object);

        return response;
    }

    public MultipleCustomResponse mockMultipleResponse(Object object) {
        MultipleCustomResponse response = new MultipleCustomResponse();
        response.setEntities(Collections.singletonList(this.mockResponse(object)));

        return response;
    }

    public Page mockPageRequest(Object object) {
        return new PageImpl<>(Arrays.asList(object, object));
    }
}