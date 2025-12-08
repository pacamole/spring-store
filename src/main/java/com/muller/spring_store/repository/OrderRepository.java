package com.muller.spring_store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.spring_store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Page<Order> findAllByUserId(Pageable pageable, Long userId);

}
