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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(ProductNotFoundException::new);

        Order order = Order.create(product, requestDto.getQuantity());
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    // 주문 조회
    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return new OrderResponseDto(order);
    }

    // 주문 목록 조회 (페이지네이션)
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 (성능 최적화)
    public Page<OrderResponseDto> getOrders(int page, int size) {
        // page는 0부터 시작하므로 그대로 사용하거나, 클라이언트가 1부터 보낸다면 -1 처리 필요
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Order> orderPage = orderRepository.findAll(pageable);

        // Page 내부의 Order 엔티티들을 DTO로 변환
        return orderPage.map(OrderResponseDto::new);
    }
    // TODO: 주문 취소
}
