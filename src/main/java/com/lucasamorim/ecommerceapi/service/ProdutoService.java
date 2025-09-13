package com.lucasamorim.ecommerceapi.service;

import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // <<<< MELHORADO: Verifica estoque antes de atualizar
    public Produto atualizarEstoque(Produto produto, int quantidade) {
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }
        
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        return produtoRepository.save(produto);
    }

    // <<<< ADICIONADO: Métodos úteis
    public Produto atualizarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public boolean temEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarPorId(produtoId);
        return produto.getQuantidadeEstoque() >= quantidade;
    }
}