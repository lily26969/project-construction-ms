package com.example.Pidev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.example.Pidev.models.Order;
import com.example.Pidev.services.OrderService;
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController<Order,Long>{

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        super(orderService);
        this.orderService = orderService;

    }

    @PostMapping("/submit")
    public ResponseEntity<Order> submitOrder(@RequestBody Order order, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        System.out.println("📦 Order submitted by user: " + username);

        Order savedOrder = orderService.submitOrder(order.getBasket().getBasketId());
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
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
            var userDTO = orderService.fetchUserByUsername(username);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to fetch user: " + e.getMessage());
        }
    }

}

