package com.sparta.crud.dto.product;

import com.sparta.crud.model.ProductStatus;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private int price;
    private int stock;
    private ProductStatus status;

}
