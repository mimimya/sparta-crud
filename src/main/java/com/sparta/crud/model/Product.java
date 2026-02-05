package com.sparta.crud.model;

import com.sparta.crud.exception.order.OrderQuantityExceedsStockException;
import com.sparta.crud.exception.product.ProductPriceInvalidException;
import com.sparta.crud.exception.product.ProductStockInvalidException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected Product() {}
@Getter
@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private int price; // 제품 현재 판매 가격
    private int stock;
    private String status; // TODO: Enum으르 변경
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    public Product(String name, int price, int stock, String status) {
        validate(price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        checkStock();
    }

    public static Product create(String name, int price, int stock, String status) {
        Product createdPproduct = new Product(name, price, stock, resolveStatus(status));
        createdPproduct.createdAt = LocalDateTime.now();
        return createdPproduct;
    }

    public void update(String name, int price, int stock, String status) {
        validate(price, stock);

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = resolveStatus(status);
        this.updatedAt = LocalDateTime.now();

        checkStock();
    }

    public void delete() {
        this.status = "DELETED";
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStockByOrder(int quantity) {
        if (this.stock < quantity) {
            throw new OrderQuantityExceedsStockException();
        }
        this.stock -= quantity;
        this.updatedAt = LocalDateTime.now();
        checkStock();
    }

    private static String resolveStatus(String status) {
        return (status == null || status.isBlank()) ? "ACTIVE" : status;
    }

    private static void validate(int price, int stock) {
        if (price < 0) {
            throw new ProductPriceInvalidException();
        }
        if (stock < 0) {
            throw new ProductStockInvalidException();
        }
    }

    private void checkStock() {
        if (this.stock == 0) {
            this.status = "NOTINSTOCK";
        }
    }

}
