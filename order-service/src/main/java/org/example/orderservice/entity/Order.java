package org.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order {
    @Id
    Long id;

    String orderNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderLineItem> orderLineItems;
}
