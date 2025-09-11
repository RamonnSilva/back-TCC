package com.itb.inf2am.pizzaria.controller;

import com.itb.inf2am.pizzaria.exceptions.BadRequest;
import com.itb.inf2am.pizzaria.model.Doacao;
import com.itb.inf2am.pizzaria.model.DoacaoRequest;
import com.itb.inf2am.pizzaria.service.DoacaoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doacao")
public class DoacaoController {

    private final DoacaoService doacaoService;

    public DoacaoController(DoacaoService doacaoService) {
        this.doacaoService = doacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Doacao>> listarTodosDoacao() {
        List<Doacao> doadores = doacaoService.listarTodosDoacao();
        return ResponseEntity.ok().body(doadores);
    }

    @GetMapping("/usuario")
    public List<Doacao> getDoacoesPorEmail(@RequestParam String email) {
        return doacaoService.listarDoacoesPorEmail(email);
    }


    // Endpoint para receber doação com imagem em base64
    @PostMapping("/base64")
    public ResponseEntity<?> doarComImagemBase64(@RequestBody DoacaoRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo email é obrigatório.");
            }

            Doacao doacao = new Doacao();
            doacao.setNome(request.getNome());
            doacao.setTitulo(request.getTitulo());
            doacao.setGenero(request.getGenero());
            doacao.setAutor(request.getAutor());
            doacao.setDescricao(request.getDescricao());
            doacao.setEmail(request.getEmail());  // Setando o email

            if (request.getImagem() != null && request.getImagem().contains(",")) {
                String base64Imagem = request.getImagem().split(",")[1];
                byte[] imagemBytes = Base64.getDecoder().decode(base64Imagem);
                doacao.setImagem(imagemBytes);
            }

            Doacao novaDoacao = doacaoService.salvarDoacao(doacao);
            return ResponseEntity.ok(novaDoacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar doação: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarDoacao(@RequestBody Doacao doacao) {
        if (doacao.getEmail() == null || doacao.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo email é obrigatório.");
        }
        Doacao novoDoacao = doacaoService.salvarDoacao(doacao);
        return ResponseEntity.ok().body(novoDoacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarDoacao(@PathVariable String id) {
        try {
            int doacaoId = Integer.parseInt(id);
            if (doacaoService.deletarDoacao(doacaoId)) {
                return ResponseEntity.ok().body("Doação com o id " + id + " excluída com sucesso");
            } else {
                return ResponseEntity.status(404).body("Doação com o id " + id + " não encontrada");
            }
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDoacao(@RequestBody Doacao doacao, @PathVariable String id) {
        try {
            if (doacao.getEmail() == null || doacao.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo email é obrigatório.");
            }
            int doacaoId = Integer.parseInt(id);
            return ResponseEntity.ok().body(doacaoService.atualizarDoacao(doacao, doacaoId));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido.");
        }
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<Doacao> uploadImagem(
            @PathVariable Integer id,
            @RequestParam("imagem") MultipartFile imagem) throws IOException {
        Optional<Doacao> optionalDoacao = Optional.ofNullable(doacaoService.listarDoacaoPorId(id));
        if (optionalDoacao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Doacao doacao = optionalDoacao.get();
        doacao.setImagem(imagem.getBytes());
        Doacao salvo = doacaoService.salvarDoacao(doacao);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagem(@PathVariable Integer id) {
        Doacao doacao = doacaoService.listarDoacaoPorId(id);
        if (doacao == null || doacao.getImagem() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(doacao.getImagem());
    }
}