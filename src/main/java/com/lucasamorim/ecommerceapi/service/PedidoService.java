package com.lucasamorim.ecommerceapi.service;

import org.springframework.stereotype.Service;

import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.repository.PedidoRepository;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Aplica cupom
    public void aplicarCupom(Pedido pedido, Cupom cupom) {
        double desconto = cupom.getDesconto(); // supondo percentual
        double total = pedido.getProdutos().stream()
                        .mapToDouble(p -> p.getPreco())
                        .sum();
        total = total - (total * desconto / 100);
        pedido.setValorTotal(total);
    }
}

