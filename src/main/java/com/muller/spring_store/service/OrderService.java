package com.muller.spring_store.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muller.spring_store.dto.OrderRequestDTO;
import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.mapper.OrderMapper;
import com.muller.spring_store.model.Order;
import com.muller.spring_store.model.OrderItem;
import com.muller.spring_store.model.OrderStatus;
import com.muller.spring_store.model.Product;
import com.muller.spring_store.model.User;
import com.muller.spring_store.repository.OrderRepository;
import com.muller.spring_store.repository.ProductRepository;
import com.muller.spring_store.repository.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper mapper;

    private void processItemsAndCalculateTotal(Order order) {
        if (order.getItems() == null) {
            return;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            Product product = this.validateAndUpdateStock(item.getProduct().getId(), item.getQuantity());

            item.setProduct(product);
            item.setUnitPrice(product.getPrice());

            total = total.add(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        // função lambda dentro do forEach proibe a alteração de variáveis fora do
        // escopo, bloqueando a atualização do "total"
        // order.getItems().forEach(item -> {... total = total.add(itemTotal)}

        order.setTotal(total);
    }

    private Product validateAndUpdateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + productId));

        var availableStock = product.getStockQuantity();

        if (quantity > product.getStockQuantity()) {
            throw new IllegalArgumentException(
                    String.format("Estoque insuficiente para o produto %s. Disponível: %d, Solicitado: %d", productId,
                            availableStock, quantity));
        }

        product.setStockQuantity(availableStock - quantity);

        return productRepository.save(product);
    }

    private void returnItemsToStock(List<OrderItem> items) {
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            // CORREÇÃO: Soma ao estoque existente, não substitui!
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
    }

    private void validateOrderFields(Order order) {
        if (order.getItems().size() <= 0) {
            throw new IllegalArgumentException("O pedido deve ter no mínimo 1 item de compra");
        }
        var userId = order.getUser().getId();
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("O usuário informado não existe: " + userId);
        }
    }

    public Page<OrderResponseDTO> findAllByUser(@NonNull Pageable pageable, Long userId) {
        var orders = orderRepository.findAllByUserId(pageable, userId).map(mapper::toDTO);

        return orders;
    }

    public OrderResponseDTO findById(@NonNull Long orderId, @NonNull Long currentUserId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(
                "O id do pedido não existe"));

        if (order.getUser().getId() != currentUserId) {
            throw new IllegalArgumentException("O usuário não pode ver pedidos de outros usuários");
        }

        var userExists = userRepository.existsById(currentUserId);
        if (!userExists) {
            throw new IllegalArgumentException("O usuário atual não existe: " + currentUserId);
        }

        return mapper.toDTO(order);
    }

    public OrderResponseDTO create(OrderRequestDTO orderDTO, Long userId) {
        Order order = mapper.toEntity(orderDTO);

        User user = new User();
        user.setId(userId);
        order.setUser(user);

        this.validateOrderFields(order);

        this.processItemsAndCalculateTotal(order);

        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return mapper.toDTO(orderRepository.save(order));
    }

    public OrderResponseDTO update(Long orderId, OrderRequestDTO orderDTO) {
        // 1. Busca o pedido ANTIGO real do banco (com os itens velhos)
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + orderId));

        this.returnItemsToStock(existingOrder.getItems());

        existingOrder.getItems().clear();

        Order newOrderData = mapper.toEntity(orderDTO);

        for (OrderItem newItem : newOrderData.getItems()) {
            newItem.setOrder(existingOrder);
            existingOrder.getItems().add(newItem);
        }

        this.processItemsAndCalculateTotal(existingOrder);

        return mapper.toDTO(orderRepository.save(existingOrder));
    }

    public void delete(Long orderId, Long currentUserId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("O id do pedido não existe"));

        if (order.getUser().getId() != currentUserId) {
            throw new IllegalArgumentException("O usuário não pode ver pedidos de outros usuários");
        }

        orderRepository.deleteById(orderId);
    }

}
