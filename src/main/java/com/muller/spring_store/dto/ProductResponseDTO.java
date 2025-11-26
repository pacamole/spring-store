package com.muller.spring_store.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductResponseDTO(
                @Schema(description = "Identificador único (Gerado automaticamente)", example = "1") Long id,
                @Schema(description = "Nome do produto", example = "Notebook Gamer Alienware") String name,
                @Schema(description = "Descrição técnica detalhada", example = "Intel i9, 32GB RAM, RTX 4080") String description,
                @Schema(description = "Preço de venda (deve ser maior que zero)", example = "15999.90") BigDecimal price,
                @Schema(description = "Quantidade em estoque", example = "5") Integer stockQuantity) {

}
