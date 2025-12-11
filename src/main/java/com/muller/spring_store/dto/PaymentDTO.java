package com.muller.spring_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @NotBlank(message = "Número do cartão é obrigatório")
    @Size(min = 16, max = 16, message = "Cartão deve ter 16 dígitos")
    @Schema(example = "1234567812345678")
    private String cardNumber;

    @NotBlank(message = "CVV é obrigatório")
    @Size(min = 3, max = 3, message = "CVV deve ter 3 dígitos")
    @Schema(example = "123")
    private String cvv;

    @NotBlank(message = "Validade é obrigatória")
    @Schema(example = "12/30")
    private String expirationDate;
}
