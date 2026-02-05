package com.sparta.crud.exception.product;


import com.sparta.crud.exception.BadRequestException;

public class ProductStockInvalidException extends BadRequestException {
    public ProductStockInvalidException() {
        super("재고는 0개 이상이어야 합니다.");
    }
}
