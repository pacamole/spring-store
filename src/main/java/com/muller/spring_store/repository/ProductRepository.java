package com.muller.spring_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.spring_store.model.Product;

// Ao estender JpaRepository, herdamos de graça: save(), findAll(), findById(), delete()...
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
    
}
