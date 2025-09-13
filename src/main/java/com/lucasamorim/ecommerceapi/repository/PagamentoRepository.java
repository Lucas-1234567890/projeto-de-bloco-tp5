package com.lucasamorim.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasamorim.ecommerceapi.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    // Todos os métodos já cobertos por JpaRepository
}
