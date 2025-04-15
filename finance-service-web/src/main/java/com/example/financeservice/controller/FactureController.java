package com.example.financeservice.controller;


import com.example.financeservice.entity.Facture;
import com.example.financeservice.service.FactureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService service;

    public FactureController(FactureService service) {
        this.service = service;
    }

    @GetMapping
    public List<Facture> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Facture getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Facture create(@RequestBody Facture facture) {
        System.out.println("Facture reçue : " + facture);
        return service.save(facture);
    }

    @PutMapping("/{id}")
    public Facture update(@PathVariable Long id, @RequestBody Facture facture) {
        facture.setId(id);
        return service.save(facture);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @GetMapping("/check-token-status")
    public ResponseEntity<String> checkToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("🚫 Missing or malformed Authorization header");
        }

        System.out.println("✅ Token received: " + authHeader);
        return ResponseEntity.ok("✅ Token received on backend");
    }

    @GetMapping("/check-token-details")
    public ResponseEntity<String> checkTokenDetails(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok("👤 User: " + username + " | 📧 Email: " + email);
    }


    @GetMapping("/user")
    public ResponseEntity<String> getCurrentUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            return ResponseEntity.ok("✅ Authenticated user email: " + email);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ User not authenticated");
    }
    @GetMapping("/test-user-fetch/{username}")
    public ResponseEntity<?> fetchUserFromUserService(@PathVariable String username) {
        try {
            var userDTO = service.fetchUserByUsername(username);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to fetch user: " + e.getMessage());
        }
    }

}
