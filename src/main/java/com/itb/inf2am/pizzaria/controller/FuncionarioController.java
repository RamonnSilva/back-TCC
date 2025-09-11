package com.itb.inf2am.pizzaria.controller;

import com.itb.inf2am.pizzaria.exceptions.BadRequest;
import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.model.RedefinirSenhaRequest;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import com.itb.inf2am.pizzaria.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class FuncionarioController {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public FuncionarioController(ClienteService clienteService,
                                 ClienteRepository clienteRepository,
                                 BCryptPasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        return ResponseEntity.ok().body(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvarCliente(cliente);
        return ResponseEntity.ok().body(novoCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable(value = "id") String id) {
        try {
            if (clienteService.deletarCliente(Integer.parseInt(id))) {
                return ResponseEntity.ok().body("Cliente com o id " + id + " excluído com sucesso");
            }
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 15.");
        }
        return ResponseEntity.ok().body("Não foi possível a exclusão do cliente com o id " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente,
                                                    @PathVariable(value = "id") String id) {
        try {
            return ResponseEntity.ok().body(clienteService.atualizarCliente(cliente, Integer.parseInt(id)));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 15.");
        }
    }

    @GetMapping("/total")
    public long totalClientes() {
        return clienteRepository.count();
    }

    @PutMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RedefinirSenhaRequest request) {
        Optional<Cliente> optionalCliente = clienteRepository.findByEmail(request.getEmail());

        if (optionalCliente.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente com esse e-mail não encontrado.");
        }

        Cliente cliente = optionalCliente.get();

        // Criptografar a senha com o encoder injetado
        String senhaCriptografada = passwordEncoder.encode(request.getNovaSenha());
        cliente.setSenha(senhaCriptografada);
        clienteRepository.save(cliente);

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}