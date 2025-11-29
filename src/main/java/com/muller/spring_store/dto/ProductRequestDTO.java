package com.muller.spring_store.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @Schema(description = "Nome do produto", example = "Notebook Gamer Alienware")
    private String name;
    @Schema(description = "Descrição técnica detalhada", example = "Intel i9, 32GB RAM, RTX 4000")
    private String description;
    @Schema(description = "Preço de venda (deve ser maior que zero)", example = "15999.00")
    private BigDecimal price;
    @Schema(description = "Quantidade em estoque", example = "5")
    private Integer stockQuantity;

    public ProductRequestDTO(
            String name,
            String description,
            BigDecimal price,
            Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
