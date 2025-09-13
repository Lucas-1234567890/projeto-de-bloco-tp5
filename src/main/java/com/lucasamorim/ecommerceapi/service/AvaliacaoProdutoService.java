package com.lucasamorim.ecommerceapi.service;

import org.springframework.stereotype.Service;
import com.lucasamorim.ecommerceapi.model.AvaliacaoProduto;
import com.lucasamorim.ecommerceapi.repository.AvaliacaoProdutoRepository;
import java.util.List;

@Service
public class AvaliacaoProdutoService {
    private final AvaliacaoProdutoRepository repo;

    public AvaliacaoProdutoService(AvaliacaoProdutoRepository repo) {
        this.repo = repo;
    }

    public AvaliacaoProduto avaliar(AvaliacaoProduto avaliacao) {
        return repo.save(avaliacao);
    }

    public List<AvaliacaoProduto> listarPorProduto(Long produtoId) {
        return repo.findByProdutoId(produtoId);
    }

    // <<<< ADICIONADO
    public List<AvaliacaoProduto> listarTodas() {
        return repo.findAll();
    }

    public AvaliacaoProduto buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }
}