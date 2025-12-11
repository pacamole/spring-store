package com.muller.spring_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.dto.PaymentDTO;
import com.muller.spring_store.model.User;
import com.muller.spring_store.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private final PaymentService service;

    @PostMapping("/{orderId}")
    @Operation(summary = "Realizar pagamento", description = "Simula o pagamento de um pedido pendente")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponseDTO pay(@PathVariable Long orderId, @RequestBody @Valid PaymentDTO paymentData,
            @AuthenticationPrincipal User user) {
        return service.pay(orderId, user.getId(), paymentData);
    }
}
