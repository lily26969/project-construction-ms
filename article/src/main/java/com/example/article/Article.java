package com.example.article;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String name;
    private String description;
    private float price;
    private int stock;
    private String type;
    private String image;
    private int salesCount;

    public void incrementSales() {
        this.salesCount++;
    }
}
