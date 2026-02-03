package com.sparta.crud.controller;

import com.sparta.crud.dto.product.ProductRequestDto;
import com.sparta.crud.dto.product.ProductResponseDto;
import com.sparta.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public List<ProductResponseDto> getProducts() {
        return productService.getProductsNotDisabled();
    }

    // 상품 수정 / 상태 변경
    @PutMapping("{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto requestDto) {
        return productService.updateProduct(productId, requestDto);
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ProductResponseDto deleteProduct(@PathVariable Long productId) {
        return productService.softDeleteProduct(productId);
    }
}
