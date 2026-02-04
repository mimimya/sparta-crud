package com.sparta.crud.model;

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
    }

    public void delete() {
        this.status = "DELETED";
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStockByOrder(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("요청한 수량이 재고를 초과했습니다.");
        }
        this.stock -= quantity;
        this.updatedAt = LocalDateTime.now();

        if (this.stock == 0) {
            this.status = "NOTINSTOCK";
        }
    }

    private static String resolveStatus(String status) {
        return (status == null || status.isBlank()) ? "ACTIVE" : status;
    }

    private static void validate(int price, int stock) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("재고는 0개 이상이어야 합니다.");
        }
    }


}
