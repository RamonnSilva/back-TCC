package com.itb.inf2am.pizzaria.infra.security;



import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not  found"));
        return new org.springframework.security.core.userdetails.User(cliente.getEmail(), cliente.getSenha(), new ArrayList<>());

    }
}
