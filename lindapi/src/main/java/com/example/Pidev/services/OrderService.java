package com.example.Pidev.services;

import com.example.Pidev.client.UserClient;
import com.example.Pidev.dto.UserDTO;
import com.example.Pidev.models.Basket;
import com.example.Pidev.models.Order;
import com.example.Pidev.repositories.BasketRepository;
import com.example.Pidev.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService<Order, Long> {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final EmailService emailService;
    private final UserClient userClient;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        BasketRepository basketRepository,
                        EmailService emailService,
                        UserClient userClient) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.emailService = emailService;
        this.userClient = userClient;

        // If your BaseService has a 'repository' field, set it like this:
    }

    public Order submitOrder(Long basketId) {
        String username = extractCurrentUsername();
        if (username == null) {
            throw new IllegalStateException("Authenticated user's username not found.");
        }

        UserDTO user = fetchUserByUsername(username);
        if (user == null) {
            throw new IllegalStateException("User not found in user-service");
        }

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new IllegalArgumentException("Basket not found"));

        float totalPrice = (float) basket.getBasketArticles().stream()
                .mapToDouble(item -> item.getArticle().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setBasket(basket);
        order.setTotalPrice(totalPrice);
        order.setStatus("Pending");

        Order savedOrder = orderRepository.save(order);
        savedOrder.setNumber("REF#" + savedOrder.getOrderId());

        Order finalOrder = orderRepository.save(savedOrder);

        emailService.sendOrderConfirmation(user.getEmail(), finalOrder);

        return finalOrder;
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
