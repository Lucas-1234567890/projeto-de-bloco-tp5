package com.lucasamorim.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasamorim.ecommerceapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Não precisa de métodos extras por enquanto
}
