package com.example.Pidev.client;

import com.example.Pidev.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/service/user/GetUserDTOByUserName/{username}")
    UserDTO getUserDTOByUsername(@PathVariable("username") String username);
}

