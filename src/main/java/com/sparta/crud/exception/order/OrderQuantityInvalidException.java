package com.sparta.crud.exception.order;

import com.sparta.crud.exception.BadRequestException;

public class OrderQuantityInvalidException extends BadRequestException {
    public OrderQuantityInvalidException() {
        super("주문 수량은 1개 이상이어야 합니다.");
    }

    public OrderQuantityInvalidException(int requested) {
        super("주문된 수량: " + requested + " 주문 수량은 1개 이상이어야 합니다.");
    }
}
