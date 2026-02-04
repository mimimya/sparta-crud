package com.sparta.crud.controller;

import com.sparta.crud.dto.product.ProductRequestDto;
import com.sparta.crud.dto.product.ProductResponseDto;
import com.sparta.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto savedDto = productService.createProduct(requestDto);
        return ResponseEntity
                .created(URI.create("/products/" + savedDto.getId()))
                .body(savedDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
        ProductResponseDto dto = productService.getProduct(productId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> dtos = productService.getProductsNotDisabled();
        return ResponseEntity.ok(dtos);
    }

    // 상품 수정 / 상태 변경
    @PutMapping("{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto dto = productService.updateProduct(productId, requestDto);
        return ResponseEntity.ok(dto);
    }

    // TODO: 1. 상태 변경 API 따로 분리

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.softDeleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
} //TODO: 2. 예외 처리 (ControllerAdvice)
