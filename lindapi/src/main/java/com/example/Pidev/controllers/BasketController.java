package com.example.Pidev.controllers;

import com.example.Pidev.models.TypeAchat;
import com.example.Pidev.models.Basket;
import com.example.Pidev.services.BasketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController extends BaseController<Basket, Long> {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        super(basketService);
        this.basketService = basketService;
    }

    @PutMapping("/{id}")
    public Basket update(@PathVariable Long id, @RequestBody Basket basket) {
        return basketService.updateBasket(id, basket);
    }


    @PostMapping("/assign")
    public Basket assignArticleToBasket(
            @RequestParam Long idArticle,
            @RequestParam(required = false) Long idBasket,
            @RequestParam int quantity,
            @RequestParam TypeAchat type
    ) {
        return basketService.assignArticleToBasket(idArticle, idBasket, quantity, type);
    }

    @DeleteMapping("/unassign")
    public void unassignArticleFromBasket(
            @RequestParam Long idBasket,
            @RequestParam Long idArticle,
            @RequestParam TypeAchat type
    ) {
        basketService.removeArticleFromBasket(idBasket, idArticle, type);
    }
}
