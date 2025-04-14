package com.example.Pidev.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Pidev.models.Article;
import com.example.Pidev.repositories.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ArticleService extends BaseService<Article,Long>{
  /*  private final ArticleRepository articleRepository;*/

    @Autowired
    private ArticleRepository articleRepository;
    @Value("${openrouter.api.key}")
    private String OPENROUTER_API_KEY;
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    public List<Article> searchArticles(String name, Float minPrice, Float maxPrice, String type) {
        return articleRepository.searchArticles(name, minPrice, maxPrice, type);
    }

    @Autowired
    private FileStorageService fileStorageService;


    @Value("${upload.path}")
    private String upload;

    public Article createArticleWithImage(String name, String description, float price, int stock, String type, MultipartFile file) throws IOException {
        Article article = new Article();
        article.setName(name);
        article.setDescription(description);
        article.setPrice(price);
        article.setStock(stock);
        article.setType(type);
        article.setSalesCount(0);

        // Save basic article info first (without image)
        Article savedArticle = articleRepository.save(article);

        // Prepare path and filename
        String uploadPath = upload + "/articles";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String newFileName = savedArticle.getArticleId() + "_" + originalFileName;

        // Save file to disk
        fileStorageService.saveFile(file, newFileName, uploadPath);

        // Update article with image filename
        savedArticle.setImage(newFileName);
        return articleRepository.save(savedArticle);
    }


    public List<Article> getBestSellingArticles(int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return articleRepository.findBestSellingArticles(pageable);
}
public void addArticles (List<Article> articles){
    articleRepository.saveAll(articles);
}

    public Article updateArticle(Long id, Article updatedArticle, MultipartFile file) throws IOException {
        Optional<Article> existingArticleOptional = articleRepository.findById(id);
        if (existingArticleOptional.isPresent()) {
            Article existingArticle = existingArticleOptional.get();

            String uploadPath = upload + "/articles";

            // Update fields if provided
            existingArticle.setName(updatedArticle.getName() != null ? updatedArticle.getName() : existingArticle.getName());
            existingArticle.setDescription(updatedArticle.getDescription() != null ? updatedArticle.getDescription() : existingArticle.getDescription());
            existingArticle.setPrice(updatedArticle.getPrice());
            existingArticle.setStock(updatedArticle.getStock());
            existingArticle.setType(updatedArticle.getType());

            // Handle image replacement if new file is sent
            if (file != null && !file.isEmpty()) {
                // Delete old image if it exists
                if (existingArticle.getImage() != null) {
                    File oldFile = new File(uploadPath, existingArticle.getImage());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                String originalFileName = file.getOriginalFilename();
                String newFileName = existingArticle.getArticleId() + "_" + originalFileName;

                fileStorageService.saveFile(file, newFileName, uploadPath);
                existingArticle.setImage(newFileName);
            }

            return articleRepository.save(existingArticle);
        } else {
            throw new EntityNotFoundException("Article with ID " + id + " not found.");
        }
    }
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();


    public String generateDescription(Article article) {
        String prompt = buildPrompt(article);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENROUTER_API_KEY);
        headers.set("User-Agent", "MrammaAI (lindamlika865@gmail.com)");

        // OpenRouter uses OpenAI-style chat structure
        String requestBody = """
    {
      "model": "mistralai/mistral-7b-instruct",
      "messages": [
        {"role": "system", "content": "You are a helpful assistant that writes realistic descriptions for online shop products."},
        {"role": "user", "content": "%s"}
      ]
    }
    """.formatted(prompt);

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_API_URL, httpEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode json = mapper.readTree(response.getBody());
                String content = json.at("/choices/0/message/content").asText();
                return String.join(" ", content.split("\\n"));
            } else {
                throw new RuntimeException("Erreur API: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la description: " + e.getMessage());
        }
    }

    /*  private String buildPrompt(Article article) {
           String name = article.getName() != null ? article.getName() : "Article sans nom";
           return "Suggérer une description pour cet article que je veux vendre basé sur des produits similaires que vous connaissez déjà : " + name;
       }*/
    private String buildPrompt(Article article) {
        String name = article.getName() != null ? article.getName() : "Article sans nom";
        return """
        Suggérer une description structurée pour un produit à vendre. Divisez la description en sections comme suit:
        1. Introduction : Une brève introduction sur le produit.
        2. Caractéristiques principales : Liste des principales caractéristiques du produit.
        3. Applications : Les usages possibles du produit.
        4. Garantie de qualité : Données sur la qualité du produit.
        
        Nom du produit : %s
    """.formatted(name);
    }
    public String chatWithBot(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENROUTER_API_KEY);
        headers.set("User-Agent", "MrammaAI (lindamlika865@gmail.com)");

        String requestBody = """
    {
      "model": "mistralai/mistral-7b-instruct",
      "messages": [
        {"role": "system", "content": "Tu es un assistant virtuel pour un magasin de matériaux de construction. Réponds de façon utile et amicale."},
        {"role": "user", "content": "%s"}
      ]
    }
    """.formatted(userMessage);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_API_URL, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode json = mapper.readTree(response.getBody());
                return json.at("/choices/0/message/content").asText();
            } else {
                throw new RuntimeException("Erreur API: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur dans le chatbot: " + e.getMessage());
        }
    }



}
