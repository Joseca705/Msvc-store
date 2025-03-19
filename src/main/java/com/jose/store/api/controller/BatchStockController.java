package com.jose.store.api.controller;

import com.jose.store.api.model.projection.BatchStockSimpleInfoProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.infraestructure.abstract_service.IBatchStockService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchStockController {

  private final IBatchStockService batchStockService;

  @PostMapping("")
  public ResponseEntity<CreatedBatchStock> createbatchStock(
    @RequestBody @Valid CreateBatchStockDto batchStockDto
  ) {
    return ResponseEntity.ok(this.batchStockService.create(batchStockDto));
  }

  @GetMapping(path = "/simple-info")
  public ResponseEntity<
    List<BatchStockSimpleInfoProjection>
  > getBatchStockSimpleInfo(
    @RequestParam(name = "ids", required = true) List<Integer> ids
  ) {
    return ResponseEntity.ok(
      this.batchStockService.getBatchStocksSimpleInfo(ids)
    );
  }
}
