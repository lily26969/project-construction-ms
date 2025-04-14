package com.example.Pidev.services;

import com.example.Pidev.models.TypeAchat;
import com.example.Pidev.models.Article;
import com.example.Pidev.models.Basket;
import com.example.Pidev.models.BasketArticle;
import com.example.Pidev.repositories.ArticleRepository;
import com.example.Pidev.repositories.BasketArticleRepository;
import com.example.Pidev.repositories.BasketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService extends BaseService<Basket, Long> {

    private final BasketRepository basketRepository;
    private final ArticleRepository articleRepository;
    private final BasketArticleRepository basketArticleRepository;

    public Basket assignArticleToBasket(Long idArticle, Long idBasket, int quantity, TypeAchat type) {
        Article article = articleRepository.findById(idArticle)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));

        Basket basket = (idBasket == null)
                ? basketRepository.save(new Basket())
                : basketRepository.findById(idBasket)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found"));

        Optional<BasketArticle> existing = basket.getBasketArticles().stream()
                .filter(ba -> ba.getArticle().getArticleId().equals(idArticle) && ba.getType() == type)
                .findFirst();

        if (existing.isPresent()) {
            BasketArticle ba = existing.get();
            ba.setQuantity(ba.getQuantity() + quantity);
        } else {
            BasketArticle ba = new BasketArticle();
            ba.setBasket(basket);
            ba.setArticle(article);
            ba.setQuantity(quantity);
            ba.setType(type);
            basket.getBasketArticles().add(ba);
        }

        recalculateTotal(basket);
        return basketRepository.save(basket);
    }

    public void removeArticleFromBasket(Long idBasket, Long idArticle, TypeAchat type) {
        Basket basket = basketRepository.findById(idBasket)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found"));

        basket.getBasketArticles().removeIf(
                ba -> ba.getArticle().getArticleId().equals(idArticle) && ba.getType() == type
        );

        recalculateTotal(basket);
        basketRepository.save(basket);
    }

    private void recalculateTotal(Basket basket) {
        double total = basket.getBasketArticles().stream()
                .mapToDouble(ba -> ba.getArticle().getPrice() * ba.getQuantity())
                .sum();
        basket.setPrice((float) total);
    }

    public Basket updateBasket(Long id, Basket updatedBasket) {
        return basketRepository.findById(id)
                .map(existingBasket -> {
                    existingBasket.setPrice(updatedBasket.getPrice());
                    return basketRepository.save(existingBasket);
                })
                .orElseThrow(() -> new EntityNotFoundException("Basket with ID " + id + " not found."));
    }
}
