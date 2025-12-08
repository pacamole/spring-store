package com.muller.spring_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    @Schema(description = "ID do produto a ser comprado", example = "1")
    @NotNull(message = "O ID do produto é obrigatório")
    private Long productId;

    @Schema(description = "Quantidade de itens", example = "2")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantity;
}
