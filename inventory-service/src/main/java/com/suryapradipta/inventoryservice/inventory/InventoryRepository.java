package com.suryapradipta.inventoryservice.inventory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(
            """
                SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END
                FROM Inventory i
                WHERE i.skuCode = :skuCode
                AND i.quantity >= :quantity
            """
    )
    boolean existsBySkuCodeAndQuantity(String skuCode, Integer quantity);
}
