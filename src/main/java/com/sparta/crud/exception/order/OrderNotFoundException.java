package com.sparta.crud.exception.order;

import com.sparta.crud.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException() {
        super("존재하지 않는 주문입니다.");
    }

    public OrderNotFoundException(int requestedId) {
        super("요청된 주문 Id" + requestedId + "는 존재하지 않는 주문입니다.");
    }
}