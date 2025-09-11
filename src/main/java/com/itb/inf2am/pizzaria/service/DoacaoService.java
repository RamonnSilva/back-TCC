package com.itb.inf2am.pizzaria.service;

import com.itb.inf2am.pizzaria.model.Doacao;

import java.util.List;

public interface DoacaoService {

    // Salva uma doação
    Doacao salvarDoacao(Doacao doacao);

    // Deleta uma doação pelo ID
    boolean deletarDoacao(Integer id);

    // Lista todas as doações
    List<Doacao> listarTodosDoacao();

    // Lista uma doação por ID
    Doacao listarDoacaoPorId(Integer id);

    // Atualiza uma doação pelo ID
    Doacao atualizarDoacao(Doacao doacao, Integer id);

    List<Doacao> listarDoacoesPorEmail(String email);
}
