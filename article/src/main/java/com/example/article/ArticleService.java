package com.example.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Add a new article
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    // Get all articles
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // Update an article by its ID
    public Article updateArticle(Long id, Article newArticle) {
        Optional<Article> existingArticleOpt = articleRepository.findById(id);

        if (existingArticleOpt.isPresent()) {
            Article existingArticle = existingArticleOpt.get();
            existingArticle.setName(newArticle.getName());
            existingArticle.setDescription(newArticle.getDescription());
            existingArticle.setPrice(newArticle.getPrice());
            existingArticle.setStock(newArticle.getStock());
            existingArticle.setType(newArticle.getType());
            existingArticle.setImage(newArticle.getImage());
            existingArticle.setSalesCount(newArticle.getSalesCount());
            return articleRepository.save(existingArticle);
        } else {
            return null; // Or you can throw a custom exception
        }
    }

    // Delete an article by its ID
    public String deleteArticle(Long id) {
        if (articleRepository.findById(id).isPresent()) {
            articleRepository.deleteById(id);
            return "Article deleted successfully";
        } else {
            return "Article not found, deletion failed";
        }
    }

    // You can add more service methods as needed, such as for searching articles by name.
}
