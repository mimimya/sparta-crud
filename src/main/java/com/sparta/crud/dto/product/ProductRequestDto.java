package com.sparta.crud.dto.product;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private int price;
    private int stock;
    private String status;

}
