package com.itb.inf2am.pizzaria.controller;

import com.itb.inf2am.pizzaria.exceptions.NotFound;
import com.itb.inf2am.pizzaria.model.Doacao;
import com.itb.inf2am.pizzaria.model.Pedido;
import com.itb.inf2am.pizzaria.model.PedidoRequest;
import com.itb.inf2am.pizzaria.repository.DoacaoRepository;
import com.itb.inf2am.pizzaria.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;


//feito pelo grande master of programmation Felipe, por que o bixa do ramon so sabe fazer gambiarra e não colocou o endpoint de email
//além disso, fez gambiarra no react para colocar o get do email solicitante

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	private final PedidoService pedidoService;
	private final DoacaoRepository doacaoRepository;

	public PedidoController(PedidoService pedidoService, DoacaoRepository doacaoRepository) {
		this.pedidoService = pedidoService;
		this.doacaoRepository = doacaoRepository;
	}

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequest pedidoRequest) {
		Pedido pedido = new Pedido();
		pedido.setTituloLivro(pedidoRequest.getTituloLivro());
		pedido.setAutorLivro(pedidoRequest.getAutorLivro());
		pedido.setGeneroLivro(pedidoRequest.getGeneroLivro());
		pedido.setDescricaoLivro(pedidoRequest.getDescricaoLivro());
		pedido.setEmailSolicitante(pedidoRequest.getEmailSolicitante());
		pedido.setStatusPedido(pedidoRequest.getStatusPedido());
		pedido.setCorreios(pedidoRequest.getCorreios());

		if (pedidoRequest.getImagemLivroBase64() != null && !pedidoRequest.getImagemLivroBase64().isEmpty()) {
			byte[] imagem = Base64.getDecoder().decode(pedidoRequest.getImagemLivroBase64());
			pedido.setImagemLivro(imagem);
		}

		if (pedidoRequest.getIdDoacao() == null) {
			throw new IllegalArgumentException("idDoacao é obrigatório");
		}
		Doacao doacao = doacaoRepository.findById(pedidoRequest.getIdDoacao().intValue())
				.orElseThrow(() -> new NotFound("Doação não encontrada com id " + pedidoRequest.getIdDoacao()));
		pedido.setIddoacao(doacao);

		Pedido criado = pedidoService.criarPedido(pedido);
		return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosPedido() {
        List<Pedido> pedidos = pedidoService.listarTodosPedido();
        return ResponseEntity.ok().body(pedidos);
    }

    // Listar pedidos pelo email do solicitante
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Pedido>> listarPedidosPorEmail(@PathVariable String email) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorEmail(email);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    // Atualizar apenas o status do pedido
    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedidoComStatus) {

        Pedido atualizado = pedidoService.atualizarStatus(id, pedidoComStatus.getStatusPedido());
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    // Deletar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}/correios")
    public ResponseEntity<Pedido> atualizarCodigoCorreios(
            @PathVariable Long id,
            @RequestBody Pedido pedidoComCodigo) {

        Pedido atualizado = pedidoService.atualizarCodigoCorreios(id, pedidoComCodigo.getCorreios());
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

}
