package com.lucasamorim.ecommerceapi.controller;

import org.springframework.web.bind.annotation.*;
import com.lucasamorim.ecommerceapi.model.Cupom;
import com.lucasamorim.ecommerceapi.service.CupomService;
import java.util.List;

@RestController
@RequestMapping("/api/cupons")
@CrossOrigin(origins = "http://localhost:5500") // <<<< ADICIONADO
public class CupomController {
    private final CupomService service;

    public CupomController(CupomService service) {
        this.service = service;
    }

    @PostMapping
    public Cupom criarCupom(@RequestBody Cupom cupom) {
        return service.criarCupom(cupom);
    }

    @GetMapping("/validar/{codigo}")
    public Cupom validarCupom(@PathVariable String codigo) {
        return service.validarCupom(codigo)
                .orElseThrow(() -> new RuntimeException("Cupom inválido ou expirado"));
    }

    // <<<< ADICIONADO: Listar todos os cupons
    @GetMapping
    public List<Cupom> listarTodos() {
        return service.listarTodos();
    }
}