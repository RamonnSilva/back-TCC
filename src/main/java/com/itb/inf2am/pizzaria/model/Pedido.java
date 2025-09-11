package com.itb.inf2am.pizzaria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulolivro")
    private String tituloLivro;

    @Column(name = "autorlivro")
    private String autorLivro;

    @Column(name = "generolivro")
    private String generoLivro;

    @Column(name = "descricaolivro")
    private String descricaoLivro;

    @Lob
    @Column(name = "imagemlivro")
    private byte[] imagemLivro;


    @Column(name = "correios")
    private String correios;

    @Column(name = "emailsolicitante")
    private String emailSolicitante;

    @Column(name = "statuspedido")
    private String statusPedido;

    @Column(name = "datapedido")
    private LocalDateTime dataPedido;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public String getGeneroLivro() {
        return generoLivro;
    }

    public void setGeneroLivro(String generoLivro) {
        this.generoLivro = generoLivro;
    }

    public String getDescricaoLivro() {
        return descricaoLivro;
    }

    public void setDescricaoLivro(String descricaoLivro) {
        this.descricaoLivro = descricaoLivro;
    }

    public byte[] getImagemLivro() {
        return imagemLivro;
    }

    public void setImagemLivro(byte[] imagemLivro) {
        this.imagemLivro = imagemLivro;
    }

    public String getEmailSolicitante() {
        return emailSolicitante;
    }

    public void setEmailSolicitante(String emailSolicitante) {
        this.emailSolicitante = emailSolicitante;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getCorreios() {
        return correios;
    }

    public void setCorreios(String correios) {
        this.correios = correios;
    }
}
