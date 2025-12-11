package com.muller.spring_store.service;

import org.springframework.stereotype.Service;

import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.dto.PaymentDTO;
import com.muller.spring_store.mapper.OrderMapper;
import com.muller.spring_store.model.Order;
import com.muller.spring_store.model.OrderStatus;
import com.muller.spring_store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    public OrderResponseDTO pay(Long orderId, Long userId, PaymentDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (order.getUser().getId() != userId) {
            throw new IllegalArgumentException("Você não tem permissão para pagar este pedido");
        }

        if (order.getStatus() == OrderStatus.PAID) {
            throw new IllegalArgumentException("Este pedido já foi pago");
        }

        order.setStatus(OrderStatus.PAID);

        // Aqui chamaria uma API externa. Mas vamos simular uma validação simples.
        this.simulateProcessing(dto);

        return mapper.toDTO(orderRepository.save(order));
    }

    public void simulateProcessing(PaymentDTO data) {
        if (data.getCardNumber().endsWith("0000")) {
            throw new IllegalArgumentException("Pagamento recusado pela operadora");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }
}
