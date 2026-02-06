package com.sparta.crud.dto.order;

import com.sparta.crud.model.Order;
import com.sparta.crud.model.OrderStatus;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private String productName;
    private int quantity;
    private int totalPrice;
    private OrderStatus status;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getPrice();
        this.status = order.getStatus();
    }
}
