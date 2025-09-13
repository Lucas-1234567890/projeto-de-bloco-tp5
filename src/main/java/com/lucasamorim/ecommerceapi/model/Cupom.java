package com.lucasamorim.ecommerceapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cupom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private Double desconto;
    private LocalDateTime validade;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public LocalDateTime getValidade() {
		return validade;
	}
	public void setValidade(LocalDateTime validade) {
		this.validade = validade;
	}

    // getters e setters
}
