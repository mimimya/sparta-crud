package com.sparta.crud.repository;

import com.sparta.crud.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // @EntityGraph를 사용하면 내부적으로 Fetch Join이 발생하여 N+1을 방지합니다.
    @EntityGraph(attributePaths = {"product"})
    Page<Order> findAll(Pageable pageable);
}
