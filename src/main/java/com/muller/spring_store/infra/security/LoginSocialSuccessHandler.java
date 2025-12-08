package com.muller.spring_store.infra.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.muller.spring_store.model.User;
import com.muller.spring_store.model.UserRole;
import com.muller.spring_store.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("SOCIAL_LOGIN"); // Senha dummy (não será usada)
            user.setRole(UserRole.USER);
            user = userRepository.save(user);
        }

        String token = tokenService.generateToken(user);
        String formatedToken = "{\"token\": \"" + token + "\"}";

        // 4. Devolve o token na resposta (simulando um JSON)
        // Em produção, redirecionaríamos para o Front-end:
        // response.sendRedirect("http://meusite.com?token=" + token);
        response.setContentType("application/json");
        response.getWriter().write(formatedToken);
    }

}
