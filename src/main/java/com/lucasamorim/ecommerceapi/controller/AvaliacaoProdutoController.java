package com.lucasamorim.ecommerceapi.controller;

import org.springframework.web.bind.annotation.*;
import com.lucasamorim.ecommerceapi.model.AvaliacaoProduto;
import com.lucasamorim.ecommerceapi.service.AvaliacaoProdutoService;
import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoProdutoController {
    private final AvaliacaoProdutoService service;

    public AvaliacaoProdutoController(AvaliacaoProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public AvaliacaoProduto avaliar(@RequestBody AvaliacaoProduto avaliacao) {
        return service.avaliar(avaliacao);
    }

    @GetMapping("/produto/{produtoId}")
    public List<AvaliacaoProduto> listarPorProduto(@PathVariable Long produtoId) {
        return service.listarPorProduto(produtoId);
    }
}
