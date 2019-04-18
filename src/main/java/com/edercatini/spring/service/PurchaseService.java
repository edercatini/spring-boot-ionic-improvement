package com.edercatini.spring.service;

import com.edercatini.spring.domain.Purchase;
import com.edercatini.spring.dto.PurchaseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PurchaseService {

    Purchase findById(Long id);

    List<Purchase> findAll();

    Page<PurchaseDto> findByPage(Integer page, Integer size, String direction, String properties);

    Purchase save(PurchaseDto dtu);

    void update(Long id, PurchaseDto dto);

    void delete(Long id);
}