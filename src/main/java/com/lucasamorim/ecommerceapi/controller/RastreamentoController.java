package com.lucasamorim.ecommerceapi.controller;

import org.springframework.web.bind.annotation.*;
import com.lucasamorim.ecommerceapi.model.Rastreamento;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.service.RastreamentoService;
import com.lucasamorim.ecommerceapi.service.PedidoService;
import java.util.List;

@RestController
@RequestMapping("/api/rastreamentos")
public class RastreamentoController {
    private final RastreamentoService rastreamentoService;
    private final PedidoService pedidoService;

    public RastreamentoController(RastreamentoService rastreamentoService, PedidoService pedidoService) {
        this.rastreamentoService = rastreamentoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Rastreamento> listarPorPedido(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        return rastreamentoService.listarPorPedido(pedido);
    }
}
