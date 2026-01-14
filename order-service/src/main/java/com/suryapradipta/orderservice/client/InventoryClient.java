package com.suryapradipta.orderservice.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    @GetExchange("/api/inventories/check")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
