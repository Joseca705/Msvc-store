package com.jose.store.api.controller;

import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.infraestructure.service.BatchStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchStockController {

  private final BatchStockService batchStockService;

  @PostMapping("")
  public ResponseEntity<CreatedBatchStock> createbatchStock(
    @RequestBody @Valid CreateBatchStockDto batchStockDto
  ) {
    return ResponseEntity.ok(this.batchStockService.create(batchStockDto));
  }
}
