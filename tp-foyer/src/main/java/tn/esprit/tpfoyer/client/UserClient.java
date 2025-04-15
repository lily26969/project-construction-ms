package tn.esprit.tpfoyer.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.tpfoyer.dto.UserDTO;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/service/user/GetUserDTOByUserName/{username}")
    UserDTO getUserDTOByUsername(@PathVariable("username") String username);
}

