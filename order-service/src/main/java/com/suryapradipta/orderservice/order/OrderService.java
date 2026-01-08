package com.suryapradipta.orderservice.order;

import com.suryapradipta.orderservice.client.InventoryClient;
import com.suryapradipta.orderservice.exception.ProductOutOfStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest dto) {

        boolean isProductInStock = inventoryClient.isInStock(dto.skuCode(), dto.quantity());
        if (!isProductInStock) {
            throw new ProductOutOfStockException("Product with code " + dto.skuCode() + " is not in stock");
        }


        Order order = orderMapper.toEntity(dto);
        orderRepository.save(order);
    }
}
