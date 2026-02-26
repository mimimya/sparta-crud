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
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    public Product(String name, int price, int stock, ProductStatus status) {
        validate(price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public static Product create(String name, int price, int stock, ProductStatus status) {
        Product createdPproduct = new Product(name, price, stock, resolveStatus(status));
        createdPproduct.createdAt = LocalDateTime.now();
        return createdPproduct;
    }

    public void update(String name, int price, int stock, ProductStatus status) {
        validate(price, stock);

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = resolveStatus(status);
        this.updatedAt = LocalDateTime.now();

        checkStock();
    }

    public void delete() {
        this.status = this.status.delete();
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

    /**
     * DTO에서 null 값으로 들어온 상태를 처리
     */
    private static ProductStatus resolveStatus(ProductStatus status) {
        return status == null ? ProductStatus.ON_SALE : status;
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
            this.status = this.status.soldOut();
        } else {
            this.status = this.status.restore();
        }
    }

}
