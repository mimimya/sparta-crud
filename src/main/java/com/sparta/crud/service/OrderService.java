package com.sparta.crud.service;

import com.sparta.crud.dto.order.OrderRequestDto;
import com.sparta.crud.dto.order.OrderResponseDto;
import com.sparta.crud.model.Order;
import com.sparta.crud.model.Product;
import com.sparta.crud.repository.OrderRepository;
import com.sparta.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        // TODO:
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (product.getStock() < requestDto.getQuantity()) {
            throw new IllegalArgumentException("요청한 수량이 재고를 초과했습니다.");
        }

        // 상품 재고 차감
        product.setStock(product.getStock() - requestDto.getQuantity());

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(requestDto.getQuantity());
        order.setPrice(product.getPrice() * requestDto.getQuantity());
        order.setStatus("ORDERED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    // 주문 조회
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        return new OrderResponseDto(order);
    }

    // TODO: 주문 목록 조회 (페이지네이션)
    // TODO: 주문 취소
}
