package com.example.financeservice.service;


import com.example.financeservice.client.UserClient;
import com.example.financeservice.dto.UserDTO;
import com.example.financeservice.entity.Facture;
import com.example.financeservice.repository.FactureRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService {

    private final FactureRepository repository;
    private final UserClient userClient;

    public FactureService(FactureRepository repository, UserClient userClient) {
        this.repository = repository;
        this.userClient = userClient;
    }

    public List<Facture> getAll() {
        return repository.findAll();
    }

    public Facture getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Facture save(Facture facture) {
        return repository.save(facture);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public UserDTO fetchUserByUsername(String username) {
        return userClient.getUserDTOByUsername(username);
    }

    private String extractCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("preferred_username");
        }
        return null;
    }
}

