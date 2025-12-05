package com.muller.spring_store.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muller.spring_store.dto.OrderRequestDTO;
import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.mapper.OrderMapper;
import com.muller.spring_store.model.Order;
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

    private void calculateOrderTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (var item : order.getItems()) {
            Long productId = item.getProduct().getId();

            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException(
                    "Produto selecionado para compra não existe: " + productId));

            item.setProduct(product);
            item.setUnitPrice(product.getPrice());

            BigDecimal itemTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }

        // função lambda dentro do forEach proibe a alteração de variáveis fora do
        // escopo, bloqueando a atualização do "total"
        // order.getItems().forEach(item -> {... total = total.add(itemTotal)}
        
        order.setTotal(total);
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

        this.calculateOrderTotal(order);

        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return mapper.toDTO(orderRepository.save(order));
    }

    public OrderResponseDTO update(Long orderId, OrderRequestDTO orderDTO) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Produto não encontrado: " + orderId);
        }

        var orderUpdated = mapper.toEntity(orderDTO);
        orderUpdated.setId(orderId);

        validateOrderFields(orderUpdated);

        return mapper.toDTO(orderRepository.save(orderUpdated));
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
