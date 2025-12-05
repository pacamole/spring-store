package com.muller.spring_store.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.muller.spring_store.dto.OrderRequestDTO;
import com.muller.spring_store.dto.OrderResponseDTO;
import com.muller.spring_store.model.Order;
import com.muller.spring_store.model.OrderItem;
import com.muller.spring_store.model.Product;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();

        // transforma itens se items no dto não for nulo
        if (dto.getItems() != null) {
            List<OrderItem> items = dto.getItems().stream().map(itemDTO -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDTO.getQuantity());

                Product product = new Product();
                product.setId(itemDTO.getProductId());
                orderItem.setProduct(product);

                orderItem.setOrder(order);

                return orderItem;
            }).collect(Collectors.toList());
            // retorna um arrayList mutável. Essa característica é benéfica pro
            // service, que muda os objetos.

            order.setItems(items);
        }
        return order;
    }

    public OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getId());
        }
        dto.setStatus(order.getStatus());
        dto.setTotal(order.getTotal());
        dto.setItems(order.getItems().stream().map(this::toItemDTO).toList());
        return dto;
    }

    public OrderResponseDTO.OrderItemResponse toItemDTO(OrderItem item) {
        BigDecimal subTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));

        return new OrderResponseDTO.OrderItemResponse(
                item.getProduct().getName(),
                item.getUnitPrice(),
                item.getQuantity(),
                subTotal);
    }
}
