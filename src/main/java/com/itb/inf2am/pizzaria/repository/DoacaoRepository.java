package com.itb.inf2am.pizzaria.repository;


import com.itb.inf2am.pizzaria.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Integer> {
    List<Doacao> findByEmail(String email); //  filtra por email
}
