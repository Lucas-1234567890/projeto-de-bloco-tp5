package com.lucasamorim.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasamorim.ecommerceapi.model.AvaliacaoProduto;
import java.util.List;

public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
    List<AvaliacaoProduto> findByProdutoId(Long produtoId);
}
