package org.example.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.config.WebClientConfig;
import org.example.orderservice.dto.InventoryResponse;
import org.example.orderservice.dto.OrderLineItemsRequest;
import org.example.orderservice.dto.OrderRequest;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderLineItem;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final WebClient webClient;

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

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        // Call inventory service and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get().uri("http://localhost:8083/api/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);


        if (allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is out of stock");
        }

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
