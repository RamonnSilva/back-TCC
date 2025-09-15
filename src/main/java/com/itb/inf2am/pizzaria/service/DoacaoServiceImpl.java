package com.itb.inf2am.pizzaria.service;

import com.itb.inf2am.pizzaria.exceptions.BadRequest;
import com.itb.inf2am.pizzaria.exceptions.NotFound;
import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.model.Doacao;
import com.itb.inf2am.pizzaria.repository.ClienteRepository;
import com.itb.inf2am.pizzaria.repository.DoacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoServiceImpl implements DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final ClienteRepository clienteRepository;

    public DoacaoServiceImpl(DoacaoRepository doacaoRepository, ClienteRepository clienteRepository) {
        this.doacaoRepository = doacaoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Doacao salvarDoacao(Doacao doacao) {
        // Validações básicas
        if (doacao.getNome() == null || doacao.getNome().trim().isEmpty()) {
            throw new BadRequest("Nome é obrigatório");
        }
        if (doacao.getTitulo() == null || doacao.getTitulo().trim().isEmpty()) {
            throw new BadRequest("Título é obrigatório");
        }
        if (doacao.getEmail() == null || doacao.getEmail().trim().isEmpty()) {
            throw new BadRequest("Email é obrigatório");
        }
        
        return doacaoRepository.save(doacao);
    }

    @Override
    @Transactional
    public boolean deletarDoacao(Integer id) {
        if (doacaoRepository.existsById(id)) {
            doacaoRepository.deleteById(id);
            return true;
        } else {
            throw new NotFound("Cliente não encontrado com o id " + id);
        }
    }

    @Override
    public List<Doacao> listarTodosDoacao() {
        return doacaoRepository.findAll();
    }

    @Override
    public Doacao listarDoacaoPorId(Integer id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new NotFound("Cliente não encontrado com o id " + id));
    }

    @Override
    @Transactional
    public Doacao atualizarDoacao(Doacao doacao, Integer id) {
        try {
            if (!doacao.validarDoacao()) {
                throw new BadRequest(doacao.getMessage());
            }
            Doacao doacaoBd = doacaoRepository.findById(id).get();
            doacaoBd.setNome(doacao.getNome());
            doacaoBd.setTitulo(doacao.getTitulo());
            doacaoBd.setAutor(doacao.getAutor());
            doacaoBd.setDescricao(doacao.getDescricao());
            return doacaoRepository.save(doacaoBd);
        } catch (Exception e) {
            throw new NotFound("Doação não encontrada com o id " + id);
        }
    }


    @Override
    public List<Doacao> listarDoacoesPorEmail(String email) {
        return doacaoRepository.findByEmail(email);
    }

    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFound("Cliente não encontrado com o id " + id));
    }
}
