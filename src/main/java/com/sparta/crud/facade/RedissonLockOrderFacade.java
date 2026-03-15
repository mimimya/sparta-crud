package com.sparta.crud.facade;

import com.sparta.crud.dto.order.OrderRequestDto;
import com.sparta.crud.dto.order.OrderResponseDto;
import com.sparta.crud.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockOrderFacade {

    private final RedissonClient redissonClient;
    private final OrderService orderService;

    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        // 1. 락 이름 설정 (상품 ID를 기준으로 락을 잡음)
        RLock lock = redissonClient.getLock("product_lock:" + requestDto.getProductId());

        try {
            // 2. 락 획득 시도 (최대 10초 대기, 1초 동안 락 점유 - 데드락 방지 ; 예상되는 로직 수행 시간보다 충분히 길게 잡거나, Redisson의 Watchdog(락 연장 기능) 기능을 활용)
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                // 락 획득 실패 시 예외 처리 (재시도 로직을 넣기도 함)
                throw new RuntimeException("락 획득 실패: 현재 주문량이 많습니다.");
            }

            // 3. 실제 주문 로직 호출 (여기서 @Transactional이 시작되고 끝남)
            return orderService.createOrder(requestDto);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 4. 트랜잭션 커밋 완료 후 락 해제
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public OrderResponseDto getOrder(Long orderId) {
        return orderService.getOrder(orderId); // 그냥 전달만 함
    }

    public Page<OrderResponseDto> getOrders(int page, int size) {
        return orderService.getOrders(page, size); // 그냥 전달만 함
    }
}