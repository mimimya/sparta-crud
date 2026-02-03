package com.sparta.crud.service;

import com.sparta.crud.dto.product.ProductRequestDto;
import com.sparta.crud.dto.product.ProductResponseDto;
import com.sparta.crud.model.Product;
import com.sparta.crud.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setName(requestDto.getName());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setStatus(requestDto.getStatusOrDefault()); // ACTIVE / DISABLED ...
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

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

        product.setName(requestDto.getName());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setStatus(requestDto.getStatusOrDefault()); // ACTIVE / DISABLED ...
        product.setUpdatedAt(LocalDateTime.now());

        return new ProductResponseDto(product);
    }

    // 상품 삭제
    @Transactional
    public ProductResponseDto softDeleteProduct(Long productId) {
        Product product = this.findProduct(productId);

        product.setStatus("DELETE");
        product.setUpdatedAt(LocalDateTime.now());

        return new ProductResponseDto(product);
    }

    // 상품 DB에서 삭제
    public boolean deleteProduct(Long productId) {
        try { //??: 클라이언트 오류 전달
            Product product = findProduct(productId);
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO: -> ProductQueryService
    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
