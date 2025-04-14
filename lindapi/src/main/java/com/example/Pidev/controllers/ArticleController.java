package com.example.Pidev.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Pidev.models.Article;
import com.example.Pidev.services.ArticleService;
import com.example.Pidev.services.ExcelService;


@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController<Article,Long>{
    private final ArticleService articleService;
    private final ExcelService excelService;
   public ArticleController(ArticleService articleService,ExcelService excelService) {
        super(articleService);
        this.articleService = articleService;
        this.excelService=excelService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long articleId,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "price", required = false) Float price,
                                           @RequestParam(value = "stock", required = false) Integer stock,
                                           @RequestParam(value = "type", required = false) String type,
                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        Optional<Article> existingArticleOpt = articleService.retrieveById(articleId);
        if (!existingArticleOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article avec id " + articleId + " non trouvé");
        }

        Article articleToUpdate = new Article();
        articleToUpdate.setName(name);
        articleToUpdate.setDescription(description);
        articleToUpdate.setPrice(price != null ? price : 0);
        articleToUpdate.setStock(stock != null ? stock : 0);
        articleToUpdate.setType(type);

        try {
            Article updated = articleService.updateArticle(articleId, articleToUpdate, file);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public List<Article> searchArticles(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String type) {
        return articleService.searchArticles(name, minPrice, maxPrice, type);
    }
    @GetMapping("/best-selling")
    public List<Article> getBestSellingArticles(@RequestParam(defaultValue = "3") int limit) {
        return articleService.getBestSellingArticles(limit);
    }
    @GetMapping("/export")
    public ResponseEntity<Resource> exportArticles() {
        ByteArrayInputStream in = excelService.exportArticlesToExcel(articleService.retrieveAll());
        InputStreamResource file = new InputStreamResource(in);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=articles.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/import")
    public ResponseEntity<List<Article>> importArticles(@RequestParam("file") MultipartFile file) {
        List<Article> articles = excelService.importArticlesFromExcel(file);
        articleService.addArticles(articles);
        return ResponseEntity.ok(articles);
    }
    @PreAuthorize("hasAnyAuthority('admin')")

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> createArticleWithImage(@RequestParam("name") String name,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("price") float price,
                                                    @RequestParam("stock") int stock,
                                                    @RequestParam("type") String type,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            if (file.getSize() > (5 * 1024 * 1024)) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "La taille de l'image dépasse la limite autorisée (5MB).")
                );
            }

            Article created = articleService.createArticleWithImage(name, description, price, stock, type, file);
            return new ResponseEntity<>(created, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/generate-description/{id}")
    public ResponseEntity<String> generateArticleDescription(@PathVariable Long id) {
       /* Optional<Article> optionalArticle =articleRepository.findById(id);*/
        Optional<Article> optionalArticle = articleService.retrieveById(id);

        if (optionalArticle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article introuvable");
        }

        try {
            String description = articleService.generateDescription(optionalArticle.get());
            return ResponseEntity.ok(description);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la génération: " + e.getMessage());
        }
    }
    @PostMapping("/chat")
    public ResponseEntity<String> chatWithBot(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le message ne peut pas être vide.");
        }

        try {
            String response = articleService.chatWithBot(userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la réponse du chatbot: " + e.getMessage());
        }
    }


}
