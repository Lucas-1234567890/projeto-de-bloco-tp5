package com.lucasamorim.ecommerceapi.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;

    @ManyToOne
    private Cliente cliente;

    @ManyToMany
    private List<Produto> produtos;

    private Double valorTotal;

    @OneToOne(cascade = CascadeType.ALL)
    private Pagamento pagamento;

    @OneToOne(cascade = CascadeType.ALL)
    private NotaFiscal notaFiscal;

    private String status; // "EM_PROCESSAMENTO", "APROVADO", etc.

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
    public NotaFiscal getNotaFiscal() { return notaFiscal; }
    public void setNotaFiscal(NotaFiscal notaFiscal) { this.notaFiscal = notaFiscal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEmail() {
        return email;
    }

    // Setter para email
    public void setEmail(String email) {
        this.email = email;
    }
	
}
