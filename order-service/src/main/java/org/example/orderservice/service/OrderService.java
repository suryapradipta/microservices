package org.example.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.OrderLineItemsRequest;
import org.example.orderservice.dto.OrderRequest;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderLineItem;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsRequestList()
                .stream()
                .map(orderLineItemsRequest -> mapToDto(orderLineItemsRequest, order))
                .toList();

        order.setOrderLineItems(orderLineItems);

        orderRepository.save(order);
        return "order placed successfully";
    }

    private OrderLineItem mapToDto(OrderLineItemsRequest orderLineItemsRequest, Order order) {
        OrderLineItem orderLineItem = new OrderLineItem();

        orderLineItem.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        orderLineItem.setPrice(orderLineItemsRequest.getPrice());
        orderLineItem.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItem.setSkuCode(orderLineItemsRequest.getSkuCode());
        orderLineItem.setOrder(order);

        return orderLineItem;
    }
}
