package com.lucasamorim.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasamorim.ecommerceapi.model.Pedido;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByEmail(String email);
}
