package com.suryapradipta.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "inventory-service", url = "${inventory.url}")
public interface InventoryClient {

    @GetMapping("/api/inventories/check")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
