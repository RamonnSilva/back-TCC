package com.itb.inf2am.pizzaria.model;


//outra coisa, alem disso, ramon não fez o DTO para o Pedido, me agradeça se tiver vendo isso do master of programmation f4
 public class PedidoRequest {
    private String tituloLivro;
    private String autorLivro;
    private String generoLivro;
    private String descricaoLivro;
    private String imagemLivro; // Imagem em base64
    private String emailSolicitante;
    private String statusPedido;
    private Long idDoacao;
    private String correios;

    // Getters e setters
    public String getTituloLivro() { return tituloLivro; }
    public void setTituloLivro(String tituloLivro) { this.tituloLivro = tituloLivro; }

    public String getAutorLivro() { return autorLivro; }
    public void setAutorLivro(String autorLivro) { this.autorLivro = autorLivro; }

    public String getGeneroLivro() { return generoLivro; }
    public void setGeneroLivro(String generoLivro) { this.generoLivro = generoLivro; }

    public String getDescricaoLivro() { return descricaoLivro; }
    public void setDescricaoLivro(String descricaoLivro) { this.descricaoLivro = descricaoLivro; }

    public String getImagemLivro() { return imagemLivro; }
    public void setImagemLivroBase64(String imagemLivro) { this.imagemLivro = imagemLivro; }

    public String getEmailSolicitante() { return emailSolicitante; }
    public void setEmailSolicitante(String emailSolicitante) { this.emailSolicitante = emailSolicitante; }

    public String getStatusPedido() { return statusPedido; }
    public void setStatusPedido(String statusPedido) { this.statusPedido = statusPedido; }

    public Long getIdDoacao() { return idDoacao; }
    public void setIdDoacao(Long idDoacao) { this.idDoacao = idDoacao; }

    public String getCorreios() { return correios; }
    public void setCorreios(String correios) { this.correios = correios; }
}