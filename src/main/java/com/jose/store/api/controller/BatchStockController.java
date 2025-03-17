package com.jose.store.api.controller;

import com.jose.store.api.model.projection.BatchStockSimpleInfoProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.request.UpdateBatchAmountDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.UpdatedAmountBatchResponse;
import com.jose.store.infraestructure.service.BatchStockService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PutMapping("/less")
  public ResponseEntity<UpdatedAmountBatchResponse> updateCurrentAmountBatch(
    @RequestBody @Valid UpdateBatchAmountDto dto
  ) {
    UpdatedAmountBatchResponse response =
      this.batchStockService.updateAmountById(dto.getId(), dto.getAmount());
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/simple-info")
  public ResponseEntity<
    List<BatchStockSimpleInfoProjection>
  > getBatchStockSimpleInfo(@RequestParam List<Integer> ids) {
    return ResponseEntity.ok(
      this.batchStockService.getBatchStocksSimpleInfo(ids)
    );
  }
}
