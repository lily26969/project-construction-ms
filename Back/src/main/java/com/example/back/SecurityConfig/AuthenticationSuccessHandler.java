package com.example.back.SecurityConfig;

import com.example.back.ServiceImp.UserLoginTrackingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserLoginTrackingService userLoginTrackingService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                           Authentication authentication) throws ServletException, IOException {
        String username = authentication.getName();
        userLoginTrackingService.trackUserLogin(username, request); // ✅ Track login details
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
