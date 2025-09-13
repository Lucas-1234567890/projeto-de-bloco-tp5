package com.lucasamorim.ecommerceapi.service;

import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.repository.CupomRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    // Cria um novo cupom
    public Cupom criarCupom(Cupom cupom) {
        return cupomRepository.save(cupom);
    }

    // Valida cupom por código
    public Optional<Cupom> validarCupom(String codigo) {
        Optional<Cupom> cupomOpt = cupomRepository.findByCodigo(codigo);
        // Pode adicionar lógica de validade do cupom aqui
        return cupomOpt.filter(c -> c.getValidade().isAfter(java.time.LocalDateTime.now()));
    }
}
