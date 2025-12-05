package com.muller.spring_store.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid // Valida cada item da lista
    private List<OrderItemDTO> items;
}
