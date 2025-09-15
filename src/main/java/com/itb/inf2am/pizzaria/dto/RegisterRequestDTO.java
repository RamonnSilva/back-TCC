package com.itb.inf2am.pizzaria.dto;


public record RegisterRequestDTO (String nome, String email, String senha, String cep, String telefone, String endereco, String estado, Integer logradouro, String cidade, String funcao) {

}
