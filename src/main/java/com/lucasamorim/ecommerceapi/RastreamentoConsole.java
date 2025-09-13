package com.lucasamorim.ecommerceapi;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.Pedido;
import com.lucasamorim.ecommerceapi.model.Rastreamento;
import com.lucasamorim.ecommerceapi.repository.PedidoRepository;
import com.lucasamorim.ecommerceapi.repository.RastreamentoRepository;

@Component
public class RastreamentoConsole {

    @Autowired
    private RastreamentoRepository rastreamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD RASTREAMENTO ===");
            System.out.println("1 - Cadastrar Rastreamento");
            System.out.println("2 - Listar Rastreamentos");
            System.out.println("3 - Atualizar Rastreamento");
            System.out.println("4 - Deletar Rastreamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrar(sc);
                case 2 -> listar();
                case 3 -> atualizar(sc);
                case 4 -> deletar(sc);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar(Scanner sc) {
        Rastreamento r = new Rastreamento();

        // Escolher pedido
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado!");
            return;
        }
        System.out.println("Pedidos disponíveis:");
        pedidos.forEach(p -> System.out.println(p.getId() + " - Cliente: " + p.getCliente().getNome() + " | Status: " + p.getStatus()));
        System.out.print("ID do pedido: ");
        Long pedidoId = sc.nextLong();
        sc.nextLine();
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido == null) {
            System.out.println("Pedido inválido!");
            return;
        }
        r.setPedido(pedido);

        // Dados do rastreamento
        System.out.print("Etapa: ");
        r.setEtapa(sc.nextLine());
        System.out.print("Status: ");
        r.setStatus(sc.nextLine());
        r.setDataAtualizacao(LocalDateTime.now());

        rastreamentoRepository.save(r);
        System.out.println("Rastreamento cadastrado!");
    }

    private void listar() {
        List<Rastreamento> rastreamentos = rastreamentoRepository.findAll();
        rastreamentos.forEach(r -> {
            System.out.println("\nID: " + r.getId() +
                               " | Pedido: " + r.getPedido().getId() +
                               " | Etapa: " + r.getEtapa() +
                               " | Status: " + r.getStatus() +
                               " | Data: " + r.getDataAtualizacao());
        });
    }

    private void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do rastreamento para atualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Rastreamento r = rastreamentoRepository.findById(id).orElse(null);
        if (r == null) {
            System.out.println("Rastreamento não encontrado!");
            return;
        }

        System.out.print("Nova etapa (" + r.getEtapa() + "): ");
        r.setEtapa(sc.nextLine());
        System.out.print("Novo status (" + r.getStatus() + "): ");
        r.setStatus(sc.nextLine());
        r.setDataAtualizacao(LocalDateTime.now());

        rastreamentoRepository.save(r);
        System.out.println("Rastreamento atualizado!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID do rastreamento para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        rastreamentoRepository.deleteById(id);
        System.out.println("Rastreamento removido!");
    }
}
