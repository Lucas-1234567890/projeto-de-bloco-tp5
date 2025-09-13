package com.lucasamorim.ecommerceapi.service;

import org.springframework.stereotype.Service;

import com.lucasamorim.ecommerceapi.model.Pagamento;


@Service
public class PagamentoService {

    public void processarPagamento(Pagamento pagamento) {
        // Aqui você pode colocar regra de aprovação automática, por exemplo:
        if ("Boleto".equalsIgnoreCase(pagamento.getMetodo())) {
            pagamento.setStatus("PENDENTE");
        } else {
            pagamento.setStatus("APROVADO");
        }
        pagamento.setDataPagamento(java.time.LocalDateTime.now());
    }
}
