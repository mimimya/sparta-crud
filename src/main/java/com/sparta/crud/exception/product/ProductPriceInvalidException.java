package com.sparta.crud.exception.product;

import com.sparta.crud.exception.BadRequestException;

public class ProductPriceInvalidException extends BadRequestException {
    public ProductPriceInvalidException() {
        super("상품의 가격은 0원 이상이어야 합니다.");
    }
}
