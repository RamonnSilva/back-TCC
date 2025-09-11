package com.itb.inf2am.pizzaria.service;

import com.itb.inf2am.pizzaria.exceptions.BadRequest;
import com.itb.inf2am.pizzaria.exceptions.NotFound;
import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        if (!cliente.validarCliente()) {
            throw new BadRequest(cliente.getMessage());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public boolean deletarCliente(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            throw new NotFound("Cliente não encontrado com o id " + id);
        }
    }

    @Override
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente listarClientePorId(Long id) {
        return clienteRepository.findById(id.intValue())
                .orElseThrow(() -> new NotFound("Cliente não encontrado com o id " + id));
    }

    @Override
    @Transactional
    public Cliente atualizarCliente(Cliente cliente, Integer id) {
        try {
            if (!cliente.validarCliente()) {
                throw new BadRequest(cliente.getMessage());
            }
            Cliente clienteBd = clienteRepository.findById(id)
                    .orElseThrow(() -> new NotFound("Cliente não encontrado com o id " + id));

            clienteBd.setNome(cliente.getNome());
            clienteBd.setEmail(cliente.getEmail());
            clienteBd.setSenha(cliente.getSenha());
            clienteBd.setCep(cliente.getCep());
            clienteBd.setTelefone(cliente.getTelefone());
            clienteBd.setEndereco(cliente.getEndereco());
            clienteBd.setEstado(cliente.getEstado());
            clienteBd.setLogradouro(cliente.getLogradouro());
            clienteBd.setCidade(cliente.getCidade());
            clienteBd.setFuncao(cliente.getFuncao());

            return clienteRepository.save(clienteBd);
        } catch (Exception e) {
            throw new NotFound("Erro ao atualizar cliente com o id " + id);
        }
    }

    @Override
    @Transactional
    public void redefinirSenha(String email, String novaSenha) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound("Cliente com email '" + email + "' não encontrado"));

        if (novaSenha == null || novaSenha.length() < 6) {
            throw new BadRequest("A nova senha deve ter pelo menos 6 caracteres.");
        }

        cliente.setSenha(novaSenha);
        clienteRepository.save(cliente);
    }
}
