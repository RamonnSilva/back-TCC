package com.itb.inf2am.pizzaria.repository;

import com.itb.inf2am.pizzaria.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Buscar pedidos por email do solicitante
    List<Pedido> findByEmailSolicitante(String emailSolicitante);
}
