package com.muller.spring_store.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDTO {
    @Schema(description = "Identificador único (Gerado automaticamente)", example = "1")
    private Long id;
    @Schema(description = "Nome do produto", example = "Notebook Gamer Alienware")
    private String name;
    @Schema(description = "Descrição técnica detalhada", example = "Intel i9, 32GB RAM, RTX 4080")
    private String description;
    @Schema(description = "Preço de venda (deve ser maior que zero)", example = "15999.90")
    private BigDecimal price;
    @Schema(description = "Quantidade em estoque", example = "5")
    private Integer stockQuantity;
}
