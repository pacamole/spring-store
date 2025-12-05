package com.muller.spring_store.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.spring_store.dto.OrderRequestDTO;
import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.model.User;
import com.muller.spring_store.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    @Operation(summary = "Meus Pedidos", description = "Lista os pedidos do usuário logado")
    public Page<OrderResponseDTO> findAllByUser(
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "createdAt") Pageable pageable,
            @AuthenticationPrincipal User user) {

        return service.findAllByUser(pageable, user.getId());
    }

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um pedido do usuário logado")
    public OrderResponseDTO create(@RequestBody @Valid OrderRequestDTO orderDTO, @AuthenticationPrincipal User user) {

        return service.create(orderDTO, user.getId());
    }

}
