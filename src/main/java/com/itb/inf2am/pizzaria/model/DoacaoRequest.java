package com.itb.inf2am.pizzaria.model;

public class DoacaoRequest {

    private String nome;
    private String titulo;
    private String genero;
    private String autor;
    private String descricao;
    private String imagem; // base64
    private String email;  // campo email adicionado
    private Integer doadorid;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdDoador() { return doadorid; }
    public void setIdDoador(Integer idDoador) { this.doadorid = doadorid; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
