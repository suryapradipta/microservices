package com.suryapradipta.orderservice.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public void placeOrder(OrderRequest dto) {
        Order order = orderMapper.toEntity(dto);
        orderRepository.save(order);
    }
}
