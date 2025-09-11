package com.itb.inf2am.pizzaria.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // Autenticação pública
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // Cliente (usuários)
                        .requestMatchers(HttpMethod.GET, "/cliente/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cliente/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/cliente/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/cliente/**").permitAll()

                        // Doações
                        .requestMatchers(HttpMethod.GET, "/doacao/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/doacao/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/doacao/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/doacao/**").permitAll()

                        // Pedidos (liberando POST e GET explicitamente)
                        .requestMatchers(HttpMethod.POST, "/pedido").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pedido/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "pedido/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"pedido/**").permitAll()

                        // Outros
                        .requestMatchers(HttpMethod.GET, "/total").permitAll()

                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
