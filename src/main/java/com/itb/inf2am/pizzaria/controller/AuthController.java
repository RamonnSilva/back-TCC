package com.itb.inf2am.pizzaria.controller;

import com.itb.inf2am.pizzaria.dto.LoginRequestDTO;
import com.itb.inf2am.pizzaria.dto.RegisterRequestDTO;
import com.itb.inf2am.pizzaria.dto.ResponseDTO;
import com.itb.inf2am.pizzaria.infra.security.TokenService;
import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        System.out.println("== LOGIN RECEBIDO ==");
        System.out.println("Email: " + body.email());
        System.out.println("Senha: " + body.senha());

        Optional<Cliente> clienteOpt = repository.findByEmail(body.email());

        if (clienteOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email ou senha incorretos");
            return ResponseEntity.status(401).body(error);
        }

        Cliente cliente = clienteOpt.get();
        System.out.println("Senha armazenada no banco (hash): " + cliente.getSenha());

        if (!passwordEncoder.matches(body.senha(), cliente.getSenha())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email ou senha incorretos");
            return ResponseEntity.status(401).body(error);
        }

        String token = tokenService.generateToken(cliente);
        return ResponseEntity.ok(new ResponseDTO(cliente.getId(), cliente.getNome(), token, cliente.getFuncao()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<Cliente> existingCliente = repository.findByEmail(body.email());

        if (existingCliente.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email já cadastrado");
            return ResponseEntity.badRequest().body(error);
        }

        Cliente newCliente = new Cliente();
        newCliente.setSenha(passwordEncoder.encode(body.senha()));
        newCliente.setEmail(body.email());
        newCliente.setNome(body.nome());
        newCliente.setCep(body.cep());
        newCliente.setTelefone(body.telefone());
        newCliente.setEstado(body.estado());
        newCliente.setCpf(body.cpf());
        newCliente.setCidade(body.cidade());
        newCliente.setEndereco(body.endereco());
        newCliente.setLogradouro(body.logradouro());
        newCliente.setFuncao(body.funcao());





        repository.save(newCliente);

        String token = tokenService.generateToken(newCliente);
        return ResponseEntity.ok(new ResponseDTO(newCliente.getId(), newCliente.getNome(), token, newCliente.getFuncao()));
    }
}