package com.itb.inf2am.pizzaria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Doacao")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100, unique = false)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(nullable = false, length = 666)
    private String autor;

    @Column(nullable = false, length = 350)
    private String descricao;

    @Lob
    @Column(name = "imagem", columnDefinition = "VARBINARY(MAX)")
    private byte[] imagem;

    @Column(nullable = false, length = 255)
    private String email;

    @JsonIgnoreProperties("doacoes")
    @ManyToOne
    @JoinColumn(name = "doadorid", nullable = false)
    private Cliente doador;

    @OneToMany(mappedBy = "iddoacao")

    private List<Pedido> pedidos;

    @Transient
    private String message = "";

    @Transient
    private boolean isValid = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Cliente getDoador() {
        return doador;
    }

    public void setDoador(Cliente doador) {
        this.doador = doador;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
    public boolean validarDoacao() {
        // Implementar validação real aqui
        return isValid;
    }
}



// Construtor padrão
