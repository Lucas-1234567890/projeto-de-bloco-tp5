package com.lucasamorim.ecommerceapi.controller;

import org.springframework.web.bind.annotation.*;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.service.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;
    private final ProdutoService produtoService;
    private final NotaFiscalService notaFiscalService;
    private final RastreamentoService rastreamentoService;
    private final CupomService cupomService;

    public PedidoController(PedidoService pedidoService, PagamentoService pagamentoService,
                            ProdutoService produtoService, NotaFiscalService notaFiscalService,
                            RastreamentoService rastreamentoService, CupomService cupomService) {
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
        this.produtoService = produtoService;
        this.notaFiscalService = notaFiscalService;
        this.rastreamentoService = rastreamentoService;
        this.cupomService = cupomService;
    }

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido, @RequestParam(required=false) String codigoCupom) {
        // Aplica cupom se houver
        if(codigoCupom != null) {
            Cupom cupom = cupomService.validarCupom(codigoCupom)
                    .orElseThrow(() -> new RuntimeException("Cupom inválido"));
            pedidoService.aplicarCupom(pedido, cupom);
        }

        // Processa pagamento
        pagamentoService.processarPagamento
        (pedido.getPagamento());

        // Atualiza estoque
        pedido.getProdutos().forEach(produto -> produtoService.atualizarEstoque(produto, 1));

        // Gera nota fiscal se aprovado
        if("APROVADO".equalsIgnoreCase(pedido.getPagamento().getStatus())) {
            notaFiscalService.gerarNota(pedido);
            rastreamentoService.criarEtapa(pedido, "Separação");
        }

        return pedidoService.criarPedido(pedido);
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }
}
