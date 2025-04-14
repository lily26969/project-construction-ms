package com.example.Pidev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Basket basket;

    @ManyToOne
    private Article article;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private TypeAchat type;
}
