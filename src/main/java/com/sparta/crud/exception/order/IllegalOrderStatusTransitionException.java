package com.sparta.crud.exception.order;

import com.sparta.crud.exception.BadRequestException;
import com.sparta.crud.model.OrderStatus;

public class IllegalOrderStatusTransitionException extends BadRequestException {

    public IllegalOrderStatusTransitionException(OrderStatus current, String action) {
        super("주문 상태 [" + current + "] 에서는 '" + action + "' 작업을 수행할 수 없습니다.");
    }
}
