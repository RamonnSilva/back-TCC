package com.itb.inf2am.pizzaria.controller;

import com.itb.inf2am.pizzaria.exceptions.BadRequest;
import com.itb.inf2am.pizzaria.model.Cliente;
import com.itb.inf2am.pizzaria.model.Doacao;
import com.itb.inf2am.pizzaria.model.DoacaoRequest;
import com.itb.inf2am.pizzaria.service.DoacaoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Listar todas as doações
    @GetMapping
    public ResponseEntity<List<Doacao>> listarTodosDoacao() {
        List<Doacao> doacoes = doacaoService.listarTodosDoacao();
        return ResponseEntity.ok(doacoes);
    }

    // Listar doações de um usuário específico pelo email
    @GetMapping("/usuario")
    public List<Doacao> getDoacoesPorEmail(@RequestParam String email) {
        return doacaoService.listarDoacoesPorEmail(email);
    }

    // Criar doação recebendo imagem em Base64
    @PostMapping
    public ResponseEntity<?> criarDoacao(@RequestBody DoacaoRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo email é obrigatório.");
            }

            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo nome é obrigatório.");
            }

            if (request.getDoadorid() == null) {
                return ResponseEntity.badRequest().body("O campo doadorid é obrigatório.");
            }

            // Buscar o doador pelo ID
            Cliente doador = doacaoService.buscarClientePorId(request.getDoadorid());

            // Criar doação
            Doacao doacao = new Doacao();
            doacao.setNome(request.getNome());
            doacao.setTitulo(request.getTitulo());
            doacao.setGenero(request.getGenero());
            doacao.setAutor(request.getAutor());
            doacao.setDescricao(request.getDescricao());
            doacao.setEmail(request.getEmail());
            doacao.setDoador(doador);

            // Processar imagem em Base64 se houver
            if (request.getImagem() != null && !request.getImagem().isEmpty()) {
                String base64Data = request.getImagem();
                // Se tiver prefixo tipo "data:image/png;base64,", remove
                if (base64Data.contains(",")) {
                    base64Data = base64Data.split(",")[1];
                }
                byte[] imagemBytes = Base64.getDecoder().decode(base64Data);
                doacao.setImagem(imagemBytes);
            }

            Doacao novaDoacao = doacaoService.salvarDoacao(doacao);

            return ResponseEntity.ok(novaDoacao);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar doação: " + e.getMessage());
        }
    }

    // Buscar doação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Doacao> listarDoacaoPorId(@PathVariable Integer id) {
        Doacao doacao = doacaoService.listarDoacaoPorId(id);
        if (doacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doacao);
    }

    // Deletar doação
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDoacao(@PathVariable Integer id) {
        boolean deletado = doacaoService.deletarDoacao(id);
        if (deletado) {
            return ResponseEntity.ok("Doação com o id " + id + " excluída com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Doação com o id " + id + " não encontrada.");
        }
    }

    // Atualizar doação
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDoacao(@RequestBody Doacao doacao, @PathVariable Integer id) {
        try {
            if (doacao.getEmail() == null || doacao.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo email é obrigatório.");
            }
            Doacao atualizada = doacaoService.atualizarDoacao(doacao, id);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar doação: " + e.getMessage());
        }
    }

    // Endpoint para buscar imagem de doação
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
