package com.itb.inf2am.pizzaria.infra.security;

import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Ignora autenticação para rotas públicas
        if (
                (path.equals("/cliente") && method.equals("POST")) ||
                        (path.startsWith("/auth")) ||
                        (path.startsWith("/doacao")) ||
                        (path.equals("/total"))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // Processa token normalmente
        String token = recoverToken(request);
        String login = tokenService.validateToken(token);

        if (login != null) {
            Cliente cliente = clienteRepository.findByEmail(login)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(cliente, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}
