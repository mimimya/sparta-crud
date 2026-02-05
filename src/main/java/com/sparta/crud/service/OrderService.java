package com.sparta.crud.service;

import com.sparta.crud.dto.order.OrderRequestDto;
import com.sparta.crud.dto.order.OrderResponseDto;
import com.sparta.crud.exception.order.OrderNotFoundException;
import com.sparta.crud.exception.product.ProductNotFoundException;
import com.sparta.crud.model.Order;
import com.sparta.crud.model.Product;
import com.sparta.crud.repository.OrderRepository;
import com.sparta.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new ProductNotFoundException());

        Order order = Order.create(product, requestDto.getQuantity());
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    // 주문 조회
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        return new OrderResponseDto(order);
    }

    // TODO: 주문 목록 조회 (페이지네이션)
    // TODO: 주문 취소
}
