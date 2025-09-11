package com.itb.inf2am.pizzaria.service;

import com.itb.inf2am.pizzaria.model.Pedido;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PedidoService {

    Pedido criarPedido(Pedido pedido);

    List<Pedido> listarPedidosPorEmail(String emailSolicitante);

    Pedido buscarPedidoPorId(Long id);


    Pedido atualizarStatus(Long id, String novoStatus);

    @Transactional
    Pedido atualizarPedido(Pedido pedido, Long id);

    boolean deletarPedido(Long id);

    Pedido atualizarCodigoCorreios(Long id, String correios);
}
