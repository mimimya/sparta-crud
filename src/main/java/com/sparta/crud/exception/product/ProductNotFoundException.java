package com.sparta.crud.exception.product;

import com.sparta.crud.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super("상품을 찾을 수 없습니다.");
    }
}
