package com.lucasamorim.ecommerceapi.service;

import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.repository.CupomRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    public Cupom criarCupom(Cupom cupom) {
        return cupomRepository.save(cupom);
    }

    public Optional<Cupom> validarCupom(String codigo) {
        Optional<Cupom> cupomOpt = cupomRepository.findByCodigo(codigo);
        return cupomOpt.filter(c -> c.getValidade().isAfter(LocalDateTime.now()));
    }

    // <<<< ADICIONADO
    public List<Cupom> listarTodos() {
        return cupomRepository.findAll();
    }

    public Cupom buscarPorId(Long id) {
        return cupomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
    }
}
