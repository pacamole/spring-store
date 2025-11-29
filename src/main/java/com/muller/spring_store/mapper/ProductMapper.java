package com.muller.spring_store.mapper;

import org.springframework.stereotype.Component;

import com.muller.spring_store.dto.ProductRequestDTO;
import com.muller.spring_store.dto.ProductResponseDTO;
import com.muller.spring_store.model.Product;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        return product;
    }

    public ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity());
    }
}
