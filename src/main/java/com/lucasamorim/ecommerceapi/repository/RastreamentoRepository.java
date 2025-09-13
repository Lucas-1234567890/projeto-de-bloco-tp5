package com.lucasamorim.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasamorim.ecommerceapi.model.Rastreamento;
import com.lucasamorim.ecommerceapi.model.Pedido;
import java.util.List;

public interface RastreamentoRepository extends JpaRepository<Rastreamento, Long> {
    List<Rastreamento> findByPedido(Pedido pedido);
}
