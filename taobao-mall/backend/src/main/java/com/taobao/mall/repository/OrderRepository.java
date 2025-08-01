package com.taobao.mall.repository;

import com.taobao.mall.entity.Order;
import com.taobao.mall.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findByUser(User user, Pageable pageable);
    Page<Order> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}