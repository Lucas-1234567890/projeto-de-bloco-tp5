package com.lucasamorim.ecommerceapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Cliente;
import com.lucasamorim.ecommerceapi.model.Endereco;
import com.lucasamorim.ecommerceapi.model.Carrinho;
import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.ClienteRepository;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;

@Component
public class CarrinhoConsole {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID do cliente: ");
        Long clienteId = sc.nextLong();
        sc.nextLine();

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        if (cliente.getCarrinho() == null) {
            cliente.setCarrinho(new Carrinho());
            cliente.getCarrinho().setCliente(cliente);
        }

        int opcao;
        do {
            System.out.println("\n=== GERENCIAR CLIENTE: " + cliente.getNome() + " ===");
            System.out.println("1 - Gerenciar Endereços");
            System.out.println("2 - Gerenciar Carrinho");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> gerenciarEnderecos(cliente, sc);
                case 2 -> gerenciarCarrinho(cliente, sc);
            }
        } while (opcao != 0);

        clienteRepository.save(cliente);
    }

    // ------------------ ENDEREÇOS ------------------
    private void gerenciarEnderecos(Cliente c, Scanner sc) {
        int opcao;
        do {
            System.out.println("\n=== ENDEREÇOS ===");
            System.out.println("1 - Adicionar Endereço");
            System.out.println("2 - Listar Endereços");
            System.out.println("3 - Remover Endereço");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    Endereco e = new Endereco();
                    System.out.print("Rua: "); e.setRua(sc.nextLine());
                    System.out.print("Número: "); e.setNumero(sc.nextLine());
                    System.out.print("Cidade: "); e.setCidade(sc.nextLine());
                    System.out.print("Estado: "); e.setEstado(sc.nextLine());

                    if (c.getEnderecos() == null) c.setEnderecos(new ArrayList<>());
                    c.getEnderecos().add(e);

                    System.out.println("Endereço adicionado!");
                }
                case 2 -> {
                    if (c.getEnderecos() == null || c.getEnderecos().isEmpty()) {
                        System.out.println("Nenhum endereço cadastrado.");
                    } else {
                        c.getEnderecos().forEach(e -> System.out.println(
                            e.getRua() + ", " + e.getNumero() + " - " + e.getCidade() + "/" + e.getEstado()
                        ));
                    }
                }
                case 3 -> {
                    if (c.getEnderecos() == null || c.getEnderecos().isEmpty()) {
                        System.out.println("Nenhum endereço para remover.");
                    } else {
                        for (int i = 0; i < c.getEnderecos().size(); i++) {
                            System.out.println(i + " - " + c.getEnderecos().get(i).getRua());
                        }
                        System.out.print("Escolha índice para remover: ");
                        int idx = sc.nextInt();
                        sc.nextLine();
                        c.getEnderecos().remove(idx);
                        System.out.println("Endereço removido!");
                    }
                }
            }
        } while (opcao != 0);
    }

    // ------------------ CARRINHO ------------------
    private void gerenciarCarrinho(Cliente c, Scanner sc) {
        String resp;
        do {
            List<Produto> produtos = produtoRepository.findAll();
            System.out.println("\n=== PRODUTOS DISPONÍVEIS ===");
            produtos.forEach(p -> System.out.println(
                p.getId() + " - " + p.getNome() + " | R$ " + p.getPreco()
            ));

            System.out.print("Digite o ID do produto para adicionar ao carrinho: ");
            Long produtoId = sc.nextLong();
            sc.nextLine();

            Produto p = produtoRepository.findById(produtoId).orElse(null);
            if (p != null) {
                if (c.getCarrinho().getItens() == null) {
                    c.getCarrinho().setItens(new ArrayList<>());
                }
                c.getCarrinho().getItens().add(p);
                System.out.println("Produto adicionado ao carrinho!");
            } else {
                System.out.println("Produto não encontrado!");
            }

            System.out.print("Deseja adicionar outro produto? (s/n): ");
            resp = sc.nextLine();
        } while (resp.equalsIgnoreCase("s"));

        double total = c.getCarrinho().getItens().stream()
                        .mapToDouble(Produto::getPreco)
                        .sum();

        System.out.println("Total do carrinho: R$ " + total);
        System.out.println("Carrinho atualizado!");
    }
}
