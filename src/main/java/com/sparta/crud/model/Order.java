package com.sparta.crud.model;


import com.sparta.crud.exception.order.OrderQuantityInvalidException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private int price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private Order(Product product, int quantity) {
        validate(quantity);
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice() * quantity;
        this.status = "CREATED";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Order create(Product product, int quantity) {
        product.decreaseStockByOrder(quantity);
        return new Order(product, quantity);
    }

    private void validate(int quantity) {
        if (quantity <= 0) {
            throw new OrderQuantityInvalidException();
        }
    }
}