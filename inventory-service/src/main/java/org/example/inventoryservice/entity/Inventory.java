package org.example.inventoryservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "inventory")
public class Inventory {
    @Id
    private Long id;

    private String skuCode;

    private Integer quantity;
}
