package com.lucasamorim.ecommerceapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Cliente;
import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.ClienteRepository;
import com.lucasamorim.ecommerceapi.repository.PedidoRepository;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;

@Component
public class PedidoConsole {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD PEDIDOS ===");
            System.out.println("1 - Cadastrar Pedido");
            System.out.println("2 - Listar Pedidos");
            System.out.println("3 - Atualizar Status");
            System.out.println("4 - Deletar Pedido");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrar(sc);
                case 2 -> listar();
                case 3 -> atualizarStatus(sc);
                case 4 -> deletar(sc);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar(Scanner sc) {
        Pedido pedido = new Pedido();

        // Escolher cliente
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
            return;
        }
        System.out.println("Clientes disponíveis:");
        clientes.forEach(c -> System.out.println(c.getId() + " - " + c.getNome() + " (" + c.getEmail() + ")"));
        System.out.print("ID do cliente: ");
        Long clienteId = sc.nextLong();
        sc.nextLine();
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            System.out.println("Cliente inválido!");
            return;
        }
        pedido.setCliente(cliente);
        pedido.setEmail(cliente.getEmail());

        // Escolher produtos
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto disponível!");
            return;
        }
        List<Produto> produtosSelecionados = new ArrayList<>();
        double valorTotal = 0;
        String continuar;
        do {
            System.out.println("Produtos disponíveis:");
            produtos.forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " | R$" + p.getPreco()));
            System.out.print("ID do produto: ");
            Long produtoId = sc.nextLong();
            sc.nextLine();
            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            if (produto != null) {
                produtosSelecionados.add(produto);
                valorTotal += produto.getPreco();
            } else {
                System.out.println("Produto inválido!");
            }
            System.out.print("Adicionar outro produto? (s/n): ");
            continuar = sc.nextLine();
        } while (continuar.equalsIgnoreCase("s"));

        pedido.setProdutos(produtosSelecionados);
        pedido.setValorTotal(valorTotal);

        // Status
        System.out.print("Status do pedido (EM_PROCESSAMENTO, APROVADO, ENTREGUE): ");
        pedido.setStatus(sc.nextLine());

        pedidoRepository.save(pedido);
        System.out.println("Pedido cadastrado com sucesso!");
    }

    private void listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        pedidos.forEach(p -> {
            System.out.println("\nID: " + p.getId() + " | Cliente: " + p.getCliente().getNome() + " | Email: " + p.getEmail());
            System.out.println("Produtos:");
            p.getProdutos().forEach(prod -> System.out.println(" - " + prod.getNome() + " | R$" + prod.getPreco()));
            System.out.println("Valor Total: R$" + p.getValorTotal() + " | Status: " + p.getStatus());
        });
    }

    private void atualizarStatus(Scanner sc) {
        listar();
        System.out.print("ID do pedido para atualizar status: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }
        System.out.print("Novo status: ");
        p.setStatus(sc.nextLine());
        pedidoRepository.save(p);
        System.out.println("Status atualizado!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID do pedido para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        pedidoRepository.deleteById(id);
        System.out.println("Pedido removido!");
    }
}
