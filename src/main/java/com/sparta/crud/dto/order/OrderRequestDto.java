package com.sparta.crud.dto.order;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long productId;
    private int quantity;
}
