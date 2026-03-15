package com.sparta.crud.controller;

import com.sparta.crud.dto.order.OrderRequestDto;
import com.sparta.crud.dto.order.OrderResponseDto;
import com.sparta.crud.facade.RedissonLockOrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    // private final OrderService orderService; // 기존 코드 주석 처리
    private final RedissonLockOrderFacade orderFacade; // Facade 주입

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto dto = orderFacade.createOrder(requestDto);
        return ResponseEntity
                .created(URI.create("/orders/" + dto.getOrderId()))
                .body(dto);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        OrderResponseDto dto = orderFacade.getOrder(orderId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<OrderResponseDto> dtos = orderFacade.getOrders(page, size);
        return ResponseEntity.ok(dtos);
    }

}
