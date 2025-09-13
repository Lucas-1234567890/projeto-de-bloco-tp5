package com.lucasamorim.ecommerceapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pagamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metodo; // Cartão, PIX, Boleto
    private String status; // APROVADO, RECUSADO
    private LocalDateTime dataPagamento;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
	
}
