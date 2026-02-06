package com.sparta.crud.repository;

import com.sparta.crud.model.Product;
import com.sparta.crud.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStatusNot(ProductStatus status);
}
