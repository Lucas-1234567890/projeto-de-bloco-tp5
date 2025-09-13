package com.lucasamorim.ecommerceapi.service;

import org.springframework.stereotype.Service;
import com.lucasamorim.ecommerceapi.model.Rastreamento;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.repository.RastreamentoRepository;
import java.util.List;

@Service
public class RastreamentoService {

    private final RastreamentoRepository rastreamentoRepository;

    public RastreamentoService(RastreamentoRepository rastreamentoRepository) {
        this.rastreamentoRepository = rastreamentoRepository;
    }

    public List<Rastreamento> listarPorPedido(Pedido pedido) {
        return rastreamentoRepository.findByPedido(pedido);
    }

    public Rastreamento criarEtapa(Pedido pedido, String etapa) {
        Rastreamento rast = new Rastreamento();
        rast.setPedido(pedido);
        rast.setEtapa(etapa);
        rast.setStatus("PENDENTE");
        rast.setDataAtualizacao(java.time.LocalDateTime.now());
        return rastreamentoRepository.save(rast);
    }
}
