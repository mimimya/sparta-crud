package com.sparta.crud.exception.order;

import com.sparta.crud.exception.BadRequestException;

public class OrderQuantityExceedsStockException extends BadRequestException {
    public OrderQuantityExceedsStockException() {
        super("요청한 수량이 재고를 초과했습니다.");
    }

    public OrderQuantityExceedsStockException(int requested, int available) {
        super("요청 수량 " + requested + "이/가 재고 " + available + "을 초과했습니다.");
    }
}

