package com.lucasamorim.ecommerceapi.service;

import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Cria um novo produto
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Busca um produto por ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    // Lista todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Atualiza estoque
    public Produto atualizarEstoque(Produto produto, int quantidade) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        return produtoRepository.save(produto);
    }
}
