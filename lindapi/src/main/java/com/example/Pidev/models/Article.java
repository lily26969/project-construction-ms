package com.example.Pidev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String image;

    private String type;
    private int salesCount;


    public void incrementSales() {
        this.salesCount++;
    }
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BasketArticle> basketArticles;

}
