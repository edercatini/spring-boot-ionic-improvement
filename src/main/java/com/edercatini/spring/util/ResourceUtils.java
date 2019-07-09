package com.edercatini.spring.util;

import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.Entity;

import java.io.Serializable;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

public class ResourceUtils implements Serializable {

    private static final long serialVersionUID = -5969745812238645378L;

    public static URI createUri(Entity entity) {
        return fromCurrentContextPath().path("/category/{id}").buildAndExpand(entity.getId()).toUri();
    }

    public static CustomResponse setResponse(Entity entity, URI uri) {
        return new CustomResponse<>(entity, uri);
    }
}