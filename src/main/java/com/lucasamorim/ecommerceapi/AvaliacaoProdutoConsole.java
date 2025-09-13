package com.lucasamorim.ecommerceapi;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucasamorim.ecommerceapi.model.AvaliacaoProduto;
import com.lucasamorim.ecommerceapi.model.Cliente;
import com.lucasamorim.ecommerceapi.model.Produto;
import com.lucasamorim.ecommerceapi.repository.AvaliacaoProdutoRepository;
import com.lucasamorim.ecommerceapi.repository.ClienteRepository;
import com.lucasamorim.ecommerceapi.repository.ProdutoRepository;

@Component
public class AvaliacaoProdutoConsole {

    @Autowired
    private AvaliacaoProdutoRepository avaliacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CRUD AVALIAÇÃO PRODUTO ===");
            System.out.println("1 - Cadastrar Avaliação");
            System.out.println("2 - Listar Avaliações");
            System.out.println("3 - Atualizar Avaliação");
            System.out.println("4 - Deletar Avaliação");
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
        AvaliacaoProduto a = new AvaliacaoProduto();

        // Escolher cliente
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
            return;
        }
        System.out.println("Clientes disponíveis:");
        clientes.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        System.out.print("ID do cliente: ");
        Long clienteId = sc.nextLong();
        sc.nextLine();
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            System.out.println("Cliente inválido!");
            return;
        }
        a.setCliente(cliente);

        // Escolher produto
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado!");
            return;
        }
        System.out.println("Produtos disponíveis:");
        produtos.forEach(p -> System.out.println(p.getId() + " - " + p.getNome()));
        System.out.print("ID do produto: ");
        Long produtoId = sc.nextLong();
        sc.nextLine();
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) {
            System.out.println("Produto inválido!");
            return;
        }
        a.setProduto(produto);

        // Dados da avaliação
        System.out.print("Nota (1-5): ");
        a.setNota(sc.nextInt());
        sc.nextLine();
        System.out.print("Comentário: ");
        a.setComentario(sc.nextLine());

        avaliacaoRepository.save(a);
        System.out.println("Avaliação cadastrada!");
    }

    private void listar() {
        List<AvaliacaoProduto> avaliacoes = avaliacaoRepository.findAll();
        avaliacoes.forEach(a -> {
            System.out.println("\nID: " + a.getId() +
                               " | Cliente: " + a.getCliente().getNome() +
                               " | Produto: " + a.getProduto().getNome() +
                               " | Nota: " + a.getNota() +
                               " | Comentário: " + a.getComentario());
        });
    }

    private void atualizar(Scanner sc) {
        listar();
        System.out.print("ID da avaliação para atualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        AvaliacaoProduto a = avaliacaoRepository.findById(id).orElse(null);
        if (a == null) {
            System.out.println("Avaliação não encontrada!");
            return;
        }

        System.out.print("Nova nota (" + a.getNota() + "): ");
        a.setNota(sc.nextInt());
        sc.nextLine();
        System.out.print("Novo comentário (" + a.getComentario() + "): ");
        a.setComentario(sc.nextLine());

        avaliacaoRepository.save(a);
        System.out.println("Avaliação atualizada!");
    }

    private void deletar(Scanner sc) {
        listar();
        System.out.print("ID da avaliação para deletar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        avaliacaoRepository.deleteById(id);
        System.out.println("Avaliação removida!");
    }
}
