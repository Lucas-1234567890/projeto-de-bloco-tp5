package com.lucasamorim.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private Cliente cliente;

    @ManyToMany
    private List<Produto> itens;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<Produto> getItens() { return itens; }
    public void setItens(List<Produto> itens) { this.itens = itens; }

    // Evita loop infinito no console
    @Override
    public String toString() {
        return "Carrinho{id=" + id + ", itens=" + (itens != null ? itens.size() : 0) + '}';
    }
}
