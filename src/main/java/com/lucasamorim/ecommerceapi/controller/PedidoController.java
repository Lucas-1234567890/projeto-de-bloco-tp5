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
        try {
            // Aplica cupom se houver
            if(codigoCupom != null && !codigoCupom.trim().isEmpty()) {
                Cupom cupom = cupomService.validarCupom(codigoCupom)
                        .orElseThrow(() -> new RuntimeException("Cupom inválido ou expirado"));
                pedidoService.aplicarCupom(pedido, cupom);
            }

            // Processa pagamento
            if (pedido.getPagamento() != null) {
                pagamentoService.processarPagamento(pedido.getPagamento());
            }

            // Atualiza estoque
            if (pedido.getProdutos() != null) {
                pedido.getProdutos().forEach(produto -> {
                    try {
                        produtoService.atualizarEstoque(produto, 1);
                    } catch (Exception e) {
                        System.err.println("Erro ao atualizar estoque do produto " + produto.getId());
                    }
                });
            }

            // Salva o pedido primeiro
            Pedido pedidoSalvo = pedidoService.criarPedido(pedido);

            // Gera nota fiscal se aprovado
            if(pedido.getPagamento() != null && "APROVADO".equalsIgnoreCase(pedido.getPagamento().getStatus())) {
                try {
                    notaFiscalService.gerarNota(pedidoSalvo);
                    rastreamentoService.criarEtapa(pedidoSalvo, "Separação");
                } catch (Exception e) {
                    System.err.println("Erro ao gerar nota fiscal ou rastreamento: " + e.getMessage());
                }
            }

            return pedidoSalvo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar pedido: " + e.getMessage());
        }
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