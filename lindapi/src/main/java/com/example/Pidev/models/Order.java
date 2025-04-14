package com.example.Pidev.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String number;
    private float totalPrice;
    private String status;

    @OneToOne
    @JoinColumn(name = "basket_id")
    @JsonManagedReference
    private Basket basket;
}