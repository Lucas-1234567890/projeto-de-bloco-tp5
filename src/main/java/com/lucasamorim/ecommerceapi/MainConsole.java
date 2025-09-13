package com.lucasamorim.ecommerceapi;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainConsole implements CommandLineRunner {

    @Autowired
    private ProdutoConsole produtoConsole;

    @Autowired
    private ClienteConsole clienteConsole;

    @Autowired
    private CarrinhoConsole carrinhoConsole; // <<< Adicionado

    @Autowired
    private CupomConsole cupomConsole;

    @Autowired
    private PedidoConsole pedidoConsole;

    @Autowired
    private RastreamentoConsole rastreamentoConsole;

    @Autowired
    private AvaliacaoProdutoConsole avaliacaoProdutoConsole;

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Gerenciar Produtos");
            System.out.println("2 - Gerenciar Clientes");
            System.out.println("3 - Gerenciar Carrinho / Endereços"); // <<< Nova opção
            System.out.println("4 - Gerenciar Cupons");
            System.out.println("5 - Gerenciar Pedidos");
            System.out.println("6 - Gerenciar Rastreamentos");
            System.out.println("7 - Gerenciar Avaliações de Produto");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> produtoConsole.menu();
                case 2 -> clienteConsole.menu();
                case 3 -> carrinhoConsole.menu(); // <<< Chama o CarrinhoConsole
                case 4 -> cupomConsole.menu();
                case 5 -> pedidoConsole.menu();
                case 6 -> rastreamentoConsole.menu();
                case 7 -> avaliacaoProdutoConsole.menu();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}
