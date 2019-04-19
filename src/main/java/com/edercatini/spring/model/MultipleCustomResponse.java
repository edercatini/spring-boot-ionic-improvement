package com.edercatini.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class MultipleCustomResponse {

    private List<CustomResponse> entities = new ArrayList<>();
}