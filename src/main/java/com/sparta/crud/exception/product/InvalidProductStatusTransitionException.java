package com.sparta.crud.exception.product;

import com.sparta.crud.exception.BadRequestException;
import com.sparta.crud.model.ProductStatus;

public class InvalidProductStatusTransitionException extends BadRequestException {
    public InvalidProductStatusTransitionException(
            ProductStatus from,
            ProductStatus to
    ) {
        super("상품 상태를 " + from + " 에서 " + to + " 로 변경할 수 없습니다.");
    }
}
