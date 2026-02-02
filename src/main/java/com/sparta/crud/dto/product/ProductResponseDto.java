package com.sparta.crud.dto.product;

import com.sparta.crud.model.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private String status;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
    }
}
