package com.muller.spring_store.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.muller.spring_store.model.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    @Schema(description = "Id do pedido de compra", example = "1")
    private Long id;

    @Schema(description = "Usuário que realizou o pedido")
    private Long userId;

    @Schema(description = "Status atual do pedido", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Valor total do pedido", example = "14999.00")
    private BigDecimal total;

    @Schema(description = "Itens incluídos no pedido")
    private List<OrderItemResponse> items = new ArrayList<>();

    @Schema(description = "Data e hora da criação do pedido", example = "2024-06-15T14:30:00")
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private String productName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subTotal;
    }
}
