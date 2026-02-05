package com.sparta.crud.service;

import com.sparta.crud.dto.product.ProductRequestDto;
import com.sparta.crud.dto.product.ProductResponseDto;
import com.sparta.crud.exception.product.ProductNotFoundException;
import com.sparta.crud.model.Product;
import com.sparta.crud.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.create(
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getStock(),
                requestDto.getStatus()
        );
        return new ProductResponseDto(productRepository.save(product));
    }

    // 단일 상품 조회
    public ProductResponseDto getProduct(Long productId) {
        Product product = this.findProduct(productId);
        return new ProductResponseDto(product);
    }

    // 상품 목록 조회 (삭제 포함)
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(ProductResponseDto::new).toList();
    }

    // 상품 목록 조회 (판매중/품절)
    public List<ProductResponseDto> getProductsNotDisabled() {
        return productRepository.findAllByStatusNot("DISABLED").stream().map(ProductResponseDto::new).toList();
    }

    // 상품 수정
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
        Product product = this.findProduct(productId);
        product.update(
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getStock(),
                requestDto.getStatus()
        );
        return new ProductResponseDto(product);
    }

    // 상품 삭제
    @Transactional
    public void softDeleteProduct(Long productId) {
        Product product = this.findProduct(productId);
        product.delete();
    }

    // TODO: -> ProductQueryService
    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }
}
