package com.muller.spring_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muller.spring_store.dto.SalesReportDTO;
import com.muller.spring_store.model.Product;

// Ao estender JpaRepository, herdamos de graça: save(), findAll(), findById(), delete()...
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT 
                p.name AS productName,
                SUM(oi.quantity) AS quantity,
                SUM(oi.unit_price * oi.quantity) AS totalRevenue
            FROM order_items oi
            JOIN products p 
            ON p.id = oi.product_id
            GROUP BY p.name
            ORDER BY totalRevenue DESC
            """, nativeQuery = true)
    List<SalesReportDTO> getSalesReport();
}
